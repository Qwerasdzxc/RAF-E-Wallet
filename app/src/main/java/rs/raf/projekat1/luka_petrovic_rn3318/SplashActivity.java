package rs.raf.projekat1.luka_petrovic_rn3318;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed((Runnable) () -> {

            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);

            finish();
        }, 1000);
    }
}