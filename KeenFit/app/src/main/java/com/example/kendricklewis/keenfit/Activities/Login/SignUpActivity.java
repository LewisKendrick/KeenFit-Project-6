package com.example.kendricklewis.keenfit.Activities.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.HomeActivity;
import com.example.kendricklewis.keenfit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.example.kendricklewis.keenfit.Activities.Login.LoginActivity.mCurrentUser;

public class SignUpActivity extends AppCompatActivity
{

    private ArrayList<Bitmap> pic_Array = new ArrayList<>();
    private int picSelection = 0;
    private FirebaseAuth mAuth;

    private ImageView profileImage;
    private EditText name_txt;
    private EditText email_txt;
    private EditText password_txt;
    private EditText secondPassword_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //setting image array
        fillImageArray();

        //creating instance for firebase
        mAuth = FirebaseAuth.getInstance();

        profileImage = (ImageView) findViewById(R.id.s_profile_image);
        name_txt = (EditText) findViewById(R.id.s_name_txt);
        email_txt = (EditText) findViewById(R.id.s_email_txt);
        password_txt = (EditText) findViewById(R.id.s_password_txt);
        secondPassword_txt = (EditText) findViewById(R.id.s_secondPassword_txt);

        findViewById(R.id.s_previous_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(picSelection >= 1 && picSelection <= 9)
                {
                    picSelection--;
                }
                else
                {
                    picSelection = 9;
                }
                setProfilePic();
                Toasty.info(getApplicationContext(), ""+ picSelection).show();
            }
        });

        findViewById(R.id.s_next_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(picSelection >= 0 && picSelection <= 8 )
                {
                    picSelection++;
                }
                else
                {
                    picSelection = 0;
                }
                setProfilePic();
                Toasty.info(getApplicationContext(), ""+ picSelection).show();
            }
        });

        findViewById(R.id.s_signup_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               createUser();
            }
        });
    }

    public void createUser()
    {
        final String nameString = String.valueOf(name_txt.getText());
        String emailString = String.valueOf(email_txt.getText());
        String passwordString = String.valueOf(password_txt.getText());
        String secondPassword = String.valueOf(secondPassword_txt.getText());

        if(nameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty() || secondPassword.isEmpty())
        {
            Toasty.error(getApplicationContext(), "Error: You must complete all fields").show();
            return;
        }
        if(!passwordString.equals(secondPassword))
        {
            Toasty.error(getApplicationContext(), "Error: Passwords do not match").show();
            return;
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SignUpActivity", "createUserWithEmail:success");
                                mCurrentUser = mAuth.getCurrentUser();

                                CurrentUser newUser = new CurrentUser();
                                CurrentUser.Goal newGoals = new CurrentUser.Goal();

                                newUser.setName(nameString);
                                newUser.setId_picture(picSelection);
                                newUser.setGoals(newGoals);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                assert mCurrentUser != null;
                                DatabaseReference newUserRef = database.getReference("Users").child(mCurrentUser.getUid());
                                newUserRef.setValue(newUser);


                                //move to the next page
                                Intent homepageIntent = new Intent(SignUpActivity.this, HomeActivity
                                        .class);
                                homepageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(homepageIntent);
                                finish();
                            }
                            else
                            {
                                // If sign in fails, display a message to the user.
                                Log.w("SignUpActivity", "createUserWithEmail:failure", task.getException());
                                Toasty.error(getApplicationContext(), "Authentication failed." + task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }


    }

    public void setProfilePic()
    {
        profileImage.setImageBitmap(pic_Array.get(picSelection));
    }

    public void fillImageArray()
    {
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_1);
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_2);
        Bitmap image3 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_3);
        Bitmap image4 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_4);
        Bitmap image5 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_5);
        Bitmap image6 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_6);
        Bitmap image7 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_7);
        Bitmap image8 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_8);
        Bitmap image9 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_9);
        Bitmap image10 = BitmapFactory.decodeResource(getResources(),R.drawable.icon_10);

        pic_Array.add(image1);
        pic_Array.add(image2);
        pic_Array.add(image3);
        pic_Array.add(image4);
        pic_Array.add(image5);
        pic_Array.add(image6);
        pic_Array.add(image7);
        pic_Array.add(image8);
        pic_Array.add(image9);
        pic_Array.add(image10);
    }
}
