package xc.engine.utils;

import android.os.Build;
import android.util.Log;
import android.view.AttachedSurfaceControl;
import android.view.Display;
import android.view.View;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.data.VariableNames;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.cbridge.SStore;

/* JADX INFO: compiled from: TransformHintInfoHelper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Lxc/engine/utils/TransformHintInfoHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TransformHintInfoHelper {
    public static final int BUFFER_TRANSFORM_IDENTITY = 0;
    public static final int BUFFER_TRANSFORM_MIRROR_HORIZONTAL = 1;
    public static final int BUFFER_TRANSFORM_MIRROR_VERTICAL = 2;
    public static final int BUFFER_TRANSFORM_ROTATE_180 = 3;
    public static final int BUFFER_TRANSFORM_ROTATE_270 = 7;
    public static final int BUFFER_TRANSFORM_ROTATE_90 = 4;
    public static final String ORIENTATION_0 = "ORIENTATION_0";
    public static final String ORIENTATION_180 = "ORIENTATION_180";
    public static final String ORIENTATION_270 = "ORIENTATION_270";
    public static final String ORIENTATION_90 = "ORIENTATION_90";
    public static final int UNKNOWN_TRANSFORM_HINT = -1;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "TransformHintInfoHelper";

    @JvmStatic
    public static final int getBufferTransformHint(View view) {
        return INSTANCE.getBufferTransformHint(view);
    }

    @JvmStatic
    public static final String getBufferTransformLabel(int i) {
        return INSTANCE.getBufferTransformLabel(i);
    }

    @JvmStatic
    private static final int obtainBufferTransformHint(View view) {
        return INSTANCE.obtainBufferTransformHint(view);
    }

    @JvmStatic
    public static final float resolveBufferTransformRotate(int i) {
        return INSTANCE.resolveBufferTransformRotate(i);
    }

    private TransformHintInfoHelper() {
    }

    /* JADX INFO: compiled from: TransformHintInfoHelper.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0003J\f\u0010\u0016\u001a\u00020\u0005*\u00020\bH\u0007J\f\u0010\u0017\u001a\u00020\u0018*\u00020\bH\u0007J\u0010\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0018\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\bH\u0002R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lxc/engine/utils/TransformHintInfoHelper$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "kotlin.jvm.PlatformType", "BUFFER_TRANSFORM_IDENTITY", "", "BUFFER_TRANSFORM_MIRROR_HORIZONTAL", "BUFFER_TRANSFORM_MIRROR_VERTICAL", "BUFFER_TRANSFORM_ROTATE_180", "BUFFER_TRANSFORM_ROTATE_90", "BUFFER_TRANSFORM_ROTATE_270", "UNKNOWN_TRANSFORM_HINT", "ORIENTATION_0", "ORIENTATION_90", "ORIENTATION_180", "ORIENTATION_270", "obtainBufferTransformHint", "view", "Landroid/view/View;", "getBufferTransformLabel", "resolveBufferTransformRotate", "", "getBufferTransformHint", "getBufferTransformHintFromInstallOrientation", VariableNames.ORIENTATION, "rotation", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final float resolveBufferTransformRotate(int i) {
            if (i == 0) {
                return 0.0f;
            }
            if (i == 7) {
                return 270.0f;
            }
            if (i != 3) {
                return i != 4 ? 0.0f : 90.0f;
            }
            return 180.0f;
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        @JvmStatic
        public final int obtainBufferTransformHint(View view) {
            AttachedSurfaceControl rootSurfaceControl = view.getRootSurfaceControl();
            if (rootSurfaceControl != null) {
                return rootSurfaceControl.getBufferTransformHint();
            }
            return 0;
        }

        @JvmStatic
        public final String getBufferTransformLabel(int i) {
            if (i == 0) {
                return "BUFFER_TRANSFORM_IDENTITY";
            }
            if (i == 1) {
                return "BUFFER_TRANSFORM_MIRROR_HORIZONTAL";
            }
            if (i == 2) {
                return "BUFFER_TRANSFORM_MIRROR_VERTICAL";
            }
            if (i == 3) {
                return "BUFFER_TRANSFORM_ROTATE_180";
            }
            if (i == 4) {
                return "BUFFER_TRANSFORM_ROTATE_90";
            }
            if (i == 7) {
                return "BUFFER_TRANSFORM_ROTATE_270";
            }
            return "UNKNOWN_TRANSFORM_HINT";
        }

        @JvmStatic
        public final int getBufferTransformHint(View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            if (Build.VERSION.SDK_INT >= 32) {
                return obtainBufferTransformHint(view);
            }
            try {
                String strNGetDisplayOrientation = SStore.INSTANCE.nGetDisplayOrientation();
                Display display = view.getDisplay();
                Integer numValueOf = display != null ? Integer.valueOf(display.getRotation()) : null;
                if (numValueOf == null) {
                    Log.w(TransformHintInfoHelper.TAG, "Unable to obtain current display rotation");
                    return -1;
                }
                int bufferTransformHintFromInstallOrientation = getBufferTransformHintFromInstallOrientation(strNGetDisplayOrientation, numValueOf.intValue());
                Log.d(TransformHintInfoHelper.TAG, "Obtained transform: " + bufferTransformHintFromInstallOrientation + " for orientation: " + strNGetDisplayOrientation);
                return bufferTransformHintFromInstallOrientation;
            } catch (Exception unused) {
                Log.w(TransformHintInfoHelper.TAG, "Unable to obtain current display orientation");
                return -1;
            }
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        private final int getBufferTransformHintFromInstallOrientation(String orientation, int rotation) {
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
            Log.w(TransformHintInfoHelper.TAG, "Unknown orientation \"" + orientation + "\"");
            return -1;
        }
    }
}
