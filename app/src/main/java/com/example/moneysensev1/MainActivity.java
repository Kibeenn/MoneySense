package com.example.moneysensev1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private Button btnLogin;
    private TextView mForgotPassword;
    private TextView mSignupHere;

    boolean passwordVisible;


    private ProgressDialog mDialog;

    //Firebase

    private FirebaseAuth mAuth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Auto log in feature
        mAuth=FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        mDialog=new ProgressDialog(this);

        loginDetails();

        //Password Visibility
        mPass.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getRawX() > mPass.getRight() - mPass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=mPass.getSelectionEnd();
                        if (passwordVisible){
                            mPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);

                            mPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        } else{
                            mPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24,0);

                            mPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        mPass.setSelection(selection);
                        return true;
                        }
                    }
                return false;
                }
            });
        }

    private void loginDetails(){

        mEmail=findViewById(R.id.email_login);
        mPass=findViewById(R.id.password_login);
        btnLogin=findViewById(R.id.btn_login);
        mForgotPassword=findViewById(R.id.forgot_password);
        mSignupHere=findViewById(R.id.signup_reg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=mEmail.getText().toString().trim();
                String pass=mPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Field can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    mPass.setError("Field can't be empty");
                    return;
                }

                mAuth.signInWithEmailAndPassword(mEmail.getText().toString(),
                                mPass.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    if (mAuth.getCurrentUser().isEmailVerified()) {
                                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    } else {
                                        Toast.makeText(MainActivity.this, "Please verify you email address",
                                                Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Invalid username or password",
                                            Toast.LENGTH_LONG).show();

                                }

                            }

                        });

            }

        });

        //Registration
        mSignupHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity1.class));
            }
        });

        //Reset Password
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText resetMail = new EditText(MainActivity.this);
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(MainActivity.this);
                passwordResetDialog.setTitle("Reset your password?");
                passwordResetDialog.setMessage("Enter your registered email address to receive an email reset link.");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setCancelable(false);


                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the email and send reset link

                        String email = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                    Toast.makeText(MainActivity.this,"Reset link has been sent to your verified email address"
                                            ,Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(MainActivity.this, "Error! Reset link is not sent",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog
                    }
                });

                passwordResetDialog.show();

            }
        });
    }
}
