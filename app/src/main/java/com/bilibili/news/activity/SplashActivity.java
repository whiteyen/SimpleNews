package com.bilibili.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bilibili.news.R;
import com.bilibili.news.utils.PreUtils;

public class SplashActivity extends AppCompatActivity {
    ImageView iv;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv = (ImageView) findViewById(R.id.activity_splash);
        initAnim();
    }

    private void initAnim() {
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        alpha.setDuration(3000);
        alpha.setFillAfter(true);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                jumpToNextPage();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        iv.startAnimation(alpha);
    }

    private void jumpToNextPage() {
        boolean isFirst = PreUtils.getBoolean(this,"isFirst",true);
        Intent intent = new Intent();
        if(isFirst){
            intent.setClass(this,GuideActivity.class);
        }else {
            intent.setClass(this,MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
