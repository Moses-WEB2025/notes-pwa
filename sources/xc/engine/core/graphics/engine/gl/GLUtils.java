package xc.engine.core.graphics.engine.gl;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.os.Environment;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.maml.folme.AnimatedProperty;
import defpackage.R2;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import xc.engine.cbridge.SStore;
import xc.engine.core.graphics.opengl.OpenGLShaderUtil;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: GLUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Lxc/engine/core/graphics/engine/gl/GLUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLUtils {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "GLUtils";

    /* JADX INFO: compiled from: GLUtils.kt */
    @Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000eJ\u0016\u0010\u0011\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013J\b\u0010\u0014\u001a\u00020\bH\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\bJ\u0006\u0010\u0018\u001a\u00020\bJ\u0016\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\bJ\u000e\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\bJ\u000e\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\bJ\u000e\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\bJ\u0014\u0010\u001e\u001a\u00020\u00162\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\b0 J\u000e\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\bJ\u000e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&J\u001e\u0010'\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\b2\u0006\u0010(\u001a\u00020\b2\u0006\u0010)\u001a\u00020\bJ\u0010\u0010*\u001a\u00020$2\b\b\u0002\u0010+\u001a\u00020\u0005J\u0016\u0010,\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bJ\u000e\u0010-\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u0013J&\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/2\u0006\u00101\u001a\u00020\u000e2\u0006\u00102\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u000eJ&\u00104\u001a\u0002052\u0006\u00106\u001a\u0002052\u0006\u00101\u001a\u00020\u000e2\u0006\u00102\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u000eR\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lxc/engine/core/graphics/engine/gl/GLUtils$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "kotlin.jvm.PlatformType", "createShaderProgramFromSStore", "", "vert", "frag", "getLimitedTextureSize", "Landroid/graphics/Point;", AnimatedProperty.PROPERTY_NAME_W, "", AnimatedProperty.PROPERTY_NAME_H, "maxSize", "createTexture", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "createGlTexture", "generateMipmap", "", "textureId", "createFramebufferOnly", "bindTextureToFramebuffer", "framebufferId", "createFramebufferFromTexture", "deleteFramebuffer", "deleteTexture", "deleteTextures", "textureIds", "", "deleteGLProgram", "programId", "isGLSyncSignaled", "", "syncHandle", "", "getBitmapFromFramebuffer", "bitmapWidth", "bitmapHeight", "getGLError", "msg", "debugSaveTexture", "debugSaveBitmap", "getRotatedBoundingBox", "Landroid/graphics/RectF;", "rectF", "degrees", "centerX", "centerY", "getRotatedPoint", "Landroid/graphics/PointF;", "point", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int createShaderProgramFromSStore(int vert, int frag) {
            String strS1 = SStore.s1(vert);
            int iCreateShader = OpenGLShaderUtil.createShader(35633, strS1 != null ? StringsKt.trim((CharSequence) strS1).toString() : null);
            String strS2 = SStore.s2(frag);
            int iCreateShader2 = OpenGLShaderUtil.createShader(35632, strS2 != null ? StringsKt.trim((CharSequence) strS2).toString() : null);
            int iCreateProgram = OpenGLShaderUtil.createProgram(iCreateShader, iCreateShader2);
            GLES30.glDeleteShader(iCreateShader);
            GLES30.glDeleteShader(iCreateShader2);
            return iCreateProgram;
        }

        public static /* synthetic */ Point getLimitedTextureSize$default(Companion companion, float f, float f2, float f3, int i, Object obj) {
            if ((i & 4) != 0) {
                f3 = 8192.0f;
            }
            return companion.getLimitedTextureSize(f, f2, f3);
        }

        public final Point getLimitedTextureSize(float w, float h, float maxSize) {
            if (w > h) {
                if (w > maxSize) {
                    return new Point((int) maxSize, (int) (h * (maxSize / w)));
                }
            } else if (h > maxSize) {
                return new Point((int) (w * (maxSize / h)), (int) maxSize);
            }
            return new Point((int) w, (int) h);
        }

        public final int createTexture(int w, int h) {
            int iCreateGlTexture = createGlTexture();
            GLES30.glTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, w, h, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, R2.color.miuix_default_color_on_error_dark, null);
            return iCreateGlTexture;
        }

        public final int createTexture(Bitmap bitmap) {
            Intrinsics.checkNotNullParameter(bitmap, "bitmap");
            int iCreateGlTexture = createGlTexture();
            android.opengl.GLUtils.texImage2D(R2.color.m3_ref_palette_primary80, 0, bitmap, 0);
            return iCreateGlTexture;
        }

        private final int createGlTexture() {
            int[] iArr = new int[1];
            GLES30.glGenTextures(1, iArr, 0);
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, iArr[0]);
            GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_empty, R2.dimen.stroke_item_bg_level_1);
            GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_error, R2.dimen.stroke_item_bg_level_1);
            GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_selected_bg, 33071);
            GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_unselect_bg, 33071);
            return iArr[0];
        }

        public final void generateMipmap(int textureId) {
            if (textureId <= 0) {
                Log.e(GLUtils.TAG, "xl-- generateMipmap error, texture id invalid.");
                return;
            }
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, textureId);
            GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_error, R2.dimen.widget_note_root_padding_top_redress2x2);
            GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_empty, R2.dimen.stroke_item_bg_level_1);
            GLES30.glGenerateMipmap(R2.color.m3_ref_palette_primary80);
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, 0);
        }

        public final int createFramebufferOnly() {
            int[] iArr = new int[1];
            GLES30.glGenFramebuffers(1, iArr, 0);
            return iArr[0];
        }

        public final void bindTextureToFramebuffer(int framebufferId, int textureId) {
            GLES30.glBindFramebuffer(36160, framebufferId);
            GLES30.glFramebufferTexture2D(36160, 36064, R2.color.m3_ref_palette_primary80, textureId, 0);
        }

        public final int createFramebufferFromTexture(int textureId) {
            if (textureId <= 0) {
                Log.e(GLUtils.TAG, "xl-- createFramebufferFromTexture error, texture id invalid.");
                return 0;
            }
            int[] iArr = new int[1];
            GLES30.glGenFramebuffers(1, iArr, 0);
            GLES30.glBindFramebuffer(36160, iArr[0]);
            GLES30.glFramebufferTexture2D(36160, 36064, R2.color.m3_ref_palette_primary80, textureId, 0);
            if (36053 != GLES30.glCheckFramebufferStatus(36160)) {
                Log.e(GLUtils.TAG, "xl- createFramebufferFromTexture: failed");
            }
            GLES30.glBindFramebuffer(36160, 0);
            return iArr[0];
        }

        public final void deleteFramebuffer(int framebufferId) {
            if (framebufferId == 0) {
                return;
            }
            GLES30.glBindFramebuffer(36160, 0);
            GLES30.glDeleteFramebuffers(1, new int[]{framebufferId}, 0);
        }

        public final void deleteTexture(int textureId) {
            if (textureId <= 0) {
                return;
            }
            GLES30.glDeleteTextures(1, new int[]{textureId}, 0);
        }

        public final void deleteTextures(List<Integer> textureIds) {
            Intrinsics.checkNotNullParameter(textureIds, "textureIds");
            if (textureIds.isEmpty()) {
                return;
            }
            int[] iArr = new int[textureIds.size()];
            int size = textureIds.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                if (textureIds.get(i2).intValue() > 0) {
                    iArr[i] = textureIds.get(i2).intValue();
                    i++;
                }
            }
            GLES30.glDeleteTextures(i, iArr, 0);
        }

        public final void deleteGLProgram(int programId) {
            if (programId <= 0) {
                return;
            }
            GLES30.glDeleteProgram(programId);
        }

        public final boolean isGLSyncSignaled(long syncHandle) {
            int[] iArr = new int[1];
            GLES30.glGetSynciv(syncHandle, 37140, 4, new int[1], 0, iArr, 0);
            return iArr[0] == 37145;
        }

        public final Bitmap getBitmapFromFramebuffer(int framebufferId, int bitmapWidth, int bitmapHeight) {
            GLES30.glBindFramebuffer(36160, framebufferId);
            int[] iArr = new int[bitmapWidth * bitmapHeight];
            GLES30.glReadPixels(0, 0, bitmapWidth, bitmapHeight, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, R2.color.miuix_default_color_on_error_dark, IntBuffer.wrap(iArr));
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iArr, bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            return bitmapCreateBitmap;
        }

        public static /* synthetic */ boolean getGLError$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = "";
            }
            return companion.getGLError(str);
        }

        public final boolean getGLError(String msg) {
            String str;
            Intrinsics.checkNotNullParameter(msg, "msg");
            int iGlGetError = GLES30.glGetError();
            if (iGlGetError == 0) {
                return false;
            }
            switch (iGlGetError) {
                case 1280:
                    str = "GL_INVALID_ENUM";
                    break;
                case R2.attr.iconPrimaryColor /* 1281 */:
                    str = "GL_INVALID_VALUE";
                    break;
                case R2.attr.iconRight /* 1282 */:
                    str = "GL_INVALID_OPERATION";
                    break;
                case R2.attr.iconSingle /* 1283 */:
                case R2.attr.iconSize /* 1284 */:
                default:
                    str = "UNKNOWN_ERROR";
                    break;
                case R2.attr.iconSpaceReserved /* 1285 */:
                    str = "GL_OUT_OF_MEMORY";
                    break;
                case R2.attr.iconStartPadding /* 1286 */:
                    str = "GL_INVALID_FRAMEBUFFER_OPERATION";
                    break;
            }
            XLog.e("-", "%s glError:%d (%s)", msg, Integer.valueOf(iGlGetError), str);
            return true;
        }

        public final Bitmap debugSaveTexture(int w, int h) {
            int[] iArr = new int[w * h];
            GLES30.glReadPixels(0, 0, w, h, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, R2.color.miuix_default_color_on_error_dark, IntBuffer.wrap(iArr));
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iArr, w, h, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            return bitmapCreateBitmap;
        }

        public final void debugSaveBitmap(Bitmap bitmap) {
            Intrinsics.checkNotNullParameter(bitmap, "bitmap");
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            Intrinsics.checkNotNullExpressionValue(externalStoragePublicDirectory, "getExternalStoragePublicDirectory(...)");
            if (externalStoragePublicDirectory.exists() || externalStoragePublicDirectory.mkdirs()) {
                File file = new File(externalStoragePublicDirectory, "rendered_preview_" + System.currentTimeMillis() + ".png");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        Log.d("BitmapSaver", "xl-- Bitmap saved to " + file.getAbsolutePath());
                        CloseableKt.closeFinally(fileOutputStream, null);
                        return;
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(fileOutputStream, th);
                            throw th2;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("BitmapSaver", "Error saving Bitmap: " + e.getMessage());
                    return;
                }
            }
            Log.e("BitmapSaver", "Download directory doesn't exist and couldn't be created.");
        }

        public final RectF getRotatedBoundingBox(RectF rectF, float degrees, float centerX, float centerY) {
            Intrinsics.checkNotNullParameter(rectF, "rectF");
            PointF rotatedPoint = getRotatedPoint(new PointF(rectF.centerX(), rectF.centerY()), degrees, centerX, centerY);
            PointF rotatedPoint2 = getRotatedPoint(new PointF(rectF.left, rectF.top), degrees, centerX, centerY);
            PointF rotatedPoint3 = getRotatedPoint(new PointF(rectF.left, rectF.bottom), degrees, centerX, centerY);
            PointF pointF = new PointF((rotatedPoint.x * 2.0f) - rotatedPoint3.x, (rotatedPoint.y * 2.0f) - rotatedPoint3.y);
            PointF pointF2 = new PointF((rotatedPoint.x * 2.0f) - rotatedPoint2.x, (rotatedPoint.y * 2.0f) - rotatedPoint2.y);
            RectF rectF2 = new RectF(rotatedPoint2.x, rotatedPoint2.y, rotatedPoint2.x, rotatedPoint2.y);
            rectF2.union(rotatedPoint3.x, rotatedPoint3.y);
            rectF2.union(pointF.x, pointF.y);
            rectF2.union(pointF2.x, pointF2.y);
            return rectF2;
        }

        public final PointF getRotatedPoint(PointF point, float degrees, float centerX, float centerY) {
            Intrinsics.checkNotNullParameter(point, "point");
            PointF pointF = new PointF();
            float f = point.x - centerX;
            float f2 = point.y - centerY;
            double radians = Math.toRadians(degrees);
            float fCos = (float) Math.cos(radians);
            float fSin = (float) Math.sin(radians);
            pointF.x = ((f * fCos) - (f2 * fSin)) + centerX;
            pointF.y = (f * fSin) + (f2 * fCos) + centerY;
            return pointF;
        }
    }
}
