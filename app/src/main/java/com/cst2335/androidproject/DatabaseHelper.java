package com.cst2335.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "Database";
    public static String TABLE_NAME = "RecipeTable";
    public static int VERSION = 1;
    public static String KEY_TITLE = "title";
    public static String KEY_INGREDIENTS = "ingredients";
    public static String KEY_URL = "url";
    public static String KEY_ID = "id";





    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
