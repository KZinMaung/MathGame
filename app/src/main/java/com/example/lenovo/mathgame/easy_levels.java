package com.example.lenovo.mathgame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class easy_levels extends AppCompatActivity implements View.OnClickListener{
ImageView btn2;
Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_levels);




    }

    @Override
    protected void onStop() {
        super.onStop();
        setContentView(R.layout.activity_easy_levels);
    }



    public void onClick(View view){
        ImageButton btnObj=(ImageButton) view;
Intent intent;
        switch (btnObj.getId()){
            case R.id.btn1:
                 intent=new Intent(easy_levels.this,MainActivity.class);
                startActivity(intent);

                break;

            case R.id.btn2:
                 intent=new Intent(easy_levels.this,easy_level2.class);
                startActivity(intent);

        }
    }







}
