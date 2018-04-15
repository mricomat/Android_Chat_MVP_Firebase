/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 30/03/18 13:58
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.view.RecyclerView;

/**
 * Created by Martín Rico Martínez on 27/03/2018.
 */

public interface RequestsContactsViewHolderListener {

    void onRequestAccepted(RequestsContactsViewHolder viewHolder);

    void onRequestDeclined(RequestsContactsViewHolder viewHolder);

}
