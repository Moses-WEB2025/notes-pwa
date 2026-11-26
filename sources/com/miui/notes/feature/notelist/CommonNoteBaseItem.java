package com.miui.notes.feature.notelist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.view.ItemAnimHelper;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.component.BaseItem;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CommonNoteBaseItem extends BaseItem {
    private static final int pressResId = 2131231122;
    public String content;
    protected ImageView mAlarm;
    protected BindContext mBindContext;
    private int mContentType;
    protected ItemCache mItemCache;
    protected IFolme mItemViewFolme;
    protected String mLastImageRes;
    protected ViewGroup mNoteContainer;
    protected ViewGroup mNoteContent;
    protected NoteCache mNoteDataCache;
    protected ImageView mStick;
    protected TextView mTime;
    protected AnimState touchDownScale;
    protected AnimState touchUpScale;

    protected abstract ItemAnimHelper.AnchorInfo getAnchorInfoFromView(View view);

    public abstract ViewGroup getNoteContentContainer();

    public CommonNoteBaseItem(View view) {
        super(view);
        this.touchDownScale = new AnimState("touchDownScale").add(ViewProperty.SCALE_X, 0.9d).add(ViewProperty.SCALE_Y, 0.9d);
        this.touchUpScale = new AnimState("touchUpScale").add(ViewProperty.SCALE_X, 1.0d).add(ViewProperty.SCALE_Y, 1.0d);
        this.mContentType = -1;
        this.mLastImageRes = null;
        this.mItemViewFolme = Folme.useAt(view);
        this.mNoteContainer = (ViewGroup) view.findViewById(R.id.note_group);
        initNoteGroup();
    }

    @Override // com.miui.notes.component.BaseItem
    public void onViewAttachedToWindow() {
        IFolme iFolme;
        if (this.itemView.getScaleX() == 1.0d || (iFolme = this.mItemViewFolme) == null) {
            return;
        }
        iFolme.state().to(this.touchUpScale, new AnimConfig[0]);
    }

    @Override // com.miui.notes.component.BaseItem
    public void bind(ItemCache itemCache, BindContext bindContext, int i) {
        long id;
        if (this.itemView.getVisibility() != 0) {
            this.itemView.setVisibility(0);
        }
        this.mItemCache = itemCache;
        this.mContentType = itemCache.getCacheType();
        this.mBindContext = bindContext;
        if (itemCache.getCacheType() == 0) {
            NoteCache noteCache = (NoteCache) itemCache.getCacheObject();
            id = noteCache.getNote().getId();
            this.content = noteCache.getNote().getSnippet();
            updateTime(noteCache);
            this.mAlarm.setVisibility(noteCache.getNote().getRemindAt() > System.currentTimeMillis() ? 0 : 8);
            this.mNoteDataCache = noteCache;
            this.mNoteContainer.setVisibility(0);
        } else {
            id = 0;
        }
        if (bindContext.isInActionMode()) {
            onUpdateEditable(true, bindContext.isItemIdChecked(id));
        } else {
            onUpdateEditable(false, false);
        }
    }

    protected void setItemBackground(int i) {
        this.mNoteContainer.setBackgroundResource(i);
    }

    protected void initNoteGroup() {
        TextView textView = (TextView) this.mNoteContainer.findViewById(R.id.time);
        this.mTime = textView;
        DisplayUtils.setMiuiRegularTypeFace(textView);
        this.mAlarm = (ImageView) this.mNoteContainer.findViewById(R.id.alarm);
        this.mStick = (ImageView) this.mNoteContainer.findViewById(R.id.stick);
        this.mNoteContent = (ViewGroup) this.mNoteContainer.findViewById(R.id.content);
    }

    public void setIsCallNote(boolean z) {
        this.mIsCallNote = z;
    }

    @Override // com.miui.notes.component.BaseItem
    public int getContentType() {
        return this.mContentType;
    }

    @Override // com.miui.notes.component.BaseItem
    public ItemCache getItemCache() {
        return this.mItemCache;
    }

    public NoteCache getNoteCache() {
        return this.mNoteDataCache;
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        if (z2) {
            this.mNoteContainer.setForeground(ContextCompat.getDrawable(this.mNoteContainer.getContext(), R.drawable.common_note_list_item_press_fg));
        } else {
            this.mNoteContainer.setForeground(null);
        }
        return super.onUpdateEditable(z, z2);
    }

    @Override // com.miui.notes.component.BaseItem
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }

    @Override // com.miui.notes.component.BaseItem
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.itemView.setOnLongClickListener(onLongClickListener);
    }

    private void updateTime(final NoteCache noteCache) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.feature.notelist.CommonNoteBaseItem$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                CommonNoteBaseItem.lambda$updateTime$0(noteCache, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CharSequence>() { // from class: com.miui.notes.feature.notelist.CommonNoteBaseItem.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(CharSequence charSequence) {
                CommonNoteBaseItem.this.mTime.setText(charSequence);
            }
        });
    }

    static /* synthetic */ void lambda$updateTime$0(NoteCache noteCache, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(noteCache.getFormattedSortedDate());
        observableEmitter.onComplete();
    }
}
