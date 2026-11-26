package com.miui.richeditor;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.miui.notes.R;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.EditLineHeightSpan;
import java.lang.ref.WeakReference;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ImageInsertTask extends AsyncTask<Uri, Void, SpannableStringBuilder> implements IEditorBgTask {
    private static final String TAG = "ImageInsertTask";
    private final MainHandler<RichEditTextView> handler = new MainHandler<>(Looper.getMainLooper(), null);
    private final WeakReference<RichEditTextView> mViewRef;
    private final Runnable nCallback;
    private final String nText;

    public ImageInsertTask(Runnable runnable, RichEditTextView richEditTextView) {
        this.nCallback = runnable;
        this.nText = richEditTextView.getText().toString();
        this.mViewRef = new WeakReference<>(richEditTextView);
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        if (this.handler != null) {
            Logger.INSTANCE.i(TAG, "imageInsert onPreExecute,handler post delay ...");
            this.handler.postDelayed(new Runnable() { // from class: com.miui.richeditor.ImageInsertTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onPreExecute$0();
                }
            }, 0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPreExecute$0() {
        RichEditTextView richEditTextView = this.mViewRef.get();
        if (richEditTextView != null) {
            if (this == richEditTextView.getImageInsertTask()) {
                richEditTextView.showLoadingView();
                Logger.INSTANCE.i(TAG, "imageInsert onPreExecute,showLoadingView ...");
            } else {
                Logger.INSTANCE.w(TAG, "task has changed, don't show loading progress");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public SpannableStringBuilder doInBackground(Uri... uriArr) throws Throwable {
        Logger.INSTANCE.i(TAG, "imageInsert doInBackground ...");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");
        boolean zAppendImage = false;
        for (Uri uri : uriArr) {
            spannableStringBuilder.append(StringUtils.LF);
            RichEditTextView richEditTextView = this.mViewRef.get();
            if (richEditTextView != null) {
                spannableStringBuilder.setSpan(new EditLineHeightSpan(richEditTextView, 0), 0, 1, 33);
            }
            zAppendImage = appendImage(spannableStringBuilder, uri);
            Logger.INSTANCE.i(TAG, "imageInsert doInBackground appendImage1 success = " + zAppendImage);
            if (Thread.currentThread().isInterrupted()) {
                Logger.INSTANCE.i(TAG, "imageInsert doInBackground Thread.currentThread() interrupted");
                return null;
            }
        }
        if (uriArr.length == 1 && !zAppendImage) {
            AiActionUtil.asyncNotifyResult(-1);
        }
        return spannableStringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(SpannableStringBuilder spannableStringBuilder) {
        RichEditTextView richEditTextView = this.mViewRef.get();
        if (richEditTextView == null) {
            Logger.INSTANCE.i(TAG, "ImageInsertTask view = null");
            return;
        }
        if (this != richEditTextView.mImageInsertTask) {
            Logger.INSTANCE.w(TAG, "task has changed, don't apply image result");
            return;
        }
        richEditTextView.mImageInsertTask = null;
        Logger.INSTANCE.i(TAG, "imageInsert onPostExecute,hideLoadingView.");
        richEditTextView.hideLoadingView();
        if (TextUtils.isEmpty(spannableStringBuilder)) {
            richEditTextView.makeTextToToast(richEditTextView.getContext(), R.string.toast_message_add_image_fail, 0);
            return;
        }
        if ((richEditTextView.getText() == null || !this.nText.equals(richEditTextView.getText().toString())) && !TextUtils.isEmpty(this.nText)) {
            Logger.INSTANCE.w(TAG, "text has changed, don't apply image result");
            return;
        }
        Editable text = richEditTextView.getText();
        int length = richEditTextView.mCacheEditSelection[0];
        if (length == -1) {
            length = richEditTextView.getPreferSelectionStart(false);
        }
        int iMax = Math.max(length, richEditTextView.mCacheEditSelection[1] == -1 ? richEditTextView.getSelectionEnd() : richEditTextView.mCacheEditSelection[1]);
        if (length > richEditTextView.getText().length()) {
            length = richEditTextView.getText().length();
        }
        if (iMax > richEditTextView.getText().length()) {
            iMax = richEditTextView.getText().length();
        }
        if (length > 0 && text.charAt(length - 1) != '\n') {
            richEditTextView.clearTextWatcher();
            richEditTextView.setSkipRichText(true);
            text.insert(length, StringUtils.LF);
            int i = length + 1;
            text.setSpan(new EditLineHeightSpan(richEditTextView, 0), length, i, 33);
            richEditTextView.setSkipRichText(false);
            richEditTextView.restoreTextWatcher();
            length = i;
        }
        if (iMax != text.length() && text.charAt(iMax) == '\n') {
            iMax++;
        }
        SpannableStringBuilder spannableStringBuilderTrimImageText = richEditTextView.trimImageText(spannableStringBuilder);
        if (spannableStringBuilderTrimImageText == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "insertImage onPostExecute start = " + length + " end " + iMax);
        richEditTextView.setImage(text, spannableStringBuilderTrimImageText, length, iMax);
        richEditTextView.setSelection(length + spannableStringBuilderTrimImageText.length());
        richEditTextView.cacheSelection(false);
        richEditTextView.setCursorVisible(true);
        this.nCallback.run();
    }

    @Override // com.miui.richeditor.IEditorBgTask
    public void restoreAndCancel() {
        Logger.INSTANCE.i(TAG, "imageInsert restoreAndCancel,restoreAndCancel:" + this.handler);
        if (this.handler != null) {
            Logger.INSTANCE.i(TAG, "imageInsert restoreAndCancel,handler removeCallbacksAndMessages ...");
            this.handler.removeCallbacksAndMessages(null);
        }
        RichEditTextView richEditTextView = this.mViewRef.get();
        if (richEditTextView != null) {
            richEditTextView.hideLoadingView();
            if (richEditTextView.getImageInsertTask() == this) {
                richEditTextView.clearImageInsertTask();
            }
            cancel(true);
            richEditTextView.makeTextToToast(richEditTextView.getContext(), R.string.toast_cancel_to_insert_image, 0);
        }
    }

    private boolean appendImage(SpannableStringBuilder spannableStringBuilder, Uri uri) throws Throwable {
        RichEditTextView richEditTextView = this.mViewRef.get();
        if (richEditTextView == null) {
            Logger.INSTANCE.i(TAG, "imageInsert doInBackground appendImage0 -> view is null");
            return false;
        }
        String strSaveImageFile = AttachmentUtils.saveImageFile(richEditTextView.getContext(), uri, true);
        if (TextUtils.isEmpty(strSaveImageFile)) {
            Logger.INSTANCE.i(TAG, "imageInsert doInBackground appendImage0 -> imageName is empty");
            return false;
        }
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(HtmlParser.MEDIA_DUMMY_CHAR).append(StringUtils.LF);
        int i = length + 1;
        richEditTextView.getStyleFactory().setNormalImageSpan(spannableStringBuilder, length, i, strSaveImageFile);
        spannableStringBuilder.setSpan(new EditLineHeightSpan(richEditTextView, 0), i, length + 2, 33);
        return true;
    }
}
