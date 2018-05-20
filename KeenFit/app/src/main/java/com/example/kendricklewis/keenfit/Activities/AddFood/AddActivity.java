package com.example.kendricklewis.keenfit.Activities.AddFood;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kendricklewis.keenfit.Classes.CurrentUser;
//import com.example.kendricklewis.keenfit.Downloader.FindNutritionTask;
import com.example.kendricklewis.keenfit.Fragments.FoodList_Fragment;
import com.example.kendricklewis.keenfit.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class AddActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener
{
    MaterialSearchBar searchBar;
    ProgressBar loadingImage;

    private DrawerLayout drawer;
    public static ArrayList<CurrentUser.Meal> allMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        loadingImage = (ProgressBar) findViewById(R.id.progressBar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        //searchBar.inflateMenu(R.menu.activity_main_drawer);
        searchBar.setText("apples");

        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            { }

            @Override
            public void afterTextChanged(Editable editable)
            { }

        });

        FindNutritionTask downloadNutritionList = new FindNutritionTask();
        downloadNutritionList.execute("apples");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.nav_addfood:
                {
                    Toasty.info(getApplicationContext(), "Add Food").show();
                    break;
                }
            case  R.id.nav_mealhistory:
                {
                    break;
                }
            case R.id.nav_goals:
                {
                    break;
                }
            case R.id.nav_friends:
                {
                    break;
                }
            case R.id.nav_entries:
                {
                    break;
                }

            case R.id.nav_aboutus:
                {
                    break;
                }
            case R.id.nav_signout:
                {
                    break;
                }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
    }

    @Override
    public void onSearchConfirmed(CharSequence text) //search button clicked
    {

        //TODO: Search for food info out of nutritionX
        if(isConnected(getApplicationContext()))
        {
            //downloading all data
            FindNutritionTask downloadNutritionList = new FindNutritionTask();
            downloadNutritionList.execute(text.toString());
        }
        searchBar.setPlaceHolder(text.toString());
        searchBar.disableSearch();

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
            {
                drawer.openDrawer(Gravity.LEFT);
                break;
            }
            case MaterialSearchBar.BUTTON_BACK: {
                searchBar.disableSearch();
                break;
            }
        }
    }

    public void entryDataGathered(boolean isDone)
    {
        if(isDone)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.a_FoodList_frame,
                    FoodList_Fragment.newInstance()).commit();
        }
    }

    private static boolean isConnected(Context _context)
    {
        //checking for connection
        ConnectivityManager connected = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connected != null)
        {
            NetworkInfo networkInfo = connected.getActiveNetworkInfo();

            if(networkInfo != null)
            {
                if(networkInfo.isConnected())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public class FindNutritionTask extends AsyncTask<String, Void, ArrayList<CurrentUser.Meal>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingImage.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<CurrentUser.Meal> doInBackground(String... strings)
        {
            String nutritionResults_JsonString = null;

            try
            {
                URL url = new URL("https://api.nutritionix.com/v1_1/search/" + strings[0] +"?results=0:30&fields=item_name,brand_name,item_id,nf_calories,item_description,nf_total_fat,nf_saturated_fat,,nf_cholesterol,nf_sodium,nf_total_carbohydrate,nf_dietary_fiber,nf_sugars,nf_protein,nf_potassium&appId=b3aa35a2&appKey=78eca08668db866e38e0f0beec9c9692");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                connection.connect();

                InputStream is = connection.getInputStream();

                String data = IOUtils.toString(is, "UTF-8");

                is.close();

                connection.disconnect();

                nutritionResults_JsonString= data;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            if(nutritionResults_JsonString != null)
            {
                try {

                    allMeals = new ArrayList<>();

                    //turning my dataString into a jsonObject
                    JSONObject outerObj = new JSONObject(nutritionResults_JsonString);

                    //Grabing the array of items
//                JSONObject dataObj = outerObj.getJSONObject();
                    JSONArray entryJsonArray = outerObj.getJSONArray("hits");

                    //looping through my data for info
                    for (int i = 0; i < entryJsonArray.length(); ++i)
                    {
                        JSONObject obj = entryJsonArray.getJSONObject(i);

                        JSONObject postObj = obj.getJSONObject("fields");

                        String id = postObj.getString("item_id");
                        String name = postObj.getString("item_name");
                        String brandname = postObj.getString("brand_name");
                        String dateAdded = getCurrentDate();
                        int servings = postObj.getInt("nf_serving_size_qty");

                        String test_carbs  = "";
                        String test_calories  = "";
                        String test_cholesterol  = "";
                        String test_dietary   = "";
                        String test_fat   = "";
                        String test_protein   = "";
                        String test_saturatedfat   = "";
                        String test_sodium   = "";
                        String test_sugars   = "";

                        test_calories =      postObj.get("nf_calories").toString();
                        test_carbs =         postObj.get("nf_total_carbohydrate").toString();
                        test_cholesterol =   postObj.get("nf_cholesterol").toString();
                        test_dietary =       postObj.get("nf_dietary_fiber").toString();
                        test_fat =           postObj.get("nf_total_fat").toString();
                        test_protein =       postObj.get("nf_protein").toString();
                        test_saturatedfat =  postObj.get("nf_saturated_fat").toString();
                        test_sodium =        postObj.get("nf_sodium").toString();
                        test_sugars =        postObj.get("nf_sugars").toString();

                        double carbs = 0.0;
                        double calories = 0.0;
                        double cholesterol = 0.0;
                        double dietary  = 0.0;
                        double fat  = 0.0;
                        double protein  = 0.0;
                        double saturatedfat  = 0.0;
                        double sodium  = 0.0;
                        double sugars  = 0.0;


                        if(test_calories != "null")
                        {
                            calories = getValueOrDefault(postObj.getDouble("nf_calories"));
                        }

                        if(test_carbs != "null")
                        {
                            carbs = getValueOrDefault(postObj.getDouble("nf_total_carbohydrate"));
                        }

                        if(test_cholesterol != "null")
                        {
                            cholesterol =   getValueOrDefault(postObj.getDouble("nf_cholesterol"));
                        }
                        if(test_dietary != "null")
                        {
                            dietary =       getValueOrDefault(postObj.getDouble("nf_dietary_fiber"));
                        }
                        if(test_fat != "null")
                        {
                            fat =           getValueOrDefault(postObj.getDouble("nf_total_fat"));
                        }
                        if(test_protein != "null")
                        {
                            protein =       getValueOrDefault(postObj.getDouble("nf_protein"));
                        }
                        if(test_saturatedfat != "null")
                        {
                            saturatedfat =  getValueOrDefault(postObj.getDouble("nf_saturated_fat"));
                        }
                        if(test_sodium != "null")
                        {
                            sodium =        getValueOrDefault(postObj.getDouble("nf_sodium"));
                        }
                        if(test_sugars != "null")
                        {
                            sugars =        getValueOrDefault(postObj.getDouble("nf_sugars"));
                        }


                        allMeals.add(new CurrentUser.Meal(id, name, brandname, dateAdded, servings, calories, carbs,
                                cholesterol, dietary, fat, protein, saturatedfat, sodium, sugars));
                    }

                    //sending information
                    return allMeals;
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

      @Override
        protected void onPostExecute(ArrayList<CurrentUser.Meal> meals)
        {
            if(!allMeals.isEmpty())
            {
               entryDataGathered(true);
               loadingImage.setVisibility(View.INVISIBLE);
            }
            super.onPostExecute(allMeals);
        }

        public String getCurrentDate()
        {
            String currentDate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date today = new Date();

            currentDate = dateFormat.format(today);


            return currentDate;
        }

        public double getValueOrDefault(Double value)
        {
          if(value != null)
          {
              return value;
          }
          else
          {
              return 0.0;
          }
        }
    }
}
