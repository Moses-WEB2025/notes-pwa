package miuix.media;

import android.media.AudioRecord;
import android.util.Log;
import java.lang.reflect.Method;
import miuix.reflect.Reflects;

/* JADX INFO: loaded from: classes5.dex */
class AudioRecordHelper {
    private static final String TAG = "AudioRecordHelper";
    private static final Method setParameters;

    AudioRecordHelper() {
    }

    static {
        try {
            Reflects.getMethod((Class<?>) AudioRecord.class, "setParameters", (Class<?>[]) new Class[]{String.class});
        } catch (Exception e) {
            Log.w(TAG, "failed to get setParameters", e);
        }
        setParameters = null;
    }

    public static boolean isExtraParamSupported() {
        return setParameters != null;
    }

    public static int setParameters(AudioRecord audioRecord, String str) {
        Method method = setParameters;
        if (method == null) {
            return -1;
        }
        try {
            return ((Integer) Reflects.invoke(audioRecord, method, str)).intValue();
        } catch (Exception e) {
            Log.w(TAG, "failed to invoke setParameters", e);
            return -1;
        }
    }
}
