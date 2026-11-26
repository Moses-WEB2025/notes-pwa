package com.miui.todo.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes4.dex */
public class SerializableUtils {
    private static final String TAG = "SerializableUtils";

    public static void writeObject(String str, Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ObjectOutputStream(byteArrayOutputStream).writeObject(obj);
            PreferenceUtils.setStringPreference(NoteApp.getInstance(), str, new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0)));
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "saveObject error,exception:" + e);
        }
    }

    public static Object readObject(String str) {
        try {
            String stringPreference = PreferenceUtils.getStringPreference(NoteApp.getInstance(), str, null);
            if (TextUtils.isEmpty(stringPreference)) {
                return null;
            }
            return new ObjectInputStream(new ByteArrayInputStream(Base64.decode(stringPreference, 0))).readObject();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "readObject error,exception:" + e);
            return null;
        }
    }
}
