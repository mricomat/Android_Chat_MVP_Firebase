/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 25/03/18 1:20
 */

package com.mricomat.chat_mvp_firebase.ui.base;

/**
 * Created by Martín Rico Martínez on 25/03/2018.
 */

public interface BasePresenter {

    void onStart(boolean firstStart);

    void onViewDetached();

    void onPresentedDestroyed();

}
