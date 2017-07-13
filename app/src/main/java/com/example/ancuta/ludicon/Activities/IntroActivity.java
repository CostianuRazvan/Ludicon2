package com.example.ancuta.ludicon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ancuta.ludicon.Controller.Persistance;
import com.example.ancuta.ludicon.R;
import com.example.ancuta.ludicon.User;

/**
 * Created by ancuta on 7/10/2017.
 */

public class IntroActivity extends Activity {
    int i=0;
    Button facebookButton;
    Button loginButton;
    Button registerButton;
    TextView infoTextView;


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
        logo.animate().translationY(-300f).setDuration(1000);
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

        if(Persistance.getInstance().getUserInfo(this).range.equals("null")){
            Intent intent = new Intent(getApplicationContext(), AskPrefferencies.class);
            startActivity(intent);


        }

        else if(Persistance.getInstance().getUserInfo(this).id.equals("")){
            facebookButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
            infoTextView.setVisibility(View.VISIBLE);
            facebookButton.animate().alpha(1f).setDuration(1000);
            loginButton.animate().alpha(1f).setDuration(1000);
            registerButton.animate().alpha(1f).setDuration(1000);
            infoTextView.animate().alpha(1f).setDuration(1000);
        }
        else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }


    }
    @Override
    public void onBackPressed() {
        finish();
    }

}
