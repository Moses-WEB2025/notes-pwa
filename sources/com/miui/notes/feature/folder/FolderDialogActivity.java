package com.miui.notes.feature.folder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.activity.result.ActivityResultLauncher;
import androidx.hardware.DataSpace;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.cta.CTAActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FolderDialogActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\u0005H\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\fH\u0014J\b\u0010\u000f\u001a\u00020\nH\u0014J\b\u0010\u0010\u001a\u00020\nH\u0016J\b\u0010\u0011\u001a\u00020\nH\u0014J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\nH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/notes/feature/folder/FolderDialogActivity;", "Lcom/miui/notes/cta/CTAActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mController", "Lcom/miui/notes/feature/folder/FolderDialogController;", "isInFullWindowGestureMode", "", "createFragmentController", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onSaveInstanceState", "outState", "onResume", "onBackPressed", "onDestroy", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "smallWindowAdapter", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FolderDialogActivity extends CTAActivity {
    public static final String ACTION_TO_TRASH = "toTrash";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int FOLDER_DIALOG_ACTIVITY_REQUEST_CODE = 1000;
    public static final String FOLDER_LIST_SELECT_ID = "folder_list_select_id";
    private static final String TAG = "FolderDialogActivity";
    private boolean isInFullWindowGestureMode;
    private FolderDialogController mController;

    private final FolderDialogController createFragmentController() {
        return new FolderDialogController(this);
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smallWindowAdapter();
        FolderDialogController folderDialogControllerCreateFragmentController = createFragmentController();
        this.mController = folderDialogControllerCreateFragmentController;
        Intrinsics.checkNotNull(folderDialogControllerCreateFragmentController);
        folderDialogControllerCreateFragmentController.onCreate(savedInstanceState);
        this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(this);
        BaseHybridNoteEditFragment.INSTANCE.removeAiFloatingWindow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.cta.CTAActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        FolderDialogController folderDialogController = this.mController;
        if (folderDialogController != null) {
            Intrinsics.checkNotNull(folderDialogController);
            folderDialogController.onSaveInstanceState(outState);
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        smallWindowAdapter();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = 3;
        getWindow().setAttributes(attributes);
        UIUtils.checkOrientationInFoldDevice(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        getWindow().clearFlags(524288);
        FolderDialogController folderDialogController = this.mController;
        if (folderDialogController != null) {
            Intrinsics.checkNotNull(folderDialogController);
            if (folderDialogController.onBackPressed()) {
                return;
            }
            Logger.INSTANCE.d(TAG, "onBackPressed,activity consume");
            super.onBackPressed();
        }
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        FolderDialogController folderDialogController = this.mController;
        if (folderDialogController != null) {
            Intrinsics.checkNotNull(folderDialogController);
            folderDialogController.onDestroy();
        }
        super.onDestroy();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        smallWindowAdapter();
        UIUtils.checkOrientationInFoldDevice(this);
    }

    private final void smallWindowAdapter() {
        if (UIUtils.isSmallWindowMode(this)) {
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
    }

    /* JADX INFO: compiled from: FolderDialogActivity.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J(\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/notes/feature/folder/FolderDialogActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "FOLDER_DIALOG_ACTIVITY_REQUEST_CODE", "", "TAG", "", "FOLDER_LIST_SELECT_ID", "ACTION_TO_TRASH", "open", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "resultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "folderId", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void open(Context context, ActivityResultLauncher<Intent> resultLauncher, long folderId) {
            Intrinsics.checkNotNullParameter(resultLauncher, "resultLauncher");
            Logger.INSTANCE.d(FolderDialogActivity.TAG, "open FolderDialogActivity");
            Intent intent = new Intent(context, (Class<?>) FolderDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong(FolderDialogActivity.FOLDER_LIST_SELECT_ID, folderId);
            intent.putExtras(bundle);
            resultLauncher.launch(intent);
        }
    }
}
