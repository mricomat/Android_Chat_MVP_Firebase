/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:47
 */

package com.mricomat.chat_mvp_firebase.ui.login.view;

import android.app.Activity;

/**
 * Created by mricomat on 01/03/2018.
 */

public interface LoginView {

    void showInvalidCredentialsMessage();

    void showLoginSuccessMessage();

    void showNetworkErrorMessage();

    void showLoadingIndicator();

    void hideLoadingIndicator();

    Activity getViewActivity();

}
