package miuix.internal.webkit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import miuix.internal.hybrid.provider.AbsWebView;
import miuix.internal.util.UrlResolverHelper;
import miuix.util.UrlResolver;

/* JADX INFO: loaded from: classes5.dex */
public class WebViewClientDelegate {
    private static final int FEATURE_ALL = -1;
    public static final int FEATURE_AUTO_LOGIN = 2;
    public static final int FEATURE_DEEP_LINK = 1;
    private static final String TAG = "WebViewClientDelegate";
    private DeviceAccountLogin mAccountLogin;
    private LoginState mLoginState;
    private boolean mSupportAutoLogin;
    private boolean mSupportDeepLink;

    private enum LoginState {
        LOGIN_START,
        LOGIN_INPROGRESS,
        LOGIN_FINISHED
    }

    public WebViewClientDelegate() {
        this(-1);
    }

    public WebViewClientDelegate(int i) {
        this(i, -1);
    }

    public WebViewClientDelegate(int i, int i2) {
        this.mLoginState = LoginState.LOGIN_FINISHED;
        int i3 = (i & i2) | (~i2);
        this.mSupportDeepLink = (i3 & 1) != 0;
        this.mSupportAutoLogin = (i3 & 2) != 0;
    }

    public boolean shouldOverrideUrlLoading(AbsWebView absWebView, String str) {
        if (!this.mSupportDeepLink || !UrlResolverHelper.isMiUrl(str)) {
            return false;
        }
        Context context = absWebView.getContext();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.addCategory("android.intent.category.BROWSABLE");
        ResolveInfo resolveInfoCheckMiuiIntent = UrlResolver.checkMiuiIntent(context, packageManager, intent);
        if (resolveInfoCheckMiuiIntent == null) {
            return false;
        }
        if (resolveInfoCheckMiuiIntent.activityInfo == null) {
            return true;
        }
        context.startActivity(intent);
        return true;
    }

    public void onPageStarted(AbsWebView absWebView, String str, Bitmap bitmap) {
        if (this.mSupportAutoLogin && this.mLoginState == LoginState.LOGIN_START) {
            this.mLoginState = LoginState.LOGIN_INPROGRESS;
        }
    }

    public void onPageFinished(AbsWebView absWebView, String str) {
        if (this.mSupportAutoLogin && this.mLoginState == LoginState.LOGIN_INPROGRESS) {
            this.mLoginState = LoginState.LOGIN_FINISHED;
            this.mAccountLogin.onLoginPageFinished();
        }
    }

    private static Activity getActivityContextFromView(View view) {
        Context context = ((ViewGroup) view.getRootView()).getChildAt(0).getContext();
        if (context.getClass().getName().contains("com.android.internal.policy.DecorContext")) {
            try {
                Field declaredField = context.getClass().getDeclaredField("mPhoneWindow");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(context);
                Object objInvoke = obj.getClass().getMethod("getContext", new Class[0]).invoke(obj, new Object[0]);
                if (objInvoke != null && (objInvoke instanceof Context)) {
                    context = (Context) objInvoke;
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        Log.i(TAG, "fail to get activity");
        return null;
    }

    public void onReceivedLoginRequest(AbsWebView absWebView, String str, String str2, String str3) {
        Activity activityContextFromView;
        if (this.mSupportAutoLogin && (activityContextFromView = getActivityContextFromView(absWebView.getRootView())) != null) {
            if (this.mAccountLogin == null) {
                this.mAccountLogin = new DefaultDeviceAccountLogin(activityContextFromView, absWebView);
            }
            if (absWebView.canGoForward()) {
                if (absWebView.canGoBack()) {
                    absWebView.goBack();
                    return;
                } else {
                    activityContextFromView.finish();
                    return;
                }
            }
            this.mLoginState = LoginState.LOGIN_START;
            absWebView.setVisibility(4);
            this.mAccountLogin.login(str, str2, str3);
        }
    }
}
