package com.miui.notes.basefeature.noteedit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.editor.ImagePopMenu;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class RichTextFloatMenuHelper {
    public static RichTextFloatMenuInfo createLinkCardMenu(Context context, Rect rect, Rect rect2, final View.OnClickListener onClickListener) {
        final RichTextFloatMenuInfo richTextFloatMenuInfo = new RichTextFloatMenuInfo();
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.editor_link_card_menu, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) frameLayout.findViewById(R.id.menu_view);
        richTextFloatMenuInfo.menu = createPopupWindow(frameLayout);
        richTextFloatMenuInfo.gravity = 0;
        setMenuShowInfo(context, rect, rect2, frameLayout, richTextFloatMenuInfo);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.RichTextFloatMenuHelper$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RichTextFloatMenuHelper.lambda$createLinkCardMenu$0(onClickListener, richTextFloatMenuInfo, view);
                }
            });
        }
        return richTextFloatMenuInfo;
    }

    static /* synthetic */ void lambda$createLinkCardMenu$0(View.OnClickListener onClickListener, RichTextFloatMenuInfo richTextFloatMenuInfo, View view) {
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
        richTextFloatMenuInfo.menu.dismiss();
    }

    public static RichTextFloatMenuInfo createImageActionMenu(Context context, Rect rect, Rect rect2, boolean z, final View.OnClickListener onClickListener) {
        final RichTextFloatMenuInfo richTextFloatMenuInfo = new RichTextFloatMenuInfo();
        Logger.INSTANCE.d("RichTextFloatMenuHelper", "createImageActionMenu");
        Resources resources = context.getResources();
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(context).inflate((UIUtils.isInJ18NarrowScreen(context) || (RomUtils.isO88() && DisplayUtils.getRealScreenOrientation(context) == 1 && resources != null && !UIUtils.isFreeFormScreen(resources.getConfiguration()))) ? R.layout.editor_image_menu_small : R.layout.editor_image_menu, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) frameLayout.findViewById(R.id.menu_view);
        final AppCompatImageView appCompatImageView = (AppCompatImageView) linearLayout.findViewById(R.id.annotate_image);
        appCompatImageView.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.RichTextFloatMenuHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                appCompatImageView.sendAccessibilityEvent(128);
            }
        }, 100L);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) linearLayout.findViewById(R.id.scale_image);
        AccessibilityDelegateUtil.setButtonClassAccessibilityDelegate(appCompatImageView2);
        if (z) {
            appCompatImageView2.setImageResource(R.drawable.ic_float_menu_zoom_in);
            appCompatImageView2.setContentDescription(resources.getString(R.string.image_action_zoom_in));
        } else {
            appCompatImageView2.setImageResource(R.drawable.ic_float_menu_zoom_out);
            appCompatImageView2.setSelected(false);
            appCompatImageView2.setContentDescription(resources.getString(R.string.image_action_zoom_out));
        }
        if (LiteUtils.isSuperLite()) {
            linearLayout.findViewById(R.id.edit_image).setVisibility(8);
        }
        if (!Utils.isSupportSuperClipboard()) {
            Logger.INSTANCE.e("RichTextFloatMenuHelper", "not SupportSuperClipboard");
            linearLayout.findViewById(R.id.copy_image).setVisibility(8);
        }
        richTextFloatMenuInfo.menu = createPopupWindow(frameLayout);
        richTextFloatMenuInfo.gravity = 0;
        setMenuShowInfo(context, rect, rect2, frameLayout, richTextFloatMenuInfo);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.RichTextFloatMenuHelper$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RichTextFloatMenuHelper.lambda$createImageActionMenu$2(onClickListener, richTextFloatMenuInfo, view);
                }
            });
        }
        return richTextFloatMenuInfo;
    }

    static /* synthetic */ void lambda$createImageActionMenu$2(View.OnClickListener onClickListener, RichTextFloatMenuInfo richTextFloatMenuInfo, View view) {
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
        richTextFloatMenuInfo.menu.dismiss();
    }

    public static RichTextFloatMenuInfo createPcImageActionMenu(Context context, Rect rect, Rect rect2, boolean z, final View.OnClickListener onClickListener) {
        final RichTextFloatMenuInfo richTextFloatMenuInfo = new RichTextFloatMenuInfo();
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.pc_note_image_edit_menu, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) frameLayout.findViewById(R.id.menu_view);
        TextView textView = (TextView) linearLayout.findViewById(R.id.scale_image);
        if (z) {
            textView.setText(R.string.image_action_zoom_in);
        } else {
            textView.setText(R.string.image_action_zoom_out);
        }
        richTextFloatMenuInfo.menu = createPopupWindow(frameLayout);
        richTextFloatMenuInfo.gravity = 0;
        setMenuShowInfo(context, rect, rect2, frameLayout, richTextFloatMenuInfo);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.RichTextFloatMenuHelper.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    View.OnClickListener onClickListener2 = onClickListener;
                    if (onClickListener2 != null) {
                        onClickListener2.onClick(view);
                    }
                    richTextFloatMenuInfo.menu.dismiss();
                }
            });
        }
        return richTextFloatMenuInfo;
    }

    private static PopupWindow createPopupWindow(View view) {
        ImagePopMenu imagePopMenu = new ImagePopMenu(view, -2, -2);
        imagePopMenu.setOutsideTouchable(true);
        imagePopMenu.setClippingEnabled(false);
        imagePopMenu.setFocusable(false);
        imagePopMenu.setElevation(8.0f);
        imagePopMenu.setWindowLayoutType(1002);
        imagePopMenu.setClippingEnabled(false);
        imagePopMenu.setAnimationStyle(R.style.RichTextFloatMenuAnim);
        return imagePopMenu;
    }

    private static void setMenuShowInfo(Context context, Rect rect, Rect rect2, View view, RichTextFloatMenuInfo richTextFloatMenuInfo) {
        Resources resources = context.getResources();
        int navigationBarHeight = UIUtils.getNavigationBarHeight(context);
        if (UIUtils.isSmallWindowMode(context)) {
            navigationBarHeight = 0;
        }
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.rich_text_menu_margin_vertical_to_edge);
        View viewFindViewById = view.findViewById(R.id.container);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewFindViewById.getLayoutParams();
        if (Build.VERSION.SDK_INT >= 30 && !LiteUtils.isSuperLite() && Utils.isSupportSuperClipboard() && context.getResources().getDimension(R.dimen.editor_image_menu_width) > context.getDisplay().getWidth()) {
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams.rightMargin = 0;
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) ((LinearLayout) viewFindViewById.findViewById(R.id.menu_view)).getLayoutParams();
            marginLayoutParams2.leftMargin = 0;
            marginLayoutParams2.rightMargin = 0;
        }
        viewFindViewById.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        Rect rect3 = new Rect(0, 0, viewFindViewById.getMeasuredWidth(), viewFindViewById.getMeasuredHeight());
        rect3.offset(marginLayoutParams.leftMargin, marginLayoutParams.topMargin);
        int iWidth = rect3.width() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
        int iHeight = rect.top + ((int) ((rect.height() - rect3.height()) / 2.0f));
        if (rect3.height() + iHeight + dimensionPixelSize + navigationBarHeight > rect2.bottom) {
            iHeight = ((rect2.bottom - rect3.height()) - dimensionPixelSize) - navigationBarHeight;
        } else if (iHeight < rect2.top + dimensionPixelSize) {
            iHeight = rect2.top + dimensionPixelSize;
        }
        int i = iHeight - marginLayoutParams.topMargin;
        richTextFloatMenuInfo.showX = Math.abs(rect2.left + ((int) ((rect2.width() - iWidth) / 2.0f)));
        richTextFloatMenuInfo.showY = i;
    }

    private static boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }
}
