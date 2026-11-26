package org.apache.commons.text.lookup;

import com.xiaomi.mirror.RemoteDeviceInfo;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: classes6.dex */
final class DnsStringLookup extends AbstractStringLookup {
    static final DnsStringLookup INSTANCE = new DnsStringLookup();

    private DnsStringLookup() {
    }

    @Override // org.apache.commons.text.lookup.StringLookup
    public String lookup(String str) {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.trim().split("\\|");
        int length = strArrSplit.length;
        String strTrim = strArrSplit[0].trim();
        if (length >= 2) {
            str = strArrSplit[1].trim();
        }
        try {
            InetAddress byName = InetAddress.getByName(str);
            int iHashCode = strTrim.hashCode();
            if (iHashCode != -1147692044) {
                if (iHashCode != 3373707) {
                    if (iHashCode == 1339224004 && strTrim.equals("canonical-name")) {
                        return byName.getCanonicalHostName();
                    }
                } else if (strTrim.equals("name")) {
                    return byName.getHostName();
                }
            } else if (strTrim.equals(RemoteDeviceInfo.KEY_ADDRESS)) {
                return byName.getHostAddress();
            }
            return byName.getHostAddress();
        } catch (UnknownHostException unused) {
            return null;
        }
    }
}
