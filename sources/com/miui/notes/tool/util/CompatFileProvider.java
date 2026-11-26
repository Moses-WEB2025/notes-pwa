package com.miui.notes.tool.util;

import android.net.Uri;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import com.android.soundrecorder.ai.base.constants.OutputMimeType;

/* JADX INFO: loaded from: classes4.dex */
public class CompatFileProvider extends FileProvider {
    public final String SHARE_IMAGE_NAME = "note_image";
    public final String SHARE_AUDIO_NAME = "note_audio";

    @Override // androidx.core.content.FileProvider, android.content.ContentProvider
    public String getType(Uri uri) {
        if (uri.getPathSegments().size() > 0) {
            if ("note_image".equals(uri.getPathSegments().get(0))) {
                return "image/jpeg";
            }
            if ("note_audio".equals(uri.getPathSegments().get(0))) {
                return OutputMimeType.MP3;
            }
        }
        return super.getType(uri);
    }

    private static String[] copyOf(String[] strArr, int i) {
        String[] strArr2 = new String[i];
        System.arraycopy(strArr, 0, strArr2, 0, i);
        return strArr2;
    }

    private static Object[] copyOf(Object[] objArr, int i) {
        Object[] objArr2 = new Object[i];
        System.arraycopy(objArr, 0, objArr2, 0, i);
        return objArr2;
    }

    private static boolean contains(String[] strArr, String str) {
        if (strArr == null || strArr.length < 1 || TextUtils.isEmpty(str)) {
            return false;
        }
        for (String str2 : strArr) {
            if (TextUtils.equals(str2, str)) {
                return true;
            }
        }
        return false;
    }
}
