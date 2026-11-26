package com.miui.notes.preference;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class PreferencePrivacy extends Preference {
    public PreferencePrivacy(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.privacy_preference);
    }

    public PreferencePrivacy(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutResource(R.layout.privacy_preference);
    }

    public PreferencePrivacy(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.privacy_preference);
    }

    public PreferencePrivacy(Context context) {
        super(context);
        setLayoutResource(R.layout.privacy_preference);
    }
}
