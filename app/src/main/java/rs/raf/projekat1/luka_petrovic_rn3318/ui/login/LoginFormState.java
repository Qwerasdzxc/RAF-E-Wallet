package rs.raf.projekat1.luka_petrovic_rn3318.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer nameError;
    @Nullable
    private Integer surnameError;
    @Nullable
    private Integer bankError;
    @Nullable
    private Integer passwordError;

    private boolean isDataValid;

    LoginFormState(@Nullable Integer nameError, @Nullable Integer surnameError, @Nullable Integer bankError, @Nullable Integer passwordError) {
        this.nameError = nameError;
        this.surnameError = surnameError;
        this.bankError = bankError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.nameError = null;
        this.surnameError = null;
        this.bankError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getNameError() {
        return nameError;
    }

    @Nullable
    public Integer getSurnameError() {
        return surnameError;
    }

    @Nullable
    public Integer getBankError() {
        return bankError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}