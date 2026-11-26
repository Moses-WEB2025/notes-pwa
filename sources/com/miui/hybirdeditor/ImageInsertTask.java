package com.miui.hybirdeditor;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.richeditor.IEditorBgTask;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes3.dex */
public class ImageInsertTask extends AsyncTask<Uri, Void, String[]> implements IEditorBgTask {
    private static final String TAG = "Notes:ImageInsertTask";
    private boolean mNeedWithPos;
    private WeakReference<WebRichEditor> mViewRef;
    private Runnable nCallback;
    private String nText;
    private String[] savedImageNames = null;

    @Override // com.miui.richeditor.IEditorBgTask
    public void restoreAndCancel() {
    }

    public ImageInsertTask(Runnable runnable, WebRichEditor webRichEditor, boolean z) {
        this.mNeedWithPos = false;
        this.nCallback = runnable;
        this.mViewRef = new WeakReference<>(webRichEditor);
        this.mNeedWithPos = z;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        WebRichEditor webRichEditor = this.mViewRef.get();
        if (webRichEditor == null || webRichEditor.mWebCore == null) {
            return;
        }
        webRichEditor.mWebCore.showLoadingView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public String[] doInBackground(Uri... uriArr) throws Throwable {
        Logger.INSTANCE.d(TAG, "doInBackground.....");
        Thread.currentThread().setName("ImgInsertTask");
        String[] strArr = new String[uriArr.length];
        this.savedImageNames = strArr;
        int length = uriArr.length;
        boolean zAppendImage = appendImage(strArr, uriArr[0], 0);
        if (length == 1 && !zAppendImage) {
            AiActionUtil.asyncNotifyResult(-1);
        }
        if (!zAppendImage) {
            length--;
        }
        for (int i = 1; i < uriArr.length; i++) {
            if (!appendImage(this.savedImageNames, uriArr[i], i)) {
                length--;
            }
            if (Thread.currentThread().isInterrupted()) {
                Logger.INSTANCE.i(TAG, "imageInsert doInBackground Thread.currentThread() interrupted");
                return null;
            }
        }
        if (length <= 0) {
            return null;
        }
        String[] strArr2 = new String[length];
        int i2 = 0;
        for (int i3 = 0; i3 < uriArr.length && i2 < length; i3++) {
            String str = this.savedImageNames[i3];
            if (str != null) {
                strArr2[i2] = str;
                i2++;
            }
        }
        return strArr2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(String[] strArr) {
        Logger.INSTANCE.d(TAG, "onPostExecute, images size: " + (strArr == null ? 0 : strArr.length));
        WebRichEditor webRichEditor = this.mViewRef.get();
        if (webRichEditor != null) {
            if (webRichEditor.mWebCore != null) {
                webRichEditor.mWebCore.hideLoadingView();
            }
            webRichEditor.insertImageEnd(strArr, this.mNeedWithPos);
        }
    }

    private boolean appendImage(String[] strArr, Uri uri, int i) throws Throwable {
        Logger.INSTANCE.d(TAG, "appendImage");
        WebRichEditor webRichEditor = this.mViewRef.get();
        if (webRichEditor != null) {
            String strSaveImageFile = AttachmentUtils.saveImageFile(webRichEditor.getContext(), uri, true);
            if (TextUtils.isEmpty(strSaveImageFile)) {
                strArr[i] = null;
                Logger.INSTANCE.i(TAG, "imageInsert doInBackground appendImage0 -> imageName is empty");
                return false;
            }
            strArr[i] = strSaveImageFile;
            return true;
        }
        Logger.INSTANCE.i(TAG, "imageInsert doInBackground appendImage0 -> view is null");
        return false;
    }
}
