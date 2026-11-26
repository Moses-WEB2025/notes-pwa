package xc.engine.core.graphics.opengl;

import android.opengl.GLES30;
import xc.engine.utils.XLog;

/* JADX INFO: loaded from: classes6.dex */
public class OpenGLShaderUtil {
    private static final String TAG = "OpenGLShaderUtil";

    public static int createShader(int i, String str) {
        int iGlCreateShader = GLES30.glCreateShader(i);
        if (iGlCreateShader != 0) {
            GLES30.glShaderSource(iGlCreateShader, str);
            GLES30.glCompileShader(iGlCreateShader);
            int[] iArr = new int[1];
            GLES30.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
            if (iArr[0] == 0) {
                XLog.e(TAG, "Shader error: %s", GLES30.glGetShaderInfoLog(iGlCreateShader));
                GLES30.glDeleteShader(iGlCreateShader);
            }
        }
        return iGlCreateShader;
    }

    public static int createProgram(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return -1;
        }
        int iGlCreateProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(iGlCreateProgram, i);
        GLES30.glAttachShader(iGlCreateProgram, i2);
        GLES30.glLinkProgram(iGlCreateProgram);
        int[] iArr = new int[1];
        GLES30.glGetProgramiv(iGlCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 0) {
            String strGlGetProgramInfoLog = GLES30.glGetProgramInfoLog(iGlCreateProgram);
            GLES30.glDeleteProgram(iGlCreateProgram);
            XLog.e(TAG, "Program link failed: %s", strGlGetProgramInfoLog);
        }
        return iGlCreateProgram;
    }
}
