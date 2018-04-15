/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:50
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.view;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;
import com.mricomat.chat_mvp_firebase.ui.base.BaseFragment;
import com.mricomat.chat_mvp_firebase.ui.contacts.ContactsActivity;
import com.mricomat.chat_mvp_firebase.ui.contacts.presenter.ContactsPresenter;
import com.mricomat.chat_mvp_firebase.ui.contacts.presenter.ContactsPresenterImpl;
import com.mricomat.chat_mvp_firebase.ui.contacts.view.RecyclerView.ContactsAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Martín Rico Martínez on 24/03/2018.
 */

public class ContactsViewFragment extends BaseFragment implements ContactsView {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_container)
    LinearLayout mEmptyContainer;

    @BindView(R.id.floating_button)
    FloatingActionButton mFloatingButton;

    private ContactsAdapter mRecyclerViewAdapter;
    private ContactsPresenter mPresenter;
    private boolean mFirstStart = true;

    public ContactsViewFragment() {
    }

    public static ContactsViewFragment newInstance() {
        return new ContactsViewFragment();
    }

    @Override
    protected void initializeData() {
        mLayoutId = R.layout.contacts_fragment;
        mPresenter = new ContactsPresenterImpl(this);
    }

    @Override
    protected void fillViews() {
        updateActionbar();
        initSwipeRefreshLayout();
        initRecyclerView();
        setListener();
    }

    private void updateActionbar() {
        ActionBar actionBar = ((ContactsActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(R.string.app_name);
    }

    @Override
    public void onStart() {
        super.onStart();
        assert mPresenter != null;
        mPresenter.onStart(mFirstStart);
        mFirstStart = false;
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onViewDetached();
            mPresenter.onPresentedDestroyed();
            mPresenter = null;
        }
        super.onDestroy();
    }

    private void setListener() {
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.findAndRequestContact();
            }
        });
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getContacts();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerViewAdapter = new ContactsAdapter();
        mRecyclerViewAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mRecyclerView.getChildAdapterPosition(view);
                ContactListModel contactModel = mRecyclerViewAdapter.getContacts().get(position);
                mPresenter.navigateToChatContact(contactModel);
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showEmptyList() {
        mEmptyContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContacts(List<ContactListModel> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            mEmptyContainer.setVisibility(View.VISIBLE);
        } else {
            mEmptyContainer.setVisibility(View.GONE);
            mRecyclerViewAdapter.setContacts(contacts);
        }
    }



    @Override
    public void showLoadingIndicator() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingIndicator() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public FragmentActivity getActivityFromView() {
        return getActivity();
    }
}
