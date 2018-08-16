package com.example.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 张翔宇 on 2018/6/27.
 */

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{
    public  static boolean flag=true;
    public  int pingmuwidth;
    public  int clickok;
    //gamelistener gamelistener;
    public static boolean isFlag() {
        return flag;
    }
    public static void setFlag(boolean flag) {
        GameSurface.flag = flag;
    }
    SurfaceHolder holder;
    private Paint paint;
    private boolean isRunning;
    Canvas canvas;
    private Background background;
    private Bird bird;
    Context context;
    private Bound bound;
    private int count1=0;
    private Bitmap bitmap;
    public GameSurface(Context context) {
        super(context);
        holder=getHolder();
        holder.addCallback(this);
        paint = new Paint();
        bird = new Bird(context);
        background = new Background(context);
        this.context=context;
        bound=new Bound(context);

    }
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        Log.d("tag","创建"+"");
        bitmap = new BitmapFactory().decodeResource(context.getResources(), R.drawable.start_button);
        isRunning=true;
        flag=true;
        bird.setFlag(true);
        bound.setFlag(true);
        background.setFlag(true);
        pingmuwidth=getWidth();
        clickok=0;
        new startThread().start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        isRunning=false;
        flag=true;
        Log.d("tag","回收");
    }
    class startThread extends Thread {
        @Override
        public void run() {
            while(isRunning){
                canvas = holder.lockCanvas();
                if (canvas == null)
                    continue;
                canvas.drawColor(0xFFFFFFFF);
                draw(canvas);
                faill(canvas, paint, flag);
                //if (flag==false) break;
                logic();
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void draw(Canvas canvas){

        background.draw(canvas, paint);
        bird.draw(canvas, paint,count1);
        count1++;
        bound.draw(canvas, paint);
        if(bound.isCollection(bird)){
            bird.setFlag(false);
            background.setFlag(false);
            bound.setFlag(false);
            flag=false;
            paint.setTextSize(150);
            canvas.drawText(bound.count-16+"",getWidth()/2-40,200,paint);
        }else {
            paint.setTextSize(150);
            canvas.drawText(bound.count+"",getWidth()/2-40,200,paint);
        }
    }
    public void logic(){
        background.logic();
        bird.logic();
        bound.logic();
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(flag==false){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    int x= (int) event.getX();
                    Log.d("tag",x+"");
                    int y= (int) event.getY();
                    Log.d("tag",y+"");
                    if (x<getWidth()/2+bitmap.getWidth()/2&&x>getWidth()/2-bitmap.getWidth()/2
                            &&y<getHeight()/2+bitmap.getHeight()/2&&y>getHeight()/2-bitmap.getWidth()/2){
                        clickok=1;
                        isRunning=false;
                        Log.d("tag",isRunning+"");
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if(clickok==1){
                        Restart();
                        Log.d("tag","zhongxin");
                    }
                    return true;
            }
        }
        bird.move();
        Log.d("tag","店家");
        return true;
    }
    public void faill(Canvas canvas, Paint paint, Boolean flag){
        if(flag){

        }
        else{
            canvas.drawBitmap(bitmap,getWidth()/2-bitmap.getWidth()/2,getHeight()/2-bitmap.getHeight()/2,paint);
        }
    }
    //public void setGamelistener(gamelistener gamelistener){
        //this.gamelistener=gamelistener;
    //}
    public void Restart(){
        bird = new Bird(context);
        background = new Background(context);
        this.context=context;
        bound=new Bound(context);
        isRunning=true;
        flag=true;
        bird.setFlag(true);
        bound.setFlag(true);
        background.setFlag(true);
        pingmuwidth=getWidth();
        clickok=0;
        new startThread().start();
    }
}