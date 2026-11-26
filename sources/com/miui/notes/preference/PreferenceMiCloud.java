package com.miui.notes.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import com.miui.notes.ui.MiCloudPreferenceView;
import com.miui.notes.ui.MiCloudStatePreferenceController;
import miuix.core.util.MiuixUIUtils;
import miuix.preference.FolmeAnimationController;

/* JADX INFO: loaded from: classes4.dex */
public class PreferenceMiCloud extends Preference implements FolmeAnimationController {
    private String TAG;
    private MiCloudPreferenceView mMiCloudPreferenceView;
    private MiCloudStatePreferenceController mMiCloudStateController;

    @Override // miuix.preference.FolmeAnimationController
    public boolean isTouchAnimationEnable() {
        return true;
    }

    public PreferenceMiCloud(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.TAG = "PreferenceMiCloud";
        setLayoutResource(R.layout.micloud_preference);
    }

    public PreferenceMiCloud(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PreferenceMiCloud(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PreferenceMiCloud(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mMiCloudPreferenceView = (MiCloudPreferenceView) preferenceViewHolder.findViewById(R.id.micloud_state_view);
        Logger.INSTANCE.d(this.TAG, "onBindViewHolder: font size: " + getContext().getResources().getConfiguration().fontScale);
        Logger.INSTANCE.d(this.TAG, "onBindViewHolder: font level: " + MiuixUIUtils.getFontLevel(getContext()));
        if (getContext().getResources().getConfiguration().fontScale > 1.5f) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mMiCloudPreferenceView.getLayoutParams();
            marginLayoutParams.topMargin = MiuixUIUtils.dp2px(getContext(), 10.0f);
            marginLayoutParams.bottomMargin = MiuixUIUtils.dp2px(getContext(), 10.0f);
            this.mMiCloudPreferenceView.setLayoutParams(marginLayoutParams);
        }
        MiCloudStatePreferenceController miCloudStatePreferenceController = new MiCloudStatePreferenceController(this.mMiCloudPreferenceView);
        this.mMiCloudStateController = miCloudStatePreferenceController;
        miCloudStatePreferenceController.onResume();
        this.mMiCloudStateController.onDataSetChanged();
    }
}
