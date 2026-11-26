package com.miui.pad.feature.notes.share;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.hardware.DataSpace;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.handwriting.common.filemanager.PageFileManager;
import com.miui.handwriting.common.filemanager.base.ExportCallback;
import com.miui.handwriting.common.filemanager.strategy.MiuiExportStrategy;
import com.miui.handwriting.common.tools.UIUtils;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.view.BaseActivity;
import com.miui.handwriting.common.view.thumbnail.ExportImageManager;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.util.CompatFileProvider;
import com.miui.pad.feature.notes.filemanager.ShareJumpHelper;
import com.miui.pad.feature.notes.filemanager.SingleViewTypeSpaceAroundDecoration;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import miuix.appcompat.app.ProgressDialog;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ExportShareActivity extends BaseActivity implements ExportCallback {
    private static final String EXTRA_KEY_CREATION_TITLE = "extra_creation_title";
    private static final String EXTRA_KEY_DIR = "extra_dir";
    private static final String EXTRA_KEY_ENT_PATH = "extra_ent_filepath";
    private static final String EXTRA_KEY_FILES = "extra_files";
    private static final String EXTRA_KEY_PRIVATE = "extra_private";
    private static final String EXTRA_KEY_TYPE = "extra_type";
    private static final String FILE_PROVIDER = "com.miui.notes";
    public static final String INTENT_ACTIVITY_RESUME = "share.activity.resume";
    private static final String INTENT_CLASSNAME_SHARE_WECHAT = "com.tencent.mm.ui.tools.ShareImgUI";
    private static final String INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    private static final int REQUEST_CODE_AS_SHARE = 2;
    private static final String SAVE_IS_IN_EXPORT = "save_is_in_export";
    private static final String SAVE_KEY_INTENT = "save_intent";
    private static final String SAVE_KEY_IS_RESTORE = "is_restore";
    private static final String SAVE_KEY_SELECT_INDEX_LIST = "selectIndexList";
    public static final int SHARE_TYPE_IMAGE = 1;
    public static final int SHARE_TYPE_PDF = 0;
    private static final String TAG = "ExportShareActivityLog";
    private static MiuiExportStrategy exportStrategy;
    private static PageFileManager pageFileManager;
    private String dirPath;
    private String entFilePath;
    private ProgressDialog exportProgressDialog;
    private boolean isInFullWindowGestureMode;
    private ImageView ivClose;
    private List<Bitmap> mBitmapList;
    private ShareAppsAdapter mChooserAdapter;
    private RecyclerView mChooserView;
    private String mContent;
    private String mCreationTitle;
    private SingleViewTypeSpaceAroundDecoration mDecor;
    private List<String> mFilePathList;
    private ImagePreViewAdapter mPreViewAdapter;
    private RecyclerView mPreviewRecycleView;
    private ViewGroup mShareContainer;
    private int mShareType;
    private String mTitle;
    private BroadcastReceiver operationReceiver;
    private Intent shareIntent;
    public ShareViewModel shareViewModel;
    private RelativeLayout titleBar;
    private List<String> imageList = new ArrayList();
    private boolean mFromPrivate = false;
    private boolean mInitResume = true;
    private ArrayList<Integer> restoreSelectIndexList = new ArrayList<>();
    private boolean isRestoreActivity = false;
    private boolean restoreInExportState = false;
    private int percentDoneMax = 0;

    static class ExportCallbackImpl implements ExportCallback {
        private WeakReference<ExportShareActivity> weakReference;

        public ExportCallbackImpl(ExportShareActivity exportShareActivity) {
            this.weakReference = new WeakReference<>(exportShareActivity);
        }

        @Override // com.miui.handwriting.common.filemanager.base.ExportCallback
        public void call(int i, boolean z, List<String> list) {
            if (this.weakReference.get() != null) {
                this.weakReference.get().call(i, z, list);
            }
        }

        @Override // com.miui.handwriting.common.filemanager.base.ExportCallback
        public void onProgress(int i) {
            if (this.weakReference.get() != null) {
                this.weakReference.get().onProgress(i);
            }
        }
    }

    public static void launchActivity(Activity activity, String str, String str2, int i, List<String> list, int i2) {
        Intent intent = new Intent(activity, (Class<?>) ExportShareActivity.class);
        intent.putExtra("android.intent.extra.TEXT", str);
        intent.putExtra("android.intent.extra.TITLE", str2);
        intent.putExtra(EXTRA_KEY_TYPE, i);
        intent.putStringArrayListExtra(EXTRA_KEY_FILES, (ArrayList) list);
        activity.startActivityForResult(intent, i2);
    }

    public static void launchActivity(Activity activity, String str, int i, String str2, int i2, String str3, boolean z) {
        Intent intent = new Intent(activity, (Class<?>) ExportShareActivity.class);
        intent.putExtra(EXTRA_KEY_TYPE, i);
        intent.putExtra(EXTRA_KEY_DIR, str);
        intent.putExtra(EXTRA_KEY_ENT_PATH, str2);
        intent.putExtra(EXTRA_KEY_CREATION_TITLE, str3);
        intent.putExtra(EXTRA_KEY_PRIVATE, z);
        Log.d(TAG, "EXTRA_KEY_TYPE:" + i);
        activity.startActivityForResult(intent, i2);
    }

    public static void launchActivity(Activity activity, int i, String str, String str2, String str3, boolean z) {
        launchActivity(activity, str, i, str2, 0, str3, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_export_share);
        initialize();
        this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(this);
        UIUtils.setNavigationTrans(this);
        if (pageFileManager == null) {
            pageFileManager = new PageFileManager();
        }
        if (exportStrategy == null) {
            exportStrategy = new MiuiExportStrategy();
        }
        exportStrategy.setDirPath(this.dirPath);
        exportStrategy.setEntFilePath(this.entFilePath);
        exportStrategy.setContext(getApplicationContext());
        exportStrategy.setExportCallback(new ExportCallbackImpl(this));
        pageFileManager.setExportStrategy(exportStrategy);
        registerExportOperationReceiver();
        setFloatingWindowBorderEnable(false);
        UIUtils.setFullScreenMode(this, true);
        ShareViewModel shareViewModel = (ShareViewModel) new ViewModelProvider(this).get(ShareViewModel.class);
        this.shareViewModel = shareViewModel;
        shareViewModel.isOneItemCheckedLiveData().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.share.ExportShareActivity$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(Boolean bool) {
        Logger.INSTANCE.d(TAG, "ImagesNumberChange: " + bool);
        new Intent("android.intent.action.SEND").setType(getTypeString());
        if (bool.booleanValue()) {
            this.mChooserAdapter.isMulti = false;
            this.mChooserAdapter.reBuildList();
            this.mChooserAdapter.notifyDataSetChanged();
        }
        if (bool.booleanValue()) {
            return;
        }
        this.mChooserAdapter.isMulti = true;
        this.mChooserAdapter.reBuildList();
        this.mChooserAdapter.notifyDataSetChanged();
    }

    private void registerExportOperationReceiver() {
        this.operationReceiver = new BroadcastReceiver() { // from class: com.miui.pad.feature.notes.share.ExportShareActivity.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                if (TextUtils.equals(extras.getString("operationType"), "onProgress")) {
                    int i = extras.getInt("percentDone", 0);
                    if (ExportShareActivity.this.percentDoneMax < i) {
                        ExportShareActivity.this.percentDoneMax = i;
                    }
                    ExportShareActivity.exportStrategy.onProgress(ExportShareActivity.this.percentDoneMax);
                    return;
                }
                if (TextUtils.equals(extras.getString("operationType"), "onCompleted")) {
                    ExportShareActivity.this.percentDoneMax = 0;
                    ExportShareActivity.exportStrategy.completeExport(extras.getBoolean("isCompletedSuccess"), extras.getString("completedPath"), extras.getInt("exportType"));
                }
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.operationReceiver, new IntentFilter(Constants.EXPORT_OPERATION_INTENT));
    }

    @Override // com.miui.handwriting.common.filemanager.base.ExportCallback
    public void call(int i, final boolean z, List<String> list) {
        Log.d(TAG, "call export:" + z);
        if (isFinishing()) {
            return;
        }
        this.restoreInExportState = false;
        this.mFilePathList = list;
        dismissExportDialog(new DialogInterface.OnDismissListener() { // from class: com.miui.pad.feature.notes.share.ExportShareActivity.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                Log.d(ExportShareActivity.TAG, "process dialog onDismiss");
                if (ExportShareActivity.this.mFilePathList == null) {
                    Log.d(ExportShareActivity.TAG, "mFilePathList empty");
                }
                if (!z) {
                    Toast.makeText(ExportShareActivity.this.getApplicationContext(), ExportShareActivity.this.getApplicationContext().getString(R.string.export_fail), 0).show();
                    return;
                }
                if (ExportShareActivity.this.shareIntent != null) {
                    ExportShareActivity exportShareActivity = ExportShareActivity.this;
                    exportShareActivity.doShare(exportShareActivity.shareIntent, ExportShareActivity.this.mShareType);
                } else if (ExportShareActivity.this.mShareType == 1) {
                    ExportShareActivity exportShareActivity2 = ExportShareActivity.this;
                    ShareJumpHelper.updateToGallery(exportShareActivity2, exportShareActivity2.mFilePathList);
                } else {
                    ShareJumpHelper.openDownload(ExportShareActivity.this);
                }
            }
        });
    }

    @Override // com.miui.handwriting.common.filemanager.base.ExportCallback
    public void onProgress(int i) {
        ProgressDialog progressDialog;
        Log.d(TAG, "percentDone:" + i);
        if (isFinishing() || (progressDialog = this.exportProgressDialog) == null) {
            return;
        }
        progressDialog.setProgress(i);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity
    protected void onRestoreInstanceState(Bundle bundle) {
        MiuiExportStrategy miuiExportStrategy;
        super.onRestoreInstanceState(bundle);
        this.isRestoreActivity = bundle.getBoolean(SAVE_KEY_IS_RESTORE);
        this.restoreInExportState = bundle.getBoolean(SAVE_IS_IN_EXPORT, false);
        this.restoreSelectIndexList = bundle.getIntegerArrayList(SAVE_KEY_SELECT_INDEX_LIST);
        try {
            String string = bundle.getString(SAVE_KEY_INTENT);
            if (!TextUtils.isEmpty(string)) {
                this.shareIntent = Intent.parseUri(string, 1);
            }
        } catch (Exception e) {
            Log.e(TAG, "parseUri error", e);
        }
        if (!this.isRestoreActivity && (miuiExportStrategy = exportStrategy) != null) {
            miuiExportStrategy.initState();
        }
        checkRestore();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.d(TAG, "onSaveInstanceState");
        bundle.putBoolean(SAVE_KEY_IS_RESTORE, true);
        bundle.putBoolean(SAVE_IS_IN_EXPORT, this.restoreInExportState);
        Intent intent = this.shareIntent;
        if (intent != null) {
            bundle.putString(SAVE_KEY_INTENT, intent.toUri(1));
        }
        bundle.putIntegerArrayList(SAVE_KEY_SELECT_INDEX_LIST, (ArrayList) this.mPreViewAdapter.createCheckList());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        UIUtils.checkOrientationInFoldDevice(this);
        if (this.isInFullWindowGestureMode != UIUtils.isInFullWindowGestureMode(this)) {
            this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(this);
            UIUtils.setNavigationTrans(this);
        }
        if (this.mFromPrivate) {
            if (this.mInitResume) {
                this.mInitResume = false;
            } else {
                Log.d(TAG, "resume send broadcast");
                sendBroadcast(new Intent(INTENT_ACTIVITY_RESUME));
            }
        }
    }

    private void initialize() {
        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "intent is null");
            finish();
            return;
        }
        this.mContent = intent.getStringExtra("android.intent.extra.TEXT");
        this.mTitle = intent.getStringExtra("android.intent.extra.TITLE");
        this.mCreationTitle = intent.getStringExtra(EXTRA_KEY_CREATION_TITLE);
        this.mFromPrivate = intent.getBooleanExtra(EXTRA_KEY_PRIVATE, false);
        String str = this.mCreationTitle;
        if (str != null && !str.isEmpty() && this.mCreationTitle.charAt(0) == '.') {
            this.mCreationTitle = this.mCreationTitle.substring(1);
        }
        this.dirPath = intent.getStringExtra(EXTRA_KEY_DIR);
        this.entFilePath = intent.getStringExtra(EXTRA_KEY_ENT_PATH);
        List<Bitmap> bitmapPreviewList = ExportImageManager.INSTANCE.getInstance().getBitmapPreviewList();
        this.mBitmapList = bitmapPreviewList;
        if (bitmapPreviewList.isEmpty()) {
            Log.e(TAG, "error bitmap list empty");
            finish();
            return;
        }
        this.mShareType = intent.getIntExtra(EXTRA_KEY_TYPE, 0);
        Log.d(TAG, "imageList:" + this.mBitmapList.size());
        this.mShareContainer = (ViewGroup) findViewById(R.id.share_container);
        this.mPreviewRecycleView = (RecyclerView) findViewById(R.id.rv_preview);
        this.ivClose = (ImageView) findViewById(R.id.iv_close);
        TextView textView = (TextView) findViewById(R.id.creation_save_tittle);
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.share.ExportShareActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initialize$1(view);
            }
        });
        textView.setText(getString(1 == this.mShareType ? R.string.export_share_image : R.string.export_share_pdf));
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_title);
        this.titleBar = relativeLayout;
        ((LinearLayout.LayoutParams) relativeLayout.getLayoutParams()).topMargin = UIUtils.getStatusBarHeight(this);
        initPreView();
        initShareContainer(this.mShareContainer);
        showShareContainer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialize$1(View view) {
        onBackPressed();
    }

    private void checkRestore() {
        Log.d(TAG, "isRestoreActivity:" + this.isRestoreActivity);
        if (this.isRestoreActivity) {
            this.mPreViewAdapter.selectIndex(this.restoreSelectIndexList);
        }
    }

    private void initPreView() {
        RecyclerView.LayoutManager linearLayoutManager;
        this.mPreViewAdapter = new ImagePreViewAdapter(this, new CheckedCallBack() { // from class: com.miui.pad.feature.notes.share.ExportShareActivity$$ExternalSyntheticLambda2
            @Override // com.miui.pad.feature.notes.share.CheckedCallBack
            public final void onImageNumberChecked(boolean z) {
                this.f$0.lambda$initPreView$2(z);
            }
        });
        int i = 4;
        if (this.mBitmapList.size() > 1) {
            i = (DisplayUtils.isInMultiWindowMode(this) || UIUtils.isSmallWindowMode()) ? 3 : 4;
            linearLayoutManager = new GridLayoutManager(this, i);
        } else {
            linearLayoutManager = new LinearLayoutManager(this, 1, false);
        }
        this.mPreviewRecycleView.setLayoutManager(linearLayoutManager);
        this.mPreviewRecycleView.setAdapter(this.mPreViewAdapter);
        if (this.mBitmapList.size() > 1) {
            SingleViewTypeSpaceAroundDecoration singleViewTypeSpaceAroundDecoration = new SingleViewTypeSpaceAroundDecoration(0, getResources().getDimensionPixelOffset(R.dimen.chooser_item_horizontal_margin), i);
            this.mDecor = singleViewTypeSpaceAroundDecoration;
            this.mPreviewRecycleView.addItemDecoration(singleViewTypeSpaceAroundDecoration);
        }
        this.mPreViewAdapter.setImageList(this.mBitmapList);
        this.mPreViewAdapter.setImagePreviewClickCallback(new ImagePreViewAdapter.ImagePreviewClickCallback() { // from class: com.miui.pad.feature.notes.share.ExportShareActivity$$ExternalSyntheticLambda3
            @Override // com.miui.pad.feature.notes.share.ImagePreViewAdapter.ImagePreviewClickCallback
            public final void onPreviewClick(int i2) {
                this.f$0.lambda$initPreView$3(i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPreView$2(boolean z) {
        this.shareViewModel.setOneItemChecked(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPreView$3(int i) {
        ShareAppsAdapter shareAppsAdapter = this.mChooserAdapter;
        if (shareAppsAdapter == null || this.mShareType != 1) {
            return;
        }
        shareAppsAdapter.updateShareApp(i);
    }

    private void initShareContainer(View view) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.share_container);
        this.mShareContainer = viewGroup;
        viewGroup.setVisibility(8);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chooser_recycler);
        this.mChooserView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        RecyclerView recyclerView2 = this.mChooserView;
        recyclerView2.setPadding(recyclerView2.getPaddingLeft(), this.mChooserView.getPaddingTop(), this.mChooserView.getPaddingRight(), UIUtils.getNavigationBarHeight(this));
        ShareAppsAdapter shareAppsAdapter = new ShareAppsAdapter(this, null, true, this.mShareType, this.mBitmapList.size() > 1);
        this.mChooserAdapter = shareAppsAdapter;
        shareAppsAdapter.setOnIntentSelectedListener(new ShareAppsAdapter.OnSaveSelectedListener() { // from class: com.miui.pad.feature.notes.share.ExportShareActivity.3
            @Override // com.miui.pad.feature.notes.share.ShareAppsAdapter.OnSaveSelectedListener
            public void onSaveToLocal(int i) {
                ExportShareActivity.this.startShareClick(null);
            }

            @Override // com.miui.pad.feature.notes.share.ShareAppsAdapter.OnIntentSelectedListener
            public void onIntentSelected(Intent intent, int i) {
                ExportShareActivity.this.startShareClick(intent);
            }
        });
        this.mChooserView.setAdapter(this.mChooserAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startShareClick(Intent intent) {
        List<Integer> listCreateCheckList = this.mPreViewAdapter.createCheckList();
        if (listCreateCheckList.isEmpty()) {
            Toast.makeText(this, R.string.export_select_empty, 0).show();
            return;
        }
        showExportDialog();
        this.shareIntent = intent;
        MiuiExportStrategy miuiExportStrategy = exportStrategy;
        if (miuiExportStrategy != null) {
            miuiExportStrategy.setFileTitle(this.mCreationTitle);
            exportStrategy.setExportPageIndexList(listCreateCheckList);
            exportStrategy.initState();
        }
        if (this.mShareType == 1) {
            pageFileManager.exportImage();
        } else {
            pageFileManager.exportPdf();
        }
    }

    private void showShareContainer() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(getTypeString());
        this.mChooserAdapter.requery(intent);
        this.mShareContainer.setBackgroundResource(R.color.pad_note_sns_share_bg);
        this.mShareContainer.setVisibility(0);
    }

    private String getTypeString() {
        return this.mShareType == 1 ? ShareAppsAdapter.TYPE_IMAGE : ShareAppsAdapter.TYPE_PDF;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:18:0x0080  */
    public void doShare(Intent intent, int i) {
        Uri uri;
        Intent intent2;
        if (this.mFilePathList == null) {
            return;
        }
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        for (int i2 = 0; i2 < this.mFilePathList.size(); i2++) {
            Uri uriForFile = CompatFileProvider.getUriForFile(this, "com.miui.notes", new File(this.mFilePathList.get(i2)));
            arrayList.add(uriForFile);
            grantUriPermission(intent.getComponent().getPackageName(), uriForFile, 3);
        }
        if (arrayList.size() > 1) {
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
            intent.setAction("android.intent.action.SEND_MULTIPLE");
        } else {
            if (arrayList.size() == 1) {
                uri = (Uri) arrayList.get(0);
                intent.putExtra("android.intent.extra.STREAM", uri);
                intent.setAction("android.intent.action.SEND");
            }
            intent.setType(getTypeString());
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(DataSpace.RANGE_LIMITED);
            if (isIgnoreNewTask(i, intent.getComponent().getPackageName())) {
                intent.removeFlags(DataSpace.RANGE_LIMITED);
            }
            intent.addFlags(67108864);
            intent.addFlags(8388608);
            intent.addFlags(1);
            grantUriPermission(intent.getComponent().getPackageName(), uri, 3);
            intent2 = new Intent("miui.intent.action.XMAN_SHARE_MANAGER");
            intent2.setPackage("com.miui.xman");
            if ((!intent.getComponent().getClassName().equals(INTENT_CLASSNAME_SHARE_WECHAT) || intent.getComponent().getClassName().equals(INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS)) && isXmanShareSupport(this, intent2)) {
                grantUriPermission("com.miui.xman", uri, 3);
                intent2.putExtra("android.intent.extra.INTENT", intent);
                startActivityForResult(intent2, 2);
                return;
            }
            startActivityForResult(intent, 2);
        }
        uri = null;
        intent.setType(getTypeString());
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(DataSpace.RANGE_LIMITED);
        if (isIgnoreNewTask(i, intent.getComponent().getPackageName())) {
            intent.removeFlags(DataSpace.RANGE_LIMITED);
        }
        intent.addFlags(67108864);
        intent.addFlags(8388608);
        intent.addFlags(1);
        grantUriPermission(intent.getComponent().getPackageName(), uri, 3);
        intent2 = new Intent("miui.intent.action.XMAN_SHARE_MANAGER");
        intent2.setPackage("com.miui.xman");
        if (!intent.getComponent().getClassName().equals(INTENT_CLASSNAME_SHARE_WECHAT)) {
            grantUriPermission("com.miui.xman", uri, 3);
            intent2.putExtra("android.intent.extra.INTENT", intent);
            startActivityForResult(intent2, 2);
            return;
        } else {
            grantUriPermission("com.miui.xman", uri, 3);
            intent2.putExtra("android.intent.extra.INTENT", intent);
            startActivityForResult(intent2, 2);
            return;
        }
        startActivityForResult(intent, 2);
    }

    private boolean isIgnoreNewTask(int i, String str) {
        return i == 0 && "com.tencent.mm".equals(str);
    }

    private boolean isXmanShareSupport(Context context, Intent intent) {
        try {
            List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 32);
            return listQueryIntentActivities != null && listQueryIntentActivities.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showExportDialog() {
        if (this.exportProgressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            this.exportProgressDialog = progressDialog;
            progressDialog.setProgressStyle(1);
            this.exportProgressDialog.setMax(100);
            this.exportProgressDialog.setProgress(0);
            this.exportProgressDialog.setMessage(getString(1 == this.mShareType ? R.string.export_share_image : R.string.export_share_pdf));
            this.exportProgressDialog.setCancelable(false);
            this.exportProgressDialog.setCanceledOnTouchOutside(false);
        }
        this.restoreInExportState = true;
        this.exportProgressDialog.show();
    }

    private void dismissExportDialog(DialogInterface.OnDismissListener onDismissListener) {
        ProgressDialog progressDialog = this.exportProgressDialog;
        if (progressDialog != null) {
            progressDialog.setOnDismissListener(onDismissListener);
            this.exportProgressDialog.dismiss();
            this.exportProgressDialog = null;
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        ProgressDialog progressDialog = this.exportProgressDialog;
        if (progressDialog != null) {
            progressDialog.setOnDismissListener(null);
            this.exportProgressDialog.dismiss();
            this.exportProgressDialog = null;
        }
        LocalBroadcastManager.getInstance(NoteApp.getInstance()).unregisterReceiver(this.operationReceiver);
        if (isFinishing()) {
            ExportImageManager.INSTANCE.getInstance().previewFinish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.d(TAG, "onActivityResult" + i);
        if (i == 2) {
            finish();
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mBitmapList.size() <= 1 || this.mPreviewRecycleView == null) {
            return;
        }
        int i = (DisplayUtils.isInMultiWindowMode(this) || UIUtils.isSmallWindowMode()) ? 3 : 4;
        this.mPreviewRecycleView.setLayoutManager(new GridLayoutManager(this, i));
        this.mDecor.setSpanCount(i);
    }
}
