package com.example.sppbtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XYZActivity extends AppCompatActivity {
    private TextView G_X, G_Y, G_Z, A_X, A_Y, A_Z;
    private Button FinishButton;
    private Handler handler = new Handler(Looper.getMainLooper());
    private final int INTERVAL = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xyz);

        G_X = findViewById(R.id.G_X);
        G_Y = findViewById(R.id.G_Y);
        G_Z = findViewById(R.id.G_Z);
        A_X = findViewById(R.id.A_X);
        A_Y = findViewById(R.id.A_Y);
        A_Z = findViewById(R.id.A_Z);

        FinishButton = findViewById(R.id.FinishButton);
        FinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(updateTask);
                Intent intent = new Intent(XYZActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        handler.post(updateTask);
    }

    private final Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            fetchLatestData();
            handler.postDelayed(this, INTERVAL);
        }
    };

    private void fetchLatestData() {
        new Thread(() -> {
            try {
                URL url = new URL("http://www.all-tafp.org/SPPB_get_data.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                runOnUiThread(() -> updateUI(response.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(XYZActivity.this, "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void updateUI(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");

            G_X.setText("X: " + data.getDouble("G_X"));
            G_Y.setText("Y: " + data.getDouble("G_Y"));
            G_Z.setText("Z: " + data.getDouble("G_Z"));
            A_X.setText("X: " + data.getDouble("A_X"));
            A_Y.setText("Y: " + data.getDouble("A_Y"));
            A_Z.setText("Z: " + data.getDouble("A_Z"));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSON 데이터 처리 중 오류 발생", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTask);
    }
}
