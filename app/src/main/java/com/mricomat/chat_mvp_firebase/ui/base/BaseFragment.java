/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 12:36
 */

package com.mricomat.chat_mvp_firebase.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by mricomat on 08/03/2018.
 */

public class BaseFragment extends Fragment {

    protected int mLayoutId;
    protected View mRootView;

    public BaseFragment() {
        // Need an empty Constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
    }

    protected void initializeData() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(mLayoutId, container, false);
        ButterKnife.bind(this, mRootView);
        fillViews();
        return mRootView;
    }

    protected void fillViews() {
    }
}
