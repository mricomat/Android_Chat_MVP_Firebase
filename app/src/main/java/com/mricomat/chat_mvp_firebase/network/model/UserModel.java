/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 28/03/18 17:46
 */

package com.mricomat.chat_mvp_firebase.network.model;

import android.net.Uri;

/**
 * Created by Martín Rico Martínez on 11/03/2018.
 */

public class UserModel {

    private String name;
    private String password;
    private String provider;
    private String email;
    private String id;
    private String uriProfilePhoto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUriProfilePhoto() {
        return uriProfilePhoto;
    }

    public void setUriProfilePhoto(String uriProfilePhoto) {
        this.uriProfilePhoto = uriProfilePhoto;
    }
}
