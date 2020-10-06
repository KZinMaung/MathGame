package com.example.lenovo.mathgame;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX=2;

    private static SoundPool soundPool;
    private static int correctSound;
    private static int wrongSound;

    public SoundPlayer(Context context){


        //soundpool is deprecated in API level21(lollipop)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            audioAttributes=new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool=new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }
        else{
            soundPool=new SoundPool(2,AudioManager.STREAM_MUSIC,0);
        }
        correctSound=soundPool.load(context,R.raw.correct_sound,2);
        wrongSound=soundPool.load(context,R.raw.wrong_sound,2);


    }
    public void playCorrectSound(){
        soundPool.play(correctSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playWrongSound(){
        soundPool.play(wrongSound,1.0f,1.0f,1,0,1.0f);
    }
}
