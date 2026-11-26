package com.miui.notes.component;

import android.content.Context;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.content.res.AppCompatResources;
import com.miui.common.record.AudioInputListener;
import com.miui.common.record.SpeechRecognitionListener;
import com.miui.common.record.SpeechRecognitionTask;
import com.miui.common.record.SpeechRecognitionTaskImp;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.NetworkCallbackImpl;
import com.miui.common.tool.Utils;
import com.miui.common.view.RecordingWaveView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.ai.mibrain.MibrainException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;
import miui.os.Build;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes4.dex */
public class CreateAsrDataDialog extends AlertDialog {
    private static final String TAG = "CreateAsrDataDialog";
    private String mAudioFileName;
    private final AudioInputListener mAudioInputListener;
    private CountDownTimer mAudioRecordTimer;
    private StringBuilder mAudioResultSb;
    private EditText mAudioResultView;
    private RecordingWaveView mAudioWaveView;
    ConnectivityManager mConnMgr;
    private final Context mContext;
    private TextView mCountDownWarnView;
    private View mEditGroup;
    protected FrameLayout mEditorMaskView;
    private int mEnd;
    private boolean mIsWarned;
    private final NetworkCallbackImpl.NetworkCall mNetworkCall;
    NetworkCallbackImpl mNetworkCallback;
    private View mRecordBtn;
    private SpeechRecognitionTask mRecordTask;
    private int mRecordType;
    NetworkRequest mRequest;
    private View mRootView;
    private int mStart;
    private final View.OnClickListener mViewClickListener;
    private SpeechRecognitionImpl speechRecognitionListener;
    private static final long MAX_RECORD_TIME = TimeUnit.SECONDS.toMillis(300);
    private static final long LAST_WARRING_TIME = TimeUnit.SECONDS.toMillis(10);

    public void onResume() {
    }

    public CreateAsrDataDialog(Context context, int i, View.OnClickListener onClickListener, AudioInputListener audioInputListener) {
        super(context, R.style.MiuixTheme_DayNight_Dialog);
        this.mAudioResultSb = new StringBuilder();
        this.mAudioFileName = null;
        this.mAudioRecordTimer = null;
        NetworkCallbackImpl.NetworkCall networkCall = new NetworkCallbackImpl.NetworkCall() { // from class: com.miui.notes.component.CreateAsrDataDialog.1
            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onAvailable() {
                if (CreateAsrDataDialog.this.mRecordTask != null) {
                    CreateAsrDataDialog.this.mRecordTask.setNetWorkValid(true);
                    CreateAsrDataDialog.this.mRecordTask.startEngine();
                }
            }

            @Override // com.miui.common.tool.NetworkCallbackImpl.NetworkCall
            public void onLost() {
                if (CreateAsrDataDialog.this.mRecordTask != null) {
                    CreateAsrDataDialog.this.mRecordTask.setNetWorkValid(false);
                    CreateAsrDataDialog.this.mRecordTask.stopEngine();
                    Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getResources().getString(R.string.audio_toast_network_is_not_available), 1).show();
                }
            }
        };
        this.mNetworkCall = networkCall;
        this.mNetworkCallback = new NetworkCallbackImpl(networkCall);
        this.mRequest = new NetworkRequest.Builder().build();
        this.mConnMgr = (ConnectivityManager) NoteApp.getInstance().getSystemService("connectivity");
        this.mRecordType = i;
        this.mContext = context;
        this.mViewClickListener = onClickListener;
        this.mAudioInputListener = audioInputListener;
        Window window = getWindow();
        window.addFlags(128);
        window.setSoftInputMode(2);
        setView(LayoutInflater.from(getContext()).inflate(R.layout.create_asr_data_panel, (ViewGroup) null));
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initEditView();
        if (Build.IS_GLOBAL_BUILD || !PreferenceUtils.getAllowXiaoai(this.mContext)) {
            this.mAudioResultView.setVisibility(8);
        } else {
            this.mAudioResultView.setVisibility(0);
        }
    }

    public void onPause() {
        TextView textView = this.mCountDownWarnView;
        if (textView != null) {
            this.mEditorMaskView.removeView(textView);
        }
        stopRecordTask();
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog
    public void show() {
        super.show();
        try {
            View view = (View) this.mRootView.getParent().getParent().getParent();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.gravity = 81;
            view.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAudioResultView.clearFocus();
        startRecordTask();
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        try {
            getWindow().clearFlags(128);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error in dismiss " + e);
        }
        super.dismiss();
    }

    @Override // miuix.appcompat.app.AlertDialog
    public void dismissWithoutAnimation() {
        try {
            getWindow().clearFlags(128);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "error in dismissWithoutAnimation " + e);
        }
        super.dismissWithoutAnimation();
    }

    private void initEditView() {
        View viewFindViewById = findViewById(R.id.edit_group);
        this.mRootView = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this.mViewClickListener);
        }
        this.mEditGroup = findViewById(R.id.audio_create_panel);
        this.mEditorMaskView = (FrameLayout) findViewById(R.id.dialog_root_view);
        this.mAudioWaveView = (RecordingWaveView) findViewById(R.id.record_wave);
        this.mAudioResultView = (EditText) findViewById(R.id.audio_content);
        View viewFindViewById2 = findViewById(R.id.record);
        this.mRecordBtn = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.component.CreateAsrDataDialog$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initEditView$0(view);
                }
            });
        }
        this.mAudioResultView.clearFocus();
        this.mAudioResultView.setFocusable(false);
        this.mAudioResultView.setFocusableInTouchMode(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initEditView$0(View view) {
        dismiss();
    }

    private void startRecordTask() {
        CreateAsrDataDialog createAsrDataDialog;
        String str;
        if (this.mRecordTask == null) {
            if (this.mRecordType != 1) {
                str = AudioUtils.NOTE_MP3_DIR_NAME;
            } else {
                str = AudioUtils.TODO_MP3_DIR_NAME;
            }
            this.speechRecognitionListener = new SpeechRecognitionImpl();
            this.mRecordTask = new SpeechRecognitionTaskImp(NoteApp.getInstance(), str, this.speechRecognitionListener);
        }
        startAudioInput();
        this.mAudioWaveView.setEnabled(true);
        this.mAudioWaveView.startDraw();
        if (this.mAudioRecordTimer == null) {
            createAsrDataDialog = this;
            createAsrDataDialog.mAudioRecordTimer = new CountDownTimer(MAX_RECORD_TIME, 1000L) { // from class: com.miui.notes.component.CreateAsrDataDialog.2
                @Override // android.os.CountDownTimer
                public void onTick(long j) {
                    CreateAsrDataDialog.this.onAudioTimerTick(j);
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    CreateAsrDataDialog.this.onAudioTimerFinish();
                }
            };
        } else {
            createAsrDataDialog = this;
        }
        createAsrDataDialog.mAudioRecordTimer.start();
        createAsrDataDialog.mNetworkCallback.initNetWorkState();
        boolean isNetWorkAvailable = createAsrDataDialog.mNetworkCallback.getIsNetWorkAvailable();
        Logger.INSTANCE.d(TAG, "isNetworkAvailable " + isNetWorkAvailable);
        if (!createAsrDataDialog.mRecordTask.isSkipSpeechRecognition()) {
            createAsrDataDialog.registerNetWorkListener();
            if (!isNetWorkAvailable) {
                Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getResources().getString(R.string.audio_toast_network_is_not_available), 1).show();
            }
        }
        try {
            createAsrDataDialog.mRecordTask.setNetWorkValid(isNetWorkAvailable);
            createAsrDataDialog.mRecordTask.start();
            createAsrDataDialog.mIsWarned = false;
        } catch (MibrainException unused) {
            Logger.INSTANCE.e(TAG, "Fail to start record mRecordTask!");
        }
    }

    private void stopRecordTask() {
        SpeechRecognitionTask speechRecognitionTask = this.mRecordTask;
        if (speechRecognitionTask != null) {
            speechRecognitionTask.shutdownNow();
            if (!this.mRecordTask.isSkipSpeechRecognition()) {
                unRegisterNetWorkListener();
            }
            CountDownTimer countDownTimer = this.mAudioRecordTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                this.mAudioRecordTimer = null;
            }
            this.mIsWarned = false;
        }
        this.mAudioWaveView.stopDraw();
        this.mAudioWaveView.reset();
    }

    public void onUpdateAudioResult(String str) {
        if (this.mAudioInputListener != null) {
            this.mAudioInputListener.onAppendText(AudioUtils.createAudioLabel(this.mAudioFileName) + str, this.mAudioFileName);
        }
        View.OnClickListener onClickListener = this.mViewClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(this.mRecordBtn);
        }
    }

    public void startAudioInput() {
        this.mStart = 0;
        this.mEnd = 0;
        this.mAudioResultView.setSelection(0, 0);
    }

    private View getCountDownView() {
        this.mCountDownWarnView = new TextView(this.mContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        Rect rect = new Rect();
        this.mEditGroup.getLocalVisibleRect(rect);
        layoutParams.topMargin = (rect.bottom - rect.top) / 2;
        layoutParams.gravity = 1;
        this.mCountDownWarnView.setLayoutParams(layoutParams);
        this.mCountDownWarnView.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.countdown_view_backgound));
        this.mCountDownWarnView.setTextColor(getContext().getColor(R.color.countdown_view_text_color));
        this.mCountDownWarnView.setTextSize(0, this.mContext.getResources().getDimension(R.dimen.bubble_text_size));
        return this.mCountDownWarnView;
    }

    public void exitDialog() {
        stopRecordTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAudioTimerTick(long j) {
        if (!this.mIsWarned && j <= LAST_WARRING_TIME) {
            this.mIsWarned = true;
            this.mEditorMaskView.addView(getCountDownView());
        }
        if (this.mIsWarned) {
            int iFloor = ((int) Math.floor(j / 1000)) + 1;
            TextView textView = this.mCountDownWarnView;
            if (textView != null) {
                textView.setText(Utils.obtainLocalInteger(iFloor, R.plurals.record_last_ten_seconds_reminder));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAudioTimerFinish() {
        TextView textView = this.mCountDownWarnView;
        if (textView != null) {
            this.mEditorMaskView.removeView(textView);
        }
        exitDialog();
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
        private final WeakReference<CreateAsrDataDialog> iContainerRef;

        private SpeechRecognitionImpl(CreateAsrDataDialog createAsrDataDialog) {
            this.iContainerRef = new WeakReference<>(createAsrDataDialog);
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrAppendTextResult(String str, boolean z) {
            CreateAsrDataDialog createAsrDataDialog = this.iContainerRef.get();
            if (createAsrDataDialog != null) {
                if (!z) {
                    if (str.equals("。")) {
                        String string = createAsrDataDialog.mAudioResultView.getText().subSequence(0, createAsrDataDialog.mStart).toString();
                        if (string.length() > 0) {
                            if (string.endsWith("。")) {
                                return;
                            }
                            if (string.endsWith(",")) {
                                createAsrDataDialog.mStart--;
                            }
                        }
                    }
                    createAsrDataDialog.mAudioResultView.getText().replace(createAsrDataDialog.mStart, createAsrDataDialog.mEnd, str);
                    createAsrDataDialog.mStart += str.length();
                    createAsrDataDialog.mEnd = createAsrDataDialog.mStart;
                } else {
                    createAsrDataDialog.mAudioResultView.getText().replace(createAsrDataDialog.mStart, createAsrDataDialog.mEnd, str);
                    createAsrDataDialog.mEnd = createAsrDataDialog.mStart + str.length();
                }
                createAsrDataDialog.mAudioResultSb.delete(0, createAsrDataDialog.mAudioResultSb.length());
                createAsrDataDialog.mAudioResultSb.append(createAsrDataDialog.mAudioResultView.getText().toString());
            }
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrSaveAudioFileName(String str) {
            CreateAsrDataDialog createAsrDataDialog = this.iContainerRef.get();
            if (createAsrDataDialog != null) {
                createAsrDataDialog.mAudioFileName = str;
                createAsrDataDialog.onUpdateAudioResult(createAsrDataDialog.mAudioResultSb.toString());
            }
        }

        @Override // com.miui.common.record.SpeechRecognitionListener
        public void onAsrMaxAmplitudeUpdate(short s) {
            CreateAsrDataDialog createAsrDataDialog = this.iContainerRef.get();
            if (createAsrDataDialog != null) {
                createAsrDataDialog.mAudioWaveView.addVolumes(s);
            }
        }
    }
}
