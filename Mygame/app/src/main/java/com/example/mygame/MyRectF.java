package com.example.mygame;

import android.graphics.RectF;

import java.util.Random;

/**
 * Created by 张翔宇 on 2018/6/26.
 */

public class MyRectF extends RectF {
    private int type;

    public final static int BLAKE = 0;
    public final static int WRITE = 1;
    public final static int BLUE = 2;
    public final static int START = 3;
    public final static int RED = 4;

    public MyRectF(){
        super();
        Random random=new Random();
        type=random.nextInt(2);
    }


    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
