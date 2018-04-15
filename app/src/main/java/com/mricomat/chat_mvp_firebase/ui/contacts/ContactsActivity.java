/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:50
 */

package com.mricomat.chat_mvp_firebase.ui.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;


import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.FireBaseAuthService;
import com.mricomat.chat_mvp_firebase.ui.about.AboutActivity;
import com.mricomat.chat_mvp_firebase.ui.base.BaseActivity;
import com.mricomat.chat_mvp_firebase.ui.contacts.view.ContactsViewFragment;
import com.mricomat.chat_mvp_firebase.ui.login.view.LoginActivity;
import com.mricomat.chat_mvp_firebase.utils.NavigationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Martín Rico Martínez on 05/03/2018.
 */

public class ContactsActivity extends BaseActivity {

    public static final int VIEW_FRAME = R.id.frame_container;

    @BindView(R.id.frame_container)
    FrameLayout mFrameContainer;

    @BindView(R.id.toolbar_layout)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);

        setupToolbar();

        NavigationUtils.navigateToFragment((FragmentActivity) this, ContactsViewFragment.newInstance(),
                VIEW_FRAME, false, false);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        int count = getFragmentManager().getBackStackEntryCount();
        if(count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
