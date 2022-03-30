package com.cst2335.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                " CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_TITLE + " TEXT, " + KEY_INGREDIENTS + " TEXT, " + KEY_URL + " TEXT)"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void insertIntoDatabase(String title, String ingredients, String url) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put(KEY_TITLE, title);
        cValues.put(KEY_INGREDIENTS, ingredients);
        cValues.put(KEY_URL, url);

        sqLiteDatabase.insert(TABLE_NAME, null, cValues);


    }

    public Cursor selectAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
