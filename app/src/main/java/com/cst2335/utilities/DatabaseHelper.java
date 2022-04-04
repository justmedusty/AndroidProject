package com.cst2335.utilities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * The type Database helper.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * The constant DATABASE_NAME.
     */
    public static String DATABASE_NAME = "Database";
    /**
     * The constant TABLE_NAME.
     */
    public static String TABLE_NAME = "RecipeTable";
    /**
     * The constant VERSION.
     */
    public static int VERSION = 3;
    /**
     * The constant KEY_TITLE.
     */
    public static String KEY_TITLE = "title";
    /**
     * The constant KEY_INGREDIENTS.
     */
    public static String KEY_INGREDIENTS = "ingredients";
    /**
     * The constant KEY_URL.
     */
    public static String KEY_URL = "url";
    /**
     * The constant KEY_ID.
     */
    public static String KEY_ID = "id";


    /**
     * Instantiates a new Database helper.
     *
     * @param context the context
     * @param name    the name
     * @param factory the factory
     * @param version the version
     */
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                " CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_TITLE + " TEXT UNIQUE, " + KEY_INGREDIENTS + " TEXT UNIQUE, " + KEY_URL + " TEXT UNIQUE)"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    /**
     * Insert into database.
     *
     * @param title       the title
     * @param ingredients the ingredients
     * @param url         the url
     */
    public void insertIntoDatabase(String title, String ingredients, String url) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put(KEY_TITLE, title);
        cValues.put(KEY_INGREDIENTS, ingredients);
        cValues.put(KEY_URL, url);

        sqLiteDatabase.insert(TABLE_NAME, null, cValues);


    }

    /**
     * Remove from database.
     *
     * @param url the url
     */
    public void removeFromDatabase(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_URL + " = " + "\"" + url + "\"");
    }

    /**
     * Select all cursor.
     *
     * @return the cursor
     */
    public Cursor selectAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    /**
     * Check for record boolean.
     *
     * @param url the url
     * @return the boolean
     */
    public boolean checkForRecord(String url) {
        Cursor cursor = selectAll();
        boolean isMatch = false;
        if(cursor != null){
            if (cursor.moveToFirst()){
                do {
                    @SuppressLint("Range") String urlToCheck = cursor.getString(cursor.getColumnIndex(KEY_URL));
                    if(urlToCheck.equals(url)){
                        isMatch = true;
                    }

                }while (cursor.moveToNext() && !isMatch);

            }
            cursor.close();
        }
        return isMatch;
    }
}
