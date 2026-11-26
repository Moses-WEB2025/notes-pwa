package com.xiaomi.passport.uicontroller;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.MiLoginResult;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.accountsdk.account.data.NotificationLoginEndParams;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.futureservice.ClientFuture;
import com.xiaomi.accountsdk.futureservice.ServerServiceConnector;
import com.xiaomi.accountsdk.utils.SystemXiaomiAccountPackageName;

/* JADX INFO: loaded from: classes4.dex */
public class MiPassportUIController {
    private static final String DEFAULT_ACTION_LOGIN_SERVICE = "com.xiaomi.account.action.UI_CONTROLLER_SERVICE";
    private static final String TAG = "MiPassportUIController";
    private static volatile MiPassportUIControllerFactory factory = MiPassportUIControllerFactory.DEFAULT_IMPL;
    private static volatile MiPassportUIController sThis;
    private final String loginServiceActionName;
    private final String loginServicePackageName;
    private final Context mContext;

    public MiPassportUIController(Context context, String str, String str2) {
        this.mContext = context.getApplicationContext();
        this.loginServiceActionName = str;
        this.loginServicePackageName = str2;
    }

    public static void resetMiPassportUIControllerFactoryForTest() {
        factory = MiPassportUIControllerFactory.DEFAULT_IMPL;
    }

    public static void setMiPassportUIControllerFactoryForTest(MiPassportUIControllerFactory miPassportUIControllerFactory) {
        factory = miPassportUIControllerFactory;
    }

    public static MiPassportUIController get(Context context) {
        return factory.newMUiController(context, DEFAULT_ACTION_LOGIN_SERVICE, context.getPackageName());
    }

    public static MiPassportUIController getForMiuiSystemAccountService(Context context) {
        return factory.newMUiController(context, DEFAULT_ACTION_LOGIN_SERVICE, SystemXiaomiAccountPackageName.getValid(context));
    }

    public MiPassportLoginFuture.PasswordLoginFuture loginByPassword(final PasswordLoginParams passwordLoginParams, MiPassportLoginFuture.PasswordLoginUICallback passwordLoginUICallback) {
        MiPassportLoginFuture.PasswordLoginFuture passwordLoginFuture = new MiPassportLoginFuture.PasswordLoginFuture(passwordLoginUICallback);
        new UIConnector<MiLoginResult, AccountInfo>(passwordLoginFuture) { // from class: com.xiaomi.passport.uicontroller.MiPassportUIController.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.passport.uicontroller.MiPassportUIController.UIConnector
            public MiLoginResult doModelLayerWork() throws RemoteException {
                return getIService().loginByPassword(passwordLoginParams);
            }
        }.bind();
        return passwordLoginFuture;
    }

    public MiPassportLoginFuture.NotificationLoginFuture onNotificationLoginEnd(final NotificationLoginEndParams notificationLoginEndParams, MiPassportLoginFuture.NotificationLoginUICallback notificationLoginUICallback) {
        MiPassportLoginFuture.NotificationLoginFuture notificationLoginFuture = new MiPassportLoginFuture.NotificationLoginFuture(notificationLoginUICallback);
        new UIConnector<MiLoginResult, AccountInfo>(notificationLoginFuture) { // from class: com.xiaomi.passport.uicontroller.MiPassportUIController.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.passport.uicontroller.MiPassportUIController.UIConnector
            public MiLoginResult doModelLayerWork() throws RemoteException {
                return getIService().onNotificationLoginEnd(notificationLoginEndParams);
            }
        }.bind();
        return notificationLoginFuture;
    }

    public MiPassportLoginFuture.Step2LoginFuture loginByStep2(final Step2LoginParams step2LoginParams, MiPassportLoginFuture.Step2LoginUICallback step2LoginUICallback) {
        MiPassportLoginFuture.Step2LoginFuture step2LoginFuture = new MiPassportLoginFuture.Step2LoginFuture(step2LoginUICallback);
        new UIConnector<MiLoginResult, AccountInfo>(step2LoginFuture) { // from class: com.xiaomi.passport.uicontroller.MiPassportUIController.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.passport.uicontroller.MiPassportUIController.UIConnector
            public MiLoginResult doModelLayerWork() throws RemoteException {
                return getIService().loginByStep2(step2LoginParams);
            }
        }.bind();
        return step2LoginFuture;
    }

    @Deprecated
    public void addOrUpdateAccountManager(AccountInfo accountInfo) {
        addOrUpdateAccountManager(accountInfo, null);
    }

    public MiPassportLoginFuture.AddOrUpdateAccountFuture addOrUpdateAccountManager(final AccountInfo accountInfo, MiPassportLoginFuture.AddOrUpdateUICallback addOrUpdateUICallback) {
        MiPassportLoginFuture.AddOrUpdateAccountFuture addOrUpdateAccountFuture = new MiPassportLoginFuture.AddOrUpdateAccountFuture(addOrUpdateUICallback);
        new UIConnector<Void, Void>(addOrUpdateAccountFuture) { // from class: com.xiaomi.passport.uicontroller.MiPassportUIController.4
            @Override // com.xiaomi.passport.uicontroller.MiPassportUIController.UIConnector
            public Void doModelLayerWork() throws RemoteException {
                getIService().addOrUpdateAccountManager(accountInfo);
                return null;
            }
        }.bind();
        return addOrUpdateAccountFuture;
    }

    public MiPassportLoginFuture.NotificationAuthFuture onNotificationAuthEnd(final String str, MiPassportLoginFuture.NotificationAuthUICallback notificationAuthUICallback) {
        MiPassportLoginFuture.NotificationAuthFuture notificationAuthFuture = new MiPassportLoginFuture.NotificationAuthFuture(notificationAuthUICallback);
        new UIConnector<NotificationAuthResult, NotificationAuthResult>(notificationAuthFuture) { // from class: com.xiaomi.passport.uicontroller.MiPassportUIController.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.passport.uicontroller.MiPassportUIController.UIConnector
            public NotificationAuthResult doModelLayerWork() throws RemoteException {
                return getIService().onNotificationAuthEnd(str);
            }
        }.bind();
        return notificationAuthFuture;
    }

    private abstract class UIConnector<ModelDataType, UIDataType> extends ServerServiceConnector<IMiPassportUIControllerService, ModelDataType, UIDataType> {
        protected abstract ModelDataType doModelLayerWork() throws RemoteException;

        protected UIConnector(ClientFuture<ModelDataType, UIDataType> clientFuture) {
            super(MiPassportUIController.this.mContext, MiPassportUIController.this.loginServiceActionName, MiPassportUIController.this.loginServicePackageName, clientFuture);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
        public IMiPassportUIControllerService binderToServiceType(IBinder iBinder) {
            return IMiPassportUIControllerService.Stub.asInterface(iBinder);
        }

        @Override // com.xiaomi.accountsdk.futureservice.ServerServiceConnector
        protected ModelDataType callServiceWork() throws RemoteException {
            return doModelLayerWork();
        }
    }
}
