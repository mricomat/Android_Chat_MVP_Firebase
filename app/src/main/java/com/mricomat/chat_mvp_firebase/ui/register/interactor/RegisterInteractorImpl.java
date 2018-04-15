/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 11:13
 */

package com.mricomat.chat_mvp_firebase.ui.register.interactor;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mricomat.chat_mvp_firebase.network.FireBaseUserService;
import com.mricomat.chat_mvp_firebase.network.model.UserModel;
import com.mricomat.chat_mvp_firebase.network.FireBaseAuthService;
import com.mricomat.chat_mvp_firebase.network.OnResultListener;

/**
 * Created by Martín Rico Martínez on 11/03/2018.
 */

public class RegisterInteractorImpl implements RegisterInteractor {

    private static final String ERROR_RESULT_NAMES = "names";
    private static final String ERROR_RESULT_DATABASE = "database";

    private FireBaseAuthService mFirebaseAuthService;
    private FireBaseUserService mFirebaseUserService;
    private UserModel mUserModel;
    private OnResultListener<String, Object> mListener;


    public RegisterInteractorImpl() {
        mFirebaseAuthService = new FireBaseAuthService();
        mFirebaseUserService = new FireBaseUserService();
    }

    @Override
    public void createNewUserAccount(UserModel userModel, OnResultListener<String, Object> listener) {
        mListener = listener;
        mUserModel = userModel;
        checkIfUserExists(userModel);
    }

    private void checkIfUserExists(final UserModel userModel) {
        mFirebaseUserService.getUsersNames().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(userModel.getName())) {
                    mListener.onError(ERROR_RESULT_NAMES);
                } else {
                    createUserWithEmail();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mListener.onError(ERROR_RESULT_DATABASE);
            }
        });
    }

    private void createUserWithEmail() {
        mFirebaseAuthService.createUserWithEmail(mUserModel.getEmail(), mUserModel.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUserModel.setId(mFirebaseUserService.getCurrentUserId());
                            mFirebaseUserService.createUser(mUserModel);
                            mListener.onSuccess(ERROR_RESULT_NAMES);
                        } else{
                            mListener.onError(task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception error) {
                mListener.onError(error);
            }
        });
    }
}
