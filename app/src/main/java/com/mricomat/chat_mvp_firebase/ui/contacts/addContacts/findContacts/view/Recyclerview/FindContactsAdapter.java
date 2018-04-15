/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 30/03/18 13:58
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.view.Recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public class FindContactsAdapter extends  RecyclerView.Adapter<FindContactsViewHolder> {

    private List<ContactModel> mContacts;
    private FindContactsViewHolder.FindContactsHolderListener mOnClickListener;

    @Override
    public FindContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.find_contact_item, parent, false);
        return new FindContactsViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(FindContactsViewHolder holder, int position) {
        holder.update(mContacts.get(position));
    }

    public void setOnClickListener(FindContactsViewHolder.FindContactsHolderListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    public void setContacts(List<ContactModel> contacts) {
        mContacts = contacts;
        notifyDataSetChanged();
    }

    public List<ContactModel> getContacts() {
        return mContacts;
    }

}
