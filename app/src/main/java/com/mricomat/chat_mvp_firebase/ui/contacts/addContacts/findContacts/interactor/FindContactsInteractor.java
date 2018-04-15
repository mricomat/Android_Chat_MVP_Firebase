/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 12/04/18 0:29
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.interactor;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public interface FindContactsInteractor {

    void getAllContacts(OnResultListener<List<ContactModel>,Object> listener);

    void sendRequest(ContactModel contactModel);

}
