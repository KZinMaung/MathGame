package com.example.lenovo.mathgame;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;




import android.text.Html;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.lang.annotation.Target;
import java.util.Random;

public class hard_mode extends AppCompatActivity implements View.OnClickListener {
    private Button btnGoFirst;
    private Button btnGoSecond;
    private Button btnGoThird;
    private Button btnGoFourth;
    private Button btnGoPlus;
    private Button btnGoMinus;
    private Button btnGoMultiply;
    private Button btnGoDivision;
    private Button btnGoReset;
    private TextView txtTarget;
    private TextView txtTimeLeft;
    private ProgressBar timeLeft;
    private TextView txtQuestionLeft;
    private TextView txtScore;
    private static int count=0;
    int questioncount = 1;
    private static  int  noOfQuestionLeft ;
    private long maxTimeRange = 20000;
    private long fastTime = 5000;
    private long remainingtime;
    private long remainingTimeQ5;
    private long remainingTimeQ4;
    private long remainingTimeQ3;
    private long remainingTimeQ2;
    private long remainingTimeQ1;
    private boolean isFast;
    private boolean right;
    private boolean isFastWrong;
    private boolean wrong;
    private CountDownTimer countDownTimer;
    private int operand1;
    private int operand2;
    private int target = 0;
    private String operator;
    private String[] operatorArr = {"+", "-", "*"};
    private String correctBtn;
    private boolean IsCompleteShowScoreDialog;
    private String ansOperator;
    private int ansOperand1;
    private int ansOperand2;
    private int targetScore = 300;
    private int markForFastAndCorrect = 150;
    private static int score = 0;
    private int markForCorrect = 100;
    private int markForWrong = -20;
    private int markForFastAndWrong = -50;
    private static int btnCount;
    private SoundPlayer sound;
    private boolean isClearOperandsAndOperator;
    MediaPlayer mysong;
    boolean isSetAnsOperand1;
    private LinearLayout linearLayoutForHardMode;
    private int noOfCallMyCountDownTimer;
    private MyCountDownTimer1 myCountDownTimer;
    private long timeRageToGetHighScore = 10000;
    private boolean isSetAnsOperator;
    private TextView txtFirstOperand;
    private TextView txtSecondOperand;
    private TextView txtOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mysong = MediaPlayer.create(hard_mode.this, R.raw.background_music);
        mysong.start();
        createLevel1();
    }
    protected void onPause() {
        super.onPause();
        mysong.release();

    }
    private void createLevel1() {
        setContentView(R.layout.activity_hard_mode);


        btnGoPlus = (Button) findViewById(R.id.btnGoPlus);
        btnGoMinus = (Button) findViewById(R.id.btnGoMinus);
        btnGoMultiply = (Button) findViewById(R.id.btnGoMultiply);
        btnGoDivision = (Button) findViewById(R.id.btnGoDivision);
        btnGoFirst = (Button) findViewById(R.id.btnGoFirst);
        btnGoSecond = (Button) findViewById(R.id.btnGoSecond);
        btnGoThird = (Button) findViewById(R.id.btnGoThird);
        btnGoFourth = (Button) findViewById(R.id.btnGoFourth);
        txtScore = (TextView) findViewById(R.id.score);
        txtQuestionLeft = (TextView) findViewById(R.id.question_left);
        timeLeft = (ProgressBar) findViewById(R.id.time_left);
        txtTarget = (TextView) findViewById(R.id.txt_target);
        linearLayoutForHardMode = (LinearLayout) findViewById(R.id.linearLayoutForHardMode);
        btnGoReset=(Button) findViewById(R.id.btnGoReset);
        txtFirstOperand=(TextView)findViewById(R.id.txtFirstOperand);
        txtSecondOperand=(TextView)findViewById(R.id.txtSecondOperand);
        txtOperator=(TextView)findViewById(R.id.txtOperator);

        count = 0;
        score = 0;
        noOfQuestionLeft = 5;
        createQuestion();


        btnGoPlus.setOnClickListener(this);
        btnGoMinus.setOnClickListener(this);
        btnGoMultiply.setOnClickListener(this);
        btnGoDivision.setOnClickListener(this);
        btnGoFirst.setOnClickListener(this);
        btnGoSecond.setOnClickListener(this);
        btnGoThird.setOnClickListener(this);
        btnGoFourth.setOnClickListener(this);
        btnGoReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInputs();
                Button reset = (Button) v;
                int id=reset.getId();
                if(id==R.id.btnGoReset) {
                    Toast.makeText(getApplicationContext(), "Reset button is selected!", Toast.LENGTH_SHORT).show();
                    resetInputs();

                }

            }
        });

        txtScore.setText("Score:" + String.valueOf(score));


        sound = new SoundPlayer(this);//for sound effect

        //for playing background sound
    }
    private void resetInputs() {
        ansOperator = " ";
        ansOperand1 = 0;
        ansOperand2 = 0;
        isSetAnsOperand1 = false;
        isSetAnsOperator = false;
        txtFirstOperand.setText(" ");
        txtOperator.setText(" ");
        txtSecondOperand.setText(" ");
        btnCount = 0;

    }
    private void createQuestion() {
        resetInputs();
        noOfCallMyCountDownTimer++;
        count++;
        noOfQuestionLeft--;
        txtQuestionLeft.setText("QuestionLeft:" + String.valueOf(noOfQuestionLeft));

        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
        }
        myCountDownTimer = new MyCountDownTimer1(maxTimeRange, 1000);
        myCountDownTimer.start();


        Random r = new Random();
        operand1 = r.nextInt(20) - 10;
        while (operand1 == 0)
            operand1 = r.nextInt(20) - 10;

        operand2 = r.nextInt(20) - 10;
        while (operand2 == 0)
            operand2 = r.nextInt(20) - 10;

        operator = operatorArr[r.nextInt(3)];
        switch (operator) {
            case "+":
                target = (operand1) + (operand2);
                break;

            case "-":
                target = (operand1) - (operand2);
                break;

            case "*":
                target = (operand1) * (operand2);
                break;


        }
        txtTarget.setText(String.valueOf(target));
      /*  Snackbar.make(coordinatorLayoutForHardMode,"Operand1:"+operand1+"  Operand2:"+operand2+" Target:"+target, Snackbar.LENGTH_LONG)
                .show();
*/


        setBtnWithRandomAns(operand1, operand2, target, operator);


    }

    private void setBtnWithRandomAns(int ans1, int ans2, int target, String operator) {


        Random r1 = new Random();
        int where1 = r1.nextInt(4) + 1;
        int where2 = r1.nextInt(4) + 1;
        while (where2 == where1)
            where2 = r1.nextInt(4) + 1;
        String where = String.valueOf(where1) + " " + String.valueOf(where2);

        // where=String.valueOf(where1)+ String.valueOf(where2);
        Log.i("PROCESS : ", where);

        int value1 = r1.nextInt(20) - 10;//coz containg answer as parameter is within this range

        while (value1 == ans1 || value1 == ans2)
            value1 = r1.nextInt(20) - 10;

        int value2 = r1.nextInt(20) - 10;

        while (value2 == ans1 || value2 == ans2 || value2 == value1)
            value2 = r1.nextInt(20) - 10;


        int ansTarget = 0;
        switch (operator) {
            case "+":
                ansTarget = value1 + value2;
                break;
            case "-":
                ansTarget = value1 - value2;
                break;
            case "*":
                ansTarget = value1 * value2;
                break;
        }
        while (ansTarget == target) {
            value1 = r1.nextInt(20) - 10;//coz containg answer as parameter is within this range

            while (value1 == ans1 || value1 == ans2)
                value1 = r1.nextInt(20) - 10;

            value2 = r1.nextInt(20) - 10;

            while (value2 == ans1 || value2 == ans2 || value2 == value1)
                value2 = r1.nextInt(20) - 10;

        }


        switch (where) {
            case "1 2":
                btnGoFirst.setText(String.valueOf(ans1));
                btnGoSecond.setText(String.valueOf(ans2));
                btnGoThird.setText(String.valueOf(value1));
                btnGoFourth.setText(String.valueOf(value2));

                correctBtn = "btnGoFirst,btnGoSecond";  //note button of correct answer

                break;
            case "1 3":
                btnGoFirst.setText(String.valueOf(ans1));
                btnGoSecond.setText(String.valueOf(value1));
                btnGoThird.setText(String.valueOf(ans2));
                btnGoFourth.setText(String.valueOf(value2));

                correctBtn = "btnGoFirst,btnGoThird";  //note button of correct answer

                break;
            case "1 4":
                btnGoFirst.setText(String.valueOf(ans1));
                btnGoSecond.setText(String.valueOf(value1));
                btnGoThird.setText(String.valueOf(value2));
                btnGoFourth.setText(String.valueOf(ans2));

                correctBtn = "btnGoFirst,btnGoFourth";  //note button of correct answer

                break;
            case "2 1":
                btnGoFirst.setText(String.valueOf(ans2));
                btnGoSecond.setText(String.valueOf(ans1));
                btnGoThird.setText(String.valueOf(value1));
                btnGoFourth.setText(String.valueOf(value2));

                correctBtn = "btnGoSecond,btnGoFirst";  //note button of correct answer

                break;
            case "2 3":
                btnGoFirst.setText(String.valueOf(value1));
                btnGoSecond.setText(String.valueOf(ans1));
                btnGoThird.setText(String.valueOf(ans2));
                btnGoFourth.setText(String.valueOf(value2));

                correctBtn = "btnGoSecond,btnGoThird";  //note button of correct answer

                break;
            case "2 4":
                btnGoFirst.setText(String.valueOf(value1));
                btnGoSecond.setText(String.valueOf(ans1));
                btnGoThird.setText(String.valueOf(value2));
                btnGoFourth.setText(String.valueOf(ans2));

                correctBtn = "btnGoSecond,btnGoFourth";  //note button of correct answer

                break;
            case "3 1":
                btnGoFirst.setText(String.valueOf(ans2));
                btnGoSecond.setText(String.valueOf(value1));
                btnGoThird.setText(String.valueOf(ans1));
                btnGoFourth.setText(String.valueOf(value2));

                correctBtn = "btnGoThird,btnGoFirst";  //note button of correct answer

                break;
            case "3 2":
                btnGoFirst.setText(String.valueOf(value1));
                btnGoSecond.setText(String.valueOf(ans2));
                btnGoThird.setText(String.valueOf(ans1));
                btnGoFourth.setText(String.valueOf(value2));

                correctBtn = "btnGoThird,btnGoSecond";  //note button of correct answer

                break;
            case "3 4":
                btnGoFirst.setText(String.valueOf(value1));
                btnGoSecond.setText(String.valueOf(value2));
                btnGoThird.setText(String.valueOf(ans1));
                btnGoFourth.setText(String.valueOf(ans2));

                correctBtn = "btnGoThird,btnGoFourth";  //note button of correct answer

                break;
            case "4 1":
                btnGoFirst.setText(String.valueOf(ans1));
                btnGoSecond.setText(String.valueOf(value1));
                btnGoThird.setText(String.valueOf(value2));
                btnGoFourth.setText(String.valueOf(ans2));

                correctBtn = "btnGoFourth,btnGoFirst";  //note button of correct answer

                break;
            case "4 2":
                btnGoFirst.setText(String.valueOf(value1));
                btnGoSecond.setText(String.valueOf(ans2));
                btnGoThird.setText(String.valueOf(value2));
                btnGoFourth.setText(String.valueOf(ans1));

                correctBtn = "btnGoFourth,btnGoSecond";  //note button of correct answer

                break;
            case "4 3":
                btnGoFirst.setText(String.valueOf(value1));
                btnGoSecond.setText(String.valueOf(value2));
                btnGoThird.setText(String.valueOf(ans2));
                btnGoFourth.setText(String.valueOf(ans1));

                correctBtn = "btnGoFourth,btnGoThird";  //note button of correct answer
                break;


        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class MyCountDownTimer1 extends CountDownTimer {

        public MyCountDownTimer1(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

        }

        int progress = 20;

        @Override
        public void onTick(long millisUntilFinished) {
            progress--;
            timeLeft.setProgress(progress);

        }

        @Override
        public void onFinish() {
            //stop the music
            if (count > 0 && count < 5) {

                Snackbar.make(linearLayoutForHardMode, "Time Out for this Question!!", Snackbar.LENGTH_SHORT).show();
                createQuestion();
            }
            else if (count == 5) {

                txtQuestionLeft.setText("QuestionLeft:" + noOfQuestionLeft);
                onPause();
                myCountDownTimer.cancel();
                mysong.release();

                if (!IsCompleteShowScoreDialog) {

                    Snackbar.make(linearLayoutForHardMode, "Time Out for this Question!!", Snackbar.LENGTH_SHORT).show();


                    AlertDialog.Builder timeOutAlert = new AlertDialog.Builder(hard_mode.this);
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
        btnCount++;

        Button btnObj = (Button) view;
        String toastTextForOperator="Please choose firstOperand!";
        String toastTextForOperand="Please choose Operator first!";
        Toast toast;

        switch (btnObj.getId()) {
            case R.id.btnGoPlus:
                if(isSetAnsOperand1) {
                    ansOperator = "+";
                    isSetAnsOperator=true;

                    txtOperator.setText(ansOperator);
                }
                else{
                    btnCount--;
                    toast = Toast.makeText(this, toastTextForOperator, Toast.LENGTH_SHORT);

                    toast.show();

                }

                break;
            case R.id.btnGoMinus:
                if(isSetAnsOperand1) {
                    ansOperator = "-";
                    isSetAnsOperator=true;
                    txtOperator.setText(ansOperator);
                }
                else{
                    btnCount--;
                    toast = Toast.makeText(this, toastTextForOperator, Toast.LENGTH_SHORT);
                    toast.show();

                }
                break;
            case R.id.btnGoMultiply:
                if(isSetAnsOperand1) {
                    ansOperator = "*";
                    isSetAnsOperator=true;
                    txtOperator.setText(ansOperator);
                }
                else{

                    btnCount--;
                    toast = Toast.makeText(this, toastTextForOperator, Toast.LENGTH_SHORT);
                    toast.show();

                }
                break;
            case R.id.btnGoDivision:
                if(isSetAnsOperand1) {
                    ansOperator = "*";
                    isSetAnsOperator=true;
                    txtOperator.setText(ansOperator);
                }
                else{
                    btnCount--;
                    toast = Toast.makeText(this, toastTextForOperator, Toast.LENGTH_SHORT);
                    toast.show();

                }
                break;
            case R.id.btnGoFirst:
                if (!isSetAnsOperand1) {
                    ansOperand1 = Integer.parseInt((String) btnGoFirst.getText());
                    isSetAnsOperand1 = true;
                    txtFirstOperand.setText(String.valueOf(ansOperand1));
                }
                else if(isSetAnsOperator)
                {
                    ansOperand2 = Integer.parseInt((String) btnGoFirst.getText());
                    txtSecondOperand.setText(String.valueOf(ansOperand2));

                }
                else{
                    btnCount--;
                    toast = Toast.makeText(this, toastTextForOperand, Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
            case R.id.btnGoSecond:
                if (!isSetAnsOperand1) {
                    ansOperand1 = Integer.parseInt((String) btnGoSecond.getText());
                    isSetAnsOperand1 = true;
                    txtFirstOperand.setText(String.valueOf(ansOperand1));
                }
                else if(isSetAnsOperator)
                {
                    ansOperand2 = Integer.parseInt((String) btnGoSecond.getText());
                    txtSecondOperand.setText(String.valueOf(ansOperand2));

                }
                else{
                    btnCount--;
                    toast = Toast.makeText(this, toastTextForOperand, Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
            case R.id.btnGoThird:
                if (!isSetAnsOperand1) {
                    ansOperand1 = Integer.parseInt((String) btnGoThird.getText());
                    isSetAnsOperand1 = true;
                    txtFirstOperand.setText(String.valueOf(ansOperand1));
                }
                else if(isSetAnsOperator)
                {
                    ansOperand2 = Integer.parseInt((String) btnGoThird.getText());
                    txtSecondOperand.setText(String.valueOf(ansOperand2));

                }
                else{
                    btnCount--;
                    toast = Toast.makeText(this, toastTextForOperand, Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
            case R.id.btnGoFourth:
                if (!isSetAnsOperand1) {
                    ansOperand1 = Integer.parseInt((String) btnGoFourth.getText());
                    isSetAnsOperand1 = true;
                    txtFirstOperand.setText(String.valueOf(ansOperand1));
                }
                else if(isSetAnsOperator)
                {
                    ansOperand2 = Integer.parseInt((String) btnGoFourth.getText());
                    txtSecondOperand.setText(String.valueOf(ansOperand2));

                }
                else{
                    btnCount--;
                    toast = Toast.makeText(this, toastTextForOperand, Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;



        }
        if (btnCount == 3 || btnCount == 6 || btnCount == 9 || btnCount == 12 || btnCount == 15) {


            //fast or slow
            if ((maxTimeRange - remainingtime) >= timeRageToGetHighScore)
                isFast = true;
            else
                isFast = false;

            calculateScore(ansOperand1,ansOperand2,ansOperator);
            if (count > 0 && count < 5) {
                createQuestion();
            } else {
                calculateScore(ansOperand1,ansOperand2,ansOperator);
                if (isWin()) {
                    //call windialog
                } else {
                    //call loseDialog
                }


                //stop music
                onPause();


                AlertDialog.Builder showFinalScore = new AlertDialog.Builder(hard_mode.this);
                // Set the dialog title
                showFinalScore.setTitle(" ");
                showFinalScore.setMessage("Your Final Score :" + score);
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
                IsCompleteShowScoreDialog = true;
                myCountDownTimer.cancel();
                mysong.release();


            }


            // txtQuestionLeft.setText("QuestionLeft:" + String.valueOf(noOfQuestionLeft));


        }
    }
    private boolean isWin () {
        if (score >= targetScore) {
            return true;
        } else
            return false;


    }

    private void calculateScore (int ansOperand1,int ansOperand2,String ansOperator){

        //calculate user's ans
        int ansTarget = 0;
        switch (ansOperator) {
            case "+":
                ansTarget = (ansOperand1) + (ansOperand2);
                break;
            case "-":
                ansTarget = (ansOperand1) - (ansOperand2);
                break;
            case "*":
                ansTarget = (ansOperand1) * (ansOperand2);
                break;
            case "/":
                ansTarget = (ansOperand1) / (ansOperand2);
                break;

        }
        // Snackbar.make(coordinatorLayoutForHardMode, "ansTarget:" + ansTarget, Snackbar.LENGTH_SHORT).show();
        //check ans is correct or wrong

        String toastText;
        if (ansTarget == target) {
            if (isFast) {
                score += markForFastAndCorrect;
                toastText = "+" + String.valueOf(markForFastAndCorrect);
            } else {
                score += markForCorrect;
                toastText = "+" + String.valueOf(markForCorrect);
            }
        } else {
            if (isFast) {
                score += markForFastAndWrong;
                toastText = String.valueOf(markForFastAndWrong);
            } else {
                score += markForWrong;
                toastText = String.valueOf(markForWrong);
            }


        }
        Toast toast = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);

        toast.show();


        txtScore.setText("Score:" + String.valueOf(score));

        operand1 = 0;
        operand2 = 0;
        operator = "";
        isSetAnsOperand1 = false;
        isSetAnsOperator=false;



        //isClearOperandsAndOperator=true;


    }



}
