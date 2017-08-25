package com.aharoldk.iak_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.etName) EditText mName;
    @BindView(R.id.etEmail) EditText mEmail;
    @BindView(R.id.etPassword) EditText mPassword;
    @BindView(R.id.btnRegister) Button mRegister;

    private String name;

    private FirebaseAuth mAuth;
    private DatabaseReference mUsers;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        declarate();
    }

    private void declarate() {
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mUsers = FirebaseDatabase.getInstance().getReference().child("users");

        mDialog = new ProgressDialog(this);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mDialog.setMessage("Wait . . .");
            mDialog.show();

            checkRegis();
            }
        });
    }

    private void checkRegis() {
        name = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)  && !TextUtils.isEmpty(password) ){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        createDatabase();
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            mDialog.dismiss();
            Toast.makeText(this, "Please Fill All Field", Toast.LENGTH_SHORT).show();
        }
    }

    private void createDatabase() {
        String id = mAuth.getCurrentUser().getUid();

        DatabaseReference user = mUsers.child(id);
        user.child("name").setValue(name);

        mDialog.dismiss();

        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
