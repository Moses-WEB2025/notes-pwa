package com.miui.pad.feature.notes.mindnote;

import android.animation.Animator;
import android.view.View;
import android.widget.ImageView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.BlurHelper;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.ui.BaseEditorActionBarController;
import com.miui.richeditor.theme.Theme;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.android.content.MiuiIntent;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;

/* JADX INFO: compiled from: PadMindNoteToolBarController.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010&\u001a\u00020'J\u0006\u0010(\u001a\u00020'J\u000e\u0010)\u001a\u00020'2\u0006\u0010*\u001a\u00020+J\u000e\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020%J\u0010\u0010.\u001a\u00020'2\u0006\u0010/\u001a\u00020\u0003H\u0016J\u001e\u00100\u001a\u00020'2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002022\u0006\u00104\u001a\u000202J\u0010\u00105\u001a\u00020'2\u0006\u00106\u001a\u000202H\u0002J\u0010\u00107\u001a\u00020'2\u0006\u00106\u001a\u000202H\u0002J\u0010\u00108\u001a\u00020'2\u0006\u00106\u001a\u000202H\u0002J\u0016\u00109\u001a\u00020'2\u0006\u0010:\u001a\u0002022\u0006\u0010;\u001a\u000202J\u000e\u0010<\u001a\u00020'2\u0006\u00106\u001a\u000202J\u000e\u0010=\u001a\u00020'2\u0006\u00106\u001a\u000202R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u0003X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0010X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u001a\u0010\u0018\u001a\u00020\u0010X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u0014R\u001a\u0010\u001b\u001a\u00020\u0003X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\f\"\u0004\b\u001d\u0010\u000eR\u001a\u0010\u001e\u001a\u00020\u0010X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0012\"\u0004\b \u0010\u0014R\u001a\u0010!\u001a\u00020\u0010X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0012\"\u0004\b#\u0010\u0014R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lcom/miui/pad/feature/notes/mindnote/PadMindNoteToolBarController;", "Landroid/view/View$OnClickListener;", "mToolRootView", "Landroid/view/View;", "mActionCallBack", "Lcom/miui/notes/ui/BaseEditorActionBarController$EditCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;Lcom/miui/notes/ui/BaseEditorActionBarController$EditCallback;)V", "mNaviPlaceHolder", "mToolBar", "mGroupMap", "getMGroupMap", "()Landroid/view/View;", "setMGroupMap", "(Landroid/view/View;)V", "mAddChildNode", "Landroid/widget/ImageView;", "getMAddChildNode", "()Landroid/widget/ImageView;", "setMAddChildNode", "(Landroid/widget/ImageView;)V", "mAddBrotherNode", "getMAddBrotherNode", "setMAddBrotherNode", "mDeleteNode", "getMDeleteNode", "setMDeleteNode", "mGroupOverView", "getMGroupOverView", "setMGroupOverView", "mLevelIncrease", "getMLevelIncrease", "setMLevelIncrease", "mLevelReduce", "getMLevelReduce", "setMLevelReduce", "mViewMode", "", "show", "", MiuiIntent.COMMAND_ICON_PANEL_HIDE, "updateStyle", "theme", "Lcom/miui/richeditor/theme/Theme;", "setViewMode", "viewMode", "onClick", "v", "updateMapBtnState", "addChildEnable", "", "addBrotherEnable", "deleteEnable", "setToolAddChildEnable", "enable", "setToolAddBrotherEnable", "setToolDeleteEnable", "updateOverviewBtnState", "levelReduceEnable", "levelIncreaseEnable", "setToolLevelIncreaseEnable", "setToolLevelReduceEnable", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadMindNoteToolBarController implements View.OnClickListener {
    private final BaseEditorActionBarController.EditCallback mActionCallBack;
    private ImageView mAddBrotherNode;
    private ImageView mAddChildNode;
    private ImageView mDeleteNode;
    private View mGroupMap;
    private View mGroupOverView;
    private ImageView mLevelIncrease;
    private ImageView mLevelReduce;
    private final View mNaviPlaceHolder;
    private final View mToolBar;
    private final View mToolRootView;
    private int mViewMode;

    public PadMindNoteToolBarController(View mToolRootView, BaseEditorActionBarController.EditCallback editCallback) {
        Intrinsics.checkNotNullParameter(mToolRootView, "mToolRootView");
        this.mToolRootView = mToolRootView;
        this.mActionCallBack = editCallback;
        View viewFindViewById = mToolRootView.findViewById(R.id.toolbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.mToolBar = viewFindViewById;
        View viewFindViewById2 = mToolRootView.findViewById(R.id.group_map);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.mGroupMap = viewFindViewById2;
        View viewFindViewById3 = mToolRootView.findViewById(R.id.add_child_node);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        ImageView imageView = (ImageView) viewFindViewById3;
        this.mAddChildNode = imageView;
        PadMindNoteToolBarController padMindNoteToolBarController = this;
        imageView.setOnClickListener(padMindNoteToolBarController);
        Folme.useAt(this.mAddChildNode).touch().handleTouchOf(this.mAddChildNode, new AnimConfig[0]);
        UIUtils.setFolmeHoverEffect(this.mAddChildNode);
        View viewFindViewById4 = mToolRootView.findViewById(R.id.add_brother_node);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        ImageView imageView2 = (ImageView) viewFindViewById4;
        this.mAddBrotherNode = imageView2;
        imageView2.setOnClickListener(padMindNoteToolBarController);
        UIUtils.setFolmeHoverEffect(this.mAddBrotherNode);
        Folme.useAt(this.mAddBrotherNode).touch().handleTouchOf(this.mAddBrotherNode, new AnimConfig[0]);
        View viewFindViewById5 = mToolRootView.findViewById(R.id.delete_node);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        ImageView imageView3 = (ImageView) viewFindViewById5;
        this.mDeleteNode = imageView3;
        imageView3.setOnClickListener(padMindNoteToolBarController);
        UIUtils.setFolmeHoverEffect(this.mDeleteNode);
        Folme.useAt(this.mDeleteNode).touch().handleTouchOf(this.mDeleteNode, new AnimConfig[0]);
        View viewFindViewById6 = mToolRootView.findViewById(R.id.group_overview);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
        this.mGroupOverView = viewFindViewById6;
        UIUtils.setFolmeHoverEffect(viewFindViewById6);
        View viewFindViewById7 = mToolRootView.findViewById(R.id.level_increase);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(...)");
        ImageView imageView4 = (ImageView) viewFindViewById7;
        this.mLevelIncrease = imageView4;
        imageView4.setOnClickListener(padMindNoteToolBarController);
        UIUtils.setFolmeHoverEffect(this.mLevelIncrease);
        Folme.useAt(this.mLevelIncrease).touch().handleTouchOf(this.mLevelIncrease, new AnimConfig[0]);
        View viewFindViewById8 = mToolRootView.findViewById(R.id.level_reduce);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(...)");
        ImageView imageView5 = (ImageView) viewFindViewById8;
        this.mLevelReduce = imageView5;
        imageView5.setOnClickListener(padMindNoteToolBarController);
        Folme.useAt(this.mLevelReduce).touch().handleTouchOf(this.mLevelReduce, new AnimConfig[0]);
        UIUtils.setFolmeHoverEffect(this.mLevelReduce);
        View viewFindViewById9 = mToolRootView.findViewById(R.id.navi_placeholder);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(...)");
        this.mNaviPlaceHolder = viewFindViewById9;
    }

    protected final View getMGroupMap() {
        return this.mGroupMap;
    }

    protected final void setMGroupMap(View view) {
        Intrinsics.checkNotNullParameter(view, "<set-?>");
        this.mGroupMap = view;
    }

    protected final ImageView getMAddChildNode() {
        return this.mAddChildNode;
    }

    protected final void setMAddChildNode(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<set-?>");
        this.mAddChildNode = imageView;
    }

    protected final ImageView getMAddBrotherNode() {
        return this.mAddBrotherNode;
    }

    protected final void setMAddBrotherNode(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<set-?>");
        this.mAddBrotherNode = imageView;
    }

    protected final ImageView getMDeleteNode() {
        return this.mDeleteNode;
    }

    protected final void setMDeleteNode(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<set-?>");
        this.mDeleteNode = imageView;
    }

    protected final View getMGroupOverView() {
        return this.mGroupOverView;
    }

    protected final void setMGroupOverView(View view) {
        Intrinsics.checkNotNullParameter(view, "<set-?>");
        this.mGroupOverView = view;
    }

    protected final ImageView getMLevelIncrease() {
        return this.mLevelIncrease;
    }

    protected final void setMLevelIncrease(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<set-?>");
        this.mLevelIncrease = imageView;
    }

    protected final ImageView getMLevelReduce() {
        return this.mLevelReduce;
    }

    protected final void setMLevelReduce(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<set-?>");
        this.mLevelReduce = imageView;
    }

    public final void show() {
        Logger.INSTANCE.d("RKeyboardHeightProvider", "mToolRootView.alpha" + this.mToolRootView.getAlpha());
        this.mToolRootView.animate().cancel();
        this.mToolRootView.setAlpha(0.0f);
        this.mToolRootView.animate().alpha(1.0f).setDuration(200L).setListener(new Animator.AnimatorListener() { // from class: com.miui.pad.feature.notes.mindnote.PadMindNoteToolBarController.show.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                PadMindNoteToolBarController.this.mToolRootView.setAlpha(1.0f);
                PadMindNoteToolBarController.this.mToolRootView.setVisibility(0);
            }
        }).start();
        Logger.INSTANCE.d("RKeyboardHeightProvider", "mToolBar.visibility" + this.mToolBar.getVisibility());
        this.mToolBar.animate().cancel();
        this.mToolBar.setVisibility(0);
        this.mToolBar.setAlpha(0.0f);
        this.mToolBar.animate().alpha(1.0f).setListener(new Animator.AnimatorListener() { // from class: com.miui.pad.feature.notes.mindnote.PadMindNoteToolBarController.show.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                PadMindNoteToolBarController.this.mToolBar.setAlpha(1.0f);
                PadMindNoteToolBarController.this.mToolBar.setVisibility(0);
                PadMindNoteToolBarController.this.mToolRootView.setVisibility(0);
            }
        }).setDuration(200L).start();
    }

    public final void hide() {
        this.mToolRootView.animate().alpha(0.0f).setDuration(200L).setListener(new Animator.AnimatorListener() { // from class: com.miui.pad.feature.notes.mindnote.PadMindNoteToolBarController.hide.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                if (!BlurHelper.isEnableBlur() || PadMindNoteToolBarController.this.mToolRootView.getBackground() == null) {
                    return;
                }
                PadMindNoteToolBarController.this.mToolRootView.setBackground(null);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                PadMindNoteToolBarController.this.mToolRootView.setVisibility(8);
            }
        }).start();
    }

    public final void updateStyle(Theme theme) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        this.mAddChildNode.setBackground(theme.getResTheme(BaseNoteApp.INSTANCE.getInstance()).getDrawable(R.drawable.ic_tool_add_child_node));
        this.mAddBrotherNode.setBackground(theme.getResTheme(BaseNoteApp.INSTANCE.getInstance()).getDrawable(R.drawable.ic_tool_add_brother_node));
        this.mDeleteNode.setBackground(theme.getResTheme(BaseNoteApp.INSTANCE.getInstance()).getDrawable(R.drawable.ic_tool_delete_node));
        this.mLevelIncrease.setBackground(theme.getResTheme(BaseNoteApp.INSTANCE.getInstance()).getDrawable(R.drawable.ic_tool_overview_promote));
        this.mLevelReduce.setBackground(theme.getResTheme(BaseNoteApp.INSTANCE.getInstance()).getDrawable(R.drawable.ic_tool_overview_demote));
        Theme.EditorStyle editorStyle = theme.getEditorStyle(this.mToolRootView.getContext(), System.currentTimeMillis());
        BlurHelper.setBlurBackground(this.mToolRootView, null, editorStyle.mBlurMixColor, editorStyle.mBlurMixColor2);
    }

    public final void setViewMode(int viewMode) {
        this.mViewMode = viewMode;
        if (viewMode == 0) {
            this.mGroupMap.setVisibility(0);
            this.mGroupOverView.setVisibility(8);
        } else if (viewMode == 1) {
            this.mGroupMap.setVisibility(8);
            this.mGroupOverView.setVisibility(0);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        switch (v.getId()) {
            case R.id.add_brother_node /* 2131361910 */:
                BaseEditorActionBarController.EditCallback editCallback = this.mActionCallBack;
                if (editCallback != null) {
                    editCallback.onPerformAction(51, new Object[0]);
                }
                break;
            case R.id.add_child_node /* 2131361911 */:
                BaseEditorActionBarController.EditCallback editCallback2 = this.mActionCallBack;
                if (editCallback2 != null) {
                    editCallback2.onPerformAction(50, new Object[0]);
                }
                break;
            case R.id.delete_node /* 2131362400 */:
                BaseEditorActionBarController.EditCallback editCallback3 = this.mActionCallBack;
                if (editCallback3 != null) {
                    editCallback3.onPerformAction(52, new Object[0]);
                }
                break;
            case R.id.level_increase /* 2131362809 */:
                BaseEditorActionBarController.EditCallback editCallback4 = this.mActionCallBack;
                if (editCallback4 != null) {
                    editCallback4.onPerformAction(55, new Object[0]);
                }
                break;
            case R.id.level_reduce /* 2131362810 */:
                BaseEditorActionBarController.EditCallback editCallback5 = this.mActionCallBack;
                if (editCallback5 != null) {
                    editCallback5.onPerformAction(56, new Object[0]);
                }
                break;
        }
    }

    public final void updateMapBtnState(boolean addChildEnable, boolean addBrotherEnable, boolean deleteEnable) {
        com.miui.notes.base.utils.Logger.INSTANCE.d("BridgeTest", "addChildEnable = " + addChildEnable + " addBrotherEnable = " + addBrotherEnable + " deleteEnable = " + deleteEnable);
        setToolAddChildEnable(addChildEnable);
        setToolAddBrotherEnable(addBrotherEnable);
        setToolDeleteEnable(deleteEnable);
    }

    private final void setToolAddChildEnable(boolean enable) {
        this.mAddChildNode.setEnabled(enable);
    }

    private final void setToolAddBrotherEnable(boolean enable) {
        this.mAddBrotherNode.setEnabled(enable);
    }

    private final void setToolDeleteEnable(boolean enable) {
        this.mDeleteNode.setEnabled(enable);
    }

    public final void updateOverviewBtnState(boolean levelReduceEnable, boolean levelIncreaseEnable) {
        com.miui.notes.base.utils.Logger.INSTANCE.d("BridgeTest", "levelReduceEnable = " + levelReduceEnable + " levelIncreaseEnable = " + levelIncreaseEnable);
        setToolLevelReduceEnable(levelReduceEnable);
        setToolLevelIncreaseEnable(levelIncreaseEnable);
    }

    public final void setToolLevelIncreaseEnable(boolean enable) {
        this.mLevelIncrease.setEnabled(enable);
    }

    public final void setToolLevelReduceEnable(boolean enable) {
        this.mLevelReduce.setEnabled(enable);
    }
}
