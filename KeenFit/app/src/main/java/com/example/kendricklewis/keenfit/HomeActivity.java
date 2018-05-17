package com.example.kendricklewis.keenfit;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kendricklewis.keenfit.Activities.AddActivity;
import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.Fragments.Summary_Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.example.kendricklewis.keenfit.Activities.LoginActivity.mCurrentUser;
import static com.example.kendricklewis.keenfit.Classes.CurrentUser.*;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{

    private DrawerLayout drawer;
    public static String userID;
    //grabing all my user info
    public static CurrentUser currentUser = new CurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setActionBar(null);

        Toasty.Config.getInstance().setSuccessColor(Color.GREEN).apply();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setting buttons
        findViewById(R.id.h_AddFood_btn).setOnClickListener(this);
        findViewById(R.id.h_Entries_btn).setOnClickListener(this);
        findViewById(R.id.h_Goals_btn).setOnClickListener(this);
        findViewById(R.id.h_Friends_btn).setOnClickListener(this);
        findViewById(R.id.h_MealHistory_btn).setOnClickListener(this);

        findViewById(R.id.menuBtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        userID = mCurrentUser.getUid();

        //grabing my data for displaying in realtime with updates and changes

        /* MY FIREBASE DATABASE*/FirebaseDatabase database = FirebaseDatabase.getInstance();
        /* USER REFERENCE */ DatabaseReference userRef = database.getReference().child("Users").child(userID);


        //TODO: GATHERS MY USER DATA
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        //String value = dataSnapshot.getValue(String.class);
        //Log.d("returned value:", "Value is: " + value);
        userRef.addValueEventListener(new ValueEventListener() //This method will fire whenever my database changes
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { gatherUserData(dataSnapshot); }

            @Override
            public void onCancelled(DatabaseError error) { Log.w("Error: ", "Failed to read value.", error.toException()); }
        });

        //READS MEAL DATA FROM FIREBASE
        /* GOAL REFERENCE */ DatabaseReference mealRef = database.getReference().child("Meals").child(userID);

        mealRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { gatherMealData(dataSnapshot); }

            @Override
            public void onCancelled(DatabaseError error) { Log.w("Error: ", "Failed to read value.", error.toException()); }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.h_Summary_frame,
                Summary_Fragment.newInstance()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void gatherUserData(DataSnapshot dataSnapshot)
    {

        try
        {
            //Grab the info out of the firebase one at a time and fill
            String name = dataSnapshot.child("name").getValue(String.class);
            String gName = dataSnapshot.child("g_name").getValue(String.class);
            Integer id_picture = dataSnapshot.child("id_picture").getValue(Integer.class);
            Double weight  = dataSnapshot.child("weight").getValue(Double.class);
            Double currentCals = dataSnapshot.child("current_Calories").getValue(Double.class);
            Double averageDaily = dataSnapshot.child("average_daily").getValue(Double.class);
            Double averageWeekly = dataSnapshot.child("average_weekly").getValue(Double.class);
            Double averageByWeekly = dataSnapshot.child("average_biWeekly").getValue(Double.class);

            //grabing goals out of firebase
            DataSnapshot snapshot = dataSnapshot.child("goals");;

            double totalCals = snapshot.child("total_calories").getValue(Double.class);
            double totalCarbs = snapshot.child("total_carbs").getValue(Double.class);
            double totalCholesterol = snapshot.child("total_cholesterol").getValue(Double.class);
            double totalDietary = snapshot.child("total_dietary").getValue(Double.class);
            double totalFat = snapshot.child("total_fat").getValue(Double.class);
            double totalProtein = snapshot.child("total_protein").getValue(Double.class);
            double totalSaturated = snapshot.child("total_saturatedfat").getValue(Double.class);
            double totalSodium = snapshot.child("total_sodium").getValue(Double.class);
            double totalSugars = snapshot.child("total_sugars").getValue(Double.class);

            //* Setting the information into my user summary *//
            currentUser.setName(name);

            currentUser.setG_name(gName);

            currentUser.setId_picture(id_picture);

            currentUser.setWeight(weight);

            currentUser.setCurrent_Calories(currentCals);

            currentUser.setAverage_daily(averageDaily);

            currentUser.setAverage_weekly(averageWeekly);

            currentUser.setAverage_byWeekly(averageByWeekly);

            //* Setting the goals*//
            Goal updatedGoals = new Goal();

            updatedGoals.setTotal_calories(totalCals);
            updatedGoals.setTotal_carbs(totalCarbs);
            updatedGoals.setTotal_cholesterol(totalCholesterol);
            updatedGoals.setTotal_dietary(totalDietary);
            updatedGoals.setTotal_fat(totalFat);
            updatedGoals.setTotal_protein(totalProtein);
            updatedGoals.setTotal_saturatedfat(totalSaturated);
            updatedGoals.setTotal_sodium(totalSodium);
            updatedGoals.setTotal_sugars(totalSugars);

            currentUser.setGoals(updatedGoals);

            //reseting my summary fragment to reflect new changes
            getSupportFragmentManager().beginTransaction().replace(R.id.h_Summary_frame,
                    Summary_Fragment.newInstance()).commit();

        }
        catch (NullPointerException e)
        {
            Toasty.error(this, "Gathering info Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void gatherMealData(DataSnapshot datasnapshot)
    {
        ArrayList<Meal> savedMeals = new ArrayList<>();

       for (DataSnapshot aMeal: datasnapshot.getChildren())
       {
           try
           {
               String id =          aMeal.child("id").getValue(String.class);
               String name =        aMeal.child("name").getValue(String.class);
               String brandname =   aMeal.child("brandname").getValue(String.class);
               String dateAdded =   aMeal.child("date").getValue(String.class);

               Integer servings =   aMeal.child("servings").getValue(Integer.class);

               Double calories =    aMeal.child("calories").getValue(Double.class);
               Double carbs =       aMeal.child("carbs").getValue(Double.class);
               Double cholesterol = aMeal.child("cholesterol").getValue(Double.class);
               Double dietary =     aMeal.child("dietary").getValue(Double.class);
               Double fat =         aMeal.child("fat").getValue(Double.class);
               Double protein =     aMeal.child("protein").getValue(Double.class);
               Double saturatedfat= aMeal.child("saturatedfat").getValue(Double.class);
               Double sodium =      aMeal.child("sodium").getValue(Double.class);
               Double sugars =      aMeal.child("sugars").getValue(Double.class);

               CurrentUser.Meal databaseMeal =
                       new CurrentUser.Meal(
                               id, name, brandname, dateAdded, servings, calories, carbs,
                               cholesterol, dietary, fat, protein, saturatedfat, sodium, sugars);

               savedMeals.add(databaseMeal);

           }
           catch (NullPointerException e)
           {
             e.printStackTrace();
           }
       }

       currentUser.setMeals(savedMeals);
    }

    @Override
    public void onClick(View v)
    {
        findViewById(R.id.h_AddFood_btn).setOnClickListener(this);
        findViewById(R.id.h_Entries_btn).setOnClickListener(this);
        findViewById(R.id.h_Goals_btn).setOnClickListener(this);
        findViewById(R.id.h_Friends_btn).setOnClickListener(this);
        findViewById(R.id.h_MealHistory_btn).setOnClickListener(this);

        int id = v.getId();

        switch(id)
        {
            case R.id.h_AddFood_btn:
            {
                Intent addIntent = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(addIntent);
                break;
            }

            case R.id.h_Entries_btn:
            {
                break;
            }

            case R.id.h_Goals_btn:
            {
                break;
            }

            case R.id.h_Friends_btn:
            {
                break;
            }

            case R.id.h_MealHistory_btn:
            {
                break;
            }
        }
    }
}
