package com.xiaomi.passport.ui.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import com.xiaomi.passport.ui.onetrack.Analytics;

/* JADX INFO: loaded from: classes4.dex */
public class BaseFragment extends Fragment {
    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Analytics.viewEvent(getName());
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        Analytics.resumeEvent(getName());
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        Analytics.pauseEvent(getName());
    }

    public String getName() {
        return getClass().getSimpleName();
    }
}
