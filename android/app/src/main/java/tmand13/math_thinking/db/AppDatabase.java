package tmand13.math_thinking.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

/**
 * Created by tmand on 4/17/2018.
 */

@Database(entities = {Article.class, Category.class, SuperCategory.class, Task.class, Test.class,
        TestCategory.class, ArticleCategory.class, SuperCategoryCategory.class, TaskTest.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
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

    public synchronized static AppDatabase getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            insertTasks(context);
                                            insertTaskWithOptions(context);
                                            insertTwoDummyTasks(context);
                                            insertTests(context);
                                            insertSuperCategories(context);
                                            insertCategories(context);
                                            insertSuperCategoryCategories(context);
                                            insertArticles(context);
                                            insertArticleCategories(context);
                                            insertTestCategories(context);
                                            insertTaskTests(context);
                                        }
                                    });
                                }
                            })
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    private static void insertTestCategories(Context context) {
        getAppDatabase(context).testCategoryDao().insertAll(
                new TestCategory(1, 1, 1),
                new TestCategory(2, 2, 2),
                new TestCategory(3, 3, 3),
                new TestCategory(4, 4, 4),
                new TestCategory(5, 5, 5),
                new TestCategory(6, 6, 6),
                new TestCategory(7, 7, 7),
                new TestCategory(8, 8, 8),
                new TestCategory(9, 9, 9),
                new TestCategory(10, 10, 10),
                new TestCategory(11, 11, 11));
    }

    private static void insertSuperCategoryCategories(Context context) {
        getAppDatabase(context).superCategoryCategoryDao().insertAll(
            new SuperCategoryCategory(1, 1, 1),
            new SuperCategoryCategory(2, 1, 2),
            new SuperCategoryCategory(3, 1, 3),
            new SuperCategoryCategory(4, 1, 4),
            new SuperCategoryCategory(5, 1, 5),
            new SuperCategoryCategory(6, 1, 6),
            new SuperCategoryCategory(7, 1, 7),
            new SuperCategoryCategory(8, 1, 8),
            new SuperCategoryCategory(9, 2, 9),
            new SuperCategoryCategory(10, 2, 10),
            new SuperCategoryCategory(11, 3, 11));
    }

    private static void insertSuperCategories(Context context) {
        getAppDatabase(context).superCategoryDao().insertAll(
            new SuperCategory(1, "Logic", "ლოგიკა"),
            new SuperCategory(4, "Procents", "პროცენტები"),
            new SuperCategory(2, "graph theory", "გრაფთა თეორია"),
            new SuperCategory(3, "Geometry", "გეომეტრიააააააააააააა"));
    }
    //TODO maybe give numbers like 1. Logic 1.1 Logic puzzles so on programmatically
    private static void insertCategories(Context context) {
        getAppDatabase(context).categoryDao().insertAll(
            new Category(1, "Puzzles", "პაზლები"),
            new Category(2, "Crosswords1", "კროსვორდები11111111111111"),
            new Category(3, "Crosswords2", "კროსვორდები2"),
            new Category(4, "Crosswords3", "კროსვორდები3"),
            new Category(5, "Crosswords4", "კროსვორდები4"),
            new Category(6, "Crosswords5", "კროსვორდები5"),
            new Category(7, "Crosswords6", "კროსვორდები6"),
            new Category(8, "Crosswords7", "კროსვორდები7"),
            new Category(9, "trees", "ხეები"),
            new Category(10, "dijsktra", "დეიქსტრა"),
            new Category(11, "Triangle", "სამკუთხედი"));
    }

    private static void insertArticleCategories(Context context) {
        getAppDatabase(context).articleCategoryDao().insertAll(
            new ArticleCategory(1, 1, 1),
            new ArticleCategory(2, 2, 2),
            new ArticleCategory(3, 3, 3),
            new ArticleCategory(4, 4, 4),
            new ArticleCategory(5, 5, 5),
            new ArticleCategory(6, 6, 6),
            new ArticleCategory(7, 7, 7),
            new ArticleCategory(8, 8, 8),
            new ArticleCategory(9, 9, 9),
            new ArticleCategory(10, 10, 10),
            new ArticleCategory(11, 11, 11));
    }

    private static void insertArticles(Context context) {
        getAppDatabase(context).articleDao().insertAll(
            new Article(1, "Puzzles","პაზლები", "puzzles",
                    "პაზლები"),
            new Article(2, "Crosswords1","კროსვორდები11111111111111",
                    "Crosswords1", "კროსვორდები11111111111111"),
            new Article(3, "Crosswords2","კროსვორდები2",
                    "Crosswords2", "კროსვორდები2"),
            new Article(4, "Crosswords3","კროსვორდები3",
                    "Crosswords3", "კროსვორდები3"),
            new Article(5, "Crosswords4","კროსვორდები4",
                    "Crosswords4", "კროსვორდები4"),
            new Article(6, "Crosswords5","კროსვორდები5",
                    "Crosswords5", "კროსვორდები5"),
            new Article(7, "Crosswords6","კროსვორდები6",
                    "Crosswords6", "კროსვორდები6"),
            new Article(8, "Crosswords7","კროსვორდები7",
                    "Crosswords7", "კროსვორდები7"),
            new Article(9, "trees","ხეები", "trees",
                    "ხეები"),
            new Article(10, "dijsktra","დეიქსტრა", "dijsktra",
                    "დეიქსტრა"),
            new Article(11, "Triangle","სამკუთხედი", "Triangle",
                    "სამკუთხედი"));
    }

    private static void insertTwoDummyTasks(Context context) {
        Task[] tasks = new Task[4];
        for (int i = 102; i <= 105; i++) {
            tasks[i - 102] = new Task(i, "magari amocana"+String.valueOf(i),
                    "მაგარი ამოცანა", "es aris descriptioni",
                    "ეს არის დესკრიპშენი", "g", "ჰინტი",
                    "pasuxii", "პასუხი", 1, 2,
                    2, i%2==0, "option pirveli",
                    "პირველი", "meore", "მეორე",
                    "mesame", "მესამე", "meotxe",
                    "მეოთხე");
        }
        getAppDatabase(context).taskDao().insertAll(tasks);
    }

    private static void insertTasks(Context context) {
        Task[] tasks = new Task[4];
        for (int i = 1; i <= 100; i++) {
            tasks[i - 1] = new Task(i, "gela"+String.valueOf(i), "გელა",
                    "d", "დ", "g", "ჰინტი", "g",
                    "პასუხი", 1, 2, 2,
                    false, "d", "დ", "D",
                    "დდ", "d", "დდდ", "D",
                    "დდდდ");
        }
        getAppDatabase(context).taskDao().insertAll(tasks);
    }

    private static void insertTaskWithOptions(Context context) {
        getAppDatabase(context).taskDao().insertAll(
            new Task(100, "gela"+String.valueOf(100), "გელსონა",
                    "d", "აეეეე", "g", "გგ", "g",
                    "პასუხი", 1, 2, 2, true,
                    "d", "დ", "D", "დდ", "D",
                    "დდდ", "D", "დდდდ"));
    }

    private static void insertTests(Context context) {
        Test[] tests = new Test[100];
        for (int i = 1; i <= 100; i++) {
            tests[i - 1] = new Test(i, "test" + String.valueOf(i),
                    "ტესტი" + String.valueOf(i));
        }
        getAppDatabase(context).testDao().insertAll(tests);
    }

    private static void insertTaskTests(Context context) {
        TaskTest[] taskTests = new TaskTest[300];
        for (int i = 1; i <= 100; i++) {
            taskTests[3*i - 3] = new TaskTest(3*i-2, i, i);
            taskTests[3*i - 2] = new TaskTest(3*i-1, i+1, i);
            taskTests[3*i - 1] = new TaskTest(3*i, 104, i);
        }
        getAppDatabase(context).taskTestDao().insertAll(taskTests);
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}