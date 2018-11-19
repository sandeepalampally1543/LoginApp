package com.example.admin.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText username,password,displayname;
    private Button login;
    private Button loginpage;
    private ProgressDialog show;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),Profile.class));
        }
        show = new ProgressDialog(this);
        loginpage = (Button)findViewById(R.id.textView);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        displayname = (EditText)findViewById(R.id.displayname);

        login = (Button) findViewById(R.id.button2);

        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebase();
            }
        });

    }

    private void firebase()
    {
        final String name = displayname.getText().toString().trim();
        final String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
            return;
        }
        show.setMessage("Registering...");
        show.show();
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        show.dismiss();
                        if (task.isSuccessful())
                        {
                            FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
                            String user_id = firebaseAuth.getUid();

                            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                            HashMap<String,String> userMap= new HashMap<>();
                            userMap.put("name",name);
                            userMap.put("email",email);

                            firebaseDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(),Profile.class));
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"couldnot Registred sucesful",Toast.LENGTH_LONG).show();

                        }
                    }

                });
    }


}
