package com.xiaomi.onetrack.c;

import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class m {
    private long a;
    private String b;
    private String c;
    private String d;
    private Map<String, String> e;
    private long f;

    public m(long j, String str, String str2, String str3, Map<String, String> map, long j2) {
        this.a = j;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = map;
        this.f = j2;
    }

    public boolean a() {
        return (TextUtils.isEmpty(this.b) || TextUtils.isEmpty(this.c) || TextUtils.isEmpty(this.d)) ? false : true;
    }

    public long b() {
        return this.a;
    }

    public void a(long j) {
        this.a = j;
    }

    public String c() {
        return this.b;
    }

    public void a(String str) {
        this.b = str;
    }

    public String d() {
        return this.c;
    }

    public void b(String str) {
        this.c = str;
    }

    public String e() {
        return this.d;
    }

    public void c(String str) {
        this.d = str;
    }

    public Map<String, String> f() {
        return this.e;
    }

    public void a(Map<String, String> map) {
        this.e = map;
    }

    public long g() {
        return this.f;
    }

    public void b(long j) {
        this.f = this.f;
    }

    public String toString() {
        return "PSMessage{mId=" + this.a + ", mProjectID='" + this.b + "', mTopic='" + this.c + "', mData='" + this.d + "', mAttributes=" + this.e + ", mTimestamp=" + this.f + CoreConstants.CURLY_RIGHT;
    }
}
