package com.xiaomi.onetrack.api;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OnMainThreadException;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.ServiceQualityEvent;
import com.xiaomi.onetrack.util.DeviceUtil;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class c {
    protected static ExecutorService c = null;
    private static final String k = "BaseOneTrackImp";
    protected ak a;
    protected ak b;
    protected Context d;
    protected al e;
    protected Configuration f;
    protected OneTrack.ICommonPropertyProvider g;
    protected OneTrack.IEventHook h;
    protected com.xiaomi.onetrack.util.x i;
    protected boolean j = false;

    protected abstract void a(Context context);

    public c(Context context, Configuration configuration) {
        Context applicationContext = context.getApplicationContext();
        this.d = applicationContext;
        this.f = configuration;
        e(applicationContext);
        Log.d(k, "OneTrackImp init : " + configuration.toString());
        Log.d(k, "OneTrackImp sdk ver : 3.0.2");
        com.xiaomi.onetrack.util.r.a(k, "BuildConfig.Channel:global");
    }

    private void e(Context context) {
        com.xiaomi.onetrack.util.s.a(this.f.isInternational(), this.f.getRegion(), this.f.getMode());
        if (c == null) {
            c = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        }
        this.i = new com.xiaomi.onetrack.util.x(this.f);
        a(context);
        if (this.f.getMode() == OneTrack.Mode.APP) {
            com.xiaomi.onetrack.util.s.a(this.f.isOverrideMiuiRegionSetting());
            b(context);
            if (this.f.isExceptionCatcherEnable()) {
                com.xiaomi.onetrack.util.j.a(new d(this, context));
            }
        }
        c.execute(new o(this));
    }

    protected boolean a() {
        if (this.f.isOverrideMiuiRegionSetting()) {
            return TextUtils.equals(com.xiaomi.onetrack.util.s.m(), this.f.getRegion());
        }
        return true;
    }

    public void a(String str, String str2, Map<String, Object> map) {
        c.execute(new w(this, str2, map, str));
    }

    public void a(String str, Map<String, Object> map) {
        c.execute(new x(this, str, map));
    }

    public void a(String str, Map<String, Object> map, List<String> list) {
        c.execute(new y(this, str, map, list));
    }

    public void a(String str) {
        c.execute(new z(this, str));
    }

    public void a(String str, String str2, String str3, String str4, String str5, long j, Map<String, Object> map) {
        c.execute(new aa(this, map, str, str2, str3, str5, str4, j));
    }

    public void a(Map<String, Object> map) {
        c.execute(new ab(this, map));
    }

    public void a(String str, Object obj) {
        c.execute(new ac(this, obj, str));
    }

    public void a(String str, OneTrack.UserIdType userIdType, Map<String, Object> map, boolean z) {
        c.execute(new e(this, str, userIdType, z, map));
    }

    public void b(Map<String, ? extends Number> map) {
        c.execute(new f(this, map));
    }

    public void a(String str, Number number) {
        c.execute(new g(this, str, number));
    }

    public void a(Map<String, Object> map, boolean z) {
        c.execute(new h(this, z, map));
    }

    protected void b(Context context) {
        ((Application) context).registerActivityLifecycleCallbacks(new i(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        c.execute(new j(this));
    }

    protected void a(String str, boolean z) {
        c.execute(new k(this, str, z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, long j) {
        c.execute(new l(this, str, j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(boolean z) {
        c.execute(new m(this, z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d(String str) {
        boolean zA = com.xiaomi.onetrack.util.t.a(str);
        if (!zA) {
            com.xiaomi.onetrack.util.r.b(k, String.format("Invalid eventname: %s. Eventname can only consist of numbers, letters, underscores ,and can not start with a number or \"onetrack_\" or \"ot_\"", str));
        }
        return !zA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e(String str) {
        if ("onetrack_dau".equals(str) || "view".equals(str) || "ot_login".equals(str) || "ot_logout".equals(str)) {
            return false;
        }
        boolean zA = com.xiaomi.onetrack.util.t.a(str);
        if (!zA) {
            com.xiaomi.onetrack.util.r.b(k, String.format("Invalid eventname: %s. Eventname can only consist of numbers, letters, underscores ,and can not start with a number or \"onetrack_\" or \"ot_\"", str));
        }
        return !zA;
    }

    public void c(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        c.execute(new n(this, map));
    }

    public void b() {
        c.execute(new p(this));
    }

    public void b(String str) {
        c.execute(new q(this, str));
    }

    public void a(OneTrack.ICommonPropertyProvider iCommonPropertyProvider) {
        this.g = iCommonPropertyProvider;
    }

    public OneTrack.ICommonPropertyProvider c() {
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject f(String str) throws Throwable {
        try {
            OneTrack.ICommonPropertyProvider iCommonPropertyProvider = this.g;
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.t.a(iCommonPropertyProvider != null ? iCommonPropertyProvider.getDynamicProperty(str) : null, false);
            String strA = com.xiaomi.onetrack.util.m.a(com.xiaomi.onetrack.util.t.a(this.f));
            return com.xiaomi.onetrack.util.t.a(jSONObjectA, !TextUtils.isEmpty(strA) ? new JSONObject(strA) : null);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(k, "getCommonProperty failed, e: " + e.getMessage());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject d(Map<String, Object> map) throws Throwable {
        try {
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.t.a(map, false);
            String strA = com.xiaomi.onetrack.util.m.a(com.xiaomi.onetrack.util.t.a(this.f));
            return com.xiaomi.onetrack.util.t.a(jSONObjectA, !TextUtils.isEmpty(strA) ? new JSONObject(strA) : null);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(k, "getCommonPropertyForException failed, e: " + e.getMessage());
            return null;
        }
    }

    public void c(String str) {
        c.execute(new r(this, str));
    }

    public String d() throws OnMainThreadException {
        if (com.xiaomi.onetrack.util.y.a()) {
            throw new OnMainThreadException("Can't call this method on main thread");
        }
        return com.xiaomi.onetrack.util.q.a().b();
    }

    public String c(Context context) throws OnMainThreadException {
        if (com.xiaomi.onetrack.util.y.a()) {
            throw new OnMainThreadException("Can't call this method on main thread");
        }
        return DeviceUtil.e(context);
    }

    public String d(Context context) throws OnMainThreadException {
        if (com.xiaomi.onetrack.util.y.a()) {
            throw new OnMainThreadException("Can't call this method on main thread");
        }
        return DeviceUtil.f(context);
    }

    public void e() {
        DeviceUtil.e();
    }

    public boolean f() {
        return DeviceUtil.f();
    }

    public void a(ServiceQualityEvent serviceQualityEvent) {
        if (serviceQualityEvent == null) {
            return;
        }
        c.execute(new s(this, serviceQualityEvent));
    }

    public void a(boolean z) {
        com.xiaomi.onetrack.util.r.a = z;
    }

    private void m() {
        c.execute(new t(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        if (com.xiaomi.onetrack.c.j.d()) {
            c.execute(new u(this));
        }
    }

    protected void g() {
        try {
            if (!h() && this.f.getMode() == OneTrack.Mode.APP) {
                long jD = com.xiaomi.onetrack.f.a.d();
                String strA = a(jD, com.xiaomi.onetrack.f.a.c());
                String strA2 = com.xiaomi.onetrack.util.ac.A();
                if (TextUtils.isEmpty(strA2)) {
                    com.xiaomi.onetrack.util.ac.j(strA);
                    return;
                }
                JSONObject jSONObject = new JSONObject(strA2);
                long jOptLong = jSONObject.optLong(ah.W);
                String strOptString = jSONObject.optString(ah.X);
                if (jOptLong != jD) {
                    com.xiaomi.onetrack.util.ac.j(strA);
                    i().a(ah.j, ai.a(jOptLong, strOptString, jD, com.xiaomi.onetrack.f.a.f(), this.f, this.h, this.i, this.j));
                }
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(k, "trackUpgradeEvent error: " + e.toString());
        }
    }

    private String a(long j, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ah.X, str);
        jSONObject.put(ah.W, j);
        return jSONObject.toString();
    }

    public void a(OneTrack.IEventHook iEventHook) {
        this.h = iEventHook;
        this.i.a(iEventHook);
    }

    public void b(boolean z) {
        if (this.f.isUseCustomPrivacyPolicy()) {
            c.execute(new v(this, z));
        }
    }

    public void c(boolean z) {
        this.j = z;
    }

    public void d(boolean z) {
        com.xiaomi.onetrack.util.oaid.a.a().a(z);
    }

    public boolean h() {
        if (!OneTrack.isDisable()) {
            return false;
        }
        com.xiaomi.onetrack.util.r.a(k, "isDisable is true,Not allowed Track");
        return true;
    }

    protected ak i() {
        return this.a;
    }

    public String a(Intent intent) throws OnMainThreadException {
        if (!com.xiaomi.onetrack.util.s.k()) {
            if (com.xiaomi.onetrack.util.y.a()) {
                throw new OnMainThreadException("Can't call this method on main thread");
            }
            if (intent == null || !a.a().c()) {
                com.xiaomi.onetrack.util.r.b(k, "Not allowed to call,intent is null or Not specify the package name");
                return "";
            }
            return a.a().a(intent);
        }
        com.xiaomi.onetrack.util.r.a(k, "this appActiveBroadcast method is not supported in the international version");
        return "";
    }

    protected boolean j() {
        if (com.xiaomi.onetrack.util.r.a) {
            com.xiaomi.onetrack.util.r.a(k, "enable:" + k() + " isSupportEmptyEvent: " + o() + "_isSupportAdMonitor():" + p());
        }
        return k() && o() && p();
    }

    protected boolean k() {
        try {
            int componentEnabledSetting = com.xiaomi.onetrack.f.a.b().getPackageManager().getComponentEnabledSetting(new ComponentName(az.a, az.b));
            return componentEnabledSetting == 1 || componentEnabledSetting == 0;
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(k, "enable error:" + e.toString());
            return false;
        }
    }

    private static boolean o() {
        try {
            int i = com.xiaomi.onetrack.f.a.b().getPackageManager().getPackageInfo(az.a, 0).versionCode;
            if (i >= 2020062900) {
                return true;
            }
            com.xiaomi.onetrack.util.r.a(k, "system analytics version: " + i);
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.r.b(k, "isSupportEmptyEvent error:" + th.getMessage());
        }
        return false;
    }

    private boolean p() {
        try {
            if (TextUtils.isEmpty(this.f.getAdEventAppId()) || OneTrack.isUseSystemNetTrafficOnly()) {
                return true;
            }
            int i = com.xiaomi.onetrack.f.a.b().getPackageManager().getPackageInfo(az.a, 0).versionCode;
            com.xiaomi.onetrack.util.r.a(k, "system analytics version: " + i);
            return i >= 2022042900;
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.r.b(k, "isSupportAdMonitor error:" + th.getMessage());
            return true;
        }
    }
}
