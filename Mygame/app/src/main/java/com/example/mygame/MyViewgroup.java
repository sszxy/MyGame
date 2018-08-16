package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import static android.os.SystemClock.sleep;

/**
 * Created by 张翔宇 on 2018/6/26.
 */

public class MyViewgroup extends View {
    public MyRectF[][] rectf=new MyRectF[5][4];
    boolean isgameover=false;
    int topRectHeight=0;
    Paint paint=new Paint();
    private OnBalckCheckListener onBalckCheckListener;
    SparseArray array=new SparseArray();
    int score=0;
    int speed=8;
    int account=10;
    Myplayer myplayer;
    private SoundUtils utils;



    public MyViewgroup(Context context) {
        super(context);
        initMyrect();
        utils=SoundUtils.getInstance();
        utils.init(getContext());

    }


    public MyViewgroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initMyrect();
        utils=SoundUtils.getInstance();
        utils.init(getContext());
    }

    public MyViewgroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initMyrect(){
        myplayer=new Myplayer();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                MyRectF rectF=new MyRectF();
                rectf[i][j]=rectF;
                if(j==1){
                    if(rectf[i][j-1].getType()==MyRectF.BLAKE||
                            rectf[i][j-1].getType()==MyRectF.START){
                        rectf[i][j].setType(MyRectF.WRITE);
                    }
                }
                else  if(j==3){
                    if(rectf[i][j-1].getType()==MyRectF.BLAKE||
                            rectf[i][j-1].getType()==MyRectF.START){
                        rectf[i][j].setType(MyRectF.WRITE);
                    }
                    if (rectf[i][j - 2].getType() == MyRectF.WRITE &&
                            rectf[i][j - 3].getType() ==MyRectF.WRITE) {
                        rectf[i][j].setType(MyRectF.BLAKE);
                    }

                }
            }
        }
        for(int i=0;i<4;i++){

            rectf[4][i]=new MyRectF();
            if(i==3){
                rectf[4][i].setType(MyRectF.START);
            }else {
                rectf[4][i].setType(MyRectF.WRITE);
            }
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(150);
        canvas.drawText(score+"",getWidth()/2,150,paint);
    }


    public void drawRect(Canvas canvas){
        int width=getWidth()/4;
        int height=getHeight()/4;
        if (isgameover){
            if(onBalckCheckListener!=null){
                onBalckCheckListener.gameover();
                utils.release();
            }
            isgameover=false;
        }
        if(score==account){
            account+=10;
            speed+=1;
            if(score==40){
                score+=2;
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                rectf[i][j].left = width * j;
                rectf[i][j].right = width * (j + 1);
                rectf[i][j].bottom = topRectHeight + i * height;
                rectf[i][j].top = rectf[i][j].bottom - height;
                paint.setStyle(Paint.Style.FILL);
                if (rectf[i][j].getType() == MyRectF.WRITE) {

                    paint.setColor(Color.WHITE);
                    canvas.drawRect(rectf[i][j], paint);

                } else if (rectf[i][j].getType() == MyRectF.BLAKE) {

                    paint.setColor(Color.BLACK);
                    canvas.drawRect(rectf[i][j], paint);

                } else if (rectf[i][j].getType() == MyRectF.BLUE) {

                    paint.setColor(Color.BLUE);
                    canvas.drawRect(rectf[i][j], paint);

                } else if (rectf[i][j].getType() == MyRectF.RED) {

                    paint.setColor(Color.RED);
                    canvas.drawRect(rectf[i][j], paint);
                }
                else if (rectf[i][j].getType() == MyRectF.START) {

                    paint.setColor(Color.BLACK);
                    canvas.drawRect(rectf[i][j], paint);

                    paint.setColor(Color.parseColor("#ffffff"));
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(50);

                    String start = "开始";
                    Rect bounds = new Rect();
                    paint.getTextBounds(start, 0, start.length(), bounds);
                    float x = rectf[i][j].left / 2 + rectf[i][j].right / 2;
                    float y = rectf[i][j].top / 2 + rectf[i][j].bottom / 2+ bounds.bottom / 2 - bounds.top / 2 ;
                    canvas.drawText(start, x, y, paint);
                }
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.parseColor("#cccccc"));
                    paint.setStrokeWidth(3);
                    canvas.drawRect(rectf[i][j], paint);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index=event.getActionIndex();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int id=event.getPointerId(index);
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 4; j++) {
                        MyRectF f = rectf[i][j];
                        if (event.getX() > f.left && event.getX() < f.right
                                && event.getY() < f.bottom && event.getY() > f.top) {
                            array.put(id,f);
                            if (f.getType() == MyRectF.BLAKE) {
                                f.setType(MyRectF.BLUE);
                                score++;
                                //utils.play();
                               // myplayer.start(getContext());
                            } else if (f.getType() == MyRectF.WRITE) {
                                f.setType(MyRectF.RED);
                                isgameover = true;
                                postInvalidate();
                            } else if (f.getType() == MyRectF.START) {
                                f.setType(MyRectF.BLUE);
                                startThread();
                                utils.play();
                            }
                            if(onBalckCheckListener !=null){
                                onBalckCheckListener.score(score);
                            }


                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                id = event.getPointerId(index);
                MyRectF f = (MyRectF) array.get(id, null);
                if (f != null && f.getType() == MyRectF.BLUE) {
                    f.setType(MyRectF.WRITE);
                }
                //utils.pause();
               // myplayer.pause(this.getContext());
               break;

    }
          return true;
    }

    private void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(topRectHeight>0){
                        if(checkHasBlack()){
                            isgameover = true;
                            postInvalidate();
                            return;
                        }
                    }
                    topRectHeight += getSpeed();
                    if(isgameover){
                        break;
                    }
                    if (topRectHeight > getHeight() / 4) {
                        topRectHeight = 0;
                        updateView();
                    }
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        }).start();
    }

    private void updateView() {
        for(int i=4;i>=0;i--){
            for(int j=0;j<4;j++){
                if(i==0){
                  MyRectF rectF=new MyRectF();
                  rectf[i][j]=rectF;
                if(j==1){
                    if(rectf[i][j-1].getType()==MyRectF.BLAKE||rectf[i][j-1].getType()==MyRectF.START){
                        rectf[i][j].setType(MyRectF.WRITE);
                    }
                }
                else if(j==3){
                    if(rectf[i][j-1].getType()==MyRectF.BLAKE||rectf[i][j-1].getType()==MyRectF.START){
                        rectf[i][j].setType(MyRectF.WRITE);
                    }
                    else if (rectf[i][j-2].getType() ==MyRectF.WRITE &&
                            rectf[i][j-3].getType() == MyRectF.WRITE)
                        rectf[i][j].setType(MyRectF.BLAKE);

                }
                }else {
                    rectf[i][j]=rectf[i-1][j];
                }
            }
        }
    }

    private Boolean checkHasBlack() {
        Boolean hasBlack = false;
        for (int i = 0; i < 4; i++) {
            if (rectf[4][i].getType() == MyRectF.BLAKE||rectf[4][i].getType() == MyRectF.START){
                rectf[4][i].setType(MyRectF.RED);
                hasBlack = true;
            }
        }
        return hasBlack;
    }
    public int  getSpeed(){
        return speed;
    }
    public void setspeed(){
        this.speed=speed;
    }

    public interface OnBalckCheckListener{
        void score(int score);
        void gameover();
    }
    public void setOnBalckCheckListener(OnBalckCheckListener onBalckCheckListener){
        this.onBalckCheckListener = onBalckCheckListener;
    }
    public void Restart(){
        isgameover = false;
        topRectHeight= 0;
        score = 0;
        speed=8;
        account=10;
        onBalckCheckListener.score(score);
        initMyrect();
        invalidate();
    }
}