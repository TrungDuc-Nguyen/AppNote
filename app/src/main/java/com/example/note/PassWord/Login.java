package com.example.note.PassWord;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.Home.Home;
import com.example.note.R;
import com.example.note.database.RealmContext;

import java.util.List;

public class Login extends AppCompatActivity {
    EditText et_user;
    EditText et_pass;
    CheckBox cb_remember;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        init();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_user.getText().toString().isEmpty() || et_pass.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Bạn chưa nhập tên hoặc mật khẩu.", Toast.LENGTH_LONG).show();
                } else {
                    String user = et_user.getText().toString();
                    String pass = et_pass.getText().toString();
                    List<UserAndPass> userAndPass = RealmContext.getInstance().getAllUserAndPass();
                    if (user.equals(userAndPass.get(0).getUser())&& pass.equals(userAndPass.get(0).getPass())) {
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Tên tài khoản hoặc mật khẩu không đúng." , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void init() {
        et_user = findViewById(R.id.tiet_user);
        et_pass = findViewById(R.id.tiet_pass);
        cb_remember = findViewById(R.id.cb_rememberpass);
        btn_login = findViewById(R.id.btn_pass_login);
    }
}
