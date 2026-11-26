package com.miui.pad.feature.settings;

import android.content.res.Configuration;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.R;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.DoubleImagePreference;
import com.miui.notes.ui.fragment.AppSettingsFragment;
import com.miui.todo.feature.settings.QuickNotePreferenceActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.preference.DropDownPreference;

/* JADX INFO: compiled from: FoldAppSettingsFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH\u0017J\b\u0010\n\u001a\u00020\u000bH\u0014J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000bH\u0016J\u0010\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u000bH\u0014J\b\u0010\u0017\u001a\u00020\u000bH\u0014J\u0010\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\tH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/miui/pad/feature/settings/FoldAppSettingsFragment;", "Lcom/miui/notes/ui/fragment/AppSettingsFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mNoteHomePageArrayPref", "Lmiuix/preference/DropDownPreference;", "mHomePageLayoutDemo", "Lcom/miui/notes/ui/DoubleImagePreference;", "getPreferencesRes", "", "initPreferences", "", "onPreferenceChange", "", "preference", "Landroidx/preference/Preference;", "newValue", "", "onResume", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "toQuickNoteSetting", "toPermissionPreference", "updateLayoutDemoInfo", "homepageArrayMode", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class FoldAppSettingsFragment extends AppSettingsFragment {
    protected static final String PRE_KEY_LAYOUT_DEMO = "pref_key_layout_demo";
    protected static final String PRE_KEY_NOTE_HOMEPAGE_ARRAY_WAY = "pref_key_note_homepage_array_way";
    private DoubleImagePreference mHomePageLayoutDemo;
    private DropDownPreference mNoteHomePageArrayPref;

    @Override // com.miui.notes.ui.fragment.AppSettingsFragment
    public int getPreferencesRes() {
        return R.xml.fold_app_preferences;
    }

    @Override // com.miui.notes.ui.fragment.AppSettingsFragment
    protected void initPreferences() {
        super.initPreferences();
        CheckBoxPreference mRemindTypePref$app_PhoneGlobalRelease = getMRemindTypePref();
        if (mRemindTypePref$app_PhoneGlobalRelease != null) {
            mRemindTypePref$app_PhoneGlobalRelease.setChecked(PreferenceUtils.getRemindType(BaseNoteApp.INSTANCE.getInstance()));
        }
        DropDownPreference dropDownPreference = (DropDownPreference) findPreference(PRE_KEY_NOTE_HOMEPAGE_ARRAY_WAY);
        this.mNoteHomePageArrayPref = dropDownPreference;
        Intrinsics.checkNotNull(dropDownPreference);
        dropDownPreference.setEntries(R.array.big_screen_layouts);
        DropDownPreference dropDownPreference2 = this.mNoteHomePageArrayPref;
        Intrinsics.checkNotNull(dropDownPreference2);
        dropDownPreference2.setEntryValues(NoteConfig.NOTE_ARRAY_ENTRY_VALUE);
        DropDownPreference dropDownPreference3 = this.mNoteHomePageArrayPref;
        Intrinsics.checkNotNull(dropDownPreference3);
        dropDownPreference3.setDefaultValue("0");
        int noteHomePageArrayWay = PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0);
        DropDownPreference dropDownPreference4 = this.mNoteHomePageArrayPref;
        Intrinsics.checkNotNull(dropDownPreference4);
        dropDownPreference4.setValue(String.valueOf(noteHomePageArrayWay));
        DropDownPreference dropDownPreference5 = this.mNoteHomePageArrayPref;
        Intrinsics.checkNotNull(dropDownPreference5);
        dropDownPreference5.setOnPreferenceChangeListener(this);
        this.mHomePageLayoutDemo = (DoubleImagePreference) findPreference(PRE_KEY_LAYOUT_DEMO);
        updateLayoutDemoInfo(noteHomePageArrayWay);
    }

    @Override // com.miui.notes.ui.fragment.AppSettingsFragment, androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        super.onPreferenceChange(preference, newValue);
        if (isAdded() && Intrinsics.areEqual(PRE_KEY_NOTE_HOMEPAGE_ARRAY_WAY, preference.getKey())) {
            int i = Integer.parseInt(newValue.toString());
            PreferenceUtils.setNoteHomePageArrayWay(getContext(), i);
            DropDownPreference dropDownPreference = this.mNoteHomePageArrayPref;
            Intrinsics.checkNotNull(dropDownPreference);
            dropDownPreference.setValue(newValue.toString());
            updateLayoutDemoInfo(i);
            if (i == 1) {
                LiveDataEventBus.with(LiveDataEventBus.BUS_HOMEPAGE_ARRAY_WAY).setValue(true);
            } else {
                LiveDataEventBus.with(LiveDataEventBus.BUS_HOMEPAGE_ARRAY_WAY).setValue(false);
            }
        }
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            UIUtils.checkOrientationInFoldDevice(fragmentActivityRequireActivity);
        }
    }

    @Override // miuix.preference.PreferenceFragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        if (getActivity() != null) {
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            UIUtils.checkOrientationInFoldDevice(fragmentActivityRequireActivity);
        }
    }

    @Override // com.miui.notes.ui.fragment.AppSettingsFragment
    protected void toQuickNoteSetting() {
        QuickNotePreferenceActivity.Companion companion = QuickNotePreferenceActivity.INSTANCE;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        companion.open(fragmentActivityRequireActivity);
    }

    @Override // com.miui.notes.ui.fragment.AppSettingsFragment
    protected void toPermissionPreference() {
        PadPermissionPreferenceActivity.Companion companion = PadPermissionPreferenceActivity.INSTANCE;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        companion.open(fragmentActivityRequireActivity);
    }

    private final void updateLayoutDemoInfo(int homepageArrayMode) {
        int[] iArr = {R.string.app_name, R.string.todo_module_name};
        int[] iArr2 = new int[2];
        if (homepageArrayMode == 0) {
            iArr2[0] = R.drawable.ic_layout_demo_columns_note;
            iArr2[1] = R.drawable.ic_layout_demo_columns_todo;
        } else if (homepageArrayMode == 1) {
            iArr2[0] = R.drawable.ic_layout_demo_flat_note;
            iArr2[1] = R.drawable.ic_layout_demo_flat_todo;
        }
        DoubleImagePreference doubleImagePreference = this.mHomePageLayoutDemo;
        Intrinsics.checkNotNull(doubleImagePreference);
        doubleImagePreference.setImageAndInfo(iArr2, iArr);
    }
}
