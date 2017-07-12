package com.example.ancuta.ludicon.Controller;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ancuta.ludicon.Activities.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
                json=jsonObject;
                Persistance.getInstance().setUserInfo(activity,jsonObject);
            }
        };
    }
    public String trimMessage(String json, String key){
        String trimmedString = null;

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

    public JSONObject returnResponse(HashMap<String,String> params, Activity activity,String url){
        setActivity(activity);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params,this.createRequestSuccessListener(), this.createRequestErrorListener());
        requestQueue.add(jsObjRequest);

        return json;
    }
}
