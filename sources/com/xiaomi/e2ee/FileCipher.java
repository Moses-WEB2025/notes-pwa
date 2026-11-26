package com.xiaomi.e2ee;

import com.xiaomi.e2ee.appkey.KeyEntry;
import com.xiaomi.e2ee.io.E2EEInputStream;
import com.xiaomi.e2ee.stat.MiCloudE2EEEventStatInjector;
import com.xiaomi.e2ee.utils.CipherUtils;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
class FileCipher extends E2EECipher<InputStream> {
    FileCipher() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaomi.e2ee.E2EECipher
    public InputStream decodeData(InputStream inputStream, KeyEntry keyEntry, int i) throws InterruptedException, E2EEException {
        try {
            return new E2EEInputStream(inputStream, CipherUtils.getDecodeFileCipher(keyEntry, i));
        } catch (E2EEException e) {
            MiCloudE2EEEventStatInjector.getInstance().addDecodeDataErrorEvent(e);
            throw e;
        }
    }
}
