/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 12/04/18 0:29
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.ui.base.BaseFragment;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.view.FindContactsFragment;
import com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.view.RequestsContactsFragment;
import com.mricomat.chat_mvp_firebase.ui.contacts.ContactsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public class AddContactsFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public AddContactsFragment() {
    }

    public static AddContactsFragment newInstance() {
        return new AddContactsFragment();
    }

    @Override
    protected void initializeData() {
        mLayoutId = R.layout.add_contacts_fragment;
    }

    @Override
    protected void fillViews() {
        setupActionBar();
        setViewPagerAdaper();
    }

    private void setupActionBar() {
        ActionBar actionBar = ((ContactsActivity)getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.generic_contacts);
    }

    private void setViewPagerAdaper() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(FindContactsFragment.newInstance(),getString(R.string.find_contacts));
        adapter.addFragment(RequestsContactsFragment.newInstance(),getString(R.string.generic_requests));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Fragment> mFragments = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
