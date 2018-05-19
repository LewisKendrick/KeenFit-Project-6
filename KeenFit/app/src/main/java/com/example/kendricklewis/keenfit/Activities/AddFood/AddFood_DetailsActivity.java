package com.example.kendricklewis.keenfit.Activities.AddFood;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.kendricklewis.keenfit.Classes.CurrentUser;
import com.example.kendricklewis.keenfit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.kendricklewis.keenfit.Activities.AddFood.AddActivity.*;
import static com.example.kendricklewis.keenfit.Fragments.AddList_Fragment.selectedItem;
import static com.example.kendricklewis.keenfit.HomeActivity.userID;

public class AddFood_DetailsActivity extends Activity
{

    private DatabaseReference mDatabase;
    CurrentUser.Meal selectedMeal;

    int servingCount;

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
    TextView servingsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food__details);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.85), (int)(height*.9));

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
        totalFatTextView =  (TextView) findViewById(R.id.d_totalFat_txt);
        proteinTextView =   (TextView) findViewById(R.id.d_protein_txt);
        saturatedTextView = (TextView) findViewById(R.id.d_saturatedFat_txt);
        SodiumTextView =    (TextView) findViewById(R.id.d_sodium_txt);
        SugarTextView =     (TextView) findViewById(R.id.d_sugars_txt);
        servingsTextView =  (TextView) findViewById(R.id.d_servings_txt);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        servingCount = 1;
        changeInfo();

        findViewById(R.id.d_addServings_Btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (servingCount >= 1)
                {
                    servingCount++;
                    changeInfo();
                }
            }
        });

        findViewById(R.id.d_subtractServings_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (servingCount > 1)
                {
                    servingCount--;
                    changeInfo();
                }
            }
        });

        findViewById(R.id.d_addFood_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //finds the place to store the information
                DatabaseReference mealsReference = mDatabase.child("Meals").child(userID);
                selectedMeal.setServings(servingCount);

                //places my class into firebase  .push(adds a new autoId child) .setvalue changes values
                mealsReference.push().setValue(selectedMeal);

                finish();
            }
        });
    }

    private void changeInfo()
    {
        selectedMeal = allMeals.get(selectedItem);

        foodNameTextView.setText(selectedMeal.getName());
        brandNameTextView.setText(selectedMeal.getBrandname());
        calsTextView.setText(String.format("%s", selectedMeal.getCalories()));
        carbsTextView.setText(String.format("%s", selectedMeal.getCarbs()));
        cholesTextView.setText(String.format("%s", selectedMeal.getCholesterol()));
        dietTextView.setText(String.format("%s", selectedMeal.getDietary()));
        totalFatTextView.setText(String.format("%s", selectedMeal.getFat()));
        proteinTextView.setText(String.format("%s", selectedMeal.getProtein()));
        saturatedTextView.setText(String.format("%s", selectedMeal.getSaturatedfat()));
        SodiumTextView.setText(String.format("%s", selectedMeal.getSodium()));
        SugarTextView.setText(String.format("%s", selectedMeal.getSugars()));
        servingsTextView.setText(String.format("%d", servingCount));

    }
}
