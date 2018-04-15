/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 30/03/18 13:53
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.presenter;

import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;
import com.mricomat.chat_mvp_firebase.ui.base.BasePresenter;

/**
 * Created by Martín Rico Martínez on 05/03/2018.
 */

public interface ContactsPresenter extends BasePresenter{

    void getContacts();

    void navigateToChatContact(ContactListModel contactListModel);

    void findAndRequestContact();

}
