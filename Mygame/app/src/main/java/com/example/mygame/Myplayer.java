package com.example.mygame;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.Random;

/**
 * Created by 张翔宇 on 2018/6/26.
 */

public class Myplayer {
    private static MediaPlayer player;
    Random random=new Random();
    int resID[]={R.raw.kanong,R.raw.tiankong,R.raw.yekong,R.raw.summer};
    public void start(Context context){
        player=MediaPlayer.create(context,resID[random.nextInt(4)]);
        player.start();
    }
    public void pause(Context context){
        player.pause();
    }
    public void release(){
        player.release();
    }

}
