package com.xiaomi.accountsdk.hasheddeviceidlib;

import android.content.Context;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;

/* JADX INFO: loaded from: classes4.dex */
public final class PlainDeviceIdUtil {

    public interface IPlainDeviceIdFetcher {
        String getPlainDeviceId(Context context);
    }

    private static class FetcherHolder {
        private static volatile IPlainDeviceIdFetcher sInstance = new PlainDeviceIdUtilImplDefault();

        private FetcherHolder() {
        }
    }

    public static IPlainDeviceIdFetcher getFetcherInstance() {
        return FetcherHolder.sInstance;
    }

    public static void setFetcherInstance(IPlainDeviceIdFetcher iPlainDeviceIdFetcher) {
        IPlainDeviceIdFetcher unused = FetcherHolder.sInstance = iPlainDeviceIdFetcher;
    }

    public static final class PlainDeviceIdUtilImplDefault implements IPlainDeviceIdFetcher {
        @Override // com.xiaomi.accountsdk.hasheddeviceidlib.PlainDeviceIdUtil.IPlainDeviceIdFetcher
        public String getPlainDeviceId(Context context) {
            if (context == null) {
                return null;
            }
            return PrivacyDataMaster.get(context, PrivacyDataType.DEVICE_ID, new String[0]);
        }
    }
}
