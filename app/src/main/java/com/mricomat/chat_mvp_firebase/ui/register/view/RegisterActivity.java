/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:28
 */

package com.mricomat.chat_mvp_firebase.ui.register.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.UserModel;
import com.mricomat.chat_mvp_firebase.ui.base.BaseActivity;
import com.mricomat.chat_mvp_firebase.ui.register.presenter.RegisterPresenter;
import com.mricomat.chat_mvp_firebase.ui.register.presenter.RegisterPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Martín Rico Martínez on 03/03/2018.
 */

public class RegisterActivity extends BaseActivity implements RegisterView {

    private static final String EMPTY_STRING = "";

    @BindView(R.id.linear_focus)
    LinearLayout mMainLinear;

    @BindView(R.id.username_edit_text)
    EditText mUsernameField;

    @BindView(R.id.email_edit_text)
    EditText mEmailField;

    @BindView(R.id.password_edit_text)
    EditText mPasswordField;

    @BindView(R.id.error_text)
    TextView mErrorText;

    @BindView(R.id.register_button)
    Button mRegisterButton;

    @BindView(R.id.already_account_text)
    TextView mAlreadyAccountText;

    @BindView(R.id.sign_in_text)
    TextView mSignInText;

    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        ButterKnife.bind(this);

        initializeData();
        mMainLinear.requestFocus();
        setListeners();
    }

    @Override
    protected void initializeData() {
        mPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    protected void setListeners() {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()) {
                    UserModel userModel = new UserModel();
                    userModel.setName(mUsernameField.getText().toString());
                    userModel.setEmail(mEmailField.getText().toString());
                    userModel.setPassword(mPasswordField.getText().toString());
                    userModel.setProvider(getString(R.string.email_provider));
                    mPresenter.createNewUser(userModel);
                } else {
                    mErrorText.setText(getString(R.string.input_error_empty_fields));
                }
            }
        });

        mSignInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mUsernameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mErrorText.setText(EMPTY_STRING);
                }
            }
        });

        mEmailField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mErrorText.setText(EMPTY_STRING);
                }
            }
        });

        mPasswordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mErrorText.setText(EMPTY_STRING);
                }
            }
        });
    }

    private boolean checkData() {
        return !(mUsernameField.getText().toString().equals(EMPTY_STRING) || mEmailField.toString().equals(EMPTY_STRING)
                || mPasswordField.toString().equals(EMPTY_STRING));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void showUserNameError() {
        showAlertDialog(getString(R.string.generic_error), getString(R.string.username_exists));
    }

    @Override
    public void showEmailError() {
        showAlertDialog(getString(R.string.generic_error), getString(R.string.email_exists));
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
    public void resetInputs() {
        mUsernameField.setText(EMPTY_STRING);
        mEmailField.setText(EMPTY_STRING);
        mPasswordField.setText(EMPTY_STRING);
    }

    @Override
    public void hideLoadingIndicator() {
        hideProgressDialog();
    }

    @Override
    public Activity getViewActivity() {
        return RegisterActivity.this;
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
