package rs.raf.projekat1.luka_petrovic_rn3318.ui.list;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.raf.projekat1.luka_petrovic_rn3318.R;

public class IncomeFragment extends Fragment {

    private IncomeViewModel mViewModel;

    public static IncomeFragment newInstance() {
        return new IncomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.income_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        // TODO: Use the ViewModel
    }

}