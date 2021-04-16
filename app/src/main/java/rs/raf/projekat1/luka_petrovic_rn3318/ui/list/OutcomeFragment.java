package rs.raf.projekat1.luka_petrovic_rn3318.ui.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Outcome;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.adapters.OutcomeListAdapter;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.details.IncomeDetailsActivity;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.details.OutcomeDetailsActivity;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.edit.EditOutcomeActivity;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.view_models.OutcomeViewModel;

public class OutcomeFragment extends Fragment {

    private OutcomeViewModel viewModel;
    private OutcomeListAdapter adapter;

    private final int EDIT_INTENT_KEY = 1;

    public static OutcomeFragment newInstance() {
        return new OutcomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.outcome_tab, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.outcome_tab_recycler_view);
        adapter = new OutcomeListAdapter(new DiffUtil.ItemCallback<Outcome>() {
            @Override
            public boolean areItemsTheSame(@NonNull Outcome oldItem, @NonNull Outcome newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Outcome oldItem, @NonNull Outcome newItem) {
                return oldItem.equals(newItem);
            }
        }, outcome -> {
            Intent intent = new Intent(getActivity(), OutcomeDetailsActivity.class);
            intent.putExtra("outcome", outcome);
            startActivity(intent);
            return null;
        }, outcome -> {
            viewModel.deleteOutcome(outcome);
            return null;
        }, outcome -> {
            Intent intent = new Intent(getActivity(), EditOutcomeActivity.class);
            intent.putExtra("outcome", outcome);
            startActivityForResult(intent, EDIT_INTENT_KEY);
            return null;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(OutcomeViewModel.class);
        viewModel.getOutcomeData().observe(getViewLifecycleOwner(), outcomeData -> {
            adapter.submitList(outcomeData);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED)
            return;
        if (requestCode != EDIT_INTENT_KEY || resultCode != Activity.RESULT_OK)
            return;

        Outcome current = (Outcome) data.getSerializableExtra("current_outcome");
        viewModel.updateOutcome(current);
        adapter.notifyDataSetChanged();
    }
}