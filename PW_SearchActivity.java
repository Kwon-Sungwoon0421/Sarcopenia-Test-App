package com.example.sppbtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PW_SearchActivity extends AppCompatActivity {
    private EditText ETid, ETphone, ETnew_pw, ETcheck_pw;
    private Button reset, back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_search);

        ETid = findViewById(R.id.id);
        ETphone = findViewById(R.id.phone);
        ETnew_pw = findViewById(R.id.new_pw);
        ETcheck_pw = findViewById(R.id.check_pw);

        reset = findViewById(R.id.reset);
        back = findViewById(R.id.back);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = ETid.getText().toString().trim();
                String phone = ETphone.getText().toString().trim();
                String new_pw = ETnew_pw.getText().toString().trim();
                String check_pw = ETcheck_pw.getText().toString().trim();

                if (id.isEmpty() || phone.isEmpty() || new_pw.isEmpty() || check_pw.isEmpty()) {
                    showAlert("모든 필드를 입력해주세요.");
                    return;
                }

                if (!new_pw.equals(check_pw)) {
                    showAlert("비밀번호가 일치하지 않습니다.");
                    return;
                }

                updatePW(id, phone, new_pw);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PW_SearchActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updatePW(String id, String phone, String new_pw){
        String url = "http://www.all-tafp.org/SPPB_update_pw.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            Intent intent = new Intent(PW_SearchActivity.this, PW_Search_ResultActivity.class);
                            if (success) {
                                intent.putExtra("result_message", "비밀번호가 성공적으로 변경되었습니다.");
                            } else {
                                String message = jsonResponse.getString("message");
                                intent.putExtra("result_message", message);
                            }
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showAlert("응답 처리 중 오류가 발생했습니다.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        showAlert("서버 요청 중 오류가 발생했습니다.");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("phone", phone);
                params.put("new_pw", new_pw);
                return params;
            }
        };

        queue.add(request);
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("확인", (dialog, which) -> dialog.dismiss())
                .show();
    }
}