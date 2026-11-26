package com.miui.pc.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class CreateMorePopupWindow extends PopupWindow implements View.OnClickListener {
    private View mContentView;
    private Context mContext;
    private CreateMenuView mFolderView;
    private View.OnClickListener mListener;
    private LinearLayout mMenuLayout;
    private CreateMenuView mMindView;
    private CreateMenuView mNoteView;
    private View mSharpView;

    public CreateMorePopupWindow(Context context, View.OnClickListener onClickListener) {
        this.mContext = context;
        this.mListener = onClickListener;
        View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.create_more_popup_win, (ViewGroup) null);
        this.mContentView = viewInflate;
        setContentView(viewInflate);
        setWidth(this.mContext.getResources().getDimensionPixelSize(R.dimen.pc_create_more_menu));
        setHeight(-2);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        this.mMenuLayout = (LinearLayout) this.mContentView.findViewById(R.id.pop_menu_layout);
        initMenu();
        this.mSharpView = this.mContentView.findViewById(R.id.sharp_view);
    }

    private void initMenu() {
        this.mNoteView = (CreateMenuView) this.mMenuLayout.findViewById(R.id.create_note_view);
        this.mMindView = (CreateMenuView) this.mMenuLayout.findViewById(R.id.create_mind_view);
        this.mFolderView = (CreateMenuView) this.mMenuLayout.findViewById(R.id.create_folder_view);
        this.mNoteView.setData(this.mContext.getString(R.string.pc_action_create_new_normal_note), R.drawable.pc_icon_text_note, R.drawable.pc_icon_text_note_highlight);
        this.mMindView.setData(this.mContext.getString(R.string.pc_action_create_new_mind_note), R.drawable.pc_icon_mind_note, R.drawable.pc_icon_mind_note_highlight);
        this.mFolderView.setData(this.mContext.getString(R.string.pc_action_create_new_folder), R.drawable.pc_icon_folder, R.drawable.pc_icon_folder_highlight);
        this.mNoteView.setOnClickListener(this);
        this.mMindView.setOnClickListener(this);
        this.mFolderView.setOnClickListener(this);
    }

    public void showPopupWindow(View view, int i, int i2) {
        if (!isShowing()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mSharpView.getLayoutParams();
            int i3 = -i;
            marginLayoutParams.setMarginStart(i3);
            this.mSharpView.setLayoutParams(marginLayoutParams);
            if (view.getLayoutDirection() == 1) {
                showAsDropDown(view, i3, i2);
                return;
            } else {
                showAsDropDown(view, i, i2);
                return;
            }
        }
        dismiss();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.mListener.onClick(view);
        dismiss();
    }
}
