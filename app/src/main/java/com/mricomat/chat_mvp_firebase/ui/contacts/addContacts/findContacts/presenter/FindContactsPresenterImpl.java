/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 12/04/18 0:29
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.presenter;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.interactor.FindContactsInteractorImpl;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.view.FindContactsView;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public class FindContactsPresenterImpl implements FindContactsPresenter {

    private FindContactsView mView;
    private List<ContactModel> mContacts;
    private FindContactsInteractorImpl mInteractor;

    public FindContactsPresenterImpl(FindContactsView view) {
        mView = view;
        mInteractor = new FindContactsInteractorImpl();
    }

    @Override
    public void onStart(boolean firstStart) {
        if (firstStart) {
            getAllContacts();
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
    public void getAllContacts() {
        mView.showLoadingIndicator();
        mInteractor.getAllContacts(new OnResultListener<List<ContactModel>, Object>() {
            @Override
            public void onSuccess(List<ContactModel> response) {
                mContacts = response;
                mView.showContacts(mContacts);
                mView.hideLoadingIndicator();
            }

            @Override
            public void onError(Object errorResponse) {
                // TODO SHOW ERROR
                if (mView != null) {
                    mView.hideLoadingIndicator();
                }
            }
        });
    }

    @Override
    public void sendRequest(ContactModel contactModel) {
        mInteractor.sendRequest(contactModel);
        mContacts.remove(contactModel);
        mView.showContacts(mContacts);
    }
}
