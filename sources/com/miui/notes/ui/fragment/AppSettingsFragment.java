package com.miui.notes.ui.fragment;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Base64;
import android.view.MenuItem;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.assist.CtaActivityResult;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.cta.CTAActivity;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.feature.settings.PermissionPreferenceActivity;
import com.miui.notes.preference.PreferenceMiCloud;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.widget.NoteWidgetProvider_2x2;
import com.miui.notes.widget.NoteWidgetProvider_4x2;
import com.miui.notes.widget.NoteWidgetProvider_4x4;
import com.miui.notes.widget.NoteWidgetProvider_8x4;
import com.miui.todo.feature.settings.QuickNotePreferenceActivity;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import miui.cloud.CloudPushConstants;
import miuix.preference.DropDownPreference;
import miuix.preference.PreferenceFragment;

/* JADX INFO: compiled from: AppSettingsFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0010\b&\u0018\u0000 @2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001@B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u001c\u0010\u001f\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020#H&J\b\u0010$\u001a\u00020\u001cH\u0014J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0016J\u0018\u0010.\u001a\u00020+2\u0006\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u000201H\u0016J\u0010\u00102\u001a\u00020+2\u0006\u0010/\u001a\u00020\tH\u0016J\b\u00103\u001a\u00020\u001cH\u0002J\b\u00104\u001a\u00020\u001cH\u0002J\b\u00105\u001a\u00020\u001cH\u0002J\b\u00106\u001a\u00020\u001cH\u0002J\b\u00107\u001a\u00020\u001cH\u0002J\b\u00108\u001a\u00020\u001cH\u0002J\b\u00109\u001a\u00020\u001cH\u0002J\b\u0010:\u001a\u00020\u001cH\u0014J\b\u0010;\u001a\u00020\u001cH\u0014J\b\u0010<\u001a\u00020\u001cH\u0002J\b\u0010=\u001a\u00020\u001cH\u0002J\b\u0010>\u001a\u00020\u001cH\u0016J\b\u0010?\u001a\u00020\u001cH\u0002R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010'0&8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006A"}, d2 = {"Lcom/miui/notes/ui/fragment/AppSettingsFragment;", "Lmiuix/preference/PreferenceFragment;", "Landroidx/preference/Preference$OnPreferenceChangeListener;", "Landroidx/preference/Preference$OnPreferenceClickListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mMiCloudPref", "Lcom/miui/notes/preference/PreferenceMiCloud;", "mPrivacyPref", "Landroidx/preference/Preference;", "llmRegisterPref", "agiRegisterPref", "agreementRegisterPref", "mFontSizePref", "Lmiuix/preference/DropDownPreference;", "mNoteSortPref", "mRemindTypePref", "Landroidx/preference/CheckBoxPreference;", "getMRemindTypePref$app_PhoneGlobalRelease", "()Landroidx/preference/CheckBoxPreference;", "setMRemindTypePref$app_PhoneGlobalRelease", "(Landroidx/preference/CheckBoxPreference;)V", "mJumpToMiCloudTime", "", "toCtaBeforeCloud", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreatePreferences", "rootKey", "", "getPreferencesRes", "", "initPreferences", "styledFontSizeNames", "", "", "getStyledFontSizeNames", "()[Ljava/lang/CharSequence;", "onOptionsItemSelected", "", CloudPushConstants.XML_ITEM, "Landroid/view/MenuItem;", "onPreferenceChange", "preference", "newValue", "", "onPreferenceClick", "openCloudTrashBin", "openPrivacyPolicy", "openPrivacyGov", "openLLMReg", "openAGIReg", "openUserAgreement", "openDataSharingStatement", "toQuickNoteSetting", "toPermissionPreference", "toMiCloud", "fragmentViewCloud", "onDestroy", "initResultLauncher", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class AppSettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    protected static final String PREFERENCE_AGI_REG = "pref_key_agi_register";
    protected static final String PREFERENCE_AGREEMENT = "pref_user_agreement";
    protected static final String PREFERENCE_CLOUD_TRASH_BIN_KEY = "pref_key_cloud_trash_bin";
    protected static final String PREFERENCE_FONT_SIZE_KEY = "pref_key_font_size";
    protected static final String PREFERENCE_KEY_DATA_SHARING_STATEMENT = "pref_key_data_sharing_statement";
    protected static final String PREFERENCE_KEY_PERMISSION = "pref_key_permission";
    protected static final String PREFERENCE_LLM_REG = "pref_key_llm_register";
    protected static final String PREFERENCE_MICLOUD = "pref_micloud";
    protected static final String PREFERENCE_PRIVACY = "pref_key_privacy";
    protected static final String PREFERENCE_PRIVACY_POLICY = "privacy_policy";
    protected static final String PREFERENCE_QUICK_NOTE = "pref_quick_note";
    protected static final String PREFERENCE_QUICK_NOTE_CATEGORY = "pref_quick_note_category";
    protected static final String PREFERENCE_REMIND_TYPE = "pref_remind_type";
    protected static final String PREFERENCE_SKIP_CONFIRM_FOR_QUICK_DELETE_KEY = "pref_key_skip_confirm_for_quick_delete";
    protected static final String PRE_KEY_NOTE_SORT_WAY = "pref_key_note_sort_way";
    private static final String PRIVACY_AGI_URL_FORMAT = "aHR0cHM6Ly93d3cuY2FjLmdvdi5jbi8yMDI0LTA0LzAyL2NfMTcxMzcyOTk4MzgwMzE0NS5odG0=";
    private static final String PRIVACY_LLM_URL_FORMAT = "aHR0cHM6Ly9tcC53ZWl4aW4ucXEuY29tL3MvaTV5b3hYeU4ySlVGSjRvVDdFYWpvZw==";
    protected static final String PRIVACY_URL_FORMAT = "aHR0cHM6Ly9iZWlhbi5taWl0Lmdvdi5jbg";
    protected static final String RESTORE_HELP_URL_FORMAT = "https://i.mi.com/cloudservice/browse?url=https://i.mi.com/mobile/note/trash?_locale=%s";
    protected static final String TAG = "AppSettingsFragment";
    protected static final String USER_AGREEMENT_URL_FORMAT = "https://terms.miui.com/doc/AiWriting.html?lang=%s";
    private Preference agiRegisterPref;
    private Preference agreementRegisterPref;
    private Preference llmRegisterPref;
    private DropDownPreference mFontSizePref;
    private long mJumpToMiCloudTime;
    private PreferenceMiCloud mMiCloudPref;
    private DropDownPreference mNoteSortPref;
    private Preference mPrivacyPref;
    private CheckBoxPreference mRemindTypePref;
    private ActivityResultLauncher<Intent> toCtaBeforeCloud;

    public abstract int getPreferencesRes();

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    }

    /* JADX INFO: renamed from: getMRemindTypePref$app_PhoneGlobalRelease, reason: from getter */
    public final CheckBoxPreference getMRemindTypePref() {
        return this.mRemindTypePref;
    }

    public final void setMRemindTypePref$app_PhoneGlobalRelease(CheckBoxPreference checkBoxPreference) {
        this.mRemindTypePref = checkBoxPreference;
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(getPreferencesRes());
        initPreferences();
        setHasOptionsMenu(true);
        initResultLauncher();
    }

    protected void initPreferences() {
        Logger.INSTANCE.d(TAG, "initPreference,internationBuild:" + RomUtils.isInternationalBuild());
        DropDownPreference dropDownPreference = (DropDownPreference) findPreference(PREFERENCE_FONT_SIZE_KEY);
        this.mFontSizePref = dropDownPreference;
        Intrinsics.checkNotNull(dropDownPreference);
        AppSettingsFragment appSettingsFragment = this;
        dropDownPreference.setOnPreferenceChangeListener(appSettingsFragment);
        Preference preferenceFindPreference = findPreference(PREFERENCE_CLOUD_TRASH_BIN_KEY);
        if (preferenceFindPreference != null) {
            preferenceFindPreference.setOnPreferenceClickListener(this);
        }
        Preference preferenceFindPreference2 = findPreference(PREFERENCE_PRIVACY_POLICY);
        if (preferenceFindPreference2 != null) {
            preferenceFindPreference2.setOnPreferenceClickListener(this);
        }
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(PREFERENCE_REMIND_TYPE);
        this.mRemindTypePref = checkBoxPreference;
        if (checkBoxPreference != null) {
            checkBoxPreference.setOnPreferenceChangeListener(appSettingsFragment);
        }
        CheckBoxPreference checkBoxPreference2 = this.mRemindTypePref;
        if (checkBoxPreference2 != null) {
            checkBoxPreference2.setChecked(PreferenceUtils.getRemindType(BaseNoteApp.INSTANCE.getInstance()));
        }
        if (LiteUtils.isSuperLite()) {
            Preference preferenceFindPreference3 = findPreference(PREFERENCE_QUICK_NOTE_CATEGORY);
            if (preferenceFindPreference3 != null) {
                preferenceFindPreference3.setVisible(false);
            }
        } else {
            Preference preferenceFindPreference4 = findPreference(PREFERENCE_QUICK_NOTE);
            if (preferenceFindPreference4 != null) {
                preferenceFindPreference4.setOnPreferenceClickListener(this);
            }
        }
        Preference preferenceFindPreference5 = findPreference(PREFERENCE_KEY_DATA_SHARING_STATEMENT);
        if (preferenceFindPreference5 != null) {
            preferenceFindPreference5.setVisible(false);
        }
        Preference preferenceFindPreference6 = findPreference(PREFERENCE_KEY_PERMISSION);
        if (preferenceFindPreference6 != null) {
            preferenceFindPreference6.setOnPreferenceClickListener(this);
        }
        if (preferenceFindPreference6 != null) {
            preferenceFindPreference6.setVisible(PermissionUtils.isSupportPermissionDesc());
        }
        CharSequence[] styledFontSizeNames = getStyledFontSizeNames();
        int length = styledFontSizeNames.length;
        CharSequence[] charSequenceArr = new CharSequence[length];
        for (int i = 0; i < length; i++) {
            charSequenceArr[i] = String.valueOf(i);
        }
        int fontSize = PreferenceUtils.getFontSize(getActivity(), 1);
        DropDownPreference dropDownPreference2 = this.mFontSizePref;
        if (dropDownPreference2 != null) {
            dropDownPreference2.setEntries(styledFontSizeNames);
        }
        DropDownPreference dropDownPreference3 = this.mFontSizePref;
        if (dropDownPreference3 != null) {
            dropDownPreference3.setEntryValues(charSequenceArr);
        }
        DropDownPreference dropDownPreference4 = this.mFontSizePref;
        if (dropDownPreference4 != null) {
            dropDownPreference4.setValue(String.valueOf(fontSize));
        }
        DropDownPreference dropDownPreference5 = (DropDownPreference) findPreference(PRE_KEY_NOTE_SORT_WAY);
        this.mNoteSortPref = dropDownPreference5;
        if (dropDownPreference5 != null) {
            dropDownPreference5.setEntries(R.array.note_sort_way);
        }
        DropDownPreference dropDownPreference6 = this.mNoteSortPref;
        if (dropDownPreference6 != null) {
            dropDownPreference6.setEntryValues(NoteConfig.NOTE_SORT_ENTRY_VALUE);
        }
        DropDownPreference dropDownPreference7 = this.mNoteSortPref;
        if (dropDownPreference7 != null) {
            dropDownPreference7.setDefaultValue(1);
        }
        int noteSortWay = PreferenceUtils.getNoteSortWay(getActivity(), 1);
        DropDownPreference dropDownPreference8 = this.mNoteSortPref;
        if (dropDownPreference8 != null) {
            dropDownPreference8.setValue(String.valueOf(noteSortWay));
        }
        DropDownPreference dropDownPreference9 = this.mNoteSortPref;
        if (dropDownPreference9 != null) {
            dropDownPreference9.setOnPreferenceChangeListener(appSettingsFragment);
        }
        PreferenceMiCloud preferenceMiCloud = (PreferenceMiCloud) findPreference(PREFERENCE_MICLOUD);
        this.mMiCloudPref = preferenceMiCloud;
        if (preferenceMiCloud != null) {
            preferenceMiCloud.setOnPreferenceClickListener(this);
        }
        Preference preferenceFindPreference7 = findPreference(PREFERENCE_PRIVACY);
        this.mPrivacyPref = preferenceFindPreference7;
        if (preferenceFindPreference7 != null) {
            preferenceFindPreference7.setOnPreferenceClickListener(this);
        }
        Preference preferenceFindPreference8 = findPreference(PREFERENCE_LLM_REG);
        this.llmRegisterPref = preferenceFindPreference8;
        if (preferenceFindPreference8 != null) {
            preferenceFindPreference8.setOnPreferenceClickListener(this);
        }
        Preference preferenceFindPreference9 = findPreference(PREFERENCE_AGI_REG);
        this.agiRegisterPref = preferenceFindPreference9;
        if (preferenceFindPreference9 != null) {
            preferenceFindPreference9.setOnPreferenceClickListener(this);
        }
        Preference preferenceFindPreference10 = findPreference(PREFERENCE_AGREEMENT);
        this.agreementRegisterPref = preferenceFindPreference10;
        if (preferenceFindPreference10 != null) {
            preferenceFindPreference10.setOnPreferenceClickListener(this);
        }
        if (RomUtils.isInternationalBuild()) {
            Preference preference = this.mPrivacyPref;
            if (preference != null) {
                preference.setVisible(false);
            }
            Preference preference2 = this.llmRegisterPref;
            if (preference2 != null) {
                preference2.setVisible(false);
            }
            Preference preference3 = this.agiRegisterPref;
            if (preference3 != null) {
                preference3.setVisible(false);
            }
            Preference preference4 = this.agreementRegisterPref;
            if (preference4 != null) {
                preference4.setVisible(false);
            }
        }
    }

    private final CharSequence[] getStyledFontSizeNames() {
        String[] stringArray = getResources().getStringArray(R.array.font_size_names);
        Intrinsics.checkNotNullExpressionValue(stringArray, "getStringArray(...)");
        CharSequence[] charSequenceArr = new CharSequence[stringArray.length];
        int length = stringArray.length;
        for (int i = 0; i < length; i++) {
            charSequenceArr[i] = SpannableString.valueOf(stringArray[i]);
        }
        return charSequenceArr;
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == 16908332) {
            if (getListView() != null) {
                getListView().stopScroll();
            }
            requireActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        Logger.INSTANCE.d(TAG, "onPreferenceChange:" + preference.getKey() + "," + newValue);
        if (!isAdded()) {
            return true;
        }
        String key = preference.getKey();
        if (Intrinsics.areEqual(PREFERENCE_FONT_SIZE_KEY, key)) {
            Companion companion = INSTANCE;
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            if (companion.saveFontSize(fragmentActivityRequireActivity, Integer.parseInt(newValue.toString()))) {
                DropDownPreference dropDownPreference = this.mFontSizePref;
                Intrinsics.checkNotNull(dropDownPreference);
                dropDownPreference.setValue(newValue.toString());
            }
        } else if (Intrinsics.areEqual(PRE_KEY_NOTE_SORT_WAY, key)) {
            PreferenceUtils.setNoteSortWay(getContext(), Integer.parseInt(newValue.toString()));
            DropDownPreference dropDownPreference2 = this.mNoteSortPref;
            Intrinsics.checkNotNull(dropDownPreference2);
            dropDownPreference2.setValue(newValue.toString());
        } else if (Intrinsics.areEqual(PREFERENCE_REMIND_TYPE, key)) {
            PreferenceUtils.setRemindType(getContext(), ((Boolean) newValue).booleanValue());
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        Logger.INSTANCE.d(TAG, "onPreferenceClick-> " + preference.getKey());
        if (Intrinsics.areEqual(PREFERENCE_CLOUD_TRASH_BIN_KEY, preference.getKey())) {
            FragmentActivity activity = getActivity();
            if (PermissionUtils.supportNewPermissionStyle()) {
                if (!PreferenceUtils.getAllowNetwork(activity)) {
                    PermissionUtils.showNetworkDialog(activity, new PermissionUtils.AcceptListener() { // from class: com.miui.notes.ui.fragment.AppSettingsFragment.onPreferenceClick.1
                        @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                        public void accept() {
                            AppSettingsFragment.this.openCloudTrashBin();
                        }

                        @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                        public void reject() {
                            com.miui.notes.base.utils.Logger.INSTANCE.w(AppSettingsFragment.TAG, "user deny to authorization");
                        }
                    });
                } else {
                    openCloudTrashBin();
                }
            } else if (!CTAManager.getInstance().isAccepted(getContext())) {
                if (activity instanceof CTAActivity) {
                    ((CTAActivity) activity).showCtaDialog();
                    CTAManager.getInstance().addListener(new CTAManager.CTAListener() { // from class: com.miui.notes.ui.fragment.AppSettingsFragment.onPreferenceClick.2
                        @Override // com.miui.notes.cta.CTAManager.CTAListener
                        public void onAccept() {
                            AppSettingsFragment.this.openCloudTrashBin();
                        }

                        @Override // com.miui.notes.cta.CTAManager.CTAListener
                        public void onReject() {
                            com.miui.notes.base.utils.Logger.INSTANCE.w(AppSettingsFragment.TAG, "user deny to authorization");
                        }
                    });
                } else {
                    com.miui.notes.base.utils.Logger.INSTANCE.e(TAG, "get Activity is not instanceof CTAActivity");
                }
            } else {
                openCloudTrashBin();
            }
            return true;
        }
        if (Intrinsics.areEqual(PREFERENCE_PRIVACY_POLICY, preference.getKey())) {
            openPrivacyPolicy();
            return true;
        }
        if (Intrinsics.areEqual(PREFERENCE_QUICK_NOTE, preference.getKey())) {
            toQuickNoteSetting();
            return true;
        }
        if (Intrinsics.areEqual(PREFERENCE_KEY_PERMISSION, preference.getKey())) {
            toPermissionPreference();
            return true;
        }
        if (Intrinsics.areEqual(PREFERENCE_MICLOUD, preference.getKey())) {
            toMiCloud();
            return true;
        }
        if (Intrinsics.areEqual(PREFERENCE_PRIVACY, preference.getKey())) {
            openPrivacyGov();
            return true;
        }
        if (Intrinsics.areEqual(PREFERENCE_LLM_REG, preference.getKey())) {
            openAGIReg();
            return true;
        }
        if (Intrinsics.areEqual(PREFERENCE_AGI_REG, preference.getKey())) {
            openAGIReg();
            return true;
        }
        if (!Intrinsics.areEqual(PREFERENCE_AGREEMENT, preference.getKey())) {
            return false;
        }
        openUserAgreement();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openCloudTrashBin() {
        Logger.INSTANCE.d(TAG, "openCloudTrashBin");
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setPackage("com.miui.cloudservice");
        String string = getResources().getConfiguration().locale.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(RESTORE_HELP_URL_FORMAT, Arrays.copyOf(new Object[]{string}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        intent.setData(Uri.parse(str));
        startActivity(intent);
    }

    private final void openPrivacyPolicy() {
        String str;
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (RomUtils.isInternationalBuild()) {
            if (RomUtils.isSupportAi()) {
                str = "https://privacy.mi.com/notes-share-Global/" + language + "_" + country;
            } else {
                str = "https://privacy.mi.com/all/" + language + "_" + country;
            }
        } else {
            str = "https://privacy.mi.com/notes-share/" + language + "_" + country;
        }
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "Exception :" + e);
        }
    }

    private final void openPrivacyGov() {
        byte[] bArrDecode = Base64.decode(PRIVACY_URL_FORMAT, 0);
        Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
        String str = new String(bArrDecode, Charsets.UTF_8);
        if (str.length() == 0) {
            return;
        }
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "Exception :" + e);
        }
    }

    private final void openLLMReg() {
        byte[] bArrDecode = Base64.decode(PRIVACY_LLM_URL_FORMAT, 0);
        Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
        String str = new String(bArrDecode, Charsets.UTF_8);
        if (str.length() == 0) {
            return;
        }
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "Exception :" + e);
        }
    }

    private final void openAGIReg() {
        byte[] bArrDecode = Base64.decode(PRIVACY_AGI_URL_FORMAT, 0);
        Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
        String str = new String(bArrDecode, Charsets.UTF_8);
        if (str.length() == 0) {
            return;
        }
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "Exception :" + e);
        }
    }

    private final void openUserAgreement() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        String string = getResources().getConfiguration().locale.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(USER_AGREEMENT_URL_FORMAT, Arrays.copyOf(new Object[]{string}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        intent.setData(Uri.parse(str));
        try {
            startActivity(intent);
        } catch (Exception e) {
            com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "Exception :" + e);
        }
    }

    private final void openDataSharingStatement() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://privacy.mi.com/notes-share/" + Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry())));
        } catch (Exception e) {
            com.miui.notes.base.utils.Logger.INSTANCE.d(TAG, "Exception :" + e);
        }
    }

    protected void toQuickNoteSetting() {
        QuickNotePreferenceActivity.Companion companion = QuickNotePreferenceActivity.INSTANCE;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        companion.open(fragmentActivityRequireActivity);
    }

    protected void toPermissionPreference() {
        PermissionPreferenceActivity.open(getActivity());
    }

    private final void toMiCloud() {
        if (!PreferenceUtils.isCTAAccepted(getContext()) && PermissionUtils.supportNewPermissionStyle()) {
            PermissionNewUtils.showCtaDialog(this.toCtaBeforeCloud);
        } else {
            fragmentViewCloud();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fragmentViewCloud() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.mJumpToMiCloudTime < 300) {
            return;
        }
        this.mJumpToMiCloudTime = jCurrentTimeMillis;
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ExternalAppUtils.viewCloud(activity);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        PermissionUtils.releaseListeners();
        super.onDestroy();
    }

    private final void initResultLauncher() {
        this.toCtaBeforeCloud = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.ui.fragment.AppSettingsFragment.initResultLauncher.1
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                com.miui.notes.base.utils.Logger.INSTANCE.d(AppSettingsFragment.TAG, "toCtaBeforeCloud result:" + result.getResultCode());
                if (result.getResultCode() == 1) {
                    AppSettingsFragment.this.fragmentViewCloud();
                } else if (result.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(AppSettingsFragment.this.toCtaBeforeCloud);
                }
            }
        });
    }

    /* JADX INFO: compiled from: AppSettingsFragment.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0010\u0010 \u001a\u00020!2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/miui/notes/ui/fragment/AppSettingsFragment$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "PREFERENCE_FONT_SIZE_KEY", "", "PREFERENCE_SKIP_CONFIRM_FOR_QUICK_DELETE_KEY", "PREFERENCE_CLOUD_TRASH_BIN_KEY", "PREFERENCE_PRIVACY_POLICY", "PREFERENCE_REMIND_TYPE", "PREFERENCE_QUICK_NOTE", "PREFERENCE_QUICK_NOTE_CATEGORY", "PREFERENCE_KEY_PERMISSION", "PREFERENCE_KEY_DATA_SHARING_STATEMENT", "RESTORE_HELP_URL_FORMAT", "USER_AGREEMENT_URL_FORMAT", "PRIVACY_URL_FORMAT", "PRIVACY_LLM_URL_FORMAT", "PRIVACY_AGI_URL_FORMAT", "PRE_KEY_NOTE_SORT_WAY", "TAG", "PREFERENCE_MICLOUD", "PREFERENCE_PRIVACY", "PREFERENCE_AGREEMENT", "PREFERENCE_LLM_REG", "PREFERENCE_AGI_REG", "saveFontSize", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "newFontSizeId", "", "updateAllWidgets", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean saveFontSize(Context context, int newFontSizeId) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (newFontSizeId == PreferenceUtils.getFontSize(context, 1)) {
                return false;
            }
            PreferenceUtils.setFontSize(context, newFontSizeId);
            updateAllWidgets(context);
            return true;
        }

        private final void updateAllWidgets(Context context) {
            int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, (Class<?>) NoteWidgetProvider_2x2.class));
            if (appWidgetIds != null) {
                for (int i : appWidgetIds) {
                    UIUtils.updateWidget(BaseNoteApp.INSTANCE.getInstance(), i, 1);
                }
            }
            int[] appWidgetIds2 = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, (Class<?>) NoteWidgetProvider_4x2.class));
            if (appWidgetIds2 != null) {
                for (int i2 : appWidgetIds2) {
                    UIUtils.updateWidget(BaseNoteApp.INSTANCE.getInstance(), i2, 2);
                }
            }
            int[] appWidgetIds3 = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, (Class<?>) NoteWidgetProvider_4x4.class));
            if (appWidgetIds3 != null) {
                for (int i3 : appWidgetIds3) {
                    UIUtils.updateWidget(BaseNoteApp.INSTANCE.getInstance(), i3, 3);
                }
            }
            int[] appWidgetIds4 = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, (Class<?>) NoteWidgetProvider_8x4.class));
            if (appWidgetIds4 != null) {
                for (int i4 : appWidgetIds4) {
                    UIUtils.updateWidget(BaseNoteApp.INSTANCE.getInstance(), i4, 4);
                }
            }
        }
    }
}
