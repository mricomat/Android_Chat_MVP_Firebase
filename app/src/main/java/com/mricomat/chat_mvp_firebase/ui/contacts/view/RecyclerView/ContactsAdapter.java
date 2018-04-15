/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 10/04/18 23:02
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.view.RecyclerView;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 24/03/2018.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> implements View.OnClickListener{

    private List<ContactListModel> mContacts;
    private View.OnClickListener mOnClickListener;

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        view.setOnClickListener(this);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        holder.update(mContacts.get(position));
    }


    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        mOnClickListener.onClick(v);
    }

    public void setContacts(List<ContactListModel> contacts) {
        mContacts = contacts;
        notifyDataSetChanged();
    }

    public List<ContactListModel> getContacts() {
        return mContacts;
    }
}
