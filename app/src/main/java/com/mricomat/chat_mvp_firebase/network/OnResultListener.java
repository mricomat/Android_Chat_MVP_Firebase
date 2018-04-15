/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 10:05
 */

package com.mricomat.chat_mvp_firebase.network;

/**
 * Created by Martín Rico Martínez on 04/03/2018.
 */

public interface OnResultListener<S,E> {

    void onSuccess(S response);

    void onError(E errorResponse);
}
