/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 13:52
 */

package com.mricomat.chat_mvp_firebase.ui.chat.presenter;

import com.mricomat.chat_mvp_firebase.network.model.MessageModel;
import com.mricomat.chat_mvp_firebase.ui.base.BasePresenter;

/**
 * Created by Martín Rico Martínez on 01/04/2018.
 */

public interface ChatPresenter extends BasePresenter {

    void getRoomMessages();

    void sendMessage(String textMessage);

    void getIncomingMessages();

    void saveLastMessage();
}
