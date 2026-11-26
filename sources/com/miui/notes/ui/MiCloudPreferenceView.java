package com.miui.notes.ui;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudPreferenceView extends AppCompatTextView {
    private static final int SYNC_COMPLETE = 1;
    private static final int SYNC_OBSERVER_MASK = 13;
    private static final int SYNC_OBSERVER_TYPE_STATUS = 8;
    private int mCloudCountCompleteTextAppearance;
    private int mCloudCountHighlightTextAppearance;
    private int mCloudCountNormalTextAppearance;
    private UpdateStateTask mCurrentUpdateTask;
    private String mDisabledStatusText;
    private Handler mHandler;
    private boolean mNeedToSync;
    private boolean mPendingUpdate;
    private Object mSyncChangeHandle;
    private ISyncInfoProvider mSyncInfoProvider;
    private boolean mSyncing;

    public enum EncStatus {
        ENCRYPT_OFF,
        ENCRYPT_ON
    }

    public interface ILayoutUpdateListener {
        void onLayoutUpdate(boolean z, boolean z2, boolean z3, int[] iArr);
    }

    public enum SyncState {
        SYNC_DISABLED,
        ENC_DISABLED,
        SYNC_PRE_START,
        SYNC_RUNNING,
        SYNC_COMPLETE
    }

    public interface ISyncInfoProvider {
        String getAuthority();

        String getSyncText(SyncState syncState);

        int[] getUnsyncedCount(Context context);

        String getUnsyncedCountText(Context context, int[] iArr);

        Account getXiaomiAccount();

        default EncStatus getEncryptionStatus() {
            return EncStatus.ENCRYPT_ON;
        }
    }

    public MiCloudPreferenceView(Context context) {
        this(context, null);
    }

    public MiCloudPreferenceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.MiCloudStatePreference);
    }

    public MiCloudPreferenceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNeedToSync = false;
        this.mSyncing = false;
        initialize(context, attributeSet, i);
    }

    private void initialize(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MiCloudTextView, R.attr.miuixCloudStateViewStyle, i);
        this.mCloudCountNormalTextAppearance = typedArrayObtainStyledAttributes.getResourceId(2, 0);
        this.mCloudCountHighlightTextAppearance = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        this.mCloudCountCompleteTextAppearance = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.mDisabledStatusText = getResources().getString(R.string.miuix_appcompat_cloud_state_disabled);
        this.mHandler = new updateViewHandle(this);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setTextAppearance(getContext(), this.mCloudCountNormalTextAppearance);
        setSelected(true);
    }

    public void setTotalCountText(String str) {
        setText(str);
    }

    public void setDisabledStatusText(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mDisabledStatusText = str;
    }

    public void setSyncInfoProvider(ISyncInfoProvider iSyncInfoProvider) {
        this.mSyncInfoProvider = iSyncInfoProvider;
    }

    private static class SyncObserver implements SyncStatusObserver {
        WeakReference<MiCloudPreferenceView> mView;

        SyncObserver(MiCloudPreferenceView miCloudPreferenceView) {
            this.mView = new WeakReference<>(miCloudPreferenceView);
        }

        @Override // android.content.SyncStatusObserver
        public void onStatusChanged(int i) {
            MiCloudPreferenceView miCloudPreferenceView = this.mView.get();
            if (miCloudPreferenceView != null) {
                miCloudPreferenceView.updateState(true);
            }
        }
    }

    public void registerObserver() {
        if (this.mSyncChangeHandle == null) {
            this.mSyncChangeHandle = ContentResolver.addStatusChangeListener(13, new SyncObserver(this));
        }
    }

    public void unregisterObserver() {
        Object obj = this.mSyncChangeHandle;
        if (obj != null) {
            ContentResolver.removeStatusChangeListener(obj);
            this.mSyncChangeHandle = null;
        }
    }

    public void updateState() {
        updateState(false);
    }

    public void updateState(final boolean z) {
        if (getHandler() == null || Looper.myLooper() != getHandler().getLooper()) {
            post(new Runnable() { // from class: com.miui.notes.ui.MiCloudPreferenceView.1
                @Override // java.lang.Runnable
                public void run() {
                    MiCloudPreferenceView.this.updateState(z);
                }
            });
            return;
        }
        if (!isAttachedToWindow()) {
            if (z) {
                this.mPendingUpdate = true;
            }
        } else if (z || !this.mSyncing) {
            if (this.mSyncInfoProvider == null) {
                throw new IllegalStateException("mSyncInfoProvider can't be null");
            }
            if (this.mCurrentUpdateTask == null) {
                UpdateStateTask updateStateTask = new UpdateStateTask(this);
                this.mCurrentUpdateTask = updateStateTask;
                updateStateTask.execute(new Void[0]);
                return;
            }
            this.mPendingUpdate = true;
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mPendingUpdate) {
            this.mPendingUpdate = false;
            updateState(true);
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.ui.MiCloudPreferenceView$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$notes$ui$MiCloudPreferenceView$SyncState;

        static {
            int[] iArr = new int[SyncState.values().length];
            $SwitchMap$com$miui$notes$ui$MiCloudPreferenceView$SyncState = iArr;
            try {
                iArr[SyncState.SYNC_DISABLED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$notes$ui$MiCloudPreferenceView$SyncState[SyncState.ENC_DISABLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$notes$ui$MiCloudPreferenceView$SyncState[SyncState.SYNC_PRE_START.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$notes$ui$MiCloudPreferenceView$SyncState[SyncState.SYNC_RUNNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$notes$ui$MiCloudPreferenceView$SyncState[SyncState.SYNC_COMPLETE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSyncText(SyncState syncState) {
        int i = AnonymousClass2.$SwitchMap$com$miui$notes$ui$MiCloudPreferenceView$SyncState[syncState.ordinal()];
        if (i == 1) {
            String syncText = this.mSyncInfoProvider.getSyncText(SyncState.SYNC_DISABLED);
            if (TextUtils.isEmpty(syncText)) {
                syncText = this.mDisabledStatusText;
            }
            setText(syncText);
            setTextAppearance(getContext(), this.mCloudCountNormalTextAppearance);
            return;
        }
        if (i == 2) {
            String syncText2 = this.mSyncInfoProvider.getSyncText(SyncState.ENC_DISABLED);
            if (!TextUtils.isEmpty(syncText2)) {
                setText(syncText2);
            }
            setTextAppearance(getContext(), this.mCloudCountHighlightTextAppearance);
            return;
        }
        if (i == 3) {
            setText(this.mSyncInfoProvider.getSyncText(SyncState.SYNC_PRE_START));
            setTextAppearance(getContext(), this.mCloudCountHighlightTextAppearance);
            return;
        }
        if (i == 4) {
            String syncText3 = this.mSyncInfoProvider.getSyncText(SyncState.SYNC_RUNNING);
            if (TextUtils.isEmpty(syncText3)) {
                setText(R.string.miuix_appcompat_cloud_state_syncing);
            } else {
                setText(syncText3);
            }
            setTextAppearance(getContext(), this.mCloudCountCompleteTextAppearance);
            return;
        }
        if (i != 5) {
            return;
        }
        String syncText4 = this.mSyncInfoProvider.getSyncText(SyncState.SYNC_COMPLETE);
        if (TextUtils.isEmpty(syncText4)) {
            setText(R.string.miuix_appcompat_cloud_state_finished);
        } else {
            setText(syncText4);
        }
        setTextAppearance(getContext(), this.mCloudCountCompleteTextAppearance);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLayout(boolean z, boolean z2, boolean z3, int[] iArr) {
        int totalCount = getTotalCount(iArr);
        if (!z) {
            updateSyncText(SyncState.SYNC_DISABLED);
        } else if (z && z2) {
            updateSyncText(SyncState.ENC_DISABLED);
        } else if (z3) {
            this.mNeedToSync = true;
            updateSyncText(SyncState.SYNC_RUNNING);
        } else if (totalCount > 0) {
            this.mNeedToSync = true;
            updateSyncText(SyncState.SYNC_PRE_START);
        } else if (this.mNeedToSync) {
            if (this.mHandler.hasMessages(1)) {
                this.mHandler.removeMessages(1);
            }
            this.mHandler.sendEmptyMessageDelayed(1, 500L);
        } else {
            updateSyncText(SyncState.SYNC_COMPLETE);
        }
        requestLayout();
    }

    private int getTotalCount(int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            return 0;
        }
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        return i;
    }

    private static class UpdateStateTask extends AsyncTask<Void, Void, Void> {
        private boolean enabled;
        private boolean encryptDisabled = false;
        private WeakReference<MiCloudPreferenceView> mRefs;
        private boolean syncing;
        private int[] unsyncedCounts;

        UpdateStateTask(MiCloudPreferenceView miCloudPreferenceView) {
            this.mRefs = new WeakReference<>(miCloudPreferenceView);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            Logger.INSTANCE.d(getClass().getSimpleName(), "miclouldPreference task doInbackground");
            Thread.currentThread().setName("MiCloudUpdateStateTask");
            WeakReference<MiCloudPreferenceView> weakReference = this.mRefs;
            MiCloudPreferenceView miCloudPreferenceView = weakReference == null ? null : weakReference.get();
            if (miCloudPreferenceView == null) {
                return null;
            }
            Account xiaomiAccount = miCloudPreferenceView.mSyncInfoProvider.getXiaomiAccount();
            if (xiaomiAccount == null) {
                this.enabled = false;
                this.encryptDisabled = false;
                this.syncing = false;
            } else {
                String authority = miCloudPreferenceView.mSyncInfoProvider.getAuthority();
                if (TextUtils.isEmpty(authority)) {
                    this.enabled = false;
                    this.encryptDisabled = false;
                    this.syncing = false;
                } else {
                    this.enabled = ContentResolver.getSyncAutomatically(xiaomiAccount, authority);
                    this.encryptDisabled = miCloudPreferenceView.mSyncInfoProvider.getEncryptionStatus() == EncStatus.ENCRYPT_OFF;
                    this.syncing = ContentResolver.isSyncActive(xiaomiAccount, authority);
                }
            }
            miCloudPreferenceView.mSyncing = this.syncing;
            if (this.enabled && !this.syncing) {
                this.unsyncedCounts = miCloudPreferenceView.mSyncInfoProvider.getUnsyncedCount(miCloudPreferenceView.getContext());
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r4) {
            super.onPostExecute(r4);
            WeakReference<MiCloudPreferenceView> weakReference = this.mRefs;
            MiCloudPreferenceView miCloudPreferenceView = weakReference == null ? null : weakReference.get();
            if (miCloudPreferenceView == null) {
                return;
            }
            miCloudPreferenceView.mCurrentUpdateTask = null;
            if (miCloudPreferenceView.isAttachedToWindow()) {
                miCloudPreferenceView.updateLayout(this.enabled, this.encryptDisabled, this.syncing, this.unsyncedCounts);
                if (miCloudPreferenceView.mPendingUpdate) {
                    miCloudPreferenceView.mPendingUpdate = false;
                    miCloudPreferenceView.updateState(true);
                }
            }
        }
    }

    private static class updateViewHandle extends Handler {
        private final WeakReference<MiCloudPreferenceView> mView;

        public updateViewHandle(MiCloudPreferenceView miCloudPreferenceView) {
            this.mView = new WeakReference<>(miCloudPreferenceView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            MiCloudPreferenceView miCloudPreferenceView = this.mView.get();
            if (miCloudPreferenceView == null || message.what != 1) {
                return;
            }
            miCloudPreferenceView.updateSyncText(SyncState.SYNC_COMPLETE);
        }
    }
}
