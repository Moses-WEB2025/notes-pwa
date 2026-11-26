package com.miui.notes.ui.activity;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.hardware.DataSpace;
import com.miui.common.stat.WidgetStat;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.assist.CtaActivityResult;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment;
import com.miui.notes.feature.noteedit.PhoneLiteHybridNoteEditFragment;
import com.miui.notes.provider.Notes;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.NotesUtils;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.PhoneNoteEditFragment;
import com.miui.notes.ui.PhoneNoteLiteEditFragment;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.util.NoteClipManager;
import com.xiaomi.aiinput.AIInputResultListener;
import com.xiaomi.aiinput.AIInputTextManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes4.dex */
public class EditActivity extends DialogManagedActivity {
    public static final String KEYGUARD_SHORTCUT = "from_keyguard_shortcut";
    private static final String TAG = "EditActivity";
    private static final ExecutorService sExecutorService = Executors.newSingleThreadExecutor();
    private AIInputTextManager.AITextCallback mAIInputCallback;
    private BaseNoteDetailFragment mEditFragment;
    private IEditorContext mEditorContext;
    private BroadcastReceiver mFinishReceiver;
    private ActivityResultLauncher<Intent> toCtaLauncher;
    private ActivityResultLauncher<Intent> toCtaPasteLauncher;
    private boolean mUseHybridEditor = true;
    private boolean mIsSupportAIInput = false;

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        try {
            Trace.beginSection("EditActivity.onCreate");
            super.onCreate(bundle);
            getWindow().addFlags(DataSpace.RANGE_FULL);
            setContentView(R.layout.phone_layout);
            Intent intent = getIntent();
            Logger.INSTANCE.d(TAG, "onCreate,from_keyguard_shortcut:" + intent.getBooleanExtra(KEYGUARD_SHORTCUT, false));
            changeLockedFlag(intent);
            boolean booleanExtra = intent.getBooleanExtra(Notes.Intents.INTENT_EXTRA_NEW_STYLE, false);
            boolean zIsPadMode = RomUtils.isPadMode();
            boolean zIsPadDevice = RomUtils.isPadDevice();
            boolean zIsNewLiteOrMiddle = LiteUtils.isNewLiteOrMiddle();
            Logger.INSTANCE.d(TAG, "onCreate,new style:" + booleanExtra + ",isPadMode:" + RomUtils.isPadMode() + ",isLiteOrMiddle:" + zIsNewLiteOrMiddle + ",isPadDevice:" + zIsPadDevice + ",isPadMode:" + zIsPadMode);
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            Fragment fragmentFindFragmentById = supportFragmentManager.findFragmentById(R.id.content);
            this.mFinishReceiver = new BroadcastReceiver() { // from class: com.miui.notes.ui.activity.EditActivity.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent2) {
                    EditActivity.this.finish();
                }
            };
            getWindow().getDecorView().post(new Runnable() { // from class: com.miui.notes.ui.activity.EditActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$0();
                }
            });
            if (booleanExtra || !zIsNewLiteOrMiddle || zIsPadDevice) {
                if (zIsPadMode) {
                    if ("android.intent.action.INSERT_OR_EDIT".equals(intent.getAction()) || "android.intent.action.SEND".equals(intent.getAction())) {
                        intent.setClass(this, NotesListActivity.class);
                        intent.setFlags(268484608);
                        startActivity(intent);
                        Logger.INSTANCE.d(TAG, "onCreate,pad mode,action_insert or send,start NotesListActivity");
                    }
                    Logger.INSTANCE.d(TAG, "onCreate, mode pad ->finish");
                    finish();
                    return;
                }
                if (!this.mUseHybridEditor || LiteUtils.isLiteOrMiddle()) {
                    if (booleanExtra) {
                        if (fragmentFindFragmentById instanceof PhoneLiteHybridNoteEditFragment) {
                            Logger.INSTANCE.d(TAG, "onCreate,new style:true,PhoneLiteHybridNoteEditFragment");
                            this.mEditFragment = (PhoneLiteHybridNoteEditFragment) supportFragmentManager.findFragmentById(R.id.content);
                        }
                    } else if (fragmentFindFragmentById instanceof PhoneNoteEditFragment) {
                        Logger.INSTANCE.d(TAG, "onCreate,new style:false,PhoneNoteEditFragment");
                        this.mEditFragment = (PhoneNoteEditFragment) supportFragmentManager.findFragmentById(R.id.content);
                    }
                } else if (fragmentFindFragmentById instanceof PhoneHybridNoteEditFragment) {
                    Logger.INSTANCE.d(TAG, "onCreate,use hybrid,PhoneHybridNoteEditFragment");
                    this.mEditFragment = (PhoneHybridNoteEditFragment) supportFragmentManager.findFragmentById(R.id.content);
                }
            } else if (fragmentFindFragmentById instanceof PhoneNoteLiteEditFragment) {
                this.mEditFragment = (PhoneNoteLiteEditFragment) supportFragmentManager.findFragmentById(R.id.content);
                Logger.INSTANCE.d(TAG, "onCreate,old style,newLiteOrMiddle->PhoneNoteLiteEditFragment");
            }
            if (this.mEditFragment == null) {
                if (!booleanExtra && zIsNewLiteOrMiddle) {
                    this.mEditFragment = new PhoneNoteLiteEditFragment();
                } else if (!booleanExtra && (LiteUtils.isLiteOrMiddle() || !this.mUseHybridEditor)) {
                    this.mEditFragment = new PhoneNoteEditFragment();
                } else if (!booleanExtra || (!LiteUtils.isLiteOrMiddle() && this.mUseHybridEditor)) {
                    this.mEditFragment = new PhoneHybridNoteEditFragment();
                } else {
                    this.mEditFragment = new PhoneLiteHybridNoteEditFragment();
                }
                Logger.INSTANCE.d(TAG, "onCreate with fragment " + this.mEditFragment + ",begin transaction..");
                supportFragmentManager.beginTransaction().add(R.id.content, this.mEditFragment).commit();
            }
            if (bundle != null) {
                ResourceManager.isNightMode = UIUtils.isNightMode(getApplicationContext());
                Logger.INSTANCE.d(TAG, "onCreate,bundle not null,clear themes");
                ResourceManager.clearThemes();
            } else if (ResourceManager.isNightMode != UIUtils.isNightMode(getApplicationContext())) {
                ResourceManager.isNightMode = UIUtils.isNightMode(getApplicationContext());
                Logger.INSTANCE.d(TAG, "onCreate,clear themes,mode conflict");
                ResourceManager.clearThemes();
            }
            Logger.INSTANCE.d(TAG, "onCreate,stats->edit page enter");
            initCtaLauncher();
            if (bundle == null) {
                requestCta();
            }
            sExecutorService.execute(new Runnable() { // from class: com.miui.notes.ui.activity.EditActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.initAIInput();
                }
            });
            getWindow().getDecorView().post(new Runnable() { // from class: com.miui.notes.ui.activity.EditActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$2();
                }
            });
            Logger.INSTANCE.d(TAG, "onCreate,done");
        } finally {
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PasswordController.ACTIN_CANCEL);
        registerReceiver(this.mFinishReceiver, intentFilter, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        WidgetStat.handleEditEnter(getIntent(), WidgetStat.NOTES_EDIT_PAGE);
        this.mHandoffSession = HandoffHelper.publishDeepLink(this, new Supplier() { // from class: com.miui.notes.ui.activity.EditActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$onCreate$1();
            }
        }, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$onCreate$1() {
        BaseNoteDetailFragment baseNoteDetailFragment = this.mEditFragment;
        if (baseNoteDetailFragment != null && (baseNoteDetailFragment instanceof BaseNormalNoteEditFragment)) {
            Uri deepLink = ((BaseNormalNoteEditFragment) baseNoteDetailFragment).getDeepLink();
            Logger.INSTANCE.d("Notes:HandoffHelper", "get note  ");
            if (deepLink != null) {
                return deepLink;
            }
        }
        return Uri.parse("minote://data/notes/");
    }

    private void requestCta() {
        Intent intent = getIntent();
        if (intent != null && (!TextUtils.isEmpty(intent.getStringExtra("android.intent.extra.PHONE_NUMBER")) || NoteIntent.TYPE_ACTION_SHOW_AI_TEXT_WIDGET.equals(intent.getType()))) {
            Logger.INSTANCE.d(TAG, "requestCta callNote or intentFormMiShow, return");
            return;
        }
        boolean zIsKeyguardLocked = ((KeyguardManager) getSystemService("keyguard")).isKeyguardLocked();
        if (PreferenceUtils.isCTAAccepted(getApplicationContext()) || !PermissionUtils.supportNewPermissionStyle() || !PreferenceUtils.isFirstHandle(getApplicationContext()) || zIsKeyguardLocked) {
            return;
        }
        PermissionNewUtils.showCtaDialog(this.toCtaLauncher);
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        Logger.INSTANCE.d(TAG, "onRestart");
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        Trace.beginSection("EditActivity.onStart");
        super.onStart();
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.ui.activity.EditActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                UIUtils.setMiuiWidgetSupported(NoteApp.getInstance());
            }
        }).subscribeOn(Schedulers.io()).subscribe();
        Trace.endSection();
    }

    @Override // com.miui.common.base.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.INSTANCE.d(TAG, "onNewIntent");
        changeLockedFlag(intent);
        requestCta();
        boolean zEquals = NoteIntent.TYPE_ACTION_SHOW_AI_TEXT_WIDGET.equals(intent.getType());
        Logger.INSTANCE.d(TAG, "onNewIntent isShowAiWidgetFromMiShow = " + zEquals);
        if (this.mEditFragment != null) {
            if (PreferenceUtils.isCTAAccepted(getApplicationContext()) || Objects.equals(intent.getType(), NoteIntent.ACTION_CREATE_NOTE) || zEquals) {
                this.mEditFragment.onNewIntent(intent);
            }
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        Trace.beginSection("EditActivity.onResume");
        super.onResume();
        if ((getWindow().getAttributes().flags & DataSpace.RANGE_FULL) == 0) {
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
        Trace.endSection();
    }

    @Override // com.miui.notes.ui.activity.DialogManagedActivity, com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Trace.beginSection("EditActivity-onDestroy");
        super.onDestroy();
        Logger.INSTANCE.d(TAG, "onDestroy");
        try {
            unregisterReceiver(this.mFinishReceiver);
        } catch (IllegalArgumentException e) {
            Logger.INSTANCE.w(TAG, "unregisterReceiver failed: " + e.getMessage());
        }
        Utils.fixInputMethodMemLeak(this, getWindow().getDecorView().getWindowToken());
        this.mEditFragment = null;
        ResourceManager.clearThemesEditorStyle();
        releaseAIInput();
        Trace.endSection();
    }

    private void changeLockedFlag(Intent intent) {
        if (NotesUtils.isAddLockFlag(NoteApp.getInstance(), intent)) {
            getWindow().addFlags(524288);
        } else {
            getWindow().clearFlags(524288);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        BaseNoteDetailFragment baseNoteDetailFragment = this.mEditFragment;
        if (baseNoteDetailFragment == null || !baseNoteDetailFragment.onBackPressed()) {
            Logger.INSTANCE.d(TAG, "onBackPressed");
            NoteContentStat.QUIT_WAY = "SystemBack";
            NoteContentStat.QUIT_EDIT_WAY = "SystemBack";
            super.onBackPressed();
            finish();
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity
    public void finish() {
        Logger.INSTANCE.i(TAG, "finish ");
        setResult(-1);
        super.finish();
    }

    private void initCtaLauncher() {
        this.toCtaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.ui.activity.EditActivity.2
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d(EditActivity.TAG, "toCtaLauncher result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(EditActivity.this.toCtaLauncher);
                }
                if (EditActivity.this.mEditFragment != null) {
                    EditActivity.this.mEditFragment.showSoftInput();
                }
            }
        });
        this.toCtaPasteLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.ui.activity.EditActivity.3
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d(EditActivity.TAG, "toCtaPasteLauncher result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(EditActivity.this.toCtaPasteLauncher);
                    return;
                }
                if (activityResult.getResultCode() == 666) {
                    if (EditActivity.this.mEditorContext != null && (EditActivity.this.mEditorContext instanceof RichEditTextView)) {
                        NoteClipManager.INSTANCE.buildClipSpannableString(NoteApp.getInstance(), EditActivity.this.mEditorContext);
                    } else {
                        NoteClipManager.INSTANCE.buildJsonObject(NoteApp.getInstance());
                    }
                }
            }
        });
    }

    public void showCtaForPaste(IEditorContext iEditorContext) {
        this.mEditorContext = iEditorContext;
        PermissionNewUtils.showCtaDialog(this.toCtaPasteLauncher);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if ((motionEvent.getAction() & 255) == 5) {
            motionEvent.setAction(3);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private void initAIInputCallBack() {
        this.mAIInputCallback = new AIInputTextManager.AITextCallback() { // from class: com.miui.notes.ui.activity.EditActivity.4
            @Override // com.xiaomi.aiinput.AIInputTextManager.AITextCallback
            public void onFocusedAITextEdit(View view, String str, int i, AIInputResultListener aIInputResultListener) {
                if (i == 0) {
                    if (EditActivity.this.mEditFragment instanceof BaseHybridNoteEditFragment) {
                        ((BaseHybridNoteEditFragment) EditActivity.this.mEditFragment).replaceText(str);
                    }
                } else if (i == 1 && (EditActivity.this.mEditFragment instanceof BaseHybridNoteEditFragment)) {
                    ((BaseHybridNoteEditFragment) EditActivity.this.mEditFragment).insertText(str);
                }
                try {
                    aIInputResultListener.onHandleTextView(i, true);
                } catch (Exception e) {
                    Logger.INSTANCE.e(EditActivity.TAG, "onFocusedAITextEdit error" + e.getMessage());
                }
            }

            @Override // com.xiaomi.aiinput.AIInputTextManager.AITextCallback
            public void onRequestFocusedAITextInfo(View view, AIInputResultListener aIInputResultListener) {
                try {
                    if (EditActivity.this.mEditFragment instanceof BaseHybridNoteEditFragment) {
                        ((BaseHybridNoteEditFragment) EditActivity.this.mEditFragment).handleGetAiTextInfo(aIInputResultListener, EditActivity.this.toString());
                    }
                } catch (Exception e) {
                    Logger.INSTANCE.e(EditActivity.TAG, "onRequestFocusedAITextInfo error" + e.getMessage());
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAIInput() {
        try {
            this.mIsSupportAIInput = AIInputTextManager.getInstance(getApplicationContext()) != null && (this.mEditFragment instanceof BaseHybridNoteEditFragment);
        } catch (Throwable th) {
            Logger.INSTANCE.e(TAG, "setIsSupportAIInput error: " + th);
            th.printStackTrace();
            this.mIsSupportAIInput = false;
        }
        if (this.mIsSupportAIInput) {
            initAIInputCallBack();
            AIInputTextManager.getInstance(getApplicationContext()).registerAITextCallback(this.mAIInputCallback);
        }
    }

    private void releaseAIInput() {
        if (!this.mIsSupportAIInput || this.mAIInputCallback == null) {
            return;
        }
        try {
            AIInputTextManager.getInstance(getApplicationContext()).unregisterAITextCallback(this.mAIInputCallback);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "releaseAIInput error:" + e.getMessage());
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        BaseNoteDetailFragment baseNoteDetailFragment;
        Logger.INSTANCE.d(TAG, "onWindowFocusChanged " + z);
        Trace.beginSection("EditActivity.onWindowFocusChanged");
        if (z && (baseNoteDetailFragment = this.mEditFragment) != null) {
            baseNoteDetailFragment.setWindowsOnFocus();
        }
        Trace.endSection();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getWindow().addFlags(DataSpace.RANGE_FULL);
    }
}
