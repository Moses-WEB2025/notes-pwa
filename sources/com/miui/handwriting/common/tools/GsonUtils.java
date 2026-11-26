package com.miui.handwriting.common.tools;

import com.google.gson.Gson;

/* JADX INFO: loaded from: classes3.dex */
public class GsonUtils {
    private static final String TAG = "GsonUtils";

    private static class SingletonHolder {
        private static final Gson INSTANCE = new Gson();

        private SingletonHolder() {
        }
    }

    public static Gson getGsonInstance() {
        return SingletonHolder.INSTANCE;
    }
}
