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
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Article;
import tmand13.math_thinking.db.Category;
import tmand13.math_thinking.db.SuperCategory;

public class CategoriesActivity extends BaseActivity {
    public static final String ARTICLE_ID = "article_id";

    Map<String, List<String>> superCategoriesToCategories;
    List<String> superCategoriesTitles;
    Map<String, Integer> categoryToArticleId;

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
        superCategoriesToCategories = new HashMap<>();
        superCategoriesTitles = new ArrayList<>();
        categoryToArticleId = new HashMap<>();

        final AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        List<SuperCategory> superCategories = db.superCategoryDao().getAll();
        List<Category> categories = db.categoryDao().getAll();
        List<Article> articles = db.articleDao().getAll();
        Map<Integer, String> idToSuperCategory = new HashMap<>();
        Map<Integer, String> idToCategory = new HashMap<>();

        for (SuperCategory superCategory : superCategories) {
            String title = superCategory.getTitle(getBaseContext());
            idToSuperCategory.put(superCategory.getSuperCategoryId(), title);
            superCategoriesTitles.add(title);
            superCategoriesToCategories.put(title, new ArrayList<String>());
        }

        for (Category category : categories) {
            idToCategory.put(category.getCategoryId(), category.getTitle(getBaseContext()));
        }

        for (Article article : articles) {
            String superCategory = idToSuperCategory.get(article.getSuperCategoryId());
            String category = idToCategory.get(article.getCategoryId());
            superCategoriesToCategories.get(superCategory).add(category);
            categoryToArticleId.put(category, article.getArticleId());
        }
    }

    private class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
        private RecyclerView recyclerView;
        private List<String> categories;

        CategoriesAdapter(RecyclerView recyclerView, String superCategory) {
            this.recyclerView = recyclerView;
            this.categories = superCategoriesToCategories.get(superCategory);
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
            private TextView articleLink;

            ViewHolder(View itemView) {
                super(itemView);
                articleLink = itemView.findViewById(R.id.article_link);
                articleLink.setOnClickListener(this);
            }

            void bind() {
                int position = getAdapterPosition();
                articleLink.setText(getString(R.string.category_with_tabs, categories.get(position)));
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                openArticle(categoryToArticleId.get(categories.get(position)));
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
            return superCategoriesTitles.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
            private ExpandableLayout expandableLayout;
            private RecyclerView recyclerViewCategories;
            private TextView expandButton;

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

                expandButton.setText(superCategoriesTitles.get(position));
                expandButton.setSelected(isSelected);

                recyclerViewCategories = expandableLayout.findViewById(R.id.categories);
                recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerViewCategories.setAdapter(new CategoriesAdapter(recyclerViewCategories,
                        superCategoriesTitles.get(position)));

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
                    recyclerView.smoothScrollToPosition(getAdapterPosition());
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
