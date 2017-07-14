package com.example.ancuta.ludicon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ancuta.ludicon.Controller.Persistance;
import com.example.ancuta.ludicon.R;
import com.example.ancuta.ludicon.User;

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
            if (Persistance.getInstance().getUserInfo(this).range.equals("0")) {
                Intent intent = new Intent(getApplicationContext(), ProfileDetailsActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
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
        facebookButton=(Button) findViewById(R.id.facebookButton);
        loginButton=(Button) findViewById(R.id.loginButton);
        registerButton=(Button) findViewById(R.id.registerButton);
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
