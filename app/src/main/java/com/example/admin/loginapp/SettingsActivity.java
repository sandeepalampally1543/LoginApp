package com.example.admin.loginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    TextView user_name;
    android.support.v7.widget.Toolbar settings_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings_toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(settings_toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_name = (TextView)findViewById(R.id.profile_name);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();

                user_name.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
