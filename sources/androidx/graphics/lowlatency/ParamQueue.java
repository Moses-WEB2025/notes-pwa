package androidx.graphics.lowlatency;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ParamQueue.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fJ \u0010\u0010\u001a\u00020\r2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\r0\u0012H\u0086\bø\u0001\u0000J \u0010\u0013\u001a\u00020\r2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\r0\u0012H\u0086\bø\u0001\u0000J\u0013\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u0016J\u0014\u0010\u0017\u001a\u00020\r2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019J\u0006\u0010\u001a\u001a\u00020\u000bJ\u0006\u0010\u001b\u001a\u00020\u001cR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00028\u00000\bj\b\u0012\u0004\u0012\u00028\u0000`\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001d"}, d2 = {"Landroidx/graphics/lowlatency/ParamQueue;", ExifInterface.GPS_DIRECTION_TRUE, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mLock", "Ljava/util/concurrent/locks/ReentrantLock;", "mParams", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "mIndex", "", "clear", "", "release", "", "next", "block", "Lkotlin/Function1;", "flush", "add", "param", "(Ljava/lang/Object;)V", "addAll", "params", "", "count", "isEmpty", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ParamQueue<T> {
    private int mIndex;
    private final ReentrantLock mLock = new ReentrantLock();
    private ArrayList<T> mParams = new ArrayList<>();

    public final void clear() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            this.mIndex = 0;
            this.mParams.clear();
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final Collection<T> release() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            ArrayList<T> arrayList = this.mParams;
            this.mParams = new ArrayList<>();
            this.mIndex = 0;
            return arrayList;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void next(Function1<? super T, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            if (this.mIndex < this.mParams.size()) {
                ArrayList arrayList = this.mParams;
                int i = this.mIndex;
                this.mIndex = i + 1;
                block.invoke((Object) arrayList.get(i));
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void flush(Function1<? super T, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        while (this.mIndex < this.mParams.size()) {
            try {
                ArrayList arrayList = this.mParams;
                int i = this.mIndex;
                this.mIndex = i + 1;
                block.invoke((Object) arrayList.get(i));
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }
        Unit unit = Unit.INSTANCE;
        reentrantLock.unlock();
    }

    public final void add(T param) {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            this.mParams.add(param);
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void addAll(Collection<? extends T> params) {
        Intrinsics.checkNotNullParameter(params, "params");
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            this.mParams.addAll(params);
        } finally {
            reentrantLock.unlock();
        }
    }

    public final int count() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            return this.mParams.size();
        } finally {
            reentrantLock.unlock();
        }
    }

    public final boolean isEmpty() {
        return count() == 0;
    }
}
