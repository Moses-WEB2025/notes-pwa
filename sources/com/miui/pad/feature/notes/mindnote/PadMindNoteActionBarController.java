package com.miui.pad.feature.notes.mindnote;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.ui.BaseEditorActionBarController;
import com.miui.pad.feature.notes.PadNoteActionBarController;
import com.miui.richeditor.theme.Theme;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.app.Fragment;

/* JADX INFO: compiled from: PadMindNoteActionBarController.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\u0014\u001a\u00020\u000bH\u0014J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0010H\u0016J\u0010\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0010H\u0016J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00162\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fJ\u000e\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\rJ\u000e\u0010\"\u001a\u00020\u00162\u0006\u0010!\u001a\u00020\rJ\b\u0010(\u001a\u00020\u0016H\u0016J\b\u0010)\u001a\u00020\u0016H\u0016R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010#\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u000b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'¨\u0006*"}, d2 = {"Lcom/miui/pad/feature/notes/mindnote/PadMindNoteActionBarController;", "Lcom/miui/pad/feature/notes/PadNoteActionBarController;", "fragment", "Lmiuix/appcompat/app/Fragment;", "editCallback", "Lcom/miui/notes/ui/BaseEditorActionBarController$EditCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lmiuix/appcompat/app/Fragment;Lcom/miui/notes/ui/BaseEditorActionBarController$EditCallback;)V", "mMode", "Landroid/widget/ImageView;", "mViewMode", "", "mUndoEnable", "", "mRedoEnable", "mEndMenuGroup", "Landroid/view/View;", "mBtnUndo", "mBtnRedo", "mBlankView", "getLayoutResource", "initializeView", "", "contentView", "onClick", "v", "updateStyle", "theme", "Lcom/miui/richeditor/theme/Theme;", "updateMoreMenu", "menu", "Landroid/view/Menu;", "updateUndoEnable", "enable", "updateRedoEnable", "viewMode", "getViewMode", "()I", "setViewMode", "(I)V", "onViewMode", "onEditMode", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class PadMindNoteActionBarController extends PadNoteActionBarController {
    private View mBlankView;
    private ImageView mBtnRedo;
    private ImageView mBtnUndo;
    private View mEndMenuGroup;
    private ImageView mMode;
    private boolean mRedoEnable;
    private boolean mUndoEnable;
    private int mViewMode;

    @Override // com.miui.notes.ui.BaseEditorActionBarController
    protected int getLayoutResource() {
        return R.layout.pad_mind_note_edit_action_bar;
    }

    public final void updateMoreMenu(Menu menu) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PadMindNoteActionBarController(Fragment fragment, BaseEditorActionBarController.EditCallback editCallback) {
        super(fragment, editCallback);
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        this.mViewMode = -1;
    }

    @Override // com.miui.pad.feature.notes.PadNoteActionBarController, com.miui.notes.ui.BaseEditorActionBarController
    public void initializeView(View contentView) {
        Intrinsics.checkNotNullParameter(contentView, "contentView");
        super.initializeView(contentView);
        View viewFindViewById = contentView.findViewById(R.id.blank_view);
        this.mBlankView = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this);
        }
        this.mEndMenuGroup = contentView.findViewById(R.id.end_menu_group);
        ImageView imageView = (ImageView) contentView.findViewById(R.id.undo);
        this.mBtnUndo = imageView;
        if (imageView != null) {
            imageView.setOnClickListener(this);
        }
        ImageView imageView2 = this.mBtnUndo;
        if (imageView2 != null) {
            Intrinsics.checkNotNull(imageView2);
            imageView2.setImageDrawable(Theme.getThemeDrawable(R.drawable.ic_action_undo_40dp, this.mTheme.getResTheme(this.mActivity)));
        }
        ImageView imageView3 = (ImageView) contentView.findViewById(R.id.redo);
        this.mBtnRedo = imageView3;
        if (imageView3 != null) {
            imageView3.setOnClickListener(this);
        }
        ImageView imageView4 = this.mBtnRedo;
        if (imageView4 != null) {
            Intrinsics.checkNotNull(imageView4);
            imageView4.setImageDrawable(Theme.getThemeDrawable(R.drawable.ic_action_redo_40dp, this.mTheme.getResTheme(this.mActivity)));
        }
        ImageView imageView5 = (ImageView) contentView.findViewById(R.id.mode);
        this.mMode = imageView5;
        Intrinsics.checkNotNull(imageView5);
        imageView5.setOnClickListener(this);
        Folme.useAt(this.mBtnUndo).touch().handleTouchOf(this.mBtnUndo, new AnimConfig[0]);
        ImageView imageView6 = this.mBtnUndo;
        Intrinsics.checkNotNull(imageView6);
        UIUtils.setFolmeHoverEffect(imageView6);
        Folme.useAt(this.mBtnRedo).touch().handleTouchOf(this.mBtnRedo, new AnimConfig[0]);
        ImageView imageView7 = this.mBtnRedo;
        Intrinsics.checkNotNull(imageView7);
        UIUtils.setFolmeHoverEffect(imageView7);
        Folme.useAt(this.mMode).touch().handleTouchOf(this.mMode, new AnimConfig[0]);
        Folme.useAt(this.mMode).visible().setHide();
        ImageView imageView8 = this.mMode;
        Intrinsics.checkNotNull(imageView8);
        UIUtils.setFolmeHoverEffect(imageView8);
    }

    @Override // com.miui.pad.feature.notes.PadNoteActionBarController, com.miui.notes.ui.BaseEditorActionBarController, android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        switch (v.getId()) {
            case R.id.blank_view /* 2131362069 */:
                this.mActionCallback.onPerformAction(29, new Object[0]);
                break;
            case R.id.mode /* 2131362969 */:
                int i = this.mViewMode;
                if (i == 0) {
                    this.mActionCallback.onPerformAction(54, new Object[0]);
                    ImageView imageView = this.mMode;
                    Intrinsics.checkNotNull(imageView);
                    imageView.setImageDrawable(Theme.getThemeDrawable(R.drawable.miui_ic_mind_map, this.mTheme.getResTheme(this.mActivity)));
                    ImageView imageView2 = this.mMode;
                    Intrinsics.checkNotNull(imageView2);
                    ImageView imageView3 = this.mMode;
                    Intrinsics.checkNotNull(imageView3);
                    imageView2.setContentDescription(imageView3.getContext().getString(R.string.mind_note_mind_map_view));
                    this.mViewMode = 1;
                } else if (i == 1) {
                    this.mActionCallback.onPerformAction(53, new Object[0]);
                    ImageView imageView4 = this.mMode;
                    Intrinsics.checkNotNull(imageView4);
                    imageView4.setImageDrawable(Theme.getThemeDrawable(R.drawable.miui_ic_layers, this.mTheme.getResTheme(this.mActivity)));
                    ImageView imageView5 = this.mMode;
                    Intrinsics.checkNotNull(imageView5);
                    ImageView imageView6 = this.mMode;
                    Intrinsics.checkNotNull(imageView6);
                    imageView5.setContentDescription(imageView6.getContext().getString(R.string.mind_note_outline_view));
                    this.mViewMode = 0;
                }
                break;
            case R.id.redo /* 2131363278 */:
                this.mActionCallback.onPerformAction(18, new Object[0]);
                break;
            case R.id.undo /* 2131363735 */:
                this.mActionCallback.onPerformAction(17, new Object[0]);
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override // com.miui.pad.feature.notes.PadNoteActionBarController, com.miui.notes.ui.BaseEditorActionBarController
    public void updateStyle(Theme theme) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        super.updateStyle(theme);
        ImageView imageView = this.mMode;
        if (imageView != null) {
            int i = this.mViewMode;
            if (i == 0) {
                Intrinsics.checkNotNull(imageView);
                imageView.setImageDrawable(Theme.getThemeDrawable(R.drawable.miui_ic_layers, this.mTheme.getResTheme(this.mActivity)));
                ImageView imageView2 = this.mMode;
                Intrinsics.checkNotNull(imageView2);
                ImageView imageView3 = this.mMode;
                Intrinsics.checkNotNull(imageView3);
                imageView2.setContentDescription(imageView3.getContext().getString(R.string.mind_note_outline_view));
                return;
            }
            if (i == 1) {
                Intrinsics.checkNotNull(imageView);
                imageView.setImageDrawable(Theme.getThemeDrawable(R.drawable.miui_ic_mind_map, this.mTheme.getResTheme(this.mActivity)));
                ImageView imageView4 = this.mMode;
                Intrinsics.checkNotNull(imageView4);
                ImageView imageView5 = this.mMode;
                Intrinsics.checkNotNull(imageView5);
                imageView4.setContentDescription(imageView5.getContext().getString(R.string.mind_note_mind_map_view));
            }
        }
    }

    public final void updateUndoEnable(boolean enable) {
        this.mUndoEnable = enable;
        ImageView imageView = this.mBtnUndo;
        Intrinsics.checkNotNull(imageView);
        imageView.setEnabled(enable);
    }

    public final void updateRedoEnable(boolean enable) {
        this.mRedoEnable = enable;
        ImageView imageView = this.mBtnRedo;
        Intrinsics.checkNotNull(imageView);
        imageView.setEnabled(enable);
    }

    /* JADX INFO: renamed from: getViewMode, reason: from getter */
    public final int getMViewMode() {
        return this.mViewMode;
    }

    public final void setViewMode(int i) {
        this.mViewMode = i;
        ImageView imageView = this.mMode;
        if (imageView != null) {
            if (i == 0) {
                Intrinsics.checkNotNull(imageView);
                imageView.setImageDrawable(Theme.getThemeDrawable(R.drawable.miui_ic_layers, this.mTheme.getResTheme(this.mActivity)));
                ImageView imageView2 = this.mMode;
                Intrinsics.checkNotNull(imageView2);
                ImageView imageView3 = this.mMode;
                Intrinsics.checkNotNull(imageView3);
                imageView2.setContentDescription(imageView3.getContext().getString(R.string.mind_note_outline_view));
            } else if (i == 1) {
                Intrinsics.checkNotNull(imageView);
                imageView.setImageDrawable(Theme.getThemeDrawable(R.drawable.miui_ic_mind_map, this.mTheme.getResTheme(this.mActivity)));
                ImageView imageView4 = this.mMode;
                Intrinsics.checkNotNull(imageView4);
                ImageView imageView5 = this.mMode;
                Intrinsics.checkNotNull(imageView5);
                imageView4.setContentDescription(imageView5.getContext().getString(R.string.mind_note_mind_map_view));
            }
            Folme.useAt(this.mMode).visible().show(new AnimConfig[0]);
        }
    }

    @Override // com.miui.notes.ui.BaseEditorActionBarController
    public void onViewMode() {
        super.onViewMode();
        changeMenuVisibility(this.mBtnUndo, false);
        ImageView imageView = this.mBtnUndo;
        Intrinsics.checkNotNull(imageView);
        imageView.setOnClickListener(null);
        changeMenuVisibility(this.mBtnRedo, false);
        ImageView imageView2 = this.mBtnRedo;
        Intrinsics.checkNotNull(imageView2);
        imageView2.setOnClickListener(null);
        changeMenuVisibility(this.mMoreView, true);
        Folme.useAt(this.mEndMenuGroup).state().to(ViewProperty.TRANSLATION_X, 0);
        this.mMoreView.setVisibility(0);
    }

    @Override // com.miui.notes.ui.BaseEditorActionBarController
    public void onEditMode() {
        changeMenuVisibility(this.mBtnUndo, true);
        ImageView imageView = this.mBtnUndo;
        Intrinsics.checkNotNull(imageView);
        PadMindNoteActionBarController padMindNoteActionBarController = this;
        imageView.setOnClickListener(padMindNoteActionBarController);
        changeMenuVisibility(this.mBtnRedo, true);
        ImageView imageView2 = this.mBtnRedo;
        Intrinsics.checkNotNull(imageView2);
        imageView2.setOnClickListener(padMindNoteActionBarController);
        changeMenuVisibility(this.mMoreView, false);
        ImageView imageView3 = this.mMode;
        Intrinsics.checkNotNull(imageView3);
        int width = imageView3.getWidth();
        ImageView imageView4 = this.mMode;
        Intrinsics.checkNotNull(imageView4);
        ViewGroup.LayoutParams layoutParams = imageView4.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        int marginStart = width + ((ViewGroup.MarginLayoutParams) layoutParams).getMarginStart();
        IStateStyle iStateStyleState = Folme.useAt(this.mEndMenuGroup).state();
        ViewProperty viewProperty = ViewProperty.TRANSLATION_X;
        if (Utils.isRTL()) {
            marginStart = -marginStart;
        }
        iStateStyleState.to(viewProperty, Integer.valueOf(marginStart));
        super.onEditMode();
    }
}
