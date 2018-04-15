/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 6/04/18 12:36
 */

package com.mricomat.chat_mvp_firebase.ui.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mricomat on 01/03/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String EMPTY_STRING = "";
    private static final String OK_STRING = "OK";

    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(this);
    }

    protected void initializeData() {
    }

    protected void loadUiTexts() {
    }

    protected void setListeners() {
    }

    protected void showProgressDialog(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }

    protected void showAlertDialog(String title, String message) {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(BaseActivity.this).create();
        }
        if (title != null) {
            mAlertDialog.setTitle(title);
        } else {
            mAlertDialog.setTitle(EMPTY_STRING);
        }

        if (message != null) {
            mAlertDialog.setMessage(message);
        } else {
            mAlertDialog.setMessage(EMPTY_STRING);
        }

        mAlertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, OK_STRING,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        mAlertDialog.show();
    }
}
