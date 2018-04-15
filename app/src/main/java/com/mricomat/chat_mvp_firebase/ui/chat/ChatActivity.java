/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 5/04/18 14:36
 */

package com.mricomat.chat_mvp_firebase.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.FireBaseAuthService;
import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;
import com.mricomat.chat_mvp_firebase.ui.about.AboutActivity;
import com.mricomat.chat_mvp_firebase.ui.base.BaseActivity;
import com.mricomat.chat_mvp_firebase.ui.chat.view.ChatViewFragment;
import com.mricomat.chat_mvp_firebase.ui.contacts.presenter.ContactsPresenterImpl;
import com.mricomat.chat_mvp_firebase.ui.login.view.LoginActivity;
import com.mricomat.chat_mvp_firebase.utils.NavigationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Martín Rico Martínez on 30/03/2018.
 */

public class ChatActivity extends BaseActivity {

    public static final int VIEW_FRAME = R.id.frame_container;
    public static final String ID_CONTACT_KEY = "ID_CONTACT";

    @BindView(R.id.toolbar_layout)
    Toolbar mToolbar;

    private ContactListModel mContactListModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);

        getArguments();
        setupToolbar();

        navigationChatViewFragment();
    }

    private void navigationChatViewFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(ID_CONTACT_KEY, mContactListModel.getChatRoomId());
        ChatViewFragment chatViewFragment = ChatViewFragment.newInstance();
        chatViewFragment.setArguments(bundle);

        NavigationUtils.navigateToFragment(this, chatViewFragment, VIEW_FRAME, false, false);
    }

    private void getArguments() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        mContactListModel = bundle.getParcelable(ContactsPresenterImpl.CONTACT_ARGUMENT);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mContactListModel.getContact().getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.pref:
                return true;
            case R.id.about:
                NavigationUtils.navigateToActivity(this, AboutActivity.class, null);
                return true;
            case R.id.logOut:
                FireBaseAuthService.logOut(this);
                NavigationUtils.navigateToActivity(this, LoginActivity.class, null);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
