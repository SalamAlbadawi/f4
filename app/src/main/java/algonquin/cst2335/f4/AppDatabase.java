package algonquin.cst2335.f4;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ImageEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();

    private static AppDatabase db;

    public static AppDatabase getDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "image-database")
                    .build();
        }
        return db;
    }
}
