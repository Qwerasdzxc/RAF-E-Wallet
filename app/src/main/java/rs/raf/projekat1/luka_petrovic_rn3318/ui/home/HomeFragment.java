package rs.raf.projekat1.luka_petrovic_rn3318.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Income;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Outcome;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.view_models.IncomeViewModel;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.view_models.OutcomeViewModel;

public class HomeFragment extends Fragment {

    private TextView incomeTextView;
    private TextView outcomeTextView;
    private TextView differenceTextView;

    private IncomeViewModel incomeViewModel;
    private OutcomeViewModel outcomeViewModel;

    private int currentDifference = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        incomeTextView = root.findViewById(R.id.home_income_text);
        outcomeTextView = root.findViewById(R.id.home_outcome_text);
        differenceTextView = root.findViewById(R.id.home_difference_text);

        incomeViewModel = new ViewModelProvider(requireActivity()).get(IncomeViewModel.class);
        outcomeViewModel = new ViewModelProvider(requireActivity()).get(OutcomeViewModel.class);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        incomeViewModel.getIncomeData().observe(getViewLifecycleOwner(), incomeData -> {
            int totalIncome = 0;
            for (Income income : incomeData)
                totalIncome += income.getValue();

            currentDifference += totalIncome;

            incomeTextView.setText(String.valueOf(totalIncome));
            differenceTextView.setText(String.valueOf(currentDifference));
            differenceTextView.setTextColor(currentDifference > 0 ? Color.GREEN : Color.RED);
        });

        outcomeViewModel.getOutcomeData().observe(getViewLifecycleOwner(), outcomeData -> {
            int totalOutcome = 0;
            for (Outcome income : outcomeData)
                totalOutcome += income.getValue();

            currentDifference -= totalOutcome;

            outcomeTextView.setText(String.valueOf(totalOutcome));
            differenceTextView.setText(String.valueOf(currentDifference));
            differenceTextView.setTextColor(currentDifference >= 0 ? Color.GREEN : Color.RED);
        });
    }
}