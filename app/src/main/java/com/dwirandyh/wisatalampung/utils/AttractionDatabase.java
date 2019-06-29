package com.dwirandyh.wisatalampung.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dwirandyh.wisatalampung.model.WishList;
import com.dwirandyh.wisatalampung.model.WishListDAO;

@Database(entities = {WishList.class}, version = 1, exportSchema = false)
public abstract class AttractionDatabase extends RoomDatabase {

    public abstract WishListDAO wishListDAO();

    private static AttractionDatabase instance;

    public static synchronized AttractionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AttractionDatabase.class, "attractions_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
