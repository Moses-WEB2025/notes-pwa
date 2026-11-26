package com.miui.common.view;

import android.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.miui.common.record.AudioInputListener;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes2.dex */
public class AudioCreateWindow implements IAudioCreatePanel {
    private static final String TAG = "AudioCreatePanel";
    private Activity mActivity;
    private FrameLayout mActivityContentView;
    private String mAudioFileFolderName;
    private AudioInputListener mAudioInputListener;
    private View mContentView;
    private View mDimBgView;
    private View mFakeBtn;
    private boolean mIsInPrivate;
    private View mRootView;
    private BaseAudioCreatePanel.StateCallback mStateCallback;
    private BaseAudioCreatePanel mAudioCreatePanel = null;
    private boolean mIsShow = false;
    private boolean mIsCanceling = false;
    private TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.common.view.AudioCreateWindow.1
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.common.view.AudioCreateWindow.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (AudioCreateWindow.this.mRootView == null || (viewGroup = (ViewGroup) AudioCreateWindow.this.mRootView.getParent()) == null) {
                return;
            }
            viewGroup.removeView(AudioCreateWindow.this.mRootView);
        }
    };

    public AudioCreateWindow(Activity activity, String str, BaseAudioCreatePanel.StateCallback stateCallback, AudioInputListener audioInputListener, boolean z) {
        this.mIsInPrivate = false;
        this.mActivity = activity;
        this.mAudioFileFolderName = str;
        this.mStateCallback = stateCallback;
        this.mAudioInputListener = audioInputListener;
        this.mIsInPrivate = z;
    }

    public void show(Activity activity) {
        if (this.mActivity.isFinishing()) {
            return;
        }
        this.mIsShow = true;
        this.mActivityContentView = (FrameLayout) activity.findViewById(R.id.content);
        View viewInflate = LayoutInflater.from(activity).inflate(com.miui.notes.R.layout.audio_create_group, (ViewGroup) null);
        this.mRootView = viewInflate;
        this.mContentView = viewInflate.findViewById(com.miui.notes.R.id.audio_create_panel);
        initView();
        if (this.mIsInPrivate || !UIUtils.isHyperOsAndAbove()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mFakeBtn.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, (int) (layoutParams.bottomMargin - NoteApp.getInstance().getResources().getDimension(com.miui.notes.R.dimen.audio_create_panel_btn_margin_bottom_private)));
            this.mFakeBtn.setLayoutParams(layoutParams);
        }
        if (!UIUtils.isInFullWindowGestureMode(activity) || !DisplayUtils.isEnableGestureLine(activity)) {
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mFakeBtn.getLayoutParams();
            layoutParams2.setMargins(layoutParams2.leftMargin, layoutParams2.topMargin, layoutParams2.rightMargin, (int) (layoutParams2.bottomMargin - NoteApp.getInstance().getResources().getDimension(com.miui.notes.R.dimen.audio_create_panel_btn_margin_bottom_delta)));
            this.mFakeBtn.setLayoutParams(layoutParams2);
        }
        this.mActivityContentView.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mActivityContentView.post(new Runnable() { // from class: com.miui.common.view.AudioCreateWindow.3
            @Override // java.lang.Runnable
            public void run() {
                AudioCreateWindow.this.startEnterAnim();
                AudioCreateWindow.this.mAudioCreatePanel.show();
            }
        });
    }

    public void dismiss() {
        if (this.mActivity.isFinishing()) {
            return;
        }
        this.mIsShow = false;
        startExitAnim(null);
    }

    public boolean isShowing() {
        return this.mIsShow;
    }

    private void initView() {
        View viewFindViewById = this.mRootView.findViewById(com.miui.notes.R.id.dialog_dim_bg);
        this.mDimBgView = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.common.view.AudioCreateWindow.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AudioCreateWindow.this.mIsCanceling) {
                    AudioCreateWindow.this.cancelRecord();
                } else {
                    AudioCreateWindow.this.completeRecord();
                }
            }
        });
        BaseAudioCreatePanel baseAudioCreatePanel = new BaseAudioCreatePanel(this.mActivity, this.mRootView, this.mAudioFileFolderName, this.mStateCallback);
        this.mAudioCreatePanel = baseAudioCreatePanel;
        baseAudioCreatePanel.setAudioInputListener(this.mAudioInputListener);
        View viewFindViewById2 = this.mRootView.findViewById(com.miui.notes.R.id.fake_menu_add);
        this.mFakeBtn = viewFindViewById2;
        viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.common.view.AudioCreateWindow.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AudioCreateWindow.this.mIsCanceling) {
                    AudioCreateWindow.this.cancelRecord();
                } else {
                    AudioCreateWindow.this.completeRecord();
                }
            }
        });
    }

    public void updateLayout() {
        View view = this.mContentView;
        view.setPadding(0, view.getPaddingTop(), 0, UIUtils.getNavigationBarHeight(this.mActivity));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startEnterAnim() {
        Folme.useAt(this.mDimBgView).state().to(ViewProperty.ALPHA, Float.valueOf(0.3f), new AnimConfig().addListeners(this.mShowListener));
    }

    private void startExitAnim(Runnable runnable) {
        Folme.useAt(this.mDimBgView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    @Override // com.miui.common.view.IAudioCreatePanel
    public void prepareRecord() {
        this.mIsCanceling = false;
        this.mAudioCreatePanel.prepareRecord();
    }

    @Override // com.miui.common.view.IAudioCreatePanel
    public void willCancelRecord() {
        this.mIsCanceling = true;
        this.mAudioCreatePanel.willCancelRecord();
    }

    @Override // com.miui.common.view.IAudioCreatePanel
    public void completeRecord() {
        this.mAudioCreatePanel.completeRecord();
    }

    @Override // com.miui.common.view.IAudioCreatePanel
    public void cancelRecord() {
        this.mAudioCreatePanel.cancelRecord();
    }
}
