package com.example.incommaneger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class ExpenseTransactions extends AppCompatActivity {
    private RecyclerView ExpenseList;
    private DBOperations dbOperations;
    private List<TransactionModel> expenseTransactions;
    ImageView btnBackk,DeleteExpense;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_transactions);

        btnBackk = findViewById(R.id.btnBackk);

        ExpenseList =findViewById(R.id.ExpenseList);
        dbOperations = new DBOperations(this);

        expenseTransactions = dbOperations.getExpenseTransactions();
        DeleteExpense =findViewById(R.id.DeleteExpense);

        ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenseTransactions);
        ExpenseList.setLayoutManager(new LinearLayoutManager(this));
        ExpenseList.setAdapter(expenseAdapter);


        btnBackk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ExpenseTransactions.this, MainActivity.class);

                startActivity(intent);
            }
        });
        DeleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExpenseDeletionConfirmationDialog();

            }
        });
    }

    private void showExpenseDeletionConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_message, null);
        builder.setView(customLayout);


        TextView customTitle = customLayout.findViewById(R.id.custom_title);
        TextView customMessage = customLayout.findViewById(R.id.custom_message);
        Button customButtonYes = customLayout.findViewById(R.id.custom_button_yes);
        Button customButtonNo = customLayout.findViewById(R.id.custom_button_no);


        customTitle.setText("Confirm Deletion");
        customMessage.setText("Are you sure you want to delete all the Expenses?");


        customButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteExpense();
                showStyledToast("Expense deleted");
                Intent intent = new Intent(ExpenseTransactions.this, MainActivity.class);
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
        customButtonNo.setTag(dialog);
        dialog.show();
    }




    private void deleteExpense() {
        dbOperations.deleteExpense();


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