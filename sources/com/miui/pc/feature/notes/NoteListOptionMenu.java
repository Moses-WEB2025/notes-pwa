package com.miui.pc.feature.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.common.view.IPopMenu;
import com.miui.notes.R;
import com.miui.notes.cache.CacheManager;
import com.miui.notes.cache.NoteCache;
import com.miui.pc.component.EditableNoteListWrapper;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class NoteListOptionMenu implements IPopMenu {
    private TextView mDelete;
    private long mFolderId;
    private EditableNoteListWrapper mListWrapper;
    private TextView mMoveTo;
    private TextView mMultiChoice;
    private TextView mNewNote;
    private TextView mRestore;
    private View mRootView;
    private TextView mStick;
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.pc.feature.notes.NoteListOptionMenu.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.delete /* 2131362396 */:
                    if (NoteListOptionMenu.this.mOnMenuListener != null) {
                        NoteListOptionMenu.this.mOnMenuListener.onItemSelected(view, 5);
                    }
                    NoteListOptionMenu.this.dismiss();
                    break;
                case R.id.move_to_folder /* 2131362991 */:
                    if (NoteListOptionMenu.this.mOnMenuListener != null) {
                        NoteListOptionMenu.this.mOnMenuListener.onItemSelected(view, 3);
                    }
                    NoteListOptionMenu.this.dismiss();
                    break;
                case R.id.multi_choice /* 2131363016 */:
                    if (NoteListOptionMenu.this.mOnMenuListener != null) {
                        NoteListOptionMenu.this.mOnMenuListener.onItemSelected(view, 0);
                    }
                    NoteListOptionMenu.this.dismiss();
                    break;
                case R.id.new_note /* 2131363049 */:
                    if (NoteListOptionMenu.this.mOnMenuListener != null) {
                        NoteListOptionMenu.this.mOnMenuListener.onItemSelected(view, 1);
                    }
                    NoteListOptionMenu.this.dismiss();
                    break;
                case R.id.restore /* 2131363295 */:
                    if (NoteListOptionMenu.this.mOnMenuListener != null) {
                        NoteListOptionMenu.this.mOnMenuListener.onItemSelected(view, 4);
                    }
                    NoteListOptionMenu.this.dismiss();
                    break;
                case R.id.stick /* 2131363535 */:
                    if (NoteListOptionMenu.this.mOnMenuListener != null) {
                        NoteListOptionMenu.this.mOnMenuListener.onItemSelected(view, 2);
                    }
                    NoteListOptionMenu.this.dismiss();
                    break;
            }
        }
    };
    private IPopMenu.OnMenuListener mOnMenuListener = null;
    private TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.NoteListOptionMenu.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (NoteListOptionMenu.this.mOnMenuListener != null) {
                NoteListOptionMenu.this.mOnMenuListener.onShow();
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.NoteListOptionMenu.3
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (NoteListOptionMenu.this.mRootView != null && (viewGroup = (ViewGroup) NoteListOptionMenu.this.mRootView.getParent()) != null) {
                viewGroup.removeView(NoteListOptionMenu.this.mRootView);
            }
            if (NoteListOptionMenu.this.mOnMenuListener != null) {
                NoteListOptionMenu.this.mOnMenuListener.onDismiss();
            }
        }
    };

    public NoteListOptionMenu(Context context, EditableNoteListWrapper editableNoteListWrapper) {
        this.mRootView = null;
        this.mListWrapper = editableNoteListWrapper;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.pc_note_list_option_menu, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.NoteListOptionMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NoteListOptionMenu.this.dismiss();
            }
        });
        initView(this.mRootView);
    }

    private void initView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.multi_choice);
        this.mMultiChoice = textView;
        textView.setOnClickListener(this.mItemClickListener);
        TextView textView2 = (TextView) view.findViewById(R.id.new_note);
        this.mNewNote = textView2;
        textView2.setOnClickListener(this.mItemClickListener);
        TextView textView3 = (TextView) view.findViewById(R.id.stick);
        this.mStick = textView3;
        textView3.setOnClickListener(this.mItemClickListener);
        TextView textView4 = (TextView) view.findViewById(R.id.restore);
        this.mRestore = textView4;
        textView4.setOnClickListener(this.mItemClickListener);
        TextView textView5 = (TextView) view.findViewById(R.id.delete);
        this.mDelete = textView5;
        textView5.setOnClickListener(this.mItemClickListener);
        TextView textView6 = (TextView) view.findViewById(R.id.move_to_folder);
        this.mMoveTo = textView6;
        textView6.setOnClickListener(this.mItemClickListener);
    }

    public void updateFolderId(long j) {
        this.mFolderId = j;
        if (j == -3) {
            this.mStick.setVisibility(8);
            this.mRestore.setVisibility(0);
            this.mDelete.setText(R.string.menu_delete_forever);
            this.mNewNote.setVisibility(8);
            this.mMoveTo.setVisibility(8);
            return;
        }
        if (j == -2 || j == -5) {
            this.mStick.setVisibility(0);
            this.mRestore.setVisibility(8);
            this.mDelete.setText(R.string.menu_delete);
            this.mNewNote.setVisibility(8);
            this.mMoveTo.setVisibility(8);
            return;
        }
        if (checkedItemsInFolder(-2L) || checkedItemsInFolder(-5L)) {
            this.mStick.setVisibility(0);
            this.mRestore.setVisibility(8);
            this.mDelete.setText(R.string.menu_delete);
            this.mNewNote.setVisibility(0);
            this.mMoveTo.setVisibility(8);
            return;
        }
        this.mStick.setVisibility(0);
        this.mRestore.setVisibility(8);
        this.mDelete.setText(R.string.menu_delete);
        this.mNewNote.setVisibility(0);
        this.mMoveTo.setVisibility(0);
    }

    private boolean checkedItemsInFolder(long j) {
        for (long j2 : this.mListWrapper.getCheckedItemIds()) {
            NoteCache note = CacheManager.getDefault().getNote(j2);
            if (note == null) {
                return false;
            }
            if (note.getNote().getFolderId() == j) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(View view, final int[] iArr) {
        if (this.mListWrapper.isAllCheckedItemSticking()) {
            this.mStick.setText(R.string.menu_unstick);
        } else {
            this.mStick.setText(R.string.menu_stick);
        }
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        if (this.mRootView.getParent() != null) {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        }
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        viewGroup.post(new Runnable() { // from class: com.miui.pc.feature.notes.NoteListOptionMenu.5
            @Override // java.lang.Runnable
            public void run() {
                View viewFindViewById = NoteListOptionMenu.this.mRootView.findViewById(R.id.content);
                viewFindViewById.setX(iArr[0]);
                if (iArr[1] + viewFindViewById.getHeight() > NoteListOptionMenu.this.mRootView.getHeight()) {
                    viewFindViewById.setY(iArr[1] - viewFindViewById.getHeight());
                } else {
                    viewFindViewById.setY(iArr[1]);
                }
                Folme.useAt(NoteListOptionMenu.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(NoteListOptionMenu.this.mShowListener));
            }
        });
    }

    @Override // com.miui.common.view.IPopMenu
    public void dismiss() {
        if (this.mRootView.getAlpha() != 0.0f) {
            Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
            return;
        }
        try {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.miui.common.view.IPopMenu
    public void setOnMenuListener(IPopMenu.OnMenuListener onMenuListener) {
        this.mOnMenuListener = onMenuListener;
    }
}
