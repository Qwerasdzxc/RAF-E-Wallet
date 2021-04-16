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
import rs.raf.projekat1.luka_petrovic_rn3318.models.Income;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.adapters.IncomeListAdapter;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.edit.EditIncomeActivity;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.view_models.IncomeViewModel;

public class IncomeFragment extends Fragment {

    private IncomeViewModel viewModel;
    private IncomeListAdapter adapter;

    private final int EDIT_INTENT_KEY = 1;

    public static IncomeFragment newInstance() {
        return new IncomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.income_tab, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.income_tab_recycler_view);
        adapter = new IncomeListAdapter(new DiffUtil.ItemCallback<Income>() {
            @Override
            public boolean areItemsTheSame(@NonNull Income oldItem, @NonNull Income newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Income oldItem, @NonNull Income newItem) {
                return oldItem.equals(newItem);
            }
        }, income -> {
            System.out.println("Open view page");
            return null;
        }, income -> {
            viewModel.deleteIncome(income);
            return null;
        }, income -> {
            Intent intent = new Intent(getActivity(), EditIncomeActivity.class);
            intent.putExtra("income", income);
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
        viewModel = new ViewModelProvider(getActivity()).get(IncomeViewModel.class);
        viewModel.getIncomeData().observe(getViewLifecycleOwner(), incomeData -> {
            adapter.submitList(incomeData);
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

        Income current = (Income) data.getSerializableExtra("current_income");
        viewModel.updateIncome(current);
        adapter.notifyDataSetChanged();
    }
}