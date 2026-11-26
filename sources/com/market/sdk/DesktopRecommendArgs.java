package com.market.sdk;

import android.os.Bundle;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopRecommendArgs {
    private static final String KEY_EXTRA_PARAMS = "key_extra_params";
    private static final String KEY_FOLDER_ID = "key_folder_id";
    private static final String KEY_FOLDER_NAME = "key_folder_name";
    private static final String KEY_PKG_LIST = "key_pkg_list";
    private final Map<String, String> extraParams;
    private final long folderId;
    private final String folderName;
    private final ArrayList<String> installedPackages;

    public DesktopRecommendArgs(long j, String str, ArrayList<String> arrayList, Map<String, String> map) {
        this.folderId = j;
        this.folderName = str;
        this.installedPackages = arrayList;
        this.extraParams = map;
    }

    public DesktopRecommendArgs(Bundle bundle) {
        this.folderId = bundle.getLong(KEY_FOLDER_ID);
        this.folderName = bundle.getString(KEY_FOLDER_NAME);
        this.installedPackages = bundle.getStringArrayList(KEY_PKG_LIST);
        this.extraParams = convertToMap(bundle.getString(KEY_EXTRA_PARAMS));
    }

    public long getFolderId() {
        return this.folderId;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public ArrayList<String> getInstalledPackages() {
        return this.installedPackages;
    }

    public Map<String, String> getExtraParams() {
        return this.extraParams;
    }

    public Bundle parcel() {
        Bundle bundle = new Bundle(4);
        bundle.putLong(KEY_FOLDER_ID, this.folderId);
        bundle.putString(KEY_FOLDER_NAME, this.folderName);
        bundle.putStringArrayList(KEY_PKG_LIST, this.installedPackages);
        bundle.putString(KEY_EXTRA_PARAMS, convertToJson(this.extraParams));
        return bundle;
    }

    private static String convertToJson(Map<String, String> map) {
        return new Gson().toJson(map);
    }

    private static Map<String, String> convertToMap(String str) {
        return (Map) new Gson().fromJson(str, (Class) new HashMap().getClass());
    }
}
