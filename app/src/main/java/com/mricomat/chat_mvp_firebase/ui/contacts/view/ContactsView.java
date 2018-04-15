/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 26/03/18 1:16
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.view;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;

import java.util.List;

/**
 * Created by Martín Rico Martínez on 05/03/2018.
 */

public interface ContactsView {

    void showEmptyList();

    void showContacts(List<ContactListModel> contacts);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    FragmentActivity getActivityFromView();

}
