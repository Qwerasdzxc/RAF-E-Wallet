package rs.raf.projekat1.luka_petrovic_rn3318.ui.new_entry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Income;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Outcome;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.view_models.IncomeViewModel;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.view_models.OutcomeViewModel;

public class NewEntryFragment extends Fragment {

    private EditText titleEditText;
    private EditText valueEditText;
    private CheckBox audioCheckBox;
    private EditText descriptionEditText;
    private ImageView audioButton;
    private Spinner selectionSpinner;
    private Button addButton;

    private IncomeViewModel incomeViewModel;
    private OutcomeViewModel outcomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_entry, container, false);

        titleEditText = root.findViewById(R.id.new_entry_title_edit_text);
        valueEditText = root.findViewById(R.id.new_entry_value_edit_text);
        audioCheckBox = root.findViewById(R.id.new_entry_checkbox);
        descriptionEditText = root.findViewById(R.id.new_entry_description);
        audioButton = root.findViewById(R.id.new_entry_audio_button);
        selectionSpinner = root.findViewById(R.id.new_entry_spinner);
        addButton = root.findViewById(R.id.new_entry_add_button);

        incomeViewModel = new ViewModelProvider(requireActivity()).get(IncomeViewModel.class);
        outcomeViewModel = new ViewModelProvider(requireActivity()).get(OutcomeViewModel.class);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        audioCheckBox.setOnClickListener(__ -> {
            if (audioCheckBox.isChecked()) {
                audioButton.setVisibility(View.VISIBLE);
                descriptionEditText.setVisibility(View.GONE);

                // TODO: Audio implementation
            } else {
                audioButton.setVisibility(View.GONE);
                descriptionEditText.setVisibility(View.VISIBLE);
            }
        });

        addButton.setOnClickListener(__ -> {
            if (titleEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), getResources().getString(R.string.new_entry_title_required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (valueEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), getResources().getString(R.string.new_entry_value_required), Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                int value = Integer.parseInt(valueEditText.getText().toString());
                if (value < 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), getResources().getString(R.string.new_entry_value_format), Toast.LENGTH_SHORT).show();
                return;
            }
            // Audio entry:
            if (audioCheckBox.isChecked()) {
                // TODO: Audio implementation
            }
            // Description entry:
            else {
                if (descriptionEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.new_entry_description_required), Toast.LENGTH_SHORT).show();
                    return;
                }
                // Income entry:
                if (selectionSpinner.getSelectedItemPosition() == 0) {
                    Income income = new Income(incomeViewModel.getAvailableId(), Integer.parseInt(valueEditText.getText().toString()), titleEditText.getText().toString(), descriptionEditText.getText().toString());
                    incomeViewModel.addIncome(income);
                }
                // Outcome entry:
                else {
                    Outcome outcome = new Outcome(outcomeViewModel.getAvailableId(), Integer.parseInt(valueEditText.getText().toString()), titleEditText.getText().toString(), descriptionEditText.getText().toString());
                    outcomeViewModel.addOutcome(outcome);
                }

                Toast.makeText(getActivity(), getResources().getString(R.string.new_entry_success), Toast.LENGTH_LONG).show();
            }

            titleEditText.getText().clear();
            valueEditText.getText().clear();
            descriptionEditText.getText().clear();
        });
    }
}