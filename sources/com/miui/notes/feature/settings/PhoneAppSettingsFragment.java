package com.miui.notes.feature.settings;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.R;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.fragment.AppSettingsFragment;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.preference.DropDownPreference;

/* JADX INFO: compiled from: PhoneAppSettingsFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0016\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0017J\b\u0010\b\u001a\u00020\tH\u0014J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\tH\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/miui/notes/feature/settings/PhoneAppSettingsFragment;", "Lcom/miui/notes/ui/fragment/AppSettingsFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mNoteHomePageArrayPref", "Lmiuix/preference/DropDownPreference;", "getPreferencesRes", "", "initPreferences", "", "onPreferenceChange", "", "preference", "Landroidx/preference/Preference;", "newValue", "", "onDestroyView", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class PhoneAppSettingsFragment extends AppSettingsFragment {
    protected static final String PRE_KEY_PHONE_NOTE_HOMEPAGE_ARRAY_WAY = "pref_key_phone_note_homepage_array_way";
    private DropDownPreference mNoteHomePageArrayPref;

    @Override // com.miui.notes.ui.fragment.AppSettingsFragment
    public int getPreferencesRes() {
        return R.xml.phone_app_preferences;
    }

    @Override // com.miui.notes.ui.fragment.AppSettingsFragment
    protected void initPreferences() {
        Logger.INSTANCE.d("PhoneAppSettingsFragment", "initPreferences");
        super.initPreferences();
        CheckBoxPreference mRemindTypePref$app_PhoneGlobalRelease = getMRemindTypePref();
        if (mRemindTypePref$app_PhoneGlobalRelease != null) {
            mRemindTypePref$app_PhoneGlobalRelease.setChecked(PreferenceUtils.getRemindType(BaseNoteApp.INSTANCE.getInstance()));
        }
        DropDownPreference dropDownPreference = (DropDownPreference) findPreference(PRE_KEY_PHONE_NOTE_HOMEPAGE_ARRAY_WAY);
        this.mNoteHomePageArrayPref = dropDownPreference;
        Intrinsics.checkNotNull(dropDownPreference);
        dropDownPreference.setEntries(R.array.setting_phone_note_layout_way);
        DropDownPreference dropDownPreference2 = this.mNoteHomePageArrayPref;
        Intrinsics.checkNotNull(dropDownPreference2);
        dropDownPreference2.setEntryValues(NoteConfig.PHONE_NOTE_ARRAY_ENTRY_VALUE);
        DropDownPreference dropDownPreference3 = this.mNoteHomePageArrayPref;
        Intrinsics.checkNotNull(dropDownPreference3);
        dropDownPreference3.setDefaultValue("0");
        boolean gridMode = PreferenceUtils.getGridMode(getContext());
        DropDownPreference dropDownPreference4 = this.mNoteHomePageArrayPref;
        Intrinsics.checkNotNull(dropDownPreference4);
        dropDownPreference4.setValue(String.valueOf(gridMode ? 1 : 0));
        DropDownPreference dropDownPreference5 = this.mNoteHomePageArrayPref;
        Intrinsics.checkNotNull(dropDownPreference5);
        dropDownPreference5.setOnPreferenceChangeListener(this);
        if (LiteUtils.isLiteOrMiddle()) {
            DropDownPreference dropDownPreference6 = this.mNoteHomePageArrayPref;
            Intrinsics.checkNotNull(dropDownPreference6);
            dropDownPreference6.setVisible(false);
        }
    }

    @Override // com.miui.notes.ui.fragment.AppSettingsFragment, androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        super.onPreferenceChange(preference, newValue);
        if (isAdded() && Intrinsics.areEqual(PRE_KEY_PHONE_NOTE_HOMEPAGE_ARRAY_WAY, preference.getKey())) {
            int i = Integer.parseInt(newValue.toString());
            LiveDataEventBus.with(LiveDataEventBus.BUS_HOMEPAGE_GRID_MODE).setValue(Boolean.valueOf(i == 1));
            PreferenceUtils.setGridMode(getContext(), i == 1);
            DropDownPreference dropDownPreference = this.mNoteHomePageArrayPref;
            Intrinsics.checkNotNull(dropDownPreference);
            dropDownPreference.setValue(newValue.toString());
        }
        return true;
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        DropDownPreference dropDownPreference = this.mNoteHomePageArrayPref;
        if (dropDownPreference != null) {
            dropDownPreference.setOnPreferenceChangeListener(null);
        }
    }
}
