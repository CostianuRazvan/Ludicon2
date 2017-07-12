package com.example.ancuta.ludicon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ancuta.ludicon.R;

/**
 * Created by ancuta on 7/11/2017.
 */

public class ResetPasswordActivity extends Activity {

    ImageButton backButton;
    Button resetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);
        TextView titleText=(TextView) findViewById(R.id.titleText);
        titleText.setText("Reset Password");
        backButton=(ImageButton) findViewById(R.id.backButton);
        resetPassword=(Button) findViewById(R.id.resetPasswordButton);
        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordFinalActivity.class);
                startActivity(intent);
            }
        });

    }
}
