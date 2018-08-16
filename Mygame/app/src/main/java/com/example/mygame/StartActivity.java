package com.example.mygame;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;

public class StartActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    RadioGroup radioGroup;
    String out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        radioGroup= (RadioGroup) findViewById(R.id.mygroup);
        button= (Button) findViewById(R.id.playbt);
        imageView= (ImageView) findViewById(R.id.bkimg);
        Glide.with(this).load(R.drawable.bk).asGif().into(imageView);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int id=group.getCheckedRadioButtonId();
                RadioButton button= (RadioButton) findViewById(id);
                out=button.getText().toString();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(out.equals("黑白块模式")){
                    Intent intent=new Intent(StartActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(StartActivity.this,SecondActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}
