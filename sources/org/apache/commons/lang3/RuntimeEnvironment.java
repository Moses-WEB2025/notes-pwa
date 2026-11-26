package org.apache.commons.lang3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes6.dex */
public class RuntimeEnvironment {
    private static boolean fileExists(String str) {
        return Files.exists(Paths.get(str, new String[0]), new LinkOption[0]);
    }

    public static Boolean inContainer() {
        return Boolean.valueOf(inContainer(""));
    }

    static boolean inContainer(String str) {
        String file = readFile(str + "/proc/1/environ", "container");
        if (file != null) {
            return !file.isEmpty();
        }
        return fileExists(new StringBuilder().append(str).append("/.dockerenv").toString()) || fileExists(new StringBuilder().append(str).append("/run/.containerenv").toString());
    }

    private static String readFile(String str, String str2) {
        try {
            final String str3 = str2 + "=";
            return (String) Arrays.stream(new String(Files.readAllBytes(Paths.get(str, new String[0])), Charset.defaultCharset()).split(String.valueOf((char) 0))).filter(new Predicate() { // from class: org.apache.commons.lang3.RuntimeEnvironment$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((String) obj).startsWith(str3);
                }
            }).map(new Function() { // from class: org.apache.commons.lang3.RuntimeEnvironment$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((String) obj).split("=", 2);
                }
            }).map(new Function() { // from class: org.apache.commons.lang3.RuntimeEnvironment$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return RuntimeEnvironment.lambda$readFile$2((String[]) obj);
                }
            }).findFirst().orElse(null);
        } catch (IOException unused) {
            return null;
        }
    }

    static /* synthetic */ String lambda$readFile$2(String[] strArr) {
        return strArr[1];
    }

    @Deprecated
    public RuntimeEnvironment() {
    }
}
