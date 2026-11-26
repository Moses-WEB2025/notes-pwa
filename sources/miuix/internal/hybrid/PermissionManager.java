package miuix.internal.hybrid;

import android.net.Uri;
import com.xiaomi.onetrack.util.ab;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes5.dex */
public class PermissionManager {
    private Config mConfig;
    private Map<String, Boolean> mValidMap = new HashMap();

    public PermissionManager(Config config) {
        this.mConfig = config;
    }

    private boolean initPermission(String str) {
        Uri uri = Uri.parse(str);
        String host = "file".equals(uri.getScheme()) ? "*" : uri.getHost();
        Iterator<Map.Entry<String, Permission>> it = this.mConfig.getPermissions().entrySet().iterator();
        boolean zEquals = false;
        while (it.hasNext()) {
            Permission value = it.next().getValue();
            String uri2 = value.getUri();
            String host2 = "*".equals(uri2) ? "*" : Uri.parse(uri2).getHost();
            if (value.isApplySubdomains()) {
                String[] strArrSplit = host2.split(ab.a);
                String[] strArrSplit2 = host.split(ab.a);
                if (strArrSplit2.length >= strArrSplit.length) {
                    zEquals = true;
                    for (int i = 1; i <= strArrSplit.length; i++) {
                        if (!strArrSplit2[strArrSplit2.length - i].equals(strArrSplit[strArrSplit.length - i])) {
                            zEquals = false;
                            break;
                        }
                    }
                }
            } else {
                zEquals = host.equals(host2);
            }
            if (zEquals) {
                break;
            }
        }
        return zEquals;
    }

    public boolean isValid(String str) {
        if (!this.mValidMap.containsKey(str)) {
            this.mValidMap.put(str, Boolean.valueOf(initPermission(str)));
        }
        return this.mValidMap.get(str).booleanValue();
    }
}
