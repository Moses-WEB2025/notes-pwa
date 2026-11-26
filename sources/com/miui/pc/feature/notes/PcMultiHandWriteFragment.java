package com.miui.pc.feature.notes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.tool.Utils;
import com.miui.handwritecommon.api.HandViewProvider;
import com.miui.handwritecommon.api.callback.NoteDataLoadListener;
import com.miui.handwritecommon.api.multipage.IMultiPageHandView;
import com.miui.handwritecommon.api.multipage.IMultiPagePreviewClickListener;
import com.miui.notes.R;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.IFragmentController;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWriteEntity;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class PcMultiHandWriteFragment extends BaseNoteDetailFragment {
    public static String TAG = "PcMultiHandWriteFragment";
    private View loadingView;
    private final BaseDragListener mBlockDragListener = new BaseDragListener() { // from class: com.miui.pc.feature.notes.PcMultiHandWriteFragment.1
        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragDrop(View view, DragEvent dragEvent) {
            return true;
        }

        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragEntered(View view, DragEvent dragEvent) {
            return true;
        }

        @Override // com.miui.notes.ui.BaseDragListener
        protected void handleDragLocation(View view, DragEvent dragEvent) {
        }

        @Override // com.miui.notes.ui.BaseDragListener
        protected boolean handleDragStarted(View view, DragEvent dragEvent) {
            return true;
        }
    };
    public IFragmentController mController;
    private IMultiPageHandView mMultiPageHandView;
    private long mNoteId;
    private FrameLayout mRootView;
    private String mSearchContent;
    private String queryText;

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    /* JADX INFO: renamed from: isSupportNavigator */
    protected boolean getIsSupportNavigator() {
        return false;
    }

    public void setController(IFragmentController iFragmentController) {
        this.mController = iFragmentController;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setThemeRes(R.style.NoteTheme_Light_PcEdit);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.pc_multi_hand_write_fragment, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    private void initView(final View view) {
        this.mRootView = (FrameLayout) view;
        this.loadingView = view.findViewById(R.id.loading_container);
        view.setOnDragListener(this.mBlockDragListener);
        IMultiPageHandView iMultiPageHandViewAddMultiPageView = HandViewProvider.addMultiPageView(this.mRootView);
        this.mMultiPageHandView = iMultiPageHandViewAddMultiPageView;
        iMultiPageHandViewAddMultiPageView.setCanWrite(false);
        this.mMultiPageHandView.setPreviewOnClick(new IMultiPagePreviewClickListener() { // from class: com.miui.pc.feature.notes.PcMultiHandWriteFragment.2
            @Override // com.miui.handwritecommon.api.multipage.IMultiPagePreviewClickListener
            public void onClick() {
                Utils.hideSoftInput(view);
            }
        });
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        FragmentActivity activity = getActivity();
        if (activity != null && activity.getResources() != null && getActionBar() != null) {
            getActionBar().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.v8_placeholder));
        }
        setImmersionMenuEnabled(true);
        Intent intent = getActivity() != null ? getActivity().getIntent() : null;
        if (intent != null) {
            this.mSearchContent = getExtraUserQuery(intent);
            long longExtra = intent.getLongExtra("android.intent.extra.UID", 0L);
            this.mNoteId = longExtra;
            MultiHandWriteEntity multiHandWriteEntityLoadNote = MultiHandWriteDataUtils.loadNote(longExtra);
            if (multiHandWriteEntityLoadNote != null) {
                this.mFolderId = multiHandWriteEntityLoadNote.folderId;
                updateUI(multiHandWriteEntityLoadNote);
            }
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        try {
            setArguments(noteLoadDataEntity.toBundle());
            loadNoteFromArguments();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadNoteFromArguments() {
        if (getArguments() == null) {
            return;
        }
        long j = getArguments().getLong("data_id", -1L);
        String string = getArguments().getString(NoteLoadDataEntity.SEARCH_TEXT, "");
        this.queryText = string;
        this.mNoteId = j;
        this.mSearchContent = string;
        MultiHandWriteEntity multiHandWriteEntityLoadNote = MultiHandWriteDataUtils.loadNote(j);
        if (multiHandWriteEntityLoadNote != null) {
            this.mFolderId = multiHandWriteEntityLoadNote.folderId;
            updateUI(multiHandWriteEntityLoadNote);
        }
    }

    private void updateUI(MultiHandWriteEntity multiHandWriteEntity) {
        String strObserveEntDir;
        View view;
        IMultiPageHandView iMultiPageHandView = this.mMultiPageHandView;
        if (iMultiPageHandView != null) {
            iMultiPageHandView.setCanWrite(false);
        }
        if (multiHandWriteEntity != null) {
            if (TextUtils.isEmpty(multiHandWriteEntity.entName)) {
                strObserveEntDir = "";
            } else {
                strObserveEntDir = multiHandWriteEntity.observeEntDir();
            }
            String str = multiHandWriteEntity.title;
            IMultiPageHandView iMultiPageHandView2 = this.mMultiPageHandView;
            if (iMultiPageHandView2 != null) {
                iMultiPageHandView2.setCanWrite(false);
                this.mMultiPageHandView.setTitleTime(multiHandWriteEntity.getSortDate());
                this.mMultiPageHandView.setTitle(str);
                this.mMultiPageHandView.load(strObserveEntDir, new NoteDataLoadListener() { // from class: com.miui.pc.feature.notes.PcMultiHandWriteFragment.3
                    @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                    public void onLoadComplete(boolean z) {
                    }

                    @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                    public void onStartLoad() {
                    }
                });
            }
            View view2 = this.loadingView;
            if (view2 != null) {
                view2.setVisibility(8);
            }
            if (TextUtils.isEmpty(strObserveEntDir)) {
                return;
            }
            try {
                if (new File(strObserveEntDir).exists() || (view = this.loadingView) == null) {
                    return;
                }
                view.setVisibility(8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getExtraUserQuery(Intent intent) {
        if (intent.hasExtra("intent_extra_data_key")) {
            return intent.getStringExtra("intent_extra_data_key");
        }
        return null;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        IMultiPageHandView iMultiPageHandView = this.mMultiPageHandView;
        if (iMultiPageHandView != null) {
            iMultiPageHandView.release();
        }
    }
}
