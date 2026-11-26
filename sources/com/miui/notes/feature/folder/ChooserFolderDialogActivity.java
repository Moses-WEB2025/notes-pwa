package com.miui.notes.feature.folder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.hardware.DataSpace;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.UIUtils;
import com.miui.notes.cta.CTAActivity;
import com.miui.notes.ui.IFragmentController;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ChooserFolderDialogActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\u0005H\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\r\u001a\u00020\nH\u0014J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016J\b\u0010\u0012\u001a\u00020\nH\u0014J\b\u0010\u0013\u001a\u00020\nH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/notes/feature/folder/ChooserFolderDialogActivity;", "Lcom/miui/notes/cta/CTAActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mController", "Lcom/miui/notes/ui/IFragmentController;", "isInFullWindowGestureMode", "", "createChooserFragmentController", "onCreate", "", "bundle", "Landroid/os/Bundle;", "onResume", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "onBackPressed", "onDestroy", "smallWindowAdapter", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ChooserFolderDialogActivity extends CTAActivity {
    public static final int CHOOSER_FOLDER_DIALOG_ACTIVITY_REQUEST_CODE = 1001;
    public static final String CHOOSER_FOLDER_ID = "chooser_folder_id";
    public static final String CHOOSER_FOLDER_SELECT_FOLDER = "chooser_folder_select_folder_name";
    public static final String CHOOSER_FOLDER_SELECT_FOLDER_ID = "chooser_folder_select_folder_id";
    public static final String CHOOSER_ITEM_IDS = "chooser_item_ids";
    public static final String CHOOSER_MOVE_NOTE_IDS = "chooser_move_note_ids";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "ChooserFolderDialogActivity";
    private boolean isInFullWindowGestureMode;
    private IFragmentController mController;

    private final IFragmentController createChooserFragmentController() {
        return new ChooserFolderDialogController(this);
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        smallWindowAdapter();
        IFragmentController iFragmentControllerCreateChooserFragmentController = createChooserFragmentController();
        this.mController = iFragmentControllerCreateChooserFragmentController;
        Intrinsics.checkNotNull(iFragmentControllerCreateChooserFragmentController);
        iFragmentControllerCreateChooserFragmentController.onCreate(bundle);
        this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(this);
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

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        UIUtils.checkOrientationInFoldDevice(this);
        smallWindowAdapter();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        getWindow().clearFlags(524288);
        IFragmentController iFragmentController = this.mController;
        if (iFragmentController != null) {
            Intrinsics.checkNotNull(iFragmentController);
            if (iFragmentController.onBackPressed()) {
                return;
            }
            super.onBackPressed();
        }
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        IFragmentController iFragmentController = this.mController;
        if (iFragmentController != null) {
            Intrinsics.checkNotNull(iFragmentController);
            iFragmentController.onDestroy();
        }
        super.onDestroy();
    }

    private final void smallWindowAdapter() {
        if (UIUtils.isSmallWindowMode(this)) {
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
    }

    /* JADX INFO: compiled from: ChooserFolderDialogActivity.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J2\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u000e\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017J2\u0010\u0018\u001a\u00020\u000e2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u000e\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/miui/notes/feature/folder/ChooserFolderDialogActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "CHOOSER_ITEM_IDS", "CHOOSER_FOLDER_ID", "CHOOSER_FOLDER_SELECT_FOLDER", "CHOOSER_FOLDER_SELECT_FOLDER_ID", "CHOOSER_MOVE_NOTE_IDS", "CHOOSER_FOLDER_DIALOG_ACTIVITY_REQUEST_CODE", "", "open", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "resultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "itemIds", "", "folderId", "", "openForResult", "fragment", "Landroidx/fragment/app/Fragment;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void open(Context context, ActivityResultLauncher<Intent> resultLauncher, long[] itemIds, long folderId) {
            Intrinsics.checkNotNullParameter(resultLauncher, "resultLauncher");
            if (context != null) {
                Intent intent = new Intent(context, (Class<?>) ChooserFolderDialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLongArray(ChooserFolderDialogActivity.CHOOSER_ITEM_IDS, itemIds);
                bundle.putLong(ChooserFolderDialogActivity.CHOOSER_FOLDER_ID, folderId);
                intent.putExtras(bundle);
                resultLauncher.launch(intent);
            }
        }

        public final void openForResult(Fragment fragment, ActivityResultLauncher<Intent> resultLauncher, long[] itemIds, long folderId) {
            Intrinsics.checkNotNullParameter(resultLauncher, "resultLauncher");
            if (fragment != null) {
                Intent intent = new Intent(fragment.getContext(), (Class<?>) ChooserFolderDialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLongArray(ChooserFolderDialogActivity.CHOOSER_ITEM_IDS, itemIds);
                bundle.putLong(ChooserFolderDialogActivity.CHOOSER_FOLDER_ID, folderId);
                intent.putExtras(bundle);
                resultLauncher.launch(intent);
            }
        }
    }
}
