/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 11:46
 */

package com.mricomat.chat_mvp_firebase.ui.register.presenter;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.mricomat.chat_mvp_firebase.network.model.UserModel;
import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.ui.register.interactor.RegisterInteractorImpl;
import com.mricomat.chat_mvp_firebase.ui.register.view.RegisterView;
import com.mricomat.chat_mvp_firebase.utils.NetworkUtils;

/**
 * Created by Martín Rico Martínez on 11/03/2018.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterInteractorImpl mInteractor;
    private RegisterView mView;

    public RegisterPresenterImpl(RegisterView view) {
        mView = view;
        mInteractor = new RegisterInteractorImpl();
    }

    @Override
    public void createNewUser(UserModel userModel) {
        if (NetworkUtils.isNetworkAvailable(mView.getViewActivity())) {
            mView.showLoadingIndicator();
            mInteractor.createNewUserAccount(userModel, new OnResultListener<String, Object>() {
                @Override
                public void onSuccess(String response) {
                    setSuccessResult();
                }

                @Override
                public void onError(Object errorResponse) {
                    if (errorResponse instanceof Exception) {
                        Toast.makeText(mView.getViewActivity(), ((Exception) errorResponse).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String response = (String) errorResponse;
                        if (response != null && response.equals("names")) {
                            mView.showUserNameError();
                        } else {
                            mView.showEmailError();
                        }
                    }
                    mView.resetInputs();
                    mView.hideLoadingIndicator();
                }
            });
        } else {
            mView.showNetworkErrorMessage();
        }
    }

    private void setSuccessResult() {
        mView.hideLoadingIndicator();
        mView.getViewActivity().finish();
    }
}
