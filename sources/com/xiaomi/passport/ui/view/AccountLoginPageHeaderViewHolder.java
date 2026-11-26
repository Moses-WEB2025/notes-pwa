package com.xiaomi.passport.ui.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;

/* JADX INFO: loaded from: classes4.dex */
public class AccountLoginPageHeaderViewHolder implements View.OnClickListener {
    private OnActionListener onActionListener;
    public final TextView tvCountryName;
    public final TextView tvHelp;
    public final View viewRoot;

    public interface OnActionListener {
        void onCountryNameClicked(View view);

        void onHelpClicked(View view);
    }

    public AccountLoginPageHeaderViewHolder(View view, TextView textView, TextView textView2) {
        this.viewRoot = view;
        this.tvCountryName = textView;
        textView.setOnClickListener(this);
        this.tvHelp = textView2;
        textView2.setOnClickListener(this);
    }

    public static AccountLoginPageHeaderViewHolder create(Context context) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.passport_layout_account_login_page_header_end_view, (ViewGroup) null);
        viewInflate.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        return new AccountLoginPageHeaderViewHolder(viewInflate, (TextView) viewInflate.findViewById(R.id.country_name), (TextView) viewInflate.findViewById(R.id.help));
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnActionListener onActionListener = this.onActionListener;
        if (onActionListener == null) {
            return;
        }
        if (view == this.tvHelp) {
            onActionListener.onHelpClicked(view);
        } else if (view == this.tvCountryName) {
            onActionListener.onCountryNameClicked(view);
        }
    }

    public void setCountryName(String str) {
        this.tvCountryName.setText(str);
        this.tvCountryName.setVisibility(TextUtils.isEmpty(str) ? 8 : 0);
    }

    public void setCountryAccessibilityClick(final Intent intent) {
        this.tvCountryName.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.xiaomi.passport.ui.view.AccountLoginPageHeaderViewHolder.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                if (intent == null) {
                    accessibilityNodeInfo.setClickable(false);
                    accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                } else {
                    accessibilityNodeInfo.setClickable(true);
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                }
            }
        });
    }
}
