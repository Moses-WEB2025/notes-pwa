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
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.handwritecommon.api.HandViewProvider;
import com.miui.handwritecommon.api.callback.NoteDataLoadListener;
import com.miui.handwritecommon.api.singlepage.ISinglePageHandView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.notes.model.HandWriteDataUtils;
import com.miui.notes.model.HandWriteEntity;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.IFragmentController;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class PcHandWriteFragment extends BaseNoteDetailFragment {
    public static final String BLACK_PAPER_BACKGROUND = "black";
    public static final String DOT_PAPER_BACKGROUND = "dot";
    public static String TAG = "PcHandWriteFragment";
    public static final String WHITE_PAPER_BACKGROUND = "white";
    private View loadingView;
    private final BaseDragListener mBlockDragListener = new BaseDragListener() { // from class: com.miui.pc.feature.notes.PcHandWriteFragment.1
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
    private ISinglePageHandView mHandWriteView;
    private long mNoteId;
    private FrameLayout mRootView;
    private String mSearchContent;
    private String queryText;

    private void setHighLight(String str, String str2, String str3) {
    }

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
        View viewInflate = layoutInflater.inflate(R.layout.pc_hand_write_fragment, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    private void initView(View view) {
        this.mRootView = (FrameLayout) view;
        this.loadingView = view.findViewById(R.id.loading_container);
        view.setOnDragListener(this.mBlockDragListener);
        ISinglePageHandView iSinglePageHandViewAddSinglePageView = HandViewProvider.addSinglePageView(this.mRootView, 2, false, false);
        this.mHandWriteView = iSinglePageHandViewAddSinglePageView;
        iSinglePageHandViewAddSinglePageView.setCanWrite(false);
        this.mHandWriteView.setPreviewOnClick(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcHandWriteFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Utils.hideSoftInput(view2);
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
            this.mNoteId = intent.getLongExtra("android.intent.extra.UID", 0L);
            HandWriteDataUtils.HandWriteNoteInfo handWriteNoteInfoQuery = HandWriteDataUtils.query(getActivity(), "_id=" + this.mNoteId);
            if (handWriteNoteInfoQuery != null) {
                this.mFolderId = handWriteNoteInfoQuery.getParentId();
                updateUI(HandWriteEntity.toHandWriteEntity(handWriteNoteInfoQuery.getHandWriteContent()), HandWriteDataUtils.getSortDate(handWriteNoteInfoQuery.getCreatedDate(), handWriteNoteInfoQuery.getModifiedDate()));
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
        HandWriteDataUtils.HandWriteNoteInfo handWriteNoteInfoQuery = HandWriteDataUtils.query(NoteApp.getInstance(), "_id=" + this.mNoteId);
        if (handWriteNoteInfoQuery != null) {
            this.mFolderId = handWriteNoteInfoQuery.getParentId();
            updateUI(HandWriteEntity.toHandWriteEntity(handWriteNoteInfoQuery.getHandWriteContent()), HandWriteDataUtils.getSortDate(handWriteNoteInfoQuery.getCreatedDate(), handWriteNoteInfoQuery.getModifiedDate()));
        }
    }

    private void updateUI(HandWriteEntity handWriteEntity, long j) {
        View view;
        ISinglePageHandView iSinglePageHandView = this.mHandWriteView;
        if (iSinglePageHandView != null) {
            iSinglePageHandView.setCanWrite(false);
        }
        if (handWriteEntity != null) {
            String attachmentPath = !TextUtils.isEmpty(handWriteEntity.entName) ? AttachmentUtils.getAttachmentPath(getContext(), handWriteEntity.entName) : null;
            String str = handWriteEntity.title;
            ISinglePageHandView iSinglePageHandView2 = this.mHandWriteView;
            if (iSinglePageHandView2 != null) {
                iSinglePageHandView2.setCanWrite(false);
                this.mHandWriteView.setTitleTime((String) DateFormatUtils.formatDateText(getContext(), j), (String) DateFormatUtils.formatTimeText(getContext(), j));
                this.mHandWriteView.setTitle(str);
                this.mHandWriteView.load(attachmentPath, new NoteDataLoadListener() { // from class: com.miui.pc.feature.notes.PcHandWriteFragment.3
                    @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                    public void onLoadComplete(boolean z) {
                    }

                    @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                    public void onStartLoad() {
                    }
                });
                setCanvasBg(handWriteEntity.canvasBgType);
            }
            if (!TextUtils.equals(this.queryText, this.mSearchText)) {
                setHighLight(handWriteEntity.textContent, handWriteEntity.recognizedText, handWriteEntity.getRecognizedFile());
            }
            View view2 = this.loadingView;
            if (view2 != null) {
                view2.setVisibility(8);
            }
            if (TextUtils.isEmpty(attachmentPath)) {
                return;
            }
            try {
                if (new File(attachmentPath).exists() || (view = this.loadingView) == null) {
                    return;
                }
                view.setVisibility(8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setCanvasBg(String str) {
        int singlePageWhiteBg = HandViewProvider.getSinglePageWhiteBg();
        if (TextUtils.isEmpty(str)) {
            if (UIUtils.isNightMode(getActivity())) {
                singlePageWhiteBg = HandViewProvider.getSinglePageBlackBg();
            }
        } else {
            str.hashCode();
            if (str.equals("dot")) {
                singlePageWhiteBg = HandViewProvider.getSinglePageDotBg();
            } else if (str.equals("black")) {
                singlePageWhiteBg = HandViewProvider.getSinglePageBlackBg();
            }
        }
        this.mHandWriteView.setBackgroundIndex(singlePageWhiteBg);
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
        ISinglePageHandView iSinglePageHandView = this.mHandWriteView;
        if (iSinglePageHandView != null) {
            iSinglePageHandView.release();
        }
    }
}
