package com.example.kendricklewis.keenfit.Activities.MealHistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kendricklewis.keenfit.Activities.AddFood.AddFood_DetailsActivity;
import com.example.kendricklewis.keenfit.Adapters.AddFood_Adapter;
import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.R;

import java.util.ArrayList;

import static com.example.kendricklewis.keenfit.HomeActivity.currentUser;

public class MealHistory_ListFragment extends ListFragment
{
        public static int selected = 0;
        public static ArrayList<CurrentUser.Meal> finalValues= new ArrayList<>();


    public static MealHistory_ListFragment newInstance()
        {
            Bundle args = new Bundle();
            MealHistory_ListFragment fragment = new MealHistory_ListFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return inflater.inflate(R.layout.fragment_list, container, false);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState)
        {
            super.onActivityCreated(savedInstanceState);

            //Changing the list to have everything only one item at a time
            ArrayList<CurrentUser.Meal> values= currentUser.getMeals();
            ArrayList<String> newValues = new ArrayList<>();
            ArrayList<Integer> Positions = new ArrayList<>();
            finalValues= new ArrayList<>();
            //this will hold all final values


            //getting the id name to sort out which is unique
            for(int i = 0; i< values.size(); i++)
            {
                if (!newValues.contains(values.get(i).getId()))
                {
                   newValues.add(values.get(i).getId());
                   Positions.add(i); //adding the position where there was something new
                }
            }

            for(int i = 0; i< Positions.size(); i++)
            {
                finalValues.add(values.get(Positions.get(i)));
            }


            AddFood_Adapter foodAdapter = new AddFood_Adapter(getContext(), finalValues);
            setListAdapter(foodAdapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id)
        {
            super.onListItemClick(l, v, position, id);
            Intent detailIntent = new Intent(getContext(), AddFood_DetailsActivity.class);
            selected = position;
            startActivity(detailIntent);

        }
}

