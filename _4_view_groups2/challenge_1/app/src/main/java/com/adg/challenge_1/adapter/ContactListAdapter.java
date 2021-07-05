package com.adg.challenge_1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.*;
import androidx.recyclerview.widget.RecyclerView;

import com.adg.challenge_1.R;
import com.adg.challenge_1.model.Contact;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int TYPE_CONTACT = 0;
    private static final int TYPE_HEADER = 1;

    private ArrayList<Contact> contacts;

    public ContactListAdapter (ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout view;

        if (viewType == TYPE_CONTACT) {
            // Transform the XML into a java object, without adding the child view to the parent right away (attachToRoot = false)
            // LayoutManager will handle the operation of adding the child to its parent so that's why it's false
            view = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);

            return new ContactListViewHolder(view);
        } else {
            view = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);

            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).update(position);
        } else {
            ((ContactListViewHolder) holder).update(position);
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (contacts.get(position).getPhoneNumber().equals("x"))
            return TYPE_HEADER;
        return TYPE_CONTACT;
    }

    public class ContactListViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView contactIcon;
        private TextView contactName;
        private TextView contactPhoneNumber;

        public ContactListViewHolder(@NonNull View itemView) {
            super(itemView);

            contactIcon = itemView.findViewById(R.id.itemContact_conImg);
            contactName = itemView.findViewById(R.id.itemContact_conName);
            contactPhoneNumber = itemView.findViewById(R.id.itemContact_conPhoneNo);
        }

        public void update(int position) {
            Contact contact = contacts.get(position);

            contactIcon.setImageResource(contact.getIconType());
            contactName.setText(contact.getName());
            contactPhoneNumber.setText(contact.getPhoneNumber());
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        private TextView headerLetter;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            headerLetter = itemView.findViewById(R.id.itemHeader_header);
        }

        public void update(int position) {
            headerLetter.setText(contacts.get(position).getName());
        }
    }
}
