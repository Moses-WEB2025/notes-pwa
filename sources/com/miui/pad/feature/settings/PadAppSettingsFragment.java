package com.miui.pad.feature.settings;

import androidx.fragment.app.FragmentActivity;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.R;
import com.miui.notes.ui.fragment.AppSettingsFragment;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PadAppSettingsFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0017J\b\u0010\u0006\u001a\u00020\u0007H\u0014¨\u0006\b"}, d2 = {"Lcom/miui/pad/feature/settings/PadAppSettingsFragment;", "Lcom/miui/notes/ui/fragment/AppSettingsFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getPreferencesRes", "", "toPermissionPreference", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadAppSettingsFragment extends AppSettingsFragment {
    @Override // com.miui.notes.ui.fragment.AppSettingsFragment
    public int getPreferencesRes() {
        return R.xml.pad_app_preferences;
    }

    @Override // com.miui.notes.ui.fragment.AppSettingsFragment
    protected void toPermissionPreference() {
        PadPermissionPreferenceActivity.Companion companion = PadPermissionPreferenceActivity.INSTANCE;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        companion.open(fragmentActivityRequireActivity);
    }
}
