/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 5/04/18 23:59
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.interactor;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 27/03/2018.
 */

public interface RequestsContactsInteractor {

    void getAllRequests(OnResultListener<List<ContactModel>,Object> listener);

    void sendRequestAccepted(ContactModel contactModel);

    void sendRequestDeclined(ContactModel contactModel);

}

