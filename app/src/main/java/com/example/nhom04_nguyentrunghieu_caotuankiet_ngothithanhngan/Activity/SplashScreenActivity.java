package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    LottieAnimationView splash;
    Animation layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        layoutAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.anim_fall_down);
        splash = findViewById(R.id.splash_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splash.setVisibility(View.VISIBLE);
                splash.setAnimation(layoutAnimation);
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLogin();
            }
        }, 3000);
    }

    private void isLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent;
        if(user == null){
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);

        }else{
            intent = new Intent(SplashScreenActivity.this, MainActivity.class);

        }
        startActivity(intent);
        finish();
    }

}