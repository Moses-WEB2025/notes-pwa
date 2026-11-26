package miuix.micloudview;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.view.CompatViewMethod;

/* JADX INFO: loaded from: classes5.dex */
public class MiCloudStateView extends FrameLayout {
    private static final int SYNC_OBSERVER_MASK = 13;
    private static final int SYNC_OBSERVER_TYPE_STATUS = 8;
    private int mArrowPadding;
    private Drawable mArrowRight;
    private Drawable mCloudBackground;
    private LinearLayout mCloudBackgroundView;
    private int mCloudCountHighlightTextAppearance;
    private int mCloudCountNormalTextAppearance;
    private UpdateStateTask mCurrentUpdateTask;
    private FrameLayout mCustomView;
    private String mDisabledStatusText;
    private Handler mHandler;
    private ILayoutUpdateListener mLayoutUpdateListener;
    private TextView mMiCloudCountText;
    private boolean mPendingUpdate;
    private Drawable mSync;
    private Object mSyncChangeHandle;
    private ISyncInfoProvider mSyncInfoProvider;
    private boolean mSyncing;
    private Drawable mUnsync;

    public interface ILayoutUpdateListener {
        void onLayoutUpdate(boolean z, boolean z2, int[] iArr);
    }

    public interface ISyncInfoProvider {
        String getAuthority();

        String getSyncText(SyncState syncState);

        int[] getUnsyncedCount(Context context);

        String getUnsyncedCountText(Context context, int[] iArr);

        Account getXiaomiAccount();
    }

    public enum SyncState {
        SYNC_DISABLED,
        SYNC_PRE_START,
        SYNC_RUNNING,
        SYNC_COMPLETE
    }

    public MiCloudStateView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.MiCloudStateView_DayNight);
    }

    public MiCloudStateView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSyncing = false;
        initialize(context, attributeSet, i);
    }

    private void initialize(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MiuixAppcompatMiCloudStateView, R.attr.miuixCloudStateViewStyle, i);
        this.mCloudCountNormalTextAppearance = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MiuixAppcompatMiCloudStateView_miuixAppcompatCloudCountNormalTextAppearance, 0);
        this.mCloudCountHighlightTextAppearance = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MiuixAppcompatMiCloudStateView_miuixAppcompatCloudCountHighlightTextAppearance, 0);
        this.mCloudBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.MiuixAppcompatMiCloudStateView_miuixAppcompatCloudStatusBackground);
        this.mArrowRight = typedArrayObtainStyledAttributes.getDrawable(R.styleable.MiuixAppcompatMiCloudStateView_miuixAppcompatCloudArrow);
        this.mSync = typedArrayObtainStyledAttributes.getDrawable(R.styleable.MiuixAppcompatMiCloudStateView_miuixAppcompatCloudSyncIcon);
        this.mUnsync = typedArrayObtainStyledAttributes.getDrawable(R.styleable.MiuixAppcompatMiCloudStateView_miuixAppcompatCloudUnSyncIcon);
        typedArrayObtainStyledAttributes.recycle();
        this.mDisabledStatusText = getResources().getString(R.string.miuix_appcompat_cloud_state_disabled);
        this.mArrowPadding = getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_cloud_btn_padding);
        this.mHandler = new Handler();
        Folme.useAt(this).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).handleTouchOf(this, new AnimConfig[0]);
        Folme.useAt(this).hover().setEffect(IHoverStyle.HoverEffect.FLOATED).handleHoverOf(this, new AnimConfig[0]);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mMiCloudCountText = (TextView) findViewById(R.id.cloud_count);
        this.mCustomView = (FrameLayout) findViewById(R.id.custom_view);
        this.mCloudBackgroundView = (LinearLayout) findViewById(android.R.id.inputArea);
        this.mMiCloudCountText.setTextAppearance(getContext(), this.mCloudCountNormalTextAppearance);
        this.mMiCloudCountText.setSelected(true);
        this.mCloudBackgroundView.setBackground(this.mCloudBackground);
        CompatViewMethod.setForceDarkAllowed(this.mCloudBackgroundView, false);
    }

    public void setCustomView(View view) {
        if (view != null) {
            this.mCustomView.setVisibility(0);
            this.mCustomView.removeAllViews();
            this.mCustomView.addView(view);
            updateRightStatusIcon(null);
            return;
        }
        this.mCustomView.setVisibility(8);
        this.mCustomView.removeAllViews();
        updateState(true);
    }

    public void setCustomView(int i) {
        setCustomView(LayoutInflater.from(getContext()).inflate(i, (ViewGroup) this.mCustomView, false));
    }

    private boolean hasCustomView() {
        return this.mCustomView.getVisibility() == 0;
    }

    public void setTotalCountText(String str) {
        this.mMiCloudCountText.setText(str);
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

    public void setLayoutUpdateListener(ILayoutUpdateListener iLayoutUpdateListener) {
        this.mLayoutUpdateListener = iLayoutUpdateListener;
    }

    private static class SyncObserver implements SyncStatusObserver {
        WeakReference<MiCloudStateView> mView;

        SyncObserver(MiCloudStateView miCloudStateView) {
            this.mView = new WeakReference<>(miCloudStateView);
        }

        @Override // android.content.SyncStatusObserver
        public void onStatusChanged(int i) {
            MiCloudStateView miCloudStateView = this.mView.get();
            if (miCloudStateView != null) {
                miCloudStateView.updateState(true);
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
        if (Looper.getMainLooper() != Looper.myLooper()) {
            this.mHandler.post(new Runnable() { // from class: miuix.micloudview.MiCloudStateView.1
                @Override // java.lang.Runnable
                public void run() {
                    MiCloudStateView.this.updateState(z);
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

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mPendingUpdate) {
            this.mPendingUpdate = false;
            updateState(true);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateRightStatusIcon(Drawable drawable) {
        this.mMiCloudCountText.setCompoundDrawablePadding(drawable != 0 ? this.mArrowPadding : 0);
        this.mMiCloudCountText.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
        Object obj = this.mSync;
        if (drawable != obj) {
            ((Animatable) obj).stop();
        }
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    /* JADX INFO: renamed from: miuix.micloudview.MiCloudStateView$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$miuix$micloudview$MiCloudStateView$SyncState;

        static {
            int[] iArr = new int[SyncState.values().length];
            $SwitchMap$miuix$micloudview$MiCloudStateView$SyncState = iArr;
            try {
                iArr[SyncState.SYNC_DISABLED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$miuix$micloudview$MiCloudStateView$SyncState[SyncState.SYNC_PRE_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$miuix$micloudview$MiCloudStateView$SyncState[SyncState.SYNC_RUNNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$miuix$micloudview$MiCloudStateView$SyncState[SyncState.SYNC_COMPLETE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void updateSyncText(SyncState syncState) {
        int i = AnonymousClass2.$SwitchMap$miuix$micloudview$MiCloudStateView$SyncState[syncState.ordinal()];
        if (i == 1) {
            if (!hasCustomView()) {
                updateRightStatusIcon(this.mArrowRight);
            }
            String syncText = this.mSyncInfoProvider.getSyncText(SyncState.SYNC_DISABLED);
            TextView textView = this.mMiCloudCountText;
            if (TextUtils.isEmpty(syncText)) {
                syncText = this.mDisabledStatusText;
            }
            textView.setText(syncText);
            this.mMiCloudCountText.setTextAppearance(getContext(), this.mCloudCountNormalTextAppearance);
            return;
        }
        if (i == 2) {
            this.mMiCloudCountText.setText(this.mSyncInfoProvider.getSyncText(SyncState.SYNC_PRE_START));
            updateRightStatusIcon(this.mUnsync);
            this.mMiCloudCountText.setTextAppearance(getContext(), this.mCloudCountHighlightTextAppearance);
            return;
        }
        if (i == 3) {
            updateRightStatusIcon(this.mSync);
            String syncText2 = this.mSyncInfoProvider.getSyncText(SyncState.SYNC_RUNNING);
            if (TextUtils.isEmpty(syncText2)) {
                this.mMiCloudCountText.setText(R.string.miuix_appcompat_cloud_state_syncing);
            } else {
                this.mMiCloudCountText.setText(syncText2);
            }
            this.mMiCloudCountText.setTextAppearance(getContext(), this.mCloudCountNormalTextAppearance);
            return;
        }
        if (i != 4) {
            return;
        }
        String syncText3 = this.mSyncInfoProvider.getSyncText(SyncState.SYNC_COMPLETE);
        if (TextUtils.isEmpty(syncText3)) {
            this.mMiCloudCountText.setText(R.string.miuix_appcompat_cloud_state_finished);
        } else {
            this.mMiCloudCountText.setText(syncText3);
        }
        updateRightStatusIcon(this.mArrowRight);
        this.mMiCloudCountText.setTextAppearance(getContext(), this.mCloudCountNormalTextAppearance);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLayout(boolean z, boolean z2, int[] iArr) {
        int totalCount = getTotalCount(iArr);
        if (!z) {
            updateSyncText(SyncState.SYNC_DISABLED);
        } else if (!z2) {
            if (totalCount > 0) {
                updateSyncText(SyncState.SYNC_PRE_START);
            } else {
                updateSyncText(SyncState.SYNC_COMPLETE);
            }
            if (hasCustomView()) {
                updateRightStatusIcon(null);
            }
        } else {
            updateSyncText(SyncState.SYNC_RUNNING);
            if (hasCustomView()) {
                updateRightStatusIcon(null);
            }
        }
        ILayoutUpdateListener iLayoutUpdateListener = this.mLayoutUpdateListener;
        if (iLayoutUpdateListener != null) {
            iLayoutUpdateListener.onLayoutUpdate(z, z2, iArr);
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
        private WeakReference<MiCloudStateView> mRefs;
        private boolean syncing;
        private int[] unsyncedCounts;

        UpdateStateTask(MiCloudStateView miCloudStateView) {
            this.mRefs = new WeakReference<>(miCloudStateView);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            WeakReference<MiCloudStateView> weakReference = this.mRefs;
            MiCloudStateView miCloudStateView = weakReference == null ? null : weakReference.get();
            if (miCloudStateView == null) {
                return null;
            }
            Account xiaomiAccount = miCloudStateView.mSyncInfoProvider.getXiaomiAccount();
            if (xiaomiAccount != null) {
                String authority = miCloudStateView.mSyncInfoProvider.getAuthority();
                if (TextUtils.isEmpty(authority)) {
                    this.enabled = false;
                    this.syncing = false;
                } else {
                    this.enabled = ContentResolver.getSyncAutomatically(xiaomiAccount, authority);
                    this.syncing = ContentResolver.isSyncActive(xiaomiAccount, authority);
                }
            } else {
                this.enabled = false;
                this.syncing = false;
            }
            miCloudStateView.mSyncing = this.syncing;
            if (this.enabled && !this.syncing) {
                this.unsyncedCounts = miCloudStateView.mSyncInfoProvider.getUnsyncedCount(miCloudStateView.getContext());
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r3) {
            super.onPostExecute(r3);
            WeakReference<MiCloudStateView> weakReference = this.mRefs;
            MiCloudStateView miCloudStateView = weakReference == null ? null : weakReference.get();
            if (miCloudStateView == null) {
                return;
            }
            miCloudStateView.mCurrentUpdateTask = null;
            if (miCloudStateView.isAttachedToWindow()) {
                miCloudStateView.updateLayout(this.enabled, this.syncing, this.unsyncedCounts);
                if (miCloudStateView.mPendingUpdate) {
                    miCloudStateView.mPendingUpdate = false;
                    miCloudStateView.updateState(true);
                }
            }
        }
    }
}
