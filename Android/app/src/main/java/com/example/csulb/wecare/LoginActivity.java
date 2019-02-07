package com.example.csulb.wecare;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.net.URL;

/*
Process the google sign in options, facebook login adn login via email and password.
Autheticates the user to check the credibility of the source of the login.
*/
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private Button mLoginButton, mSignUpButton;
    private TextView mForgotLoginTextView;

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar mProgressBar;

    private LoginButton loginFacebookButton;
    private FirebaseAuth mFacebookAuth;
    private CallbackManager mFacebookCallbackManager;
    private static final String TAG2 = "Facebook Sign in";
    private SignInButton googleLoginButton;
    private static final int RC_SIGN_IN = 196;
    private static final String TAG1 = "Google Sign in";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        auth = FirebaseAuth.getInstance();

        //Check if user is already logged in.

        if(SharedPrefManager.getmInstance(this).isLoggedIn()){

            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            return;
        }


        //Initialize the items in the view.
        inputEmail = (EditText) findViewById(R.id.loginEmailEditText);
        inputPassword = (EditText) findViewById(R.id.loginPasswordEditText);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLoginButton = (Button)findViewById(R.id.loginButton);

        mLoginButton.setOnClickListener(this);
        mSignUpButton = (Button)findViewById(R.id.loginSignUpButton);
        mSignUpButton.setOnClickListener(this);

        mForgotLoginTextView = (TextView)findViewById(R.id.loginForgotTextView);
        mForgotLoginTextView.setOnClickListener(this);

        //Facebook Sign in
        //Register a callback from the facebook manager.
        //The requested permissions are for email and public profile.
        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookAuth = FirebaseAuth.getInstance();
        mFacebookCallbackManager = CallbackManager.Factory.create();

        loginFacebookButton = (LoginButton) findViewById(R.id.loginFacebookButton);
        loginFacebookButton.setReadPermissions("email", "public_profile");
        loginFacebookButton.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG2, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG2, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d(TAG2, "facebook:onError", exception);
                    }
                });

        //Facebook sign extends

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        mFirebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

        googleLoginButton= (SignInButton) findViewById(R.id.loginGoogleButton);
        googleLoginButton.setOnClickListener(this);
        //Google sign in extended
    }


    //Facebook sign in continues

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mFacebookAuth.getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }
    // [END on_start_check_user]

    // [START on_activity_result]
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }*/
    // [END on_activity_result]

    // [START auth_with_facebook]
    //Handle the facebook access token to verify the users credibility.
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG2, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFacebookAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG2, "signInWithCredential:success");
                            FirebaseUser user = mFacebookAuth.getCurrentUser();
                            String name = user.getDisplayName();
                            SharedPrefManager.getmInstance(getApplicationContext())
                                    .userName(name);
                            String photoUrl = user.getPhotoUrl().toString();
                            SharedPrefManager.getmInstance(getApplicationContext())
                                    .userPhoto(photoUrl);
                            SharedPrefManager.getmInstance(getApplicationContext())
                                    .setFbLoggedIn("fb");
                            Intent loginIntent = new Intent(LoginActivity.this,TermsActivity.class);
                            startActivity(loginIntent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG2, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "An account already exists with " +
                                            "the same email address but different sign-in credentials. Sign in using a provider " +
                                            "associated with this email address.",
                                    Toast.LENGTH_LONG).show();

                        }


                    }
                });
    }
    // [END auth_with_facebook]








    //Google sign in continues

    //SignIn the activity calls the google authenticator to get the user.
        private void signIn() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            mProgressBar.setVisibility(View.VISIBLE);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        //check the user on result from the sign in
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            mProgressBar.setVisibility(View.GONE);
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }else{
                mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }

        //Authenticate the user for google sign in option
        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
            Log.d(TAG1, "firebaseAuthWithGoogle:" + acct.getId());

            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mFirebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG1, "signInWithCredential:success");
                                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                String name = user.getDisplayName();
                                SharedPrefManager.getmInstance(getApplicationContext())
                                        .userName(name);
                                String photoUrl = user.getPhotoUrl().toString();
                                SharedPrefManager.getmInstance(getApplicationContext())
                                        .userPhoto(photoUrl);
                                SharedPrefManager.getmInstance(getApplicationContext())
                                        .setGoogleLoggedIn("google");
                                Intent loginIntent = new Intent(LoginActivity.this,TermsActivity.class);
                                startActivity(loginIntent);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG1, "signInWithCredential:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

            //Google Sign in ends here.

        }

//onClick button call the respective function
    @Override
    public void onClick(View v) {
        if(v == mLoginButton){
            mProgressBar.setVisibility(View.VISIBLE);
            login();
        }else if (v == mSignUpButton){
            signUp();
        } else if(v == googleLoginButton){
            signIn();
        } else if (v == mForgotLoginTextView){
            forgotLogin();
        }
    }

//Start the forgot login activity
    private void forgotLogin() {
        Intent intentForgot = new Intent(LoginActivity.this,ForgotLoginActivity.class);
        startActivity(intentForgot);
    }

//start the sign up activity.
    private void signUp() {
        Intent intentSignUp = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intentSignUp);

    }

//login using email and password
    private void login() {
    /*    mProgressBar.setVisibility(View.GONE);
        finish();
        SharedPrefManager.getmInstance(getApplicationContext())
                .userLoginButton("login");
        Intent intentLogin = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intentLogin);
*/
        mProgressBar.setVisibility(View.VISIBLE);
        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

//check if the fields are filled appropriately.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.GONE);
            return;
        }



        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            mProgressBar.setVisibility(View.GONE);
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //User authenticated catch all the details and open the home activity.
                            mProgressBar.setVisibility(View.GONE);
                            FirebaseUser user = auth.getCurrentUser();
                            String name = user.getDisplayName();
                            SharedPrefManager.getmInstance(getApplicationContext())
                                    .userName(name);
                            String photoUrl = user.getPhotoUrl().toString();
                            SharedPrefManager.getmInstance(getApplicationContext())
                                    .userPhoto(photoUrl);
                            SharedPrefManager.getmInstance(getApplicationContext())
                                    .setUserLoggedIn("email");
                            Intent intent = new Intent(LoginActivity.this, TermsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }



}


