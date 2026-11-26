package cn.kuaipan.android.utils;

import java.util.Collection;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class ObtainabelList<E> extends LinkedList<E> implements IObtainable {
    private static final int MAX_POOL_SIZE = 80;
    private static ObtainabelList<?> mPool = null;
    private static int mPoolSize = 0;
    private static Object mPoolSync = new Object();
    private static final long serialVersionUID = 6483198895359712723L;
    private ObtainabelList<?> next;

    public static <E> ObtainabelList<E> obtain() {
        synchronized (mPoolSync) {
            ObtainabelList<E> obtainabelList = (ObtainabelList<E>) mPool;
            if (obtainabelList != null) {
                mPool = ((ObtainabelList) obtainabelList).next;
                ((ObtainabelList) obtainabelList).next = null;
                mPoolSize--;
                obtainabelList.clear();
                return obtainabelList;
            }
            return new ObtainabelList<>();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.kuaipan.android.utils.IObtainable
    public void recycle() {
        synchronized (mPoolSync) {
            int i = mPoolSize;
            if (i < 80) {
                mPoolSize = i + 1;
                this.next = mPool;
                mPool = this;
            }
            for (Object obj : this) {
                if (obj instanceof IObtainable) {
                    ((IObtainable) obj).recycle();
                }
            }
            clear();
        }
    }

    private ObtainabelList() {
    }

    private ObtainabelList(Collection<? extends E> collection) {
        super(collection);
    }
}
