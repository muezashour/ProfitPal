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

public class incomee extends AppCompatActivity {
    EditText IncomeTitle, IncomeAmount, IncomeCategory;
    Button AddIncomeBtn;
    ImageView BackToMainn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomee);
        IncomeTitle = findViewById(R.id.IncomeTitle);
        IncomeAmount = findViewById(R.id.IncomeAmount);
        IncomeCategory = findViewById(R.id.IncomeCategory);
        AddIncomeBtn = findViewById(R.id.AddIncomeBtn);
        BackToMainn =findViewById(R.id.BackToMainn);

        BackToMainn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(incomee.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddIncome(View v) {
        String incomeTitle=IncomeTitle.getText().toString();
        String incomeCategory=IncomeCategory.getText().toString();
        String incomeAmountString = IncomeAmount.getText().toString();

        if (!incomeAmountString.isEmpty()) {

            double incomeAmount = Double.parseDouble(incomeAmountString);
            String currentDateForIncome = getCurrentDate();


            Intent resultIntent = new Intent();
            resultIntent.putExtra("INCOME_AMOUNT", incomeAmount);
            resultIntent.putExtra("INCOME_TITLE", incomeTitle);
            resultIntent.putExtra("INCOME_CATEGORY", incomeCategory);
            resultIntent.putExtra("CURRENT_DATE_FOR_INCOME", currentDateForIncome);


            setResult(RESULT_OK, resultIntent);


            finish();
        } else {

            IncomeAmount.setError("Please enter the income amount");
        }
    }
    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(currentDate);
    }
}
