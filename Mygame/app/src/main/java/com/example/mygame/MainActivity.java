package com.example.mygame;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    MyViewgroup viewgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewgroup= (MyViewgroup) findViewById(R.id.mygame);
        viewgroup.setOnBalckCheckListener(new MyViewgroup.OnBalckCheckListener() {
            @Override
            public void score(int score) {

            }

            @Override
            public void gameover() {
                final AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("得分");
                alert.setMessage("当前得分："+viewgroup.score);
                alert.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewgroup.Restart();
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }

}
