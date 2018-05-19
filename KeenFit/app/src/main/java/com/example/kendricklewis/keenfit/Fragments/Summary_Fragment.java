package com.example.kendricklewis.keenfit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kendricklewis.keenfit.Activities.LoginActivity;
import com.example.kendricklewis.keenfit.R;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

import static com.example.kendricklewis.keenfit.HomeActivity.currentUser;

public class Summary_Fragment extends Fragment
{

    public static Summary_Fragment newInstance() {

        Bundle args = new Bundle();

        Summary_Fragment fragment = new Summary_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View summaryView = inflater.inflate(R.layout.fragment_summary, container, false);

        TextView currentCals = (TextView) summaryView.findViewById(R.id.s_CurrentCal_Txt);
        TextView goalTotal = (TextView) summaryView.findViewById(R.id.s_GoalTotal_Txt);
        TextView mealsToday = (TextView) summaryView.findViewById(R.id.s_MealsToday_Txt);
        TextView dailyAverage = (TextView) summaryView.findViewById(R.id.s_DailyAverage_Txt);

        try
        {
            currentCals.setText(String.format("%s", currentUser.getCurrent_Calories()));
            goalTotal.setText(String.format("%s", currentUser.getGoals().getTotal_calories()));
            mealsToday.setText(String.format("%d", currentUser.getMeals().size()));
            dailyAverage.setText(String.format("%s", currentUser.getAverage_daily()));
        }
        catch (NullPointerException e)
        {
            Log.i(getTag(), "Summary failed to load");
        }


        return summaryView;
    }
}
