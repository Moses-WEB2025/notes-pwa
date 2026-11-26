package com.miui.aiengine.common.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.richeditor.schema.HtmlParser;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import kotlin.text.Typography;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: CloudCoderUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\tH\u0007J\u0014\u0010\n\u001a\u0004\u0018\u00010\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005H\u0007J\u0014\u0010\f\u001a\u0004\u0018\u00010\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u000eH\u0007J<\u0010\u0011\u001a\u0004\u0018\u00010\u00052\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0005J\u0010\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/miui/aiengine/common/utils/CloudCoderUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "hash4SHA1", "plain", "base64Flags", "", "getMd5DigestUpperCase", HtmlParser.InputElement.TAG, "getDataMd5Digest", "data", "", "getHexString", "b", "generateSignature", XMLWriter.METHOD, "requestUrl", "params", "", "security", "generateNonce", "serverTime", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CloudCoderUtils {
    public static final CloudCoderUtils INSTANCE = new CloudCoderUtils();
    private static final String TAG = "CloudCoderUtils";

    public final String hash4SHA1(String str) {
        return hash4SHA1$default(this, str, 0, 2, null);
    }

    private CloudCoderUtils() {
    }

    public static /* synthetic */ String hash4SHA1$default(CloudCoderUtils cloudCoderUtils, String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 2;
        }
        return cloudCoderUtils.hash4SHA1(str, i);
    }

    public final String hash4SHA1(String plain, int base64Flags) {
        if (plain == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            Charset charsetForName = Charset.forName("UTF-8");
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(...)");
            byte[] bytes = plain.getBytes(charsetForName);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            return Base64.encodeToString(messageDigest.digest(bytes), base64Flags);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalStateException("failed to SHA1");
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            throw new IllegalStateException("failed to SHA1");
        }
    }

    @JvmStatic
    public static final String getMd5DigestUpperCase(String input) {
        if (input == null) {
            return null;
        }
        byte[] bytes = input.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        String dataMd5Digest = getDataMd5Digest(bytes);
        if (dataMd5Digest == null) {
            return null;
        }
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
        String upperCase = dataMd5Digest.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        return upperCase;
    }

    @JvmStatic
    public static final String getDataMd5Digest(byte[] data) {
        if (data != null) {
            if (!(data.length == 0)) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.update(data);
                    byte[] bArrDigest = messageDigest.digest();
                    Intrinsics.checkNotNullExpressionValue(bArrDigest, "digest(...)");
                    return getHexString(bArrDigest);
                } catch (Exception e) {
                    Log.e(TAG, "getDataMd5Digest", e);
                }
            }
        }
        return null;
    }

    @JvmStatic
    public static final String getHexString(byte[] b) {
        Intrinsics.checkNotNullParameter(b, "b");
        StringBuilder sb = new StringBuilder();
        int length = b.length;
        for (int i = 0; i < length; i++) {
            int i2 = (b[i] & 240) >> 4;
            sb.append((char) ((i2 < 0 || i2 >= 10) ? i2 + 87 : i2 + 48));
            int i3 = b[i] & 15;
            sb.append((char) ((i3 < 0 || i3 >= 10) ? i3 + 87 : i3 + 48));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final String generateSignature(String method, String requestUrl, Map<String, String> params, String security) {
        if (TextUtils.isEmpty(security)) {
            throw new InvalidParameterException("security is not nullable");
        }
        ArrayList arrayList = new ArrayList();
        if (method != null) {
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
            String upperCase = method.toUpperCase(locale);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            arrayList.add(upperCase);
        }
        if (requestUrl != null) {
            String encodedPath = Uri.parse(requestUrl).getEncodedPath();
            Intrinsics.checkNotNull(encodedPath);
            arrayList.add(encodedPath);
        }
        if (params != null && !params.isEmpty()) {
            Set<Map.Entry> setEntrySet = new TreeMap(params).entrySet();
            Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
            for (Map.Entry entry : setEntrySet) {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format("%s=%s", Arrays.copyOf(new Object[]{entry.getKey(), entry.getValue()}, 2));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                arrayList.add(str);
            }
        }
        Intrinsics.checkNotNull(security);
        arrayList.add(security);
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        boolean z = true;
        while (it.hasNext()) {
            Object next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            String str2 = (String) next;
            if (!z) {
                sb.append(Typography.amp);
            }
            sb.append(str2);
            z = false;
        }
        return hash4SHA1$default(this, sb.toString(), 0, 2, null);
    }

    @JvmStatic
    public static final String generateNonce(long serverTime) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeLong(new Random().nextLong());
            dataOutputStream.writeInt((int) (serverTime / 60000));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String strEncodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
        Intrinsics.checkNotNullExpressionValue(strEncodeToString, "encodeToString(...)");
        return strEncodeToString;
    }
}
