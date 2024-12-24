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

public class WalkingActivity extends AppCompatActivity {
    private ImageButton BackButton;
    private Button StartTimerButton, FinishButton;
    private TextView TimerText;

    private long startTime;
    private boolean isTimerRunning = false;
    private double walkingTime = 0;
    private int WalkingScore = 0;

    private Handler handler = new Handler();
    private Runnable timerRunnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);

        BackButton = findViewById(R.id.BackButton);
        StartTimerButton = findViewById(R.id.StartTimerButton);
        FinishButton = findViewById(R.id.FinishButton);
        TimerText = findViewById(R.id.TimerText);

        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(WalkingActivity.this, Evaluation_ItemsActivity.class);
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
            if (walkingTime == 0) {
                Toast.makeText(WalkingActivity.this, "타이머를 먼저 시작하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            calculateWalkingScore(walkingTime);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("WalkingScore", WalkingScore);
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
        walkingTime = elapsedTime / 1000.0;
        TimerText.setText(String.format("%.2f", walkingTime));
        StartTimerButton.setText("타이머");
        isTimerRunning = false;
        handler.removeCallbacks(timerRunnable);
    }

    private void calculateWalkingScore(double time) {
        if (time < 4.82) {
            WalkingScore = 4;
        } else if (time <= 6.20) {
            WalkingScore = 3;
        } else if (time <= 8.70) {
            WalkingScore = 2;
        } else {
            WalkingScore = 1;
        }
    }
}
