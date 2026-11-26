package androidx.lifecycle.viewmodel.internal;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: DefaultViewModelProviderFactory.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J-\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\b2\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"Landroidx/lifecycle/viewmodel/internal/DefaultViewModelProviderFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "create", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Lkotlin/reflect/KClass;", SecureDatabaseHelper.TABLE_EXTRAS, "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Lkotlin/reflect/KClass;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class DefaultViewModelProviderFactory implements ViewModelProvider.Factory {
    public static final DefaultViewModelProviderFactory INSTANCE = new DefaultViewModelProviderFactory();

    private DefaultViewModelProviderFactory() {
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(KClass<T> modelClass, CreationExtras extras) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        Intrinsics.checkNotNullParameter(extras, "extras");
        return (T) JvmViewModelProviders.INSTANCE.createViewModel(JvmClassMappingKt.getJavaClass((KClass) modelClass));
    }
}
