/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:47
 */

package com.mricomat.chat_mvp_firebase.ui.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.ui.base.BaseActivity;
import com.mricomat.chat_mvp_firebase.ui.login.presenter.LoginPresenter;
import com.mricomat.chat_mvp_firebase.ui.login.presenter.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginView {

    public static final String EMPTY_STRING = "";
    public static final int REQUEST_SIGN_GOOGLE = 9001;

    @BindView(R.id.linear_focus)
    LinearLayout mMainLinear;

    @BindView(R.id.title_text)
    TextView mTitleText;

    @BindView(R.id.email_edit_text)
    EditText mEmailInput;

    @BindView(R.id.password_edit_text)
    EditText mPasswordInput;

    @BindView(R.id.error_text)
    TextView mErrorText;

    @BindView(R.id.sign_in_button)
    Button mSignInButton;

    @BindView(R.id.google_button)
    Button mGoogleButton;

    @BindView(R.id.no_account_text)
    TextView mNoAccountText;

    @BindView(R.id.sign_up_text)
    TextView mSignUpButton;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        initializeData();
        mMainLinear.requestFocus();
        loadUiTexts();
        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.checkIfAlreadyLogged();
    }

    @Override
    protected void initializeData() {
        mPresenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void setListeners() {
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    mPresenter.loginWithEmail(mEmailInput.getText().toString(),
                            mPasswordInput.getText().toString());
                } else {
                    mErrorText.setText(R.string.input_error_empty_fields);
                }
            }
        });

        mGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.loginWithGoogle();
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.startRegisterActivity();
            }
        });

        mEmailInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    mErrorText.setText(EMPTY_STRING);
                }
            }
        });

        mPasswordInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    mErrorText.setText(EMPTY_STRING);
                }
            }
        });
    }

    private boolean checkData() {
        return !(mEmailInput.getText().toString().equals(EMPTY_STRING) || mPasswordInput.toString().equals(EMPTY_STRING));
    }

    @Override
    public void showInvalidCredentialsMessage() {
        mErrorText.setText(getResources().getString(R.string.input_error_email_password_incorrect));
    }

    @Override
    public void showLoginSuccessMessage() {

    }

    @Override
    public void showNetworkErrorMessage() {
        showAlertDialog(getString(R.string.login_error), getString(R.string.error_server_connexion));
    }

    @Override
    public void showLoadingIndicator() {
        showProgressDialog(getString(R.string.wait_progress));
    }

    @Override
    public void hideLoadingIndicator() {
        hideProgressDialog();
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGN_GOOGLE) {
            mPresenter.setIntentGoogleLogin(data);
        }
    }

    // Control focus of EditTexts
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
