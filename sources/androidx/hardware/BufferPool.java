package androidx.hardware;

import android.hardware.HardwareBuffer;
import androidx.exifinterface.media.ExifInterface;
import androidx.hardware.BufferPool.BufferProvider;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: BufferPool.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0001\u0018\u0000 0*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0003./0B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u0000¢\u0006\u0002\u0010\u0019J/\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u00052\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001fJ\u0015\u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0019J\u001f\u0010!\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010\"J\u001a\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020%2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010'J\u0006\u0010,\u001a\u00020\u0017J\u0006\u0010-\u001a\u00020\u0017R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR*\u0010\n\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0\u000bj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010(\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b)\u0010\tR\u0011\u0010*\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b*\u0010+\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00061"}, d2 = {"Landroidx/hardware/BufferPool;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/hardware/BufferPool$BufferProvider;", "", "maxPoolSize", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(I)V", "getMaxPoolSize", "()I", "mPool", "Ljava/util/ArrayList;", "Landroidx/hardware/BufferPool$Entry;", "Lkotlin/collections/ArrayList;", "mLock", "Ljava/util/concurrent/locks/ReentrantLock;", "mCondition", "Ljava/util/concurrent/locks/Condition;", "kotlin.jvm.PlatformType", "mBuffersAvailable", "mIsClosed", "", "add", "", "buffer", "(Landroidx/hardware/BufferPool$BufferProvider;)V", "obtain", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "factory", "Lkotlin/Function0;", "(IILkotlin/jvm/functions/Function0;)Landroidx/hardware/BufferPool$BufferProvider;", "insert", "obtainFromPool", "(II)Landroidx/hardware/BufferPool$BufferProvider;", "release", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "fence", "Landroidx/hardware/SyncFenceCompat;", "allocationCount", "getAllocationCount", "isClosed", "()Z", SplitLayout.TAG_CLOSE, "closeInvalidBuffer", "Entry", "BufferProvider", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BufferPool<T extends BufferProvider> {
    private static final String TAG = "BufferPool";
    private int mBuffersAvailable;
    private final Condition mCondition;
    private boolean mIsClosed;
    private final ReentrantLock mLock;
    private final ArrayList<Entry<T>> mPool = new ArrayList<>();
    private final int maxPoolSize;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Function1<Entry<?>, Boolean> signaledFence = new Function1() { // from class: androidx.hardware.BufferPool$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return Boolean.valueOf(BufferPool.signaledFence$lambda$14((BufferPool.Entry) obj));
        }
    };
    private static final Function1<Entry<?>, Boolean> isAvailable = new Function1() { // from class: androidx.hardware.BufferPool$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return Boolean.valueOf(BufferPool.isAvailable$lambda$15((BufferPool.Entry) obj));
        }
    };

    /* JADX INFO: compiled from: BufferPool.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Landroidx/hardware/BufferPool$BufferProvider;", "", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "getHardwareBuffer", "()Landroid/hardware/HardwareBuffer;", "release", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface BufferProvider {
        HardwareBuffer getHardwareBuffer();

        void release();
    }

    public BufferPool(int i) {
        this.maxPoolSize = i;
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mCondition = reentrantLock.newCondition();
        if (i <= 0) {
            throw new IllegalArgumentException("Pool size must be at least 1");
        }
    }

    public final int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public final void add(T buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        this.mPool.add(new Entry<>(buffer, null, false, 6, null));
    }

    public final T obtain(int w, int h, Function0<? extends T> factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            T t = (T) obtainFromPool(w, h);
            if (t == null) {
                T tInvoke = factory.invoke();
                insert(tInvoke);
                t = tInvoke;
            }
            return t;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void insert(T buffer) {
        boolean z = this.maxPoolSize == 1;
        if (z) {
            this.mBuffersAvailable++;
        }
        this.mPool.add(new Entry<>(buffer, null, z, 2, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final T obtainFromPool(int w, int h) throws InterruptedException {
        if (this.mIsClosed) {
            throw new IllegalStateException("Attempt to obtain frame buffer from FrameBufferPool that has already been closed");
        }
        if (this.maxPoolSize == 1) {
            Entry entry = (Entry) CollectionsKt.firstOrNull((List) this.mPool);
            if (entry != null) {
                entry.setAvailable(true);
            } else {
                entry = null;
            }
            if (entry != null) {
                return (T) entry.getBufferProvider();
            }
            return null;
        }
        while (this.mBuffersAvailable == 0 && this.mPool.size() >= this.maxPoolSize) {
            this.mCondition.await();
        }
        Entry entry2 = (Entry) INSTANCE.findEntryWith$libXCEngine_release(this.mPool, isAvailable, signaledFence);
        if (entry2 != null) {
            this.mBuffersAvailable--;
            entry2.setAvailable(false);
            SyncFenceCompat releaseFence = entry2.getReleaseFence();
            if (releaseFence != null) {
                releaseFence.awaitForever();
                releaseFence.close();
            }
            entry2.getBufferProvider();
        } else {
            entry2 = null;
        }
        if (entry2 != null) {
            return (T) entry2.getBufferProvider();
        }
        return null;
    }

    public static /* synthetic */ void release$default(BufferPool bufferPool, HardwareBuffer hardwareBuffer, SyncFenceCompat syncFenceCompat, int i, Object obj) {
        if ((i & 2) != 0) {
            syncFenceCompat = null;
        }
        bufferPool.release(hardwareBuffer, syncFenceCompat);
    }

    public final void release(HardwareBuffer hardwareBuffer, SyncFenceCompat fence) {
        Object next;
        Intrinsics.checkNotNullParameter(hardwareBuffer, "hardwareBuffer");
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            Iterator<T> it = this.mPool.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (((Entry) next).getHardwareBuffer() != hardwareBuffer);
            Entry entry = (Entry) next;
            if (entry != null) {
                if (!entry.isAvailable()) {
                    if (!entry.getHardwareBuffer().isClosed()) {
                        entry.setReleaseFence(fence);
                        entry.setAvailable(true);
                        this.mBuffersAvailable++;
                    } else {
                        this.mPool.remove(entry);
                    }
                }
                if (!this.mIsClosed) {
                    this.mCondition.signal();
                } else {
                    hardwareBuffer.close();
                    if (this.mBuffersAvailable == this.mPool.size()) {
                        this.mPool.clear();
                    }
                }
            } else if (!hardwareBuffer.isClosed()) {
                throw new IllegalArgumentException("No entry associated with this framebuffer instance. Was this frame buffer created from a different FrameBufferPool?");
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public final int getAllocationCount() {
        return this.mPool.size();
    }

    public final boolean isClosed() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            return this.mIsClosed;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void close() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            if (!this.mIsClosed) {
                Iterator<Entry<T>> it = this.mPool.iterator();
                Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                while (it.hasNext()) {
                    Entry<T> next = it.next();
                    Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                    Entry<T> entry = next;
                    if (entry.isAvailable()) {
                        SyncFenceCompat releaseFence = entry.getReleaseFence();
                        if (releaseFence != null) {
                            releaseFence.awaitForever();
                            releaseFence.close();
                        }
                        entry.getBufferProvider().release();
                    }
                }
                if (this.mBuffersAvailable == this.mPool.size()) {
                    this.mPool.clear();
                }
                this.mIsClosed = true;
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void closeInvalidBuffer() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            if (!this.mIsClosed) {
                CollectionsKt.removeAll((List) this.mPool, new Function1() { // from class: androidx.hardware.BufferPool$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(BufferPool.closeInvalidBuffer$lambda$13$lambda$12(this.f$0, (BufferPool.Entry) obj));
                    }
                });
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean closeInvalidBuffer$lambda$13$lambda$12(BufferPool bufferPool, Entry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        if (!entry.isAvailable()) {
            return false;
        }
        SyncFenceCompat releaseFence = entry.getReleaseFence();
        if (releaseFence != null) {
            releaseFence.awaitForever();
            releaseFence.close();
        }
        entry.setReleaseFence(null);
        entry.getBufferProvider().release();
        bufferPool.mBuffersAvailable--;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: BufferPool.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u0000*\b\b\u0001\u0010\u0001*\u00020\u00022\u00020\u0003B%\u0012\u0006\u0010\u0004\u001a\u00028\u0001\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u000e\u0010\u0019\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\fJ\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010\u001b\u001a\u00020\bHÆ\u0003J4\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u00012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001¢\u0006\u0002\u0010\u001dJ\u0013\u0010\u001e\u001a\u00020\b2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010 \u001a\u00020!HÖ\u0001J\t\u0010\"\u001a\u00020#HÖ\u0001R\u0013\u0010\u0004\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006$"}, d2 = {"Landroidx/hardware/BufferPool$Entry;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/hardware/BufferPool$BufferProvider;", "", "bufferProvider", "releaseFence", "Landroidx/hardware/SyncFenceCompat;", "isAvailable", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/hardware/BufferPool$BufferProvider;Landroidx/hardware/SyncFenceCompat;Z)V", "getBufferProvider", "()Landroidx/hardware/BufferPool$BufferProvider;", "Landroidx/hardware/BufferPool$BufferProvider;", "getReleaseFence", "()Landroidx/hardware/SyncFenceCompat;", "setReleaseFence", "(Landroidx/hardware/SyncFenceCompat;)V", "()Z", "setAvailable", "(Z)V", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "getHardwareBuffer", "()Landroid/hardware/HardwareBuffer;", "component1", "component2", "component3", "copy", "(Landroidx/hardware/BufferPool$BufferProvider;Landroidx/hardware/SyncFenceCompat;Z)Landroidx/hardware/BufferPool$Entry;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    static final /* data */ class Entry<T extends BufferProvider> {
        private final T bufferProvider;
        private boolean isAvailable;
        private SyncFenceCompat releaseFence;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Entry copy$default(Entry entry, BufferProvider bufferProvider, SyncFenceCompat syncFenceCompat, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                bufferProvider = entry.bufferProvider;
            }
            if ((i & 2) != 0) {
                syncFenceCompat = entry.releaseFence;
            }
            if ((i & 4) != 0) {
                z = entry.isAvailable;
            }
            return entry.copy(bufferProvider, syncFenceCompat, z);
        }

        public final T component1() {
            return this.bufferProvider;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final SyncFenceCompat getReleaseFence() {
            return this.releaseFence;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getIsAvailable() {
            return this.isAvailable;
        }

        public final Entry<T> copy(T bufferProvider, SyncFenceCompat releaseFence, boolean isAvailable) {
            Intrinsics.checkNotNullParameter(bufferProvider, "bufferProvider");
            return new Entry<>(bufferProvider, releaseFence, isAvailable);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) other;
            return Intrinsics.areEqual(this.bufferProvider, entry.bufferProvider) && Intrinsics.areEqual(this.releaseFence, entry.releaseFence) && this.isAvailable == entry.isAvailable;
        }

        public int hashCode() {
            int iHashCode = this.bufferProvider.hashCode() * 31;
            SyncFenceCompat syncFenceCompat = this.releaseFence;
            return ((iHashCode + (syncFenceCompat == null ? 0 : syncFenceCompat.hashCode())) * 31) + Boolean.hashCode(this.isAvailable);
        }

        public String toString() {
            return "Entry(bufferProvider=" + this.bufferProvider + ", releaseFence=" + this.releaseFence + ", isAvailable=" + this.isAvailable + ")";
        }

        public Entry(T bufferProvider, SyncFenceCompat syncFenceCompat, boolean z) {
            Intrinsics.checkNotNullParameter(bufferProvider, "bufferProvider");
            this.bufferProvider = bufferProvider;
            this.releaseFence = syncFenceCompat;
            this.isAvailable = z;
        }

        public /* synthetic */ Entry(BufferProvider bufferProvider, SyncFenceCompat syncFenceCompat, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(bufferProvider, (i & 2) != 0 ? null : syncFenceCompat, (i & 4) != 0 ? true : z);
        }

        public final T getBufferProvider() {
            return this.bufferProvider;
        }

        public final SyncFenceCompat getReleaseFence() {
            return this.releaseFence;
        }

        public final void setReleaseFence(SyncFenceCompat syncFenceCompat) {
            this.releaseFence = syncFenceCompat;
        }

        public final boolean isAvailable() {
            return this.isAvailable;
        }

        public final void setAvailable(boolean z) {
            this.isAvailable = z;
        }

        public final HardwareBuffer getHardwareBuffer() {
            return this.bufferProvider.getHardwareBuffer();
        }
    }

    /* JADX INFO: compiled from: BufferPool.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0080\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JS\u0010\u000b\u001a\u0004\u0018\u0001H\f\"\u0004\b\u0001\u0010\f*\u0012\u0012\u0004\u0012\u0002H\f0\rj\b\u0012\u0004\u0012\u0002H\f`\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\t0\u00072\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\t0\u0007H\u0000¢\u0006\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Landroidx/hardware/BufferPool$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "signaledFence", "Lkotlin/Function1;", "Landroidx/hardware/BufferPool$Entry;", "", "isAvailable", "findEntryWith", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "primaryCondition", "secondaryCondition", "findEntryWith$libXCEngine_release", "(Ljava/util/ArrayList;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final <T> T findEntryWith$libXCEngine_release(ArrayList<T> arrayList, Function1<? super T, Boolean> primaryCondition, Function1<? super T, Boolean> secondaryCondition) {
            Intrinsics.checkNotNullParameter(arrayList, "<this>");
            Intrinsics.checkNotNullParameter(primaryCondition, "primaryCondition");
            Intrinsics.checkNotNullParameter(secondaryCondition, "secondaryCondition");
            Iterator<T> it = arrayList.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            T t = null;
            while (it.hasNext()) {
                T next = it.next();
                if (primaryCondition.invoke(next).booleanValue()) {
                    if (t == null) {
                        t = next;
                    }
                    if (secondaryCondition.invoke(next).booleanValue()) {
                        return next;
                    }
                }
            }
            return t;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean signaledFence$lambda$14(Entry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        SyncFenceCompat releaseFence = entry.getReleaseFence();
        return releaseFence == null || releaseFence.getSignalTimeNanos() != Long.MAX_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isAvailable$lambda$15(Entry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        return entry.isAvailable();
    }
}
