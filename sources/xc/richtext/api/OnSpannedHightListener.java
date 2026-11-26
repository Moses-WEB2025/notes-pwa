package xc.richtext.api;

import android.graphics.RectF;
import java.util.List;
import kotlin.Metadata;
import xc.richtext.data.SpannedData;

/* JADX INFO: compiled from: OnSpannedHightListener.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J.\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lxc/richtext/api/OnSpannedHightListener;", "", "onSpannedHightChanged", "", "mSpannedData", "Lxc/richtext/data/SpannedData;", "rectList", "", "Landroid/graphics/RectF;", "staticLayoutWidth", "", "scale", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface OnSpannedHightListener {
    void onSpannedHightChanged(SpannedData mSpannedData, List<? extends RectF> rectList, float staticLayoutWidth, float scale);
}
