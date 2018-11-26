package com.example.admin.loginapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Random;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    TextView user_name;
    android.support.v7.widget.Toolbar settings_toolbar;
    Button changename,change_image;

    //firebase storgae
    StorageReference mimage_storage;

    private static final int GALLERY_PICK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings_toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(settings_toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        change_image = (Button)findViewById(R.id.changeimage);
        changename = (Button)findViewById(R.id.changestatus);
        user_name = (TextView)findViewById(R.id.profile_name);
        mimage_storage = FirebaseStorage.getInstance().getReference();
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
        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsActivity.this,Setting_change.class);
                String update_name = user_name.getText().toString();
                intent.putExtra("name",update_name);
                startActivity(intent);
            }
        });
        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SELECT IMAGE"),GALLERY_PICK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK)
        {
            Uri image_uri = data.getData();

            CropImage.activity(image_uri)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                StorageReference filepath = mimage_storage.child("profile_images").child(random() + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful())
                    {

                    }else
                    {

                    }
                    }
                });
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    public static String random()
    {
        Random random = new Random();
        StringBuilder randomStringbuilder = new StringBuilder();
        int randomlength = random.nextInt(10);
        char tempchar;
        for (int i = 0 ; i<randomlength;i++)
        {
            tempchar = (char)(random.nextInt(96)+32);
            randomStringbuilder.append(tempchar);
        }
        return randomStringbuilder.toString();
    }
}
