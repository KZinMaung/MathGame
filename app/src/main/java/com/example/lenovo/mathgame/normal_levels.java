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

public class normal_levels extends AppCompatActivity implements View.OnClickListener{
    ImageView btn2;
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_levels);




    }

    @Override
    protected void onStop() {
        super.onStop();
        setContentView(R.layout.activity_normal_levels);
    }



    public void onClick(View view){
        ImageButton btnObj=(ImageButton) view;
        Intent intent;
        switch (btnObj.getId()){
            case R.id.btn1:
                intent=new Intent(normal_levels.this,normal_mode.class);
                startActivity(intent);

                break;


        }
    }







}
