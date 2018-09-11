package com.socialapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;


public class MainActivity extends AppCompatActivity {
FirebaseAuth mAuth;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    TextView mUserName;
  RecyclerView mRecyclerView;
  RecyclerView.LayoutManager manager;
    MyRecyclerView adapter;
    List<User> mUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserName=(TextView)findViewById(R.id.username);
        mRecyclerView=(RecyclerView) findViewById(R.id.recyclerViews);
        if(mUser==null)
        {
        mUser=new ArrayList<>();}
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarView);
           databaseReference= FirebaseDatabase.getInstance().getReference().child("posts");
        storageReference= FirebaseStorage.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String name=user.getDisplayName();
        /*if(user!=null)
        {*/
         mUserName.setText(name);
      /*  }
        else {
            mUserName.setText("name");
        }*/
        FloatingActionButton actionButton=(FloatingActionButton)findViewById(R.id.floatingAction);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,AddPost.class));
                finish();
            }
        });
       adapter=new MyRecyclerView(mUser);
        mRecyclerView.setAdapter(adapter);
        manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User users=dataSnapshot.getValue(User.class);

                mUser.add(users);
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



}













    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,LogInActivity.class));
                break;
        }


        return true;
    }




}
