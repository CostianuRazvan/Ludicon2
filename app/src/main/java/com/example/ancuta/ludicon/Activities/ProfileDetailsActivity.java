package com.example.ancuta.ludicon.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ancuta.ludicon.Controller.HTTPResponseController;
import com.example.ancuta.ludicon.Controller.ImagePicker;
import com.example.ancuta.ludicon.Controller.Persistance;
import com.example.ancuta.ludicon.R;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import static android.R.color.transparent;

/**
 * Created by ancuta on 7/14/2017.
 */

public class ProfileDetailsActivity extends Activity {
    ImageButton backButton;
    RadioButton male;
    RadioButton female;
    RadioGroup sexSwitch;
    ImageView chooseAPhoto;
    ImageView imgProfilePicture;
    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    String myBase64Image;
    Button saveAndContinueButton;
    TextView introText;
    int sex=0;
    EditText age;

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }



    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.profile_details_activity);
        backButton=(ImageButton)findViewById(R.id.backButton);
        backButton.setAlpha(0f);
        backButton.setClickable(false);
        male=(RadioButton) findViewById(R.id.male);
        female=(RadioButton) findViewById(R.id.female);
        TextView titleText=(TextView) findViewById(R.id.titleText);
        titleText.setText("Profile Details");
        sexSwitch=(RadioGroup) findViewById(R.id.sexSwitch);
        chooseAPhoto=(ImageView) findViewById(R.id.chooseAPhoto);
        imgProfilePicture=(ImageView) findViewById(R.id.imgProfilePicture);
        saveAndContinueButton=(Button) findViewById(R.id.saveAndContinueButton);
        introText=(TextView) findViewById(R.id.introText);
        introText.setText("New you out here "+Persistance.getInstance().getUserInfo(this).firstName+"?Cool!");
        age=(EditText)findViewById(R.id.age);
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                age.setBackgroundResource(R.drawable.rounded_edittext);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        if(male.isChecked()){
            male.setBackgroundResource(R.drawable.rounded_button);

        }
        else{
            female.setBackgroundResource(R.drawable.rounded_button);
        }
        sexSwitch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(male.isChecked()){
                    male.setBackgroundResource(R.drawable.rounded_button);
                    female.setBackgroundResource(transparent);
                    sex=0;

                }
                else{
                    female.setBackgroundResource(R.drawable.rounded_button);
                    male.setBackgroundResource(transparent);
                    sex=1;
                }
            }
        });
        chooseAPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(ProfileDetailsActivity.this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);

            }
        });
        saveAndContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (age.getText().length() > 0) {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("userId", Persistance.getInstance().getUserInfo(ProfileDetailsActivity.this).id);
                    params.put("gender", String.valueOf(sex));
                    params.put("yearBorn", String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(age.getText().toString())));
                    if (myBase64Image != null) {
                        params.put("profileImage", myBase64Image);
                    }
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("authKey", Persistance.getInstance().getUserInfo(ProfileDetailsActivity.this).authKey);
                    HTTPResponseController.getInstance().returnResponse(params, headers, ProfileDetailsActivity.this, "http://207.154.236.13/api/user/");
                }
                else{
                    Animation shake = AnimationUtils.loadAnimation(ProfileDetailsActivity.this, R.anim.shake);
                    age.startAnimation(shake);
                    age.setBackgroundResource(R.drawable.rounded_edittext_red);
                }
            }
        });








    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                if(bitmap != null) {
                    imgProfilePicture.setImageBitmap(bitmap);
                    myBase64Image=encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
                    System.out.println("dimensiunea este:" + myBase64Image.length());
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    @Override
    public void onBackPressed() {

    }
}
