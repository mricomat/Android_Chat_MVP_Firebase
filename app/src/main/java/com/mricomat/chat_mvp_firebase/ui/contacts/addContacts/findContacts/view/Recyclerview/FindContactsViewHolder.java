/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 30/03/18 13:58
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.findContacts.view.Recyclerview;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mricomat.chat_mvp_firebase.R;
import com.mricomat.chat_mvp_firebase.network.model.ContactModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Martín Rico Martínez on 26/03/2018.
 */

public class FindContactsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;

    @BindView(R.id.name_text_item)
    TextView mNameText;

    @BindView(R.id.send_request_button)
    Button mSendRequestButton;

    public FindContactsViewHolder(View itemView, final FindContactsHolderListener buttonListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mSendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonListener.onSendRequestButtonClicked(FindContactsViewHolder.this);
            }
        });
    }

    public void update(ContactModel contact) {
        if (!TextUtils.isEmpty(contact.getProfilePhoto())) {
            Uri imageUri = Uri.parse(contact.getProfilePhoto());
            Picasso.get().load(imageUri).into(mProfileImage);
        }
        mNameText.setText(contact.getName());
    }

    public void changeStyle() { // TODo
        mSendRequestButton.setClickable(false);
        mSendRequestButton.setVisibility(View.GONE);
        //mSendRequestButton.setText("Requested");
        ///mSendRequestButton.setBackgroundTintList();
    }

    public interface FindContactsHolderListener {

        void onSendRequestButtonClicked(FindContactsViewHolder viewHolder);

    }
}
