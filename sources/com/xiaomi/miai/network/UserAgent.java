package com.xiaomi.miai.network;

import com.xiaomi.miai.utils.Requires;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class UserAgent {
    private static Pattern productPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9\\-_]*$");
    private static Pattern versionPattern = Pattern.compile("^[^;,/() ]+$");
    private Map<String, String> products = new HashMap();

    private UserAgent() {
    }

    public static UserAgent create() {
        return new UserAgent();
    }

    public UserAgent add(String str, String str2) {
        return add(str, str2, true);
    }

    public UserAgent add(String str) {
        return add(str, "", false);
    }

    private UserAgent add(String str, String str2, boolean z) {
        Requires.notNull(str, "product is null.");
        Requires.isTrue(productPattern.matcher(str).matches(), "product format: " + productPattern.pattern());
        if (z) {
            Requires.notNull(str2, "version is null.");
            Requires.isTrue(versionPattern.matcher(str2).matches(), "version format: " + versionPattern.pattern());
        }
        this.products.put(str, str2);
        return this;
    }

    private String buildUAString() {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str : this.products.keySet()) {
            if (!z) {
                sb.append(StringUtils.SPACE);
            }
            sb.append(str);
            String str2 = this.products.get(str);
            if (!str2.isEmpty()) {
                sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR).append(str2);
            }
            z = false;
        }
        return sb.toString().trim();
    }

    public String toString() {
        return buildUAString();
    }
}
