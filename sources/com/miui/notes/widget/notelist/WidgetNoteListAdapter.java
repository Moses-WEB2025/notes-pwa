package com.miui.notes.widget.notelist;

import android.view.ViewGroup;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.notelist.BaseNoteListAdapter;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.component.BaseItem;
import java.security.InvalidParameterException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WidgetNoteListAdapter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\f\u001a\u00020\r2\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0004J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u000bH\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0005H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/notes/widget/notelist/WidgetNoteListAdapter;", "Lcom/miui/notes/basefeature/notelist/BaseNoteListAdapter;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mSelectId", "", "onCreateViewHolder", "Lcom/miui/notes/component/BaseItem;", "parent", "Landroid/view/ViewGroup;", "viewType", "", "createView", "Lcom/miui/notes/widget/notelist/WidgetNoteListItem;", "isCallNote", "", "onBindViewHolder", "", "holder", "position", "setSelectId", "selectId", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class WidgetNoteListAdapter extends BaseNoteListAdapter {
    private static final String TAG = "WidgetNoteListAdapter";
    private long mSelectId;

    public WidgetNoteListAdapter() {
        super(false);
    }

    @Override // com.miui.notes.basefeature.notelist.BaseNoteListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseItem onCreateViewHolder(ViewGroup parent, int viewType) {
        WidgetNoteListItem widgetNoteListItemCreateView;
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (viewType == 2) {
            widgetNoteListItemCreateView = createView(parent, false);
        } else if (viewType == 4) {
            widgetNoteListItemCreateView = createView(parent, true);
        } else {
            Logger.INSTANCE.w(TAG, "Unknown view type: " + viewType);
            throw new InvalidParameterException("unknown type ");
        }
        widgetNoteListItemCreateView.setOnClickListener(this.mOnClickListener);
        return widgetNoteListItemCreateView;
    }

    protected final WidgetNoteListItem createView(ViewGroup parent, boolean isCallNote) {
        WidgetNoteListItem widgetNoteListItemNewInstance = WidgetNoteListItem.newInstance(parent, isCallNote);
        Intrinsics.checkNotNullExpressionValue(widgetNoteListItemNewInstance, "newInstance(...)");
        return widgetNoteListItemNewInstance;
    }

    @Override // com.miui.notes.basefeature.notelist.BaseNoteListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseItem holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        ItemCache item = getItem(position);
        Object cacheObject = item.getCacheObject();
        Intrinsics.checkNotNull(cacheObject, "null cannot be cast to non-null type com.miui.notes.cache.NoteCache");
        holder.bind(item, this.mBindContext, position, ((long) ((NoteCache) cacheObject).getNote().getWidgetId()) == this.mSelectId);
    }

    @Override // com.miui.notes.basefeature.notelist.BaseNoteListAdapter
    public void setSelectId(long selectId) {
        this.mSelectId = selectId;
    }
}
