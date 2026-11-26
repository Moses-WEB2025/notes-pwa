package com.miui.floatwindow.feature.note;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.app.BaseNoteApp;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cache.CacheManager;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.excerpt.ExcerptDataUtils;
import com.miui.notes.excerpt.ExcerptInfo;
import com.miui.notes.excerpt.LinkUtils;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.NoteProviderAccessUtils;
import com.miui.notes.store.ItemStore;
import com.miui.notes.store.NoteStore;
import defpackage.R2;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: FwNoteListAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 M2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002LMB\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\u000e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000bJ\u0018\u0010$\u001a\u00020\u00022\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0006H\u0016J\u0010\u0010(\u001a\u00020\"2\b\u0010)\u001a\u0004\u0018\u00010\rJ\u0006\u0010*\u001a\u00020\"J\u0010\u0010+\u001a\u00020\"2\b\u0010,\u001a\u0004\u0018\u00010\u0013J\u0018\u0010-\u001a\u00020\"2\b\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u00020\u001bJ\u0018\u00101\u001a\u00020\"2\u0006\u00102\u001a\u00020\u00022\u0006\u00103\u001a\u00020\u0006H\u0016J\b\u00104\u001a\u00020\u0006H\u0016J\u000e\u00105\u001a\u00020\"2\u0006\u00106\u001a\u00020\u0006J\u0006\u00107\u001a\u00020\"J\u0010\u00108\u001a\u00020\u00062\u0006\u00103\u001a\u00020\u0006H\u0016J\u0010\u00109\u001a\u0004\u0018\u00010\u00192\u0006\u00103\u001a\u00020\u0006J\u001a\u0010:\u001a\u0004\u0018\u00010\u00192\u0006\u0010#\u001a\u00020\u000b2\u0006\u00103\u001a\u00020\u0006H\u0002J\u0010\u0010>\u001a\u00020\"2\b\u0010?\u001a\u0004\u0018\u00010\u000fJ\u0010\u0010@\u001a\u00020A2\u0006\u00103\u001a\u00020\u0006H\u0016J\u0010\u0010B\u001a\u00020\"2\b\u0010C\u001a\u0004\u0018\u00010\u0011J\u0006\u0010D\u001a\u00020\"J\u0006\u0010E\u001a\u00020\"J\u000e\u0010F\u001a\u00020\"2\u0006\u0010G\u001a\u00020AJ\"\u0010H\u001a\u00020\"2\u0006\u0010G\u001a\u00020A2\n\u0010I\u001a\u00060JR\u00020\u0000H\u0082@¢\u0006\u0002\u0010KR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u0014\u001a\u0004\u0018\u00010\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u001b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010;\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b<\u0010=¨\u0006N"}, d2 = {"Lcom/miui/floatwindow/feature/note/FwNoteListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/miui/floatwindow/feature/note/FloatBaseItem;", "mContext", "Landroid/content/Context;", "mItemHeight", "", "mContentHeight", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;II)V", "mCursor", "Landroid/database/Cursor;", "mOnClickListener", "Landroid/view/View$OnClickListener;", "mBindContext", "Lcom/miui/notes/adapter/BindContext;", "mDataSource", "Lcom/miui/floatwindow/feature/note/INoteSource;", "value", "Lcom/miui/notes/excerpt/ExcerptInfo;", "excerptInfo", "getExcerptInfo", "()Lcom/miui/notes/excerpt/ExcerptInfo;", "mSavedNoteList", "", "Lcom/miui/notes/cache/ItemCache;", "mIsContinuousExcerpt", "", "isExcerptSaving", "()Z", "mSavedNotePos", "mExcerptNotePos", "mDisplayLimit", "setCursor", "", "cursor", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setOnClickListener", "listener", "clearExcerptInfo", "showSingleExcerptInfo", Constants.JSON_FILTER_INFO, "notifyNoteSaving", "uuid", "Ljava/util/UUID;", "isNeedRefresh", "onBindViewHolder", "holder", "position", "getItemCount", "setDisplayLimit", "displayLimit", "clearDisplayLimit", "getItemViewType", "getItem", "getItemCacheFromCursor", "excerptItemPosition", "getExcerptItemPosition", "()I", "setBindContext", "bindContext", "getItemId", "", "bindDataSource", "dataSource", "clearSavedCache", "saveLinkExcerpt", "notifyExcerptItemChange", NotifyUtils.KEY_NOTE_ID, "asyncQueryNote", "callBack", "Lcom/miui/floatwindow/feature/note/FwNoteListAdapter$QueryNoteCallBack;", "(JLcom/miui/floatwindow/feature/note/FwNoteListAdapter$QueryNoteCallBack;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "QueryNoteCallBack", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FwNoteListAdapter extends RecyclerView.Adapter<FloatBaseItem> {
    private static final String TAG = "FwNoteListAdapter";
    public static final int VIEW_TYPE_CONTINUOUS_EXCERPT_ITEM = 1;
    public static final int VIEW_TYPE_INVALID = -1;
    public static final int VIEW_TYPE_ITEM = 2;
    public static final int VIEW_TYPE_SHOW_ALL = 3;
    public static final int VIEW_TYPE_SINGLE_EXCERPT_ITEM = 0;
    private ExcerptInfo excerptInfo;
    private boolean isExcerptSaving;
    private BindContext mBindContext;
    private final int mContentHeight;
    private final Context mContext;
    private Cursor mCursor;
    private INoteSource mDataSource;
    private int mDisplayLimit;
    private final int mExcerptNotePos;
    private boolean mIsContinuousExcerpt;
    private final int mItemHeight;
    private View.OnClickListener mOnClickListener;
    private final List<ItemCache> mSavedNoteList;
    private int mSavedNotePos;

    public FwNoteListAdapter(Context mContext, int i, int i2) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
        this.mItemHeight = i;
        this.mContentHeight = i2;
        this.mSavedNoteList = new ArrayList();
        this.mSavedNotePos = -1;
        this.mDisplayLimit = -1;
        setHasStableIds(true);
    }

    public final ExcerptInfo getExcerptInfo() {
        return this.excerptInfo;
    }

    /* JADX INFO: renamed from: isExcerptSaving, reason: from getter */
    public final boolean getIsExcerptSaving() {
        return this.isExcerptSaving;
    }

    public final void setCursor(Cursor cursor) {
        Intrinsics.checkNotNullParameter(cursor, "cursor");
        if (this.mCursor != cursor) {
            this.mCursor = cursor;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateViewHolder$lambda$0(FwNoteListAdapter fwNoteListAdapter, View view) {
        if (fwNoteListAdapter.excerptInfo != null) {
            Logger.INSTANCE.d(TAG, "onCreateViewHolder->saveSingleExcerpt");
            ExcerptInfo excerptInfo = fwNoteListAdapter.excerptInfo;
            Intrinsics.checkNotNull(excerptInfo);
            FloatWindowManagerAgent.saveSingleExcerpt(excerptInfo.getUUID(), true);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FloatBaseItem onCreateViewHolder(ViewGroup parent, int viewType) {
        FloatNoteItem floatNoteItemNewInstance;
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (viewType == 0) {
            floatNoteItemNewInstance = FloatNoteItem.INSTANCE.newInstance(parent, this.mItemHeight, this.mContentHeight);
            floatNoteItemNewInstance.setOnSaveListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.note.FwNoteListAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FwNoteListAdapter.onCreateViewHolder$lambda$0(this.f$0, view);
                }
            });
        } else if (viewType == 1 || viewType == 2) {
            floatNoteItemNewInstance = FloatNoteItem.INSTANCE.newInstance(parent, this.mItemHeight, this.mContentHeight);
        } else if (viewType == 3) {
            floatNoteItemNewInstance = ShowAllItem.INSTANCE.newInstance(parent, this.mItemHeight, this.mContentHeight);
        } else {
            Logger.INSTANCE.w(TAG, "Unknown view type: " + viewType);
            floatNoteItemNewInstance = null;
        }
        if (floatNoteItemNewInstance != null) {
            floatNoteItemNewInstance.setOnClickListener(this.mOnClickListener);
        }
        Intrinsics.checkNotNull(floatNoteItemNewInstance);
        return floatNoteItemNewInstance;
    }

    public final void setOnClickListener(View.OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public final void clearExcerptInfo() {
        this.excerptInfo = null;
        this.mIsContinuousExcerpt = false;
        notifyDataSetChanged();
    }

    public final void showSingleExcerptInfo(ExcerptInfo info) {
        int i;
        if (this.excerptInfo == null && (i = this.mSavedNotePos) != -1) {
            this.mSavedNotePos = i + 1;
        }
        this.excerptInfo = info;
        this.mIsContinuousExcerpt = false;
        notifyDataSetChanged();
    }

    public final void notifyNoteSaving(UUID uuid, boolean isNeedRefresh) {
        ExcerptInfo excerptInfo = this.excerptInfo;
        if (excerptInfo != null) {
            Intrinsics.checkNotNull(excerptInfo);
            if (!Intrinsics.areEqual(uuid, excerptInfo.getUUID()) || this.isExcerptSaving) {
                return;
            }
            this.isExcerptSaving = true;
            if (isNeedRefresh) {
                notifyItemChanged(getExcerptItemPosition());
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FloatBaseItem holder, int position) {
        List<ItemCache> list;
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (position == getExcerptItemPosition()) {
            int i = this.mIsContinuousExcerpt ? 2 : 1;
            boolean z = this.isExcerptSaving;
            ExcerptInfo excerptInfo = this.excerptInfo;
            Intrinsics.checkNotNull(excerptInfo);
            holder.bind(i, z, excerptInfo);
        } else if (position == this.mSavedNotePos && (list = this.mSavedNoteList) != null && list.get(0) != null) {
            ItemCache itemCache = this.mSavedNoteList.get(0);
            Intrinsics.checkNotNull(itemCache);
            holder.bind(0, itemCache);
        } else {
            ItemCache item = getItem(position);
            Intrinsics.checkNotNull(item);
            holder.bind(0, item);
        }
        if (this.mDisplayLimit > 0 && ((this.excerptInfo != null && position >= getExcerptItemPosition() + this.mDisplayLimit) || (this.excerptInfo == null && position >= this.mDisplayLimit))) {
            holder.itemView.setVisibility(4);
        } else {
            holder.itemView.setVisibility(0);
        }
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0018  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int size;
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            Intrinsics.checkNotNull(cursor);
            if (cursor.isClosed()) {
                size = 0;
            } else {
                Cursor cursor2 = this.mCursor;
                Intrinsics.checkNotNull(cursor2);
                size = cursor2.getCount();
            }
        } else {
            size = 0;
        }
        List<ItemCache> list = this.mSavedNoteList;
        if (list != null && list.size() > 0) {
            size += this.mSavedNoteList.size();
        }
        return this.excerptInfo != null ? size + 1 : size;
    }

    public final void setDisplayLimit(int displayLimit) {
        this.mDisplayLimit = displayLimit;
        notifyDataSetChanged();
    }

    public final void clearDisplayLimit() {
        this.mDisplayLimit = -1;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        if (position == getExcerptItemPosition()) {
            return this.mIsContinuousExcerpt ? 1 : 0;
        }
        int i = this.mSavedNotePos;
        if (i != -1 && position >= i) {
            if (position <= (i + this.mSavedNoteList.size()) - 1) {
                return 2;
            }
            position -= this.mSavedNoteList.size();
        }
        if (this.excerptInfo != null && position > getExcerptItemPosition()) {
            position--;
        }
        if (position >= 10) {
            return 3;
        }
        if (getItem(position) != null) {
            return 2;
        }
        Logger.INSTANCE.e(TAG, "ItemCache is null");
        return -1;
    }

    public final ItemCache getItem(int position) {
        int i = this.mSavedNotePos;
        if (i != -1 && position >= i) {
            if (position <= (i + this.mSavedNoteList.size()) - 1) {
                return this.mSavedNoteList.get(position - this.mSavedNotePos);
            }
            position -= this.mSavedNoteList.size();
        }
        if (this.excerptInfo != null && position > getExcerptItemPosition()) {
            position--;
        }
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            Logger.INSTANCE.e(TAG, "mCursor is null");
            return null;
        }
        Intrinsics.checkNotNull(cursor);
        if (cursor.moveToPosition(position)) {
            Cursor cursor2 = this.mCursor;
            Intrinsics.checkNotNull(cursor2);
            return getItemCacheFromCursor(cursor2, position);
        }
        Logger logger = Logger.INSTANCE;
        Cursor cursor3 = this.mCursor;
        Intrinsics.checkNotNull(cursor3);
        logger.e(TAG, "moveToPosition fail! pos:" + position + "cursor count:" + cursor3.getCount());
        return null;
    }

    private final ItemCache getItemCacheFromCursor(Cursor cursor, int position) {
        if (position >= cursor.getCount()) {
            Logger.INSTANCE.e(TAG, "position bigger than cursor count ! pos:" + position + "cursor count:" + cursor.getCount());
            return null;
        }
        int type = ItemStore.getType(cursor);
        long id = ItemStore.getId(cursor);
        if (type == 0) {
            NoteCache note = CacheManager.getDefault().getNote(id);
            NoteModel note2 = ItemStore.parseNote(cursor);
            if (note == null) {
                note = CacheManager.getDefault().getNote(note2);
            } else {
                note.update(note2);
            }
            return new ItemCache(0, note);
        }
        Logger.INSTANCE.e(TAG, "note type error");
        return null;
    }

    public final int getExcerptItemPosition() {
        if (this.excerptInfo == null) {
            return -1;
        }
        return this.mExcerptNotePos;
    }

    public final void setBindContext(BindContext bindContext) {
        this.mBindContext = bindContext;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        if (this.excerptInfo != null && position == getExcerptItemPosition()) {
            ExcerptInfo excerptInfo = this.excerptInfo;
            Intrinsics.checkNotNull(excerptInfo);
            return excerptInfo.getExcerptContent().hashCode();
        }
        int i = this.mSavedNotePos;
        if (i != -1 && position >= i) {
            if (position <= (i + this.mSavedNoteList.size()) - 1) {
                ItemCache itemCache = this.mSavedNoteList.get(position - this.mSavedNotePos);
                Intrinsics.checkNotNull(itemCache);
                return itemCache.getCacheId();
            }
            position -= this.mSavedNoteList.size();
        }
        if (this.excerptInfo != null && position > getExcerptItemPosition()) {
            position--;
        }
        if (position < 10) {
            Cursor cursor = this.mCursor;
            if (cursor == null) {
                return -1L;
            }
            Intrinsics.checkNotNull(cursor);
            if (cursor.moveToPosition(position)) {
                return NoteStore.getId(this.mCursor);
            }
            return -1L;
        }
        return this.mContext.getString(R.string.view_all_notes).hashCode();
    }

    public final void bindDataSource(INoteSource dataSource) {
        clearSavedCache();
        this.mDataSource = dataSource;
        Intrinsics.checkNotNull(dataSource);
        Cursor data = dataSource.getData();
        Intrinsics.checkNotNull(data);
        setCursor(data);
    }

    public final void clearSavedCache() {
        this.mSavedNotePos = -1;
        this.mSavedNoteList.clear();
    }

    public final void saveLinkExcerpt() {
        ExcerptInfo excerptInfo = this.excerptInfo;
        if (excerptInfo == null) {
            Logger.INSTANCE.e(TAG, "mExcerptInfo is null");
            return;
        }
        Intrinsics.checkNotNull(excerptInfo);
        String excerptContent = excerptInfo.getExcerptContent();
        if (LinkUtils.isAccurateValidUrl(excerptContent)) {
            ExcerptInfo excerptInfo2 = this.excerptInfo;
            Intrinsics.checkNotNull(excerptInfo2);
            ExcerptDataUtils.saveLinkExcerpt(excerptContent, "", excerptInfo2.getUUID());
            return;
        }
        Logger.INSTANCE.e(TAG, "content is not url");
    }

    public final void notifyExcerptItemChange(long noteId) {
        Logger.INSTANCE.e(TAG, "notifyExcerptItemChange");
        this.isExcerptSaving = false;
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass1(noteId, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.feature.note.FwNoteListAdapter$notifyExcerptItemChange$1, reason: invalid class name */
    /* JADX INFO: compiled from: FwNoteListAdapter.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.floatwindow.feature.note.FwNoteListAdapter$notifyExcerptItemChange$1", f = "FwNoteListAdapter.kt", i = {}, l = {315}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $noteId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(long j, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$noteId = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FwNoteListAdapter.this.new AnonymousClass1(this.$noteId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (FwNoteListAdapter.this.asyncQueryNote(this.$noteId, FwNoteListAdapter.this.new QueryNoteCallBack(this.$noteId), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: FwNoteListAdapter.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lcom/miui/floatwindow/feature/note/FwNoteListAdapter$QueryNoteCallBack;", "", "mNoteId", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/floatwindow/feature/note/FwNoteListAdapter;J)V", "cursor", "Landroid/database/Cursor;", "getCursor", "()Landroid/database/Cursor;", "setCursor", "(Landroid/database/Cursor;)V", "dealQueryResult", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class QueryNoteCallBack {
        private Cursor cursor;
        private final long mNoteId;

        public QueryNoteCallBack(long j) {
            this.mNoteId = j;
        }

        public final Cursor getCursor() {
            return this.cursor;
        }

        public final void setCursor(Cursor cursor) {
            this.cursor = cursor;
        }

        public final void dealQueryResult() {
            Cursor cursor = this.cursor;
            if (cursor != null) {
                Intrinsics.checkNotNull(cursor);
                if (cursor.getCount() != 0) {
                    Cursor cursor2 = this.cursor;
                    Intrinsics.checkNotNull(cursor2);
                    cursor2.moveToPosition(0);
                    NoteCache note = CacheManager.getDefault().getNote(this.mNoteId);
                    NoteModel note2 = ItemStore.parseNote(this.cursor);
                    if (note == null) {
                        note = CacheManager.getDefault().getNote(note2);
                    } else {
                        note.update(note2);
                    }
                    FwNoteListAdapter.this.mSavedNoteList.add(0, new ItemCache(0, note));
                    FwNoteListAdapter fwNoteListAdapter = FwNoteListAdapter.this;
                    fwNoteListAdapter.mSavedNotePos = fwNoteListAdapter.getExcerptItemPosition();
                    if (!FwNoteListAdapter.this.mIsContinuousExcerpt) {
                        FwNoteListAdapter.this.excerptInfo = null;
                    }
                    FwNoteListAdapter fwNoteListAdapter2 = FwNoteListAdapter.this;
                    fwNoteListAdapter2.notifyItemChanged(fwNoteListAdapter2.mSavedNotePos);
                    return;
                }
            }
            Logger.INSTANCE.e(FwNoteListAdapter.TAG, "can not find note");
            FwNoteListAdapter.this.excerptInfo = null;
            FwNoteListAdapter.this.notifyDataSetChanged();
        }
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.feature.note.FwNoteListAdapter$asyncQueryNote$2, reason: invalid class name */
    /* JADX INFO: compiled from: FwNoteListAdapter.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.floatwindow.feature.note.FwNoteListAdapter$asyncQueryNote$2", f = "FwNoteListAdapter.kt", i = {0}, l = {R2.attr.actionModeWebSearchDrawable}, m = "invokeSuspend", n = {"$this$flow"}, s = {"L$0"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<FlowCollector<? super Boolean>, Continuation<? super Unit>, Object> {
        final /* synthetic */ QueryNoteCallBack $callBack;
        final /* synthetic */ long $noteId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(QueryNoteCallBack queryNoteCallBack, long j, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$callBack = queryNoteCallBack;
            this.$noteId = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$callBack, this.$noteId, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector<? super Boolean> flowCollector, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.$callBack.setCursor(NoteProviderAccessUtils.queryNote(BaseNoteApp.INSTANCE.getInstance(), this.$noteId));
                this.L$0 = SpillingKt.nullOutSpilledVariable(flowCollector);
                this.label = 1;
                if (flowCollector.emit(Boxing.boxBoolean(true), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object asyncQueryNote(long j, final QueryNoteCallBack queryNoteCallBack, Continuation<? super Unit> continuation) {
        Object objCollect = FlowKt.flowOn(FlowKt.flow(new AnonymousClass2(queryNoteCallBack, j, null)), Dispatchers.getIO()).collect(new FlowCollector() { // from class: com.miui.floatwindow.feature.note.FwNoteListAdapter.asyncQueryNote.3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation2) {
                return emit(((Boolean) obj).booleanValue(), (Continuation<? super Unit>) continuation2);
            }

            public final Object emit(boolean z, Continuation<? super Unit> continuation2) {
                if (z) {
                    queryNoteCallBack.dealQueryResult();
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }
}
