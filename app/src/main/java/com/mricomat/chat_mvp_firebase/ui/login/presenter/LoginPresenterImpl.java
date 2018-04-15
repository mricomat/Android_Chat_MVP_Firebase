/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:50
 */

package com.mricomat.chat_mvp_firebase.ui.login.presenter;

import android.content.Intent;
import android.widget.Toast;

import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.ui.contacts.ContactsActivity;
import com.mricomat.chat_mvp_firebase.ui.login.interactor.LoginInteractorImpl;
import com.mricomat.chat_mvp_firebase.ui.login.view.LoginActivity;
import com.mricomat.chat_mvp_firebase.ui.login.view.LoginView;
import com.mricomat.chat_mvp_firebase.ui.register.view.RegisterActivity;
import com.mricomat.chat_mvp_firebase.utils.NavigationUtils;
import com.mricomat.chat_mvp_firebase.utils.NetworkUtils;

/**
 * Created by mricomat on 01/03/2018.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mView;
    private LoginInteractorImpl mInteractor;

    public LoginPresenterImpl(LoginView view) {
        mView = view;
        mInteractor = new LoginInteractorImpl(mView.getViewActivity());
    }

    @Override
    public void loginWithEmail(String email, String pass) {
        if (NetworkUtils.isNetworkAvailable(mView.getViewActivity())) {
            mView.showLoadingIndicator();
            mInteractor.loginWithEmail(email, pass, new OnResultListener<String, Object>() {
                @Override
                public void onSuccess(String response) {
                    mView.hideLoadingIndicator();
                    startContactsActivity();
                }

                @Override
                public void onError(Object errorResponse) {
                    mView.hideLoadingIndicator();
                    if (errorResponse instanceof Exception) {
                        Toast.makeText(mView.getViewActivity(), ((Exception) errorResponse).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            mView.showNetworkErrorMessage();
        }
    }

    @Override
    public void loginWithGoogle() {
        if (NetworkUtils.isNetworkAvailable(mView.getViewActivity())) {
            mView.showLoadingIndicator();
            mView.getViewActivity().startActivityForResult(mInteractor.getIntentLoginGoogle(),
                    LoginActivity.REQUEST_SIGN_GOOGLE);
        } else {
            mView.showNetworkErrorMessage();
        }
    }

    @Override
    public void setIntentGoogleLogin(Intent data) {
        mView.showLoadingIndicator();
        mInteractor.getAuthWithGoogle(data, new OnResultListener<String, Object>() {
            @Override
            public void onSuccess(String response) {
                mView.hideLoadingIndicator();
                startContactsActivity();
            }

            @Override
            public void onError(Object errorResponse) {
                mView.hideLoadingIndicator();
                if (errorResponse instanceof Exception) {
                    Toast.makeText(mView.getViewActivity(), ((Exception) errorResponse).getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void startRegisterActivity() {
        Intent intent = new Intent(mView.getViewActivity(), RegisterActivity.class);
        mView.getViewActivity().startActivity(intent);
    }

    @Override
    public void checkIfAlreadyLogged() {
        if (mInteractor.isAlreadyLogged()) {
            startContactsActivity();
        }
    }

    private void startContactsActivity() {
        NavigationUtils.navigateToActivity(mView.getViewActivity(), ContactsActivity.class, null);
        mView.getViewActivity().finish();
    }
}
