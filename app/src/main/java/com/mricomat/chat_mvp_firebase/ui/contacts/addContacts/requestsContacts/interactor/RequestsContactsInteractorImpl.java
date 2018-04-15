/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 11/04/18 21:59
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.interactor;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mricomat.chat_mvp_firebase.network.FireBaseUserService;
import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martín Rico Martínez on 27/03/2018.
 */

public class RequestsContactsInteractorImpl implements RequestsContactsInteractor{

    private FireBaseUserService mUserService;

    public RequestsContactsInteractorImpl() {
        mUserService = new FireBaseUserService();
    }

    @Override
    public void getAllRequests(final OnResultListener<List<ContactModel>, Object> listener) {
        mUserService.getCurrentReceivedRequests().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ContactModel> contacts = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    contacts.add(snapshot.getValue(ContactModel.class));
                }
                listener.onSuccess(contacts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError);
            }
        });
    }

    @Override
    public void sendRequestAccepted(ContactModel contactModel) {
        mUserService.acceptRequestResponse(contactModel);
    }

    @Override
    public void sendRequestDeclined(ContactModel contactModel) {
        mUserService.declineRequestResponse(contactModel);
    }
}
