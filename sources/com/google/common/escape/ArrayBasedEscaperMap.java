package com.google.common.escape;

import com.google.common.base.Preconditions;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public final class ArrayBasedEscaperMap {
    private static final char[][] EMPTY_REPLACEMENT_ARRAY = (char[][]) Array.newInstance((Class<?>) Character.TYPE, 0, 0);
    private final char[][] replacementArray;

    public static ArrayBasedEscaperMap create(Map<Character, String> replacements) {
        return new ArrayBasedEscaperMap(createReplacementArray(replacements));
    }

    private ArrayBasedEscaperMap(char[][] replacementArray) {
        this.replacementArray = replacementArray;
    }

    char[][] getReplacementArray() {
        return this.replacementArray;
    }

    static char[][] createReplacementArray(Map<Character, String> map) {
        Preconditions.checkNotNull(map);
        if (map.isEmpty()) {
            return EMPTY_REPLACEMENT_ARRAY;
        }
        char[][] cArr = new char[((Character) Collections.max(map.keySet())).charValue() + 1][];
        for (Character ch2 : map.keySet()) {
            cArr[ch2.charValue()] = map.get(ch2).toCharArray();
        }
        return cArr;
    }
}
