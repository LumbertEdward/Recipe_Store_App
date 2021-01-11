package com.example.recipestore.AllClasses;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseSQLite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Meals";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "Meals_Table";
    public static final String COLUMN_MEAL = "strMeal";
    public static final String COLUMN_THUMB = "strMealThumb";
    public static final String COLUMN_ID = "idMeal";

    public MyDatabaseSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_MEAL + " TEXT," + COLUMN_THUMB + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(Meals meals){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEAL, meals.getStrMeal());
        values.put(COLUMN_THUMB, meals.getStrMealThumb());
        values.put(COLUMN_ID, Integer.parseInt(meals.getIdMeal()));
        long id = database.insert(TABLE_NAME, null, values);
        if (id > -1){
            return true;
        }
        else {
            return false;
        }
    }

    public List<Meals> getMeals(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<Meals> mealsList = new ArrayList<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String thumb = cursor.getString(2);
            Meals meals = new Meals(name, thumb, id);
            mealsList.add(meals);
        }
        /**if (cursor.moveToFirst()){
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String thumb = cursor.getString(2);
                Meals meals = new Meals(name, thumb, id);
                mealsList.add(meals);
            } while (cursor.moveToNext());
            cursor.close();
        }**/
        return mealsList;
    }
    public boolean deleteItem(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        int value = database.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{ id });
        if (value > -1){
            return true;
        }
        else {
            return false;
        }

    }
    public boolean confirmItem(String id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[]{ COLUMN_ID }, COLUMN_ID + "=?", new String[]{ id }, null, null, null);
        if (cursor != null){
            return true;
        }
        else {
            return false;
        }

    }
}
