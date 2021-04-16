package rs.raf.projekat1.luka_petrovic_rn3318.ui.list.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Function;

import rs.raf.projekat1.luka_petrovic_rn3318.R;
import rs.raf.projekat1.luka_petrovic_rn3318.models.Outcome;

/**
 * Created by Qwerasdzxc on 4/16/21.
 */
public class OutcomeListAdapter extends ListAdapter<Outcome, OutcomeListAdapter.ViewHolder> {


    private final Function<Outcome, Void> onItemClicked;
    private final Function<Outcome, Void> onDeleteClicked;
    private final Function<Outcome, Void> onEditClicked;

    public OutcomeListAdapter(@NonNull DiffUtil.ItemCallback<Outcome> diffCallback, Function<Outcome, Void> onItemClicked, Function<Outcome, Void> onDeleteClicked,
                              Function<Outcome, Void> onEditClicked) {
        super(diffCallback);
        this.onItemClicked = onItemClicked;
        this.onEditClicked = onEditClicked;
        this.onDeleteClicked = onDeleteClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outcome_list_item, parent, false);
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

            itemView.findViewById(R.id.outcome_list_item).setOnClickListener(user -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.apply(getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.outcome_list_item_delete_button).setOnClickListener(user -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onDeleteClicked.apply(getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.outcome_list_item_edit_button).setOnClickListener(user -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onEditClicked.apply(getAdapterPosition());
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(Outcome outcome) {
            ((TextView) itemView.findViewById(R.id.outcome_list_item_title_tv)).setText(outcome.getTitle());
            ((TextView) itemView.findViewById(R.id.outcome_list_item_value_tv)).setText(outcome.getValue().toString());
        }
    }
}
