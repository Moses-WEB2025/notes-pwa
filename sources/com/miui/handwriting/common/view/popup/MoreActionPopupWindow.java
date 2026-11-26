package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.data.dataset.HistorySet;
import com.miui.handwriting.common.tools.DeviceUtils;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import miuix.core.util.MiuixUIUtils;
import miuix.slidingwidget.widget.SlidingButton;
import xc.engine.utils.DisplayUtils;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes3.dex */
public class MoreActionPopupWindow extends BaseAppPopupWindow {
    private static final String TAG = "MoreActionPopupWindow";
    private static int[] colors;
    private ActionCallBack actionCallBack;
    private View clearContentLayout;
    private int colorIndex;
    private Context context;
    public View creationPageSelectViewNormal;
    private float fontScale;
    private boolean isShowDeletePage;
    private View mDividerLine;
    public ImageView mImgPageSize;
    private LinearLayout mLLBgColor;
    private LinearLayout mLLColors;
    private LinearLayout mLLPapers;
    private OnTitleColorChangeListener mOnTitleColorChangeListener;
    public View mPageSelect;
    public View mPageSelectNormal;
    private View mPageSizeChange;
    private ScrollView mScrollView;
    private SlidingButton mSlidButtonDoubleFingerUndo;
    private SlidingButton mSlidButtonFinger;
    private TextView mTvClearContent;
    private TextView mTvDeletePage;
    private TextView mTvInsertPic;
    private TextView mTvInsertShape;
    private TextView mTvInsertText;
    private TextView mTvPageSize;
    private TextView mTvPageSizeSmall;
    private TextView mTvPencil;
    private TextView mTvShare;
    private TextView mTvSwitchFinger;
    private RelativeLayout mTvSwitchFingerItem;
    private int paperIndex;
    private PenWriteCheckedChangeListener penWriteCheckedChangeListener;
    private View rootView;
    private boolean showShare;
    private static int[] colorBgRes = {R.drawable.bg_paper_white, R.drawable.bg_paper_yellow, R.drawable.bg_paper_black};
    public static int ACTION_PICK_IMAGE = 1;
    public static int ACTION_INSERT_TEXT = 2;
    public static int ACTION_SHOW_KEY_SHORT = 3;
    public static int ACTION_DELETE_PAGE = 4;
    public static int ACTION_SHARE = 5;
    public static int ACTION_INSERT_SHAPE = 6;
    public static int ACTION_CLEAR_CONTENT = 7;
    public static int ACTION_PAGE_SIZE = 8;
    private static boolean DEBUG_PAPER = false;
    public static int BLACK_COLOR_INDEX = 2;

    public interface ActionCallBack {
        void call(int i);

        void callBackground(int i, int i2);
    }

    public interface OnTitleColorChangeListener {
        void onTitleColorChanged(int i);
    }

    public interface PenWriteCheckedChangeListener {
        void call(boolean z);
    }

    static /* synthetic */ void lambda$initView$8(View view) {
    }

    public View showAnchorView() {
        if (this.fontScale > 1.0f) {
            return this.mImgPageSize;
        }
        return this.creationPageSelectViewNormal;
    }

    public void setShowShare(boolean z) {
        this.showShare = z;
        View view = this.rootView;
        if (view != null) {
            view.findViewById(R.id.creation_more_action_share).setVisibility(z ? 0 : 8);
        }
    }

    public void updateClearContentTitle(boolean z) {
        TextView textView = this.mTvClearContent;
        if (textView != null) {
            if (z) {
                textView.setText(R.string.creation_more_tool_clear_content_unbounded);
            } else {
                textView.setText(R.string.creation_more_tool_clear_content);
            }
        }
    }

    public void setShowDeletePage(boolean z) {
        this.isShowDeletePage = z;
        TextView textView = this.mTvDeletePage;
        if (textView != null) {
            textView.setVisibility(z ? 0 : 8);
        }
    }

    public void setActionCallBack(ActionCallBack actionCallBack) {
        this.actionCallBack = actionCallBack;
    }

    public void setTitleColorChangeListener(OnTitleColorChangeListener onTitleColorChangeListener) {
        this.mOnTitleColorChangeListener = onTitleColorChangeListener;
    }

    public MoreActionPopupWindow(Context context) {
        super(context);
        this.isShowDeletePage = false;
        this.colorIndex = 0;
        this.paperIndex = 0;
        this.showShare = false;
        this.fontScale = 1.0f;
        this.context = context;
        colors = new int[]{ContextCompat.getColor(context, R.color.paper_white), ContextCompat.getColor(context, R.color.paper_yellow), ContextCompat.getColor(context, R.color.paper_black)};
        initView();
    }

    private void selectColor() {
        int childCount = this.mLLColors.getChildCount();
        for (int i = 0; i < childCount; i++) {
            FrameLayout frameLayout = (FrameLayout) this.mLLColors.getChildAt(i);
            frameLayout.setBackground(null);
            ImageView imageView = (ImageView) frameLayout.getChildAt(0);
            if (this.colorIndex == i) {
                imageView.setBackground(null);
                OnTitleColorChangeListener onTitleColorChangeListener = this.mOnTitleColorChangeListener;
                if (onTitleColorChangeListener != null) {
                    onTitleColorChangeListener.onTitleColorChanged(this.colorIndex);
                }
                frameLayout.setForeground(getDrawable(R.drawable.bg_drawing_check_color_checked));
            } else {
                imageView.setBackground(getDrawable(R.drawable.bg_drawing_check_color_bg));
                frameLayout.setForeground(null);
            }
        }
        setPaperColor();
    }

    private void setPaperColor() {
        int childCount = this.mLLPapers.getChildCount();
        int i = this.paperIndex;
        int i2 = this.colorIndex;
        if (childCount != 4) {
            return;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            FrameLayout frameLayout = (FrameLayout) this.mLLPapers.getChildAt(i3);
            ImageView imageView = (ImageView) frameLayout.getChildAt(0);
            if (i == i3) {
                frameLayout.setBackground(ContextCompat.getDrawable(getContext(), colorBgRes[i2]));
                imageView.setBackground(ContextCompat.getDrawable(getContext(), colorBgRes[i2]));
                imageView.setForeground(getDrawable(R.drawable.bg_paper_unselect_bg));
                frameLayout.setForeground(getDrawable(R.drawable.bg_paper_checked));
            } else {
                frameLayout.setBackground(null);
                imageView.setBackground(ContextCompat.getDrawable(getContext(), colorBgRes[i2]));
                frameLayout.setForeground(null);
                imageView.setForeground(null);
                imageView.setForeground(getDrawable(R.drawable.bg_paper_unselect_bg));
            }
        }
    }

    public void setPenWrite(boolean z) {
        this.mSlidButtonFinger.setChecked(!z);
    }

    private void selectPaper(int i) {
        this.paperIndex = i;
        setPaperColor();
    }

    public void setBackgroundIndex(int i, int i2) {
        int i3 = 0;
        this.colorIndex = 0;
        while (true) {
            int[] iArr = colors;
            if (i3 >= iArr.length) {
                break;
            }
            if (i == iArr[i3]) {
                this.colorIndex = i3;
                break;
            }
            i3++;
        }
        selectColor();
        selectPaper(i2);
    }

    private Drawable getDrawable(int i) {
        return ContextCompat.getDrawable(getContext(), i);
    }

    private void initView() {
        this.rootView = LayoutInflater.from(this.context).inflate(R.layout.note_more_action_view, (ViewGroup) null, false);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(this.rootView);
        this.mScrollView = (ScrollView) this.rootView.findViewById(R.id.creation_item_group);
        this.mLLColors = (LinearLayout) this.rootView.findViewById(R.id.ll_colors);
        this.mLLPapers = (LinearLayout) this.rootView.findViewById(R.id.ll_papers);
        this.mLLBgColor = (LinearLayout) this.rootView.findViewById(R.id.ll_bg_color);
        this.mDividerLine = this.rootView.findViewById(R.id.creation_rectangle);
        this.mTvShare = (TextView) this.rootView.findViewById(R.id.creation_more_action_share);
        this.mTvDeletePage = (TextView) this.rootView.findViewById(R.id.creation_more_action_delete_page);
        this.mTvInsertText = (TextView) this.rootView.findViewById(R.id.creation_more_action_add_text);
        this.mTvInsertPic = (TextView) this.rootView.findViewById(R.id.creation_more_action_insert_pic);
        this.mTvInsertShape = (TextView) this.rootView.findViewById(R.id.creation_more_action_insert_shape);
        this.mTvClearContent = (TextView) this.rootView.findViewById(R.id.creation_more_action_clear_content);
        this.clearContentLayout = this.rootView.findViewById(R.id.rl_clear_content);
        this.mTvSwitchFingerItem = (RelativeLayout) this.rootView.findViewById(R.id.creation_more_action_switch_finger_item);
        this.mTvSwitchFinger = (TextView) this.rootView.findViewById(R.id.creation_more_action_switch_finger);
        this.mSlidButtonFinger = (SlidingButton) this.rootView.findViewById(R.id.creation_more_action_switch_finger_btn);
        SlidingButton slidingButton = (SlidingButton) this.rootView.findViewById(R.id.creation_more_action_switch_undo_btn);
        this.mSlidButtonDoubleFingerUndo = slidingButton;
        slidingButton.setChecked(HistorySet.isIsDoubleClickUndo());
        this.mTvPencil = (TextView) this.rootView.findViewById(R.id.creation_more_action_mi_pencil_tips);
        if (RomUtils.isP81() || RomUtils.isP82()) {
            this.mTvPencil.setVisibility(8);
        }
        this.mPageSizeChange = this.rootView.findViewById(R.id.page_size_parent);
        this.mPageSelect = this.rootView.findViewById(R.id.creation_page_select_view);
        TextView textView = (TextView) this.rootView.findViewById(R.id.creation_page_size);
        this.mTvPageSize = textView;
        textView.setBackground(null);
        this.mPageSelectNormal = this.rootView.findViewById(R.id.page_size_parent_normal);
        this.creationPageSelectViewNormal = this.rootView.findViewById(R.id.creation_page_select_view_normal);
        this.mTvPageSizeSmall = (TextView) this.rootView.findViewById(R.id.creation_page_size_normal);
        this.mImgPageSize = (ImageView) this.rootView.findViewById(R.id.img_page_size);
        this.mTvPageSizeSmall.setBackground(null);
        this.fontScale = MiuixUIUtils.getFontLevel(HandWritingViewApplication.INSTANCE.getApplication());
        int childCount = this.mLLColors.getChildCount();
        for (final int i = 0; i < childCount; i++) {
            this.mLLColors.getChildAt(i).setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m764xff2903ff(i, view);
                }
            });
        }
        int childCount2 = this.mLLPapers.getChildCount();
        for (final int i2 = 0; i2 < childCount2; i2++) {
            this.mLLPapers.getChildAt(i2).setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m765x9367739e(i2, view);
                }
            });
        }
        this.mTvShare.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m769x27a5e33d(view);
            }
        });
        this.mTvInsertText.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m770xbbe452dc(view);
            }
        });
        this.mTvDeletePage.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m771x5022c27b(view);
            }
        });
        this.mTvInsertPic.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m772xe461321a(view);
            }
        });
        this.mTvInsertShape.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m773x789fa1b9(view);
            }
        });
        this.clearContentLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m774xcde1158(view);
            }
        });
        this.mSlidButtonFinger.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MoreActionPopupWindow.lambda$initView$8(view);
            }
        });
        this.mSlidButtonFinger.setOnPerformCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m775x355af096(compoundButton, z);
            }
        });
        this.mTvPencil.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m766xcc5f390c(view);
            }
        });
        setBackgroundIndex(this.colorIndex, this.paperIndex);
        this.mPageSizeChange.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m767x609da8ab(view);
            }
        });
        this.creationPageSelectViewNormal.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.MoreActionPopupWindow$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m768xf4dc184a(view);
            }
        });
        if (RomUtils.isSupportStylus()) {
            return;
        }
        this.mTvSwitchFingerItem.setVisibility(8);
        this.mTvPencil.setVisibility(8);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m764xff2903ff(int i, View view) {
        this.colorIndex = i;
        selectColor();
        setPaperColor();
        callBackBackground();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m765x9367739e(int i, View view) {
        if (DEBUG_PAPER) {
            Toast.makeText(this.context, "待sdk支持", 0).show();
        } else {
            selectPaper(i);
            callBackBackground();
        }
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m769x27a5e33d(View view) {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.call(ACTION_SHARE);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m770xbbe452dc(View view) {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.call(ACTION_INSERT_TEXT);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m771x5022c27b(View view) {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.call(ACTION_DELETE_PAGE);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m772xe461321a(View view) {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.call(ACTION_PICK_IMAGE);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m773x789fa1b9(View view) {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.call(ACTION_INSERT_SHAPE);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m774xcde1158(View view) {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.call(ACTION_CLEAR_CONTENT);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$9$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m775x355af096(CompoundButton compoundButton, boolean z) {
        PenWriteCheckedChangeListener penWriteCheckedChangeListener = this.penWriteCheckedChangeListener;
        if (penWriteCheckedChangeListener != null) {
            penWriteCheckedChangeListener.call(z);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$10$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m766xcc5f390c(View view) {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.call(ACTION_SHOW_KEY_SHORT);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$11$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m767x609da8ab(View view) {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.call(ACTION_PAGE_SIZE);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$12$com-miui-handwriting-common-view-popup-MoreActionPopupWindow, reason: not valid java name */
    /* synthetic */ void m768xf4dc184a(View view) {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.call(ACTION_PAGE_SIZE);
        }
    }

    public int initPopupWindow() {
        int multiHeight = DisplayUtils.getMultiHeight(Utils.getActivity(this.context));
        float dimension = this.context.getResources().getDimension(R.dimen.creation_tool_bar_topmargin);
        int dimension2 = (int) this.context.getResources().getDimension(R.dimen.creation_tools_head_height);
        int dimension3 = (int) ((dimension2 - getContext().getResources().getDimension(R.dimen.creation_tool_head_icon_size)) - dimension);
        int statusBarHeight = UIUtils.getStatusBarHeight(this.context);
        int navigationBarHeightFromProp = UIUtils.getNavigationBarHeightFromProp(this.context);
        int i = (((multiHeight - dimension2) - dimension3) - statusBarHeight) - navigationBarHeightFromProp;
        ViewGroup.LayoutParams layoutParams = this.rootView.getLayoutParams();
        this.rootView.measure(0, 0);
        int measuredHeight = this.rootView.getMeasuredHeight();
        Log.d(TAG, "contentH: " + i + ", offsetY: " + dimension3 + ", statusBarH: " + statusBarHeight + ", navBarH: " + navigationBarHeightFromProp + ", popupWinH: " + measuredHeight);
        if (measuredHeight != this.rootView.getHeight()) {
            layoutParams.height = measuredHeight;
            this.rootView.setLayoutParams(layoutParams);
        }
        if (i < measuredHeight) {
            layoutParams.height = Math.min(i, (int) this.context.getResources().getDimension(R.dimen.creation_more_action_popup_window_origin_height));
            this.rootView.setLayoutParams(layoutParams);
        }
        return dimension3;
    }

    private void callBackBackground() {
        ActionCallBack actionCallBack = this.actionCallBack;
        if (actionCallBack != null) {
            actionCallBack.callBackground(this.colorIndex, this.paperIndex);
        }
    }

    public static AlertDialog getHotKeyPopDialog(Context context, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.creation_hot_key_dialog_style);
        if (DeviceUtils.getXiaomiStylusType(context) == 3) {
            ((ImageView) view.findViewById(R.id.creation_img_primary_key)).setImageResource(R.drawable.creation_primary_key);
            ((ImageView) view.findViewById(R.id.creation_img_sub_key)).setImageResource(R.drawable.creation_sub_key);
        } else {
            ((ImageView) view.findViewById(R.id.creation_img_primary_key)).setImageResource(R.drawable.creation_primary_key_2);
            ((ImageView) view.findViewById(R.id.creation_img_sub_key)).setImageResource(R.drawable.creation_sub_key_2);
        }
        if (context.getResources().getConfiguration().fontScale > DisplayUtils.DEFAULT_FONT_SIZE) {
            TextView textView = (TextView) view.findViewById(R.id.creation_shortcut_primary_key);
            TextView textView2 = (TextView) view.findViewById(R.id.creation_shortcut_primary_key_longpress);
            TextView textView3 = (TextView) view.findViewById(R.id.creation_shortcut_sub_key);
            textView.setMaxEms(4);
            textView2.setMaxEms(3);
            textView3.setMaxEms(5);
        }
        builder.setView(view);
        return builder.create();
    }

    public void setActionPageSizeVisible(boolean z) {
        this.mPageSizeChange.setVisibility(z ? 0 : 8);
        if (this.fontScale <= 1.0f) {
            this.mPageSelectNormal.setVisibility(z ? 0 : 8);
            this.mPageSizeChange.setVisibility(8);
        } else {
            this.mPageSelectNormal.setVisibility(8);
            this.mPageSizeChange.setVisibility(z ? 0 : 8);
        }
    }

    public void setPageSizeText(PageSizeConfig pageSizeConfig) {
        Log.d(TAG, "setPageSizeText " + pageSizeConfig);
        if (pageSizeConfig.isUnLimit) {
            this.mTvPageSize.setText(R.string.creation_page_size_unbounded);
            this.mTvPageSizeSmall.setText(R.string.creation_page_size_unbounded);
            return;
        }
        if (pageSizeConfig.type != 0) {
            this.mTvPageSize.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_custom));
            this.mTvPageSizeSmall.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_custom));
            return;
        }
        int i = pageSizeConfig.width;
        int i2 = pageSizeConfig.orientation;
        if (pageSizeConfig.pageType != 0) {
            int i3 = pageSizeConfig.pageType;
            if (i3 == 1) {
                this.mTvPageSize.setText(R.string.creation_page_size_default_h);
                this.mTvPageSizeSmall.setText(R.string.creation_page_size_default_h);
                return;
            }
            if (i3 == 2) {
                this.mTvPageSize.setText(R.string.creation_page_size_default_v);
                this.mTvPageSizeSmall.setText(R.string.creation_page_size_default_v);
                return;
            }
            if (i3 == 3) {
                this.mTvPageSize.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_h), 16, 9));
                this.mTvPageSizeSmall.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_h), 16, 9));
                return;
            }
            if (i3 == 4) {
                this.mTvPageSize.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_v), 16, 9));
                this.mTvPageSizeSmall.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_v), 16, 9));
                return;
            } else if (i3 == 5) {
                this.mTvPageSize.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_1_1), 1, 1));
                this.mTvPageSizeSmall.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_1_1), 1, 1));
                return;
            } else {
                if (i3 != 7) {
                    return;
                }
                this.mTvPageSize.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_custom));
                this.mTvPageSizeSmall.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_custom));
                return;
            }
        }
        if (i2 == 0) {
            if (i == 755) {
                this.mTvPageSize.setText(R.string.creation_page_size_default_v);
                this.mTvPageSizeSmall.setText(R.string.creation_page_size_default_v);
                return;
            } else if (i == 650) {
                this.mTvPageSize.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_v), 16, 9));
                this.mTvPageSizeSmall.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_v), 16, 9));
                return;
            } else if (i == 1156) {
                this.mTvPageSize.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_1_1), 1, 1));
                this.mTvPageSizeSmall.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_1_1), 1, 1));
                return;
            } else {
                this.mTvPageSize.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_default_v));
                this.mTvPageSizeSmall.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_default_v));
                return;
            }
        }
        if (i == 755) {
            this.mTvPageSize.setText(R.string.creation_page_size_default_h);
            this.mTvPageSizeSmall.setText(R.string.creation_page_size_default_h);
        } else if (i == 650) {
            this.mTvPageSize.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_h), 16, 9));
            this.mTvPageSizeSmall.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_h), 16, 9));
        } else if (i == 1156) {
            this.mTvPageSize.setText(R.string.creation_page_size_default_v);
            this.mTvPageSizeSmall.setText(R.string.creation_page_size_default_v);
        } else {
            this.mTvPageSize.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_default_v));
            this.mTvPageSizeSmall.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_default_v));
        }
    }

    public CharSequence getPageSizeText() {
        if (this.fontScale > 1.0f) {
            return this.mTvPageSize.getText();
        }
        return this.mTvPageSizeSmall.getText();
    }

    public void setPenWriteCheckedChangeListener(PenWriteCheckedChangeListener penWriteCheckedChangeListener) {
        this.penWriteCheckedChangeListener = penWriteCheckedChangeListener;
    }

    public void setDoubleFingerUndoCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mSlidButtonDoubleFingerUndo.setOnPerformCheckedChangeListener(onCheckedChangeListener);
    }

    public void setBgColorChooseVisible(boolean z) {
        this.mLLBgColor.setVisibility(z ? 0 : 8);
        this.mLLPapers.setVisibility(z ? 0 : 8);
        this.mDividerLine.setVisibility(z ? 0 : 8);
        this.mScrollView.setPadding(0, z ? 0 : this.context.getResources().getDimensionPixelSize(R.dimen.creation_more_popwindow_scroll_view_top_padding), 0, 0);
    }
}
