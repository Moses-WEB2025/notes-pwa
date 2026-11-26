package org.apache.commons.text;

import java.text.Format;
import java.util.Locale;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FormatFactory {
    Format getFormat(String str, String str2, Locale locale);
}
