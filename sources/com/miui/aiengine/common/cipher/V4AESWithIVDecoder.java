package com.miui.aiengine.common.cipher;

import android.util.Log;
import com.market.sdk.reflect.Field;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: V4AESWithIVDecoder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/miui/aiengine/common/cipher/V4AESWithIVDecoder;", "Lcom/miui/aiengine/common/cipher/AESWithIVCoder;", "aesKey", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "decrypt", "message", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class V4AESWithIVDecoder extends AESWithIVCoder {
    private final String aesKey;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public V4AESWithIVDecoder(String aesKey) {
        super(aesKey);
        Intrinsics.checkNotNullParameter(aesKey, "aesKey");
        this.aesKey = aesKey;
    }

    @Override // com.miui.aiengine.common.cipher.AESWithIVCoder, com.miui.aiengine.common.cipher.CryptCoder
    public String decrypt(String message) throws CipherException {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(message, "message");
        checkThreadIdThrow();
        try {
            JSONObject jSONObject = new JSONObject(message);
            String string = jSONObject.getString(Field.DOUBLE_SIGNATURE_PRIMITIVE);
            String string2 = jSONObject.getString("S");
            Intrinsics.checkNotNull(string2);
            List<String> listSplit = new Regex(":").split(string2, 0);
            if (!listSplit.isEmpty()) {
                ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
                while (true) {
                    if (listIterator.hasPrevious()) {
                        if (listIterator.previous().length() != 0) {
                            listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                            break;
                        }
                    } else {
                        listEmptyList = CollectionsKt.emptyList();
                        break;
                    }
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
                break;
            }
            String[] strArr = (String[]) listEmptyList.toArray(new String[0]);
            if (strArr.length != 2) {
                throw new SecurityException("not 2 columns in security info");
            }
            String str = strArr[0];
            String str2 = strArr[1];
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str3 = String.format("%s:%s", Arrays.copyOf(new Object[]{str, new String(string)}, 2));
            Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
            Charset charsetForName = Charset.forName("UTF-8");
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(...)");
            byte[] bytes = str3.getBytes(charsetForName);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            String strEncodeToString = android.util.Base64.encodeToString(V4AESWithIVDecoderKt.encryptHMACSha1(bytes, android.util.Base64.decode(this.aesKey, 0)), 11);
            if (!Intrinsics.areEqual(str2, strEncodeToString)) {
                throw new CipherException("server hash " + str2 + " and local hash " + strEncodeToString + " dis-match");
            }
            Intrinsics.checkNotNull(string);
            return super.decrypt(string);
        } catch (UnsupportedEncodingException e) {
            UnsupportedEncodingException unsupportedEncodingException = e;
            Log.e("CloudAESWithIVCoder", "decrypt error", unsupportedEncodingException);
            throw new CipherException("decrypt error", unsupportedEncodingException);
        } catch (IllegalArgumentException e2) {
            IllegalArgumentException illegalArgumentException = e2;
            Log.e("CloudAESWithIVCoder", "decrypt error", illegalArgumentException);
            throw new CipherException("decrypt error", illegalArgumentException);
        } catch (NullPointerException e3) {
            NullPointerException nullPointerException = e3;
            Log.e("CloudAESWithIVCoder", "decrypt error", nullPointerException);
            throw new CipherException("decrypt error", nullPointerException);
        } catch (JSONException e4) {
            JSONException jSONException = e4;
            Log.e("CloudAESWithIVCoder", "decrypt error", jSONException);
            throw new CipherException("decrypt error", jSONException);
        }
    }
}
