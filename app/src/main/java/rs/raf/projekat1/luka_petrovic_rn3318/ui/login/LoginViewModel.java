package rs.raf.projekat1.luka_petrovic_rn3318.ui.login;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.data.LoginRepository;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.data.Result;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String name, String surname, String bank, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(name, surname, bank, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String name, String surname, String bank, String password) {
        if (!isNameValid(name)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_name, null, null, null));
        } else if (!isSurnameValid(surname)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_surname, null, null));
        } else if (!isBankValid(bank)) {
            loginFormState.setValue(new LoginFormState(null, null, R.string.invalid_bank, null));
        } else if (!isPasswordLengthValid(password)) {
            loginFormState.setValue(new LoginFormState(null, null, null, R.string.invalid_password_short));
        } else if (!isPasswordMatch(password)) {
            loginFormState.setValue(new LoginFormState(null, null, null, R.string.invalid_password_match));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isNameValid(String name) {
        return !name.isEmpty();
    }

    private boolean isSurnameValid(String surname) {
        return !surname.isEmpty();
    }

    private boolean isBankValid(String bank) {
        return !bank.isEmpty();
    }

    private boolean isPasswordLengthValid(String password) {
        return password != null && password.trim().length() >= 5;
    }

    private boolean isPasswordMatch(String password) {
        return password.equals("12345");
    }
}