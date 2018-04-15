/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 11:13
 */

package com.mricomat.chat_mvp_firebase.ui.register.interactor;

import com.mricomat.chat_mvp_firebase.network.model.UserModel;
import com.mricomat.chat_mvp_firebase.network.OnResultListener;

/**
 * Created by Martín Rico Martínez on 11/03/2018.
 */

public interface RegisterInteractor {

    void createNewUserAccount(UserModel userModel, final OnResultListener<String, Object> listener);

}
