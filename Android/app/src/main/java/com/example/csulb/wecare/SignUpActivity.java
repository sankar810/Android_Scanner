package com.example.csulb.wecare;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

//Sign up activity to create a new user using regular email and password.
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

//Declare necessary items.
    private static final int CHOOSE_IMAGE = 101;
    private EditText signUpName, signUpPhone, signUpEmail, signUpPassword;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    String name;
    private ImageView signUpProfilePhoto;

    Uri uriProfileImage;
    String profileImageUrl;

//onCreate is called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

//Define the items in the content
        btnSignUp = (Button) findViewById(R.id.loginSignUpButton);
        signUpName = (EditText)findViewById(R.id.signUpNameEditText);
        signUpPhone = (EditText)findViewById(R.id.signUpPhoneEditText);
        signUpEmail = (EditText) findViewById(R.id.sigUpEmailEditText);
        signUpPassword = (EditText) findViewById(R.id.signUpPasswordEditText);
        progressBar = (ProgressBar) findViewById(R.id.signUpProgressBar);

//Set onClickListener to call the sign up function
        btnSignUp.setOnClickListener(this);
        
        signUpProfilePhoto = (ImageView)findViewById(R.id.signUpProfilePhoto);
        signUpProfilePhoto.setOnClickListener(this);
    }

    //Resume the activity and hide the progress bar.
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


//onClick function captures the view to allow the various functions to be called.
    @Override
    public void onClick(View v) {
        if(v == btnSignUp) {
            signUpAction();
        }
        else if(v== signUpProfilePhoto){
            imageChooser();
        }
    }

//Open the documents options in android to allow the selection of a profile photo.
    private void imageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),CHOOSE_IMAGE);
    }

    //On result from the imageChoose check the conditions to set the image in the view and upload the image to firebase database.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                signUpProfilePhoto.setImageBitmap(bitmap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    signUpProfilePhoto.setForeground(null);
                }
                uploadImageToFireBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//Upload the image to Firebase database.
    private void uploadImageToFireBase() {
        //Save the image with the time extension
        final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("ProfilePhoto/"+System.currentTimeMillis()
                +".jpg");

        //Check if image is not empty
        if (uriProfileImage != null){
            profileImageRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                //onSuccess take the download url to load the image everytime.
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                }
                //onFailure display a toast containing the error message.
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this, "Error uploading data to storage!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

//Final signUp function.
    private void signUpAction() {
        final String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();
        name = signUpName.getText().toString();
        final String phone = signUpPhone.getText().toString();

        //Check if all the fields in the signup have been filled else display an error.
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone) && phone.length() < 10) {
            Toast.makeText(getApplicationContext(), "Enter a valid phone number!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(uriProfileImage == null){
            Toast.makeText(this, "Please select a profile photo!", Toast.LENGTH_SHORT).show();
            return;
        }

        //If all the consitions are met continue with the sign up process.
        progressBar.setVisibility(View.VISIBLE);
        //create user using Firebase create user function
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            FirebaseUser Username = auth.getCurrentUser();
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .setPhotoUri(Uri.parse(profileImageUrl))
                                    .build();
                            Username.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        User user = new User(name, email, phone,"0");
                                        FirebaseDatabase.getInstance().getReference("users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            FirebaseUser user = auth.getCurrentUser();
                                                            String name = user.getDisplayName();
                                                            //Set the username, image and display a message as successful registration.
                                                            SharedPrefManager.getmInstance(getApplicationContext())
                                                                    .userName(name);
                                                            String photoUrl = user.getPhotoUrl().toString();
                                                            SharedPrefManager.getmInstance(getApplicationContext())
                                                                    .userPhoto(photoUrl);
                                                            SharedPrefManager.getmInstance(getApplicationContext())
                                                                    .setUserLoggedIn("email");
                                                            progressBar.setVisibility(View.GONE);
                                                            Toast.makeText(SignUpActivity.this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                            startActivity(new Intent(SignUpActivity.this, TermsActivity2.class));

                                                        }
                                                        else{
                                                            Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    }
                                }
                            });
                        }
                    }
                });

    }
}

//Class user to use a the constructor to set the necessary user fields.
class User{

    public String name, email, phone, ishihara;

    public User(){

    }
    public User(String name, String email, String phone, String ishihara) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.ishihara = ishihara;
    }
}
