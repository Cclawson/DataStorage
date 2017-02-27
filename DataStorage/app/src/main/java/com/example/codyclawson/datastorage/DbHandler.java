package com.example.codyclawson.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.security.Key;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodyClawson on 2/22/2017.
 */

public class DbHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TaskData";

//Tasks Table Definition
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_Category = "category";
    private static final String KEY_ID = "id";
    private static final String KEY_PARENT_ID = "parentid";
    private static final String KEY_NAME = "name";
    private static final String KEY_COLOR = "color";
    private static final String KEY_TIME = "time";
    private static final String KEY_CATEGORYID = "catid";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_COMPLETED = "completed";


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +KEY_PARENT_ID + " INTEGER," + KEY_NAME + " TEXT,"
                +  KEY_COLOR + " TEXT," + KEY_TIME + " INTEGER," +
                KEY_CATEGORYID + " INTEGER," + KEY_PRIORITY + " INTEGER, " + KEY_COMPLETED + " INTEGER" +")";

        String CREATE_CAT_TABLE = "CREATE TABLE "+ TABLE_Category + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT )";

        db.execSQL(CREATE_CAT_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
// Creating tables again
        onCreate(db);
    }
    // Adding new shop
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_PARENT_ID, task.getParentId());
        values.put(KEY_NAME, task.getName());
        values.put(KEY_COLOR, task.getColor());
        values.put(KEY_TIME, task.getTime());
        values.put(KEY_CATEGORYID, task.getCategoryId());
        values.put(KEY_PRIORITY, task.getPriority());
        values.put(KEY_COMPLETED, task.getCompleted());

        db.insert(TABLE_TASKS, null, values);

    }

    public void addCat(Category cat){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, cat.getId());
        values.put(KEY_NAME, cat.getName());

        db.insert(TABLE_Category, null, values);
    }

    // Getting one shop
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID, KEY_PARENT_ID,
                        KEY_NAME, KEY_COLOR, KEY_TIME, KEY_CATEGORYID, KEY_PRIORITY, KEY_COMPLETED}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3), Long.parseLong(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),Integer.parseInt(cursor.getString(7)) );

        cursor.close();
        return task;
    }

    public Category getCat(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_Category, new String[]{KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category cat = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        cursor.close();

        return cat;
    }

    // Getting  Tasks
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setParentId(Integer.parseInt(cursor.getString(1)));
                task.setName(cursor.getString(2));
                task.setColor(cursor.getString(3));
                task.setTime(Long.parseLong(cursor.getString(4)));
                task.setCategoryId(Integer.parseInt(cursor.getString(5)));
                task.setPriority(Integer.parseInt(cursor.getString(6)));
                task.setCompleted(Integer.parseInt(cursor.getString(7)));


                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
// return contact list
        return taskList;
    }

//return subtasks
public List<Task> getSubTasks(int id) {
    List<Task> taskList = new ArrayList<Task>();

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID, KEY_PARENT_ID,
                    KEY_NAME, KEY_COLOR, KEY_TIME, KEY_CATEGORYID, KEY_PRIORITY, KEY_COMPLETED}, KEY_PARENT_ID + "=?",
            new String[]{String.valueOf(id)}, null, null, null, null);
    if (cursor != null) {
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setParentId(Integer.parseInt(cursor.getString(1)));
                task.setName(cursor.getString(2));
                task.setColor(cursor.getString(3));
                task.setTime(Long.parseLong(cursor.getString(4)));
                task.setCategoryId(Integer.parseInt(cursor.getString(5)));
                task.setPriority(Integer.parseInt(cursor.getString(6)));
                task.setCompleted(Integer.parseInt(cursor.getString(7)));


                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
// return contact list
    return taskList;
}

    public List<Category> getAllCats() {
        List<Category> catList = new ArrayList<Category>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Category;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setId(Integer.parseInt(cursor.getString(0)));
                cat.setName(cursor.getString(1));
                catList.add(cat);
            } while (cursor.moveToNext());
        }
        cursor.close();
// return contact list
        return catList;
    }
    // Getting tasks Count
    public int getTasksCount() {
        String countQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
// return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PARENT_ID, task.getParentId());
        values.put(KEY_NAME, task.getName());
        values.put(KEY_COLOR, task.getColor());
        values.put(KEY_TIME, task.getTime());
        values.put(KEY_CATEGORYID, task.getCategoryId());
        values.put(KEY_PRIORITY, task.getPriority());
        values.put(KEY_COMPLETED, task.getCompleted());

// updating row
        int returnVal = db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});

        return returnVal;

    }

    // Deleting a shop
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = Integer.toString(task.getId());
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }
}