package com.example.incommaneger;

public class TransactionModel {
    private double amount;
    private String title;
    private String category;
    private String date;

    public TransactionModel(double amount, String title, String category, String date) {
        this.amount = amount;
        this.title = title;
        this.category = category;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }
}

