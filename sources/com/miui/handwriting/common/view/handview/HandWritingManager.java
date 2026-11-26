package com.miui.handwriting.common.view.handview;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: HandWritingManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005J\u0006\u0010\u000b\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/handwriting/common/view/handview/HandWritingManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "VIEW_TYPE_PAGE_SURFACE_VIEW", "", "VIEW_TYPE_PAGE_TEXTURE_VIEW", "viewType", "setViewType", "", "newType", "getViewType", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HandWritingManager {
    public static final HandWritingManager INSTANCE = new HandWritingManager();
    public static final int VIEW_TYPE_PAGE_SURFACE_VIEW = 0;
    public static final int VIEW_TYPE_PAGE_TEXTURE_VIEW = 1;
    private static int viewType;

    private HandWritingManager() {
    }

    public final void setViewType(int newType) {
        viewType = newType;
    }

    public final int getViewType() {
        return viewType;
    }
}
