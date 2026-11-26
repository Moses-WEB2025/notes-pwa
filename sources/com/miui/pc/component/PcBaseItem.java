package com.miui.pc.component;

import android.R;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.ui.widget.ViewHolderRecycledCallback;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.ITouchStyle;

/* JADX INFO: loaded from: classes4.dex */
public abstract class PcBaseItem extends RecyclerView.ViewHolder implements ViewHolderEditableCallback, ViewHolderRecycledCallback {
    public String content;
    protected ImageView mAlarm;
    protected BindContext mBindContext;
    private int mBindPosition;
    protected CheckBox mCheck;
    private int mContentType;
    protected ItemCache mItemCache;
    protected IFolme mItemViewFolme;
    protected String mLastImageRes;
    protected PcNoteBaseContent mNoteBaseContent;
    protected ViewGroup mNoteContainer;
    protected ViewGroup mNoteContent;
    protected NoteCache mNoteDataCache;
    protected boolean mStarted;
    protected ImageView mStick;
    protected TextView mTime;

    public abstract ViewGroup getNoteContentContainer();

    public PcBaseItem(View view) {
        super(view);
        this.mContentType = -1;
        this.mItemViewFolme = null;
        this.mBindPosition = -1;
        this.mLastImageRes = null;
        this.mCheck = (CheckBox) view.findViewById(R.id.checkbox);
        view.setLongClickable(false);
        IFolme iFolmeUseAt = Folme.useAt(view);
        this.mItemViewFolme = iFolmeUseAt;
        iFolmeUseAt.touch().setTint(0.03f, 0.0f, 0.0f, 0.0f).setScale(1.0f, new ITouchStyle.TouchType[0]);
        this.mNoteContainer = (ViewGroup) view.findViewById(com.miui.notes.R.id.note_group);
        initNoteGroup();
    }

    public void bind(ItemCache itemCache, BindContext bindContext, int i, long j) {
        if (this.itemView.getVisibility() != 0) {
            this.itemView.setVisibility(0);
        }
        this.mItemCache = itemCache;
        this.mContentType = itemCache.getCacheType();
        this.mBindPosition = i;
        this.mBindContext = bindContext;
        if (itemCache.getCacheType() == 0) {
            this.itemView.setLongClickable(false);
            NoteCache noteCache = (NoteCache) itemCache.getCacheObject();
            this.content = noteCache.getNote().getSnippet();
            this.mTime.setText(noteCache.getFormattedSortedDate());
            this.mAlarm.setVisibility(noteCache.getNote().getRemindAt() > System.currentTimeMillis() ? 0 : 8);
            this.mNoteDataCache = noteCache;
            this.mNoteContainer.setVisibility(0);
        }
        if (itemCache.getCacheId() == j) {
            this.mNoteContainer.setBackgroundColor(Color.parseColor("#F1F1F1"));
        } else {
            this.mNoteContainer.setBackground(null);
        }
        if (bindContext.isInActionMode()) {
            onUpdateEditable(true, bindContext.isItemIdChecked(0L));
        } else {
            onUpdateEditable(false, false);
        }
    }

    protected void initNoteGroup() {
        this.mTime = (TextView) this.mNoteContainer.findViewById(com.miui.notes.R.id.time);
        this.mAlarm = (ImageView) this.mNoteContainer.findViewById(com.miui.notes.R.id.alarm);
        this.mStick = (ImageView) this.mNoteContainer.findViewById(com.miui.notes.R.id.stick);
        this.mNoteContent = (ViewGroup) this.mNoteContainer.findViewById(com.miui.notes.R.id.content);
    }

    public void setContent(PcNoteBaseContent pcNoteBaseContent) {
        this.mNoteBaseContent = pcNoteBaseContent;
        this.mNoteContent.addView(pcNoteBaseContent.getItemView(), 0);
    }

    public int getContentType() {
        return this.mContentType;
    }

    public ItemCache getItemCache() {
        return this.mItemCache;
    }

    public NoteCache getNoteCache() {
        return this.mNoteDataCache;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.itemView.setOnLongClickListener(onLongClickListener);
    }

    @Override // com.miui.pc.component.ViewHolderEditableCallback
    public boolean hasAnimationStarted() {
        return this.mStarted;
    }

    @Override // com.miui.pc.component.ViewHolderEditableCallback
    public void onAnimationStart(boolean z) {
        this.mStarted = true;
        if (z) {
            this.mCheck.setVisibility(0);
            this.mCheck.setAlpha(0.0f);
            this.mCheck.setScaleX(0.8f);
            this.mCheck.setScaleY(0.8f);
        }
    }

    @Override // com.miui.pc.component.ViewHolderEditableCallback
    public void onAnimationUpdate(boolean z, float f) {
        if (!z) {
            f = 1.0f - f;
        }
        this.mCheck.setAlpha(f);
        float f2 = (f * 0.2f) + 0.8f;
        this.mCheck.setScaleX(f2);
        this.mCheck.setScaleY(f2);
    }

    @Override // com.miui.pc.component.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
        this.mStarted = false;
        if (z) {
            return;
        }
        onClearAnimation();
        if (z) {
            return;
        }
        this.mCheck.setVisibility(8);
    }

    @Override // com.miui.pc.component.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        if (!isRecyclable()) {
            return false;
        }
        boolean z3 = this.mCheck.getVisibility() == 0;
        boolean zIsChecked = this.mCheck.isChecked();
        this.mCheck.setChecked(z2);
        if (z == z3 && (!z || z2 == zIsChecked)) {
            return false;
        }
        if (z) {
            this.mCheck.setAlpha(1.0f);
            this.mCheck.setScaleX(1.0f);
            this.mCheck.setScaleY(1.0f);
            this.mCheck.setVisibility(0);
        } else {
            this.mCheck.setVisibility(8);
        }
        return true;
    }

    @Override // com.miui.pc.component.ViewHolderEditableCallback
    public void onClearAnimation() {
        this.mCheck.setAlpha(1.0f);
        this.mCheck.setScaleX(1.0f);
        this.mCheck.setScaleY(1.0f);
    }

    @Override // com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        if (this.mStarted) {
            onAnimationStop(false);
        }
    }
}
