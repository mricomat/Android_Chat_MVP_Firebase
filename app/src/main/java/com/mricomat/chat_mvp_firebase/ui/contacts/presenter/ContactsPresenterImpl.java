/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 12:36
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.presenter;

import android.os.Bundle;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;
import com.mricomat.chat_mvp_firebase.ui.chat.ChatActivity;
import com.mricomat.chat_mvp_firebase.ui.contacts.ContactsActivity;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.AddContactsFragment;
import com.mricomat.chat_mvp_firebase.ui.contacts.interactor.ContactsInteractor;
import com.mricomat.chat_mvp_firebase.ui.contacts.interactor.ContactsInteractorImpl;
import com.mricomat.chat_mvp_firebase.ui.contacts.view.ContactsView;
import com.mricomat.chat_mvp_firebase.utils.NavigationUtils;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 05/03/2018.
 */

public class ContactsPresenterImpl implements ContactsPresenter {

    public static final String CONTACT_ARGUMENT = "CONTACT_ARGUMENT";

    private ContactsView mView;
    private ContactsInteractor mInteractor;
    private List<ContactListModel> mContacts;

    public ContactsPresenterImpl(ContactsView view) {
        mView = view;
        mInteractor = new ContactsInteractorImpl();
    }

    @Override
    public void onStart(boolean firstStart) {
        if (firstStart) {
            getContacts();
        } else {
            mView.showContacts(mContacts);
        }
    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onPresentedDestroyed() {
        // Cancel some Process
    }

    @Override
    public void getContacts() {
        mView.showLoadingIndicator();
        mInteractor.getContacts(new OnResultListener<List<ContactListModel>, Object>() {
            @Override
            public void onSuccess(List<ContactListModel> response) {
                mContacts = response;
                mView.showContacts(mContacts);
                mView.hideLoadingIndicator();
            }

            @Override
            public void onError(Object errorResponse) {
                mView.hideLoadingIndicator();
            }
        });
    }

    @Override
    public void navigateToChatContact(ContactListModel contactListModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CONTACT_ARGUMENT, contactListModel);
        NavigationUtils.navigateToActivity(mView.getActivityFromView(), ChatActivity.class, bundle);
    }

    @Override
    public void findAndRequestContact() {
        NavigationUtils.navigateToFragment(mView.getActivityFromView(), AddContactsFragment.newInstance(),
                ContactsActivity.VIEW_FRAME, true, true);
    }
}
