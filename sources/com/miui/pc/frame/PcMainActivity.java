package com.miui.pc.frame;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.miui.common.base.BaseTodoFragment;
import com.miui.common.base.FragmentActionListener;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.constant.FrameConstant;
import com.miui.common.stat.BaseStat;
import com.miui.common.stat.GlobalStat;
import com.miui.common.tool.DualScreenManager;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.handwritecommon.api.HandViewProvider;
import com.miui.notes.R;
import com.miui.notes.assist.CtaActivityResult;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import com.miui.notes.ui.activity.DialogManagedActivity;
import com.miui.pc.feature.notes.PcHandWriteFragment;
import com.miui.pc.feature.notes.PcHybridNoteEditFragment;
import com.miui.pc.feature.notes.PcMultiHandWriteFragment;
import com.miui.pc.feature.notes.PcNoteEditFragment;
import com.miui.pc.feature.notes.PcWebMindNoteFragment;
import com.miui.pc.feature.remind.PcTodoRemindDialog;
import com.miui.pc.feature.todo.PcTodoListFragment;
import com.miui.pc.feature.todo.PcTodoListPresenter;
import com.miui.todo.feature.todolist.TodoListPresenter;
import com.miui.todo.utils.SchedulerProvider;
import com.xiaomi.mirror.synergy.MiuiSynergySdk;
import java.lang.ref.WeakReference;
import miui.accounts.ExtraAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class PcMainActivity extends DialogManagedActivity {
    private static final String LEFT_TAG = "left";
    private static final String NOTE_TAG = "note";
    private static final String TODO_TAG = "todo";
    private BaseRightLayout mBaseRightContainer;
    private int mCurTabIndex;
    private AppCompatDelegate mDelegate;
    private MainHandler<PcMainActivity> mHandler;
    private BaseNoteDetailFragment mLastEditFragment;
    private LeftFragment mLeftFragment;
    private BaseNoteDetailFragment mPcHandWriteFragment;
    private BaseNoteDetailFragment mPcMultiHandWriteFragment;
    private BaseNoteDetailFragment mPcNoteFragment;
    private BaseTodoFragment mPcTodoFragment;
    private BaseNoteDetailFragment mPcWebBrainFragment;
    private TodoListPresenter mTodoPresenter;
    private Account mXiaomiAccount;
    private ActivityResultLauncher<Intent> toCtaLauncher;
    protected int mMode = -2;
    private PcTodoRemindDialog mTodoRemindDialog = null;
    private final BroadcastReceiver mAlarmReceiver = new AnonymousClass1();
    private final FragmentActionListener mPcNoteActionListener = new FragmentActionListener() { // from class: com.miui.pc.frame.PcMainActivity.2
        @Override // com.miui.common.base.FragmentActionListener
        public void onAction(int i, Bundle bundle) {
            if (i == 10) {
                if (PcMainActivity.this.mLeftFragment == null || PcMainActivity.this.mLastEditFragment == null) {
                    return;
                }
                PcMainActivity.this.mLeftFragment.onNewNoteSaved(bundle.getLong("new_note_id", -2L));
                return;
            }
            if (i == 11) {
                if (PcMainActivity.this.mLeftFragment != null) {
                    PcMainActivity.this.mLeftFragment.createMindNoteFromNormalNote(bundle);
                }
            } else {
                if (i != 21 || PcMainActivity.this.mLeftFragment == null || PcMainActivity.this.mLastEditFragment == null) {
                    return;
                }
                PcMainActivity.this.mLeftFragment.exitSearchMode();
            }
        }
    };
    private final FragmentActionListener mPcWebBrainActionListener = new FragmentActionListener() { // from class: com.miui.pc.frame.PcMainActivity.3
        @Override // com.miui.common.base.FragmentActionListener
        public void onAction(int i, Bundle bundle) {
            if (i != 21 || PcMainActivity.this.mLeftFragment == null || PcMainActivity.this.mLastEditFragment == null) {
                return;
            }
            PcMainActivity.this.mLeftFragment.exitSearchMode();
        }
    };
    private final FragmentActionListener mPcHandWriteActionListener = new FragmentActionListener() { // from class: com.miui.pc.frame.PcMainActivity.4
        @Override // com.miui.common.base.FragmentActionListener
        public void onAction(int i, Bundle bundle) {
            if (i != 21 || PcMainActivity.this.mLeftFragment == null || PcMainActivity.this.mLastEditFragment == null) {
                return;
            }
            PcMainActivity.this.mLeftFragment.exitSearchMode();
        }
    };
    private final FragmentActionListener mPcMultiHandWriteActionListener = new FragmentActionListener() { // from class: com.miui.pc.frame.PcMainActivity.5
        @Override // com.miui.common.base.FragmentActionListener
        public void onAction(int i, Bundle bundle) {
            if (i != 21 || PcMainActivity.this.mLeftFragment == null || PcMainActivity.this.mLastEditFragment == null) {
                return;
            }
            PcMainActivity.this.mLeftFragment.exitSearchMode();
        }
    };

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
    }

    /* JADX INFO: renamed from: com.miui.pc.frame.PcMainActivity$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, final Intent intent) {
            if (FrameConstant.ACTION_DUAL_SCREEN_LAUNCH_TODO_ALARM.equals(intent.getAction())) {
                PcMainActivity.this.mHandler.post(new Runnable() { // from class: com.miui.pc.frame.PcMainActivity$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReceive$0(intent);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(Intent intent) {
            PcMainActivity.this.showTodoRemind(intent);
        }
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        setForceDarkMode(1);
        FloatWindowManagerAgent.changeGuideVisible(false);
        super.onCreate(bundle);
        this.mHandler = new MainHandler<>(getMainLooper(), new WeakReference(this));
        HandViewProvider.init(getApplication());
        setContentView(R.layout.pc_layout);
        BaseRightLayout baseRightLayout = (BaseRightLayout) findViewById(R.id.right_container);
        this.mBaseRightContainer = baseRightLayout;
        baseRightLayout.setRightTouchListener(new OnRightTouchListener() { // from class: com.miui.pc.frame.PcMainActivity$$ExternalSyntheticLambda0
            @Override // com.miui.pc.frame.OnRightTouchListener
            public final boolean onTouchEvent(MotionEvent motionEvent) {
                return this.f$0.lambda$onCreate$0(motionEvent);
            }
        });
        initializeFragments();
        updateCurrentTab(0);
        if (PreferenceUtils.isCTAAccepted(getApplicationContext())) {
            this.mXiaomiAccount = ExtraAccountManager.getXiaomiAccount(this);
        }
        SyncUtils.pcStopSync(this.mXiaomiAccount);
        DualScreenManager.INSTANCE.setPcActivityLiving(true);
        Intent intent = new Intent();
        intent.setAction(FrameConstant.ACTION_DUAL_SCREEN_RUN_STATE_CHANGED);
        intent.putExtra(FrameConstant.PARAM_DUAL_SCREEN_RUN_STATE, 1);
        sendBroadcast(intent);
        MiuiSynergySdk.getInstance().updateTitle(this, getString(R.string.new_app_name));
        if (this.needShowCtaDialog) {
            initActivityResultLauncher();
        }
        if (this.needShowCtaDialog && bundle == null) {
            requestCta();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$0(MotionEvent motionEvent) {
        LeftFragment leftFragment;
        if (motionEvent.getAction() != 0 || (leftFragment = this.mLeftFragment) == null || !leftFragment.isAdded()) {
            return false;
        }
        this.mLeftFragment.clearFocus();
        return false;
    }

    @Override // com.miui.common.base.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        requestCta();
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        setForceDarkMode(1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FrameConstant.ACTION_DUAL_SCREEN_LAUNCH_NOTE_ALARM);
        intentFilter.addAction(FrameConstant.ACTION_DUAL_SCREEN_LAUNCH_TODO_ALARM);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mAlarmReceiver, intentFilter);
        super.onResume();
        if (!PreferenceUtils.isCTAAccepted(getApplicationContext())) {
            Logger.INSTANCE.i("PcMainActivity", "onResume,CTA NOT ACCEPTED!!");
        }
        BaseStat.reportExposeEvent(GlobalStat.TIP_PC_EXPOSE, null);
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mAlarmReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AppCompatDelegate getDelegate() {
        AppCompatDelegate appCompatDelegate = this.mDelegate;
        return appCompatDelegate == null ? createDelegate() : appCompatDelegate;
    }

    private AppCompatDelegate createDelegate() {
        AppCompatDelegate appCompatDelegateCreate = AppCompatDelegate.create(this, (AppCompatCallback) null);
        this.mDelegate = appCompatDelegateCreate;
        return appCompatDelegateCreate;
    }

    protected void setForceDarkMode(int i) {
        AppCompatDelegate.setDefaultNightMode(i);
        getDelegate().applyDayNight();
        getDelegate().setLocalNightMode(i);
    }

    private void initializeFragments() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        LeftFragment leftFragment = (LeftFragment) supportFragmentManager.findFragmentByTag("left");
        this.mLeftFragment = leftFragment;
        if (leftFragment == null) {
            LeftFragment leftFragment2 = new LeftFragment();
            this.mLeftFragment = leftFragment2;
            fragmentTransactionBeginTransaction.add(R.id.left, leftFragment2, "left");
        }
        BaseNoteDetailFragment baseNoteDetailFragment = (BaseNoteDetailFragment) supportFragmentManager.findFragmentByTag("note");
        this.mLastEditFragment = baseNoteDetailFragment;
        if (baseNoteDetailFragment == null) {
            BaseNoteDetailFragment baseNoteDetailFragmentCreatePadNoteEditFragment = createPadNoteEditFragment(new NoteLoadDataEntity(-1L, 0L, "", 0, Long.MIN_VALUE, false, null));
            this.mPcNoteFragment = baseNoteDetailFragmentCreatePadNoteEditFragment;
            this.mLastEditFragment = baseNoteDetailFragmentCreatePadNoteEditFragment;
        } else if (baseNoteDetailFragment instanceof PcNoteEditFragment) {
            this.mPcNoteFragment = baseNoteDetailFragment;
            baseNoteDetailFragment.setOutKeyListener(this.mLeftFragment.getKeyListener());
            this.mPcNoteFragment.setActionListener(this.mPcNoteActionListener);
        } else if (baseNoteDetailFragment instanceof PcWebMindNoteFragment) {
            this.mPcWebBrainFragment = baseNoteDetailFragment;
            baseNoteDetailFragment.setOutKeyListener(this.mLeftFragment.getKeyListener());
            this.mPcWebBrainFragment.setActionListener(this.mPcWebBrainActionListener);
        } else if (baseNoteDetailFragment instanceof PcHandWriteFragment) {
            this.mPcHandWriteFragment = baseNoteDetailFragment;
            baseNoteDetailFragment.setOutKeyListener(this.mLeftFragment.getKeyListener());
            this.mPcHandWriteFragment.setActionListener(this.mPcHandWriteActionListener);
        } else if (baseNoteDetailFragment instanceof PcMultiHandWriteFragment) {
            this.mPcMultiHandWriteFragment.setOutKeyListener(this.mLeftFragment.getKeyListener());
            this.mPcMultiHandWriteFragment.setActionListener(this.mPcMultiHandWriteActionListener);
        }
        BaseTodoFragment baseTodoFragment = (BaseTodoFragment) supportFragmentManager.findFragmentByTag("todo");
        this.mPcTodoFragment = baseTodoFragment;
        if (baseTodoFragment == null) {
            BaseTodoFragment baseTodoFragmentNewInstance = PcTodoFragmentBuilder.newInstance(0);
            this.mPcTodoFragment = baseTodoFragmentNewInstance;
            baseTodoFragmentNewInstance.setActionListener(new FragmentActionListener() { // from class: com.miui.pc.frame.PcMainActivity$$ExternalSyntheticLambda2
                @Override // com.miui.common.base.FragmentActionListener
                public final void onAction(int i, Bundle bundle) {
                    this.f$0.lambda$initializeFragments$1(i, bundle);
                }
            });
        }
        if (this.mTodoPresenter == null) {
            this.mTodoPresenter = new PcTodoListPresenter((PcTodoListFragment) this.mPcTodoFragment, SchedulerProvider.getInstance());
        }
        fragmentTransactionBeginTransaction.commit();
        supportFragmentManager.executePendingTransactions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initializeFragments$1(int i, Bundle bundle) {
        LeftFragment leftFragment;
        if (i != 1) {
            if (i == 2 && (leftFragment = this.mLeftFragment) != null) {
                leftFragment.lockUI(false);
                return;
            }
            return;
        }
        LeftFragment leftFragment2 = this.mLeftFragment;
        if (leftFragment2 != null) {
            leftFragment2.lockUI(true);
        }
    }

    public void convertTodo(int i, String str) {
        if (this.mCurTabIndex == 0) {
            this.mLeftFragment.setNotesListLaneVisible(8);
        }
        if (this.mCurTabIndex == 0) {
            if (!this.mPcTodoFragment.isAdded()) {
                Bundle bundle = new Bundle();
                bundle.putInt("todo_list_type", i);
                this.mPcTodoFragment.setArguments(bundle);
            } else {
                this.mPcTodoFragment.changeTodoCategory(i);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.right, this.mPcTodoFragment, "todo").commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();
            updateCurrentTab(1);
        } else {
            this.mPcTodoFragment.changeTodoCategory(i);
        }
        if (!TextUtils.isEmpty(str)) {
            ((PcTodoListFragment) this.mPcTodoFragment).enterSearchMode(str);
        } else {
            ((PcTodoListFragment) this.mPcTodoFragment).exitSearchMode();
        }
    }

    public void convertNote() {
        if (this.mCurTabIndex == 1) {
            this.mLeftFragment.setNotesListLaneVisible(0);
        }
        if (this.mCurTabIndex == 1) {
            replaceEditFragment();
            updateCurrentTab(0);
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0044  */
    /* JADX WARN: Code duplicated, block: B:26:0x0067  */
    /* JADX WARN: Code duplicated, block: B:28:0x006e  */
    /* JADX WARN: Code duplicated, block: B:34:0x008b  */
    /* JADX WARN: Code duplicated, block: B:36:0x0092  */
    /* JADX WARN: Code duplicated, block: B:40:0x009c  */
    public void showNote(final NoteLoadDataEntity noteLoadDataEntity, final boolean z) {
        if (this.mCurTabIndex == 0) {
            if (!this.mLastEditFragment.isAdded()) {
                this.mHandler.post(new Runnable() { // from class: com.miui.pc.frame.PcMainActivity$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$showNote$2(z, noteLoadDataEntity);
                    }
                });
                return;
            }
            this.mLastEditFragment.setmIsFromPc(true);
            if (noteLoadDataEntity.getNoteType() == 0) {
                BaseNoteDetailFragment baseNoteDetailFragment = this.mLastEditFragment;
                if (!(baseNoteDetailFragment instanceof PcNoteEditFragment) && !(baseNoteDetailFragment instanceof PcHybridNoteEditFragment)) {
                    if (this.mPcNoteFragment == null) {
                        this.mPcNoteFragment = createPadNoteEditFragment(noteLoadDataEntity);
                    }
                    this.mLastEditFragment.deleteEmptyPcNote();
                    this.mLastEditFragment = this.mPcNoteFragment;
                    replaceEditFragment();
                } else if (noteLoadDataEntity.getNoteType() != 1 && !(this.mLastEditFragment instanceof PcWebMindNoteFragment)) {
                    if (this.mPcWebBrainFragment == null) {
                        this.mPcWebBrainFragment = createPadWebBrainEditFragment(noteLoadDataEntity);
                    }
                    this.mLastEditFragment.deleteEmptyPcNote();
                    this.mLastEditFragment = this.mPcWebBrainFragment;
                    replaceEditFragment();
                } else if (noteLoadDataEntity.getNoteType() != 2 && !(this.mLastEditFragment instanceof PcHandWriteFragment)) {
                    if (this.mPcHandWriteFragment == null) {
                        this.mPcHandWriteFragment = createPadHandWriteEditFragment(noteLoadDataEntity);
                    }
                    this.mLastEditFragment.deleteEmptyPcNote();
                    this.mLastEditFragment = this.mPcHandWriteFragment;
                    replaceEditFragment();
                } else if (noteLoadDataEntity.getNoteType() == 3 && !(this.mLastEditFragment instanceof PcMultiHandWriteFragment)) {
                    if (this.mPcMultiHandWriteFragment == null) {
                        this.mPcMultiHandWriteFragment = createPadMultiHandWriteEditFragment(noteLoadDataEntity);
                    }
                    this.mLastEditFragment.deleteEmptyPcNote();
                    this.mLastEditFragment = this.mPcMultiHandWriteFragment;
                    replaceEditFragment();
                }
            } else if (noteLoadDataEntity.getNoteType() != 1) {
                if (noteLoadDataEntity.getNoteType() != 2) {
                    if (noteLoadDataEntity.getNoteType() == 3) {
                        if (this.mPcMultiHandWriteFragment == null) {
                            this.mPcMultiHandWriteFragment = createPadMultiHandWriteEditFragment(noteLoadDataEntity);
                        }
                        this.mLastEditFragment.deleteEmptyPcNote();
                        this.mLastEditFragment = this.mPcMultiHandWriteFragment;
                        replaceEditFragment();
                    }
                } else if (noteLoadDataEntity.getNoteType() == 3) {
                    if (this.mPcMultiHandWriteFragment == null) {
                        this.mPcMultiHandWriteFragment = createPadMultiHandWriteEditFragment(noteLoadDataEntity);
                    }
                    this.mLastEditFragment.deleteEmptyPcNote();
                    this.mLastEditFragment = this.mPcMultiHandWriteFragment;
                    replaceEditFragment();
                }
            } else if (noteLoadDataEntity.getNoteType() != 2) {
                if (noteLoadDataEntity.getNoteType() == 3) {
                    if (this.mPcMultiHandWriteFragment == null) {
                        this.mPcMultiHandWriteFragment = createPadMultiHandWriteEditFragment(noteLoadDataEntity);
                    }
                    this.mLastEditFragment.deleteEmptyPcNote();
                    this.mLastEditFragment = this.mPcMultiHandWriteFragment;
                    replaceEditFragment();
                }
            } else if (noteLoadDataEntity.getNoteType() == 3) {
                if (this.mPcMultiHandWriteFragment == null) {
                    this.mPcMultiHandWriteFragment = createPadMultiHandWriteEditFragment(noteLoadDataEntity);
                }
                this.mLastEditFragment.deleteEmptyPcNote();
                this.mLastEditFragment = this.mPcMultiHandWriteFragment;
                replaceEditFragment();
            }
            changeNote(noteLoadDataEntity, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNote$2(boolean z, NoteLoadDataEntity noteLoadDataEntity) {
        if (noteLoadDataEntity.getNoteType() == 0) {
            this.mLastEditFragment = this.mPcNoteFragment;
        } else if (noteLoadDataEntity.getNoteType() == 1) {
            if (this.mPcWebBrainFragment == null) {
                this.mPcWebBrainFragment = createPadWebBrainEditFragment(noteLoadDataEntity);
                z = true;
            }
            this.mLastEditFragment = this.mPcWebBrainFragment;
        } else if (noteLoadDataEntity.getNoteType() == 3) {
            if (this.mPcMultiHandWriteFragment == null) {
                this.mPcMultiHandWriteFragment = createPadMultiHandWriteEditFragment(noteLoadDataEntity);
                z = true;
            }
            this.mLastEditFragment = this.mPcMultiHandWriteFragment;
        } else {
            if (this.mPcHandWriteFragment == null) {
                this.mPcHandWriteFragment = createPadHandWriteEditFragment(noteLoadDataEntity);
                z = true;
            }
            this.mLastEditFragment = this.mPcHandWriteFragment;
        }
        Logger.INSTANCE.i("PcMain", "mPcNoteFragment isAdd = " + this.mLastEditFragment.isAdded());
        if (!this.mLastEditFragment.isAdded()) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            getSupportFragmentManager().beginTransaction().add(R.id.right, this.mLastEditFragment, "note").commitAllowingStateLoss();
            if (!supportFragmentManager.isDestroyed()) {
                supportFragmentManager.executePendingTransactions();
            }
        }
        changeNote(noteLoadDataEntity, z);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        this.mLeftFragment.onKeyDown(i, keyEvent);
        return super.onKeyDown(i, keyEvent);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        this.mLeftFragment.onKeyUp(i, keyEvent);
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Activity
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        this.mLeftFragment.onKeyShortCut(i, keyEvent);
        return super.onKeyShortcut(i, keyEvent);
    }

    private void replaceEditFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().replace(R.id.right, this.mLastEditFragment, "note").commitAllowingStateLoss();
        if (supportFragmentManager.isDestroyed()) {
            return;
        }
        supportFragmentManager.executePendingTransactions();
    }

    @Override // com.miui.notes.ui.activity.DialogManagedActivity, com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setAction(FrameConstant.ACTION_DUAL_SCREEN_RUN_STATE_CHANGED);
        intent.putExtra(FrameConstant.PARAM_DUAL_SCREEN_RUN_STATE, 0);
        sendBroadcast(intent);
        FloatWindowManagerAgent.changeGuideVisible(true);
        DualScreenManager.INSTANCE.setPcActivityLiving(false);
        SyncUtils.recoverSyncIfPcStop(this, this.mXiaomiAccount);
        MainHandler<PcMainActivity> mainHandler = this.mHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private BaseNoteDetailFragment createPadNoteEditFragment(NoteLoadDataEntity noteLoadDataEntity) {
        BaseNoteDetailFragment baseNoteDetailFragmentNewInstance = PcNoteFragmentBuilder.newInstance(noteLoadDataEntity);
        baseNoteDetailFragmentNewInstance.setOutKeyListener(this.mLeftFragment.getKeyListener());
        baseNoteDetailFragmentNewInstance.setActionListener(this.mPcNoteActionListener);
        return baseNoteDetailFragmentNewInstance;
    }

    private BaseNoteDetailFragment createPadWebBrainEditFragment(NoteLoadDataEntity noteLoadDataEntity) {
        BaseNoteDetailFragment baseNoteDetailFragmentNewInstance = PcNoteFragmentBuilder.newInstance(noteLoadDataEntity);
        baseNoteDetailFragmentNewInstance.setOutKeyListener(this.mLeftFragment.getKeyListener());
        baseNoteDetailFragmentNewInstance.setActionListener(this.mPcWebBrainActionListener);
        return baseNoteDetailFragmentNewInstance;
    }

    private BaseNoteDetailFragment createPadHandWriteEditFragment(NoteLoadDataEntity noteLoadDataEntity) {
        BaseNoteDetailFragment baseNoteDetailFragmentNewInstance = PcNoteFragmentBuilder.newInstance(noteLoadDataEntity);
        baseNoteDetailFragmentNewInstance.setOutKeyListener(this.mLeftFragment.getKeyListener());
        baseNoteDetailFragmentNewInstance.setActionListener(this.mPcHandWriteActionListener);
        return baseNoteDetailFragmentNewInstance;
    }

    private BaseNoteDetailFragment createPadMultiHandWriteEditFragment(NoteLoadDataEntity noteLoadDataEntity) {
        BaseNoteDetailFragment baseNoteDetailFragmentNewInstance = PcNoteFragmentBuilder.newInstance(noteLoadDataEntity);
        baseNoteDetailFragmentNewInstance.setOutKeyListener(this.mLeftFragment.getKeyListener());
        baseNoteDetailFragmentNewInstance.setActionListener(this.mPcMultiHandWriteActionListener);
        return baseNoteDetailFragmentNewInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTodoRemind(Intent intent) {
        PcTodoRemindDialog pcTodoRemindDialog = this.mTodoRemindDialog;
        if (pcTodoRemindDialog == null) {
            PcTodoRemindDialog pcTodoRemindDialog2 = new PcTodoRemindDialog(this, R.layout.pc_remind_todo_dialog, new PcTodoRemindDialog.ActionListener() { // from class: com.miui.pc.frame.PcMainActivity.6
                @Override // com.miui.pc.feature.remind.PcTodoRemindDialog.ActionListener
                public void onCancel() {
                    PcMainActivity.this.mTodoRemindDialog = null;
                    Intent intent2 = new Intent();
                    intent2.setAction(FrameConstant.ACTION_DUAL_SCREEN_LAUNCH_ALARM_RESULT);
                    intent2.putExtra(FrameConstant.PARAM_SCREEN_ID, 1);
                    LocalBroadcastManager.getInstance(PcMainActivity.this).sendBroadcast(intent2);
                }

                @Override // com.miui.pc.feature.remind.PcTodoRemindDialog.ActionListener
                public void onConfirm() {
                    PcMainActivity.this.mTodoRemindDialog = null;
                }
            });
            this.mTodoRemindDialog = pcTodoRemindDialog2;
            pcTodoRemindDialog2.addData(intent);
            this.mTodoRemindDialog.show(getWindow().getDecorView());
            return;
        }
        pcTodoRemindDialog.addData(intent);
    }

    private void updateCurrentTab(int i) {
        this.mCurTabIndex = i;
        this.mLeftFragment.setCurrentTab(i);
    }

    private void changeNote(NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        this.mLeftFragment.setLastViewNoteId(noteLoadDataEntity.getNoteId());
        this.mLastEditFragment.setmIsFromPc(true);
        this.mLastEditFragment.changeNote(noteLoadDataEntity);
    }

    public boolean isNoteDateEmpty() {
        BaseNoteDetailFragment baseNoteDetailFragment = this.mLastEditFragment;
        if (baseNoteDetailFragment != null && baseNoteDetailFragment.isDataEmpty()) {
            BaseNoteDetailFragment baseNoteDetailFragment2 = this.mLastEditFragment;
            if ((baseNoteDetailFragment2 instanceof PcHybridNoteEditFragment) || (baseNoteDetailFragment2 instanceof PcNoteEditFragment)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMindDateEmpty() {
        BaseNoteDetailFragment baseNoteDetailFragment = this.mLastEditFragment;
        return baseNoteDetailFragment != null && baseNoteDetailFragment.isDataEmpty() && (this.mLastEditFragment instanceof PcWebMindNoteFragment);
    }

    public boolean isBlankViewShow() {
        BaseNoteDetailFragment baseNoteDetailFragment = this.mLastEditFragment;
        return baseNoteDetailFragment != null && baseNoteDetailFragment.isBlankViewShow();
    }

    private void initActivityResultLauncher() {
        this.toCtaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.pc.frame.PcMainActivity.7
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                if (activityResult.getResultCode() == 1) {
                    PcMainActivity.this.needShowCtaDialog = false;
                } else if (activityResult.getResultCode() == 666) {
                    PcMainActivity.this.needShowCtaDialog = false;
                } else if (activityResult.getResultCode() == -3) {
                    PcMainActivity.this.requestCta();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCta() {
        if (this.toCtaLauncher == null || !this.needShowCtaDialog || PreferenceUtils.isCTAAccepted(getApplicationContext()) || !PermissionUtils.supportNewPermissionStyle()) {
            return;
        }
        PermissionNewUtils.showCtaDialog(this.toCtaLauncher);
    }
}
