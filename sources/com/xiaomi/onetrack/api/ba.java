package com.xiaomi.onetrack.api;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ba {
    public static final String a = "name";
    public static final String b = "gender";
    public static final String c = "birthday";
    public static final String d = "phone";
    public static final String e = "job";
    public static final String f = "hobby";
    public static final String g = "region";
    public static final String h = "province";
    public static final String i = "city";
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;

    private ba() {
    }

    public String a() {
        return this.j;
    }

    public String b() {
        return this.k;
    }

    public String c() {
        return this.l;
    }

    public String d() {
        return this.m;
    }

    public String e() {
        return this.n;
    }

    public String f() {
        return this.o;
    }

    public String g() {
        return this.p;
    }

    public String h() {
        return this.q;
    }

    public String i() {
        return this.r;
    }

    public JSONObject j() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", this.j);
            jSONObject.put(b, this.k);
            jSONObject.put(c, this.l);
            jSONObject.put("phone", this.m);
            jSONObject.put(e, this.n);
            jSONObject.put(f, this.o);
            jSONObject.put("region", this.p);
            jSONObject.put(h, this.q);
            jSONObject.put(i, this.r);
            return jSONObject;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return jSONObject;
        }
    }

    public String toString() {
        return j().toString();
    }

    public static final class a {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;
        private String g;
        private String h;
        private String i;

        public a a(String str) {
            this.a = str;
            return this;
        }

        public a b(String str) {
            this.b = str;
            return this;
        }

        public a c(String str) {
            this.c = str;
            return this;
        }

        public a d(String str) {
            this.d = str;
            return this;
        }

        public a e(String str) {
            this.e = str;
            return this;
        }

        public a f(String str) {
            this.f = str;
            return this;
        }

        public a g(String str) {
            this.g = str;
            return this;
        }

        public a h(String str) {
            this.h = str;
            return this;
        }

        public a i(String str) {
            this.i = str;
            return this;
        }

        public ba a() {
            ba baVar = new ba();
            baVar.o = this.f;
            baVar.n = this.e;
            baVar.r = this.i;
            baVar.m = this.d;
            baVar.q = this.h;
            baVar.l = this.c;
            baVar.j = this.a;
            baVar.p = this.g;
            baVar.k = this.b;
            return baVar;
        }
    }
}
