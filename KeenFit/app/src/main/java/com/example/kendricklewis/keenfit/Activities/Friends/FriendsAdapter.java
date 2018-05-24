package com.example.kendricklewis.keenfit.Activities.Friends;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kendricklewis.keenfit.R;

import co.ceryle.fitgridview.FitGridAdapter;

import static com.example.kendricklewis.keenfit.Activities.Friends.FriendsActivity.allFriends;
import static com.example.kendricklewis.keenfit.HomeActivity.profilePic_Array;

public class FriendsAdapter extends FitGridAdapter
{
    private Context context;

    FriendsAdapter(Context context)
    {
        super(context, R.layout.cell_grid_item);
        this.context = context;
    }

    @Override
    public void onBindView(int position, View view)
    {
        ImageView profile_imageView =(ImageView) view.findViewById(R.id.cell_profilePic_image);
        TextView  profile_name = (TextView) view.findViewById(R.id.cell_name_txt);
        TextView  profile_entryCount = (TextView) view.findViewById(R.id.cell_EntryCount_txt);
        TextView  profile_dailyAvg = (TextView) view.findViewById(R.id.cell_dailyAverage_txt);
        TextView  profile_todayAvg = (TextView) view.findViewById(R.id.cell_todayAvg_txt);


        int profile = allFriends.get(position).getProfilePic();
        profile_imageView.setImageBitmap(profilePic_Array.get(profile));
        profile_name.setText(allFriends.get(position).getFriendName());
        profile_entryCount.setText(allFriends.get(position).getTotalMeals());
        profile_dailyAvg.setText(allFriends.get(position).getDailyAvg());
        profile_todayAvg.setText(allFriends.get(position).getTodaysCals());

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }
}
