package org.apache.commons.text.lookup;

import com.xiaomi.mirror.RemoteDeviceInfo;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableSupplier;

/* JADX INFO: loaded from: classes6.dex */
final class InetAddressStringLookup extends AbstractStringLookup {
    static final InetAddressStringLookup LOCAL_HOST = new InetAddressStringLookup(new FailableSupplier() { // from class: org.apache.commons.text.lookup.InetAddressStringLookup$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableSupplier
        public final Object get() {
            return InetAddress.getLocalHost();
        }
    });
    static final InetAddressStringLookup LOOPACK_ADDRESS = new InetAddressStringLookup(new FailableSupplier() { // from class: org.apache.commons.text.lookup.InetAddressStringLookup$$ExternalSyntheticLambda1
        @Override // org.apache.commons.lang3.function.FailableSupplier
        public final Object get() {
            return InetAddress.getLoopbackAddress();
        }
    });
    private final FailableSupplier<InetAddress, UnknownHostException> inetAddressSupplier;

    private InetAddressStringLookup(FailableSupplier<InetAddress, UnknownHostException> failableSupplier) {
        this.inetAddressSupplier = (FailableSupplier) Objects.requireNonNull(failableSupplier, "inetAddressSupplier");
    }

    private InetAddress getInetAddress() throws UnknownHostException {
        return this.inetAddressSupplier.get();
    }

    @Override // org.apache.commons.text.lookup.StringLookup
    public String lookup(String str) {
        if (str == null) {
            return null;
        }
        try {
            int iHashCode = str.hashCode();
            if (iHashCode != -1147692044) {
                if (iHashCode != 3373707) {
                    if (iHashCode == 1339224004 && str.equals("canonical-name")) {
                        return getInetAddress().getCanonicalHostName();
                    }
                } else if (str.equals("name")) {
                    return getInetAddress().getHostName();
                }
            } else if (str.equals(RemoteDeviceInfo.KEY_ADDRESS)) {
                return getInetAddress().getHostAddress();
            }
            throw new IllegalArgumentException(str);
        } catch (UnknownHostException unused) {
            return null;
        }
    }
}
