package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.internal.ViewModelProviders;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: InitializerViewModelFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B#\u0012\u001a\u0010\u0002\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\"\u0006\u0012\u0002\b\u00030\u0004¢\u0006\u0004\b\u0005\u0010\u0006J-\u0010\b\u001a\u0002H\t\"\b\b\u0000\u0010\t*\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010\u000fR\u001c\u0010\u0002\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007¨\u0006\u0010"}, d2 = {"Landroidx/lifecycle/viewmodel/InitializerViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "initializers", "", "Landroidx/lifecycle/viewmodel/ViewModelInitializer;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "([Landroidx/lifecycle/viewmodel/ViewModelInitializer;)V", "[Landroidx/lifecycle/viewmodel/ViewModelInitializer;", "create", "VM", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", SecureDatabaseHelper.TABLE_EXTRAS, "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Ljava/lang/Class;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class InitializerViewModelFactory implements ViewModelProvider.Factory {
    private final ViewModelInitializer<?>[] initializers;

    public InitializerViewModelFactory(ViewModelInitializer<?>... initializers) {
        Intrinsics.checkNotNullParameter(initializers, "initializers");
        this.initializers = initializers;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <VM extends ViewModel> VM create(Class<VM> modelClass, CreationExtras extras) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        Intrinsics.checkNotNullParameter(extras, "extras");
        ViewModelProviders viewModelProviders = ViewModelProviders.INSTANCE;
        KClass<VM> kotlinClass = JvmClassMappingKt.getKotlinClass(modelClass);
        ViewModelInitializer<?>[] viewModelInitializerArr = this.initializers;
        return (VM) viewModelProviders.createViewModelFromInitializers$lifecycle_viewmodel_release(kotlinClass, extras, (ViewModelInitializer[]) Arrays.copyOf(viewModelInitializerArr, viewModelInitializerArr.length));
    }
}
