package com.example.ancuta.ludicon.Controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ancuta.ludicon.Activities.AskPrefferencies;
import com.example.ancuta.ludicon.Activities.LoginActivity;
import com.example.ancuta.ludicon.Activities.MainActivity;
import com.example.ancuta.ludicon.Sport;
import com.example.ancuta.ludicon.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ancuta on 7/12/2017.
 */

public class HTTPResponseController {

    private static HTTPResponseController instance = null;

    protected HTTPResponseController() {
    }

    public static HTTPResponseController getInstance() {
        if(instance == null) {
            instance = new HTTPResponseController();
        }
        return instance;
    }

    public JSONObject json=null;
    Activity activity;

    private Response.Listener<JSONObject>  createRequestSuccessListener(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(activity.getLocalClassName().toString().equals("Activities.LoginActivity")) {
                    try {
                        json = jsonObject;
                        ArrayList<String> listOfSports =new ArrayList<String>();
                        for(int i=0;i<jsonObject.getJSONObject("user").getJSONArray("sports").length();i++){
                            listOfSports.add(jsonObject.getJSONObject("user").getJSONArray("sports").get(i).toString());
                        }
                        ArrayList<Sport> sports =new ArrayList<Sport>();
                        for(int i=0;i<listOfSports.size();i++){
                            Sport sport=new Sport(listOfSports.get(i));
                            sports.add(sport);
                        }
                        User user=new User(jsonObject.getString("authKey"),jsonObject.getJSONObject("user").getString("id"),
                                jsonObject.getJSONObject("user").getString("firstName"),jsonObject.getJSONObject("user").getString("gender"),
                                jsonObject.getJSONObject("user").getString("facebookId"),jsonObject.getJSONObject("user").getString("lastName"),
                                jsonObject.getJSONObject("user").getInt("ludicoins"),jsonObject.getJSONObject("user").getInt("level"),
                                jsonObject.getJSONObject("user").getString("profileImage"),jsonObject.getJSONObject("user").getString("range"),
                                sports);
                        Persistance.getInstance().setUserInfo(activity, user);


                        if(user.range.equals("null")){
                            Intent intent = new Intent(activity, AskPrefferencies.class);
                            activity.startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);

                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                else if(activity.getLocalClassName().toString().equals("Activities.RegisterActivity")){
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);

                }

            }
        };
    }
    public String trimMessage(String json, String key){
        String trimmedString = null;
        if(activity.getLocalClassName().toString().equals("Activities.LoginActivity"))
        LoginActivity.progressBar.setAlpha(0f);

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


    private  Response.ErrorListener createRequestErrorListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = error.getMessage();
                json = trimMessage(json, "error");
                if(json != null) displayMessage(json);

                //Additional cases
            }

        };
    }

    public void setActivity(Activity activity){
        this.activity=activity;
    }

    public void displayMessage(String toastString){
        Toast.makeText(activity, toastString, Toast.LENGTH_LONG).show();
    }

    public JSONObject returnResponse(HashMap<String,String> params,HashMap<String,String> headers, Activity activity,String url){
        setActivity(activity);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params,headers,this.createRequestSuccessListener(), this.createRequestErrorListener());
        requestQueue.add(jsObjRequest);

        return json;
    }
}
