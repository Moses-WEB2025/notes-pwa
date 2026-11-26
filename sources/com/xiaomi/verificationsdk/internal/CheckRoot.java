package com.xiaomi.verificationsdk.internal;

import android.os.Build;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class CheckRoot {
    private static String LOG_TAG = "com.xiaomi.verificationsdk.internal.CheckRoot";

    public static boolean isDeviceRooted() {
        return checkDeviceDebuggable() || checkSuperuserApk() || checkRootPathSU() || checkRootWhichSU() || checkAccessRootData();
    }

    public static boolean checkDeviceDebuggable() {
        String str = Build.TAGS;
        if (str == null || !str.contains("test-keys")) {
            return false;
        }
        AccountLogger.log(LOG_TAG, "buildTags=" + str);
        return true;
    }

    public static boolean checkSuperuserApk() {
        try {
            if (!new File("/system/app/Superuser.apk").exists()) {
                return false;
            }
            AccountLogger.log(LOG_TAG, "/system/app/Superuser.apk exist");
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean checkRootPathSU() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        for (int i = 0; i < 5; i++) {
            try {
                if (new File(strArr[i] + "su").exists()) {
                    AccountLogger.log(LOG_TAG, "find su in : " + strArr[i]);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean checkRootWhichSU() {
        ArrayList<String> arrayListExecuteCommand = executeCommand(new String[]{"/system/xbin/which", "su"});
        if (arrayListExecuteCommand != null) {
            AccountLogger.log(LOG_TAG, "execResult=" + arrayListExecuteCommand.toString());
            return true;
        }
        AccountLogger.log(LOG_TAG, "execResult=null");
        return false;
    }

    public static ArrayList<String> executeCommand(String[] strArr) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            AccountLogger.log(LOG_TAG, "to shell exec which for find su :");
            Process processExec = Runtime.getRuntime().exec(strArr);
            new BufferedWriter(new OutputStreamWriter(processExec.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    AccountLogger.log(LOG_TAG, "–> Line received: " + line);
                    arrayList.add(line);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            AccountLogger.log(LOG_TAG, "–> Full response was: " + arrayList);
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    public static synchronized boolean checkGetRootAuth() {
        Process processExec;
        DataOutputStream dataOutputStream = null;
        try {
            try {
                AccountLogger.log(LOG_TAG, "to exec su");
                processExec = Runtime.getRuntime().exec("su");
                try {
                    DataOutputStream dataOutputStream2 = new DataOutputStream(processExec.getOutputStream());
                    try {
                        dataOutputStream2.writeBytes("exit\n");
                        dataOutputStream2.flush();
                        int iWaitFor = processExec.waitFor();
                        AccountLogger.log(LOG_TAG, "exitValue=" + iWaitFor);
                        if (iWaitFor == 0) {
                            try {
                                dataOutputStream2.close();
                                processExec.destroy();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return true;
                        }
                        try {
                            dataOutputStream2.close();
                            processExec.destroy();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        return false;
                    } catch (Exception e3) {
                        e = e3;
                        dataOutputStream = dataOutputStream2;
                        AccountLogger.log(LOG_TAG, "Unexpected error - Here is what I know: " + e.getMessage());
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                                return false;
                            }
                        }
                        processExec.destroy();
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        dataOutputStream = dataOutputStream2;
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (Exception e5) {
                                e5.printStackTrace();
                                throw th;
                            }
                        }
                        processExec.destroy();
                        throw th;
                    }
                } catch (Exception e6) {
                    e = e6;
                }
            } catch (Exception e7) {
                e = e7;
                processExec = null;
            } catch (Throwable th2) {
                th = th2;
                processExec = null;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        throw th;
    }

    public static synchronized boolean checkBusybox() {
        try {
            AccountLogger.log(LOG_TAG, "to exec busybox df");
            ArrayList<String> arrayListExecuteCommand = executeCommand(new String[]{"busybox", "df"});
            if (arrayListExecuteCommand != null) {
                AccountLogger.log(LOG_TAG, "execResult=" + arrayListExecuteCommand.toString());
                return true;
            }
            AccountLogger.log(LOG_TAG, "execResult=null");
            return false;
        } catch (Exception e) {
            AccountLogger.log(LOG_TAG, "Unexpected error - Here is what I know: " + e.getMessage());
            return false;
        }
    }

    public static synchronized boolean checkAccessRootData() {
        try {
            AccountLogger.log(LOG_TAG, "to write /data");
            if (writeFile("/data/su_test", "test_ok").booleanValue()) {
                AccountLogger.log(LOG_TAG, "write ok");
            } else {
                AccountLogger.log(LOG_TAG, "write failed");
            }
            AccountLogger.log(LOG_TAG, "to read /data");
            String file = readFile("/data/su_test");
            AccountLogger.log(LOG_TAG, "strRead=" + file);
            return "test_ok".equals(file);
        } catch (Exception e) {
            AccountLogger.log(LOG_TAG, "Unexpected error - Here is what I know: " + e.getMessage());
            return false;
        }
    }

    public static Boolean writeFile(String str, String str2) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            fileOutputStream.write(str2.getBytes());
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readFile(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            byte[] bArr = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i > 0) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    String str2 = new String(byteArrayOutputStream.toByteArray());
                    AccountLogger.log(LOG_TAG, str2);
                    return str2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
