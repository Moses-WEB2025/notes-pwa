package com.miui.aiengine.common.cipher;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.utils.Logger;
import java.nio.charset.Charset;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: AESCoder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0007\b\u0016\u0018\u0000 \u00102\u00020\u00012\u00020\u0002:\u0001\u0010B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0014J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\nH\u0002J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/miui/aiengine/common/cipher/AESCoder;", "Lcom/miui/aiengine/common/cipher/CryptCoder;", "Lcom/miui/aiengine/common/utils/Logger;", "aesKey", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "keySpec", "Ljavax/crypto/spec/SecretKeySpec;", "getInitialVector", "", "decrypt", "message", "encrypt", "cipherData", "rawData", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class AESCoder extends Logger implements CryptCoder {
    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String TAG = "AESCoder";
    public static final String UTF8 = "UTF-8";
    private SecretKeySpec keySpec;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AESCoder(String aesKey) throws CipherException {
        super(null, 1, null);
        Intrinsics.checkNotNullParameter(aesKey, "aesKey");
        byte[] bArrDecode = Base64.INSTANCE.decode(aesKey, 2);
        if (bArrDecode.length != 16) {
            throw new CipherException("invalid key size,only size of 16  can be accepted");
        }
        this.keySpec = new SecretKeySpec(bArrDecode, "AES");
    }

    protected byte[] getInitialVector() {
        byte[] bytes = "0102030405060708".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return bytes;
    }

    @Override // com.miui.aiengine.common.cipher.CryptCoder
    public String decrypt(String message) throws CipherException {
        Intrinsics.checkNotNullParameter(message, "message");
        try {
            byte[] bArrDecrypt = decrypt(Base64.INSTANCE.decode(message, 2));
            Charset charsetForName = Charset.forName("UTF-8");
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(...)");
            return new String(bArrDecrypt, charsetForName);
        } catch (Exception e) {
            throw new CipherException("failed to decrypt: " + message + " by aes coder", e);
        }
    }

    @Override // com.miui.aiengine.common.cipher.CryptCoder
    public String encrypt(String message) throws CipherException {
        Intrinsics.checkNotNullParameter(message, "message");
        try {
            Charset charsetForName = Charset.forName("UTF-8");
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(...)");
            byte[] bytes = message.getBytes(charsetForName);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            return Base64.INSTANCE.encodeToString(encrypt(bytes), 2);
        } catch (Exception e) {
            throw new CipherException("fail to encrypt by aes coder", e);
        }
    }

    private final byte[] decrypt(byte[] cipherData) throws CipherException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, this.keySpec, new IvParameterSpec(getInitialVector()));
            if (cipherData == null) {
                throw new IllegalBlockSizeException("no block data");
            }
            byte[] bArrDoFinal = cipher.doFinal(cipherData);
            Intrinsics.checkNotNull(bArrDoFinal);
            return bArrDoFinal;
        } catch (Exception e) {
            throw new CipherException("fail to decrypt by aes coder", e);
        }
    }

    private final byte[] encrypt(byte[] rawData) throws CipherException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, this.keySpec, new IvParameterSpec(getInitialVector()));
            byte[] bArrDoFinal = cipher.doFinal(rawData);
            Intrinsics.checkNotNullExpressionValue(bArrDoFinal, "doFinal(...)");
            return bArrDoFinal;
        } catch (Exception e) {
            throw new CipherException("fail to encrypt by aes coder", e);
        }
    }
}
