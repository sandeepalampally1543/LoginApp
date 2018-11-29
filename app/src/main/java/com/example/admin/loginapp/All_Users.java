package com.example.admin.loginapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class All_Users extends AppCompatActivity {

    Toolbar allusers_toolbar;
    RecyclerView recyclerView;

    private DatabaseReference muserdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__users);

        allusers_toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(allusers_toolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        muserdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView = (RecyclerView)findViewById(R.id.allusers_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users,UserViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UserViewholder>(

                Users.class,
                R.layout.users_single,
                UserViewholder.class,
                muserdatabase

        ) {
            @Override
            protected void populateViewHolder(UserViewholder viewHolder, Users model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setUserImage(model.getThumb_nail(),getApplicationContext());

                final String user_id = getRef(position).getKey();
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent profileintent = new Intent(All_Users.this,UsersProfile.class);
                        profileintent.putExtra("user_id",user_id);
                        startActivity(profileintent);
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UserViewholder extends RecyclerView.ViewHolder
    {
        View mview;
        public UserViewholder(View itemView) {
            super(itemView);

            mview = itemView;
        }
        public void setName(String name)
        {
            TextView usernameview = mview.findViewById(R.id.single_name);
            usernameview.setText(name);
        }
        public void setUserImage(String image, Context context)
        {
            CircleImageView userImageView = mview.findViewById(R.id.singlr_image);
            Picasso.with(context).load(image).placeholder(R.mipmap.ic_launcher).into(userImageView);
        }
    }
}
