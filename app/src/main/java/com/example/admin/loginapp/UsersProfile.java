package com.example.admin.loginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UsersProfile extends AppCompatActivity {

    private TextView mdisplayid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);

        String user_id = getIntent().getStringExtra("user_id");

        mdisplayid = (TextView)findViewById(R.id.profile_name);
        mdisplayid.setText(user_id);
    }
}
