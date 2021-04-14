package rs.raf.projekat1.luka_petrovic_rn3318.ui.new_entry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.luka_petrovic_rn3318.R;

public class NewEntryFragment extends Fragment {

    private NewEntryViewModel newEntryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newEntryViewModel =
                new ViewModelProvider(this).get(NewEntryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_new_entry, container, false);
        newEntryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }
}