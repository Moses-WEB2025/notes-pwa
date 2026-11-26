package com.miui.notes.tool.tapshareutils;

import android.content.Context;
import android.net.Uri;
import com.miui.common.tool.Logger;
import com.miui.mishare.tapshare.TapShareDataHandler;
import com.miui.mishare.tapshare.TapSharePreviewBean;
import com.miui.mishare.tapshare.TapShareSendBean;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareSender {
    public static final String DEEP_LINK = "mishare://data/notes/Share";
    private static final String TAG = "TapShareSender";

    public static boolean shareSingleNote(Context context, TapShareDataModel.SingleNoteTapShareData singleNoteTapShareData, TapShareDataHandler tapShareDataHandler) {
        if (context == null || singleNoteTapShareData == null || tapShareDataHandler == null || singleNoteTapShareData.zipUri == null) {
            Logger.INSTANCE.e(TAG, "Invalid parameters for shareSingleNote");
            return false;
        }
        try {
            tapShareDataHandler.sendByLinkAndUris(DEEP_LINK, Collections.singletonList(singleNoteTapShareData.zipUri), createTapShareSendBean(context, singleNoteTapShareData.title, singleNoteTapShareData.subTitle, singleNoteTapShareData.previewImageUri));
            return true;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error sharing single note: " + e.getMessage());
            return false;
        }
    }

    public static boolean shareMultipleNotes(Context context, TapShareDataModel.MultipleNotesTapShareData multipleNotesTapShareData, TapShareDataHandler tapShareDataHandler) {
        if (context == null || multipleNotesTapShareData == null || tapShareDataHandler == null || multipleNotesTapShareData.zipUris == null || multipleNotesTapShareData.zipUris.isEmpty()) {
            Logger.INSTANCE.e(TAG, "Invalid parameters for shareMultipleNotes");
            return false;
        }
        try {
            tapShareDataHandler.sendByLinkAndUris(DEEP_LINK, multipleNotesTapShareData.zipUris, createTapShareSendBeanWithPreviewList(context, multipleNotesTapShareData.title, multipleNotesTapShareData.subTitle, multipleNotesTapShareData.previewBeanList));
            return true;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error sharing multiple notes: " + e.getMessage());
            return false;
        }
    }

    private static TapShareSendBean createTapShareSendBean(Context context, String str, String str2, Uri uri) {
        TapShareSendBean.Builder action = new TapShareSendBean.Builder().setAction("note");
        if (uri != null) {
            action.setPreviewInfo(uri, str, str2);
            Logger.INSTANCE.d(TAG, "Set preview image URI: " + uri);
        } else {
            action.setPreviewInfo(null, str, str2);
        }
        return action.build();
    }

    private static TapShareSendBean createTapShareSendBeanWithPreviewList(Context context, String str, String str2, List<TapSharePreviewBean> list) {
        TapShareSendBean.Builder previewInfo = new TapShareSendBean.Builder().setAction("note").setPreviewInfo(null, str, str2);
        if (list != null && !list.isEmpty()) {
            previewInfo.setPreviewInfoList(list);
            Logger.INSTANCE.d(TAG, "Set preview info list with " + list.size() + " items");
        }
        return previewInfo.build();
    }
}
