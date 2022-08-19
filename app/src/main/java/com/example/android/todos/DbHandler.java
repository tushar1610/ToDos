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
    private static final String CHECKED_COL = "isChecked";
    private static final String TNAME_COL = "taskName";

    public DbHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CHECKED_COL + " INTEGER, "
                + TNAME_COL + " TEXT NOT NULL)";

        db.execSQL(query);
    }

    public void addNewTask(todo td){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(td.check){
            values.put(CHECKED_COL, 1);
        } else {
            values.put(CHECKED_COL, 0);
        }
        values.put(TNAME_COL, td.newText);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<todo> readToDo(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<todo> arrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                boolean check = cursor.getInt(1) != 0;
                arrayList.add(new todo(cursor.getString(2), check));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}