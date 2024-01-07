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

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {
    private List<TransactionModel> incomeTransactions;
    public IncomeAdapter(List<TransactionModel> incomeTransactions) {
        this.incomeTransactions = incomeTransactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_income, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionModel transaction = incomeTransactions.get(position);

        String formattedAmount = NumberFormat.getCurrencyInstance(Locale.US)
                .format(transaction.getAmount());

        holder.amountTextView.setText(formattedAmount);
        holder.titleTextView.setText(transaction.getTitle());
        holder.categoryTextView.setText(transaction.getCategory());
        holder.dateTextView.setText(transaction.getDate());
    }

    @Override
    public int getItemCount(

    ) {
        return incomeTransactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView amountTextView, titleTextView, categoryTextView, dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amountTextView = itemView.findViewById(R.id.tvAmount);
            titleTextView = itemView.findViewById(R.id.tvTitle);
            categoryTextView = itemView.findViewById(R.id.tvCategory);
            dateTextView = itemView.findViewById(R.id.tvDate);
        }
    }
}
