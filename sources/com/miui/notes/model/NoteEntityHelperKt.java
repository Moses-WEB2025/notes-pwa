package com.miui.notes.model;

import android.database.Cursor;
import ch.qos.logback.core.CoreConstants;
import com.miui.notes.provider.Notes;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NoteEntityHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002¨\u0006\u0005"}, d2 = {CoreConstants.VALUE_OF, "Lcom/miui/notes/model/DataInfo;", "Landroid/database/Cursor;", "load2NoteInfo", "Lcom/miui/notes/model/NoteInfo;", "app_PhoneGlobalRelease"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class NoteEntityHelperKt {
    public static final DataInfo valueOf(Cursor cursor) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        DataInfo dataInfo = new DataInfo();
        while (cursor.moveToNext()) {
            String string = cursor.getString(2);
            if (Intrinsics.areEqual(Notes.TextData.CONTENT_ITEM_TYPE, string)) {
                dataInfo.setTextId(cursor.getLong(0));
                dataInfo.setContent(cursor.getString(1));
            } else if (Intrinsics.areEqual(Notes.CallData.CONTENT_ITEM_TYPE, string)) {
                dataInfo.setCallDate(cursor.getLong(3));
                dataInfo.setCallNumber(cursor.getString(5));
            } else {
                String string2 = cursor.getString(1);
                HashMap<String, String> attachmentTypeMap = dataInfo.getAttachmentTypeMap();
                Intrinsics.checkNotNull(attachmentTypeMap);
                attachmentTypeMap.put(string2, string);
            }
        }
        return dataInfo;
    }

    public static final NoteInfo load2NoteInfo(Cursor cursor) {
        Intrinsics.checkNotNullParameter(cursor, "<this>");
        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setId(cursor.getLong(0));
        noteInfo.setParentId(cursor.getLong(1));
        noteInfo.setAlertDate(cursor.getLong(2));
        noteInfo.setBgColorId(cursor.getInt(3));
        noteInfo.setThemeId(cursor.getInt(11));
        noteInfo.setWidgetId(cursor.getInt(4));
        noteInfo.setWidgetType(cursor.getInt(5));
        noteInfo.setCreatedDate(cursor.getLong(6));
        noteInfo.setModifiedDate(cursor.getLong(7));
        noteInfo.setSourceName(cursor.getString(8));
        noteInfo.setSourcePackage(cursor.getString(9));
        noteInfo.setSourceIntent(cursor.getString(10));
        noteInfo.setTitle(cursor.getString(12));
        noteInfo.setNoteType(cursor.getString(13));
        noteInfo.setMindContent(cursor.getString(14));
        noteInfo.setMindContentPlainText(cursor.getString(15));
        noteInfo.setSyncId(cursor.getLong(16));
        noteInfo.setNoteTextCount(cursor.getInt(17));
        noteInfo.setAiTextUsed(cursor.getInt(18));
        noteInfo.setAigcDataId(cursor.getString(19));
        return noteInfo;
    }
}
