package com.xiaomi.verificationsdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.verificationsdk.internal.Config;
import com.xiaomi.verificationsdk.internal.Constants;
import com.xiaomi.verificationsdk.internal.EnvEncryptUtils;
import com.xiaomi.verificationsdk.internal.ErrorInfo;
import com.xiaomi.verificationsdk.internal.NetWorkUtils;
import com.xiaomi.verificationsdk.internal.RegisterInfo;
import com.xiaomi.verificationsdk.internal.ScoreManager;
import com.xiaomi.verificationsdk.internal.SensorHelper;
import com.xiaomi.verificationsdk.internal.SharedPreferencesUtil;
import com.xiaomi.verificationsdk.internal.TalkBack;
import com.xiaomi.verificationsdk.internal.VerificationException;
import com.xiaomi.verificationsdk.internal.VerifyError;
import com.xiaomi.verificationsdk.internal.VerifyRequest;
import com.xiaomi.verificationsdk.internal.VerifyResult;
import java.lang.ref.WeakReference;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class VerificationManager {
    private static final String SHARED_PREFERENCE_NAME = "VerificationConfig";
    private static final String TAG = "VerificationManager";
    private static final ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private boolean isSdkHandleException;
    private String mAction;
    private WeakReference<Activity> mActivityWeakReference;
    private AlertDialog mAlertDialog;
    private AsyncSessionRegister mAsyncSessionRegister;
    private SimpleFutureTask<Config> mConfigTask;
    private String mDomain;
    private boolean mErrorAction;
    private int mFrequency;
    private Handler mHandler;
    private boolean mIsForce;
    private boolean mIsManualClose;
    private boolean mIsVerifyExpired;
    private boolean mIsVoiceover;
    private String mKey;
    private String mLocale;
    private int mMaxDuration;
    private LinearLayout mProgressBar;
    private SensorHelper mSensorHelper;
    private SharedPreferencesUtil mSharedPreferencesUtil;
    private StartShowManMachineVerificationDialogCallback mShowManMachineVerificationDialogCallback;
    private String mUserId;
    private VerifyResultCallback mVerifyResultCallback;
    private String mVerifyUrl;
    private View mView;
    private View mViewCustom;
    private WebView mWebView;
    private VerifyDialogLayoutParams mVerifyDialogLandscapeLayoutParams = new VerifyDialogLayoutParams.Builder().build();
    private VerifyDialogLayoutParams mVerifyDialogPortraitLayoutParams = new VerifyDialogLayoutParams.Builder().build();
    private boolean mIsForceCancelDialog = true;
    private boolean mRecycled = false;
    private final AtomicBoolean mVerifyCompletedState = new AtomicBoolean(false);
    private DialogInterface.OnKeyListener mOnKeyListener = new DialogInterface.OnKeyListener() { // from class: com.xiaomi.verificationsdk.VerificationManager.1
        @Override // android.content.DialogInterface.OnKeyListener
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (i != 4) {
                return false;
            }
            VerificationManager.this.hideDialog();
            if (VerificationManager.this.mVerifyResultCallback == null) {
                return true;
            }
            VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    VerificationManager.this.mVerifyResultCallback.onVerifyCancel();
                    VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                }
            });
            return true;
        }
    };
    private DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener() { // from class: com.xiaomi.verificationsdk.VerificationManager.2
        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
            if (!VerificationManager.this.mIsForceCancelDialog || VerificationManager.this.mVerifyResultCallback == null) {
                return;
            }
            VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.2.1
                @Override // java.lang.Runnable
                public void run() {
                    VerificationManager.this.mVerifyResultCallback.onVerifyCancel();
                    VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                }
            });
        }
    };

    public interface AsyncSessionRegister {
        void register(ValueCallback<RegisterInfo> valueCallback);
    }

    public interface SessionRegister {
        RegisterInfo register();
    }

    public interface StartShowManMachineVerificationDialogCallback {
        void startShowManMachineVerificationDialog();
    }

    public interface Verify2Callback {
        void onShowWebView(String str);

        void onVerifyFail(VerifyError verifyError);

        void onVerifySucess(VerifyResult verifyResult);
    }

    public interface VerifyResultCallback {
        void onVerifyCancel();

        void onVerifyFail(VerifyError verifyError);

        void onVerifySucess(VerifyResult verifyResult);
    }

    private boolean isNeedRegister() {
        return true;
    }

    public VerificationManager(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("activity  should not be null");
        }
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mActivityWeakReference = new WeakReference<>(activity);
        this.mSensorHelper = new SensorHelper(activity.getApplicationContext());
        this.mSharedPreferencesUtil = new SharedPreferencesUtil(activity, SHARED_PREFERENCE_NAME);
    }

    public void init() {
        startCollectSensorData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAndCollectSensorData() {
        this.mSensorHelper.clearCollectedData();
        startCollectSensorData();
    }

    private void startCollectSensorData() {
        this.mMaxDuration = this.mSharedPreferencesUtil.getInt(Constants.MAXDURATION, 5000);
        int i = this.mSharedPreferencesUtil.getInt(Constants.FREQUENCY, 50);
        this.mFrequency = i;
        this.mSensorHelper.collectSensorData(i, this.mMaxDuration);
        if (Math.abs(System.currentTimeMillis() - this.mSharedPreferencesUtil.getLong(Constants.LAST_DOWNLOAD_APP_CONFIG_TIME, 0L)) > 86400000) {
            AccountLogger.log(TAG, "get config from server");
            getConfig(Constants.getUrl(this.mDomain, Constants.CONFIG_URL));
        }
    }

    public void startVerify() {
        if (checkVerifyCompletedState(this.mVerifyCompletedState)) {
            if (TextUtils.isEmpty(this.mKey)) {
                throw new IllegalArgumentException("key is null");
            }
            if (TextUtils.isEmpty(this.mAction)) {
                throw new IllegalArgumentException("action is null");
            }
            this.mIsForceCancelDialog = true;
            if (this.mVerifyResultCallback == null) {
                throw new IllegalArgumentException("startVerify: mVerifyResultCallback should not be null");
            }
            if (isNeedRegister()) {
                this.mIsManualClose = false;
                captcha();
            } else {
                showWebView();
            }
        }
    }

    static boolean checkVerifyCompletedState(AtomicBoolean atomicBoolean) {
        return atomicBoolean.compareAndSet(false, true);
    }

    static void resetVerifyCompletedState(AtomicBoolean atomicBoolean) {
        atomicBoolean.getAndSet(false);
    }

    public void onRecycle() {
        dismissDialog();
        this.mRecycled = true;
    }

    public boolean isRecycled() {
        return this.mRecycled;
    }

    public void dismissDialog() {
        AlertDialog alertDialog;
        if (getAvailableActivity() == null || (alertDialog = this.mAlertDialog) == null) {
            return;
        }
        alertDialog.dismiss();
        this.mAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideDialog() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.hide();
        }
    }

    private void showDialog(String str) {
        if (getAvailableActivity() == null) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("showDialog:url should not be null");
        }
        this.mHandler.post(new AnonymousClass3(str));
    }

    /* JADX INFO: renamed from: com.xiaomi.verificationsdk.VerificationManager$3, reason: invalid class name */
    class AnonymousClass3 implements Runnable {
        final /* synthetic */ String val$url;

        AnonymousClass3(String str) {
            this.val$url = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            Activity availableActivity = VerificationManager.this.getAvailableActivity();
            if (availableActivity == null || VerificationManager.this.mRecycled) {
                return;
            }
            HashMap map = new HashMap();
            map.put("_modifyStyle", "true");
            String strAppendUrl = VerificationManager.appendUrl(this.val$url, map);
            boolean z = availableActivity.getResources().getConfiguration().orientation == 2;
            VerificationManager verificationManager = VerificationManager.this;
            VerifyDialogLayoutParams verifyDialogLayoutParams = z ? verificationManager.mVerifyDialogLandscapeLayoutParams : verificationManager.mVerifyDialogPortraitLayoutParams;
            if (VerificationManager.this.mView == null) {
                VerificationManager.this.mView = availableActivity.getLayoutInflater().inflate(R.layout.passport_verification_dialog, (ViewGroup) null);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                layoutParams.gravity = 17;
                VerificationManager.this.mView.setLayoutParams(layoutParams);
            }
            if (VerificationManager.this.mViewCustom == null) {
                VerificationManager verificationManager2 = VerificationManager.this;
                verificationManager2.mViewCustom = verificationManager2.mView.findViewById(R.id.view_custom);
            }
            VerificationManager.this.mViewCustom.setVisibility(verifyDialogLayoutParams.isCustomDialogSize() ? 0 : 8);
            if (VerificationManager.this.mWebView == null) {
                VerificationManager verificationManager3 = VerificationManager.this;
                verificationManager3.mWebView = (WebView) verificationManager3.mView.findViewById(R.id.verify_webView);
            }
            if (VerificationManager.this.mProgressBar == null) {
                VerificationManager verificationManager4 = VerificationManager.this;
                verificationManager4.mProgressBar = (LinearLayout) verificationManager4.mView.findViewById(R.id.verify_ProgressBar);
            }
            if (VerificationManager.this.mAlertDialog != null) {
                VerificationManager.this.mAlertDialog.dismiss();
                VerificationManager.this.mAlertDialog = null;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(availableActivity, android.R.style.Theme.Material.Light.Dialog.Alert);
            if ((2 & availableActivity.getApplicationInfo().flags) != 0) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
            WebSettings settings = VerificationManager.this.mWebView.getSettings();
            settings.setSupportMultipleWindows(true);
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setUserAgentString(VerificationManager.this.getUserAgent(availableActivity));
            VerificationManager.this.mWebView.setWebChromeClient(new WebChromeClient() { // from class: com.xiaomi.verificationsdk.VerificationManager.3.1
                @Override // android.webkit.WebChromeClient
                public boolean onCreateWindow(WebView webView, boolean z2, boolean z3, Message message) {
                    VerificationManager.this.jumpToBrowser(webView.getHitTestResult().getExtra());
                    return false;
                }
            });
            VerificationManager.this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.xiaomi.verificationsdk.VerificationManager.3.2
                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView webView, String str) {
                    VerificationManager.this.mProgressBar.setVisibility(8);
                    if (VerificationManager.this.mWebView.getVisibility() == 4) {
                        VerificationManager.this.mWebView.setVisibility(0);
                    }
                    super.onPageFinished(webView, str);
                }

                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    Bundle bundle;
                    if (str.contains(Constants.REDIRECT_URL) && (bundle = NetWorkUtils.parse(str)) != null) {
                        int i = Integer.parseInt(bundle.getString("code"));
                        String string = bundle.getString("errorCode");
                        String string2 = bundle.getString(Constants.VERIFY_ERROR_STATUS);
                        String string3 = bundle.getString("flag");
                        AccountLogger.log(VerificationManager.TAG, "code=" + i + ";errorCode=" + string + ";errorStatus=" + string2);
                        if (i == 0) {
                            VerificationManager.this.clearAndCollectSensorData();
                            VerificationManager.this.mIsForceCancelDialog = false;
                            VerificationManager.this.dismissDialog();
                            VerificationManager.this.mVerifyUrl = "";
                            VerificationManager.this.mIsVerifyExpired = false;
                            final VerifyResult verifyResultBuild = new VerifyResult.Builder().token(string3).scoreData(ScoreManager.getScore()).build();
                            VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.3.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    VerificationManager.this.mVerifyResultCallback.onVerifySucess(verifyResultBuild);
                                    VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                                }
                            });
                            return true;
                        }
                        if (i == 1) {
                            VerificationManager.this.mIsForceCancelDialog = false;
                            VerificationManager.this.mIsManualClose = true;
                            VerificationManager.this.dismissDialog();
                            VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.3.2.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    VerificationManager.this.mVerifyResultCallback.onVerifyCancel();
                                    VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                                }
                            });
                        } else if (i == 2) {
                            VerificationManager.this.mIsForceCancelDialog = false;
                            VerificationManager.this.dismissDialog();
                            VerificationManager.this.mIsVerifyExpired = true;
                            final VerifyError errorMessage = VerificationManager.getErrorMessage(ErrorInfo.ErrorCode.ERROR_EVENTID_EXPIRED.getCode(), "eventid expired");
                            VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.3.2.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    VerificationManager.this.mVerifyResultCallback.onVerifyFail(errorMessage);
                                    VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                                }
                            });
                        } else if (i == 3) {
                            VerificationManager.this.mIsForceCancelDialog = false;
                            VerificationManager.this.dismissDialog();
                            VerificationManager.this.mVerifyUrl = "";
                            VerificationManager.this.mIsVerifyExpired = false;
                            final VerifyResult verifyResultBuild2 = new VerifyResult.Builder().token(EnvEncryptUtils.getUUID()).build();
                            VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.3.2.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    VerificationManager.this.mVerifyResultCallback.onVerifySucess(verifyResultBuild2);
                                    VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                                }
                            });
                        } else if (i == 95008 || i == 95009) {
                            VerificationManager.this.mIsForceCancelDialog = false;
                            VerificationManager.this.dismissDialog();
                            VerificationManager.this.mIsVerifyExpired = false;
                            final VerifyError errorMessage2 = VerificationManager.getErrorMessage(ErrorInfo.ErrorCode.ERROR_VERIFY_SERVER.getCode(), "code=" + i + ";errorCode=" + string + ";errorStatus=" + string2);
                            VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.3.2.5
                                @Override // java.lang.Runnable
                                public void run() {
                                    VerificationManager.this.mVerifyResultCallback.onVerifyFail(errorMessage2);
                                    VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                                }
                            });
                        }
                    }
                    return false;
                }

                @Override // android.webkit.WebViewClient
                public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                    VerificationManager.this.mProgressBar.setVisibility(0);
                    if (VerificationManager.this.mWebView.getVisibility() == 0) {
                        VerificationManager.this.mWebView.setVisibility(4);
                    }
                    super.onPageStarted(webView, str, bitmap);
                }
            });
            ViewGroup viewGroup = (ViewGroup) VerificationManager.this.mView.getParent();
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            VerificationManager.this.mAlertDialog = builder.create();
            VerificationManager.this.mAlertDialog.setView(VerificationManager.this.mView);
            VerificationManager.this.mAlertDialog.setOnKeyListener(VerificationManager.this.mOnKeyListener);
            VerificationManager.this.mAlertDialog.setOnDismissListener(VerificationManager.this.mOnDismissListener);
            VerificationManager.this.mAlertDialog.show();
            VerificationManager.this.mWebView.setBackgroundColor(0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) VerificationManager.this.mWebView.getLayoutParams();
            if (verifyDialogLayoutParams.webViewMarginRect != null) {
                marginLayoutParams.setMargins(verifyDialogLayoutParams.webViewMarginRect.left, verifyDialogLayoutParams.webViewMarginRect.top, verifyDialogLayoutParams.webViewMarginRect.right, verifyDialogLayoutParams.webViewMarginRect.bottom);
            } else {
                marginLayoutParams.setMargins(0, 0, 0, 0);
            }
            if (verifyDialogLayoutParams.isCustomWebViewSize()) {
                marginLayoutParams.width = verifyDialogLayoutParams.webViewWidth;
                marginLayoutParams.height = verifyDialogLayoutParams.webViewHeight;
            }
            VerificationManager.this.mWebView.setLayoutParams(marginLayoutParams);
            VerificationManager.this.mWebView.loadUrl(strAppendUrl);
            VerifyDialogLayoutParams verifyDialogLayoutParams2 = verifyDialogLayoutParams;
            VerificationManager verificationManager5 = VerificationManager.this;
            verificationManager5.applyVerifyDialogLayoutParams(availableActivity, verificationManager5.mView.findViewById(R.id.fl_content), VerificationManager.this.mAlertDialog.getWindow(), verifyDialogLayoutParams2, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyVerifyDialogLayoutParams(Activity activity, View view, Window window, VerifyDialogLayoutParams verifyDialogLayoutParams, boolean z) {
        window.clearFlags(131072);
        if (verifyDialogLayoutParams.bgResId > 0) {
            view.setBackgroundResource(verifyDialogLayoutParams.bgResId);
        } else if (verifyDialogLayoutParams.bgDrawable != null) {
            view.setBackgroundDrawable(verifyDialogLayoutParams.bgDrawable);
        } else {
            view.setBackgroundColor(activity.getResources().getColor(R.color.passport_verification_dialog_def_bg_color));
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        View decorView = window.getDecorView();
        int i = 0;
        decorView.setBackgroundColor(0);
        if (verifyDialogLayoutParams.dialogMarginRect != null) {
            decorView.setPadding(verifyDialogLayoutParams.dialogMarginRect.left, verifyDialogLayoutParams.dialogMarginRect.top, verifyDialogLayoutParams.dialogMarginRect.right, verifyDialogLayoutParams.dialogMarginRect.bottom);
            if ((verifyDialogLayoutParams.gravity & 80) != 0) {
                i = verifyDialogLayoutParams.dialogMarginRect.bottom;
            } else if ((verifyDialogLayoutParams.gravity & 48) != 0) {
                i = verifyDialogLayoutParams.dialogMarginRect.top;
            }
        } else if (verifyDialogLayoutParams.isCustomDialogSize()) {
            decorView.setPadding(0, 0, 0, 0);
        }
        if (verifyDialogLayoutParams.isCustomDialogSize()) {
            attributes.width = verifyDialogLayoutParams.dialogWidth;
            attributes.height = verifyDialogLayoutParams.dialogHeight + i;
        } else {
            Point point = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(point);
            attributes.width = z ? point.y : point.x;
        }
        attributes.gravity = verifyDialogLayoutParams.gravity;
        window.setAttributes(attributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialog(final int i, final int i2) {
        if (getAvailableActivity() == null) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.4
            @Override // java.lang.Runnable
            public void run() {
                Activity availableActivity = VerificationManager.this.getAvailableActivity();
                if (availableActivity == null) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(availableActivity, android.R.style.Theme.Material.Light.Dialog.Alert);
                TextView textView = new TextView(availableActivity);
                textView.setText(availableActivity.getResources().getString(i2) + "(" + i + ")");
                textView.setPadding(0, 40, 0, 0);
                textView.setGravity(17);
                builder.setView(textView);
                VerificationManager.this.mAlertDialog = builder.create();
                VerificationManager.this.mAlertDialog.show();
                VerificationManager verificationManager = VerificationManager.this;
                verificationManager.applyErrDialogWindowSettings(verificationManager.mAlertDialog.getWindow(), availableActivity.getWindowManager());
            }
        });
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.5
            @Override // java.lang.Runnable
            public void run() {
                VerificationManager.this.mIsForceCancelDialog = false;
                VerificationManager.this.dismissDialog();
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyErrDialogWindowSettings(Window window, WindowManager windowManager) {
        window.clearFlags(131072);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = defaultDisplay.getWidth();
        attributes.gravity = 81;
        window.setAttributes(attributes);
    }

    /* JADX INFO: renamed from: com.xiaomi.verificationsdk.VerificationManager$6, reason: invalid class name */
    class AnonymousClass6 implements Runnable {
        AnonymousClass6() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TextUtils.isEmpty(VerificationManager.this.mSensorHelper.getCollectedData())) {
                VerificationManager.this.mSensorHelper.asyncSetCollectedData();
            }
            try {
                JSONObject jSONObject = new JSONObject(VerificationManager.this.mSensorHelper.getCollectedData());
                JSONObject jSONObject2 = jSONObject.getJSONObject("env");
                jSONObject2.put(Constants.VOICEOVER, VerificationManager.this.mIsVoiceover ? 1 : 0);
                jSONObject.put("env", jSONObject2);
                jSONObject.put(Constants.FORCE, VerificationManager.this.mIsForce);
                Activity activity = (Activity) VerificationManager.this.mActivityWeakReference.get();
                if (activity != null) {
                    jSONObject.put(Constants.TALK_BACK, TalkBack.isTallBackActive(activity));
                }
                jSONObject.put("uid", VerificationManager.this.mUserId);
                jSONObject.put("version", "2.0");
                jSONObject.put("scene", VerificationManager.this.mAction);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(Constants.RANDOM_LONG, new SecureRandom().nextLong());
                jSONObject3.put(Constants.TIMESTAMP, System.currentTimeMillis() / 1000);
                jSONObject.put(Constants.NONCE, jSONObject3);
                VerificationManager.this.mSensorHelper.setCollectedData(jSONObject.toString());
                VerificationManager.this.mSensorHelper.uploadData(VerificationManager.this.mSensorHelper.getCollectedData(), VerificationManager.this.mKey, VerificationManager.this.mAction, Boolean.valueOf(VerificationManager.this.mIsVerifyExpired), VerificationManager.this.mLocale, VerificationManager.this.mDomain, Boolean.valueOf(VerificationManager.this.mErrorAction), new Verify2Callback() { // from class: com.xiaomi.verificationsdk.VerificationManager.6.1
                    @Override // com.xiaomi.verificationsdk.VerificationManager.Verify2Callback
                    public void onVerifySucess(final VerifyResult verifyResult) {
                        VerificationManager.this.clearAndCollectSensorData();
                        VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.6.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                VerificationManager.this.mVerifyResultCallback.onVerifySucess(verifyResult);
                                VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                            }
                        });
                    }

                    @Override // com.xiaomi.verificationsdk.VerificationManager.Verify2Callback
                    public void onVerifyFail(final VerifyError verifyError) {
                        VerificationManager.this.showDialog(verifyError.getCode(), verifyError.getDialogMsgId());
                        VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.6.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                VerificationManager.this.mVerifyResultCallback.onVerifyFail(verifyError);
                            }
                        });
                    }

                    @Override // com.xiaomi.verificationsdk.VerificationManager.Verify2Callback
                    public void onShowWebView(final String str) {
                        if (VerificationManager.this.mShowManMachineVerificationDialogCallback != null) {
                            VerificationManager.this.mShowManMachineVerificationDialogCallback.startShowManMachineVerificationDialog();
                        }
                        VerificationManager.this.mVerifyUrl = str;
                        VerificationManager.this.mIsVerifyExpired = false;
                        VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.6.1.3
                            @Override // java.lang.Runnable
                            public void run() {
                                VerificationManager.this.showWebView(str);
                            }
                        });
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                VerificationManager.this.showDialog(ErrorInfo.ErrorCode.ERROR_JSON_EXCEPTION.getCode(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_JSON_EXCEPTION));
                final VerifyError errorMessage = VerificationManager.getErrorMessage(ErrorInfo.ErrorCode.ERROR_JSON_EXCEPTION.getCode(), "registere:" + e.toString());
                VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.6.2
                    @Override // java.lang.Runnable
                    public void run() {
                        VerificationManager.this.mVerifyResultCallback.onVerifyFail(errorMessage);
                        VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                    }
                });
            }
        }
    }

    private void captcha() {
        mExecutorService.execute(new AnonymousClass6());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWebView(String str) {
        Activity availableActivity = getAvailableActivity();
        if (availableActivity == null) {
            return;
        }
        if (NetWorkUtils.isNetworkAvalible(availableActivity)) {
            showDialog(str);
            return;
        }
        showDialog(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION.getCode(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION));
        final VerifyError errorMessage = getErrorMessage(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION.getCode(), "network disconnected");
        this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.7
            @Override // java.lang.Runnable
            public void run() {
                VerificationManager.this.mVerifyResultCallback.onVerifyFail(errorMessage);
                VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
            }
        });
    }

    private void showWebView() {
        Activity availableActivity = getAvailableActivity();
        if (availableActivity == null) {
            return;
        }
        if (NetWorkUtils.isNetworkAvalible(availableActivity)) {
            if (this.mAlertDialog != null) {
                this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.8
                    @Override // java.lang.Runnable
                    public void run() {
                        VerificationManager.this.mAlertDialog.show();
                    }
                });
            }
        } else {
            showDialog(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION.getCode(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION));
            final VerifyError errorMessage = getErrorMessage(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION.getCode(), "network disconnected");
            this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.9
                @Override // java.lang.Runnable
                public void run() {
                    VerificationManager.this.mVerifyResultCallback.onVerifyFail(errorMessage);
                    VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                }
            });
        }
    }

    private SimpleFutureTask<Config> getConfig(final String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("getConfig: url is null");
        }
        SimpleFutureTask<Config> simpleFutureTask = new SimpleFutureTask<>(new Callable<Config>() { // from class: com.xiaomi.verificationsdk.VerificationManager.11
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Config call() throws Exception {
                return VerifyRequest.getConfigFromServer(str);
            }
        }, new SimpleFutureTask.Callback<Config>() { // from class: com.xiaomi.verificationsdk.VerificationManager.10
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<Config> simpleFutureTask2) {
                try {
                    Config config = simpleFutureTask2.get();
                    if (config != null) {
                        VerificationManager.this.mFrequency = config.getFrequency();
                        VerificationManager.this.mMaxDuration = config.getMaxDuration();
                        VerificationManager.this.mSharedPreferencesUtil.saveLong(Constants.LAST_DOWNLOAD_APP_CONFIG_TIME, System.currentTimeMillis());
                        VerificationManager.this.mSharedPreferencesUtil.saveInt(Constants.FREQUENCY, VerificationManager.this.mFrequency);
                        VerificationManager.this.mSharedPreferencesUtil.saveInt(Constants.MAXDURATION, VerificationManager.this.mMaxDuration);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e2) {
                    e2.printStackTrace();
                }
            }
        });
        this.mConfigTask = simpleFutureTask;
        mExecutorService.submit(simpleFutureTask);
        return this.mConfigTask;
    }

    public static VerifyError getErrorMessage(int i, String str) {
        return new VerifyError.Build().code(i).msg(str).build();
    }

    public VerificationManager setRegisterUrl(final String str) {
        return setSessionRegister(new SessionRegister() { // from class: com.xiaomi.verificationsdk.VerificationManager.12
            @Override // com.xiaomi.verificationsdk.VerificationManager.SessionRegister
            public RegisterInfo register() {
                try {
                    return VerifyRequest.getRegisterInfo(str);
                } catch (VerificationException e) {
                    VerificationManager.this.isSdkHandleException = true;
                    VerificationManager.this.showDialog(e.getCode(), e.getDialogTipMsg());
                    final VerifyError errorMessage = VerificationManager.getErrorMessage(e.getCode(), e.getMessage());
                    VerificationManager.this.mHandler.post(new Runnable() { // from class: com.xiaomi.verificationsdk.VerificationManager.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            VerificationManager.this.mVerifyResultCallback.onVerifyFail(errorMessage);
                            VerificationManager.resetVerifyCompletedState(VerificationManager.this.mVerifyCompletedState);
                        }
                    });
                    return null;
                }
            }
        });
    }

    public VerificationManager setIsVoiceover(boolean z) {
        this.mIsVoiceover = z;
        return this;
    }

    public VerificationManager setIsForceVerify(boolean z) {
        this.mIsForce = z;
        return this;
    }

    public VerificationManager setKey(String str) {
        this.mKey = str;
        return this;
    }

    public VerificationManager setAction(String str) {
        this.mAction = str;
        return this;
    }

    public VerificationManager setUserId(String str) {
        this.mUserId = str;
        return this;
    }

    public VerificationManager setLocale(String str) {
        this.mLocale = str;
        return this;
    }

    public VerificationManager setDomain(String str) {
        this.mDomain = str;
        return this;
    }

    public VerificationManager setErrorAction(Boolean bool) {
        this.mErrorAction = bool.booleanValue();
        return this;
    }

    public void clearVerifyUrl(boolean z) {
        if (z) {
            this.mVerifyUrl = "";
        }
    }

    public VerificationManager setSessionRegister(final SessionRegister sessionRegister) {
        if (sessionRegister == null) {
            throw new IllegalArgumentException("setSessionRegister: sessionRegister should not be null");
        }
        this.mAsyncSessionRegister = new AsyncSessionRegister() { // from class: com.xiaomi.verificationsdk.VerificationManager.13
            @Override // com.xiaomi.verificationsdk.VerificationManager.AsyncSessionRegister
            public void register(ValueCallback<RegisterInfo> valueCallback) {
                VerificationManager.this.isSdkHandleException = false;
                valueCallback.onReceiveValue(sessionRegister.register());
            }
        };
        return this;
    }

    public VerificationManager setAsyncSessionRegister(AsyncSessionRegister asyncSessionRegister) {
        this.mAsyncSessionRegister = asyncSessionRegister;
        this.isSdkHandleException = false;
        if (asyncSessionRegister != null) {
            return this;
        }
        throw new IllegalArgumentException("setAsyncSessionRegister: asyncSessionRegister should not be null");
    }

    public VerificationManager setVerifyResultCallback(VerifyResultCallback verifyResultCallback) {
        this.mVerifyResultCallback = verifyResultCallback;
        return this;
    }

    public VerificationManager setShowManMachineVerificationDialogCallback(StartShowManMachineVerificationDialogCallback startShowManMachineVerificationDialogCallback) {
        this.mShowManMachineVerificationDialogCallback = startShowManMachineVerificationDialogCallback;
        return this;
    }

    public VerificationManager setVerifyDialogLandscapeLayoutParams(VerifyDialogLayoutParams verifyDialogLayoutParams) {
        this.mVerifyDialogLandscapeLayoutParams = verifyDialogLayoutParams;
        return this;
    }

    public VerificationManager setVerifyDialogPortraitLayoutParams(VerifyDialogLayoutParams verifyDialogLayoutParams) {
        this.mVerifyDialogPortraitLayoutParams = verifyDialogLayoutParams;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getUserAgent(Context context) {
        return "" + WebSettings.getDefaultUserAgent(context) + " androidVerifySDK/5.3.0.release.94 androidVerifySDK/VersionCode/53094 AppPackageName/" + context.getPackageName();
    }

    public void setUsePreview(boolean z) {
        Constants.mIsUsePreview = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jumpToBrowser(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        Activity availableActivity = getAvailableActivity();
        if (availableActivity != null) {
            availableActivity.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String appendUrl(String str, Map<String, String> map) {
        if (str == null) {
            throw new NullPointerException("origin is not allowed null");
        }
        Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builderBuildUpon.appendQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return builderBuildUpon.build().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Activity getAvailableActivity() {
        WeakReference<Activity> weakReference = this.mActivityWeakReference;
        if (weakReference == null) {
            return null;
        }
        Activity activity = weakReference.get();
        if (activity != null && !activity.isFinishing()) {
            return activity;
        }
        AccountLogger.log(TAG, "Activity is destroy");
        return null;
    }

    public static class VerifyDialogLayoutParams {
        public final Drawable bgDrawable;
        public final int bgResId;
        public final int dialogHeight;
        public final Rect dialogMarginRect;
        public final int dialogWidth;
        public final int gravity;
        public final int webViewHeight;
        public final Rect webViewMarginRect;
        public final int webViewWidth;

        private VerifyDialogLayoutParams(int i, Drawable drawable, int i2, int i3, int i4, int i5, int i6, Rect rect, Rect rect2) {
            this.bgResId = i;
            this.bgDrawable = drawable;
            this.gravity = i2;
            this.dialogWidth = i3;
            this.dialogHeight = i4;
            this.webViewWidth = i5;
            this.webViewHeight = i6;
            this.dialogMarginRect = rect;
            this.webViewMarginRect = rect2;
        }

        public boolean isCustomDialogSize() {
            return this.dialogWidth > 0 || this.dialogHeight > 0;
        }

        public boolean isCustomWebViewSize() {
            return this.webViewWidth > 0 || this.webViewHeight > 0;
        }

        public static class Builder {
            private Drawable bgDrawable;
            private int dialogHeight;
            private Rect dialogMarginRect;
            private int dialogWidth;
            public int webViewHeight;
            private Rect webViewMarginRect;
            public int webViewWidth;
            private int bgResId = R.drawable.passport_verification_def_dialog_bg;
            private int gravity = 81;

            public Builder setBgResId(int i) {
                this.bgResId = i;
                return this;
            }

            public Builder setBgDrawable(Drawable drawable) {
                this.bgDrawable = drawable;
                return this;
            }

            public Builder setGravity(int i) {
                this.gravity = i;
                return this;
            }

            public Builder setDialogWidth(int i) {
                this.dialogWidth = i;
                return this;
            }

            public Builder setDialogHeight(int i) {
                this.dialogHeight = i;
                return this;
            }

            public Builder setWebViewWidth(int i) {
                this.webViewWidth = i;
                return this;
            }

            public Builder setWebViewHeight(int i) {
                this.webViewHeight = i;
                return this;
            }

            public Builder setDialogMarginRect(Rect rect) {
                this.dialogMarginRect = rect;
                return this;
            }

            public Builder setWebViewMarginRect(Rect rect) {
                this.webViewMarginRect = rect;
                return this;
            }

            public VerifyDialogLayoutParams build() {
                return new VerifyDialogLayoutParams(this.bgResId, this.bgDrawable, this.gravity, this.dialogWidth, this.dialogHeight, this.webViewWidth, this.webViewHeight, this.dialogMarginRect, this.webViewMarginRect);
            }
        }
    }
}
