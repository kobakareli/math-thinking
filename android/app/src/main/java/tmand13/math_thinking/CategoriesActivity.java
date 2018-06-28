package tmand13.math_thinking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Article;
import tmand13.math_thinking.db.ArticleCategory;
import tmand13.math_thinking.db.Category;
import tmand13.math_thinking.db.SuperCategory;
import tmand13.math_thinking.db.SuperCategoryCategory;

public class CategoriesActivity extends BaseActivity {
    public static final String ARTICLE_ID = "article_id";

    List<Integer> superCategories;
    Map<Integer, List<Integer>> superCategoriesToCategories;
    Map<Integer, Integer> categoriesToArticles;
    Map<Integer, String> superCategoriesIdsToTitles;
    Map<Integer, String> categoriesIdsToTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_categories);
        setTitle(R.string.articles);

        fetchDataFromDB();

        RecyclerView recyclerView = findViewById(R.id.super_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new SuperCategoriesAdapter(recyclerView));
    }

    private void fetchDataFromDB() {
        superCategories = new ArrayList<>();
        superCategoriesToCategories = new HashMap<>();
        categoriesToArticles = new HashMap<>();
        superCategoriesIdsToTitles = new HashMap<>();
        categoriesIdsToTitles = new HashMap<>();

        final AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (ArticleCategory articleCategory : db.articleCategoryDao().getAll()) {
            categoriesToArticles.put(articleCategory.getCategoryId(),
                    articleCategory.getArticleId());
        }
        for (SuperCategoryCategory superCategoryCategory : db.superCategoryCategoryDao().getAll()) {
            int superCategoryId = superCategoryCategory.getSuperCategoryId();
            int categoryId = superCategoryCategory.getCategoryId();
            if (!superCategoriesToCategories.containsKey(superCategoryId)) {
                superCategoriesToCategories.put(superCategoryId, new ArrayList<Integer>());
                superCategories.add(superCategoryId);
            }
            superCategoriesToCategories.get(superCategoryId).add(categoryId);
        }
        for (SuperCategory superCategory : db.superCategoryDao().getAll()) {
            int superCategoryId = superCategory.getSuperCategoryId();
            superCategoriesIdsToTitles.put(superCategory.getSuperCategoryId(),
                    superCategory.getTitle(getBaseContext()));
            if (!superCategoriesToCategories.containsKey(superCategoryId)) {
                superCategoriesToCategories.put(superCategoryId, new ArrayList<Integer>());
                superCategories.add(superCategoryId);
            }
        }
        for (Category category : db.categoryDao().getAll()) {
            categoriesIdsToTitles.put(category.getCategoryId(),
                    category.getTitle(getBaseContext()));
        }
    }

    private class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
        private RecyclerView recyclerView;
        private List<Integer> categories;

        CategoriesAdapter(RecyclerView recyclerView, int superCategoryId) {
            this.recyclerView = recyclerView;
            this.categories = superCategoriesToCategories.get(superCategoryId);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind();
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private Button articleLink;

            ViewHolder(View itemView) {
                super(itemView);
                articleLink = itemView.findViewById(R.id.article_link);
                articleLink.setOnClickListener(this);
            }

            void bind() {
                int position = getAdapterPosition();
                articleLink.setText(categoriesIdsToTitles.get(categories.get(position)));
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                openArticle(categoriesToArticles.get(categories.get(position)));
            }
        }
    }

    private class SuperCategoriesAdapter extends RecyclerView.Adapter<SuperCategoriesAdapter.ViewHolder> {
        private static final int UNSELECTED = -1;

        private RecyclerView recyclerView;
        private int selectedItem = UNSELECTED;

        SuperCategoriesAdapter(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.categories, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind();
        }

        @Override
        public int getItemCount() {
            return superCategories.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
            private ExpandableLayout expandableLayout;
            private RecyclerView recyclerViewCategories;
            private Button expandButton;

            ViewHolder(View itemView) {
                super(itemView);

                expandableLayout = itemView.findViewById(R.id.expandable_layout);
                expandableLayout.setInterpolator(new OvershootInterpolator());
                expandableLayout.setOnExpansionUpdateListener(this);

                expandButton = itemView.findViewById(R.id.expand_button);
                expandButton.setOnClickListener(this);
            }

            void bind() {
                int position = getAdapterPosition();
                boolean isSelected = position == selectedItem;

                expandButton.setText(superCategoriesIdsToTitles.get(superCategories.get(position)));
                expandButton.setSelected(isSelected);

                recyclerViewCategories = expandableLayout.findViewById(R.id.categories);
                recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerViewCategories.setAdapter(new CategoriesAdapter(recyclerViewCategories,
                        superCategories.get(position)));

                expandableLayout.setExpanded(isSelected, false);
            }

            @Override
            public void onClick(View view) {
                ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
                if (holder != null) {
                    holder.expandButton.setSelected(false);
                    holder.expandableLayout.collapse();
                }

                int position = getAdapterPosition();
                if (position == selectedItem) {
                    selectedItem = UNSELECTED;
                } else {
                    expandButton.setSelected(true);
                    expandableLayout.expand();
                    selectedItem = position;
                }
            }

            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                if (state == ExpandableLayout.State.EXPANDING) {
                    // We need this, because sometimes selectedItem is -1 and
                    // smoothScrollToPosition crashes.
                    int scrollPosition = selectedItem < 0 ? 0 : selectedItem;
                    recyclerView.smoothScrollToPosition(scrollPosition);
                }
            }
        }
    }

    public void openArticle(int articleId) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ARTICLE_ID, articleId);
        startActivity(intent);
    }
}
