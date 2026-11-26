package xc.engine.core.graphics.opengl;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import defpackage.R2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/* JADX INFO: loaded from: classes6.dex */
public class GLRenderUtil {
    private static final String TAG = "GLRenderUtil";

    public static FloatBuffer createFloatBuffer(int i) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        return byteBufferAllocateDirect.asFloatBuffer();
    }

    public static ShortBuffer createShortBuffer(int i) {
        return ByteBuffer.allocateDirect(i * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
    }

    public static float[] toRGBA(int i) {
        return new float[]{((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, (i >>> 24) / 255.0f};
    }

    public static RectF borderExpand(RectF rectF, float f) {
        RectF rectF2 = new RectF();
        rectF2.top = ((int) Math.floor(rectF.top)) - f;
        rectF2.bottom = ((int) Math.ceil(rectF.bottom)) + f;
        rectF2.left = ((int) Math.floor(rectF.left)) - f;
        rectF2.right = ((int) Math.ceil(rectF.right)) + f;
        return rectF2;
    }

    public static RectF toRectF(Rect rect) {
        return new RectF(rect.left, rect.top, rect.right, rect.bottom);
    }

    public static int createTexture2(Bitmap bitmap) {
        int[] iArr = new int[1];
        GLES30.glGenTextures(1, iArr, 0);
        int i = iArr[0];
        if (i == 0) {
            getGLErrorString();
        }
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, iArr[0]);
        GLES30.glTexParameterf(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_error, 9728.0f);
        GLES30.glTexParameterf(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_empty, 9729.0f);
        GLES30.glTexParameterf(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_selected_bg, 33071.0f);
        GLES30.glTexParameterf(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_unselect_bg, 33071.0f);
        GLUtils.texImage2D(R2.color.m3_ref_palette_primary80, 0, bitmap, 0);
        return i;
    }

    public static int createTexture(Bitmap bitmap) {
        int[] iArr = new int[1];
        GLES30.glGenTextures(1, iArr, 0);
        int i = iArr[0];
        if (i == 0) {
            getGLErrorString();
        }
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, iArr[0]);
        GLES30.glTexParameterf(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_error, 9728.0f);
        GLES30.glTexParameterf(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_empty, 9729.0f);
        GLES30.glTexParameterf(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_selected_bg, 10497.0f);
        GLES30.glTexParameterf(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_unselect_bg, 10497.0f);
        GLUtils.texImage2D(R2.color.m3_ref_palette_primary80, 0, bitmap, 0);
        return i;
    }

    private static String getGLErrorString() {
        int iGlGetError = GLES30.glGetError();
        if (iGlGetError == 0) {
            return "No error";
        }
        if (iGlGetError != 1285) {
            switch (iGlGetError) {
                case 1280:
                    return "Invalid enum";
                case R2.attr.iconPrimaryColor /* 1281 */:
                    return "Invalid value";
                case R2.attr.iconRight /* 1282 */:
                    return "Invalid operation";
                default:
                    return "Unknown error";
            }
        }
        return "Out of memory";
    }

    public static int createTextureAndBindFramebuffer(int i, int i2) {
        int[] iArr = {0};
        GLES30.glGenTextures(1, iArr, 0);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, iArr[0]);
        GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_empty, R2.dimen.star_theme_end_signature_top);
        GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_error, R2.dimen.star_theme_end_signature_top);
        GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_selected_bg, R2.drawable.ic_folder_choose_shift_out_dark);
        GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_unselect_bg, R2.drawable.ic_folder_choose_shift_out_dark);
        GLES30.glTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, i, i2, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, R2.color.miuix_default_color_on_error_dark, null);
        GLES30.glBindFramebuffer(36160, iArr[0]);
        GLES30.glFramebufferTexture2D(36160, 36064, R2.color.m3_ref_palette_primary80, iArr[0], 0);
        return iArr[0];
    }

    public static int createTextureAndBindFramebuffer(Bitmap bitmap) {
        int[] iArr = {0};
        GLES30.glGenTextures(1, iArr, 0);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, iArr[0]);
        GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_empty, R2.dimen.star_theme_end_signature_top);
        GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_preview_error, R2.dimen.star_theme_end_signature_top);
        GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_selected_bg, 33071);
        GLES30.glTexParameteri(R2.color.m3_ref_palette_primary80, R2.drawable.creation_ai_recommend_unselect_bg, 33071);
        GLUtils.texImage2D(R2.color.m3_ref_palette_primary80, 0, bitmap, 0);
        GLES30.glBindFramebuffer(36160, iArr[0]);
        GLES30.glFramebufferTexture2D(36160, 36064, R2.color.m3_ref_palette_primary80, iArr[0], 0);
        return iArr[0];
    }

    public static void deleteTextureAndFrameBuffer(int i) {
        if (GLES30.glIsTexture(i)) {
            GLES30.glDeleteTextures(1, new int[]{i}, 0);
        }
        if (GLES30.glIsFramebuffer(i)) {
            GLES30.glDeleteFramebuffers(1, new int[]{i}, 0);
        }
    }

    public static float randomDecimal(float f, float f2) {
        double dSin = ((float) Math.sin((f * 12.9898f) + (f2 * 78.233f))) * 43758.547f;
        return (float) (dSin - Math.floor(dSin));
    }

    public static int getBindingFrameBuffer() {
        int[] iArr = new int[1];
        GLES30.glGetIntegerv(36006, iArr, 0);
        return iArr[0];
    }
}
