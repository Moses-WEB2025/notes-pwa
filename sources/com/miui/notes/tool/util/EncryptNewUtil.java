package com.miui.notes.tool.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: EncryptNewUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001$B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\"\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u001a\u0010\u0019\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u001c\u0010\u001a\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u0012\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u0007J\u001c\u0010\u001e\u001a\u00020\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u0007J\u001c\u0010\u001f\u001a\u00020\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u0007J\u0018\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u0010\u0010\"\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u0006\u0010#\u001a\u00020\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00108\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/miui/notes/tool/util/EncryptNewUtil;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "KEY_ALIAS", "MI_UNIFIED_ENCRYPT_FLAG", "MAX_RETRY_COUNT", "", "BASE_RETRY_DELAY_MS", "", "retryCount", "retryHandler", "Landroid/os/Handler;", "mMiUnifiedEncryptService", "Lcom/xiaomi/security/miunifiedencrypt/MiUnifiedEncrypt;", "registerKey", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", CallMethod.ARG_CALLBACK, "Lcom/miui/notes/tool/util/EncryptNewUtil$RegisterKeyCallback;", "serviceReadyListener", "Lcom/xiaomi/security/miunifiedencrypt/MiUnifiedEncrypt$ServiceReadyListener;", "connectToService", "connectAfterKeyRegistered", "isEncryptedData", "", "data", "encrypt", "decrypt", "handleErrorCode", "code", "disconnectFromService", "isServiceConnected", "RegisterKeyCallback", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EncryptNewUtil {
    private static final long BASE_RETRY_DELAY_MS = 1000;
    public static final EncryptNewUtil INSTANCE = new EncryptNewUtil();
    private static final String KEY_ALIAS = "notes_key_alias";
    private static final int MAX_RETRY_COUNT = 3;
    public static final String MI_UNIFIED_ENCRYPT_FLAG = "miunifiedencrypt_";
    private static final String TAG;
    private static MiUnifiedEncrypt mMiUnifiedEncryptService;
    private static volatile int retryCount;
    private static final Handler retryHandler;

    /* JADX INFO: compiled from: EncryptNewUtil.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&¨\u0006\u0005À\u0006\u0003"}, d2 = {"Lcom/miui/notes/tool/util/EncryptNewUtil$RegisterKeyCallback;", "", "onRegisterKeySuccess", "", "onRegisterKeyFailed", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface RegisterKeyCallback {
        void onRegisterKeyFailed();

        void onRegisterKeySuccess();
    }

    private EncryptNewUtil() {
    }

    static {
        Intrinsics.checkNotNullExpressionValue("EncryptNewUtil", "getSimpleName(...)");
        TAG = "EncryptNewUtil";
        retryHandler = new Handler(Looper.getMainLooper());
    }

    public final void registerKey(Context context, RegisterKeyCallback callback, MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Logger.INSTANCE.d(TAG, "registerKey");
        try {
            if (mMiUnifiedEncryptService == null) {
                mMiUnifiedEncryptService = new MiUnifiedEncrypt.Builder(context, KEY_ALIAS).setKeyPurposes(3).setKeyAlgorithm("AES").setKeySize(128).setEncryptionPaddings("NoPadding").setBlockModes("GCM").setSecurityLevel(1).setOperationLevel(0).build();
            }
            MiUnifiedEncrypt miUnifiedEncrypt = mMiUnifiedEncryptService;
            if (miUnifiedEncrypt != null) {
                miUnifiedEncrypt.connectToService(context, new C03201(callback, context, serviceReadyListener));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.tool.util.EncryptNewUtil$registerKey$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EncryptNewUtil.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0003H\u0016¨\u0006\u0005"}, d2 = {"com/miui/notes/tool/util/EncryptNewUtil$registerKey$1", "Lcom/xiaomi/security/miunifiedencrypt/MiUnifiedEncrypt$ServiceReadyListener;", "onServiceReady", "", "onServiceLost", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class C03201 implements MiUnifiedEncrypt.ServiceReadyListener {
        final /* synthetic */ RegisterKeyCallback $callback;
        final /* synthetic */ Context $context;
        final /* synthetic */ MiUnifiedEncrypt.ServiceReadyListener $serviceReadyListener;

        C03201(RegisterKeyCallback registerKeyCallback, Context context, MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener) {
            this.$callback = registerKeyCallback;
            this.$context = context;
            this.$serviceReadyListener = serviceReadyListener;
        }

        @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
        public void onServiceReady() {
            MiUnifiedEncrypt miUnifiedEncrypt = EncryptNewUtil.mMiUnifiedEncryptService;
            Integer numValueOf = miUnifiedEncrypt != null ? Integer.valueOf(miUnifiedEncrypt.registerKey()) : null;
            Logger.INSTANCE.d(EncryptNewUtil.TAG, "registerKey-registerKeyRet:" + numValueOf);
            if ((numValueOf != null && numValueOf.intValue() == 0) || (numValueOf != null && numValueOf.intValue() == -2)) {
                this.$callback.onRegisterKeySuccess();
                PreferenceUtils.setIsKeyRegistered(true);
            } else {
                this.$callback.onRegisterKeyFailed();
            }
        }

        @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
        public void onServiceLost() {
            Logger.INSTANCE.d(EncryptNewUtil.TAG, "registerKey-onServiceLost");
            if (EncryptNewUtil.retryCount < 3) {
                int i = EncryptNewUtil.retryCount;
                EncryptNewUtil encryptNewUtil = EncryptNewUtil.INSTANCE;
                EncryptNewUtil.retryCount = i + 1;
                long j = ((long) EncryptNewUtil.retryCount) * 1000;
                Logger.INSTANCE.d(EncryptNewUtil.TAG, "registerKey-onServiceLost: retryCount = " + EncryptNewUtil.retryCount + ", retry after " + j + "ms");
                Handler handler = EncryptNewUtil.retryHandler;
                final Context context = this.$context;
                final MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener = this.$serviceReadyListener;
                handler.postDelayed(new Runnable() { // from class: com.miui.notes.tool.util.EncryptNewUtil$registerKey$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EncryptNewUtil.C03201.onServiceLost$lambda$0(context, serviceReadyListener);
                    }
                }, j);
                return;
            }
            Logger.INSTANCE.e(EncryptNewUtil.TAG, "registerKey-onServiceLost: retryCount fail");
            this.$callback.onRegisterKeyFailed();
            EncryptNewUtil encryptNewUtil2 = EncryptNewUtil.INSTANCE;
            EncryptNewUtil.retryCount = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onServiceLost$lambda$0(Context context, MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener) {
            EncryptNewUtil.INSTANCE.connectToService(context, serviceReadyListener);
        }
    }

    public final void connectToService(Context context, final MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener) {
        boolean isKeyRegistered = PreferenceUtils.getIsKeyRegistered();
        Logger.INSTANCE.d(TAG, "connectToService: isKeyRegistered = " + isKeyRegistered);
        if (!isKeyRegistered) {
            registerKey(context, new RegisterKeyCallback() { // from class: com.miui.notes.tool.util.EncryptNewUtil.connectToService.1
                @Override // com.miui.notes.tool.util.EncryptNewUtil.RegisterKeyCallback
                public void onRegisterKeySuccess() {
                    Logger.INSTANCE.d(EncryptNewUtil.TAG, "connectToService-onRegisterKeySuccess");
                    MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener2 = serviceReadyListener;
                    if (serviceReadyListener2 != null) {
                        serviceReadyListener2.onServiceReady();
                    }
                }

                @Override // com.miui.notes.tool.util.EncryptNewUtil.RegisterKeyCallback
                public void onRegisterKeyFailed() {
                    Logger.INSTANCE.d(EncryptNewUtil.TAG, "connectToService-onRegisterKeyFailed");
                    MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener2 = serviceReadyListener;
                    if (serviceReadyListener2 != null) {
                        serviceReadyListener2.onServiceLost();
                    }
                }
            }, serviceReadyListener);
        } else {
            connectAfterKeyRegistered(context, serviceReadyListener);
        }
    }

    private final void connectAfterKeyRegistered(Context context, MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener) {
        Logger.INSTANCE.d(TAG, "connectAfterKeyRegistered");
        if (mMiUnifiedEncryptService == null) {
            mMiUnifiedEncryptService = new MiUnifiedEncrypt(context, KEY_ALIAS);
        }
        MiUnifiedEncrypt miUnifiedEncrypt = mMiUnifiedEncryptService;
        if (miUnifiedEncrypt != null) {
            miUnifiedEncrypt.connectToService(context, new AnonymousClass1(serviceReadyListener, context));
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.tool.util.EncryptNewUtil$connectAfterKeyRegistered$1, reason: invalid class name */
    /* JADX INFO: compiled from: EncryptNewUtil.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0003H\u0016¨\u0006\u0005"}, d2 = {"com/miui/notes/tool/util/EncryptNewUtil$connectAfterKeyRegistered$1", "Lcom/xiaomi/security/miunifiedencrypt/MiUnifiedEncrypt$ServiceReadyListener;", "onServiceReady", "", "onServiceLost", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AnonymousClass1 implements MiUnifiedEncrypt.ServiceReadyListener {
        final /* synthetic */ Context $context;
        final /* synthetic */ MiUnifiedEncrypt.ServiceReadyListener $serviceReadyListener;

        AnonymousClass1(MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener, Context context) {
            this.$serviceReadyListener = serviceReadyListener;
            this.$context = context;
        }

        @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
        public void onServiceReady() {
            Logger.INSTANCE.d(EncryptNewUtil.TAG, "connectAfterKeyRegistered-onServiceReady");
            MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener = this.$serviceReadyListener;
            if (serviceReadyListener != null) {
                serviceReadyListener.onServiceReady();
            }
            EncryptNewUtil encryptNewUtil = EncryptNewUtil.INSTANCE;
            EncryptNewUtil.retryCount = 0;
        }

        @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
        public void onServiceLost() {
            if (EncryptNewUtil.retryCount < 3) {
                int i = EncryptNewUtil.retryCount;
                EncryptNewUtil encryptNewUtil = EncryptNewUtil.INSTANCE;
                EncryptNewUtil.retryCount = i + 1;
                long j = ((long) EncryptNewUtil.retryCount) * 1000;
                Logger.INSTANCE.d(EncryptNewUtil.TAG, "connectAfterKeyRegistered-onServiceLost: retryCount = " + EncryptNewUtil.retryCount + ", retry after " + j + "ms");
                Handler handler = EncryptNewUtil.retryHandler;
                final Context context = this.$context;
                final MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener = this.$serviceReadyListener;
                handler.postDelayed(new Runnable() { // from class: com.miui.notes.tool.util.EncryptNewUtil$connectAfterKeyRegistered$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EncryptNewUtil.AnonymousClass1.onServiceLost$lambda$0(context, serviceReadyListener);
                    }
                }, j);
                return;
            }
            Logger.INSTANCE.e(EncryptNewUtil.TAG, "connectAfterKeyRegistered-onServiceLost: retryCount fail");
            MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener2 = this.$serviceReadyListener;
            if (serviceReadyListener2 != null) {
                serviceReadyListener2.onServiceLost();
            }
            EncryptNewUtil encryptNewUtil2 = EncryptNewUtil.INSTANCE;
            EncryptNewUtil.retryCount = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onServiceLost$lambda$0(Context context, MiUnifiedEncrypt.ServiceReadyListener serviceReadyListener) {
            EncryptNewUtil.INSTANCE.connectToService(context, serviceReadyListener);
        }
    }

    @JvmStatic
    public static final boolean isEncryptedData(String data) {
        if (data != null && StringsKt.startsWith$default(data, MI_UNIFIED_ENCRYPT_FLAG, false, 2, (Object) null)) {
            String strSubstring = data.substring(17);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            if (strSubstring.length() == 0) {
                return false;
            }
            try {
                byte[] bArrDecode = Base64.decode(strSubstring, 1);
                Intrinsics.checkNotNull(bArrDecode);
                return !(bArrDecode.length == 0);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "isEncryptedData e:" + e);
            }
        }
        return false;
    }

    @JvmStatic
    public static final String encrypt(Context context, String data) {
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        logger.d(str, "encrypt");
        if (TextUtils.isEmpty(data)) {
            Logger.INSTANCE.d(str, "encrypt: empty input data");
            return "";
        }
        try {
            EncryptNewUtil encryptNewUtil = INSTANCE;
            if (isEncryptedData(data)) {
                Logger.INSTANCE.e(str, "encrypt: Attempting to encrypt already encrypted/corrupted data. data length: " + (data != null ? Integer.valueOf(data.length()) : null));
                Intrinsics.checkNotNull(data);
                return data;
            }
            Intrinsics.checkNotNull(data);
            byte[] bytes = data.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            int[] iArr = new int[1];
            MiUnifiedEncrypt miUnifiedEncrypt = mMiUnifiedEncryptService;
            if (miUnifiedEncrypt != null) {
                Intrinsics.checkNotNull(miUnifiedEncrypt);
                byte[] bArrOperation = miUnifiedEncrypt.operation(bytes, 1, iArr);
                if (bArrOperation != null && bArrOperation.length != 0) {
                    return MI_UNIFIED_ENCRYPT_FLAG + Base64.encodeToString(bArrOperation, 1);
                }
                encryptNewUtil.handleErrorCode(iArr[0], context);
                Logger.INSTANCE.d(str, "encrypt: The returned data is empty, " + EncryptNewErrorCode.INSTANCE.getErrorDescriptionsFormatted(iArr[0]));
                return data;
            }
            Logger.INSTANCE.d(str, "encrypt: Service object is null, needs to be reconnected");
            encryptNewUtil.connectToService(context, new MiUnifiedEncrypt.ServiceReadyListener() { // from class: com.miui.notes.tool.util.EncryptNewUtil.encrypt.1
                @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                public void onServiceReady() {
                    Logger.INSTANCE.d(EncryptNewUtil.TAG, "encrypt: Reconnect service successfully");
                }

                @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                public void onServiceLost() {
                    Logger.INSTANCE.d(EncryptNewUtil.TAG, "encrypt: Reconnecting service failed");
                }
            });
            return data;
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "Encrypt failed: " + e.getMessage());
            return data == null ? "" : data;
        }
    }

    @JvmStatic
    public static final String decrypt(Context context, String data) throws DecryptFailedException {
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        logger.d(str, "decrypt");
        String str2 = data;
        if (str2 == null || str2.length() == 0) {
            Logger.INSTANCE.d(str, "decrypt: input data is null or empty");
            return "";
        }
        try {
            byte[] bArrOperation = null;
            if (StringsKt.startsWith$default(data, MI_UNIFIED_ENCRYPT_FLAG, false, 2, (Object) null)) {
                data = data.substring(17);
                Intrinsics.checkNotNullExpressionValue(data, "substring(...)");
            }
            byte[] bArrDecode = Base64.decode(data, 1);
            int[] iArr = new int[1];
            MiUnifiedEncrypt miUnifiedEncrypt = mMiUnifiedEncryptService;
            if (miUnifiedEncrypt != null) {
                Intrinsics.checkNotNull(miUnifiedEncrypt);
                bArrOperation = miUnifiedEncrypt.operation(bArrDecode, 2, iArr);
            } else {
                Logger.INSTANCE.d(str, "decrypt: Service object is null, needs to be reconnected");
                INSTANCE.connectToService(context, new MiUnifiedEncrypt.ServiceReadyListener() { // from class: com.miui.notes.tool.util.EncryptNewUtil.decrypt.1
                    @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                    public void onServiceReady() {
                        Logger.INSTANCE.d(EncryptNewUtil.TAG, "decrypt: Reconnect service successfully");
                    }

                    @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
                    public void onServiceLost() {
                        Logger.INSTANCE.d(EncryptNewUtil.TAG, "decrypt: Reconnecting service failed");
                    }
                });
            }
            if (bArrOperation == null || bArrOperation.length == 0) {
                INSTANCE.handleErrorCode(iArr[0], context);
                Logger.INSTANCE.d(str, "decrypt: The returned data is empty, " + EncryptNewErrorCode.INSTANCE.getErrorDescriptionsFormatted(iArr[0]));
                throw new DecryptFailedException("decrypt failed: The returned data is empty");
            }
            return new String(bArrOperation, Charsets.UTF_8);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "Decrypt failed: " + e.getMessage());
            throw new DecryptFailedException("Decrypt failed: " + e.getMessage(), e);
        }
    }

    public final void handleErrorCode(int code, Context context) {
        if (code == EncryptNewErrorCode.KEY_NOT_EXIST.getCode()) {
            PreferenceUtils.setIsKeyRegistered(false);
        }
        connectToService(context, new MiUnifiedEncrypt.ServiceReadyListener() { // from class: com.miui.notes.tool.util.EncryptNewUtil.handleErrorCode.1
            @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
            public void onServiceReady() {
                Logger.INSTANCE.d(EncryptNewUtil.TAG, "handleErrorCode: connect service successfully");
            }

            @Override // com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.ServiceReadyListener
            public void onServiceLost() {
                Logger.INSTANCE.d(EncryptNewUtil.TAG, "handleErrorCode: connecting service failed");
            }
        });
    }

    public final void disconnectFromService(Context context) {
        Logger.INSTANCE.d(TAG, "disconnectFromService");
        try {
            MiUnifiedEncrypt miUnifiedEncrypt = mMiUnifiedEncryptService;
            if (miUnifiedEncrypt != null) {
                miUnifiedEncrypt.disconnectFromService(context);
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "disconnectFromService error, e: " + e);
        } finally {
            mMiUnifiedEncryptService = null;
        }
    }

    public final boolean isServiceConnected() {
        MiUnifiedEncrypt miUnifiedEncrypt = mMiUnifiedEncryptService;
        return miUnifiedEncrypt != null && miUnifiedEncrypt.isConnected();
    }
}
