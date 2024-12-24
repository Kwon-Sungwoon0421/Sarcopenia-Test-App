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

public class BalanceActivity extends AppCompatActivity {
    private ImageButton BackButton;
    private Button StartTimer1Button, StartTimer2Button, StartTimer3Button, FinishButton;
    private TextView TimerText1, TimerText2, TimerText3;

    private long startTime;
    private boolean isTimerRunning = false;
    private int activeTimer = 0;
    private int BalanceScore = 0;
    private double balanceTime1 = 0, balanceTime2 = 0, balanceTime3 = 0;

    private Handler handler = new Handler();
    private Runnable timerRunnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        BackButton = findViewById(R.id.BackButton);
        StartTimer1Button = findViewById(R.id.StartTimer1Button);
        StartTimer2Button = findViewById(R.id.StartTimer2Button);
        StartTimer3Button = findViewById(R.id.StartTimer3Button);
        FinishButton = findViewById(R.id.FinishButton);
        TimerText1 = findViewById(R.id.BalanceTime1);
        TimerText2 = findViewById(R.id.BalanceTime2);
        TimerText3 = findViewById(R.id.BalanceTime3);

        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(BalanceActivity.this, Evaluation_ItemsActivity.class);
            startActivity(intent);
        });

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsedTime = SystemClock.elapsedRealtime() - startTime;
                float timeInSeconds = elapsedTime / 1000f;

                switch (activeTimer) {
                    case 1:
                        TimerText1.setText(String.format("%.2f", timeInSeconds));
                        break;
                    case 2:
                        TimerText2.setText(String.format("%.2f", timeInSeconds));
                        break;
                    case 3:
                        TimerText3.setText(String.format("%.2f", timeInSeconds));
                        break;
                }
                handler.postDelayed(this, 100);
            }
        };

        StartTimer1Button.setOnClickListener(view -> startOrStopTimer(1));
        StartTimer2Button.setOnClickListener(view -> startOrStopTimer(2));
        StartTimer3Button.setOnClickListener(view -> startOrStopTimer(3));

        FinishButton.setOnClickListener(view -> {
            calculateBalanceScore();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("BalanceScore", BalanceScore);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void startOrStopTimer(int timerNumber) {
        if (isTimerRunning) {
            stopTimer(timerNumber);
        } else {
            startTimer(timerNumber);
        }
    }

    private void startTimer(int timerNumber) {
        activeTimer = timerNumber;
        startTime = SystemClock.elapsedRealtime();
        isTimerRunning = true;

        switch (timerNumber) {
            case 1:
                StartTimer1Button.setText("정지");
                break;
            case 2:
                StartTimer2Button.setText("정지");
                break;
            case 3:
                StartTimer3Button.setText("정지");
                break;
        }
        handler.post(timerRunnable);
    }

    private void stopTimer(int timerNumber) {
        long elapsedTime = SystemClock.elapsedRealtime() - startTime;
        double timeInSeconds = elapsedTime / 1000.0;

        switch (timerNumber) {
            case 1:
                balanceTime1 = timeInSeconds;
                TimerText1.setText(String.format("%.2f", balanceTime1));
                StartTimer1Button.setText("타이머");
                break;
            case 2:
                balanceTime2 = timeInSeconds;
                TimerText2.setText(String.format("%.2f", balanceTime2));
                StartTimer2Button.setText("타이머");
                break;
            case 3:
                balanceTime3 = timeInSeconds;
                TimerText3.setText(String.format("%.2f", balanceTime3));
                StartTimer3Button.setText("타이머");
                break;
        }

        isTimerRunning = false;
        handler.removeCallbacks(timerRunnable);
    }

    private void calculateBalanceScore() {
        if (balanceTime1 >= 10.00) {
            BalanceScore += 1;
        }

        if (balanceTime2 >= 10.00) {
            BalanceScore += 1;
        }

        if (balanceTime3 >= 3.00 && balanceTime3 < 10.00) {
            BalanceScore += 1;
        } else if (balanceTime3 >= 10.00) {
            BalanceScore += 2;
        }
    }
}
