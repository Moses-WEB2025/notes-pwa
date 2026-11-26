package com.miui.pad.feature.notes.multihandwrite;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.app.ShareViewModel;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.base.SafeBroadcastReceiver;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.RomUtils;
import com.miui.creation.taskservice.ITaskServiceCallback;
import com.miui.handwritecommon.api.util.FilePathUtils;
import com.miui.handwriting.common.MiuiHandWritingView;
import com.miui.handwriting.common.ai.AiCreationHelper;
import com.miui.handwriting.common.ai.AiCreationUtil;
import com.miui.handwriting.common.ai.anno.EventType;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.miui.handwriting.common.ai.event.LiveDataBus;
import com.miui.handwriting.common.ai.event.TransitionData;
import com.miui.handwriting.common.clipboard.BaseDragListener;
import com.miui.handwriting.common.clipboard.ClipManager;
import com.miui.handwriting.common.data.bean.BackgroundWrapper;
import com.miui.handwriting.common.data.bean.CommonPageBean;
import com.miui.handwriting.common.data.bean.CommonPageData;
import com.miui.handwriting.common.data.bean.CommonSearchWrapper;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.data.bean.SearchItemWrapper;
import com.miui.handwriting.common.data.dataset.CanvasBgSet;
import com.miui.handwriting.common.data.dataset.EstimateParamsSet;
import com.miui.handwriting.common.data.dataset.HistorySet;
import com.miui.handwriting.common.data.dataset.ShapeRecognizeParamsSet;
import com.miui.handwriting.common.engine.export.BitmapHolder;
import com.miui.handwriting.common.filemanager.PageFileManager;
import com.miui.handwriting.common.filemanager.base.ImportCallback;
import com.miui.handwriting.common.filemanager.strategy.MiuiImportStrategy;
import com.miui.handwriting.common.listener.CanvasElementTransactCallback;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPageStateListener;
import com.miui.handwriting.common.listener.ToolBoxActionCallBack;
import com.miui.handwriting.common.mask.CanvasElementTransactionMask;
import com.miui.handwriting.common.taskservice.CreationServiceManager;
import com.miui.handwriting.common.tools.BitmapUtil;
import com.miui.handwriting.common.tools.DisplaySizeUtil;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.handwriting.common.tools.MainThreadUtils;
import com.miui.handwriting.common.tools.RecognizeDataHelper;
import com.miui.handwriting.common.tools.ThreadPoolHelper;
import com.miui.handwriting.common.tools.TimeUtils;
import com.miui.handwriting.common.tools.UIUtils;
import com.miui.handwriting.common.tools.Utils;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.handwriting.common.view.custom.BaseFragment;
import com.miui.handwriting.common.view.custom.TitleBottomSheetDialogFragment;
import com.miui.handwriting.common.view.handview.HandWritingManager;
import com.miui.handwriting.common.view.page.HeaderView;
import com.miui.handwriting.common.view.popup.PasswordDialog;
import com.miui.handwriting.common.view.thumbnail.ExportImageManager;
import com.miui.libhandwritingpage.listener.PDFExportListener;
import com.miui.libhandwritingpage.listener.PdfThumbnailLoadListener;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import com.miui.libhandwritingpage.view.PageItemView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.home.ContentFragment;
import com.miui.notes.page.PadNoteListPageFragment;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.pad.feature.notes.handwrite.HandWriteWorkingNote;
import com.miui.pad.feature.notes.handwrite.NoteWholeRecognize;
import com.miui.pad.feature.notes.handwrite.TouchStylusBoostMode;
import com.miui.pad.feature.notes.share.ExportShareActivity;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.Fragment;
import miuix.appcompat.app.ProgressDialog;
import miuix.appcompat.widget.PopupMenu;
import miuix.core.util.IOUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.ICreationEngine;
import xc.engine.api.listener.DrawErrorCallback;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.engine.utils.FeatureManager;
import xc.engine.utils.FileUtil;

/* JADX INFO: loaded from: classes4.dex */
public class CreationEditFragment extends BaseFragment implements BaseFragment.OnInnerActionBarListener, ExportImageManager.ExportCallback, DialogManagerInterface {
    public static final int AREA_AI = 2000;
    public static final int AREA_AI_FOR_OS1 = 4000;
    public static final int AREA_AI_FROM_TEXT = 5000;
    private static final int DOUBLE_CLICK_GAP = 500;
    public static final int FULL_PAGE_AI = 1000;
    public static final int ID_INDEX = 0;
    public static final String INTENT_PARAM_PREVIEW_HEIGHT = "previewHeight";
    public static final String INTENT_PARAM_PREVIEW_WIDTH = "previewWidth";
    private static final int MSG_FRAGMENT_SAVE_MESSAGE_DELAY = 1;
    private static final int MSG_FRAGMENT_SAVE_PAUSE_MESSAGE = 0;
    public static final String NOTE_DATA = "note_data";
    private static final String NOTE_ID_RESTORE = "note_id_restore";
    public static final String PARAM_ENT_FILE_PATH = "ent_file_path";
    public static final int PATICIAL_AI = 3000;
    public static final String RECREATE_IMAGE_INFO = "recreate_image_info";
    private static final int SAVE_DELAY_TIME = 5000;
    private static final String TAG = "CreationEditFragment";
    public static final int TITLE_INDEX = 5;
    private Messenger activityMessenger;
    private Observer<Object> closeAIPageObserver;
    private String dirPath;
    private ProgressDialog exportPreviewDialog;
    private boolean fromPc;
    private MultiHandWriteEntity handWriteEntity;
    private String importBgImagePath;
    private String importPdfPath;
    private ProgressDialog importProgressDialog;
    private ActivityResultLauncher<Intent> launcher;
    private String local;
    private View mCreationEditFragmentLayout;
    private AlertDialog mCreationLimitDialog;
    private MiuiHandWritingView mHandWritingView;
    private List<Integer> mLastExportList;
    private long mNoteId;
    private PasswordController mPasswordController;
    private PasswordDialog mPasswordDialog;
    private PopupMenu mSharePopupMenu;
    private TaskServiceCallBack mTaskCallback;
    private HeaderView.OnTitleChangedListener mTitleChangeListener;
    private CanvasElementTransactionMask mTransactionMask;
    private OnBackPressedCallback onBackPressedCallback;
    private BroadcastReceiver operationReceiver;
    private PageFileManager pageFileManager;
    private Uri pcOutput;
    private BroadcastReceiver receiver;
    private ProgressDialog saveDialog;
    private SaveHandler saveHandler;
    private ShareViewModel shareViewModel;
    TitleBottomSheetDialogFragment titleBottomSheetDialogFragment;
    private Observer<Object> transitionFinishObserver;
    private String entFilePath = "";
    private boolean isFromImport = false;
    private boolean isFromImportImg = false;
    private boolean isFromSearch = false;
    private String searchContent = "";
    private String bgImage = null;
    private String imageFileTmpPath = null;
    private String mCreationTitle = "测试名称";
    private Long mCreationTime = 0L;
    int lastPageSize = 0;
    private boolean isInImport = false;
    private ScreenStatusReceiver mScreenStatusReceiver = new ScreenStatusReceiver(this);
    private boolean start = false;
    private int importPageCount = 0;
    private boolean isSaving = false;
    private boolean pcActiveSave = false;
    private boolean mNewPageFromAI = false;
    private int pdfPasswordErrCount = 0;
    private boolean isNightMode = false;
    private int mSearchPageIndex = 0;
    protected PasswordController.LockStateListener mLockStateListener = new PasswordController.SimpleLockStateListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.3
        @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
        public void onLockStateChanged(PasswordController passwordController, int i) {
            super.onLockStateChanged(passwordController, i);
            Log.d(CreationEditFragment.TAG, "lockState =  " + i);
            if (CreationEditFragment.this.mHandWritingView != null) {
                if (i == 0) {
                    CreationEditFragment.this.mHandWritingView.setVisibility(8);
                } else if (i == 2) {
                    CreationEditFragment.this.mHandWritingView.setVisibility(0);
                }
            }
        }
    };
    private Runnable showSaveDialogTask = new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.18
        @Override // java.lang.Runnable
        public void run() {
            FragmentActivity activity = CreationEditFragment.this.getActivity();
            if (activity == null) {
                return;
            }
            if (CreationEditFragment.this.saveDialog == null && CreationEditFragment.this.isAdded()) {
                CreationEditFragment.this.saveDialog = new ProgressDialog(activity);
                CreationEditFragment.this.saveDialog.setMessage(activity.getString(R.string.save_note_dialog));
                CreationEditFragment.this.saveDialog.setIndeterminate(true);
                CreationEditFragment.this.saveDialog.setCanceledOnTouchOutside(false);
                CreationEditFragment.this.saveDialog.setCancelable(false);
            }
            if (CreationEditFragment.this.saveDialog != null) {
                CreationEditFragment.this.saveDialog.show();
            }
        }
    };

    private void exportToPc() {
    }

    static /* synthetic */ void lambda$showLimitDialog$20(DialogInterface dialogInterface, int i) {
    }

    static /* synthetic */ void lambda$showLimitDialog$21(DialogInterface dialogInterface, int i) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPdfLimitToast(int i) {
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean dismissDialog() {
        return false;
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportThumbnailFinish(long j, ContentValues contentValues, boolean z) {
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Fragment getThisFragment() {
        return null;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean isDialogShowing() {
        return false;
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void loadPreviewFinish(int i) {
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public void manageDialog(Dialog dialog) {
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Activity getThisActivity() {
        return getActivity();
    }

    static class ScreenStatusReceiver extends SafeBroadcastReceiver<CreationEditFragment> {
        String SCREEN_OFF;

        public ScreenStatusReceiver(CreationEditFragment creationEditFragment) {
            super(creationEditFragment);
            this.SCREEN_OFF = "android.intent.action.SCREEN_OFF";
        }

        @Override // com.miui.common.base.SafeBroadcastReceiver, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            CreationEditFragment wkObject = getWkObject();
            if (wkObject == null) {
                return;
            }
            if (wkObject.start) {
                Log.d(CreationEditFragment.TAG, "onReceive: return");
            } else {
                if (!this.SCREEN_OFF.equals(intent.getAction()) || wkObject.getActivity() == null) {
                    return;
                }
                Log.d(CreationEditFragment.TAG, "onReceive remove flag FLAG_SHOW_WHEN_LOCKED");
                wkObject.getActivity().getWindow().clearFlags(524288);
            }
        }
    }

    private void registerScreenStatusReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(this.mScreenStatusReceiver, intentFilter);
    }

    private void registerCreationTaskReceiver() {
        this.operationReceiver = new BroadcastReceiver() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String uniqueFileName;
                Bundle extras = intent.getExtras();
                Log.d(CreationEditFragment.TAG, "LocalBroadcast onReceive: " + extras);
                if (TextUtils.equals(Constants.OPERATION_INTENT, intent.getAction())) {
                    Log.d(CreationEditFragment.TAG, "LocalBroadcast onReceive: " + extras);
                    if (extras != null) {
                        if (TextUtils.equals(extras.getString("operationType"), "distinguish")) {
                            extras.putString("dirPath", CreationEditFragment.this.dirPath);
                            CreationServiceManager.INSTANCE.dispatchTask(7, extras, CreationEditFragment.this.mTaskCallback);
                            return;
                        }
                        if (TextUtils.equals(extras.getString("operationType"), "classificationForAi")) {
                            CreationServiceManager.INSTANCE.dispatchTask(10, extras, CreationEditFragment.this.mTaskCallback);
                            return;
                        }
                        if (TextUtils.equals(extras.getString("operationType"), "export")) {
                            int i = extras.getInt("exportType");
                            String string = extras.getString("folderPath");
                            String string2 = extras.getString("folderName");
                            ArrayList<Integer> integerArrayList = extras.getIntegerArrayList("exportPageIndexList");
                            Log.d(CreationEditFragment.TAG, "export type:" + i + "----fragment:" + CreationEditFragment.this.hashCode());
                            if (integerArrayList == null || integerArrayList.isEmpty()) {
                                return;
                            }
                            if (i == 1) {
                                uniqueFileName = FileUtil.getUniqueFileName(string + InternalZipConstants.ZIP_FILE_SEPARATOR + string2);
                                ExportImageManager.INSTANCE.getInstance().startExportPdf(i, integerArrayList, uniqueFileName, CreationEditFragment.this);
                            } else {
                                uniqueFileName = string + InternalZipConstants.ZIP_FILE_SEPARATOR + FileUtil.getUniqueFolderName(string, string2);
                                ExportImageManager.INSTANCE.getInstance().startExportImage(i, integerArrayList, uniqueFileName, CreationEditFragment.this);
                            }
                            Log.d(CreationEditFragment.TAG, "folder path:" + string + "---folder name:" + string2 + "----out path:" + uniqueFileName);
                            return;
                        }
                        if (TextUtils.equals(extras.getString("operationType"), "exportToAI")) {
                            if (!CreationEditFragment.this.isResumed() && CreationEditFragment.this.mHandWritingView != null) {
                                CreationEditFragment.this.mHandWritingView.dismissAILoadingDialog();
                                return;
                            }
                            extras.putString("dirPath", CreationEditFragment.this.dirPath);
                            extras.putString("folderPath", Utils.getTempDir());
                            extras.putString("folderName", ContentFragment.ARG_PAGE);
                            extras.putString("entFilePath", CreationEditFragment.this.entFilePath);
                            extras.putBoolean("isDir", true);
                            extras.putInt("exportType", 2);
                            ArrayList<Integer> arrayList = new ArrayList<>();
                            arrayList.add(Integer.valueOf(CreationEditFragment.this.mHandWritingView.getCurrentPosition()));
                            extras.putIntegerArrayList("exportPageIndexList", arrayList);
                            CreationServiceManager.INSTANCE.dispatchTask(3, extras, CreationEditFragment.this.mTaskCallback);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (TextUtils.equals(Constants.ADJUST_PAGE_SIZE_INTENT, intent.getAction())) {
                    Log.d(CreationEditFragment.TAG, "ADJUST_PAGE_SIZE_INTENT " + extras);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(Constants.OPERATION_INTENT);
        intentFilter.addAction(Constants.ADJUST_PAGE_SIZE_INTENT);
        LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).registerReceiver(this.operationReceiver, intentFilter);
    }

    @Override // com.miui.handwriting.common.view.custom.BaseFragment.OnInnerActionBarListener
    public void onStartViewClick() {
        if (this.titleBottomSheetDialogFragment != null) {
            FragmentTransaction fragmentTransactionBeginTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransactionBeginTransaction.remove(this.titleBottomSheetDialogFragment);
            fragmentTransactionBeginTransaction.commit();
        }
    }

    @Override // com.miui.handwriting.common.view.custom.BaseFragment.OnInnerActionBarListener
    public void onEndViewClick(int i, int i2, int i3) {
        int i4;
        int i5;
        Log.d(TAG, "onEndViewClick pageHeight " + i + " ; pageWidth " + i2 + "; pageUnit " + i3);
        UIUtils.setFullScreenMode(getActivity(), true);
        int realWidth = UIUtils.getRealWidth(getContext().getApplicationContext()) * UIUtils.getRealHeight(getContext().getApplicationContext());
        if (realWidth <= 2073600) {
            i4 = 1440;
            i5 = 2560;
        } else if (realWidth <= 3686400) {
            i4 = 2160;
            i5 = 3840;
        } else {
            i4 = 3384;
            i5 = 6016;
        }
        Resources resources = BaseNoteApp.getInstance().getResources();
        if (i3 == 2) {
            int iPx2mm = (int) UIUtils.px2mm(BaseNoteApp.getInstance(), i5);
            int iPx2mm2 = (int) UIUtils.px2mm(BaseNoteApp.getInstance(), i4);
            if (i > iPx2mm) {
                Toast.makeText(BaseNoteApp.getInstance(), resources.getQuantityString(R.plurals.creation_page_size_custom_height_limit_toast_mm, iPx2mm, Integer.valueOf(iPx2mm)), 0).show();
                return;
            }
            if (i < ((int) UIUtils.px2mm(BaseNoteApp.getInstance(), 600.0f))) {
                Toast.makeText(BaseNoteApp.getInstance(), resources.getQuantityString(R.plurals.creation_page_size_custom_height_lower_limit_toast_mm, (int) UIUtils.px2mm(BaseNoteApp.getInstance(), 600.0f), Integer.valueOf((int) UIUtils.px2mm(BaseNoteApp.getInstance(), 600.0f))), 0).show();
                return;
            } else if (i2 > iPx2mm2) {
                Toast.makeText(BaseNoteApp.getInstance(), resources.getQuantityString(R.plurals.creation_page_size_custom_width_limit_toast_mm, iPx2mm2, Integer.valueOf(iPx2mm2)), 0).show();
                return;
            } else if (i2 < ((int) UIUtils.px2mm(BaseNoteApp.getInstance(), 600.0f))) {
                Toast.makeText(BaseNoteApp.getInstance(), resources.getQuantityString(R.plurals.creation_page_size_custom_width_lower_limit_toast_mm, (int) UIUtils.px2mm(BaseNoteApp.getInstance(), 600.0f), Integer.valueOf((int) UIUtils.px2mm(BaseNoteApp.getInstance(), 600.0f))), 0).show();
                return;
            }
        } else {
            if (i > i5) {
                Toast.makeText(BaseNoteApp.getInstance(), resources.getQuantityString(R.plurals.creation_page_size_custom_height_limit_toast_px, i5, Integer.valueOf(i5)), 0).show();
                return;
            }
            if (i < 600) {
                Toast.makeText(BaseNoteApp.getInstance(), resources.getQuantityString(R.plurals.creation_page_size_custom_height_lower_limit_toast_px, 600, 600), 0).show();
                return;
            } else if (i2 > i4) {
                Toast.makeText(BaseNoteApp.getInstance(), resources.getQuantityString(R.plurals.creation_page_size_custom_width_limit_toast_px, i4, Integer.valueOf(i4)), 0).show();
                return;
            } else if (i2 < 600) {
                Toast.makeText(BaseNoteApp.getInstance(), resources.getQuantityString(R.plurals.creation_page_size_custom_width_lower_limit_toast_px, 600, 600), 0).show();
                return;
            }
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.remove(this.titleBottomSheetDialogFragment);
        fragmentTransactionBeginTransaction.commit();
        this.mHandWritingView.adjustPageSize(i2, i, i3);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        HandWritingManager.INSTANCE.setViewType(0);
        setThemeRes(R.style.NoteTheme_List_NoTitle);
        this.shareViewModel = NoteApp.getInstance().getGlobalSharedViewModel();
        Intent intent = requireActivity().getIntent();
        Log.d(TAG, "onCreate: ");
        this.fromPc = false;
        this.pcActiveSave = false;
        boolean z = true;
        if (intent != null && TextUtils.equals(intent.getAction(), Constants.INSERT_CREATION_FROM_PC)) {
            this.fromPc = true;
            Bundle extras = intent.getExtras();
            if (extras != null) {
                this.pcOutput = (Uri) extras.getParcelable("output");
            }
            Log.d(TAG, "output:" + this.pcOutput);
        }
        this.onBackPressedCallback = new OnBackPressedCallback(z) { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.2
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                Log.d(CreationEditFragment.TAG, "handleOnBackPressed");
                CreationEditFragment.this.ensureMathResultSavable();
                CreationEditFragment.this.save();
            }
        };
        registerCreationTaskReceiver();
        requireActivity().getOnBackPressedDispatcher().addCallback(this.onBackPressedCallback);
        this.launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                CreationEditFragment.lambda$onCreate$0((ActivityResult) obj);
            }
        });
        this.mTaskCallback = new TaskServiceCallBack(this);
        if (PreferenceUtils.getIsInPrivate()) {
            initPasswordController();
        }
    }

    static /* synthetic */ void lambda$onCreate$0(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            Log.v(TAG, "Intent data = result.getData() " + activityResult.getData());
        }
    }

    protected void initPasswordController() {
        PasswordController passwordController = new PasswordController(this, false);
        this.mPasswordController = passwordController;
        passwordController.setLockSateListener(this.mLockStateListener, 2);
        getActivity().getWindow().addFlags(8192);
        PreferenceUtils.setIsInPrivateDetail(true);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        MiuiHandWritingView miuiHandWritingView;
        ICreationEngine creationEngine;
        super.onSaveInstanceState(bundle);
        Log.d(TAG, "onSaveInstanceState: ");
        if (!FeatureManager.getInstance().isEnableMathRecognition() || (miuiHandWritingView = this.mHandWritingView) == null || miuiHandWritingView.getCurrentItemView() == null || (creationEngine = this.mHandWritingView.getCurrentItemView().getCreationEngine()) == null || creationEngine.getCurveEngine() == null) {
            return;
        }
        creationEngine.getCurveEngine().getRecognitionModel().saveContent();
    }

    private void checkIsFromMishow(Intent intent) {
        if (intent != null ? intent.getExtras().getBoolean(Creation.FROM_MISHOW) : false) {
            this.mHandWritingView.enableAi(false);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            registerScreenStatusReceiver(activity);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.start = true;
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.unlock();
        }
        TouchStylusBoostMode.INSTANCE.getSInstance().setMode(1);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        this.mHandWritingView.onResume();
        if (this.mTitleChangeListener == null) {
            HeaderView.OnTitleChangedListener onTitleChangedListener = new HeaderView.OnTitleChangedListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda17
                @Override // com.miui.handwriting.common.view.page.HeaderView.OnTitleChangedListener
                public final void onTitleChanged(String str) {
                    this.f$0.lambda$onResume$1(str);
                }
            };
            this.mTitleChangeListener = onTitleChangedListener;
            this.mHandWritingView.setTitleChangedListener(onTitleChangedListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$1(String str) {
        this.mCreationTitle = str;
        this.handWriteEntity.setTitle(str);
        this.mHandWritingView.setTitleTime(System.currentTimeMillis());
        HandWriteViewManager.INSTANCE.saveToDB(false, this.handWriteEntity, this.mHandWritingView);
    }

    public void onWindowFocused() {
        MiuiHandWritingView miuiHandWritingView = this.mHandWritingView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.onWindowFocused();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.operationReceiver != null) {
            LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).unregisterReceiver(this.operationReceiver);
            this.operationReceiver = null;
        }
        ExportImageManager.INSTANCE.getInstance().unRegisterCallback(this);
        UIUtils.setFullScreenMode(getActivity(), false);
        dismissImportDialog();
        dismissSaveDialog();
        dismissExportPreviewDialog();
        OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.remove();
            this.onBackPressedCallback = null;
        }
        CreationServiceManager.INSTANCE.clearLastMessage();
        MiuiHandWritingView miuiHandWritingView = this.mHandWritingView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.setPageStateListener(null);
        }
        if (this.transitionFinishObserver != null) {
            LiveDataBus.INSTANCE.with(EventType.EVENT_SHARE_TRANSITION_FINISH).removeObserver(this.transitionFinishObserver);
            this.transitionFinishObserver = null;
        }
        if (this.closeAIPageObserver != null) {
            LiveDataBus.INSTANCE.with(EventType.EVENT_SHARE_CLOSE_AI_PAGE).removeObserver(this.closeAIPageObserver);
            this.closeAIPageObserver = null;
        }
        this.mTaskCallback = null;
        ExportImageManager.INSTANCE.getInstance().unRegisterCallback(this);
        if (getArguments() != null && getArguments().getBoolean(Creation.JUMP_IS_CREATE_NEW, false)) {
            PreferenceUtils.setPreferenceFirstCreateHandWrite(NoteApp.getInstance(), false);
        }
        if (getArguments() == null || !getArguments().getBoolean(Creation.JUMP_IS_CREATE_NEW, false) || requireActivity().isFinishing() || this.handWriteEntity == null) {
            return;
        }
        getArguments().putLong(NOTE_ID_RESTORE, this.handWriteEntity.id);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.start = false;
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.lock();
        }
        TouchStylusBoostMode.INSTANCE.getSInstance().setMode(0);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onInflateView: ");
        View viewInflate = layoutInflater.inflate(R.layout.fragment_creation_edit, viewGroup, false);
        this.mCreationEditFragmentLayout = viewInflate;
        viewInflate.setBackgroundColor(getResources().getColor(R.color.creation_action_bar_bg, null));
        this.mHandWritingView = (MiuiHandWritingView) this.mCreationEditFragmentLayout.findViewById(R.id.handwriting_view);
        this.mTransactionMask = (CanvasElementTransactionMask) this.mCreationEditFragmentLayout.findViewById(R.id.transaction_mask_iew);
        Log.d(TAG, "onInflateView: mHandWritingView=".concat(this.mHandWritingView != null ? "not null" : "null"));
        Log.d(TAG, "onInflateView: mTransactionMask=".concat(this.mTransactionMask == null ? "null" : "not null"));
        this.mHandWritingView.setActionCallBack(new ToolBoxActionCallBack() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.4
            @Override // com.miui.handwriting.common.listener.ToolBoxActionCallBack
            public void showGuide(Boolean bool) {
            }

            @Override // com.miui.handwriting.common.listener.ToolBoxActionCallBack
            public void callBackground(BackgroundWrapper backgroundWrapper) {
                CreationEditFragment.this.mHandWritingView.setPageBackground(CreationEditFragment.this.mHandWritingView.getCurrentPosition(), backgroundWrapper.colorIndex, backgroundWrapper.paperIndex);
            }

            @Override // com.miui.handwriting.common.listener.ToolBoxActionCallBack
            public void insertImage() {
                if (CreationPageSDK.sErrorCallback == null && CreationEditFragment.this.isAdded()) {
                    CreationEditFragment.this.initErrorCallback();
                }
                if (CreationEditFragment.this.pageFileManager != null) {
                    CreationEditFragment.this.pageFileManager.importImage();
                }
            }

            @Override // com.miui.handwriting.common.listener.ToolBoxActionCallBack
            public void clickThumbnail() {
                if (!CreationEditFragment.this.mHandWritingView.getHandWritingView().getThumbnailManager().getShow()) {
                    CreationEditFragment.this.mHandWritingView.getHandWritingView().getThumbnailManager().show(CreationEditFragment.this.mHandWritingView.getCurrentItemView().model.getCurrentPosition(), CreationEditFragment.this.mHandWritingView.getCurrentItemView().model.getPageCount());
                    return;
                }
                CreationEditFragment.this.mHandWritingView.getHandWritingView().getThumbnailManager().hide();
            }
        });
        this.mHandWritingView.initUIProperties();
        this.mHandWritingView.setFromPc(this.fromPc);
        this.mHandWritingView.setSaveClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onInflateView$2(view);
            }
        });
        this.mHandWritingView.setShareClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onInflateView$3(view);
            }
        });
        this.mHandWritingView.setCanvasElementTransactListener(new CanvasElementTransactCallback() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.5
            @Override // com.miui.handwriting.common.listener.CanvasElementTransactCallback
            public void onTransactCanvasElement(RectF rectF, CanvasMaskEntity canvasMaskEntity, float f, int i, String str, boolean z, String str2, boolean z2) {
                CreationEditFragment.this.mTransactionMask.removeChildrenIfExist();
                CreationEditFragment.this.mTransactionMask.setVisibility(0);
                if (z2) {
                    CreationEditFragment.this.animateToPortionPage(rectF, f, canvasMaskEntity, str, i, str2);
                } else {
                    CreationEditFragment.this.mTransactionMask.extractElementFromCanvas(rectF, Boolean.valueOf(z), canvasMaskEntity, new CanvasElementTransactionMask.Action() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.5.1
                        @Override // com.miui.handwriting.common.mask.CanvasElementTransactionMask.Action
                        public void run() {
                        }
                    });
                }
            }

            @Override // com.miui.handwriting.common.listener.CanvasElementTransactCallback
            public void onTransactCanvasElementFromText(RectF rectF, CanvasMaskEntity canvasMaskEntity, String str) {
                CreationEditFragment.this.mTransactionMask.setVisibility(0);
                CreationEditFragment.this.mTransactionMask.extractElementFromCanvas(rectF, false, canvasMaskEntity, new CanvasElementTransactionMask.Action() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.5.2
                    @Override // com.miui.handwriting.common.mask.CanvasElementTransactionMask.Action
                    public void run() {
                    }
                });
            }
        });
        this.isNightMode = UIUtils.isNightMode();
        this.local = UIUtils.getLocale(getContext());
        initListener();
        loadCreationFromArguments();
        initFileManager();
        startService();
        return this.mCreationEditFragmentLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInflateView$2(View view) {
        save();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInflateView$3(View view) {
        MiuiHandWritingView miuiHandWritingView = this.mHandWritingView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.calculateUnLimitExportArea();
        }
        if (this.fromPc) {
            exportToPc();
        } else {
            showShare(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateToPortionPage(final RectF rectF, final float f, final CanvasMaskEntity canvasMaskEntity, String str, int i, String str2) {
        Glide.with(this).load(str).into(new CustomTarget<Drawable>() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.6
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadCleared(Drawable drawable) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                onResourceReady((Drawable) obj, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                DisplaySizeUtil displaySizeUtil = new DisplaySizeUtil(CreationEditFragment.this.requireContext());
                Pair<Integer, Integer> pairRefreshImageSizeByScreen = displaySizeUtil.refreshImageSizeByScreen(CreationEditFragment.this.requireActivity(), displaySizeUtil.scaleToFixedDiagonalPartionSelect(intrinsicWidth, intrinsicHeight));
                RectF rectF2 = new RectF();
                int windowWidth = UIUtils.getWindowWidth(CreationEditFragment.this.requireContext());
                int windowHeight = UIUtils.getWindowHeight(CreationEditFragment.this.requireContext());
                rectF2.left = (windowWidth - pairRefreshImageSizeByScreen.getFirst().intValue()) / 2.0f;
                rectF2.top = (windowHeight - pairRefreshImageSizeByScreen.getSecond().intValue()) / 2.0f;
                rectF2.right = rectF2.left + pairRefreshImageSizeByScreen.getFirst().intValue();
                rectF2.bottom = rectF2.top + pairRefreshImageSizeByScreen.getSecond().intValue();
                Log.d(CreationEditFragment.TAG, "onResourceReady, get final portion img size: " + pairRefreshImageSizeByScreen + ", rotate: " + f);
                CreationEditFragment.this.mTransactionMask.setVisibility(0);
                CreationEditFragment.this.mTransactionMask.scalePreviewImage(rectF, rectF2, canvasMaskEntity, f, 0.0f, CreationEditFragment.this.getResources().getDimension(R.dimen.ai_create_part), true, new CanvasElementTransactionMask.Action() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.6.1
                    @Override // com.miui.handwriting.common.mask.CanvasElementTransactionMask.Action
                    public void run() {
                        if (CreationEditFragment.this.requireActivity().isFinishing()) {
                            return;
                        }
                        if (CreationEditFragment.this.mHandWritingView != null) {
                            CreationEditFragment.this.mHandWritingView.preventBackByAI();
                        }
                        CreationEditFragment.this.mTransactionMask.release();
                    }
                });
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        this.mHandWritingView.onPause();
        super.onPause();
        ensureMathResultSavable();
        asyncSave(true, -1);
        MiuiHandWritingView miuiHandWritingView = this.mHandWritingView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.dismissPopupWindow();
        }
        PopupMenu popupMenu = this.mSharePopupMenu;
        if (popupMenu != null) {
            popupMenu.dismiss();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        stopService();
        FileUtil.deleteDir(new File(FileUtil.getDir(requireContext(), FileUtil.TMP_DATA)));
        MiuiHandWritingView miuiHandWritingView = this.mHandWritingView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.release();
            this.mHandWritingView.setSaveClickListener(null);
            this.mHandWritingView.setShareClickListener(null);
            this.mHandWritingView.setTitleChangedListener(null);
            this.mHandWritingView = null;
        }
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.destroy();
            PreferenceUtils.setIsInPrivateDetail(false);
        }
        try {
            if (getActivity() != null) {
                getActivity().unregisterReceiver(this.mScreenStatusReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mCreationEditFragmentLayout = null;
        this.mTransactionMask = null;
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity != null && multiHandWriteEntity.isEmpty()) {
            MultiHandWriteDataUtils.deleteNote(this.mNoteId);
        }
        AlertDialog alertDialog = this.mCreationLimitDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mCreationLimitDialog.dismiss();
        }
        this.mCreationLimitDialog = null;
    }

    private void exportThumbnail(long j, String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("dirPath", this.dirPath);
        bundle.putString("filePath", str2);
        bundle.putLong("creationId", j);
        bundle.putString("thumbnailPath", str);
        CreationServiceManager.INSTANCE.dispatchTask(1, bundle, this.mTaskCallback);
    }

    public void save() {
        MiuiHandWritingView miuiHandWritingView;
        Log.d(TAG, "save: " + this.mHandWritingView.isContentChanged() + ",EntName:" + this.handWriteEntity.entName);
        if (getActivity() != null) {
            getActivity().getWindow().clearFlags(524288);
        }
        MiuiHandWritingView miuiHandWritingView2 = this.mHandWritingView;
        if (miuiHandWritingView2 == null || miuiHandWritingView2.isPreventBackByAI()) {
            return;
        }
        boolean z = false;
        if (this.handWriteEntity != null && (miuiHandWritingView = this.mHandWritingView) != null) {
            this.handWriteEntity.setCurrentPageNum(Math.max(0, Math.min(miuiHandWritingView.getCurrentPosition(), this.mHandWritingView.getPageCount() - 1)));
        }
        this.mHandWritingView.requestFocus();
        this.mHandWritingView.setSelectCopyDataToClip();
        if (this.isSaving) {
            HandWriteViewManager.INSTANCE.savePageItem(this.mHandWritingView, this.handWriteEntity);
            return;
        }
        this.isSaving = true;
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity != null && multiHandWriteEntity.isSuniaSignalPage()) {
            z = true;
        }
        if (!this.mHandWritingView.isContentChanged() && !z) {
            HandWriteViewManager.INSTANCE.savePageItem(this.mHandWritingView, this.handWriteEntity);
            backPage();
        } else {
            Log.d(TAG, "show save dialog " + isAdded());
            if (isAdded()) {
                showSaveDialog();
            }
            this.mHandWritingView.save(new CommonPageSaveListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.7
                @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
                public void onDataSaved(boolean z2, String str, boolean z3) {
                    Log.d(CreationEditFragment.TAG, "onDataSaved: isSuccess:" + z2 + ",Path:" + str);
                    if (!CreationEditFragment.this.isAdded() || CreationEditFragment.this.mHandWritingView == null) {
                        Log.w(CreationEditFragment.TAG, "onDataSaved: fragment destroyed, skip db update");
                        return;
                    }
                    if (CreationEditFragment.this.handWriteEntity.isSuniaSignalPage()) {
                        CreationEditFragment.this.handWriteEntity.setEntName(FilePathConstant.keepLastLevels(str, 2));
                        CreationEditFragment.this.handWriteEntity.setNoteType("multi_handwrite");
                        CreationEditFragment.this.handWriteEntity.setMindContent("");
                    }
                    if (CreationEditFragment.this.mHandWritingView.isContentEmpty()) {
                        CreationEditFragment.this.handWriteEntity.setEmpty(true);
                        if (HandWriteViewManager.INSTANCE.saveToDB(false, CreationEditFragment.this.handWriteEntity, CreationEditFragment.this.mHandWritingView)) {
                            NoteWholeRecognize.INSTANCE.saveMultiPageRecognizeResult(CreationEditFragment.this.handWriteEntity.id, CreationEditFragment.this.handWriteEntity.folderId, CreationEditFragment.this.handWriteEntity.observeEntDir());
                        }
                        CreationEditFragment.this.backPage();
                        return;
                    }
                    CreationEditFragment.this.handWriteEntity.setEmpty(false);
                    HandWriteViewManager.INSTANCE.saveToDB(true, CreationEditFragment.this.handWriteEntity, CreationEditFragment.this.mHandWritingView);
                    HandWriteViewManager.INSTANCE.savePageItem(CreationEditFragment.this.mHandWritingView, CreationEditFragment.this.handWriteEntity);
                    Bundle bundle = new Bundle();
                    bundle.putString("entFilePath", str);
                    bundle.putString("dirPath", CreationEditFragment.this.dirPath);
                    bundle.putLong("creationEntityId", CreationEditFragment.this.handWriteEntity.getId());
                    CreationServiceManager.INSTANCE.dispatchTask(11, bundle, CreationEditFragment.this.mTaskCallback);
                    CreationEditFragment.this.backPage();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void asyncSave(boolean z, int i) {
        SaveHandler saveHandler = this.saveHandler;
        if (saveHandler == null) {
            return;
        }
        if (z) {
            if (saveHandler.hasMessages(0)) {
                return;
            }
            if (this.saveHandler.hasMessages(1)) {
                Log.d(TAG, "asyncSave: onPause remove SAVE_MESSAGE_DELAY");
                this.saveHandler.removeMessages(1);
            }
            this.saveHandler.sendEmptyMessage(0);
            return;
        }
        if (saveHandler.hasMessages(1)) {
            Log.d(TAG, "asyncSave: onContentChange remove SAVE_MESSAGE_DELAY");
            this.saveHandler.removeMessages(1);
        }
        SaveHandler saveHandler2 = this.saveHandler;
        saveHandler2.sendMessageDelayed(saveHandler2.obtainMessage(1, Integer.valueOf(i)), 5000L);
    }

    public void autoSave(boolean z) {
        MiuiHandWritingView miuiHandWritingView;
        Log.i(TAG, "autoSave: " + z + " isSaving " + this.isSaving);
        if (this.isSaving || this.pdfPasswordErrCount != 0 || (miuiHandWritingView = this.mHandWritingView) == null) {
            return;
        }
        if (!miuiHandWritingView.isContentChanged()) {
            Log.i(TAG, "autoSave: No ContentChanged");
            return;
        }
        if (this.mHandWritingView.isContentEmpty()) {
            this.handWriteEntity.setEmpty(true);
            HandWriteViewManager.INSTANCE.saveToDB(false, this.handWriteEntity, this.mHandWritingView);
            return;
        }
        this.handWriteEntity.setEmpty(false);
        HandWriteViewManager.INSTANCE.saveToDB(true, this.handWriteEntity, this.mHandWritingView);
        this.handWriteEntity.setModifiedTime(System.currentTimeMillis());
        this.mHandWritingView.post(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.8
            @Override // java.lang.Runnable
            public void run() {
                if (CreationEditFragment.this.mHandWritingView != null) {
                    CreationEditFragment.this.mHandWritingView.setTitleAndTime(CreationEditFragment.this.handWriteEntity.getTitle(), CreationEditFragment.this.handWriteEntity.getModifiedTime());
                }
            }
        });
        this.mHandWritingView.save(new CommonPageSaveListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.9
            @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
            public void onDataSaved(boolean z2, String str, boolean z3) {
            }
        });
    }

    private void updatePageImage(MultiHandWriteEntity multiHandWriteEntity) {
        List<CommonPageData> pageDatas = this.mHandWritingView.getPageDatas();
        ArrayList arrayList = new ArrayList();
        for (CommonPageData commonPageData : pageDatas) {
            if (!arrayList.isEmpty()) {
            }
        }
    }

    private void initListener() {
        this.mHandWritingView.setPageStateListener(new CommonPageStateListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.10
            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void dataLoadFinish() {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onStepChanged(int i, int i2, boolean z, boolean z2, boolean z3) {
                CreationEditFragment.this.mHandWritingView.onStepChanged(i, i2, z, z2, z3);
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onContentChanged(int i) {
                CreationEditFragment.this.asyncSave(false, i);
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onDoubleUndo() {
                CreationEditFragment.this.mHandWritingView.onDoubleUndo();
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onPageScale(float f) {
                CreationEditFragment.this.mHandWritingView.updateBackgroundMargin(f);
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onPageLoadingFinish() {
                Log.d(CreationEditFragment.TAG, "onPageLoadingFinish: ");
                if (CreationEditFragment.this.mHandWritingView != null) {
                    CreationEditFragment.this.mHandWritingView.setShareIconState(true);
                }
            }
        });
        initErrorCallback();
        this.mHandWritingView.setOnDragListener(new BaseDragListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.11
            @Override // com.miui.handwriting.common.clipboard.BaseDragListener, android.view.View.OnDragListener
            public boolean onDrag(View view, DragEvent dragEvent) {
                return super.onDrag(view, dragEvent);
            }

            @Override // com.miui.handwriting.common.clipboard.BaseDragListener
            protected boolean needHandleEvent(DragEvent dragEvent) {
                return super.needHandleEvent(dragEvent);
            }

            @Override // com.miui.handwriting.common.clipboard.BaseDragListener
            protected boolean handleDragStarted(View view, DragEvent dragEvent) {
                return super.handleDragStarted(view, dragEvent);
            }

            @Override // com.miui.handwriting.common.clipboard.BaseDragListener
            protected boolean handleDragEntered(View view, DragEvent dragEvent) {
                return super.handleDragEntered(view, dragEvent);
            }

            @Override // com.miui.handwriting.common.clipboard.BaseDragListener
            protected void handleDragLocation(View view, DragEvent dragEvent) {
                super.handleDragLocation(view, dragEvent);
            }

            @Override // com.miui.handwriting.common.clipboard.BaseDragListener
            protected boolean handleDragDrop(View view, DragEvent dragEvent) {
                int toolBoxHeight = CreationEditFragment.this.mHandWritingView.getToolBoxHeight();
                float x = dragEvent.getX();
                float y = dragEvent.getY() - toolBoxHeight;
                return ClipManager.INSTANCE.getImageUrisFromDragEventInFragment(CreationEditFragment.this.requireActivity(), dragEvent, new AnonymousClass1(y <= 0.0f, x, y));
            }

            /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$11$1, reason: invalid class name */
            class AnonymousClass1 implements ClipManager.UriGetCallback {
                final /* synthetic */ float val$dragX;
                final /* synthetic */ float val$dragY;
                final /* synthetic */ boolean val$isInsertCenter;

                static /* synthetic */ void lambda$onException$2() {
                }

                AnonymousClass1(boolean z, float f, float f2) {
                    this.val$isInsertCenter = z;
                    this.val$dragX = f;
                    this.val$dragY = f2;
                }

                @Override // com.miui.handwriting.common.clipboard.ClipManager.UriGetCallback
                public void onGetUri(final ArrayList<Uri> arrayList) {
                    if (arrayList.size() > 0) {
                        ThreadPoolHelper threadPoolHelper = ThreadPoolHelper.getInstance();
                        final boolean z = this.val$isInsertCenter;
                        final float f = this.val$dragX;
                        final float f2 = this.val$dragY;
                        threadPoolHelper.execute(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$11$1$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$onGetUri$1(arrayList, z, f, f2);
                            }
                        });
                    }
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onGetUri$1(final ArrayList arrayList, final boolean z, final float f, final float f2) {
                    final String dragPath = ClipManager.getDragPath(CreationEditFragment.this.requireContext(), Uri.parse(String.valueOf(arrayList.get(0))));
                    MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$11$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onGetUri$0(z, dragPath, f, f2, arrayList);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onGetUri$0(boolean z, String str, float f, float f2, ArrayList arrayList) {
                    CreationEditFragment.this.mHandWritingView.enableViewSelect();
                    if (z) {
                        CreationEditFragment.this.mHandWritingView.insertImageCenter(str);
                    } else {
                        CreationEditFragment.this.mHandWritingView.insertImagePosition(str, f, f2);
                    }
                    arrayList.size();
                }

                @Override // com.miui.handwriting.common.clipboard.ClipManager.UriGetCallback
                public void onException(String str) {
                    MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$11$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CreationEditFragment.AnonymousClass11.AnonymousClass1.lambda$onException$2();
                        }
                    });
                }
            }

            @Override // com.miui.handwriting.common.clipboard.BaseDragListener
            protected void handleDragExit(View view, DragEvent dragEvent) {
                super.handleDragExit(view, dragEvent);
            }

            @Override // com.miui.handwriting.common.clipboard.BaseDragListener
            protected boolean handleDragEnd(View view, DragEvent dragEvent) {
                return super.handleDragEnd(view, dragEvent);
            }
        });
        this.transitionFinishObserver = new Observer<Object>() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.12
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object obj) {
                Log.d(CreationEditFragment.TAG, "onChanged, need close bg: " + obj);
                if (CreationEditFragment.this.mTransactionMask != null) {
                    CreationEditFragment.this.mTransactionMask.clearBackgroundWithAnim();
                }
            }
        };
        this.closeAIPageObserver = new Observer<Object>() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.13
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object obj) {
                TransitionData transitionData;
                RectF transactRect;
                Log.d(CreationEditFragment.TAG, "before share element anim start: " + obj);
                if (CreationEditFragment.this.mTransactionMask == null || (transitionData = (TransitionData) obj) == null) {
                    return;
                }
                if (CreationEditFragment.this.mHandWritingView != null && BitmapHolder.INSTANCE.getIsFromText()) {
                    AiCreationHelper aiCreationHelper = (CreationEditFragment.this.mHandWritingView == null || CreationEditFragment.this.mHandWritingView.getHandWritingView() == null) ? null : CreationEditFragment.this.mHandWritingView.getHandWritingView().getAiCreationHelper();
                    if (aiCreationHelper == null) {
                        return;
                    }
                    aiCreationHelper.calculateAiImageRectForText();
                    float dimension = CreationEditFragment.this.getContext() != null ? CreationEditFragment.this.getContext().getResources().getDimension(R.dimen.ai_from_text_margin) * CreationEditFragment.this.mHandWritingView.getScale() : 0.0f;
                    int toolBoxHeight = aiCreationHelper.getToolBoxHeight();
                    if (CreationEditFragment.this.mHandWritingView.isUnlimited()) {
                        AiCreationUtil.adjustAiInsertAreaForUnLimit(CreationEditFragment.this.mHandWritingView.getCurrentItemView(), BitmapHolder.INSTANCE.getTransactRect(), dimension, toolBoxHeight);
                    } else {
                        RectF transactRect2 = BitmapHolder.INSTANCE.getTransactRect();
                        RectF insertRectFForLimit = BitmapHolder.INSTANCE.getInsertRectFForLimit();
                        if (!BitmapHolder.INSTANCE.getIsOverLimitPageNum()) {
                            if (BitmapHolder.INSTANCE.getIsNeedNewPage()) {
                                AiCreationUtil.adjustAiInsertAreaForNewPage(CreationEditFragment.this.mHandWritingView.getCurrentItemView(), transactRect2, insertRectFForLimit, BitmapHolder.INSTANCE.getImageHeight(), dimension, CreationEditFragment.this.getContext() != null ? CreationEditFragment.this.getContext().getResources().getDimensionPixelSize(R.dimen.creation_page_spacing) * CreationEditFragment.this.mHandWritingView.getScale() : 0.0f);
                            } else {
                                AiCreationUtil.adjustAiInsertAreaForLimit(CreationEditFragment.this.mHandWritingView.getCurrentItemView(), transactRect2, insertRectFForLimit, dimension, toolBoxHeight);
                            }
                        }
                    }
                    CreationEditFragment.this.mHandWritingView.finishSelect();
                    CreationEditFragment.this.mTransactionMask.releaseCanvasMaskView();
                    int offsetX = BitmapHolder.INSTANCE.getOffsetX();
                    int offsetY = BitmapHolder.INSTANCE.getOffsetY();
                    Log.e(CreationEditFragment.TAG, "onChanged: offsetX -> " + offsetX + ", offsetY -> " + offsetY + ", needNewPage -> " + BitmapHolder.INSTANCE.getIsNeedNewPage());
                    if (BitmapHolder.INSTANCE.getIsNeedNewPage() && BitmapHolder.INSTANCE.getNewPageIndex() >= 0 && BitmapHolder.INSTANCE.getNewPageIndex() <= CreationEditFragment.this.mHandWritingView.getPageCount() + 1 && BitmapHolder.INSTANCE.getNewPageWidth() > 0 && BitmapHolder.INSTANCE.getNewPageHeight() > 0) {
                        CreationEditFragment.this.mHandWritingView.insertPage(BitmapHolder.INSTANCE.getNewPageIndex(), BitmapHolder.INSTANCE.getNewPageWidth(), BitmapHolder.INSTANCE.getNewPageHeight(), false);
                    }
                    if (CreationEditFragment.this.mHandWritingView.isUnlimited() && (transactRect = BitmapHolder.INSTANCE.getTransactRect()) != null && !transactRect.isEmpty()) {
                        float f = offsetX;
                        float f2 = transactRect.left - f;
                        float f3 = transactRect.right - f;
                        float f4 = offsetY;
                        float f5 = transactRect.top - f4;
                        float f6 = transactRect.bottom - f4;
                        CreationEditFragment.this.mHandWritingView.expanseUnLimitedPage(f2, f5, true);
                        CreationEditFragment.this.mHandWritingView.expanseUnLimitedPage(f3, f6, true);
                    }
                    if (offsetX != 0 || offsetY != 0) {
                        CreationEditFragment.this.mHandWritingView.aiScrollBy(offsetX, offsetY);
                    }
                }
                BitmapHolder.INSTANCE.setIsFromText(false);
                BitmapHolder.INSTANCE.setOffsetX(0);
                BitmapHolder.INSTANCE.setOffsetY(0);
                BitmapHolder.INSTANCE.setNewPageWidth(0);
                BitmapHolder.INSTANCE.setNewPageHeight(0);
                BitmapHolder.INSTANCE.setImageWidth(0.0f);
                BitmapHolder.INSTANCE.setImageHeight(0.0f);
                CreationEditFragment.this.imageFileTmpPath = transitionData.getOutputPath();
                final String outputPath = transitionData.getOutputPath();
                if (CreationEditFragment.this.mHandWritingView != null) {
                    CreationEditFragment.this.mHandWritingView.preventBackByAI();
                }
                CreationEditFragment.this.getView().postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.13.1
                    @Override // java.lang.Runnable
                    public void run() {
                        RectF insertRectFForLimit2;
                        if (CreationEditFragment.this.mHandWritingView.isUnlimited()) {
                            insertRectFForLimit2 = BitmapHolder.INSTANCE.getTransactRect();
                        } else {
                            insertRectFForLimit2 = BitmapHolder.INSTANCE.getInsertRectFForLimit();
                        }
                        if (insertRectFForLimit2 == null) {
                            insertRectFForLimit2 = BitmapHolder.INSTANCE.getBitmapReactF();
                        }
                        Log.d(CreationEditFragment.TAG, "before ai creation page closed, area selection dstRect: " + insertRectFForLimit2);
                        if (insertRectFForLimit2 == null) {
                            Log.i(CreationEditFragment.TAG, "close ai page back, getRectF fail:");
                            insertRectFForLimit2 = CreationEditFragment.this.mHandWritingView.getHandWritingView().getSelectAiRect();
                        }
                        RectF rectFAdaptForInsertLocationFromAIImage = CreationEditFragment.this.adaptForInsertLocationFromAIImage(insertRectFForLimit2);
                        if (rectFAdaptForInsertLocationFromAIImage == null) {
                            return;
                        }
                        Log.d(CreationEditFragment.TAG, "final dstRect: " + insertRectFForLimit2);
                        CreationEditFragment.this.mTransactionMask.displayGhostViewForShareElementAnim(rectFAdaptForInsertLocationFromAIImage, outputPath);
                    }
                }, 20L);
            }
        };
        LiveDataBus.INSTANCE.with(EventType.EVENT_SHARE_TRANSITION_FINISH).observe(getViewLifecycleOwner(), this.transitionFinishObserver);
        LiveDataBus.INSTANCE.with(EventType.EVENT_SHARE_CLOSE_AI_PAGE).observe(getViewLifecycleOwner(), this.closeAIPageObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createPageEntSave() {
        Schedulers.io().scheduleDirect(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.14
            @Override // java.lang.Runnable
            public void run() {
                try {
                    CreationEditFragment.this.ensureMathResultSavable();
                    if (CreationEditFragment.this.mHandWritingView != null) {
                        CreationEditFragment.this.handWriteEntity.setEmpty(false);
                        HandWriteViewManager.INSTANCE.saveToDB(true, CreationEditFragment.this.handWriteEntity, CreationEditFragment.this.mHandWritingView);
                        CreationEditFragment.this.handWriteEntity.setModifiedTime(System.currentTimeMillis());
                        CreationEditFragment.this.mHandWritingView.post(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.14.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CreationEditFragment.this.mHandWritingView.setTitleAndTime(CreationEditFragment.this.handWriteEntity.getTitle(), CreationEditFragment.this.handWriteEntity.getModifiedTime());
                            }
                        });
                        CreationEditFragment.this.mHandWritingView.save(new CommonPageSaveListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.14.2
                            @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
                            public void onDataSaved(boolean z, String str, boolean z2) {
                                Log.d(CreationEditFragment.TAG, "createPageSave: " + z);
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.e(CreationEditFragment.TAG, "CreatePageSave err is " + e);
                }
            }
        });
    }

    private void showShare(View view) {
        if (Utils.isFastClick()) {
            return;
        }
        if (this.mHandWritingView.isContentEmpty()) {
            Toast.makeText(getContext(), R.string.error_send_empty_note, 0).show();
        } else {
            showExportPreviewDialog();
            this.mHandWritingView.saveEntFile(new AnonymousClass15());
        }
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$15, reason: invalid class name */
    class AnonymousClass15 implements CommonPageSaveListener {
        AnonymousClass15() {
        }

        @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
        public void onDataSaved(boolean z, final String str, boolean z2) {
            if (z) {
                int pageCount = CreationEditFragment.this.mHandWritingView.getPageCount();
                final ArrayList arrayList = new ArrayList();
                for (int i = 0; i < pageCount; i++) {
                    arrayList.add(Integer.valueOf(i));
                }
                if (CreationEditFragment.this.mHandWritingView.isUnlimited()) {
                    CreationEditFragment.this.dismissExportPreviewDialog();
                    RectF rectFCalculateInfiniteBoundary = CreationEditFragment.this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getDataManager().calculateInfiniteBoundary(false);
                    Point unLimitPoint = CreationEditFragment.this.getUnLimitPoint(rectFCalculateInfiniteBoundary, 400);
                    CreationEditFragment.this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getUnLimitThumbnail(rectFCalculateInfiniteBoundary, unLimitPoint.x, unLimitPoint.y, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$15$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return this.f$0.lambda$onDataSaved$0(str, (Bitmap) obj);
                        }
                    });
                    return;
                }
                if (TextUtils.isEmpty(CreationEditFragment.this.mHandWritingView.getCurrentItemView().model.getPdfId())) {
                    CreationEditFragment.this.mHandWritingView.getCurrentItemView().model.getMultiPageThumbnailStatus(new PdfThumbnailLoadListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$15$$ExternalSyntheticLambda1
                        @Override // com.miui.libhandwritingpage.listener.PdfThumbnailLoadListener
                        public final void onFinish() {
                            this.f$0.lambda$onDataSaved$2(arrayList, str);
                        }
                    });
                    return;
                } else {
                    CreationEditFragment.this.mHandWritingView.getCurrentItemView().model.getPdfThumbnailLoadStatus(new PdfThumbnailLoadListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$15$$ExternalSyntheticLambda2
                        @Override // com.miui.libhandwritingpage.listener.PdfThumbnailLoadListener
                        public final void onFinish() {
                            this.f$0.lambda$onDataSaved$4(arrayList, str);
                        }
                    });
                    return;
                }
            }
            CreationEditFragment.this.dismissExportPreviewDialog();
            Log.i(CreationEditFragment.TAG, "showShare save fail");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Unit lambda$onDataSaved$0(String str, Bitmap bitmap) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(bitmap);
            ExportImageManager.INSTANCE.getInstance().setBitmapPreviewList(arrayList);
            ExportShareActivity.launchActivity((Activity) CreationEditFragment.this.requireActivity(), 1, CreationEditFragment.this.dirPath, str, CreationEditFragment.this.handWriteEntity == null ? "" : CreationEditFragment.this.handWriteEntity.getTitle(), false);
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataSaved$2(ArrayList arrayList, final String str) {
            CreationEditFragment.this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getPageThumbnailCustomList(arrayList, 400, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$15$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f$0.lambda$onDataSaved$1(str, (List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Unit lambda$onDataSaved$1(String str, List list) throws Throwable {
            CreationEditFragment.this.dismissExportPreviewDialog();
            ExportImageManager.INSTANCE.getInstance().setBitmapPreviewList(list);
            if (!RomUtils.isOS31Support()) {
                ExportShareActivity.launchActivity((Activity) CreationEditFragment.this.requireActivity(), 1, CreationEditFragment.this.dirPath, str, CreationEditFragment.this.handWriteEntity == null ? "" : CreationEditFragment.this.handWriteEntity.getTitle(), false);
            } else {
                ArrayList arrayList = new ArrayList();
                String str2 = "Creation_Image_" + System.currentTimeMillis();
                ClipData clipDataNewRawUri = null;
                int i = 0;
                while (i < list.size()) {
                    Bitmap bitmap = (Bitmap) list.get(i);
                    i++;
                    File file = new File(FilePathUtils.getMultiPageExportFileDir(CreationEditFragment.this.getContext()), str2 + "_" + i + ".png");
                    String absolutePath = file.getAbsolutePath();
                    BitmapUtil.saveBitmapToFile(bitmap, file, true);
                    if (FileUtil.isFileExists(file)) {
                        Uri uriCreateTempUriWithFilepath = CreationEditFragment.createTempUriWithFilepath(CreationEditFragment.this.getContext(), absolutePath);
                        if (clipDataNewRawUri == null) {
                            clipDataNewRawUri = ClipData.newRawUri("share_image", uriCreateTempUriWithFilepath);
                        } else {
                            clipDataNewRawUri.addItem(new ClipData.Item(uriCreateTempUriWithFilepath));
                        }
                        arrayList.add(uriCreateTempUriWithFilepath);
                    }
                }
                boolean z = arrayList.size() > 1;
                Intent intent = new Intent(z ? "android.intent.action.SEND_MULTIPLE" : "android.intent.action.SEND");
                intent.setFlags(1);
                intent.setType("image/png");
                if (!z) {
                    intent.putExtra("android.intent.extra.STREAM", (Parcelable) arrayList.get(0));
                    intent.setClipData(ClipData.newRawUri(null, (Uri) arrayList.get(0)));
                } else {
                    intent.setClipData(clipDataNewRawUri);
                    intent.putParcelableArrayListExtra("android.intent.extra.STREAM", new ArrayList<>(arrayList));
                }
                intent.putExtra(PadMultiHandWriteFragment.EXTRA_TAP_SHARE_SUPPORT, true);
                intent.putExtra(PadMultiHandWriteFragment.EXTRA_TAP_SHARE_MULTIPLE_INFO, String.valueOf(0L));
                CreationEditFragment.this.startActivity(Intent.createChooser(intent, null));
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataSaved$4(ArrayList arrayList, final String str) {
            CreationEditFragment.this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getPageThumbnailCustomList(arrayList, 400, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$15$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return this.f$0.lambda$onDataSaved$3(str, (List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Unit lambda$onDataSaved$3(String str, List list) {
            CreationEditFragment.this.dismissExportPreviewDialog();
            ExportImageManager.INSTANCE.getInstance().setBitmapPreviewList(list);
            ExportShareActivity.launchActivity((Activity) CreationEditFragment.this.requireActivity(), 1, CreationEditFragment.this.dirPath, str, CreationEditFragment.this.handWriteEntity == null ? "" : CreationEditFragment.this.handWriteEntity.getTitle(), false);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Uri createTempUriWithFilepath(Context context, String str) {
        if (context == null) {
            Log.e(TAG, "Context is null, cannot create Uri");
            return null;
        }
        File file = new File(str);
        try {
            if (!file.exists() && !file.createNewFile()) {
                Log.e(TAG, "Failed to create file");
                return null;
            }
            return CompatUtils.getCompatUri(context, file);
        } catch (IOException e) {
            Log.e(TAG, "createNewFile error: " + e.getMessage());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Point getUnLimitPoint(RectF rectF, int i) {
        float f = i;
        int iHeight = (int) ((rectF.height() * f) / rectF.width());
        if (rectF.width() > rectF.height()) {
            iHeight = i;
            i = (int) ((f * rectF.width()) / rectF.height());
        }
        return new Point(i, iHeight);
    }

    public void loadCreationFromArguments() {
        if (getArguments() == null) {
            Log.e(TAG, "loadCreationFromArguments: Bundle is null");
            return;
        }
        NoteLoadDataEntity noteLoadDataEntity = (NoteLoadDataEntity) getArguments().getSerializable("entity");
        if (noteLoadDataEntity == null) {
            return;
        }
        if (getArguments().getBoolean(Creation.JUMP_IS_IN_MULTIWINDOW_MODE, false)) {
            this.shareViewModel.isInMultiWindowMode().postValue(true);
        }
        Bundle bundle = noteLoadDataEntity.toBundle();
        long j = bundle.getLong("data_id", -1L);
        Log.d(TAG, "loadCreationFromArguments: restore note id:" + getArguments().getLong(NOTE_ID_RESTORE, -2L));
        if (getArguments().getLong(NOTE_ID_RESTORE, -2L) != -2) {
            j = getArguments().getLong(NOTE_ID_RESTORE, -2L);
        }
        Log.d(TAG, "loadCreationFromArguments: noteId:" + j);
        String searchText = noteLoadDataEntity.getSearchText();
        this.searchContent = searchText;
        this.isFromSearch = !TextUtils.isEmpty(searchText);
        this.importPdfPath = getArguments().getString(CreationEditActivity.INTENT_IMPORT_PDF, "");
        this.importBgImagePath = getArguments().getString(CreationEditActivity.INTENT_IMPORT_IMAGE, "");
        if (j == -2) {
            MultiHandWriteEntity multiHandWriteEntityCreateNewNote = MultiHandWriteDataUtils.createNewNote(bundle.getLong("folder_id", 0L));
            this.handWriteEntity = multiHandWriteEntityCreateNewNote;
            createEntFile(multiHandWriteEntityCreateNewNote);
        } else {
            this.handWriteEntity = MultiHandWriteDataUtils.loadNote(j);
        }
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity == null) {
            if (getActivity() != null) {
                getActivity().finish();
                return;
            }
            return;
        }
        if (j != -2) {
            loadEntFile(multiHandWriteEntity);
        }
        this.shareViewModel.getEntityIdCountMap().compute(Long.valueOf(this.handWriteEntity.getId()), new BiFunction() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                Integer num = (Integer) obj2;
                return Integer.valueOf(num != null ? 1 + num.intValue() : 1);
            }
        });
        this.mCreationTime = Long.valueOf(this.handWriteEntity.getModifiedTime());
        this.mHandWritingView.setTitleAndTime(this.handWriteEntity.getTitle(), this.mCreationTime.longValue());
        this.mHandWritingView.bindPageId(j);
        Log.d(TAG, "loadCreationFromArguments: completed, handWriteEntity=".concat(this.handWriteEntity != null ? "not null" : "null"));
        Log.d(TAG, "loadCreationFromArguments: mHandWritingView=".concat(this.mHandWritingView == null ? "null" : "not null"));
    }

    public void createEntFile(final MultiHandWriteEntity multiHandWriteEntity) {
        final CommonPageBean commonPageBean = new CommonPageBean();
        this.dirPath = multiHandWriteEntity.observeEntDir();
        Log.d(TAG, "createEntFile: " + this.dirPath);
        if (FileUtil.createOrExistsDir(this.dirPath)) {
            commonPageBean.path = this.dirPath;
            commonPageBean.width = 755;
            commonPageBean.height = 1156;
            Log.d(TAG, "pageBean:" + commonPageBean.width + InternalZipConstants.ZIP_FILE_SEPARATOR + commonPageBean.height);
            commonPageBean.radius = getResources().getDimensionPixelSize(R.dimen.creation_paint_round_corner_region_size);
            if (!TextUtils.isEmpty(this.importPdfPath)) {
                this.isFromImport = true;
                this.mCreationTitle = FileUtil.getFileName(this.importPdfPath);
                ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$createEntFile$6(commonPageBean, multiHandWriteEntity);
                    }
                });
                showImportDialog();
                return;
            }
            if (!TextUtils.isEmpty(this.importBgImagePath)) {
                this.isFromImport = true;
                this.isFromImportImg = true;
                this.mCreationTitle = getDefaultTitle();
                ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() throws Throwable {
                        this.f$0.lambda$createEntFile$8(commonPageBean, multiHandWriteEntity);
                    }
                });
                showImportDialog();
                return;
            }
            this.mCreationTitle = getDefaultTitle();
            lambda$showPasswordDialog$15(commonPageBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createEntFile$6(final CommonPageBean commonPageBean, MultiHandWriteEntity multiHandWriteEntity) {
        commonPageBean.pdfPath = changeImportFileToSavePath(this.importPdfPath);
        multiHandWriteEntity.setTitle(this.mCreationTitle);
        MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createEntFile$5(commonPageBean);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createEntFile$8(final CommonPageBean commonPageBean, MultiHandWriteEntity multiHandWriteEntity) throws Throwable {
        String strChangeImportFileToSavePath = changeImportFileToSavePath(this.importBgImagePath);
        this.bgImage = strChangeImportFileToSavePath;
        Point imageSize = BitmapUtil.getImageSize(strChangeImportFileToSavePath);
        commonPageBean.width = imageSize.x;
        commonPageBean.height = imageSize.y;
        commonPageBean.bgImage = this.bgImage;
        multiHandWriteEntity.setTitle(this.mCreationTitle);
        MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createEntFile$7(commonPageBean);
            }
        });
    }

    private String changeImportFileToSavePath(String str) throws Throwable {
        Uri uri = Uri.parse(str);
        String path = uri.getPath();
        String str2 = this.dirPath + path.substring(path.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR)).replace(InternalZipConstants.ZIP_FILE_SEPARATOR, InternalZipConstants.ZIP_FILE_SEPARATOR + System.currentTimeMillis() + "_");
        try {
            FileUtil.copyUriToFile(BaseNoteApp.getInstance(), uri, new File(str2));
            if (this.isFromImportImg) {
                BitmapUtil.compress(new File(str2));
            }
        } catch (IOException e) {
            Log.d(TAG, e + "");
        }
        Log.d(TAG, "savePath:" + str2);
        return str2;
    }

    public void loadEntFile(final MultiHandWriteEntity multiHandWriteEntity) {
        final HandWriteWorkingNote handWriteWorkingNoteLoadNote;
        if (multiHandWriteEntity == null) {
            return;
        }
        if (multiHandWriteEntity.isSuniaSignalPage()) {
            handWriteWorkingNoteLoadNote = HandWriteWorkingNote.loadNote(multiHandWriteEntity.id);
            this.entFilePath = AttachmentUtils.getAttachmentPath(BaseNoteApp.getInstance(), handWriteWorkingNoteLoadNote.entName);
        } else {
            this.entFilePath = multiHandWriteEntity.observeEntDir() + "/multi_page_ent.ent";
            handWriteWorkingNoteLoadNote = null;
        }
        String str = this.entFilePath;
        if (str != null) {
            this.dirPath = str.substring(0, str.lastIndexOf(File.separator));
        }
        if (this.isFromSearch) {
            SearchItemWrapper searchItemWrapper = new SearchItemWrapper(multiHandWriteEntity.entName, multiHandWriteEntity.getRecognizedFile(), multiHandWriteEntity.getSnippet(), multiHandWriteEntity.getThumbnail());
            CommonSearchWrapper rectFromRecognizeWrapper = RecognizeDataHelper.getRectFromRecognizeWrapper(RecognizeDataHelper.getMiuiRecognizeWrapper(multiHandWriteEntity.observeEntDir(), searchItemWrapper), RecognizeDataHelper.getMiuiSpannedDataWrapper(multiHandWriteEntity.observeEntDir(), searchItemWrapper), multiHandWriteEntity.getSnippet(), this.searchContent.toLowerCase());
            if (rectFromRecognizeWrapper.searchPageIndex != -1) {
                this.mHandWritingView.setSearchResult(rectFromRecognizeWrapper.searchPageIndex, getResources().getColor(R.color.creation_search_paint_height_light), 26, rectFromRecognizeWrapper);
                this.mSearchPageIndex = rectFromRecognizeWrapper.searchPageIndex;
            } else {
                this.mSearchPageIndex = Math.max(0, multiHandWriteEntity.getCurrentPageNum());
                this.isFromSearch = false;
            }
        }
        Log.d(TAG, "loadEntFile: Path:" + this.entFilePath + "," + multiHandWriteEntity.entName);
        this.mHandWritingView.load(this.entFilePath, new CommonPageLoadListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.16
            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPageCount(int i) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPdfError(int i) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onLoadCompleted(boolean z, boolean z2, String str2) {
                Log.d(CreationEditFragment.TAG, "onLoadCompleted: success -> " + z + ", isPdf -> " + z2 + ", path -> " + str2);
                if (CreationEditFragment.this.isAdded()) {
                    CreationEditFragment.this.entFilePath = str2;
                    CreationEditFragment.this.mHandWritingView.setDoubleFingerClickUndo(HistorySet.isIsDoubleClickUndo());
                    CreationEditFragment.this.mHandWritingView.setOneShape(ShapeRecognizeParamsSet.isEnable());
                    CreationEditFragment.this.mHandWritingView.enableEstimate(EstimateParamsSet.isEnable());
                    CreationEditFragment.this.mHandWritingView.registerToolBarListener();
                    CreationEditFragment.this.mHandWritingView.refreshPenType();
                    if (!CreationEditFragment.this.isFromSearch) {
                        HandWriteViewManager.INSTANCE.loadPageItem(CreationEditFragment.this.mHandWritingView, multiHandWriteEntity);
                        return;
                    }
                    CreationEditFragment.this.mHandWritingView.resetScale(true, Float.valueOf(Math.max(0.55f, Math.min(3.0f, multiHandWriteEntity.getCurrentPageScale()))));
                    CreationEditFragment.this.mHandWritingView.jumpPage(CreationEditFragment.this.mSearchPageIndex, false);
                    CreationEditFragment.this.mHandWritingView.getCurrentItemView().model.getAdapter().setSearchPageIndex(CreationEditFragment.this.mSearchPageIndex);
                    CreationEditFragment.this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getPageSystem().setStartPageIndex(CreationEditFragment.this.mSearchPageIndex);
                }
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public String getSuniaSinglePageBgType() {
                HandWriteWorkingNote handWriteWorkingNote;
                if (multiHandWriteEntity.isSuniaSignalPage() && (handWriteWorkingNote = handWriteWorkingNoteLoadNote) != null) {
                    return handWriteWorkingNote.canvasBgType;
                }
                return "";
            }
        });
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$17, reason: invalid class name */
    class AnonymousClass17 implements CommonPageLoadListener {
        @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
        public void onPdfError(int i) {
        }

        AnonymousClass17() {
        }

        @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
        public void onLoadCompleted(boolean z, boolean z2, String str) {
            if (CreationEditFragment.this.isAdded()) {
                if (CreationEditFragment.this.isFromImport && z2) {
                    CreationEditFragment creationEditFragment = CreationEditFragment.this;
                    creationEditFragment.showPdfLimitToast(creationEditFragment.importPageCount);
                }
                CreationEditFragment.this.entFilePath = str;
                Log.d(CreationEditFragment.TAG, "entFilePath:" + CreationEditFragment.this.entFilePath);
                CreationEditFragment.this.mHandWritingView.setDoubleFingerClickUndo(HistorySet.isIsDoubleClickUndo());
                CreationEditFragment.this.mHandWritingView.setOneShape(ShapeRecognizeParamsSet.isEnable());
                CreationEditFragment.this.mHandWritingView.enableEstimate(EstimateParamsSet.isEnable());
                CreationEditFragment.this.mHandWritingView.registerToolBarListener();
                CreationEditFragment.this.mHandWritingView.refreshPenType();
                CreationEditFragment.this.mHandWritingView.post(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$17$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onLoadCompleted$0();
                    }
                });
                CreationEditFragment.this.isInImport = false;
                CreationEditFragment.this.dismissImportDialog();
                if (!CreationEditFragment.this.isFromImportImg) {
                    CreationEditFragment.this.initBackgroundColor();
                } else {
                    CreationEditFragment.this.handWriteEntity.setEmpty(false);
                }
                CreationEditFragment.this.handWriteEntity.setTitle(CreationEditFragment.this.mCreationTitle);
                if (z) {
                    CreationEditFragment.this.createPageEntSave();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLoadCompleted$0() {
            CreationEditFragment.this.handlerImportFile();
        }

        @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
        public void onPageCount(int i) {
            CreationEditFragment.this.importPageCount = i;
            if (i != 0) {
                CreationEditFragment.this.pdfPasswordErrCount = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: createPage, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$showPasswordDialog$15(CommonPageBean commonPageBean) {
        this.mHandWritingView.create(commonPageBean, new AnonymousClass17(), false);
        this.mHandWritingView.setPcMode(this.fromPc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBackgroundColor() {
        this.mHandWritingView.setPageBackground(0, this.isNightMode ? CanvasBgSet.PAPER_BLACK : CanvasBgSet.PAPER_WHITE, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlerImportFile() {
        if (this.isFromImport && this.isFromImportImg) {
            this.mHandWritingView.importImageBgToPage(this.bgImage);
        }
    }

    private void initFileManager() {
        this.pageFileManager = new PageFileManager();
        final MiuiImportStrategy miuiImportStrategy = new MiuiImportStrategy(getAppCompatActivity());
        this.pageFileManager.setImportStrategy(miuiImportStrategy.setImportCallback(new ImportCallback() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda23
            @Override // com.miui.handwriting.common.filemanager.base.ImportCallback
            public final void call(int i, String str) {
                this.f$0.lambda$initFileManager$11(miuiImportStrategy, i, str);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFileManager$11(final MiuiImportStrategy miuiImportStrategy, int i, final String str) {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initFileManager$10(miuiImportStrategy, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFileManager$10(MiuiImportStrategy miuiImportStrategy, String str) {
        final String copyPath = miuiImportStrategy.getCopyPath(getContext(), Uri.parse(str));
        MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initFileManager$9(copyPath);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFileManager$9(String str) {
        this.mHandWritingView.enableViewSelect();
        this.mHandWritingView.insertImageCenter(str);
    }

    private void showExportPreviewDialog() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (isAdded()) {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            this.exportPreviewDialog = progressDialog;
            progressDialog.setIndeterminate(true);
            this.exportPreviewDialog.setCanceledOnTouchOutside(false);
            this.exportPreviewDialog.setCancelable(false);
        }
        this.exportPreviewDialog.setMessage(activity.getString(R.string.export_preview_loading));
        this.exportPreviewDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissExportPreviewDialog() {
        MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$dismissExportPreviewDialog$12();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dismissExportPreviewDialog$12() {
        ProgressDialog progressDialog = this.exportPreviewDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.exportPreviewDialog = null;
        }
    }

    private void showSaveDialog() {
        this.mCreationEditFragmentLayout.post(this.showSaveDialogTask);
    }

    private void dismissSaveDialog() {
        View view = this.mCreationEditFragmentLayout;
        if (view != null) {
            view.removeCallbacks(this.showSaveDialogTask);
            this.showSaveDialogTask = null;
        }
        ProgressDialog progressDialog = this.saveDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.saveDialog.dismiss();
        this.saveDialog = null;
    }

    private void showImportDialog() {
        getActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissImportDialog() {
        ProgressDialog progressDialog = this.importProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.importProgressDialog = null;
        }
    }

    private String getDefaultTitle() {
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity == null) {
            return "";
        }
        return TimeUtils.formatTimestamp(multiHandWriteEntity.getCreatedTime(), getContext().getResources().getString(R.string.creation_edit_title));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backPage() {
        Log.d(TAG, "backPage: ");
        if (isAdded()) {
            requireActivity().runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$backPage$14();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$backPage$14() {
        MiuiHandWritingView miuiHandWritingView;
        ICreationEngine creationEngine;
        try {
            if (FeatureManager.getInstance().isEnableMathRecognition() && (miuiHandWritingView = this.mHandWritingView) != null && miuiHandWritingView.getCurrentItemView() != null && (creationEngine = this.mHandWritingView.getCurrentItemView().getCreationEngine()) != null && creationEngine.getCurveEngine() != null) {
                creationEngine.getCurveEngine().getRecognitionModel().dispose();
                Log.d("yjg", "Page destroyed, cleared recognition engine state");
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to clear recognition engine on destroy", e);
        }
        dismissSaveDialog();
        OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.setEnabled(false);
        }
        if (getActivity() != null && getActivity().getIntent().getStringExtra(Constants.INTENT_SCENE) != null) {
            Intent intent = new Intent(getActivity(), (Class<?>) PadNoteListPageFragment.class);
            intent.setFlags(67108864);
            startActivity(intent);
        }
        if (this.fromPc) {
            Log.d(TAG, "pcActiveSave：" + this.pcActiveSave);
            if (this.pcActiveSave) {
                requireActivity().setResult(-1);
            } else {
                requireActivity().setResult(0);
            }
        }
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity != null) {
            Long lValueOf = Long.valueOf(multiHandWriteEntity.getId());
            this.shareViewModel.getEntityIdCountMap().compute(lValueOf, new BiFunction() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda18
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    Integer num = (Integer) obj2;
                    return Integer.valueOf((num == null || num.intValue() <= 0) ? 0 : num.intValue() - 1);
                }
            });
            Integer num = this.shareViewModel.getEntityIdCountMap().get(lValueOf);
            PreferenceUtils.setPreferenceFromCreate(NoteApp.getInstance(), true);
            if (this.mHandWritingView.isContentEmpty() && num != null && num.intValue() == 0) {
                Log.d(TAG, "backPage: CurrentPage is empty, delete note id=" + lValueOf);
                NoteStore.deleteEmpty(NoteApp.getInstance(), "_id = " + lValueOf);
            } else {
                PreferenceUtils.setPreferenceFirstCreateHandWrite(NoteApp.getInstance(), false);
                Intent intent2 = new Intent();
                intent2.putExtra(NOTE_DATA, new NoteLoadDataEntity(this.handWriteEntity.id, this.handWriteEntity.folderId, this.searchContent, 3, this.handWriteEntity.modifiedTime, false, null));
                requireActivity().setResult(-1, intent2);
            }
        }
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        int i;
        int i2;
        super.onConfigurationChanged(configuration);
        CanvasElementTransactionMask canvasElementTransactionMask = this.mTransactionMask;
        if (canvasElementTransactionMask != null) {
            canvasElementTransactionMask.cancelBallAnm();
        }
        Log.d(TAG, "onConfigurationChanged");
        if (this.isInImport) {
            dismissImportDialog();
            this.importProgressDialog = null;
            showImportDialog();
        }
        AlertDialog alertDialog = this.mCreationLimitDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mCreationLimitDialog.dismiss();
        }
        if (this.exportPreviewDialog != null && getContext() != null) {
            this.exportPreviewDialog.setMessage(getContext().getString(R.string.export_preview_loading));
        }
        int i3 = 1;
        if (UIUtils.isNightMode() != this.isNightMode || !TextUtils.equals(configuration.getLocales().toString(), this.local)) {
            this.isNightMode = UIUtils.isNightMode();
            this.local = configuration.getLocales().toString();
            PasswordDialog passwordDialog = this.mPasswordDialog;
            if (passwordDialog != null && passwordDialog.isShowing()) {
                this.mPasswordDialog.dismiss();
                this.pdfPasswordErrCount--;
                showPasswordDialog();
            }
            onStartViewClick();
        }
        this.mCreationEditFragmentLayout.setBackgroundColor(getResources().getColor(R.color.creation_action_bar_bg));
        if (this.mHandWritingView.getCurrentItemView() != null) {
            i = this.mHandWritingView.getCurrentItemView().model.bgColor;
            i2 = this.mHandWritingView.getCurrentItemView().model.bgColorChange;
        } else {
            i = -1;
            i2 = -1;
        }
        if (i == -1) {
            i3 = 0;
        } else if (i != -1296) {
            i3 = 2;
        }
        int i4 = getResources().getColor(R.color.creation_action_bar_bg) == -1250068 ? 4 : 5;
        if (i2 != -1) {
            this.mHandWritingView.changeColorMode(i2, i4);
        } else {
            this.mHandWritingView.changeColorMode(i3, i4);
        }
        if (UIUtils.isSmallScreenForNoPageChange(requireContext()) || UIUtils.isActiveDekstopMode(getContext())) {
            this.mHandWritingView.removePageChangeStep(false);
            onStartViewClick();
        }
    }

    private void startService() {
        this.saveHandler = new SaveHandler(new WeakReference(this));
        this.activityMessenger = new Messenger(this.saveHandler);
    }

    private void stopService() {
        SaveHandler saveHandler = this.saveHandler;
        if (saveHandler != null) {
            saveHandler.removeCallbacksAndMessages(null);
            this.saveHandler = null;
        }
        this.activityMessenger = null;
        this.mTaskCallback = null;
    }

    private void showPasswordDialog() {
        MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showPasswordDialog$19();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPasswordDialog$19() {
        dismissImportDialog();
        if (isAdded()) {
            this.pdfPasswordErrCount++;
            PasswordDialog passwordDialog = new PasswordDialog(getContext(), this.pdfPasswordErrCount);
            this.mPasswordDialog = passwordDialog;
            passwordDialog.setPositiveListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showPasswordDialog$17(view);
                }
            });
            this.mPasswordDialog.setNegativeListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showPasswordDialog$18(view);
                }
            });
            this.mPasswordDialog.setCancelable(false);
            this.mPasswordDialog.show();
            return;
        }
        this.isSaving = true;
        backPage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPasswordDialog$17(View view) {
        if (Utils.isFastClick()) {
            return;
        }
        final CommonPageBean commonPageBean = new CommonPageBean();
        if (this.dirPath == null) {
            this.dirPath = FilePathConstant.getEngineAndPageEntPath();
        }
        if (FileUtil.createOrExistsDir(this.dirPath)) {
            commonPageBean.path = this.dirPath;
            commonPageBean.width = 755;
            commonPageBean.height = 1156;
            commonPageBean.radius = getResources().getDimensionPixelSize(R.dimen.creation_paint_round_corner_region_size);
            this.isFromImport = true;
            ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showPasswordDialog$16(commonPageBean);
                }
            });
            showImportDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPasswordDialog$16(final CommonPageBean commonPageBean) {
        commonPageBean.pdfPath = changeImportFileToSavePath(this.importPdfPath);
        this.handWriteEntity.setTitle(this.mCreationTitle);
        commonPageBean.pdfPassword = this.mPasswordDialog.getPassword();
        MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showPasswordDialog$15(commonPageBean);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPasswordDialog$18(View view) {
        backPage();
    }

    private static class TaskServiceCallBack extends ITaskServiceCallback.Stub {
        WeakReference<CreationEditFragment> mEditFragmentWeakReference;

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToAI(boolean z, String str, int i) throws RemoteException {
        }

        public TaskServiceCallBack(CreationEditFragment creationEditFragment) {
            this.mEditFragmentWeakReference = new WeakReference<>(creationEditFragment);
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void recognizeAsText(String str, int i) throws RemoteException {
            PartiBean selectRecognizeDataFromFile = RecognizeDataHelper.readSelectRecognizeDataFromFile(str);
            CreationEditFragment creationEditFragment = this.mEditFragmentWeakReference.get();
            if (creationEditFragment != null) {
                creationEditFragment.mHandWritingView.recognizeAsText(selectRecognizeDataFromFile, i);
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void copyAsText(String str) throws RemoteException {
            PartiBean selectRecognizeDataFromFile = RecognizeDataHelper.readSelectRecognizeDataFromFile(str);
            CreationEditFragment creationEditFragment = this.mEditFragmentWeakReference.get();
            if (creationEditFragment != null) {
                creationEditFragment.mHandWritingView.copyAsText(selectRecognizeDataFromFile);
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void classificationForAi(boolean z, String str) throws RemoteException {
            CreationEditFragment creationEditFragment = this.mEditFragmentWeakReference.get();
            if (creationEditFragment != null) {
                creationEditFragment.mHandWritingView.selectMoveEnable(true);
                MiuiHandWritingView miuiHandWritingView = creationEditFragment.mHandWritingView;
                if (miuiHandWritingView != null) {
                    miuiHandWritingView.startAiFormClassification(z, str);
                }
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void showExportPreview(String str, int i, String str2, List<String> list, String str3) throws RemoteException {
            CreationServiceManager.INSTANCE.clearLastMessage();
            CreationEditFragment creationEditFragment = this.mEditFragmentWeakReference.get();
            if (creationEditFragment == null || !creationEditFragment.isAdded()) {
                return;
            }
            Log.d(CreationEditFragment.TAG, "Fragment receive: MSG_FRAGMENT_EXPORT_PREVIEW_IMAGE " + i);
            creationEditFragment.dismissExportPreviewDialog();
            if (i == 1) {
                Log.d(CreationEditFragment.TAG, "pdf");
            } else if (i == 2) {
                Log.d(CreationEditFragment.TAG, "image: " + list + "___dirPath :" + str3);
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportOnProgress(int i) throws RemoteException {
            Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
            intent.putExtra("operationType", "onProgress");
            intent.putExtra("percentDone", i);
            Log.d(CreationEditFragment.TAG, "Fragment receive: MSG_FRAGMENT_EXPORT_ON_PROGRESS: " + i);
            LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).sendBroadcast(intent);
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportCompleted(boolean z, String str, int i) throws RemoteException {
            Log.d(CreationEditFragment.TAG, "Fragment receive: MSG_FRAGMENT_EXPORT_ON_COMPLETED");
            Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
            intent.putExtra("operationType", "onCompleted");
            intent.putExtra("isCompletedSuccess", z);
            intent.putExtra("completedPath", str);
            Log.d(CreationEditFragment.TAG, "EXPORT_completedPath:" + str);
            intent.putExtra("exportType", i);
            CreationServiceManager.INSTANCE.clearLastMessage();
            LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).sendBroadcast(intent);
        }

        /* JADX WARN: Code duplicated, block: B:43:0x00a8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToPcCompleted(String str) throws Throwable {
            OutputStream outputStream;
            CreationServiceManager.INSTANCE.clearLastMessage();
            final CreationEditFragment creationEditFragment = this.mEditFragmentWeakReference.get();
            if (creationEditFragment == null) {
                return;
            }
            Log.d(CreationEditFragment.TAG, "fragment receive export to pc：" + str);
            FileInputStream fileInputStream = null;
            outputStreamOpenOutputStream = null;
            OutputStream outputStreamOpenOutputStream = null;
            fileInputStream = null;
            try {
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(str);
                    try {
                        byte[] byteArray = IOUtils.toByteArray(fileInputStream2);
                        outputStreamOpenOutputStream = BaseNoteApp.getInstance().getContentResolver().openOutputStream(creationEditFragment.pcOutput);
                        if (outputStreamOpenOutputStream != null) {
                            outputStreamOpenOutputStream.write(byteArray);
                        }
                        FileUtil.deleteFile(new File(str));
                        IOUtils.closeQuietly((InputStream) fileInputStream2);
                        if (outputStreamOpenOutputStream != null) {
                            outputStreamOpenOutputStream.close();
                        }
                    } catch (IOException e) {
                        e = e;
                        outputStream = outputStreamOpenOutputStream;
                        fileInputStream = fileInputStream2;
                        try {
                            Log.d(CreationEditFragment.TAG, "fragment receive export to pc exception：" + e);
                            FileUtil.deleteFile(new File(str));
                            IOUtils.closeQuietly((InputStream) fileInputStream);
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            creationEditFragment.pcActiveSave = true;
                            Objects.requireNonNull(creationEditFragment);
                            MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$TaskServiceCallBack$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    creationEditFragment.save();
                                }
                            });
                        } catch (Throwable th) {
                            th = th;
                            FileUtil.deleteFile(new File(str));
                            IOUtils.closeQuietly((InputStream) fileInputStream);
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        outputStream = outputStreamOpenOutputStream;
                        fileInputStream = fileInputStream2;
                        FileUtil.deleteFile(new File(str));
                        IOUtils.closeQuietly((InputStream) fileInputStream);
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    outputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    outputStream = null;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            creationEditFragment.pcActiveSave = true;
            Objects.requireNonNull(creationEditFragment);
            MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$TaskServiceCallBack$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    creationEditFragment.save();
                }
            });
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportPreviewFail() throws RemoteException {
            CreationEditFragment creationEditFragment = this.mEditFragmentWeakReference.get();
            if (creationEditFragment != null) {
                creationEditFragment.dismissExportPreviewDialog();
            }
            CreationServiceManager.INSTANCE.clearLastMessage();
            Log.i(CreationEditFragment.TAG, "Fragment receive: MSG_FRAGMENT_EXPORT_PREVIEW_IMAGE_FAIL");
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void backToHome() throws RemoteException {
            Log.d(CreationEditFragment.TAG, "backToHome: ");
            CreationServiceManager.INSTANCE.clearLastMessage();
            CreationEditFragment creationEditFragment = this.mEditFragmentWeakReference.get();
            if (creationEditFragment != null) {
                creationEditFragment.backPage();
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public boolean exportThumbnailFail() throws RemoteException {
            if (CreationServiceManager.INSTANCE.getRestartNum() < 3) {
                CreationServiceManager.INSTANCE.restartServiceTask();
                return false;
            }
            CreationServiceManager.INSTANCE.setRestartNum(0);
            backToHome();
            return true;
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void onGenComplete(long j, ContentValues contentValues) throws RemoteException {
            Log.d(CreationEditFragment.TAG, "onGenComplete: ");
            String asString = contentValues.getAsString("recognized_file_names");
            String asString2 = contentValues.getAsString(Creation.TEXT_CONTENT);
            if (TextUtils.isEmpty(asString2) || RecognizeDataHelper.containsSeparators(asString2)) {
                asString2 = RecognizeDataHelper.removeSeparatorsAndJoin(asString2);
            }
            if (asString != null) {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("snippet", asString2);
                contentValues2.put("recognized_file_names", asString);
                MultiHandWriteDataUtils.updateValues(contentValues2, j);
            }
        }
    }

    private static class SaveHandler extends Handler {
        private final WeakReference<CreationEditFragment> fragmentRef;

        public SaveHandler(WeakReference<CreationEditFragment> weakReference) {
            super(Looper.myLooper());
            this.fragmentRef = weakReference;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Log.d(CreationEditFragment.TAG, "Fragment receive: " + message.what);
            int i = message.what;
            if (i == 0) {
                Log.d(CreationEditFragment.TAG, "MSG_FRAGMENT_SAVE_PAUSE_MESSAGE pause Save");
                Schedulers.io().scheduleDirect(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.SaveHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            ((CreationEditFragment) SaveHandler.this.fragmentRef.get()).autoSave(true);
                        } catch (Exception e) {
                            Log.e(CreationEditFragment.TAG, "autoSave SAVE_PAUSE_MESSAGE err is " + e);
                        }
                    }
                });
            } else {
                if (i != 1) {
                    return;
                }
                final int iIntValue = ((Integer) message.obj).intValue();
                Log.d(CreationEditFragment.TAG, "CacheHandler: SAVE_MESSAGE_DELAY ， pageIndex is " + iIntValue);
                Schedulers.io().scheduleDirect(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.SaveHandler.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            ((CreationEditFragment) SaveHandler.this.fragmentRef.get()).ensureMathResultSavable();
                            ((CreationEditFragment) SaveHandler.this.fragmentRef.get()).autoSave(iIntValue == 0);
                        } catch (Exception e) {
                            Log.e(CreationEditFragment.TAG, "autoSave SAVE_MESSAGE_DELAY err is " + e);
                        }
                    }
                });
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, final Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == 0) {
            MiuiHandWritingView miuiHandWritingView = this.mHandWritingView;
            if (miuiHandWritingView != null) {
                miuiHandWritingView.allowBackByAI();
            }
            this.mTransactionMask.release();
            this.mTransactionMask.setVisibility(8);
            MiuiHandWritingView miuiHandWritingView2 = this.mHandWritingView;
            if (miuiHandWritingView2 != null) {
                miuiHandWritingView2.finishSelect();
            }
        }
        CanvasElementTransactionMask canvasElementTransactionMask = this.mTransactionMask;
        if (canvasElementTransactionMask != null) {
            canvasElementTransactionMask.releaseCanvasMaskView();
        }
        if (i == 1000) {
            this.mTransactionMask.setVisibility(8);
            if (i2 == -1) {
                this.mNewPageFromAI = true;
                int currentPosition = this.mHandWritingView.getCurrentPosition();
                String string = intent.getExtras().getString("ai_path");
                this.imageFileTmpPath = string;
                Point imageSize = BitmapUtil.getImageSize(string);
                final int i3 = this.fromPc ? 0 : currentPosition + 1;
                this.mHandWritingView.insertPage(i3, imageSize.x, imageSize.y);
                getView().postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.19
                    @Override // java.lang.Runnable
                    public void run() {
                        CreationEditFragment.this.mHandWritingView.setBgImage(i3, CreationEditFragment.this.imageFileTmpPath);
                        if (!CreationEditFragment.this.fromPc || CreationEditFragment.this.mHandWritingView.getPageCount() <= 1) {
                            return;
                        }
                        ArrayList<Integer> arrayList = new ArrayList<>();
                        arrayList.add(1);
                        CreationEditFragment.this.mHandWritingView.deletePage(arrayList);
                    }
                }, 500L);
                return;
            }
            return;
        }
        if (i == 2000 || i == 3000) {
            if (i2 == -1 && intent != null && intent.getExtras() != null && getView() != null) {
                this.imageFileTmpPath = intent.getExtras().getString("ai_path");
                this.mTransactionMask.setVisibility(0);
                getView().postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.20
                    @Override // java.lang.Runnable
                    public void run() {
                        CreationEditFragment.this.mHandWritingView.enableViewSelect();
                        CreationImage creationImage = (CreationImage) intent.getExtras().getParcelable(CreationEditFragment.RECREATE_IMAGE_INFO);
                        Log.i(CreationEditFragment.TAG, "MiuiHandWritingImpl replaceSelected");
                        CreationEditFragment.this.mHandWritingView.replaceSelected(CreationEditFragment.this.imageFileTmpPath, creationImage, CreationEditFragment.this.mTransactionMask);
                    }
                }, 200L);
                return;
            }
            MiuiHandWritingView miuiHandWritingView3 = this.mHandWritingView;
            if (miuiHandWritingView3 != null) {
                miuiHandWritingView3.allowBackByAI();
            }
            MiuiHandWritingView miuiHandWritingView4 = this.mHandWritingView;
            if (miuiHandWritingView4 != null) {
                miuiHandWritingView4.finishSelect();
                return;
            }
            return;
        }
        if (i == 4000) {
            if (i2 != -1 || intent == null || intent.getExtras() == null || getView() == null) {
                return;
            }
            this.imageFileTmpPath = intent.getExtras().getString("ai_path");
            getView().postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.21
                @Override // java.lang.Runnable
                public void run() {
                    CreationEditFragment.this.mHandWritingView.enableViewSelect();
                    CreationEditFragment.this.mHandWritingView.replaceSelected(CreationEditFragment.this.imageFileTmpPath);
                }
            }, 500L);
            return;
        }
        if (i != 5000) {
            return;
        }
        if (i2 == -1 && intent != null && intent.getExtras() != null && getView() != null) {
            this.imageFileTmpPath = intent.getExtras().getString("ai_path");
            this.mTransactionMask.setVisibility(0);
            getView().postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment.22
                @Override // java.lang.Runnable
                public void run() {
                    CreationEditFragment.this.mHandWritingView.enableViewSelect();
                    CreationImage creationImage = (CreationImage) intent.getExtras().getParcelable(CreationEditFragment.RECREATE_IMAGE_INFO);
                    Log.i(CreationEditFragment.TAG, "MiuiHandWritingImpl insert ai image");
                    CreationEditFragment.this.mHandWritingView.insertAiImage(CreationEditFragment.this.imageFileTmpPath, creationImage, CreationEditFragment.this.mTransactionMask);
                }
            }, 200L);
            return;
        }
        this.mHandWritingView.finishSelect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RectF adaptForInsertLocationFromAIImage(RectF rectF) {
        RectF rectF2 = new RectF(rectF);
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(this.imageFileTmpPath);
        if (bitmapDecodeFile == null) {
            Log.d(TAG, "adaptForInsertLocationFromAIImage, get null bitmap from imageFileTmpPath");
            return null;
        }
        if (bitmapDecodeFile.getWidth() / bitmapDecodeFile.getHeight() < rectF.width() / rectF.height()) {
            float fWidth = (((rectF.width() * bitmapDecodeFile.getHeight()) / bitmapDecodeFile.getWidth()) - rectF.height()) / 2.0f;
            rectF2.top -= fWidth;
            rectF2.bottom += fWidth;
            return rectF2;
        }
        float width = (((bitmapDecodeFile.getWidth() * rectF.height()) / bitmapDecodeFile.getHeight()) - rectF.width()) / 2.0f;
        rectF2.left -= width;
        rectF2.right += width;
        return rectF2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLimitDialog() {
        if (!isAdded() || getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        AlertDialog alertDialog = this.mCreationLimitDialog;
        if ((alertDialog != null && alertDialog.isShowing()) || getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        if (this.mHandWritingView.isUnlimited()) {
            Toast.makeText(getContext(), R.string.creation_edit_limit_dialog_message_content_max, 0).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.creation_edit_limit_dialog_title);
        if (this.mHandWritingView.getPageCount() >= 500) {
            builder.setMessage(R.string.creation_edit_limit_dialog_message_page_max);
            builder.setPositiveButton(R.string.creation_edit_limit_dialog_sure, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    CreationEditFragment.lambda$showLimitDialog$20(dialogInterface, i);
                }
            });
        } else {
            builder.setMessage(R.string.creation_edit_limit_dialog_message_short);
            builder.setNegativeButton(R.string.creation_edit_limit_dialog_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    CreationEditFragment.lambda$showLimitDialog$21(dialogInterface, i);
                }
            });
            builder.setPositiveButton(R.string.creation_edit_limit_dialog_new, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda12
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$showLimitDialog$22(dialogInterface, i);
                }
            });
        }
        AlertDialog alertDialogCreate = builder.create();
        this.mCreationLimitDialog = alertDialogCreate;
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLimitDialog$22(DialogInterface dialogInterface, int i) {
        if (this.mHandWritingView == null || Utils.isFastClick(i, 300L)) {
            return;
        }
        this.mHandWritingView.addPage(true);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$23, reason: invalid class name */
    class AnonymousClass23 implements DrawErrorCallback {
        AnonymousClass23() {
        }

        @Override // xc.engine.api.listener.DrawErrorCallback
        public void onError(int i) {
            Log.i(CreationEditFragment.TAG, "onError: type" + i);
            final WeakReference weakReference = new WeakReference(CreationEditFragment.this);
            if (weakReference.get() == null || ((CreationEditFragment) weakReference.get()).isDetached() || ((CreationEditFragment) weakReference.get()).isRemoving()) {
                return;
            }
            if (i == 0) {
                MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$23$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CreationEditFragment.AnonymousClass23.lambda$onError$0(weakReference);
                    }
                });
            } else if (i == 2) {
                Log.i(CreationEditFragment.TAG, "onError: BITMAP_LIMIT");
                MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$23$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(BaseNoteApp.getInstance(), R.string.creation_edit_limit_img, 0).show();
                    }
                });
            } else if (i == 1) {
                Log.i(CreationEditFragment.TAG, "onError: TOTAL_POINT_LIMIT");
                MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$23$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(BaseNoteApp.getInstance(), R.string.creation_edit_limit_all, 0).show();
                    }
                });
            } else if (i == 3) {
                Log.i(CreationEditFragment.TAG, "onError: TEXT_LIMIT");
                MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$23$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(BaseNoteApp.getInstance(), R.string.creation_edit_limit_text_box, 0).show();
                    }
                });
            }
            ((CreationEditFragment) weakReference.get()).mHandWritingView.dismissPasteDialog();
        }

        static /* synthetic */ void lambda$onError$0(WeakReference weakReference) {
            Log.i(CreationEditFragment.TAG, "onError: POINT_LIMIT");
            ((CreationEditFragment) weakReference.get()).showLimitDialog();
        }

        @Override // xc.engine.api.listener.DrawErrorCallback
        public void onSuccess() {
            WeakReference weakReference = new WeakReference(CreationEditFragment.this);
            if (weakReference.get() == null || ((CreationEditFragment) weakReference.get()).isDetached() || ((CreationEditFragment) weakReference.get()).isRemoving() || ((CreationEditFragment) weakReference.get()).mHandWritingView == null || !((CreationEditFragment) weakReference.get()).mHandWritingView.isUnlimited()) {
                return;
            }
            ((CreationEditFragment) weakReference.get()).mHandWritingView.expanseUnLimitPage();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initErrorCallback() {
        CreationPageSDK.sErrorCallback = new AnonymousClass23();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureMathResultSavable() {
        PageItemView currentItemView;
        ICreationEngine creationEngine;
        try {
            MiuiHandWritingView miuiHandWritingView = this.mHandWritingView;
            if (miuiHandWritingView == null || (currentItemView = miuiHandWritingView.getCurrentItemView()) == null || (creationEngine = currentItemView.getCreationEngine()) == null || creationEngine.getCurveEngine() == null || creationEngine.getCurveEngine().getMathSystem() == null) {
                return;
            }
            MathSystem mathSystem = creationEngine.getCurveEngine().getMathSystem();
            boolean zHasRenderContent = mathSystem.hasRenderContent();
            boolean isSaveBtnClicked = mathSystem.getIsSaveBtnClicked();
            Log.d(TAG, "ensureMathResultSavable: hasRenderContent=" + zHasRenderContent + ", currentSaveState=" + isSaveBtnClicked);
            if (zHasRenderContent && !isSaveBtnClicked) {
                mathSystem.setSaveBtnClicked(true);
                Log.d(TAG, "ensureMathResultSavable: Set  isSaveBtnClicked   true");
            } else if (zHasRenderContent && isSaveBtnClicked) {
                Log.d(TAG, "ensureMathResultSavable: isSaveBtnClicked  is true already");
            } else {
                Log.w(TAG, "ensureMathResultSavable: MathResult is empty");
            }
        } catch (Exception e) {
            Log.e(TAG, "ensureMathResultSavable error: " + e.getMessage(), e);
        }
    }

    private void releasePage() {
        List<Integer> list = this.mLastExportList;
        if (list == null || list.isEmpty()) {
            return;
        }
        if (TextUtils.isEmpty(this.mHandWritingView.getCurrentItemView().model.getPdfId())) {
            Log.d(TAG, "common page need not release");
            return;
        }
        int currentPosition = this.mHandWritingView.getCurrentItemView().model.getCurrentPosition();
        for (Integer num : this.mLastExportList) {
            if (num.intValue() < currentPosition - 4 || num.intValue() > currentPosition + 4) {
                this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getPageSystem().releasePageBgImage(num.intValue());
            }
        }
        this.mLastExportList = null;
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportProgress(int i) {
        Log.d(TAG, "export progress:" + i);
        Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
        intent.putExtra("operationType", "onProgress");
        intent.putExtra("percentDone", i);
        Log.d(TAG, "Fragment receive: MSG_FRAGMENT_EXPORT_ON_PROGRESS: " + i);
        LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).sendBroadcast(intent);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void requireNextList(List<Integer> list) {
        releasePage();
        this.mLastExportList = list;
        if (TextUtils.isEmpty(this.mHandWritingView.getCurrentItemView().model.getPdfId())) {
            if (this.mHandWritingView.isUnlimited()) {
                RectF rectFCalculateInfiniteBoundary = this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getDataManager().calculateInfiniteBoundary(false);
                Point unLimitPoint = getUnLimitPoint(rectFCalculateInfiniteBoundary, 1440);
                this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getUnLimitThumbnail(rectFCalculateInfiniteBoundary, unLimitPoint.x, unLimitPoint.y, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return CreationEditFragment.lambda$requireNextList$23((Bitmap) obj);
                    }
                });
                return;
            }
            this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getPageThumbnailCustomList(list, 1440, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return CreationEditFragment.lambda$requireNextList$24((List) obj);
                }
            });
            return;
        }
        this.mHandWritingView.getCurrentItemView().model.getPdfBitmapForExport(list, new AnonymousClass24(list));
    }

    static /* synthetic */ Unit lambda$requireNextList$23(Bitmap bitmap) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(bitmap);
        ExportImageManager.INSTANCE.getInstance().addExportBitmap(arrayList);
        return null;
    }

    static /* synthetic */ Unit lambda$requireNextList$24(List list) {
        ExportImageManager.INSTANCE.getInstance().addExportBitmap(list);
        return null;
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$24, reason: invalid class name */
    class AnonymousClass24 implements PDFExportListener {
        final /* synthetic */ List val$list;

        AnonymousClass24(List list) {
            this.val$list = list;
        }

        @Override // com.miui.libhandwritingpage.listener.PDFExportListener
        public void onFinish() {
            CreationEditFragment.this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getPageThumbnailCustomList(this.val$list, 1440, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$24$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return CreationEditFragment.AnonymousClass24.lambda$onFinish$0((List) obj);
                }
            });
        }

        static /* synthetic */ Unit lambda$onFinish$0(List list) {
            ExportImageManager.INSTANCE.getInstance().addExportBitmap(list);
            return null;
        }

        @Override // com.miui.libhandwritingpage.listener.PDFExportListener
        public void onLoading() {
            Log.e(CreationEditFragment.TAG, "require next list error");
        }
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportFinish(int i, String str) {
        releasePage();
        Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
        intent.putExtra("operationType", "onCompleted");
        intent.putExtra("isCompletedSuccess", true);
        intent.putExtra("completedPath", str);
        Log.d(TAG, "EXPORT_completedPath:" + str);
        intent.putExtra("exportType", i);
        LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).sendBroadcast(intent);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportError(int i, int i2, String str) {
        dismissExportPreviewDialog();
        Log.e(TAG, "export error:" + i);
        releasePage();
        Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
        intent.putExtra("operationType", "onCompleted");
        intent.putExtra("isCompletedSuccess", false);
        intent.putExtra("completedPath", str);
        Log.d(TAG, "EXPORT_completedPath:$path");
        intent.putExtra("exportType", i2);
        LocalBroadcastManager.getInstance(BaseNoteApp.getInstance()).sendBroadcast(intent);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$25, reason: invalid class name */
    class AnonymousClass25 implements PDFExportListener {
        final /* synthetic */ List val$list;

        AnonymousClass25(List list) {
            this.val$list = list;
        }

        @Override // com.miui.libhandwritingpage.listener.PDFExportListener
        public void onFinish() {
            CreationEditFragment.this.mHandWritingView.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getPageThumbnailCustomList(this.val$list, 400, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.CreationEditFragment$25$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return CreationEditFragment.AnonymousClass25.lambda$onFinish$0((List) obj);
                }
            });
        }

        static /* synthetic */ Unit lambda$onFinish$0(List list) {
            ExportImageManager.INSTANCE.getInstance().addPreviewBitmap(list);
            return null;
        }

        @Override // com.miui.libhandwritingpage.listener.PDFExportListener
        public void onLoading() {
            Log.e(CreationEditFragment.TAG, "require preview list error");
        }
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void requirePreviewList(List<Integer> list) {
        this.mHandWritingView.getCurrentItemView().model.getPdfBitmapForExport(list, new AnonymousClass25(list));
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void loadPreviewError(int i) {
        dismissExportPreviewDialog();
        Log.e(TAG, "load preview error:" + i);
    }
}
