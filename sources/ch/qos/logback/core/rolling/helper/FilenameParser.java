package ch.qos.logback.core.rolling.helper;

import java.lang.Comparable;

/* JADX INFO: loaded from: classes.dex */
interface FilenameParser<T extends Comparable<T>> {
    T parseFilename(String str);
}
