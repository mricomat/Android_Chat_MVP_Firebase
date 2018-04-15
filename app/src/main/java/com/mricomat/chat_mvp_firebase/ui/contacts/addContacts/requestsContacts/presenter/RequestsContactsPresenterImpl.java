/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 11/04/18 22:01
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.presenter;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.interactor.RequestsContactsInteractor;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.interactor.RequestsContactsInteractorImpl;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.view.RequestsContactsView;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 27/03/2018.
 */

public class RequestsContactsPresenterImpl implements RequestsContactsPresenter {

    private RequestsContactsView mView;
    private List<ContactModel> mContacts;
    private RequestsContactsInteractor mInteractor;

    public RequestsContactsPresenterImpl(RequestsContactsView view) {
        mView = view;
        mInteractor = new RequestsContactsInteractorImpl();
    }

    @Override
    public void onStart(boolean firstStart) {
        if (firstStart) {
            getAllRequests();
        } else {
            mView.showAllRequests(mContacts);
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
    public void getAllRequests() {
        mView.showLoadingIndicator();
        mInteractor.getAllRequests(new OnResultListener<List<ContactModel>, Object>() {
            @Override
            public void onSuccess(List<ContactModel> response) {
                mContacts = response;
                mView.showAllRequests(mContacts);
                mView.hideLoadingIndicator();
            }

            @Override
            public void onError(Object errorResponse) {
                // SHOW ERROR
                if (mView != null) {
                    mView.hideLoadingIndicator();
                }
            }
        });
    }

    @Override
    public void responseRequest(ContactModel contactModel, boolean accepted) {
        if (accepted) {
            mInteractor.sendRequestAccepted(contactModel);
        } else {
            mInteractor.sendRequestDeclined(contactModel);
        }
        mContacts.remove(contactModel);
        mView.showAllRequests(mContacts);
    }
}
