package com.miui.notes.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.FrameLayout;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.miui.common.base.BaseActivity;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.JumpVerificationUtils;
import com.miui.handwritecommon.api.HandViewProvider;
import com.miui.handwritecommon.api.callback.NotaDataSaveListener;
import com.miui.handwritecommon.api.callback.NoteDataLoadListener;
import com.miui.handwritecommon.api.singlepage.ISinglePageHandView;
import com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.pad.feature.notes.handwrite.SinglePagePathUtils;
import java.lang.ref.WeakReference;
import miuix.appcompat.app.ProgressDialog;

/* JADX INFO: loaded from: classes4.dex */
public class CalcHandWriteActivity extends BaseActivity {
    private static final String DEFAULT_NAME = "calc-ent.ent";
    private static final String TAG = "CalcHandWriteActivity";
    private CalcHandWriteNoteHandler mHandler;
    private InitRecognizeEngineTask mInitRecognizeEngineTask;
    private OnBackPressedCallback onBackPressedCallback;
    private ProgressDialog saveDialog;
    private ActivityResultLauncher<Intent> toCtaLauncher;
    private ISinglePageHandView writeView;
    String entPath = SinglePagePathUtils.getHandWrittenCalcPath(DEFAULT_NAME);
    private boolean noFirst = false;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger.INSTANCE.d(TAG, "onCreate");
        if (!JumpVerificationUtils.verifyCalculatorJumpSource(getLaunchedPackageName())) {
            finish();
            return;
        }
        initCtaLauncher();
        if (bundle == null) {
            requestCta();
        }
        setContentView(R.layout.activity_calc_hand_write);
        this.mHandler = new CalcHandWriteNoteHandler();
    }

    public String getLaunchedPackageName() {
        String callingPackage = getCallingPackage();
        Logger.INSTANCE.d(TAG, "callingPackage:" + callingPackage);
        if (!TextUtils.isEmpty(callingPackage)) {
            return callingPackage;
        }
        try {
            IBinder iBinder = (IBinder) Activity.class.getMethod("getActivityToken", new Class[0]).invoke(this, new Object[0]);
            Object objInvoke = ((ActivityManager) getSystemService("activity")).getClass().getMethod("getService", new Class[0]).invoke(null, new Object[0]);
            return (String) objInvoke.getClass().getMethod("getLaunchedFromPackage", IBinder.class).invoke(objInvoke, iBinder);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addHandView() {
        Logger.INSTANCE.d(TAG, "addHandView");
        ISinglePageHandView iSinglePageHandViewAddSinglePageView = HandViewProvider.addSinglePageView((FrameLayout) findViewById(R.id.hand_write_content), 1, true, true);
        this.writeView = iSinglePageHandViewAddSinglePageView;
        iSinglePageHandViewAddSinglePageView.load(this.entPath, new NoteDataLoadListener() { // from class: com.miui.notes.ui.CalcHandWriteActivity.1
            @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
            public void onStartLoad() {
                if (DisplayUtils.isNightMode()) {
                    CalcHandWriteActivity.this.writeView.showLoading(R.color.singlepage_bg_black);
                } else {
                    CalcHandWriteActivity.this.writeView.showLoading(R.color.singlepage_bg_white);
                }
            }

            @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
            public void onLoadComplete(boolean z) {
                CalcHandWriteActivity.this.writeView.hideLoading();
                Logger.INSTANCE.d(CalcHandWriteActivity.TAG, "ent onLoadComplete :" + z + " path:" + CalcHandWriteActivity.this.entPath);
            }
        });
        this.writeView.setToolBoxViewTopPadding(DisplayUtils.getStatusBarHeight(this));
        this.onBackPressedCallback = new OnBackPressedCallback(true) { // from class: com.miui.notes.ui.CalcHandWriteActivity.2
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                Logger.INSTANCE.d(CalcHandWriteActivity.TAG, "handleOnBackPressed");
                CalcHandWriteActivity.this.saveEnt();
            }
        };
        getOnBackPressedDispatcher().addCallback(this.onBackPressedCallback);
        this.writeView.setSingePageOptionListener(new ISinglePageLayoutListener() { // from class: com.miui.notes.ui.CalcHandWriteActivity.3
            @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
            public void onInsertImageClick() {
            }

            @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
            public void onShare() {
            }

            @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
            public void onTitleChange() {
            }

            @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
            public void onBtnSave() {
                CalcHandWriteActivity.this.saveEnt();
            }

            @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
            public void onContentChange() {
                if (CalcHandWriteActivity.this.mHandler.hasMessages(1)) {
                    CalcHandWriteActivity.this.mHandler.removeMessages(1);
                }
                CalcHandWriteActivity.this.mHandler.sendEmptyMessageDelayed(1, 5000L);
            }
        });
    }

    @Override // com.miui.common.base.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        requestCta();
    }

    private void requestCta() {
        if (PreferenceUtils.isCTAAccepted(getApplicationContext()) || !PermissionUtils.supportNewPermissionStyle()) {
            return;
        }
        PermissionNewUtils.showCtaDialog(this.toCtaLauncher);
    }

    private void initCtaLauncher() {
        this.toCtaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.ui.CalcHandWriteActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initCtaLauncher$0((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCtaLauncher$0(ActivityResult activityResult) {
        int resultCode = activityResult.getResultCode();
        if (resultCode == -3) {
            PermissionNewUtils.showCtaDialog(this.toCtaLauncher);
            return;
        }
        if (resultCode != 1) {
            if (resultCode != 666) {
                return;
            }
            finish();
        } else {
            PreferenceUtils.setFirstHandle(false, getApplicationContext());
            PreferenceUtils.setCTAAccepted(true, getApplicationContext());
            PreferenceUtils.setAllowNetwork(NoteApp.getInstance(), true);
            addHandView();
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.ui.CalcHandWriteActivity$4, reason: invalid class name */
    class AnonymousClass4 implements NotaDataSaveListener {
        AnonymousClass4() {
        }

        @Override // com.miui.handwritecommon.api.callback.NotaDataSaveListener
        public void onStartSave() {
            if (CalcHandWriteActivity.this.onBackPressedCallback != null) {
                CalcHandWriteActivity.this.onBackPressedCallback.setEnabled(false);
            }
            CalcHandWriteActivity.this.runOnUiThread(new Runnable() { // from class: com.miui.notes.ui.CalcHandWriteActivity$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onStartSave$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStartSave$0() {
            CalcHandWriteActivity.this.showSaveDialog(NoteApp.getInstance().getString(R.string.save_note_dialog));
        }

        @Override // com.miui.handwritecommon.api.callback.NotaDataSaveListener
        public void onSaveComplete(final boolean z) {
            CalcHandWriteActivity.this.runOnUiThread(new Runnable() { // from class: com.miui.notes.ui.CalcHandWriteActivity$4$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSaveComplete$1(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSaveComplete$1(boolean z) {
            CalcHandWriteActivity.this.dismissSaveDialog();
            Logger.INSTANCE.d(CalcHandWriteActivity.TAG, "ent onSaveComplete :" + z + " path:" + CalcHandWriteActivity.this.entPath);
            CalcHandWriteActivity.this.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveEnt() {
        this.writeView.save(this.entPath, new AnonymousClass4());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSaveDialog(String str) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        dismissSaveDialog();
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.saveDialog = progressDialog;
        progressDialog.setIndeterminate(true);
        this.saveDialog.setCanceledOnTouchOutside(false);
        this.saveDialog.setCancelable(false);
        this.saveDialog.setMessage(str);
        this.saveDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.ui.CalcHandWriteActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$showSaveDialog$1(dialogInterface);
            }
        });
        this.saveDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSaveDialog$1(DialogInterface dialogInterface) {
        this.saveDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissSaveDialog() {
        ProgressDialog progressDialog = this.saveDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.saveDialog.dismiss();
        this.saveDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void autoSave() {
        ISinglePageHandView iSinglePageHandView = this.writeView;
        if (iSinglePageHandView == null || !iSinglePageHandView.isDataChanged()) {
            return;
        }
        this.writeView.autoSave(this.entPath);
    }

    static class InitRecognizeEngineTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<CalcHandWriteActivity> iActivityRef;

        public InitRecognizeEngineTask(CalcHandWriteActivity calcHandWriteActivity) {
            this.iActivityRef = new WeakReference<>(calcHandWriteActivity);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            HandViewProvider.init(this.iActivityRef.get().getApplication());
            return null;
        }

        @Override // android.os.AsyncTask
        protected void onCancelled() {
            CalcHandWriteActivity calcHandWriteActivity = this.iActivityRef.get();
            if (calcHandWriteActivity != null && calcHandWriteActivity.mInitRecognizeEngineTask == this) {
                calcHandWriteActivity.mInitRecognizeEngineTask = null;
            }
            super.onCancelled();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r1) {
            if (PreferenceUtils.isCTAAccepted(this.iActivityRef.get()) || !PermissionUtils.supportNewPermissionStyle()) {
                this.iActivityRef.get().addHandView();
            }
            HandViewProvider.initRecognizedEngine(this.iActivityRef.get().getApplication());
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ISinglePageHandView iSinglePageHandView = this.writeView;
        if (iSinglePageHandView != null && this.noFirst) {
            iSinglePageHandView.setAutoOptimizeHandwrite();
        }
        if (PreferenceUtils.isCTAAccepted(getApplicationContext())) {
            InitRecognizeEngineTask initRecognizeEngineTask = new InitRecognizeEngineTask(this);
            this.mInitRecognizeEngineTask = initRecognizeEngineTask;
            initRecognizeEngineTask.execute(new Void[0]);
            return;
        }
        Logger.INSTANCE.i(TAG, "onResume,CTA NOT ACCEPTED!!");
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        autoSave();
        this.noFirst = true;
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.remove();
            this.onBackPressedCallback = null;
        }
        ISinglePageHandView iSinglePageHandView = this.writeView;
        if (iSinglePageHandView != null) {
            iSinglePageHandView.release();
        }
        dismissSaveDialog();
        CalcHandWriteNoteHandler calcHandWriteNoteHandler = this.mHandler;
        if (calcHandWriteNoteHandler != null) {
            calcHandWriteNoteHandler.removeCallbacksAndMessages(null);
            this.mHandler = null;
        }
        super.onDestroy();
    }

    private static class CalcHandWriteNoteHandler extends Handler {
        private static final int TIMED_SAVE = 1;
        private WeakReference<CalcHandWriteActivity> mContextRef;

        private CalcHandWriteNoteHandler(CalcHandWriteActivity calcHandWriteActivity) {
            this.mContextRef = new WeakReference<>(calcHandWriteActivity);
        }

        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            super.dispatchMessage(message);
            CalcHandWriteActivity calcHandWriteActivity = this.mContextRef.get();
            if (calcHandWriteActivity != null && message.what == 1) {
                try {
                    calcHandWriteActivity.autoSave();
                } catch (Exception e) {
                    Logger.INSTANCE.d(CalcHandWriteActivity.TAG, e.getMessage());
                }
            }
        }
    }
}
