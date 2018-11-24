package com.example.admin.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Setting_change extends AppCompatActivity {

    EditText update_name;
    Button update_button;
    Toolbar toolbar3;

    //Database ref
    DatabaseReference databaseReference;
    FirebaseUser firebaseAuth;

    //progress dialog
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_change);
        toolbar3 =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar3);
        getSupportActionBar().setTitle("Update Name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String name_value = getIntent().getStringExtra("name");

        update_name = (EditText)findViewById(R.id.edit_name);
        update_button = (Button)findViewById(R.id.updatename_button);

        update_name.setText(name_value);

        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = firebaseAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar = new ProgressDialog(Setting_change.this);
                progressBar.setTitle("Saving Changes");
                progressBar.setMessage("Please wait");
                progressBar.show();

                String username = update_name.getText().toString();
                databaseReference.child("name").setValue(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            progressBar.dismiss();
                            Intent intent = new Intent(Setting_change.this,SettingsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }else
                        {
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
