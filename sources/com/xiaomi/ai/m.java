package com.xiaomi.ai;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaomi.ai.mibrain.MibrainOauthManager;
import com.xiaomi.ai.mibrain.MibrainOauthManagerCallbacks;
import com.xiaomi.ai.mibrain.MibrainUtils;
import com.xiaomi.ai.utils.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class m {
    private static String a = "OauthPreference_xml";
    private static String b = "MiAiOauthHelper";
    private MiAiOauthCallbacks c;
    private Context d;
    private MibrainOauthManager e;
    private volatile int f;
    private boolean g;
    private boolean h;
    private final Object i = new Object();
    private MibrainOauthManagerCallbacks j = new n(this);

    public static class a {
        public String a;
        public int b;
    }

    m(Context context, String str, String str2, String str3) {
        this.d = context;
        this.e = new MibrainOauthManager(this.j, str, str2, str3, b(a(context)));
    }

    public static String a(Context context) {
        return com.xiaomi.ai.utils.n.b(context);
    }

    public static String b(String str) {
        return MibrainUtils.base64(String.format("{\"d\":\"%s\"}", str).getBytes());
    }

    private void h() {
        synchronized (this.i) {
            this.f++;
        }
    }

    private void i() {
        synchronized (this.i) {
            this.f--;
        }
    }

    private boolean j() {
        synchronized (this.i) {
            if (!this.g) {
                if (this.f >= 0) {
                    return false;
                }
                this.e.release();
                this.g = true;
                Log.w(b, "release MiAiOauthHelper ");
            }
            return true;
        }
    }

    public int a(String str, String str2, String str3, int i) {
        return this.e.setAnonymousAuth(str, str2, str3, i);
    }

    public int a(String str, String str2, String str3, String str4, String str5, String str6) {
        return this.e.updateAnonymousArgs(str, str2, str3, str4, str5, str6);
    }

    public a a(boolean z) {
        a aVar = new a();
        synchronized (this.i) {
            if (!this.h) {
                throw new IllegalStateException("MiAiOauthHelper is not init !");
            }
            h();
            if (j()) {
                return null;
            }
            synchronized (m.class) {
                MibrainOauthManager.AccessTokenResult accessToken = this.e.getAccessToken(z);
                aVar.b = accessToken.errorCode;
                aVar.a = accessToken.accessToken;
            }
            synchronized (this.i) {
                i();
                if (j()) {
                    return null;
                }
                return aVar;
            }
        }
    }

    public String a() {
        return this.d.getSharedPreferences(a, 0).getString(b(a(this.d)), null);
    }

    public String a(String str) {
        if (str == null) {
            return null;
        }
        return this.d.getSharedPreferences(a, 0).getString(String.format("%s:%s", b(a(this.d)), str), null);
    }

    public String a(String str, String str2, String str3) {
        return this.e.genAnonymousAuthorization(str, str2, str3);
    }

    public void a(int i) {
        this.e.setOauthPt(i);
    }

    public void a(MiAiOauthCallbacks miAiOauthCallbacks) {
        this.c = miAiOauthCallbacks;
    }

    public void a(MibrainOauthManager.updateTokenCallback updatetokencallback) {
        this.e.setUpdateTokenCallback(updatetokencallback);
    }

    public synchronized boolean a(int i, String str, String str2, String str3, long j) {
        if (str2 == null || str3 == null || j < 0) {
            Log.e(b, "setAuthorizationTokens: invalid parameters\n");
            return false;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("accessToken", str2);
            jSONObject.put("refreshToken", str3);
            jSONObject.put("upTime", Long.toString(j + (System.currentTimeMillis() / 1000)));
            String str4 = b(a(this.d)) + ":" + str;
            if (i != SpeechEngine.ENGINE_AUTH_ANONYMOUS_PROXY && i != SpeechEngine.ENGINE_AUTH_ANONYMOUS) {
                this.j.putOauthData(b(a(this.d)), jSONObject.toString());
            } else {
                if (str == null) {
                    Log.e(b, "setAuthorizationTokens: clientId is null\n");
                    return false;
                }
                this.j.putOauthData(str4, jSONObject.toString());
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void b() {
        synchronized (this.i) {
            this.e.init();
            this.h = true;
            Log.w(b, "init MiAiOauthHelper ");
        }
    }

    public void b(int i) {
        SharedPreferences sharedPreferences = this.d.getSharedPreferences(a, 0);
        if (sharedPreferences.getInt("env", 0) != i) {
            d();
            sharedPreferences.edit().putInt("env", i).apply();
        }
        this.e.setOauthEnv(i);
        android.util.Log.e(b, "setEnv" + i);
    }

    public void c() {
        synchronized (this.i) {
            i();
            j();
        }
    }

    public void c(int i) {
        this.e.setNetTimeOut(i);
    }

    public void c(String str) {
        if (str == null) {
            return;
        }
        Log.MiLogD(b, "cleanOauthData clientId " + str);
        String strB = b(a(this.d));
        SharedPreferences.Editor editorEdit = this.d.getSharedPreferences(a, 0).edit();
        editorEdit.remove(String.format("%s:%s", strB, str));
        editorEdit.apply();
    }

    public void d() {
        Log.MiLogD(b, "cleanOauthData");
        String strB = b(a(this.d));
        SharedPreferences.Editor editorEdit = this.d.getSharedPreferences(a, 0).edit();
        editorEdit.remove(strB);
        editorEdit.apply();
    }

    public void e() {
        Log.MiLogD(b, "cleanAllOauthData");
        SharedPreferences.Editor editorEdit = this.d.getSharedPreferences(a, 0).edit();
        editorEdit.clear();
        editorEdit.apply();
    }
}
