package com.adg.challenge_1.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adg.challenge_1.R;
import com.adg.challenge_1.model.Contact;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>
{
    private ArrayList<Contact> mDataSource;

    public ContactListAdapter(ArrayList<Contact> mDataSource) {
        this.mDataSource = mDataSource;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView mContactIcon;
        private TextView mContactName;
        private TextView mContactPhoneNo;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            mContactIcon = itemView.findViewById(R.id.itemContact_conImg);
            mContactName = itemView.findViewById(R.id.itemContact_conName);
            mContactPhoneNo = itemView.findViewById(R.id.itemContact_conPhoneNo);
        }


    }
}
