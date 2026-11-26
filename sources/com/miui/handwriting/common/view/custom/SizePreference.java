package com.miui.handwriting.common.view.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.tools.UIUtils;
import miuix.preference.PreferenceStyle;

/* JADX INFO: loaded from: classes3.dex */
public class SizePreference extends Preference implements PreferenceStyle {
    private static final int INVALID_RESOURCE_ID = 0;
    private final String TAG;
    StateEditText contentTextHeight;
    StateEditText contentTextWidth;
    private int defaultHeightMM;
    private int defaultHeightPX;
    private int defaultWidthMM;
    private int defaultWidthPX;
    private boolean firstShow;
    private int heightUpperLimit;
    private CharSequence mText;
    private int mTextRes;
    CustomPreferenceFragment.OnHeightInputListener onHeightInputListener;
    CustomPreferenceFragment.OnWidthInputListener onWidthInputListener;
    private int realHeightMM;
    private int realHeightPX;
    private int realWidthMM;
    private int realWidthPX;
    private int unitType;
    private int widthUpperLimit;

    @Override // miuix.preference.PreferenceStyle
    public boolean enabledCardStyle() {
        return false;
    }

    @Override // miuix.preference.FolmeAnimationController
    public boolean isTouchAnimationEnable() {
        return false;
    }

    public void setOnHeightInputListener(CustomPreferenceFragment.OnHeightInputListener onHeightInputListener) {
        this.onHeightInputListener = onHeightInputListener;
    }

    public void setOnWidthInputListener(CustomPreferenceFragment.OnWidthInputListener onWidthInputListener) {
        this.onWidthInputListener = onWidthInputListener;
    }

    public float getRealWidthMM() {
        return this.realWidthMM;
    }

    public float getRealWidthPX() {
        return this.realWidthPX;
    }

    public void setDefaultWidth(int i) {
        if (this.unitType == 1) {
            this.defaultWidthPX = i;
            this.defaultWidthMM = (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), i);
        } else {
            this.defaultWidthPX = (int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), i);
            this.defaultWidthMM = i;
        }
        this.realWidthMM = this.defaultWidthMM;
        this.realWidthPX = this.defaultWidthPX;
    }

    public float getRealHeightMM() {
        return this.realHeightMM;
    }

    public float getRealHeightPX() {
        return this.realHeightPX;
    }

    public void setDefaultHeight(int i) {
        if (this.unitType == 1) {
            this.defaultHeightPX = i;
            this.defaultHeightMM = (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), this.defaultHeightPX);
        } else {
            this.defaultHeightPX = (int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), i);
            this.defaultHeightMM = i;
        }
        this.realHeightMM = this.defaultHeightMM;
        this.realHeightPX = this.defaultHeightPX;
    }

    public void setUnitType(int i) {
        this.unitType = i;
    }

    public StateEditText getContentTextHeight() {
        return this.contentTextHeight;
    }

    public StateEditText getContentTextWidth() {
        return this.contentTextWidth;
    }

    public SizePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.Miuix_Preference_HeightPreference);
    }

    public SizePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.heightPreferenceStyle);
    }

    public SizePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.TAG = getClass().getSimpleName();
        this.mText = "";
        this.mTextRes = 0;
        this.unitType = 0;
        this.defaultHeightPX = 0;
        this.defaultHeightMM = 0;
        this.realHeightMM = 0;
        this.realHeightPX = 0;
        this.defaultWidthPX = 0;
        this.defaultWidthMM = 0;
        this.realWidthMM = 0;
        this.realWidthPX = 0;
        this.firstShow = true;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HeightPreference, i, i2);
        this.mTextRes = typedArrayObtainStyledAttributes.getResourceId(R.styleable.HeightPreference_android_text, 0);
        this.mText = typedArrayObtainStyledAttributes.getText(R.styleable.HeightPreference_android_text);
        int realWidth = UIUtils.getRealWidth(context) * UIUtils.getRealHeight(context);
        if (realWidth <= 2073600) {
            this.heightUpperLimit = 2560;
            this.widthUpperLimit = 1440;
        } else if (realWidth <= 3686400) {
            this.heightUpperLimit = 3840;
            this.widthUpperLimit = 2160;
        } else {
            this.heightUpperLimit = 6016;
            this.widthUpperLimit = 3384;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        StateEditText stateEditText = (StateEditText) view.findViewById(miuix.preference.R.id.content);
        this.contentTextHeight = stateEditText;
        if (stateEditText != null) {
            stateEditText.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_unfoused));
            this.contentTextHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.handwriting.common.view.custom.SizePreference.1
                @Override // android.view.View.OnFocusChangeListener
                public void onFocusChange(View view2, boolean z) {
                    int i;
                    Log.d(SizePreference.this.TAG, "onFocusChange " + ((Object) SizePreference.this.contentTextHeight.getText()));
                    if (!z) {
                        SizePreference.this.contentTextHeight.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_unfoused));
                        return;
                    }
                    if (TextUtils.isEmpty(SizePreference.this.contentTextHeight.getText())) {
                        SizePreference.this.contentTextHeight.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_blue));
                        return;
                    }
                    try {
                        i = Integer.parseInt(SizePreference.this.contentTextHeight.getText().toString());
                    } catch (NumberFormatException unused) {
                        Log.e(SizePreference.this.TAG, "onFocusChange NumberFormatException ");
                        i = Integer.MAX_VALUE;
                    }
                    if (SizePreference.this.unitType == 2) {
                        if (i != SizePreference.this.realHeightMM) {
                            SizePreference.this.realHeightMM = i;
                            SizePreference.this.realHeightPX = (int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.realHeightMM);
                        }
                        Log.d(SizePreference.this.TAG, "contentTextHeight onFocusChange realHeightPX " + SizePreference.this.realHeightPX + ";  realHeightMM " + SizePreference.this.realHeightMM);
                        if (i > ((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.heightUpperLimit))) {
                            SizePreference.this.contentTextHeight.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_red));
                            return;
                        } else if (i < ((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), 600.0f))) {
                            SizePreference.this.contentTextHeight.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_red));
                            return;
                        } else {
                            SizePreference.this.contentTextHeight.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_blue));
                            return;
                        }
                    }
                    if (i != SizePreference.this.realHeightPX) {
                        SizePreference.this.realHeightPX = i;
                        SizePreference.this.realHeightMM = (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.realHeightPX);
                    }
                    Log.d(SizePreference.this.TAG, "contentTextHeight onFocusChange px  realHeightPX " + SizePreference.this.realHeightPX + ";  realHeightMM " + SizePreference.this.realHeightMM);
                    if (i > SizePreference.this.heightUpperLimit) {
                        SizePreference.this.contentTextHeight.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_red));
                    } else if (i < 600) {
                        SizePreference.this.contentTextHeight.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_red));
                    } else {
                        SizePreference.this.contentTextHeight.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_blue));
                    }
                }
            });
            this.contentTextHeight.addTextChangedListener(new TextWatcher() { // from class: com.miui.handwriting.common.view.custom.SizePreference.2
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    int i4;
                    Log.d(SizePreference.this.TAG, "contentTextHeight onTextChanged " + ((Object) charSequence) + ";  unitType " + SizePreference.this.unitType + "; firstShow " + SizePreference.this.firstShow);
                    if (TextUtils.isEmpty(charSequence)) {
                        if (SizePreference.this.onHeightInputListener != null) {
                            SizePreference.this.onHeightInputListener.onHeightInput(false);
                        }
                        if (SizePreference.this.contentTextHeight != null) {
                            if (SizePreference.this.unitType == 2) {
                                if (SizePreference.this.firstShow) {
                                    SizePreference.this.contentTextHeight.setText(String.valueOf(SizePreference.this.defaultHeightMM));
                                } else {
                                    SizePreference.this.contentTextHeight.setHint(R.string.creation_page_size_hint);
                                }
                            } else if (SizePreference.this.firstShow) {
                                SizePreference.this.contentTextHeight.setText(String.valueOf(SizePreference.this.defaultHeightPX));
                            } else {
                                SizePreference.this.contentTextHeight.setHint(R.string.creation_page_size_hint);
                            }
                            SizePreference sizePreference = SizePreference.this;
                            sizePreference.setBackgroundBlue(sizePreference.contentTextHeight, "");
                            return;
                        }
                        return;
                    }
                    try {
                        i4 = Integer.parseInt(charSequence.toString());
                    } catch (NumberFormatException unused) {
                        Log.e(SizePreference.this.TAG, "onTextChanged NumberFormatException ");
                        i4 = Integer.MAX_VALUE;
                    }
                    if (SizePreference.this.onHeightInputListener != null) {
                        SizePreference.this.onHeightInputListener.onHeightInput(true);
                    }
                    if (SizePreference.this.contentTextHeight != null) {
                        Resources resources = HandWritingViewApplication.INSTANCE.getApplication().getResources();
                        if (SizePreference.this.unitType == 2) {
                            if (i4 == 1) {
                                SizePreference.this.realHeightMM = i4;
                                if (SizePreference.this.realHeightPX >= ((int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), 2.0f)) || SizePreference.this.realHeightPX < 1) {
                                    SizePreference.this.realHeightPX = (int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), 1.0f);
                                }
                            } else if (i4 != SizePreference.this.realHeightMM) {
                                SizePreference.this.realHeightMM = i4;
                                SizePreference.this.realHeightPX = (int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.realHeightMM);
                            }
                            Log.d(SizePreference.this.TAG, "contentTextHeight val " + i4 + ";  realHeightPX " + SizePreference.this.realHeightPX);
                            if (i4 > ((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.heightUpperLimit))) {
                                String quantityString = resources.getQuantityString(R.plurals.creation_page_size_custom_height_limit_mm, (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.heightUpperLimit), Integer.valueOf((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.heightUpperLimit)));
                                SizePreference.this.contentTextHeight.setLabelSummary(quantityString);
                                SizePreference sizePreference2 = SizePreference.this;
                                sizePreference2.setBackgroundRed(sizePreference2.contentTextHeight, quantityString);
                                return;
                            }
                            if (i4 < ((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), 600.0f))) {
                                String quantityString2 = resources.getQuantityString(R.plurals.creation_page_size_custom_height_lower_limit_mm, (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), 600.0f), Integer.valueOf((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), 600.0f)));
                                SizePreference.this.contentTextHeight.setLabelSummary(quantityString2);
                                SizePreference sizePreference3 = SizePreference.this;
                                sizePreference3.setBackgroundRed(sizePreference3.contentTextHeight, quantityString2);
                                return;
                            }
                            SizePreference sizePreference4 = SizePreference.this;
                            sizePreference4.setBackgroundBlue(sizePreference4.contentTextHeight, "");
                            return;
                        }
                        if (i4 != SizePreference.this.realHeightPX) {
                            SizePreference.this.realHeightPX = i4;
                            SizePreference.this.realHeightMM = (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.realHeightPX);
                        }
                        Log.d(SizePreference.this.TAG, "contentTextHeight px val " + i4 + ";  realHeightPX " + SizePreference.this.realHeightPX);
                        if (i4 > SizePreference.this.heightUpperLimit) {
                            String quantityString3 = resources.getQuantityString(R.plurals.creation_page_size_custom_height_limit_px, SizePreference.this.heightUpperLimit, Integer.valueOf(SizePreference.this.heightUpperLimit));
                            SizePreference sizePreference5 = SizePreference.this;
                            sizePreference5.setBackgroundRed(sizePreference5.contentTextHeight, quantityString3);
                        } else if (i4 < 600) {
                            String quantityString4 = resources.getQuantityString(R.plurals.creation_page_size_custom_height_lower_limit_px, 600, 600);
                            SizePreference sizePreference6 = SizePreference.this;
                            sizePreference6.setBackgroundRed(sizePreference6.contentTextHeight, quantityString4);
                        } else {
                            SizePreference sizePreference7 = SizePreference.this;
                            sizePreference7.setBackgroundBlue(sizePreference7.contentTextHeight, "");
                        }
                    }
                }
            });
            int i = this.mTextRes;
            if (i != 0) {
                this.contentTextHeight.setText(i);
            } else {
                this.contentTextHeight.setText(this.mText);
            }
        }
        StateEditText stateEditText2 = (StateEditText) view.findViewById(R.id.content_width);
        this.contentTextWidth = stateEditText2;
        if (stateEditText2 != null) {
            stateEditText2.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_unfoused));
            this.contentTextWidth.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.handwriting.common.view.custom.SizePreference.3
                @Override // android.view.View.OnFocusChangeListener
                public void onFocusChange(View view2, boolean z) {
                    int i2;
                    Log.d(SizePreference.this.TAG, "contentTextWidth onFocusChange " + z);
                    if (!z) {
                        SizePreference.this.contentTextWidth.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_unfoused));
                        return;
                    }
                    if (TextUtils.isEmpty(SizePreference.this.contentTextWidth.getText())) {
                        SizePreference.this.contentTextWidth.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_blue));
                        return;
                    }
                    try {
                        i2 = Integer.parseInt(SizePreference.this.contentTextWidth.getText().toString());
                    } catch (NumberFormatException unused) {
                        Log.e(SizePreference.this.TAG, "onTextChanged NumberFormatException ");
                        i2 = Integer.MAX_VALUE;
                    }
                    if (SizePreference.this.unitType == 2) {
                        if (i2 != SizePreference.this.realWidthMM) {
                            SizePreference.this.realWidthMM = i2;
                            SizePreference.this.realWidthPX = (int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.realWidthMM);
                            Log.d(SizePreference.this.TAG, "contentTextWidth mm val " + i2 + ";  realHeightPX " + SizePreference.this.realHeightPX);
                        }
                        if (i2 > ((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.widthUpperLimit))) {
                            SizePreference.this.contentTextWidth.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_red));
                            return;
                        } else if (i2 < ((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), 600.0f))) {
                            SizePreference.this.contentTextWidth.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_red));
                            return;
                        } else {
                            SizePreference.this.contentTextWidth.setLabelSummary("");
                            SizePreference.this.contentTextWidth.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_blue));
                            return;
                        }
                    }
                    if (i2 != SizePreference.this.realWidthPX) {
                        SizePreference.this.realWidthPX = i2;
                        SizePreference.this.realWidthMM = (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.realWidthPX);
                    }
                    Log.d(SizePreference.this.TAG, "contentTextWidth  val " + i2 + ";  realWidthPX " + SizePreference.this.realWidthMM);
                    if (i2 > SizePreference.this.widthUpperLimit) {
                        SizePreference.this.contentTextWidth.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_red));
                    } else if (i2 < 600) {
                        SizePreference.this.contentTextWidth.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_red));
                    } else {
                        SizePreference.this.contentTextWidth.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_blue));
                    }
                }
            });
            this.contentTextWidth.addTextChangedListener(new TextWatcher() { // from class: com.miui.handwriting.common.view.custom.SizePreference.4
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    int i5;
                    if (TextUtils.isEmpty(charSequence)) {
                        if (SizePreference.this.onWidthInputListener != null) {
                            SizePreference.this.onWidthInputListener.onWidthInput(false);
                        }
                        if (SizePreference.this.contentTextWidth != null) {
                            if (SizePreference.this.unitType == 2) {
                                if (SizePreference.this.firstShow) {
                                    SizePreference.this.contentTextWidth.setText(String.valueOf(SizePreference.this.defaultWidthMM));
                                    SizePreference.this.firstShow = false;
                                } else {
                                    SizePreference.this.contentTextWidth.setHint(R.string.creation_page_size_hint);
                                }
                            } else if (SizePreference.this.firstShow) {
                                SizePreference.this.firstShow = false;
                                SizePreference.this.contentTextWidth.setText(String.valueOf(SizePreference.this.defaultWidthPX));
                            } else {
                                SizePreference.this.contentTextWidth.setHint(R.string.creation_page_size_hint);
                            }
                            SizePreference sizePreference = SizePreference.this;
                            sizePreference.setBackgroundBlue(sizePreference.contentTextWidth, "");
                            return;
                        }
                        return;
                    }
                    try {
                        i5 = Integer.parseInt(charSequence.toString());
                    } catch (NumberFormatException unused) {
                        Log.e(SizePreference.this.TAG, "onTextChanged NumberFormatException ");
                        i5 = Integer.MAX_VALUE;
                    }
                    if (SizePreference.this.onWidthInputListener != null) {
                        SizePreference.this.onWidthInputListener.onWidthInput(true);
                    }
                    Log.d(SizePreference.this.TAG, "contentTextWidth onTextChanged ");
                    if (SizePreference.this.contentTextWidth != null) {
                        Resources resources = HandWritingViewApplication.INSTANCE.getApplication().getResources();
                        if (SizePreference.this.unitType == 2) {
                            if (i5 == 1) {
                                SizePreference.this.realWidthMM = i5;
                                if (SizePreference.this.realWidthPX >= ((int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), 2.0f)) || SizePreference.this.realWidthPX < 1) {
                                    SizePreference.this.realWidthPX = (int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), 1.0f);
                                }
                            } else if (i5 != SizePreference.this.realWidthMM) {
                                SizePreference.this.realWidthMM = i5;
                                SizePreference.this.realWidthPX = (int) UIUtils.mm2px(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.realWidthMM);
                            }
                            Log.d(SizePreference.this.TAG, "contentTextWidth  mm realWidthMM " + SizePreference.this.realWidthMM + ";  realWidthPX " + SizePreference.this.realWidthPX);
                            if (i5 > ((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.widthUpperLimit))) {
                                String quantityString = resources.getQuantityString(R.plurals.creation_page_size_custom_width_limit_mm, (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.widthUpperLimit), Integer.valueOf((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.widthUpperLimit)));
                                SizePreference sizePreference2 = SizePreference.this;
                                sizePreference2.setBackgroundRed(sizePreference2.contentTextWidth, quantityString);
                                return;
                            } else if (i5 < ((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), 600.0f))) {
                                String quantityString2 = resources.getQuantityString(R.plurals.creation_page_size_custom_width_lower_limit_mm, (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), 600.0f), Integer.valueOf((int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), 600.0f)));
                                SizePreference sizePreference3 = SizePreference.this;
                                sizePreference3.setBackgroundRed(sizePreference3.contentTextWidth, quantityString2);
                                return;
                            } else {
                                SizePreference sizePreference4 = SizePreference.this;
                                sizePreference4.setBackgroundBlue(sizePreference4.contentTextWidth, "");
                                return;
                            }
                        }
                        if (i5 != SizePreference.this.realWidthPX) {
                            SizePreference.this.realWidthPX = i5;
                            SizePreference.this.realWidthMM = (int) UIUtils.px2mm(HandWritingViewApplication.INSTANCE.getApplication(), SizePreference.this.realWidthPX);
                        }
                        Log.d(SizePreference.this.TAG, "contentTextWidth  realWidthMM " + SizePreference.this.realWidthMM + ";  realWidthPX " + SizePreference.this.realWidthPX);
                        if (i5 > SizePreference.this.widthUpperLimit) {
                            String quantityString3 = resources.getQuantityString(R.plurals.creation_page_size_custom_width_limit_px, SizePreference.this.widthUpperLimit, Integer.valueOf(SizePreference.this.widthUpperLimit));
                            SizePreference sizePreference5 = SizePreference.this;
                            sizePreference5.setBackgroundRed(sizePreference5.contentTextWidth, quantityString3);
                        } else if (i5 < 600) {
                            String quantityString4 = resources.getQuantityString(R.plurals.creation_page_size_custom_width_lower_limit_px, 600, 600);
                            SizePreference sizePreference6 = SizePreference.this;
                            sizePreference6.setBackgroundRed(sizePreference6.contentTextWidth, quantityString4);
                        } else {
                            SizePreference sizePreference7 = SizePreference.this;
                            sizePreference7.setBackgroundBlue(sizePreference7.contentTextWidth, "");
                        }
                    }
                }
            });
            int i2 = this.mTextRes;
            if (i2 != 0) {
                this.contentTextWidth.setText(i2);
            } else {
                this.contentTextWidth.setText(this.mText);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBackgroundRed(StateEditText stateEditText, String str) {
        if (stateEditText == null) {
            return;
        }
        stateEditText.setLabelSummary(str);
        if (stateEditText.isFocused()) {
            stateEditText.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_red));
        } else {
            stateEditText.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_unfoused));
        }
    }

    public void setSelection(StateEditText stateEditText, int i) {
        if (stateEditText == null || !stateEditText.isFocused() || TextUtils.isEmpty(stateEditText.getText())) {
            return;
        }
        stateEditText.setSelection(Math.min(i, stateEditText.getText().length()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBackgroundBlue(StateEditText stateEditText, String str) {
        if (stateEditText == null) {
            return;
        }
        stateEditText.setLabelSummary(str);
        if (stateEditText.isFocused()) {
            stateEditText.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_blue));
        } else {
            stateEditText.setBackground(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.miuix_appcompat_edit_text_bg_light_unfoused));
        }
    }
}
