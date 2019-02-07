package com.example.csulb.wecare;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashSet;

public class GeneralSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout age, gender, disease, body, language;
    RadioGroup ageRadioGroup, genderRadioGroup, bodyRadioGroup, voiceRadioGroup;
    RadioButton ltFiftyRadioButton,fiftySixtyRadioButton, sixtySeventyRadioButton, gtSeventyRadioButton;
    RadioButton maleRadioButton, femaleRadioButton, otherRadioButton;
    RadioButton superFitRadioButton, fitRadioButton, balancedRadioButton, midLowerRadioButton, obeseRadioButton;
    RadioButton englishRadioButton, spanishRadioButton;

    CheckBox arthritis, cholestrol, diabetes, heart, hypertension, obesity, osteroposis, none;
    CheckBox checkboxes[];


    Button save,reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);

        age = (LinearLayout)findViewById(R.id.generalAgeLinearLayout);
        gender = (LinearLayout)findViewById(R.id.generalGenderLinearLayout);
        disease = (LinearLayout)findViewById(R.id.generalDiseaseLinearLayout);
        body = (LinearLayout)findViewById(R.id.generalBodyLinearLayout);
        language = (LinearLayout)findViewById(R.id.generalLanguageLinearLayout);
        checkTheme();

        ageRadioGroup = (RadioGroup)findViewById(R.id.ageRadioGroup);
        genderRadioGroup = (RadioGroup)findViewById(R.id.genderRadioGroup);
        bodyRadioGroup = (RadioGroup)findViewById(R.id.bodyRadioGroup);
        voiceRadioGroup = (RadioGroup)findViewById(R.id.languageRadioGroup);

        ltFiftyRadioButton = (RadioButton)findViewById(R.id.ltFiftyRadioButton);
        fiftySixtyRadioButton = (RadioButton)findViewById(R.id.fiftySixtyRadioButton);
        sixtySeventyRadioButton = (RadioButton)findViewById(R.id.sixtySeventyRadioButton);
        gtSeventyRadioButton = (RadioButton)findViewById(R.id.gtSeventyRadioButton);
        maleRadioButton = (RadioButton)findViewById(R.id.maleRadioButton);
        femaleRadioButton = (RadioButton)findViewById(R.id.femaleRadioButton);
        otherRadioButton = (RadioButton)findViewById(R.id.otherRadioButton);
        superFitRadioButton = (RadioButton)findViewById(R.id.superFitRadioButton);
        fitRadioButton = (RadioButton)findViewById(R.id.fitRadioButton);
        balancedRadioButton = (RadioButton)findViewById(R.id.balancedRadioButton);
        midLowerRadioButton = (RadioButton)findViewById(R.id.midLowerRadioButton);
        obeseRadioButton = (RadioButton)findViewById(R.id.obeseRadioButton);
        englishRadioButton = (RadioButton)findViewById(R.id.englishRadioButton);
        spanishRadioButton = (RadioButton)findViewById(R.id.spanishRadioButton);

        arthritis = (CheckBox)findViewById(R.id.arthritisCheckBox);
        cholestrol = (CheckBox)findViewById(R.id.cholestrolCheckBox);
        diabetes = (CheckBox)findViewById(R.id.diabetesCheckBox);
        heart = (CheckBox)findViewById(R.id.heartCheckBox);
        hypertension = (CheckBox)findViewById(R.id.hypertensionCheckBox);
        obesity = (CheckBox)findViewById(R.id.obesityCheckBox);
        osteroposis = (CheckBox)findViewById(R.id.osteoporosisCheckBox);
        none = (CheckBox)findViewById(R.id.noneCheckBox);
        checkboxes = new CheckBox[]{arthritis, cholestrol, diabetes, heart, hypertension, obesity, osteroposis, none};
        save = (Button)findViewById(R.id.generalSaveButton);
        save.setOnClickListener(this);
        reset = (Button)findViewById(R.id.generalResetButton);
        reset.setOnClickListener(this);

        if(SharedPrefManager.getmInstance(this).getAge() != 0 && SharedPrefManager.getmInstance(this).getAge() != -1){
            int age = SharedPrefManager.getmInstance(this).getAge();
            RadioButton age1 = (RadioButton)findViewById(age);
            age1.setChecked(true);
        }else{
            ageRadioGroup.clearCheck();
        }
        if(SharedPrefManager.getmInstance(this).getGender() != -1 && SharedPrefManager.getmInstance(this).getGender() != 0){
            int gender = SharedPrefManager.getmInstance(this).getGender();
            RadioButton gender1 = (RadioButton)findViewById(gender);
            gender1.setChecked(true);
        }else{
            genderRadioGroup.clearCheck();
        }
        if(SharedPrefManager.getmInstance(this).getBody() != -1 && SharedPrefManager.getmInstance(this).getBody() != 0){
            int body = SharedPrefManager.getmInstance(this).getBody();
            RadioButton body1 = (RadioButton)findViewById(body);
            body1.setChecked(true);
        }else{
            bodyRadioGroup.clearCheck();
        }
        if(SharedPrefManager.getmInstance(this).getLanguage() != -1 && SharedPrefManager.getmInstance(this).getLanguage() != 0){
            int lang = SharedPrefManager.getmInstance(this).getLanguage();
            RadioButton lang1 = (RadioButton)findViewById(lang);
            lang1.setChecked(true);
        }else{
            voiceRadioGroup.clearCheck();
        }


        String[] check = SharedPrefManager.getmInstance(this).loadArray("KEY_CHECK_BOX",this);
        for (int i = 0; i< check.length; i++){
            if(check[i]=="1"){
                checkboxes[i].setChecked(true);
            }
        }
    }

    //Check the theme as per the color blindness test.
    private void checkTheme() {
        String i = SharedPrefManager.getmInstance(this).getColor();
        String[] color = {};

        switch (i){
            case "0":
                color = this.getResources().getStringArray(R.array.normal);
                break;
            case "1":
                color = this.getResources().getStringArray(R.array.duetranope);
                break;
            case "2":
                color = this.getResources().getStringArray(R.array.protanope);
                break;
            case "3":
                color = this.getResources().getStringArray(R.array.tirtanope);
                break;
            case "4":
                color = this.getResources().getStringArray(R.array.deuteranomly);
                break;
        }

        age.setBackgroundColor(Color.parseColor(color[0]));
        gender.setBackgroundColor(Color.parseColor(color[1]));
        disease.setBackgroundColor(Color.parseColor(color[2]));
        body.setBackgroundColor(Color.parseColor(color[3]));
        language.setBackgroundColor(Color.parseColor(color[4]));

    }


    @Override
    protected void onStart() {

        super.onStart();
        checkTheme();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkTheme();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkTheme();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTheme();
    }

    @Override
    public void onClick(View view) {
        if (view == save){
            saveDetails();
        }
        else if(view == reset){
            reset();
        }

    }

    //reset all values
    private void reset() {
        ageRadioGroup.clearCheck();
        genderRadioGroup.clearCheck();
        bodyRadioGroup.clearCheck();
        voiceRadioGroup.clearCheck();
        if(SharedPrefManager.getmInstance(this).getAge() != 0 && SharedPrefManager.getmInstance(this).getAge() != -1){
            SharedPrefManager.getmInstance(this).editor.remove("KEY_AGE").clear().commit();
        }
        if(SharedPrefManager.getmInstance(this).getGender() != -1 && SharedPrefManager.getmInstance(this).getGender() != 0){
            SharedPrefManager.getmInstance(this).editor.remove("KEY_GENDER").clear().commit();

        }
        if(SharedPrefManager.getmInstance(this).getBody() != -1 && SharedPrefManager.getmInstance(this).getBody() != 0){
            SharedPrefManager.getmInstance(this).editor.remove("KEY_BODY").clear().commit();
        }
        if(SharedPrefManager.getmInstance(this).getLanguage() != -1 && SharedPrefManager.getmInstance(this).getLanguage() != 0){
            SharedPrefManager.getmInstance(this).editor.remove("KEY_LANGUAGE").clear().commit();
        }
        SharedPrefManager.getmInstance(this).setLanguageCode(1);
        for (int i=0; i < checkboxes.length; i++){
            checkboxes[i].setChecked(false);
        }
        String[] notChecked = {"0","0","0","0","0","0","0","0"};
        SharedPrefManager.getmInstance(this).saveArray(notChecked,"KEY_CHECK_BOX",this);
    }

    //Save details to memory
    private void saveDetails() {
        int age = ageRadioGroup.getCheckedRadioButtonId();
        int gender = genderRadioGroup.getCheckedRadioButtonId();
        int body = bodyRadioGroup.getCheckedRadioButtonId();
        int language = voiceRadioGroup.getCheckedRadioButtonId();

        SharedPrefManager.getmInstance(this).setAge(age);
        SharedPrefManager.getmInstance(this).setGender(gender);
        SharedPrefManager.getmInstance(this).setBody(body);
        SharedPrefManager.getmInstance(this).setLanguage(language);
        if(language != -1){
            RadioButton radioButton = (RadioButton)findViewById(language);
            if (radioButton.getText().toString().equals("English")){
                SharedPrefManager.getmInstance(this).setLanguageCode(1);
            }
            else if (radioButton.getText().toString().equals("Spanish")){
                SharedPrefManager.getmInstance(this).setLanguageCode(2);
            }
            else{
                SharedPrefManager.getmInstance(this).setLanguageCode(1);
            }
        }

        String[] checked = new String[checkboxes.length];
        for(int i=0;i<checkboxes.length; i++){
            if(checkboxes[i].isChecked()){
                checked[i] = "1";
            }
            else{
                checked[i]="0";
            }
        }

        SharedPrefManager.getmInstance(this).saveArray(checked,"KEY_CHECK_BOX", this);

    }



}
