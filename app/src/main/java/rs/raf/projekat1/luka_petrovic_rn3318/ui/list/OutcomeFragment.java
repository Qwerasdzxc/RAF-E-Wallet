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

public class OutcomeFragment extends Fragment {

    private OutcomeViewModel mViewModel;

    public static OutcomeFragment newInstance() {
        return new OutcomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.outcome_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OutcomeViewModel.class);
        // TODO: Use the ViewModel
    }

}