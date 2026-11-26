package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.utils.BitmapUtils;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.TalkBack;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: classes4.dex */
public class CaptchaView extends FrameLayout {
    private static final String TAG = "CaptchaView";
    private SimpleFutureTask<Pair<Bitmap, String>> mFetchImageCaptchaTask;
    private SimpleFutureTask<Boolean> mFetchVoiceCaptchaTask;
    private String mIck;
    private String mImageCaptchaUrl;
    private boolean mIsTalkBackAlive;
    private ImageView mIvCaptcha;
    private String mVoiceCaptchaUrl;

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        cancelCaptchaDownloadTask();
        super.onDetachedFromWindow();
    }

    public CaptchaView(Context context) {
        super(context);
        this.mImageCaptchaUrl = "https://account.xiaomi.com/pass/getCode?icodeType=login&0.07169544044424958";
        init(context);
    }

    public CaptchaView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        init(context);
    }

    public CaptchaView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mImageCaptchaUrl = "https://account.xiaomi.com/pass/getCode?icodeType=login&0.07169544044424958";
        init(context);
    }

    private void init(Context context) {
        int i;
        ImageView imageView = new ImageView(context);
        this.mIvCaptcha = imageView;
        addView(imageView, -1, -1);
        this.mIsTalkBackAlive = TalkBack.isActive(context);
        ImageView imageView2 = this.mIvCaptcha;
        Resources resources = getResources();
        if (this.mIsTalkBackAlive) {
            i = R.string.passport_talkback_voice_captcha;
        } else {
            i = R.string.passport_talkback_image_captcha;
        }
        imageView2.setContentDescription(resources.getString(i));
        this.mIvCaptcha.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CaptchaView.this.startDownLoad();
            }
        });
    }

    public String getIck() {
        return this.mIck;
    }

    public void onCaptchaError() {
        String string;
        Context context = getContext();
        if (this.mIsTalkBackAlive) {
            string = getResources().getString(R.string.passport_wrong_voice);
        } else {
            string = getResources().getString(R.string.passport_wrong_captcha);
        }
        AccountToast.showToastMessage(context, string);
    }

    public void setupCaptcha(String str, String str2) {
        this.mVoiceCaptchaUrl = str2;
        this.mImageCaptchaUrl = str;
        startDownLoad();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDownLoad() {
        post(new Runnable() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.2
            @Override // java.lang.Runnable
            public void run() {
                if (CaptchaView.this.mIsTalkBackAlive) {
                    CaptchaView captchaView = CaptchaView.this;
                    captchaView.downloadAndPlayVoiceCaptcha(captchaView.mVoiceCaptchaUrl);
                } else {
                    CaptchaView captchaView2 = CaptchaView.this;
                    captchaView2.downloadImageCaptcha(captchaView2.mImageCaptchaUrl);
                }
            }
        });
    }

    private void cancelCaptchaDownloadTask() {
        SimpleFutureTask<Pair<Bitmap, String>> simpleFutureTask = this.mFetchImageCaptchaTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
            this.mFetchImageCaptchaTask = null;
        }
        SimpleFutureTask<Boolean> simpleFutureTask2 = this.mFetchVoiceCaptchaTask;
        if (simpleFutureTask2 != null) {
            simpleFutureTask2.cancel(true);
            this.mFetchVoiceCaptchaTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadImageCaptcha(final String str) {
        SimpleFutureTask<Pair<Bitmap, String>> simpleFutureTask = this.mFetchImageCaptchaTask;
        if (simpleFutureTask != null && !simpleFutureTask.isDone()) {
            AccountLogger.log(TAG, "pre image task passport_input_speaker_capcha_hintis doing");
            return;
        }
        final Context applicationContext = getContext().getApplicationContext();
        final int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.passport_captcha_img_w);
        final int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.passport_captcha_img_h);
        this.mFetchImageCaptchaTask = new SimpleFutureTask<>(new Callable<Pair<Bitmap, String>>() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Pair<Bitmap, String> call() throws Exception {
                Pair pairBlockingDownloadCaptcha = CaptchaView.this.blockingDownloadCaptcha(applicationContext, str);
                if (pairBlockingDownloadCaptcha == null) {
                    AccountLogger.log(CaptchaView.TAG, "image captcha result is null");
                    return null;
                }
                return Pair.create(CaptchaView.getFixedImageBitmap(((File) pairBlockingDownloadCaptcha.first).getPath(), dimensionPixelSize, dimensionPixelSize2), (String) pairBlockingDownloadCaptcha.second);
            }
        }, new SimpleFutureTask.Callback<Pair<Bitmap, String>>() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.3
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<Pair<Bitmap, String>> simpleFutureTask2) {
                try {
                    Pair<Bitmap, String> pair = simpleFutureTask2.get();
                    if (pair == null) {
                        return;
                    }
                    CaptchaView.this.mIck = (String) pair.second;
                    CaptchaView.this.mIvCaptcha.setBackground(new BitmapDrawable((Bitmap) pair.first));
                } catch (InterruptedException e) {
                    AccountLogger.log(CaptchaView.TAG, "downloadCaptchaImage", e);
                } catch (ExecutionException e2) {
                    AccountLogger.log(CaptchaView.TAG, "downloadCaptchaImage", e2);
                }
            }
        });
        XiaomiPassportExecutor.getSingleton().execute(this.mFetchImageCaptchaTask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadAndPlayVoiceCaptcha(final String str) {
        SimpleFutureTask<Boolean> simpleFutureTask = this.mFetchVoiceCaptchaTask;
        if (simpleFutureTask != null && !simpleFutureTask.isDone()) {
            AccountLogger.log(TAG, "pre speaker task is doing");
            return;
        }
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.5
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer2) {
                mediaPlayer2.release();
                if (CaptchaView.this.mIsTalkBackAlive) {
                    CaptchaView.this.mIvCaptcha.setBackgroundResource(R.drawable.passport_ic_sound_wave_retry);
                }
            }
        });
        this.mFetchVoiceCaptchaTask = new SimpleFutureTask<>(new Callable<Boolean>() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Boolean call() throws Exception {
                Context applicationContext = CaptchaView.this.getContext().getApplicationContext();
                Pair pairBlockingDownloadCaptcha = CaptchaView.this.blockingDownloadCaptcha(applicationContext, str);
                if (pairBlockingDownloadCaptcha == null) {
                    AccountLogger.log(CaptchaView.TAG, "speaker captcha null");
                    return false;
                }
                mediaPlayer.setDataSource(applicationContext, Uri.fromFile((File) pairBlockingDownloadCaptcha.first));
                mediaPlayer.prepare();
                CaptchaView.this.mIck = (String) pairBlockingDownloadCaptcha.second;
                return true;
            }
        }, new SimpleFutureTask.Callback<Boolean>() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.6
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<Boolean> simpleFutureTask2) {
                try {
                    try {
                        boolean zBooleanValue = simpleFutureTask2.get().booleanValue();
                        if (zBooleanValue) {
                            CaptchaView.this.mIvCaptcha.setBackgroundResource(R.drawable.passport_ic_sound_wave);
                            mediaPlayer.start();
                        } else {
                            AccountToast.showToastMessage(CaptchaView.this.getContext(), R.string.passport_input_voice_captcha_hint, 1);
                        }
                        if (zBooleanValue) {
                            return;
                        }
                        mediaPlayer.release();
                    } catch (InterruptedException e) {
                        AccountLogger.log(CaptchaView.TAG, "downloadSpeakerCaptcha", e);
                        if (0 != 0) {
                            return;
                        }
                        mediaPlayer.release();
                    } catch (ExecutionException e2) {
                        AccountLogger.log(CaptchaView.TAG, "downloadSpeakerCaptcha", e2);
                        if (0 == 0) {
                            mediaPlayer.release();
                        }
                    }
                } catch (Throwable th) {
                    if (0 == 0) {
                        mediaPlayer.release();
                    }
                    throw th;
                }
            }
        });
        XiaomiPassportExecutor.getSingleton().execute(this.mFetchVoiceCaptchaTask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Pair<File, String> blockingDownloadCaptcha(Context context, String str) {
        SimpleRequest.StreamContent asStream;
        try {
            asStream = SimpleRequestForAccount.getAsStream(str, null, null);
        } catch (AccessDeniedException e) {
            AccountLogger.log(TAG, "getCaptcha", e);
            asStream = null;
        } catch (AuthenticationFailureException e2) {
            AccountLogger.log(TAG, "getCaptcha", e2);
            asStream = null;
        } catch (IOException e3) {
            AccountLogger.log(TAG, "getCaptcha", e3);
            asStream = null;
        }
        try {
            if (asStream == null) {
                return null;
            }
            return Pair.create(BitmapUtils.saveAsFile(context, asStream.getStream(), NeedCaptchaException.TYPE_PICTURE_CAPTCHA), asStream.getHeader("ick"));
        } catch (IOException e4) {
            AccountLogger.log(TAG, "getCaptcha", e4);
            return null;
        } finally {
            asStream.closeStream();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap getFixedImageBitmap(String str, int i, int i2) {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeFile, i, i2, true);
        if (bitmapDecodeFile != bitmapCreateScaledBitmap) {
            bitmapDecodeFile.recycle();
        }
        return bitmapCreateScaledBitmap;
    }

    public String getHint() {
        int i;
        Context context = getContext();
        if (this.mIsTalkBackAlive) {
            i = R.string.passport_input_voice_captcha_hint;
        } else {
            i = R.string.passport_input_captcha_hint;
        }
        return context.getString(i);
    }
}
