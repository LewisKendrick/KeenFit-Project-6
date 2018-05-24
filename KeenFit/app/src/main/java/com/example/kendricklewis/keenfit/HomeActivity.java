package com.example.kendricklewis.keenfit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kendricklewis.keenfit.Activities.AddFood.AddActivity;
import com.example.kendricklewis.keenfit.Activities.Entries.EntriesActivity;
import com.example.kendricklewis.keenfit.Activities.Goals.GoalsActivity;
import com.example.kendricklewis.keenfit.Activities.MealHistory.MealHistoryActivity;
import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.Fragments.Summary_Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.example.kendricklewis.keenfit.Activities.Login.LoginActivity.mCurrentUser;
import static com.example.kendricklewis.keenfit.Classes.CurrentUser.*;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{

    private DrawerLayout drawer;
    public static String userID;
    private TextView greeting;
    private TextView totalEntries;
    private Boolean shouldUpdate = true;
    private ImageButton menuBtn;
    ImageView nav_image;
    TextView nav_name;
    TextView nav_email;


    //grabing all my user info
    public static CurrentUser currentUser = new CurrentUser();

    //This holds all profile pic images and will be filled later
    public static ArrayList<Bitmap> profilePic_Array = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setActionBar(null);

        setImageArray();

        Toasty.Config.getInstance().setSuccessColor(Color.GREEN).apply();
        shouldUpdate = true;
        userID = mCurrentUser.getUid();
        /* MY FIREBASE DATABASE*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        /* USER REFERENCE */
        DatabaseReference userRef = database.getReference().child("Users").child(userID);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();

        /*
        *setting buttons
         */
        greeting = (TextView) findViewById(R.id.h_Greeting_lbl);
        totalEntries = (TextView) findViewById(R.id.h_TotalEntries_txt);
        findViewById(R.id.h_AddFood_btn).setOnClickListener(this);
        findViewById(R.id.h_Entries_btn).setOnClickListener(this);
        findViewById(R.id.h_Goals_btn).setOnClickListener(this);
        findViewById(R.id.h_Friends_btn).setOnClickListener(this);
        findViewById(R.id.h_MealHistory_btn).setOnClickListener(this);

        /*
        *
        * Navigational Drawer setup
        */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        View hView =  navigationView.getHeaderView(0);
        nav_image = (ImageView)hView.findViewById(R.id.header_image);
        nav_name = (TextView)hView.findViewById(R.id.header_name);
        nav_email = (TextView)hView.findViewById(R.id.header_email);
//   TODO:     nav_image.setImageBitmap(profilePic_Array.get(2));
//   TODO:     nav_name.setText(currentUser.getName());
//   TODO:     nav_email.setText(mCurrentUser.getEmail());

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        menuBtn = (ImageButton) findViewById(R.id.menuBtn);

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

        //TODO: GATHERS MY USER DATA
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        userRef.addValueEventListener(new ValueEventListener() //This method will fire whenever my database changes
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //gatherUserData(dataSnapshot);
                GetUserTask userTask = new GetUserTask();
                userTask.execute(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) { Log.w("Error: ", "Failed to read value.", error.toException()); }
        });

        //READS MEAL DATA FROM FIREBASE
        /* GOAL REFERENCE */ DatabaseReference mealRef = database.getReference().child("Meals").child(userID);

        mealRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                GetMealsTask mealsTask = new GetMealsTask();
                mealsTask.execute(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) { Log.w("Error: ", "Failed to read value.", error.toException()); }
        });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        shouldUpdate = true;
        if(currentUser.getMeals() != null)
        {
            totalEntries.setText(String.format("%d", currentUser.getMeals().size()));
            getSupportFragmentManager().beginTransaction().replace(R.id.h_Summary_frame,
                    Summary_Fragment.newInstance()).commit();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        shouldUpdate = false;
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
                Intent entriesIntent = new Intent(HomeActivity.this, EntriesActivity.class);
                startActivity(entriesIntent);
                break;
            }

            case R.id.h_Goals_btn:
            {
                Intent goalsIntent = new Intent(HomeActivity.this, GoalsActivity.class);
                startActivity(goalsIntent);
                break;
            }

            case R.id.h_Friends_btn:
            {
                break;
            }

            case R.id.h_MealHistory_btn:
            {
                Intent mealHistoryIntent = new Intent(HomeActivity.this, MealHistoryActivity.class);
                startActivity(mealHistoryIntent);
                break;
            }
        }
    }

    public void setImageArray()
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

        profilePic_Array.add(image1);
        profilePic_Array.add(image2);
        profilePic_Array.add(image3);
        profilePic_Array.add(image4);
        profilePic_Array.add(image5);
        profilePic_Array.add(image6);
        profilePic_Array.add(image7);
        profilePic_Array.add(image8);
        profilePic_Array.add(image9);
        profilePic_Array.add(image10);
    }

    public class GetUserTask extends AsyncTask<DataSnapshot, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(DataSnapshot... dataSnapshots)
        {
            try
            {
                DataSnapshot dataSnapshot = dataSnapshots[0];

                //Grab the info out of the firebase one at a time and fill
                String name =            dataSnapshot.child("name").getValue(String.class);
                Integer id_picture =     dataSnapshot.child("id_picture").getValue(Integer.class);
                Double weight  =         dataSnapshot.child("weight").getValue(Double.class);
                Double currentCals =     dataSnapshot.child("current_Calories").getValue(Double.class);
                Double averageDaily =    dataSnapshot.child("average_daily").getValue(Double.class);
                Double averageWeekly =   dataSnapshot.child("average_weekly").getValue(Double.class);
                Double averageByWeekly = dataSnapshot.child("average_biWeekly").getValue(Double.class);

                //grabing goals out of firebase
                DataSnapshot snapshot = dataSnapshot.child("goals");

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

                currentUser.setId_picture(id_picture);

                currentUser.setWeight(weight);

                currentUser.setCurrent_Calories(currentCals);

                currentUser.setAverage_daily(averageDaily);

                currentUser.setAverage_weekly(averageWeekly);

                currentUser.setAverage_biWeekly(averageByWeekly);

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

                return true;
            }
            catch (NullPointerException e)
            {
                Toasty.error(getApplicationContext(), "Gathering info Failed", Toast.LENGTH_SHORT).show();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            super.onPostExecute(aBoolean);

            if(aBoolean && shouldUpdate)
            {
                //setting topbar info
                greeting.setText(String.format("Welcome, %s", currentUser.getName()));
                menuBtn.setImageBitmap(profilePic_Array.get(currentUser.getId_picture()));
                nav_image.setImageBitmap(profilePic_Array.get(2));
                nav_name.setText(currentUser.getName());
                nav_email.setText(mCurrentUser.getEmail());

                getSupportFragmentManager().beginTransaction().replace(R.id.h_Summary_frame,
                        Summary_Fragment.newInstance()).commit();
            }
            else
            {
                Toasty.error(getApplicationContext(), "Error: Failed to get Info");
            }
        }
    }
    public class GetMealsTask extends AsyncTask<DataSnapshot, Void, ArrayList<Meal>>
    {
        @Override
        protected ArrayList<Meal> doInBackground(DataSnapshot... dataSnapshots)
        {
            try
            {
                DataSnapshot datasnapshot = dataSnapshots[0];
                ArrayList<Meal> savedMeals = new ArrayList<>();

                for (DataSnapshot aMeal: datasnapshot.getChildren())
                {
                    try
                    {
                        String id =          aMeal.child("id").getValue(String.class);
                        String name =        aMeal.child("name").getValue(String.class);
                        String brandname =   aMeal.child("brandname").getValue(String.class);
                        String dateAdded =   aMeal.child("dateAdded").getValue(String.class);

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

                return savedMeals;
            }
            catch (NullPointerException e)
            {
                Toasty.error(getApplicationContext(), "Gathering info Failed", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Meal> meals)
        {
            super.onPostExecute(meals);

            if(shouldUpdate)
            {
                totalEntries.setText(meals.size() + "");
                getSupportFragmentManager().beginTransaction().replace(R.id.h_Summary_frame,
                        Summary_Fragment.newInstance()).commit();
            }
            else
            {
                Toasty.error(getApplicationContext(), "Error: Failed to get Meal info");
            }
        }
    }
}
