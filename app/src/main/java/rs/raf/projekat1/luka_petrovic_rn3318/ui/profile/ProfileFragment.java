package rs.raf.projekat1.luka_petrovic_rn3318.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import rs.raf.projekat1.luka_petrovic_rn3318.Constants;
import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.LoginActivity;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.data.LoginRepository;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.login.data.model.LoggedInUser;

/**
 * Created by Qwerasdzxc on 4/14/21.
 */
public class ProfileFragment extends Fragment {

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText bankEditText;
    private Button editButton;
    private Button logoutButton;

    private boolean isInEditMode = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        nameEditText = root.findViewById(R.id.profile_name_edit_text);
        surnameEditText = root.findViewById(R.id.profile_surname_edit_text);
        bankEditText = root.findViewById(R.id.profile_bank_edit_text);
        editButton = root.findViewById(R.id.profile_edit_button);
        logoutButton = root.findViewById(R.id.profile_logout_button);

        LoggedInUser user = LoginRepository.getInstance().getUser();
        nameEditText.setText(user.getName());
        surnameEditText.setText(user.getSurname());
        bankEditText.setText(user.getBank());

        logoutButton.setOnClickListener(view -> {
            LoginRepository.getInstance().logout();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        editButton.setOnClickListener(view -> {
            if (!isInEditMode) {
                nameEditText.setEnabled(true);
                surnameEditText.setEnabled(true);
                bankEditText.setEnabled(true);

                isInEditMode = true;
            } else {
                if (nameEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.profile_name_required), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (surnameEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.profile_surname_required), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bankEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.profile_bank_required), Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginRepository.getInstance().getUser().setName(nameEditText.getText().toString().trim());
                LoginRepository.getInstance().getUser().setSurname(surnameEditText.getText().toString().trim());
                LoginRepository.getInstance().getUser().setBank(bankEditText.getText().toString().trim());

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
                sharedPreferences.edit()
                        .putString(Constants.KEY_NAME, nameEditText.getText().toString().trim())
                        .putString(Constants.KEY_SURNAME, surnameEditText.getText().toString().trim())
                        .putString(Constants.KEY_BANK, bankEditText.getText().toString().trim())
                        .apply();

                nameEditText.setEnabled(false);
                surnameEditText.setEnabled(false);
                bankEditText.setEnabled(false);

                isInEditMode = false;
            }
        });

        return root;
    }
}
