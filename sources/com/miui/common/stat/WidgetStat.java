package com.miui.common.stat;

import android.content.Intent;
import android.net.Uri;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.widget.NoteWidgetProvider;
import com.miui.richeditor.IRichEditor;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class WidgetStat extends BaseStat {
    public static final String NOTES_BRAIN_PAGE = "NotesBrainPage";
    public static final String NOTES_EDIT_PAGE = "NotesEditPage";
    public static final String NOTES_WIDGET_BIND_PAGE = "NotesWidgetBindPage";
    public static final String NOTES_WIDGET_EDIT_PAGE = "NotesWidgetEditPage";
    public static final String TIP_WIDGET_CLICK = "729.35.0.1.25547";

    private static void track(int i, String str) {
        HashMap map = new HashMap();
        map.put("jump_result", str);
        map.put("click_element_type", getWidgetType(i));
        reportClickEvent(TIP_WIDGET_CLICK, map);
    }

    public static void handleEditEnter(Intent intent, String str) {
        int intExtra;
        if (intent == null || (intExtra = intent.getIntExtra("com.miui.notes.widget_type", -1)) == -1) {
            return;
        }
        track(intExtra, str);
    }

    public static void handleWidgetEditEnter(Intent intent) {
        try {
            Uri data = intent.getData();
            if (data != null && (NoteWidgetProvider.SCHEME_WIDGET_NOTE_LIST_PAGE.equals(data.getScheme()) || NoteWidgetProvider.SCHEME_PAD_WIDGET_NOTE_LIST_PAGE.equals(data.getScheme()))) {
                List<String> pathSegments = data.getPathSegments();
                track(Integer.valueOf(pathSegments.get(pathSegments.size() - 1)).intValue(), NOTES_WIDGET_EDIT_PAGE);
            } else if (intent.getIntExtra("com.miui.notes.widget_type", -1) > 0) {
                track(intent.getIntExtra("com.miui.notes.widget_type", -1), NOTES_WIDGET_BIND_PAGE);
            }
        } catch (Throwable th) {
            Logger.INSTANCE.e(IRichEditor.CLIP_LABEL_RICH_EDITOR, "handleWidgetEditEnter error: " + th);
        }
    }

    private static String getWidgetType(int i) {
        switch (i) {
            case 1:
                return "notes_2x2";
            case 2:
                return "notes_4x2";
            case 3:
                return "notes_4x4";
            case 4:
                return "notes_8x4";
            case 5:
                return "notes_2x1";
            case 6:
                return "icon_2x1";
            case 7:
                return "todo_2x1";
            default:
                return "";
        }
    }
}
