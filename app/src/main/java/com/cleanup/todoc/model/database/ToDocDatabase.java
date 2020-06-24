package com.cleanup.todoc.model.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.database.dao.ProjectDao;
import com.cleanup.todoc.model.database.dao.TaskDao;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class ToDocDatabase extends RoomDatabase {
    //-- PROPERTIES
    // SINGLETON
    private static volatile ToDocDatabase INSTANCE;

    // DAO
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    //-- METHODS
    // INSTANCE
    public static ToDocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ToDocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDocDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("name", "Projet Tartampion");
                contentValues.put("color", "0xFFEADAD1");

                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }

}