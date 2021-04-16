package rs.raf.projekat1.luka_petrovic_rn3318.ui.list.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.IncomeFragment;
import rs.raf.projekat1.luka_petrovic_rn3318.ui.list.OutcomeFragment;

/**
 * Created by Qwerasdzxc on 4/16/21.
 */
public class ListTabAdapter extends FragmentStateAdapter {


    public ListTabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new IncomeFragment();
        } else {
            fragment = new OutcomeFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
