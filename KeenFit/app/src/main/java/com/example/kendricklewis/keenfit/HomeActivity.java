package com.example.kendricklewis.keenfit;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
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

import com.example.kendricklewis.keenfit.Activities.LoginActivity;
import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.Fragments.Summary_Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

import static com.example.kendricklewis.keenfit.Activities.LoginActivity.mCurrentUser;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawer;
    public static String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toasty.Config.getInstance().setSuccessColor(Color.GREEN).apply();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        //grabing my data for displaying in realtime with updates and changes
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        userID = mCurrentUser.getUid();


        // Read from the database this gets called as soon as the method starts it will listen
        // for anychanges
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d("returned value:", "Value is: " + value);

                gatherSnapshotData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error: ", "Failed to read value.", error.toException());
            }
        });




        getSupportFragmentManager().beginTransaction().replace(R.id.h_Summary_frame,
                Summary_Fragment.newInstance()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void gatherSnapshotData(DataSnapshot dataSnapshot)
    {
        //gathering all my information
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            CurrentUser currentUser = new CurrentUser();
            currentUser.setName(ds.child(userID).getValue(CurrentUser.class).getName());

            currentUser.setG_name(ds.child(userID).getValue(CurrentUser.class).getG_name());

            currentUser.setId_picture(ds.child(userID).getValue(CurrentUser.class).getId_picture());

            currentUser.setWeight(ds.child(userID).getValue(CurrentUser.class).getWeight());

            currentUser.setCurrent_Calories(ds.child(userID).getValue(CurrentUser.class).getCurrent_Calories());

            currentUser.setAverage_daily(ds.child(userID).getValue(CurrentUser.class).getAverage_daily());

            currentUser.setAverage_weekly(ds.child(userID).getValue(CurrentUser.class).getAverage_weekly());

            currentUser.setAverage_byWeekly(ds.child(userID).getValue(CurrentUser.class).getAverage_byWeekly());

           //TODO: currentUser.setGoals(ds.child(userID).getValue(CurrentUser.class).getGoals());

            //currentUser.setName(ds.child(userID).getValue(CurrentUser.class).getName());


        }
    }
}
