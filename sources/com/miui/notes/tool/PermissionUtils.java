package com.miui.notes.tool;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.hardware.DataSpace;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class PermissionUtils {
    private static final String APP_PERMISSION_EXTRA_PKG = "extra_pkgname";
    private static final String APP_PERMISSION_MANAGE_ACTION = "miui.intent.action.APP_PERM_EDITOR";
    private static final String APP_PERMISSION_MANAGE_PKG = "com.miui.securitycenter";
    public static final int CTA_REQUEST_CODE = 200;
    public static final int CTA_RESULT_AGREE = 1;
    public static final int CTA_RESULT_LOCAL_CHANGE = -3;
    public static final int CTA_RESULT_REJECT = 666;
    public static final int PERMISSION_FLAG_ASK = 1;
    public static final int PERMISSION_FLAG_DENIED = -1;
    public static final int PERMISSION_FLAG_FOREGROUND = 2;
    public static final int PERMISSION_FLAG_GRANTED = 0;
    public static final int PERMISSION_REQUEST_CLOUD = 1;
    public static final int PERMISSION_REQUEST_HOMEPAGE = 0;
    public static final int PERMISSION_REQUEST_NOTIFICATIONS = 6;
    public static final int PERMISSION_REQUEST_RECORD = 2;
    public static final int PERMISSION_REQUEST_RECORD_FROM_SHORTCUT = 3;
    public static final int PERMISSION_REQUEST_STORAGE = 4;
    private static Map<String, Boolean> sPermissionsRequestFlag = new ArrayMap();
    private static Map<String, Boolean> sPermissionsOnceRequestFlag = new ArrayMap();
    private static AcceptListener sGetAccountPermListener = null;
    private static AcceptListener sRecordPermListener = null;
    private static AcceptListener sStoragePermListener = null;
    private static AcceptListener sContactPermListener = null;
    private static AcceptListener sCtaListener = null;

    public interface AcceptListener {
        void accept();

        void reject();
    }

    private static void setRequestPermissionFlag(String str, boolean z) {
        sPermissionsRequestFlag.put(str, Boolean.valueOf(z));
    }

    private static void setOnceRequestPermissionFlag(String str, boolean z) {
        sPermissionsOnceRequestFlag.put(str, Boolean.valueOf(z));
    }

    public static void initPermissionsFlagGroup() {
        sPermissionsRequestFlag.put("android.permission.READ_CONTACTS", true);
        sPermissionsRequestFlag.put("android.permission.GET_ACCOUNTS_PRIVILEGED", true);
        sPermissionsRequestFlag.put("android.permission-group.STORAGE", true);
        sPermissionsRequestFlag.put("android.permission.RECORD_AUDIO", true);
        sPermissionsRequestFlag.put("android.permission.READ_PHONE_STATE", true);
        sPermissionsRequestFlag.put("android.permission.BLUETOOTH", true);
        sPermissionsRequestFlag.put("android.permission.READ_CALENDAR", true);
        sPermissionsRequestFlag.put("android.permission.WRITE_CALENDAR", true);
        sPermissionsRequestFlag.put("android.permission.WRITE_EXTERNAL_STORAGE", true);
        if (Utils.isAboveSDK33()) {
            sPermissionsRequestFlag.put("android.permission.READ_MEDIA_IMAGES", true);
        }
        sPermissionsOnceRequestFlag.put("android.permission.READ_CONTACTS", true);
        sPermissionsOnceRequestFlag.put("android.permission.GET_ACCOUNTS_PRIVILEGED", true);
        sPermissionsOnceRequestFlag.put("android.permission-group.STORAGE", true);
        sPermissionsOnceRequestFlag.put("android.permission.RECORD_AUDIO", true);
        sPermissionsOnceRequestFlag.put("android.permission.READ_PHONE_STATE", true);
        sPermissionsOnceRequestFlag.put("android.permission.BLUETOOTH", true);
        sPermissionsOnceRequestFlag.put("android.permission.READ_CALENDAR", true);
        sPermissionsOnceRequestFlag.put("android.permission.WRITE_CALENDAR", true);
        sPermissionsOnceRequestFlag.put("android.permission.WRITE_EXTERNAL_STORAGE", true);
        if (Utils.isAboveSDK33()) {
            sPermissionsOnceRequestFlag.put("android.permission.READ_MEDIA_IMAGES", true);
        }
    }

    public static boolean isPermissionNotGranted(Context context, String str) {
        return ActivityCompat.checkSelfPermission(context, str) != 0;
    }

    public static void resetPermissionRequestFlag(String str) {
        setRequestPermissionFlag(str, true);
    }

    public static boolean isPermissionNeitherRejected(String str) {
        Boolean bool = sPermissionsRequestFlag.get(str);
        if (bool != null) {
            return !bool.booleanValue();
        }
        return true;
    }

    public static boolean shouldRequestPermission(Activity activity, String str) {
        if (!isPermissionNotGranted(activity, str)) {
            return false;
        }
        Boolean bool = sPermissionsOnceRequestFlag.get(str);
        if (bool != null) {
            return bool.booleanValue() && !isPermissionNeitherRejected(str);
        }
        return !isPermissionNeitherRejected(str);
    }

    public static boolean shouldOnceRequestPermission(Activity activity, String str) {
        if (isPermissionNotGranted(activity, str)) {
            return !isPermissionNeitherRejected(str);
        }
        return false;
    }

    public static void processPermissionResult(Activity activity, String[] strArr, int[] iArr) {
        for (int i = 0; i < strArr.length; i++) {
            if (iArr[i] == -1) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, strArr[i])) {
                    setRequestPermissionFlag(strArr[i], false);
                } else {
                    setOnceRequestPermissionFlag(strArr[i], false);
                }
            }
        }
    }

    public static boolean supportNewPermissionStyle() {
        try {
            return NoteApp.getInstance().getPackageManager().queryIntentActivities(new Intent("miui.intent.action.SYSTEM_PERMISSION_DECLARE_NEW"), 0).size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showCtaDialog(Activity activity) {
        String string;
        String[] strArr;
        String[] strArr2;
        if (RomUtils.isPadDevice()) {
            string = activity.getString(R.string.permission_storage_desc_pad_new_v12);
        } else {
            string = activity.getString(R.string.permission_storage_desc_new_v12);
        }
        if (LiteUtils.isSuperLite()) {
            strArr2 = new String[]{"android.permission.READ_CONTACTS"};
            strArr = new String[]{activity.getString(R.string.permission_read_contact_desc)};
        } else {
            strArr = new String[]{activity.getString(R.string.permission_read_contact_desc), activity.getString(R.string.permission_microphone_desc), string};
            strArr2 = new String[]{"android.permission.READ_CONTACTS", "android.permission-group.MICROPHONE", "android.permission-group.STORAGE"};
        }
        Intent intent = new Intent("miui.intent.action.SYSTEM_PERMISSION_DECLARE_NEW");
        intent.putExtra("all_purpose", activity.getResources().getString(LiteUtils.isSuperLite() ? R.string.cta_v2_app_purpose_v12_lite : R.string.cta_v2_app_purpose_v12));
        intent.putExtra("mandatory_permission", false);
        intent.putExtra("use_network", true);
        if (UIUtils.isMiuiXIISdkSupported()) {
            intent.putExtra("optional_perm", strArr2);
            intent.putExtra("optional_perm_desc", strArr);
            intent.putExtra("optional_perm_show", false);
            intent.putExtra("agree_desc", activity.getResources().getString(R.string.cta_agree_desc));
        } else {
            intent.putExtra("runtime_perm", strArr2);
            intent.putExtra("runtime_perm_desc", strArr);
        }
        intent.setPackage("com.miui.securitycenter");
        activity.startActivityForResult(intent, 200);
    }

    public static void showCtaDialogWithCallback(Activity activity, AcceptListener acceptListener) {
        String string;
        String[] strArr;
        String[] strArr2;
        if (RomUtils.isPadDevice()) {
            string = activity.getString(R.string.permission_storage_desc_pad_new_v12);
        } else {
            string = activity.getString(R.string.permission_storage_desc_new_v12);
        }
        if (LiteUtils.isSuperLite()) {
            strArr2 = new String[]{"android.permission.READ_CONTACTS"};
            strArr = new String[]{activity.getString(R.string.permission_read_contact_desc)};
        } else {
            strArr = new String[]{activity.getString(R.string.permission_read_contact_desc), activity.getString(R.string.permission_microphone_desc), string};
            strArr2 = new String[]{"android.permission.READ_CONTACTS", "android.permission-group.MICROPHONE", "android.permission-group.STORAGE"};
        }
        Intent intent = new Intent("miui.intent.action.SYSTEM_PERMISSION_DECLARE_NEW");
        intent.putExtra("all_purpose", activity.getResources().getString(LiteUtils.isSuperLite() ? R.string.cta_v2_app_purpose_v12_lite : R.string.cta_v2_app_purpose_v12));
        intent.putExtra("mandatory_permission", false);
        intent.putExtra("use_network", true);
        if (UIUtils.isMiuiXIISdkSupported()) {
            intent.putExtra("optional_perm", strArr2);
            intent.putExtra("optional_perm_desc", strArr);
            intent.putExtra("optional_perm_show", false);
            intent.putExtra("agree_desc", activity.getResources().getString(R.string.cta_agree_desc));
        } else {
            intent.putExtra("runtime_perm", strArr2);
            intent.putExtra("runtime_perm_desc", strArr);
        }
        intent.setPackage("com.miui.securitycenter");
        sCtaListener = acceptListener;
        activity.startActivityForResult(intent, 200);
    }

    public static void processCtaResult(Activity activity, int i) {
        AcceptListener acceptListener;
        if (i == -3) {
            showCtaDialog(activity);
            return;
        }
        if (i != 1) {
            if (i == 666 && (acceptListener = sCtaListener) != null) {
                acceptListener.reject();
                sCtaListener = null;
                return;
            }
            return;
        }
        AcceptListener acceptListener2 = sCtaListener;
        if (acceptListener2 != null) {
            acceptListener2.accept();
            sCtaListener = null;
        }
    }

    public static void restoreCtaListenerFromRebuild(AcceptListener acceptListener) {
        sCtaListener = acceptListener;
    }

    public static void processOnHomePage(Activity activity, String[] strArr, int[] iArr) {
        processPermissionResult(activity, strArr, iArr);
    }

    public static void requestOnClickCloudView(final Activity activity, final Fragment fragment, final AcceptListener acceptListener) {
        if (!PreferenceUtils.isCTAAccepted(activity.getApplicationContext())) {
            showCtaDialogWithCallback(activity, new AcceptListener() { // from class: com.miui.notes.tool.PermissionUtils.1
                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void reject() {
                }

                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void accept() {
                    PermissionUtils.requestOnClickCloudView(activity, fragment, acceptListener);
                }
            });
        } else {
            acceptListener.accept();
        }
    }

    public static void processOnClickCloudView(Activity activity, String[] strArr, int[] iArr) {
        AcceptListener acceptListener;
        processPermissionResult(activity, strArr, iArr);
        if (iArr.length == 0) {
            AcceptListener acceptListener2 = sGetAccountPermListener;
            if (acceptListener2 != null) {
                acceptListener2.reject();
                sGetAccountPermListener = null;
                return;
            }
            return;
        }
        int i = iArr[0];
        if (i != -1) {
            if (i != 0 || (acceptListener = sGetAccountPermListener) == null) {
                return;
            }
            acceptListener.accept();
            sGetAccountPermListener = null;
            return;
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, strArr[0])) {
            setRequestPermissionFlag(strArr[0], false);
        } else {
            setOnceRequestPermissionFlag(strArr[0], false);
        }
        AcceptListener acceptListener3 = sGetAccountPermListener;
        if (acceptListener3 != null) {
            acceptListener3.reject();
            sGetAccountPermListener = null;
        }
    }

    public static void processOnAudioNote(Activity activity, String[] strArr, int[] iArr) {
        AcceptListener acceptListener;
        processPermissionResult(activity, strArr, iArr);
        if (iArr.length == 0) {
            AcceptListener acceptListener2 = sRecordPermListener;
            if (acceptListener2 != null) {
                acceptListener2.reject();
                sRecordPermListener = null;
                return;
            }
            return;
        }
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                i = 0;
                break;
            } else if (TextUtils.equals(strArr[i], "android.permission.RECORD_AUDIO")) {
                break;
            } else {
                i++;
            }
        }
        int i2 = iArr[i];
        if (i2 != -1) {
            if (i2 != 0 || (acceptListener = sRecordPermListener) == null) {
                return;
            }
            acceptListener.accept();
            sRecordPermListener = null;
            return;
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, strArr[i])) {
            setRequestPermissionFlag(strArr[i], false);
        } else {
            setOnceRequestPermissionFlag(strArr[i], false);
        }
        AcceptListener acceptListener3 = sRecordPermListener;
        if (acceptListener3 != null) {
            acceptListener3.reject();
            sRecordPermListener = null;
        }
    }

    public static void requestReadExternalPermission(Activity activity, AcceptListener acceptListener) {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"android.permission.READ_EXTERNAL_STORAGE"};
        if (!isPermissionNotGranted(activity, "android.permission.READ_EXTERNAL_STORAGE")) {
            acceptListener.accept();
            return;
        }
        if (shouldOnceRequestPermission(activity, "android.permission-group.STORAGE")) {
            arrayList.add(strArr[0]);
        }
        int size = arrayList.size();
        String[] strArr2 = new String[size];
        arrayList.toArray(strArr2);
        if (size > 0) {
            ActivityCompat.requestPermissions(activity, strArr2, 4);
            sStoragePermListener = acceptListener;
        } else {
            acceptListener.reject();
        }
    }

    public static void requestReadExternalPermissionWithFragment(Activity activity, Fragment fragment, AcceptListener acceptListener) {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"android.permission.READ_EXTERNAL_STORAGE"};
        if (!isPermissionNotGranted(activity, "android.permission.READ_EXTERNAL_STORAGE")) {
            acceptListener.accept();
            return;
        }
        String[] strArr2 = {activity.getString(R.string.permission_storage_desc_new_v12)};
        if (shouldOnceRequestPermission(activity, "android.permission-group.STORAGE")) {
            arrayList.add(strArr[0]);
            arrayList.add(strArr2[0]);
        }
        int size = arrayList.size();
        String[] strArr3 = new String[size];
        arrayList.toArray(strArr3);
        if (size > 0) {
            fragment.requestPermissions(strArr3, 4);
            sStoragePermListener = acceptListener;
        } else {
            acceptListener.reject();
        }
    }

    public static void requestReadImagesPermissionWithFragment(Activity activity, Fragment fragment, AcceptListener acceptListener) {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"android.permission.READ_MEDIA_IMAGES"};
        if (!isPermissionNotGranted(activity, "android.permission.READ_MEDIA_IMAGES")) {
            acceptListener.accept();
            return;
        }
        String[] strArr2 = new String[1];
        strArr2[0] = RomUtils.isPadDevice() ? activity.getString(R.string.permission_photos_desc_pad) : activity.getString(R.string.permission_photos_desc);
        arrayList.add(strArr[0]);
        arrayList.add(strArr2[0]);
        int size = arrayList.size();
        String[] strArr3 = new String[size];
        arrayList.toArray(strArr3);
        if (size > 0) {
            activity.requestPermissions(strArr3, 4);
            sStoragePermListener = acceptListener;
        } else {
            acceptListener.reject();
        }
    }

    public static void requestOnStoragePermission(Activity activity, AcceptListener acceptListener) {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        String[] strArr2 = {activity.getString(R.string.permission_storage_desc_new_v12)};
        if (!isPermissionNotGranted(activity, strArr[0])) {
            acceptListener.accept();
            return;
        }
        if (shouldOnceRequestPermission(activity, "android.permission-group.STORAGE")) {
            arrayList.add(strArr[0]);
            arrayList.add(strArr2[0]);
        }
        int size = arrayList.size();
        String[] strArr3 = new String[size];
        arrayList.toArray(strArr3);
        if (size > 0) {
            ActivityCompat.requestPermissions(activity, strArr3, 4);
            sStoragePermListener = acceptListener;
        } else {
            acceptListener.reject();
        }
    }

    public static void requestMediaImagesPermission(Activity activity, AcceptListener acceptListener) {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"android.permission.READ_MEDIA_IMAGES"};
        String[] strArr2 = new String[1];
        strArr2[0] = RomUtils.isPadDevice() ? activity.getString(R.string.permission_photos_desc_pad) : activity.getString(R.string.permission_photos_desc);
        if (!isPermissionNotGranted(activity, strArr[0])) {
            acceptListener.accept();
            return;
        }
        arrayList.add(strArr[0]);
        arrayList.add(strArr2[0]);
        int size = arrayList.size();
        String[] strArr3 = new String[size];
        arrayList.toArray(strArr3);
        if (size > 0) {
            ActivityCompat.requestPermissions(activity, strArr3, 4);
            sStoragePermListener = acceptListener;
        } else {
            acceptListener.reject();
        }
    }

    public static void requestOnStoragePermissionWithFragment(Activity activity, Fragment fragment, AcceptListener acceptListener) {
        String[] strArr;
        String[] strArr2;
        ArrayList arrayList = new ArrayList();
        if (Utils.isAboveSDK33()) {
            strArr = new String[]{"android.permission.READ_MEDIA_IMAGES"};
            strArr2 = new String[1];
            strArr2[0] = RomUtils.isPadDevice() ? activity.getString(R.string.permission_photos_desc_pad) : activity.getString(R.string.permission_photos_desc);
        } else {
            strArr = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"};
            strArr2 = new String[]{activity.getString(R.string.permission_storage_desc_new_v12)};
        }
        if (!isPermissionNotGranted(activity, strArr[0])) {
            acceptListener.accept();
            return;
        }
        if (shouldOnceRequestPermission(activity, "android.permission-group.STORAGE")) {
            arrayList.add(strArr[0]);
            arrayList.add(strArr2[0]);
        }
        int size = arrayList.size();
        String[] strArr3 = new String[size];
        arrayList.toArray(strArr3);
        if (size > 0) {
            fragment.requestPermissions(strArr3, 4);
            sStoragePermListener = acceptListener;
        } else {
            acceptListener.reject();
        }
    }

    public static void processOnStoragePermission(Activity activity, String[] strArr, int[] iArr) {
        AcceptListener acceptListener;
        processPermissionResult(activity, strArr, iArr);
        if (iArr.length == 0) {
            AcceptListener acceptListener2 = sStoragePermListener;
            if (acceptListener2 != null) {
                acceptListener2.reject();
                sStoragePermListener = null;
                return;
            }
            return;
        }
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                i = 0;
                break;
            } else if (TextUtils.equals(strArr[i], "android.permission.WRITE_EXTERNAL_STORAGE")) {
                break;
            } else {
                i++;
            }
        }
        int i2 = iArr[i];
        if (i2 != -1) {
            if (i2 != 0 || (acceptListener = sStoragePermListener) == null) {
                return;
            }
            acceptListener.accept();
            sStoragePermListener = null;
            return;
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, strArr[i])) {
            setRequestPermissionFlag(strArr[i], false);
        } else {
            setOnceRequestPermissionFlag(strArr[i], false);
        }
        AcceptListener acceptListener3 = sStoragePermListener;
        if (acceptListener3 != null) {
            acceptListener3.reject();
            sStoragePermListener = null;
        }
    }

    public static void restoreStoragePermListener(AcceptListener acceptListener) {
        sStoragePermListener = acceptListener;
    }

    public static void processOnEnterCallNoteFolder(Activity activity, String[] strArr, int[] iArr) {
        AcceptListener acceptListener;
        processPermissionResult(activity, strArr, iArr);
        if (iArr.length == 0) {
            AcceptListener acceptListener2 = sContactPermListener;
            if (acceptListener2 != null) {
                acceptListener2.reject();
                sContactPermListener = null;
                return;
            }
            return;
        }
        int i = iArr[0];
        if (i != -1) {
            if (i != 0 || (acceptListener = sContactPermListener) == null) {
                return;
            }
            acceptListener.accept();
            sContactPermListener = null;
            return;
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, strArr[0])) {
            setRequestPermissionFlag(strArr[0], false);
        } else {
            setOnceRequestPermissionFlag(strArr[0], false);
        }
        AcceptListener acceptListener3 = sContactPermListener;
        if (acceptListener3 != null) {
            acceptListener3.reject();
            sContactPermListener = null;
        }
    }

    public static Dialog showXiaoaiAcceptDialog(Activity activity, final AcceptListener acceptListener) {
        View viewInflate = activity.getLayoutInflater().inflate(R.layout.new_custom_permission_agreement_dialog, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.main_purpose);
        textView.setAutoLinkMask(15);
        textView.setText(getXiaoaiPrivacyUrlSpan(activity, R.string.permission_xiaoai_desc_n));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return new AlertDialog.Builder(activity).setTitle(R.string.permission_xiaoai_title).setView(viewInflate).setPositiveButton(R.string.confirm_ok, new DialogInterface.OnClickListener() { // from class: com.miui.notes.tool.PermissionUtils.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PreferenceUtils.setAllowXiaoai(NoteApp.getInstance(), true);
                acceptListener.accept();
                dialogInterface.dismiss();
            }
        }).setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.tool.PermissionUtils.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PreferenceUtils.setAllowXiaoai(NoteApp.getInstance(), false);
                acceptListener.reject();
                dialogInterface.dismiss();
            }
        }).setCancelable(false).show();
    }

    public static Dialog showAiAcceptDialog(Activity activity, final DialogInterface.OnDismissListener onDismissListener) {
        View viewInflate = activity.getLayoutInflater().inflate(R.layout.new_custom_permission_agreement_dialog, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.main_purpose);
        textView.setAutoLinkMask(15);
        textView.setText(getAiPrivacyUrlSpan(activity, R.string.permission_ai_desc_n));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return new AlertDialog.Builder(activity).setTitle(R.string.permission_ai_title).setView(viewInflate).setPositiveButton(R.string.confirm_ok, new DialogInterface.OnClickListener() { // from class: com.miui.notes.tool.PermissionUtils.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PreferenceUtils.setAllowAi(NoteApp.getInstance(), true);
                dialogInterface.dismiss();
            }
        }).setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.tool.PermissionUtils.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.tool.PermissionUtils.4
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                DialogInterface.OnDismissListener onDismissListener2 = onDismissListener;
                if (onDismissListener2 != null) {
                    onDismissListener2.onDismiss(dialogInterface);
                }
            }
        }).setCancelable(false).show();
    }

    public static Dialog showNetworkDialog(Activity activity, final AcceptListener acceptListener) {
        View viewInflate = activity.getLayoutInflater().inflate(R.layout.new_custom_permission_agreement_dialog, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.main_purpose)).setText(R.string.permission_network_for_cloud_desc);
        return new AlertDialog.Builder(activity).setTitle(R.string.app_name).setView(viewInflate).setPositiveButton(R.string.confirm_ok, new DialogInterface.OnClickListener() { // from class: com.miui.notes.tool.PermissionUtils.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PreferenceUtils.setAllowNetwork(NoteApp.getInstance(), true);
                acceptListener.accept();
                dialogInterface.dismiss();
            }
        }).setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.tool.PermissionUtils.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PreferenceUtils.setAllowNetwork(NoteApp.getInstance(), false);
                acceptListener.reject();
                dialogInterface.dismiss();
            }
        }).setCancelable(false).show();
    }

    public static Spanned getXiaoaiPrivacyUrl(Context context, int i) {
        Locale.getDefault().getLanguage();
        Locale.getDefault().getCountry();
        return Html.fromHtml(context.getString(i, "https://privacy.mi.com/notes-share/zh_CN/", "https://privacy.mi.com/xiaoai-sdk/zh_CN/"));
    }

    public static SpannableStringBuilder getXiaoaiPrivacyUrlSpan(Context context, int i) {
        Locale.getDefault().getLanguage();
        Locale.getDefault().getCountry();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(Html.fromHtml(context.getString(i, "https://privacy.mi.com/notes-share/zh_CN/", "https://privacy.mi.com/xiaoai-sdk/zh_CN/")));
        for (URLSpan uRLSpan : (URLSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), URLSpan.class)) {
            spannableStringBuilder.setSpan(new URLClickable(context, uRLSpan.getURL()), spannableStringBuilder.getSpanStart(uRLSpan), spannableStringBuilder.getSpanEnd(uRLSpan), spannableStringBuilder.getSpanFlags(uRLSpan));
        }
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder getAiPrivacyUrlSpan(Context context, int i) {
        String str;
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (RomUtils.isInternationalBuild()) {
            str = "https://privacy.mi.com/notes-share-Global/" + language + "_" + country;
        } else {
            str = "https://privacy.mi.com/notes-share/" + language + "_" + country;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(Html.fromHtml(context.getString(i, str)));
        for (URLSpan uRLSpan : (URLSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), URLSpan.class)) {
            spannableStringBuilder.setSpan(new URLClickable(context, uRLSpan.getURL()), spannableStringBuilder.getSpanStart(uRLSpan), spannableStringBuilder.getSpanEnd(uRLSpan), spannableStringBuilder.getSpanFlags(uRLSpan));
        }
        return spannableStringBuilder;
    }

    static class URLClickable extends ClickableSpan implements View.OnClickListener {
        private final Context mContext;
        private final String mURL;

        public URLClickable(Context context, String str) {
            this.mContext = context;
            this.mURL = str;
        }

        @Override // android.text.style.ClickableSpan, android.view.View.OnClickListener
        public void onClick(View view) {
            Utils.hideSoftInput((Activity) this.mContext);
            PermissionUtils.openPrivacyPolicy(this.mURL);
        }
    }

    public static void openPrivacyPolicy(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.setFlags(DataSpace.RANGE_LIMITED);
        NoteApp.getInstance().startActivity(intent);
        FloatWindowManagerAgent.changeFullLayoutToGuideService();
    }

    public static void releaseListeners() {
        sCtaListener = null;
        sGetAccountPermListener = null;
        sRecordPermListener = null;
        sStoragePermListener = null;
        sContactPermListener = null;
    }

    public static abstract class ActivityAcceptListener implements AcceptListener {
        private WeakReference<Activity> mWkActivity;

        public ActivityAcceptListener(Activity activity) {
            this.mWkActivity = new WeakReference<>(activity);
        }

        public Activity getActivity() {
            return this.mWkActivity.get();
        }
    }

    public static int getPermissionStatus(Context context, String str) {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("permissionName", str);
            return context.getContentResolver().call(Uri.parse("content://com.lbe.security.miui.autostartmgr"), "getPermissionState", (String) null, bundle).getInt("flag");
        } catch (Exception unused) {
            return isPermissionNotGranted(context, str) ? -1 : 0;
        }
    }

    public static void startAppPermsEditor(Context context) {
        Intent intent = new Intent(APP_PERMISSION_MANAGE_ACTION);
        intent.setPackage("com.miui.securitycenter");
        intent.putExtra(APP_PERMISSION_EXTRA_PKG, context.getPackageName());
        if (!(context instanceof Activity)) {
            intent.addFlags(DataSpace.RANGE_LIMITED);
        }
        context.startActivity(intent);
    }

    public static void startAppDetail(Context context) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addFlags(DataSpace.RANGE_LIMITED);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        if (!(context instanceof Activity)) {
            intent.addFlags(DataSpace.RANGE_LIMITED);
        }
        context.startActivity(intent);
    }

    public static boolean isSupportPermissionDesc() {
        return !RomUtils.isInternationalBuild() && UIUtils.isMiuiXII_H_SdkSupported() && isSupportGetPermissionStatus();
    }

    public static boolean isSupportGetPermissionStatus() {
        try {
            return NoteApp.getInstance().getPackageManager().getApplicationInfo("com.lbe.security.miui", 128).metaData.getBoolean("miui.supportGetPermissionState", false);
        } catch (Exception unused) {
            return false;
        }
    }

    public static void startNewAppPermsManager(Context context) {
        Intent intent = new Intent(APP_PERMISSION_MANAGE_ACTION);
        intent.putExtra(APP_PERMISSION_EXTRA_PKG, context.getPackageName());
        context.startActivity(intent);
    }

    public static void showNotificationPermission(Activity activity) {
        Log.d("PermissionUtils", "showNotificationPermission");
        if (activity != null && Utils.isAboveSDK33() && isPermissionNotGranted(activity, "android.permission.POST_NOTIFICATIONS")) {
            activity.requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS", activity.getString(R.string.send_notifications_for_reminder)}, 6);
        }
    }

    public static boolean isNotificationPermissionGranted(Context context) {
        if (Utils.isAboveSDK33()) {
            return !isPermissionNotGranted(context, "android.permission.POST_NOTIFICATIONS");
        }
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }
}
