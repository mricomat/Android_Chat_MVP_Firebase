/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 10:59
 */

package com.mricomat.chat_mvp_firebase.ui.register.view;

import android.app.Activity;

/**
 * Created by Martín Rico Martínez on 10/03/2018.
 */

public interface RegisterView {

    void showUserNameError();

    void showEmailError();

    void showNetworkErrorMessage();

    void showLoadingIndicator();

    void resetInputs();

    void hideLoadingIndicator();

    Activity getViewActivity();

}
