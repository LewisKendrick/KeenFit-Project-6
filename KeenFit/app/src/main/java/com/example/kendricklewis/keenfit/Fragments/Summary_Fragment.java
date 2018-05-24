package com.example.kendricklewis.keenfit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.kendricklewis.keenfit.Activities.Login.LoginActivity.mCurrentUser;
import static com.example.kendricklewis.keenfit.HomeActivity.currentUser;

public class Summary_Fragment extends Fragment {

    public static Summary_Fragment newInstance() {

        Bundle args = new Bundle();

        Summary_Fragment fragment = new Summary_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View summaryView = inflater.inflate(R.layout.fragment_summary, container, false);

        TextView currentCals = (TextView) summaryView.findViewById(R.id.s_CurrentCal_Txt);
        TextView goalTotal = (TextView) summaryView.findViewById(R.id.s_GoalTotal_Txt);
        TextView mealsToday = (TextView) summaryView.findViewById(R.id.s_MealsToday_Txt);
        TextView dailyAverage = (TextView) summaryView.findViewById(R.id.s_DailyAverage_Txt);

        try {
            currentCals.setText(String.format("%s", currentUser.getCurrent_Calories()));
            goalTotal.setText(String.format("%s", currentUser.getGoals().getTotal_calories()));
            mealsToday.setText(String.format("%d", currentUser.getMealsToday()));
            dailyAverage.setText(String.format("%s", currentUser.getAverage_daily()));
        } catch (NullPointerException e) {
            Log.i(getTag(), "Summary failed to load");
        }


        return summaryView;
    }

//    public void saveToStorage() {
//        double currentCals = 0;
//        double avgWeekly = 0;
//        double avgDaily = 0;
//        double avgBiWeekly = 0;
//
//        Calendar today = Calendar.getInstance();
//
//
//        //Get Daily Count
//        int currentCalCount = 0;
//        double fullCount = 0;
//        String selectedDateStr = DateFormat.format("yyyy/MM/dd", today).toString();
//        for (CurrentUser.Meal theMeal : currentUser.getMeals()) {
//            if (theMeal.getDateAdded().equals(selectedDateStr)) {
//                currentCalCount++;
//                fullCount = fullCount + theMeal.getCalories();
//            }
//        }
//
//        //get weekly
//        Calendar week = Calendar.getInstance();
//        week.getFirstDayOfWeek();
//
//
//        int weeklyCalCount = 0;
//        double weeklyCals = 0;
//
//        String expectedPattern = "yyyy/MM/dd";
//        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
//
//        for (CurrentUser.Meal theMeal : currentUser.getMeals()) {
//            try {
//                Date date = formatter.parse(theMeal.getDateAdded());
//
//                Calendar weeklyDate = Calendar.getInstance();
//                weeklyDate.setTime(date);
//
//                if (weeklyDate.after(week)) {
//                    weeklyCalCount++;
//                    weeklyCals = weeklyCals + theMeal.getCalories();
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        int biWeeklyCount = 0;
//        double biWeeklyCals = 0;
//        Calendar biWeekly = Calendar.getInstance();
//        biWeekly.set(Calendar.WEEK_OF_MONTH, -1);
//
//        for (CurrentUser.Meal theMeal : currentUser.getMeals())
//        {
//            try
//            {
//                Date date = formatter.parse(theMeal.getDateAdded());
//
//                Calendar weeklyDate = Calendar.getInstance();
//                weeklyDate.setTime(date);
//
//                if (weeklyDate.after(biWeekly))
//                {
//                    biWeeklyCount++;
//                    weeklyCals = biWeeklyCals + theMeal.getCalories();
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//
//            //daily amount
//            currentCals = fullCount / currentCalCount;
//            avgWeekly = weeklyCals / weeklyCalCount;
//            avgBiWeekly = biWeeklyCals / biWeeklyCount;
//
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            assert mCurrentUser != null;
//            DatabaseReference userReference = database.getReference("Users").child(mCurrentUser.getUid());
//
//            userReference.child("current_Calories").setValue(currentCals);
//            userReference.child("average_daily").setValue(currentCals + 100);
//            userReference.child("average_weekly").setValue(avgWeekly);
//            userReference.child("average_biWeekly").setValue(avgBiWeekly);
//
//        }
    }

