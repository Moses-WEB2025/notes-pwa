package ho;

import android.R;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.MultiWindowModeChangedInfo;
import androidx.core.app.PictureInPictureModeChangedInfo;
import androidx.core.util.Consumer;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.dist.handoff.sdk.HandoffSession;
import com.xiaomi.dist.handoff.sdk.callback.HandoffCallback;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public final class c {
    public final f b;
    public final C0137c d;
    public final HandoffSession e;
    public final e f;
    public volatile ho.f l;
    public CountDownLatch m;
    public HandoffCallback n;
    public View p;
    public final d q;
    public b c = new b(null);
    public final Consumer<Intent> g = new Consumer() { // from class: ho.c$$ExternalSyntheticLambda2
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            this.f$0.a((Intent) obj);
        }
    };
    public Boolean h = null;
    public final Consumer<PictureInPictureModeChangedInfo> i = new Consumer() { // from class: ho.c$$ExternalSyntheticLambda3
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            this.f$0.a((PictureInPictureModeChangedInfo) obj);
        }
    };
    public int j = 0;
    public boolean k = true;
    public final Consumer<MultiWindowModeChangedInfo> o = new Consumer() { // from class: ho.c$$ExternalSyntheticLambda4
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            this.f$0.a((MultiWindowModeChangedInfo) obj);
        }
    };
    public Boolean r = null;
    public final Handler a = ho.b.b().a();

    public static final class b extends g.a {
        public final WeakReference<HandoffCallback> a;

        public b(HandoffCallback handoffCallback) {
            this.a = new WeakReference<>(handoffCallback);
        }
    }

    /* JADX INFO: renamed from: ho.c$c, reason: collision with other inner class name */
    public final class C0137c extends i {
        public C0137c() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(ho.f fVar) {
            c.this.a(fVar, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(ho.f fVar) {
            c.this.a(fVar, true);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityDestroyed(Activity activity) {
            j.a("HH", "onActivityDestroyed, act class name=%s, hash=%s", activity.getLocalClassName(), Integer.valueOf(activity.hashCode()));
            c.this.k = false;
            c.this.b();
            c.this.e.clearActivity();
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityPaused(Activity activity) {
            j.a("HH", "onActivityPaused, act class name=%s, hash=%s", activity.getLocalClassName(), Integer.valueOf(activity.hashCode()));
            c.this.k = false;
            c.this.a(new java.util.function.Consumer() { // from class: ho.c$c$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((f) obj);
                }
            }, (java.util.function.Consumer<Void>) null);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityResumed(Activity activity) {
            j.a("HH", "onActivityResumed, act class name=%s, hash=%s", activity.getLocalClassName(), Integer.valueOf(activity.hashCode()));
            c.this.k = true;
            c.this.a(new java.util.function.Consumer() { // from class: ho.c$c$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((f) obj);
                }
            }, (java.util.function.Consumer<Void>) null);
        }
    }

    public final class d implements View.OnLayoutChangeListener {
        public d() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(ho.f fVar) {
            try {
                fVar.a(c.this.r.booleanValue());
            } catch (Throwable th) {
                j.a("HH", "setInMultiWindowMode exception", th);
            }
        }

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            Activity activity = c.this.e.getActivity();
            if (activity == null) {
                return;
            }
            boolean zIsInMultiWindowMode = activity.isInMultiWindowMode();
            Boolean bool = c.this.r;
            if (bool == null || bool.booleanValue() != zIsInMultiWindowMode) {
                j.a("HH", "onLayoutChange, isSmallWindowState=%s", Boolean.valueOf(zIsInMultiWindowMode));
                c.this.r = Boolean.valueOf(zIsInMultiWindowMode);
                c.this.a(new java.util.function.Consumer() { // from class: ho.c$d$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((f) obj);
                    }
                }, (java.util.function.Consumer<Void>) null);
            }
        }
    }

    public final class e implements ServiceConnection {
        public e() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, ho.f fVar) {
            boolean z = i == c.this.j;
            Object[] objArr = {Boolean.valueOf(z)};
            try {
                j.a("HH", String.format("UpdateSessionState %s when service connected.", objArr));
            } catch (IllegalFormatException unused) {
                j.a("HH", "UpdateSessionState %s when service connected." + Arrays.toString(objArr));
            }
            if (z) {
                c.this.a(fVar);
                c cVar = c.this;
                cVar.a(fVar, cVar.k);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ho.f c0138a;
            j.a("HH", "onServiceConnected", (Object[]) null);
            c cVar = c.this;
            int i = ho.f.a.a;
            if (iBinder == null) {
                c0138a = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.dist.handoff.IAppHandoffService");
                c0138a = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ho.f)) ? new ho.f.a.C0138a(iBinder) : (ho.f) iInterfaceQueryLocalInterface;
            }
            cVar.l = c0138a;
            try {
                CountDownLatch countDownLatch = c.this.m;
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            } catch (Throwable unused) {
            }
            j.a("HH", "restoreState");
            c cVar2 = c.this;
            final int i2 = cVar2.j;
            cVar2.a(new java.util.function.Consumer() { // from class: ho.c$e$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(i2, (f) obj);
                }
            }, (java.util.function.Consumer<Void>) null);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            j.a("HH", "onServiceDisconnected");
            c.this.l = null;
            c.this.a((java.util.function.Consumer<ho.f>) null, (java.util.function.Consumer<Void>) null);
        }
    }

    public static final class f extends h.a {
        public final WeakReference<HandoffSession> a;

        public f(HandoffSession handoffSession) {
            this.a = new WeakReference<>(handoffSession);
        }

        public final boolean a() {
            HandoffSession handoffSession = this.a.get();
            boolean z = (handoffSession == null || handoffSession.e == null) ? false : true;
            j.a("HH", "hasDeeplinkCallBack %s", Boolean.valueOf(z));
            return z;
        }
    }

    public c(HandoffSession handoffSession) {
        this.d = new C0137c();
        this.f = new e();
        this.q = new d();
        this.e = handoffSession;
        this.b = new f(handoffSession);
        c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, ho.f fVar) {
        a(fVar, true, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ho.f fVar) {
        try {
            fVar.a(this.h.booleanValue());
        } catch (Throwable th) {
            j.a("HH", "setInMultiWindowMode exception", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(java.util.function.Consumer consumer, java.util.function.Consumer consumer2) {
        try {
            ho.f fVar = this.l;
            if (fVar == null || !fVar.asBinder().isBinderAlive()) {
                i();
                a();
            }
            ho.f fVar2 = this.l;
            if (fVar2 != null) {
                if (consumer2 != null) {
                    consumer2.accept(fVar2);
                }
            } else {
                j.a("HH", "bind fail, handoff service unavailable", (Throwable) null);
                if (consumer != null) {
                    consumer.accept(null);
                }
            }
        } catch (Throwable th) {
            j.a("HH", "handle task error.", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(ho.f fVar) {
        a(fVar, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(ho.f fVar) {
        a(fVar, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        a(this.l);
        i();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        if (Thread.interrupted()) {
            j.a("clear interrupted state", (Object[]) null);
        }
        if (this.j > 0) {
            a((java.util.function.Consumer<ho.f>) null, new java.util.function.Consumer() { // from class: ho.c$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    j.a("HH", "connect HandoffService fail");
                }
            });
        }
        this.a.post(new Runnable() { // from class: ho.c$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.e();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g() {
        a(this.l);
        i();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        if (Thread.interrupted()) {
            j.a("clear interrupted state", (Object[]) null);
        }
        if (this.j > 0) {
            a((java.util.function.Consumer<ho.f>) null, new java.util.function.Consumer() { // from class: ho.c$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    j.a("HH", "connect HandoffService fail");
                }
            });
        }
        this.a.post(new Runnable() { // from class: ho.c$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.g();
            }
        });
    }

    public final void a() {
        j.a("bind Handoff Service", (Object[]) null);
        Application application = this.e.getApplication();
        if (application == null) {
            return;
        }
        if (!ho.e.b(application)) {
            j.a("HH", "bindService fail, not support");
            return;
        }
        String strA = ho.e.a(application);
        if (TextUtils.isEmpty(strA)) {
            Log.e(j.a("HU"), "service package not found!", null);
            strA = null;
        } else {
            j.a("HU", "get ho service pkg name= %s", strA);
        }
        if (TextUtils.isEmpty(strA)) {
            j.a("HH", "bindService fail, service package name empty");
            return;
        }
        for (int i = 0; i < 3; i++) {
            this.m = new CountDownLatch(1);
            try {
                if (Thread.interrupted()) {
                    j.a("HH", "bind service interrupted");
                    return;
                }
                j.a("HH", "start bind ho service", (Object[]) null);
                ho.f fVar = this.l;
                if (fVar != null && fVar.asBinder().isBinderAlive()) {
                    j.a("HH", "bind ho service, already bind", (Object[]) null);
                    return;
                }
                TimeUnit timeUnit = TimeUnit.SECONDS;
                Thread.sleep(timeUnit.toMillis(((long) i) * 2));
                Intent intent = new Intent();
                intent.setAction("com.xiaomi.dist.intent.action.APP_HANDOFF");
                intent.setPackage(strA);
                application.bindService(intent, this.f, 1);
                this.m.await(2L, timeUnit);
            } catch (InterruptedException e2) {
                Log.e(j.a("HH"), "bind service interrupted", e2);
                return;
            } catch (Throwable th) {
                Log.e(j.a("HH"), "bind service fail", th);
            }
        }
    }

    public final void a(MultiWindowModeChangedInfo multiWindowModeChangedInfo) {
        d();
    }

    public final void a(PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo) {
        d();
    }

    public final void a(ho.f fVar, boolean z) {
        Activity activity = this.e.getActivity();
        if (activity == null) {
            return;
        }
        Intent intent = activity.getIntent();
        if (intent == null) {
            j.a("HH", "handleUpdateSessionState intent is null");
        } else {
            a(fVar, z, intent.getIntExtra("com.xiaomi.dist.intent.extra.REF_TAG", 0));
        }
    }

    public final void a(final java.util.function.Consumer<ho.f> consumer, final java.util.function.Consumer<Void> consumer2) {
        this.a.post(new Runnable() { // from class: ho.c$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.b(consumer2, consumer);
            }
        });
    }

    public final void b() {
        Activity activity = this.e.getActivity();
        if (activity == null) {
            return;
        }
        if (activity instanceof AppCompatActivity) {
            a((AppCompatActivity) activity);
        } else {
            a(activity);
        }
    }

    public final void c() {
        a((java.util.function.Consumer<ho.f>) null, (java.util.function.Consumer<Void>) null);
    }

    public final void d() {
        Activity activity = this.e.getActivity();
        if (activity == null) {
            return;
        }
        boolean z = activity.isInMultiWindowMode() || activity.isInPictureInPictureMode();
        Boolean bool = this.h;
        if (bool == null || bool.booleanValue() != z) {
            j.a("HH", "handleMultiWindowAndPictureInPictureMode, isMultiWindowOrPicInPicState=%s", Boolean.valueOf(z));
            this.h = Boolean.valueOf(z);
            a(new java.util.function.Consumer() { // from class: ho.c$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((f) obj);
                }
            }, (java.util.function.Consumer<Void>) null);
        }
    }

    public final void i() {
        j.a("unbind Handoff Service", (Object[]) null);
        Application application = this.e.getApplication();
        if (application == null) {
            return;
        }
        try {
            application.unbindService(this.f);
        } catch (Throwable unused) {
        }
    }

    public final void a(Activity activity) {
        j.a("HH", ToDoStat.CANCEL, (Object[]) null);
        View view = this.p;
        if (view != null) {
            view.removeOnLayoutChangeListener(this.q);
            this.p = null;
        }
        activity.unregisterActivityLifecycleCallbacks(this.d);
        this.a.removeCallbacksAndMessages(null);
        try {
            this.a.getLooper().getThread().interrupt();
        } catch (Throwable unused) {
        }
        this.a.post(new Runnable() { // from class: ho.c$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.f();
            }
        });
    }

    public final void a(AppCompatActivity appCompatActivity) {
        j.a("HH", ToDoStat.CANCEL, (Object[]) null);
        appCompatActivity.removeOnPictureInPictureModeChangedListener(this.i);
        appCompatActivity.removeOnMultiWindowModeChangedListener(this.o);
        appCompatActivity.removeOnNewIntentListener(this.g);
        appCompatActivity.unregisterActivityLifecycleCallbacks(this.d);
        this.a.removeCallbacksAndMessages(null);
        this.h = Boolean.FALSE;
        try {
            this.a.getLooper().getThread().interrupt();
        } catch (Throwable unused) {
        }
        this.a.post(new Runnable() { // from class: ho.c$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.h();
            }
        });
    }

    public final void a(ho.f fVar) {
        try {
            int i = this.j;
            j.a("HH", "call cancelHandoffSession, sessionId %s", Integer.valueOf(i));
            if (i > 0 && fVar != null) {
                fVar.a(i);
                j.a("HH", "call cancelHandoffSession done", (Object[]) null);
            }
            this.j = 0;
        } catch (Throwable th) {
            try {
                j.a("HH", "cancelHandoffSession exception", th);
            } finally {
                this.j = 0;
            }
        }
    }

    public final void a(ho.f fVar, boolean z, int i) {
        Activity activity = this.e.getActivity();
        if (activity == null) {
            return;
        }
        try {
            j.a("HH", "handleUpdateSessionActive, active=%s, refTag=%s", Boolean.valueOf(z), Integer.valueOf(i));
            if (this.j <= 0) {
                j.a("HH", "publish handoff session", (Object[]) null);
                ho.d dVar = new ho.d(activity.getClass().getName().hashCode(), activity.getTaskId(), activity.getClass().getName(), activity.getPackageName());
                j.a("getSessionInfo, info=%s", dVar);
                this.j = fVar.a(dVar, this.b, this.c, i);
            }
            fVar.a(this.j, !z ? 1 : 0, i);
        } catch (Throwable th) {
            Log.e(j.a("HH"), "handleUpdateSessionState exception", th);
        }
    }

    public final void a(Intent intent) {
        if (intent == null) {
            j.a("HH", "onNewIntent, intent is null, skip", (Object[]) null);
            return;
        }
        final int intExtra = intent.getIntExtra("com.xiaomi.dist.intent.extra.REF_TAG", 0);
        j.a("HH", "onNewIntent, ref %s", Integer.valueOf(intExtra));
        a(new java.util.function.Consumer() { // from class: ho.c$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(intExtra, (f) obj);
            }
        }, (java.util.function.Consumer<Void>) null);
    }

    public final void a(HandoffCallback handoffCallback, Activity activity) {
        j.a("HH", "publish", (Object[]) null);
        HandoffCallback handoffCallback2 = this.n;
        this.n = handoffCallback;
        this.c = new b(handoffCallback);
        if (handoffCallback2 == null) {
            activity.registerActivityLifecycleCallbacks(this.d);
            View viewFindViewById = activity.findViewById(R.id.content);
            this.p = viewFindViewById;
            if (viewFindViewById != null) {
                viewFindViewById.addOnLayoutChangeListener(this.q);
            }
        }
        a(new java.util.function.Consumer() { // from class: ho.c$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((f) obj);
            }
        }, (java.util.function.Consumer<Void>) null);
    }

    public final void a(HandoffCallback handoffCallback, AppCompatActivity appCompatActivity) {
        j.a("HH", "publish", (Object[]) null);
        HandoffCallback handoffCallback2 = this.n;
        this.n = handoffCallback;
        this.c = new b(handoffCallback);
        if (handoffCallback2 == null) {
            appCompatActivity.registerActivityLifecycleCallbacks(this.d);
            appCompatActivity.addOnNewIntentListener(this.g);
            appCompatActivity.addOnPictureInPictureModeChangedListener(this.i);
            appCompatActivity.addOnMultiWindowModeChangedListener(this.o);
        }
        a(new java.util.function.Consumer() { // from class: ho.c$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d((f) obj);
            }
        }, (java.util.function.Consumer<Void>) null);
    }
}
