package com.example.kendricklewis.keenfit.Activities.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kendricklewis.keenfit.HomeActivity;
import com.example.kendricklewis.keenfit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity
{
    EditText username_editText;
    EditText password_editText;
    ProgressBar loadingBar;

    //checking the current auth state
    public static FirebaseAuth mAuth;
    public static FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);

        //creating instance for firebase
        mAuth = FirebaseAuth.getInstance();

        username_editText = (EditText) findViewById(R.id.l_Username_txt);
        password_editText = (EditText) findViewById(R.id.l_Password_txt);

        findViewById(R.id.l_SignIn_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadingBar.setVisibility(View.VISIBLE);
                signInUser();
            }
        });

        findViewById(R.id.l_SignUp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingBar.setVisibility(View.GONE);
    }

    private void signInUser()
    {
       String email, password;

       email = username_editText.getText().toString();
       password = password_editText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mCurrentUser = mAuth.getCurrentUser();


                            //move to the next page
                            Intent homepageIntent = new Intent(LoginActivity.this, HomeActivity
                                    .class);

                            startActivity(homepageIntent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Failed: ", "signInWithEmail:failure", task.getException());
                            Toasty.error(getApplicationContext(), "Sign in failed: Incorrect " +
                                    "password or email", Toast
                                    .LENGTH_SHORT).show();

                            password_editText.setText("");
                            username_editText.setText("");

                            loadingBar.setVisibility(View.GONE);

                        }
                    }
                });
    }
}
