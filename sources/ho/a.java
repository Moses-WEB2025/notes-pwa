package ho;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.dist.handoff.sdk.HandoffSession;
import com.xiaomi.dist.handoff.sdk.callback.HandoffCallback;

/* JADX INFO: loaded from: classes4.dex */
public final class a {
    public c a;
    public final Handler b;

    public a(final HandoffSession handoffSession) {
        Handler handlerA = b.b().a();
        this.b = handlerA;
        handlerA.post(new Runnable() { // from class: ho.a$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(handoffSession);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Intent intent) {
        c cVar = this.a;
        if (cVar != null) {
            cVar.a(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(HandoffSession handoffSession) {
        Activity activity = handoffSession.getActivity();
        this.a = (activity == null || !e.b(activity)) ? null : new c(handoffSession);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        c cVar = this.a;
        if (cVar != null) {
            cVar.b();
        }
    }

    public final void a() {
        this.b.post(new Runnable() { // from class: ho.a$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.b();
            }
        });
    }

    public final void b(final Intent intent) {
        this.b.post(new Runnable() { // from class: ho.a$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(intent);
            }
        });
    }

    public final void b(final HandoffCallback handoffCallback) {
        this.b.post(new Runnable() { // from class: ho.a$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(handoffCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(HandoffCallback handoffCallback) {
        Activity activity;
        c cVar = this.a;
        if (cVar == null || (activity = cVar.e.getActivity()) == null) {
            return;
        }
        if (activity instanceof AppCompatActivity) {
            cVar.a(handoffCallback, (AppCompatActivity) activity);
        } else {
            cVar.a(handoffCallback, activity);
        }
    }
}
