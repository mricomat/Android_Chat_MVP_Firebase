/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 18/03/18 19:04
 */

package com.mricomat.chat_mvp_firebase.ui.register.presenter;

import com.mricomat.chat_mvp_firebase.network.model.UserModel;

/**
 * Created by Martín Rico Martínez on 11/03/2018.
 */

public interface RegisterPresenter {

    void createNewUser(UserModel userModel);

}
