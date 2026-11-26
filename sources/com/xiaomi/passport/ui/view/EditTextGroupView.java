package com.xiaomi.passport.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.ui.keyboard.PassportKeyboardSettings;
import com.xiaomi.passport.ui.settings.CaptchaView;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.utils.CountryCodePhoneNumber;
import com.xiaomi.passport.utils.MultiLangTextFormatter;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
public class EditTextGroupView extends FrameLayout {
    public static final int INPUT_TEXT_TYPE_ALL = 0;
    public static final int INPUT_TEXT_TYPE_CAPTCHA = 4;
    public static final int INPUT_TEXT_TYPE_PASSWORD = 3;
    public static final int INPUT_TEXT_TYPE_PHONE_NUMBER = 1;
    public static final int INPUT_TEXT_TYPE_USER_ID = 2;
    private static final String PARCEL_PARENT_STATE = "parent_state";
    private static final String SPARSE_PARCEL_ARRAY_CHILDREN_STATE = "children_state";
    private static final String TAG = "EditTextGroupView";
    private ImageView mBtnClearAll;
    private ImageView mCbPasswordVisible;
    private AutoCompleteTextView mEtInput;
    private int mInputTextType;
    private CaptchaView mIvCaptcha;
    private boolean mShowPassword;
    private TextView mTvCountryCode;

    public EditTextGroupView(Context context) {
        super(context);
        init(context, null);
    }

    public EditTextGroupView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public EditTextGroupView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(getContext()).inflate(R.layout.passport_layout_edit_text_group_view, this);
        setBackgroundResource(R.drawable.passport_edit_text_layout_bg_normal);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.input);
        this.mEtInput = autoCompleteTextView;
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.xiaomi.passport.ui.view.EditTextGroupView.1
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                EditTextGroupView.this.setBackgroundResource(z ? R.drawable.passport_edit_text_layout_bg_focused : R.drawable.passport_edit_text_layout_bg_normal);
            }
        });
        if (Build.VERSION.SDK_INT >= 35) {
            try {
                Method method = this.mEtInput.getClass().getMethod("setLocalePreferredLineHeightForMinimumUsed", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(this.mEtInput, false);
            } catch (Exception e) {
                AccountLogger.log(TAG, "init>>>setLocalePreferredLineHeightForMinimumUsed failed", e);
            }
        }
        ImageView imageView = (ImageView) findViewById(R.id.clear_all);
        this.mBtnClearAll = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.view.EditTextGroupView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditTextGroupView.this.mEtInput.setText("");
                EditTextGroupView.this.updateViews();
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.password_visible);
        this.mCbPasswordVisible = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.view.EditTextGroupView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditTextGroupView editTextGroupView = EditTextGroupView.this;
                editTextGroupView.mShowPassword = !editTextGroupView.mShowPassword;
                EditTextGroupView.this.mCbPasswordVisible.setImageResource(EditTextGroupView.this.mShowPassword ? R.drawable.passport_password_show : R.drawable.passport_password_not_show);
                EditTextGroupView.this.mCbPasswordVisible.setContentDescription(EditTextGroupView.this.mShowPassword ? EditTextGroupView.this.getContext().getString(R.string.passport_password_show) : EditTextGroupView.this.getContext().getString(R.string.passport_password_hide_show));
                EditTextGroupView.this.updateViews();
            }
        });
        this.mCbPasswordVisible.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.xiaomi.passport.ui.view.EditTextGroupView.4
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setClassName("android.widget.Switch");
                accessibilityNodeInfo.setCheckable(true);
                accessibilityNodeInfo.setChecked(EditTextGroupView.this.mShowPassword);
            }
        });
        this.mTvCountryCode = (TextView) findViewById(R.id.country_code);
        this.mIvCaptcha = (CaptchaView) findViewById(R.id.captcha_image);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Passport_EditTextGroupView);
        this.mEtInput.setHint(typedArrayObtainStyledAttributes.getString(R.styleable.Passport_EditTextGroupView_passport_inputHintText));
        this.mEtInput.addTextChangedListener(new TextWatcher() { // from class: com.xiaomi.passport.ui.view.EditTextGroupView.5
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                EditTextGroupView.this.updateViews();
            }
        });
        this.mInputTextType = typedArrayObtainStyledAttributes.getInt(R.styleable.Passport_EditTextGroupView_passport_inputTextType, 0);
        typedArrayObtainStyledAttributes.recycle();
        setupInputType();
        updateViews();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        PassportKeyboardSettings.AbsPassportKeyboard passwordKeyboard = PassportKeyboardSettings.getPasswordKeyboard();
        if (this.mInputTextType != 3 || passwordKeyboard == null) {
            return;
        }
        passwordKeyboard.attachToActivity((Activity) getContext());
        passwordKeyboard.target(this.mEtInput);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        PassportKeyboardSettings.AbsPassportKeyboard passwordKeyboard = PassportKeyboardSettings.getPasswordKeyboard();
        if (passwordKeyboard != null) {
            passwordKeyboard.dismiss();
            passwordKeyboard.detachFromActivity();
            passwordKeyboard.untarget();
        }
        super.onDetachedFromWindow();
    }

    private void setupInputType() {
        int i = this.mInputTextType;
        if (i == 1) {
            this.mEtInput.setInputType(2);
            this.mEtInput.setFilters(new InputFilter[]{new PhoneNumberInputFilter()});
            return;
        }
        if (i == 2) {
            this.mEtInput.setInputType(32);
            return;
        }
        if (i == 3) {
            this.mEtInput.setInputType(129);
            this.mEtInput.setFilters(new InputFilter[]{new PasswordInputFilter(this.mEtInput)});
        } else {
            if (i != 4) {
                return;
            }
            this.mEtInput.setInputType(1);
            this.mEtInput.setHint(this.mIvCaptcha.getHint());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateViews() {
        TransformationMethod passwordTransformationMethod;
        boolean zIsEnabled = isEnabled();
        this.mTvCountryCode.setEnabled(zIsEnabled);
        this.mEtInput.setEnabled(zIsEnabled);
        if (!zIsEnabled) {
            this.mBtnClearAll.setVisibility(8);
            this.mCbPasswordVisible.setVisibility(8);
            return;
        }
        this.mBtnClearAll.setVisibility(this.mEtInput.length() == 0 ? 8 : 0);
        this.mCbPasswordVisible.setVisibility(this.mInputTextType == 3 ? 0 : 8);
        TextView textView = this.mTvCountryCode;
        textView.setVisibility(textView.length() == 0 ? 8 : 0);
        this.mIvCaptcha.setVisibility(this.mInputTextType == 4 ? 0 : 8);
        if (this.mInputTextType == 3) {
            int selectionStart = this.mEtInput.getSelectionStart();
            if (this.mShowPassword) {
                passwordTransformationMethod = HideReturnsTransformationMethod.getInstance();
            } else {
                passwordTransformationMethod = PasswordTransformationMethod.getInstance();
            }
            this.mEtInput.setTransformationMethod(passwordTransformationMethod);
            this.mEtInput.setSelection(selectionStart);
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        updateViews();
    }

    public void setInputText(String str) {
        this.mEtInput.setText(str);
        this.mEtInput.setSelection(str.length());
        updateViews();
    }

    public String getInputText() {
        Editable text = this.mEtInput.getText();
        return text == null ? "" : text.toString();
    }

    public void setCountryCode(int i) {
        String strForceLTR;
        TextView textView = this.mTvCountryCode;
        if (i > 0) {
            strForceLTR = MultiLangTextFormatter.forceLTR(CountryCodePhoneNumber.getCountryCodeAsString(i));
        } else {
            strForceLTR = "";
        }
        textView.setText(strForceLTR);
        updateViews();
    }

    public void initCountryCode(int i, View.OnClickListener onClickListener) {
        setCountryCode(i);
        this.mTvCountryCode.setOnClickListener(onClickListener);
    }

    public int getCountryCode() {
        return CountryCodePhoneNumber.parseCountryCodeAsInt(String.valueOf(this.mTvCountryCode.getText()));
    }

    public void setupCaptcha(String str) {
        this.mIvCaptcha.setupCaptcha(str, null);
    }

    public String getCaptchaIck() {
        return this.mIvCaptcha.getIck();
    }

    private static class PhoneNumberInputFilter extends InputFilter.LengthFilter {
        private static final int MAX_PHONE_NUMBER_LENGTH = 15;

        public PhoneNumberInputFilter() {
            super(15);
        }

        @Override // android.text.InputFilter.LengthFilter, android.text.InputFilter
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            return super.filter(charSequence, i, i2, spanned, i3, i4);
        }
    }

    private static class PasswordInputFilter extends InputFilter.LengthFilter {
        private static final int MAX_PASSWORD_LENGTH = 16;
        private final EditText mTargetEditText;

        public PasswordInputFilter(EditText editText) {
            super(16);
            this.mTargetEditText = editText;
        }

        @Override // android.text.InputFilter.LengthFilter, android.text.InputFilter
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            CharSequence charSequenceFilter = super.filter(charSequence, i, i2, spanned, i3, i4);
            if ("".equals(charSequenceFilter) && this.mTargetEditText.length() == 16) {
                AccountToast.showToastMessage(this.mTargetEditText.getContext(), R.string.passport_error_length_limit);
            }
            return charSequenceFilter;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCEL_PARENT_STATE, super.onSaveInstanceState());
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray(SPARSE_PARCEL_ARRAY_CHILDREN_STATE, sparseArray);
        return bundle;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(SPARSE_PARCEL_ARRAY_CHILDREN_STATE);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).restoreHierarchyState(sparseParcelableArray);
            }
            super.onRestoreInstanceState(bundle.getParcelable(PARCEL_PARENT_STATE));
        }
    }

    public EditText getInputTextView() {
        return this.mEtInput;
    }
}
