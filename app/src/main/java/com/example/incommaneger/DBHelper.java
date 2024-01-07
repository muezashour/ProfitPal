// DBHelper.java
package com.example.incommaneger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "income_manager.db";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_BALANCE = "balance";
    public static final String TABLE_INCOME = "income";
    public static final String TABLE_EXPENSE = "expense";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";


    public static final String COLUMN_NEW_COLUMN = "new_column";


    private static final String CREATE_TABLE_BALANCE =
            "CREATE TABLE " + TABLE_BALANCE + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_AMOUNT + " REAL);";

    private static final String CREATE_TABLE_INCOME =
            "CREATE TABLE " + TABLE_INCOME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_AMOUNT + " REAL," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_DATE + " TEXT);";

    private static final String CREATE_TABLE_EXPENSE =
            "CREATE TABLE " + TABLE_EXPENSE + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_AMOUNT + " REAL," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_DATE + " TEXT);";

    private static final String ALTER_TABLE_BALANCE_ADD_COLUMN =
            "ALTER TABLE " + TABLE_BALANCE + " ADD COLUMN " + COLUMN_NEW_COLUMN + " INTEGER;";

    private static final String ALTER_TABLE_INCOME_ADD_COLUMN =
            "ALTER TABLE " + TABLE_INCOME + " ADD COLUMN " + COLUMN_NEW_COLUMN + " INTEGER;";

    private static final String ALTER_TABLE_EXPENSE_ADD_COLUMN =
            "ALTER TABLE " + TABLE_EXPENSE + " ADD COLUMN " + COLUMN_NEW_COLUMN + " INTEGER;";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_BALANCE);
        db.execSQL(CREATE_TABLE_INCOME);
        db.execSQL(CREATE_TABLE_EXPENSE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int version = oldVersion + 1; version <= newVersion; version++) {
            switch (version) {
                case 2:
                    db.execSQL(ALTER_TABLE_BALANCE_ADD_COLUMN);
                    db.execSQL(ALTER_TABLE_INCOME_ADD_COLUMN);
                    db.execSQL(ALTER_TABLE_EXPENSE_ADD_COLUMN);
                    break;

            }
        }
    }



}
