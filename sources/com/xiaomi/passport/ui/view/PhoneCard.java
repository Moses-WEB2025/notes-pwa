package com.xiaomi.passport.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.settings.utils.NetworkCircleImageSaver;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneCard extends FrameLayout implements View.OnClickListener {
    private ImageView mAvatarImage;
    private boolean mIsBigMode;
    private OnActionListener mOnActionListener;
    private PhoneAccount mPhoneAccount;
    private TextView mSubTitleText;
    private TextView mTitleText;

    public interface OnActionListener {
        void onCardClick(View view, PhoneAccount phoneAccount);
    }

    public PhoneCard(Context context) {
        super(context);
        initView(context, null);
    }

    public PhoneCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context, attributeSet);
    }

    public PhoneCard(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.Passport_PhoneAccountCard, 0, 0);
        this.mIsBigMode = typedArrayObtainStyledAttributes.getInt(R.styleable.Passport_PhoneAccountCard_passport_card_mode, 0) == 0;
        typedArrayObtainStyledAttributes.recycle();
        if (this.mIsBigMode) {
            LayoutInflater.from(context).inflate(R.layout.passport_layout_phone_card_big, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.passport_layout_phone_card_small, this);
        }
        this.mTitleText = (TextView) findViewById(R.id.card_title);
        this.mSubTitleText = (TextView) findViewById(R.id.card_sub_title);
        this.mAvatarImage = (ImageView) findViewById(R.id.card_title_avatar);
        setOnClickListener(this);
    }

    public void update(PhoneAccount phoneAccount) {
        this.mPhoneAccount = phoneAccount;
        if (phoneAccount.canShowUserAvatar()) {
            Bitmap userAvatarFileBitmap = getUserAvatarFileBitmap(getContext(), phoneAccount.registerUserInfo.avatarAddress);
            if (userAvatarFileBitmap == null) {
                this.mAvatarImage.setImageResource(R.drawable.passport_ic_user_avatar_default);
            } else {
                this.mAvatarImage.setImageBitmap(userAvatarFileBitmap);
            }
        } else {
            this.mAvatarImage.setImageResource(R.drawable.passport_auth_logo);
        }
        if (this.mIsBigMode) {
            this.mTitleText.setText(phoneAccount.registerUserInfo.phone);
            return;
        }
        if (phoneAccount.canShowUserName()) {
            String str = phoneAccount.registerUserInfo.userName;
            if (TextUtils.isEmpty(str)) {
                str = phoneAccount.registerUserInfo.maskedUserId;
            }
            this.mTitleText.setText(str);
        } else {
            this.mTitleText.setText(R.string.passport_user_name_phone_number);
        }
        this.mSubTitleText.setText(phoneAccount.registerUserInfo.phone);
    }

    private static Bitmap getUserAvatarFileBitmap(Context context, String str) {
        File defaultNetworkImageCacheFile = NetworkCircleImageSaver.getDefaultNetworkImageCacheFile(context, str);
        if (defaultNetworkImageCacheFile != null && defaultNetworkImageCacheFile.isFile() && defaultNetworkImageCacheFile.exists()) {
            return BitmapFactory.decodeFile(defaultNetworkImageCacheFile.getAbsolutePath());
        }
        return null;
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.mOnActionListener = onActionListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnActionListener onActionListener;
        if (this.mIsBigMode || (onActionListener = this.mOnActionListener) == null) {
            return;
        }
        onActionListener.onCardClick(view, this.mPhoneAccount);
    }
}
