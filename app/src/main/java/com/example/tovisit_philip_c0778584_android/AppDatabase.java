package com.example.tovisit_philip_c0778584_android;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tovisit_philip_c0778584_android.Place;

@Database(entities = {Place.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase = null;

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database_lab_assignment").allowMainThreadQueries().build();
        }
        return appDatabase;
    }

    public abstract PlaceDao placeDao();
}
