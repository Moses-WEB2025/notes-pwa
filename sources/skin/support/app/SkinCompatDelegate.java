package skin.support.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import skin.support.SkinCompatManager;
import skin.support.widget.SkinCompatSupportable;

/* JADX INFO: loaded from: classes6.dex */
public class SkinCompatDelegate implements LayoutInflater.Factory2 {
    private final Context mContext;
    private SkinCompatViewInflater mSkinCompatViewInflater;
    private List<WeakReference<SkinCompatSupportable>> mSkinHelpers = new CopyOnWriteArrayList();

    private SkinCompatDelegate(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View viewCreateView = createView(view, str, context, attributeSet);
        if (viewCreateView == 0) {
            return null;
        }
        if (viewCreateView instanceof SkinCompatSupportable) {
            this.mSkinHelpers.add(new WeakReference<>((SkinCompatSupportable) viewCreateView));
        }
        return viewCreateView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View viewCreateView = createView(null, str, context, attributeSet);
        if (viewCreateView == 0) {
            return null;
        }
        if (viewCreateView instanceof SkinCompatSupportable) {
            this.mSkinHelpers.add(new WeakReference<>((SkinCompatSupportable) viewCreateView));
        }
        return viewCreateView;
    }

    public View createView(View view, String str, Context context, AttributeSet attributeSet) {
        if (this.mSkinCompatViewInflater == null) {
            this.mSkinCompatViewInflater = new SkinCompatViewInflater();
        }
        Iterator<SkinWrapper> it = SkinCompatManager.getInstance().getWrappers().iterator();
        while (it.hasNext()) {
            Context contextWrapContext = it.next().wrapContext(this.mContext, view, attributeSet);
            if (contextWrapContext != null) {
                context = contextWrapContext;
            }
        }
        return this.mSkinCompatViewInflater.createView(view, str, context, attributeSet);
    }

    public static SkinCompatDelegate create(Context context) {
        return new SkinCompatDelegate(context);
    }

    public void applySkin() {
        List<WeakReference<SkinCompatSupportable>> list = this.mSkinHelpers;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (WeakReference<SkinCompatSupportable> weakReference : this.mSkinHelpers) {
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().applySkin();
            }
        }
    }
}
