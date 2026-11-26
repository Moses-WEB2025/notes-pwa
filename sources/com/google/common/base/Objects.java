package com.google.common.base;

import java.util.Arrays;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public final class Objects extends ExtraObjectsMethodsForWeb {
    private Objects() {
    }

    public static boolean equal(@CheckForNull Object a, @CheckForNull Object b) {
        if (a != b) {
            return a != null && a.equals(b);
        }
        return true;
    }

    public static int hashCode(@CheckForNull Object... objects) {
        return Arrays.hashCode(objects);
    }
}
