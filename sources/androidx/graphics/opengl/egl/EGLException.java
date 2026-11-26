package androidx.graphics.opengl.egl;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EGLSpec.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\f¨\u0006\u000f"}, d2 = {"Landroidx/graphics/opengl/egl/EGLException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", SNSAuthProvider.VALUE_SNS_ERROR, "", "msg", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILjava/lang/String;)V", "getError", "()I", "getMsg", "()Ljava/lang/String;", "message", "getMessage", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EGLException extends RuntimeException {
    private final int error;
    private final String msg;

    public EGLException(int i, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        this.error = i;
        this.msg = msg;
    }

    public /* synthetic */ EGLException(int i, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? "" : str);
    }

    public final int getError() {
        return this.error;
    }

    public final String getMsg() {
        return this.msg;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return "Error: " + EGLSpec.INSTANCE.getStatusString(this.error) + ", " + this.msg;
    }
}
