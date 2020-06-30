package com.example.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private List<Contacts> mContacts = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.contacts_list_item, parent, false);
        return new ContactsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        Contacts currentContacts = mContacts.get(position);
        if (currentContacts != null) {
            holder.bind(currentContacts);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void setContacts (List<Contacts> contacts) {
       mContacts = contacts;
        notifyDataSetChanged();
    }

public Contacts getNoteAt(int position) {
        return mContacts.get(position);
}

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView, phoneView, emailView, ageView,genderView, cityView, collegeView,phoneTextView;
        // private RadioButton genderView;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.contact_name);
            phoneTextView = itemView.findViewById(R.id.phone_text);
            phoneView = itemView.findViewById(R.id.contact_phone);
            emailView = itemView.findViewById(R.id.contact_email);
            ageView = itemView.findViewById(R.id.contact_age);
            genderView = itemView.findViewById(R.id.contact_gender);
            cityView = itemView.findViewById(R.id.contact_city);
            collegeView = itemView.findViewById(R.id.contact_college);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position!= RecyclerView.NO_POSITION) {
                        listener.onItemClick(mContacts.get(position));
                    }
                }
            });

        }

        public void bind(Contacts contacts) {
            nameView.setText(contacts.getName());
            phoneView.setText(contacts.getPhoneNo());
            emailView.setText(contacts.getEmail());
            ageView.setText(String.valueOf(contacts.getAge()));
            genderView.setText(String.valueOf(contacts.getGender()));
            cityView.setText(contacts.getCity());
            collegeView.setText(contacts.getCollege());
        }

    }
    public interface onItemClickListener{
        void onItemClick(Contacts contacts);
    }

    public void setOnItemClickListener (onItemClickListener listener){
        this.listener = listener;
    }
}
