package com.example.kendricklewis.keenfit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kendricklewis.keenfit.Activities.LoginActivity;
import com.example.kendricklewis.keenfit.R;
import com.google.firebase.auth.FirebaseUser;

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


        return summaryView;
    }
}
