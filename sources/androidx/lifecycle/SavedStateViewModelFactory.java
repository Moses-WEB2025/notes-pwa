package androidx.lifecycle;

import android.app.Application;
import android.os.Bundle;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import java.lang.reflect.Constructor;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: SavedStateViewModelFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\t\b\u0016¢\u0006\u0004\b\u0003\u0010\u0004B\u001b\b\u0016\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\u0003\u0010\tB%\b\u0017\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0004\b\u0003\u0010\fJ-\u0010\u0012\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0013*\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00130\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016¢\u0006\u0002\u0010\u0019J-\u0010\u0012\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0013*\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00130\u001a2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016¢\u0006\u0002\u0010\u001bJ+\u0010\u0012\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0013*\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00130\u001a¢\u0006\u0002\u0010\u001eJ%\u0010\u0012\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0013*\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00130\u001aH\u0016¢\u0006\u0002\u0010\u001fJ\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0014H\u0017R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Landroidx/lifecycle/SavedStateViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$OnRequeryFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "application", "Landroid/app/Application;", "owner", "Landroidx/savedstate/SavedStateRegistryOwner;", "(Landroid/app/Application;Landroidx/savedstate/SavedStateRegistryOwner;)V", "defaultArgs", "Landroid/os/Bundle;", "(Landroid/app/Application;Landroidx/savedstate/SavedStateRegistryOwner;Landroid/os/Bundle;)V", "factory", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "savedStateRegistry", "Landroidx/savedstate/SavedStateRegistry;", "create", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Lkotlin/reflect/KClass;", SecureDatabaseHelper.TABLE_EXTRAS, "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Lkotlin/reflect/KClass;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "Ljava/lang/Class;", "(Ljava/lang/Class;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "key", "", "(Ljava/lang/String;Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "onRequery", "", "viewModel", "lifecycle-viewmodel-savedstate_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class SavedStateViewModelFactory extends ViewModelProvider.OnRequeryFactory implements ViewModelProvider.Factory {
    private Application application;
    private Bundle defaultArgs;
    private final ViewModelProvider.Factory factory;
    private Lifecycle lifecycle;
    private SavedStateRegistry savedStateRegistry;

    public SavedStateViewModelFactory() {
        this.factory = new ViewModelProvider.AndroidViewModelFactory();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner owner) {
        this(application, owner, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner owner, Bundle bundle) {
        ViewModelProvider.AndroidViewModelFactory androidViewModelFactory;
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.savedStateRegistry = owner.getSavedStateRegistry();
        this.lifecycle = owner.getLifecycle();
        this.defaultArgs = bundle;
        this.application = application;
        if (application != null) {
            androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.INSTANCE.getInstance(application);
        } else {
            androidViewModelFactory = new ViewModelProvider.AndroidViewModelFactory();
        }
        this.factory = androidViewModelFactory;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(KClass<T> modelClass, CreationExtras extras) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        Intrinsics.checkNotNullParameter(extras, "extras");
        return (T) create(JvmClassMappingKt.getJavaClass((KClass) modelClass), extras);
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        Intrinsics.checkNotNullParameter(extras, "extras");
        String str = (String) extras.get(ViewModelProvider.VIEW_MODEL_KEY);
        if (str == null) {
            throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        }
        if (extras.get(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY) != null && extras.get(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY) != null) {
            Application application = (Application) extras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
            boolean zIsAssignableFrom = AndroidViewModel.class.isAssignableFrom(modelClass);
            Constructor constructorFindMatchingConstructor = (!zIsAssignableFrom || application == null) ? SavedStateViewModelFactory_androidKt.findMatchingConstructor(modelClass, SavedStateViewModelFactory_androidKt.VIEWMODEL_SIGNATURE) : SavedStateViewModelFactory_androidKt.findMatchingConstructor(modelClass, SavedStateViewModelFactory_androidKt.ANDROID_VIEWMODEL_SIGNATURE);
            if (constructorFindMatchingConstructor == null) {
                return (T) this.factory.create(modelClass, extras);
            }
            if (zIsAssignableFrom && application != null) {
                return (T) SavedStateViewModelFactory_androidKt.newInstance(modelClass, constructorFindMatchingConstructor, application, SavedStateHandleSupport.createSavedStateHandle(extras));
            }
            return (T) SavedStateViewModelFactory_androidKt.newInstance(modelClass, constructorFindMatchingConstructor, SavedStateHandleSupport.createSavedStateHandle(extras));
        }
        if (this.lifecycle != null) {
            return (T) create(str, modelClass);
        }
        throw new IllegalStateException("SAVED_STATE_REGISTRY_OWNER_KEY andVIEW_MODEL_STORE_OWNER_KEY must be provided in the creation extras tosuccessfully create a ViewModel.");
    }

    public final <T extends ViewModel> T create(String key, Class<T> modelClass) {
        T t;
        Application application;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        Lifecycle lifecycle = this.lifecycle;
        if (lifecycle == null) {
            throw new UnsupportedOperationException("SavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
        }
        boolean zIsAssignableFrom = AndroidViewModel.class.isAssignableFrom(modelClass);
        Constructor constructorFindMatchingConstructor = (!zIsAssignableFrom || this.application == null) ? SavedStateViewModelFactory_androidKt.findMatchingConstructor(modelClass, SavedStateViewModelFactory_androidKt.VIEWMODEL_SIGNATURE) : SavedStateViewModelFactory_androidKt.findMatchingConstructor(modelClass, SavedStateViewModelFactory_androidKt.ANDROID_VIEWMODEL_SIGNATURE);
        if (constructorFindMatchingConstructor == null) {
            return this.application != null ? (T) this.factory.create(modelClass) : (T) ViewModelProvider.NewInstanceFactory.INSTANCE.getInstance().create(modelClass);
        }
        SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        Intrinsics.checkNotNull(savedStateRegistry);
        SavedStateHandleController savedStateHandleControllerCreate = LegacySavedStateHandleController.create(savedStateRegistry, lifecycle, key, this.defaultArgs);
        if (zIsAssignableFrom && (application = this.application) != null) {
            Intrinsics.checkNotNull(application);
            t = (T) SavedStateViewModelFactory_androidKt.newInstance(modelClass, constructorFindMatchingConstructor, application, savedStateHandleControllerCreate.getHandle());
        } else {
            t = (T) SavedStateViewModelFactory_androidKt.newInstance(modelClass, constructorFindMatchingConstructor, savedStateHandleControllerCreate.getHandle());
        }
        t.addCloseable(LegacySavedStateHandleController.TAG_SAVED_STATE_HANDLE_CONTROLLER, savedStateHandleControllerCreate);
        return t;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> modelClass) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return (T) create(canonicalName, modelClass);
    }

    @Override // androidx.lifecycle.ViewModelProvider.OnRequeryFactory
    public void onRequery(ViewModel viewModel) {
        Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        if (this.lifecycle != null) {
            SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
            Intrinsics.checkNotNull(savedStateRegistry);
            Lifecycle lifecycle = this.lifecycle;
            Intrinsics.checkNotNull(lifecycle);
            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, savedStateRegistry, lifecycle);
        }
    }
}
