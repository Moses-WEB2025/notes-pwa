package com.miui.floatwindow.feature.note;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.floatwindow.frame.IFloatWindowView;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.excerpt.ExcerptInfo;
import com.miui.notes.excerpt.LinkUtils;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.model.NoteModel;
import com.miui.notes.tool.PreferenceUtils;
import defpackage.R2;
import java.lang.ref.WeakReference;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;

/* JADX INFO: compiled from: FwNoteListView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u000f\u0018\u0000 L2\u00020\u00012\u00020\u0002:\u0003JKLB\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010(\u001a\u00020)H\u0016J\u0006\u0010*\u001a\u00020)J\n\u0010+\u001a\u0004\u0018\u00010\u0010H\u0016J\u0018\u0010,\u001a\u00020)2\b\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u00020\u001dJ\u0006\u00100\u001a\u00020)J\u0010\u00101\u001a\u00020)2\b\u00102\u001a\u0004\u0018\u00010\u0014J\u0010\u00103\u001a\u00020)2\b\u00104\u001a\u0004\u0018\u00010\u0002J\b\u00105\u001a\u00020)H\u0016J\b\u00106\u001a\u00020)H\u0016J\b\u00107\u001a\u00020)H\u0016J\u001a\u0010:\u001a\u00020)2\b\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010=\u001a\u00020>H\u0016J\b\u0010?\u001a\u00020\u001dH\u0016J\b\u0010@\u001a\u00020)H\u0016J\u0018\u0010A\u001a\u00020)2\b\u0010B\u001a\u0004\u0018\u00010\u001b2\u0006\u0010C\u001a\u00020\u001dR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020#8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020#8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b'\u0010%R\u0014\u00108\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b9\u0010%R\u0014\u0010D\u001a\u00020#8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bE\u0010%R$\u0010F\u001a\u00020\u001d2\u0006\u0010F\u001a\u00020\u001d8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bF\u0010G\"\u0004\bH\u0010I¨\u0006M"}, d2 = {"Lcom/miui/floatwindow/feature/note/FwNoteListView;", "Lcom/miui/floatwindow/feature/note/IBaseNoteListView;", "Lcom/miui/floatwindow/frame/IFloatWindowView;", "mRootView", "Landroid/view/View;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;)V", "mContext", "Landroid/content/Context;", "mToggleView", "Landroid/widget/ImageView;", "mInvisibleItem", "mParentHelper", "mRvNote", "Landroidx/recyclerview/widget/RecyclerView;", "mAdapter", "Lcom/miui/floatwindow/feature/note/FwNoteListAdapter;", "mBindContext", "Lcom/miui/notes/adapter/BindContext;", "mPresenter", "Lcom/miui/floatwindow/feature/note/FwNoteListPresenter;", "mContent", "mBlankView", "mBtnAddNote", "mHandler", "Landroid/os/Handler;", "mNeedJumpUuid", "Ljava/util/UUID;", "mExpandCached", "", "mTimeOutTask", "Ljava/lang/Runnable;", "mItemClickListener", "Landroid/view/View$OnClickListener;", "floatItemHeight", "", "getFloatItemHeight", "()I", "floatContentHeight", "getFloatContentHeight", "onDataLoaded", "", "restoreDisplayPosition", "getListAdapter", "showSingleExcerptInfo", Constants.JSON_FILTER_INFO, "Lcom/miui/notes/excerpt/ExcerptInfo;", "needScrollToExcerpt", "clearExcerptInfo", "setPresenter", "presenter", "setParentHelper", "helper", "preToFullWindow", "onFullWindowShow", "preChangeToGuide", "visibleViewWidth", "getVisibleViewWidth", "showToast", "content", "", "time", "", "canToGuide", "onDetachFromWindow", "notifyNoteSaving", "uuid", "isNeedRefresh", "contentHeight", "getContentHeight", "isExpand", "()Z", "setExpand", "(Z)V", "ExcerptHandler", "SpacesItemDecoration", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FwNoteListView implements IBaseNoteListView, IFloatWindowView {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final long MAX_TIME_OUT = 60000;
    private static final String TAG = "FwNoteListView";
    private FwNoteListAdapter mAdapter;
    private final BindContext mBindContext;
    private final View mBlankView;
    private final View mBtnAddNote;
    private final View mContent;
    private final Context mContext;
    private volatile boolean mExpandCached;
    private final Handler mHandler;
    private final View mInvisibleItem;
    private final View.OnClickListener mItemClickListener;
    private UUID mNeedJumpUuid;
    private IFloatWindowView mParentHelper;
    private FwNoteListPresenter mPresenter;
    private RecyclerView mRvNote;
    private final Runnable mTimeOutTask;
    private final ImageView mToggleView;

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public boolean canToGuide() {
        return true;
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void onDetachFromWindow() {
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void preChangeToGuide() {
    }

    public FwNoteListView(View mRootView) {
        Intrinsics.checkNotNullParameter(mRootView, "mRootView");
        final BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
        this.mContext = companion;
        this.mTimeOutTask = new Runnable() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                FwNoteListView.mTimeOutTask$lambda$0(this.f$0);
            }
        };
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FwNoteListView.mItemClickListener$lambda$2(this.f$0, view);
            }
        };
        this.mItemClickListener = onClickListener;
        companion.setTheme(R.style.NoteTheme);
        View viewFindViewById = mRootView.findViewById(R.id.recycler_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.mRvNote = (RecyclerView) viewFindViewById;
        View viewFindViewById2 = mRootView.findViewById(R.id.invisible_card_item);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.mInvisibleItem = viewFindViewById2;
        View viewFindViewById3 = mRootView.findViewById(R.id.iv_switch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        ImageView imageView = (ImageView) viewFindViewById3;
        this.mToggleView = imageView;
        imageView.setRotation(180.0f);
        imageView.setContentDescription(companion.getString(R.string.action_expand));
        View viewFindViewById4 = mRootView.findViewById(R.id.content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        this.mContent = viewFindViewById4;
        this.mAdapter = new FwNoteListAdapter(companion, getFloatItemHeight(), getFloatContentHeight());
        BindContext bindContext = new BindContext();
        this.mBindContext = bindContext;
        this.mAdapter.setBindContext(bindContext);
        this.mAdapter.setOnClickListener(onClickListener);
        this.mRvNote.setAdapter(this.mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(companion) { // from class: com.miui.floatwindow.feature.note.FwNoteListView$layoutManager$1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(0);
        this.mRvNote.setLayoutManager(linearLayoutManager);
        this.mRvNote.addItemDecoration(new SpacesItemDecoration(this, this.mAdapter));
        this.mRvNote.setVisibility(8);
        View viewFindViewById5 = mRootView.findViewById(R.id.blankview_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        this.mBlankView = viewFindViewById5;
        viewFindViewById5.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FloatWindowManagerAgent.changeFullLayoutToGuideService();
            }
        });
        viewFindViewById5.setVisibility(0);
        View viewFindViewById6 = mRootView.findViewById(R.id.iv_data_add);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
        this.mBtnAddNote = viewFindViewById6;
        viewFindViewById6.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FwNoteListView._init_$lambda$5(this.f$0, view);
            }
        });
        Folme.useAt(viewFindViewById6).touch().handleTouchOf(viewFindViewById6, new AnimConfig[0]);
        ExcerptHandler excerptHandler = new ExcerptHandler(this);
        this.mHandler = excerptHandler;
        NotifyUtils.getInstance().setHandler(excerptHandler);
        new Thread(new Runnable() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                FwNoteListView._init_$lambda$7(this.f$0);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mTimeOutTask$lambda$0(FwNoteListView fwNoteListView) {
        Logger.INSTANCE.i(TAG, "time out, saveLinkExcerpt");
        FwNoteListAdapter mAdapter = fwNoteListView.getMAdapter();
        if (mAdapter != null) {
            mAdapter.saveLinkExcerpt();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mItemClickListener$lambda$2(FwNoteListView fwNoteListView, View view) {
        int childLayoutPosition = fwNoteListView.mRvNote.getChildLayoutPosition(view);
        int itemViewType = fwNoteListView.mAdapter.getItemViewType(childLayoutPosition);
        if (itemViewType == 0) {
            ExcerptInfo excerptInfo = fwNoteListView.mAdapter.getExcerptInfo();
            if (excerptInfo != null) {
                FloatWindowManagerAgent.saveSingleExcerpt(excerptInfo.getUUID(), false);
                if (LinkUtils.isAccurateValidUrl(excerptInfo.getExcerptContent())) {
                    return;
                }
                fwNoteListView.mNeedJumpUuid = excerptInfo.getUUID();
                return;
            }
            return;
        }
        if (itemViewType != 2) {
            if (itemViewType != 3) {
                return;
            }
            FloatWindowManagerAgent.changeFullLayoutToGuideService();
            view.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FwNoteListView.mItemClickListener$lambda$2$lambda$1();
                }
            }, 200L);
            return;
        }
        ItemCache item = fwNoteListView.mAdapter.getItem(childLayoutPosition);
        if (item != null) {
            Object cacheObject = item.getCacheObject();
            Intrinsics.checkNotNull(cacheObject, "null cannot be cast to non-null type com.miui.notes.cache.NoteCache");
            NoteCache noteCache = (NoteCache) cacheObject;
            Folme.useAt(view).touch().setTouchUp();
            FloatWindowManagerAgent.changeFullLayoutToGuideService();
            Utils utils = Utils.INSTANCE;
            Utils.isDoodleOnWin = false;
            Utils utils2 = Utils.INSTANCE;
            Utils.isCtaOnWin = false;
            if (noteCache.getNoteContentType() == 5) {
                NoteOperationHelper noteOperationHelper = NoteOperationHelper.INSTANCE;
                NoteModel note = noteCache.getNote();
                Intrinsics.checkNotNullExpressionValue(note, "getNote(...)");
                NoteModel note2 = noteCache.getNote();
                Intrinsics.checkNotNull(note2);
                String mindContent = note2.getMindContent();
                Intrinsics.checkNotNull(mindContent);
                noteOperationHelper.openMindNote(note, mindContent);
                return;
            }
            if (noteCache.getNoteContentType() == 6 || noteCache.getNoteContentType() == 7) {
                NoteOperationHelper.INSTANCE.openMultiHandWriteNote(noteCache);
            } else {
                NoteOperationHelper.INSTANCE.openNote(noteCache);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mItemClickListener$lambda$2$lambda$1() {
        NoteOperationHelper.INSTANCE.showAllNotes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$5(FwNoteListView fwNoteListView, View view) {
        FloatWindowManagerAgent.changeFullLayoutToGuideService();
        FwNoteListAdapter mAdapter = fwNoteListView.getMAdapter();
        Intrinsics.checkNotNull(mAdapter);
        if (!mAdapter.getIsExcerptSaving()) {
            FwNoteListAdapter mAdapter2 = fwNoteListView.getMAdapter();
            Intrinsics.checkNotNull(mAdapter2);
            mAdapter2.clearExcerptInfo();
        }
        if (UIUtils.isBrowseMode(fwNoteListView.mContext)) {
            Toast.makeText(fwNoteListView.mContext, R.string.warn_enter_browse_mode_forbid_create, 0).show();
        } else {
            new Handler().postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    FwNoteListView.lambda$5$lambda$4();
                }
            }, 400L);
        }
    }

    static final void lambda$5$lambda$4() {
        Utils utils = Utils.INSTANCE;
        Utils.isDoodleOnWin = false;
        Utils utils2 = Utils.INSTANCE;
        Utils.isCtaOnWin = false;
        NoteOperationHelper.INSTANCE.addNote(0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$7(final FwNoteListView fwNoteListView) {
        boolean isFloatNotesExpand = PreferenceUtils.getIsFloatNotesExpand(fwNoteListView.mContext);
        fwNoteListView.mExpandCached = isFloatNotesExpand;
        if (isFloatNotesExpand) {
            fwNoteListView.mToggleView.post(new Runnable() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    FwNoteListView.lambda$7$lambda$6(this.f$0);
                }
            });
        }
    }

    static final void lambda$7$lambda$6(FwNoteListView fwNoteListView) {
        fwNoteListView.mToggleView.setRotation(0.0f);
        fwNoteListView.mToggleView.setContentDescription(fwNoteListView.mContext.getString(R.string.action_collapse));
        fwNoteListView.mContent.setVisibility(0);
        fwNoteListView.mContent.getLayoutParams().height = fwNoteListView.getContentHeight();
        fwNoteListView.mContent.requestLayout();
    }

    private final int getFloatItemHeight() {
        this.mInvisibleItem.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        return this.mInvisibleItem.getMeasuredHeight();
    }

    private final int getFloatContentHeight() {
        View viewFindViewById = this.mInvisibleItem.findViewById(R.id.note_content);
        viewFindViewById.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        return viewFindViewById.getMeasuredHeight();
    }

    /* JADX INFO: compiled from: FwNoteListView.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u001c\u0010\u0006\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00030\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/floatwindow/feature/note/FwNoteListView$ExcerptHandler;", "Landroid/os/Handler;", "view", "Lcom/miui/floatwindow/feature/note/FwNoteListView;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/floatwindow/feature/note/FwNoteListView;)V", "viewRefs", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "handleMessage", "", "msg", "Landroid/os/Message;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class ExcerptHandler extends Handler {
        private final WeakReference<FwNoteListView> viewRefs;

        public ExcerptHandler(FwNoteListView view) {
            Intrinsics.checkNotNullParameter(view, "view");
            this.viewRefs = new WeakReference<>(view);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            super.handleMessage(msg);
            Bundle data = msg.getData();
            UUID uuid = (UUID) data.getSerializable("uuid");
            long j = data.getLong(NotifyUtils.KEY_NOTE_ID);
            boolean z = data.getBoolean("result");
            Logger.INSTANCE.i(FwNoteListView.TAG, "msg uuid:, isExcerptSuccess:" + z + ", noteId:" + j);
            FwNoteListView fwNoteListView = this.viewRefs.get();
            if (fwNoteListView != null) {
                FwNoteListAdapter fwNoteListAdapter = fwNoteListView.mAdapter;
                Intrinsics.checkNotNull(fwNoteListAdapter);
                ExcerptInfo excerptInfo = fwNoteListAdapter.getExcerptInfo();
                if (excerptInfo == null || !Intrinsics.areEqual(uuid, excerptInfo.getUUID())) {
                    return;
                }
                removeCallbacks(fwNoteListView.mTimeOutTask);
                if (!z) {
                    fwNoteListView.mAdapter.saveLinkExcerpt();
                    return;
                }
                if (j > 0 && fwNoteListView.mNeedJumpUuid == uuid) {
                    FloatWindowManagerAgent.changeFullLayoutToGuideService();
                    NoteOperationHelper.INSTANCE.openNote(j);
                }
                fwNoteListView.mAdapter.notifyExcerptItemChange(j);
                return;
            }
            Logger.INSTANCE.e(FwNoteListView.TAG, "FwNoteListView is null");
        }
    }

    @Override // com.miui.floatwindow.feature.note.IBaseNoteListView
    public void onDataLoaded() {
        FwNoteListAdapter fwNoteListAdapter = this.mAdapter;
        Intrinsics.checkNotNull(fwNoteListAdapter);
        if (fwNoteListAdapter.getItemCount() != 0) {
            RecyclerView recyclerView = this.mRvNote;
            Intrinsics.checkNotNull(recyclerView);
            recyclerView.setVisibility(0);
            this.mBlankView.setVisibility(8);
            restoreDisplayPosition();
            this.mAdapter.notifyDataSetChanged();
            return;
        }
        RecyclerView recyclerView2 = this.mRvNote;
        Intrinsics.checkNotNull(recyclerView2);
        recyclerView2.setVisibility(8);
        this.mBlankView.setVisibility(0);
    }

    public final void restoreDisplayPosition() {
        if (this.mAdapter.getExcerptItemPosition() >= 0) {
            int excerptItemPosition = this.mAdapter.getExcerptItemPosition();
            this.mRvNote.smoothScrollToPosition(excerptItemPosition);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mRvNote.getLayoutManager();
            Intrinsics.checkNotNull(linearLayoutManager);
            linearLayoutManager.scrollToPositionWithOffset(excerptItemPosition, 20);
            return;
        }
        this.mRvNote.smoothScrollToPosition(0);
    }

    @Override // com.miui.floatwindow.feature.note.IBaseNoteListView
    /* JADX INFO: renamed from: getListAdapter, reason: from getter */
    public FwNoteListAdapter getMAdapter() {
        return this.mAdapter;
    }

    public final void showSingleExcerptInfo(ExcerptInfo info, boolean needScrollToExcerpt) {
        Logger.INSTANCE.d(TAG, "showSingleExcerptInfo..." + needScrollToExcerpt);
        if (info == null) {
            Logger.INSTANCE.e(TAG, "ExcerptInfo is null");
        }
        if (this.mBlankView.getVisibility() == 0) {
            this.mRvNote.setVisibility(0);
            this.mBlankView.setVisibility(8);
        }
        this.mAdapter.showSingleExcerptInfo(info);
        if (needScrollToExcerpt) {
            restoreDisplayPosition();
        }
    }

    public final void clearExcerptInfo() {
        FwNoteListAdapter fwNoteListAdapter = this.mAdapter;
        Intrinsics.checkNotNull(fwNoteListAdapter);
        fwNoteListAdapter.clearExcerptInfo();
        if (this.mAdapter.getItemCount() == 0) {
            RecyclerView recyclerView = this.mRvNote;
            Intrinsics.checkNotNull(recyclerView);
            recyclerView.setVisibility(8);
            this.mBlankView.setVisibility(0);
        }
    }

    public final void setPresenter(FwNoteListPresenter presenter) {
        this.mPresenter = presenter;
    }

    /* JADX INFO: compiled from: FwNoteListView.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J(\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/miui/floatwindow/feature/note/FwNoteListView$SpacesItemDecoration;", "Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;", "mAdapter", "Lcom/miui/floatwindow/feature/note/FwNoteListAdapter;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/floatwindow/feature/note/FwNoteListView;Lcom/miui/floatwindow/feature/note/FwNoteListAdapter;)V", "firstSpace", "", "normalSpace", "lastSpace", "getItemOffsets", "", "outRect", "Landroid/graphics/Rect;", "view", "Landroid/view/View;", "parent", "Landroidx/recyclerview/widget/RecyclerView;", "state", "Landroidx/recyclerview/widget/RecyclerView$State;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int firstSpace;
        private final int lastSpace;
        private final FwNoteListAdapter mAdapter;
        private final int normalSpace;
        final /* synthetic */ FwNoteListView this$0;

        public SpacesItemDecoration(FwNoteListView fwNoteListView, FwNoteListAdapter mAdapter) {
            Intrinsics.checkNotNullParameter(mAdapter, "mAdapter");
            this.this$0 = fwNoteListView;
            this.mAdapter = mAdapter;
            this.firstSpace = (int) fwNoteListView.mContext.getResources().getDimension(R.dimen.ic_44px_size);
            this.normalSpace = (int) fwNoteListView.mContext.getResources().getDimension(R.dimen.ic_28px_size);
            this.lastSpace = (int) fwNoteListView.mContext.getResources().getDimension(R.dimen.ic_68px_size);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            Intrinsics.checkNotNullParameter(outRect, "outRect");
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(parent, "parent");
            Intrinsics.checkNotNullParameter(state, "state");
            if (parent.getLayoutDirection() == 1) {
                outRect.right = this.normalSpace;
                if (parent.getChildPosition(view) == 0) {
                    outRect.right = this.firstSpace;
                }
                if (parent.getChildPosition(view) == this.mAdapter.getItemCount() - 1) {
                    outRect.left = this.lastSpace;
                    return;
                }
                return;
            }
            outRect.left = this.normalSpace;
            if (parent.getChildPosition(view) == 0) {
                outRect.left = this.firstSpace;
            }
            if (parent.getChildPosition(view) == this.mAdapter.getItemCount() - 1) {
                outRect.right = this.lastSpace;
            }
        }
    }

    public final void setParentHelper(IFloatWindowView helper) {
        this.mParentHelper = helper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void preToFullWindow$lambda$8(FwNoteListView fwNoteListView) {
        FwNoteListPresenter fwNoteListPresenter = fwNoteListView.mPresenter;
        Intrinsics.checkNotNull(fwNoteListPresenter);
        fwNoteListPresenter.subscribe();
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void preToFullWindow() {
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FwNoteListView.preToFullWindow$lambda$8(this.f$0);
            }
        }, 250L);
        if (!getMExpandCached()) {
            this.mContent.setVisibility(8);
        } else {
            this.mAdapter.setDisplayLimit(2);
        }
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void onFullWindowShow() {
        if (getMExpandCached()) {
            this.mAdapter.clearDisplayLimit();
        }
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public int getVisibleViewWidth() {
        return DisplayUtils.getRealWidth(this.mContext);
    }

    @Override // com.miui.floatwindow.frame.IFloatWindowView
    public void showToast(String content, long time) {
        IFloatWindowView iFloatWindowView = this.mParentHelper;
        Intrinsics.checkNotNull(iFloatWindowView);
        iFloatWindowView.showToast(content, time);
    }

    public final void notifyNoteSaving(UUID uuid, boolean isNeedRefresh) {
        FwNoteListAdapter mAdapter = getMAdapter();
        if (mAdapter != null) {
            mAdapter.notifyNoteSaving(uuid, isNeedRefresh);
        }
        Logger.INSTANCE.i(TAG, "postDelayed time out task");
        this.mHandler.postDelayed(this.mTimeOutTask, 60000L);
    }

    private final int getContentHeight() {
        return getFloatItemHeight() + this.mRvNote.getPaddingTop() + this.mRvNote.getPaddingBottom();
    }

    /* JADX INFO: renamed from: isExpand, reason: from getter */
    public final boolean getMExpandCached() {
        return this.mExpandCached;
    }

    public final void setExpand(boolean z) {
        this.mExpandCached = z;
        PreferenceUtils.setFloatNotesExpand(z);
        if (this.mContent.getVisibility() == 8) {
            this.mContent.setVisibility(0);
        }
        if (z) {
            INSTANCE.changeViewHeightAnimatorStart(this.mContent, 0, getContentHeight());
            this.mToggleView.setContentDescription(this.mContext.getString(R.string.action_collapse));
        } else {
            INSTANCE.changeViewHeightAnimatorStart(this.mContent, getContentHeight(), 0);
            this.mToggleView.setContentDescription(this.mContext.getString(R.string.action_expand));
        }
        this.mToggleView.animate().rotation(z ? 0 : R2.array.ai_tag_top_item_gradient_border_colors).setDuration(200L).start();
    }

    /* JADX INFO: compiled from: FwNoteListView.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/floatwindow/feature/note/FwNoteListView$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MAX_TIME_OUT", "", "TAG", "", "changeViewHeightAnimatorStart", "", "view", "Landroid/view/View;", "startHeight", "", "endHeight", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void changeViewHeightAnimatorStart(final View view, final int startHeight, final int endHeight) {
            if (view == null || startHeight < 0 || endHeight < 0) {
                return;
            }
            ValueAnimator duration = ValueAnimator.ofInt(startHeight, endHeight).setDuration(400L);
            duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.floatwindow.feature.note.FwNoteListView$Companion$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    FwNoteListView.Companion.changeViewHeightAnimatorStart$lambda$0(view, startHeight, endHeight, valueAnimator);
                }
            });
            duration.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void changeViewHeightAnimatorStart$lambda$0(View view, int i, int i2, ValueAnimator animation) {
            Intrinsics.checkNotNullParameter(animation, "animation");
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            Object animatedValue = animation.getAnimatedValue();
            Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
            layoutParams.height = ((Integer) animatedValue).intValue();
            view.requestLayout();
            if (i > i2) {
                view.setAlpha(layoutParams.height / i);
            } else if (i < i2) {
                view.setAlpha(layoutParams.height / i2);
            }
        }
    }
}
