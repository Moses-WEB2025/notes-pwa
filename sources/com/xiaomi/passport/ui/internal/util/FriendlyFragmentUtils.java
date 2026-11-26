package com.xiaomi.passport.ui.internal.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/* JADX INFO: loaded from: classes4.dex */
public class FriendlyFragmentUtils {
    public static void addFragment(FragmentManager fragmentManager, int i, Fragment fragment) {
        addFragmentIfNonExist(fragmentManager, i, fragment);
    }

    public static Fragment addFragmentIfNonExist(FragmentManager fragmentManager, int i, Fragment fragment) {
        String name = fragment.getClass().getName();
        Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag(name);
        if (fragmentFindFragmentByTag != null) {
            return fragmentFindFragmentByTag;
        }
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        fragmentTransactionBeginTransaction.add(i, fragment, name);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        return fragment;
    }

    public static void popUpFragment(Fragment fragment) {
        Activity activity = fragment.getActivity();
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            activity.finish();
        }
    }
}
