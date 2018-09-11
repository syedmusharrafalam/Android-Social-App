package com.socialapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextEmails,editTextPasswords,editTextUser;
    private FirebaseAuth mAuth;
    DatabaseReference databaseUser;
    TextView textView;
    ProgressBar progressBar;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        databaseUser= FirebaseDatabase.getInstance().getReference().child("All Users");
        editTextEmails= (EditText) findViewById(R.id.editTextEmails);
        editTextPasswords= (EditText) findViewById(R.id.editTextPasswords);
        editTextUser=(EditText)findViewById(R.id.editTextUser);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        textView=(TextView)findViewById(R.id.textViewLogins);
        signup=(Button)findViewById(R.id.buttonSignup);
        textView=(TextView)findViewById(R.id.textViewLogins);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeruser();
            }
        });
      textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(SignUpActivity.this,LogInActivity.class));
          }
      });
    }
    private void registeruser()
    {
        final String email=editTextEmails.getText().toString().trim();
        final String password=editTextPasswords.getText().toString().trim();
        final String name=editTextUser.getText().toString().trim();
        if(name.isEmpty())
        {
            editTextUser.setError("user name is required");
            editTextUser.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            editTextEmails.setError("email is required");
            editTextEmails.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {  editTextEmails.setError("please enter valid email");
            editTextEmails.requestFocus();
            return;

        }

        if(password.isEmpty())
        {
            editTextPasswords.setError("password is required");
            editTextPasswords.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editTextPasswords.setError("please enter 6 digits");
            editTextPasswords.requestFocus();
            return;

        }
else{
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
               //userProfile();
                if(task.isSuccessful())
                {

                    userProfile();
                    finish();
                    Intent i1=new Intent(SignUpActivity.this,MainActivity.class);
                    i1.putExtra("name",editTextUser.getText().toString().trim());
                   startActivity(i1);
                   // start();
                    Toast.makeText(getApplicationContext(),"Registered Successfull",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(),"you are already registered",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });}
    }
    private void userProfile() {
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {
            UserProfileChangeRequest profileUpdate= new UserProfileChangeRequest.Builder().setDisplayName(editTextUser.getText().toString().trim()).build();
            //startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {

                        // Toast.makeText(SignUpActiviy.this,"Registered Successfull",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }




}
