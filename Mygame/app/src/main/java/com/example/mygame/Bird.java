package com.example.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by 张翔宇 on 2018/6/27.
 */
public class Bird {
    private int x=200,y=400,speed=0;
    Context context;
    private Bitmap bitmap;
    private boolean flag=true;

    public Bird(Context context) {
        super();
        this.context=context;
        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.bird);
        Log.d("tag",bitmap.getHeight()+"小鸟");
        Log.d("tag",bitmap.getWidth()+"小鸟");
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public void draw(Canvas canvas, Paint paint, int count1){
            canvas.drawBitmap(bitmap, 200, y, paint);
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void logic(){
        if(flag){
            y+=speed;
            speed++;
            if(y<0)
                y=0;
            if(y>1800){
                GameSurface.flag=false;
            }
        }
        else{
            speed=20;
            y+=speed;
            speed++;
        }
    }
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public void move(){
        speed=-13;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
}
