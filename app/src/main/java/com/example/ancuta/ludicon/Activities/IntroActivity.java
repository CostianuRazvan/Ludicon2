package com.example.ancuta.ludicon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ancuta.ludicon.Controller.HTTPResponseController;
import com.example.ancuta.ludicon.Controller.Persistance;
import com.example.ancuta.ludicon.PasswordEncryptor;
import com.example.ancuta.ludicon.R;
import com.example.ancuta.ludicon.User;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ancuta on 7/10/2017.
 */

public class IntroActivity extends Activity {
    int i=0;
    Button facebookButton;
    Button loginButton;
    Button registerButton;
    TextView infoTextView;
    ImageView profileImage;
    Boolean go=false;

    public void goToActivity(){
        if(!go) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("email", Persistance.getInstance().getUserInfo(IntroActivity.this).email);
            params.put("password", Persistance.getInstance().getUserInfo(IntroActivity.this).password);
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("apiKey", "b0a83e90-4ee7-49b7-9200-fdc5af8c2d33");
            HTTPResponseController.getInstance().returnResponse(params, headers, IntroActivity.this, "http://207.154.236.13/api/login/");
        }
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);
        ImageView logo=(ImageView) findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        // set font
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.ttf");

        facebookButton=(Button) findViewById(R.id.facebookButton);
        facebookButton.setTypeface(typeFace);
        loginButton=(Button) findViewById(R.id.loginButton);
        loginButton.setTypeface(typeFace);

        registerButton=(Button) findViewById(R.id.registerButton);
        registerButton.setTypeface(typeFace);
        infoTextView=(TextView) findViewById(R.id.textView);
        profileImage=(ImageView) findViewById(R.id.profileImage) ;
        logo.animate().translationY(-300f).setDuration(1000);
        if(Persistance.getInstance().getUserInfo(this).profileImage != null){
            Bitmap image;
            image=decodeBase64(Persistance.getInstance().getUserInfo(this).profileImage);
            profileImage.setImageBitmap(image);
            profileImage.animate().alpha(1f).setDuration(1000);
        }
        if(Persistance.getInstance().getUserInfo(this).id.equals("")){
            facebookButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
            infoTextView.setVisibility(View.VISIBLE);
            facebookButton.animate().alpha(1f).setDuration(1000);
            loginButton.animate().alpha(1f).setDuration(1000);
            registerButton.animate().alpha(1f).setDuration(1000);
            infoTextView.animate().alpha(1f).setDuration(1000);
            go=true;
        }
        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                goToActivity();
            }
        }.start();



        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });





    }
    @Override
    public void onBackPressed() {
        finish();
    }

}
