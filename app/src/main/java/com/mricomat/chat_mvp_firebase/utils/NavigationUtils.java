/*
 * Created by Martín Rico Martínez on 15/04/18 13:05
 * Copyright (c) 2018. All rights reserved
 *
 * Last modified 15/04/18 12:42
 */

package com.mricomat.chat_mvp_firebase.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.mricomat.chat_mvp_firebase.R;

/**
 * Created by mricomat on 05/03/2018.
 */

public class NavigationUtils {

    public static void navigateToFragment(FragmentActivity activity, Fragment fragment,
                                          int contentFrame, boolean addToBackStack, boolean animation) {
        if (fragment == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager()
                .beginTransaction();

        if (Build.VERSION.SDK_INT > 12 && animation) {
            fragmentTransaction.setCustomAnimations(R.anim.animation_right_to_left,
                    R.anim.animation_center_to_left, R.anim.animation_left_to_right,
                    R.anim.animation_center_to_right);
        }

        String tag = ((Object) fragment).getClass().getName();

        fragmentTransaction.replace(contentFrame, fragment, tag);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }

        fragmentTransaction.commit();

        if (contentFrame < 0) {
            activity.getSupportFragmentManager().executePendingTransactions();
        }
    }

    public static void navigateToActivity(Activity activityLaunching, Class classToStartIntent,
                                          Bundle extras) {
        Intent intent = createIntent(activityLaunching.getApplicationContext(), classToStartIntent, extras);
        performNavigationToActivity(activityLaunching, intent, 0, false);
    }

    private static Intent createIntent(Context context, Class classToStartIntent, Bundle extras) {
        Intent intent = new Intent(context, classToStartIntent);
        if (extras != null) {
            intent.putExtras(extras);
        }
        return intent;
    }

    private static void performNavigationToActivity(Activity activityLaunching, Intent intent,
                                                    int requestCode, boolean removePreviousActivity) {
        if (activityLaunching != null) {
            if (requestCode > 0) {
                activityLaunching.startActivityForResult(intent, requestCode);
            } else {
                activityLaunching.startActivity(intent);
            }
        }
        if (removePreviousActivity) {
            activityLaunching.finish();
        }
    }
}
