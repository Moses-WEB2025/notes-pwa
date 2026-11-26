package com.miui.common.view.StrikeTextView;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class StrikeTextView extends AppCompatTextView {
    private List<IPainting> paintings;

    public StrikeTextView(Context context) {
        this(context, null);
    }

    public StrikeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textViewStyle);
    }

    public StrikeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.paintings = null;
    }

    void addPainting(IPainting iPainting) {
        if (this.paintings == null) {
            this.paintings = new ArrayList();
        }
        this.paintings.add(iPainting);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        super.onDraw(canvas);
        List<IPainting> list = this.paintings;
        if (list == null || list.size() == 0) {
            return;
        }
        Iterator<IPainting> it = this.paintings.iterator();
        while (it.hasNext()) {
            it.next().onDraw(canvas);
        }
    }
}
