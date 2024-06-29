package com.example.bookstore.Splashscreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.Login.LoginActivity;
import com.example.bookstore.R;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView logoImageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logoImageView = findViewById(R.id.logoImageView);
        textView = findViewById(R.id.textView);

        ObjectAnimator logoAnimator = ObjectAnimator.ofFloat(logoImageView, "alpha", 0f, 1f);
        ObjectAnimator textAnimator = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(logoAnimator, textAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(3000); // Sesuaikan durasi sesuai kebutuhan

        set.start();

        // Anda juga bisa menetapkan pendengar untuk memulai aktivitas berikutnya setelah animasi selesai
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Mulai LoginActivity setelah animasi selesai
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
