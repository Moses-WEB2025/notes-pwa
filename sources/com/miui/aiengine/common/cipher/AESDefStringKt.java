package com.miui.aiengine.common.cipher;

import android.text.TextUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AESDefString.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a*\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003¨\u0006\u0007"}, d2 = {"parse", "Lcom/miui/aiengine/common/cipher/AESDefString;", "aesString", "", "version", "iv", "data", "net_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class AESDefStringKt {
    public static final AESDefString parse(String aesString) throws InvalidAESDataException {
        Intrinsics.checkNotNullParameter(aesString, "aesString");
        String[] strArrSplit = TextUtils.split(aesString, ":");
        if (strArrSplit != null && strArrSplit.length == 3) {
            String str = strArrSplit[0];
            Intrinsics.checkNotNullExpressionValue(str, "get(...)");
            String str2 = strArrSplit[1];
            Intrinsics.checkNotNullExpressionValue(str2, "get(...)");
            String str3 = strArrSplit[2];
            Intrinsics.checkNotNullExpressionValue(str3, "get(...)");
            return new AESDefString(str, str2, str3);
        }
        throw new InvalidAESDataException("invalid encrypt string format,the correct format is version:iv:content but original string is:" + aesString);
    }

    public static /* synthetic */ AESDefString parse$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            str3 = null;
        }
        return parse(str, str2, str3);
    }

    public static final AESDefString parse(String str, String str2, String str3) throws InvalidAESDataException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            throw new InvalidAESDataException("invalid AES data");
        }
        Intrinsics.checkNotNull(str);
        Intrinsics.checkNotNull(str2);
        Intrinsics.checkNotNull(str3);
        return new AESDefString(str, str2, str3);
    }
}
