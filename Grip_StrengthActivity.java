package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class Grip_StrengthActivity extends AppCompatActivity {
    private NumberPicker LeftNumberPicker1, LeftNumberPicker2, LeftNumberPicker3;
    private NumberPicker RightNumberPicker1, RightNumberPicker2, RightNumberPicker3;

    private ImageButton BackButton;
    private Button FinishButton;

    int LeftValue100, LeftValue10, LeftValue1;
    int RightValue100, RightValue10, RightValue1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grip_strength);

        LeftNumberPicker1 = findViewById(R.id.LeftNumberPicker1);   // 왼 100
        LeftNumberPicker2 = findViewById(R.id.LeftNumberPicker2);   // 왼 10
        LeftNumberPicker3 = findViewById(R.id.LeftNumberPicker3);   // 왼 1
        RightNumberPicker1 = findViewById(R.id.RightNumberPicker1); // 오 100
        RightNumberPicker2 = findViewById(R.id.RightNumberPicker2); // 오 10
        RightNumberPicker3 = findViewById(R.id.RightNumberPicker3); // 오 1

        BackButton = findViewById(R.id.BackButton);
        FinishButton = findViewById(R.id.FinishButton);

        LeftNumberPicker1.setMaxValue(9); //최대값
        LeftNumberPicker1.setMinValue(0); //최소값
        LeftNumberPicker1.setValue(0);    //초기값
        LeftNumberPicker1.setWrapSelectorWheel(true); // 스크롤이 최대값, 최소값을 넘어감

        LeftNumberPicker2.setMaxValue(9);
        LeftNumberPicker2.setMinValue(0);
        LeftNumberPicker2.setValue(0);
        LeftNumberPicker2.setWrapSelectorWheel(true);

        LeftNumberPicker3.setMaxValue(9);
        LeftNumberPicker3.setMinValue(0);
        LeftNumberPicker3.setValue(0);
        LeftNumberPicker3.setWrapSelectorWheel(true);

        RightNumberPicker1.setMaxValue(9);
        RightNumberPicker1.setMinValue(0);
        RightNumberPicker1.setValue(0);
        RightNumberPicker1.setWrapSelectorWheel(true);

        RightNumberPicker2.setMaxValue(9);
        RightNumberPicker2.setMinValue(0);
        RightNumberPicker2.setValue(0);
        RightNumberPicker2.setWrapSelectorWheel(true);

        RightNumberPicker3.setMaxValue(9);
        RightNumberPicker3.setMinValue(0);
        RightNumberPicker3.setValue(0);
        RightNumberPicker3.setWrapSelectorWheel(true);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Grip_StrengthActivity.this, Evaluation_ItemsActivity.class);
                startActivity(intent);
            }
        });

        FinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeftValue100 = LeftNumberPicker1.getValue();
                LeftValue100 *=100;
                LeftValue10 = LeftNumberPicker2.getValue();
                LeftValue10 *= 10;
                LeftValue1 = LeftNumberPicker3.getValue();
                int LeftGripStrength = LeftValue100+LeftValue10+LeftValue1;

                RightValue100 = RightNumberPicker1.getValue();
                RightValue100 *= 100;
                RightValue10 = RightNumberPicker2.getValue();
                RightValue10 *= 10;
                RightValue1 = RightNumberPicker3.getValue();
                int RightGripStrength = RightValue100 + RightValue10 + RightValue1;

                Intent resultIntent = new Intent();
                resultIntent.putExtra("LeftGripStrength", LeftGripStrength);
                resultIntent.putExtra("RightGripStrength", RightGripStrength);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }
}
