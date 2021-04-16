package rs.raf.projekat1.luka_petrovic_rn3318.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.luka_petrovic_rn3318.Constants;
import rs.raf.projekat1.luka_petrovic_rn3318.MainActivity;
import rs.raf.projekat1.luka_petrovic_rn3318.R;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText nameEditText = findViewById(R.id.login_name_edit_text);
        final EditText surnameEditText = findViewById(R.id.login_surname_edit_text);
        final EditText bankEditText = findViewById(R.id.login_bank_edit_text);
        final EditText passwordEditText = findViewById(R.id.login_password_edit_text);
        final Button loginButton = findViewById(R.id.login_button);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getNameError() != null) {
                    nameEditText.setError(getString(loginFormState.getNameError()));
                }
                if (loginFormState.getSurnameError() != null) {
                    surnameEditText.setError(getString(loginFormState.getSurnameError()));
                }
                if (loginFormState.getBankError() != null) {
                    bankEditText.setError(getString(loginFormState.getBankError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(nameEditText.getText().toString(),
                        surnameEditText.getText().toString(), bankEditText.getText().toString(), passwordEditText.getText().toString());
            }
        };
        nameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(nameEditText.getText().toString(), surnameEditText.getText().toString(), bankEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(Constants.KEY_NAME, model.getName())
                .putString(Constants.KEY_SURNAME, model.getSurname())
                .putString(Constants.KEY_BANK, model.getBank())
        .apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}