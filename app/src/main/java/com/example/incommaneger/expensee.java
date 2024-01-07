package com.example.incommaneger;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class expensee extends AppCompatActivity {
    EditText ExpenseTitle,ExpenseAmount,ExpenseCategory;
    Button AddExpenseBtn;
    ImageView BackToMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expensee);
        AddExpenseBtn =findViewById(R.id.AddExpenseBtn);
        ExpenseTitle=findViewById(R.id.ExpenseTitle);
        ExpenseAmount=findViewById(R.id.ExpenseAmount);
        ExpenseCategory=findViewById(R.id.ExpenseCategory);
        BackToMain =findViewById(R.id.BackToMain);

        BackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(expensee.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }

    public void AddExpense(View view ){
        String expenseAmountString =ExpenseAmount.getText().toString();
        String expenseTitle=ExpenseTitle.getText().toString();
        String expenseCategory=ExpenseCategory.getText().toString();

        if(!expenseAmountString.isEmpty()){
            double expenseAmount =Double.parseDouble(expenseAmountString);

            String currentDateForExpense = getCurrentDate();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("EXPENSE_AMOUNT", expenseAmount);
            resultIntent.putExtra("EXPENSE_TITLE", expenseTitle);
            resultIntent.putExtra("EXPENSE_CATEGORY", expenseCategory);
            resultIntent.putExtra("CURRENT_DATE_FOR_EXPENSE", currentDateForExpense);

            setResult(RESULT_OK, resultIntent);
            finish();
        }else {

            ExpenseAmount.setError("Please enter the income amount");
        }
    }
    private String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(currentDate);
    }


}