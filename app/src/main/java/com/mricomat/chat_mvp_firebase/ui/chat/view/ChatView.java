/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 1/04/18 14:44
 */

package com.mricomat.chat_mvp_firebase.ui.chat.view;

import com.mricomat.chat_mvp_firebase.network.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martín Rico Martínez on 31/03/2018.
 */

public interface ChatView {

    void showAllMessages(ArrayList<MessageModel> messages);

    void showMessage(MessageModel message);

    String getRoomId();

    void showLoadingIndicator();

    void hideLoadingIndicator();

}
