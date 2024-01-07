// TransactionAdapter.java
package com.example.incommaneger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private List<TransactionModel> expenseTransactions;

    public ExpenseAdapter(List<TransactionModel> expenseTransactions) {
        this.expenseTransactions = expenseTransactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionModel transaction = expenseTransactions.get(position);

        String formattedAmount = NumberFormat.getCurrencyInstance(Locale.US)
                .format(transaction.getAmount());

        holder.amountTextView.setText(formattedAmount);
        holder.titleTextView.setText(transaction.getTitle());
        holder.categoryTextView.setText(transaction.getCategory());
        holder.dateTextView.setText(transaction.getDate());
    }

    @Override
    public int getItemCount() {
        return expenseTransactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView amountTextView, titleTextView, categoryTextView, dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amountTextView = itemView.findViewById(R.id.Amount);
            titleTextView = itemView.findViewById(R.id.Title);
            categoryTextView = itemView.findViewById(R.id.Category);
            dateTextView = itemView.findViewById(R.id.Date);
        }
    }
}

