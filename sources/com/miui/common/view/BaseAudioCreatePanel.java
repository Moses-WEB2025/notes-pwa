package com.miui.common.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.miui.common.anim.AudioCreatePanelOutlineProvider;
import com.miui.common.record.AudioInputListener;
import com.miui.common.record.SpeechRecognitionListener;
import com.miui.common.record.SpeechRecognitionTask;
import com.miui.common.record.SpeechRecognitionTaskImp;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.NetworkCallbackImpl;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.ai.mibrain.MibrainException;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class BaseAudioCreatePanel implements IAudioCreatePanel {
    private static final String TAG = "BaseAudioCreatePanel";
    private EditText mAudioResult;
    ConnectivityManager mConnMgr;
    private ValueAnimator mContentAnimator;
    private final Context mContext;
    private TextView mCountDownWarnView;
    protected LinearLayout mEditorMaskView;
    private int mEnd;
    private ValueAnimator mExpandAnimator;
    private View mFakeBtn;
    private boolean mIsWarned;
    private final String mMp3DirName;
    private final NetworkCallbackImpl.NetworkCall mNetworkCall;
    NetworkCallbackImpl mNetworkCallback;
    private ViewGroup mPanel;
    private SpeechRecognitionTask mRecordTask;
    private Disposable mRecordTimerTask;
    NetworkRequest mRequest;
    private final ViewGroup mRootView;
    private int mStart;
    private final StateCallback mStateCallback;
    private TextView mTvTint;
    private RecordingWaveView mWaveView;
    private SpeechRecognitionImpl speechRecognitionListener;
    private static final long MAX_RECORD_TIME = TimeUnit.SECONDS.toMillis(300);
    private static final long LAST_WARRING_TIME = TimeUnit.SECONDS.toMillis(10);
    private boolean mIsRecording = false;
    private boolean mSaveRecord = false;
    private String mAudioFileName = null;
    private StringBuilder mAudioResultSb = new StringBuilder();
    private AudioInputListener mAudioInputListener = null;
    private boolean mAnimReverse = false;
    private final AudioCreatePanelOutlineProvider mPanelOutline = new AudioCreatePanelOutlineProvider();
    private final ValueAnimator.AnimatorUpdateListener mAnimUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.common.view.BaseAudioCreatePanel.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            if (BaseAudioCreatePanel.this.mStateCallback != null) {
                BaseAudioCreatePanel.this.mStateCallback.onAnimUpdate(fFloatValue);
            }
            BaseAudioCreatePanel.this.mPanelOutline.setProgress(fFloatValue);
            BaseAudioCreatePanel.this.mPanel.setOutlineProvider(BaseAudioCreatePanel.this.mPanelOutline);
            BaseAudioCreatePanel.this.mPanel.invalidateOutline();
            BaseAudioCreatePanel.this.mFakeBtn.setAlpha(fFloatValue);
            if (fFloatValue >= 0.7f) {
                BaseAudioCreatePanel.this.mAudioResult.setTranslationX(BaseAudioCreatePanel.this.mPanelOutline.getDrawRect().left);
                BaseAudioCreatePanel.this.mAudioResult.setTranslationY(BaseAudioCreatePanel.this.mPanelOutline.getDrawRect().top);
            }
        }
    };
    private final ValueAnimator.AnimatorUpdateListener mContentAnimUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.common.view.BaseAudioCreatePanel$$ExternalSyntheticLambda0
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f$0.lambda$new$0(valueAnimator);
        }
    };
    private final Animator.AnimatorListener mPanelAnimListener = new Animator.AnimatorListener() { // from class: com.miui.common.view.BaseAudioCreatePanel.2
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (!BaseAudioCreatePanel.this.mAnimReverse) {
                if (BaseAudioCreatePanel.this.mStateCallback != null) {
                    BaseAudioCreatePanel.this.mStateCallback.onShow();
                    return;
                }
                return;
            }
            BaseAudioCreatePanel.this.mPanel.setVisibility(4);
            BaseAudioCreatePanel.this.mRootView.setVisibility(8);
            BaseAudioCreatePanel.this.mTvTint.setText("");
            BaseAudioCreatePanel.this.mTvTint.setTextColor(BaseAudioCreatePanel.this.getColor(R.color.todo_item_content_text_hint_color));
            if (BaseAudioCreatePanel.this.mStateCallback != null) {
                BaseAudioCreatePanel.this.mStateCallback.onDismiss();
            }
        }
    };

    public interface StateCallback {
        void onAnimUpdate(float f);

        void onCancelRecord();

        void onCompleteRecord();

        void onDismiss();

        void onPrepareRecord();

        void onShow();

        void onToast(String str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        setViewsAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public BaseAudioCreatePanel(Context context, View view, String str, StateCallback stateCallback) {
        NetworkCallbackImpl.NetworkCall networkCall = new NetworkCallbackImpl.NetworkCall() { // from class: com.miui.common.view.BaseAudioCreatePanel.3
            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onAvailable() {
                if (BaseAudioCreatePanel.this.mRecordTask != null) {
                    BaseAudioCreatePanel.this.mRecordTask.setNetWorkValid(true);
                    BaseAudioCreatePanel.this.mRecordTask.startEngine();
                }
            }

            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onLost() {
                if (BaseAudioCreatePanel.this.mRecordTask != null) {
                    BaseAudioCreatePanel.this.mRecordTask.setNetWorkValid(false);
                    BaseAudioCreatePanel.this.mRecordTask.stopEngine();
                    Toast.makeText(NoteApp.getInstance(), BaseAudioCreatePanel.this.getString(R.string.audio_toast_network_is_not_available), 1).show();
                }
            }
        };
        this.mNetworkCall = networkCall;
        this.mNetworkCallback = new NetworkCallbackImpl(networkCall);
        this.mRequest = new NetworkRequest.Builder().build();
        this.mConnMgr = (ConnectivityManager) NoteApp.getInstance().getSystemService("connectivity");
        this.mContext = context;
        this.mMp3DirName = str;
        this.mStateCallback = stateCallback;
        this.mRootView = (ViewGroup) view;
        initView(view);
    }

    public void initView(View view) {
        this.mFakeBtn = view.findViewById(R.id.fake_menu_add);
        this.mPanel = (ViewGroup) view.findViewById(R.id.audio_create_panel);
        this.mTvTint = (TextView) view.findViewById(R.id.tint);
        this.mAudioResult = (EditText) view.findViewById(R.id.audio_content);
        this.mWaveView = (RecordingWaveView) view.findViewById(R.id.record_wave);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.edit_mask);
        this.mEditorMaskView = linearLayout;
        linearLayout.setVisibility(0);
    }

    public void reset() {
        this.mAudioResult.setText((CharSequence) null);
    }

    private Context getContext() {
        return this.mContext;
    }

    public void show() {
        reset();
        this.mWaveView.setEnabled(true);
        this.mTvTint.setText(getString(R.string.audio_create_normal_state));
        setViewsAlpha(0.0f);
        if (RomUtils.isInternationalBuild()) {
            this.mAudioResult.setVisibility(8);
        }
        ((ImageView) this.mFakeBtn).setImageResource(R.drawable.action_button_main_voice_normal_light);
        this.mRootView.setVisibility(0);
        this.mRootView.post(new Runnable() { // from class: com.miui.common.view.BaseAudioCreatePanel$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$show$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$1() {
        this.mPanel.setClipToOutline(true);
        this.mPanel.setVisibility(0);
        this.mPanelOutline.setStartPoint((this.mFakeBtn.getLeft() + this.mFakeBtn.getRight()) / 2, (this.mPanel.getHeight() - ((this.mPanel.getBottom() - this.mFakeBtn.getBottom()) + this.mFakeBtn.getPaddingBottom())) - ((this.mFakeBtn.getHeight() - this.mFakeBtn.getPaddingBottom()) / 2));
        this.mPanelOutline.setRadius(((int) (NoteApp.getInstance().getResources().getDimension(R.dimen.floating_action_button_size) - this.mFakeBtn.getPaddingBottom())) / 2, (int) NoteApp.getInstance().getResources().getDimension(R.dimen.v11_default_radius));
        expandPanel();
        Folme.useAt(this.mFakeBtn).touch().setTouchDown();
    }

    public void dismiss() {
        collapsePanel();
        stopWaveAnim();
        this.mSaveRecord = false;
    }

    @Override // com.miui.common.view.IAudioCreatePanel
    public void prepareRecord() {
        this.mWaveView.setEnabled(true);
        this.mTvTint.setText(getString(R.string.audio_create_normal_state));
        this.mTvTint.setTextColor(getColor(R.color.todo_item_content_text_hint_color));
        ((ImageView) this.mFakeBtn).setImageResource(R.drawable.action_button_main_voice_normal_light);
        if (!this.mIsRecording) {
            this.mIsRecording = true;
            startRecordTask();
        }
        startWaveAnim();
        Folme.useAt(this.mFakeBtn).touch().touchDown(new AnimConfig[0]);
    }

    @Override // com.miui.common.view.IAudioCreatePanel
    public void completeRecord() {
        if (this.mIsRecording) {
            this.mIsRecording = false;
            this.mSaveRecord = true;
            stopRecordTask();
            this.mTvTint.announceForAccessibility(getString(R.string.new_audio_create_done_talkback_des));
        }
        Folme.useAt(this.mFakeBtn).touch().touchUp(new AnimConfig[0]);
    }

    @Override // com.miui.common.view.IAudioCreatePanel
    public void willCancelRecord() {
        this.mWaveView.setEnabled(false);
        this.mTvTint.setText(getString(R.string.audio_create_will_cancel_state));
        this.mTvTint.setTextColor(getColor(R.color.warn_color_red));
        ((ImageView) this.mFakeBtn).setImageResource(R.drawable.ic_audio_create_close_btn);
        Folme.useAt(this.mFakeBtn).touch().touchUp(new AnimConfig[0]);
    }

    @Override // com.miui.common.view.IAudioCreatePanel
    public void cancelRecord() {
        if (this.mIsRecording) {
            this.mIsRecording = false;
            this.mSaveRecord = false;
            StateCallback stateCallback = this.mStateCallback;
            if (stateCallback != null) {
                stateCallback.onCancelRecord();
            }
            stopRecordTask();
            this.mTvTint.announceForAccessibility(getString(R.string.new_audio_create_cancel_talkback_des));
        }
        dismiss();
    }

    private void startWaveAnim() {
        this.mWaveView.startDraw();
    }

    private void stopWaveAnim() {
        this.mWaveView.stopDraw();
    }

    private void setViewsAlpha(float f) {
        this.mWaveView.setAlpha(f);
        this.mTvTint.setAlpha(f);
        this.mAudioResult.setAlpha(f);
    }

    public void setAudioInputListener(AudioInputListener audioInputListener) {
        this.mAudioInputListener = audioInputListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateAudioResult(String str) {
        boolean zIsEmpty = TextUtils.isEmpty(this.mAudioFileName);
        if (this.mAudioInputListener != null && !zIsEmpty) {
            this.mAudioInputListener.onAppendText(AudioUtils.createAudioLabel(this.mAudioFileName) + str, this.mAudioFileName);
        }
        if (this.mSaveRecord) {
            StateCallback stateCallback = this.mStateCallback;
            if (stateCallback != null) {
                if (!zIsEmpty) {
                    stateCallback.onCompleteRecord();
                } else {
                    stateCallback.onCancelRecord();
                }
            }
            dismiss();
        }
    }

    private void startRecordTask() {
        if (this.mRecordTask == null) {
            this.speechRecognitionListener = new SpeechRecognitionImpl();
            this.mRecordTask = new SpeechRecognitionTaskImp(NoteApp.getInstance(), this.mMp3DirName, this.speechRecognitionListener);
        }
        startAudioInput();
        this.mNetworkCallback.initNetWorkState();
        boolean isNetWorkAvailable = this.mNetworkCallback.getIsNetWorkAvailable();
        Logger.INSTANCE.d(TAG, "isNetworkAvailable " + isNetWorkAvailable);
        if (!this.mRecordTask.isSkipSpeechRecognition()) {
            registerNetWorkListener();
            if (!isNetWorkAvailable) {
                Toast.makeText(NoteApp.getInstance(), getString(R.string.audio_toast_network_is_not_available), 1).show();
            }
        }
        try {
            this.mRecordTask.setNetWorkValid(isNetWorkAvailable);
            this.mRecordTask.start();
            this.mIsWarned = false;
            startRecordTimer();
        } catch (MibrainException unused) {
            Logger.INSTANCE.e(TAG, "Fail to start record mRecordTask!");
        }
    }

    private void stopRecordTask() {
        if (this.mRecordTask != null) {
            stopRecordTimer();
            this.mRecordTask.shutdownNow();
            if (!this.mRecordTask.isSkipSpeechRecognition()) {
                unRegisterNetWorkListener();
            }
            this.mIsWarned = false;
            this.mEditorMaskView.removeAllViews();
        }
        this.mWaveView.reset();
    }

    private void startRecordTimer() {
        Observable.interval(1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.miui.common.view.BaseAudioCreatePanel.4
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                BaseAudioCreatePanel.this.mRecordTimerTask = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                long jLongValue = l.longValue() * 1000;
                if (!BaseAudioCreatePanel.this.mIsWarned && jLongValue >= BaseAudioCreatePanel.MAX_RECORD_TIME - BaseAudioCreatePanel.LAST_WARRING_TIME) {
                    BaseAudioCreatePanel.this.mIsWarned = true;
                    BaseAudioCreatePanel.this.mEditorMaskView.addView(BaseAudioCreatePanel.this.getCountDownView());
                } else if (jLongValue >= BaseAudioCreatePanel.MAX_RECORD_TIME) {
                    BaseAudioCreatePanel.this.mEditorMaskView.removeAllViews();
                    BaseAudioCreatePanel.this.completeRecord();
                }
                if (BaseAudioCreatePanel.this.mIsWarned) {
                    BaseAudioCreatePanel.this.mCountDownWarnView.setText(Utils.obtainLocalInteger(((int) Math.floor((BaseAudioCreatePanel.MAX_RECORD_TIME - jLongValue) / 1000)) + 1, R.plurals.record_last_ten_seconds_reminder));
                }
            }
        });
    }

    private void stopRecordTimer() {
        Disposable disposable = this.mRecordTimerTask;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mRecordTimerTask.dispose();
    }

    private void startAudioInput() {
        this.mStart = 0;
        this.mEnd = 0;
        this.mAudioResult.setSelection(0, 0);
    }

    private void expandPanel() {
        this.mAnimReverse = false;
        if (this.mExpandAnimator == null) {
            ValueAnimator duration = new ValueAnimator().setDuration(350L);
            this.mExpandAnimator = duration;
            duration.addUpdateListener(this.mAnimUpdateListener);
            this.mExpandAnimator.addListener(this.mPanelAnimListener);
            this.mExpandAnimator.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.95f).setResponse(0.85f));
            this.mExpandAnimator.setFloatValues(0.0f, 1.0f);
        }
        this.mExpandAnimator.start();
        if (this.mContentAnimator == null) {
            ValueAnimator duration2 = new ValueAnimator().setDuration(150L);
            this.mContentAnimator = duration2;
            duration2.addUpdateListener(this.mContentAnimUpdateListener);
            this.mExpandAnimator.setInterpolator(new EaseManager.SpringInterpolator().setDamping(0.95f).setResponse(0.67f));
            this.mContentAnimator.setFloatValues(0.0f, 1.0f);
        }
        this.mContentAnimator.setStartDelay(200L);
        this.mContentAnimator.start();
    }

    private void collapsePanel() {
        this.mAnimReverse = true;
        ValueAnimator valueAnimator = this.mExpandAnimator;
        if (valueAnimator != null) {
            valueAnimator.reverse();
        }
        ValueAnimator valueAnimator2 = this.mContentAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.reverse();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getCountDownView() {
        this.mCountDownWarnView = new TextView(NoteApp.getInstance());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        Rect rect = new Rect();
        this.mPanel.getLocalVisibleRect(rect);
        layoutParams.topMargin = (rect.bottom - rect.top) / 2;
        this.mCountDownWarnView.setLayoutParams(layoutParams);
        this.mCountDownWarnView.setBackground(getDrawable(R.drawable.countdown_view_backgound));
        this.mCountDownWarnView.setTextColor(getColor(R.color.countdown_view_text_color));
        this.mCountDownWarnView.setTextSize(0, NoteApp.getInstance().getResources().getDimension(R.dimen.bubble_text_size));
        return this.mCountDownWarnView;
    }

    private void registerNetWorkListener() {
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(this.mRequest, this.mNetworkCallback);
        }
    }

    private void unRegisterNetWorkListener() {
        ConnectivityManager connectivityManager = this.mConnMgr;
        if (connectivityManager != null) {
            connectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        }
    }

    private static class SpeechRecognitionImpl implements SpeechRecognitionListener {
        private WeakReference<BaseAudioCreatePanel> iContainerRef;

        private SpeechRecognitionImpl(BaseAudioCreatePanel baseAudioCreatePanel) {
            this.iContainerRef = new WeakReference<>(baseAudioCreatePanel);
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrAppendTextResult(String str, boolean z) {
            BaseAudioCreatePanel baseAudioCreatePanel = this.iContainerRef.get();
            if (baseAudioCreatePanel != null) {
                if (!z) {
                    if (str.equals("。")) {
                        String string = baseAudioCreatePanel.mAudioResult.getText().subSequence(0, baseAudioCreatePanel.mStart).toString();
                        if (string.length() > 0) {
                            if (string.endsWith("。")) {
                                return;
                            }
                            if (string.endsWith(",")) {
                                baseAudioCreatePanel.mStart--;
                            }
                        }
                    }
                    baseAudioCreatePanel.mAudioResult.getText().replace(baseAudioCreatePanel.mStart, baseAudioCreatePanel.mEnd, str);
                    baseAudioCreatePanel.mStart += str.length();
                    baseAudioCreatePanel.mEnd = baseAudioCreatePanel.mStart;
                } else {
                    baseAudioCreatePanel.mAudioResult.getText().replace(baseAudioCreatePanel.mStart, baseAudioCreatePanel.mEnd, str);
                    baseAudioCreatePanel.mEnd = baseAudioCreatePanel.mStart + str.length();
                }
                baseAudioCreatePanel.mAudioResultSb.delete(0, baseAudioCreatePanel.mAudioResultSb.length());
                baseAudioCreatePanel.mAudioResultSb.append(baseAudioCreatePanel.mAudioResult.getText().toString());
            }
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrSaveAudioFileName(String str) {
            BaseAudioCreatePanel baseAudioCreatePanel = this.iContainerRef.get();
            if (baseAudioCreatePanel != null) {
                baseAudioCreatePanel.mAudioFileName = str;
                baseAudioCreatePanel.onUpdateAudioResult(baseAudioCreatePanel.mAudioResultSb.toString());
            }
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrMaxAmplitudeUpdate(short s) {
            this.iContainerRef.get().mWaveView.addVolumes(s);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getString(int i) {
        return NoteApp.getInstance().getResources().getString(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getColor(int i) {
        return NoteApp.getInstance().getResources().getColor(i, null);
    }

    private Drawable getDrawable(int i) {
        return NoteApp.getInstance().getResources().getDrawable(i, null);
    }
}
