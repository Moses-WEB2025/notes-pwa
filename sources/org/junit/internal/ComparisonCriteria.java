package org.junit.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import org.junit.Assert;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ComparisonCriteria {
    protected abstract void assertElementsEqual(Object obj, Object obj2);

    public void arrayEquals(String str, Object obj, Object obj2) throws ArrayComparisonFailure {
        if (obj == obj2 || Arrays.deepEquals(new Object[]{obj}, new Object[]{obj2})) {
            return;
        }
        String str2 = str == null ? "" : str + ": ";
        int iAssertArraysAreSameLength = assertArraysAreSameLength(obj, obj2, str2);
        for (int i = 0; i < iAssertArraysAreSameLength; i++) {
            Object obj3 = Array.get(obj, i);
            Object obj4 = Array.get(obj2, i);
            if (isArray(obj3) && isArray(obj4)) {
                try {
                    arrayEquals(str, obj3, obj4);
                } catch (ArrayComparisonFailure e) {
                    e.addDimension(i);
                    throw e;
                }
            } else {
                try {
                    assertElementsEqual(obj3, obj4);
                } catch (AssertionError e2) {
                    throw new ArrayComparisonFailure(str2, e2, i);
                }
            }
        }
    }

    private boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    private int assertArraysAreSameLength(Object obj, Object obj2, String str) {
        if (obj == null) {
            Assert.fail(str + "expected array was null");
        }
        if (obj2 == null) {
            Assert.fail(str + "actual array was null");
        }
        int length = Array.getLength(obj2);
        int length2 = Array.getLength(obj);
        if (length != length2) {
            Assert.fail(str + "array lengths differed, expected.length=" + length2 + " actual.length=" + length);
        }
        return length2;
    }
}
