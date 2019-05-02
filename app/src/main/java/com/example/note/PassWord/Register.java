package com.example.note.PassWord;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.R;
import com.example.note.database.RealmContext;

import io.realm.RealmObject;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText et_user;
    EditText et_pass;
    Button btn_sign;
    Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng ký mật khẩu");

        init();
    }


    private void init() {
        et_pass = findViewById(R.id.tiet_pass_sign);
        et_user = findViewById(R.id.tiet_user_sign);
        btn_sign = findViewById(R.id.btn_pass_sign);
        btn_cancel = findViewById(R.id.btn_pass_cancel);
        btn_sign.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pass_sign: {
                addUserAndPass();
            }
            break;
            case R.id.btn_pass_cancel: {
                onBackPressed();
            }
            break;
        }
    }

    private void addUserAndPass() {
        if (et_user.getText().toString().isEmpty() || et_pass.getText().toString().isEmpty()) {
            Toast.makeText(Register.this, "Bạn chưa nhập tên hoặc mật khẩu.", Toast.LENGTH_LONG).show();
        } else {
            String user = et_user.getText().toString();
            String pass = et_pass.getText().toString();
            UserAndPass newUserAndPass = new UserAndPass(user, pass);
            RealmContext.getInstance().addUserAndPass(newUserAndPass);
            onBackPressed();
        }
    }
}
