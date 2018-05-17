package com.example.kendricklewis.keenfit.Downloader;

import android.os.AsyncTask;

import com.example.kendricklewis.keenfit.Activities.AddActivity;
import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.Fragments.Summary_Fragment;
import com.example.kendricklewis.keenfit.R;

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

//public class FindNutritionTask extends AsyncTask<String, Void, ArrayList<CurrentUser.Meal>>
//{
//    @Override
//    protected ArrayList<CurrentUser.Meal> doInBackground(String... strings)
//    {
//        String nutritionResults_JsonString = null;
//
//        try
//        {
//            URL url = new URL("https://api.nutritionix.com/v1_1/search/" + strings[0] +"?results=0:30&fields=item_name,brand_name,item_id,nf_calories,item_description,nf_total_fat,nf_saturated_fat,,nf_cholesterol,nf_sodium,nf_total_carbohydrate,nf_dietary_fiber,nf_sugars,nf_protein,nf_potassium&appId=b3aa35a2&appKey=78eca08668db866e38e0f0beec9c9692");
//
//            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//
//            connection.connect();
//
//            InputStream is = connection.getInputStream();
//
//            String data = IOUtils.toString(is, "UTF-8");
//
//            is.close();
//
//            connection.disconnect();
//
//            nutritionResults_JsonString= data;
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        if(nutritionResults_JsonString != null)
//        {
//            try {
//
//                ArrayList<CurrentUser.Meal> entryArray = new ArrayList<>();
//
//                //turning my dataString into a jsonObject
//                JSONObject outerObj = new JSONObject(nutritionResults_JsonString);
//
//                //Grabing the array of items
////                JSONObject dataObj = outerObj.getJSONObject();
//                JSONArray entryJsonArray = outerObj.getJSONArray("hits");
//
//                //looping through my data for info
//                for (int i = 0; i < entryJsonArray.length(); ++i)
//                {
//                    JSONObject obj = entryJsonArray.getJSONObject(i);
//
//                    JSONObject postObj = obj.getJSONObject("fields");
//
//                    String id = postObj.getString("item_id");
//                    String name = postObj.getString("item_name");
//                    String brandname = postObj.getString("brand_name");
//                    String dateAdded = getCurrentDate();
//                    int servings = postObj.getInt("nf_serving_size_qty");
//                    double calories =      postObj.getDouble("nf_calories");
//                    double carbs =         postObj.getDouble("nf_total_carbohydrate");
//                    double cholesterol =   postObj.getDouble("nf_cholesterol");
//                    double dietary =       postObj.getDouble("nf_dietary_fiber");
//                    double fat =           postObj.getDouble("nf_total_fat");
//                    double protein =       postObj.getDouble("nf_protein");
//                    double saturatedfat =  postObj.getDouble("nf_saturated_fat");
//                    double sodium =        postObj.getDouble("nf_sodium");
//                    double sugars =        postObj.getDouble("nf_sugars");
//
//
//                    entryArray.add(new CurrentUser.Meal(id, name, brandname, dateAdded, servings, calories, carbs,
//                            cholesterol, dietary, fat, protein, saturatedfat, sodium, sugars));
//                }
//
//                //sending information
//                return entryArray;
//            }
//            catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(ArrayList<CurrentUser.Meal> meals)
//    {
//        super.onPostExecute(meals);
//        if(!meals.isEmpty())
//        {
//
//        }
//    }
//
//    public String getCurrentDate()
//    {
//        String currentDate;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        Date today = new Date();
//
//        currentDate = dateFormat.format(today);
//
//
//        return currentDate;
//    }
//}
