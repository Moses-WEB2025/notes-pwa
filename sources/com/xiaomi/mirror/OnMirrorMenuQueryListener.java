package com.xiaomi.mirror;

import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public interface OnMirrorMenuQueryListener {
    ArrayList<MirrorMenu> onMirrorMenuQuery(View view);

    void onMirrorMenuShow(View view, boolean z);
}
