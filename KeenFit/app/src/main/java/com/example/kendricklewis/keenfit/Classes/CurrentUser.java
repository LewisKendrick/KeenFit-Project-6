package com.example.kendricklewis.keenfit.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CurrentUser
{
    private String name;
    private String g_name;
    private int id_picture;
    private double weight;
    private HashMap<String, Double> goals;
    private double current_Calories;
    private double average_daily;
    private double average_weekly;
    private double average_byWeekly;

    public CurrentUser()
    {

    }

    public CurrentUser(String name, String g_name, int id_picture, double weight, HashMap<String, Double> goals, double current_Calories, double average_daily, double average_weekly, double average_byWeekly)
    {
        this.name = name;
        this.g_name = g_name;
        this.id_picture = id_picture;
        this.weight = weight;
        this.goals = goals;
        this.current_Calories = current_Calories;
        this.average_daily = average_daily;
        this.average_weekly = average_weekly;
        this.average_byWeekly = average_byWeekly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
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

    public HashMap<String, Double> getGoals() {
        return goals;
    }

    public void setGoals(HashMap<String, Double> goals) {
        this.goals = goals;
    }

    public double getCurrent_Calories() {
        return current_Calories;
    }

    public void setCurrent_Calories(double current_Calories) {
        this.current_Calories = current_Calories;
    }

    public double getAverage_daily() {
        return average_daily;
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

    public double getAverage_byWeekly() {
        return average_byWeekly;
    }

    public void setAverage_byWeekly(double average_byWeekly) {
        this.average_byWeekly = average_byWeekly;
    }

    public class Goal
    {

    }

    public class Meal
    {

    }

}
