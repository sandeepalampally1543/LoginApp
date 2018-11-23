package com.example.admin.loginapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {

    private ViewPager viewPager;
    private SectionPageAdapter sectionPageAdapter;
    private TabLayout tabLayout;
    Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar1 = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Chat App");

        tabLayout =(TabLayout) findViewById(R.id.tabLayout);
        viewPager =(ViewPager) findViewById(R.id.tabviewer);
        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    public void sendTostart()
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sign_out:

                FirebaseAuth.getInstance().signOut();
                sendTostart();
                return true;

            case R.id.acc_settings:

                Intent intent = new Intent(Profile.this,SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
