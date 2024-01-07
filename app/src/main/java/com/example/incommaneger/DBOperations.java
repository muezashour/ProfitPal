// DBOperations.java
package com.example.incommaneger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBOperations {
    private DBHelper dbHelper;

    public DBOperations(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addBalance(double amount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(DBHelper.COLUMN_AMOUNT, amount);

        db.insert(DBHelper.TABLE_BALANCE, null, values);
        db.close();
    }

    public void addIncome(double amount, String title, String category, String date) {
        if (amount > 0) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_AMOUNT, amount);
            values.put(DBHelper.COLUMN_TITLE, title);
            values.put(DBHelper.COLUMN_CATEGORY, category);
            values.put(DBHelper.COLUMN_DATE, date);

            db.insert(DBHelper.TABLE_INCOME, null, values);
            db.close();
        }
    }

    public void addExpense(double amount, String title, String category, String date) {
        if (amount > 0) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_AMOUNT, amount);
            values.put(DBHelper.COLUMN_TITLE, title);
            values.put(DBHelper.COLUMN_CATEGORY, category);
            values.put(DBHelper.COLUMN_DATE, date);

            db.insert(DBHelper.TABLE_EXPENSE, null, values);
            db.close();
        }
    }

    public double getBalance() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " + DBHelper.COLUMN_AMOUNT + " FROM " + DBHelper.TABLE_BALANCE;
        Cursor cursor = db.rawQuery(query, null);

        double balance = 0;

        if (cursor.moveToFirst()) {
            balance = cursor.getDouble(0);
        }

        cursor.close();
        db.close();

        return balance;
    }
    public double getIncomeBalance() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT SUM(" + DBHelper.COLUMN_AMOUNT + ") FROM " + DBHelper.TABLE_INCOME;
        Cursor cursor = db.rawQuery(query, null);

        double incomeBalance = 0;

        if (cursor.moveToFirst()) {
            incomeBalance = cursor.getDouble(0);
        }

        cursor.close();
        db.close();

        return incomeBalance;
    }
    public double getExpenseBalance() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT SUM(" + DBHelper.COLUMN_AMOUNT + ") FROM " + DBHelper.TABLE_EXPENSE;
        Cursor cursor = db.rawQuery(query, null);

        double expenseBalance = 0;

        if (cursor.moveToFirst()) {
            expenseBalance = cursor.getDouble(0);
        }

        cursor.close();
        db.close();

        return expenseBalance;
    }


    public List<TransactionModel> getIncomeTransactions() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<TransactionModel> incomeTransactions = new ArrayList<>();


        String query = "SELECT * FROM " + DBHelper.TABLE_INCOME;
        Cursor cursor = db.rawQuery(query, null);


        while (cursor.moveToNext()) {
            double amount = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_AMOUNT));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE));
            String category = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CATEGORY));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE));

            TransactionModel transaction = new TransactionModel(amount, title, category, date);
            incomeTransactions.add(transaction);
        }


        cursor.close();
        db.close();

        return incomeTransactions;
    }

    public List<TransactionModel> getExpenseTransactions() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<TransactionModel> expenseTransactions = new ArrayList<>();


        String query = "SELECT * FROM " + DBHelper.TABLE_EXPENSE;
        Cursor cursor = db.rawQuery(query, null);


        while (cursor.moveToNext()) {
            double amount = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_AMOUNT));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE));
            String category = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CATEGORY));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE));

            TransactionModel transaction = new TransactionModel(amount, title, category, date);
            expenseTransactions.add(transaction);
        }

        cursor.close();
        db.close();

        return expenseTransactions;
    }
    public void deleteAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.TABLE_INCOME, null, null);
        db.delete(DBHelper.TABLE_EXPENSE, null, null);

        db.close();
    }
    public void deleteExpense(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_EXPENSE, null, null);

        db.close();

    }
    public void deleteIncome(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_INCOME, null, null);

        db.close();

    }



}
