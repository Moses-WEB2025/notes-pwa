package androidx.constraintlayout.core.motion.utils;

import com.xiaomi.passport.Constants;
import defpackage.R2;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    static DebugHandle sOurHandle;

    public interface DebugHandle {
        void message(String str);
    }

    private static int clamp(int i) {
        int i2 = (i & (~(i >> 31))) + Constants.LOGIN_PUSH_ID;
        return (i2 & (i2 >> 31)) + 255;
    }

    public static void log(String str, String str2) {
        System.out.println(str + " : " + str2);
    }

    public static void loge(String str, String str2) {
        System.err.println(str + " : " + str2);
    }

    public static void socketSend(String str) {
        try {
            OutputStream outputStream = new Socket("127.0.0.1", R2.color.miuix_preference_secondary_text_color_checked_light).getOutputStream();
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (IOException e) {
            System.err.println(e.toString() + StringUtils.LF + Arrays.toString(e.getStackTrace()).replace("[", "   at ").replace(",", "\n   at").replace("]", ""));
        }
    }

    public int getInterpolatedColor(float[] fArr) {
        return (clamp((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (fArr[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f));
    }

    public static int rgbaTocColor(float f, float f2, float f3, float f4) {
        int iClamp = clamp((int) (f * 255.0f));
        int iClamp2 = clamp((int) (f2 * 255.0f));
        return (iClamp << 16) | (clamp((int) (f4 * 255.0f)) << 24) | (iClamp2 << 8) | clamp((int) (f3 * 255.0f));
    }

    public static void setDebugHandle(DebugHandle debugHandle) {
        sOurHandle = debugHandle;
    }

    public static void logStack(String str, int i) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int iMin = Math.min(i, stackTrace.length - 1);
        String str2 = StringUtils.SPACE;
        for (int i2 = 1; i2 <= iMin; i2++) {
            StackTraceElement stackTraceElement = stackTrace[i2];
            String str3 = ".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ") " + stackTraceElement.getMethodName();
            str2 = str2 + StringUtils.SPACE;
            System.out.println(str + str2 + str3 + str2);
        }
    }

    public static void log(String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String str2 = ".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")" + "    ".substring(Integer.toString(stackTraceElement.getLineNumber()).length()) + (stackTraceElement.getMethodName() + "                  ").substring(0, 17);
        System.out.println(str2 + StringUtils.SPACE + str);
        DebugHandle debugHandle = sOurHandle;
        if (debugHandle != null) {
            debugHandle.message(str2 + StringUtils.SPACE + str);
        }
    }
}
