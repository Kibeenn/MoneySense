package com.example.moneysensev1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity2 extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private Button btnReg;
    private EditText mConPassReg;
    private ProgressDialog mDialog;
    private CheckBox terms_cons;

    //Firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.email_reg);
        mPass = (EditText) findViewById(R.id.password_reg);
        mConPassReg = (EditText) findViewById(R.id.con_password_reg);
        btnReg = (Button) findViewById(R.id.btn_reg);
        mDialog = new ProgressDialog(this);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });

    }

    private void CreateNewAccount(){

        String email = mEmail.getText().toString();
        String password = mPass.getText().toString();
        String conPass = mConPassReg.getText().toString();

        if (TextUtils.isEmpty(email)){
            mEmail.setError("Field can't be empty");
        }

        else if (TextUtils.isEmpty(password)){
            mPass.setError("Field can't be empty");
        }

        else if (password.length()<6){
            mPass.setError("Password must be at least 6 characters");
        }

        else if (TextUtils.isEmpty(conPass)){
            mConPassReg.setError("Field can't be empty");
        }

        else if (!password.equals(conPass)){
            mPass.setError("Passwords does not match");
            mConPassReg.setError("Passwords does not match");
        }

        else {

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                mAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegistrationActivity2.this, "Please check your email for verification link.",
                                                            Toast.LENGTH_SHORT).show();
                                                    mEmail.setText("");
                                                    mPass.setText("");
                                                    mConPassReg.setText("");
                                                }

                                                else {
                                                    Toast.makeText(RegistrationActivity2.this, task.getException().getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }

                            else {
                                Toast.makeText(RegistrationActivity2.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        });
                    }
    }


}


