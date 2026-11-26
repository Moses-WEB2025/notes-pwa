package com.miui.notes.ui.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.miui.common.tool.BlurHelper;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.ai.utils.FreeModeUtils;
import com.miui.richeditor.theme.Theme;
import miuix.animation.Folme;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class AnnotationEditController {
    private TextView mDone;
    private ClearableEditText mEditText;
    private View mPlaceHolder;
    private View mRootView;
    private String mAnnotation = "";
    private EditListener mEditListener = null;
    private TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.notes.ui.widget.AnnotationEditController.1
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            AnnotationEditController.this.mEditText.requestFocus();
        }
    };
    private TransitionListener mHideListener = new TransitionListener() { // from class: com.miui.notes.ui.widget.AnnotationEditController.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            AnnotationEditController.this.mRootView.setVisibility(8);
            AnnotationEditController.this.mPlaceHolder.setVisibility(8);
        }
    };
    private boolean mIsShow = false;
    private TextWatcher mTextWatcher = new TextWatcher() { // from class: com.miui.notes.ui.widget.AnnotationEditController.3
        final int maxLength = 1000;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 1000) {
                if (AnnotationEditController.this.mEditListener != null) {
                    AnnotationEditController.this.mEditListener.onOverLength();
                }
                AnnotationEditController.this.mEditText.removeTextChangedListener(this);
                AnnotationEditController.this.mEditText.setText(editable.subSequence(0, 1000));
                AnnotationEditController.this.mEditText.setSelection(AnnotationEditController.this.mEditText.getText().toString().length());
                AnnotationEditController.this.mEditText.addTextChangedListener(AnnotationEditController.this.mTextWatcher);
            }
        }
    };

    public interface EditListener {
        void onCompete(String str);

        void onOverLength();
    }

    public AnnotationEditController(View view, View view2) {
        this.mEditText = null;
        this.mDone = null;
        this.mRootView = view;
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ui.widget.AnnotationEditController.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view3) {
                if (AnnotationEditController.this.isShowing()) {
                    if (AnnotationEditController.this.mEditListener != null) {
                        AnnotationEditController.this.mEditListener.onCompete(AnnotationEditController.this.getAnnotation());
                    }
                    AnnotationEditController.this.hide();
                } else {
                    AnnotationEditController.this.mRootView.setVisibility(8);
                    AnnotationEditController.this.mPlaceHolder.setVisibility(8);
                }
            }
        });
        this.mPlaceHolder = view2;
        ClearableEditText clearableEditText = (ClearableEditText) this.mRootView.findViewById(R.id.edit_content);
        this.mEditText = clearableEditText;
        clearableEditText.addTextChangedListener(this.mTextWatcher);
        int color = this.mRootView.getResources().getColor(R.color.v8_text_appearance_list_section_text_color, null);
        CompatUtils.invokeCursorColor(this.mEditText, color, color);
        TextView textView = (TextView) this.mRootView.findViewById(R.id.done);
        this.mDone = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ui.widget.AnnotationEditController.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view3) {
                if (AnnotationEditController.this.mEditListener != null) {
                    AnnotationEditController.this.mEditListener.onCompete(AnnotationEditController.this.getAnnotation());
                }
            }
        });
        smallWindowAdapter();
    }

    public void updatePanelMarginBottom(Activity activity, int i) {
        if (activity == null) {
            return;
        }
        View viewFindViewById = this.mRootView.findViewById(R.id.annotate_group);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewFindViewById.getLayoutParams();
        marginLayoutParams.bottomMargin = i;
        viewFindViewById.setLayoutParams(marginLayoutParams);
    }

    public void setAnnotation(String str) {
        if (str == null) {
            this.mAnnotation = "";
        } else {
            this.mAnnotation = str;
        }
    }

    public void updateStyle(Theme theme) {
        Theme.EditorStyle editorStyle = theme.getEditorStyle(this.mRootView.getContext());
        BlurHelper.setBlurBackground(this.mRootView.findViewById(R.id.annotate_group), null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor2);
        int color = this.mRootView.getContext().getColor(editorStyle.mTextColor);
        this.mEditText.setTextColor(color);
        this.mEditText.setHintTextColor((16777215 & color) | 1711276032);
        Drawable drawable = this.mRootView.getContext().getDrawable(R.drawable.edit_layout_bg);
        drawable.setTint(this.mRootView.getContext().getColor(editorStyle.mEditInputBgColor));
        this.mEditText.setBackground(drawable);
        Drawable drawable2 = this.mRootView.getContext().getDrawable(R.drawable.edit_text_clear_btn_light);
        if (theme.getStatusBarStyle(this.mRootView.getContext()) == Theme.STATUS_BAR_STYLE_DARK) {
            drawable2 = this.mRootView.getContext().getDrawable(R.drawable.edit_text_clear_btn_dark);
        } else if (theme.getId() == 0) {
            drawable2 = this.mRootView.getContext().getDrawable(R.drawable.edit_text_clear_btn);
        }
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        this.mEditText.setCompoundDrawablesRelative(null, null, drawable2, null);
        this.mDone.setEnabled(true);
        if (theme.getId() == 0 || theme.getId() == 8) {
            this.mDone.setTextColor(this.mRootView.getContext().getColor(R.color.todo_edit_done_a_color));
        } else {
            this.mDone.setTextColor(color);
        }
    }

    public void show() {
        ClearableEditText clearableEditText;
        if (this.mRootView == null || (clearableEditText = this.mEditText) == null) {
            return;
        }
        clearableEditText.setText(this.mAnnotation);
        ClearableEditText clearableEditText2 = this.mEditText;
        clearableEditText2.setSelection(clearableEditText2.getText().toString().length());
        this.mIsShow = true;
        this.mRootView.setVisibility(0);
        this.mRootView.setAlpha(0.0f);
        this.mPlaceHolder.setVisibility(4);
        Folme.useAt(this.mRootView).state().cancel();
        Folme.useAt(this.mRootView).state().removeListener(this.mHideListener).removeListener(this.mShowListener).addListener(this.mShowListener).to(ViewProperty.ALPHA, Float.valueOf(1.0f));
        Utils.showSoftInputWithFocus(this.mEditText);
        this.mEditText.setCursorVisible(true);
    }

    public void hide() {
        if (this.mRootView == null) {
            return;
        }
        Utils.hideSoftInput(this.mEditText);
        this.mIsShow = false;
        this.mEditText.clearFocus();
        this.mEditText.setShowSoftInputOnFocus(false);
        Folme.useAt(this.mRootView).state().cancel();
        Folme.useAt(this.mRootView).state().removeListener(this.mShowListener).removeListener(this.mHideListener).addListener(this.mHideListener).to(ViewProperty.ALPHA, Float.valueOf(0.0f));
    }

    public void hideWithSoftInput() {
        if (this.mRootView == null || this.mPlaceHolder == null) {
            return;
        }
        this.mIsShow = false;
        this.mEditText.clearFocus();
        Folme.useAt(this.mRootView).state().cancel();
        Folme.useAt(this.mRootView).state().removeListener(this.mShowListener).removeListener(this.mHideListener).addListener(this.mHideListener).to(ViewProperty.ALPHA, Float.valueOf(0.0f));
        this.mRootView.setVisibility(8);
        this.mPlaceHolder.setVisibility(8);
    }

    public void cancel() {
        this.mRootView.performClick();
    }

    public void setEditListener(EditListener editListener) {
        this.mEditListener = editListener;
    }

    public String getAnnotation() {
        if (this.mRootView == null) {
            return "";
        }
        return this.mEditText.getText().toString();
    }

    public boolean isShowing() {
        return this.mIsShow;
    }

    private void smallWindowAdapter() {
        View view = this.mRootView;
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.ui.widget.AnnotationEditController.6
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    Activity activity = UIUtils.getActivity(AnnotationEditController.this.mRootView.getContext());
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view2);
                    if (rootWindowInsets != null && activity != null) {
                        Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                        if (FreeModeUtils.isFreeFormScreen(activity.getResources().getConfiguration())) {
                            AnnotationEditController.this.updatePanelMarginBottom(activity, insetsIgnoringVisibility.bottom);
                            return windowInsetsCompat;
                        }
                        AnnotationEditController.this.updatePanelMarginBottom(activity, 0);
                    }
                    return windowInsetsCompat;
                }
            });
        }
    }
}
