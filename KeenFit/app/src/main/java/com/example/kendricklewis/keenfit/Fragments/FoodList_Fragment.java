package com.example.kendricklewis.keenfit.Fragments;

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
import com.example.kendricklewis.keenfit.R;
import static com.example.kendricklewis.keenfit.Activities.AddFood.AddActivity.allMeals;

public class FoodList_Fragment extends ListFragment
{
    public static int selectedItem = 0;

    public static FoodList_Fragment newInstance()
    {

        Bundle args = new Bundle();

        FoodList_Fragment fragment = new FoodList_Fragment();
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

        AddFood_Adapter foodAdapter = new AddFood_Adapter(getContext(), allMeals);

        setListAdapter(foodAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Intent detailIntent = new Intent(getContext(), AddFood_DetailsActivity.class);
        selectedItem = position;
        startActivity(detailIntent);

    }
}
