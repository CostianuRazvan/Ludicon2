package com.example.ancuta.ludicon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ancuta.ludicon.R;

/**
 * Created by ancuta on 7/11/2017.
 */

public class RegisterActivity extends FragmentActivity {
    ImageButton backButton;
    Button createAccountButton;
    EditText firstName;
    EditText lastName;
    EditText emailAdress;
    EditText password;
    EditText passwordRepeat;

    //verify password constraints

    public boolean passwordValidate(String password,String repeatPassword){
        boolean validity=false;
        if(!password.equals(repeatPassword) || password.length() < 6){

            validity=true;
        }
        return validity;
    }
    //verify all register fields constraints and shake them if not check them
    public void checkFieldsConstraints(){
        if(firstName.getText().toString().length() == 0){
            Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
            firstName.startAnimation(shake);
        }
        if(lastName.getText().toString().length() == 0){
            Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
            lastName.startAnimation(shake);
        }
        if(emailAdress.getText().toString().length() == 0 || !emailAdress.getText().toString().contains("@") || !emailAdress.getText().toString().contains(".")){
            Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
            emailAdress.startAnimation(shake);
        }
        if(passwordValidate(password.getText().toString(),passwordRepeat.getText().toString())){
            Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
            passwordRepeat.startAnimation(shake);
            password.startAnimation(shake);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        TextView titleText=(TextView) findViewById(R.id.titleText);
        titleText.setText("Register");
        backButton=(ImageButton) findViewById(R.id.backButton);
        createAccountButton=(Button) findViewById(R.id.createAccountButton);
        firstName=(EditText) findViewById(R.id.firstName);
        lastName=(EditText) findViewById(R.id.lastName);
        emailAdress=(EditText) findViewById(R.id.emailAdress);
        password=(EditText) findViewById(R.id.password);
        passwordRepeat=(EditText) findViewById(R.id.passwordRepeat);
        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
            }
        });
        createAccountButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                checkFieldsConstraints();
            }
        });
    }

}
