package com.example.lenovo.mathgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class three_modes extends AppCompatActivity  implements View.OnClickListener{
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_modes);

        Button btnEasy = (Button) findViewById(R.id.btnEasy);
        Button btnNormal = (Button) findViewById(R.id.btnNormal);
        Button btnHard = (Button) findViewById(R.id.btnHard);
    }

    public void onClick(View view){
        Button btnObj = (Button) view;

        switch (btnObj.getId()){

            case R.id.btnEasy:

                intent=new Intent(three_modes.this,easy_levels.class);
                startActivity(intent);break;

            case R.id.btnNormal:
                intent=new Intent(three_modes.this,normal_levels.class);
                startActivity(intent);break;

            case R.id.btnHard:
                intent=new Intent(three_modes.this,hard_levels.class);
                startActivity(intent);break;


        }


    }


}
