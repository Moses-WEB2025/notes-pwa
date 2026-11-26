package miuix.internal.hybrid;

import java.util.TreeSet;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes5.dex */
public class ConfigUtils {
    private static final String KEY_FEATURES = "features";
    private static final String KEY_NAME = "name";
    private static final String KEY_ORIGIN = "origin";
    private static final String KEY_PARAMS = "params";
    private static final String KEY_PERMISSIONS = "permissions";
    private static final String KEY_SUBDOMAINS = "subdomains";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_VALUE = "value";
    private static final String KEY_VENDOR = "vendor";

    private ConfigUtils() {
    }

    public static String getRawConfig(Config config) {
        return "{timestamp:" + config.getSecurity().getTimestamp() + ",vendor:\"" + config.getVendor() + "\"," + buildFeatures(config) + "," + buildPermissions(config) + StringSubstitutor.DEFAULT_VAR_END;
    }

    private static String buildFeatures(Config config) {
        return "features:[" + buildFeature(config) + "]";
    }

    private static String buildFeature(Config config) {
        StringBuilder sb = new StringBuilder();
        TreeSet<String> treeSet = new TreeSet(config.getFeatures().keySet());
        if (treeSet.isEmpty()) {
            return "";
        }
        for (String str : treeSet) {
            sb.append("{name:\"");
            sb.append(str);
            sb.append("\",params:[");
            sb.append(buildParam(config.getFeature(str)));
            sb.append("]},");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static String buildParam(Feature feature) {
        StringBuilder sb = new StringBuilder();
        TreeSet<String> treeSet = new TreeSet(feature.getParams().keySet());
        if (treeSet.isEmpty()) {
            return "";
        }
        for (String str : treeSet) {
            sb.append("{name:\"");
            sb.append(str);
            sb.append("\",value:\"");
            sb.append(feature.getParam(str));
            sb.append("\"},");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static String buildPermissions(Config config) {
        return "permissions:[" + buildPermission(config) + "]";
    }

    private static Object buildPermission(Config config) {
        StringBuilder sb = new StringBuilder();
        TreeSet<String> treeSet = new TreeSet(config.getPermissions().keySet());
        if (treeSet.isEmpty()) {
            return "";
        }
        for (String str : treeSet) {
            sb.append("{origin:\"");
            sb.append(str);
            sb.append("\",subdomains:");
            sb.append(config.getPermission(str).isApplySubdomains());
            sb.append("},");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
