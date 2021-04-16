package rs.raf.projekat1.luka_petrovic_rn3318;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.LoginActivity;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.data.LoginDataSource;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.data.LoginRepository;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed((Runnable) () -> {
            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            String name = sharedPreferences.getString(Constants.KEY_NAME, null);
            String surname = sharedPreferences.getString(Constants.KEY_SURNAME, null);
            String bank = sharedPreferences.getString(Constants.KEY_BANK, null);

            if (name == null || surname == null || bank == null) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                LoginRepository.getInstance(new LoginDataSource());
                LoginRepository.getInstance().login(name, surname, bank);

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
            finish();
        }, 1000);
    }
}