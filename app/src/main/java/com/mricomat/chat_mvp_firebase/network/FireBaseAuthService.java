/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 11:51
 */

package com.mricomat.chat_mvp_firebase.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.UserModel;

/**
 * Created by Martín Rico Martínez on 04/03/2018.
 */

public class FireBaseAuthService {

    private FirebaseAuth mFirebaseAuth;
    private static GoogleApiClient mGoogleApiClient;
    private static GoogleSignInClient mGoogleSignInClient;

    public FireBaseAuthService() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> getUserWithEmail(String email, String password) {
        return mFirebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> createUserWithEmail(String email, String password) {
        return mFirebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public Intent getUserWithGoogle(final Activity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(activity.getApplicationContext())
                    .enableAutoManage((FragmentActivity) activity, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(activity.getApplicationContext(), connectionResult.getErrorMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }


        return Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    }

    public Task<AuthResult> getAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return mFirebaseAuth.signInWithCredential(credential);
    }

    public boolean isAlreadyLogged(Activity activity) {
        if (mFirebaseAuth.getCurrentUser() != null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

        }
        return mFirebaseAuth.getCurrentUser() != null;
    }

    public static void logOut(Activity activity) {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}
