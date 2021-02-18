package app.care.db;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import app.care.extras.Constants;

@Database(entities = {MyEntity.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public abstract MyDAO getMyDAO();

    @VisibleForTesting
    public static final String DATABASE_NAME = Constants.DATABASE_NAME;

    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context,
                    AppDatabase.class,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }

}
