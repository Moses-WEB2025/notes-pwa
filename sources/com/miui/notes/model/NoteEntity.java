package com.miui.notes.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.updater.MD5;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Language;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.cloudservice.NotesConstants;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.widget.WidgetProcessDatabaseUtils;
import com.miui.richeditor.schema.NoteSchema;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: NoteEntity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b$\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u0084\u00012\u00020\u0001:\u0002\u0084\u0001B\t\b\u0016¢\u0006\u0004\b\u0002\u0010\u0003B\u0011\b\u0012\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0002\u0010\u0006B\u0013\b\u0016\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0000¢\u0006\u0004\b\u0002\u0010\bJ\u000e\u0010@\u001a\u00020A2\u0006\u0010>\u001a\u000209J\u000e\u0010D\u001a\u00020A2\u0006\u0010B\u001a\u000209J\u0006\u0010e\u001a\u00020fJ\u000e\u0010q\u001a\u00020f2\u0006\u0010r\u001a\u00020sJ\u000e\u0010t\u001a\u00020u2\u0006\u0010r\u001a\u00020sJ\u000e\u0010v\u001a\u00020A2\u0006\u0010r\u001a\u00020sJ\u000e\u0010w\u001a\u00020f2\u0006\u0010r\u001a\u00020sJ\u0006\u0010x\u001a\u00020AJ \u0010y\u001a\u0012\u0012\u0004\u0012\u00020{0zj\b\u0012\u0004\u0012\u00020{`|2\u0006\u0010r\u001a\u00020sH\u0002J\u0018\u0010}\u001a\u0012\u0012\u0004\u0012\u00020{0zj\b\u0012\u0004\u0012\u00020{`|H\u0002J\b\u0010~\u001a\u000209H\u0016J\u001a\u0010\u007f\u001a\u00020A2\u0007\u0010\u0080\u0001\u001a\u00020\u00052\u0007\u0010\u0081\u0001\u001a\u000209H\u0016R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR(\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010\u001f\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R(\u0010%\u001a\u0004\u0018\u00010 2\b\u0010%\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b&\u0010\"\"\u0004\b'\u0010$R(\u0010(\u001a\u0004\u0018\u00010 2\b\u0010(\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b)\u0010\"\"\u0004\b*\u0010$R(\u0010+\u001a\u0004\u0018\u00010 2\b\u0010+\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b,\u0010\"\"\u0004\b-\u0010$R(\u0010.\u001a\u0004\u0018\u00010 2\b\u0010.\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b/\u0010\"\"\u0004\b0\u0010$R$\u00101\u001a\u00020\u001c2\u0006\u00101\u001a\u00020\u001c8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b2\u0010\u001e\"\u0004\b3\u00104R$\u00105\u001a\u00020\u001c2\u0006\u00105\u001a\u00020\u001c8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b6\u0010\u001e\"\u0004\b7\u00104R$\u00108\u001a\u0002092\u0006\u00108\u001a\u0002098F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u0011\u0010>\u001a\u0002098F¢\u0006\u0006\u001a\u0004\b?\u0010;R\u0011\u0010B\u001a\u0002098F¢\u0006\u0006\u001a\u0004\bC\u0010;R$\u0010E\u001a\u00020\u001c2\u0006\u0010E\u001a\u00020\u001c8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bF\u0010\u001e\"\u0004\bG\u00104R$\u0010H\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020\u001c8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bI\u0010\u001e\"\u0004\bJ\u00104R(\u0010K\u001a\u0004\u0018\u00010 2\b\u0010K\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bL\u0010\"\"\u0004\bM\u0010$R(\u0010N\u001a\u0004\u0018\u00010 2\b\u0010N\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bO\u0010\"\"\u0004\bP\u0010$R(\u0010Q\u001a\u0004\u0018\u00010 2\b\u0010Q\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bR\u0010\"\"\u0004\bS\u0010$R\u0011\u0010T\u001a\u00020\u001c8F¢\u0006\u0006\u001a\u0004\bU\u0010\u001eR$\u0010W\u001a\u0002092\u0006\u0010V\u001a\u0002098F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bX\u0010;\"\u0004\bY\u0010=R$\u0010Z\u001a\u00020\u001c2\u0006\u0010Z\u001a\u00020\u001c8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b[\u0010\u001e\"\u0004\b\\\u00104R(\u0010]\u001a\u0004\u0018\u00010 2\b\u0010]\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b^\u0010\"\"\u0004\b_\u0010$R$\u0010`\u001a\u0002092\u0006\u0010`\u001a\u0002098F@FX\u0086\u000e¢\u0006\f\u001a\u0004\ba\u0010;\"\u0004\bb\u0010=R\u0011\u0010c\u001a\u00020\u001c8F¢\u0006\u0006\u001a\u0004\bd\u0010\u001eR\u0011\u0010g\u001a\u00020f8F¢\u0006\u0006\u001a\u0004\bg\u0010hR\u0011\u0010i\u001a\u00020f8F¢\u0006\u0006\u001a\u0004\bi\u0010hR\u0011\u0010j\u001a\u00020f8F¢\u0006\u0006\u001a\u0004\bj\u0010hR$\u0010k\u001a\u00020f2\u0006\u0010k\u001a\u00020f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bk\u0010h\"\u0004\bl\u0010mR(\u0010n\u001a\u0004\u0018\u00010 2\b\u0010n\u001a\u0004\u0018\u00010 8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bo\u0010\"\"\u0004\bp\u0010$R\u0013\u0010\u0082\u0001\u001a\u0002098F¢\u0006\u0007\u001a\u0005\b\u0083\u0001\u0010;¨\u0006\u0085\u0001"}, d2 = {"Lcom/miui/notes/model/NoteEntity;", "Landroid/os/Parcelable;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", Language.LA_IN, "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "from", "(Lcom/miui/notes/model/NoteEntity;)V", "originalInfo", "Lcom/miui/notes/model/NoteInfo;", "getOriginalInfo", "()Lcom/miui/notes/model/NoteInfo;", "setOriginalInfo", "(Lcom/miui/notes/model/NoteInfo;)V", "updatedInfo", "getUpdatedInfo", "setUpdatedInfo", "currentInfo", "getCurrentInfo", "setCurrentInfo", "dataInfo", "Lcom/miui/notes/model/DataInfo;", "getDataInfo", "()Lcom/miui/notes/model/DataInfo;", "setDataInfo", "(Lcom/miui/notes/model/DataInfo;)V", "id", "", "getId", "()J", "content", "", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "title", "getTitle", "setTitle", "noteType", "getNoteType", "setNoteType", "mindContent", "getMindContent", "setMindContent", "mindContentPlainText", "getMindContentPlainText", "setMindContentPlainText", MiCloudConstants.PDC.J_PARENT_ID, "getParentId", "setParentId", "(J)V", NotesConstants.JSON_KEY_ALERT_DATE, "getAlertDate", "setAlertDate", "themeId", "", "getThemeId", "()I", "setThemeId", "(I)V", "widgetId", "getWidgetId", "applyWidgetId", "", "widgetType", "getWidgetType", "applyWidgetType", "createdDate", "getCreatedDate", "setCreatedDate", "modifiedDate", "getModifiedDate", "setModifiedDate", "sourceName", "getSourceName", "setSourceName", "sourcePackage", "getSourcePackage", "setSourcePackage", "sourceIntent", "getSourceIntent", "setSourceIntent", "syncId", "getSyncId", "count", "textCount", "getTextCount", "setTextCount", "callDate", "getCallDate", "setCallDate", "callNumber", "getCallNumber", "setCallNumber", "aiTextUsed", "getAiTextUsed", "setAiTextUsed", "textDataId", "getTextDataId", "existInDatabase", "", "isHidden", "()Z", "isEmpty", "isWorthSaving", "isModified", "setModified", "(Z)V", "aigcDataId", "getAigcDataId", "setAigcDataId", "syncNote", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "insertNote", "Landroid/net/Uri;", "insertNoteByDB", "createNew", "handleSent", "buildDataValuesList", "Ljava/util/ArrayList;", "Landroid/content/ContentValues;", "Lkotlin/collections/ArrayList;", "buildHandWriteValuesList", "describeContents", "writeToParcel", "dest", "flags", "parcelableContentLength", "getParcelableContentLength", "CREATOR", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteEntity implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private NoteInfo currentInfo;
    private DataInfo dataInfo;
    private NoteInfo originalInfo;
    private NoteInfo updatedInfo;

    public /* synthetic */ NoteEntity(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final NoteInfo getOriginalInfo() {
        return this.originalInfo;
    }

    public final void setOriginalInfo(NoteInfo noteInfo) {
        this.originalInfo = noteInfo;
    }

    public final NoteInfo getUpdatedInfo() {
        return this.updatedInfo;
    }

    public final void setUpdatedInfo(NoteInfo noteInfo) {
        this.updatedInfo = noteInfo;
    }

    public final NoteInfo getCurrentInfo() {
        return this.currentInfo;
    }

    public final void setCurrentInfo(NoteInfo noteInfo) {
        Intrinsics.checkNotNullParameter(noteInfo, "<set-?>");
        this.currentInfo = noteInfo;
    }

    public final DataInfo getDataInfo() {
        return this.dataInfo;
    }

    public final void setDataInfo(DataInfo dataInfo) {
        Intrinsics.checkNotNullParameter(dataInfo, "<set-?>");
        this.dataInfo = dataInfo;
    }

    public NoteEntity() {
        this.currentInfo = new NoteInfo();
        this.dataInfo = new DataInfo();
    }

    public final long getId() {
        return this.currentInfo.getId();
    }

    public final String getContent() {
        return this.currentInfo.getContent();
    }

    public final void setContent(String str) {
        this.currentInfo.setContent(str);
    }

    public final String getTitle() {
        return this.currentInfo.getTitle();
    }

    public final void setTitle(String str) {
        this.currentInfo.setTitle(str);
        Logger.INSTANCE.d("NoteEntity", "setTitle: md5_" + (str != null ? MD5.getMd5Digest(str) : null));
    }

    public final String getNoteType() {
        return this.currentInfo.getNoteType();
    }

    public final void setNoteType(String str) {
        this.currentInfo.setNoteType(str);
    }

    public final String getMindContent() {
        return this.currentInfo.getMindContent();
    }

    public final void setMindContent(String str) {
        this.currentInfo.setMindContent(str);
    }

    public final String getMindContentPlainText() {
        return this.currentInfo.getMindContentPlainText();
    }

    public final void setMindContentPlainText(String str) {
        this.currentInfo.setMindContentPlainText(str);
    }

    public final long getParentId() {
        return this.currentInfo.getParentId();
    }

    public final void setParentId(long j) {
        this.currentInfo.setParentId(j);
    }

    public final long getAlertDate() {
        return this.currentInfo.getAlertDate();
    }

    public final void setAlertDate(long j) {
        this.currentInfo.setAlertDate(j);
    }

    public final int getThemeId() {
        if (this.currentInfo.getThemeId() > 0) {
            return this.currentInfo.getThemeId();
        }
        return this.currentInfo.getBgColorId();
    }

    public final void setThemeId(int i) {
        this.currentInfo.setThemeId(i);
        if (i < 5) {
            this.currentInfo.setBgColorId(i);
        } else {
            this.currentInfo.setBgColorId(0);
        }
    }

    public final int getWidgetId() {
        return this.currentInfo.getWidgetId();
    }

    public final void applyWidgetId(int widgetId) {
        this.currentInfo.setWidgetId(widgetId);
        NoteInfo noteInfo = this.updatedInfo;
        if (noteInfo != null) {
            Intrinsics.checkNotNull(noteInfo);
            noteInfo.setWidgetId(widgetId);
        }
        NoteInfo noteInfo2 = this.originalInfo;
        if (noteInfo2 != null) {
            Intrinsics.checkNotNull(noteInfo2);
            noteInfo2.setWidgetId(widgetId);
        }
    }

    public final int getWidgetType() {
        return this.currentInfo.getWidgetType();
    }

    public final void applyWidgetType(int widgetType) {
        this.currentInfo.setWidgetType(widgetType);
        NoteInfo noteInfo = this.updatedInfo;
        if (noteInfo != null) {
            Intrinsics.checkNotNull(noteInfo);
            noteInfo.setWidgetType(widgetType);
        }
        NoteInfo noteInfo2 = this.originalInfo;
        if (noteInfo2 != null) {
            Intrinsics.checkNotNull(noteInfo2);
            noteInfo2.setWidgetType(widgetType);
        }
    }

    public final long getCreatedDate() {
        return this.currentInfo.getCreatedDate();
    }

    public final void setCreatedDate(long j) {
        this.currentInfo.setCreatedDate(j);
    }

    public final long getModifiedDate() {
        return this.currentInfo.getModifiedDate();
    }

    public final void setModifiedDate(long j) {
        this.currentInfo.setModifiedDate(j);
    }

    public final String getSourceName() {
        return this.currentInfo.getSourceName();
    }

    public final void setSourceName(String str) {
        this.currentInfo.setSourceName(str);
    }

    public final String getSourcePackage() {
        return this.currentInfo.getSourcePackage();
    }

    public final void setSourcePackage(String str) {
        this.currentInfo.setSourcePackage(str);
    }

    public final String getSourceIntent() {
        return this.currentInfo.getSourceIntent();
    }

    public final void setSourceIntent(String str) {
        this.currentInfo.setSourceIntent(str);
    }

    public final long getSyncId() {
        return this.currentInfo.getSyncId();
    }

    public final int getTextCount() {
        return this.currentInfo.getNoteTextCount();
    }

    public final void setTextCount(int i) {
        this.currentInfo.setNoteTextCount(i);
    }

    public final long getCallDate() {
        return this.dataInfo.getCallDate();
    }

    public final void setCallDate(long j) {
        this.dataInfo.setCallDate(j);
    }

    public final String getCallNumber() {
        return this.dataInfo.getCallNumber();
    }

    public final void setCallNumber(String str) {
        this.dataInfo.setCallNumber(str);
    }

    public final int getAiTextUsed() {
        return this.currentInfo.getAiTextUsed();
    }

    public final void setAiTextUsed(int i) {
        this.currentInfo.setAiTextUsed(i);
    }

    public final long getTextDataId() {
        return this.dataInfo.getTextId();
    }

    public final boolean existInDatabase() {
        return this.currentInfo.getId() > 0;
    }

    public final boolean isHidden() {
        return this.currentInfo.getParentId() == -4;
    }

    public final boolean isEmpty() {
        if (Intrinsics.areEqual(getNoteType(), NoteModel.NoteType.TYPE_MIND)) {
            return TextUtils.isEmpty(this.currentInfo.getContent()) && TextUtils.isEmpty(this.currentInfo.getMindContentPlainText()) && TextUtils.isEmpty(this.currentInfo.getTitle());
        }
        return (TextUtils.isEmpty(this.currentInfo.getContent()) || NoteEntityHelper.isContentEmpty(this.currentInfo.getContent())) && TextUtils.isEmpty(this.currentInfo.getTitle()) && !Utils.isDoodleOnWin && !Utils.isCtaOnWin;
    }

    public final boolean isWorthSaving() {
        NoteInfo noteInfo = this.updatedInfo;
        if (noteInfo != null) {
            return !Intrinsics.areEqual(this.currentInfo, noteInfo);
        }
        if (TextUtils.isEmpty(this.currentInfo.getTitle())) {
            String content = this.currentInfo.getContent();
            Intrinsics.checkNotNull(content);
            String str = content;
            int length = str.length() - 1;
            int i = 0;
            boolean z = false;
            while (i <= length) {
                boolean z2 = Intrinsics.compare((int) str.charAt(!z ? i : length), 32) <= 0;
                if (z) {
                    if (!z2) {
                        break;
                    }
                    length--;
                } else if (z2) {
                    i++;
                } else {
                    z = true;
                }
            }
            if (str.subSequence(i, length + 1).toString().length() <= 0) {
                String mindContent = this.currentInfo.getMindContent();
                Intrinsics.checkNotNull(mindContent);
                String str2 = mindContent;
                int length2 = str2.length() - 1;
                int i2 = 0;
                boolean z3 = false;
                while (i2 <= length2) {
                    boolean z4 = Intrinsics.compare((int) str2.charAt(!z3 ? i2 : length2), 32) <= 0;
                    if (z3) {
                        if (!z4) {
                            break;
                        }
                        length2--;
                    } else if (z4) {
                        i2++;
                    } else {
                        z3 = true;
                    }
                }
                if (str2.subSequence(i2, length2 + 1).toString().length() <= 0) {
                    return false;
                }
            }
            return true;
        }
        String content2 = this.currentInfo.getContent();
        Intrinsics.checkNotNull(content2);
        String str3 = content2;
        int length3 = str3.length() - 1;
        int i3 = 0;
        boolean z5 = false;
        while (i3 <= length3) {
            boolean z6 = Intrinsics.compare((int) str3.charAt(!z5 ? i3 : length3), 32) <= 0;
            if (z5) {
                if (!z6) {
                    break;
                }
                length3--;
            } else if (z6) {
                i3++;
            } else {
                z5 = true;
            }
        }
        if (str3.subSequence(i3, length3 + 1).toString().length() <= 0) {
            String title = this.currentInfo.getTitle();
            Intrinsics.checkNotNull(title);
            if (StringsKt.trim((CharSequence) title).toString().length() <= 0) {
                String mindContent2 = this.currentInfo.getMindContent();
                Intrinsics.checkNotNull(mindContent2);
                String str4 = mindContent2;
                int length4 = str4.length() - 1;
                int i4 = 0;
                boolean z7 = false;
                while (i4 <= length4) {
                    boolean z8 = Intrinsics.compare((int) str4.charAt(!z7 ? i4 : length4), 32) <= 0;
                    if (z7) {
                        if (!z8) {
                            break;
                        }
                        length4--;
                    } else if (z8) {
                        i4++;
                    } else {
                        z7 = true;
                    }
                }
                if (str4.subSequence(i4, length4 + 1).toString().length() <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean isModified() {
        if (!TextUtils.isEmpty(this.currentInfo.getTitle())) {
            NoteInfo noteInfo = this.originalInfo;
            if (noteInfo != null) {
                return !Intrinsics.areEqual(this.currentInfo, noteInfo);
            }
            String content = this.currentInfo.getContent();
            Intrinsics.checkNotNull(content);
            String str = content;
            int length = str.length() - 1;
            int i = 0;
            boolean z = false;
            while (i <= length) {
                boolean z2 = Intrinsics.compare((int) str.charAt(!z ? i : length), 32) <= 0;
                if (z) {
                    if (!z2) {
                        break;
                    }
                    length--;
                } else if (z2) {
                    i++;
                } else {
                    z = true;
                }
            }
            if (str.subSequence(i, length + 1).toString().length() <= 0) {
                String title = this.currentInfo.getTitle();
                Intrinsics.checkNotNull(title);
                if (StringsKt.trim((CharSequence) title).toString().length() <= 0) {
                    return false;
                }
            }
            return true;
        }
        NoteInfo noteInfo2 = this.originalInfo;
        NoteInfo noteInfo3 = this.currentInfo;
        if (noteInfo2 != null) {
            return !Intrinsics.areEqual(noteInfo3, noteInfo2);
        }
        String content2 = noteInfo3.getContent();
        Intrinsics.checkNotNull(content2);
        String str2 = content2;
        int length2 = str2.length() - 1;
        int i2 = 0;
        boolean z3 = false;
        while (i2 <= length2) {
            boolean z4 = Intrinsics.compare((int) str2.charAt(!z3 ? i2 : length2), 32) <= 0;
            if (z3) {
                if (!z4) {
                    break;
                }
                length2--;
            } else if (z4) {
                i2++;
            } else {
                z3 = true;
            }
        }
        return str2.subSequence(i2, length2 + 1).toString().length() > 0;
    }

    public final void setModified(boolean z) {
        this.currentInfo.setLocalModified(z);
    }

    public final String getAigcDataId() {
        return this.currentInfo.getAigcDataId();
    }

    public final void setAigcDataId(String str) {
        this.currentInfo.setAigcDataId(str);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x007e  */
    /* JADX WARN: Code duplicated, block: B:24:0x008c  */
    /* JADX WARN: Code duplicated, block: B:25:0x0098  */
    /* JADX WARN: Code duplicated, block: B:41:0x0187  */
    /* JADX WARN: Code duplicated, block: B:43:0x0197  */
    /* JADX WARN: Code duplicated, block: B:46:0x01ef  */
    /* JADX WARN: Code duplicated, block: B:48:0x01f7  */
    /* JADX WARN: Code duplicated, block: B:49:0x021f  */
    /* JADX WARN: Code duplicated, block: B:50:0x0226  */
    /* JADX WARN: Code duplicated, block: B:52:0x0229  */
    public final boolean syncNote(Context context) throws Throwable {
        ArrayList<ContentValues> arrayListBuildDataValuesList;
        Context context2;
        boolean z;
        NoteInfo noteInfo;
        Object objValueOf;
        Object objValueOf2;
        NoteEntity noteEntityLoad;
        long jCurrentTimeMillis;
        Uri uriInsertNoteAtomic;
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        ContentValues contentValuesBuildValues = this.currentInfo.buildValues();
        if (TextUtils.equals(this.currentInfo.getNoteType(), "handwrite") || TextUtils.equals(this.currentInfo.getNoteType(), "multi_handwrite")) {
            arrayListBuildDataValuesList = buildHandWriteValuesList();
        } else {
            arrayListBuildDataValuesList = buildDataValuesList(context);
        }
        ArrayList<ContentValues> arrayList = arrayListBuildDataValuesList;
        boolean z2 = true;
        if (this.currentInfo.getId() > 0) {
            NoteInfo noteInfo2 = this.updatedInfo;
            if (noteInfo2 != null) {
                Intrinsics.checkNotNull(noteInfo2);
                str = "modified_date=" + noteInfo2.getModifiedDate();
            } else {
                str = null;
            }
            context2 = context;
            z = Notes.Utils.updateNoteAtomic(context2, this.currentInfo.getId(), contentValuesBuildValues, arrayList, str, null) > 0;
            if (z) {
                z2 = z;
            } else {
                Logger logger = Logger.INSTANCE;
                long id = this.currentInfo.getId();
                noteInfo = this.updatedInfo;
                objValueOf = "null";
                if (noteInfo != null) {
                    Intrinsics.checkNotNull(noteInfo);
                    objValueOf2 = Long.valueOf(noteInfo.getModifiedDate());
                } else {
                    objValueOf2 = "null";
                }
                logger.i("NoteEntity", "save result:" + z + " id:" + id + " lastModifiedDate:" + objValueOf2);
                noteEntityLoad = NoteEntityHelper.load(context2, "_id=" + this.currentInfo.getId(), null);
                Logger.INSTANCE.d("NoteEntity", "load notes with entity ");
                if (noteEntityLoad != null || !TextUtils.equals(noteEntityLoad.getTitle(), this.currentInfo.getTitle()) || !TextUtils.equals(noteEntityLoad.getContent(), this.currentInfo.getContent()) || (TextUtils.equals(noteEntityLoad.getNoteType(), NoteModel.NoteType.TYPE_MIND) && !TextUtils.equals(noteEntityLoad.getMindContentPlainText(), this.currentInfo.getMindContentPlainText()))) {
                    Logger logger2 = Logger.INSTANCE;
                    long id2 = this.currentInfo.getId();
                    if (this.currentInfo.getContent() != null) {
                        String content = this.currentInfo.getContent();
                        Intrinsics.checkNotNull(content);
                        objValueOf = Integer.valueOf(content.length());
                    }
                    logger2.i("NoteEntity", "new note,find different: id:" + id2 + " content size:" + objValueOf);
                    jCurrentTimeMillis = System.currentTimeMillis();
                    contentValuesBuildValues.put("created_date", Long.valueOf(jCurrentTimeMillis));
                    uriInsertNoteAtomic = Notes.Utils.insertNoteAtomic(context2, contentValuesBuildValues, arrayList);
                    Logger.INSTANCE.d("NoteEntity", "do insert notes with ts " + jCurrentTimeMillis + " with uri ");
                    if (uriInsertNoteAtomic == null) {
                        Logger.INSTANCE.e("NoteEntity", "insert note failed!!!!");
                    } else if (ContentUris.parseId(uriInsertNoteAtomic) > 0) {
                        this.currentInfo.setId(ContentUris.parseId(uriInsertNoteAtomic));
                        Logger.INSTANCE.d("NoteEntity", "update current note id " + this.currentInfo.getId());
                        setCreatedDate(jCurrentTimeMillis);
                    }
                    z2 = z;
                } else {
                    this.currentInfo.setModifiedDate(noteEntityLoad.getModifiedDate());
                    Logger logger3 = Logger.INSTANCE;
                    long id3 = this.currentInfo.getId();
                    long modifiedDate = noteEntityLoad.getModifiedDate();
                    if (this.currentInfo.getContent() != null) {
                        String content2 = this.currentInfo.getContent();
                        Intrinsics.checkNotNull(content2);
                        objValueOf = Integer.valueOf(content2.length());
                    }
                    logger3.i("NoteEntity", "already exist,update modify date,note : id:" + id3 + " remoteModifiedDate:" + modifiedDate + " content size:" + objValueOf);
                }
            }
            if (z2) {
                this.updatedInfo = new NoteInfo(this.currentInfo);
            }
            return z2;
        }
        context2 = context;
        if (z) {
            Logger logger4 = Logger.INSTANCE;
            long id4 = this.currentInfo.getId();
            noteInfo = this.updatedInfo;
            objValueOf = "null";
            if (noteInfo != null) {
                Intrinsics.checkNotNull(noteInfo);
                objValueOf2 = Long.valueOf(noteInfo.getModifiedDate());
            } else {
                objValueOf2 = "null";
            }
            logger4.i("NoteEntity", "save result:" + z + " id:" + id4 + " lastModifiedDate:" + objValueOf2);
            noteEntityLoad = NoteEntityHelper.load(context2, "_id=" + this.currentInfo.getId(), null);
            Logger.INSTANCE.d("NoteEntity", "load notes with entity ");
            if (noteEntityLoad != null) {
                Logger logger5 = Logger.INSTANCE;
                long id5 = this.currentInfo.getId();
                if (this.currentInfo.getContent() != null) {
                    String content3 = this.currentInfo.getContent();
                    Intrinsics.checkNotNull(content3);
                    objValueOf = Integer.valueOf(content3.length());
                }
                logger5.i("NoteEntity", "new note,find different: id:" + id5 + " content size:" + objValueOf);
                jCurrentTimeMillis = System.currentTimeMillis();
                contentValuesBuildValues.put("created_date", Long.valueOf(jCurrentTimeMillis));
                uriInsertNoteAtomic = Notes.Utils.insertNoteAtomic(context2, contentValuesBuildValues, arrayList);
                Logger.INSTANCE.d("NoteEntity", "do insert notes with ts " + jCurrentTimeMillis + " with uri ");
                if (uriInsertNoteAtomic == null) {
                    Logger.INSTANCE.e("NoteEntity", "insert note failed!!!!");
                } else if (ContentUris.parseId(uriInsertNoteAtomic) > 0) {
                    this.currentInfo.setId(ContentUris.parseId(uriInsertNoteAtomic));
                    Logger.INSTANCE.d("NoteEntity", "update current note id " + this.currentInfo.getId());
                    setCreatedDate(jCurrentTimeMillis);
                }
                z2 = z;
            } else {
                Logger logger6 = Logger.INSTANCE;
                long id6 = this.currentInfo.getId();
                if (this.currentInfo.getContent() != null) {
                    String content4 = this.currentInfo.getContent();
                    Intrinsics.checkNotNull(content4);
                    objValueOf = Integer.valueOf(content4.length());
                }
                logger6.i("NoteEntity", "new note,find different: id:" + id6 + " content size:" + objValueOf);
                jCurrentTimeMillis = System.currentTimeMillis();
                contentValuesBuildValues.put("created_date", Long.valueOf(jCurrentTimeMillis));
                uriInsertNoteAtomic = Notes.Utils.insertNoteAtomic(context2, contentValuesBuildValues, arrayList);
                Logger.INSTANCE.d("NoteEntity", "do insert notes with ts " + jCurrentTimeMillis + " with uri ");
                if (uriInsertNoteAtomic == null) {
                    Logger.INSTANCE.e("NoteEntity", "insert note failed!!!!");
                } else if (ContentUris.parseId(uriInsertNoteAtomic) > 0) {
                    this.currentInfo.setId(ContentUris.parseId(uriInsertNoteAtomic));
                    Logger.INSTANCE.d("NoteEntity", "update current note id " + this.currentInfo.getId());
                    setCreatedDate(jCurrentTimeMillis);
                }
                z2 = z;
            }
        } else {
            z2 = z;
        }
        if (z2) {
            this.updatedInfo = new NoteInfo(this.currentInfo);
        }
        return z2;
    }

    public final Uri insertNote(Context context) throws Throwable {
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.d("NoteEntity", "insertNote with context");
        Uri uriInsertNoteAtomic = Notes.Utils.insertNoteAtomic(context, this.currentInfo.buildValues(), buildDataValuesList(context), true);
        Intrinsics.checkNotNullExpressionValue(uriInsertNoteAtomic, "insertNoteAtomic(...)");
        return uriInsertNoteAtomic;
    }

    public final void insertNoteByDB(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.d("NoteEntity", "insertNoteByDB with context");
        ContentValues contentValuesBuildValues = this.currentInfo.buildValues();
        Notes.Utils.addDataValuesToNoteValues(contentValuesBuildValues, buildDataValuesList(context));
        WidgetProcessDatabaseUtils.insertNote(context, contentValuesBuildValues);
    }

    public final boolean createNew(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.currentInfo.setId(0L);
        this.updatedInfo = null;
        this.originalInfo = null;
        return syncNote(context);
    }

    public final void handleSent() {
        this.originalInfo = new NoteInfo(this.currentInfo);
    }

    private final ArrayList<ContentValues> buildDataValuesList(Context context) throws Throwable {
        String strTryGetImageMimeType;
        ArrayList<ContentValues> arrayList = new ArrayList<>();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Data.MIME_TYPE, Notes.TextData.CONTENT_ITEM_TYPE);
        contentValues.put("content", this.currentInfo.getContent());
        arrayList.add(contentValues);
        if (this.dataInfo.getCallDate() > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(Notes.Data.MIME_TYPE, Notes.CallData.CONTENT_ITEM_TYPE);
            contentValues2.put("data3", this.dataInfo.getCallNumber());
            contentValues2.put("data1", Long.valueOf(this.dataInfo.getCallDate()));
            PreferenceUtils.setLastCallNoteData(this.dataInfo.getCallDate());
            arrayList.add(contentValues2);
        }
        List<String> listRetrieveMediasAncient = NoteSchema.retrieveMediasAncient(this.currentInfo.getContent());
        String strRetrieveMindThumbnail = NoteSchema.retrieveMindThumbnail(this.currentInfo.getMindContent());
        for (String str : listRetrieveMediasAncient) {
            if (AudioUtils.isMp3Suffix(str)) {
                strTryGetImageMimeType = AudioUtils.MP3_MINI_TYPE;
            } else if (AttachmentUtils.isDoodleSuffix(str)) {
                strTryGetImageMimeType = AttachmentUtils.DOODLE_TYPE;
            } else {
                HashMap<String, String> attachmentTypeMap = this.dataInfo.getAttachmentTypeMap();
                Intrinsics.checkNotNull(attachmentTypeMap);
                strTryGetImageMimeType = attachmentTypeMap.get(str);
                if (strTryGetImageMimeType == null) {
                    String attachmentPath = AttachmentUtils.getAttachmentPath(context, str);
                    strTryGetImageMimeType = AttachmentUtils.tryGetImageMimeType(attachmentPath != null ? new File(attachmentPath) : null);
                    if (strTryGetImageMimeType != null) {
                        HashMap<String, String> attachmentTypeMap2 = this.dataInfo.getAttachmentTypeMap();
                        Intrinsics.checkNotNull(attachmentTypeMap2);
                        attachmentTypeMap2.put(str, strTryGetImageMimeType);
                    }
                }
            }
            if (strTryGetImageMimeType != null) {
                ContentValues contentValues3 = new ContentValues();
                contentValues3.put("content", str);
                contentValues3.put(Notes.Data.MIME_TYPE, strTryGetImageMimeType);
                arrayList.add(contentValues3);
            }
        }
        if (!TextUtils.isEmpty(strRetrieveMindThumbnail)) {
            ContentValues contentValues4 = new ContentValues();
            contentValues4.put("content", strRetrieveMindThumbnail);
            contentValues4.put(Notes.Data.MIME_TYPE, "image/jpeg");
            arrayList.add(contentValues4);
        }
        Iterator<ContentValues> it = arrayList.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            ContentValues next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            next.put(Notes.Data.DIRTY, (Integer) 1);
        }
        return arrayList;
    }

    private final ArrayList<ContentValues> buildHandWriteValuesList() {
        ArrayList<ContentValues> arrayList = new ArrayList<>();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Data.MIME_TYPE, Notes.TextData.CONTENT_ITEM_TYPE);
        contentValues.put("content", this.currentInfo.getContent());
        arrayList.add(contentValues);
        if (this.dataInfo.getAttachmentTypeMap() != null) {
            HashMap<String, String> attachmentTypeMap = this.dataInfo.getAttachmentTypeMap();
            Intrinsics.checkNotNull(attachmentTypeMap);
            if (!attachmentTypeMap.isEmpty()) {
                HashMap<String, String> attachmentTypeMap2 = this.dataInfo.getAttachmentTypeMap();
                Intrinsics.checkNotNull(attachmentTypeMap2);
                for (String str : attachmentTypeMap2.keySet()) {
                    Intrinsics.checkNotNullExpressionValue(str, "next(...)");
                    String str2 = str;
                    if (!TextUtils.isEmpty(str2)) {
                        HashMap<String, String> attachmentTypeMap3 = this.dataInfo.getAttachmentTypeMap();
                        Intrinsics.checkNotNull(attachmentTypeMap3);
                        String str3 = attachmentTypeMap3.get(str2);
                        if (!TextUtils.isEmpty(str3)) {
                            ContentValues contentValues2 = new ContentValues();
                            contentValues2.put("content", str2);
                            contentValues2.put(Notes.Data.MIME_TYPE, str3);
                            arrayList.add(contentValues2);
                        }
                    }
                }
            }
        }
        Iterator<ContentValues> it = arrayList.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            ContentValues next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            next.put(Notes.Data.DIRTY, (Integer) 1);
        }
        return arrayList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeParcelable(this.originalInfo, 0);
        dest.writeParcelable(this.updatedInfo, 0);
        dest.writeParcelable(this.currentInfo, 0);
        dest.writeParcelable(this.dataInfo, 0);
    }

    private NoteEntity(Parcel parcel) {
        ClassLoader classLoader = getClass().getClassLoader();
        this.originalInfo = (NoteInfo) parcel.readParcelable(classLoader);
        this.updatedInfo = (NoteInfo) parcel.readParcelable(classLoader);
        Parcelable parcelable = parcel.readParcelable(classLoader);
        Intrinsics.checkNotNull(parcelable);
        this.currentInfo = (NoteInfo) parcelable;
        Parcelable parcelable2 = parcel.readParcelable(classLoader);
        Intrinsics.checkNotNull(parcelable2);
        this.dataInfo = (DataInfo) parcelable2;
    }

    public NoteEntity(NoteEntity noteEntity) {
        if (noteEntity == null) {
            throw new IllegalArgumentException("Copy error,NoteEntity should not be null!".toString());
        }
        this.currentInfo = new NoteInfo(noteEntity.currentInfo);
        this.dataInfo = new DataInfo(noteEntity.dataInfo);
    }

    public final int getParcelableContentLength() {
        if (TextUtils.equals(this.currentInfo.getNoteType(), NoteModel.NoteType.TYPE_MIND)) {
            String mindContent = this.currentInfo.getMindContent();
            Intrinsics.checkNotNull(mindContent);
            return mindContent.length();
        }
        String content = this.currentInfo.getContent();
        Intrinsics.checkNotNull(content);
        return content.length();
    }

    /* JADX INFO: renamed from: com.miui.notes.model.NoteEntity$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: NoteEntity.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001d\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/miui/notes/model/NoteEntity$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/miui/notes/model/NoteEntity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/miui/notes/model/NoteEntity;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion implements Parcelable.Creator<NoteEntity> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NoteEntity createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new NoteEntity(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    }
}
