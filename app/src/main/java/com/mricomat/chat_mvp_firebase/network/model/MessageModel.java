/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 1/04/18 14:44
 */

package com.mricomat.chat_mvp_firebase.network.model;

/**
 * Created by Martín Rico Martínez on 31/03/2018.
 */

public class MessageModel {

    private String id;
    private String text;
    private String time;
    private String userId;
    private int typeFrom;

    public MessageModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTypeFrom() {
        return typeFrom;
    }

    public void setTypeFrom(int typeFrom) {
        this.typeFrom = typeFrom;
    }
}
