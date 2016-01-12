package com.thoughtworks.leansw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thoughtworks.leansw.user.Account;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        // TODO do real login
        ((LeanSWApplication)getApplication()).createUserComponent(new Account());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
