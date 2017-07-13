package com.example.ancuta.ludicon.Controller;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.ancuta.ludicon.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

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

    public void setUserInfo(Activity activity, User user){

        SharedPreferences.Editor editor = activity.getSharedPreferences(userDetailsString, 0).edit();
        Gson gson = new Gson();
        editor.putString(userDetailsString, gson.toJson(user));
        editor.commit();
    }

    public User getUserInfo(Activity activity){
        String json=null;
        SharedPreferences sharedPreferences = activity.getSharedPreferences(userDetailsString, 0);
        json = sharedPreferences.getString(userDetailsString, "0");
        Gson gson = new Gson();
        User user;
        System.out.println(json);
        if(json.equals("0")){
            user=new User();
        }
        else {
            user = gson.fromJson(json, User.class);
        }
        return user;
    }


}
