package com.example.csulb.wecare;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//The Ishihara test is a color perception test for red-green color deficiencies, the first in a class of successful color vision tests.
public class IshiharaActivity extends AppCompatActivity {

    public static final String TAG = "StepCounter";
    public int correctAnswer;//holding correct answer
    public int protan=0, counterprotan=0; // counter for protan deficiency
    public int duetan=0, counterduetan=0; //counter for duetan deficiency
    public int tirtanope=0, countertirtanope = 0; //counter for tirtan deficiency
    public int progressStatus;//the number of question user has answered correct
    public int userStatus;//the question number user is currently at
    public Random rand;//random generator initialize onCreate
    public int rndInt;// n = the number of images, that start at idx 1 initialize onCreate
    public List<Integer> list;//this list will hold our numbers 1 to 8 representing the images

    public void answerGenerator(){
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        String btnName;
        //-----------------------------------------------------------------------------------------------------------------
        int rndBtn, min, max, randomOne, randomTwo;
        //String btnName;
        //setting the bounds of random answers
        if(correctAnswer - 20 < 0)
            min = 0;
        else
            min = correctAnswer - 20;
        max = correctAnswer + 20;

        //making answers unique
        do {
            randomOne = rand.nextInt(max - min + 1) + min;//+1 to include the max value in our limits
        } while (randomOne == correctAnswer);

        do{
            randomTwo = rand.nextInt(max - min + 1) + min;//+1 to include the max value in our limits
        }while (randomOne == randomTwo || correctAnswer == randomTwo);
//------------------------------------------------------------------------------------------------------
        //choosing random button for the right answer
        rndBtn  = rand.nextInt(3) + 1;
        btnName = "btn" + rndBtn;

        //and setting the other two random numbers to the other two buttons
        if(randomOne==0)
        {
            randomOne+=1;
        }
        else if(randomTwo==0)
        {
            randomTwo+=1;
        }
        else
        {

        }
        if(btnName.equals("btn1")) //btn1
        {
            if(protan==0 && duetan==0)
            {
                btn1.setText(Integer.toString(correctAnswer));
                btn2.setText(Integer.toString(randomOne));
                btn3.setText("Not Sure");
            }
            else if (protan>0 && duetan==0)
            {
                if(protan==correctAnswer) {
                    btn1.setText(Integer.toString(correctAnswer));
                    btn2.setText(Integer.toString(randomTwo));
                    btn3.setText("Not Sure");
                }
                else
                {
                    btn1.setText(Integer.toString(correctAnswer));
                    btn2.setText(Integer.toString(protan));
                    btn3.setText("Not Sure");
                }

            }
            else if(protan==0 && duetan>0)
            {
                if(duetan==correctAnswer) {
                    btn1.setText(Integer.toString(correctAnswer));
                    btn2.setText(Integer.toString(randomTwo));
                    btn3.setText("Not Sure");
                }
                else {
                    btn1.setText(Integer.toString(correctAnswer));
                    btn2.setText(Integer.toString(duetan));
                    btn3.setText("Not Sure");
                }
            }
            else if(protan==duetan)
            {
                if(protan==correctAnswer) {
                    btn1.setText(Integer.toString(correctAnswer));
                    btn2.setText(Integer.toString(randomOne));
                    btn3.setText("Not Sure");
                }
                else
                {
                    btn1.setText(Integer.toString(correctAnswer));
                    btn2.setText(Integer.toString(protan));
                    btn3.setText("Not Sure");
                }
            }
            else
            {
                if(correctAnswer==duetan) {
                    btn1.setText(Integer.toString(correctAnswer));
                    btn2.setText(Integer.toString(randomTwo));
                    btn3.setText(Integer.toString(protan));
                }
                else if(correctAnswer==protan)
                {
                    btn1.setText(Integer.toString(correctAnswer));
                    btn2.setText(Integer.toString(randomOne));
                    btn3.setText(Integer.toString(duetan));
                }
                else{
                    btn1.setText(Integer.toString(correctAnswer));
                    btn2.setText(Integer.toString(duetan));
                    btn3.setText(Integer.toString(protan));
                }

            }

        }

        else if(btnName.equals("btn2")) //btn2
        {
            if(protan==0 && duetan==0)
            {
                btn1.setText(Integer.toString(randomOne));
                btn2.setText(Integer.toString(correctAnswer));
                btn3.setText("Not Sure");
            }
            else if (protan>0 && duetan==0)
            {
                if(protan==correctAnswer) {
                    btn1.setText(Integer.toString(randomOne));
                    btn2.setText(Integer.toString(correctAnswer));
                    btn3.setText("Not Sure");
                }
                else
                {
                    btn1.setText(Integer.toString(protan));
                    btn2.setText(Integer.toString(correctAnswer));
                    btn3.setText("Not Sure");
                }
            }

            else if(protan==0 && duetan>0)
            {
                if(duetan==correctAnswer) {
                    btn1.setText(Integer.toString(randomTwo));
                    btn2.setText(Integer.toString(correctAnswer));
                    btn3.setText("Not Sure");
                }
                else {
                    btn1.setText(Integer.toString(duetan));
                    btn2.setText(Integer.toString(correctAnswer));
                    btn3.setText("Not Sure");
                }
            }
            else if(protan==duetan)
            {
                if(protan==correctAnswer) {
                    btn1.setText(Integer.toString(randomOne));
                    btn2.setText(Integer.toString(correctAnswer));
                    btn3.setText("Not Sure");
                }
                else{
                    btn1.setText(Integer.toString(protan));
                    btn2.setText(Integer.toString(correctAnswer));
                    btn3.setText("Not Sure");
                }
            }
            else
            {
                if(correctAnswer==duetan)
                {
                    btn1.setText(Integer.toString(protan));
                    btn2.setText(Integer.toString(correctAnswer));
                    btn3.setText(Integer.toString(randomTwo));
                }
                else if(correctAnswer==protan){
                    btn1.setText(Integer.toString(randomOne));
                    btn2.setText(Integer.toString(correctAnswer));
                    btn3.setText(Integer.toString(duetan));
                }
                else {
                    btn1.setText(Integer.toString(protan));
                    btn2.setText(Integer.toString(correctAnswer));
                    btn3.setText(Integer.toString(duetan));
                }
            }

        }

        else //btn3
        {
            if(protan==0 && duetan==0)
            {
                btn1.setText(Integer.toString(randomOne));
                btn2.setText("Not Sure");
                btn3.setText(Integer.toString(correctAnswer));
            }

            else if (protan>0 && duetan==0)
            {
                if(protan==correctAnswer) {
                    btn1.setText(Integer.toString(randomOne));
                    btn2.setText("Not Sure");
                    btn3.setText(Integer.toString(correctAnswer));
                }
                else {
                    btn1.setText(Integer.toString(protan));
                    btn2.setText("Not Sure");
                    btn3.setText(Integer.toString(correctAnswer));
                }
            }

            else if(protan==0 && duetan>0)
            {
                if(duetan==correctAnswer)
                {
                    btn1.setText(Integer.toString(randomTwo));
                    btn2.setText("Not Sure");
                    btn3.setText(Integer.toString(correctAnswer));

                }
                else {
                    btn1.setText(Integer.toString(duetan));
                    btn2.setText("Not Sure");
                    btn3.setText(Integer.toString(correctAnswer));
                }
            }

            else if(protan==duetan)
            {
                if(protan==correctAnswer)
                {
                    btn1.setText(Integer.toString(randomOne));
                    btn2.setText("Not Sure");
                    btn3.setText(Integer.toString(correctAnswer));

                }
                else {
                    btn1.setText(Integer.toString(protan));
                    btn2.setText("Not Sure");
                    btn3.setText(Integer.toString(correctAnswer));
                }
            }

            else
            {
                if(correctAnswer==protan)
                {
                    btn1.setText(Integer.toString(randomOne));
                    btn2.setText(Integer.toString(duetan));
                    btn3.setText(Integer.toString(correctAnswer));

                }
                else if(correctAnswer==duetan)
                {
                    btn1.setText(Integer.toString(protan));
                    btn2.setText(Integer.toString(randomTwo));
                    btn3.setText(Integer.toString(correctAnswer));
                }
                else {
                    btn1.setText(Integer.toString(protan));
                    btn2.setText(Integer.toString(duetan));
                    btn3.setText(Integer.toString(correctAnswer));
                }
            }
        }
    }
    public void randomGenerator(){
        boolean gamefinished = false;
        //   int rndBtn, min, max, randomOne, randomTwo;
        ImageView mainImage = (ImageView) findViewById(R.id.mainView);
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        //resetButtonText
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");

        while (list.contains(rndInt)){//if the number already exists in our list we need a new one
            if(list.size() == 17){
                gamefinished = true;//if our list has 8 unique numbers then we must terminate the game
                break;
            }
            rndInt = rand.nextInt(17) + 1;//if list.size < 8 then rand.nextInt
        }

        if(!gamefinished){
            list.add(rndInt);
            String imgName = "i" + rndInt;
            int id = getResources().getIdentifier(imgName, "drawable", getPackageName());
            mainImage.setImageResource(id);
            switch (imgName){//initialize correctAnswer
                case "i1":
                    correctAnswer = 12;
                    protan = 12 ;
                    duetan= 12 ;
                    tirtanope = 12 ;
                    break;
                case "i2":
                    correctAnswer = 8;
                    protan = 8;
                    duetan= 8;
                    tirtanope = 8;
                    break;
                case "i3":
                    correctAnswer = 6;
                    protan = 0;
                    duetan = 0;
                    tirtanope = 6;
                    break;
                case "i4":
                    correctAnswer = 29;
                    protan = 0;
                    duetan= 29;
                    tirtanope = 29;
                    break;
                case "i5":
                    correctAnswer = 57;
                    protan = 57;
                    duetan= 57;
                    tirtanope = 57;

                    break;
                case "i6":
                    correctAnswer = 5;
                    protan = 0;
                    duetan= 5;
                    tirtanope = 5;
                    break;
                case "i7":
                    correctAnswer = 3;
                    protan = 5;
                    duetan= 3;
                    tirtanope = 3;
                    break;
                case "i8":
                    correctAnswer = 15;
                    protan = 17;
                    duetan= 15;
                    tirtanope = 15;
                    break;
                case "i9":
                    correctAnswer = 74;
                    protan = 0;
                    duetan= 74;
                    tirtanope = 74;
                    break;
                case "i10":
                    correctAnswer = 2;
                    protan = 2;
                    duetan= 2;
                    tirtanope = 2;
                    break;
                case "i11":
                    correctAnswer = 6;
                    protan = 6;
                    duetan= 6;
                    tirtanope = 6;
                    break;
                case "i12":
                    correctAnswer = 97;
                    protan = 0;
                    duetan= 87;
                    tirtanope = 87;
                    break;
                case "i13":
                    correctAnswer = 45;
                    protan = 0;
                    duetan= 0;
                    tirtanope = 45;
                    break;

                case "i14":
                    correctAnswer = 5;
                    protan = 5;
                    duetan= 5;
                    tirtanope = 5;
                    break;
                case "i15":
                    correctAnswer = 7;
                    protan = 0;
                    duetan= 7;
                    tirtanope = 7;
                    break;
                case "i16":
                    correctAnswer = 16;
                    protan = 16;
                    duetan= 16;
                    tirtanope = 16;
                    break;
                case "i17":
                    correctAnswer = 73;
                    protan = 73;
                    duetan= 73;
                    tirtanope = 73;
                    break;
            }
            answerGenerator();
        }else{
            gameTermination();
            scoreDisplay();
        }

    }

    //Display the score of the test and provide the user with deficiency type or no deficiency.
    public void scoreDisplay()
    {

        Log.i(TAG, "Duetan: "+String.valueOf(counterduetan));
        Log.i(TAG, "Protan: "+String.valueOf(counterprotan));
        Log.i(TAG, "Tirtanope: "+String.valueOf(countertirtanope));
        Log.i(TAG, "Correct: "+String.valueOf(progressStatus));


        if(counterduetan>=counterprotan && counterduetan>=progressStatus)
        {
            Toast.makeText(getApplicationContext(),"Kind of color blindness detected: Deuteranope (Green)", Toast.LENGTH_LONG).show();
            SharedPrefManager.getmInstance(this).setColor("1");
        }
        else if(counterprotan>=counterduetan && counterprotan>=progressStatus)
        {
            Toast.makeText(getApplicationContext(),"Kind if color blindness detected: Protanope (Red)", Toast.LENGTH_LONG).show();
            SharedPrefManager.getmInstance(this).setColor("2");
        }
        else if(countertirtanope > counterduetan && countertirtanope > counterprotan && countertirtanope > progressStatus){
            Toast.makeText(getApplicationContext(),"Kind of color blindness detected: Tirtanope (Blue)", Toast.LENGTH_LONG).show();
            SharedPrefManager.getmInstance(this).setColor("3");
        }
        else if(counterduetan==counterprotan && counterduetan>=progressStatus )
        {
            Toast.makeText(getApplicationContext(),"Kind of color blindness detected: Deuteranomaly (Red & Green)", Toast.LENGTH_LONG).show();
            SharedPrefManager.getmInstance(this).setColor("4");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Congratulations no color blindness was detected.", Toast.LENGTH_LONG).show();
            SharedPrefManager.getmInstance(this).setColor("0");
        }
    }


//on clicking the answers increment the counter for the respective deficiency.
    public void userUpdateStatus(int userChoice){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView answersView = (TextView) findViewById(R.id.answersView);
        userStatus++;
        if(userChoice == protan){
            counterprotan++;
        }
        if(userChoice == duetan){
            counterduetan++;
        }
        if(userChoice == tirtanope){
            countertirtanope++;
        }

        if(userChoice == correctAnswer){
            progressStatus++;
        }else
        answersView.setText(progressStatus + "/" + userStatus);
        progressBar.setProgress(userStatus);

    }


    public void initiateGame(){
        //Initialize the items
        final Button btn1 = (Button) findViewById(R.id.btn1);
        final Button btn2 = (Button) findViewById(R.id.btn2);
        final Button btn3 = (Button) findViewById(R.id.btn3);
        Button restart = (Button) findViewById(R.id.infoBtn);
        ImageView mainImage = (ImageView) findViewById(R.id.mainView);
        progressStatus = 0;
        userStatus = 0;

        randomGenerator();//Generate the first image

        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(250);
        animation1.setStartOffset(250);
        mainImage.startAnimation(animation1);
        mainImage.animate().scaleX(1.3f).scaleY(1.3f).setDuration(500);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userChoice;
                if(btn1.getText().toString()=="Not Sure")
                {
                    userChoice = 0;
                }
                else {
                    userChoice = Integer.parseInt(btn1.getText().toString());
                }
                userUpdateStatus(userChoice);
                randomGenerator();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userChoice=0;
                if(btn2.getText().toString()=="Not Sure")
                {
                    userChoice = 0;
                }
                else
                {
                    userChoice = Integer.parseInt(btn2.getText().toString());
                }
                userUpdateStatus(userChoice);
                randomGenerator();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userChoice=0;
                if(btn3.getText().toString()=="Not Sure")
                {
                    userChoice=0;
                }
                else
                {
                    userChoice = Integer.parseInt(btn3.getText().toString());
                }

                userUpdateStatus(userChoice);
                randomGenerator();
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

    }


//Terminate the game on completion and exit.
    public void gameTermination(){
        final Button btn1 = (Button) findViewById(R.id.btn1);
        final Button btn2 = (Button) findViewById(R.id.btn2);
        final Button btn3 = (Button) findViewById(R.id.btn3);
        final Button btn4 = (Button)findViewById(R.id.btn4);
        btn1.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);
        btn3.setVisibility(View.GONE);
        btn4.setVisibility(View.VISIBLE);
        final Button restart = (Button) findViewById(R.id.infoBtn);
        final TextView finalText = (TextView) findViewById(R.id.answersView);
        final ImageView finalImage = (ImageView) findViewById(R.id.mainView);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(0);
        fadeOut.setDuration(100);

        final Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setStartOffset(250);
        fadeIn.setDuration(500);

        //finalText.setText(progressStatus + "/" + userStatus);
        btn1.setAnimation(fadeOut);
        btn2.setAnimation(fadeOut);
        btn3.setAnimation(fadeOut);
        restart.setAnimation(fadeOut);
        finalImage.setAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                btn1.setText("Restart");
                btn2.setText("Exit");
                finalText.animate().translationX(-450).setDuration(1000);
                finalText.animate().translationY(-200).setDuration(1000);
                finalText.animate().scaleX(1.9f).setDuration(1000);
                finalText.animate().scaleY(1.9f).setDuration(1000);
                progressBar.animate().translationX(85).setStartDelay(150).setDuration(1000);
                btn3.setAlpha(0);
                restart.setAlpha(0);
                btn1.setClickable(false);
                btn2.setClickable(false);
                btn3.setClickable(false);
                restart.setClickable(false);
                //layout.removeView(finalImage);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(IshiharaActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }

    //Restart the game on request.
    public void restartGame(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView answersView = (TextView) findViewById(R.id.answersView);
        answersView.setText(0 + "/" + 0);
        progressBar.setProgress(0);
        list.clear();
        Toast.makeText(getApplicationContext(),"Restarting Test", Toast.LENGTH_SHORT).show();
        initiateGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ishihara);

        final Button btn1 = (Button) findViewById(R.id.btn1);
        final Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setVisibility(View.GONE);
        final Button btn4 = (Button)findViewById(R.id.btn4);
        btn4.setVisibility(View.GONE);
        final Button btn3 = (Button) findViewById(R.id.btn3);
        final Button restart = (Button) findViewById(R.id.infoBtn);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TextView answersView = (TextView) findViewById(R.id.answersView);
        final Drawable d = getResources().getDrawable(R.drawable.restartgame);
        final ImageView mainImage = (ImageView) findViewById(R.id.mainView);

        rand = new Random();
        rndInt = rand.nextInt(17) + 1;
        list = new ArrayList<>();
        //INITIATE GAME
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btn2.setVisibility(View.VISIBLE);
                AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
                mainImage.startAnimation(animation1);

                btn1.setText("");
                btn2.setText("");
                btn3.setText("");
                //btn1.animate().scaleX(0.4f).setDuration(500);
                //btn2.animate().scaleX(0.4f).setDuration(500);
                //btn3.animate().scaleX(0.4f).setDuration(500);
                restart.setBackgroundDrawable(d);
                restart.animate().translationY(-650).setDuration(500);
                progressBar.animate().alpha(1f).setStartDelay(250).setDuration(250);
                answersView.animate().alpha(1f).setStartDelay(250).setDuration(250);
                initiateGame();
            }
        });
        //EXIT GAME
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(IshiharaActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"WeCare, Fitness App for Elderly.", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onBackPressed() {}
}
