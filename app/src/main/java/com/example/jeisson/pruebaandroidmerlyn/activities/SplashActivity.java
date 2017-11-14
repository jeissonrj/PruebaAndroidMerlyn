package com.example.jeisson.pruebaandroidmerlyn.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jeisson.pruebaandroidmerlyn.R;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private final Context context = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.i(TAG,"onCreate()");
        iniciarAnimacion();

        thread.start();
    }

    private final Thread thread = new Thread() {

        private Intent intent;

        @Override
        public void run() {
            iniciarActivity();
        }

        private void iniciarActivity() {
            try {
                int waited = 0;
                while (waited < 2000) {
                    sleep(100);
                    waited += 100;
                }
                Log.i(TAG,"Thread: iniciarActivity()");
                intent = new Intent(context, ListadoActivity.class);
                startActivity(intent);
            } catch (InterruptedException e) {
                Log.i(TAG,"Thread: iniciarActivity(): "+e.getMessage());
            } finally {
                SplashActivity.this.finish();
            }
        }
    };

    private void iniciarAnimacion() {
        Log.i(TAG,"iniciarAnimacion()");
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.translate);
        animacion.reset();

        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(animacion);

        animacion = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animacion.reset();

        LinearLayout l=(LinearLayout) findViewById(R.id.splash_activity);
        l.clearAnimation();
        l.startAnimation(animacion);
    }
}
