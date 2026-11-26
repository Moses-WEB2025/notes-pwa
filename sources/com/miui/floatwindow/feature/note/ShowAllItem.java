package com.miui.floatwindow.feature.note;

import android.view.View;
import android.view.ViewGroup;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.excerpt.ExcerptInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.base.AnimConfig;

/* JADX INFO: compiled from: ShowAllItem.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0016J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016¨\u0006\u0013"}, d2 = {"Lcom/miui/floatwindow/feature/note/ShowAllItem;", "Lcom/miui/floatwindow/feature/note/FloatBaseItem;", "itemView", "Landroid/view/View;", "itemHeight", "", "contentHeight", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;II)V", "bind", "", "type", "itemCache", "Lcom/miui/notes/cache/ItemCache;", "isSaving", "", Constants.JSON_FILTER_INFO, "Lcom/miui/notes/excerpt/ExcerptInfo;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ShowAllItem extends FloatBaseItem {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // com.miui.floatwindow.feature.note.FloatBaseItem
    public void bind(int type, ItemCache itemCache) {
        Intrinsics.checkNotNullParameter(itemCache, "itemCache");
    }

    @Override // com.miui.floatwindow.feature.note.FloatBaseItem
    public void bind(int type, boolean isSaving, ExcerptInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShowAllItem(View itemView, int i, int i2) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.height = i;
        itemView.setLayoutParams(layoutParams);
        this.mItemViewFolme = Folme.useAt(itemView);
        IFolme iFolme = this.mItemViewFolme;
        Intrinsics.checkNotNull(iFolme);
        iFolme.touch().setTint(0.03f, 0.0f, 0.0f, 0.0f).handleTouchOf(itemView, new AnimConfig[0]);
    }

    /* JADX INFO: compiled from: ShowAllItem.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t¨\u0006\u000b"}, d2 = {"Lcom/miui/floatwindow/feature/note/ShowAllItem$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "newInstance", "Lcom/miui/floatwindow/feature/note/ShowAllItem;", "parent", "Landroid/view/ViewGroup;", "itemHeight", "", "contentHeight", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ShowAllItem newInstance(ViewGroup parent, int itemHeight, int contentHeight) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            View viewInflateView = UIUtils.inflateView(parent, R.layout.float_show_all_note_item);
            Intrinsics.checkNotNull(viewInflateView);
            return new ShowAllItem(viewInflateView, itemHeight, contentHeight);
        }
    }
}
