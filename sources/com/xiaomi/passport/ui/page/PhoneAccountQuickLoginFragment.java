package com.xiaomi.passport.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.ui.internal.LayoutWrapperActivity;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneAccountQuickLoginFragment extends PhoneAccountLoginFragment {
    private LayoutWrapperActivity mAttachedActivity;
    private View mInitHeaderEndView;
    private View mInitHeaderStartView;

    @Override // com.xiaomi.passport.ui.page.PhoneAccountLoginFragment
    protected boolean canShowSNS() {
        return false;
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LayoutWrapperActivity) {
            this.mAttachedActivity = (LayoutWrapperActivity) context;
            return;
        }
        throw new IllegalStateException("only LayoutWrapperActivity can use this fragment");
    }

    @Override // com.xiaomi.passport.ui.page.PhoneAccountLoginFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.passport_fragment_phone_account_quick_login, viewGroup, false);
        initViews(viewInflate);
        this.mAttachedActivity.getWindow().setBackgroundDrawableResource(R.color.passport_black_20_percent);
        this.mInitHeaderStartView = this.mAttachedActivity.getHeaderStartView();
        this.mAttachedActivity.setHeaderStartView(null);
        this.mInitHeaderEndView = this.mAttachedActivity.getHeaderEndView();
        View viewInflate2 = View.inflate(getContext(), R.layout.passport_layout_phone_accuount_quick_login_dialog_end_view, null);
        viewInflate2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.PhoneAccountQuickLoginFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PhoneAccountQuickLoginFragment.this.getActivity().finish();
            }
        });
        viewInflate2.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        this.mAttachedActivity.setHeaderEndView(viewInflate2);
        this.mAttachedActivity.setPageBackupGround(R.drawable.halfdialog_shape);
        this.mAttachedActivity.setPageHeight((int) getResources().getDimension(R.dimen.passport_phone_account_quick_login_dialog_height), 80);
        return viewInflate;
    }

    @Override // com.xiaomi.passport.ui.page.PhoneAccountLoginFragment, com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.mAttachedActivity.getWindow().setBackgroundDrawableResource(R.color.passport_activity_bg_color);
        this.mAttachedActivity.setHeaderStartView(this.mInitHeaderStartView);
        this.mAttachedActivity.setHeaderEndView(this.mInitHeaderEndView);
        this.mAttachedActivity.setPageBackupGround(R.color.passport_activity_bg_color);
        this.mAttachedActivity.setPageHeight(-1, 48);
        super.onDestroyView();
    }
}
