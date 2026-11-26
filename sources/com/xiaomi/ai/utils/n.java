package com.xiaomi.ai.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.xiaomi.accountsdk.hasheddeviceidlib.HardwareInfo;
import com.xiaomi.ai.mibrain.MibrainUtils;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public class n {
    public static String a = "MiSpeechSDK:VoiceRecognizeUtils";
    private static String b = "aivs_id";
    private static String c = "_id_";

    public static float a(byte[] bArr, int i) {
        short[] sArrA = e.a(bArr, i);
        if (sArrA == null) {
            return -1.0f;
        }
        int length = sArrA.length;
        long jAbs = 0;
        for (short s : sArrA) {
            jAbs += (long) Math.abs((int) s);
        }
        return jAbs / length;
    }

    public static String a() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress()) {
                        return inetAddressNextElement.getHostAddress();
                    }
                }
            }
            return "";
        } catch (SocketException e) {
            Log.MiLogE(a, "SocketException", e);
            return "";
        }
    }

    public static String a(Context context) {
        String string = context.getSharedPreferences(b, 0).getString(c, null);
        Log.MiLogD(a, string == null ? "READ ID null" : "READ ID " + string);
        return string;
    }

    private static String a(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] cArr = new char[4096];
        while (true) {
            int i = reader.read(cArr);
            if (i < 0) {
                return sb.toString();
            }
            sb.append(cArr, 0, i);
        }
    }

    public static String a(String str) {
        String str2;
        String str3;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(bArrDigest.length * 2);
            for (byte b2 : bArrDigest) {
                int i = b2 & 255;
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e = e;
            str2 = a;
            str3 = "UnsupportedEncodingException";
            Log.MiLogE(str2, str3, e);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e = e2;
            str2 = a;
            str3 = "NoSuchAlgorithmException";
            Log.MiLogE(str2, str3, e);
            return null;
        }
    }

    private static void a(Context context, String str) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(b, 0).edit();
        editorEdit.putString(c, str);
        editorEdit.apply();
        Log.MiLogD(a, "SAVE ID " + str);
    }

    public static String b() {
        String strTrim;
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ").getInputStream()));
            String line = "";
            while (true) {
                if (line != null) {
                    line = lineNumberReader.readLine();
                    if (line != null) {
                        strTrim = line.trim();
                        break;
                    }
                    strTrim = "";
                    break;
                }
                strTrim = "";
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strTrim == null || "".equals(strTrim)) {
            try {
                return d("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (c(strTrim)) {
            return null;
        }
        return strTrim;
    }

    public static String b(Context context) {
        boolean z;
        String strA = a(context);
        if (TextUtils.isEmpty(strA) || "d41d8cd98f00b204e9800998ecf8427e".equals(strA)) {
            strA = a(c(context));
            if (TextUtils.isEmpty(strA)) {
                strA = a(b());
                if (TextUtils.isEmpty(strA)) {
                    strA = a(c());
                }
                if (TextUtils.isEmpty(strA)) {
                    try {
                        strA = a(Build.getSerial());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (TextUtils.isEmpty(strA)) {
                    strA = a(UUID.randomUUID().toString());
                }
            }
            z = false;
        } else {
            z = true;
        }
        if (!TextUtils.isEmpty(strA) && !z) {
            a(context, strA);
        }
        return strA;
    }

    public static String b(String str) {
        return MibrainUtils.base64(String.format("{\"d\":\"%s\"}", str).getBytes());
    }

    public static String c() {
        String address;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            try {
                address = defaultAdapter.getAddress();
            } catch (Exception e) {
                e.printStackTrace();
                address = "";
            }
        } else {
            address = "";
        }
        if (c(address)) {
            return null;
        }
        return address;
    }

    public static String c(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager.getPhoneCount() == 0) {
                return null;
            }
            return telephonyManager.getImei(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean c(String str) {
        return HardwareInfo.FAKE_MAC_ADDRESS.equals(str) || "00:00:00:00:00:00".equals(str) || "ff:ff:ff:ff:ff:ff".equals(str) || "FF:FF:FF:FF:FF:FF".equals(str);
    }

    private static String d(String str) throws IOException {
        FileReader fileReader = new FileReader(str);
        String strA = a(fileReader);
        fileReader.close();
        return strA;
    }
}
