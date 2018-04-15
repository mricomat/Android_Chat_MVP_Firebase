/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 3/04/18 0:08
 */

package com.mricomat.chat_mvp_firebase.ui.chat.view.ChatRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martín Rico Martínez on 31/03/2018.
 */

public class ChatViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_OTHER_USER = 0;
    public static final int TYPE_CURRENT_USER = 1;

    private List<MessageModel> mMessages = new ArrayList<>();

    public ChatViewAdapter() {
        mMessages = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TYPE_CURRENT_USER:
                View viewCurrentUser = inflater.inflate(R.layout.current_user_message_item, parent, false);
                viewHolder = new ChatViewHolder(viewCurrentUser);
                break;

            case TYPE_OTHER_USER:
                View viewOtherUser = inflater.inflate(R.layout.other_user_message_item, parent, false);
                viewHolder = new ChatViewHolder(viewOtherUser);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ChatViewHolder currentViewHolder = (ChatViewHolder) viewHolder;
        currentViewHolder.buildItem(mMessages.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getTypeFrom();
    }

    @Override
    public int getItemCount() {
        return mMessages == null ? 0 : mMessages.size();
    }

    public void setMessages(List<MessageModel> messages) {
        if (messages == null) {
            mMessages = new ArrayList<>();
        } else {
            mMessages = messages;
        }
        notifyDataSetChanged();
    }

    public void setMessage(MessageModel message) {
        mMessages.add(message);
        notifyDataSetChanged();
    }
}
