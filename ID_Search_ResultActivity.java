package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ID_Search_ResultActivity extends AppCompatActivity {
    private TextView id;
    private Button back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search_result);

        id = findViewById(R.id.id);
        back = findViewById(R.id.back);

        String foundID = getIntent().getStringExtra("found_id");

        if (foundID != null && !foundID.isEmpty()) {
            id.setText("찾은 아이디: " + foundID);
        } else {
            id.setText("아이디를 찾을 수 없습니다.");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ID_Search_ResultActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
