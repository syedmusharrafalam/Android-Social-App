package com.socialapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by User on 3/1/2018.
 */

public class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {
    Comments comments;
    boolean clicked=false;
    List<User> mList;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference commentRef;
    FirebaseAuth auth;
    int count=0;

Activity activity;

    public MyRecyclerView(List<User> mList) {

        this.mList = mList;


    }


    @Override
    public MyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;



    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {



        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        commentRef = reference.getRef();
        auth=FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        final String name=user.getDisplayName();
        final User users=mList.get(position);
         holder.userName.setText(users.getUserName());
          holder.post.setText(users.getPostText());
        Glide.with(holder.image.getContext()).load(users.getImage()).into(holder.image);

        final int[] counter = {users.getLike()};

        /*counter=Integer.parseInt(users.getLike());*/
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             counter[0]++;
                //commentRef.child("posts").child(users.getGetPostId()).child("like").setValue(String.valueOf(counter[0]));
                commentRef.child("posts").child(users.getGetPostId()).child("like").setValue(counter[0]);
                //Toast.makeText(MyApp.getContext(),"like",Toast.LENGTH_LONG).show();
               //holder.likeText.setText(users.getLike());

            }
        });

  DatabaseReference refLikes =  commentRef.child("posts").child(users.getGetPostId()).child("like");
        refLikes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a = dataSnapshot.getValue().toString();
                int b = Integer.parseInt(a);
                holder.btnLike.setText(""+b);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*DatabaseReference ref1 = commentRef.child("POSTS").child(users.getGetPostId()).child("likes");
        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Log.d("TAG LIKES",dataSnapshot.getValue().toString());




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
*/


        holder.submitComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getComm = holder.addComments.getText().toString();
                final Comments comment = new Comments(name,getComm);
               commentRef.child("posts").child(users.getGetPostId()).child("comments").push().setValue(comment);
                holder.addComments.setText("");
                  //  adapter.notifyDataSetChanged();


            }
        });

        holder.commentHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearLayout.setVisibility(View.GONE);
                holder.btnComments2.setVisibility(View.VISIBLE);
                //;
            }
        });

        holder.btnComments2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearLayout.setVisibility(View.VISIBLE);
                TextView textView = new TextView(holder.linearLayout.getContext());
                textView.setText(comments.getUserName() + " " + "says" + " " + " ");

                TextView textViews = new TextView(holder.linearLayout.getContext());
                textViews.setText(comments.getComments());
                holder.btnComments2.setVisibility(View.INVISIBLE);
                holder.commentHide.setVisibility(View.VISIBLE);


            }
        });



holder.btnComments.setOnClickListener(new View.OnClickListener() {



    @Override
    public void onClick(View v) {holder.linearLayout.setVisibility(View.VISIBLE);
      /*count++;
       if(count==0){*/
        DatabaseReference ref3=FirebaseDatabase.getInstance().getReference().child("posts").child(users.getGetPostId()).child("comments");
        //databaseReference= FirebaseDatabase.getInstance().getReference().child("posts");

       // DatabaseReference ref3=commentRef.child("POSTS").child(users.getGetPostId()).child("comments");
        ref3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                comments = dataSnapshot.getValue(Comments.class);
                TextView textView = new TextView(holder.linearLayout.getContext());
                textView.setText(comments.getUserName() + " " + "says" + " " + " ");
                holder.linearLayout.addView(textView);


                TextView textViews = new TextView(holder.linearLayout.getContext());
                textViews.setText(comments.getComments());
                holder.linearLayout.addView(textViews);



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
holder.btnComments.setVisibility(View.INVISIBLE);




    //}
  /*  else{

           holder.linearLayout.setVisibility(View.GONE);
           count=0;
       }*/
    }

});






    }




    @Override
    public int getItemCount() {
       return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView userName,post,likeText,addComments;
        public Button btnLike,btnComments,submitComments,commentHide,btnComments2;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

           userName=(TextView)itemView.findViewById(R.id.textUserName);
          post=(TextView)itemView.findViewById(R.id.textPost);
         image=(ImageView)itemView.findViewById(R.id.imageUser);
           btnLike=(Button)itemView.findViewById(R.id.btnLike);
            btnComments=(Button)itemView.findViewById(R.id.btnComments);
            likeText=(TextView)itemView.findViewById(R.id.likeText);
           submitComments=(Button)itemView.findViewById(R.id.submitComments);
            addComments=(TextView)itemView.findViewById(R.id.addComments);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear_layout);
           commentHide=(Button)itemView.findViewById(R.id.btnCommentsHide);
            btnComments2=(Button)itemView.findViewById(R.id.btnComments2);
           btnComments2.setVisibility(View.INVISIBLE);
          //  commentHide.setVisibility(View.INVISIBLE);







        }


    }







    }










