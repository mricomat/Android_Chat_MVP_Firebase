/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:42
 */

package com.mricomat.chat_mvp_firebase.ui;

import android.app.Application;
import android.content.Context;

/**
 * Created by Martín Rico Martínez on 03/04/2018.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
