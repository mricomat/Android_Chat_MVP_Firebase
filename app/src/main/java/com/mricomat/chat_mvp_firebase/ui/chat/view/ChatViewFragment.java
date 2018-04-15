/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 5/04/18 19:31
 */

package com.mricomat.chat_mvp_firebase.ui.chat.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.MessageModel;
import com.mricomat.chat_mvp_firebase.ui.base.BaseFragment;
import com.mricomat.chat_mvp_firebase.ui.chat.ChatActivity;
import com.mricomat.chat_mvp_firebase.ui.chat.presenter.ChatPresenter;
import com.mricomat.chat_mvp_firebase.ui.chat.presenter.ChatPresenterImpl;
import com.mricomat.chat_mvp_firebase.ui.chat.view.ChatRecyclerView.ChatViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Martín Rico Martínez on 30/03/2018.
 */

public class ChatViewFragment extends BaseFragment implements ChatView {

    private static final String EMPTY_STRING = "";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.chat_input_text)
    EditText mInputText;

    @BindView(R.id.enter_button)
    ImageButton mEnterButton;

    private String mChatRoomId;
    private ChatViewAdapter mAdapter;
    private ChatPresenter mPresenter;
    private boolean mFirstStart = true;
    private boolean mIsLastPosition = false;
    private boolean mIsAllLoaded = false;

    public ChatViewFragment() {
    }

    public static ChatViewFragment newInstance() {
        return new ChatViewFragment();
    }

    @Override
    protected void initializeData() {
        mLayoutId = R.layout.chat_fragment;
        getBundleData();
        mPresenter = new ChatPresenterImpl(this);
    }

    @Override
    protected void fillViews() {
        initSwipeRefreshLayout();
        initRecyclerView();
        setListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        assert mPresenter != null;
        // TODO deberia havber una forma de recoger información del AddFragment al guardar una tarea y asi recargar si es necesario
        // TODO Refactor
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

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null && !TextUtils.isEmpty(bundle.getString(ChatActivity.ID_CONTACT_KEY))) {
            mChatRoomId = bundle.getString(ChatActivity.ID_CONTACT_KEY);
        }
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hideLoadingIndicator();
            }
        });
        // TODO VER COMO voy a realizar el refresh
    }

    private void initRecyclerView() {
        mAdapter = new ChatViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setListeners() {
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendMessage(mInputText.getText().toString());
                mInputText.setText(EMPTY_STRING);
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom && mIsLastPosition) {
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                        }
                    }, 0);
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mIsLastPosition = !recyclerView.canScrollVertically(1);
            }
        });

        mPresenter.getIncomingMessages();
    }

    @Override
    public void showAllMessages(ArrayList<MessageModel> messages) {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mAdapter.setMessages(messages);
        if(messages != null && !messages.isEmpty()) {
            mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
        }
        hideLoadingIndicator();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                mIsAllLoaded = true;
            }
        }, 10);
    }

    @Override
    public void showMessage(MessageModel message) {
        if (mIsAllLoaded) {
            mAdapter.setMessage(message);
        }
    }

    @Override
    public String getRoomId() {
        return mChatRoomId;
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