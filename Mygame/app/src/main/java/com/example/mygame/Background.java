package com.example.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by 张翔宇 on 2018/6/27.
 */

public class Background {
    private int x = 0, x2 = 1080;
    private int speed;
    Context context;
    private Bitmap bitmap,bkbitmap;
    private boolean flag = true;
    public Background(Context context) {
        super();
        this.context = context;
        speed = 10;
        bitmap = new BitmapFactory().decodeResource(context.getResources(), R.drawable.background);
        bkbitmap= Bitmap.createBitmap(bitmap,0,0,1080,1800);
    }
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bkbitmap, x, 0, paint);
        canvas.drawBitmap(bkbitmap, x2, 0, paint);

    }
    public void logic() {
        if (flag) {
            x -= speed;
            x2 -= speed;
            if (x <= -1080)
                x = 1080;
            if (x2 <= -1080)
                x2 = 1080;
        } else {

        }
    }
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
