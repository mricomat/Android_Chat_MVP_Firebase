/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 30/03/18 13:58
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.view.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 27/03/2018.
 */

public class RequestsContactsAdapter extends RecyclerView.Adapter<RequestsContactsViewHolder> {

    private List<ContactModel> mContacts;
    private RequestsContactsViewHolderListener mHolderListener;

    @Override
    public RequestsContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.requests_contacts_item, parent, false);
        return new RequestsContactsViewHolder(view, mHolderListener);
    }

    @Override
    public void onBindViewHolder(RequestsContactsViewHolder holder, int position) {
        holder.update(mContacts.get(position));
    }

    public void setOnClickListener(RequestsContactsViewHolderListener holderListener) {
        mHolderListener = holderListener;
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
