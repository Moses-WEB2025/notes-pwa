package com.miui.notes.provider;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.miai.SDKConstants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes4.dex */
public class SignatureValidator {
    private static final String MIUI_PACKAGE_HASH_RELEASE = "c9009d01ebf9f5d0302bc71b2fe9aa9a47a432bba17308a3111b75d7b2149025";
    private static final String PACKAGE_HASH_1 = "b031fe98a4dbb0d4d82661787f25de643182b378e9ef632d8adea75aab58f2d8";
    private static final String PACKAGE_HASH_2 = "d45f076fe23a1a5b7f486e3ff41547a2023dbfe1fe73353b1e48ebdfed72cc6f";
    private static final String PACKAGE_HASH_3 = "93d3dd258a01146851ca1b087f6094f394cdfa88bb09d7e1afcd58c5327dda7e";
    private static final String PACKAGE_HASH_4 = "5884e83fa0135f0a4042ff868e3eb6fe809b90627bc90c11e20f9daf5f915530";
    private static final String TAG = "SignatureValidator";
    private static final HashSet<String> validAppSignatureHashes = buildAppSignatureHashes();

    private static HashSet<String> buildAppSignatureHashes() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add(MIUI_PACKAGE_HASH_RELEASE);
        hashSet.add(PACKAGE_HASH_1);
        hashSet.add(PACKAGE_HASH_2);
        hashSet.add(PACKAGE_HASH_3);
        hashSet.add(PACKAGE_HASH_4);
        SystemProperties.get("ro.miui.ui.version.name");
        return hashSet;
    }

    public static boolean validateSignature(Context context, String str) {
        String str2 = Build.BRAND;
        int i = context.getApplicationInfo().flags;
        if (!isReleaseRom()) {
            return true;
        }
        if (str2.startsWith("generic") && (i & 2) != 0) {
            return true;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 64);
            if (packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                for (Signature signature : packageInfo.signatures) {
                    if (!validAppSignatureHashes.contains(sha256(signature.toByteArray()))) {
                        return false;
                    }
                }
                return true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    private static boolean isReleaseRom() {
        try {
            String str = SystemProperties.get("ro.build.tags");
            Logger.INSTANCE.d(TAG, "rom sign:" + str);
            return str.contains("release-keys");
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "observe rom sign exception:" + e);
            return false;
        }
    }

    public static String sha256(byte[] bArr) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance(SDKConstants.DigestAlgorithm.SHA_256).digest(bArr);
            StringBuilder sb = new StringBuilder(bArrDigest.length * 2);
            for (byte b : bArrDigest) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
