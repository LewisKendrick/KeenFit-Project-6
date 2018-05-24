package com.example.kendricklewis.keenfit.Activities.Friends;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kendricklewis.keenfit.Classes.Friend;
import com.example.kendricklewis.keenfit.HomeActivity;
import com.example.kendricklewis.keenfit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import co.ceryle.fitgridview.FitGridView;

public class FriendsActivity extends AppCompatActivity
{
    private FitGridView gridView;
    public static ArrayList<Friend> allFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        /* USER REFERENCE */
        DatabaseReference userRef = database.getReference().child("Users");

        DatabaseReference mealsRef = database.getReference().child("Meals");

        userRef.addValueEventListener(new ValueEventListener() //This method will fire whenever my database changes
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                FriendInfoTask infoTask = new FriendInfoTask();
                infoTask.execute(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) { Log.w("Error: ", "Failed to read value.", error.toException()); }
        });


        mealsRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onCancelled(DatabaseError error) { Log.w("Error: ", "Failed to read value.", error.toException()); }
        });

    }

    public void setGridView()
    {
        gridView = (FitGridView) findViewById(R.id.f_List_Frame);
        gridView.setFitGridAdapter(new FriendsAdapter(this));
    }

    public class FriendInfoTask extends AsyncTask<DataSnapshot, Void, ArrayList<Friend>>
    {
        @Override
        protected ArrayList<Friend> doInBackground(DataSnapshot... dataSnapshots)
        {
            DataSnapshot dataSnapshot = dataSnapshots[0];

            for (DataSnapshot aFriend : dataSnapshot.getChildren())
            {
                String  id = aFriend.getKey();
                String  name =    aFriend.child("name").getValue(String.class);
                Integer id_picture = aFriend.child("id_picture").getValue(Integer.class);
                Double  currentCals = aFriend.child("current_Calories").getValue(Double.class);
                Double  averageDaily =    dataSnapshot.child("average_daily").getValue(Double.class);
                //TODO: USE THIS TO GRAB THE REST OF THE DATA OUT OF USERS
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Friend> friends)
        {
            super.onPostExecute(friends);
            setGridView();
        }
    }

}
