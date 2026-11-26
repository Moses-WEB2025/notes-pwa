package com.miui.hybirdeditor;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.google.common.net.HttpHeaders;
import com.miui.common.tool.AudioUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import miuix.webkit.WebViewClient;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes3.dex */
public class WebRichEditorClient extends WebViewClient {
    static final String TAG = "Notes:WebRichEditorClient";
    private Context mContext;
    private onRenderProcessGoneListener mOnRenderProcessGoneListener;
    final Map mResResponseHeaders;
    final String CONTENT_IMAGE_TEMPLATE = "content://com.miui.notes/note_image/%s";
    final String CONTENT_AUDIO_TEMPLATE = "content://com.miui.notes/share/Android/data/com.miui.notes/files/mp3/%s";
    final String LOCAL_HOST = "com.miui.notes";
    final String LOCAL_IMAGE_PATH_0 = "note_image";
    final String LOCAL_AUDIO_PATH_0 = "note_audio";

    public interface onRenderProcessGoneListener {
        void onRenderProcessExit(WebView webView);
    }

    public WebRichEditorClient(Context context) {
        HashMap map = new HashMap();
        this.mResResponseHeaders = map;
        this.mContext = context;
        map.put(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        map.put("Cache-Control", "max-age=86400");
    }

    @Override // android.webkit.WebViewClient
    public void onScaleChanged(WebView webView, float f, float f2) {
        super.onScaleChanged(webView, f, f2);
        Logger.INSTANCE.d(TAG, "onScaleChanged, oldScale: " + f + " newScale:" + f2);
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        long jLongValue;
        Logger.INSTANCE.d("WebEditor", "shouldInterceptRequest new request = " + webResourceRequest.getUrl().toString());
        if (webResourceRequest.getUrl() != null && "com.miui.notes".equals(webResourceRequest.getUrl().getHost())) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            List<String> pathSegments = webResourceRequest.getUrl().getPathSegments();
            if (pathSegments.size() > 1 && "note_image".equals(webResourceRequest.getUrl().getPathSegments().get(0))) {
                try {
                    WebResourceResponse webResourceResponse = new WebResourceResponse(ShareAppsAdapter.TYPE_IMAGE, "UTF-8", contentResolver.openInputStream(Uri.parse(String.format("content://com.miui.notes/note_image/%s", webResourceRequest.getUrl().getPathSegments().get(1)))));
                    webResourceResponse.setResponseHeaders(this.mResResponseHeaders);
                    return webResourceResponse;
                } catch (Exception e) {
                    e.printStackTrace();
                    WebResourceResponse webResourceResponse2 = new WebResourceResponse(ShareAppsAdapter.TYPE_IMAGE, "UTF-8", null);
                    webResourceResponse2.setStatusCodeAndReasonPhrase(404, "not found file");
                    return webResourceResponse2;
                }
            }
            if (pathSegments.size() > 1 && "note_audio".equals(webResourceRequest.getUrl().getPathSegments().get(0))) {
                try {
                    String str = webResourceRequest.getRequestHeaders().get("Range");
                    jLongValue = Long.valueOf(str.substring(6, str.length() - 1)).longValue();
                } catch (Exception e2) {
                    Logger.INSTANCE.e(TAG, "get requestStart error: " + e2.getMessage());
                    jLongValue = 0;
                }
                try {
                    try {
                        InputStream inputStreamOpenInputStream = contentResolver.openInputStream(Uri.parse(String.format("content://com.miui.notes/share/Android/data/com.miui.notes/files/mp3/%s", webResourceRequest.getUrl().getPathSegments().get(1))));
                        long jAvailable = inputStreamOpenInputStream.available();
                        try {
                            WebResourceResponse webResourceResponse3 = new WebResourceResponse(AudioUtils.MP3_MINI_TYPE, "UTF-8", inputStreamOpenInputStream);
                            webResourceRequest.getRequestHeaders().put("Accept-Ranges", "bytes");
                            webResourceRequest.getRequestHeaders().put("Content-Range", "bytes " + jLongValue + "-" + (jAvailable - 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + jAvailable);
                            if (jLongValue > 0) {
                                webResourceResponse3.setStatusCodeAndReasonPhrase(206, "206 response from " + jLongValue);
                            } else {
                                webResourceResponse3.setStatusCodeAndReasonPhrase(200, "200 response from " + jLongValue);
                            }
                            Map<String, String> requestHeaders = webResourceRequest.getRequestHeaders();
                            requestHeaders.put(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
                            requestHeaders.put(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, OPTIONS");
                            webResourceResponse3.setResponseHeaders(requestHeaders);
                            return webResourceResponse3;
                        } catch (FileNotFoundException e3) {
                            e = e3;
                            e.printStackTrace();
                            WebResourceResponse webResourceResponse4 = new WebResourceResponse(AudioUtils.MP3_MINI_TYPE, "UTF-8", null);
                            webResourceResponse4.setStatusCodeAndReasonPhrase(404, "not found file");
                            return webResourceResponse4;
                        }
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                } catch (FileNotFoundException e5) {
                    e = e5;
                }
            }
        }
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

    @Override // android.webkit.WebViewClient
    public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail renderProcessGoneDetail) {
        Logger.INSTANCE.e(TAG, "onRenderProcessGone: " + renderProcessGoneDetail.didCrash());
        onRenderProcessGoneListener onrenderprocessgonelistener = this.mOnRenderProcessGoneListener;
        if (onrenderprocessgonelistener == null) {
            return true;
        }
        onrenderprocessgonelistener.onRenderProcessExit(webView);
        return true;
    }

    public void setOnRenderProcessGoneListener(onRenderProcessGoneListener onrenderprocessgonelistener) {
        this.mOnRenderProcessGoneListener = onrenderprocessgonelistener;
    }
}
