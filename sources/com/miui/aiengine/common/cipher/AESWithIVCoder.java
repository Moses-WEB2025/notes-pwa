package com.miui.aiengine.common.cipher;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.security.SecureRandom;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AESWithIVCoder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0003H\u0016J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0003H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0004R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lcom/miui/aiengine/common/cipher/AESWithIVCoder;", "Lcom/miui/aiengine/common/cipher/CryptCoder;", "aesKey", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "threadId", "", "ivRaw", "", "coder", "Lcom/miui/aiengine/common/cipher/AESCoder;", "getCoder", "()Lcom/miui/aiengine/common/cipher/AESCoder;", "coder$delegate", "Lkotlin/Lazy;", "decrypt", "message", "encrypt", "checkThreadIdThrow", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class AESWithIVCoder implements CryptCoder {

    /* JADX INFO: renamed from: coder$delegate, reason: from kotlin metadata */
    private final Lazy coder;
    private byte[] ivRaw;
    private final long threadId;

    public AESWithIVCoder(final String aesKey) {
        Intrinsics.checkNotNullParameter(aesKey, "aesKey");
        this.threadId = Thread.currentThread().getId();
        this.coder = LazyKt.lazy(new Function0() { // from class: com.miui.aiengine.common.cipher.AESWithIVCoder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AESWithIVCoder.coder_delegate$lambda$0(aesKey, this);
            }
        });
    }

    private final AESCoder getCoder() {
        return (AESCoder) this.coder.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.aiengine.common.cipher.AESWithIVCoder$coder$2$1] */
    public static final AESWithIVCoder$coder$2$1 coder_delegate$lambda$0(final String str, final AESWithIVCoder aESWithIVCoder) {
        return new AESCoder(str) { // from class: com.miui.aiengine.common.cipher.AESWithIVCoder$coder$2$1
            @Override // com.miui.aiengine.common.cipher.AESCoder
            protected byte[] getInitialVector() {
                return aESWithIVCoder.ivRaw;
            }
        };
    }

    @Override // com.miui.aiengine.common.cipher.CryptCoder
    public String decrypt(String message) throws InvalidAESDataException {
        Intrinsics.checkNotNullParameter(message, "message");
        checkThreadIdThrow();
        AESDefString aESDefString = AESDefStringKt.parse(message);
        if (!Intrinsics.areEqual(aESDefString.getVersion(), "1")) {
            throw new InvalidAESDataException("aes encrypt format version is wrong" + message);
        }
        this.ivRaw = android.util.Base64.decode(aESDefString.getIv(), 11);
        return getCoder().decrypt(aESDefString.getData());
    }

    @Override // com.miui.aiengine.common.cipher.CryptCoder
    public String encrypt(String message) throws InvalidAESDataException, CipherException {
        Intrinsics.checkNotNullParameter(message, "message");
        checkThreadIdThrow();
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        this.ivRaw = bArr;
        return AESDefStringKt.parse("1", android.util.Base64.encodeToString(this.ivRaw, 11), getCoder().encrypt(message)).toString();
    }

    protected final void checkThreadIdThrow() {
        if (Thread.currentThread().getId() != this.threadId) {
            throw new IllegalAccessError("this method can not call concurrently");
        }
    }
}
