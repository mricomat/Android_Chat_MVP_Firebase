/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 25/03/18 19:46
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.interactor;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 05/03/2018.
 */

public interface ContactsInteractor {

    void getContacts(OnResultListener<List<ContactListModel>,Object> listener);
}
