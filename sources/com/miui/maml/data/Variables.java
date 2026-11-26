package com.miui.maml.data;

import android.util.Log;
import com.miui.maml.util.Utils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class Variables {
    private static boolean DBG = false;
    private static final String LOG_TAG = "Variables";
    public static final int MAX_ARRAY_SIZE = 10000;
    public static final int VARIABLE_TYPE_HIGH_PRIORITY = 1;
    public static final int VARIABLE_TYPE_NORMAL = 0;
    private DoubleBucket mDoubleBucket;
    private VarBucket<Object> mObjectBucket;

    public Variables() {
        this.mDoubleBucket = new DoubleBucket();
        this.mObjectBucket = new VarBucket<>();
    }

    private static class DoubleInfo {
        double mValue;
        int mVarType;
        int mVersion;

        public DoubleInfo(double d, int i, int i2) {
            this.mValue = d;
            this.mVersion = i;
            this.mVarType = i2 != 1 ? 0 : 1;
        }

        public void setValue(double d) {
            setValue(d, 0);
        }

        public void setValue(double d, int i) {
            if (i > this.mVarType) {
                this.mVarType = i;
            }
            if (i == this.mVarType) {
                this.mValue = d;
                this.mVersion++;
            }
        }
    }

    private static abstract class BaseVarBucket {
        private HashMap<String, Integer> mIndices;
        private int mNextIndex;

        protected abstract void onAddItem(int i);

        private BaseVarBucket() {
            this.mIndices = new HashMap<>();
            this.mNextIndex = 0;
        }

        public synchronized int registerVariable(String str) {
            Integer numValueOf;
            numValueOf = this.mIndices.get(str);
            if (numValueOf == null) {
                numValueOf = Integer.valueOf(this.mNextIndex);
                this.mIndices.put(str, numValueOf);
                onAddItem(this.mNextIndex);
            }
            int iIntValue = numValueOf.intValue();
            int i = this.mNextIndex;
            if (iIntValue == i) {
                this.mNextIndex = i + 1;
            }
            if (Variables.DBG) {
                Log.d(Variables.LOG_TAG, "registerVariable: " + str + "  index:" + numValueOf);
            }
            return numValueOf.intValue();
        }

        public boolean exists(String str) {
            return this.mIndices.containsKey(str);
        }
    }

    private static class DoubleBucket extends BaseVarBucket {
        private ArrayList<DoubleInfo> mArray;

        private DoubleBucket() {
            super();
            this.mArray = new ArrayList<>();
        }

        public final synchronized boolean exists(int i) {
            boolean z;
            z = false;
            if (i >= 0) {
                try {
                    if (this.mArray.get(i) != null) {
                        z = true;
                    }
                } catch (IndexOutOfBoundsException unused) {
                    return false;
                }
            }
            return z;
        }

        public final synchronized void put(int i, double d, int i2) {
            if (i < 0) {
                return;
            }
            try {
                DoubleInfo doubleInfo = this.mArray.get(i);
                if (doubleInfo == null) {
                    this.mArray.set(i, new DoubleInfo(d, 0, i2));
                } else {
                    doubleInfo.setValue(d, i2);
                }
            } catch (IndexOutOfBoundsException unused) {
            }
        }

        public synchronized double get(int i) {
            double d;
            d = 0.0d;
            try {
                DoubleInfo doubleInfo = this.mArray.get(i);
                if (doubleInfo != null) {
                    d = doubleInfo.mValue;
                }
            } catch (IndexOutOfBoundsException unused) {
                return 0.0d;
            }
            return d;
        }

        public synchronized int getVer(int i) {
            int i2;
            i2 = -1;
            try {
                DoubleInfo doubleInfo = this.mArray.get(i);
                if (doubleInfo != null) {
                    i2 = doubleInfo.mVersion;
                }
            } catch (IndexOutOfBoundsException unused) {
                return -1;
            }
            return i2;
        }

        public void reset() {
            int size = this.mArray.size();
            for (int i = 0; i < size; i++) {
                DoubleInfo doubleInfo = this.mArray.get(i);
                if (doubleInfo != null) {
                    doubleInfo.setValue(0.0d);
                }
            }
        }

        @Override // com.miui.maml.data.Variables.BaseVarBucket
        protected void onAddItem(int i) {
            while (this.mArray.size() <= i) {
                this.mArray.add(null);
            }
        }
    }

    private static class ValueInfo<T> {
        T mValue;
        int mVarType;
        int mVersion;

        public ValueInfo(T t, int i, int i2) {
            this.mValue = t;
            this.mVersion = i;
            this.mVarType = i2 != 1 ? 0 : 1;
        }

        public void setValue(T t) {
            setValue(t, 0);
        }

        public void setValue(T t, int i) {
            if (i > this.mVarType) {
                this.mVarType = i;
            }
            if (i == this.mVarType) {
                this.mValue = t;
                this.mVersion++;
            }
        }

        public void reset() {
            T t = this.mValue;
            int i = 0;
            if (t instanceof double[]) {
                double[] dArr = (double[]) t;
                while (i < dArr.length) {
                    dArr[i] = 0.0d;
                    i++;
                }
                return;
            }
            if (t instanceof float[]) {
                float[] fArr = (float[]) t;
                while (i < fArr.length) {
                    fArr[i] = 0.0f;
                    i++;
                }
                return;
            }
            if (t instanceof int[]) {
                int[] iArr = (int[]) t;
                for (int i2 = 0; i2 < iArr.length; i2++) {
                    iArr[i2] = 0;
                }
                return;
            }
            if (t instanceof Object[]) {
                Object[] objArr = (Object[]) t;
                while (i < objArr.length) {
                    objArr[i] = null;
                    i++;
                }
                return;
            }
            setValue(null);
        }
    }

    private static class VarBucket<T> extends BaseVarBucket {
        private ArrayList<ValueInfo<T>> mArray;

        private VarBucket() {
            super();
            this.mArray = new ArrayList<>();
        }

        public final synchronized void put(int i, T t, int i2) {
            if (i < 0) {
                return;
            }
            try {
                ValueInfo<T> valueInfo = this.mArray.get(i);
                if (valueInfo == null) {
                    this.mArray.set(i, new ValueInfo<>(t, 0, i2));
                } else {
                    valueInfo.setValue(t, i2);
                }
            } catch (IndexOutOfBoundsException unused) {
            }
        }

        public synchronized T get(int i) {
            T t;
            t = null;
            try {
                ValueInfo<T> valueInfo = this.mArray.get(i);
                if (valueInfo != null) {
                    t = valueInfo.mValue;
                }
            } catch (IndexOutOfBoundsException unused) {
                return null;
            }
            return t;
        }

        public synchronized int getVer(int i) {
            int i2;
            i2 = -1;
            try {
                ValueInfo<T> valueInfo = this.mArray.get(i);
                if (valueInfo != null) {
                    i2 = valueInfo.mVersion;
                }
            } catch (IndexOutOfBoundsException unused) {
                return -1;
            }
            return i2;
        }

        public void reset() {
            int size = this.mArray.size();
            for (int i = 0; i < size; i++) {
                ValueInfo<T> valueInfo = this.mArray.get(i);
                if (valueInfo != null) {
                    valueInfo.reset();
                }
            }
        }

        @Override // com.miui.maml.data.Variables.BaseVarBucket
        protected void onAddItem(int i) {
            while (this.mArray.size() <= i) {
                this.mArray.add(null);
            }
        }
    }

    public int registerDoubleVariable(String str) {
        return this.mDoubleBucket.registerVariable(str);
    }

    public int registerVariable(String str) {
        return this.mObjectBucket.registerVariable(str);
    }

    public boolean existsDouble(String str) {
        return this.mDoubleBucket.exists(str);
    }

    public boolean existsDouble(int i) {
        return this.mDoubleBucket.exists(i);
    }

    public boolean existsObj(String str) {
        return this.mObjectBucket.exists(str);
    }

    public boolean existsArrItem(int i, int i2) {
        Object obj = get(i);
        if (obj != null && i2 >= 0) {
            try {
                if (i2 < Array.getLength(obj)) {
                    return true;
                }
            } catch (RuntimeException unused) {
            }
        }
        return false;
    }

    public boolean createArray(String str, int i, Class<?> cls) {
        if (cls == null || i <= 0 || i > 10000) {
            Log.e(LOG_TAG, "createArray failed: name= " + str + "  size=" + i);
            return false;
        }
        int iRegisterVariable = registerVariable(str);
        if (get(iRegisterVariable) == null) {
            try {
                put(iRegisterVariable, Array.newInstance(cls, i));
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    @Deprecated
    public final void putNum(String str, double d) {
        put(str, d);
    }

    public final void put(String str, double d) {
        put(registerDoubleVariable(str), d);
    }

    public final void put(String str, double d, int i) {
        this.mDoubleBucket.put(registerDoubleVariable(str), d, i);
    }

    public final void put(int i, double d) {
        this.mDoubleBucket.put(i, d, 0);
    }

    public final boolean putDouble(int i, Object obj) {
        if (obj instanceof Number) {
            put(i, ((Number) obj).doubleValue());
            return true;
        }
        if (obj instanceof Boolean) {
            put(i, ((Boolean) obj).booleanValue() ? 1.0d : 0.0d);
            return true;
        }
        if (!(obj instanceof String)) {
            return false;
        }
        try {
            put(i, Double.parseDouble((String) obj));
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public void put(String str, Object obj) {
        put(registerVariable(str), obj);
    }

    public final void put(String str, Object obj, int i) {
        this.mObjectBucket.put(registerVariable(str), obj, i);
    }

    public final void put(int i, Object obj) {
        this.mObjectBucket.put(i, obj, 0);
    }

    public Object get(String str) {
        return get(registerVariable(str));
    }

    public Object get(int i) {
        return this.mObjectBucket.get(i);
    }

    public double getDouble(int i) {
        return this.mDoubleBucket.get(i);
    }

    public double getDouble(String str) {
        return getDouble(registerDoubleVariable(str));
    }

    public String getString(int i) {
        try {
            return (String) get(i);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public String getString(String str) {
        return getString(registerVariable(str));
    }

    public Object getArr(int i, int i2) {
        return getArrInner(i, i2);
    }

    public double getArrDouble(int i, int i2) {
        try {
            Object obj = get(i);
            if (obj == null) {
                dbglog("getArrDouble: designated array does not exist. index:" + i);
            } else {
                if (isIndexValid(obj, i2)) {
                    if (obj instanceof boolean[]) {
                        return ((boolean[]) obj)[i2] ? 1.0d : 0.0d;
                    }
                    return Array.getDouble(obj, i2);
                }
                dbglog("getArrDouble: designated index is invalid. index:" + i + " arrIndex:" + i2);
            }
        } catch (Exception unused) {
            dbglog("getArrDouble: designated index is invalid. index:" + i + " arrIndex:" + i2);
        }
        return 0.0d;
    }

    public String getArrString(int i, int i2) {
        return (String) getArrInner(i, i2);
    }

    public boolean putArr(int i, int i2, Object obj) {
        try {
            Object[] objArr = (Object[]) get(i);
            if (objArr == null) {
                dbglog("putArr: designated array does not exist. index:" + i);
                return false;
            }
            if (!isIndexValid(objArr, i2)) {
                dbglog("putArr: designated array index is invalid. index:" + i + " arrIndex:" + i2);
                return false;
            }
            objArr[i2] = obj;
            put(i, objArr);
            return true;
        } catch (ClassCastException unused) {
            dbglog("putArr: designated object is not an object array. index:" + i);
            return false;
        } catch (IndexOutOfBoundsException unused2) {
            dbglog("putArr: designated array index is invalid. index:" + i + " arrIndex:" + i2);
            return false;
        }
    }

    public boolean putArrDouble(int i, int i2, Object obj) {
        if (obj instanceof Number) {
            return putArr(i, i2, ((Number) obj).doubleValue());
        }
        if (!(obj instanceof String)) {
            return false;
        }
        try {
            return putArr(i, i2, Utils.parseDouble((String) obj));
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public boolean putArr(int i, int i2, double d) {
        Object obj = get(i);
        if (obj == null) {
            dbglog("putArr: designated array does not exist. index:" + i);
            return false;
        }
        if (!putValueToArr(obj, i2, d)) {
            return false;
        }
        put(i, obj);
        return true;
    }

    public static boolean putValueToArr(Object obj, int i, double d) {
        if (!isIndexValid(obj, i)) {
            dbglog(" designated array index is invalid. arrIndex:" + i);
            return false;
        }
        if (obj instanceof double[]) {
            ((double[]) obj)[i] = d;
        } else if (obj instanceof byte[]) {
            ((byte[]) obj)[i] = (byte) d;
        } else if (obj instanceof char[]) {
            ((char[]) obj)[i] = (char) d;
        } else if (obj instanceof float[]) {
            ((float[]) obj)[i] = (float) d;
        } else if (obj instanceof int[]) {
            ((int[]) obj)[i] = (int) d;
        } else if (obj instanceof long[]) {
            ((long[]) obj)[i] = (long) d;
        } else if (obj instanceof short[]) {
            ((short[]) obj)[i] = (short) d;
        } else if (obj instanceof boolean[]) {
            ((boolean[]) obj)[i] = d > 0.0d;
        }
        return true;
    }

    private static boolean isIndexValid(Object obj, int i) {
        if (i >= 0) {
            try {
                if (i < Array.getLength(obj)) {
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private <T> T getArrInner(int i, int i2) {
        try {
            Object[] objArr = (Object[]) get(i);
            if (objArr == null) {
                dbglog("getArrInner: designated object is not an array. index:" + i);
                return null;
            }
            if (isIndexValid(objArr, i2)) {
                return (T) objArr[i2];
            }
            dbglog("getArrInner: designated array index is invalid. index:" + i + " arrIndex:" + i2);
            return null;
        } catch (ClassCastException unused) {
            dbglog("getArrInner: designated object type is not correct. index:" + i);
            return null;
        } catch (IndexOutOfBoundsException unused2) {
            dbglog("getArrInner: designated index is invalid. index:" + i + " arrIndex:" + i2);
            return null;
        }
    }

    private static void dbglog(String str) {
        if (DBG) {
            Log.d(LOG_TAG, str);
        }
    }

    public int getVer(int i, boolean z) {
        return z ? this.mDoubleBucket.getVer(i) : this.mObjectBucket.getVer(i);
    }

    public void reset() {
        this.mDoubleBucket.reset();
        this.mObjectBucket.reset();
    }
}
