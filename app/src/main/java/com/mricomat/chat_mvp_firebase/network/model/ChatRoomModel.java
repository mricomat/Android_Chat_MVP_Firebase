/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 10/04/18 19:46
 */

package com.mricomat.chat_mvp_firebase.network.model;

import java.util.ArrayList;

/**
 * Created by Martín Rico Martínez on 31/03/2018.
 */

public class ChatRoomModel {

    private String id;
    private ArrayList<MessageModel> messages;
    private ArrayList<ContactModel> contacts;

    public ChatRoomModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageModel> messages) {
        this.messages = messages;
    }

    public ArrayList<ContactModel> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<ContactModel> contacts) {
        this.contacts = contacts;
    }
}
