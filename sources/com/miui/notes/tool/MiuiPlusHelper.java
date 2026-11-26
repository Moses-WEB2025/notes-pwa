package com.miui.notes.tool;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.home.ContentFragment;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.NoteProviderAccessUtils;
import com.miui.notes.store.ItemStore;
import java.util.List;
import miui.accounts.ExtraAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class MiuiPlusHelper {
    private static final String TAG = "MiuiPlusHelper";

    public interface MiuiPlusCallBack {
        public static final int ERROR_CODE_NOTE_DATA_NOT_FOUND = 1;

        void onError(int i);

        void onOpenAppPage(String str);

        void onOpenNote(long j, long j2);
    }

    public static boolean checkMiuiplusDataFromIntent(Context context, Intent intent, MiuiPlusCallBack miuiPlusCallBack) {
        String str;
        Cursor cursorQueryNoteBySyncId;
        if (PreferenceUtils.isCTAAccepted(context) && intent != null && intent.getData() != null) {
            Uri data = intent.getData();
            intent.setData(Uri.parse("minote://data/notes"));
            if ("minote".equals(data.getScheme())) {
                if ("data".equals(data.getHost())) {
                    List<String> pathSegments = data.getPathSegments();
                    int size = pathSegments.size();
                    if (size > 1 && "notes".equals(pathSegments.get(0))) {
                        String str2 = pathSegments.get(1);
                        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(context);
                        if (xiaomiAccount == null) {
                            str = "-1";
                        } else {
                            str = xiaomiAccount.name;
                        }
                        if (!"-1".equals(str2)) {
                            if (!"-1".equals(str)) {
                                if (size > 2 && str2.equals(str)) {
                                    String str3 = pathSegments.get(2);
                                    String queryParameter = data.getQueryParameter("index");
                                    String queryParameter2 = data.getQueryParameter("folder");
                                    String str4 = TextUtils.isEmpty(queryParameter2) ? "-1" : queryParameter2;
                                    long j = Long.parseLong(str4);
                                    long j2 = Long.parseLong(str3);
                                    Logger.INSTANCE.d("MiuiPlusHelper:Miui+", "view data account = " + str2 + " dataSyncId = " + str3 + " index = " + queryParameter + " folder = " + str4);
                                    if (j2 == -1) {
                                        return false;
                                    }
                                    if (j == -4) {
                                        cursorQueryNoteBySyncId = NoteProviderAccessUtils.queryPrivateNoteBySyncId(context, j2);
                                    } else {
                                        cursorQueryNoteBySyncId = NoteProviderAccessUtils.queryNoteBySyncId(context, j2);
                                    }
                                    cursorQueryNoteBySyncId.moveToFirst();
                                    if (cursorQueryNoteBySyncId.getCount() > 0) {
                                        NoteModel note = ItemStore.parseNote(cursorQueryNoteBySyncId);
                                        cursorQueryNoteBySyncId.close();
                                        if (miuiPlusCallBack != null) {
                                            miuiPlusCallBack.onOpenNote(note.getId(), note.getFolderId());
                                        }
                                        return true;
                                    }
                                    cursorQueryNoteBySyncId.close();
                                    Toast.makeText(NoteApp.getInstance(), R.string.miui_plus_relay_waring_data_not_find, 1).show();
                                    return false;
                                }
                            } else {
                                if (PreferenceUtils.getAllowNetwork(NoteApp.getInstance())) {
                                    ExternalAppUtils.viewCloud(context);
                                }
                                return true;
                            }
                        }
                    } else {
                        "todos".equals(pathSegments.get(0));
                    }
                } else if (ContentFragment.ARG_PAGE.equals(data.getHost())) {
                    List<String> pathSegments2 = data.getPathSegments();
                    if (pathSegments2.size() > 1 && "navi".equals(pathSegments2.get(0))) {
                        String str5 = pathSegments2.get(1);
                        if (miuiPlusCallBack != null) {
                            miuiPlusCallBack.onOpenAppPage(str5);
                        }
                        return true;
                    }
                }
                intent.setData(null);
            }
        }
        return false;
    }

    public static boolean isAboveV3() {
        try {
            return NoteApp.getInstance().getPackageManager().getPackageInfo("com.xiaomi.mirror", 0).getLongVersionCode() >= 30000;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
