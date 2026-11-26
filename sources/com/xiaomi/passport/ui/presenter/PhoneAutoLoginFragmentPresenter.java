package com.xiaomi.passport.ui.presenter;

import android.content.Context;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.QueryPhoneInfoParams;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.settings.utils.NetworkCircleImageSaver;
import com.xiaomi.phonenum.data.AccountCertification;
import com.xiaomi.phonenum.procedure.AccountPhoneNumberManagerFactory;
import com.xiaomi.phonenum.procedure.AccountPhoneNumberSourceFlag;
import com.xiaomi.phonenum.procedure.IAccountPhoneNumberManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneAutoLoginFragmentPresenter extends BaseLoginPresenter {
    private static final String TAG = "PhoneAutoPresenter";

    public static List<PhoneAccount> getPhoneAccount(Context context, String str, AccountPhoneNumberSourceFlag accountPhoneNumberSourceFlag) {
        IAccountPhoneNumberManager iAccountPhoneNumberManagerCreateProperManager = AccountPhoneNumberManagerFactory.createProperManager(context);
        AccountLogger.log(TAG, "query sid=" + str + ", flag=" + accountPhoneNumberSourceFlag.sourceFlag);
        AccountCertification[] accountCertifications = iAccountPhoneNumberManagerCreateProperManager.getAccountCertifications(context, str, accountPhoneNumberSourceFlag);
        int length = accountCertifications.length;
        PhoneAccount[] phoneAccountArr = new PhoneAccount[length];
        for (int i = 0; i < length; i++) {
            if (accountCertifications[i] != null) {
                AccountLogger.log(TAG, "query account slot " + i + " is valid, accountCert=" + accountCertifications[i]);
                try {
                    RegisterUserInfo registerUserInfoQueryPhoneUserInfo = XMPassport.queryPhoneUserInfo(new QueryPhoneInfoParams.Builder().serviceId(str).subId(String.valueOf(accountCertifications[i].subId)).phoneHashActivatorToken(new ActivatorPhoneInfo.Builder().slotId(i).activatorToken(accountCertifications[i].activatorToken).phoneHash(accountCertifications[i].hashedPhoneNumber).build()).build());
                    phoneAccountArr[i] = new PhoneAccount(accountCertifications[i], registerUserInfoQueryPhoneUserInfo);
                    if (registerUserInfoQueryPhoneUserInfo != null) {
                        NetworkCircleImageSaver.saveNetworkImageDefault(context, registerUserInfoQueryPhoneUserInfo.avatarAddress);
                    }
                } catch (InvalidPhoneNumException | AccessDeniedException | AuthenticationFailureException | InvalidResponseException | IOException e) {
                    AccountLogger.log(TAG, "queryPhoneUserInfo", e);
                } catch (InvalidVerifyCodeException e2) {
                    iAccountPhoneNumberManagerCreateProperManager.invalidateAccountCertification(context, str, accountCertifications[i]);
                    AccountLogger.log(TAG, "queryPhoneUserInfo", e2);
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < length; i2++) {
            PhoneAccount phoneAccount = phoneAccountArr[i2];
            if (phoneAccount != null) {
                arrayList.add(phoneAccount);
            }
        }
        return arrayList;
    }
}
