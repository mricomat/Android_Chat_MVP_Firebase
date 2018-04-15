/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:28
 */

package com.mricomat.chat_mvp_firebase.ui.login.presenter;

import android.content.Intent;

/**
 * Created by mricomat on 01/03/2018.
 */

public interface LoginPresenter {

    void loginWithEmail(String email, String pass);

    void loginWithGoogle();

    void setIntentGoogleLogin(Intent data);

    void startRegisterActivity();

    void checkIfAlreadyLogged();

}
