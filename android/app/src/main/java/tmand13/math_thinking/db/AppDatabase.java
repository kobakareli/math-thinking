package tmand13.math_thinking.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by tmand on 4/17/2018.
 */

@Database(entities = {Article.class, Category.class, SuperCategory.class, Task.class, Test.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //TODO: https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/db/AppDatabase.java
    // https://developer.android.com/reference/java/util/concurrent/Executor.html
    private static AppDatabase INSTANCE;

    public abstract ArticleDao articleDao();

    public abstract CategoryDao categoryDao();

    public abstract TaskDao taskDao();

    public abstract TestDao testDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-db")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}