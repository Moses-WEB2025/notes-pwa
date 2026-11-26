package com.miui.maml.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import com.miui.floatwindow.frame.AppInstallBroadcastReceiver;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import miuix.core.util.FileUtils;
import org.slf4j.Marker;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class Utils {
    private static ArrayList<String> INTENT_BLACK_LIST = null;
    private static final int INVALID = -2;
    private static int sAcrossUsersFullPermission = -2;
    private static int sAcrossUsersPermission = -2;
    private static Context sAppContext;

    public interface XmlTraverseListener {
        void onChild(Element element);
    }

    public static void initContextIfNeed(Context context) {
        if (sAppContext == null) {
            sAppContext = context;
        }
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static void asserts(boolean z) throws Exception {
        asserts(z, "assert error");
    }

    public static void asserts(boolean z, String str) throws Exception {
        if (!z) {
            throw new Exception(str);
        }
    }

    public static int getAttrAsInt(Element element, String str, int i) {
        String attribute = element.getAttribute(str);
        if (!TextUtils.isEmpty(attribute)) {
            try {
                return Integer.parseInt(attribute);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public static int getAttrAsIntThrows(Element element, String str) throws NumberFormatException {
        return Integer.parseInt(element.getAttribute(str));
    }

    public static long getAttrAsLong(Element element, String str, long j) {
        String attribute = element.getAttribute(str);
        if (!TextUtils.isEmpty(attribute)) {
            try {
                return Long.parseLong(attribute);
            } catch (NumberFormatException unused) {
            }
        }
        return j;
    }

    public static long getAttrAsLongThrows(Element element, String str) throws NumberFormatException {
        return Long.parseLong(element.getAttribute(str));
    }

    public static float getAttrAsFloat(Element element, String str, float f) {
        String attribute = element.getAttribute(str);
        if (!TextUtils.isEmpty(attribute)) {
            try {
                return Float.parseFloat(attribute);
            } catch (NumberFormatException unused) {
            }
        }
        return f;
    }

    public static float getAttrAsFloatThrows(Element element, String str) throws NumberFormatException {
        return Float.parseFloat(element.getAttribute(str));
    }

    public static class GetChildWrapper {
        private Element mEle;

        public GetChildWrapper(Element element) {
            this.mEle = element;
        }

        public Element getElement() {
            return this.mEle;
        }

        public GetChildWrapper getChild(String str) {
            return new GetChildWrapper(Utils.getChild(this.mEle, str));
        }
    }

    public static Element getChild(Element element, String str) {
        if (element == null) {
            return null;
        }
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == 1 && nodeItem.getNodeName().equalsIgnoreCase(str)) {
                return (Element) nodeItem;
            }
        }
        return null;
    }

    public static class Point {
        public double x;
        public double y;

        public Point(double d, double d2) {
            this.x = d;
            this.y = d2;
        }

        public void Offset(Point point) {
            this.x += point.x;
            this.y += point.y;
        }

        Point minus(Point point) {
            return new Point(this.x - point.x, this.y - point.y);
        }
    }

    public static double Dist(Point point, Point point2, boolean z) {
        double d = point.x - point2.x;
        double d2 = point.y - point2.y;
        return z ? Math.sqrt((d * d) + (d2 * d2)) : (d * d) + (d2 * d2);
    }

    public static boolean equals(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static boolean arrayContains(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (equals(str2, str)) {
                return true;
            }
        }
        return false;
    }

    public static Point pointProjectionOnSegment(Point point, Point point2, Point point3, boolean z) {
        Point pointMinus = point2.minus(point);
        Point pointMinus2 = point3.minus(point);
        double dDist = ((pointMinus.x * pointMinus2.x) + (pointMinus.y * pointMinus2.y)) / Dist(point, point2, false);
        if (dDist < 0.0d || dDist > 1.0d) {
            if (z) {
                return dDist < 0.0d ? point : point2;
            }
            return null;
        }
        pointMinus.x *= dDist;
        pointMinus.y *= dDist;
        pointMinus.Offset(point);
        return pointMinus;
    }

    public static String addFileNameSuffix(String str, String str2, String str3) {
        int iIndexOf = str.indexOf(46);
        return iIndexOf == -1 ? str : str.substring(0, iIndexOf) + str2 + str3 + str.substring(iIndexOf);
    }

    public static String addFileNameSuffix(String str, String str2) {
        return addFileNameSuffix(str, "_", str2);
    }

    public static String doubleToString(double d) {
        String strValueOf = String.valueOf(d);
        return strValueOf.endsWith(".0") ? strValueOf.substring(0, strValueOf.length() - 2) : strValueOf;
    }

    public static String numberToString(Number number) {
        String strValueOf = String.valueOf(number);
        return strValueOf.endsWith(".0") ? strValueOf.substring(0, strValueOf.length() - 2) : strValueOf;
    }

    public static double parseDouble(String str) {
        if (str.startsWith(Marker.ANY_NON_NULL_MARKER) && str.length() > 1) {
            str = str.substring(1);
        }
        return Double.parseDouble(str);
    }

    public static double stringToDouble(String str, double d) {
        if (str == null) {
            return d;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException unused) {
            return d;
        }
    }

    public static void traverseXmlElementChildrenTags(Element element, String[] strArr, XmlTraverseListener xmlTraverseListener) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            String nodeName = nodeItem.getNodeName();
            if (nodeItem.getNodeType() == 1 && (strArr == null || arrContains(strArr, nodeName))) {
                xmlTraverseListener.onChild((Element) nodeItem);
            }
        }
    }

    public static void traverseXmlElementChildren(Element element, String str, XmlTraverseListener xmlTraverseListener) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == 1 && (str == null || TextUtils.equals(nodeItem.getNodeName(), str))) {
                xmlTraverseListener.onChild((Element) nodeItem);
            }
        }
    }

    private static boolean arrContains(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (TextUtils.equals(str2, str)) {
                return true;
            }
        }
        return false;
    }

    public static int mixAlpha(int i, int i2) {
        if (i >= 255) {
            i = i2;
        } else if (i2 < 255) {
            i = Math.round((i * i2) / 255.0f);
        }
        return Math.min(255, Math.max(0, i));
    }

    public static void putVariableString(String str, Variables variables, String str2) {
        variables.put(str, str2);
    }

    public static void putVariableNumber(String str, Variables variables, Double d) {
        variables.put(str, d.doubleValue());
    }

    public static void putVariableNumber(String str, Variables variables, double d) {
        variables.put(str, d);
    }

    public static double getVariableNumber(String str, Variables variables) {
        return new IndexedVariable(str, variables, true).getDouble();
    }

    public static String getVariableString(String str, Variables variables) {
        return new IndexedVariable(str, variables, false).getString();
    }

    public static PorterDuff.Mode getPorterDuffMode(String str) {
        if (TextUtils.isEmpty(str)) {
            return PorterDuff.Mode.SRC_OVER;
        }
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_OVER;
        for (PorterDuff.Mode mode2 : PorterDuff.Mode.values()) {
            if (str.equalsIgnoreCase(mode2.name())) {
                return mode2;
            }
        }
        return mode;
    }

    public static PorterDuff.Mode getPorterDuffMode(int i) {
        return getPorterDuffMode(i, PorterDuff.Mode.SRC_OVER);
    }

    public static PorterDuff.Mode getPorterDuffMode(int i, PorterDuff.Mode mode) {
        for (PorterDuff.Mode mode2 : PorterDuff.Mode.values()) {
            if (mode2.ordinal() == i) {
                return mode2;
            }
        }
        return mode;
    }

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        INTENT_BLACK_LIST = arrayList;
        arrayList.add("android.intent.action.AIRPLANE_MODE");
        INTENT_BLACK_LIST.add("android.intent.action.BATTERY_CHANGED");
        INTENT_BLACK_LIST.add("android.intent.action.BATTERY_LOW");
        INTENT_BLACK_LIST.add("android.intent.action.BATTERY_OKAY");
        INTENT_BLACK_LIST.add("android.intent.action.BOOT_COMPLETED");
        INTENT_BLACK_LIST.add("android.intent.action.CONFIGURATION_CHANGED");
        INTENT_BLACK_LIST.add("android.intent.action.DEVICE_STORAGE_LOW");
        INTENT_BLACK_LIST.add("android.intent.action.DEVICE_STORAGE_OK");
        INTENT_BLACK_LIST.add("android.intent.action.DREAMING_STARTED");
        INTENT_BLACK_LIST.add("android.intent.action.DREAMING_STOPPED");
        INTENT_BLACK_LIST.add("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        INTENT_BLACK_LIST.add("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        INTENT_BLACK_LIST.add("android.intent.action.LOCALE_CHANGED");
        INTENT_BLACK_LIST.add("android.intent.action.MY_PACKAGE_REPLACED");
        INTENT_BLACK_LIST.add("android.intent.action.NEW_OUTGOING_CALL");
        INTENT_BLACK_LIST.add("android.intent.action.PACKAGE_ADDED");
        INTENT_BLACK_LIST.add("android.intent.action.PACKAGE_CHANGED");
        INTENT_BLACK_LIST.add("android.intent.action.PACKAGE_DATA_CLEARED");
        INTENT_BLACK_LIST.add("android.intent.action.PACKAGE_FIRST_LAUNCH");
        INTENT_BLACK_LIST.add("android.intent.action.PACKAGE_FULLY_REMOVED");
        INTENT_BLACK_LIST.add("android.intent.action.PACKAGE_INSTALL");
        INTENT_BLACK_LIST.add("android.intent.action.PACKAGE_NEEDS_VERIFICATION");
        INTENT_BLACK_LIST.add(AppInstallBroadcastReceiver.PACKAGE_REMOVED_ACTION);
        INTENT_BLACK_LIST.add(AppInstallBroadcastReceiver.PACKAGE_REPLACED_ACTION);
        INTENT_BLACK_LIST.add("android.intent.action.PACKAGE_RESTARTED");
        INTENT_BLACK_LIST.add("android.intent.action.PACKAGE_VERIFIED");
        INTENT_BLACK_LIST.add("android.intent.action.ACTION_POWER_CONNECTED");
        INTENT_BLACK_LIST.add("android.intent.action.ACTION_POWER_DISCONNECTED");
        INTENT_BLACK_LIST.add("android.intent.action.REBOOT");
        INTENT_BLACK_LIST.add("android.intent.action.SCREEN_OFF");
        INTENT_BLACK_LIST.add("android.intent.action.SCREEN_ON");
        INTENT_BLACK_LIST.add("android.intent.action.ACTION_SHUTDOWN");
        INTENT_BLACK_LIST.add("android.intent.action.TIMEZONE_CHANGED");
        INTENT_BLACK_LIST.add("android.intent.action.TIME_TICK");
        INTENT_BLACK_LIST.add("android.intent.action.UID_REMOVED");
        INTENT_BLACK_LIST.add("android.intent.action.USER_PRESENT");
    }

    public static boolean isProtectedIntent(String str) {
        if (str == null) {
            return false;
        }
        return INTENT_BLACK_LIST.contains(str.trim());
    }

    public static String[] splitStringArray(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.split(str2);
    }

    public static double[] splitDoubleArray(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(",");
        int length = strArrSplit.length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            try {
                dArr[i] = Double.parseDouble(strArrSplit[i]);
            } catch (NumberFormatException unused) {
            }
        }
        return dArr;
    }

    public static int[] splitIntArray(String str) {
        return splitIntArray(str, 10);
    }

    public static int[] splitIntArray(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(",");
        int length = strArrSplit.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            try {
                iArr[i2] = Integer.parseInt(strArrSplit[i2], i);
            } catch (NumberFormatException unused) {
            }
        }
        return iArr;
    }

    public static byte[] splitByteArray(String str) {
        return splitByteArray(str, 10);
    }

    public static byte[] splitByteArray(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(",");
        int length = strArrSplit.length;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            try {
                bArr[i2] = Byte.parseByte(strArrSplit[i2], i);
            } catch (NumberFormatException unused) {
            }
        }
        return bArr;
    }

    public static void startActivity(Context context, Intent intent, Bundle bundle) {
        boolean zEquals = "com.android.systemui".equals(context.getPackageName());
        if (zEquals && sAcrossUsersFullPermission == -2) {
            sAcrossUsersFullPermission = context.checkSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL");
        }
        if (zEquals && sAcrossUsersFullPermission == 0) {
            HideSdkDependencyUtils.Context_startActivityAsUser(context, intent, bundle, HideSdkDependencyUtils.UserHandle_CURRENT());
        } else {
            context.startActivity(intent, bundle);
        }
    }

    public static void sendBroadcast(Context context, Intent intent) {
        if (sAcrossUsersPermission == -2) {
            sAcrossUsersPermission = context.checkSelfPermission("android.permission.INTERACT_ACROSS_USERS");
        }
        if (sAcrossUsersPermission == 0) {
            context.sendBroadcastAsUser(intent, HideSdkDependencyUtils.UserHandle_CURRENT());
        } else {
            context.sendBroadcast(intent);
        }
    }

    public static void startService(Context context, Intent intent) {
        if (sAcrossUsersPermission == -2) {
            sAcrossUsersPermission = context.checkSelfPermission("android.permission.INTERACT_ACROSS_USERS");
        }
        if (sAcrossUsersPermission == 0) {
            HideSdkDependencyUtils.Context_startServiceAsUser(context, intent, HideSdkDependencyUtils.UserHandle_CURRENT());
        } else {
            context.startService(intent);
        }
    }

    public static void triggerGC() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
    }

    public static boolean onMuiltDisplayType2() {
        return SystemProperties.getInt("persist.sys.muiltdisplay_type", 0) == 2;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static void copyFile(String str, String str2) {
        FileUtils.copyFile(new File(str), new File(str2));
    }

    public static void writeDataToFile(String str, String str2) {
        if (isEmpty(str2)) {
            return;
        }
        try {
            FileUtils.stringToFile(str, str2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean removeFile(String str) {
        return rm(str);
    }

    public static boolean rm(String str) {
        File[] fileArrListFiles;
        if (isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null && fileArrListFiles.length > 0) {
            for (File file2 : fileArrListFiles) {
                rm(file2.getPath());
            }
        }
        return file.delete();
    }
}
