/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 9/04/18 23:03
 */

package com.mricomat.chat_mvp_firebase.ui.chat.interactor;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.MessageModel;

import java.util.ArrayList;

/**
 * Created by Martín Rico Martínez on 01/04/2018.
 */

public interface ChatInteractor {

    void getRoomMessages(String roomId, OnResultListener<ArrayList<MessageModel>, Object> listener);

    void getIncomingMessages(String roomId, OnResultListener<MessageModel, Object> listener);

    void sendMessage(MessageModel messageModel, String roomId);

    String getCurrentUserId();

}
