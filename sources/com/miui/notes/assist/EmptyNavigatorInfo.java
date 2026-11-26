package com.miui.notes.assist;

import com.miui.notes.base.utils.Logger;
import miuix.navigator.Navigator;
import miuix.navigator.navigatorinfo.NavigatorInfo;

/* JADX INFO: loaded from: classes4.dex */
public class EmptyNavigatorInfo extends NavigatorInfo {
    public EmptyNavigatorInfo(int i) {
        super(i);
    }

    @Override // miuix.navigator.navigatorinfo.NavigatorInfo
    public boolean onNavigate(Navigator navigator) {
        Logger.INSTANCE.d("EmptyNavigatorInfo", "navigate id=" + getNavigationId());
        return true;
    }
}
