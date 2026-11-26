package com.xiaomi.accountsdk.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.futureservice.ClientFuture;
import com.xiaomi.accountsdk.futureservice.ServerServiceConnector;
import com.xiaomi.accountsdk.futureservice.SimpleClientFuture;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.IPassportCommonService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public class PassportCommonServiceClient {
    private static final String FEATURE_GET_DEVICE_INFO_VERSION = "feature_get_device_info_version";
    private static final String TAG = "PassportCommonServiceClient";
    private static final Map<String, Object> sCacheMap = new HashMap();
    private static Boolean sIsDeviceInfoSupported = null;

    private PassportCommonServiceClient() {
    }

    public static boolean isDeviceInfoSupported(Context context) {
        Boolean bool = sIsDeviceInfoSupported;
        if (bool != null) {
            return bool.booleanValue();
        }
        Boolean boolValueOf = Boolean.valueOf(checkServiceFeatureSupportedByVersion(context, FEATURE_GET_DEVICE_INFO_VERSION, 1));
        sIsDeviceInfoSupported = boolValueOf;
        return boolValueOf.booleanValue();
    }

    public static DeviceInfoResult getDeviceInfoRpc(Context context, String str, int i, int i2) {
        return getDeviceInfoRpc(context, str, i, i2, isDeviceInfoSupported(context));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static DeviceInfoResult getDeviceInfoRpc(Context context, final String str, final int i, int i2, boolean z) {
        AccountLogger.log(TAG, "getDeviceInfoRpc>>>supported=" + z);
        if (z) {
            SimpleClientFuture simpleClientFuture = new SimpleClientFuture();
            new ConnectorBase<DeviceInfoResult>(context, simpleClientFuture) { // from class: com.xiaomi.accountsdk.service.PassportCommonServiceClient.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
                public DeviceInfoResult callServiceWork() throws RemoteException {
                    return getIService().getDeviceInfo(str, i);
                }
            }.bind();
            try {
                DeviceInfoResult deviceInfoResult = (DeviceInfoResult) simpleClientFuture.get(i2, TimeUnit.MILLISECONDS);
                AccountLogger.log(TAG, "getDeviceInfoRpc>>>get suc");
                return deviceInfoResult;
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                AccountLogger.log(TAG, "getDeviceInfoRpc", e);
                return new DeviceInfoResult.Builder(null).errorCode(DeviceInfoResult.ErrorCode.ERROR_EXECUTION_EXCEPTION).errorMessage(e.getMessage()).build();
            }
        }
        return new DeviceInfoResult.Builder(null).errorCode(DeviceInfoResult.ErrorCode.ERROR_NOT_SUPPORTED).errorMessage("GetDeviceInfo feature is not yet supported by this version of XiaomiAccount, please update a newer version.").build();
    }

    public static SimpleClientFuture<Map<String, Object>> getSystemAccountData(Context context, boolean z, final String... strArr) {
        boolean zIsDeviceInfoSupported = isDeviceInfoSupported(context);
        SimpleClientFuture<Map<String, Object>> simpleClientFuture = new SimpleClientFuture<>();
        final HashMap map = new HashMap();
        if (!zIsDeviceInfoSupported || strArr == null || strArr.length == 0) {
            AccountLogger.log(TAG, "getSystemAccountData>>>can not get, supported=" + zIsDeviceInfoSupported);
            simpleClientFuture.setServerData(map);
            return simpleClientFuture;
        }
        if (z) {
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                Map<String, Object> map2 = sCacheMap;
                if (map2.containsKey(str)) {
                    map.put(str, map2.get(str));
                } else {
                    arrayList.add(str);
                }
            }
            strArr = new String[arrayList.size()];
            arrayList.toArray(strArr);
        }
        if (strArr.length == 0) {
            AccountLogger.log(TAG, "getSystemAccountData>>>use cache");
            simpleClientFuture.setServerData(map);
            return simpleClientFuture;
        }
        AccountLogger.log(TAG, "getSystemAccountData>>>realRequestKeys=" + Arrays.toString(strArr));
        new ConnectorBase<Map<String, Object>>(context, simpleClientFuture) { // from class: com.xiaomi.accountsdk.service.PassportCommonServiceClient.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
            public Map<String, Object> callServiceWork() throws RemoteException {
                Bundle systemAccountData = getIService().getSystemAccountData(strArr);
                AccountLogger.log(PassportCommonServiceClient.TAG, "getSystemAccountData>>>get finish");
                for (String str2 : strArr) {
                    Object obj = systemAccountData != null ? systemAccountData.get(str2) : null;
                    PassportCommonServiceClient.sCacheMap.put(str2, obj);
                    map.put(str2, obj);
                }
                return map;
            }
        }.bind();
        return simpleClientFuture;
    }

    private static boolean checkServiceFeatureSupportedByVersion(Context context, String str, int i) {
        ResolveInfo resolveInfo;
        Intent intent = new Intent(AccountIntent.ACTION_COMMON_SERVICE);
        intent.setPackage(AccountIntent.PACKAGE_XIAOMI_ACCOUNT);
        List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
        if (listQueryIntentServices != null && !listQueryIntentServices.isEmpty() && (resolveInfo = listQueryIntentServices.get(0)) != null && resolveInfo.serviceInfo != null) {
            try {
                ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name), 128);
                if (serviceInfo != null && serviceInfo.metaData != null) {
                    Object obj = serviceInfo.metaData.get(str);
                    return (obj instanceof Integer) && ((Integer) obj).intValue() >= i;
                }
            } catch (PackageManager.NameNotFoundException e) {
                AccountLogger.log(TAG, "component not found", e);
            }
        }
        return false;
    }

    private static abstract class ConnectorBase<T> extends ServerServiceConnector<IPassportCommonService, T, T> {
        protected ConnectorBase(Context context, ClientFuture<T, T> clientFuture) {
            super(context, AccountIntent.ACTION_COMMON_SERVICE, AccountIntent.PACKAGE_XIAOMI_ACCOUNT, clientFuture);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
        public final IPassportCommonService binderToServiceType(IBinder iBinder) {
            return IPassportCommonService.Stub.asInterface(iBinder);
        }
    }
}
