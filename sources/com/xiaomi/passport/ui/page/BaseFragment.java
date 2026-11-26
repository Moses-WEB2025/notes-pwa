package com.xiaomi.passport.ui.page;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.stat.AccountStatInterface;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.passport.ui.BaseActivity;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.utils.LoginPreferenceConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseFragment extends Fragment {
    protected abstract StatParams getStatParams();

    public static class StatParams {
        public final String mFragmentRefName;
        public final String mStatViewTip;

        public StatParams(String str, String str2) {
            this.mFragmentRefName = str;
            this.mStatViewTip = str2;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        AccountStatInterface.getInstance().statEventWithTip("view", getStatParams().mStatViewTip, "ref", getPageRef());
    }

    public Bundle getFullArguments() {
        Bundle bundle = new Bundle();
        Bundle arguments = getArguments();
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            bundle.putAll(extras);
        }
        if (arguments != null) {
            bundle.putAll(arguments);
        }
        return bundle;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public void showToast(String str) {
        if (TextUtils.isEmpty(str)) {
            str = getString(R.string.passport_unknown_error);
        }
        AccountToast.showToastMessage(getActivity(), str);
    }

    protected boolean isActivityAlive() {
        return (getActivity() == null || getActivity().isFinishing() || getActivity().isDestroyed()) ? false : true;
    }

    private String getPageRef() {
        Bundle arguments = getArguments();
        if (arguments != null && !TextUtils.isEmpty(arguments.getString("ref"))) {
            return arguments.getString("ref");
        }
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return ((BaseActivity) activity).getPageRef();
        }
        return "Unknown";
    }

    protected void statClick(int i) {
        AccountStatInterface.getInstance().statEventWithTip(StatConstants.Event.CLICK, getString(i), "ref", getStatParams().mFragmentRefName);
    }

    protected void statClick(int i, Map<String, Object> map) {
        map.put("ref", getStatParams().mFragmentRefName);
        AccountStatInterface.getInstance().statEventWithTip(StatConstants.Event.CLICK, getString(i), map);
    }

    protected void setPwdStat(String str, String str2, Integer num) {
        if (num != null) {
            AccountStatInterface.getInstance().statEventWithTip(str, str2, StatConstants.Param.STRING_REF_TIP, LoginPreferenceConfig.TYPE_REGISTER, "state", "sdk", StatConstants.Param.INT_RESULT, num);
        } else {
            AccountStatInterface.getInstance().statEventWithTip(str, str2, StatConstants.Param.STRING_REF_TIP, LoginPreferenceConfig.TYPE_REGISTER, "state", "sdk");
        }
    }
}
