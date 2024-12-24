package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Question2Activity extends AppCompatActivity {

    private RadioGroup radioGroup;

    private Button nextButton;

    int total_score = 0;
    int score1 = 0;
    int score2 = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        total_score = getIntent().getIntExtra("total_score", 0);
        score1 = getIntent().getIntExtra("score1", 0);

        radioGroup = findViewById(R.id.radioGroup);
        nextButton = findViewById(R.id.nextbutton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedID = radioGroup.getCheckedRadioButtonId();

                if (selectedID == R.id.radioButton1) {
                    score2 = 0;
                } else if (selectedID == R.id.radioButton2) {
                    score2 = 1;
                } else if (selectedID == R.id.radioButton3) {
                    score2 = 2;
                } else {
                    Toast.makeText(Question2Activity.this, "항목을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Question2Activity.this, Question3Activity.class);
                total_score+=score2;
                intent.putExtra("total_score", total_score);
                intent.putExtra("score1", score1);
                intent.putExtra("score2", score2);
                startActivity(intent);
            }
        });

    }
}
