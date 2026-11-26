package androidx.lifecycle;

import android.app.Application;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.lifecycle.viewmodel.internal.DefaultViewModelProviderFactory;
import androidx.lifecycle.viewmodel.internal.JvmViewModelProviders;
import androidx.lifecycle.viewmodel.internal.ViewModelProviders;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: ViewModelProvider.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0016\u0018\u0000 !2\u00020\u0001:\u0005\u001d\u001e\u001f !B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B#\b\u0017\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\u0004\u0010\fB\u0011\b\u0016\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0004\b\u0004\u0010\u000fB\u0019\b\u0016\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\u0004\u0010\u0010J&\u0010\u0011\u001a\u0002H\u0012\"\b\b\u0000\u0010\u0012*\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0015H\u0087\u0002¢\u0006\u0002\u0010\u0016J&\u0010\u0011\u001a\u0002H\u0012\"\b\b\u0000\u0010\u0012*\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0017H\u0096\u0002¢\u0006\u0002\u0010\u0018J.\u0010\u0011\u001a\u0002H\u0012\"\b\b\u0000\u0010\u0012*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0015H\u0087\u0002¢\u0006\u0002\u0010\u001bJ.\u0010\u0011\u001a\u0002H\u0012\"\b\b\u0000\u0010\u0012*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0017H\u0096\u0002¢\u0006\u0002\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Landroidx/lifecycle/ViewModelProvider;", "", "impl", "Landroidx/lifecycle/viewmodel/ViewModelProviderImpl;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/lifecycle/viewmodel/ViewModelProviderImpl;)V", "store", "Landroidx/lifecycle/ViewModelStore;", "factory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "defaultCreationExtras", "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Landroidx/lifecycle/ViewModelStore;Landroidx/lifecycle/ViewModelProvider$Factory;Landroidx/lifecycle/viewmodel/CreationExtras;)V", "owner", "Landroidx/lifecycle/ViewModelStoreOwner;", "(Landroidx/lifecycle/ViewModelStoreOwner;)V", "(Landroidx/lifecycle/ViewModelStoreOwner;Landroidx/lifecycle/ViewModelProvider$Factory;)V", CallMethod.METHOD_GET, ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;)Landroidx/lifecycle/ViewModel;", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "key", "", "(Ljava/lang/String;Lkotlin/reflect/KClass;)Landroidx/lifecycle/ViewModel;", "(Ljava/lang/String;Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "Factory", "OnRequeryFactory", "NewInstanceFactory", "AndroidViewModelFactory", "Companion", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public class ViewModelProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final CreationExtras.Key<String> VIEW_MODEL_KEY;
    private final ViewModelProviderImpl impl;

    /* JADX INFO: compiled from: ViewModelProvider.android.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$OnRequeryFactory;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onRequery", "", "viewModel", "Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static class OnRequeryFactory {
        public void onRequery(ViewModel viewModel) {
            Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewModelProvider(ViewModelStore store, Factory factory) {
        this(store, factory, null, 4, null);
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
    }

    @JvmStatic
    public static final ViewModelProvider create(ViewModelStore viewModelStore, Factory factory, CreationExtras creationExtras) {
        return INSTANCE.create(viewModelStore, factory, creationExtras);
    }

    @JvmStatic
    public static final ViewModelProvider create(ViewModelStoreOwner viewModelStoreOwner, Factory factory, CreationExtras creationExtras) {
        return INSTANCE.create(viewModelStoreOwner, factory, creationExtras);
    }

    private ViewModelProvider(ViewModelProviderImpl viewModelProviderImpl) {
        this.impl = viewModelProviderImpl;
    }

    public /* synthetic */ ViewModelProvider(ViewModelStore viewModelStore, Factory factory, CreationExtras.Empty empty, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(viewModelStore, factory, (i & 4) != 0 ? CreationExtras.Empty.INSTANCE : empty);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewModelProvider(ViewModelStore store, Factory factory, CreationExtras defaultCreationExtras) {
        this(new ViewModelProviderImpl(store, factory, defaultCreationExtras));
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(defaultCreationExtras, "defaultCreationExtras");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewModelProvider(ViewModelStoreOwner owner) {
        this(owner.getViewModelStore(), ViewModelProviders.INSTANCE.getDefaultFactory$lifecycle_viewmodel_release(owner), ViewModelProviders.INSTANCE.getDefaultCreationExtras$lifecycle_viewmodel_release(owner));
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewModelProvider(ViewModelStoreOwner owner, Factory factory) {
        this(owner.getViewModelStore(), factory, ViewModelProviders.INSTANCE.getDefaultCreationExtras$lifecycle_viewmodel_release(owner));
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(factory, "factory");
    }

    public final <T extends ViewModel> T get(KClass<T> modelClass) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        return (T) ViewModelProviderImpl.getViewModel$lifecycle_viewmodel_release$default(this.impl, modelClass, null, 2, null);
    }

    /* JADX INFO: compiled from: ViewModelProvider.android.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007J$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007R\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "create", "Landroidx/lifecycle/ViewModelProvider;", "owner", "Landroidx/lifecycle/ViewModelStoreOwner;", "factory", "Landroidx/lifecycle/ViewModelProvider$Factory;", SecureDatabaseHelper.TABLE_EXTRAS, "Landroidx/lifecycle/viewmodel/CreationExtras;", "store", "Landroidx/lifecycle/ViewModelStore;", "VIEW_MODEL_KEY", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ ViewModelProvider create$default(Companion companion, ViewModelStoreOwner viewModelStoreOwner, Factory factory, CreationExtras creationExtras, int i, Object obj) {
            if ((i & 2) != 0) {
                factory = ViewModelProviders.INSTANCE.getDefaultFactory$lifecycle_viewmodel_release(viewModelStoreOwner);
            }
            if ((i & 4) != 0) {
                creationExtras = ViewModelProviders.INSTANCE.getDefaultCreationExtras$lifecycle_viewmodel_release(viewModelStoreOwner);
            }
            return companion.create(viewModelStoreOwner, factory, creationExtras);
        }

        public static /* synthetic */ ViewModelProvider create$default(Companion companion, ViewModelStore viewModelStore, Factory factory, CreationExtras creationExtras, int i, Object obj) {
            if ((i & 2) != 0) {
                factory = DefaultViewModelProviderFactory.INSTANCE;
            }
            if ((i & 4) != 0) {
                creationExtras = CreationExtras.Empty.INSTANCE;
            }
            return companion.create(viewModelStore, factory, creationExtras);
        }

        private Companion() {
        }

        @JvmStatic
        public final ViewModelProvider create(ViewModelStoreOwner owner, Factory factory, CreationExtras extras) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(factory, "factory");
            Intrinsics.checkNotNullParameter(extras, "extras");
            return new ViewModelProvider(owner.getViewModelStore(), factory, extras);
        }

        @JvmStatic
        public final ViewModelProvider create(ViewModelStore store, Factory factory, CreationExtras extras) {
            Intrinsics.checkNotNullParameter(store, "store");
            Intrinsics.checkNotNullParameter(factory, "factory");
            Intrinsics.checkNotNullParameter(extras, "extras");
            return new ViewModelProvider(store, factory, extras);
        }
    }

    public <T extends ViewModel> T get(Class<T> modelClass) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        return (T) get(JvmClassMappingKt.getKotlinClass(modelClass));
    }

    public final <T extends ViewModel> T get(String key, KClass<T> modelClass) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        return (T) this.impl.getViewModel$lifecycle_viewmodel_release(modelClass, key);
    }

    public <T extends ViewModel> T get(String key, Class<T> modelClass) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        return (T) this.impl.getViewModel$lifecycle_viewmodel_release(JvmClassMappingKt.getKotlinClass(modelClass), key);
    }

    /* JADX INFO: compiled from: ViewModelProvider.android.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \r2\u00020\u0001:\u0001\rJ%\u0010\u0002\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0006H\u0016¢\u0006\u0002\u0010\u0007J-\u0010\u0002\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00062\u0006\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\nJ-\u0010\u0002\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u000b2\u0006\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$Factory;", "", "create", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", SecureDatabaseHelper.TABLE_EXTRAS, "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Ljava/lang/Class;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "Companion", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public interface Factory {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = Companion.$$INSTANCE;

        @JvmStatic
        static Factory from(ViewModelInitializer<?>... viewModelInitializerArr) {
            return INSTANCE.from(viewModelInitializerArr);
        }

        default <T extends ViewModel> T create(Class<T> modelClass) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            return (T) ViewModelProviders.INSTANCE.unsupportedCreateViewModel$lifecycle_viewmodel_release();
        }

        default <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            Intrinsics.checkNotNullParameter(extras, "extras");
            return (T) create(modelClass);
        }

        default <T extends ViewModel> T create(KClass<T> modelClass, CreationExtras extras) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            Intrinsics.checkNotNullParameter(extras, "extras");
            return (T) create(JvmClassMappingKt.getJavaClass((KClass) modelClass), extras);
        }

        /* JADX INFO: compiled from: ViewModelProvider.android.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J)\u0010\u0004\u001a\u00020\u00052\u001a\u0010\u0006\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\b0\u0007\"\u0006\u0012\u0002\b\u00030\bH\u0007¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$Factory$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "from", "Landroidx/lifecycle/ViewModelProvider$Factory;", "initializers", "", "Landroidx/lifecycle/viewmodel/ViewModelInitializer;", "([Landroidx/lifecycle/viewmodel/ViewModelInitializer;)Landroidx/lifecycle/ViewModelProvider$Factory;", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
        public static final class Companion {
            static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            @JvmStatic
            public final Factory from(ViewModelInitializer<?>... initializers) {
                Intrinsics.checkNotNullParameter(initializers, "initializers");
                return ViewModelProviders.INSTANCE.createInitializerFactory$lifecycle_viewmodel_release((ViewModelInitializer<?>[]) Arrays.copyOf(initializers, initializers.length));
            }
        }
    }

    /* JADX INFO: compiled from: ViewModelProvider.android.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J%\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\bH\u0016¢\u0006\u0002\u0010\tJ-\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\fJ-\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\u000e¨\u0006\u0010"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "create", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", SecureDatabaseHelper.TABLE_EXTRAS, "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Ljava/lang/Class;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "Companion", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static class NewInstanceFactory implements Factory {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        public static final CreationExtras.Key<String> VIEW_MODEL_KEY = ViewModelProvider.VIEW_MODEL_KEY;
        private static NewInstanceFactory _instance;

        public static final NewInstanceFactory getInstance() {
            return INSTANCE.getInstance();
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> modelClass) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            return (T) JvmViewModelProviders.INSTANCE.createViewModel(modelClass);
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            Intrinsics.checkNotNullParameter(extras, "extras");
            return (T) create(modelClass);
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(KClass<T> modelClass, CreationExtras extras) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            Intrinsics.checkNotNullParameter(extras, "extras");
            return (T) create(JvmClassMappingKt.getJavaClass((KClass) modelClass), extras);
        }

        /* JADX INFO: compiled from: ViewModelProvider.android.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u00058GX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0007\u0010\u0003\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_instance", "Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory;", "instance", "getInstance$annotations", "getInstance", "()Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory;", "VIEW_MODEL_KEY", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @JvmStatic
            public static /* synthetic */ void getInstance$annotations() {
            }

            private Companion() {
            }

            public final NewInstanceFactory getInstance() {
                if (NewInstanceFactory._instance == null) {
                    NewInstanceFactory._instance = new NewInstanceFactory();
                }
                NewInstanceFactory newInstanceFactory = NewInstanceFactory._instance;
                Intrinsics.checkNotNull(newInstanceFactory);
                return newInstanceFactory;
            }
        }
    }

    /* JADX INFO: compiled from: ViewModelProvider.android.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u001b\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007B\t\b\u0016¢\u0006\u0004\b\u0006\u0010\bB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\tJ-\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010\u0011J%\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000eH\u0016¢\u0006\u0002\u0010\u0012J-\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000e2\u0006\u0010\u0013\u001a\u00020\u0003H\u0002¢\u0006\u0002\u0010\u0014R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$AndroidViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory;", "application", "Landroid/app/Application;", AppFunctionMetadataKt.APP_FUNCTION_ID_EMPTY, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/app/Application;I)V", "()V", "(Landroid/app/Application;)V", "create", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", SecureDatabaseHelper.TABLE_EXTRAS, "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Ljava/lang/Class;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "app", "(Ljava/lang/Class;Landroid/app/Application;)Landroidx/lifecycle/ViewModel;", "Companion", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static class AndroidViewModelFactory extends NewInstanceFactory {
        public static final CreationExtras.Key<Application> APPLICATION_KEY;

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static AndroidViewModelFactory _instance;
        private final Application application;

        @JvmStatic
        public static final AndroidViewModelFactory getInstance(Application application) {
            return INSTANCE.getInstance(application);
        }

        private AndroidViewModelFactory(Application application, int i) {
            this.application = application;
        }

        public AndroidViewModelFactory() {
            this(null, 0);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public AndroidViewModelFactory(Application application) {
            this(application, 0);
            Intrinsics.checkNotNullParameter(application, "application");
        }

        @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            Intrinsics.checkNotNullParameter(extras, "extras");
            if (this.application != null) {
                return (T) create(modelClass);
            }
            Application application = (Application) extras.get(APPLICATION_KEY);
            if (application != null) {
                return (T) create(modelClass, application);
            }
            if (AndroidViewModel.class.isAssignableFrom(modelClass)) {
                throw new IllegalArgumentException("CreationExtras must have an application by `APPLICATION_KEY`");
            }
            return (T) super.create(modelClass);
        }

        @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> modelClass) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            Application application = this.application;
            if (application == null) {
                throw new UnsupportedOperationException("AndroidViewModelFactory constructed with empty constructor works only with create(modelClass: Class<T>, extras: CreationExtras).");
            }
            return (T) create(modelClass, application);
        }

        private final <T extends ViewModel> T create(Class<T> modelClass, Application app) {
            if (AndroidViewModel.class.isAssignableFrom(modelClass)) {
                try {
                    T tNewInstance = modelClass.getConstructor(Application.class).newInstance(app);
                    Intrinsics.checkNotNull(tNewInstance);
                    return tNewInstance;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e);
                } catch (InstantiationException e2) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e2);
                } catch (NoSuchMethodException e3) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e3);
                } catch (InvocationTargetException e4) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e4);
                }
            }
            return (T) super.create(modelClass);
        }

        /* JADX INFO: compiled from: ViewModelProvider.android.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$AndroidViewModelFactory$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_instance", "Landroidx/lifecycle/ViewModelProvider$AndroidViewModelFactory;", "getInstance", "application", "Landroid/app/Application;", "APPLICATION_KEY", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            public final AndroidViewModelFactory getInstance(Application application) {
                Intrinsics.checkNotNullParameter(application, "application");
                if (AndroidViewModelFactory._instance == null) {
                    AndroidViewModelFactory._instance = new AndroidViewModelFactory(application);
                }
                AndroidViewModelFactory androidViewModelFactory = AndroidViewModelFactory._instance;
                Intrinsics.checkNotNull(androidViewModelFactory);
                return androidViewModelFactory;
            }
        }

        static {
            CreationExtras.Companion companion = CreationExtras.INSTANCE;
            APPLICATION_KEY = new CreationExtras.Key<Application>() { // from class: androidx.lifecycle.ViewModelProvider$AndroidViewModelFactory$special$$inlined$Key$1
            };
        }
    }

    static {
        CreationExtras.Companion companion = CreationExtras.INSTANCE;
        VIEW_MODEL_KEY = new CreationExtras.Key<String>() { // from class: androidx.lifecycle.ViewModelProvider$special$$inlined$Key$1
        };
    }
}
