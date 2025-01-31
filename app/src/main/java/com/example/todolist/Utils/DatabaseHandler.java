package com.example.todolist.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    public static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TASK + "TEXT," + STATUS + " INTEGER)";




    public DatabaseHandler(@Nullable Context context) {
        super(context, NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
    }


    public void openDatabase() {
        db = this.getWritableDatabase();

    }

    public void inserttask(ToDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);

    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTask() {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
db.beginTransaction();
try {
    cur = db.query(TODO_TABLE,null,null,null,null,null,null,null);
    if (cur !=null){
        if (cur.moveToFirst()){
            do {
                ToDoModel task = new ToDoModel();
                task.setId(cur.getInt(cur.getColumnIndex(ID)));
                task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                taskList.add(task);

            }while (cur.moveToNext());
        }
    }
  }finally {
    db.endTransaction();
    cur.close();
       }
        return taskList;
    }
    public void updateTask(int id , String task){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK , task);
        db.update(TODO_TABLE , cv , "ID=?" , new String[]{String.valueOf(id)});
    }
    public void updateStatus(int id , int status){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STATUS , status);
        db.update(TODO_TABLE , cv, "ID=?" , new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id ){
        db = this.getWritableDatabase();
        db.delete(TODO_TABLE , "ID=?" , new String[]{String.valueOf(id)});
    }
}