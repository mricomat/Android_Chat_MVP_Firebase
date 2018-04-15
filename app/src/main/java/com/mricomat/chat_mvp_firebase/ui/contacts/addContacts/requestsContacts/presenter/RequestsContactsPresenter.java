/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 12/04/18 0:29
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.presenter;

import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.mricomat.chat_mvp_firebase.ui.base.BasePresenter;

/**
 * Created by Martín Rico Martínez on 27/03/2018.
 */

public interface RequestsContactsPresenter extends BasePresenter{

    void getAllRequests();

    void responseRequest(ContactModel contactModel, boolean accepted);

}
