package com.example.lenovo.mathgame;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;


public class normal_mode extends AppCompatActivity implements View.OnClickListener {

    private TextView txtOperand1;
    private TextView txtOperand2;
    private TextView txtOperator;
    private TextView txtTarget;
    private Button btnAns1;
    private Button btnAns2;
    private Button btnAns3;
    private Button btnAns4;
    private ProgressBar timeLeft;
    private TextView questionLeft;
    private TextView txtScore;
    private int target;
    private int operand1;
    private int operand2;
    private String operator;
    private static int count = 0;
    private int numOfQuestionLeft;
    private static int noOfCallMyCountDownTimer;
    int temp = 0;

    private String[] operatorArr = {"+", "-", "*"};
    private int whereBlank;
    private String correctBtn;
    private static long remainingtime;
    private MyCountDownTimer myCountDownTimer;
    private long timeRageToGetHighScore = 10000;
    private int maxTimeRange = 20000;
    private boolean isFast;
    private long RemainingtimeAfterQ1;
    private long RemainingtimeAfterQ2;
    private long RemainingtimeAfterQ3;
    private long RemainingtimeAfterQ4;
    private long RemainingtimeAfterQ5;
    private int markForFastAndCorrect = 150;
    private static int score = 0;
    private int markForCorrect = 100;
    private int markForWrong = -20;
    private int markForFastAndWrong = -50;
    private CountDownTimer countDownTimer;
    private int targetScore = 300;
    private SoundPlayer sound;
    MediaPlayer mysong;
    private boolean IsCompleteShowScoreDialog;
    private LinearLayout normalModeLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_mode);

        mysong = MediaPlayer.create(normal_mode.this, R.raw.background_music);
        mysong.start();


        try {
            createLevel1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onPause() {
        super.onPause();
        mysong.release();

    }

    private void createLevel1() throws IOException {
        setContentView(R.layout.activity_normal_mode);


        txtOperand1 = (TextView) findViewById(R.id.txt_operand1);
        txtOperand2 = (TextView) findViewById(R.id.txt_operand2);
        txtOperator = (TextView) findViewById(R.id.txt_operator);
        txtTarget = (TextView) findViewById(R.id.txt_target);
        btnAns1 = (Button) findViewById(R.id.btn_ans1);
        btnAns2 = (Button) findViewById(R.id.btn_ans2);
        btnAns3 = (Button) findViewById(R.id.btn_ans3);
        btnAns4 = (Button) findViewById(R.id.btn_ans4);
        timeLeft = (ProgressBar) findViewById(R.id.time_left);
        questionLeft = (TextView) findViewById(R.id.question_left);
        txtScore = (TextView) findViewById(R.id.score);
        normalModeLinearLayout=(LinearLayout)findViewById(R.id.normalModeLinearLayout);

        count = 0;
        score = 0;

        numOfQuestionLeft = 5;
        createQuestion();


        btnAns1.setOnClickListener(this);
        btnAns2.setOnClickListener(this);
        btnAns3.setOnClickListener(this);
        btnAns4.setOnClickListener(this);





        //questionLeft.setText("QuestionLeft:" + String.valueOf(numOfQuestionLeft + 1));
        txtScore.setText("Score:" + String.valueOf(score));


        sound = new SoundPlayer(this);//for sound effect

        //for playing background sound


    }

    public  class  MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

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

                Snackbar.make(normalModeLinearLayout, "Time Out for this Question!!", Snackbar.LENGTH_SHORT).show();
                createQuestion();
            }


            else if(count==5) {

                questionLeft.setText("QuestionLeft:" + numOfQuestionLeft);
                onPause();
                myCountDownTimer.cancel();
                mysong.release();

                if (!IsCompleteShowScoreDialog) {

                    Snackbar.make(normalModeLinearLayout, "Time Out for this Question!!", Snackbar.LENGTH_SHORT).show();


                    AlertDialog.Builder timeOutAlert = new AlertDialog.Builder(normal_mode.this);
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

    public void onClick(android.view.View view){




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
            calculateScore(btnObj);
            if(isWin()){
                //call windialog
            }
            else{
                //call loseDialog
            }


            //stop music
            onPause();


            AlertDialog.Builder showFinalScore = new AlertDialog.Builder(normal_mode.this);
            // Set the dialog title
            showFinalScore.setTitle(" ");
            showFinalScore.setMessage("Your Final Score :"+score);
            showFinalScore.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //Retry
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            showFinalScore.show();
            IsCompleteShowScoreDialog=true;
            myCountDownTimer.cancel();
            mysong.release();








        }








    }

    private void calculateScore(Button btnObj) {


        String toastText;
        switch (btnObj.getId()) {
            case R.id.btn_ans1:
                if (correctBtn.equalsIgnoreCase("btnAns1")){

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

                else{

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

                Toast toastForBtn1 = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);
                toastForBtn1.show();
                // createQuestion();
                break;

            case R.id.btn_ans2:
                if (correctBtn.equalsIgnoreCase("btnAns2")) {
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
                else{
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

                Toast toastForBtn2 = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);
                toastForBtn2.show();
                // createQuestion();
                break;

            case R.id.btn_ans3:
                if (correctBtn.equalsIgnoreCase("btnAns3")) {
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
                Toast toastForBtn3 = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);
                toastForBtn3.show();
                //createQuestion();
                break;


            case R.id.btn_ans4:
                if (correctBtn.equalsIgnoreCase("btnAns4")){
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
                Toast toastForBtn4 = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);
                toastForBtn4.show();
                //createQuestion();
                break;


        }
        txtScore.setText(String.valueOf(score));


    }

    private boolean isWin() {

        if(score>=targetScore){
            return true;
        }
        else
            return false;




    }


    private void congrazForBtn1() {
        btnAns1.setOnClickListener(this);
    }


    private void createQuestion() {

        noOfCallMyCountDownTimer++;
        count++;
        numOfQuestionLeft--;
        questionLeft.setText("QuestionLeft:" + String.valueOf(numOfQuestionLeft ));

        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
        }
        myCountDownTimer = new MyCountDownTimer(maxTimeRange, 1000);
        myCountDownTimer.start();




        Random r=new Random();

        operand1=r.nextInt(20)-10;//get random operand1
        while(operand1==0)
            operand1=r.nextInt(20)-10;

        operand2=r.nextInt(20)-10;//get random operand2
        while (operand2==0)
            operand2=r.nextInt(20)-10;

        operator = operatorArr[r.nextInt(3)];
        switch (operator){
            case "+":target=operand1+operand2;
                break;

            case "-":target=operand1-operand2;
                break;

            case "*":target=operand1*operand2;
                break;


        }

        txtTarget.setText(String.valueOf(target));
       /* Snackbar.make(normalModeLinearLayout,"Operand1:"+operand1+"Operand2:"+operand2+"Target:"+target, Snackbar.LENGTH_LONG)
                .show();*/


        whereBlank=r.nextInt(3)+1;

        switch (whereBlank){
            case 1: BlankOperand1();
                break;
            case 2: BlankOperand2();
                break;
            case 3: BlankOperator();
                break;
        }





    }

    private void setBtnWithRandomAns(int ans) {
        Random r1=new Random();
        int where=r1.nextInt(4)+1;


        int value1=r1.nextInt(20)-10;//coz containg answer as parameter is within this range

        while(value1==ans)
            value1=r1.nextInt(20)-10;

        int value2=r1.nextInt(20)-10;

        while(value2==ans||value2==value1)
            value2=r1.nextInt(20)-10;

        int value3=r1.nextInt(20)-10;

        while(value3==ans || value3==value1 || value3==value2)
            value3=r1.nextInt(20)-10;


        switch (where){
            case 1:btnAns1.setText(String.valueOf(ans));
                btnAns2.setText(String.valueOf(value1));
                btnAns3.setText(String.valueOf(value2));
                btnAns4.setText(String.valueOf(value3));
                correctBtn="btnAns1";  //note button of correct answer

                break;

            case 2:btnAns2.setText(String.valueOf(ans));
                btnAns1.setText(String.valueOf(value1));
                btnAns3.setText(String.valueOf(value2));
                btnAns4.setText(String.valueOf(value3));
                correctBtn="btnAns2";
                break;

            case 3:btnAns3.setText(String.valueOf(ans));
                btnAns2.setText(String.valueOf(value1));
                btnAns1.setText(String.valueOf(value2));
                btnAns4.setText(String.valueOf(value3));
                correctBtn="btnAns3";
                break;

            case 4:btnAns4.setText(String.valueOf(ans));
                btnAns2.setText(String.valueOf(value1));
                btnAns3.setText(String.valueOf(value2));
                btnAns1.setText(String.valueOf(value3));
                correctBtn="btnAns4";
                break;

        }
    }




    private void BlankOperator() {
        txtOperand1.setText(String.valueOf(operand1));
        txtOperand2.setText(String.valueOf(operand2));
        txtOperator.setText("?");
        btnAns1.setText("+");
        btnAns2.setText("-");
        btnAns3.setText("*");
        btnAns4.setText("/");

        //note button of correct answer
        switch (operator){
            case "+":correctBtn="btnAns1";
                break;
            case "-":correctBtn="btnAns2";
                break;
            case "*":correctBtn="btnAns3";
                break;
            case "/":correctBtn="btnAns4";
                break;


        }




    }

    private void BlankOperand2() {
        txtOperand1.setText(String.valueOf(operand1));
        txtOperand2.setText("?");
        txtOperator.setText(operator);
        setBtnWithRandomAns(operand2);

    }

    private void BlankOperand1() {
        txtOperand1.setText("?");
        txtOperand2.setText(String.valueOf(operand2));
        txtOperator.setText(operator);
        setBtnWithRandomAns(operand1);
    }




}
