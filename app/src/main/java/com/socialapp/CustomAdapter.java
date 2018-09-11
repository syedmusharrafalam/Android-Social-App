package com.socialapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

/**
 * Created by User on 2/24/2018.
 */

public class CustomAdapter extends ArrayAdapter<User>{
    FirebaseAuth mAuth;
    private Activity context;
    private List<User> userList;

    public CustomAdapter(Activity context, List<User>userList)
    {
        super(context,R.layout.custom_layout,userList);
        this.context=context;
        this.userList=userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        final View listViewItem=inflater.inflate(R.layout.custom_layout,null,true);
        TextView userName=(TextView)listViewItem.findViewById(R.id.textUserName);
        TextView post=(TextView)listViewItem.findViewById(R.id.textPost);
        ImageView image=(ImageView)listViewItem.findViewById(R.id.imageUser);
        Button btnLike=(Button)listViewItem.findViewById(R.id.btnLike);
        Button btnComments=(Button)listViewItem.findViewById(R.id.btnComments);


        final User users=userList.get(position);
        userName.setText(users.getUserName());
        post.setText(users.getPostText());
    //  image.load(users.getImage().toString());


        return listViewItem;
    }
}
