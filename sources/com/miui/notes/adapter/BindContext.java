package com.miui.notes.adapter;

import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.notes.ainlsquery.AiQueryNoteExtra;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.ui.widget.EditableRecyclerViewWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BindContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0010\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010C\u001a\u00020D2\f\u0010E\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005J\u0010\u0010F\u001a\u0002062\u0006\u0010G\u001a\u00020\u0019H\u0016J\u0010\u0010I\u001a\u0004\u0018\u00010&2\u0006\u0010J\u001a\u00020\u0019J.\u0010K\u001a\u00020D2&\u0010$\u001a\"\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020&\u0018\u00010%j\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020&\u0018\u0001`'J\u0016\u0010R\u001a\u00020D2\u0006\u00105\u001a\u0002062\u0006\u0010L\u001a\u00020\u0013R*\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00052\f\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u001e\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001f\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R.\u0010$\u001a\"\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020&\u0018\u00010%j\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020&\u0018\u0001`'X\u0082\u000e¢\u0006\u0002\n\u0000R.\u0010(\u001a\u0016\u0012\u0004\u0012\u00020\u0019\u0018\u00010)j\n\u0012\u0004\u0012\u00020\u0019\u0018\u0001`*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001c\u0010/\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\f\"\u0004\b1\u0010\u000eR\u001a\u00102\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0015\"\u0004\b4\u0010\u0017R\u001a\u00105\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00107\"\u0004\b8\u00109R\"\u0010:\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010;X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001a\u0010A\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u00107\"\u0004\bB\u00109R\u0014\u0010H\u001a\u0002068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u00107R$\u0010L\u001a\u00020\u00132\u0006\u0010L\u001a\u00020\u00138F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bM\u0010\u0015\"\u0004\bN\u0010\u0017R\u001a\u0010O\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bP\u00107\"\u0004\bQ\u00109R\u0011\u0010S\u001a\u0002068F¢\u0006\u0006\u001a\u0004\bS\u00107¨\u0006T"}, d2 = {"Lcom/miui/notes/adapter/BindContext;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "value", "Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper;", "wrapper", "getWrapper", "()Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper;", "searchToken", "", "getSearchToken", "()Ljava/lang/String;", "setSearchToken", "(Ljava/lang/String;)V", "searchTokenBackUp", "getSearchTokenBackUp", "setSearchTokenBackUp", "searchType", "", "getSearchType", "()I", "setSearchType", "(I)V", "dragItemId", "", "getDragItemId", "()Ljava/lang/Long;", "setDragItemId", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "newNoteId", "getNewNoteId", "()J", "setNewNoteId", "(J)V", "mAiQueryNoteList", "Ljava/util/HashMap;", "Lcom/miui/notes/ainlsquery/AiQueryNoteExtra;", "Lkotlin/collections/HashMap;", "aiQueryIdArr", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getAiQueryIdArr", "()Ljava/util/ArrayList;", "setAiQueryIdArr", "(Ljava/util/ArrayList;)V", "aiQuerySelection", "getAiQuerySelection", "setAiQuerySelection", "mAiQueryDBProgress", "getMAiQueryDBProgress", "setMAiQueryDBProgress", "isNewGetProgress", "", "()Z", "setNewGetProgress", "(Z)V", "searchActivityLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "getSearchActivityLauncher", "()Landroidx/activity/result/ActivityResultLauncher;", "setSearchActivityLauncher", "(Landroidx/activity/result/ActivityResultLauncher;)V", "isIsNeedDestroySearch", "setIsNeedDestroySearch", "setEditableRecyclerViewWrapper", "", "editableRecyclerViewWrapper", "isItemIdChecked", "id", "isInActionMode", "getAiQueryNoteList", NotifyUtils.KEY_NOTE_ID, "setAiQueryNoteList", "aiQueryDBProgress", "getAiQueryDBProgress", "setAiQueryDBProgress", "checkAiQueryProgress", "getCheckAiQueryProgress", "setCheckAiQueryProgress", "setAiQueryProgress", "isValidAiQueryDBProgress", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BindContext {
    private ArrayList<Long> aiQueryIdArr;
    private String aiQuerySelection;
    private boolean checkAiQueryProgress;
    private Long dragItemId;
    private boolean isIsNeedDestroySearch;
    private boolean isNewGetProgress;
    private HashMap<Long, AiQueryNoteExtra> mAiQueryNoteList;
    private ActivityResultLauncher<Intent> searchActivityLauncher;
    private String searchToken;
    private String searchTokenBackUp;
    private int searchType;
    private EditableRecyclerViewWrapper<?> wrapper;
    private long newNoteId = -1;
    private int mAiQueryDBProgress = -1;

    public final EditableRecyclerViewWrapper<?> getWrapper() {
        return this.wrapper;
    }

    public final String getSearchToken() {
        return this.searchToken;
    }

    public final void setSearchToken(String str) {
        this.searchToken = str;
    }

    public final String getSearchTokenBackUp() {
        return this.searchTokenBackUp;
    }

    public final void setSearchTokenBackUp(String str) {
        this.searchTokenBackUp = str;
    }

    public final int getSearchType() {
        return this.searchType;
    }

    public final void setSearchType(int i) {
        this.searchType = i;
    }

    public final Long getDragItemId() {
        return this.dragItemId;
    }

    public final void setDragItemId(Long l) {
        this.dragItemId = l;
    }

    public final long getNewNoteId() {
        return this.newNoteId;
    }

    public final void setNewNoteId(long j) {
        this.newNoteId = j;
    }

    public final ArrayList<Long> getAiQueryIdArr() {
        return this.aiQueryIdArr;
    }

    public final void setAiQueryIdArr(ArrayList<Long> arrayList) {
        this.aiQueryIdArr = arrayList;
    }

    public final String getAiQuerySelection() {
        return this.aiQuerySelection;
    }

    public final void setAiQuerySelection(String str) {
        this.aiQuerySelection = str;
    }

    public final int getMAiQueryDBProgress() {
        return this.mAiQueryDBProgress;
    }

    public final void setMAiQueryDBProgress(int i) {
        this.mAiQueryDBProgress = i;
    }

    /* JADX INFO: renamed from: isNewGetProgress, reason: from getter */
    public final boolean getIsNewGetProgress() {
        return this.isNewGetProgress;
    }

    public final void setNewGetProgress(boolean z) {
        this.isNewGetProgress = z;
    }

    public final ActivityResultLauncher<Intent> getSearchActivityLauncher() {
        return this.searchActivityLauncher;
    }

    public final void setSearchActivityLauncher(ActivityResultLauncher<Intent> activityResultLauncher) {
        this.searchActivityLauncher = activityResultLauncher;
    }

    /* JADX INFO: renamed from: isIsNeedDestroySearch, reason: from getter */
    public final boolean getIsIsNeedDestroySearch() {
        return this.isIsNeedDestroySearch;
    }

    public final void setIsNeedDestroySearch(boolean z) {
        this.isIsNeedDestroySearch = z;
    }

    public final void setEditableRecyclerViewWrapper(EditableRecyclerViewWrapper<?> editableRecyclerViewWrapper) {
        this.wrapper = editableRecyclerViewWrapper;
    }

    public boolean isItemIdChecked(long id) {
        EditableRecyclerViewWrapper<?> editableRecyclerViewWrapper = this.wrapper;
        if (editableRecyclerViewWrapper == null) {
            return false;
        }
        Intrinsics.checkNotNull(editableRecyclerViewWrapper);
        return editableRecyclerViewWrapper.isItemIdChecked(id);
    }

    public boolean isInActionMode() {
        EditableRecyclerViewWrapper<?> editableRecyclerViewWrapper = this.wrapper;
        if (editableRecyclerViewWrapper == null) {
            return false;
        }
        Intrinsics.checkNotNull(editableRecyclerViewWrapper);
        return editableRecyclerViewWrapper.isInActionMode();
    }

    public final AiQueryNoteExtra getAiQueryNoteList(long noteId) {
        HashMap<Long, AiQueryNoteExtra> map = this.mAiQueryNoteList;
        if (map == null) {
            return null;
        }
        Intrinsics.checkNotNull(map);
        return map.get(Long.valueOf(noteId));
    }

    public final void setAiQueryNoteList(HashMap<Long, AiQueryNoteExtra> mAiQueryNoteList) {
        this.mAiQueryNoteList = mAiQueryNoteList;
    }

    /* JADX INFO: renamed from: getAiQueryDBProgress, reason: from getter */
    public final int getMAiQueryDBProgress() {
        return this.mAiQueryDBProgress;
    }

    public final void setAiQueryDBProgress(int i) {
        setAiQueryProgress(false, i);
    }

    public final boolean getCheckAiQueryProgress() {
        return this.checkAiQueryProgress;
    }

    public final void setCheckAiQueryProgress(boolean z) {
        this.checkAiQueryProgress = z;
    }

    public final void setAiQueryProgress(boolean isNewGetProgress, int aiQueryDBProgress) {
        Logger.INSTANCE.d("BindContext", "setAiQueryProgress isNewGetProgress = " + isNewGetProgress + " aiQueryDBProgress = " + aiQueryDBProgress);
        this.mAiQueryDBProgress = aiQueryDBProgress;
        this.isNewGetProgress = isNewGetProgress;
    }

    public final boolean isValidAiQueryDBProgress() {
        if (this.isNewGetProgress) {
            int i = this.mAiQueryDBProgress;
            return 2 == i || 1 == i;
        }
        int i2 = this.mAiQueryDBProgress;
        return i2 >= 0 && i2 < 100;
    }
}
