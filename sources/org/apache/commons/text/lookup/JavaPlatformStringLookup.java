package org.apache.commons.text.lookup;

import java.io.PrintStream;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes6.dex */
final class JavaPlatformStringLookup extends AbstractStringLookup {
    static final JavaPlatformStringLookup INSTANCE = new JavaPlatformStringLookup();
    private static final String KEY_HARDWARE = "hardware";
    private static final String KEY_LOCALE = "locale";
    private static final String KEY_OS = "os";
    private static final String KEY_RUNTIME = "runtime";
    private static final String KEY_VERSION = "version";
    private static final String KEY_VM = "vm";

    public static void main(String[] strArr) {
        System.out.println(JavaPlatformStringLookup.class);
        PrintStream printStream = System.out;
        JavaPlatformStringLookup javaPlatformStringLookup = INSTANCE;
        printStream.printf("%s = %s%n", "version", javaPlatformStringLookup.lookup("version"));
        System.out.printf("%s = %s%n", KEY_RUNTIME, javaPlatformStringLookup.lookup(KEY_RUNTIME));
        System.out.printf("%s = %s%n", KEY_VM, javaPlatformStringLookup.lookup(KEY_VM));
        System.out.printf("%s = %s%n", "os", javaPlatformStringLookup.lookup("os"));
        System.out.printf("%s = %s%n", KEY_HARDWARE, javaPlatformStringLookup.lookup(KEY_HARDWARE));
        System.out.printf("%s = %s%n", KEY_LOCALE, javaPlatformStringLookup.lookup(KEY_LOCALE));
    }

    private JavaPlatformStringLookup() {
    }

    String getHardware() {
        return "processors: " + Runtime.getRuntime().availableProcessors() + ", architecture: " + getSystemProperty(SystemProperties.OS_ARCH) + getSystemProperty("-", "sun.arch.data.model") + getSystemProperty(", instruction sets: ", "sun.cpu.isalist");
    }

    String getLocale() {
        return "default locale: " + Locale.getDefault() + ", platform encoding: " + getSystemProperty(SystemProperties.FILE_ENCODING);
    }

    String getOperatingSystem() {
        return getSystemProperty(SystemProperties.OS_NAME) + StringUtils.SPACE + getSystemProperty(SystemProperties.OS_VERSION) + getSystemProperty(StringUtils.SPACE, "sun.os.patch.level") + ", architecture: " + getSystemProperty(SystemProperties.OS_ARCH) + getSystemProperty("-", "sun.arch.data.model");
    }

    String getRuntime() {
        return getSystemProperty(SystemProperties.JAVA_RUNTIME_NAME) + " (build " + getSystemProperty(SystemProperties.JAVA_RUNTIME_VERSION) + ") from " + getSystemProperty(SystemProperties.JAVA_VENDOR);
    }

    private String getSystemProperty(String str) {
        return StringLookupFactory.INSTANCE_SYSTEM_PROPERTIES.apply(str);
    }

    private String getSystemProperty(String str, String str2) {
        String systemProperty = getSystemProperty(str2);
        if (StringUtils.isEmpty(systemProperty)) {
            return "";
        }
        return str + systemProperty;
    }

    String getVirtualMachine() {
        return getSystemProperty(SystemProperties.JAVA_VM_NAME) + " (build " + getSystemProperty(SystemProperties.JAVA_VM_VERSION) + ", " + getSystemProperty(SystemProperties.JAVA_VM_INFO) + ")";
    }

    @Override // org.apache.commons.text.lookup.StringLookup
    public String lookup(String str) {
        if (str == null) {
            return null;
        }
        str.hashCode();
        switch (str) {
            case "locale":
                return getLocale();
            case "os":
                return getOperatingSystem();
            case "vm":
                return getVirtualMachine();
            case "hardware":
                return getHardware();
            case "version":
                return "Java version " + getSystemProperty(SystemProperties.JAVA_VERSION);
            case "runtime":
                return getRuntime();
            default:
                throw new IllegalArgumentException(str);
        }
    }
}
