package com.example.ancuta.ludicon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ancuta.ludicon.Controller.HTTPResponseController;
import com.example.ancuta.ludicon.PasswordEncryptor;
import com.example.ancuta.ludicon.R;

import java.util.HashMap;

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
    public boolean checkFieldsConstraints(){
        boolean isVerified=false;
        if(firstName.getText().toString().length() == 0){
            firstName.setBackgroundResource(R.drawable.rounded_edittext_red);
            Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
            firstName.startAnimation(shake);
            firstName.setBackgroundResource(R.drawable.rounded_edittext_red);
            isVerified=true;

        }
        if(lastName.getText().toString().length() == 0){
            Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
            lastName.startAnimation(shake);
            lastName.setBackgroundResource(R.drawable.rounded_edittext_red);
            isVerified=true;
        }
        if(emailAdress.getText().toString().length() == 0 || !emailAdress.getText().toString().contains("@") || !emailAdress.getText().toString().contains(".")){
            Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
            emailAdress.startAnimation(shake);
            emailAdress.setBackgroundResource(R.drawable.rounded_edittext_red);
            isVerified=true;
        }
        if(passwordValidate(password.getText().toString(),passwordRepeat.getText().toString())){
            Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
            passwordRepeat.startAnimation(shake);
            passwordRepeat.setBackgroundResource(R.drawable.rounded_edittext_red);
            password.startAnimation(shake);
            password.setBackgroundResource(R.drawable.rounded_edittext_red);
            isVerified=true;
        }
        return isVerified;
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
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                firstName.setBackgroundResource(R.drawable.rounded_edittext);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lastName.setBackgroundResource(R.drawable.rounded_edittext);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        emailAdress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailAdress.setBackgroundResource(R.drawable.rounded_edittext);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password.setBackgroundResource(R.drawable.rounded_edittext);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordRepeat.setBackgroundResource(R.drawable.rounded_edittext);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        createAccountButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!checkFieldsConstraints()){
                    try{
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("firstName", firstName.getText().toString());
                        params.put("lastName",lastName.getText().toString());
                        params.put("email",emailAdress.getText().toString());
                        params.put("password", PasswordEncryptor.generateSHA255FromString(password.getText().toString()));
                        params.put("isCustom","0");
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("apiKey", "000000");
                        HTTPResponseController.getInstance().returnResponse(params, headers, RegisterActivity.this, "http://207.154.236.13/api/register/");

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    if(firstName.getText().length() == 0 || lastName.getText().length() == 0 ){
                        Toast.makeText(RegisterActivity.this,"Please provide first and last name.",Toast.LENGTH_LONG).show();
                    }
                    else if(firstName.getText().length() == 0){
                        Toast.makeText(RegisterActivity.this,"Please provide email.",Toast.LENGTH_LONG).show();
                    }
                    else if(password.getText().length() == 0 || passwordRepeat.getText().length() == 0 ){
                        Toast.makeText(RegisterActivity.this,"Please provide password.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
