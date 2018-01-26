package com.example.rajeshpalkar.sweatout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSingup;
    private EditText editTextEmail;
    private EditText editTextPass;
    private TextView textViewLogin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        buttonSingup = (Button) findViewById(R.id.register);
        editTextEmail = (EditText) findViewById(R.id.emailID);
        editTextPass = (EditText) findViewById(R.id.password);
        textViewLogin = (TextView) findViewById(R.id.signIn);

        buttonSingup.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);


    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.register:
                registerUser();
                break;
            case R.id.signIn:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    private void registerUser()
    {
        String email = editTextEmail.getText().toString();
        String pass = editTextPass.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please Enter Your Email!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user registered and logged in
                    finish();
                    startActivity(new Intent(getApplicationContext(),ActivityNavigationDrawer.class));
                }
                else{
                    Toast.makeText(SignUpActivity.this,"Failed to register!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
