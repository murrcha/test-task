package com.kkaysheva.task65apps.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkaysheva.task65apps.R;
import com.kkaysheva.task65apps.database.model.Person;
import com.kkaysheva.task65apps.utility.Utility;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * PersonAdapter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private static final String TAG = PersonAdapter.class.getSimpleName();
    private static final String NO_DATA = "-";

    private List<Person> persons = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.person_item_view, viewGroup, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder viewHolder, int position) {
        viewHolder.bind(persons.get(position));
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public void clearItems() {
        persons.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<Person> persons) {
        this.persons.addAll(persons);
        notifyDataSetChanged();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.first_name)
        TextView firstName;
        @BindView(R.id.last_name)
        TextView lastName;
        @BindView(R.id.age)
        TextView age;
        private Resources resources;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            resources = itemView.getContext().getResources();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        Person person = persons.get(position);
                        if (person == null) {
                            Log.e(TAG, "Person is null");
                        } else {
                            listener.onItemClicked(person);
                        }
                    }
                }
            });
        }

        private void bind(Person person) {
            firstName.setText(Utility.capitalizeString(person.getFirstName()));
            lastName.setText(Utility.capitalizeString(person.getLastName()));
            String birthday = person.getBirthday();
            if (birthday == null || birthday.trim().isEmpty()) {
                age.setText(NO_DATA);
            } else {
                Date date = Utility.getDateFromString(birthday);
                int personAge = Utility.getAge(date);
                String dateString = resources.getQuantityString(R.plurals.plural_age, personAge, personAge);
                age.setText(dateString);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Person person);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
