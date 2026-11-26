package com.xiaomi.dist.handoff.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.dist.handoff.sdk.callback.DeepLinkCallback;
import com.xiaomi.dist.handoff.sdk.callback.HandoffCallback;
import ho.a;
import ho.j;
import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes4.dex */
public final class HandoffSession {
    public final WeakReference<Activity> a;
    public final Application b;
    public final Supplier<Uri> c;
    public final Supplier<Uri> d;
    public final DeepLinkCallback e;
    public final a f;

    public HandoffSession(Builder builder) {
        this.a = new WeakReference<>(builder.a);
        this.b = builder.a.getApplication();
        this.c = builder.b;
        this.d = builder.c;
        this.e = builder.d;
        int unused = builder.e;
        this.f = new a(this);
    }

    public void cancel() {
        j.a("session", ToDoStat.CANCEL, (Object[]) null);
        this.f.a();
    }

    public void clearActivity() {
        this.a.clear();
    }

    public Activity getActivity() {
        Activity activity = this.a.get();
        if (activity == null) {
            j.a("session", "session activity is null", (Throwable) null);
        }
        return activity;
    }

    public Application getApplication() {
        return this.b;
    }

    public void onNewIntent(Intent intent) {
        j.a("session", "onNewIntent", (Object[]) null);
        this.f.b(intent);
    }

    public void publish(HandoffCallback handoffCallback) {
        j.a("session", "publish", (Object[]) null);
        this.f.b((HandoffCallback) Objects.requireNonNull(handoffCallback));
    }

    public static final class Builder {
        public final Activity a;
        public Supplier<Uri> b = null;
        public Supplier<Uri> c = null;
        public DeepLinkCallback d = null;
        public int e = 0;

        public Builder(Activity activity) {
            this.a = (Activity) Objects.requireNonNull(activity);
        }

        public Builder addFlags(int i) {
            this.e = i | this.e;
            return this;
        }

        public HandoffSession build() {
            if (this.b == null && this.c == null) {
                throw new InvalidParameterException("deeplink or appLink is required");
            }
            return new HandoffSession(this);
        }

        public Builder removeFlags(int i) {
            this.e = (~i) & this.e;
            return this;
        }

        public Builder setAppLink(Supplier<Uri> supplier) {
            j.a("session", "setAppLink", (Object[]) null);
            this.c = supplier;
            return this;
        }

        public Builder setDeepLink(Supplier<Uri> supplier) {
            j.a("session", "setDeepLink without callback", (Object[]) null);
            this.b = supplier;
            return this;
        }

        public Builder setFlags(int i) {
            this.e = i;
            return this;
        }

        public Builder setDeepLink(Supplier<Uri> supplier, DeepLinkCallback deepLinkCallback) {
            j.a("session", "setDeepLink with callback", (Object[]) null);
            this.b = supplier;
            this.d = deepLinkCallback;
            return this;
        }
    }
}
