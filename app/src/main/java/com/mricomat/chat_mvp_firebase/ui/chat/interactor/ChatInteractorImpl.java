/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 9/04/18 23:03
 */

package com.mricomat.chat_mvp_firebase.ui.chat.interactor;

import android.content.res.Resources;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.FireBaseChatService;
import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ChatRoomModel;
import com.mricomat.chat_mvp_firebase.network.model.MessageModel;
import com.mricomat.chat_mvp_firebase.ui.App;

import java.util.ArrayList;

/**
 * Created by Martín Rico Martínez on 01/04/2018.
 */

public class ChatInteractorImpl implements ChatInteractor {

    private FireBaseChatService mChatService;
    private Resources mResources;

    public ChatInteractorImpl() {
        mChatService = new FireBaseChatService();
        mResources = App.getContext().getResources();
    }

    @Override
    public void getRoomMessages(String roomId, final OnResultListener<ArrayList<MessageModel>, Object> listener) {
        mChatService.getRoomMessages(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatRoomModel chatRoomModel = dataSnapshot.getValue(ChatRoomModel.class);
                assert chatRoomModel != null;
                listener.onSuccess(chatRoomModel.getMessages());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError);
            }
        });
    }

    @Override
    public void getIncomingMessages(String roomId, final OnResultListener<MessageModel, Object> listener) {
        mChatService.getRoomMessages(roomId).child(mResources.getString(R.string.messages_node))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                        if (!mChatService.getCurrentUserId().equals(messageModel.getUserId())) {
                            listener.onSuccess(dataSnapshot.getValue(MessageModel.class));
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void sendMessage(MessageModel messageModel, String roomId) {
        mChatService.sendMessage(messageModel, roomId);
    }


    @Override
    public String getCurrentUserId() {
        return mChatService.getCurrentUserId();
    }
}
