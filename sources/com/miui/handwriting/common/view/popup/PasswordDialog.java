package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.view.View;
import com.miui.creation.common.R;

/* JADX INFO: loaded from: classes3.dex */
public class PasswordDialog extends EditTextDialog {
    private final Context mContext;
    private View.OnClickListener mNegativeBtnListener;
    private int mPasswordErrCount;
    private View.OnClickListener mPositiveBtnListener;

    public PasswordDialog(Context context, int i) {
        super(context, true, true);
        this.mNegativeBtnListener = null;
        this.mPositiveBtnListener = null;
        this.mContext = context;
        this.mPasswordErrCount = i;
    }

    @Override // com.miui.handwriting.common.view.popup.EditTextDialog
    protected void onInitialized() {
        this.mDescriptionTextView.setText(this.mContext.getString(R.string.creation_password_dialog_description));
        this.mDescriptionTextView.setTextSize(this.mContext.getResources().getDimensionPixelSize(R.dimen.creation_password_description_size));
        this.mResultTextView.setText(this.mContext.getString(R.string.creation_password_dialog_result));
        this.mResultTextView.setTextColor(this.mContext.getResources().getColor(R.color.creation_password_result_err_text_color));
        this.mResultTextView.setTextSize(this.mContext.getResources().getDimensionPixelSize(R.dimen.creation_password_result_size));
        if (this.mPasswordErrCount > 1) {
            this.mResultTextView.setVisibility(0);
        } else {
            this.mResultTextView.setVisibility(4);
        }
        SpannableString spannableString = new SpannableString(this.mContext.getString(R.string.creation_password_dialog_title));
        spannableString.setSpan(new ScaleXSpan(0.8f), 0, spannableString.length(), 18);
        this.mEditor.setHint(spannableString);
        this.mEditor.setInputType(129);
        this.mEditor.setSelection(this.mEditor.getText().length());
        setTitle(this.mContext.getString(R.string.creation_password_dialog_title));
    }

    @Override // com.miui.handwriting.common.view.popup.EditTextDialog
    public void onPositiveButtonClick() {
        View.OnClickListener onClickListener = this.mPositiveBtnListener;
        if (onClickListener != null) {
            onClickListener.onClick(this.mPositiveButton);
        }
    }

    @Override // com.miui.handwriting.common.view.popup.EditTextDialog
    public void onNegativeButtonClick() {
        View.OnClickListener onClickListener = this.mNegativeBtnListener;
        if (onClickListener != null) {
            onClickListener.onClick(this.mNegativeButton);
        }
    }

    public void setPositiveListener(View.OnClickListener onClickListener) {
        this.mPositiveBtnListener = onClickListener;
    }

    public void setNegativeListener(View.OnClickListener onClickListener) {
        this.mNegativeBtnListener = onClickListener;
    }

    public String getPassword() {
        return String.valueOf(this.mEditor.getText());
    }

    public String getNewTitle() {
        if (this.mEditor != null) {
            return this.mEditor.getText().toString().trim();
        }
        return null;
    }
}
