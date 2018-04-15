/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 12/04/18 0:29
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.mricomat.chat_mvp_firebase.ui.base.BaseFragment;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.presenter.RequestsContactsPresenter;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.presenter.RequestsContactsPresenterImpl;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.view.RecyclerView.RequestsContactsAdapter;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.view.RecyclerView.RequestsContactsViewHolder;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.view.RecyclerView.RequestsContactsViewHolderListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public class RequestsContactsFragment extends BaseFragment implements RequestsContactsView {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_container)
    LinearLayout mEmptyContainer;

    @BindView(R.id.empty_text)
    TextView mEmptyText;

    private RequestsContactsPresenter mPresenter;
    private RequestsContactsAdapter mAdapter;
    private boolean mFirstStart = true;

    public RequestsContactsFragment() {
        mPresenter = new RequestsContactsPresenterImpl(this);
    }

    public static RequestsContactsFragment newInstance() {
        return new RequestsContactsFragment();
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
    protected void initializeData() {
        mLayoutId = R.layout.add_contacts_child_fragment;
    }

    @Override
    protected void fillViews() {
        loadUITexts();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAllRequests();
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new RequestsContactsAdapter();
        mAdapter.setOnClickListener(new RequestsContactsViewHolderListener() {
            @Override
            public void onRequestAccepted(RequestsContactsViewHolder viewHolder) {
                mPresenter.responseRequest(getContactFromRecycler(viewHolder.itemView), true);
            }

            @Override
            public void onRequestDeclined(RequestsContactsViewHolder viewHolder) {
                mPresenter.responseRequest(getContactFromRecycler(viewHolder.itemView), false);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private ContactModel getContactFromRecycler(View itemView){
        int position = mRecyclerView.getChildAdapterPosition(itemView);
        return mAdapter.getContacts().get(position);
    }

    private void loadUITexts() {
        mEmptyText.setText(R.string.no_requests);
    }

    @Override
    public void showAllRequests(List<ContactModel> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            mEmptyContainer.setVisibility(View.VISIBLE);
            mAdapter.setContacts(contacts);
        } else {
            mEmptyContainer.setVisibility(View.GONE);
            mAdapter.setContacts(contacts);
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

}
