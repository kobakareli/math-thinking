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

public class CategoriesActivity extends BaseActivity {
    public static final String ARTICLE_ID = "article_id";

    Map<String, List<String>> superCategoriesToCategories;
    List<String> superCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_categories);

        fetchDataFromDB();

        RecyclerView recyclerView = findViewById(R.id.super_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new SuperCategoriesAdapter(recyclerView));
    }

    private void fetchDataFromDB() {
        // TODO dummy implementation
        superCategories = new ArrayList<>();
        superCategories.add("1. Math");
        superCategories.add("2. Logic");
        superCategories.add("3. Common sense");

        superCategoriesToCategories = new HashMap<>();

        List<String> math = new ArrayList<>();
        math.add("1.1 math_1");
        math.add("1.2 math_2");
        math.add("1.3 math_3");
        superCategoriesToCategories.put(superCategories.get(0), math);

        List<String> logic = new ArrayList<>();
        logic.add("2.1 logic_1");
        logic.add("2.2 logic_2");
        logic.add("2.3 logic_3");
        superCategoriesToCategories.put(superCategories.get(1), logic);

        List<String> commonSense = new ArrayList<>();
        commonSense.add("3.1 commonsense_1");
        commonSense.add("3.2 commonsense_2");
        commonSense.add("3.3 commonsense_3");
        superCategoriesToCategories.put(superCategories.get(2), commonSense);
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
                openArticle(categories.get(position));
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

                expandButton.setText(superCategories.get(position));
                expandButton.setSelected(isSelected);

                recyclerViewCategories = expandableLayout.findViewById(R.id.categories);
                recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerViewCategories.setAdapter(new CategoriesAdapter(recyclerViewCategories, superCategories.get(position)));

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

    public void openArticle(String articleId) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ARTICLE_ID, articleId);
        startActivity(intent);
    }
}
