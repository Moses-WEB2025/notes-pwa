package org.apache.commons.lang3;

import ch.qos.logback.core.CoreConstants;
import java.util.Objects;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes6.dex */
public class ClassPathUtils {
    public static String packageToPath(String str) {
        return ((String) Objects.requireNonNull(str, "path")).replace('.', '/');
    }

    public static String pathToPackage(String str) {
        return ((String) Objects.requireNonNull(str, "path")).replace('/', '.');
    }

    public static String toFullyQualifiedName(Class<?> cls, String str) {
        Objects.requireNonNull(cls, CoreConstants.CONTEXT_SCOPE_VALUE);
        Objects.requireNonNull(str, "resourceName");
        return toFullyQualifiedName(cls.getPackage(), str);
    }

    public static String toFullyQualifiedName(Package r1, String str) {
        Objects.requireNonNull(r1, CoreConstants.CONTEXT_SCOPE_VALUE);
        Objects.requireNonNull(str, "resourceName");
        return r1.getName() + "." + str;
    }

    public static String toFullyQualifiedPath(Class<?> cls, String str) {
        Objects.requireNonNull(cls, CoreConstants.CONTEXT_SCOPE_VALUE);
        Objects.requireNonNull(str, "resourceName");
        return toFullyQualifiedPath(cls.getPackage(), str);
    }

    public static String toFullyQualifiedPath(Package r1, String str) {
        Objects.requireNonNull(r1, CoreConstants.CONTEXT_SCOPE_VALUE);
        Objects.requireNonNull(str, "resourceName");
        return packageToPath(r1.getName()) + InternalZipConstants.ZIP_FILE_SEPARATOR + str;
    }

    @Deprecated
    public ClassPathUtils() {
    }
}
