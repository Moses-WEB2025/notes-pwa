package com.xiaomi.passport.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.hardware.DataSpace;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.utils.UIUtils;
import com.xiaomi.passport.ui.internal.PassportJsbWebViewActivity;
import com.xiaomi.passport.ui.internal.util.LinkSpanHelper;

/* JADX INFO: loaded from: classes4.dex */
public class PassportDialog extends Dialog {
    private LinearLayout mBtnGroup;
    private View mBtnLeftSpace;
    private Button mBtnNegative;
    private Button mBtnNeutral;
    private Button mBtnPositive;
    private View mBtnRightSpace;
    private View mContent;
    private FrameLayout mFlContent;
    private Pair<Pair<String[], Integer>, DialogInterface.OnClickListener> mListConfig;
    private CharSequence mMessage;
    private Pair<String, View.OnClickListener> mNegativeButtonConfig;
    private Pair<String, View.OnClickListener> mNeutralButtonConfig;
    private Pair<String, View.OnClickListener> mPositiveButtonConfig;
    private boolean mShowLoadingProgress;
    private String mTitle;
    private TextView mTvMessage;
    private TextView mTvTitle;

    public PassportDialog(Context context) {
        super(context, R.style.PassportTheme_Dialog);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        int i;
        int i2;
        super.onCreate(bundle);
        setContentView(R.layout.passport_layout_dialog);
        this.mTvTitle = (TextView) findViewById(R.id.title);
        if (TextUtils.isEmpty(this.mTitle)) {
            this.mTvTitle.setVisibility(8);
        } else {
            this.mTvTitle.setVisibility(0);
            this.mTvTitle.setText(this.mTitle);
        }
        this.mTvMessage = (TextView) findViewById(R.id.message);
        if (TextUtils.isEmpty(this.mMessage)) {
            this.mTvMessage.setVisibility(8);
        } else {
            this.mTvMessage.setVisibility(0);
            this.mTvMessage.setMovementMethod(LinkMovementMethod.getInstance());
            this.mTvMessage.setText(this.mMessage);
        }
        this.mBtnNegative = (Button) findViewById(R.id.negative);
        Pair<String, View.OnClickListener> pair = this.mNegativeButtonConfig;
        if (pair == null || TextUtils.isEmpty((CharSequence) pair.first)) {
            this.mBtnNegative.setVisibility(8);
        } else {
            this.mBtnNegative.setVisibility(0);
            this.mBtnNegative.setText((CharSequence) this.mNegativeButtonConfig.first);
            this.mBtnNegative.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.view.PassportDialog.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PassportDialog.this.mNegativeButtonConfig.second != null) {
                        ((View.OnClickListener) PassportDialog.this.mNegativeButtonConfig.second).onClick(view);
                    }
                    PassportDialog.this.dismiss();
                }
            });
        }
        this.mBtnNeutral = (Button) findViewById(R.id.neutral);
        Pair<String, View.OnClickListener> pair2 = this.mNeutralButtonConfig;
        if (pair2 == null || TextUtils.isEmpty((CharSequence) pair2.first)) {
            this.mBtnNeutral.setVisibility(8);
        } else {
            this.mBtnNeutral.setVisibility(0);
            this.mBtnNeutral.setText((CharSequence) this.mNeutralButtonConfig.first);
            this.mBtnNeutral.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.view.PassportDialog.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PassportDialog.this.mNeutralButtonConfig.second != null) {
                        ((View.OnClickListener) PassportDialog.this.mNeutralButtonConfig.second).onClick(view);
                    }
                    PassportDialog.this.dismiss();
                }
            });
        }
        this.mBtnPositive = (Button) findViewById(R.id.positive);
        Pair<String, View.OnClickListener> pair3 = this.mPositiveButtonConfig;
        if (pair3 == null || TextUtils.isEmpty((CharSequence) pair3.first)) {
            this.mBtnPositive.setVisibility(8);
        } else {
            this.mBtnPositive.setVisibility(0);
            this.mBtnPositive.setText((CharSequence) this.mPositiveButtonConfig.first);
            this.mBtnPositive.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.view.PassportDialog.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PassportDialog.this.mPositiveButtonConfig.second != null) {
                        ((View.OnClickListener) PassportDialog.this.mPositiveButtonConfig.second).onClick(view);
                    }
                    PassportDialog.this.dismiss();
                }
            });
        }
        this.mBtnGroup = (LinearLayout) findViewById(R.id.button_group);
        this.mBtnLeftSpace = findViewById(R.id.space_left);
        this.mBtnRightSpace = findViewById(R.id.space_right);
        int i3 = (this.mBtnNegative.getVisibility() == 0 && this.mBtnNeutral.getVisibility() == 0 && this.mBtnPositive.getVisibility() == 0) ? 1 : 0;
        if (i3 == 0) {
            this.mBtnGroup.setOrientation(i3);
            if (this.mBtnNegative.getVisibility() == 0 || this.mBtnNeutral.getVisibility() == 0 || this.mBtnPositive.getVisibility() == 0) {
                LinearLayout linearLayout = this.mBtnGroup;
                linearLayout.setPadding(linearLayout.getPaddingLeft(), (int) UIUtils.dp2px(32.0f, getContext()), this.mBtnGroup.getPaddingRight(), this.mBtnGroup.getPaddingBottom());
            }
            if (this.mBtnNegative.getVisibility() == 0 && this.mBtnNeutral.getVisibility() == 8 && this.mBtnPositive.getVisibility() == 0) {
                i = 0;
                i2 = 0;
            } else {
                i = (this.mBtnNegative.getVisibility() == 8 || this.mBtnNeutral.getVisibility() == 8) ? 8 : 0;
                i2 = (this.mBtnNeutral.getVisibility() == 8 || this.mBtnPositive.getVisibility() == 8) ? 8 : 0;
            }
            this.mBtnLeftSpace.setVisibility(i);
            this.mBtnRightSpace.setVisibility(i2);
        }
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content);
        this.mFlContent = frameLayout;
        View view = this.mContent;
        if (view != null) {
            frameLayout.addView(view);
            this.mTvMessage.setVisibility(8);
        } else {
            this.mTvMessage.setVisibility(0);
        }
        findViewById(R.id.progress).setVisibility(this.mShowLoadingProgress ? 0 : 8);
    }

    public PassportDialog setPositiveButton(String str, View.OnClickListener onClickListener) {
        this.mPositiveButtonConfig = new Pair<>(str, onClickListener);
        return this;
    }

    public PassportDialog setNegativeButton(String str, View.OnClickListener onClickListener) {
        this.mNegativeButtonConfig = new Pair<>(str, onClickListener);
        return this;
    }

    public PassportDialog setNeutralButton(String str, View.OnClickListener onClickListener) {
        this.mNeutralButtonConfig = new Pair<>(str, onClickListener);
        return this;
    }

    public PassportDialog setTitle(String str) {
        this.mTitle = str;
        return this;
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        setTitle(charSequence.toString());
    }

    @Override // android.app.Dialog
    public void setTitle(int i) {
        setTitle(getContext().getString(i));
    }

    public PassportDialog setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        return this;
    }

    public PassportDialog setMessage(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mMessage = LinkSpanHelper.adaptLinkStyleAndAction(getContext(), str, null, false, new LinkClickListener(getContext().getApplicationContext()));
        }
        return this;
    }

    public PassportDialog setView(View view) {
        this.mContent = view;
        return this;
    }

    public PassportDialog setList(String[] strArr, int i, final DialogInterface.OnClickListener onClickListener) {
        this.mListConfig = new Pair<>(new Pair(strArr, Integer.valueOf(i)), onClickListener);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        final int i2 = 0;
        while (i2 < strArr.length) {
            View.inflate(getContext(), i2 == i ? R.layout.passport_layout_dialog_list_item_selected : R.layout.passport_layout_dialog_list_item_unselected, linearLayout);
            View childAt = linearLayout.getChildAt(i2);
            childAt.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.view.PassportDialog.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    onClickListener.onClick(PassportDialog.this, i2);
                    PassportDialog.this.dismiss();
                }
            });
            ((TextView) childAt.findViewById(R.id.text)).setText(strArr[i2]);
            i2++;
        }
        setView(linearLayout);
        return this;
    }

    public PassportDialog setLoadingProgressVisible(boolean z) {
        this.mShowLoadingProgress = z;
        return this;
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        getWindow().setLayout(-1, -2);
    }

    public void show(int i) {
        this.mMessage = getContext().getString(i);
        show();
    }

    public static PassportDialog create(Context context, PassThroughErrorInfo passThroughErrorInfo) {
        if (TextUtils.isEmpty(passThroughErrorInfo.getTips())) {
            return null;
        }
        return new PassportDialog(context).setMessage(passThroughErrorInfo.getTips()).setTitle(passThroughErrorInfo.getTitle()).setPositiveButton(context.getString(android.R.string.ok), null);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    private static class LinkClickListener implements LinkSpanHelper.OnSpanLinkClickListener {
        public final Context appContext;

        public LinkClickListener(Context context) {
            this.appContext = context;
        }

        @Override // com.xiaomi.passport.ui.internal.util.LinkSpanHelper.OnSpanLinkClickListener
        public void onLinkClicked(View view, String str) {
            Intent intentMakeIntent = PassportJsbWebViewActivity.makeIntent(this.appContext, str);
            intentMakeIntent.addFlags(DataSpace.RANGE_LIMITED);
            this.appContext.startActivity(intentMakeIntent);
        }
    }
}
