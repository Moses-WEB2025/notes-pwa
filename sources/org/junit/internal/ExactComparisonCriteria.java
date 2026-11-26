package org.junit.internal;

import org.junit.Assert;

/* JADX INFO: loaded from: classes6.dex */
public class ExactComparisonCriteria extends ComparisonCriteria {
    @Override // org.junit.internal.ComparisonCriteria
    protected void assertElementsEqual(Object obj, Object obj2) {
        Assert.assertEquals(obj, obj2);
    }
}
