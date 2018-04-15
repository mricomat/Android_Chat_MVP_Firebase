/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 30/03/18 13:58
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.view;

import com.mricomat.chat_mvp_firebase.network.model.ContactModel;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public interface FindContactsView {

    void showContacts(List<ContactModel> contacts);

    void showLoadingIndicator();

    void hideLoadingIndicator();

}
