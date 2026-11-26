package com.miui.handwriting.common.view;

import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes3.dex */
public class BaseFragment extends Fragment {

    public interface OnInnerActionBarListener {
        void onEndViewClick(int i, int i2, int i3);

        void onStartViewClick();
    }

    @Override // miuix.appcompat.app.Fragment
    protected boolean isResponsiveEnabled() {
        return true;
    }
}
