package com.xiaomi.passport.ui.uicontroller;

import com.xiaomi.passport.ui.data.PhoneAccount;

/* JADX INFO: loaded from: classes4.dex */
public interface AccountLoginUiUpdater {
    void updateFooterVisibility(boolean z, boolean z2);

    void updateHeaderVisibility(boolean z, boolean z2);

    void updateUserAgreement(PhoneAccount[] phoneAccountArr);
}
