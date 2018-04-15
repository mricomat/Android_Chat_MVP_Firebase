/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 14/04/18 1:41
 */

package com.mricomat.chat_mvp_firebase.network;

import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.ChatRoomModel;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.mricomat.chat_mvp_firebase.network.model.MessageModel;
import com.mricomat.chat_mvp_firebase.ui.App;

import java.util.ArrayList;

/**
 * Created by Martín Rico Martínez on 01/04/2018.
 */

public class FireBaseChatService {

    private static final int ONE_INCREMENT = 1;

    private DatabaseReference mDatabaseReference;
    private Resources mResources;

    public FireBaseChatService() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mResources = App.getContext().getResources();
    }

    public DatabaseReference getRoomMessages(String roomId) {
        return mDatabaseReference.child(mResources.getString(R.string.chat_rooms_node)).child(roomId);
    }

    /**
     * Send messageModel to the roomId
     */
    public void sendMessage(final MessageModel messageModel, final String roomId) {
        mDatabaseReference.child(mResources.getString(R.string.chat_rooms_node)).child(roomId)
                .child(mResources.getString(R.string.messages_node)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String messageCount = Long.toString(dataSnapshot.getChildrenCount() + ONE_INCREMENT);
                mDatabaseReference.child(mResources.getString(R.string.chat_rooms_node))
                        .child(roomId).child(mResources.getString(R.string.messages_node))
                        .child(messageCount).setValue(messageModel);

                updateLastMessage(messageModel, roomId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public String getCurrentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }


    public void updateLastMessage(final MessageModel messageModel, String chatRoomId) {

        /** Get the contacts of this chat */
        mDatabaseReference.child("chat_rooms").child(chatRoomId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ContactModel> roomContacts;
                roomContacts = dataSnapshot.getValue(ChatRoomModel.class).getContacts();

                /** Compare witch id is yours and witch id is from the other user */
                String otherUserId;
                otherUserId = roomContacts.get(0).getId().equals(messageModel.getUserId()) ?
                        roomContacts.get(1).getId() : roomContacts.get(0).getId();

                /** Save on user_contacts "lastMessage" and "lastTimeMessage" */
                mDatabaseReference.child(mResources.getString(R.string.user_contacts_node)).child(getCurrentUserId())
                        .child(otherUserId).child("lastMessage").setValue(messageModel.getText());
                mDatabaseReference.child(mResources.getString(R.string.user_contacts_node)).child(getCurrentUserId())
                        .child(otherUserId).child("lastTimeMessage").setValue(messageModel.getTime());

                mDatabaseReference.child(mResources.getString(R.string.user_contacts_node)).child(otherUserId)
                        .child(getCurrentUserId()).child("lastMessage").setValue(messageModel.getText());
                mDatabaseReference.child(mResources.getString(R.string.user_contacts_node)).child(otherUserId)
                        .child(getCurrentUserId()).child("lastTimeMessage").setValue(messageModel.getTime());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
