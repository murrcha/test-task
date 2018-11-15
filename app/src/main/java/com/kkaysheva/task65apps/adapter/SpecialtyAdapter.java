package com.kkaysheva.task65apps.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkaysheva.task65apps.R;
import com.kkaysheva.task65apps.database.model.Specialty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SpecialtyAdapter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.SpecialtyViewHolder> {

    private List<Specialty> specialties = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public SpecialtyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.specialty_item_view, viewGroup, false);
        return new SpecialtyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialtyViewHolder specialtyHolder, int position) {
        specialtyHolder.bind(specialties.get(position));
    }

    @Override
    public int getItemCount() {
        return specialties.size();
    }

    public void setItems(Collection<Specialty> specialties) {
        this.specialties.addAll(specialties);
        notifyDataSetChanged();
    }

    public void clearItems() {
        specialties.clear();
        notifyDataSetChanged();
    }

    class SpecialtyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.id_specialty)
        TextView id;
        @BindView(R.id.name_specialty)
        TextView name;

        public SpecialtyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClicked(specialties.get(position).getId());
                    }
                }
            });
        }

        private void bind(Specialty specialty) {
            id.setText(String.valueOf(specialty.getId()));
            name.setText(specialty.getName());
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int specialtyId);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
