package com.miui.handwriting.common.view.custom;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.preference.Preference;
import com.miui.creation.common.R;
import miuix.preference.DropDownPreference;
import miuix.preference.PreferenceFragment;

/* JADX INFO: loaded from: classes3.dex */
public class CustomPreferenceFragment extends PreferenceFragment {
    private final String TAG = getClass().getSimpleName();
    OnHeightInputListener onHeightInputListener;
    OnWidthInputListener onWidthInputListener;
    int pageHeight;
    int pageUnit;
    int pageWidth;
    SizePreference sizePreference;

    public interface OnHeightInputListener {
        void onHeightInput(boolean z);
    }

    public interface OnWidthInputListener {
        void onWidthInput(boolean z);
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setExtraHorizontalPaddingEnable(false);
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        setPreferencesFromResource(R.xml.preference_custom_page_size, str);
        SizePreference sizePreference = (SizePreference) findPreference("size_preference");
        this.sizePreference = sizePreference;
        sizePreference.setOnHeightInputListener(this.onHeightInputListener);
        this.sizePreference.setOnWidthInputListener(this.onWidthInputListener);
        this.sizePreference.setUnitType(this.pageUnit);
        this.sizePreference.setDefaultHeight(this.pageHeight);
        this.sizePreference.setDefaultWidth(this.pageWidth);
        DropDownPreference dropDownPreference = (DropDownPreference) findPreference("unit_preference");
        dropDownPreference.setAdapter(new DropDownPreferenceAdapter(getContext(), null));
        dropDownPreference.setEntries(new CharSequence[]{getResources().getString(R.string.creation_page_size_custom_pixel), getResources().getString(R.string.creation_page_size_custom_millimeter)});
        if (dropDownPreference != null) {
            if (this.pageUnit == 2) {
                dropDownPreference.setValue(getResources().getString(R.string.creation_page_size_custom_millimeter));
            } else {
                dropDownPreference.setValue(getResources().getString(R.string.creation_page_size_custom_pixel));
            }
        }
        dropDownPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: com.miui.handwriting.common.view.custom.CustomPreferenceFragment.1
            @Override // androidx.preference.Preference.OnPreferenceChangeListener
            public boolean onPreferenceChange(Preference preference, Object obj) {
                if (TextUtils.equals(new String((String) obj), CustomPreferenceFragment.this.getResources().getString(R.string.creation_page_size_custom_millimeter))) {
                    CustomPreferenceFragment.this.sizePreference.setUnitType(2);
                    if (CustomPreferenceFragment.this.sizePreference != null && CustomPreferenceFragment.this.sizePreference.contentTextHeight != null) {
                        String string = CustomPreferenceFragment.this.sizePreference.getContentTextHeight().getText().toString();
                        if (!TextUtils.isEmpty(string)) {
                            String strValueOf = String.valueOf((int) CustomPreferenceFragment.this.sizePreference.getRealHeightMM());
                            if (!TextUtils.equals("0", string) && TextUtils.equals("0", strValueOf)) {
                                strValueOf = "1";
                            }
                            CustomPreferenceFragment.this.sizePreference.getContentTextHeight().setText(strValueOf);
                        } else {
                            CustomPreferenceFragment.this.sizePreference.getContentTextHeight().setHint(R.string.creation_page_size_hint);
                        }
                    }
                    if (CustomPreferenceFragment.this.sizePreference != null && CustomPreferenceFragment.this.sizePreference.contentTextWidth != null) {
                        String string2 = CustomPreferenceFragment.this.sizePreference.getContentTextWidth().getText().toString();
                        if (!TextUtils.isEmpty(string2)) {
                            String strValueOf2 = String.valueOf((int) CustomPreferenceFragment.this.sizePreference.getRealWidthMM());
                            CustomPreferenceFragment.this.sizePreference.getContentTextWidth().setText((TextUtils.equals("0", string2) || !TextUtils.equals("0", strValueOf2)) ? strValueOf2 : "1");
                        } else {
                            CustomPreferenceFragment.this.sizePreference.getContentTextWidth().setHint(R.string.creation_page_size_hint);
                        }
                    }
                } else {
                    CustomPreferenceFragment.this.sizePreference.setUnitType(1);
                    if (CustomPreferenceFragment.this.sizePreference != null && CustomPreferenceFragment.this.sizePreference.contentTextHeight != null) {
                        if (!TextUtils.isEmpty(CustomPreferenceFragment.this.sizePreference.getContentTextHeight().getText().toString())) {
                            CustomPreferenceFragment.this.sizePreference.getContentTextHeight().setText(String.valueOf((int) CustomPreferenceFragment.this.sizePreference.getRealHeightPX()));
                        } else {
                            CustomPreferenceFragment.this.sizePreference.getContentTextHeight().setHint(R.string.creation_page_size_hint);
                        }
                    }
                    if (CustomPreferenceFragment.this.sizePreference != null && CustomPreferenceFragment.this.sizePreference.contentTextWidth != null) {
                        if (!TextUtils.isEmpty(CustomPreferenceFragment.this.sizePreference.getContentTextWidth().getText().toString())) {
                            CustomPreferenceFragment.this.sizePreference.getContentTextWidth().setText(String.valueOf((int) CustomPreferenceFragment.this.sizePreference.getRealWidthPX()));
                        } else {
                            CustomPreferenceFragment.this.sizePreference.getContentTextWidth().setHint(R.string.creation_page_size_hint);
                        }
                    }
                }
                if (CustomPreferenceFragment.this.sizePreference != null && CustomPreferenceFragment.this.sizePreference.getContentTextHeight() != null && CustomPreferenceFragment.this.sizePreference.getContentTextHeight().getText() != null && CustomPreferenceFragment.this.sizePreference.getContentTextWidth() != null && CustomPreferenceFragment.this.sizePreference.getContentTextWidth().getText() != null) {
                    CustomPreferenceFragment.this.sizePreference.setSelection(CustomPreferenceFragment.this.sizePreference.getContentTextHeight(), CustomPreferenceFragment.this.sizePreference.getContentTextHeight().getText().length());
                    CustomPreferenceFragment.this.sizePreference.setSelection(CustomPreferenceFragment.this.sizePreference.getContentTextWidth(), CustomPreferenceFragment.this.sizePreference.getContentTextWidth().getText().length());
                }
                return true;
            }
        });
    }

    public int getPageWidth() {
        String string = this.sizePreference.contentTextWidth.getText().toString();
        try {
            SizePreference sizePreference = this.sizePreference;
            if (sizePreference == null || sizePreference.contentTextWidth == null) {
                return Integer.MAX_VALUE;
            }
            return Integer.parseInt(string);
        } catch (NumberFormatException unused) {
            Log.e(this.TAG, "getPageWidth  NumberFormatException " + string);
            return Integer.MAX_VALUE;
        }
    }

    public int getPageHeight() {
        String string = this.sizePreference.contentTextHeight.getText().toString();
        try {
            SizePreference sizePreference = this.sizePreference;
            if (sizePreference == null || sizePreference.contentTextHeight == null) {
                return Integer.MAX_VALUE;
            }
            return Integer.parseInt(string);
        } catch (NumberFormatException unused) {
            Log.e(this.TAG, "getPageHeight NumberFormatException " + string);
            return Integer.MAX_VALUE;
        }
    }

    public int getPageUnit() {
        return TextUtils.equals(((DropDownPreference) findPreference("unit_preference")).getValue(), getResources().getString(R.string.creation_page_size_custom_millimeter)) ? 2 : 1;
    }

    public void setPageWidth(int i) {
        this.pageWidth = i;
    }

    public void setPageHeight(int i) {
        this.pageHeight = i;
    }

    public void setPageUnit(int i) {
        this.pageUnit = i;
    }

    public void setOnHeightInputListener(OnHeightInputListener onHeightInputListener) {
        this.onHeightInputListener = onHeightInputListener;
    }

    public void setOnWidthInputListener(OnWidthInputListener onWidthInputListener) {
        this.onWidthInputListener = onWidthInputListener;
    }
}
