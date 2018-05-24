package com.example.kendricklewis.keenfit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.R;

import java.util.ArrayList;

public class AddFood_Adapter extends BaseAdapter
{
    private final ArrayList<CurrentUser.Meal> mealsArray;
    private final Context mContext;

    public AddFood_Adapter(Context context, ArrayList<CurrentUser.Meal> meals)
    {
        mealsArray = meals;
        mContext = context;
    }

    @Override
    public int getCount()
    {
        if(mealsArray != null)
        {
            return mealsArray.size();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public Object getItem(int position)
    {
        if(mealsArray != null && position <= mealsArray.size())
        {
            return mealsArray.get(position);
        }
        else
        {
            return null;
        }
    }

    @Override
    public long getItemId(int position)
    {
        if(mealsArray != null && position <= mealsArray.size())
        {
            int _ID = 0x1010;
            return _ID + position;
        }
        else
        {
            return 0;
        }
    }

    //creating a view holder to make this process faster
    public static class ViewHolder
    {
        final TextView foodName_txt;
        final TextView brandName_txt;
        final TextView calories_txt;

        ViewHolder(View layout)
        {
            foodName_txt = (TextView)layout.findViewById(R.id.cell_foodName_txt);
            brandName_txt = (TextView)layout.findViewById(R.id.cell_brandName_txt);
            calories_txt = (TextView)layout.findViewById(R.id.cell_calories_txt);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder postHolder;

        //return a child view for each item at its position
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_addfood, parent, false);
            postHolder = new ViewHolder(convertView);
            convertView.setTag(new ViewHolder(convertView));
        }
        else
        {
            postHolder = (ViewHolder) convertView.getTag();
        }

        CurrentUser.Meal currentMeal = (CurrentUser.Meal) getItem(position);

        if(currentMeal != null)
        {
            postHolder.foodName_txt.setText(currentMeal.getName());
            postHolder.brandName_txt.setText(currentMeal.getBrandname());
            postHolder.calories_txt.setText(String.format("%s", currentMeal.getCalories()));
            return convertView;
        }
        return null;
    }
}
