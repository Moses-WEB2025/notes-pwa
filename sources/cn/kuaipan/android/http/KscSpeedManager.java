package cn.kuaipan.android.http;

import android.os.SystemClock;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class KscSpeedManager {
    private static final int DEF_RECODE_DURATION = 600;
    private static final long ERASE_DURATION = 300000;
    private static final int MAX_RECODE_DURATION = 3600;
    private static final int MIN_RECODE_DURATION = 300;
    private static final int RECODE_MASK = 3600000;
    private final int mRecodeDuration;
    private long mLatestEraseTime = 0;
    private final HashMap<String, SparseArray<Float>> mRecordMap = new HashMap<>();

    KscSpeedManager(int i) {
        this.mRecodeDuration = Math.min(3600, Math.max(300, i < 0 ? 600 : i));
    }

    public KscSpeedMonitor getMoniter(String str) {
        return new KscSpeedMonitor(this, str);
    }

    public synchronized int getTransmitSize(String str, int i, int i2) {
        if (i2 >= this.mRecodeDuration) {
            return -2;
        }
        SparseArray<Float> sparseArray = this.mRecordMap.get(str);
        if (sparseArray != null && sparseArray.size() > 0) {
            long jCurrent = current();
            int iMin = Math.min(i, this.mRecodeDuration - i2);
            int iComputeKey = computeKey(jCurrent) - i2;
            List<Float> listFindValidRecoders = findValidRecoders(sparseArray, iComputeKey - iMin, iComputeKey);
            if (listFindValidRecoders != null && !listFindValidRecoders.isEmpty()) {
                Iterator<Float> it = listFindValidRecoders.iterator();
                float fFloatValue = 0.0f;
                while (it.hasNext()) {
                    fFloatValue += it.next().floatValue();
                }
                return (int) fFloatValue;
            }
            return -1;
        }
        return -1;
    }

    public int getAverageSpeed(int i) {
        return getAverageSpeed(null, i, 0);
    }

    public int getAverageSpeed(String str, int i) {
        return getAverageSpeed(str, i, 0);
    }

    public synchronized int getAverageSpeed(String str, int i, int i2) {
        if (i2 >= this.mRecodeDuration) {
            return -2;
        }
        SparseArray<Float> sparseArray = this.mRecordMap.get(str);
        if (sparseArray != null && sparseArray.size() > 0) {
            long jCurrent = current();
            int iMin = Math.min(i, this.mRecodeDuration - i2);
            int iComputeKey = computeKey(jCurrent) - i2;
            List<Float> listFindValidRecoders = findValidRecoders(sparseArray, iComputeKey - iMin, iComputeKey);
            if (listFindValidRecoders != null && !listFindValidRecoders.isEmpty()) {
                int size = listFindValidRecoders.size();
                Iterator<Float> it = listFindValidRecoders.iterator();
                float fFloatValue = 0.0f;
                while (it.hasNext()) {
                    fFloatValue += it.next().floatValue();
                }
                return (int) (fFloatValue / size);
            }
            return -1;
        }
        return -1;
    }

    private static List<Float> findValidRecoders(SparseArray<Float> sparseArray, int i, int i2) {
        LinkedList linkedList = new LinkedList();
        int i3 = 0;
        boolean z = i2 < i;
        int size = sparseArray.size();
        if (z) {
            while (i3 < size) {
                int iKeyAt = sparseArray.keyAt(i3);
                if (iKeyAt <= i2 || iKeyAt >= i) {
                    linkedList.add(sparseArray.valueAt(i3));
                }
                i3++;
            }
        } else {
            while (i3 < size) {
                int iKeyAt2 = sparseArray.keyAt(i3);
                if (iKeyAt2 <= i2 && iKeyAt2 >= i) {
                    linkedList.add(sparseArray.valueAt(i3));
                } else if (iKeyAt2 > i2) {
                    break;
                }
                i3++;
            }
        }
        return linkedList;
    }

    public synchronized void recoder(String str, long j, long j2, float f) {
        if (j2 < j || f < 0.0f) {
            return;
        }
        long j3 = j / 1000;
        long j4 = j2 / 1000;
        if (j4 == j3) {
            appendRecoder(str, computeKey(j), f);
        } else {
            long j5 = j4 - j3;
            if (j5 <= 1) {
                float f2 = j2 - j;
                float f3 = ((1000 - (j % 1000)) * f) / f2;
                int iComputeKey = computeKey(j);
                int iComputeKey2 = computeKey(j2);
                appendRecoder(str, iComputeKey, f3);
                appendRecoder(str, iComputeKey2, (f * (j2 % 1000)) / f2);
            } else {
                float f4 = j2 - j;
                float f5 = ((1000 - (j % 1000)) * f) / f4;
                float f6 = ((j2 % 1000) * f) / f4;
                int iComputeKey3 = computeKey(j);
                int iComputeKey4 = computeKey(j2);
                appendRecoder(str, iComputeKey3, f5);
                appendRecoder(str, iComputeKey4, f6);
                appendRecoders(str, iComputeKey3 + 1, iComputeKey4 - 1, ((f - f5) - f6) / (j5 - 1));
            }
        }
        eraseExpired();
    }

    private void eraseExpired() {
        long jCurrent = current();
        if (jCurrent - this.mLatestEraseTime <= 300000) {
            return;
        }
        int iComputeKey = computeKey(jCurrent);
        int i = iComputeKey - this.mRecodeDuration;
        boolean z = iComputeKey < i;
        LinkedList linkedList = new LinkedList();
        for (Map.Entry<String, SparseArray<Float>> entry : this.mRecordMap.entrySet()) {
            String key = entry.getKey();
            SparseArray<Float> value = entry.getValue();
            if (z) {
                int i2 = 0;
                while (i2 < value.size()) {
                    int iKeyAt = value.keyAt(i2);
                    if (iKeyAt > iComputeKey && iKeyAt < i) {
                        value.delete(iKeyAt);
                    } else if (iKeyAt >= i) {
                        break;
                    } else {
                        i2++;
                    }
                }
            } else {
                int i3 = 0;
                while (i3 < value.size()) {
                    int iKeyAt2 = value.keyAt(i3);
                    if (iKeyAt2 > iComputeKey || iKeyAt2 < i) {
                        value.delete(iKeyAt2);
                    } else {
                        i3++;
                    }
                }
            }
            if (value.size() <= 0) {
                linkedList.add(key);
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            this.mRecordMap.remove((String) it.next());
        }
        this.mLatestEraseTime = jCurrent;
    }

    private static int computeKey(long j) {
        return (int) ((j / 1000) % 3600000);
    }

    private void appendRecoders(String str, int i, int i2, float f) {
        if (i2 >= i) {
            while (i <= i2) {
                appendRecoder(str, i, f);
                i++;
            }
            return;
        }
        while (i < 3600000) {
            appendRecoder(str, i, f);
            i++;
        }
        for (int i3 = 0; i3 <= i2; i3++) {
            appendRecoder(str, i3, f);
        }
    }

    private void appendRecoder(String str, int i, float f) {
        SparseArray<Float> sparseArray = this.mRecordMap.get(str);
        if (sparseArray == null) {
            sparseArray = new SparseArray<>();
            this.mRecordMap.put(str, sparseArray);
        }
        sparseArray.put(i, Float.valueOf(sparseArray.get(i, Float.valueOf(0.0f)).floatValue() + f));
        if (str != null) {
            appendRecoder(null, i, f);
        }
    }

    public static long current() {
        return SystemClock.elapsedRealtime();
    }
}
