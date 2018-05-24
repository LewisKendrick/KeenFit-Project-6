package com.example.kendricklewis.keenfit.Classes;

import android.text.format.DateFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class CurrentUser implements Serializable
{
    private String name;
    private int id_picture;
    private double weight;
    private Goal goals;
    private double current_Calories;
    private double average_daily;
    private double average_weekly;
    private double average_biWeekly;
    private ArrayList<Meal> meals;

    public CurrentUser()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_picture() {
        return id_picture;
    }

    public void setId_picture(int id_picture) {
        this.id_picture = id_picture;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Goal getGoals() {
        return goals;
    }

    public void setGoals(Goal goals) {
        this.goals = goals;
    }

    public double getCurrent_Calories()
    {
        double totalCal = 0;
        double totalMeals = 0;

        Calendar today = Calendar.getInstance();
        String selectedDateStr = DateFormat.format("yyyy/MM/dd", today).toString();

        for (CurrentUser.Meal singleMeal : meals)
        {
            if(singleMeal.getDateAdded().equals(selectedDateStr))
            {
                totalCal += (singleMeal.getCalories() * singleMeal.getServings());
                totalMeals = totalMeals + 1;
            }
        }

        current_Calories =  totalCal;

        if(current_Calories > 0)
        {
            return current_Calories;
        }
        else
        {
            return 0;
        }
    }

    public void setCurrent_Calories(double current_Calories) {
        this.current_Calories = current_Calories;
    }

    public double getAverage_daily()
    {
        double totalCal = 0;
        double totalMeals = 0;

        Calendar today = Calendar.getInstance();
        String selectedDateStr = DateFormat.format("yyyy/MM/dd", today).toString();

        for (CurrentUser.Meal singleMeal : meals)
        {
            if(singleMeal.getDateAdded().equals(selectedDateStr))
            {
                totalCal += (singleMeal.getCalories() * singleMeal.getServings());
                totalMeals = totalMeals + 1;
            }
        }

        average_daily =  totalCal / totalMeals;

        if(current_Calories > 0)
        {
            return average_daily;
        }
        else
        {
            return 0;
        }
    }

    public void setAverage_daily(double average_daily) {
        this.average_daily = average_daily;
    }

    public double getAverage_weekly() {
        return average_weekly;
    }

    public void setAverage_weekly(double average_weekly) {
        this.average_weekly = average_weekly;
    }

    public double getAverage_biWeekly() {
        return average_biWeekly;
    }

    public void setAverage_biWeekly(double average_biWeekly) {
        this.average_biWeekly = average_biWeekly;
    }


    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public int getMealsToday()
    {
        int totalMeals = 0;

        Calendar today = Calendar.getInstance();
        String selectedDateStr = DateFormat.format("yyyy/MM/dd", today).toString();

        for (CurrentUser.Meal singleMeal : meals)
        {
            if(singleMeal.getDateAdded().equals(selectedDateStr))
            {
                totalMeals = totalMeals + 1;
            }
        }

        return totalMeals;
    }

    /*
    TODO: THIS IS MY GOALS CLASS
    Goals Class

     */

    public static class Goal implements Serializable
    {
        private double total_calories;
        private double total_carbs;
        private double total_cholesterol;
        private double total_dietary;
        private double total_fat;
        private double total_protein;
        private double total_saturatedfat;
        private double total_sodium;
        private double total_sugars;

        public Goal()
        {

        }

        public double getTotal_calories() {
            return total_calories;
        }

        public void setTotal_calories(double total_calories) {
            this.total_calories = total_calories;
        }

        public double getTotal_carbs() {
            return total_carbs;
        }

        public void setTotal_carbs(double total_carbs) {
            this.total_carbs = total_carbs;
        }

        public double getTotal_cholesterol() {
            return total_cholesterol;
        }

        public void setTotal_cholesterol(double total_cholesterol) {
            this.total_cholesterol = total_cholesterol;
        }

        public double getTotal_dietary() {
            return total_dietary;
        }

        public void setTotal_dietary(double total_dietary) {
            this.total_dietary = total_dietary;
        }

        public double getTotal_fat() {
            return total_fat;
        }

        public void setTotal_fat(double total_fat) {
            this.total_fat = total_fat;
        }

        public double getTotal_protein() {
            return total_protein;
        }

        public void setTotal_protein(double total_protein) {
            this.total_protein = total_protein;
        }

        public double getTotal_saturatedfat() {
            return total_saturatedfat;
        }

        public void setTotal_saturatedfat(double total_saturatedfat) {
            this.total_saturatedfat = total_saturatedfat;
        }

        public double getTotal_sodium() {
            return total_sodium;
        }

        public void setTotal_sodium(double total_sodium) {
            this.total_sodium = total_sodium;
        }

        public double getTotal_sugars() {
            return total_sugars;
        }

        public void setTotal_sugars(double total_sugars) {
            this.total_sugars = total_sugars;
        }
    }

        /*
    TODO: THIS IS MY MEALS CLASS
    MEALS Class

     */

    public static class Meal implements Serializable
    {
        private String id;
        private String name;
        private String brandname;
        private String dateAdded;
        private int servings;
        private double calories;
        private double carbs;
        private double cholesterol;
        private double dietary;
        private double fat;
        private double protein;
        private double saturatedfat;
        private double sodium;
        private double sugars;

        public Meal(String id, String name, String brandname, String dateAdded, int servings, double calories, double carbs, double cholesterol, double dietary, double fat, double protein, double saturatedfat, double sodium, double sugars) {
            this.id = id;
            this.name = name;
            this.brandname = brandname;
            this.dateAdded = dateAdded;
            this.servings = servings;
            this.calories = calories;
            this.carbs = carbs;
            this.cholesterol = cholesterol;
            this.dietary = dietary;
            this.fat = fat;
            this.protein = protein;
            this.saturatedfat = saturatedfat;
            this.sodium = sodium;
            this.sugars = sugars;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getBrandname() {
            return brandname;
        }

        public String getDateAdded() {
            return dateAdded;
        }

        public int getServings() {
            return servings;
        }

        public double getCalories() {
            return calories;
        }

        public double getCarbs() {
            return carbs;
        }

        public double getCholesterol() {
            return cholesterol;
        }

        public double getDietary() {
            return dietary;
        }

        public double getFat() {
            return fat;
        }

        public double getProtein() {
            return protein;
        }

        public double getSaturatedfat() {
            return saturatedfat;
        }

        public double getSodium() {
            return sodium;
        }

        public double getSugars() {
            return sugars;
        }

        public void setServings(int servings) {
            this.servings = servings;
        }
    }

}
