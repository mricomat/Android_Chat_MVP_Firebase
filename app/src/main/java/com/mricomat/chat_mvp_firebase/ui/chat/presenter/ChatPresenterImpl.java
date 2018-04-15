/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 23:14
 */

package com.mricomat.chat_mvp_firebase.ui.chat.presenter;

import android.text.TextUtils;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.MessageModel;
import com.mricomat.chat_mvp_firebase.ui.chat.interactor.ChatInteractor;
import com.mricomat.chat_mvp_firebase.ui.chat.interactor.ChatInteractorImpl;
import com.mricomat.chat_mvp_firebase.ui.chat.view.ChatRecyclerView.ChatViewAdapter;
import com.mricomat.chat_mvp_firebase.ui.chat.view.ChatView;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Martín Rico Martínez on 01/04/2018.
 */

public class ChatPresenterImpl implements ChatPresenter {

    private static final String EMPTY_STRING = "";
    private static final String HYPHEN_STRING = "-";

    private ChatView mView;
    private ChatInteractor mInteractor;
    private ArrayList<MessageModel> mMessages;
    private MessageModel mLastMessage;

    public ChatPresenterImpl(ChatView view) {
        mView = view;
        mInteractor = new ChatInteractorImpl();
    }

    @Override
    public void onStart(boolean firstStart) {
        if (firstStart) {
            getRoomMessages();
        } else {
            mView.showAllMessages(mMessages);
        }
    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onPresentedDestroyed() {

    }

    @Override
    public void getRoomMessages() {
        mView.showLoadingIndicator();
        mInteractor.getRoomMessages(mView.getRoomId(), new OnResultListener<ArrayList<MessageModel>, Object>() {
            @Override
            public void onSuccess(ArrayList<MessageModel> response) {
                mMessages = response != null && !response.isEmpty() ? setTypeMessages(response) : response;
                mView.showAllMessages(mMessages);
                mView.hideLoadingIndicator();
            }

            @Override
            public void onError(Object errorResponse) {
                mView.hideLoadingIndicator();
            }
        });
    }

    private ArrayList<MessageModel> setTypeMessages(ArrayList<MessageModel> messages) {
        String currentUserId = mInteractor.getCurrentUserId();
        messages.remove(0);
        for (int i = 0; i < messages.size(); i++) {
            if (currentUserId.equals(messages.get(i).getUserId())) {
                messages.get(i).setTypeFrom(ChatViewAdapter.TYPE_CURRENT_USER);
            } else {
                messages.get(i).setTypeFrom(ChatViewAdapter.TYPE_OTHER_USER);
            }
        }
        return messages;
    }

    @Override
    public void sendMessage(String textMessage) {
        if (!TextUtils.isEmpty(textMessage)) {
            MessageModel messageModel = buildMessage(textMessage);
            mInteractor.sendMessage(messageModel, mView.getRoomId());
            messageModel.setTypeFrom(ChatViewAdapter.TYPE_CURRENT_USER);
            mLastMessage = messageModel;
            mView.showMessage(messageModel);
        }
    }

    private MessageModel buildMessage(String text) {
        MessageModel newMessage = new MessageModel();
        newMessage.setId(UUID.randomUUID().toString().replaceAll(HYPHEN_STRING, EMPTY_STRING));
        newMessage.setText(text);
        Long tsLong = System.currentTimeMillis()/1000;
        newMessage.setTime(tsLong.toString());
        newMessage.setUserId(mInteractor.getCurrentUserId());
        return newMessage;
    }

    @Override
    public void getIncomingMessages() {
        mInteractor.getIncomingMessages(mView.getRoomId(), new OnResultListener<MessageModel, Object>() {
            @Override
            public void onSuccess(MessageModel response) {
                response.setTypeFrom(ChatViewAdapter.TYPE_OTHER_USER);
                mLastMessage = response;
                mView.showMessage(response);
            }

            @Override
            public void onError(Object errorResponse) {

            }
        });
    }

    @Override
    public void saveLastMessage() {

    }
}
