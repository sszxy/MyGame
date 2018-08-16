package com.example.mygame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Random;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by 张翔宇 on 2018/6/26.
 */


public class SoundUtils {
    private SoundPool sp;

    private int[] sounds={R.raw.kanong,R.raw.yekong,R.raw.tiankong,R.raw.summer};
    private static SoundUtils soundUtils = new SoundUtils();
    public static SoundUtils getInstance(){
        return soundUtils;
    }

    public void init(Context context) {
        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

            }

        });
        for (int i = 0; i <sounds.length ; i++) {
            sp.load(context,sounds[i],1);
        }
    }
    public void play(){
       // Random random=new Random();

        sp.play(0,1,1,1,0,1);

    }
    public void pause(){
        sp.pause(0);
    }

    public  void release(){
        sp.release();
    }
}
