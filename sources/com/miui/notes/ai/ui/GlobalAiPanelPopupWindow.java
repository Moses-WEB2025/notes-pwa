package com.miui.notes.ai.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.ai.text.widget.R;
import com.miui.doodle.document.Layer;
import com.miui.notes.ai.utils.DisplayUtil;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import miuix.popupwidget.widget.PopupWindow;

/* JADX INFO: compiled from: GlobalAiPanelPopupWindow.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0004\b\f\u0010\rJ\u001e\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u000f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000fJ\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u000bH\u0002R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/miui/notes/ai/ui/GlobalAiPanelPopupWindow;", "Lmiuix/popupwidget/widget/PopupWindow;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "tagContent", "", "", "panelType", "popupRvAdapterListener", "Lcom/miui/notes/ai/ui/PopupRvAdapterListener;", "selectIndex", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Ljava/util/List;Ljava/lang/String;Lcom/miui/notes/ai/ui/PopupRvAdapterListener;Ljava/lang/Integer;)V", "mPopupContentView", "Landroid/view/View;", "mPopupRv", "Landroidx/recyclerview/widget/RecyclerView;", "mPopupRvAdapter", "Lcom/miui/notes/ai/ui/PopupRvAdapter;", "popupWidth", "getPopupWidth", "type", "list", "showPopupWindow", "", "tagView", "rootView", "updateRecycleViewLayout", "recyclerView", Layer.KEY_WIDTH, "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiPanelPopupWindow extends PopupWindow {
    private final View mPopupContentView;
    private final RecyclerView mPopupRv;
    private final PopupRvAdapter mPopupRvAdapter;
    private final int popupWidth;

    public /* synthetic */ GlobalAiPanelPopupWindow(Context context, List list, String str, PopupRvAdapterListener popupRvAdapterListener, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, list, str, popupRvAdapterListener, (i & 16) != 0 ? 0 : num);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlobalAiPanelPopupWindow(Context context, List<String> tagContent, String panelType, PopupRvAdapterListener popupRvAdapterListener, Integer num) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tagContent, "tagContent");
        Intrinsics.checkNotNullParameter(panelType, "panelType");
        Intrinsics.checkNotNullParameter(popupRvAdapterListener, "popupRvAdapterListener");
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.global_ai_panel_popup, (ViewGroup) null, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        this.mPopupContentView = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.global_ai_panel_tag_popup_rv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById;
        this.mPopupRv = recyclerView;
        int popupWidth = getPopupWidth(panelType, tagContent);
        this.popupWidth = popupWidth;
        Log.i("getPopupWidth", "getPopupWidth,result=" + popupWidth);
        updateRecycleViewLayout(recyclerView, popupWidth);
        PopupRvAdapter popupRvAdapter = new PopupRvAdapter(context, tagContent, num);
        this.mPopupRvAdapter = popupRvAdapter;
        popupRvAdapter.setOnItemClickListener(popupRvAdapterListener);
        setContentView(viewInflate);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        recyclerView.setAdapter(popupRvAdapter);
    }

    private final int getPopupWidth(String type, List<String> list) {
        Resources resources;
        int i;
        TextView textView = new TextView(this.mContext);
        textView.setTextSize(1, 16.0f);
        if (TextUtils.equals(type, "copy_publish")) {
            resources = this.mContext.getResources();
            i = R.dimen.global_ai_text_popup_publish_text_min_width;
        } else {
            resources = this.mContext.getResources();
            i = R.dimen.global_ai_text_popup_comment_text_min_width;
        }
        float dimension = resources.getDimension(i);
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            float fMeasureText = textView.getPaint().measureText((String) it.next());
            if (fMeasureText > dimension) {
                dimension = fMeasureText;
            }
        }
        Log.i("getPopupWidth", "getPopupWidth,maxLen=" + dimension + " 80dp=" + DisplayUtil.dip2px(this.mContext, 80.0f) + " 208f=" + DisplayUtil.dip2px(this.mContext, 208.0f));
        return (int) (RangesKt.coerceAtMost(dimension, this.mContext.getResources().getDimension(R.dimen.global_ai_text_popup_max_text_width)) + this.mContext.getResources().getDimension(R.dimen.global_ai_text_popup_text_other_widget_width));
    }

    public final void showPopupWindow(View tagView, View rootView) {
        Intrinsics.checkNotNullParameter(tagView, "tagView");
        this.mPopupRvAdapter.notifyDataSetChanged();
        setAnimationStyle(0);
        int[] iArr = new int[2];
        tagView.getLocationInWindow(iArr);
        Rect rect = new Rect();
        if (rootView != null) {
            rootView.getGlobalVisibleRect(rect);
        }
        int iDip2px = (rect.right - DisplayUtil.dip2px(this.mContext, 24.0f)) - this.popupWidth;
        Log.i("getPopupWidth", "showPopupWindow,loc[0]=" + iArr[0] + ", offsetMax=" + iDip2px + ", rect.left=" + rect.left + " rect.right=" + rect.right);
        showAtLocation(tagView, 0, RangesKt.coerceAtMost(iArr[0], iDip2px), (int) ((iArr[1] - (this.mPopupRvAdapter.getItemCount() * this.mContext.getResources().getDimension(R.dimen.global_ai_text_panel_tag_popup_item_height))) + this.mContext.getResources().getDimension(R.dimen.global_ai_text_panel_tag_height)));
    }

    private final void updateRecycleViewLayout(RecyclerView recyclerView, int width) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) recyclerView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = width;
        }
        recyclerView.setLayoutParams(layoutParams);
    }
}
