package androidx.graphics.lowlatency;

import android.graphics.Matrix;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import androidx.graphics.surface.JniBindings;
import androidx.graphics.surface.SurfaceControlCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.maml.data.VariableNames;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BufferTransformHintResolver.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0000\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u001d\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\f¨\u0006\u000e"}, d2 = {"Landroidx/graphics/lowlatency/BufferTransformHintResolver;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getBufferTransformHint", "", "view", "Landroid/view/View;", "getBufferTransformHintFromInstallOrientation", VariableNames.ORIENTATION, "", "rotation", "getBufferTransformHintFromInstallOrientation$libXCEngine_release", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BufferTransformHintResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String ORIENTATION_0 = "ORIENTATION_0";
    public static final String ORIENTATION_180 = "ORIENTATION_180";
    public static final String ORIENTATION_270 = "ORIENTATION_270";
    public static final String ORIENTATION_90 = "ORIENTATION_90";
    public static final String TAG = "TRANSFORM_HINT_RESOLVER";
    public static final int UNKNOWN_TRANSFORM = -1;

    public final int getBufferTransformHint(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (Build.VERSION.SDK_INT >= 32) {
            return TransformHintHelper.INSTANCE.resolveBufferTransformHint(view);
        }
        try {
            String strNGetDisplayOrientation = JniBindings.INSTANCE.nGetDisplayOrientation();
            Display display = view.getDisplay();
            Integer numValueOf = display != null ? Integer.valueOf(display.getRotation()) : null;
            if (numValueOf != null) {
                int bufferTransformHintFromInstallOrientation$libXCEngine_release = getBufferTransformHintFromInstallOrientation$libXCEngine_release(strNGetDisplayOrientation, numValueOf.intValue());
                Log.v(TAG, "Obtained transform: " + bufferTransformHintFromInstallOrientation$libXCEngine_release + " for orientation: " + strNGetDisplayOrientation);
                return bufferTransformHintFromInstallOrientation$libXCEngine_release;
            }
            Log.w(TAG, "Unable to obtain current display rotation");
            return -1;
        } catch (Exception unused) {
            Log.w(TAG, "Unable to obtain current display orientation");
            return -1;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final int getBufferTransformHintFromInstallOrientation$libXCEngine_release(String orientation, int rotation) {
        Intrinsics.checkNotNullParameter(orientation, "orientation");
        switch (orientation.hashCode()) {
            case -1559565983:
                if (orientation.equals("ORIENTATION_0")) {
                    if (rotation == 0) {
                        return 0;
                    }
                    if (rotation == 1) {
                        return 4;
                    }
                    if (rotation != 2) {
                        return rotation != 3 ? -1 : 7;
                    }
                    return 3;
                }
                break;
            case -1101904890:
                if (orientation.equals("ORIENTATION_90")) {
                    if (rotation == 0) {
                        return 4;
                    }
                    if (rotation == 1) {
                        return 3;
                    }
                    if (rotation != 2) {
                        return rotation != 3 ? -1 : 0;
                    }
                    return 7;
                }
                break;
            case 200679386:
                if (orientation.equals("ORIENTATION_180")) {
                    if (rotation == 0) {
                        return 3;
                    }
                    if (rotation == 1) {
                        return 7;
                    }
                    if (rotation != 2) {
                        return rotation != 3 ? -1 : 4;
                    }
                    return 0;
                }
                break;
            case 200680316:
                if (orientation.equals("ORIENTATION_270")) {
                    if (rotation == 0) {
                        return 7;
                    }
                    if (rotation == 1) {
                        return 0;
                    }
                    if (rotation != 2) {
                        return rotation != 3 ? -1 : 3;
                    }
                    return 4;
                }
                break;
        }
        Log.w(TAG, "Unknown orientation \"" + orientation + "\"");
        return -1;
    }

    /* JADX INFO: compiled from: BufferTransformHintResolver.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0080\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J/\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\b\b\u0001\u0010\u0012\u001a\u00020\u0007H\u0001¢\u0006\u0002\b\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Landroidx/graphics/lowlatency/BufferTransformHintResolver$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "UNKNOWN_TRANSFORM", "", "ORIENTATION_0", "ORIENTATION_90", "ORIENTATION_180", "ORIENTATION_270", "configureTransformMatrix", "Landroid/graphics/Matrix;", "matrix", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "transform", "configureTransformMatrix$libXCEngine_release", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Matrix configureTransformMatrix$libXCEngine_release(Matrix matrix, float width, float height, @SurfaceControlCompat.Companion.BufferTransform int transform) {
            Intrinsics.checkNotNullParameter(matrix, "matrix");
            if (transform == 3) {
                matrix.reset();
                matrix.setRotate(180.0f);
                matrix.postTranslate(width, height);
                return matrix;
            }
            if (transform == 4) {
                matrix.reset();
                matrix.setRotate(90.0f);
                matrix.postTranslate(width, 0.0f);
                return matrix;
            }
            if (transform == 7) {
                matrix.reset();
                matrix.setRotate(270.0f);
                matrix.postTranslate(0.0f, height);
                return matrix;
            }
            matrix.reset();
            return matrix;
        }
    }
}
