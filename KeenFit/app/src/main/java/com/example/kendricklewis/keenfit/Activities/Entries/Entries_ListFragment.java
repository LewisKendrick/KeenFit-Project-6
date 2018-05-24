package com.example.kendricklewis.keenfit.Activities.Entries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kendricklewis.keenfit.Activities.AddFood.AddFood_DetailsActivity;
import com.example.kendricklewis.keenfit.Adapters.AddFood_Adapter;
import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.R;

import java.util.ArrayList;

public class Entries_ListFragment extends android.support.v4.app.ListFragment
{
    public static int e_selected = 0;
    public static ArrayList<CurrentUser.Meal> finalValues= new ArrayList<>();


    public static Entries_ListFragment newInstance()
    {

        Bundle args = new Bundle();
        Entries_ListFragment fragment = new Entries_ListFragment();
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

            finalValues = EntriesActivity.entryMeals;
            AddFood_Adapter foodAdapter = new AddFood_Adapter(getContext(), EntriesActivity.entryMeals);
            setListAdapter(foodAdapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id)
        {
            super.onListItemClick(l, v, position, id);
            Intent detailIntent = new Intent(getContext(), AddFood_DetailsActivity.class);
            EntriesActivity.entryMeals = finalValues;
            e_selected = position;
            startActivity(detailIntent);
        }
}
