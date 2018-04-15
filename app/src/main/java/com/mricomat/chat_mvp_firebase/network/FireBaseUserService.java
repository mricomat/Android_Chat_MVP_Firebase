/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 14/04/18 1:35
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
import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.mricomat.chat_mvp_firebase.network.model.UserModel;
import com.mricomat.chat_mvp_firebase.ui.App;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Martín Rico Martínez on 18/03/2018.
 */

public class FireBaseUserService {

    private static final String EMPTY_STRING = "";
    private static final String HYPHEN_STRING = "-";

    private DatabaseReference mDatabaseReference;
    private Resources mResources;

    public FireBaseUserService() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mResources = App.getContext().getResources();
    }

    public void createUser(UserModel userModel) {
        mDatabaseReference.child(mResources.getString(R.string.user_names_node)).child(userModel.getName())
                .setValue(userModel.getEmail());
        mDatabaseReference.child(mResources.getString(R.string.users_node)).child(userModel.getId()).setValue(userModel);
    }

    public void createUserIfNotExist(final UserModel userModel) {
        mDatabaseReference.child(mResources.getString(R.string.user_names_node))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(userModel.getName())) {
                            createUser(userModel);
                        } else {
                            // TODO REFACTOR Problema cuando dos usuarios de google tienen el mismo nombre
                            userModel.setName(userModel.getName());
                            createUser(userModel);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public DatabaseReference getUsersNames() {
        return mDatabaseReference.child(mResources.getString(R.string.user_names_node));
    }

    public String getCurrentUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public DatabaseReference getContactsFromUser() {
        return mDatabaseReference.child(mResources.getString(R.string.user_contacts_node)).child(getCurrentUserId());
    }

    public DatabaseReference getCurrentSentRequests() {
        return mDatabaseReference.child(mResources.getString(R.string.user_requests_node))
                .child(getCurrentUserId()).child(mResources.getString(R.string.sent_node));
    }

    public void sendRequest(final ContactModel contactModel) {
        /**  Save on currentUser the sent request */
        mDatabaseReference.child(mResources.getString(R.string.user_requests_node))
                .child(getCurrentUserId()).child(mResources.getString(R.string.sent_node))
                .child(contactModel.getName()).setValue(contactModel);

        /** Get the currentUserData to save on the other contact the "received request" */
        mDatabaseReference.child(mResources.getString(R.string.users_node)).child(getCurrentUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ContactModel currentContactModel = new ContactModel();
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        currentContactModel.setId(getCurrentUserId());
                        currentContactModel.setName(userModel.getName());
                        currentContactModel.setProfilePhoto(userModel.getUriProfilePhoto());
                        mDatabaseReference.child(mResources.getString(R.string.user_requests_node))
                                .child(contactModel.getId()).child(mResources.getString(R.string.received_node))
                                .child(currentContactModel.getName()).setValue(currentContactModel);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public DatabaseReference getCurrentReceivedRequests() {
        return mDatabaseReference.child(mResources.getString(R.string.user_requests_node)).child(getCurrentUserId())
                .child(mResources.getString(R.string.received_node));
    }

    public void acceptRequestResponse(final ContactModel contactModel) {
        /**  First of all we have to delete the request form both users*/
        removeRequest(contactModel);

        /**  Add new contact to the current user  */
        ContactListModel currentContactListModel = new ContactListModel();
        currentContactListModel.setContact(contactModel);
        currentContactListModel.setLastTimeMessage(EMPTY_STRING);
        currentContactListModel.setLastMessage(mResources.getString(R.string.new_contact));
        final String chatRoomId = UUID.randomUUID().toString().replaceAll(HYPHEN_STRING, EMPTY_STRING);
        currentContactListModel.setChatRoomId(chatRoomId);

        /**  New ChatRoom  */
        final ChatRoomModel chatRoomModel = new ChatRoomModel();
        chatRoomModel.setId(chatRoomId);
        final ArrayList<ContactModel> contacts = new ArrayList<>();
        contacts.add(contactModel);

        /**  Add new contact to the accepted user  */
        mDatabaseReference.child(mResources.getString(R.string.user_contacts_node))
                .child(getCurrentUserId()).child(contactModel.getId())
                .setValue(currentContactListModel);

        mDatabaseReference.child(mResources.getString(R.string.users_node)).child(getCurrentUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        mDatabaseReference.child(mResources.getString(R.string.user_contacts_node))
                                .child(contactModel.getId()).child(userModel.getId())
                                .setValue(buildContactListModel(userModel, chatRoomId));

                        ContactModel contact = new ContactModel();
                        contact.setName(userModel.getName());
                        contact.setId(userModel.getId());
                        contacts.add(contact);
                        chatRoomModel.setContacts(contacts);

                        mDatabaseReference.child(mResources.getString(R.string.chat_rooms_node)).child(chatRoomId)
                                .setValue(chatRoomModel);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private ContactListModel buildContactListModel(UserModel userModel, String chatRoomId) {
        ContactListModel contactListModel = new ContactListModel();
        ContactModel contactModel = new ContactModel();
        contactModel.setId(userModel.getId());
        contactModel.setName(userModel.getName());
        contactModel.setProfilePhoto(userModel.getUriProfilePhoto());
        contactListModel.setContact(contactModel);
        contactListModel.setLastMessage(mResources.getString(R.string.new_contact));
        contactListModel.setLastTimeMessage(EMPTY_STRING);
        contactListModel.setChatRoomId(chatRoomId);
        return contactListModel;
    }

    public void declineRequestResponse(ContactModel contactModel) {
        removeRequest(contactModel);
    }

    private void removeRequest(final ContactModel contactModel) {
        mDatabaseReference.child(mResources.getString(R.string.user_requests_node)).child(
                getCurrentUserId()).child(mResources.getString(R.string.received_node))
                .child(contactModel.getName()).removeValue();


        mDatabaseReference.child(mResources.getString(R.string.users_node)).child(getCurrentUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDatabaseReference.child(mResources.getString(R.string.user_requests_node))
                                .child(contactModel.getId()).child(mResources.getString(R.string.sent_node))
                                .child(dataSnapshot.getValue(UserModel.class).getName()).removeValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public DatabaseReference getAllUsers() {
        return mDatabaseReference.child(mResources.getString(R.string.users_node));
    }
}
