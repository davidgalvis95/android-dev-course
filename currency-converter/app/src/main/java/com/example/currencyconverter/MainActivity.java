package com.example.currencyconverter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final Double USD_VALUE_IN_COP = 3950.00;

    public void fadeMoney(View view){
        final ImageView moneyImageView = (ImageView) findViewById(R.id.moneyImageView);
        final ImageView moneyBagImageView = (ImageView) findViewById(R.id.moneyBagImageView);

        if(moneyImageView.getAlpha() == 1){
            moneyImageView.animate().alpha(0).setDuration(2000);
            moneyBagImageView.animate().alpha(1).setDuration(2000);
        }else{
            moneyImageView.animate().alpha(1).setDuration(2000);
            moneyBagImageView.animate().alpha(0).setDuration(2000);
        }
    }

    public void exchange(View view){
        final CheckBox copOption = findViewById(R.id.copOption);
        final CheckBox usdOption = findViewById(R.id.usdOption);
        String inputAmountText = ((EditText) findViewById(R.id.imputMoneyAmountText)).getText().toString();
        int inputAmount;

        try {
            inputAmount = Integer.parseInt(inputAmountText);
            if(copOption.isChecked()){
                Toast.makeText(this, "COP " + Math.round(inputAmount) + " = " + "USD " + Math.round(inputAmount / USD_VALUE_IN_COP), Toast.LENGTH_SHORT).show();
            }else if(usdOption.isChecked()){
                Toast.makeText(this, "USD " + Math.round(inputAmount) + " = " + "COP " + Math.round(inputAmount * USD_VALUE_IN_COP), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Please select from which currency you want to convert", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception exception){
//            throw new TypeCastException("Inconvertible types, cannot convert value "+inputAmountText +" into a number");
            Toast.makeText(this, "Please enter a correct number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView rabbitView = (ImageView) findViewById(R.id.rabbitView);
        rabbitView.animate().translationXBy(1200).alpha(0).setDuration(3000);
    }
}