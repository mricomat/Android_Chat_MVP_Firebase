/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 11:51
 */

package com.mricomat.chat_mvp_firebase.ui.login.interactor;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.FireBaseAuthService;
import com.mricomat.chat_mvp_firebase.network.FireBaseUserService;
import com.mricomat.chat_mvp_firebase.network.OnResultListener;
import com.mricomat.chat_mvp_firebase.network.model.UserModel;

/**
 * Created by Martín Rico Martínez on 04/03/2018.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private Activity mActivity;
    private FireBaseAuthService mFirebaseAuthService;
    private FireBaseUserService mFirebaseUserService;

    public LoginInteractorImpl(Activity activity) {
        mActivity = activity;
        mFirebaseAuthService = new FireBaseAuthService();
        mFirebaseUserService = new FireBaseUserService();
    }

    @Override
    public Intent getIntentLoginGoogle() {
        return mFirebaseAuthService.getUserWithGoogle(mActivity);
    }

    @Override
    public void getAuthWithGoogle(Intent data, final OnResultListener<String, Object> listener) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            mFirebaseAuthService.getAuthWithGoogle(account).addOnCompleteListener(mActivity,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                createNewUser(task);
                                listener.onSuccess(null);
                            } else {
                                listener.onError(null);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception error) {
                    listener.onError(error);
                }
            });
        } else {
            listener.onError(null);
        }
    }

    private void createNewUser(Task<AuthResult> task) {
        mFirebaseUserService.createUserIfNotExist(buildUserModel(task));
    }

    private UserModel buildUserModel(Task<AuthResult> task) {
        UserModel userModel = new UserModel();
        userModel.setId(task.getResult().getUser().getProviderData().get(0).getUid());
        userModel.setName(task.getResult().getUser().getProviderData().get(0).getDisplayName());
        userModel.setEmail(task.getResult().getUser().getProviderData().get(0).getEmail());
        userModel.setUriProfilePhoto(task.getResult().getUser().getProviderData().get(0).getPhotoUrl().toString());
        userModel.setProvider(mActivity.getString(R.string.google_provider));
        return userModel;
    }

    @Override
    public void loginWithEmail(String email, String password, final OnResultListener<String, Object> listener) {
        mFirebaseAuthService.getUserWithEmail(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess(null);
                        } else {
                            listener.onError(task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception error) {
                listener.onError(error);
            }
        });
    }

    @Override
    public boolean isAlreadyLogged() {
        return mFirebaseAuthService.isAlreadyLogged(mActivity);
    }
}
