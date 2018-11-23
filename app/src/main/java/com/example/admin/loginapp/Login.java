package com.example.admin.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText loginid,password;
    Button login,register;
    private ProgressDialog show;
    private FirebaseAuth firebaseAuth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        show = new ProgressDialog(this);
        login = (Button)findViewById(R.id.button2);
        register = (Button)findViewById(R.id.button5);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login Page");

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),Profile.class));
        }
        loginid = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            login();
            }
        });

    }
        private void login()
        {
            final String login = loginid.getText().toString();
            String pass = password.getText().toString();
            if (TextUtils.isEmpty(login))
            {
                Toast.makeText(this,"Enter email",Toast.LENGTH_LONG).show();

                return;
            }
            if (TextUtils.isEmpty(pass))
            {
                Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();

                return;
            }
            show.setMessage("Loging in..");
            show.show();

            firebaseAuth.signInWithEmailAndPassword(login,pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            show.dismiss();

                            if (task.isSuccessful())
                            {
                                Intent loginintent = new Intent(getApplicationContext(),Profile.class);
                                loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(loginintent);
                                finish();
                            }
                        }
                    });
        }

}
