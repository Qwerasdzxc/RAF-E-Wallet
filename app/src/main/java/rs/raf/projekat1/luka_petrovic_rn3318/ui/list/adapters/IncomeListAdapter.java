package rs.raf.projekat1.luka_petrovic_rn3318.ui.list.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Function;

import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Income;

/**
 * Created by Qwerasdzxc on 4/16/21.
 */
public class IncomeListAdapter extends ListAdapter<Income, IncomeListAdapter.ViewHolder> {


    private final Function<Income, Void> onItemClicked;
    private final Function<Income, Void> onDeleteClicked;
    private final Function<Income, Void> onEditClicked;

    public IncomeListAdapter(@NonNull DiffUtil.ItemCallback<Income> diffCallback, Function<Income, Void> onItemClicked, Function<Income, Void> onDeleteClicked,
                             Function<Income, Void> onEditClicked) {
        super(diffCallback);
        this.onItemClicked = onItemClicked;
        this.onEditClicked = onEditClicked;
        this.onDeleteClicked = onDeleteClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_list_item, parent, false);
        return new ViewHolder(view, position -> {
            onItemClicked.apply(getItem(position));
            return null;
        }, position -> {
            onDeleteClicked.apply(getItem(position));
            return null;
        }, position -> {
            onEditClicked.apply(getItem(position));
            return null;
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView, Function<Integer, Void> onItemClicked, Function<Integer, Void> onDeleteClicked,
                          Function<Integer, Void> onEditClicked) {
            super(itemView);

            itemView.findViewById(R.id.income_list_item).setOnClickListener(user -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.apply(getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.income_list_item_delete_button).setOnClickListener(user -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onDeleteClicked.apply(getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.income_list_item_edit_button).setOnClickListener(user -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onEditClicked.apply(getAdapterPosition());
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(Income income) {
            ((TextView) itemView.findViewById(R.id.income_list_item_title_tv)).setText(income.getTitle());
            ((TextView) itemView.findViewById(R.id.income_list_item_value_tv)).setText(income.getValue().toString());
        }
    }
}
