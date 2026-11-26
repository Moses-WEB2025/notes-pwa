package com.xiaomi.passport.ui.page;

import android.os.Bundle;
import android.view.View;

/* JADX INFO: loaded from: classes4.dex */
public interface LoginUIAction {
    void gotoFragment(BaseLoginFragment.LoginFragmentType loginFragmentType, Bundle bundle, boolean z, boolean z2);

    boolean isUserAgreementSelected();

    void showAgreementConfirmDialog(View.OnClickListener onClickListener);

    void showSNSLoginFragment(boolean z);
}
