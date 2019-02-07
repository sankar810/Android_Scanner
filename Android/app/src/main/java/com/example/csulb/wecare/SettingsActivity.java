package com.example.csulb.wecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

//call the general settings page.
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    TextView name, generalHealth, ishihara, theme, logout;
    CircleImageView photo;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    String userEmail, userPhone;

    private LoginManager mLoginManager;
    private GoogleSignInClient googleSignInClient;


    //General Settings page.
    //onCreate is called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Initialize all the items in the view and set the onClickListeners.
        photo = (CircleImageView)findViewById(R.id.settingsProfilePhoto);
        Glide.with(this).load(SharedPrefManager.getmInstance(this).getPhotoUrl()).into(photo);
        name = (TextView)findViewById(R.id.settingsName);
        name.setText(SharedPrefManager.getmInstance(this).getUserName());
        generalHealth = (TextView)findViewById(R.id.settingsGeneral);
        generalHealth.setOnClickListener(this);

        ishihara = (TextView)findViewById(R.id.settingsIshihara);
        ishihara.setOnClickListener(this);

        theme = (TextView)findViewById(R.id.settingsTheme);
        theme.setOnClickListener(this);

        logout = (TextView)findViewById(R.id.settingsLogout);
        logout.setOnClickListener(this);

    }

//onClicking the Item start the function.
    @Override
    public void onClick(View view) {
        if(view == generalHealth){
            startActivity(new Intent(SettingsActivity.this, GeneralSettingsActivity.class));
        }
        else if(view == ishihara){
            startActivity(new Intent(SettingsActivity.this,IshiharaActivity.class));
        }
        else if(view == theme){
            startActivity(new Intent(SettingsActivity.this,ThemeActivity.class));
        }
        else if(view == logout){
            logout();
        }
    }

    //Provide the logout option and logout from the signed in option.

    private void logout() {
        //Delete local data
        SharedPrefManager.getmInstance(this).logout();
        //logout from facebook
        mLoginManager.getInstance().logOut();
        //Logout from google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut();

        //Logout from email
        FirebaseAuth.getInstance().signOut();

        //Start login activity
        RunningActivity runningActivity = new RunningActivity();
        runningActivity.handler.removeCallbacks(runningActivity.runnable);
        finish();
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}

