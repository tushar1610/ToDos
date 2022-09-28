package com.example.android.todos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "todoDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "todoTable";
    private static final String ID_COL = "id";
    private static final String TNAME_COL = "taskName";
    private static final String DATE_COL = "date";
    private static final String TIME_COL = "time";

    public DbHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TNAME_COL + " TEXT NOT NULL, "
                + DATE_COL + " TEXT NOT NULL, "
                + TIME_COL + " TEXT NOT NULL)";

        db.execSQL(query);
    }

    public void addNewTask(todo td){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TNAME_COL, td.newText);
        values.put(DATE_COL, td.reminderDate);
        values.put(TIME_COL, td.reminderTime);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<todo> readTask(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<todo> arrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                boolean check = false;
                if(cursor.getInt(1) != 0){
                    check = true;
                }
                arrayList.add(new todo(cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public void updateTask(todo td){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TNAME_COL, td.newText);
        values.put(DATE_COL, td.reminderDate);
        values.put(TIME_COL, td.reminderTime);

        sqLiteDatabase.update(TABLE_NAME, values, "name=?", new String[]{td.newText});
        sqLiteDatabase.close();
    }

    public void deleteTask(String newText) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "taskName=?", new String[]{newText});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
