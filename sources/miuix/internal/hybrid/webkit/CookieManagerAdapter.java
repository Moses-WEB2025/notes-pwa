package miuix.internal.hybrid.webkit;

import miuix.hybrid.CookieManager;

/* JADX INFO: loaded from: classes5.dex */
public class CookieManagerAdapter extends CookieManager {
    private android.webkit.CookieManager mCookieManager;

    public CookieManagerAdapter(android.webkit.CookieManager cookieManager) {
        this.mCookieManager = cookieManager;
    }

    @Override // miuix.hybrid.CookieManager
    public void setAcceptCookie(boolean z) {
        this.mCookieManager.setAcceptCookie(z);
    }

    @Override // miuix.hybrid.CookieManager
    public boolean acceptCookie() {
        return android.webkit.CookieManager.getInstance().acceptCookie();
    }

    @Override // miuix.hybrid.CookieManager
    public void setCookie(String str, String str2) {
        this.mCookieManager.setCookie(str, str2);
    }

    @Override // miuix.hybrid.CookieManager
    public String getCookie(String str) {
        return this.mCookieManager.getCookie(str);
    }

    @Override // miuix.hybrid.CookieManager
    public void removeSessionCookie() {
        this.mCookieManager.removeSessionCookie();
    }

    @Override // miuix.hybrid.CookieManager
    public void removeAllCookie() {
        this.mCookieManager.removeAllCookie();
    }

    @Override // miuix.hybrid.CookieManager
    public boolean hasCookies() {
        return this.mCookieManager.hasCookies();
    }

    @Override // miuix.hybrid.CookieManager
    public void removeExpiredCookie() {
        this.mCookieManager.removeExpiredCookie();
    }

    @Override // miuix.hybrid.CookieManager
    protected boolean allowFileSchemeCookiesImpl() {
        return android.webkit.CookieManager.allowFileSchemeCookies();
    }

    @Override // miuix.hybrid.CookieManager
    protected void setAcceptFileSchemeCookiesImpl(boolean z) {
        android.webkit.CookieManager.setAcceptFileSchemeCookies(z);
    }
}
