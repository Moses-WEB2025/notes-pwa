package com.xiaomi.phonenum.procedure;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.sim.SIMInfo;
import com.xiaomi.phonenum.data.AccountCertification;
import com.xiaomi.phonenum.data.PlainPhoneNumber;
import com.xiaomi.phonenum.phone.PhoneInfo;
import com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService;
import com.xiaomi.phonenum.service.IAccountPhoneNumberResponse;
import com.xiaomi.phonenum.utils.ServiceBindWaiter;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class MiuiAccountPhoneNumberManager implements IAccountPhoneNumberManager {
    public static final int ERROR_INVALID_PACKAGE_NAME = 101;
    public static final int ERROR_NEED_AGREE_CTA = 103;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NO_PERMISSION = 100;
    public static final int ERROR_NULL_PARAMETER = 102;
    private static final String INTENT_ACTION_ACCOUNT_PHONE_NUMBER_SERVICE = "com.xiaomi.phonenum.ACCOUNT_PHONE_NUMBER_MANAGER_BIND_SERVICE";
    private static final String INTENT_PACKAGE_ACCOUNT_PHONE_NUMBER_SERVICE = "com.xiaomi.account";
    private static final String TAG = "MiuiAccountPhoneNumberManager";

    public static boolean isAccountPhoneNumberManagerServiceExists(Context context) {
        List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(getAccountPhoneNumberManagerServiceIntent(), 0);
        return (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) ? false : true;
    }

    private static Intent getAccountPhoneNumberManagerServiceIntent() {
        Intent intent = new Intent(INTENT_ACTION_ACCOUNT_PHONE_NUMBER_SERVICE);
        intent.setPackage("com.xiaomi.account");
        return intent;
    }

    @Override // com.xiaomi.phonenum.procedure.IAccountPhoneNumberManager
    public PlainPhoneNumber[] getPlainPhoneNumbers(Context context, String str, AccountPhoneNumberSourceFlag accountPhoneNumberSourceFlag) {
        AccountLogger.log(TAG, "call getPlainPhoneNumbers sid=" + str);
        final PlainPhoneNumber[] plainPhoneNumberArr = new PlainPhoneNumber[PhoneInfo.get(context).getPhoneCount()];
        ServiceBindWaiter.ServiceBindResult serviceBindResult = null;
        try {
            try {
                ServiceBindWaiter.ServiceBindResult serviceBindResultBindAndWait = ServiceBindWaiter.bindAndWait(context, getAccountPhoneNumberManagerServiceIntent());
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                final PlainPhoneNumber[][] plainPhoneNumberArr2 = new PlainPhoneNumber[1][];
                IAccountPhoneNumberManagerService.Stub.asInterface(serviceBindResultBindAndWait.binder).getPlainPhoneNumbers(str, context.getPackageName(), accountPhoneNumberSourceFlag.sourceFlag, new IAccountPhoneNumberResponse.Stub() { // from class: com.xiaomi.phonenum.procedure.MiuiAccountPhoneNumberManager.1
                    @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
                    public void onResult(Bundle bundle) throws RemoteException {
                        AccountLogger.log(MiuiAccountPhoneNumberManager.TAG, "onResult");
                        bundle.setClassLoader(PlainPhoneNumber.class.getClassLoader());
                        Parcelable[] parcelableArray = bundle.getParcelableArray("plain_phone_number_array");
                        plainPhoneNumberArr2[0] = new PlainPhoneNumber[parcelableArray.length];
                        for (int i = 0; i < parcelableArray.length; i++) {
                            plainPhoneNumberArr2[0][i] = (PlainPhoneNumber) parcelableArray[i];
                        }
                        countDownLatch.countDown();
                    }

                    @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
                    public void onError(int i, String str2) throws RemoteException {
                        AccountLogger.log(MiuiAccountPhoneNumberManager.TAG, "onError code=" + i + ", desc=" + str2);
                        plainPhoneNumberArr2[0] = plainPhoneNumberArr;
                        countDownLatch.countDown();
                    }
                }, "plain_phone_number_array");
                if (!countDownLatch.await(10L, TimeUnit.SECONDS)) {
                    AccountLogger.log(TAG, "getPlainPhoneNumbers timeout");
                    if (serviceBindResultBindAndWait != null) {
                        serviceBindResultBindAndWait.unbind(context);
                        return plainPhoneNumberArr;
                    }
                    return plainPhoneNumberArr;
                }
                AccountLogger.log(TAG, "getPlainPhoneNumbers succeed");
                PlainPhoneNumber[] plainPhoneNumberArr3 = plainPhoneNumberArr2[0];
                if (serviceBindResultBindAndWait != null) {
                    serviceBindResultBindAndWait.unbind(context);
                }
                return plainPhoneNumberArr3;
            } catch (RemoteException | ServiceBindWaiter.ServiceBindFailedException | ServiceBindWaiter.ServiceBindTimeoutException | InterruptedException e) {
                AccountLogger.log(TAG, "getPlainPhoneNumbers failed", e);
                if (0 != 0) {
                    serviceBindResult.unbind(context);
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                serviceBindResult.unbind(context);
                throw th;
            }
            throw th;
        }
    }

    @Override // com.xiaomi.phonenum.procedure.IAccountPhoneNumberManager
    public AccountCertification[] getAccountCertifications(Context context, String str, AccountPhoneNumberSourceFlag accountPhoneNumberSourceFlag) {
        AccountLogger.log(TAG, "call getAccountCertifications sid=" + str);
        final AccountCertification[] accountCertificationArr = new AccountCertification[PhoneInfo.get(context).getPhoneCount()];
        ServiceBindWaiter.ServiceBindResult serviceBindResult = null;
        try {
            try {
                ServiceBindWaiter.ServiceBindResult serviceBindResultBindAndWait = ServiceBindWaiter.bindAndWait(context, getAccountPhoneNumberManagerServiceIntent());
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                final AccountCertification[][] accountCertificationArr2 = new AccountCertification[1][];
                IAccountPhoneNumberManagerService.Stub.asInterface(serviceBindResultBindAndWait.binder).getAccountCertifications(str, context.getPackageName(), accountPhoneNumberSourceFlag.sourceFlag, new IAccountPhoneNumberResponse.Stub() { // from class: com.xiaomi.phonenum.procedure.MiuiAccountPhoneNumberManager.2
                    @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
                    public void onResult(Bundle bundle) throws RemoteException {
                        AccountLogger.log(MiuiAccountPhoneNumberManager.TAG, "onResult" + bundle.toString());
                        bundle.setClassLoader(AccountCertification.class.getClassLoader());
                        Parcelable[] parcelableArray = bundle.getParcelableArray("account_certification_array");
                        accountCertificationArr2[0] = new AccountCertification[parcelableArray.length];
                        for (int i = 0; i < parcelableArray.length; i++) {
                            accountCertificationArr2[0][i] = (AccountCertification) parcelableArray[i];
                        }
                        countDownLatch.countDown();
                    }

                    @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
                    public void onError(int i, String str2) throws RemoteException {
                        AccountLogger.log(MiuiAccountPhoneNumberManager.TAG, "onError code=" + i + ", desc=" + str2);
                        accountCertificationArr2[0] = accountCertificationArr;
                        countDownLatch.countDown();
                    }
                }, "account_certification_array");
                if (!countDownLatch.await(1L, TimeUnit.MINUTES)) {
                    AccountLogger.log(TAG, "getAccountCertifications timeout");
                    if (serviceBindResultBindAndWait != null) {
                        serviceBindResultBindAndWait.unbind(context);
                        return accountCertificationArr;
                    }
                    return accountCertificationArr;
                }
                AccountLogger.log(TAG, "getAccountCertifications succeed");
                AccountCertification[] accountCertificationArr3 = accountCertificationArr2[0];
                if (serviceBindResultBindAndWait != null) {
                    serviceBindResultBindAndWait.unbind(context);
                }
                return accountCertificationArr3;
            } catch (RemoteException | ServiceBindWaiter.ServiceBindFailedException | ServiceBindWaiter.ServiceBindTimeoutException | InterruptedException e) {
                AccountLogger.log(TAG, "getAccountCertifications failed", e);
                if (0 != 0) {
                    serviceBindResult.unbind(context);
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                serviceBindResult.unbind(context);
                throw th;
            }
            throw th;
        }
    }

    @Override // com.xiaomi.phonenum.procedure.IAccountPhoneNumberManager
    public void invalidateAccountCertification(Context context, String str, AccountCertification accountCertification) {
        AccountLogger.log(TAG, "call invalidateAccountCertification sid=" + str + ", accountCertification=" + accountCertification);
        ServiceBindWaiter.ServiceBindResult serviceBindResultBindAndWait = null;
        try {
            serviceBindResultBindAndWait = ServiceBindWaiter.bindAndWait(context, getAccountPhoneNumberManagerServiceIntent());
            AccountLogger.log(TAG, "invalidateAccountCertification errorCode=" + IAccountPhoneNumberManagerService.Stub.asInterface(serviceBindResultBindAndWait.binder).invalidateAccountCertification(str, context.getPackageName(), accountCertification));
        } catch (RemoteException | ServiceBindWaiter.ServiceBindFailedException | ServiceBindWaiter.ServiceBindTimeoutException | InterruptedException e) {
            AccountLogger.log(TAG, "invalidateAccountCertification failed", e);
        } finally {
            if (serviceBindResultBindAndWait != null) {
                serviceBindResultBindAndWait.unbind(context);
            }
        }
    }

    @Override // com.xiaomi.phonenum.procedure.IAccountPhoneNumberManager
    public SIMInfo[] getSIMInfos(Context context, String str, String[] strArr) throws Throwable {
        AccountLogger.log(TAG, "call getSIMInfos sid=" + str + ", simInfoTypes=" + String.join(",", strArr));
        final SIMInfo[] sIMInfoArr = new SIMInfo[PhoneInfo.get(context).getPhoneCount()];
        ServiceBindWaiter.ServiceBindResult serviceBindResult = null;
        try {
            try {
                ServiceBindWaiter.ServiceBindResult serviceBindResultBindAndWait = ServiceBindWaiter.bindAndWait(context, getAccountPhoneNumberManagerServiceIntent());
                try {
                    try {
                        final CountDownLatch countDownLatch = new CountDownLatch(1);
                        final SIMInfo[][] sIMInfoArr2 = new SIMInfo[1][];
                        IAccountPhoneNumberManagerService.Stub.asInterface(serviceBindResultBindAndWait.binder).getSIMInfos(str, context.getPackageName(), strArr, new IAccountPhoneNumberResponse.Stub() { // from class: com.xiaomi.phonenum.procedure.MiuiAccountPhoneNumberManager.3
                            @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
                            public void onResult(Bundle bundle) throws RemoteException {
                                AccountLogger.log(MiuiAccountPhoneNumberManager.TAG, "onResult");
                                bundle.setClassLoader(SIMInfo.class.getClassLoader());
                                Parcelable[] parcelableArray = bundle.getParcelableArray("sim_info_array");
                                sIMInfoArr2[0] = new SIMInfo[parcelableArray.length];
                                for (int i = 0; i < parcelableArray.length; i++) {
                                    sIMInfoArr2[0][i] = (SIMInfo) parcelableArray[i];
                                }
                                countDownLatch.countDown();
                            }

                            @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
                            public void onError(int i, String str2) throws RemoteException {
                                AccountLogger.log(MiuiAccountPhoneNumberManager.TAG, "onError code=" + i + ", desc=" + str2);
                                sIMInfoArr2[0] = sIMInfoArr;
                                countDownLatch.countDown();
                            }
                        }, "sim_info_array");
                        if (!countDownLatch.await(1L, TimeUnit.MINUTES)) {
                            AccountLogger.log(TAG, "getSIMInfos timeout");
                            if (serviceBindResultBindAndWait != null) {
                                serviceBindResultBindAndWait.unbind(context);
                            }
                            return sIMInfoArr;
                        }
                        AccountLogger.log(TAG, "getSIMInfos succeed");
                        SIMInfo[] sIMInfoArr3 = sIMInfoArr2[0];
                        if (serviceBindResultBindAndWait != null) {
                            serviceBindResultBindAndWait.unbind(context);
                        }
                        return sIMInfoArr3;
                    } catch (RemoteException | ServiceBindWaiter.ServiceBindFailedException | ServiceBindWaiter.ServiceBindTimeoutException | InterruptedException e) {
                        e = e;
                        serviceBindResult = serviceBindResultBindAndWait;
                        AccountLogger.log(TAG, "getSIMInfos failed", e);
                        if (serviceBindResult != null) {
                            serviceBindResult.unbind(context);
                        }
                        return new SIMInfo[0];
                    }
                } catch (Throwable th) {
                    th = th;
                    serviceBindResult = serviceBindResultBindAndWait;
                    if (serviceBindResult != null) {
                        serviceBindResult.unbind(context);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (RemoteException | ServiceBindWaiter.ServiceBindFailedException | ServiceBindWaiter.ServiceBindTimeoutException | InterruptedException e2) {
            e = e2;
        }
    }
}
