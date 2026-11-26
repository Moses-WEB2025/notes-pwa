package com.xiaomi.passport.jsb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.passport.webview.PassportJsbWebPageLifecycleListener;
import com.xiaomi.passport.webview.UrlInterceptor;
import com.xiaomi.passport.webview.UrlLoadPrepareTask;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbWebViewPageConfig {
    public static final String EXTRA_BOOL_CLOSE_AFTER_LOGIN = "close_after_login";
    public static final String EXTRA_BOOL_REMOVE_ALL_COOKIES = "remove_all_cookies";
    public static final String EXTRA_BUNDLE_PARCELS_WRAPPER = "parcels_wrapper";
    public static final String EXTRA_PARCEL_PAGE_LIFECYCLE_LISTENER = "page_lifecycle_listener";
    public static final String EXTRA_STRING_URL = "url";
    public final ActionBarConfig actionBarConfig;
    public final boolean closeAfterLogin;
    public final CookieFillConfig cookieFillConfig;
    public final HeaderFillConfig headerFillConfig;
    public final PassportJsbWebPageLifecycleListener pageLifecycleListener;
    public final boolean removeAllCookies;
    public final String url;
    public final UrlInteractionStrategy urlInteractionStrategy;

    public static class CookieFillConfig {
        public static final String EXTRA_BOOL_COOKIE_FILL_ACCOUNT_DEVICE_PARAMS = "cookie_fill_account_device_params";
        public static final String EXTRA_BOOL_COOKIE_FILL_COOKIE_SET_URL = "cookie_fill_cookie_set_url";
        public static final String EXTRA_BOOL_COOKIE_FILL_LOGIN_SERVICE_ID = "cookie_fill_login_service_id";
        public final boolean cookieFillAccountDeviceParams;
        public final String cookieSetUrl;
        public final String loginServiceId;

        private CookieFillConfig(String str, boolean z, String str2) {
            this.cookieSetUrl = str;
            this.loginServiceId = str2;
            this.cookieFillAccountDeviceParams = z;
        }

        public Bundle fillBundle(Bundle bundle) {
            bundle.putString(EXTRA_BOOL_COOKIE_FILL_COOKIE_SET_URL, this.cookieSetUrl);
            bundle.putBoolean(EXTRA_BOOL_COOKIE_FILL_ACCOUNT_DEVICE_PARAMS, this.cookieFillAccountDeviceParams);
            bundle.putString(EXTRA_BOOL_COOKIE_FILL_LOGIN_SERVICE_ID, this.loginServiceId);
            return bundle;
        }

        public static CookieFillConfig create(String str, boolean z, String str2) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = null;
            }
            return new CookieFillConfig(str, z, str2);
        }
    }

    public static class HeaderFillConfig {
        public static final String EXTRA_BOOL_HEADER_FILL_ACTIVATOR_TOKEN = "header_fill_activator_token";
        public final boolean headerFillActivatorToken;

        private HeaderFillConfig(boolean z) {
            this.headerFillActivatorToken = z;
        }

        public Bundle fillBundle(Bundle bundle) {
            bundle.putBoolean(EXTRA_BOOL_HEADER_FILL_ACTIVATOR_TOKEN, this.headerFillActivatorToken);
            return bundle;
        }

        public static HeaderFillConfig create(boolean z) {
            return new HeaderFillConfig(z);
        }
    }

    public static class ActionBarConfig {
        public static final String EXTRA_STRING_ACTION_BAR_STYLE = "action_bar_style";
        public static final String EXTRA_STRING_ACTION_BAR_TITLE = "action_bar_title";
        public static final String VAL_ACTION_BAR_STYLE_NATIVE = "native";
        public static final String VAL_ACTION_BAR_STYLE_NONE = "none";
        public static final String VAL_ACTION_BAR_STYLE_WEB = "web";
        public final String style;
        public final String title;

        private ActionBarConfig(String str, String str2) {
            this.style = str;
            this.title = str2;
        }

        public static ActionBarConfig create(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            if ("none".equals(str)) {
                return new ActionBarConfig(str, str2);
            }
            if ((VAL_ACTION_BAR_STYLE_WEB.equals(str) || "native".equals(str)) && !TextUtils.isEmpty(str2)) {
                return new ActionBarConfig(str, str2);
            }
            return null;
        }

        public static ActionBarConfig createNoTitle() {
            return new ActionBarConfig("none", null);
        }

        public Bundle fillBundle(Bundle bundle) {
            bundle.putString(EXTRA_STRING_ACTION_BAR_STYLE, this.style);
            bundle.putString(EXTRA_STRING_ACTION_BAR_TITLE, this.title);
            return bundle;
        }
    }

    public static class UrlInteractionStrategy {
        public static final String EXTRA_PARCEL_UIS_JSB_METHODS = "uis_jsb_methods";
        public static final String EXTRA_PARCEL_UIS_URL_INTERCEPTORS = "uis_url_interceptors";
        public static final String EXTRA_PARCEL_UIS_URL_LOAD_PREPARE_TASKS = "uis_url_load_prepare_runnables";
        public final ParcelablePassportJsbMethod[] jsbMethods;
        public final UrlInterceptor[] urlInterceptors;
        public final UrlLoadPrepareTask[] urlLoadPrepareTasks;

        private UrlInteractionStrategy(UrlInterceptor[] urlInterceptorArr, UrlLoadPrepareTask[] urlLoadPrepareTaskArr, ParcelablePassportJsbMethod[] parcelablePassportJsbMethodArr) {
            this.urlInterceptors = urlInterceptorArr;
            this.urlLoadPrepareTasks = urlLoadPrepareTaskArr;
            this.jsbMethods = parcelablePassportJsbMethodArr;
        }

        public Bundle fillBundle(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle(PassportJsbWebViewPageConfig.EXTRA_BUNDLE_PARCELS_WRAPPER);
            if (bundle2 == null) {
                bundle2 = new Bundle();
                bundle.putBundle(PassportJsbWebViewPageConfig.EXTRA_BUNDLE_PARCELS_WRAPPER, bundle2);
            }
            UrlInterceptor[] urlInterceptorArr = this.urlInterceptors;
            if (urlInterceptorArr != null) {
                bundle2.putParcelableArray(EXTRA_PARCEL_UIS_URL_INTERCEPTORS, urlInterceptorArr);
            }
            UrlLoadPrepareTask[] urlLoadPrepareTaskArr = this.urlLoadPrepareTasks;
            if (urlLoadPrepareTaskArr != null) {
                bundle2.putParcelableArray(EXTRA_PARCEL_UIS_URL_LOAD_PREPARE_TASKS, urlLoadPrepareTaskArr);
            }
            ParcelablePassportJsbMethod[] parcelablePassportJsbMethodArr = this.jsbMethods;
            if (parcelablePassportJsbMethodArr != null) {
                bundle2.putParcelableArray(EXTRA_PARCEL_UIS_JSB_METHODS, parcelablePassportJsbMethodArr);
            }
            return bundle;
        }

        public static UrlInteractionStrategy create(UrlInterceptor[] urlInterceptorArr, UrlLoadPrepareTask[] urlLoadPrepareTaskArr, ParcelablePassportJsbMethod[] parcelablePassportJsbMethodArr) {
            return new UrlInteractionStrategy(urlInterceptorArr, urlLoadPrepareTaskArr, parcelablePassportJsbMethodArr);
        }
    }

    public PassportJsbWebViewPageConfig(Builder builder) {
        this.url = builder.url;
        this.actionBarConfig = builder.actionBarConfig;
        this.cookieFillConfig = builder.cookieFillConfig;
        this.headerFillConfig = builder.headerFillConfig;
        this.removeAllCookies = builder.removeAllCookies;
        this.closeAfterLogin = builder.closeAfterLogin;
        this.urlInteractionStrategy = builder.urlInteractionStrategy;
        this.pageLifecycleListener = builder.pageLifecycleListener;
    }

    public Bundle fillBundle(Bundle bundle) {
        if (!TextUtils.isEmpty(this.url)) {
            bundle.putString("url", this.url);
        }
        bundle.putBoolean(EXTRA_BOOL_REMOVE_ALL_COOKIES, this.removeAllCookies);
        bundle.putBoolean(EXTRA_BOOL_CLOSE_AFTER_LOGIN, this.closeAfterLogin);
        ActionBarConfig actionBarConfig = this.actionBarConfig;
        if (actionBarConfig != null) {
            actionBarConfig.fillBundle(bundle);
        }
        CookieFillConfig cookieFillConfig = this.cookieFillConfig;
        if (cookieFillConfig != null) {
            cookieFillConfig.fillBundle(bundle);
        }
        HeaderFillConfig headerFillConfig = this.headerFillConfig;
        if (headerFillConfig != null) {
            headerFillConfig.fillBundle(bundle);
        }
        Bundle bundle2 = new Bundle();
        bundle.putBundle(EXTRA_BUNDLE_PARCELS_WRAPPER, bundle2);
        UrlInteractionStrategy urlInteractionStrategy = this.urlInteractionStrategy;
        if (urlInteractionStrategy != null) {
            urlInteractionStrategy.fillBundle(bundle);
        }
        PassportJsbWebPageLifecycleListener passportJsbWebPageLifecycleListener = this.pageLifecycleListener;
        if (passportJsbWebPageLifecycleListener != null) {
            bundle2.putParcelable(EXTRA_PARCEL_PAGE_LIFECYCLE_LISTENER, passportJsbWebPageLifecycleListener);
        }
        return bundle;
    }

    public static Builder parse(Intent intent, Bundle bundle) {
        UrlInterceptor[] urlInterceptorArr;
        UrlLoadPrepareTask[] urlLoadPrepareTaskArr;
        HashMap map = new HashMap();
        Uri data = intent.getData();
        if (data != null) {
            for (String str : data.getQueryParameterNames()) {
                map.put(str, data.getQueryParameter(str));
            }
        }
        Bundle bundle2 = new Bundle();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            bundle2.putAll(extras);
        }
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        for (String str2 : bundle2.keySet()) {
            if (bundle2.get(str2) != null) {
                map.put(str2, String.valueOf(bundle2.get(str2)));
            }
        }
        Bundle bundleExtra = intent.getBundleExtra(EXTRA_BUNDLE_PARCELS_WRAPPER);
        if (bundleExtra == null) {
            bundleExtra = new Bundle();
        }
        Parcelable[] parcelableArray = bundleExtra.getParcelableArray(UrlInteractionStrategy.EXTRA_PARCEL_UIS_URL_INTERCEPTORS);
        ParcelablePassportJsbMethod[] parcelablePassportJsbMethodArr = null;
        if (parcelableArray != null) {
            int length = parcelableArray.length;
            urlInterceptorArr = new UrlInterceptor[length];
            for (int i = 0; i < length; i++) {
                urlInterceptorArr[i] = (UrlInterceptor) parcelableArray[i];
            }
        } else {
            urlInterceptorArr = null;
        }
        Parcelable[] parcelableArray2 = bundleExtra.getParcelableArray(UrlInteractionStrategy.EXTRA_PARCEL_UIS_URL_LOAD_PREPARE_TASKS);
        if (parcelableArray2 != null) {
            int length2 = parcelableArray2.length;
            urlLoadPrepareTaskArr = new UrlLoadPrepareTask[length2];
            for (int i2 = 0; i2 < length2; i2++) {
                urlLoadPrepareTaskArr[i2] = (UrlLoadPrepareTask) parcelableArray2[i2];
            }
        } else {
            urlLoadPrepareTaskArr = null;
        }
        Parcelable[] parcelableArray3 = bundleExtra.getParcelableArray(UrlInteractionStrategy.EXTRA_PARCEL_UIS_JSB_METHODS);
        if (parcelableArray3 != null) {
            int length3 = parcelableArray3.length;
            ParcelablePassportJsbMethod[] parcelablePassportJsbMethodArr2 = new ParcelablePassportJsbMethod[length3];
            for (int i3 = 0; i3 < length3; i3++) {
                parcelablePassportJsbMethodArr2[i3] = (ParcelablePassportJsbMethod) parcelableArray3[i3];
            }
            parcelablePassportJsbMethodArr = parcelablePassportJsbMethodArr2;
        }
        return new Builder().url((String) map.get("url")).removeAllCookies(Boolean.parseBoolean((String) map.get(EXTRA_BOOL_REMOVE_ALL_COOKIES))).closeAfterLogin(Boolean.parseBoolean((String) map.get(EXTRA_BOOL_CLOSE_AFTER_LOGIN))).actionBarConfig(ActionBarConfig.create((String) map.get(ActionBarConfig.EXTRA_STRING_ACTION_BAR_STYLE), (String) map.get(ActionBarConfig.EXTRA_STRING_ACTION_BAR_TITLE))).cookieFillConfig(CookieFillConfig.create((String) map.get(CookieFillConfig.EXTRA_BOOL_COOKIE_FILL_COOKIE_SET_URL), Boolean.parseBoolean((String) map.get(CookieFillConfig.EXTRA_BOOL_COOKIE_FILL_ACCOUNT_DEVICE_PARAMS)), (String) map.get(CookieFillConfig.EXTRA_BOOL_COOKIE_FILL_LOGIN_SERVICE_ID))).headerFillConfig(HeaderFillConfig.create(Boolean.parseBoolean((String) map.get(HeaderFillConfig.EXTRA_BOOL_HEADER_FILL_ACTIVATOR_TOKEN)))).urlLoadInteractionStrategy(UrlInteractionStrategy.create(urlInterceptorArr, urlLoadPrepareTaskArr, parcelablePassportJsbMethodArr)).pageLifecycleListener((PassportJsbWebPageLifecycleListener) bundleExtra.getParcelable(EXTRA_PARCEL_PAGE_LIFECYCLE_LISTENER));
    }

    public static Builder parse(Intent intent) {
        return parse(intent, null);
    }

    public static class Builder {
        private ActionBarConfig actionBarConfig;
        private boolean closeAfterLogin;
        private CookieFillConfig cookieFillConfig;
        private HeaderFillConfig headerFillConfig;
        public PassportJsbWebPageLifecycleListener pageLifecycleListener;
        private boolean removeAllCookies;
        private String url;
        public UrlInteractionStrategy urlInteractionStrategy;

        public Builder url(String str) {
            this.url = str;
            return this;
        }

        public Builder actionBarConfig(ActionBarConfig actionBarConfig) {
            this.actionBarConfig = actionBarConfig;
            return this;
        }

        public Builder removeAllCookies(boolean z) {
            this.removeAllCookies = z;
            return this;
        }

        public Builder closeAfterLogin(boolean z) {
            this.closeAfterLogin = z;
            return this;
        }

        public Builder cookieFillConfig(CookieFillConfig cookieFillConfig) {
            this.cookieFillConfig = cookieFillConfig;
            return this;
        }

        public Builder headerFillConfig(HeaderFillConfig headerFillConfig) {
            this.headerFillConfig = headerFillConfig;
            return this;
        }

        public Builder urlLoadInteractionStrategy(UrlInteractionStrategy urlInteractionStrategy) {
            this.urlInteractionStrategy = urlInteractionStrategy;
            return this;
        }

        public Builder pageLifecycleListener(PassportJsbWebPageLifecycleListener passportJsbWebPageLifecycleListener) {
            this.pageLifecycleListener = passportJsbWebPageLifecycleListener;
            return this;
        }

        public PassportJsbWebViewPageConfig build() {
            return new PassportJsbWebViewPageConfig(this);
        }
    }
}
