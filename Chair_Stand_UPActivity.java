package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Chair_Stand_UPActivity extends AppCompatActivity {

    private ImageButton BackButton;
    private Button StartTimerButton, FinishButton;
    private TextView TimerText;

    private long startTime;
    private boolean isTimerRunning = false;
    private double Stand_UpTime = 0;
    private int Stand_UpScore = 0;

    private Handler handler = new Handler();
    private Runnable timerRunnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chair_stand_up);

        BackButton = findViewById(R.id.BackButton);
        StartTimerButton = findViewById(R.id.StartTimerButton);
        FinishButton = findViewById(R.id.FinishButton);
        TimerText = findViewById(R.id.TimerText);

        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(Chair_Stand_UPActivity.this, Evaluation_ItemsActivity.class);
            startActivity(intent);
        });

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsedTime = SystemClock.elapsedRealtime() - startTime;
                float timeInSeconds = elapsedTime / 1000f;
                TimerText.setText(String.format("%.2f", timeInSeconds));
                handler.postDelayed(this, 100);
            }
        };

        StartTimerButton.setOnClickListener(view -> startOrStopTimer());

        FinishButton.setOnClickListener(view -> {
            if (Stand_UpTime == 0) {
                Toast.makeText(Chair_Stand_UPActivity.this, "타이머를 먼저 시작하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            calculateStandUpScore(Stand_UpTime);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("Stand_UpScore", Stand_UpScore);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void startOrStopTimer() {
        if (isTimerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    private void startTimer() {
        startTime = SystemClock.elapsedRealtime();
        isTimerRunning = true;
        StartTimerButton.setText("정지");
        handler.post(timerRunnable);
    }

    private void stopTimer() {
        long elapsedTime = SystemClock.elapsedRealtime() - startTime;
        Stand_UpTime = elapsedTime / 1000.0;
        TimerText.setText(String.format("%.2f", Stand_UpTime));
        StartTimerButton.setText("타이머");
        isTimerRunning = false;
        handler.removeCallbacks(timerRunnable);
    }

    private void calculateStandUpScore(double time) {
        if (time < 11.19) {
            Stand_UpScore = 4;
        } else if (time >= 11.19 && time < 13.70) {
            Stand_UpScore = 3;
        } else if (time >= 13.70 && time < 16.70) {
            Stand_UpScore = 2;
        } else if (time >= 16.70 && time < 60.00) {
            Stand_UpScore = 1;
        } else{
            Stand_UpScore = 0;
        }
    }
}
