package androidx.lifecycle.viewmodel;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CreationExtras.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B#\b\u0000\u0012\u0018\u0010\u0002\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003¢\u0006\u0004\b\u0006\u0010\u0007B\u0013\b\u0017\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0006\u0010\bJ*\u0010\t\u001a\u00020\n\"\u0004\b\u0000\u0010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u00042\u0006\u0010\r\u001a\u0002H\u000bH\u0086\u0002¢\u0006\u0002\u0010\u000eJ$\u0010\u000f\u001a\u0004\u0018\u0001H\u000b\"\u0004\b\u0000\u0010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0004H\u0096\u0002¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"Landroidx/lifecycle/viewmodel/MutableCreationExtras;", "Landroidx/lifecycle/viewmodel/CreationExtras;", "initialExtras", "", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/Map;)V", "(Landroidx/lifecycle/viewmodel/CreationExtras;)V", "set", "", ExifInterface.GPS_DIRECTION_TRUE, "key", Constants.TIMESTAMP, "(Landroidx/lifecycle/viewmodel/CreationExtras$Key;Ljava/lang/Object;)V", CallMethod.METHOD_GET, "(Landroidx/lifecycle/viewmodel/CreationExtras$Key;)Ljava/lang/Object;", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class MutableCreationExtras extends CreationExtras {
    public MutableCreationExtras() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public MutableCreationExtras(Map<CreationExtras.Key<?>, ? extends Object> initialExtras) {
        Intrinsics.checkNotNullParameter(initialExtras, "initialExtras");
        getExtras$lifecycle_viewmodel_release().putAll(initialExtras);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MutableCreationExtras(CreationExtras initialExtras) {
        this((Map<CreationExtras.Key<?>, ? extends Object>) initialExtras.getExtras$lifecycle_viewmodel_release());
        Intrinsics.checkNotNullParameter(initialExtras, "initialExtras");
    }

    public /* synthetic */ MutableCreationExtras(CreationExtras.Empty empty, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CreationExtras.Empty.INSTANCE : empty);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void set(CreationExtras.Key<T> key, T t) {
        Intrinsics.checkNotNullParameter(key, "key");
        getExtras$lifecycle_viewmodel_release().put(key, t);
    }

    @Override // androidx.lifecycle.viewmodel.CreationExtras
    public <T> T get(CreationExtras.Key<T> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (T) getExtras$lifecycle_viewmodel_release().get(key);
    }
}
