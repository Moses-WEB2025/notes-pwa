package androidx.lifecycle;

import android.os.Bundle;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: SavedStateHandleSupport.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001f\u0010\u0003\u001a\u00020\u0004\"\f\b\u0000\u0010\u0005*\u00020\u0006*\u00020\u0007*\u0002H\u0005H\u0007¢\u0006\u0002\u0010\b\u001a0\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00012\u000e\u0010\u000e\u001a\n\u0018\u00010\u000fj\u0004\u0018\u0001`\u0010H\u0002\u001a\f\u0010\t\u001a\u00020\n*\u00020\u0011H\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u0018\u0010\u0012\u001a\u00020\u0013*\u00020\u00078@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u0018\u0010\u0016\u001a\u00020\u0017*\u00020\u00068@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\"\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u001b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00070\u001b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u001d\u001a\f\u0012\b\u0012\u00060\u000fj\u0002`\u00100\u001b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"VIEWMODEL_KEY", "", "SAVED_STATE_KEY", "enableSavedStateHandles", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/savedstate/SavedStateRegistryOwner;", "Landroidx/lifecycle/ViewModelStoreOwner;", "(Landroidx/savedstate/SavedStateRegistryOwner;)V", "createSavedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "savedStateRegistryOwner", "viewModelStoreOwner", "key", "defaultArgs", "Landroid/os/Bundle;", "Landroidx/savedstate/SavedState;", "Landroidx/lifecycle/viewmodel/CreationExtras;", "savedStateHandlesVM", "Landroidx/lifecycle/SavedStateHandlesVM;", "getSavedStateHandlesVM", "(Landroidx/lifecycle/ViewModelStoreOwner;)Landroidx/lifecycle/SavedStateHandlesVM;", "savedStateHandlesProvider", "Landroidx/lifecycle/SavedStateHandlesProvider;", "getSavedStateHandlesProvider", "(Landroidx/savedstate/SavedStateRegistryOwner;)Landroidx/lifecycle/SavedStateHandlesProvider;", "SAVED_STATE_REGISTRY_OWNER_KEY", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "VIEW_MODEL_STORE_OWNER_KEY", "DEFAULT_ARGS_KEY", "lifecycle-viewmodel-savedstate_release"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class SavedStateHandleSupport {
    public static final CreationExtras.Key<Bundle> DEFAULT_ARGS_KEY;
    public static final String SAVED_STATE_KEY = "androidx.lifecycle.internal.SavedStateHandlesProvider";
    public static final CreationExtras.Key<SavedStateRegistryOwner> SAVED_STATE_REGISTRY_OWNER_KEY;
    public static final String VIEWMODEL_KEY = "androidx.lifecycle.internal.SavedStateHandlesVM";
    public static final CreationExtras.Key<ViewModelStoreOwner> VIEW_MODEL_STORE_OWNER_KEY;

    public static final <T extends SavedStateRegistryOwner & ViewModelStoreOwner> void enableSavedStateHandles(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        LifecycleOwner lifecycleOwner = (LifecycleOwner) t;
        Lifecycle.State state = lifecycleOwner.getLifecycle().getState();
        if (state != Lifecycle.State.INITIALIZED && state != Lifecycle.State.CREATED) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (t.getSavedStateRegistry().getSavedStateProvider(SAVED_STATE_KEY) == null) {
            SavedStateHandlesProvider savedStateHandlesProvider = new SavedStateHandlesProvider(t.getSavedStateRegistry(), t);
            t.getSavedStateRegistry().registerSavedStateProvider(SAVED_STATE_KEY, savedStateHandlesProvider);
            lifecycleOwner.getLifecycle().addObserver(new SavedStateHandleAttacher(savedStateHandlesProvider));
        }
    }

    private static final SavedStateHandle createSavedStateHandle(SavedStateRegistryOwner savedStateRegistryOwner, ViewModelStoreOwner viewModelStoreOwner, String str, Bundle bundle) {
        SavedStateHandlesProvider savedStateHandlesProvider = getSavedStateHandlesProvider(savedStateRegistryOwner);
        SavedStateHandlesVM savedStateHandlesVM = getSavedStateHandlesVM(viewModelStoreOwner);
        SavedStateHandle savedStateHandle = savedStateHandlesVM.getHandles().get(str);
        if (savedStateHandle != null) {
            return savedStateHandle;
        }
        SavedStateHandle savedStateHandleCreateHandle = SavedStateHandle.INSTANCE.createHandle(savedStateHandlesProvider.consumeRestoredStateForKey(str), bundle);
        savedStateHandlesVM.getHandles().put(str, savedStateHandleCreateHandle);
        return savedStateHandleCreateHandle;
    }

    public static final SavedStateHandle createSavedStateHandle(CreationExtras creationExtras) {
        Intrinsics.checkNotNullParameter(creationExtras, "<this>");
        SavedStateRegistryOwner savedStateRegistryOwner = (SavedStateRegistryOwner) creationExtras.get(SAVED_STATE_REGISTRY_OWNER_KEY);
        if (savedStateRegistryOwner == null) {
            throw new IllegalArgumentException("CreationExtras must have a value by `SAVED_STATE_REGISTRY_OWNER_KEY`");
        }
        ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) creationExtras.get(VIEW_MODEL_STORE_OWNER_KEY);
        if (viewModelStoreOwner == null) {
            throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_STORE_OWNER_KEY`");
        }
        Bundle bundle = (Bundle) creationExtras.get(DEFAULT_ARGS_KEY);
        String str = (String) creationExtras.get(ViewModelProvider.VIEW_MODEL_KEY);
        if (str == null) {
            throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_KEY`");
        }
        return createSavedStateHandle(savedStateRegistryOwner, viewModelStoreOwner, str, bundle);
    }

    public static final SavedStateHandlesVM getSavedStateHandlesVM(ViewModelStoreOwner viewModelStoreOwner) {
        Intrinsics.checkNotNullParameter(viewModelStoreOwner, "<this>");
        return (SavedStateHandlesVM) ViewModelProvider.Companion.create$default(ViewModelProvider.INSTANCE, viewModelStoreOwner, new ViewModelProvider.Factory() { // from class: androidx.lifecycle.SavedStateHandleSupport$savedStateHandlesVM$1
            @Override // androidx.lifecycle.ViewModelProvider.Factory
            public <T extends ViewModel> T create(KClass<T> modelClass, CreationExtras extras) {
                Intrinsics.checkNotNullParameter(modelClass, "modelClass");
                Intrinsics.checkNotNullParameter(extras, "extras");
                return new SavedStateHandlesVM();
            }
        }, (CreationExtras) null, 4, (Object) null).get(VIEWMODEL_KEY, Reflection.getOrCreateKotlinClass(SavedStateHandlesVM.class));
    }

    public static final SavedStateHandlesProvider getSavedStateHandlesProvider(SavedStateRegistryOwner savedStateRegistryOwner) {
        Intrinsics.checkNotNullParameter(savedStateRegistryOwner, "<this>");
        SavedStateRegistry.SavedStateProvider savedStateProvider = savedStateRegistryOwner.getSavedStateRegistry().getSavedStateProvider(SAVED_STATE_KEY);
        SavedStateHandlesProvider savedStateHandlesProvider = savedStateProvider instanceof SavedStateHandlesProvider ? (SavedStateHandlesProvider) savedStateProvider : null;
        if (savedStateHandlesProvider != null) {
            return savedStateHandlesProvider;
        }
        throw new IllegalStateException("enableSavedStateHandles() wasn't called prior to createSavedStateHandle() call");
    }

    static {
        CreationExtras.Companion companion = CreationExtras.INSTANCE;
        SAVED_STATE_REGISTRY_OWNER_KEY = new CreationExtras.Key<SavedStateRegistryOwner>() { // from class: androidx.lifecycle.SavedStateHandleSupport$special$$inlined$Key$1
        };
        CreationExtras.Companion companion2 = CreationExtras.INSTANCE;
        VIEW_MODEL_STORE_OWNER_KEY = new CreationExtras.Key<ViewModelStoreOwner>() { // from class: androidx.lifecycle.SavedStateHandleSupport$special$$inlined$Key$2
        };
        CreationExtras.Companion companion3 = CreationExtras.INSTANCE;
        DEFAULT_ARGS_KEY = new CreationExtras.Key<Bundle>() { // from class: androidx.lifecycle.SavedStateHandleSupport$special$$inlined$Key$3
        };
    }
}
