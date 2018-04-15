/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 11/04/18 23:18
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.interactor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mricomat.chat_mvp_firebase.network.FireBaseUserService;
import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.mricomat.chat_mvp_firebase.network.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public class FindContactsInteractorImpl implements FindContactsInteractor {

    private FireBaseUserService mUserService;
    private OnResultListener<List<ContactModel>, Object> mListener;

    public FindContactsInteractorImpl() {
        mUserService = new FireBaseUserService();
    }

    @Override
    public void getAllContacts(final OnResultListener<List<ContactModel>, Object> listener) {
        mListener = listener;
        getCurrentSentRequests();
    }

    private void getCurrentSentRequests() {
        mUserService.getCurrentSentRequests().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> usersRequestedIds = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    assert (snapshot.getValue(ContactModel.class)) != null;
                    usersRequestedIds.add((snapshot.getValue(ContactModel.class)).getId());
                }
                getCurrentContacts(usersRequestedIds);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getCurrentContacts(final ArrayList<String> usersRequestedIds) {
        mUserService.getContactsFromUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> currentContactsIds = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    currentContactsIds.add(snapshot.getValue(ContactListModel.class).getContact().getId());
                }
                getAllUsers(usersRequestedIds, currentContactsIds);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getAllUsers(final ArrayList<String> usersRequestedIds, final ArrayList<String> currentContactsIds) {
        mUserService.getAllUsers().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ContactModel> contacts = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    assert userModel != null;
                    // All possible Contact on this list they don't have to be actualContacts,
                    // requestedContacts or the current User
                    if (!mUserService.getCurrentUserId().equals(userModel.getId()) &&
                            !usersRequestedIds.contains(userModel.getId()) &&
                            !currentContactsIds.contains(userModel.getId())) {
                        contacts.add(buildContactModel(userModel));
                    }
                }
                mListener.onSuccess(contacts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private ContactModel buildContactModel(UserModel userModel) {
        ContactModel contactModel = new ContactModel();
        contactModel.setName(userModel.getName());
        contactModel.setId(userModel.getId());
        contactModel.setProfilePhoto(userModel.getUriProfilePhoto());
        return contactModel;
    }

    @Override
    public void sendRequest(ContactModel contactModel) {
        mUserService.sendRequest(contactModel);
    }
}
