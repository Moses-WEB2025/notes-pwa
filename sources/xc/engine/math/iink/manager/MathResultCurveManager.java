package xc.engine.math.iink.manager;

import android.util.Log;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: loaded from: classes6.dex */
public class MathResultCurveManager {
    private static final String TAG = "MathResultCurveManager";

    public static void markAsMathResult(DataCurve dataCurve) {
        if (dataCurve != null) {
            dataCurve.isMathResult = true;
            Log.d(TAG, "markAsMathResult: curve mId=" + dataCurve.mId + " marked as math result");
        }
    }

    public static boolean isMathResultCurve(DataCurve dataCurve) {
        return dataCurve != null && dataCurve.isMathResult;
    }

    public static boolean shouldAddToEditPage(DataCurve dataCurve) {
        boolean z = !isMathResultCurve(dataCurve);
        Log.d(TAG, String.format("shouldAddToEditPage: curve mId=%d, isMathResult=%s, shouldAdd=%s", Integer.valueOf(dataCurve != null ? dataCurve.mId : -1), Boolean.valueOf(dataCurve != null ? dataCurve.isMathResult : false), Boolean.valueOf(z)));
        return z;
    }

    public static boolean shouldAddToPreviewPage(DataCurve dataCurve, boolean z) {
        boolean z2 = false;
        if (dataCurve == null) {
            return false;
        }
        int size = dataCurve.size(dataCurve.mSavePoints);
        if (size > 0 && (!dataCurve.isMathResult || z)) {
            z2 = true;
        }
        Log.d(TAG, String.format("shouldAddToPreviewPage: curve mId=%d, size=%d, isMathResult=%s, filterMathResult=%s, shouldAdd=%s", Integer.valueOf(dataCurve.mId), Integer.valueOf(size), Boolean.valueOf(dataCurve.isMathResult), Boolean.valueOf(z), Boolean.valueOf(z2)));
        return z2;
    }

    public static DataCurve createMathResultCopy(DataCurve dataCurve) {
        if (dataCurve == null) {
            return null;
        }
        try {
            DataCurve dataCurveClone = dataCurve.clone();
            markAsMathResult(dataCurveClone);
            Log.d(TAG, "createMathResultCopy: created copy for curve mId=" + dataCurve.mId);
            return dataCurveClone;
        } catch (CloneNotSupportedException e) {
            Log.e(TAG, "createMathResultCopy: failed to clone curve mId=" + dataCurve.mId, e);
            return null;
        }
    }
}
