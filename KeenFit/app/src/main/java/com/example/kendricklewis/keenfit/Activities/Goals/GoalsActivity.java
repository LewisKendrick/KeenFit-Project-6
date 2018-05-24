package com.example.kendricklewis.keenfit.Activities.Goals;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kendricklewis.keenfit.Activities.AboutUsActivity;
import com.example.kendricklewis.keenfit.Activities.AddFood.AddActivity;
import com.example.kendricklewis.keenfit.Activities.Entries.EntriesActivity;
import com.example.kendricklewis.keenfit.Activities.Friends.FriendsActivity;
import com.example.kendricklewis.keenfit.Activities.Login.LoginActivity;
import com.example.kendricklewis.keenfit.Activities.MealHistory.MealHistoryActivity;
import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static com.example.kendricklewis.keenfit.Activities.Login.LoginActivity.mCurrentUser;
import static com.example.kendricklewis.keenfit.HomeActivity.currentUser;
import static com.example.kendricklewis.keenfit.HomeActivity.profilePic_Array;
import static com.example.kendricklewis.keenfit.HomeActivity.userID;

public class GoalsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference mDatabase;
    CurrentUser.Goal currentGoals;
    CurrentUser.Goal newGoals = new CurrentUser.Goal();
    double weight;

    /*
   Navigational Drawer
    */
    DrawerLayout drawer;
    private ImageButton menuBtn;
    ImageView nav_image;
    TextView nav_name;
    TextView nav_email;

    /*
    each textbox
     */
    TextView totalFat_txt;
    TextView saturatedFat_txt;
    TextView calories_txt;
    TextView cholesterol_txt;
    TextView sodium_txt;
    TextView carbohydrates_txt;
    TextView dietaryFiber_txt;
    TextView sugars_txt;
    TextView protein_txt;
    TextView weight_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /*
        --------------------------------------------------------------------------------
        --------------------------------------------------------------------------------
        START: Navigational Controller and Menu button
         */
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();

        menuBtn = (ImageButton) findViewById(R.id.g_menuBtn);

        menuBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (drawer.isDrawerOpen(GravityCompat.START))
                {
                    drawer.closeDrawer(GravityCompat.START);
                }
                else
                {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);
        nav_image = (ImageView)hView.findViewById(R.id.header_image);
        nav_name = (TextView)hView.findViewById(R.id.header_name);
        nav_email = (TextView)hView.findViewById(R.id.header_email);
        menuBtn.setImageBitmap(profilePic_Array.get(currentUser.getId_picture()));
        nav_image.setImageBitmap(profilePic_Array.get(2));
        nav_name.setText(currentUser.getName());
        nav_email.setText(mCurrentUser.getEmail());

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        END: Navigational Controller
        --------------------------------------------------------------------------------
        --------------------------------------------------------------------------------
         */

        currentGoals = currentUser.getGoals();
        newGoals = currentGoals;

        totalFat_txt =     (TextView) findViewById(R.id.g_TotalFat_txt);
        saturatedFat_txt = (TextView) findViewById(R.id.g_SaturatedFat_txt);
        calories_txt =     (TextView) findViewById(R.id.g_Calories_Txt);
        cholesterol_txt =  (TextView) findViewById(R.id.g_Cholesterol_txt);
        sodium_txt =       (TextView) findViewById(R.id.g_Sodium_txt);
        carbohydrates_txt =(TextView) findViewById(R.id.g_Carbs_txt);
        dietaryFiber_txt = (TextView) findViewById(R.id.g_Dietary_txt);
        sugars_txt =       (TextView) findViewById(R.id.g_Sugars_Txt);
        protein_txt =      (TextView) findViewById(R.id.g_Protein_txt);
        weight_txt =       (TextView) findViewById(R.id.g_Weight_text);

        fillHints();

        /*
        --------------------------------------------------------------------------------
        --------------------------------------------------------------------------------
        Start: methods for buttons
         */
        findViewById(R.id.g_Reset_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalFat_txt      .setText("");
                saturatedFat_txt  .setText("");
                calories_txt      .setText("");
                cholesterol_txt   .setText("");
                sodium_txt        .setText("");
                carbohydrates_txt .setText("");
                dietaryFiber_txt  .setText("");
                sugars_txt        .setText("");
                protein_txt       .setText("");
                weight_txt        .setText("");

            }
        });


        findViewById(R.id.g_Update_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkAndUpdate();
                finish();
            }
        });

        /*
        End: methods for buttons
        --------------------------------------------------------------------------------
        --------------------------------------------------------------------------------

         */


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.nav_addfood:
            {
                Intent nextIntent = new Intent(this, AddActivity.class);
                startActivity(nextIntent);
                break;
            }
            case  R.id.nav_mealhistory:
            {
                Intent nextIntent = new Intent(this, MealHistoryActivity.class);
                startActivity(nextIntent);
                break;
            }
            case R.id.nav_goals:
            {
//                Intent nextIntent = new Intent(this, GoalsActivity.class);
//                startActivity(nextIntent);
                break;
            }
            case R.id.nav_friends:
            {
                Intent nextIntent = new Intent(this, FriendsActivity.class);
                startActivity(nextIntent);
                break;
            }
            case R.id.nav_entries:
            {
                Intent nextIntent = new Intent(this, EntriesActivity.class);
                startActivity(nextIntent);
                break;
            }

            case R.id.nav_aboutus:
            {
                Intent nextIntent = new Intent(this, AboutUsActivity.class);
                startActivity(nextIntent);
                break;
            }
            case R.id.nav_signout:
            {
                LoginActivity.mAuth.signOut();
                break;
            }


        }

        return true;
    }


    private void checkAndUpdate()
    {

        //collect all text fields
        String totalFatString = String.valueOf(totalFat_txt.getText());
        String saturatedFatString = String.valueOf(saturatedFat_txt.getText());
        String caloriesString = String.valueOf(calories_txt.getText());
        String cholesterolString = String.valueOf(cholesterol_txt.getText());
        String sodiumString = String.valueOf(sodium_txt.getText());
        String carbohydratesString = String.valueOf(carbohydrates_txt.getText());
        String dietaryFiberString = String.valueOf(dietaryFiber_txt.getText());
        String sugarsString = String.valueOf(sugars_txt.getText());
        String proteinString = String.valueOf(protein_txt.getText());
        String weightString = String.valueOf(weight_txt.getText());


        //check to see if textfields are changed, if it is then we will update *newUsers
        //
        if(!totalFatString.isEmpty()) { double totalFatNumber = Double.parseDouble(totalFatString);newGoals.setTotal_fat(totalFatNumber); }
        if(!saturatedFatString.isEmpty()) { double saturatedFatNumber = Double.parseDouble(saturatedFatString);newGoals.setTotal_saturatedfat(saturatedFatNumber); }
        if(!caloriesString.isEmpty()) { double caloriesNumber = Double.parseDouble(caloriesString);newGoals.setTotal_calories(caloriesNumber); }
        if(!cholesterolString.isEmpty()) { double cholesterolNumber = Double.parseDouble(cholesterolString);newGoals.setTotal_cholesterol(cholesterolNumber); }
        if(!sodiumString.isEmpty()) { double sodiumNumber = Double.parseDouble(sodiumString);newGoals.setTotal_sodium(sodiumNumber); }
        if(!carbohydratesString.isEmpty()) { double carbohydratesNumber = Double.parseDouble(carbohydratesString);newGoals.setTotal_carbs(carbohydratesNumber); }
        if(!dietaryFiberString.isEmpty()) { double dietaryFiberNumber = Double.parseDouble(dietaryFiberString);newGoals.setTotal_dietary(dietaryFiberNumber); }
        if(!sugarsString.isEmpty()) { double sugarsNumber = Double.parseDouble(sugarsString);newGoals.setTotal_sugars(sugarsNumber); }
        if(!proteinString.isEmpty()) { double proteinNumber = Double.parseDouble(proteinString);newGoals     .setTotal_protein(proteinNumber); }
        if(!weightString.isEmpty()) { double weightNumber = Double.parseDouble(weightString);weight = weightNumber; }

        //finds the place to store the information
        DatabaseReference mainReference = mDatabase.child("Users").child(userID);
        mainReference.child("weight").setValue(weight);
        mainReference.child("goals").setValue(newGoals);

    }

    private void fillHints()
    {
        String totalFatString      = String.valueOf(currentGoals.getTotal_fat());
        String saturatedFatString  = String.valueOf(currentGoals.getTotal_saturatedfat());
        String caloriesString      = String.valueOf(currentGoals.getTotal_calories());
        String cholesterolString   = String.valueOf(currentGoals.getTotal_cholesterol());
        String sodiumString        = String.valueOf(currentGoals.getTotal_sodium());
        String carbohydratesString = String.valueOf(currentGoals.getTotal_carbs());
        String dietaryFiberString  = String.valueOf(currentGoals.getTotal_dietary());
        String sugarsString        = String.valueOf(currentGoals.getTotal_sugars());
        String proteinString       = String.valueOf(currentGoals.getTotal_protein());
        String weightString        = String.valueOf(currentUser.getWeight());
        weight = currentUser.getWeight();

        totalFat_txt.setHint(totalFatString);
        saturatedFat_txt.setHint(saturatedFatString);
        calories_txt.setHint(caloriesString);
        cholesterol_txt.setHint(cholesterolString);
        sodium_txt.setHint(sodiumString);
        carbohydrates_txt.setHint(carbohydratesString);
        dietaryFiber_txt.setHint(dietaryFiberString);
        sugars_txt.setHint(sugarsString);
        protein_txt.setHint(proteinString);
        weight_txt.setHint(weightString);
    }
}
