package com.xiaomi.passport.ui.diagnosis;

import androidx.collection.ArrayMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.regex.Pattern;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PingUtil {
    private static final String ipRegex = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";

    public static String getIPFromUrl(String str) throws Throwable {
        String domain = getDomain(str);
        if (domain == null) {
            return null;
        }
        if (isMatch(ipRegex, domain)) {
            return domain;
        }
        String strPing = ping(createSimplePingCommand(1, 100, domain));
        if (strPing != null) {
            try {
                String strSubstring = strPing.substring(strPing.indexOf("from") + 5);
                return strSubstring.substring(0, strSubstring.indexOf("icmp_seq") - 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getMinRTT(String str) {
        return getMinRTT(str, 1, 100);
    }

    public static int getAvgRTT(String str) {
        return getAvgRTT(str, 1, 100);
    }

    public static int getMaxRTT(String str) {
        return getMaxRTT(str, 1, 100);
    }

    public static int getMdevRTT(String str) {
        return getMdevRTT(str, 1, 100);
    }

    public static int getMinRTT(String str, int i, int i2) {
        String strPing;
        String domain = getDomain(str);
        if (domain != null && (strPing = ping(createSimplePingCommand(i, i2, domain))) != null) {
            try {
                return Math.round(Float.valueOf(strPing.substring(strPing.indexOf("min/avg/max/mdev") + 19).split(InternalZipConstants.ZIP_FILE_SEPARATOR)[0]).floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static int getAvgRTT(String str, int i, int i2) {
        String strPing;
        String domain = getDomain(str);
        if (domain != null && (strPing = ping(createSimplePingCommand(i, i2, domain))) != null) {
            try {
                return Math.round(Float.valueOf(strPing.substring(strPing.indexOf("min/avg/max/mdev") + 19).split(InternalZipConstants.ZIP_FILE_SEPARATOR)[1]).floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static int getMaxRTT(String str, int i, int i2) {
        String strPing;
        String domain = getDomain(str);
        if (domain != null && (strPing = ping(createSimplePingCommand(i, i2, domain))) != null) {
            try {
                return Math.round(Float.valueOf(strPing.substring(strPing.indexOf("min/avg/max/mdev") + 19).split(InternalZipConstants.ZIP_FILE_SEPARATOR)[2]).floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static int getMdevRTT(String str, int i, int i2) {
        String strPing;
        String domain = getDomain(str);
        if (domain != null && (strPing = ping(createSimplePingCommand(i, i2, domain))) != null) {
            try {
                return Math.round(Float.valueOf(strPing.substring(strPing.indexOf("min/avg/max/mdev") + 19).split(InternalZipConstants.ZIP_FILE_SEPARATOR)[3].replace(" ms", "")).floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static float getPacketLossFloat(String str) {
        String packetLoss = getPacketLoss(str);
        if (packetLoss == null) {
            return -1.0f;
        }
        try {
            return Float.valueOf(packetLoss.replace("%", "")).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0f;
        }
    }

    public static float getPacketLossFloat(String str, int i, int i2) {
        String packetLoss = getPacketLoss(str, i, i2);
        if (packetLoss == null) {
            return -1.0f;
        }
        try {
            return Float.valueOf(packetLoss.replace("%", "")).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0f;
        }
    }

    public static String getPacketLoss(String str) {
        return getPacketLoss(str, 1, 100);
    }

    public static String getPacketLoss(String str, int i, int i2) {
        String strPing;
        String domain = getDomain(str);
        if (domain != null && (strPing = ping(createSimplePingCommand(i, i2, domain))) != null) {
            try {
                String strSubstring = strPing.substring(strPing.indexOf("received,"));
                return strSubstring.substring(9, strSubstring.indexOf("packet"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getPingString(String str, int i, int i2) {
        return ping(createSimplePingCommand(i, i2, str));
    }

    private static String getDomain(String str) {
        try {
            return URI.create(str).getHost();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isMatch(String str, String str2) {
        return Pattern.matches(str, str2);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0050  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v2 */
    private static String ping(String str) throws Throwable {
        Throwable th;
        Process processExec;
        try {
            try {
                processExec = Runtime.getRuntime().exec(str);
                try {
                    InputStream inputStream = processExec.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                        sb.append(StringUtils.LF);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    String string = sb.toString();
                    if (processExec != null) {
                        processExec.destroy();
                    }
                    return string;
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    if (processExec != null) {
                        processExec.destroy();
                    }
                    return null;
                }
            } catch (IOException e2) {
                e = e2;
                processExec = null;
            } catch (Throwable th2) {
                th = th2;
                str = 0;
                if (str != 0) {
                    str.destroy();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (str != 0) {
                str.destroy();
            }
            throw th;
        }
    }

    private static String createSimplePingCommand(int i, int i2, String str) {
        return "/system/bin/ping -c " + i + " -w " + i2 + StringUtils.SPACE + str;
    }

    private static String createPingCommand(ArrayMap<String, String> arrayMap, String str) {
        int size = arrayMap.getSize();
        String strConcat = "/system/bin/ping";
        for (int i = 0; i < size; i++) {
            strConcat = strConcat.concat(StringUtils.SPACE + arrayMap.keyAt(i) + StringUtils.SPACE + arrayMap.get(arrayMap.keyAt(i)));
        }
        return strConcat.concat(StringUtils.SPACE + str);
    }
}
