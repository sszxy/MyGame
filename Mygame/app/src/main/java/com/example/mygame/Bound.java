package com.example.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

/**
 * Created by 张翔宇 on 2018/6/27.
 */

public class Bound {
    Random random=new Random();
    private int x=830;
    private int x1=1430;
    private int y_down=1000,y_up=-680;
    private int y_down1=800,y_up1=-880;
    private int speed=10;
    private Bitmap bound_down;
    private Bitmap bound_up;
    private boolean flag=true;
    public int count=0;
    int bitmapwidth;
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public Bound(Context context) {
        super();
        bound_down = BitmapFactory.decodeResource(context.getResources(),R.drawable.pie_down);
        bound_up = BitmapFactory.decodeResource(context.getResources(),R.drawable.pie_up);
        bitmapwidth=bound_down.getWidth();

        Log.d("tag",bitmapwidth+"柱子款");
        Log.d("tag",bound_down.getHeight()+"柱子");
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(bound_down, x, y_down, paint);
        canvas.drawBitmap(bound_up, x, y_up, paint);
        canvas.drawBitmap(bound_down, x1, y_down1, paint);
        canvas.drawBitmap(bound_up, x1, y_up1, paint);
    }
    public void setY(int y_down) {
        this.y_down = y_down;
        y_up=y_down-420-1260;
    }
    public void setY_down1(int y_down1){
        this.y_down1=y_down1;
        y_up1=y_down1-420-1260;
    }

    public void logic(){
        if(flag){
            x-=speed;
            x1-=speed;
            if(x<=-bitmapwidth){
                x=1080;
                setY(620+random.nextInt(860));
            }
            if(x1<=-bitmapwidth){
                x1=1080;
                setY_down1(620+random.nextInt(860));
            }

        }
        else{
        }
    }

    public boolean isCollection(Bird bird){
        int left=200;
        int right=200+bird.getBitmap().getWidth();
        int top=bird.getY();
        int bottom=bird.getY()+bird.getBitmap().getHeight();
        if(x<x1){
            if (right<x+bitmapwidth/2) return false;
            if (x+bitmapwidth/2<left) return false;
            if (bottom<y_down&&top>y_down-425) {count++;return false;}
        }else {
            if (right<x1+bitmapwidth/2) return false;
            if (x1+bitmapwidth/2<left) return false;
            if (bottom<y_down1&&top>y_down1-425) {count++;return false;}
        }
        return true;
    }
}