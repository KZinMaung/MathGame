package com.example.lenovo.mathgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class hard_levels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_levels);
    }
    public void onClick(View view) {
        ImageButton btnObj = (ImageButton) view;
        Intent intent;
        switch (btnObj.getId()) {
            case R.id.btn1:
                intent = new Intent(hard_levels.this, hard_mode.class);
                startActivity(intent);

                break;


        }
    }
}
