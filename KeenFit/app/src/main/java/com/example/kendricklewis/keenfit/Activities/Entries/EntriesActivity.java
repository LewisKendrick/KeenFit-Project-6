package com.example.kendricklewis.keenfit.Activities.Entries;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kendricklewis.keenfit.Activities.AboutUsActivity;
import com.example.kendricklewis.keenfit.Activities.AddFood.AddActivity;
import com.example.kendricklewis.keenfit.Activities.Friends.FriendsActivity;
import com.example.kendricklewis.keenfit.Activities.Goals.GoalsActivity;
import com.example.kendricklewis.keenfit.Activities.Login.LoginActivity;
import com.example.kendricklewis.keenfit.Activities.MealHistory.MealHistoryActivity;
import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import es.dmoral.toasty.Toasty;

import static com.example.kendricklewis.keenfit.Activities.Login.LoginActivity.mCurrentUser;
import static com.example.kendricklewis.keenfit.HomeActivity.currentUser;
import static com.example.kendricklewis.keenfit.HomeActivity.profilePic_Array;

public class EntriesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    TextView totalCal_txt;
    TextView totalServings_txt;
    double totalCal = 0;
    int totalServings = 0;
    public static ArrayList<CurrentUser.Meal> entryMeals = new ArrayList<>();
    private ArrayList<CurrentUser.Meal> usersMeals = new ArrayList<>();

    /*
    Navigational Drawer
    */
    DrawerLayout drawer;
    private ImageButton menuBtn;
    ImageView nav_image;
    TextView nav_name;
    TextView nav_email;

    /*
    Calender
     */
    private HorizontalCalendar horizontalCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);

        totalCal_txt =  (TextView) findViewById(R.id.e_TotalCalories_txt);
        totalServings_txt = (TextView) findViewById(R.id.e_TotalServings_txt);
        usersMeals = currentUser.getMeals();

        /*
        --------------------------------------------------------------------------------
        --------------------------------------------------------------------------------
        START: Navigational Controller and Menu button
         */
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();

        menuBtn = (ImageButton) findViewById(R.id.e_menuBtn);

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
        Start: CalenderView
         */

        /* start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();

        startDate.add(Calendar.MONTH, -2);

        /* end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
        //endDate.add(Calendar.DAY_OF_YEAR, -1);

        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();

        gatherMealsForDate(defaultSelectedDate);
        getSupportFragmentManager().beginTransaction().replace(R.id.e_List_Frame, Entries_ListFragment.newInstance()).commit();

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .colorTextMiddle(Color.LTGRAY, Color.parseColor("#ffd54f"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .addEvents(new CalendarEventsPredicate() {

                    Random rnd = new Random();
                    @Override
                    public List<CalendarEvent> events(Calendar date)
                    {
                        List<CalendarEvent> events = new ArrayList<>();

                        gatherMealsForDate(date);
                        int count = entryMeals.size() -1;

                        for (int i = 0; i <= count; i++)
                        {
                            events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
                        }

                        return events;
                    }
                })
                .build();

        Log.i("Default Date", DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString());

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position)
            {
                String selectedDateStr = DateFormat.format("yyyy/MM/dd", date).toString();
                Toasty.success(getApplicationContext(), selectedDateStr + " selected!", Toast.LENGTH_SHORT).show();
                Log.i("onDateSelected", selectedDateStr + " - Position = " + position);

                totalCal = 0;
                totalServings = 0;
                gatherMealsForDate(date);

                getSupportFragmentManager().beginTransaction().replace(R.id.e_List_Frame, Entries_ListFragment.newInstance()).commit();
            }

        });

        /*
        END: CalenderView
        --------------------------------------------------------------------------------
        --------------------------------------------------------------------------------
         */

        gatherMealsForDate(endDate);

    }

    private void gatherMealsForDate(Calendar date)
    {
        String selectedDateStr = DateFormat.format("yyyy/MM/dd", date).toString();
        entryMeals = new ArrayList<>();

        for (CurrentUser.Meal singleMeal : usersMeals)
        {
            if(singleMeal.getDateAdded().equals(selectedDateStr))
            {
                entryMeals.add(singleMeal);

                totalCal += (singleMeal.getCalories() * singleMeal.getServings());
                totalServings += singleMeal.getServings();
            }
        }

        totalCal_txt.setText(String.valueOf(totalCal));
        totalServings_txt.setText(String.valueOf(totalServings));

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
//                Intent nextIntent = new Intent(this, EntriesActivity.class);
//                startActivity(nextIntent);
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
