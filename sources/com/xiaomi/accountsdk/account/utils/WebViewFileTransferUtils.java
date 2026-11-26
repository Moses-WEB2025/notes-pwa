package com.xiaomi.accountsdk.account.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.utils.HttpCookies;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class WebViewFileTransferUtils {
    private static final String TAG = "WebViewFileTransferUtil";

    interface FileChooserCallback {
        void onFileChoose(ValueCallback valueCallback, String str);
    }

    public static void setupMIUISysDownloadListener(WebView webView, Context context, String str) {
        webView.setDownloadListener(new MIUISysDownloadListener(context, str));
        setWebSettings(webView);
    }

    public static FileUpload setupFileUpload(WebView webView, Activity activity, int i) {
        FileUpload fileUpload = new FileUpload(i);
        fileUpload.setupUploadWebChromeClient(webView, activity);
        return fileUpload;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setWebSettings(WebView webView) {
        String str;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        String userAgentString = settings.getUserAgentString();
        if (!TextUtils.isEmpty(userAgentString)) {
            str = String.format("%s SupportFileTransfer/1.0 ", userAgentString);
        } else {
            str = "SupportFileTransfer/1.0";
        }
        settings.setUserAgentString(str);
    }

    public static class MIUISysDownloadListener implements DownloadListener {
        private Context mContext;
        private String mDesc;

        MIUISysDownloadListener(Context context, String str) {
            this.mContext = context.getApplicationContext();
            this.mDesc = str;
        }

        @Override // android.webkit.DownloadListener
        public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
            try {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
                request.setMimeType(str4);
                CookieManager cookieManagerOrNull = HttpCookies.getCookieManagerOrNull();
                if (cookieManagerOrNull != null) {
                    request.addRequestHeader("Cookie", cookieManagerOrNull.getCookie(str));
                }
                request.addRequestHeader("User-Agent", str2);
                String strGuessFileName = URLUtil.guessFileName(str, str3, str4);
                String str5 = String.format(this.mDesc, strGuessFileName);
                request.setDescription(str5);
                request.setTitle(strGuessFileName);
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(1);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(str, str3, str4));
                ((DownloadManager) this.mContext.getSystemService("download")).enqueue(request);
                Toast.makeText(this.mContext, str5, 1).show();
                AccountLogger.log(WebViewFileTransferUtils.TAG, "download file onDownloadStart and url: " + str);
            } catch (IllegalStateException e) {
                AccountLogger.log(WebViewFileTransferUtils.TAG, "download file failed for" + e);
            }
        }
    }

    public static class UploadWebChromeClient extends WebChromeClient {
        private FileChooserCallback mCallback;

        UploadWebChromeClient(FileChooserCallback fileChooserCallback) {
            this.mCallback = fileChooserCallback;
        }

        public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
            AccountLogger.log(WebViewFileTransferUtils.TAG, "upload file in openFile Uri Callback has accept Type" + str + "has capture" + str2);
            if (TextUtils.isEmpty(str)) {
                str = "*/*";
            }
            this.mCallback.onFileChoose(valueCallback, str);
        }

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            String str;
            AccountLogger.log(WebViewFileTransferUtils.TAG, "upload file and file chooser params: " + fileChooserParams.toString());
            if (fileChooserParams.getAcceptTypes() != null && fileChooserParams.getAcceptTypes().length > 0) {
                str = fileChooserParams.getAcceptTypes()[0];
                for (int i = 1; i < fileChooserParams.getAcceptTypes().length; i++) {
                    str = str + StringUtils.SPACE + fileChooserParams.getAcceptTypes()[i];
                }
            } else {
                str = "*/*";
            }
            this.mCallback.onFileChoose(valueCallback, str);
            return true;
        }
    }

    public static class FileUpload {
        final int fileChooserRequestCode;
        ValueCallback valueCallback;

        FileUpload(int i) {
            this.fileChooserRequestCode = i;
        }

        void setupUploadWebChromeClient(WebView webView, final Activity activity) {
            webView.setWebChromeClient(new UploadWebChromeClient(new FileChooserCallback() { // from class: com.xiaomi.accountsdk.account.utils.WebViewFileTransferUtils.FileUpload.1
                @Override // com.xiaomi.accountsdk.account.utils.WebViewFileTransferUtils.FileChooserCallback
                public void onFileChoose(ValueCallback valueCallback, String str) {
                    if (FileUpload.this.valueCallback != null) {
                        FileUpload.this.valueCallback.onReceiveValue(null);
                    }
                    FileUpload.this.valueCallback = valueCallback;
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.addCategory("android.intent.category.OPENABLE");
                    intent.setType(str);
                    activity.startActivityForResult(Intent.createChooser(intent, null), FileUpload.this.fileChooserRequestCode);
                }
            }));
            WebViewFileTransferUtils.setWebSettings(webView);
        }

        public void handleActivityResult(int i, int i2, Intent intent) {
            if (i != this.fileChooserRequestCode || this.valueCallback == null) {
                return;
            }
            Uri data = (intent == null || i2 != -1) ? null : intent.getData();
            if (data == null) {
                this.valueCallback.onReceiveValue(null);
                this.valueCallback = null;
            } else {
                AccountLogger.log(WebViewFileTransferUtils.TAG, "upload file onActivityResult after parser url: " + data.toString());
                this.valueCallback.onReceiveValue(new Uri[]{data});
                this.valueCallback = null;
            }
        }
    }
}
