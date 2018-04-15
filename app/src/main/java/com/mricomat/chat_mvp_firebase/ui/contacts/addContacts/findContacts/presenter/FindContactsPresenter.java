/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 30/03/18 13:58
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.presenter;

import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.mricomat.chat_mvp_firebase.ui.base.BasePresenter;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public interface FindContactsPresenter extends BasePresenter{

    void getAllContacts();

    void sendRequest(ContactModel contactModel);

}
