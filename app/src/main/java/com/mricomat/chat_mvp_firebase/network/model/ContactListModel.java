/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 11:54
 */

package com.mricomat.chat_mvp_firebase.network.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Martín Rico Martínez on 25/03/2018.
 */

public class ContactListModel implements Parcelable {

    private ContactModel contact;
    private String lastTimeMessage;
    private String lastMessage;
    private String chatRoomId;

    public ContactListModel() {
    }

    public ContactModel getContact() {
        return contact;
    }

    public void setContact(ContactModel contact) {
        this.contact = contact;
    }

    public String getLastTimeMessage() {
        return lastTimeMessage;
    }

    public void setLastTimeMessage(String lastTimeMessage) {
        this.lastTimeMessage = lastTimeMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    protected ContactListModel(Parcel in) {
        contact = in.readParcelable(ContactModel.class.getClassLoader());
        lastTimeMessage = in.readString();
        lastMessage = in.readString();
        chatRoomId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(contact, flags);
        dest.writeString(lastTimeMessage);
        dest.writeString(lastMessage);
        dest.writeString(chatRoomId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactListModel> CREATOR = new Creator<ContactListModel>() {
        @Override
        public ContactListModel createFromParcel(Parcel in) {
            return new ContactListModel(in);
        }

        @Override
        public ContactListModel[] newArray(int size) {
            return new ContactListModel[size];
        }
    };

}
