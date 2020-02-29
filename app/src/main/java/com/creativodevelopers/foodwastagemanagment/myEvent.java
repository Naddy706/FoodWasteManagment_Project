package com.creativodevelopers.foodwastagemanagment;

public class myEvent {

    String EventKey,FoodKey,UserKey;

    public myEvent(String eventKey, String foodKey, String userKey) {
        EventKey = eventKey;
        FoodKey = foodKey;
        UserKey = userKey;
    }
    public myEvent() {


    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {

        EventKey = eventKey;
    }

    public String getFoodKey() {

        return FoodKey;
    }

    public void setFoodKey(String foodKey) {
        FoodKey = foodKey;
    }

    public String getUserKey() {

        return UserKey;
    }

    public void setUserKey(String userKey) {

        UserKey = userKey;
    }
}
