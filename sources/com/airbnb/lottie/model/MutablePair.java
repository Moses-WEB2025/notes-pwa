package com.airbnb.lottie.model;

import androidx.core.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes.dex */
public class MutablePair<T> {
    T first;
    T second;

    public void set(T t, T t2) {
        this.first = t;
        this.second = t2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return objectsEqual(pair.first, this.first) && objectsEqual(pair.second, this.second);
    }

    private static boolean objectsEqual(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public int hashCode() {
        T t = this.first;
        int iHashCode = t == null ? 0 : t.hashCode();
        T t2 = this.second;
        return iHashCode ^ (t2 != null ? t2.hashCode() : 0);
    }

    public String toString() {
        return "Pair{" + this.first + StringUtils.SPACE + this.second + StringSubstitutor.DEFAULT_VAR_END;
    }
}
