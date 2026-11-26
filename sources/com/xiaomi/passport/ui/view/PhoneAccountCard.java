package com.xiaomi.passport.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.settings.utils.NetworkCircleImageSaver;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneAccountCard extends FrameLayout {
    private Button btnLoginRegister;
    private boolean customUserNameVisible;
    private ImageView ivUserAvatar;
    private OnActionListener onActionListener;
    private PhoneAccount phoneAccount;
    private TextView tvPhoneNumber;
    private TextView tvUserName;
    private boolean useBigMode;
    private int userAvatarPlaceholderResId;

    public interface OnActionListener {
        void onLoginClicked(View view, PhoneAccount phoneAccount);

        void onRegisterClicked(View view, PhoneAccount phoneAccount);
    }

    public PhoneAccountCard(Context context) {
        super(context);
        this.userAvatarPlaceholderResId = R.drawable.passport_auth_logo;
        this.customUserNameVisible = true;
        initView(context, null);
    }

    public PhoneAccountCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.userAvatarPlaceholderResId = R.drawable.passport_auth_logo;
        this.customUserNameVisible = true;
        initView(context, attributeSet);
    }

    public PhoneAccountCard(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.userAvatarPlaceholderResId = R.drawable.passport_auth_logo;
        this.customUserNameVisible = true;
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.Passport_PhoneAccountCard, 0, 0);
        this.useBigMode = typedArrayObtainStyledAttributes.getInt(R.styleable.Passport_PhoneAccountCard_passport_card_mode, 0) == 0;
        typedArrayObtainStyledAttributes.recycle();
        if (this.useBigMode) {
            LayoutInflater.from(context).inflate(R.layout.passport_layout_phone_account_card_big, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.passport_layout_phone_account_card_small, this);
        }
        this.tvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        this.tvUserName = (TextView) findViewById(R.id.tv_user_name);
        this.ivUserAvatar = (ImageView) findViewById(R.id.iv_user_avatar);
        Button button = (Button) findViewById(R.id.btn_login_register);
        this.btnLoginRegister = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.view.PhoneAccountCard.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PhoneAccountCard.this.onActionListener == null) {
                    return;
                }
                if (PhoneAccountCard.this.phoneAccount.canLogin()) {
                    PhoneAccountCard.this.onActionListener.onLoginClicked(view, PhoneAccountCard.this.phoneAccount);
                } else {
                    PhoneAccountCard.this.onActionListener.onRegisterClicked(view, PhoneAccountCard.this.phoneAccount);
                }
            }
        });
    }

    public void update(PhoneAccount phoneAccount, String str) {
        String string;
        this.phoneAccount = phoneAccount;
        if (phoneAccount.canShowUserAvatar()) {
            Bitmap userAvatarFileBitmap = getUserAvatarFileBitmap(getContext(), phoneAccount.registerUserInfo.avatarAddress);
            if (userAvatarFileBitmap == null) {
                this.ivUserAvatar.setImageResource(R.drawable.passport_ic_user_avatar_default);
            } else {
                this.ivUserAvatar.setImageBitmap(userAvatarFileBitmap);
            }
        } else {
            this.ivUserAvatar.setImageResource(this.userAvatarPlaceholderResId);
        }
        if (!this.customUserNameVisible) {
            this.tvUserName.setVisibility(8);
        } else if (phoneAccount.canShowUserName()) {
            this.tvUserName.setVisibility(0);
            String str2 = phoneAccount.registerUserInfo.userName;
            if (TextUtils.isEmpty(str2)) {
                str2 = phoneAccount.registerUserInfo.maskedUserId;
            }
            this.tvUserName.setText(str2);
        } else if (this.useBigMode) {
            this.tvUserName.setVisibility(0);
            this.tvUserName.setText(R.string.passport_phone_auto_login_register_with_local_number);
        } else {
            this.tvUserName.setVisibility(0);
            this.tvUserName.setText(R.string.passport_user_name_phone_number);
        }
        this.tvPhoneNumber.setText(phoneAccount.registerUserInfo.phone);
        if (phoneAccount.canRegister()) {
            string = getContext().getString(this.useBigMode ? R.string.passport_register_by_local_phone_long_text : R.string.passport_register_by_local_phone_short_text);
        } else {
            string = getContext().getString(this.useBigMode ? R.string.passport_login_by_local_phone_long_text : R.string.passport_login_by_local_phone_short_text);
        }
        this.btnLoginRegister.setText(string);
    }

    private static Bitmap getUserAvatarFileBitmap(Context context, String str) {
        File defaultNetworkImageCacheFile = NetworkCircleImageSaver.getDefaultNetworkImageCacheFile(context, str);
        if (defaultNetworkImageCacheFile != null && defaultNetworkImageCacheFile.isFile() && defaultNetworkImageCacheFile.exists()) {
            return BitmapFactory.decodeFile(defaultNetworkImageCacheFile.getAbsolutePath());
        }
        return null;
    }

    public void setUserAvatarPlaceholder(int i) {
        this.userAvatarPlaceholderResId = i;
    }

    public void setCustomUserNameVisible(boolean z) {
        this.customUserNameVisible = z;
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }
}
