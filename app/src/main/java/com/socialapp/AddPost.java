package com.socialapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AddPost extends AppCompatActivity {
    FirebaseAuth mAuth;
private final int PICK_IMAGE_REQUEST=71;
    private Button mChooseImage;
     private Button mButtonPost;
     TextView mTextViewUser;
     EditText mEditTextPost;
     ImageView mImageView;
    ProgressBar progressBar;
     Uri mImageUri;
    String imageUri;
    FirebaseStorage storage;
    DatabaseReference dr;
    FirebaseDatabase database;
    DatabaseReference posrRef;
    String userID;
    String name;
    Bitmap bitmap;
    private String updateID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        storage = FirebaseStorage.getInstance();


        mTextViewUser=(TextView) findViewById(R.id.userText);
        mChooseImage=(Button)findViewById(R.id.ImageAdd);
        mButtonPost=(Button)findViewById(R.id.postButton);
        mImageView=(ImageView)findViewById(R.id.imgPost);
        mEditTextPost=(EditText)findViewById(R.id.editPost);
        progressBar=(ProgressBar)findViewById(R.id.progressbarLogin);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        name=user.getDisplayName();
       if(user!=null)
      {
         userID=mAuth.getCurrentUser().getUid();


           mTextViewUser.setText(name);
       }
       else {
            mTextViewUser.setText("name");}



        database = FirebaseDatabase.getInstance();
        dr = database.getReference();
        dr.child("posts");
        posrRef=dr.getRef();




       // mEditTextPost=(EditText)findViewById(R.id.postText);
        mChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 openFileChooser();
            }
        });
        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String id=updateID;


                if(updateID==null)
                {  //posrRef = dr.getRef();


                  id = posrRef.push().getKey();}
              if(mImageUri!=null){
                uploadPicture(mImageUri,id);
                  String getKey=dr.child(id).getKey();

                  User userClass=new User(userID,name,mEditTextPost.getText().toString(),id,0,getKey);
                  dr.child("posts").child(id).setValue(userClass);


              }
              else {
               String getKey=dr.child(id).getKey();
                
                User userClass=new User(userID,name,mEditTextPost.getText().toString(),id,0,getKey);
                dr.child("posts").child(id).setValue(userClass);

                    progressBar.setVisibility(View.GONE);
                startActivity(new Intent(AddPost.this,MainActivity.class));
                finish();
                Toast.makeText(AddPost.this,"uploaded",Toast.LENGTH_LONG).show();}



            }
        });




    }

    private void openFileChooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
{
    mImageUri=data.getData();
    try
    {
        bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),mImageUri);
        mImageView.setImageBitmap(bitmap);
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }

    //mImageView.setImageURI(mImageUri);
}

    }

/*
private String getFileExtension(Uri uri)
{
    ContentResolver cR=getContentResolver();
    MimeTypeMap mime=MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cR.getType(uri));
}*/




    private void uploadPicture(Uri uri , final String keys){



       StorageReference mStorageReference = storage.getReference();
       StorageReference pics=mStorageReference.child(keys+".jpg");
        UploadTask task=pics.putFile(uri);
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadImageUri=taskSnapshot.getDownloadUrl();
                String imageUri=downloadImageUri.toString();
                posrRef.child("posts").child(keys).child("image").setValue(downloadImageUri.toString());
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(AddPost.this,MainActivity.class));
                finish();
                 Toast.makeText(AddPost.this,"uploaded",Toast.LENGTH_LONG).show();



            }
        });
    }



}
