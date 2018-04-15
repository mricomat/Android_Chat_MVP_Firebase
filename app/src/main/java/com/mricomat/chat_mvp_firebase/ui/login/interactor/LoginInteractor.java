/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:47
 */

package com.mricomat.chat_mvp_firebase.ui.login.interactor;

import android.content.Intent;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;

/**
 * Created by Martín Rico Martínez on 04/03/2018.
 */

public interface LoginInteractor {

    Intent getIntentLoginGoogle();

    void getAuthWithGoogle(Intent data, OnResultListener<String, Object> listener);

    void loginWithEmail(String email, String password, OnResultListener<String, Object> listener);

    boolean isAlreadyLogged();
}
