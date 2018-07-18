package tmand13.math_thinking.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import tmand13.math_thinking.FirstTimeCalledWrapper;

/**
 * Created by tmand on 4/17/2018.
 */

@Database(entities = {Article.class, Category.class, SuperCategory.class, Task.class, Test.class,
        TestCategory.class, ArticleCategory.class, SuperCategoryCategory.class, TaskTest.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "app-db";
    private static final boolean COPY_FILE = true;

    //TODO: https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/db/AppDatabase.java
    // https://developer.android.com/reference/java/util/concurrent/Executor.html
    private static AppDatabase INSTANCE;

    public abstract ArticleDao articleDao();

    public abstract CategoryDao categoryDao();

    public abstract SuperCategoryDao superCategoryDao();

    public abstract TaskDao taskDao();

    public abstract TestDao testDao();

    public abstract TestCategoryDao testCategoryDao();

    public abstract SuperCategoryCategoryDao superCategoryCategoryDao();

    public abstract ArticleCategoryDao articleCategoryDao();

    public abstract TaskTestDao taskTestDao();

    public static void copyDBFileIfFirstTimeCalled(Context context) {
        FirstTimeCalledWrapper firstTimeCalledWrapper = new FirstTimeCalledWrapper(context);
        firstTimeCalledWrapper.setFirstTimeCalled(true);
        if (firstTimeCalledWrapper.firstTimeCalled()) {
            if (COPY_FILE) {
                try {
                    String db_out_path = context.getDatabasePath(DB_NAME).toString();
                    File db_out_file = new File(db_out_path);
                    InputStream db_in = context.getAssets().open(DB_NAME);
                    FileOutputStream db_out = new FileOutputStream(db_out_file);

                    boolean keepGoing = true;
                    byte[] buffer = new byte[20000];

                    // TODO maybe delete db file to avoid double use of memory

                    while (keepGoing) {
                        int bytesReturned = db_in.read(buffer);
                        if (bytesReturned > 0) {
                            db_out.write(buffer, 0, bytesReturned);
                        } else {
                            keepGoing = false;
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                insertData(context);
            }

            firstTimeCalledWrapper.setFirstTimeCalled(false);
        }
    }

    private static void insertData(Context context) {
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open("db.json");
            String data = IOUtils.toString(inputStream, Charset.defaultCharset());
            // In website every image file path needs "/" at the beginning, but in android it's
            // not needed
            data = data.replaceAll("/img", "img");
            insertDataFromJson(context, data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void insertDataFromJson(Context context, String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        insertArticles(context, jsonObject);
        insertCategories(context, jsonObject);
        insertSuperCategories(context, jsonObject);
        insertSuperCategoryCategories(context, jsonObject);
        insertArticleCategories(context, jsonObject);
        insertTasks(context, jsonObject);
        insertTests(context, jsonObject);
        insertTaskTests(context, jsonObject);
        insertTestCategories(context, jsonObject);
    }

    private static void initializeInstance(Context context) {
        INSTANCE =
                Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build();
    }

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            initializeInstance(context);
        }
        return INSTANCE;
    }

    public static void rebuildAppDatabase(Context context) {
        initializeInstance(context);
    }

    private static void insertTestCategories(Context context, JSONObject jsonObject) {
        TestCategoryDao testCategoryDao = getAppDatabase(context).testCategoryDao();
        TestDao testDao = getAppDatabase(context).testDao();
        CategoryDao categoryDao = getAppDatabase(context).categoryDao();

        try {
            JSONArray testCategories = jsonObject.getJSONArray("math_db.tests_categories");
            for (int i = 0; i < testCategories.length(); i++) {
                JSONObject testCategory = (JSONObject) testCategories.get(i);
                int id = testCategory.getInt("id");
                int testId = testCategory.getInt("test_id");
                int categoryId = testCategory.getInt("category_id");
                if (testDao.contains(testId) > 0 && categoryDao.contains(categoryId) > 0) {
                    testCategoryDao.insert(new TestCategory(id, categoryId, testId));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void insertSuperCategoryCategories(Context context, JSONObject jsonObject) {
        SuperCategoryCategoryDao superCategoryCategoryDao = getAppDatabase(context).superCategoryCategoryDao();
        SuperCategoryDao superCategoryDao = getAppDatabase(context).superCategoryDao();
        CategoryDao categoryDao = getAppDatabase(context).categoryDao();
        try {
            JSONArray superCategoriesCategories = jsonObject.getJSONArray("math_db.super_categories_categories");
            for (int i = 0; i < superCategoriesCategories.length(); i++) {
                JSONObject superCategoryCategory = (JSONObject) superCategoriesCategories.get(i);
                int id = superCategoryCategory.getInt("id");
                int superCategoryId = superCategoryCategory.getInt("super_category_id");
                int categoryId = superCategoryCategory.getInt("category_id");
                if (superCategoryDao.contains(superCategoryId) > 0 &&
                        categoryDao.contains(categoryId) > 0) {
                    superCategoryCategoryDao.insert(new SuperCategoryCategory(
                            id, superCategoryId, categoryId));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void insertSuperCategories(Context context, JSONObject jsonObject) {
        SuperCategoryDao superCategoryDao = getAppDatabase(context).superCategoryDao();
        try {
            JSONArray superCategories = jsonObject.getJSONArray("math_db.super_categories");
            for (int i = 0; i < superCategories.length(); i++) {
                JSONObject superCategory = (JSONObject) superCategories.get(i);
                int id = superCategory.getInt("id");
                String titleEn = superCategory.getString("title_en");
                String titleGe = superCategory.getString("title_ge");
                superCategoryDao.insert(new SuperCategory(id, titleEn, titleGe));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void insertCategories(Context context, JSONObject jsonObject) {
        CategoryDao categoryDao = getAppDatabase(context).categoryDao();
        try {
            JSONArray categories = jsonObject.getJSONArray("math_db.categories");
            for (int i = 0; i < categories.length(); i++) {
                JSONObject category = (JSONObject) categories.get(i);
                int id = category.getInt("id");
                String titleEn = category.getString("title_en");
                String titleGe = category.getString("title_ge");
                categoryDao.insert(new Category(id, titleEn, titleGe));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void insertArticleCategories(Context context, JSONObject jsonObject) {
        ArticleCategoryDao articleCategoryDao = getAppDatabase(context).articleCategoryDao();
        ArticleDao articleDao = getAppDatabase(context).articleDao();
        CategoryDao categoryDao = getAppDatabase(context).categoryDao();
        try {
            JSONArray articlesCategories = jsonObject.getJSONArray("math_db.articles_categories");
            for (int i = 0; i < articlesCategories.length(); i++) {
                JSONObject articleCategory = (JSONObject) articlesCategories.get(i);
                int id = articleCategory.getInt("id");
                int articleId = articleCategory.getInt("article_id");
                int categoryId = articleCategory.getInt("category_id");
                if (articleDao.contains(articleId) > 0 && categoryDao.contains(categoryId) > 0) {
                    articleCategoryDao.insert(new ArticleCategory(id, articleId, categoryId));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void insertArticles(Context context, JSONObject jsonObject) {
        ArticleDao articleDao = getAppDatabase(context).articleDao();
        try {
            JSONArray articles = jsonObject.getJSONArray("math_db.articles");
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = (JSONObject) articles.get(i);
                int id = article.getInt("id");
                String titleEn = article.getString("title_en");
                String titleGe = article.getString("title_ge");
                String textEn = article.getString("text_en");
                String textGe = article.getString("text_ge");
                articleDao.insert(new Article(id, titleEn, titleGe, textEn, textGe));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void insertTasks(Context context, JSONObject jsonObject) {
        TaskDao taskDao = getAppDatabase(context).taskDao();
        try {
            JSONArray tasks = jsonObject.getJSONArray("math_db.tasks");
            for (int i = 0; i < tasks.length(); i++) {
                JSONObject task = (JSONObject) tasks.get(i);
                int id = task.getInt("id");
                String titleEn = task.getString("title_en");
                String titleGe = task.getString("title_ge");
                String descriptionEn = task.getString("description_en");
                String descriptionGe = task.getString("description_ge");
                String hintEn = task.getString("hint_en");
                String hintGe = task.getString("hint_ge");
                String answerEn = task.getString("answer_en");
                String answerGe = task.getString("answer_ge");
                int numericAnswer = task.getInt("numeric_answer");
                int totalAnswers = task.getInt("total_answers");
                int correctAnswers = task.getInt("correct_answers");
                boolean hasOptions = task.getInt("has_options") > 0;
                String option1En = task.getString("option_1_en");
                String option1Ge = task.getString("option_1_ge");
                String option2En = task.getString("option_2_en");
                String option2Ge = task.getString("option_2_ge");
                String option3En = task.getString("option_3_en");
                String option3Ge = task.getString("option_3_ge");
                String option4En = task.getString("option_4_en");
                String option4Ge = task.getString("option_4_ge");
                String nullString = "null";
                String emptyString = "";
                if (option1En.equals(nullString)) {
                    option1En = emptyString;
                }
                if (option1Ge.equals(nullString)) {
                    option1Ge = emptyString;
                }
                if (option2En.equals(nullString)) {
                    option2En = emptyString;
                }
                if (option2Ge.equals(nullString)) {
                    option2Ge = emptyString;
                }
                if (option3En.equals(nullString)) {
                    option3En = emptyString;
                }
                if (option3Ge.equals(nullString)) {
                    option3Ge = emptyString;
                }
                if (option4En.equals(nullString)) {
                    option4En = emptyString;
                }
                if (option4Ge.equals(nullString)) {
                    option4Ge = emptyString;
                }
                taskDao.insert(new Task(id, titleEn, titleGe, descriptionEn, descriptionGe,
                        hintEn, hintGe, answerEn, answerGe, numericAnswer, totalAnswers,
                        correctAnswers, hasOptions, option1En, option1Ge, option2En, option2Ge,
                        option3En, option3Ge, option4En, option4Ge, false));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void insertTests(Context context, JSONObject jsonObject) {
        TestDao testDao = getAppDatabase(context).testDao();
        try {
            JSONArray tests = jsonObject.getJSONArray("math_db.tests");
            for (int i = 0; i < tests.length(); i++) {
                JSONObject test = (JSONObject) tests.get(i);
                int id = test.getInt("id");
                String titleEn = test.getString("title_en");
                String titleGe = test.getString("title_ge");
                testDao.insert(new Test(id, titleEn, titleGe, false));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void insertTaskTests(Context context, JSONObject jsonObject) {
        TaskTestDao taskTestDao = getAppDatabase(context).taskTestDao();
        TaskDao taskDao = getAppDatabase(context).taskDao();
        TestDao testDao = getAppDatabase(context).testDao();
        try {
            JSONArray taskTests = jsonObject.getJSONArray("math_db.tasks_tests");
            for (int i = 0; i < taskTests.length(); i++) {
                JSONObject taskTest = (JSONObject) taskTests.get(i);
                int id = taskTest.getInt("id");
                int taskId = taskTest.getInt("task_id");
                int testId = taskTest.getInt("test_id");
                if (taskDao.contains(taskId) > 0 && testDao.contains(testId) > 0) {
                    taskTestDao.insert(new TaskTest(id, taskId, testId));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}