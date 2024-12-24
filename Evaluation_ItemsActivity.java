package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Evaluation_ItemsActivity extends AppCompatActivity {
    private ImageButton ItemsImage1,ItemsImage2, ItemsImage3, ItemsImage4, BackButton;
    private Button FinishButton;

    private ActivityResultLauncher<Intent> gripStrengthLauncher;
    private ActivityResultLauncher<Intent> walkingLauncher;
    private ActivityResultLauncher<Intent> chairStandUpLauncher;
    private ActivityResultLauncher<Intent> balanceLauncher;

    int LeftGripStrength = -1, RightGripStrength = -1, WalkingScore = -1, BalanceScore = -1, Stand_UpScore = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_items);

        ItemsImage1 = findViewById(R.id.imageButton1);
        ItemsImage2 = findViewById(R.id.imageButton2);
        ItemsImage3 = findViewById(R.id.imageButton3);
        ItemsImage4 = findViewById(R.id.imageButton4);
        BackButton = findViewById(R.id.BackButton);
        FinishButton = findViewById(R.id.FinishButton);

        initializeLaunchers();

        ItemsImage1.setOnClickListener(view -> {
            Intent intent = new Intent(Evaluation_ItemsActivity.this, Grip_StrengthActivity.class);
            gripStrengthLauncher.launch(intent);
        });

        ItemsImage2.setOnClickListener(view -> {
            Intent intent = new Intent(Evaluation_ItemsActivity.this, WalkingActivity.class);
            walkingLauncher.launch(intent);
        });

        ItemsImage3.setOnClickListener(view -> {
            Intent intent = new Intent(Evaluation_ItemsActivity.this, Chair_Stand_UPActivity.class);
            chairStandUpLauncher.launch(intent);
        });

        ItemsImage4.setOnClickListener(view -> {
            Intent intent = new Intent(Evaluation_ItemsActivity.this, BalanceActivity.class);
            balanceLauncher.launch(intent);
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Evaluation_ItemsActivity.this, Test_ListActivity.class);
                startActivity(intent);
            }
        });

        FinishButton.setOnClickListener(view -> validateAndProceed());
    }

    private void initializeLaunchers() {
        gripStrengthLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        LeftGripStrength = result.getData().getIntExtra("LeftGripStrength", -1);
                        RightGripStrength = result.getData().getIntExtra("RightGripStrength", -1);
                        Log.d("Evaluation_ItemsActivity", "LeftGripStrength: " + LeftGripStrength);
                        Log.d("Evaluation_ItemsActivity", "RightGripStrength: " + RightGripStrength);
                    }
                }
        );

        walkingLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        WalkingScore = result.getData().getIntExtra("WalkingScore", -1);
                        Log.d("Evaluation_ItemsActivity", "WalkingScore: " + WalkingScore);
                    }
                }
        );

        chairStandUpLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Stand_UpScore = result.getData().getIntExtra("Stand_UpScore", -1);
                        Log.d("Evaluation_ItemsActivity", "Stand_UPScore: " + Stand_UpScore);
                    }
                }
        );

        balanceLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        BalanceScore = result.getData().getIntExtra("BalanceScore", -1);
                        Log.d("Evaluation_ItemsActivity", "BalanceScore: " + BalanceScore);
                    }
                }
        );
    }
    private void validateAndProceed() {
        if (LeftGripStrength == -1) {
            Toast.makeText(this, "왼손 악력 테스트를 완료하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (RightGripStrength == -1) {
            Toast.makeText(this, "오른손 악력 테스트를 완료하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (WalkingScore == -1) {
            Toast.makeText(this, "걷기 테스트를 완료하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (BalanceScore == -1) {
            Toast.makeText(this, "균형 테스트를 완료하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Stand_UpScore == -1) {
            Toast.makeText(this, "의자 일어서기 테스트를 완료하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Evaluation_ItemsActivity.this, Test_ResultActivity.class);
        intent.putExtra("LeftGripStrength", LeftGripStrength);
        intent.putExtra("RightGripStrength", RightGripStrength);
        intent.putExtra("WalkingScore", WalkingScore);
        intent.putExtra("BalanceScore", BalanceScore);
        intent.putExtra("Stand_UpScore", Stand_UpScore);
        startActivity(intent);
    }
}