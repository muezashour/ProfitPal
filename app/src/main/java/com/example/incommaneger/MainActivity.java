package com.example.incommaneger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageView imageView,delete;
    Button incomeBtn,expenseBtn;
    TextView Balance,IncomeBalance,ExpenseBalance;
    DBOperations dbOperations;
    CardView IncomeCard,ExpenseCard;
    private AlertDialog confirmationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView6);
        incomeBtn =findViewById(R.id.incomeBtn);
        expenseBtn = findViewById(R.id.expenseBtn);
        Balance=findViewById(R.id.Balance);
        IncomeBalance=findViewById(R.id.IncomeBalance);
        ExpenseBalance=findViewById(R.id.ExpenseBalance);
        dbOperations = new DBOperations(this);
        IncomeCard =findViewById(R.id.IncomeCard);
        ExpenseCard =findViewById(R.id.ExpenseCard);
        delete =findViewById(R.id.delete);

        updateUI();

        {
            ObjectAnimator animUp = ObjectAnimator.ofFloat(imageView, "translationY", 0f, -100f);
            ObjectAnimator animDown = ObjectAnimator.ofFloat(imageView, "translationY", -100f, 0f);

            animUp.setDuration(1000);
            animDown.setDuration(1000);

            animUp.setInterpolator(new LinearInterpolator());
            animDown.setInterpolator(new LinearInterpolator());

            animUp.setRepeatMode(ObjectAnimator.REVERSE);
            animUp.setRepeatCount(ObjectAnimator.INFINITE);

            animDown.setRepeatMode(ObjectAnimator.REVERSE);
            animDown.setRepeatCount(ObjectAnimator.INFINITE);

            animUp.start();
            animDown.start();
        }

        incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, incomee.class), 1);
            }
        });
        expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(MainActivity.this, expensee.class), 1);
            }
        });

        IncomeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, IncomeTransactions.class);

                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showConfirmationDialog();
            }
        });
        ExpenseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, ExpenseTransactions.class);

                startActivity(intent);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            double incomeAmount = data.getDoubleExtra("INCOME_AMOUNT", 0.0);
            double expenseAmount = data.getDoubleExtra("EXPENSE_AMOUNT", 0.0);

            if (incomeAmount > 0) {
                String incomeTitle = data.getStringExtra("INCOME_TITLE");
                String incomeCategory = data.getStringExtra("INCOME_CATEGORY");
                String currentDateForIncome = data.getStringExtra("CURRENT_DATE_FOR_INCOME");

                dbOperations.addIncome(incomeAmount, incomeTitle, incomeCategory, currentDateForIncome);
            }

            if (expenseAmount > 0) {
                String expenseTitle = data.getStringExtra("EXPENSE_TITLE");
                String expenseCategory = data.getStringExtra("EXPENSE_CATEGORY");
                String currentDateForExpense = data.getStringExtra("CURRENT_DATE_FOR_EXPENSE");

                dbOperations.addExpense(expenseAmount, expenseTitle, expenseCategory, currentDateForExpense);
            }
            updateUI();
        }
    }

    private void updateUI() {

        double currentBalance = dbOperations.getBalance();
        double currentIncomeBalance = dbOperations.getIncomeBalance();
        double currentExpenseBalance = dbOperations.getExpenseBalance();

        currentBalance+=currentIncomeBalance;
        currentBalance-=currentExpenseBalance;

        animateBalances(currentBalance, currentIncomeBalance, currentExpenseBalance);

        String formattedBalance = NumberFormat.getCurrencyInstance(Locale.US).format(currentBalance);
        String formattedIncomeBalance = NumberFormat.getCurrencyInstance(Locale.US).format(currentIncomeBalance);
        String formattedExpenseBalance = NumberFormat.getCurrencyInstance(Locale.US).format(currentExpenseBalance);

        Balance.setText(formattedBalance);
        IncomeBalance.setText(formattedIncomeBalance);
        ExpenseBalance.setText(formattedExpenseBalance);

    }
    private void animateBalances(double targetBalance, double targetIncomeBalance, double targetExpenseBalance) {
        animateBalance(targetBalance, Balance);
        animateBalance(targetIncomeBalance, IncomeBalance);
        animateBalance(targetExpenseBalance, ExpenseBalance);
    }

    private void animateBalance(double targetValue, TextView textView) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, (float) targetValue);
        animator.setDuration(1500);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                textView.setText(NumberFormat.getCurrencyInstance(Locale.US).format(animatedValue));
            }
        });

        animator.start();
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_balance, null);
        builder.setView(customLayout);

        TextView customTitle = customLayout.findViewById(R.id.custom_title);
        TextView customMessage = customLayout.findViewById(R.id.custom_message);
        Button customButtonYes = customLayout.findViewById(R.id.custom_button_yes);
        Button customButtonNo = customLayout.findViewById(R.id.custom_button_no);

        customTitle.setText("Confirm Deletion");
        customMessage.setText("Are you sure you want to delete Everything?");

        customButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEverything();
                showStyledToast("Balance deleted");

                if (confirmationDialog != null && confirmationDialog.isShowing()) {
                    confirmationDialog.dismiss();
                }
            }
        });

        customButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (confirmationDialog != null && confirmationDialog.isShowing()) {
                    confirmationDialog.dismiss();
                }
            }
        });
        confirmationDialog = builder.create();
        confirmationDialog.show();
    }
    private void deleteEverything() {

        dbOperations.deleteAllData();
        updateUI();
        showStyledToast("Deletion complete");
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