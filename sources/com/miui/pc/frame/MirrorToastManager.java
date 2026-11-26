package com.miui.pc.frame;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.support.cardview.CardView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class MirrorToastManager {
    private static final String TAG = "MirrorToastManager";
    private static WeakReference<Toast> sLastToast;

    public static void show(Context context, int i, boolean z) {
        show(context, context.getResources().getString(i), z);
    }

    public static void show(final Context context, final CharSequence charSequence, final boolean z) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(context, charSequence, 0, z);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.pc.frame.MirrorToastManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MirrorToastManager.show(context, charSequence, 0, z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void show(Context context, CharSequence charSequence, int i, boolean z) {
        try {
            WeakReference<Toast> weakReference = sLastToast;
            if (weakReference != null && weakReference.get() != null) {
                sLastToast.get().cancel();
            }
            Toast toastMakeText = Toast.makeText(context, charSequence, i);
            View viewInflate = View.inflate(context, R.layout.layout_mirror_toast, null);
            CardView cardView = (CardView) viewInflate.findViewById(R.id.cv_toast);
            TextView textView = (TextView) viewInflate.findViewById(R.id.tv_toast);
            cardView.setCardBackgroundColor(z ? -16777216 : -1);
            textView.setText(charSequence);
            toastMakeText.setView(viewInflate);
            toastMakeText.show();
            sLastToast = new WeakReference<>(toastMakeText);
        } catch (Exception e) {
            Logger.INSTANCE.i(TAG, "showToastError: " + e.getMessage());
        }
    }
}
