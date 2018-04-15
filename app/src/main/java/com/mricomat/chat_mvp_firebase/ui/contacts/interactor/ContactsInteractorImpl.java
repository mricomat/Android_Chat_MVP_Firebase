/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 11/04/18 21:57
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.interactor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mricomat.chat_mvp_firebase.network.FireBaseUserService;
import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martín Rico Martínez on 05/03/2018.
 */

public class ContactsInteractorImpl implements ContactsInteractor {

    private FireBaseUserService mFirebaseUserService;

    public ContactsInteractorImpl() {
        mFirebaseUserService = new FireBaseUserService();
    }

    @Override
    public void getContacts(final OnResultListener<List<ContactListModel>, Object> listener) {
        mFirebaseUserService.getContactsFromUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ContactListModel> contacts = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    contacts.add(snapshot.getValue(ContactListModel.class));
                }
                listener.onSuccess(contacts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError);
            }
        });
    }
}
