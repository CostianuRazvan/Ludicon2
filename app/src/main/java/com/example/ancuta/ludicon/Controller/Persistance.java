package com.example.ancuta.ludicon.Controller;

import android.app.Activity;
import android.content.SharedPreferences;

import org.json.JSONObject;

/**
 * Created by ancuta on 7/12/2017.
 */

public class Persistance {
    private static Persistance instance = null;

    protected Persistance() {
    }

    public static Persistance getInstance() {
        if(instance == null) {
            instance = new Persistance();
        }
        return instance;
    }
    private final String userDetailsString = "UserDetails";

    public void setUserInfo(Activity activity, JSONObject json){
        SharedPreferences.Editor editor = activity.getSharedPreferences(userDetailsString, 0).edit();
        editor.putString(userDetailsString, json.toString());
        editor.commit();
    }

    public String getUserInfo(Activity activity){
        String json=null;
        SharedPreferences sharedPreferences = activity.getSharedPreferences(userDetailsString, 0);
        json = sharedPreferences.getString(userDetailsString, "0");
        return json;
    }


}
