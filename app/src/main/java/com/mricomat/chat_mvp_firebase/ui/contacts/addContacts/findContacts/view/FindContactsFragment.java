/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 12/04/18 0:29
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.mricomat.chat_mvp_firebase.ui.base.BaseFragment;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.presenter.FindContactsPresenter;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.presenter.FindContactsPresenterImpl;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.view.Recyclerview.FindContactsAdapter;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.view.Recyclerview.FindContactsViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public class FindContactsFragment extends BaseFragment implements FindContactsView {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_container)
    LinearLayout mEmptyContainer;

    @BindView(R.id.empty_text)
    TextView mEmptyText;

    private FindContactsAdapter mAdapter;
    private FindContactsPresenter mPresenter;
    private boolean mFirstStart = true;

    public FindContactsFragment() {
    }

    public static FindContactsFragment newInstance() {
        return new FindContactsFragment();
    }

    @Override
    protected void initializeData() {
        mLayoutId = R.layout.add_contacts_child_fragment;
        mPresenter = new FindContactsPresenterImpl(this);
    }

    @Override
    protected void fillViews() {
        loadUITexts();
        initSwipeRefreshLayout();
        initRecyclerView();
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

    @Override
    public void showContacts(List<ContactModel> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            mEmptyContainer.setVisibility(View.VISIBLE);
            mAdapter.setContacts(contacts);
        } else {
            mEmptyContainer.setVisibility(View.GONE);
            mAdapter.setContacts(contacts);
        }
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAllContacts();
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new FindContactsAdapter();
        mAdapter.setOnClickListener(new FindContactsViewHolder.FindContactsHolderListener() {
            @Override
            public void onSendRequestButtonClicked(FindContactsViewHolder viewHolder) {
                int position = mRecyclerView.getChildAdapterPosition(viewHolder.itemView);
                ContactModel contactModel = mAdapter.getContacts().get(position);
                mPresenter.sendRequest(contactModel);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadUITexts() {
        mEmptyText.setText(R.string.no_contacts);
    }

    @Override
    public void showLoadingIndicator() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingIndicator() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
