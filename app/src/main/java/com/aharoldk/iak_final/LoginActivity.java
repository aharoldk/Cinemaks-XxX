package com.aharoldk.iak_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.etEmail) EditText mEmail;
    @BindView(R.id.etPassword) EditText mPassword;
    @BindView(R.id.btnLogin) Button mLogin;
    @BindView(R.id.tvRegister) TextView mRegister;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mListener;
    private ProgressDialog mDialog;

    private int i = 0;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        declarate();
    }

    private void declarate() {
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(this);

        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);

        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mListener);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(mLogin)){
            mDialog.setMessage("Wait . . .");
            mDialog.show();

            checkLogin();

        } else if(view.equals(mRegister)) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

        }
    }

    private void checkLogin() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mDialog.dismiss();

                    if(task.isSuccessful()){
                        mDialog.dismiss();

                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Register", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            mDialog.dismiss();
            Toast.makeText(this, "Please Fill All Field", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        i++;
        if(i == 1){
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                i = 0;
            }
        }, 3000);

    }
}
