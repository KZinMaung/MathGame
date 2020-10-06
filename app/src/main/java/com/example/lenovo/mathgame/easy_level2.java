package com.example.lenovo.mathgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView num1;
    private TextView num2;
    private TextView operatortxt;
    private TextView answer;
    private Button addition;
    private Button subtraction;
    private Button multiplication;
    private Button division;
    private ProgressBar timeLeft;
    private TextView questionLeft;
    private TextView txtScore;
    private static int noOfCallMyCountDownTimer;
    private String correctBtn;
    private int targetScore;
    private long timeRageToGetHighScore=10000;
    private long maxTimeRange=20000;
    private boolean isFast;
    private long RemainingtimeAfterQ1;
    private long RemainingtimeAfterQ2;
    private long RemainingtimeAfterQ3;
    private long RemainingtimeAfterQ4;
    private long RemainingtimeAfterQ5;
    private int markForFastAndCorrect=150;
    private static int score=0;
    private int markForCorrect=100;
    private int markForWrong=-20;
    private int markForFastAndWrong=-50;
    private MyCountDownTimer myCountDownTimer;
    private CountDownTimer countDownTimer;
    private static long remainingtime;
    String operator;
    Boolean canPlayLevel2;
    Bitmap bitmap;
    private static int count;
    public static final int requestCode=1;
    MediaPlayer mysong;
    private LinearLayout easyModeLinearLayout;
    private SoundPlayer sound;
    private boolean IsCompleteShowScoreDialog;
    private static int numOfQuestionLeft;




    int number1, number2, ans;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mysong = MediaPlayer.create(MainActivity.this, R.raw.background_music);
        mysong.start();




        createLevel1();


    }

    protected void onPause() {
        super.onPause();
        mysong.release();

    }


    private void createLevel1() {
        setContentView(R.layout.activity_main);


        addition=(Button)findViewById(R.id.addition);
        subtraction = (Button) findViewById(R.id.subtraction);
        multiplication = (Button) findViewById(R.id.multiplication);
        division = (Button) findViewById(R.id.division);


        timeLeft=(ProgressBar) findViewById(R.id.time_left);
        questionLeft=(TextView) findViewById(R.id.question_left);
        txtScore=(TextView)findViewById(R.id.score);

        easyModeLinearLayout=(LinearLayout) findViewById(R.id.easyModeLinearLayout);


        count=0;
        score=0;
        numOfQuestionLeft=5;
        noOfCallMyCountDownTimer=0;
        createQuestion();

        addition.setOnClickListener (this);
        subtraction.setOnClickListener(this);
        multiplication.setOnClickListener(this);
        division.setOnClickListener(this);




        //questionLeft.setText("QuestionLeft:"+String.valueOf(numOfQuestionLeft+1));
        txtScore.setText("Score:"+String.valueOf(score));

        sound = new SoundPlayer(this);//for sound effect

    }

    private void createQuestion(){
        count++;
        numOfQuestionLeft--;
        noOfCallMyCountDownTimer+=1;
        questionLeft.setText("QuestionLeft:" + String.valueOf(numOfQuestionLeft));

        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
        }

        myCountDownTimer = new MyCountDownTimer(maxTimeRange, 1000);
        myCountDownTimer.start();

        Random r = new Random();

        number1 = r.nextInt(20)-10;
        while(number1==0)                   //re-random if the randomed number is zero
            number1=r.nextInt(20)-10;


        operator=  operatorGenerator();

        number2 = r.nextInt(20)-10;
        while (number2==0)
            number2=r.nextInt(20)-10;

        num1 = (TextView) findViewById(R.id.num1);
        num1.setText("" + number1);

        operatortxt = (TextView) findViewById(R.id.operator);
        operatortxt.setText("?");

        num2 = (TextView) findViewById(R.id.num2);
        num2.setText("" + number2);

        answer = (TextView) findViewById(R.id.answer);
        answer.setText(String.valueOf(answerGenerator()));


    }


    private int answerGenerator() {

        switch (operator) {
            case "+":
                ans = number1 + number2;
                return ans;

            case "-":
                ans = number1 - number2;
                return ans;

            case "*":
                ans = number1 *(number2);
                return ans;

            case "/":
                ans = number1 / number2;
                return ans;

            default:
                return 0;


        }

    }

    private String operatorGenerator() {
        Random r = new Random();
        int op = r.nextInt(3);

        switch (op){
            case 0:operator="+";
                break;
            case 1:operator="-";
                break;
            case 2:operator="*";
                break;
            case 3:operator="/";
                break;
        }

        return operator;



     /*   if (op == 0)
            operator = "+";

        else if (op == 1)
            operator = "-";

        else if (op == 2)
            operator = "*";

        else if (op == 3)
            operator = "/";

        return operator;
       */

    }


    public class MyCountDownTimer extends CountDownTimer{
        public MyCountDownTimer(long millisInFuture,long countDownInterval) {
            super(millisInFuture, countDownInterval);
            //noOfCallMyCountDownTimer+=1;
            //count++;

        }

        int progress=20;
        @Override
        public void onTick(long millisUntilFinished) {
            progress--;
            timeLeft.setProgress(progress);

        }

        @Override
        public void onFinish() {
            //stop the music



            if(count>0 && count<5){

                Snackbar.make(easyModeLinearLayout, "Time Out for this Question!!", Snackbar.LENGTH_SHORT).show();
                createQuestion();
            }


            else if(count==5) {

                questionLeft.setText("QuestionLeft:" + numOfQuestionLeft);
                onPause();
                myCountDownTimer.cancel();
                mysong.release();

                if (!IsCompleteShowScoreDialog) {

                    Snackbar.make(easyModeLinearLayout, "Time Out for this Question!!", Snackbar.LENGTH_SHORT).show();


                    AlertDialog.Builder timeOutAlert = new AlertDialog.Builder(MainActivity.this);
                    // Set the dialog title
                    timeOutAlert.setTitle("Result!");
                    timeOutAlert.setMessage("Your Score:" + score);
                    timeOutAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    timeOutAlert.show();

                }
            }



        }



    }



    @Override
    public void onClick(View view) {

        //fast or slow
        if((maxTimeRange-remainingtime)>=timeRageToGetHighScore)
            isFast=true;
        else
            isFast=false;


        Button btnObj = (Button) view;
        calculateScore(btnObj);
        if(count>0 && count<5){
            createQuestion();
        }

        else
        {
            //calculateScore(btnObj);
            if(isWin()){
                //call windialog
                canPlayLevel2=true;

                new FancyGifDialog.Builder(this)
                        .setTitle("You Win!!!")
                        .setMessage("You Get "+score+" Points")
                        .setNegativeBtnBackground("#FFA9A7A8")
                        .setNegativeBtnText("BACK")
                        .setPositiveBtnBackground("#FF4081")
                        .setPositiveBtnText("NEXT")
                        .setGifResource(R.drawable.wingif)   //Pass your Gif here
                        .isCancellable(true)
                        .OnPositiveClicked(new FancyGifDialogListener(){

                            @Override
                            public void OnClick(){

                                Toast.makeText(MainActivity.this,"Go to level 2",Toast.LENGTH_SHORT).show();

                            }

                        })

                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {




                                Intent intent=new Intent(MainActivity.this,easy_levels.class);
                                startActivity(intent);


                            }
                        })
                        .build();
                IsCompleteShowScoreDialog=true;
                myCountDownTimer.cancel();
                mysong.release();



                //set Buttton with level2


            }
            else{
                //call loseDialog

                canPlayLevel2=false;

                new FancyGifDialog.Builder(this)
                        .setTitle("You Lost!!!")
                        .setNegativeBtnBackground("#FFA9A7A8")
                        .setNegativeBtnText("CANCEL")
                        .setPositiveBtnBackground("#FF4081")
                        .setPositiveBtnText("RETRY")
                        .setGifResource(R.drawable.failgif2)   //Pass your Gif here
                        .isCancellable(true)
                        .OnPositiveClicked(new FancyGifDialogListener(){

                            @Override
                            public void OnClick(){

                                Toast.makeText(MainActivity.this,"Go to level 2",Toast.LENGTH_SHORT).show();

                            }

                        })

                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {




                                Intent intent=new Intent(MainActivity.this,easy_levels.class);
                                startActivity(intent);


                            }
                        })
                        .build();
                IsCompleteShowScoreDialog=true;
                myCountDownTimer.cancel();
                mysong.release();
            }


            //stop music
            onPause();




        }

    }
    private boolean isWin () {
        if (score >= targetScore) {
            return true;
        } else
            return false;


    }

    private void calculateScore(View v) {

        String toastText;
        Button btnObj=(Button)v;
        checkAnswer();
        switch (btnObj.getId()) {
            case R.id.addition:
                //checkAnswer();
                if (correctBtn.equalsIgnoreCase("addition")){ //if correct,check fast or not

                    if(isFast){
                        score+=markForFastAndCorrect;
                        toastText="+"+String.valueOf(markForFastAndCorrect);
                    }


                    else{
                        score+=markForCorrect;
                        toastText="+"+String.valueOf(markForCorrect);
                    }
                    sound.playCorrectSound();



                }

                else{ //if wrong,check fast or not

                    if(isFast){
                        score+=markForFastAndWrong;
                        toastText=String.valueOf(markForFastAndWrong);
                    }

                    else{
                        score+=markForWrong;
                        toastText=String.valueOf(markForWrong);
                    }
                    sound.playWrongSound();

                }

                Toast toastForBtn1 = Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT);
                toastForBtn1.show();
                // createQuestion();
                break;

            case R.id.subtraction:

                if (correctBtn.equalsIgnoreCase("subtraction")) { //if correct,check fast or not
                    if(isFast){
                        score+=markForFastAndCorrect;
                        toastText="+"+String.valueOf(markForFastAndCorrect);
                    }


                    else{
                        score+=markForCorrect;
                        toastText="+"+String.valueOf(markForCorrect);
                    }
                    sound.playCorrectSound();


                }
                else{ //if wrong,check fast or not
                    if(isFast){
                        score+=markForFastAndWrong;
                        toastText=String.valueOf(markForFastAndWrong);
                    }

                    else{
                        score+=markForWrong;
                        toastText=String.valueOf(markForWrong);
                    }
                    sound.playWrongSound();
                }

                Toast toastForBtn2 = Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT);
                toastForBtn2.show();
                //createQuestion();
                break;

            case R.id.multiplication:

                if (correctBtn.equalsIgnoreCase("multiplication")) {
                    if(isFast){
                        score+=markForFastAndCorrect;
                        toastText="+"+String.valueOf(markForFastAndCorrect);
                    }


                    else{
                        score+=markForCorrect;
                        toastText="+"+String.valueOf(markForCorrect);
                    }
                    sound.playCorrectSound();


                }
                else
                {
                    if(isFast){
                        score+=markForFastAndWrong;
                        toastText=String.valueOf(markForFastAndWrong);
                    }

                    else{
                        score+=markForWrong;
                        toastText=String.valueOf(markForWrong);
                    }
                    sound.playWrongSound();
                }
                Toast toastForBtn3 = Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT);
                toastForBtn3.show();
                // createQuestion();
                break;


            case R.id.division:

                if (correctBtn.equalsIgnoreCase("division")){
                    if(isFast){
                        score+=markForFastAndCorrect;
                        toastText="+"+String.valueOf(markForFastAndCorrect);
                    }


                    else{
                        score+=markForCorrect;
                        toastText="+"+String.valueOf(markForCorrect);
                    }
                    sound.playCorrectSound();


                }

                else
                {
                    if(isFast){
                        score+=markForFastAndWrong;
                        toastText=String.valueOf(markForFastAndWrong);
                    }

                    else{
                        score+=markForWrong;
                        toastText=String.valueOf(markForWrong);
                    }
                    sound.playWrongSound();
                }
                Toast toastForBtn4 = Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT);
                toastForBtn4.show();
                //createQuestion();
                break;


        }//end of switch
        txtScore.setText(String.valueOf(score));

    }

    private String checkAnswer(){

        switch (operator){
            case "+":
                correctBtn="addition";
                break;

            case "-":
                correctBtn="subtraction";
                break;

            case "*":
                correctBtn="multiplication";
                break;

            case "/":
                correctBtn="division";
                break;


        }
        return correctBtn;

    }

}






