package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;

/* JADX INFO: loaded from: classes4.dex */
public class AccountPreferenceView extends LinearLayout {
    private ImageView mIvArrow;
    private ImageView mIvIcon;
    private TextView mTvSummary;
    private TextView mTvTitle;

    public AccountPreferenceView(Context context) {
        super(context);
        initLayout(context, null);
    }

    public AccountPreferenceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initLayout(context, attributeSet);
    }

    public AccountPreferenceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initLayout(context, attributeSet);
    }

    private void initLayout(Context context, AttributeSet attributeSet) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.passport_layout_account_preference_view, this);
        this.mTvTitle = (TextView) viewInflate.findViewById(R.id.title);
        this.mTvSummary = (TextView) viewInflate.findViewById(R.id.summary);
        this.mIvIcon = (ImageView) viewInflate.findViewById(R.id.icon);
        this.mIvArrow = (ImageView) viewInflate.findViewById(R.id.arrow);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Passport_AccountPreferenceView);
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.Passport_AccountPreferenceView_passport_title);
            if (!TextUtils.isEmpty(string)) {
                this.mTvTitle.setText(string);
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public void setValue(String str) {
        this.mTvSummary.setText(str);
    }

    public void setRightArrowVisible(boolean z) {
        this.mIvArrow.setVisibility(z ? 0 : 8);
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mIvIcon.setVisibility(bitmap != null ? 0 : 8);
        this.mIvIcon.setImageBitmap(bitmap);
    }

    public void setValue(int i) {
        setValue(getContext().getString(i));
    }

    public CharSequence getValue() {
        return this.mTvSummary.getText();
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mIvArrow.setVisibility(onClickListener == null ? 8 : 0);
    }
}
