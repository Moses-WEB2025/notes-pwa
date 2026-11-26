package com.miui.notes.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.hardware.DataSpace;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.base.BaseActivity;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.tool.BlurHelper;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.maml.component.MamlView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.adapter.ResolverAdapter;
import com.miui.notes.ai.AigcPicMetaData;
import com.miui.notes.base.utils.BitmapUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.component.ThemeChooserGroup;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.model.SharedViewModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.TempFileManager;
import com.miui.notes.ui.jsbridge.MindShareDataHolder;
import com.miui.notes.ui.view.WeiboPreviewView;
import com.miui.richeditor.share.RenderBuilder;
import com.miui.richeditor.share.render.Render;
import com.miui.richeditor.theme.Theme;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import miuix.android.content.SystemIntent;
import miuix.appcompat.app.Fragment;
import miuix.appcompat.app.ProgressDialog;
import miuix.graphics.BitmapFactory;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public class SnsEditActivity extends BaseActivity implements DialogManagerInterface {
    private static final String EXTRA_KEY_IS_PRIVACY = "extra_is_privacy";
    private static final String EXTRA_KEY_MODIFIED_TIME = "extra_modified_time";
    private static final String EXTRA_KEY_SHARE_HAND_WRITE_BG_INDEX = "extra_share_hand_write_bg_index";
    private static final String EXTRA_KEY_SHARE_PIC = "extra_share_pic";
    private static final String EXTRA_KEY_SHARE_PICS = "extra_share_pics";
    private static final String EXTRA_KEY_TEXTSIZE = "extra_text_size";
    private static final String EXTRA_KEY_THEME = "extra_theme";
    private static final String EXTRA_KEY_TYPE = "extra_type";
    private static final String EXTRA_KEY_WORKING_NOTE = "extra_key_working_note";
    public static final int EXTRA_TYPE_AS_HYBRID = 3;
    public static final int EXTRA_TYPE_AS_IMAGE = 2;
    public static final int EXTRA_TYPE_WEIBO = 1;
    private static final String INTENT_CLASSNAME_SHARE_WECHAT = "com.tencent.mm.ui.tools.ShareImgUI";
    private static final String INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    private static String IS_HAND_WRITE = null;
    public static String KEY_AIGC_META = "AIGC";
    private static final int REQUEST_CODE_AS_IMAGE = 2;
    private static final int REQUEST_CODE_WEIBO = 1;
    private static final String TAG = "SnsEditActivity";
    protected Uri base64Uri;
    private int bgIndex;
    private boolean isHandWrite;
    private View mActionBarView;
    private View mBlurView;
    private ResolverAdapter mChooserAdapter;
    private RecyclerView mChooserView;
    private String mContent;
    private View mContentView;
    private Context mContext;
    private ProgressDialog mDialog;
    private BroadcastReceiver mFinishReceiver;
    protected Handler mHandler;
    protected Uri[] mHybridEditorRenderImgUris;
    private boolean mIsExpandThemeChooser;
    private boolean mIsForceDark;
    private boolean mIsThemeViewVisible;
    private Runnable mMamlRenderCallback;
    private FrameLayout mMamlViewContainer;
    private long mModifiedTime;
    private PasswordController mPasswordController;
    private WeiboPreviewView mPreview;
    private Runnable mPreviewCallBack;
    private FrameLayout mPreviewParent;
    private Render mRender;
    private Runnable mRenderCallback;
    private SaveToGalleryTask mSaveToGalleryTask;
    private ViewGroup mShareContainer;
    private File mShareFile;
    private LaunchSendActivityTask mShareGenerateTask;
    private List<Uri> mShareImages;
    private String mSharePicBase64;
    private int mShareType;
    protected ValueAnimator mThemeChooserCollapseAnimator;
    protected ValueAnimator mThemeChooserExpandAnimator;
    private ThemeChooserGroup mThemeChooserGroup;
    private View mThemeChooserView;
    private View mThemeContent;
    private int mThemeId;
    private String mTitle;
    private WorkingNote mWorkingNote;
    private String modifyDate;
    private ActivityResultLauncher<String[]> requestStoragePermissionLauncher;
    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> toXManResultLauncher;
    private boolean mIsFirstInit = true;
    private long mShowTime = 0;
    private int mNavigationBarHeight = 0;
    private Theme mTheme = null;
    protected LocalBroadcastManager mLocalBroadcastManager = null;
    private Runnable mShowCreatingProgressDialogTask = new AnonymousClass1();
    private final Runnable mRendTask = new Runnable() { // from class: com.miui.notes.ui.SnsEditActivity.2
        @Override // java.lang.Runnable
        public void run() {
            Logger.INSTANCE.d(SnsEditActivity.TAG, "mRender run" + Log.d(SnsEditActivity.TAG, "", new Throwable()));
            if (SnsEditActivity.this.mRender == null) {
                Logger.INSTANCE.e(SnsEditActivity.TAG, "mRender should not be null");
                return;
            }
            if (SnsEditActivity.this.mShareGenerateTask != null) {
                SnsEditActivity.this.mShareGenerateTask.cancel(true);
                SnsEditActivity.this.mShareGenerateTask = null;
            }
            SnsEditActivity snsEditActivity = SnsEditActivity.this;
            SnsEditActivity snsEditActivity2 = SnsEditActivity.this;
            snsEditActivity.mShareGenerateTask = new LaunchSendActivityTask(snsEditActivity2, snsEditActivity2.getIntent().getStringExtra(SnsEditActivity.KEY_AIGC_META));
            SnsEditActivity.this.mShareGenerateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    };
    protected ThemeChooserGroup.OnThemeChosenListener mOnThemeChosenListener = new ThemeChooserGroup.OnThemeChosenListener() { // from class: com.miui.notes.ui.SnsEditActivity.3
        @Override // com.miui.notes.component.ThemeChooserGroup.OnThemeChosenListener
        public void onThemeChosen(int i) {
            if (i == SnsEditActivity.this.mThemeId) {
                return;
            }
            SnsEditActivity snsEditActivity = SnsEditActivity.this;
            if (LiteUtils.isNewLiteOrMiddle()) {
                i = 0;
            }
            snsEditActivity.mThemeId = i;
            SnsEditActivity.this.mThemeChooserGroup.updateColor(SnsEditActivity.this.mThemeId);
            SnsEditActivity snsEditActivity2 = SnsEditActivity.this;
            snsEditActivity2.mTheme = ResourceManager.getTheme(snsEditActivity2.mThemeId);
            SnsEditActivity.this.getIntent().putExtra(SnsEditActivity.EXTRA_KEY_THEME, SnsEditActivity.this.mThemeId);
            SnsEditActivity.this.getWindow().getDecorView().postDelayed(SnsEditActivity.this.mShowCreatingProgressDialogTask, 0L);
            SnsEditActivity.this.startCreatePreview();
            SnsEditActivity.this.applyTheme();
            SnsEditActivity.this.mThemeContent.setVisibility(8);
            SnsEditActivity snsEditActivity3 = SnsEditActivity.this;
            snsEditActivity3.initShareContainer(snsEditActivity3.mShareContainer);
            SnsEditActivity.this.mIsExpandThemeChooser = false;
            SnsEditActivity.this.mIsThemeViewVisible = false;
        }
    };

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean dismissDialog() {
        return false;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Activity getThisActivity() {
        return this;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Fragment getThisFragment() {
        return null;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean isDialogShowing() {
        return false;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public void manageDialog(Dialog dialog) {
    }

    protected void updateWindowStatus(boolean z) {
    }

    public static void launchActivity(ActivityResultLauncher<Intent> activityResultLauncher, BaseActivity baseActivity, String str, String str2, int i, int i2, long j, float f, int i3, String str3, WorkingNote workingNote, AigcPicMetaData aigcPicMetaData) {
        Intent intent = new Intent(baseActivity, (Class<?>) SnsEditActivity.class);
        intent.putExtra("android.intent.extra.TEXT", str);
        intent.putExtra("android.intent.extra.TITLE", str2);
        intent.putExtra(EXTRA_KEY_TYPE, i);
        intent.putExtra(EXTRA_KEY_THEME, i2);
        intent.putExtra(EXTRA_KEY_MODIFIED_TIME, j);
        intent.putExtra(EXTRA_KEY_TEXTSIZE, f);
        if (aigcPicMetaData != null) {
            intent.putExtra(KEY_AIGC_META, aigcPicMetaData.toStr());
        }
        intent.putExtra(EXTRA_KEY_IS_PRIVACY, workingNote != null && workingNote.getFolderId() == -4);
        MindShareDataHolder.getInstance().setData(EXTRA_KEY_WORKING_NOTE, workingNote);
        MindShareDataHolder.getInstance().setData(EXTRA_KEY_SHARE_PIC, str3);
        if (Utils.bundleIsSmall(intent.getExtras())) {
            activityResultLauncher.launch(intent);
        }
    }

    public static void launchActivity(ActivityResultLauncher<Intent> activityResultLauncher, BaseActivity baseActivity, String str, String str2, String str3, int i, int i2, ArrayList<Uri> arrayList, int i3, long j, boolean z, AigcPicMetaData aigcPicMetaData) {
        Intent intent = new Intent(baseActivity, (Class<?>) SnsEditActivity.class);
        intent.putExtra("android.intent.extra.TEXT", str);
        intent.putExtra("android.intent.extra.TITLE", str2);
        intent.putExtra(IntentCompat.EXTRA_TIME, str3);
        intent.putExtra(EXTRA_KEY_TYPE, i);
        intent.putExtra(EXTRA_KEY_IS_PRIVACY, -4);
        intent.putParcelableArrayListExtra(EXTRA_KEY_SHARE_PICS, arrayList);
        intent.putExtra(EXTRA_KEY_SHARE_HAND_WRITE_BG_INDEX, i3);
        if (aigcPicMetaData != null) {
            intent.putExtra(KEY_AIGC_META, aigcPicMetaData.toStr());
        }
        if (Utils.bundleIsSmall(intent.getExtras())) {
            activityResultLauncher.launch(intent);
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.ui.SnsEditActivity$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Log.d(SnsEditActivity.TAG, "run init dialog: ");
            SnsEditActivity.this.mDialog = new ProgressDialog(SnsEditActivity.this);
            SnsEditActivity.this.mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.ui.SnsEditActivity$1$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    this.f$0.lambda$run$0(dialogInterface);
                }
            });
            SnsEditActivity.this.mDialog.setMessage(SnsEditActivity.this.getResources().getString(R.string.dlg_info_creating_message));
            SnsEditActivity.this.mDialog.setIndeterminate(true);
            SnsEditActivity.this.mDialog.setCanceledOnTouchOutside(false);
            if (SnsEditActivity.this.isFinishing()) {
                return;
            }
            SnsEditActivity.this.mShowTime = System.currentTimeMillis();
            SnsEditActivity.this.mDialog.show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(DialogInterface dialogInterface) {
            Logger.INSTANCE.d(SnsEditActivity.TAG, "run dialog is canceled: ");
            SnsEditActivity.this.mPreview.removeCallbacks(SnsEditActivity.this.mPreviewCallBack);
            if (SnsEditActivity.this.mShareGenerateTask != null) {
                SnsEditActivity.this.mShareGenerateTask.cancel(true);
            }
            SnsEditActivity.this.finish();
        }
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.mHandler = new Handler(Looper.getMainLooper());
        Intent intent = getIntent();
        if (intent != null) {
            int intExtra = LiteUtils.isNewLiteOrMiddle() ? 0 : intent.getIntExtra(EXTRA_KEY_THEME, 0);
            this.mThemeId = intExtra;
            Theme theme = ResourceManager.getTheme(intExtra);
            this.mTheme = theme;
            boolean z = theme.getStatusBarStyle(this).mStatusBar == 2;
            this.mIsForceDark = z;
            if (z) {
                setTheme(R.style.NoteTheme_Sns_Dark);
            }
        }
        super.onCreate(bundle);
        getWindow().addFlags(DataSpace.RANGE_FULL);
        setContentView(R.layout.v12_sns_edit_activity);
        this.mContentView = findViewById(R.id.content_view);
        this.mThemeChooserView = findViewById(R.id.themeview_container);
        this.mThemeContent = findViewById(R.id.theme_content);
        ThemeChooserGroup themeChooserGroup = new ThemeChooserGroup(this, this.mThemeChooserView, this.mThemeId);
        this.mThemeChooserGroup = themeChooserGroup;
        themeChooserGroup.setOnThemeChosenListener(this.mOnThemeChosenListener);
        this.mHandler.post(this.mShowCreatingProgressDialogTask);
        this.sharedViewModel = (SharedViewModel) new ViewModelProvider(this).get(SharedViewModel.class);
        initialize();
        initResultLauncher();
        setFinishBroadcastReceiver();
        this.mContext = this;
        smallWindowAdapter();
    }

    private void setFinishBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PasswordController.ACTIN_CANCEL);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.miui.notes.ui.SnsEditActivity.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                SnsEditActivity.this.finish();
                SnsEditActivity.this.mPasswordController = null;
            }
        };
        this.mFinishReceiver = broadcastReceiver;
        registerReceiver(broadcastReceiver, intentFilter, 2);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
    }

    private void initialize() {
        Intent intent = getIntent();
        if (intent == null) {
            Logger.INSTANCE.e(TAG, "intent is null");
            finish();
            return;
        }
        this.mContent = intent.getStringExtra("android.intent.extra.TEXT");
        this.mTitle = intent.getStringExtra("android.intent.extra.TITLE");
        if (TextUtils.isEmpty(this.mContent) && TextUtils.isEmpty(this.mTitle)) {
            Logger.INSTANCE.e(TAG, "text in intent is empty");
            finish();
            return;
        }
        this.mShareType = intent.getIntExtra(EXTRA_KEY_TYPE, 1);
        this.isHandWrite = intent.getBooleanExtra(IS_HAND_WRITE, false);
        Object data = MindShareDataHolder.getInstance().getData(EXTRA_KEY_WORKING_NOTE);
        if (data instanceof WorkingNote) {
            this.mWorkingNote = (WorkingNote) data;
        }
        this.mModifiedTime = intent.getLongExtra(EXTRA_KEY_MODIFIED_TIME, 0L);
        if (this.sharedViewModel.getSharePicBase64() == null) {
            Logger.INSTANCE.d(TAG, "initialize mSharePicBase64");
            String str = (String) MindShareDataHolder.getInstance().getData(EXTRA_KEY_SHARE_PIC);
            this.mSharePicBase64 = str;
            this.sharedViewModel.setSharePicBase64(str);
        } else {
            Logger.INSTANCE.d(TAG, "initialize recover mSharePicBase64");
            this.mSharePicBase64 = this.sharedViewModel.getSharePicBase64();
        }
        this.mShareImages = intent.getParcelableArrayListExtra(EXTRA_KEY_SHARE_PICS);
        this.bgIndex = intent.getIntExtra(EXTRA_KEY_SHARE_HAND_WRITE_BG_INDEX, 0);
        this.modifyDate = intent.getStringExtra(IntentCompat.EXTRA_TIME);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.share_container);
        this.mShareContainer = viewGroup;
        viewGroup.setVisibility(8);
        initShareContainer(this.mShareContainer);
        this.mPreviewParent = (FrameLayout) findViewById(R.id.preview_parent);
        this.mPreview = (WeiboPreviewView) findViewById(R.id.preview);
        startCreatePreview();
        applyTheme();
        this.mPreview.invalidate();
        if (intent.getBooleanExtra(EXTRA_KEY_IS_PRIVACY, false)) {
            PasswordController passwordController = new PasswordController(this, true);
            this.mPasswordController = passwordController;
            passwordController.setLockSateListener(new PasswordController.SimpleLockStateListener() { // from class: com.miui.notes.ui.SnsEditActivity.5
                @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
                public void onLockStateChanged(PasswordController passwordController2, int i) {
                    super.onLockStateChanged(passwordController2, i);
                    if (i == 0) {
                        SnsEditActivity.this.mContentView.setVisibility(8);
                    } else if (i == 2) {
                        SnsEditActivity.this.mContentView.setVisibility(0);
                    }
                }
            }, 2);
            getWindow().addFlags(8192);
        }
    }

    private int getActionButtonColorFromTheme(Theme theme) {
        Resources.Theme resTheme = theme.getResTheme(this);
        TypedValue typedValue = new TypedValue();
        resTheme.resolveAttribute(R.attr.actionButtonColor, typedValue, true);
        return typedValue.data;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyTheme() {
        try {
            setActionBar(this.mTheme);
            setBackground(this.mTheme);
            setThemeChooserBackGround(this.mTheme);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error in applyTheme " + e.toString());
        }
    }

    private void setActionBar(Theme theme) {
        WorkingNote workingNote;
        int actionButtonColorFromTheme = getActionButtonColorFromTheme(theme);
        theme.getStatusBarStyle(this).apply(this);
        getAppCompatActionBar().setBackgroundDrawable(getDrawable(R.drawable.v8_placeholder));
        getAppCompatActionBar().setDisplayHomeAsUpEnabled(false);
        getAppCompatActionBar().setDisplayShowTitleEnabled(false);
        getAppCompatActionBar().setDisplayShowCustomEnabled(true);
        if (this.mActionBarView == null) {
            this.mActionBarView = UIUtils.inflateView((ViewGroup) getWindow().getDecorView(), UIUtils.isMiuiXIISdkSupported() ? R.layout.v12_sns_edit_actionbar : R.layout.sns_edit_actionbar);
        }
        getAppCompatActionBar().setCustomView(this.mActionBarView, new ActionBar.LayoutParams(-1, -2));
        TextView textView = (TextView) getAppCompatActionBar().getCustomView().findViewById(R.id.title);
        textView.setTextColor(ColorStateList.valueOf(actionButtonColorFromTheme));
        ImageView imageView = (ImageView) getAppCompatActionBar().getCustomView().findViewById(R.id.home);
        imageView.setImageTintList(ColorStateList.valueOf(actionButtonColorFromTheme));
        ImageView imageView2 = (ImageView) getAppCompatActionBar().getCustomView().findViewById(R.id.view_theme);
        imageView2.setImageTintList(ColorStateList.valueOf(actionButtonColorFromTheme));
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ui.SnsEditActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SnsEditActivity.this.onBackPressed();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ui.SnsEditActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SnsEditActivity.this.toggleThemeChooserFromHead();
            }
        });
        if (this.isHandWrite || (workingNote = this.mWorkingNote) == null || !TextUtils.equals("common", workingNote.getWorkingNoteType()) || LiteUtils.isNewLiteOrMiddle()) {
            imageView2.setVisibility(8);
        }
        if (this.mIsForceDark) {
            Resources.Theme themeNewTheme = getResources().newTheme();
            themeNewTheme.applyStyle(R.style.NoteTheme_Edit_Dark, true);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_back, themeNewTheme));
            imageView2.setImageDrawable(getResources().getDrawable(R.drawable.v12_ic_action_theme, themeNewTheme));
            textView.setTextColor(ColorStateList.valueOf(actionButtonColorFromTheme));
        } else {
            Resources.Theme resTheme = theme.getActionBarStyle(this).getResTheme();
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_back, resTheme));
            imageView2.setImageDrawable(getResources().getDrawable(R.drawable.v12_ic_action_theme, resTheme));
            textView.setTextColor(ColorStateList.valueOf(actionButtonColorFromTheme));
        }
        textView.setText(R.string.sns_title_edit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleThemeChooserFromHead() {
        if (this.mThemeChooserView == null) {
            this.mThemeChooserView = findViewById(R.id.themeview_container);
        }
        if (!this.mIsThemeViewVisible) {
            this.mIsThemeViewVisible = true;
            this.mShareContainer.setVisibility(8);
            this.mThemeContent.setVisibility(0);
            this.mThemeContent.measure(View.MeasureSpec.makeMeasureSpec(0, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(getResources().getDimensionPixelSize(R.dimen.note_edit_toolbar_content_height) + this.mNavigationBarHeight, 1073741824));
            showThemeChooserView(0, this.mThemeContent.getMeasuredHeight(), null);
            return;
        }
        this.mIsThemeViewVisible = false;
        hideThemeChooserView(this.mThemeContent.getMeasuredHeight(), 0, null);
        this.mShareContainer.setVisibility(0);
    }

    private void setBackground(Theme theme) {
        ViewGroup viewGroup = (ViewGroup) this.mPreviewParent.getRootView().findViewById(android.R.id.content).getParent();
        if (this.mBlurView == null) {
            this.mBlurView = new View(this);
        }
        this.mBlurView.setBackground(null);
        Theme.EditorStyle editorStyle = theme.getEditorStyle(this, System.currentTimeMillis());
        BlurHelper.setBlurBackground(this.mBlurView, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor3);
        if (this.mIsFirstInit) {
            if (theme instanceof AnimTheme) {
                MamlView backgroundViewForSns = ((AnimTheme) theme).getBackgroundViewForSns(getApplicationContext());
                if (backgroundViewForSns != null) {
                    viewGroup.addView(backgroundViewForSns, 0);
                    View view = this.mBlurView;
                    if (view != null) {
                        viewGroup.addView(view, 1, new ViewGroup.LayoutParams(-1, -1));
                    }
                    backgroundViewForSns.init();
                }
            } else {
                viewGroup.setBackground(editorStyle.mBackground);
                View view2 = this.mBlurView;
                if (view2 != null) {
                    viewGroup.addView(view2, 0, new ViewGroup.LayoutParams(-1, -1));
                }
            }
            this.mIsFirstInit = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCreatePreview() {
        this.mPreviewCallBack = new Runnable() { // from class: com.miui.notes.ui.SnsEditActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$startCreatePreview$0();
            }
        };
        if (!this.mIsFirstInit) {
            this.mPreview.setVisibility(4);
        }
        this.mHandler.post(this.mPreviewCallBack);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startCreatePreview$0() {
        Logger.INSTANCE.d(TAG, "mPreviewCallBack run");
        Theme theme = ResourceManager.getTheme(this.mThemeId);
        if (theme == null) {
            theme = ResourceManager.getTheme(0);
        }
        if (theme instanceof AnimTheme) {
            updateAnimPreview((AnimTheme) theme);
        } else {
            updatePreview();
        }
    }

    private void updatePreview() {
        Logger.INSTANCE.d(TAG, "updatePreview");
        Theme theme = ResourceManager.getTheme(this.mThemeId);
        if (theme == null) {
            theme = ResourceManager.getTheme(0);
        }
        Theme.ShareStyle shareStyle = theme.getShareStyle(NoteApp.getInstance(), this.mModifiedTime);
        Logger.INSTANCE.d(TAG, "updatePreview1");
        int i = (theme.getId() == 0 && UIUtils.isNightMode(this)) ? R.drawable.ic_richtext_share_checkbox_u_light : R.drawable.ic_richtext_share_checkbox_u;
        Logger.INSTANCE.d(TAG, "updatePreview2");
        if (this.mShareImages != null) {
            this.mRender = new RenderBuilder(this).setStyle(shareStyle).setTitle(this.mTitle).buildHandWrite(this.mShareImages, this.bgIndex, this.modifyDate);
        } else {
            int i2 = this.mShareType;
            if (i2 == 2) {
                if (TextUtils.isEmpty(this.mSharePicBase64)) {
                    Logger.INSTANCE.d(TAG, "updatePreview Share img mContent ==" + this.mContent + "mTitle ==" + this.mTitle + " == " + this.mThemeId);
                    this.mRender = new RenderBuilder(this).setContent(this.mContent).setTitle(TextUtils.isEmpty(this.mTitle) ? "" : this.mTitle).setThemeId(this.mThemeId).setStyle(shareStyle).setCheckRes(R.drawable.ic_richtext_share_checkbox_c).setUnCheckRes(i).build();
                } else {
                    Logger.INSTANCE.d(TAG, "updatePreview Share img  null");
                    Bitmap bitmapBase64ToBitmap = ImageUtils.INSTANCE.base64ToBitmap(this.mSharePicBase64);
                    this.base64Uri = createTempUri(this, bitmapBase64ToBitmap, false);
                    this.mRender = new RenderBuilder(this).buildFromUri(this.base64Uri);
                    Logger.INSTANCE.d(TAG, "build img from base64 bmp");
                    if (bitmapBase64ToBitmap != null && !bitmapBase64ToBitmap.isRecycled()) {
                        bitmapBase64ToBitmap.recycle();
                    }
                }
            } else if (i2 == 3) {
                if (TextUtils.isEmpty(this.mSharePicBase64)) {
                    this.mRender = new RenderBuilder(this).setContent(this.mContent).setTitle(TextUtils.isEmpty(this.mTitle) ? "" : this.mTitle).setThemeId(this.mThemeId).setStyle(shareStyle).setCheckRes(R.drawable.ic_richtext_share_checkbox_c).setUnCheckRes(i).build();
                } else {
                    Bitmap bitmapBase64ToBitmap2 = ImageUtils.INSTANCE.base64ToBitmap(this.mSharePicBase64);
                    this.base64Uri = createTempUri(this, bitmapBase64ToBitmap2, true);
                    this.mRender = new RenderBuilder(this).setThemeId(this.mThemeId).setStyle(shareStyle).buildFromHybrid(this.base64Uri);
                    if (bitmapBase64ToBitmap2 != null && !bitmapBase64ToBitmap2.isRecycled()) {
                        bitmapBase64ToBitmap2.recycle();
                    }
                }
            }
        }
        Logger.INSTANCE.d(TAG, "updatePreview0: mRender = " + this.mRender);
        if (this.mRender != null) {
            Logger.INSTANCE.d(TAG, "updatePreview: mRender = " + this.mRender);
            this.mPreviewParent.post(new Runnable() { // from class: com.miui.notes.ui.SnsEditActivity$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updatePreview$1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePreview$1() {
        if (this.mRender != null) {
            Logger.INSTANCE.d(TAG, "updatePreview: mRendTask = " + this.mRendTask);
            this.mRendTask.run();
            this.mPreview.setRender(this.mRender);
            this.mPreview.requestLayout();
        }
    }

    private void updateAnimPreview(AnimTheme animTheme) {
        final Theme.ShareStyle shareStyle = animTheme.getShareStyle(NoteApp.getInstance(), this.mModifiedTime);
        final int i = (animTheme.getId() == 0 && UIUtils.isNightMode(this)) ? R.drawable.ic_richtext_share_checkbox_u_light : R.drawable.ic_richtext_share_checkbox_u;
        final MamlView backgroundViewForSns = animTheme.getBackgroundViewForSns(getApplicationContext());
        backgroundViewForSns.setId(R.id.content_group);
        backgroundViewForSns.init();
        backgroundViewForSns.sendCommand("init");
        FrameLayout frameLayout = new FrameLayout(this);
        this.mMamlViewContainer = frameLayout;
        frameLayout.addView(backgroundViewForSns, new FrameLayout.LayoutParams(-1, -1));
        this.mMamlViewContainer.setAlpha(0.0f);
        this.mPreviewParent.addView(this.mMamlViewContainer, 0, new FrameLayout.LayoutParams(-1, -1));
        this.mRenderCallback = new RenderThread(this);
        if (this.mShareType == 3) {
            this.mMamlRenderCallback = new Runnable() { // from class: com.miui.notes.ui.SnsEditActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateAnimPreview$2();
                }
            };
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ui.SnsEditActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateAnimPreview$3(shareStyle, i, backgroundViewForSns);
                }
            }, 400L);
        } else {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ui.SnsEditActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateAnimPreview$4(shareStyle, i, backgroundViewForSns);
                }
            }, 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAnimPreview$2() {
        if (this.mRender != null) {
            this.mRendTask.run();
            this.mPreview.setRender(this.mRender);
            this.mPreview.requestLayout();
            this.mPreviewParent.removeView(this.mMamlViewContainer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAnimPreview$3(Theme.ShareStyle shareStyle, int i, MamlView mamlView) {
        this.mRender = new RenderBuilder(this).setContent(this.mContent).setTitle(TextUtils.isEmpty(this.mTitle) ? "" : this.mTitle).setThemeId(this.mThemeId).setStyle(shareStyle).setCheckRes(R.drawable.ic_richtext_share_checkbox_c).setUnCheckRes(i).buildWithCallback(mamlView, this.mMamlRenderCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAnimPreview$4(Theme.ShareStyle shareStyle, int i, MamlView mamlView) {
        if (this.mShareType == 2) {
            Logger.INSTANCE.d(TAG, "updateAnimPreview Share img mContent ==" + this.mContent + "mTitle ==" + this.mTitle + " == " + this.mThemeId);
            this.mRender = new RenderBuilder(this).setContent(this.mContent).setTitle(TextUtils.isEmpty(this.mTitle) ? "" : this.mTitle).setThemeId(this.mThemeId).setStyle(shareStyle).setCheckRes(R.drawable.ic_richtext_share_checkbox_c).setUnCheckRes(i).buildWithCallback(mamlView, this.mRenderCallback);
        }
    }

    private static class RenderThread implements Runnable {
        private WeakReference<SnsEditActivity> mSnsEditActivityRef;

        public RenderThread(SnsEditActivity snsEditActivity) {
            this.mSnsEditActivityRef = new WeakReference<>(snsEditActivity);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mSnsEditActivityRef.get().doRenderCallBack();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRenderCallBack() {
        if (this.mRender != null) {
            this.mRendTask.run();
            this.mPreview.setRender(this.mRender);
            this.mPreview.requestLayout();
            this.mPreviewParent.removeView(this.mMamlViewContainer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelShowCreatingDialog() {
        Log.d(TAG, "cancelShowCreatingDialog: ", new Throwable());
        Logger.INSTANCE.d(TAG, "cancelShowCreatingDialog dialog is canceled: ");
        getWindow().getDecorView().removeCallbacks(this.mShowCreatingProgressDialogTask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialogWithJudge() {
        Log.d(TAG, "dismissProgressDialogWithJudge: ", new Throwable());
        if (Math.abs(System.currentTimeMillis() - this.mShowTime) < 100) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ui.SnsEditActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.dismissProgressDialog();
                }
            }, 100L);
        } else {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.ui.SnsEditActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.dismissProgressDialog();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialog() {
        Log.d(TAG, "dismissProgressDialog: ", new Throwable());
        Log.d(TAG, "dismissProgressDialog mDialog: " + this.mDialog);
        ProgressDialog progressDialog = this.mDialog;
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                Log.d(TAG, "dismissProgressDialog dismiss exception ");
                e.printStackTrace();
            }
            this.mDialog = null;
        }
        Log.d(TAG, "dismissProgressDialog mPreview: " + this.mPreview);
        WeiboPreviewView weiboPreviewView = this.mPreview;
        if (weiboPreviewView != null) {
            weiboPreviewView.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initShareContainer(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chooser_recycler);
        this.mChooserView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        ResolverAdapter resolverAdapter = new ResolverAdapter(this, null, this.mTheme, true);
        this.mChooserAdapter = resolverAdapter;
        resolverAdapter.setOnIntentSelectedListener(new ResolverAdapter.OnImageSelectedListener() { // from class: com.miui.notes.ui.SnsEditActivity.8
            @Override // com.miui.notes.adapter.ResolverAdapter.OnImageSelectedListener
            public void onSaveToGallery() {
                if (SnsEditActivity.this.mShareFile == null) {
                    return;
                }
                SnsEditActivity.this.checkPermissionAndSaveToGallery();
            }

            @Override // com.miui.notes.adapter.ResolverAdapter.OnIntentSelectedListener
            public void onIntentSelected(Intent intent) {
                if (SnsEditActivity.this.mShareFile != null) {
                    SnsEditActivity.this.doShare(intent, Uri.fromFile(SnsEditActivity.this.mShareFile));
                }
            }
        });
        this.mChooserView.setAdapter(this.mChooserAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPermissionAndSaveToGallery() {
        if (Utils.isAboveSDK33() || LiteUtils.isSuperLite()) {
            doSaveToGallery();
        } else {
            PermissionNewUtils.requestOnStoragePermission(this.requestStoragePermissionLauncher);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showShareContainer(File file) {
        Logger.INSTANCE.d(TAG, "showShareContainer:" + file.getPath());
        this.mShareFile = file;
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/jpeg");
        this.mChooserAdapter.requery(intent);
        Theme.EditorStyle editorStyle = this.mTheme.getEditorStyle(this, System.currentTimeMillis());
        BlurHelper.setBlurBackground(this.mShareContainer, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor3);
        this.mShareContainer.setVisibility(0);
        this.mPreview.setVisibility(0);
        this.mPreview.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doShare(Intent intent, Uri uri) {
        Uri compatUri = CompatUtils.getCompatUri(this, uri);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/jpeg");
        intent.putExtra("android.intent.extra.STREAM", compatUri);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(DataSpace.RANGE_LIMITED);
        intent.addFlags(67108864);
        intent.addFlags(8388608);
        intent.addFlags(1);
        grantUriPermission(intent.getComponent().getPackageName(), compatUri, 3);
        Intent intent2 = new Intent("miui.intent.action.XMAN_SHARE_MANAGER");
        intent2.setPackage("com.miui.xman");
        if ((intent.getComponent().getClassName().equals(INTENT_CLASSNAME_SHARE_WECHAT) || intent.getComponent().getClassName().equals(INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS)) && isXmanShareSupport(this, intent2)) {
            grantUriPermission("com.miui.xman", compatUri, 3);
            intent2.putExtra("android.intent.extra.INTENT", intent);
            this.toXManResultLauncher.launch(intent2);
            return;
        }
        this.toXManResultLauncher.launch(intent);
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

    private void doSaveToGallery() {
        if (this.mShareFile == null) {
            return;
        }
        SaveToGalleryTask saveToGalleryTask = this.mSaveToGalleryTask;
        if (saveToGalleryTask != null) {
            saveToGalleryTask.cancel(true);
            this.mSaveToGalleryTask = null;
        }
        SaveToGalleryTask saveToGalleryTask2 = new SaveToGalleryTask(this);
        this.mSaveToGalleryTask = saveToGalleryTask2;
        saveToGalleryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.mShareFile);
    }

    static class LaunchSendActivityTask extends AsyncTask<Void, Void, Void> {
        private String TAG = "LaunchSendActivityTask";
        private WeakReference<SnsEditActivity> iActivityRef;
        private Context iContext;
        private Uri iPreviewUri;
        private File iShareFile;
        private int iShareType;
        private Uri iThumbnailUri;
        private final String metaAiInfo;

        public LaunchSendActivityTask(SnsEditActivity snsEditActivity, String str) {
            this.iActivityRef = new WeakReference<>(snsEditActivity);
            this.iContext = snsEditActivity.getApplicationContext();
            this.metaAiInfo = str;
            SnsEditActivity snsEditActivity2 = this.iActivityRef.get();
            if (snsEditActivity2 != null) {
                this.iShareType = snsEditActivity2.mShareType;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            Logger.INSTANCE.d(this.TAG, "sns edit act /doInBackground!");
            Thread.currentThread().setName("LaunchSendActivityTask");
            if (isCancelled()) {
                Logger.INSTANCE.i(this.TAG, "isCancelled, return.");
                return null;
            }
            try {
                this.iShareFile = writeShareUrl2File();
                if (this.metaAiInfo != null) {
                    Logger.INSTANCE.d(this.TAG, "set ret:" + BitmapUtils.INSTANCE.writeExifMetaData(this.iShareFile, ExifInterface.TAG_USER_COMMENT, this.metaAiInfo) + ",with value:" + BitmapUtils.INSTANCE.getMetaExifData(this.iShareFile, ExifInterface.TAG_USER_COMMENT));
                }
            } catch (Exception e) {
                Logger.INSTANCE.d(this.TAG, e.getMessage());
            }
            File file = this.iShareFile;
            if (file != null) {
                Uri uriFromFile = Uri.fromFile(file);
                this.iPreviewUri = uriFromFile;
                if (this.iShareType == 1) {
                    this.iThumbnailUri = getThumbnailUri(this.iContext, uriFromFile);
                }
                Logger.INSTANCE.d(this.TAG, "build share preview uri success!");
            } else {
                Logger.INSTANCE.e(this.TAG, "Fail to get preview file");
            }
            return null;
        }

        private File writeShareUrl2File() {
            File fileCreateFile = TempFileManager.getInstance().createFile("share", Utils.JPG_SUFFIX);
            if (fileCreateFile == null) {
                Logger.INSTANCE.e(this.TAG, "Fail to getShareFile, shareFile is null");
                return null;
            }
            if (this.iActivityRef.get() != null && this.iActivityRef.get().mRender != null) {
                if (this.iActivityRef.get().mRender.saveToFile(fileCreateFile)) {
                    fileCreateFile.setReadable(true, false);
                    fileCreateFile.setWritable(true, false);
                    Logger.INSTANCE.d(this.TAG, "writeShareUrl2File success!!");
                    return fileCreateFile;
                }
                Logger.INSTANCE.e(this.TAG, "Fail to getShareFile, saveToFile failed");
                return null;
            }
            Logger.INSTANCE.e(this.TAG, "Fail to getShareFile, wk is null failed");
            return null;
        }

        private Uri getThumbnailUri(Context context, Uri uri) {
            Bitmap thumbnailBitmap = ImageUtils.INSTANCE.getThumbnailBitmap(context, uri);
            if (thumbnailBitmap == null) {
                Logger.INSTANCE.e(this.TAG, "Fail to get thumbnail");
                return null;
            }
            File fileCreateFile = TempFileManager.getInstance().createFile("thumbnail", Utils.JPG_SUFFIX);
            if (fileCreateFile == null) {
                Logger.INSTANCE.e(this.TAG, "Fail to create thumbnail file");
                return null;
            }
            try {
                BitmapFactory.saveToFile(thumbnailBitmap, fileCreateFile.getPath());
                if (thumbnailBitmap != null && !thumbnailBitmap.isRecycled()) {
                    thumbnailBitmap.recycle();
                }
                return Uri.fromFile(fileCreateFile);
            } catch (IOException e) {
                Logger.INSTANCE.e(this.TAG, "Fail to save thumbnail,exception:" + e);
                return null;
            }
        }

        @Override // android.os.AsyncTask
        protected void onCancelled() {
            SnsEditActivity snsEditActivity = this.iActivityRef.get();
            if (snsEditActivity != null && snsEditActivity.mShareGenerateTask == this) {
                snsEditActivity.mShareGenerateTask = null;
                snsEditActivity.cancelShowCreatingDialog();
                snsEditActivity.dismissProgressDialogWithJudge();
            }
            super.onCancelled();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r4) {
            SnsEditActivity snsEditActivity = this.iActivityRef.get();
            if (snsEditActivity != null) {
                if (snsEditActivity.mShareGenerateTask != this) {
                    Logger.INSTANCE.w(this.TAG, "task has changed, don't apply result");
                    return;
                }
                snsEditActivity.mShareGenerateTask = null;
                snsEditActivity.cancelShowCreatingDialog();
                Logger.INSTANCE.d(SnsEditActivity.TAG, "LaunchSendActivityTask onPostExecute");
                snsEditActivity.dismissProgressDialogWithJudge();
                if (this.iPreviewUri == null) {
                    Logger.INSTANCE.e(this.TAG, "Fail to get preview file");
                } else {
                    snsEditActivity.showShareContainer(this.iShareFile);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.lock();
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        smallWindowAdapter();
        getWindow().addFlags(DataSpace.RANGE_FULL);
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.unlock();
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Logger.INSTANCE.d(TAG, "isFinishing " + isFinishing());
        try {
            unregisterReceiver(this.mFinishReceiver);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "onDestroy receiver is unregister or canceled: " + e);
        }
        if (isFinishing()) {
            deleteTempUri();
        }
        PermissionUtils.releaseListeners();
        Render render = this.mRender;
        if (render != null) {
            render.destroy();
            this.mRender = null;
        }
        cancelShowCreatingDialog();
        ProgressDialog progressDialog = this.mDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Log.d(TAG, "onDestroy set null to mDialog ");
        this.mDialog = null;
        LaunchSendActivityTask launchSendActivityTask = this.mShareGenerateTask;
        if (launchSendActivityTask != null) {
            launchSendActivityTask.cancel(true);
        }
        if (this.mMamlRenderCallback != null) {
            this.mMamlRenderCallback = null;
        }
        this.mShowCreatingProgressDialogTask = null;
        ResourceManager.clearThemes();
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            passwordController.destroy();
        }
        this.mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        smallWindowAdapter();
        getWindow().addFlags(DataSpace.RANGE_FULL);
    }

    static class SaveToGalleryTask extends AsyncTask<File, Void, String> {
        private WeakReference<SnsEditActivity> iActivityRef;
        private Context iContext;

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
        }

        public SaveToGalleryTask(SnsEditActivity snsEditActivity) {
            this.iActivityRef = new WeakReference<>(snsEditActivity);
            this.iContext = snsEditActivity.getApplicationContext();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Code duplicated, block: B:64:0x00a7 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:65:0x00a9 A[Catch: IOException -> 0x00a5, TRY_LEAVE, TryCatch #2 {IOException -> 0x00a5, blocks: (B:61:0x00a1, B:65:0x00a9), top: B:70:0x00a1 }] */
        /* JADX WARN: Code duplicated, block: B:70:0x00a1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v18 */
        /* JADX WARN: Type inference failed for: r9v3, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r9v8 */
        @Override // android.os.AsyncTask
        public String doInBackground(File... fileArr) throws Throwable {
            File file;
            ?? r9;
            FileOutputStream fileOutputStream;
            FileInputStream fileInputStream;
            Thread.currentThread().setName("SaveToGalleryTask");
            FileInputStream fileInputStream2 = null;
            if (fileArr == null || fileArr.length < 1 || (file = fileArr[0]) == null || !file.exists()) {
                return null;
            }
            File fileObtainJpgFile = Utils.obtainJpgFile(this.iContext);
            if (file != null) {
                try {
                    try {
                        if (fileObtainJpgFile != null) {
                            try {
                                fileInputStream = new FileInputStream(file);
                                try {
                                    fileOutputStream = new FileOutputStream(fileObtainJpgFile);
                                    try {
                                        byte[] bArr = new byte[1024];
                                        while (true) {
                                            int i = fileInputStream.read(bArr);
                                            if (i == -1) {
                                                break;
                                            }
                                            fileOutputStream.write(bArr, 0, i);
                                        }
                                        fileInputStream.close();
                                    } catch (FileNotFoundException e) {
                                        e = e;
                                        e.printStackTrace();
                                        if (fileInputStream != null) {
                                            fileInputStream.close();
                                        }
                                        if (fileOutputStream != null) {
                                        }
                                        MediaScannerConnection.scanFile(this.iContext, new String[]{fileObtainJpgFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.notes.ui.SnsEditActivity.SaveToGalleryTask.1
                                            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                            public void onScanCompleted(String str, Uri uri) {
                                                Logger.INSTANCE.i(SnsEditActivity.TAG, "Scanned " + str + " completely.");
                                            }
                                        });
                                        this.iContext.sendBroadcast(new Intent(SystemIntent.ACTION_SAVE_TO_CLOUD_GALLERY));
                                        return fileObtainJpgFile.getAbsolutePath();
                                    } catch (IOException e2) {
                                        e = e2;
                                        e.printStackTrace();
                                        if (fileInputStream != null) {
                                            fileInputStream.close();
                                        }
                                        if (fileOutputStream != null) {
                                        }
                                        MediaScannerConnection.scanFile(this.iContext, new String[]{fileObtainJpgFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.notes.ui.SnsEditActivity.SaveToGalleryTask.1
                                            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                            public void onScanCompleted(String str, Uri uri) {
                                                Logger.INSTANCE.i(SnsEditActivity.TAG, "Scanned " + str + " completely.");
                                            }
                                        });
                                        this.iContext.sendBroadcast(new Intent(SystemIntent.ACTION_SAVE_TO_CLOUD_GALLERY));
                                        return fileObtainJpgFile.getAbsolutePath();
                                    }
                                } catch (FileNotFoundException e3) {
                                    e = e3;
                                    fileOutputStream = null;
                                } catch (IOException e4) {
                                    e = e4;
                                    fileOutputStream = null;
                                } catch (Throwable th) {
                                    th = th;
                                    file = null;
                                    fileInputStream2 = fileInputStream;
                                    r9 = file;
                                    if (fileInputStream2 != null) {
                                        try {
                                            fileInputStream2.close();
                                            if (r9 != 0) {
                                                r9.close();
                                            }
                                        } catch (IOException e5) {
                                            e5.printStackTrace();
                                            throw th;
                                        }
                                    } else if (r9 != 0) {
                                        r9.close();
                                    }
                                    throw th;
                                }
                            } catch (FileNotFoundException e6) {
                                e = e6;
                                fileOutputStream = null;
                                fileInputStream = null;
                            } catch (IOException e7) {
                                e = e7;
                                fileOutputStream = null;
                                fileInputStream = null;
                            } catch (Throwable th2) {
                                th = th2;
                                r9 = 0;
                                if (fileInputStream2 != null) {
                                    fileInputStream2.close();
                                    if (r9 != 0) {
                                        r9.close();
                                    }
                                } else if (r9 != 0) {
                                    r9.close();
                                }
                                throw th;
                            }
                            fileOutputStream.close();
                            MediaScannerConnection.scanFile(this.iContext, new String[]{fileObtainJpgFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.notes.ui.SnsEditActivity.SaveToGalleryTask.1
                                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                public void onScanCompleted(String str, Uri uri) {
                                    Logger.INSTANCE.i(SnsEditActivity.TAG, "Scanned " + str + " completely.");
                                }
                            });
                            this.iContext.sendBroadcast(new Intent(SystemIntent.ACTION_SAVE_TO_CLOUD_GALLERY));
                            return fileObtainJpgFile.getAbsolutePath();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            SnsEditActivity snsEditActivity = this.iActivityRef.get();
            if (snsEditActivity != null && snsEditActivity.mSaveToGalleryTask != this) {
                Logger.INSTANCE.w(SnsEditActivity.TAG, "Task has changed, don't apply result");
                return;
            }
            snsEditActivity.mSaveToGalleryTask = null;
            snsEditActivity.dismissProgressDialogWithJudge();
            if (str == null) {
                Toast.makeText(NoteApp.getInstance(), snsEditActivity.getResources().getString(R.string.save_to_local_failure_message), 0).show();
            } else {
                Toast.makeText(NoteApp.getInstance(), snsEditActivity.getResources().getString(R.string.save_to_local_success_message), 0).show();
                ExternalAppUtils.openGallery(snsEditActivity);
            }
        }
    }

    public static Uri createTempUri(Context context, Bitmap bitmap, boolean z) {
        File file = new File(NoteApp.getInstance().getExternalCacheDir().getAbsolutePath() + InternalZipConstants.ZIP_FILE_SEPARATOR + ("IMG_SHARE_" + System.currentTimeMillis() + (z ? ".png" : AttachmentUtils.IMAGE_SUFFIX_JPEG)));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, " createNewFile error: " + e);
                return null;
            }
        }
        Uri compatUri = CompatUtils.getCompatUri(NoteApp.getInstance(), file);
        ContentResolver contentResolver = context.getContentResolver();
        try {
            OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(compatUri);
            try {
                if (z) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStreamOpenOutputStream);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStreamOpenOutputStream);
                }
                if (outputStreamOpenOutputStream != null) {
                    outputStreamOpenOutputStream.close();
                }
                return compatUri;
            } catch (Throwable th) {
                if (outputStreamOpenOutputStream != null) {
                    try {
                        outputStreamOpenOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception unused) {
            contentResolver.delete(compatUri, null, null);
            return null;
        }
    }

    protected void deleteTempUri() {
        Uri[] uriArr = this.mHybridEditorRenderImgUris;
        if (uriArr != null && uriArr.length > 0) {
            for (int i = 0; i < this.mHybridEditorRenderImgUris.length; i++) {
                try {
                    getContentResolver().delete(this.mHybridEditorRenderImgUris[i], null, null);
                } catch (Exception e) {
                    Logger.INSTANCE.w(TAG, "HybridEditorRenderImg delete fail,  e:" + e);
                }
            }
            this.mHybridEditorRenderImgUris = null;
        }
        try {
            if (this.base64Uri != null) {
                getContentResolver().delete(this.base64Uri, null, null);
            }
        } catch (Exception e2) {
            Logger.INSTANCE.e(TAG, "base64Uri delete fail: " + this.base64Uri.toString() + " e:" + e2);
        }
        try {
            AttachmentUtils.deleteHandWriteShareImage();
        } catch (Exception e3) {
            Logger.INSTANCE.w(TAG, "HandWriteShareImage delete fail e:" + e3);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mIsExpandThemeChooser && this.mIsThemeViewVisible) {
            hideThemeChooserView(this.mThemeContent.getMeasuredHeight(), 0, null);
            this.mShareContainer.setVisibility(0);
            this.mIsThemeViewVisible = false;
        } else {
            deleteTempUri();
            setResult(-1);
            super.onBackPressed();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(getWindow().getDecorView());
        if (rootWindowInsets != null) {
            this.mNavigationBarHeight = rootWindowInsets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
        }
    }

    private ValueAnimator createThemeChooserAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(300L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.notes.ui.SnsEditActivity.9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                int iIntValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                ViewGroup.LayoutParams layoutParams = SnsEditActivity.this.mThemeContent.getLayoutParams();
                layoutParams.height = iIntValue;
                SnsEditActivity.this.mThemeContent.setLayoutParams(layoutParams);
                SnsEditActivity.this.mThemeContent.requestLayout();
            }
        });
        return valueAnimator;
    }

    private void showThemeChooserView(int i, int i2, Animator.AnimatorListener animatorListener) {
        if (this.mThemeChooserView == null) {
            return;
        }
        this.mIsExpandThemeChooser = true;
        ValueAnimator valueAnimator = this.mThemeChooserCollapseAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mThemeChooserCollapseAnimator.cancel();
        }
        if (this.mThemeChooserExpandAnimator == null) {
            this.mThemeChooserExpandAnimator = createThemeChooserAnimator();
        }
        if (this.mThemeChooserExpandAnimator.isRunning()) {
            return;
        }
        if (animatorListener != null) {
            this.mThemeChooserExpandAnimator.addListener(animatorListener);
        }
        this.mThemeChooserExpandAnimator.setIntValues(i, i2);
        this.mThemeChooserExpandAnimator.start();
    }

    private void hideThemeChooserView(int i, int i2, Animator.AnimatorListener animatorListener) {
        if (this.mThemeChooserView != null && this.mIsExpandThemeChooser) {
            this.mIsExpandThemeChooser = false;
            ValueAnimator valueAnimator = this.mThemeChooserExpandAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mThemeChooserExpandAnimator.cancel();
            }
            if (this.mThemeChooserCollapseAnimator == null) {
                this.mThemeChooserCollapseAnimator = createThemeChooserAnimator();
            }
            if (this.mThemeChooserCollapseAnimator.isRunning()) {
                return;
            }
            this.mThemeChooserCollapseAnimator.removeAllListeners();
            if (animatorListener != null) {
                this.mThemeChooserCollapseAnimator.addListener(animatorListener);
            }
            this.mThemeChooserCollapseAnimator.setIntValues(i, i2);
            this.mThemeChooserCollapseAnimator.start();
        }
    }

    private void setThemeChooserBackGround(Theme theme) {
        Theme.EditorStyle editorStyle = theme.getEditorStyle(this.mThemeChooserView.getContext());
        BlurHelper.setBlurBackground(this.mThemeContent, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor2);
    }

    private void initResultLauncher() {
        this.requestStoragePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.miui.notes.ui.SnsEditActivity$$ExternalSyntheticLambda6
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$5((Map) obj);
            }
        });
        this.toXManResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.ui.SnsEditActivity$$ExternalSyntheticLambda7
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initResultLauncher$6((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$5(Map map) {
        if (Boolean.TRUE.equals(map.get("android.permission.WRITE_EXTERNAL_STORAGE"))) {
            doSaveToGallery();
        } else {
            Toast.makeText(NoteApp.getInstance(), R.string.toast_refuse_to_use_external_storage, 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initResultLauncher$6(ActivityResult activityResult) {
        finish();
    }

    private void smallWindowAdapter() {
        View view = this.mContentView;
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.ui.SnsEditActivity.10
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view2);
                    if (rootWindowInsets == null) {
                        return windowInsetsCompat;
                    }
                    Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                    if (UIUtils.isInMultiWindowMode(SnsEditActivity.this)) {
                        SnsEditActivity.this.mContentView.setPadding(0, 0, insetsIgnoringVisibility.right, 0);
                    } else {
                        SnsEditActivity.this.mContentView.setPadding(0, 0, 0, 0);
                    }
                    SnsEditActivity.this.mNavigationBarHeight = insetsIgnoringVisibility.bottom;
                    return windowInsetsCompat;
                }
            });
            ViewCompat.setOnApplyWindowInsetsListener(this.mShareContainer, new OnApplyWindowInsetsListener() { // from class: com.miui.notes.ui.SnsEditActivity.11
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view2);
                    if (rootWindowInsets == null) {
                        return windowInsetsCompat;
                    }
                    SnsEditActivity.this.mShareContainer.setPadding(0, 0, 0, rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars()).bottom);
                    return windowInsetsCompat;
                }
            });
        }
    }
}
