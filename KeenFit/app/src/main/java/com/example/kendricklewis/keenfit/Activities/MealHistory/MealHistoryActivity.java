package com.example.kendricklewis.keenfit.Activities.MealHistory;

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
import com.example.kendricklewis.keenfit.Activities.Goals.GoalsActivity;
import com.example.kendricklewis.keenfit.Activities.Login.LoginActivity;
import com.example.kendricklewis.keenfit.R;

import static com.example.kendricklewis.keenfit.Activities.Login.LoginActivity.mCurrentUser;
import static com.example.kendricklewis.keenfit.HomeActivity.currentUser;
import static com.example.kendricklewis.keenfit.HomeActivity.profilePic_Array;

public class MealHistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    /*
    Navigational Drawer
     */
     DrawerLayout drawer;
    private ImageButton menuBtn;
    ImageView nav_image;
    TextView nav_name;
    TextView nav_email;
    //-----------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_history);


        /*
        --------------------------------------------------------------------------------
        --------------------------------------------------------------------------------
        START: Navigational Controller and Menu button
         */
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();

        menuBtn = (ImageButton) findViewById(R.id.mh_menuBtn);

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


        /*
        --------------------------------------------------------------------------------
        --------------------------------------------------------------------------------
        START: FoodListFragment
         */
        if(!currentUser.getMeals().isEmpty())
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.mh_List_Frame,
                    MealHistory_ListFragment.newInstance()).commit();
        }

         /*
        END: FoodListFragment
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
//                Intent nextIntent = new Intent(this, MealHistoryActivity.class);
//                startActivity(nextIntent);
                break;
            }
            case R.id.nav_goals:
            {
                Intent nextIntent = new Intent(this, GoalsActivity.class);
                startActivity(nextIntent);
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
}
