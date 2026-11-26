package androidx.graphics.opengl;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import defpackage.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: QuadTextureRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\b\u0001\u0018\u0000 !2\u00020\u0001:\u0001!B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0016\u001a\u00020\u0017J\u0018\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002J\u0015\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u001eJ\u001e\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Landroidx/graphics/opengl/QuadTextureRenderer;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mSurfaceTexture", "Landroid/graphics/SurfaceTexture;", "mQuadCoords", "", "mTextureTransform", "mQuadPositionHandle", "", "mTexPositionHandle", "mTextureUniformHandle", "mViewProjectionMatrixHandle", "mTextureTransformHandle", "mProgram", "mVertexShader", "mFragmentShader", "mIsReleased", "", "mQuadrantCoordinatesBuffer", "Ljava/nio/FloatBuffer;", "release", "", "configureQuad", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "setSurfaceTexture", "surfaceTexture", "setSurfaceTexture$libXCEngine_release", "draw", "mvpMatrix", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class QuadTextureRenderer {
    public static final int CoordsPerVertex = 2;
    private static final short[] DrawOrder;
    private static final ShortBuffer DrawOrderBuffer;
    public static final String FragmentShader = "\n            #extension GL_OES_EGL_image_external : require\n            precision highp float;\n\n            uniform samplerExternalOES uTexture;\n\n            varying vec2 vTexCoord;\n\n            void main(void){\n                gl_FragColor = texture2D(uTexture, vTexCoord);\n            }\n            ";
    private static final float[] TextureCoordinates;
    private static final FloatBuffer TextureCoordinatesBuffer;
    public static final String VertexShader = "\n            uniform mat4 uVPMatrix;\n            uniform mat4 tVPMatrix;\n            attribute vec4 aPosition;\n            attribute vec2 aTexCoord;\n            varying vec2 vTexCoord;\n\n            void main(void)\n            {\n                gl_Position = uVPMatrix * aPosition;\n                vTexCoord = vec2(tVPMatrix * vec4(aTexCoord.x, 1.0 - aTexCoord.y, 1.0, 1.0));\n            }\n            ";
    public static final int VertexStride = 8;
    public static final String aPosition = "aPosition";
    public static final String aTexCoord = "aTexCoord";
    public static final String tVPMatrix = "tVPMatrix";
    public static final String uTexture = "uTexture";
    public static final String uVPMatrix = "uVPMatrix";
    private static final String vTexCoord = "vTexCoord";
    private int mFragmentShader;
    private boolean mIsReleased;
    private int mProgram;
    private final float[] mQuadCoords;
    private int mQuadPositionHandle;
    private final FloatBuffer mQuadrantCoordinatesBuffer;
    private SurfaceTexture mSurfaceTexture;
    private int mTexPositionHandle;
    private final float[] mTextureTransform = new float[16];
    private int mTextureTransformHandle;
    private int mTextureUniformHandle;
    private int mVertexShader;
    private int mViewProjectionMatrixHandle;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "TextureRenderer";

    public QuadTextureRenderer() {
        float[] fArr = new float[8];
        this.mQuadCoords = fArr;
        this.mQuadPositionHandle = -1;
        this.mTexPositionHandle = -1;
        this.mTextureUniformHandle = -1;
        this.mViewProjectionMatrixHandle = -1;
        this.mTextureTransformHandle = -1;
        this.mProgram = -1;
        this.mVertexShader = -1;
        this.mFragmentShader = -1;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.position(0);
        Intrinsics.checkNotNullExpressionValue(floatBufferAsFloatBuffer, "run(...)");
        this.mQuadrantCoordinatesBuffer = floatBufferAsFloatBuffer;
        Companion companion = INSTANCE;
        this.mVertexShader = companion.loadShader$libXCEngine_release(35633, VertexShader);
        this.mFragmentShader = companion.loadShader$libXCEngine_release(35632, FragmentShader);
        int iGlCreateProgram = GLES20.glCreateProgram();
        this.mProgram = iGlCreateProgram;
        GLES20.glAttachShader(iGlCreateProgram, this.mVertexShader);
        GLES20.glAttachShader(this.mProgram, this.mFragmentShader);
        GLES20.glLinkProgram(this.mProgram);
        GLES20.glUseProgram(this.mProgram);
        this.mQuadPositionHandle = GLES20.glGetAttribLocation(this.mProgram, aPosition);
        this.mTexPositionHandle = GLES20.glGetAttribLocation(this.mProgram, aTexCoord);
        this.mTextureUniformHandle = GLES20.glGetUniformLocation(this.mProgram, uTexture);
        this.mViewProjectionMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, uVPMatrix);
        this.mTextureTransformHandle = GLES20.glGetUniformLocation(this.mProgram, tVPMatrix);
        GLES20.glEnable(R2.color.audio_share_time_color_green);
        GLES20.glBlendFunc(1, R2.attr.colorPrimaryInverse);
    }

    public final void release() {
        if (this.mIsReleased) {
            return;
        }
        int i = this.mVertexShader;
        if (i != -1) {
            GLES20.glDeleteShader(i);
            this.mVertexShader = -1;
        }
        int i2 = this.mFragmentShader;
        if (i2 != -1) {
            GLES20.glDeleteShader(i2);
            this.mFragmentShader = -1;
        }
        int i3 = this.mProgram;
        if (i3 != -1) {
            GLES20.glDeleteProgram(i3);
            this.mProgram = -1;
        }
        this.mIsReleased = true;
    }

    private final FloatBuffer configureQuad(float width, float height) {
        FloatBuffer floatBuffer = this.mQuadrantCoordinatesBuffer;
        float[] fArr = this.mQuadCoords;
        fArr[0] = 0.0f;
        fArr[1] = height;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = width;
        fArr[5] = 0.0f;
        fArr[6] = width;
        fArr[7] = height;
        floatBuffer.put(fArr);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public final void setSurfaceTexture$libXCEngine_release(SurfaceTexture surfaceTexture) {
        Intrinsics.checkNotNullParameter(surfaceTexture, "surfaceTexture");
        this.mSurfaceTexture = surfaceTexture;
    }

    public final void draw(float[] mvpMatrix, float width, float height) {
        Intrinsics.checkNotNullParameter(mvpMatrix, "mvpMatrix");
        if (this.mIsReleased) {
            Log.w(TAG, "Attempt to render when TextureRenderer has been released");
            return;
        }
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture == null) {
            Log.w(TAG, "Attempt to render without texture source");
            return;
        }
        GLES20.glUseProgram(this.mProgram);
        surfaceTexture.updateTexImage();
        GLES20.glTexParameteri(36197, R2.drawable.creation_ai_preview_error, R2.dimen.stroke_item_bg_level_1);
        GLES20.glTexParameteri(36197, R2.drawable.creation_ai_preview_empty, R2.dimen.stroke_item_bg_level_1);
        GLES20.glUniform1i(this.mTextureUniformHandle, 0);
        GLES20.glUniformMatrix4fv(this.mViewProjectionMatrixHandle, 1, false, mvpMatrix, 0);
        int i = this.mTextureTransformHandle;
        float[] fArr = this.mTextureTransform;
        surfaceTexture.getTransformMatrix(fArr);
        Unit unit = Unit.INSTANCE;
        GLES20.glUniformMatrix4fv(i, 1, false, fArr, 0);
        GLES20.glVertexAttribPointer(this.mQuadPositionHandle, 2, R2.color.miuix_default_color_on_secondary_light, false, 8, (Buffer) configureQuad(width, height));
        GLES20.glVertexAttribPointer(this.mTexPositionHandle, 2, R2.color.miuix_default_color_on_secondary_light, false, 8, (Buffer) TextureCoordinatesBuffer);
        GLES20.glEnableVertexAttribArray(this.mQuadPositionHandle);
        GLES20.glEnableVertexAttribArray(this.mTexPositionHandle);
        GLES20.glDrawElements(4, DrawOrder.length, R2.color.miuix_default_color_on_primary_dark, DrawOrderBuffer);
        GLES20.glDisableVertexAttribArray(this.mQuadPositionHandle);
        GLES20.glDisableVertexAttribArray(this.mTexPositionHandle);
    }

    /* JADX INFO: compiled from: QuadTextureRenderer.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0005J\u001d\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u001fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Landroidx/graphics/opengl/QuadTextureRenderer$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", QuadTextureRenderer.uVPMatrix, QuadTextureRenderer.tVPMatrix, QuadTextureRenderer.aPosition, QuadTextureRenderer.aTexCoord, QuadTextureRenderer.vTexCoord, QuadTextureRenderer.uTexture, "VertexShader", "FragmentShader", "CoordsPerVertex", "", "VertexStride", "TextureCoordinates", "", "TextureCoordinatesBuffer", "Ljava/nio/FloatBuffer;", "DrawOrder", "", "DrawOrderBuffer", "Ljava/nio/ShortBuffer;", "checkError", "", "msg", "loadShader", "type", "shaderCode", "loadShader$libXCEngine_release", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void checkError(String msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            int iGlGetError = GLES20.glGetError();
            if (iGlGetError != 0) {
                Log.v(QuadTextureRenderer.TAG, "GLError " + msg + ": " + iGlGetError);
            }
        }

        public final int loadShader$libXCEngine_release(int type, String shaderCode) {
            Intrinsics.checkNotNullParameter(shaderCode, "shaderCode");
            int iGlCreateShader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(iGlCreateShader, shaderCode);
            GLES20.glCompileShader(iGlCreateShader);
            return iGlCreateShader;
        }
    }

    static {
        float[] fArr = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f};
        TextureCoordinates = fArr;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArr);
        floatBufferAsFloatBuffer.position(0);
        Intrinsics.checkNotNullExpressionValue(floatBufferAsFloatBuffer, "run(...)");
        TextureCoordinatesBuffer = floatBufferAsFloatBuffer;
        short[] sArr = {0, 1, 2, 0, 2, 3};
        DrawOrder = sArr;
        ByteBuffer byteBufferAllocateDirect2 = ByteBuffer.allocateDirect(sArr.length * 2);
        byteBufferAllocateDirect2.order(ByteOrder.nativeOrder());
        ShortBuffer shortBufferAsShortBuffer = byteBufferAllocateDirect2.asShortBuffer();
        shortBufferAsShortBuffer.put(sArr);
        shortBufferAsShortBuffer.position(0);
        Intrinsics.checkNotNullExpressionValue(shortBufferAsShortBuffer, "run(...)");
        DrawOrderBuffer = shortBufferAsShortBuffer;
    }
}
