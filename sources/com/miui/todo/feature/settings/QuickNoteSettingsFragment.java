package com.miui.todo.feature.settings;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.DualScreenManager;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.floatwindow.FloatService;
import com.miui.notes.R;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.AnimImagePreference;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miui.app.IFreeformCallback;
import miui.app.MiuiFreeFormManager;
import miui.cloud.CloudPushConstants;
import miuix.preference.PreferenceFragment;

/* JADX INFO: compiled from: QuickNoteSettingsFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000 <2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002;<B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\u001c\u0010\u0018\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0019\u001a\u0004\u0018\u00010\u0007H\u0016J\u0012\u0010\u001a\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u001f\u001a\u00020\u0014H\u0002J\b\u0010 \u001a\u00020\u0014H\u0016J\u0010\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020\u0014H\u0016J\b\u0010%\u001a\u00020\u0014H\u0016J\b\u0010&\u001a\u00020\u0014H\u0016J\u0010\u0010'\u001a\u00020\u00142\u0006\u0010(\u001a\u00020\u0016H\u0016J\u0018\u0010)\u001a\u00020\u00102\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\u00102\u0006\u0010*\u001a\u00020+H\u0016J\u0010\u0010/\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u0010H\u0002J\b\u00100\u001a\u00020\u0014H\u0002J\u0010\u00101\u001a\u00020\u00142\u0006\u00102\u001a\u000203H\u0016J\b\u00104\u001a\u00020\u0014H\u0002J\b\u00105\u001a\u00020\u0014H\u0002J\b\u00106\u001a\u00020\u0014H\u0002J\b\u00107\u001a\u00020\u0014H\u0002J\b\u00108\u001a\u00020\u0014H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001b\u001a\u00020\u001c8WX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u00109\u001a\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:¨\u0006="}, d2 = {"Lcom/miui/todo/feature/settings/QuickNoteSettingsFragment;", "Lmiuix/preference/PreferenceFragment;", "Landroidx/preference/Preference$OnPreferenceChangeListener;", "Landroidx/preference/Preference$OnPreferenceClickListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "tag", "", "mActiveDialog", "Landroid/app/Dialog;", "mFloatModePref", "Landroidx/preference/CheckBoxPreference;", "mExcerptLinkPref", "mQuickNotePref", "Lcom/miui/notes/ui/AnimImagePreference;", "mIsRequestStoragePermission", "", "mCallback", "Lmiui/app/IFreeformCallback;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "floatWindowModeChanged", "onCreatePreferences", "rootKey", "onActivityCreated", "preferencesRes", "", "getPreferencesRes", "()I", "initQuickNoteAnimFile", "onResume", "onOptionsItemSelected", CloudPushConstants.XML_ITEM, "Landroid/view/MenuItem;", "onStop", "onDestroy", "onDestroyView", "onSaveInstanceState", "outState", "onPreferenceChange", "preference", "Landroidx/preference/Preference;", "newValue", "", "onPreferenceClick", "updateExcerptPref", "updateFloatWindow", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "updateFloatPrefStatus", "startFloatService", "doStartFloatService", "stopExcerpt", "stopFloatService", "isFloatServiceRunning", "()Z", "FreeformCallbackStub", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class QuickNoteSettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static final String KEY_IS_REQUEST_STORAGE_PERM = "key_is_request_storage_perm";
    private static final String PREFERENCE_ENABLE_EXCERPT_LINK_FLOAT = "pref_enable_excerpt_link_float";
    private static final String PREFERENCE_ENABLE_FLOAT_MODE = "pref_enable_float_mode";
    private static final String PREFERENCE_QUICK_NOTE_GUIDE = "pref_quick_note_guide";
    private static final String TAG = "QuickNoteSettingsFragment";
    private static boolean isNeedUpdateFloat;
    private Dialog mActiveDialog;
    private IFreeformCallback mCallback;
    private CheckBoxPreference mExcerptLinkPref;
    private CheckBoxPreference mFloatModePref;
    private boolean mIsRequestStoragePermission;
    private AnimImagePreference mQuickNotePref;
    private final String tag = TAG;
    private final int preferencesRes = R.xml.quick_note_preferences;

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        return false;
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(getPreferencesRes());
        floatWindowModeChanged();
    }

    /* JADX INFO: compiled from: QuickNoteSettingsFragment.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lcom/miui/todo/feature/settings/QuickNoteSettingsFragment$FreeformCallbackStub;", "Lmiui/app/IFreeformCallback$Stub;", "fragment", "Lcom/miui/todo/feature/settings/QuickNoteSettingsFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/todo/feature/settings/QuickNoteSettingsFragment;)V", "weakReference", "Ljava/lang/ref/WeakReference;", "getWeakReference", "()Ljava/lang/ref/WeakReference;", "setWeakReference", "(Ljava/lang/ref/WeakReference;)V", "dispatchFreeFormStackModeChanged", "", "action", "", "miuiFreeFormStackInfo", "Lmiui/app/MiuiFreeFormManager$MiuiFreeFormStackInfo;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class FreeformCallbackStub extends IFreeformCallback.Stub {
        private WeakReference<QuickNoteSettingsFragment> weakReference;

        public FreeformCallbackStub(QuickNoteSettingsFragment quickNoteSettingsFragment) {
            this.weakReference = new WeakReference<>(quickNoteSettingsFragment);
        }

        public final WeakReference<QuickNoteSettingsFragment> getWeakReference() {
            return this.weakReference;
        }

        public final void setWeakReference(WeakReference<QuickNoteSettingsFragment> weakReference) {
            Intrinsics.checkNotNullParameter(weakReference, "<set-?>");
            this.weakReference = weakReference;
        }

        @Override // miui.app.IFreeformCallback
        public void dispatchFreeFormStackModeChanged(int action, MiuiFreeFormManager.MiuiFreeFormStackInfo miuiFreeFormStackInfo) throws RemoteException {
            if (action != 7 || this.weakReference.get() == null) {
                return;
            }
            QuickNoteSettingsFragment quickNoteSettingsFragment = this.weakReference.get();
            Intrinsics.checkNotNull(quickNoteSettingsFragment);
            quickNoteSettingsFragment.updateFloatPrefStatus();
        }
    }

    private final void floatWindowModeChanged() {
        if (MiuiFreeFormManager.getMiuiFreeformVersion() == 3) {
            FreeformCallbackStub freeformCallbackStub = new FreeformCallbackStub(this);
            this.mCallback = freeformCallbackStub;
            MiuiFreeFormManager.registerFreeformCallback(freeformCallbackStub);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference("pref_enable_float_mode");
        this.mFloatModePref = checkBoxPreference;
        Intrinsics.checkNotNull(checkBoxPreference);
        QuickNoteSettingsFragment quickNoteSettingsFragment = this;
        checkBoxPreference.setOnPreferenceChangeListener(quickNoteSettingsFragment);
        boolean z = false;
        boolean z2 = PreferenceUtils.getEnableFloatMode(getContext()) && isFloatServiceRunning();
        CheckBoxPreference checkBoxPreference2 = this.mFloatModePref;
        Intrinsics.checkNotNull(checkBoxPreference2);
        checkBoxPreference2.setChecked(z2);
        setHasOptionsMenu(true);
        CheckBoxPreference checkBoxPreference3 = (CheckBoxPreference) findPreference(PREFERENCE_ENABLE_EXCERPT_LINK_FLOAT);
        this.mExcerptLinkPref = checkBoxPreference3;
        Intrinsics.checkNotNull(checkBoxPreference3);
        checkBoxPreference3.setEnabled(z2);
        CheckBoxPreference checkBoxPreference4 = this.mExcerptLinkPref;
        Intrinsics.checkNotNull(checkBoxPreference4);
        CheckBoxPreference checkBoxPreference5 = this.mExcerptLinkPref;
        Intrinsics.checkNotNull(checkBoxPreference5);
        if (checkBoxPreference5.isEnabled() && PreferenceUtils.getEnableExcerptLinkFloat(BaseNoteApp.INSTANCE.getInstance())) {
            z = true;
        }
        checkBoxPreference4.setChecked(z);
        CheckBoxPreference checkBoxPreference6 = this.mExcerptLinkPref;
        Intrinsics.checkNotNull(checkBoxPreference6);
        checkBoxPreference6.setOnPreferenceChangeListener(quickNoteSettingsFragment);
        this.mQuickNotePref = (AnimImagePreference) findPreference(PREFERENCE_QUICK_NOTE_GUIDE);
        initQuickNoteAnimFile();
        if (savedInstanceState != null) {
            boolean z3 = savedInstanceState.getBoolean(KEY_IS_REQUEST_STORAGE_PERM, this.mIsRequestStoragePermission);
            this.mIsRequestStoragePermission = z3;
            if (z3) {
                PermissionUtils.restoreStoragePermListener(new PermissionUtils.ActivityAcceptListener(getActivity()) { // from class: com.miui.todo.feature.settings.QuickNoteSettingsFragment.onActivityCreated.1
                    {
                        super(fragmentActivity);
                    }

                    @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                    public void accept() {
                        CheckBoxPreference checkBoxPreference7 = QuickNoteSettingsFragment.this.mFloatModePref;
                        Intrinsics.checkNotNull(checkBoxPreference7);
                        checkBoxPreference7.setChecked(true);
                        QuickNoteSettingsFragment.this.updateExcerptPref(true);
                        PreferenceUtils.setEnableExcerptLinkFloat(QuickNoteSettingsFragment.this.getContext(), true);
                        QuickNoteSettingsFragment.this.mIsRequestStoragePermission = false;
                        QuickNoteSettingsFragment.this.startFloatService();
                    }

                    @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                    public void reject() {
                        CheckBoxPreference checkBoxPreference7 = QuickNoteSettingsFragment.this.mFloatModePref;
                        Intrinsics.checkNotNull(checkBoxPreference7);
                        checkBoxPreference7.setChecked(true);
                        QuickNoteSettingsFragment.this.mIsRequestStoragePermission = false;
                        QuickNoteSettingsFragment.this.updateExcerptPref(true);
                        PreferenceUtils.setEnableExcerptLinkFloat(QuickNoteSettingsFragment.this.getContext(), true);
                        QuickNoteSettingsFragment.this.startFloatService();
                        Toast.makeText(getActivity(), Utils.isAboveSDK33() ? R.string.toast_refuse_to_use_photos_in_excerpt : R.string.toast_refuse_to_use_storage_in_excerpt, 0).show();
                    }
                });
            }
        }
    }

    public int getPreferencesRes() {
        return this.preferencesRes;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0036  */
    /* JADX WARN: Code duplicated, block: B:14:0x0042  */
    /* JADX WARN: Code duplicated, block: B:15:0x0044  */
    private final void initQuickNoteAnimFile() {
        String str = "quick_note_guide_dark.json";
        if (RomUtils.isPadDevice()) {
            if (RomUtils.isFoldDevice()) {
                Logger.INSTANCE.d(this.tag, "initQuickNoteAnimFile,fold device");
                if (UIUtils.isInNarrowScreen(getContext())) {
                    if (!UIUtils.isNightMode(getContext())) {
                        str = "quick_note_guide.json";
                    }
                } else if (UIUtils.isNightMode(getContext())) {
                    str = "pad_quick_note_guide_dark.json";
                } else {
                    str = "pad_quick_note_guide.json";
                }
            } else if (!RomUtils.isPadMode()) {
                Logger.INSTANCE.d(this.tag, "initQuickNoteAnimFile,not pad mode");
                if (!UIUtils.isNightMode(getContext())) {
                    str = "quick_note_guide.json";
                }
            } else {
                Logger.INSTANCE.d(this.tag, "initQuickNoteAnimFile,pad mode");
                if (UIUtils.isNightMode(getContext())) {
                    str = "pad_quick_note_guide_dark.json";
                } else {
                    str = "pad_quick_note_guide.json";
                }
            }
        } else if (!UIUtils.isNightMode(getContext())) {
            str = "quick_note_guide.json";
        }
        AnimImagePreference animImagePreference = this.mQuickNotePref;
        Intrinsics.checkNotNull(animImagePreference);
        animImagePreference.setLottieAnimationJson(str);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        updateFloatPrefStatus();
        updateFloatWindow();
        if (!RomUtils.isPadDevice() || getActivity() == null) {
            return;
        }
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        UIUtils.checkOrientationInFoldDevice(fragmentActivityRequireActivity);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == 16908332) {
            requireActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        Dialog dialog = this.mActiveDialog;
        if (dialog != null) {
            Intrinsics.checkNotNull(dialog);
            if (dialog.isShowing()) {
                Dialog dialog2 = this.mActiveDialog;
                Intrinsics.checkNotNull(dialog2);
                dialog2.dismiss();
            }
            this.mActiveDialog = null;
        }
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        if (MiuiFreeFormManager.getMiuiFreeformVersion() == 3) {
            MiuiFreeFormManager.unregisterFreeformCallback(this.mCallback);
        }
        super.onDestroy();
    }

    @Override // miuix.preference.PreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        PermissionUtils.releaseListeners();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_REQUEST_STORAGE_PERM, this.mIsRequestStoragePermission);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, final Object newValue) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        if (!isAdded()) {
            return true;
        }
        String key = preference.getKey();
        if (Intrinsics.areEqual("pref_enable_float_mode", key)) {
            com.miui.notes.base.utils.Logger.INSTANCE.i(TAG, "float mode change :" + newValue);
            final FragmentActivity activity = getActivity();
            Boolean bool = (Boolean) newValue;
            if (bool.booleanValue()) {
                PreferenceUtils.setEnableFloatMode(getContext(), bool.booleanValue());
                try {
                    Context context = getContext();
                    Settings.Secure.putInt(context != null ? context.getContentResolver() : null, "notes_pref_enable_float_mode", ((Boolean) newValue).booleanValue() ? 1 : 0);
                } catch (Exception unused) {
                }
                this.mIsRequestStoragePermission = true;
                final FragmentActivity activity2 = getActivity();
                final PermissionUtils.ActivityAcceptListener activityAcceptListener = new PermissionUtils.ActivityAcceptListener(newValue, activity2) { // from class: com.miui.todo.feature.settings.QuickNoteSettingsFragment$onPreferenceChange$activityAcceptListener$1
                    final /* synthetic */ Object $newValue;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(activity2);
                    }

                    @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                    public void accept() {
                        CheckBoxPreference checkBoxPreference = this.this$0.mFloatModePref;
                        Intrinsics.checkNotNull(checkBoxPreference);
                        if (checkBoxPreference.isChecked()) {
                            if (Settings.canDrawOverlays(BaseNoteApp.INSTANCE.getInstance())) {
                                this.this$0.updateExcerptPref(((Boolean) this.$newValue).booleanValue());
                                PreferenceUtils.setEnableExcerptLinkFloat(this.this$0.getContext(), true);
                            }
                            this.this$0.mIsRequestStoragePermission = false;
                            this.this$0.startFloatService();
                        }
                    }

                    @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                    public void reject() {
                        CheckBoxPreference checkBoxPreference = this.this$0.mFloatModePref;
                        Intrinsics.checkNotNull(checkBoxPreference);
                        if (checkBoxPreference.isChecked()) {
                            this.this$0.updateExcerptPref(((Boolean) this.$newValue).booleanValue());
                            PreferenceUtils.setEnableExcerptLinkFloat(this.this$0.getContext(), true);
                            this.this$0.mIsRequestStoragePermission = false;
                            this.this$0.startFloatService();
                            Toast.makeText(getActivity(), Utils.isAboveSDK33() ? R.string.toast_refuse_to_use_photos_in_excerpt : R.string.toast_refuse_to_use_storage_in_excerpt, 0).show();
                        }
                    }
                };
                requireView().post(new Runnable() { // from class: com.miui.todo.feature.settings.QuickNoteSettingsFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        QuickNoteSettingsFragment.onPreferenceChange$lambda$1(activity, activityAcceptListener);
                    }
                });
            } else {
                updateExcerptPref(bool.booleanValue());
                PreferenceUtils.setEnableExcerptLinkFloat(getContext(), false);
                stopExcerpt();
                stopFloatService();
            }
        } else if (Intrinsics.areEqual(PREFERENCE_ENABLE_EXCERPT_LINK_FLOAT, key)) {
            Boolean bool2 = (Boolean) newValue;
            if (!bool2.booleanValue()) {
                stopExcerpt();
            }
            PreferenceUtils.setEnableExcerptLinkFloat(getContext(), bool2.booleanValue());
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onPreferenceChange$lambda$1(Activity activity, PermissionUtils.ActivityAcceptListener activityAcceptListener) {
        if (Utils.isAboveSDK33()) {
            Intrinsics.checkNotNull(activity);
            PermissionUtils.requestMediaImagesPermission(activity, activityAcceptListener);
        } else {
            Intrinsics.checkNotNull(activity);
            PermissionUtils.requestOnStoragePermission(activity, activityAcceptListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateExcerptPref(boolean newValue) {
        com.miui.notes.base.utils.Logger.INSTANCE.i(TAG, "updateExcerptPref:" + newValue);
        CheckBoxPreference checkBoxPreference = this.mExcerptLinkPref;
        Intrinsics.checkNotNull(checkBoxPreference);
        checkBoxPreference.setChecked(newValue);
        PreferenceUtils.setEnableFloatMode(getContext(), newValue);
        try {
            Context context = getContext();
            Settings.Secure.putInt(context != null ? context.getContentResolver() : null, "notes_pref_enable_float_mode", newValue ? 1 : 0);
        } catch (Exception unused) {
        }
        CheckBoxPreference checkBoxPreference2 = this.mExcerptLinkPref;
        Intrinsics.checkNotNull(checkBoxPreference2);
        checkBoxPreference2.setEnabled(newValue);
    }

    private final void updateFloatWindow() {
        com.miui.notes.base.utils.Logger.INSTANCE.i(TAG, "canDrawOverlays: " + Settings.canDrawOverlays(BaseNoteApp.INSTANCE.getInstance()) + " isNeedUpdateFloat :" + isNeedUpdateFloat);
        if (Settings.canDrawOverlays(BaseNoteApp.INSTANCE.getInstance())) {
            if (isNeedUpdateFloat) {
                CheckBoxPreference checkBoxPreference = this.mFloatModePref;
                Intrinsics.checkNotNull(checkBoxPreference);
                checkBoxPreference.setChecked(true);
                updateExcerptPref(true);
                doStartFloatService();
                isNeedUpdateFloat = false;
                return;
            }
            return;
        }
        if (isNeedUpdateFloat || PreferenceUtils.getEnableFloatMode(getContext())) {
            CheckBoxPreference checkBoxPreference2 = this.mFloatModePref;
            Intrinsics.checkNotNull(checkBoxPreference2);
            checkBoxPreference2.setChecked(false);
            updateExcerptPref(false);
        }
    }

    @Override // miuix.preference.PreferenceFragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        FragmentActivity activity;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        updateFloatPrefStatus();
        if (!RomUtils.isPadDevice() || (activity = getActivity()) == null) {
            return;
        }
        UIUtils.checkOrientationInFoldDevice(activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateFloatPrefStatus() {
        if (this.mFloatModePref == null) {
            return;
        }
        if (RomUtils.isPadDevice() || (RomUtils.isFoldDevice() && !UIUtils.isInNarrowScreen(getContext()))) {
            CheckBoxPreference checkBoxPreference = this.mFloatModePref;
            Intrinsics.checkNotNull(checkBoxPreference);
            checkBoxPreference.setEnabled(true);
            return;
        }
        boolean z = (DisplayUtils.getRealScreenOrientation(getContext()) == 2 || DualScreenManager.INSTANCE.isPcActivityLiving()) ? false : true;
        CheckBoxPreference checkBoxPreference2 = this.mFloatModePref;
        Intrinsics.checkNotNull(checkBoxPreference2);
        checkBoxPreference2.setEnabled(z);
        CheckBoxPreference checkBoxPreference3 = this.mExcerptLinkPref;
        Intrinsics.checkNotNull(checkBoxPreference3);
        checkBoxPreference3.setEnabled(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startFloatService() {
        if (!Settings.canDrawOverlays(BaseNoteApp.INSTANCE.getInstance())) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), getResources().getText(R.string.todo_float_permission_open_reminder), 1).show();
            startActivity(intent);
            isNeedUpdateFloat = true;
            return;
        }
        doStartFloatService();
    }

    /* JADX INFO: renamed from: com.miui.todo.feature.settings.QuickNoteSettingsFragment$doStartFloatService$2, reason: invalid class name */
    /* JADX INFO: compiled from: QuickNoteSettingsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.todo.feature.settings.QuickNoteSettingsFragment$doStartFloatService$2", f = "QuickNoteSettingsFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Utils.checkAndSwitchAutoStart();
            return Unit.INSTANCE;
        }
    }

    private final void doStartFloatService() {
        Context context = getContext();
        if (context != null) {
            FloatService.Companion companion = FloatService.INSTANCE;
            Context applicationContext = context.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            companion.startExcerptService(applicationContext, "QuickNoteSettingsFragment->doStartFloatService");
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass2(null), 3, null);
    }

    private final void stopExcerpt() {
        Intent intent = new Intent();
        intent.setAction(FloatService.ACTION_STOP_EXCERPT_SERVICE);
        intent.setPackage("com.miui.notes");
        BaseNoteApp.INSTANCE.getInstance().sendBroadcast(intent);
    }

    private final void stopFloatService() {
        Intent intent = new Intent();
        intent.setAction(FloatService.ACTION_STOP_FLOAT_SERVICE);
        intent.setPackage("com.miui.notes");
        BaseNoteApp.INSTANCE.getInstance().sendBroadcast(intent);
    }

    private final boolean isFloatServiceRunning() {
        Object systemService = requireActivity().getSystemService("activity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) systemService).getRunningServices(Integer.MAX_VALUE);
        if (runningServices.isEmpty()) {
            return false;
        }
        Iterator<ActivityManager.RunningServiceInfo> it = runningServices.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual("com.miui.floatwindow.FloatService", it.next().service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
