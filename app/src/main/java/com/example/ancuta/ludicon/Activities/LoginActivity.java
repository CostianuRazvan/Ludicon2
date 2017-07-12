package com.example.ancuta.ludicon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ancuta.ludicon.Controller.CustomRequest;
import com.example.ancuta.ludicon.Controller.HTTPResponseController;
import com.example.ancuta.ludicon.DatabaseConnection;
import com.example.ancuta.ludicon.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ancuta on 7/11/2017.
 */

public class LoginActivity extends Activity {
    ImageButton backButton;
    TextView forgotPasswordText;
    Button loginButton;

    private static String TAG = LoginActivity.class.getSimpleName();



    /**
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest() {
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);
        backButton=(ImageButton) findViewById(R.id.backButton);
        forgotPasswordText=(TextView) findViewById(R.id.forgotPasswordText);
        TextView titleText=(TextView) findViewById(R.id.titleText);
        titleText.setText("Login");

        loginButton=(Button) findViewById(R.id.loginButton);
        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
            }
        });
        forgotPasswordText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                HashMap<String,String> params=new HashMap<String, String>();
                params.put("email","test@test.com");
                params.put("password","ceva");
                HTTPResponseController.getInstance().returnResponse(params,LoginActivity.this,"http://207.154.236.13/api/login");
                System.out.println(HTTPResponseController.getInstance().json);

            }
        });





    }

}
