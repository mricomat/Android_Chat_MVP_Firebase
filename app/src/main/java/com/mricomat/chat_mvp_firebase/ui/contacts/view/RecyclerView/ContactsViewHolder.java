/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 10/04/18 23:05
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.view.RecyclerView;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.ContactListModel;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Martín Rico Martínez on 25/03/2018.
 */

public class ContactsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;

    @BindView(R.id.name_text)
    TextView mNameText;

    @BindView(R.id.time_text)
    TextView mTimeText;

    @BindView(R.id.message_text)
    TextView mMessageText;


    public ContactsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void update(ContactListModel contact) {
        mNameText.setText(contact.getContact().getName());
        if (!TextUtils.isEmpty(contact.getLastTimeMessage())) {
            mTimeText.setText(getDateCurrentTimeZone(Long.valueOf(contact.getLastTimeMessage())));
        }
        mMessageText.setText(contact.getLastMessage());
        if (!TextUtils.isEmpty(contact.getContact().getProfilePhoto())) {
            Uri imageUri = Uri.parse(contact.getContact().getProfilePhoto());
            Picasso.get().load(imageUri).into(mProfileImage);
        }
    }

    private String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception ignored) {
        }
        return "";
    }
}
