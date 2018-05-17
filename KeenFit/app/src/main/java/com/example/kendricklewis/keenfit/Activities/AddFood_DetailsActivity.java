package com.example.kendricklewis.keenfit.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.Fragments.AddList_Fragment;
import com.example.kendricklewis.keenfit.HomeActivity;
import com.example.kendricklewis.keenfit.R;

import es.dmoral.toasty.Toasty;

import static com.example.kendricklewis.keenfit.Activities.AddActivity.*;
import static com.example.kendricklewis.keenfit.Fragments.AddList_Fragment.selectedItem;

public class AddFood_DetailsActivity extends Activity
{

    int servingCount = 0;

    TextView foodNameTextView;
    TextView brandNameTextView;
    TextView calsTextView;
    TextView carbsTextView;
    TextView cholesTextView;
    TextView dietTextView;
    TextView totalFatTextView;
    TextView proteinTextView;
    TextView saturatedTextView;
    TextView SodiumTextView;
    TextView SugarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food__details);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.85), (int)(height*.85));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        foodNameTextView =  (TextView) findViewById(R.id.d_foodName_txt);
        brandNameTextView = (TextView) findViewById(R.id.d_brandName_txt);
        calsTextView =      (TextView) findViewById(R.id.d_Calories_txt);
        carbsTextView =     (TextView) findViewById(R.id.d_Carb_txt);
        cholesTextView =    (TextView) findViewById(R.id.d_cholest_txt);
        dietTextView =      (TextView) findViewById(R.id.d_dietary_txt);
        totalFatTextView =       (TextView) findViewById(R.id.d_totalFat_txt);
        proteinTextView =   (TextView) findViewById(R.id.d_protein_txt);
        saturatedTextView = (TextView) findViewById(R.id.d_saturatedFat_txt);
        SodiumTextView =    (TextView) findViewById(R.id.d_sodium_txt);
        SugarTextView =     (TextView) findViewById(R.id.d_sugars_txt);

        servingCount = 0;

        findViewById(R.id.d_addServings_Btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                servingCount++;
                Toasty.info(getApplicationContext(), "ADD").show();
                changeInfo();

            }
        });

        findViewById(R.id.d_subtractServings_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toasty.info(getApplicationContext(), "SUBTRACT").show();
                servingCount--;
                changeInfo();
            }
        });

        findViewById(R.id.d_addFood_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getApplicationContext(), "ADD TO LIST").show();
            }
        });

        changeInfo();

    }

    private void changeInfo()
    {
        CurrentUser.Meal selectedMeal = allMeals.get(selectedItem);

        foodNameTextView.setText(selectedMeal.getName());
        brandNameTextView.setText(selectedMeal.getBrandname());
        calsTextView.setText(selectedMeal.getCalories() + "");
        carbsTextView.setText(selectedMeal.getCarbs() + "");
        cholesTextView.setText(selectedMeal.getCholesterol() + "");
        dietTextView.setText(selectedMeal.getDietary() + "");
        totalFatTextView.setText(selectedMeal.getFat() + "");
        proteinTextView.setText(selectedMeal.getProtein() + "");
        saturatedTextView.setText(selectedMeal.getSaturatedfat() + "");
        SodiumTextView.setText(selectedMeal.getSodium() + "");
        SugarTextView.setText(selectedMeal.getSugars() + "");
    }
}
