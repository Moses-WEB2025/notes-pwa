package com.miui.notes.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Language;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.cloudservice.NotesConstants;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.feature.noteedit.DataExtraInfo;
import com.miui.notes.store.FolderStore;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.onetrack.b.e;
import com.xiaomi.verificationsdk.internal.Constants;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WorkingNote.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0015\n\u0002\u0010\b\n\u0002\bG\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 \u009a\u00012\u00020\u0001:\u0006\u0098\u0001\u0099\u0001\u009a\u0001B\u0011\b\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u0011\b\u0012\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\u0004\u0010\bJ\u000e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,J'\u0010-\u001a\u00020\u000e2\b\u0010.\u001a\u0004\u0018\u00010/2\u000e\u00100\u001a\n\u0012\u0004\u0012\u00020/\u0018\u000101H\u0002¢\u0006\u0002\u00102J\u000e\u00103\u001a\u00020*2\u0006\u00104\u001a\u00020\u000eJ\u0006\u00105\u001a\u00020\u000eJ\u000e\u00105\u001a\u00020\u000e2\u0006\u00106\u001a\u00020\u000eJ\u000e\u00107\u001a\u00020\u00032\u0006\u00106\u001a\u00020\u000eJ\u000e\u00108\u001a\u00020\u000e2\u0006\u00106\u001a\u00020\u000eJ\u0006\u00109\u001a\u00020\u000eJ\u0006\u0010=\u001a\u00020*J\u0006\u0010>\u001a\u00020*J\u000e\u0010?\u001a\u00020*2\u0006\u0010@\u001a\u00020#J\u000e\u0010A\u001a\u00020*2\u0006\u0010@\u001a\u00020#J\u000e\u0010B\u001a\u00020*2\u0006\u0010C\u001a\u00020\u000eJ\u0006\u0010D\u001a\u00020*J\u000e\u0010E\u001a\u00020*2\u0006\u0010F\u001a\u00020GJ\u000e\u0010H\u001a\u00020*2\u0006\u0010I\u001a\u00020GJ\u0010\u0010J\u001a\u00020*2\b\u0010K\u001a\u0004\u0018\u00010/J\u0010\u0010L\u001a\u00020*2\b\u0010K\u001a\u0004\u0018\u00010/J\u0018\u0010M\u001a\u00020*2\b\u0010N\u001a\u0004\u0018\u00010/2\u0006\u0010O\u001a\u00020\u0003J\u0010\u0010P\u001a\u00020*2\b\u0010Q\u001a\u0004\u0018\u00010/J\u0006\u0010\\\u001a\u00020\u000eJ\t\u0010\u0097\u0001\u001a\u00020/H\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u000e\u0010\u0016\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0017@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R+\u0010!\u001a\u0012\u0012\u0004\u0012\u00020#0\"j\b\u0012\u0004\u0012\u00020#`$8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b%\u0010&R\u0014\u0010:\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b:\u0010\u0011R$\u0010;\u001a\u00020\u000e2\u0006\u0010;\u001a\u00020\u000e8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b;\u0010\u0011\"\u0004\b<\u0010\u0013R(\u0010S\u001a\u0004\u0018\u00010/2\b\u0010R\u001a\u0004\u0018\u00010/8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR(\u0010Y\u001a\u0004\u0018\u00010/2\b\u0010X\u001a\u0004\u0018\u00010/8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bZ\u0010U\"\u0004\b[\u0010WR\u0013\u0010]\u001a\u0004\u0018\u00010/8F¢\u0006\u0006\u001a\u0004\b^\u0010UR(\u0010_\u001a\u0004\u0018\u00010/2\b\u0010_\u001a\u0004\u0018\u00010/8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b`\u0010U\"\u0004\ba\u0010WR$\u0010c\u001a\u00020\u00032\u0006\u0010b\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bd\u0010e\"\u0004\bf\u0010\u0005R\u0011\u0010g\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bh\u0010eR\u0011\u0010i\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bj\u0010eR\u0011\u0010k\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bl\u0010eR$\u0010m\u001a\u00020G2\u0006\u0010I\u001a\u00020G8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bn\u0010o\"\u0004\bp\u0010qR\u0011\u0010r\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bs\u0010eR\u0011\u0010t\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bu\u0010eR$\u0010w\u001a\u00020G2\u0006\u0010v\u001a\u00020G8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bx\u0010o\"\u0004\by\u0010qR$\u0010{\u001a\u00020G2\u0006\u0010z\u001a\u00020G8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b|\u0010o\"\u0004\b}\u0010qR*\u0010\u007f\u001a\u0004\u0018\u00010/2\b\u0010~\u001a\u0004\u0018\u00010/8F@FX\u0086\u000e¢\u0006\u000e\u001a\u0005\b\u0080\u0001\u0010U\"\u0005\b\u0081\u0001\u0010WR&\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\u000e\u001a\u0005\b\u0082\u0001\u0010e\"\u0005\b\u0083\u0001\u0010\u0005R\u0013\u0010\u0084\u0001\u001a\u00020G8F¢\u0006\u0007\u001a\u0005\b\u0085\u0001\u0010oR\u0013\u0010\u0086\u0001\u001a\u00020G8F¢\u0006\u0007\u001a\u0005\b\u0087\u0001\u0010oR,\u0010\u0088\u0001\u001a\u0004\u0018\u00010/2\t\u0010\u0088\u0001\u001a\u0004\u0018\u00010/8F@FX\u0086\u000e¢\u0006\u000e\u001a\u0005\b\u0089\u0001\u0010U\"\u0005\b\u008a\u0001\u0010WR,\u0010\u008b\u0001\u001a\u0004\u0018\u00010/2\t\u0010\u008b\u0001\u001a\u0004\u0018\u00010/8F@FX\u0086\u000e¢\u0006\u000e\u001a\u0005\b\u008c\u0001\u0010U\"\u0005\b\u008d\u0001\u0010WR0\u0010\u0090\u0001\u001a\u0005\u0018\u00010\u008f\u00012\n\u0010\u008e\u0001\u001a\u0005\u0018\u00010\u008f\u00018F@FX\u0086\u000e¢\u0006\u0010\u001a\u0006\b\u0091\u0001\u0010\u0092\u0001\"\u0006\b\u0093\u0001\u0010\u0094\u0001R\u0013\u0010\u0095\u0001\u001a\u00020\u000e8F¢\u0006\u0007\u001a\u0005\b\u0095\u0001\u0010\u0011R\u0013\u0010\u0096\u0001\u001a\u00020\u000e8F¢\u0006\u0007\u001a\u0005\b\u0096\u0001\u0010\u0011¨\u0006\u009b\u0001"}, d2 = {"Lcom/miui/notes/model/WorkingNote;", "", "folderId", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(J)V", "state", "Lcom/miui/notes/model/WorkingNote$SavedState;", "(Lcom/miui/notes/model/WorkingNote$SavedState;)V", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "mIsDeleted", "", "readonly", "getReadonly", "()Z", "setReadonly", "(Z)V", "value", "isInvalid", "mInPadMode", "Lcom/miui/notes/feature/noteedit/DataExtraInfo;", "extraInfo", "getExtraInfo", "()Lcom/miui/notes/feature/noteedit/DataExtraInfo;", "mNoteEntity", "Lcom/miui/notes/model/NoteEntity;", "getMNoteEntity", "()Lcom/miui/notes/model/NoteEntity;", "setMNoteEntity", "(Lcom/miui/notes/model/NoteEntity;)V", "settingChangedListeners", "Ljava/util/ArrayList;", "Lcom/miui/notes/model/WorkingNote$NoteSettingChangedListener;", "Lkotlin/collections/ArrayList;", "getSettingChangedListeners", "()Ljava/util/ArrayList;", "settingChangedListeners$delegate", "Lkotlin/Lazy;", "onSaveInstanceState", "", "outState", "Landroid/os/Bundle;", "load", "selection", "", "selectionArgs", "", "(Ljava/lang/String;[Ljava/lang/String;)Z", "setInPadMode", "inPadMode", "saveNote", Constants.FORCE, "saveNoteWithId", "createNoteByPad", "existInDatabase", "isWorthSaving", "isModified", "setModified", "updateAlarmInfo", "handleSent", "addOnSettingStatusChangedListener", e.a, "removeOnSettingStatusChangedListener", "markDeleted", "mark", "invalid", "applyWidgetType", "type", "", "applyWidgetId", "id", "setWorkingText", "text", "setWorkingTitle", "convertToCallNote", "phoneNumber", "callDate", "setWorkingMindPlainText", "mindPlainText", "mindContent", "workingMindContent", "getWorkingMindContent", "()Ljava/lang/String;", "setWorkingMindContent", "(Ljava/lang/String;)V", "noteType", "workingNoteType", "getWorkingNoteType", "setWorkingNoteType", "hasClockAlert", "content", "getContent", "title", "getTitle", "setTitle", "date", NotesConstants.JSON_KEY_ALERT_DATE, "getAlertDate", "()J", "setAlertDate", "modifiedDate", "getModifiedDate", "createdDate", "getCreatedDate", "sortDate", "getSortDate", "themeId", "getThemeId", "()I", "setThemeId", "(I)V", NotifyUtils.KEY_NOTE_ID, "getNoteId", "syncId", "getSyncId", "count", "textCount", "getTextCount", "setTextCount", "used", "aiTextUsed", "getAiTextUsed", "setAiTextUsed", "dataid", "aigcDataId", "getAigcDataId", "setAigcDataId", "getFolderId", "setFolderId", "widgetId", "getWidgetId", "widgetType", "getWidgetType", "sourceName", "getSourceName", "setSourceName", "sourcePackage", "getSourcePackage", "setSourcePackage", "intent", "Landroid/content/Intent;", "sourceIntent", "getSourceIntent", "()Landroid/content/Intent;", "setSourceIntent", "(Landroid/content/Intent;)V", "isHidden", "isEmpty", "toString", "NoteSettingChangedListener", "SavedState", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class WorkingNote {
    private static final String BUNDLE_KEY_WORKING_NOTE = "working_note";
    private static final String BUNDLE_KEY_WORKING_NOTE_ID = "working_note_id";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long FAIL_ID = -1000000;
    private static final String TAG = "WorkingNote";
    private final Context context;
    private DataExtraInfo extraInfo;
    private boolean isInvalid;
    private boolean mInPadMode;
    private boolean mIsDeleted;
    private NoteEntity mNoteEntity;
    private boolean readonly;

    /* JADX INFO: renamed from: settingChangedListeners$delegate, reason: from kotlin metadata */
    private final Lazy settingChangedListeners;

    /* JADX INFO: compiled from: WorkingNote.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH&¨\u0006\fÀ\u0006\u0003"}, d2 = {"Lcom/miui/notes/model/WorkingNote$NoteSettingChangedListener;", "", "onNoteThemeChanged", "", "onClockAlertChanged", "newDate", "", "oldDate", "onWidgetChanged", "widgetType", "", "widgetId", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface NoteSettingChangedListener {
        void onClockAlertChanged(long newDate, long oldDate);

        void onNoteThemeChanged();

        void onWidgetChanged(int widgetType, int widgetId);
    }

    public /* synthetic */ WorkingNote(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    public /* synthetic */ WorkingNote(SavedState savedState, DefaultConstructorMarker defaultConstructorMarker) {
        this(savedState);
    }

    @JvmStatic
    public static final WorkingNote createEmptyMindNote(long j, int i, int i2, int i3) {
        return INSTANCE.createEmptyMindNote(j, i, i2, i3);
    }

    @JvmStatic
    public static final WorkingNote createEmptyNote(long j, int i, int i2, int i3) {
        return INSTANCE.createEmptyNote(j, i, i2, i3);
    }

    @JvmStatic
    public static final WorkingNote loadNote(Bundle bundle) {
        return INSTANCE.loadNote(bundle);
    }

    @JvmStatic
    public static final WorkingNote loadNote(String str, String[] strArr) {
        return INSTANCE.loadNote(str, strArr);
    }

    public final Context getContext() {
        return this.context;
    }

    public final boolean getReadonly() {
        return this.readonly;
    }

    public final void setReadonly(boolean z) {
        this.readonly = z;
    }

    /* JADX INFO: renamed from: isInvalid, reason: from getter */
    public final boolean getIsInvalid() {
        return this.isInvalid;
    }

    public final DataExtraInfo getExtraInfo() {
        return this.extraInfo;
    }

    public final NoteEntity getMNoteEntity() {
        return this.mNoteEntity;
    }

    public final void setMNoteEntity(NoteEntity noteEntity) {
        this.mNoteEntity = noteEntity;
    }

    private final ArrayList<NoteSettingChangedListener> getSettingChangedListeners() {
        return (ArrayList) this.settingChangedListeners.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ArrayList settingChangedListeners_delegate$lambda$0() {
        return new ArrayList(6);
    }

    private WorkingNote(long j) {
        this.settingChangedListeners = LazyKt.lazy(new Function0() { // from class: com.miui.notes.model.WorkingNote$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return WorkingNote.settingChangedListeners_delegate$lambda$0();
            }
        });
        BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
        this.context = companion;
        NoteEntity noteEntity = new NoteEntity();
        this.mNoteEntity = noteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setParentId(j);
        this.extraInfo = new DataExtraInfo();
        long jCurrentTimeMillis = System.currentTimeMillis();
        String str = ((Object) DateFormatUtils.formatDateText(companion, jCurrentTimeMillis)) + "  " + ((Object) DateFormatUtils.formatTimeText(companion, jCurrentTimeMillis));
        DataExtraInfo dataExtraInfo = this.extraInfo;
        NoteEntity noteEntity2 = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity2);
        dataExtraInfo.textCount = noteEntity2.getTextCount();
        this.extraInfo.timeString = str;
        this.extraInfo.time = jCurrentTimeMillis;
        this.extraInfo.isHide = isHidden();
    }

    private WorkingNote(SavedState savedState) {
        this.settingChangedListeners = LazyKt.lazy(new Function0() { // from class: com.miui.notes.model.WorkingNote$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return WorkingNote.settingChangedListeners_delegate$lambda$0();
            }
        });
        BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
        this.context = companion;
        this.mIsDeleted = savedState.getDeleted();
        this.readonly = savedState.getReadonly();
        this.isInvalid = savedState.getInvalid();
        this.mNoteEntity = savedState.getNoteEntity();
        this.extraInfo = new DataExtraInfo();
        long sortDate = getSortDate();
        this.extraInfo.timeString = ((Object) DateFormatUtils.formatDateText(companion, sortDate)) + "  " + ((Object) DateFormatUtils.formatTimeText(companion, sortDate));
        this.extraInfo.time = sortDate;
        if (getAlertDate() > 0) {
            this.extraInfo.remindInfo = DateFormatUtils.getRelativeTime(companion, getAlertDate());
        }
        this.extraInfo.isHide = isHidden();
    }

    public final void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        if (noteEntity.getParcelableContentLength() > 20000) {
            outState.putLong(BUNDLE_KEY_WORKING_NOTE_ID, getNoteId());
            Logger.INSTANCE.d(TAG, "over size for max reach: 20000 ,won't save");
            return;
        }
        Logger.INSTANCE.d(TAG, "save note in bundle for " + getNoteId() + "," + getFolderId());
        outState.putParcelable(BUNDLE_KEY_WORKING_NOTE, new SavedState(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean load(String selection, String[] selectionArgs) {
        Logger.INSTANCE.d(TAG, "load noteId " + getNoteId());
        NoteEntity noteEntityLoad = NoteEntityHelper.load(this.context, selection, selectionArgs);
        if (noteEntityLoad == null) {
            Logger.INSTANCE.w(TAG, "Fail to load note");
            return false;
        }
        this.mNoteEntity = noteEntityLoad;
        long sortDate = getSortDate();
        this.extraInfo.timeString = ((Object) DateFormatUtils.formatDateText(this.context, sortDate)) + "  " + ((Object) DateFormatUtils.formatTimeText(this.context, sortDate));
        this.extraInfo.time = sortDate;
        if (getAlertDate() > 0) {
            this.extraInfo.remindInfo = DateFormatUtils.getRelativeTime(this.context, getAlertDate());
        }
        this.extraInfo.isHide = isHidden();
        return true;
    }

    public final void setInPadMode(boolean inPadMode) {
        this.mInPadMode = inPadMode;
    }

    public final boolean saveNote() {
        return saveNote(false);
    }

    public final synchronized boolean saveNote(boolean force) {
        Logger.INSTANCE.d(TAG, "saveNote " + getNoteId() + "  " + getFolderId());
        if (saveNoteWithId(force) == FAIL_ID) {
            return false;
        }
        long sortDate = getSortDate();
        this.extraInfo.timeString = ((Object) DateFormatUtils.formatDateText(this.context, sortDate)) + "  " + ((Object) DateFormatUtils.formatTimeText(this.context, sortDate));
        this.extraInfo.time = sortDate;
        return true;
    }

    public final synchronized long saveNoteWithId(boolean force) {
        Logger.INSTANCE.d(TAG, "saveNoteWithId force " + force + ", noteId " + getNoteId());
        if (isEmpty() && getNoteId() == -2) {
            Logger.INSTANCE.d(TAG, "new empty note,abort it !!");
            return FAIL_ID;
        }
        if (force || isWorthSaving()) {
            Logger.INSTANCE.d(TAG, "saveNoteWithID " + getNoteId());
            NoteEntity noteEntity = this.mNoteEntity;
            Intrinsics.checkNotNull(noteEntity);
            noteEntity.setModifiedDate(System.currentTimeMillis());
            NoteEntity noteEntity2 = this.mNoteEntity;
            Intrinsics.checkNotNull(noteEntity2);
            if (TextUtils.isEmpty(noteEntity2.getTitle())) {
                NoteEntity noteEntity3 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity3);
                if (AudioUtils.hasAudioLabel(noteEntity3.getContent())) {
                    NoteEntity noteEntity4 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity4);
                    noteEntity4.setTitle(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.snippet_audio_stub));
                }
            }
            NoteEntity noteEntity5 = this.mNoteEntity;
            Intrinsics.checkNotNull(noteEntity5);
            boolean zSyncNote = noteEntity5.syncNote(this.context);
            if (!zSyncNote && existInDatabase()) {
                Logger.INSTANCE.e(TAG, "This note may have been deleted, try to create new");
                NoteEntity noteEntity6 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity6);
                zSyncNote = noteEntity6.createNew(this.context);
            }
            if (!zSyncNote) {
                NoteEntity noteEntity7 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity7);
                if (noteEntity7.getParentId() != 0) {
                    Logger.INSTANCE.e(TAG, "The folder may not exists, try to save to root folder");
                    NoteEntity noteEntity8 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity8);
                    noteEntity8.setParentId(0L);
                    NoteEntity noteEntity9 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity9);
                    zSyncNote = noteEntity9.createNew(this.context);
                }
            }
            if (zSyncNote) {
                NoteEntity noteEntity10 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity10);
                if (noteEntity10.getWidgetId() != 0) {
                    NoteEntity noteEntity11 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity11);
                    if (noteEntity11.getWidgetType() != -1) {
                        Iterator<T> it = getSettingChangedListeners().iterator();
                        while (it.hasNext()) {
                            ((NoteSettingChangedListener) it.next()).onWidgetChanged(getWidgetType(), getWidgetId());
                        }
                    }
                }
                NoteEntity noteEntity12 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity12);
                long parentId = noteEntity12.getParentId();
                if (parentId != 0) {
                    FolderStore.updateFolderModifiedTime$default(FolderStore.INSTANCE, BaseNoteApp.INSTANCE.getInstance(), parentId, 0L, 4, null);
                }
                NoteEntity noteEntity13 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity13);
                return noteEntity13.getId();
            }
            Logger.INSTANCE.e(TAG, "Fail to save note");
        }
        return FAIL_ID;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0040 A[Catch: all -> 0x00d2, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x0022, B:10:0x0028, B:13:0x0040, B:15:0x004d, B:17:0x006b, B:19:0x0076, B:21:0x0082, B:22:0x008c, B:24:0x0092, B:25:0x00a4, B:27:0x00b1, B:31:0x00c6), top: B:39:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x004d A[Catch: all -> 0x00d2, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x0022, B:10:0x0028, B:13:0x0040, B:15:0x004d, B:17:0x006b, B:19:0x0076, B:21:0x0082, B:22:0x008c, B:24:0x0092, B:25:0x00a4, B:27:0x00b1, B:31:0x00c6), top: B:39:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:17:0x006b A[Catch: all -> 0x00d2, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x0022, B:10:0x0028, B:13:0x0040, B:15:0x004d, B:17:0x006b, B:19:0x0076, B:21:0x0082, B:22:0x008c, B:24:0x0092, B:25:0x00a4, B:27:0x00b1, B:31:0x00c6), top: B:39:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:19:0x0076 A[Catch: all -> 0x00d2, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x0022, B:10:0x0028, B:13:0x0040, B:15:0x004d, B:17:0x006b, B:19:0x0076, B:21:0x0082, B:22:0x008c, B:24:0x0092, B:25:0x00a4, B:27:0x00b1, B:31:0x00c6), top: B:39:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x0082 A[Catch: all -> 0x00d2, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x0022, B:10:0x0028, B:13:0x0040, B:15:0x004d, B:17:0x006b, B:19:0x0076, B:21:0x0082, B:22:0x008c, B:24:0x0092, B:25:0x00a4, B:27:0x00b1, B:31:0x00c6), top: B:39:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:24:0x0092 A[Catch: all -> 0x00d2, LOOP:0: B:22:0x008c->B:24:0x0092, LOOP_END, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x0022, B:10:0x0028, B:13:0x0040, B:15:0x004d, B:17:0x006b, B:19:0x0076, B:21:0x0082, B:22:0x008c, B:24:0x0092, B:25:0x00a4, B:27:0x00b1, B:31:0x00c6), top: B:39:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:27:0x00b1 A[Catch: all -> 0x00d2, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x0022, B:10:0x0028, B:13:0x0040, B:15:0x004d, B:17:0x006b, B:19:0x0076, B:21:0x0082, B:22:0x008c, B:24:0x0092, B:25:0x00a4, B:27:0x00b1, B:31:0x00c6), top: B:39:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x00c6 A[Catch: all -> 0x00d2, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x0022, B:10:0x0028, B:13:0x0040, B:15:0x004d, B:17:0x006b, B:19:0x0076, B:21:0x0082, B:22:0x008c, B:24:0x0092, B:25:0x00a4, B:27:0x00b1, B:31:0x00c6), top: B:39:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:6:0x0009 A[Catch: all -> 0x00d2, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x0022, B:10:0x0028, B:13:0x0040, B:15:0x004d, B:17:0x006b, B:19:0x0076, B:21:0x0082, B:22:0x008c, B:24:0x0092, B:25:0x00a4, B:27:0x00b1, B:31:0x00c6), top: B:39:0x0003 }] */
    public final synchronized boolean createNoteByPad(boolean force) {
        boolean zSyncNote;
        NoteEntity noteEntity;
        long parentId;
        NoteEntity noteEntity2;
        Iterator<T> it;
        NoteEntity noteEntity3;
        if (!force) {
            if (isWorthSaving()) {
                NoteEntity noteEntity4 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity4);
                noteEntity4.setModifiedDate(System.currentTimeMillis());
                NoteEntity noteEntity5 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity5);
                zSyncNote = noteEntity5.syncNote(this.context);
                if (!zSyncNote && existInDatabase()) {
                    Logger.INSTANCE.e(TAG, "This note may have been deleted, try to create new");
                    NoteEntity noteEntity6 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity6);
                    zSyncNote = noteEntity6.createNew(this.context);
                }
                if (!zSyncNote) {
                    noteEntity3 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity3);
                    if (noteEntity3.getParentId() != 0) {
                        Logger.INSTANCE.e(TAG, "The folder may not exists, try to save to root folder");
                        NoteEntity noteEntity7 = this.mNoteEntity;
                        Intrinsics.checkNotNull(noteEntity7);
                        noteEntity7.setParentId(0L);
                        NoteEntity noteEntity8 = this.mNoteEntity;
                        Intrinsics.checkNotNull(noteEntity8);
                        zSyncNote = noteEntity8.createNew(this.context);
                    }
                }
                if (zSyncNote) {
                    noteEntity = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity);
                    if (noteEntity.getWidgetId() != 0) {
                        noteEntity2 = this.mNoteEntity;
                        Intrinsics.checkNotNull(noteEntity2);
                        if (noteEntity2.getWidgetType() != -1) {
                            it = getSettingChangedListeners().iterator();
                            while (it.hasNext()) {
                                ((NoteSettingChangedListener) it.next()).onWidgetChanged(getWidgetType(), getWidgetId());
                            }
                        }
                    }
                    NoteEntity noteEntity9 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity9);
                    parentId = noteEntity9.getParentId();
                    if (parentId != 0) {
                        FolderStore.updateFolderModifiedTime$default(FolderStore.INSTANCE, BaseNoteApp.INSTANCE.getInstance(), parentId, 0L, 4, null);
                    }
                    return true;
                }
                Logger.INSTANCE.e(TAG, "Fail to save note");
            }
        } else {
            NoteEntity noteEntity10 = this.mNoteEntity;
            Intrinsics.checkNotNull(noteEntity10);
            noteEntity10.setModifiedDate(System.currentTimeMillis());
            NoteEntity noteEntity11 = this.mNoteEntity;
            Intrinsics.checkNotNull(noteEntity11);
            zSyncNote = noteEntity11.syncNote(this.context);
            if (!zSyncNote) {
                Logger.INSTANCE.e(TAG, "This note may have been deleted, try to create new");
                NoteEntity noteEntity12 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity12);
                zSyncNote = noteEntity12.createNew(this.context);
            }
            if (!zSyncNote) {
                noteEntity3 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity3);
                if (noteEntity3.getParentId() != 0) {
                    Logger.INSTANCE.e(TAG, "The folder may not exists, try to save to root folder");
                    NoteEntity noteEntity13 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity13);
                    noteEntity13.setParentId(0L);
                    NoteEntity noteEntity14 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity14);
                    zSyncNote = noteEntity14.createNew(this.context);
                }
            }
            if (zSyncNote) {
                noteEntity = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity);
                if (noteEntity.getWidgetId() != 0) {
                    noteEntity2 = this.mNoteEntity;
                    Intrinsics.checkNotNull(noteEntity2);
                    if (noteEntity2.getWidgetType() != -1) {
                        it = getSettingChangedListeners().iterator();
                        while (it.hasNext()) {
                            ((NoteSettingChangedListener) it.next()).onWidgetChanged(getWidgetType(), getWidgetId());
                        }
                    }
                }
                NoteEntity noteEntity15 = this.mNoteEntity;
                Intrinsics.checkNotNull(noteEntity15);
                parentId = noteEntity15.getParentId();
                if (parentId != 0) {
                    FolderStore.updateFolderModifiedTime$default(FolderStore.INSTANCE, BaseNoteApp.INSTANCE.getInstance(), parentId, 0L, 4, null);
                }
                return true;
            }
            Logger.INSTANCE.e(TAG, "Fail to save note");
        }
        return false;
    }

    public final boolean existInDatabase() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.existInDatabase();
    }

    private final boolean isWorthSaving() {
        if (this.readonly || this.mIsDeleted || this.isInvalid) {
            return false;
        }
        if (this.mInPadMode) {
            return true;
        }
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.isWorthSaving();
    }

    public final boolean isModified() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.isModified();
    }

    public final void setModified(boolean z) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setModified(z);
    }

    public final void updateAlarmInfo() {
        if (getAlertDate() > 0) {
            this.extraInfo.remindInfo = DateFormatUtils.getRelativeTime(this.context, getAlertDate());
        } else {
            this.extraInfo.remindInfo = "";
        }
    }

    public final void handleSent() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.handleSent();
    }

    public final void addOnSettingStatusChangedListener(NoteSettingChangedListener l) {
        Intrinsics.checkNotNullParameter(l, "l");
        Logger.INSTANCE.d(TAG, "addOnSettingStatusChangedListener/add success:" + getSettingChangedListeners().add(l) + " with listener left " + getSettingChangedListeners().size());
    }

    public final void removeOnSettingStatusChangedListener(NoteSettingChangedListener l) {
        Intrinsics.checkNotNullParameter(l, "l");
        Logger.INSTANCE.d(TAG, "removeOnSettingStatusChangedListener /removed success:" + getSettingChangedListeners().remove(l) + " with listener left " + getSettingChangedListeners().size());
    }

    public final void markDeleted(boolean mark) {
        this.mIsDeleted = mark;
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        if (noteEntity.getWidgetId() != 0) {
            NoteEntity noteEntity2 = this.mNoteEntity;
            Intrinsics.checkNotNull(noteEntity2);
            if (noteEntity2.getWidgetType() != -1) {
                Iterator<T> it = getSettingChangedListeners().iterator();
                while (it.hasNext()) {
                    ((NoteSettingChangedListener) it.next()).onWidgetChanged(getWidgetType(), getWidgetId());
                }
            }
        }
        UIUtils.updateWidgetNotes(BaseNoteApp.INSTANCE.getInstance());
    }

    public final void invalid() {
        this.isInvalid = true;
    }

    public final void applyWidgetType(int type) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.applyWidgetType(type);
    }

    public final void applyWidgetId(int id) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.applyWidgetId(id);
    }

    public final void setWorkingText(String text) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setContent(text);
    }

    public final void setWorkingTitle(String text) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setTitle(text);
    }

    public final void convertToCallNote(String phoneNumber, long callDate) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setCallNumber(phoneNumber);
        NoteEntity noteEntity2 = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity2);
        noteEntity2.setCallDate(callDate);
        NoteEntity noteEntity3 = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity3);
        noteEntity3.setParentId(-2L);
    }

    public final void setWorkingMindPlainText(String mindPlainText) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setMindContentPlainText(mindPlainText);
    }

    public final String getWorkingMindContent() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getMindContent();
    }

    public final void setWorkingMindContent(String str) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setMindContent(str);
    }

    public final String getWorkingNoteType() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getNoteType();
    }

    public final void setWorkingNoteType(String str) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setNoteType(str);
    }

    public final boolean hasClockAlert() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getAlertDate() > 0;
    }

    public final String getContent() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getContent();
    }

    public final String getTitle() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getTitle();
    }

    public final void setTitle(String str) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setTitle(str);
    }

    public final long getAlertDate() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getAlertDate();
    }

    public final void setAlertDate(long j) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        long alertDate = noteEntity.getAlertDate();
        NoteEntity noteEntity2 = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity2);
        noteEntity2.setAlertDate(j);
        updateAlarmInfo();
        Iterator<T> it = getSettingChangedListeners().iterator();
        while (it.hasNext()) {
            ((NoteSettingChangedListener) it.next()).onClockAlertChanged(j, alertDate);
        }
    }

    public final long getModifiedDate() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getModifiedDate();
    }

    public final long getCreatedDate() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getCreatedDate();
    }

    public final long getSortDate() {
        if (PreferenceUtils.getNoteSortWay(BaseNoteApp.INSTANCE.getInstance(), 1) == 0) {
            NoteEntity noteEntity = this.mNoteEntity;
            Intrinsics.checkNotNull(noteEntity);
            return noteEntity.getCreatedDate();
        }
        NoteEntity noteEntity2 = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity2);
        return noteEntity2.getModifiedDate();
    }

    public final int getThemeId() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getThemeId();
    }

    public final void setThemeId(int i) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        if (noteEntity.getThemeId() != i) {
            NoteEntity noteEntity2 = this.mNoteEntity;
            Intrinsics.checkNotNull(noteEntity2);
            noteEntity2.setThemeId(i);
            Iterator<T> it = getSettingChangedListeners().iterator();
            while (it.hasNext()) {
                ((NoteSettingChangedListener) it.next()).onNoteThemeChanged();
            }
        }
    }

    public final long getNoteId() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getId();
    }

    public final long getSyncId() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getSyncId();
    }

    public final int getTextCount() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getTextCount();
    }

    public final void setTextCount(int i) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setTextCount(i);
    }

    public final int getAiTextUsed() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getAiTextUsed();
    }

    public final void setAiTextUsed(int i) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setAiTextUsed(i);
    }

    public final String getAigcDataId() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getAigcDataId();
    }

    public final void setAigcDataId(String str) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setAigcDataId(str);
    }

    public final long getFolderId() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getParentId();
    }

    public final void setFolderId(long j) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setParentId(j);
        this.extraInfo.isHide = isHidden();
    }

    public final int getWidgetId() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getWidgetId();
    }

    public final int getWidgetType() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getWidgetType();
    }

    public final String getSourceName() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getSourceName();
    }

    public final void setSourceName(String str) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setSourceName(str);
    }

    public final String getSourcePackage() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.getSourcePackage();
    }

    public final void setSourcePackage(String str) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setSourcePackage(str);
    }

    public final Intent getSourceIntent() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        String sourceIntent = noteEntity.getSourceIntent();
        if (sourceIntent == null) {
            return null;
        }
        try {
            return Intent.parseUri(sourceIntent, 0);
        } catch (URISyntaxException e) {
            Logger.INSTANCE.w(TAG, "Fail to parse intent from uri: " + sourceIntent + ",exception: " + e.getMessage());
            return null;
        }
    }

    public final void setSourceIntent(Intent intent) {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        noteEntity.setSourceIntent(intent != null ? intent.toUri(0) : null);
    }

    public final boolean isHidden() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.isHidden();
    }

    /* JADX INFO: compiled from: WorkingNote.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 !2\u00020\u0001:\u0001!B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u0011\b\u0012\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\u0004\u0010\bJ\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u001cH\u0016R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\""}, d2 = {"Lcom/miui/notes/model/WorkingNote$SavedState;", "Landroid/os/Parcelable;", "workingNote", "Lcom/miui/notes/model/WorkingNote;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/model/WorkingNote;)V", Language.LA_IN, "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "deleted", "", "getDeleted", "()Z", "setDeleted", "(Z)V", "readonly", "getReadonly", "setReadonly", "invalid", "getInvalid", "setInvalid", "noteEntity", "Lcom/miui/notes/model/NoteEntity;", "getNoteEntity", "()Lcom/miui/notes/model/NoteEntity;", "setNoteEntity", "(Lcom/miui/notes/model/NoteEntity;)V", "describeContents", "", "writeToParcel", "", "dest", "flags", "CREATOR", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SavedState implements Parcelable {

        /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private boolean deleted;
        private boolean invalid;
        private NoteEntity noteEntity;
        private boolean readonly;

        public /* synthetic */ SavedState(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
            this(parcel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public final boolean getDeleted() {
            return this.deleted;
        }

        public final void setDeleted(boolean z) {
            this.deleted = z;
        }

        public final boolean getReadonly() {
            return this.readonly;
        }

        public final void setReadonly(boolean z) {
            this.readonly = z;
        }

        public final boolean getInvalid() {
            return this.invalid;
        }

        public final void setInvalid(boolean z) {
            this.invalid = z;
        }

        public final NoteEntity getNoteEntity() {
            return this.noteEntity;
        }

        public final void setNoteEntity(NoteEntity noteEntity) {
            this.noteEntity = noteEntity;
        }

        public SavedState(WorkingNote workingNote) {
            Intrinsics.checkNotNullParameter(workingNote, "workingNote");
            this.deleted = workingNote.mIsDeleted;
            this.readonly = workingNote.getReadonly();
            this.invalid = workingNote.getIsInvalid();
            this.noteEntity = workingNote.getMNoteEntity();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            Intrinsics.checkNotNullParameter(dest, "dest");
            dest.writeInt(this.deleted ? 1 : 0);
            dest.writeInt(this.readonly ? 1 : 0);
            dest.writeInt(this.invalid ? 1 : 0);
            dest.writeParcelable(this.noteEntity, 0);
        }

        private SavedState(Parcel parcel) {
            this.deleted = parcel.readInt() == 1;
            this.readonly = parcel.readInt() == 1;
            this.invalid = parcel.readInt() == 1;
            this.noteEntity = (NoteEntity) parcel.readParcelable(getClass().getClassLoader());
        }

        /* JADX INFO: renamed from: com.miui.notes.model.WorkingNote$SavedState$CREATOR, reason: from kotlin metadata */
        /* JADX INFO: compiled from: WorkingNote.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001d\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/miui/notes/model/WorkingNote$SavedState$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/miui/notes/model/WorkingNote$SavedState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/miui/notes/model/WorkingNote$SavedState;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class Companion implements Parcelable.Creator<SavedState> {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                Intrinsics.checkNotNullParameter(parcel, "parcel");
                return new SavedState(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }
    }

    public final boolean isEmpty() {
        NoteEntity noteEntity = this.mNoteEntity;
        Intrinsics.checkNotNull(noteEntity);
        return noteEntity.isEmpty();
    }

    public String toString() {
        long noteId = getNoteId();
        long folderId = getFolderId();
        String content = getContent();
        return "workingNote:" + noteId + "," + folderId + ",content_length: " + (content != null ? Integer.valueOf(content.length()) : null);
    }

    /* JADX INFO: compiled from: WorkingNote.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J(\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000eH\u0007J(\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000eH\u0007J'\u0010\u0012\u001a\u00020\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0015H\u0007¢\u0006\u0002\u0010\u0016J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/miui/notes/model/WorkingNote$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "BUNDLE_KEY_WORKING_NOTE_ID", "BUNDLE_KEY_WORKING_NOTE", "FAIL_ID", "", "createEmptyNote", "Lcom/miui/notes/model/WorkingNote;", "folderId", "widgetId", "", "widgetType", "defaultBgColorId", "createEmptyMindNote", "loadNote", "selection", "selectionArgs", "", "(Ljava/lang/String;[Ljava/lang/String;)Lcom/miui/notes/model/WorkingNote;", "savedInstanceState", "Landroid/os/Bundle;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final WorkingNote createEmptyNote(long folderId, int widgetId, int widgetType, int defaultBgColorId) {
            Logger.INSTANCE.d(WorkingNote.TAG, "createEmptyNote");
            WorkingNote workingNote = new WorkingNote(folderId, (DefaultConstructorMarker) null);
            workingNote.setThemeId(defaultBgColorId);
            workingNote.applyWidgetId(widgetId);
            workingNote.applyWidgetType(widgetType);
            return workingNote;
        }

        @JvmStatic
        public final WorkingNote createEmptyMindNote(long folderId, int widgetId, int widgetType, int defaultBgColorId) {
            WorkingNote workingNote = new WorkingNote(folderId, (DefaultConstructorMarker) null);
            workingNote.setThemeId(defaultBgColorId);
            workingNote.applyWidgetId(widgetId);
            workingNote.applyWidgetType(widgetType);
            workingNote.setWorkingNoteType(NoteModel.NoteType.TYPE_MIND);
            return workingNote;
        }

        @JvmStatic
        public final WorkingNote loadNote(String selection, String[] selectionArgs) {
            Logger.INSTANCE.d(WorkingNote.TAG, "loadNote");
            WorkingNote workingNote = new WorkingNote(0L, (DefaultConstructorMarker) null);
            workingNote.load(selection, selectionArgs);
            return workingNote;
        }

        @JvmStatic
        public final WorkingNote loadNote(Bundle savedInstanceState) {
            Logger.INSTANCE.d(WorkingNote.TAG, "loadNote  ");
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (savedInstanceState != null && (savedInstanceState.containsKey(WorkingNote.BUNDLE_KEY_WORKING_NOTE) || savedInstanceState.containsKey(WorkingNote.BUNDLE_KEY_WORKING_NOTE_ID))) {
                SavedState savedState = (SavedState) savedInstanceState.getParcelable(WorkingNote.BUNDLE_KEY_WORKING_NOTE);
                long j = savedInstanceState.getLong(WorkingNote.BUNDLE_KEY_WORKING_NOTE_ID, -2L);
                if ((savedState != null ? savedState.getNoteEntity() : null) != null) {
                    NoteEntityHelper noteEntityHelper = NoteEntityHelper.INSTANCE;
                    NoteEntity noteEntity = savedState.getNoteEntity();
                    Intrinsics.checkNotNull(noteEntity);
                    long modifyDate = noteEntityHelper.getModifyDate(noteEntity.getId());
                    if (modifyDate > 0) {
                        NoteEntity noteEntity2 = savedState.getNoteEntity();
                        Intrinsics.checkNotNull(noteEntity2);
                        if (modifyDate == noteEntity2.getModifiedDate()) {
                            return new WorkingNote(savedState, defaultConstructorMarker);
                        }
                        NoteEntity noteEntity3 = savedState.getNoteEntity();
                        Intrinsics.checkNotNull(noteEntity3);
                        return loadNote("_id=" + noteEntity3.getId(), null);
                    }
                    if (modifyDate == 0) {
                        NoteEntity noteEntity4 = savedState.getNoteEntity();
                        Intrinsics.checkNotNull(noteEntity4);
                        if (Intrinsics.areEqual(noteEntity4.getContent(), "")) {
                            return new WorkingNote(savedState, defaultConstructorMarker);
                        }
                    }
                } else if (j != -2) {
                    return loadNote("_id=" + j, null);
                }
            }
            return null;
        }
    }
}
