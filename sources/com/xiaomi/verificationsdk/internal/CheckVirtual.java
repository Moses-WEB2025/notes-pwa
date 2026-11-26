package com.xiaomi.verificationsdk.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CheckVirtual {
    private static final String TAG = "CheckVirtual";

    public static boolean isRunInVirtual() throws Throwable {
        String[] strArrSplit;
        String uidStrFormat = getUidStrFormat();
        if (uidStrFormat == null) {
            throw new UnknownValueException("getUidStrFormat is null");
        }
        String strExec = exec("ps");
        if (strExec != null && !strExec.isEmpty() && (strArrSplit = strExec.split(StringUtils.LF)) != null && strArrSplit.length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                if (strArrSplit[i2].contains(uidStrFormat)) {
                    int iLastIndexOf = strArrSplit[i2].lastIndexOf(StringUtils.SPACE);
                    String str = strArrSplit[i2];
                    if (new File(String.format("/data/data/%s", str.substring(iLastIndexOf <= 0 ? 0 : iLastIndexOf + 1, str.length()), Locale.CHINA)).exists()) {
                        i++;
                    }
                }
            }
            if (i > 1) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:55:0x0090  */
    /* JADX WARN: Code duplicated, block: B:60:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x007c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v8 */
    private static String exec(String str) throws Throwable {
        Process processExec;
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        BufferedInputStream bufferedInputStream2;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            processExec = Runtime.getRuntime().exec("sh");
            try {
                bufferedOutputStream = new BufferedOutputStream(processExec.getOutputStream());
                try {
                    bufferedInputStream2 = new BufferedInputStream(processExec.getInputStream());
                    try {
                        bufferedOutputStream.write(str.getBytes());
                        bufferedOutputStream.write(10);
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                        processExec.waitFor();
                        String strFromBufferInputSteam = getStrFromBufferInputSteam(bufferedInputStream2);
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            bufferedInputStream2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        if (processExec != null) {
                            processExec.destroy();
                        }
                        return strFromBufferInputSteam;
                    } catch (Exception unused) {
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (bufferedInputStream2 != 0) {
                            try {
                                bufferedInputStream2.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (processExec != null) {
                            processExec.destroy();
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream2 = bufferedOutputStream;
                        bufferedInputStream = bufferedInputStream2;
                        if (bufferedOutputStream2 != null) {
                            try {
                                bufferedOutputStream2.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        if (bufferedInputStream != 0) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        if (processExec != null) {
                            processExec.destroy();
                            throw th;
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                    bufferedInputStream2 = 0;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream2 = 0;
                }
            } catch (Exception unused3) {
                bufferedOutputStream = null;
                bufferedInputStream2 = bufferedOutputStream;
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (bufferedInputStream2 != 0) {
                    bufferedInputStream2.close();
                }
                if (processExec != null) {
                    processExec.destroy();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream = 0;
            }
        } catch (Exception unused4) {
            processExec = null;
            bufferedOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            processExec = null;
            bufferedInputStream = 0;
        }
    }

    private static String getStrFromBufferInputSteam(BufferedInputStream bufferedInputStream) {
        int i;
        if (bufferedInputStream == null) {
            return "";
        }
        byte[] bArr = new byte[512];
        StringBuilder sb = new StringBuilder();
        do {
            try {
                i = bufferedInputStream.read(bArr);
                if (i > 0) {
                    sb.append(new String(bArr, 0, i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (i >= 512);
        return sb.toString();
    }

    public static String getUidStrFormat() throws Throwable {
        String strExec = exec("cat /proc/self/cgroup");
        if (strExec != null && strExec.length() != 0) {
            int iLastIndexOf = strExec.lastIndexOf("uid");
            int iLastIndexOf2 = strExec.lastIndexOf("/pid");
            if (iLastIndexOf < 0) {
                return null;
            }
            if (iLastIndexOf2 <= 0) {
                iLastIndexOf2 = strExec.length();
            }
            try {
                String strReplaceAll = strExec.substring(iLastIndexOf + 4, iLastIndexOf2).replaceAll(StringUtils.LF, "");
                if (isNumber(strReplaceAll)) {
                    return String.format("u0_a%d", Integer.valueOf(Integer.valueOf(strReplaceAll).intValue() - 10000));
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isNumber(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
