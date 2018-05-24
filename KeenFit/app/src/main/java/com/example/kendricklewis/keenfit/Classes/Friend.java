package com.example.kendricklewis.keenfit.Classes;

public class Friend
{
    String friend_id;
    String friendName;
    int profilePic;
    int totalMeals;
    double dailyAvg;
    double todaysCals;

    public Friend(String friend_id, String friendName, int profilePic, int totalMeals, double dailyAvg, double todaysCals) {
        this.friend_id = friend_id;
        this.friendName = friendName;
        this.profilePic = profilePic;
        this.totalMeals = totalMeals;
        this.dailyAvg = dailyAvg;
        this.todaysCals = todaysCals;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public int getTotalMeals() {
        return totalMeals;
    }

    public void setTotalMeals(int totalMeals) {
        this.totalMeals = totalMeals;
    }

    public String getDailyAvg() {
        return String.valueOf(dailyAvg);
    }

    public void setDailyAvg(double dailyAvg) {
        this.dailyAvg = dailyAvg;
    }

    public String getTodaysCals() {
        return String.valueOf(todaysCals);
    }

    public void setTodaysCals(double todaysCals) {
        this.todaysCals = todaysCals;
    }
}
