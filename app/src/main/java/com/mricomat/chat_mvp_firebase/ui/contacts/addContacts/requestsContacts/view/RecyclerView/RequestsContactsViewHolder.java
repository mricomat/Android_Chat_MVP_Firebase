/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 30/03/18 13:58
 */

package com.mricomat.chat_mvp_firebase.ui.contacts.addContacts.requestsContacts.view.RecyclerView;

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
 * Created by Martín Rico Martínez on 27/03/2018.
 */

public class RequestsContactsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;

    @BindView(R.id.name_text_item)
    TextView mNameText;

    @BindView(R.id.accept_button)
    Button mAcceptButton;

    @BindView(R.id.decline_button)
    Button mDeclineButton;


    public RequestsContactsViewHolder(View itemView, final RequestsContactsViewHolderListener holderListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holderListener.onRequestAccepted(RequestsContactsViewHolder.this);
            }
        });

        mDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holderListener.onRequestDeclined(RequestsContactsViewHolder.this);
            }
        });
    }

    public void update(ContactModel contactModel) {
        if (!TextUtils.isEmpty(contactModel.getProfilePhoto())) {
            Uri imageUri = Uri.parse(contactModel.getProfilePhoto());
            Picasso.get().load(imageUri).into(mProfileImage);
        }
        mNameText.setText(contactModel.getName());
    }

}
