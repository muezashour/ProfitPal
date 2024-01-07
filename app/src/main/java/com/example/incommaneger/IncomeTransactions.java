package com.example.incommaneger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

public class IncomeTransactions extends AppCompatActivity {
    private RecyclerView incomeList;
    private ImageView btnBack,DeleteIncome;
    private List<TransactionModel> incomeTransactions;
    private DBOperations dbOperations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_transactions);

        btnBack = findViewById(R.id.btnBackk);
        incomeList = findViewById(R.id.IncomeList);
        DeleteIncome=findViewById(R.id.DeleteIncome);
        dbOperations = new DBOperations(this);

        incomeTransactions = dbOperations.getIncomeTransactions();

        IncomeAdapter incomeAdapter = new IncomeAdapter(incomeTransactions);
        incomeList.setLayoutManager(new LinearLayoutManager(this));
        incomeList.setAdapter(incomeAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomeTransactions.this, MainActivity.class);
                startActivity(intent);
            }
        });
        DeleteIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExpenseDeletionConfirmationDialog();
            }
        });
    }
    private void showExpenseDeletionConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

        // Inflate the custom layout
        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_income, null);
        builder.setView(customLayout);

        // Get views from the custom layout
        TextView customTitle = customLayout.findViewById(R.id.custom_title);
        TextView customMessage = customLayout.findViewById(R.id.custom_message);
        Button customButtonYes = customLayout.findViewById(R.id.custom_button_yes);
        Button customButtonNo = customLayout.findViewById(R.id.custom_button_no);

        // Customize title and message
        customTitle.setText("Confirm Deletion");
        customMessage.setText("Are you sure you want to delete all the Income?");

        // Set button click listeners
        customButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIncome();
                showStyledToast("Income deleted");
                Intent intent = new Intent(IncomeTransactions.this, MainActivity.class);
                startActivity(intent);
            }
        });

        customButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                AlertDialog dialog = (AlertDialog) v.getTag();
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog dialog = builder.create();
        customButtonNo.setTag(dialog); // Set the dialog as a tag for later access
        dialog.show();
    }

    private void deleteIncome() {
        dbOperations.deleteIncome();
    }
    private void showStyledToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_layout));

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}