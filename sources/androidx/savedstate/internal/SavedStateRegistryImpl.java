package androidx.savedstate.internal;

import android.os.Bundle;
import androidx.core.os.BundleKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.SavedStateReader;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.SavedStateWriter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SavedStateRegistryImpl.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\b\u0000\u0018\u0000 -2\u00020\u0001:\u0001-B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u001d\u0010\u001e\u001a\n\u0018\u00010\u0014j\u0004\u0018\u0001`\u00152\u0006\u0010\u001f\u001a\u00020\u000fH\u0007¢\u0006\u0002\u0010 J\u0018\u0010!\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u0010H\u0007J\u0010\u0010#\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001f\u001a\u00020\u000fJ\u0010\u0010$\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u000fH\u0007J\b\u0010%\u001a\u00020\u0006H\u0007J\u001f\u0010&\u001a\u00020\u00062\u000e\u0010'\u001a\n\u0018\u00010\u0014j\u0004\u0018\u0001`\u0015H\u0001¢\u0006\u0004\b(\u0010)J\u001b\u0010*\u001a\u00020\u00062\n\u0010+\u001a\u00060\u0014j\u0002`\u0015H\u0001¢\u0006\u0004\b,\u0010)R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0013\u001a\n\u0018\u00010\u0014j\u0004\u0018\u0001`\u0015X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0016R \u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00128G@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0012X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0019\"\u0004\b\u001c\u0010\u001d¨\u0006."}, d2 = {"Landroidx/savedstate/internal/SavedStateRegistryImpl;", "", "owner", "Landroidx/savedstate/SavedStateRegistryOwner;", "onAttach", "Lkotlin/Function0;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/savedstate/SavedStateRegistryOwner;Lkotlin/jvm/functions/Function0;)V", "getOnAttach$savedstate_release", "()Lkotlin/jvm/functions/Function0;", "lock", "Landroidx/savedstate/internal/SynchronizedObject;", "keyToProviders", "", "", "Landroidx/savedstate/SavedStateRegistry$SavedStateProvider;", "attached", "", "restoredState", "Landroid/os/Bundle;", "Landroidx/savedstate/SavedState;", "Landroid/os/Bundle;", "value", "isRestored", "()Z", "isAllowingSavingState", "isAllowingSavingState$savedstate_release", "setAllowingSavingState$savedstate_release", "(Z)V", "consumeRestoredStateForKey", "key", "(Ljava/lang/String;)Landroid/os/Bundle;", "registerSavedStateProvider", "provider", "getSavedStateProvider", "unregisterSavedStateProvider", "performAttach", "performRestore", "savedState", "performRestore$savedstate_release", "(Landroid/os/Bundle;)V", "performSave", "outBundle", "performSave$savedstate_release", "Companion", "savedstate_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class SavedStateRegistryImpl {
    private static final Companion Companion = new Companion(null);
    private static final String SAVED_COMPONENTS_KEY = "androidx.lifecycle.BundlableSavedStateRegistry.key";
    private boolean attached;
    private boolean isAllowingSavingState;
    private boolean isRestored;
    private final Map<String, SavedStateRegistry.SavedStateProvider> keyToProviders;
    private final SynchronizedObject lock;
    private final Function0<Unit> onAttach;
    private final SavedStateRegistryOwner owner;
    private Bundle restoredState;

    public SavedStateRegistryImpl(SavedStateRegistryOwner owner, Function0<Unit> onAttach) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(onAttach, "onAttach");
        this.owner = owner;
        this.onAttach = onAttach;
        this.lock = new SynchronizedObject();
        this.keyToProviders = new LinkedHashMap();
        this.isAllowingSavingState = true;
    }

    public /* synthetic */ SavedStateRegistryImpl(SavedStateRegistryOwner savedStateRegistryOwner, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(savedStateRegistryOwner, (i & 2) != 0 ? new Function0() { // from class: androidx.savedstate.internal.SavedStateRegistryImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Unit.INSTANCE;
            }
        } : function0);
    }

    public final Function0<Unit> getOnAttach$savedstate_release() {
        return this.onAttach;
    }

    /* JADX INFO: renamed from: isRestored, reason: from getter */
    public final boolean getIsRestored() {
        return this.isRestored;
    }

    /* JADX INFO: renamed from: isAllowingSavingState$savedstate_release, reason: from getter */
    public final boolean getIsAllowingSavingState() {
        return this.isAllowingSavingState;
    }

    public final void setAllowingSavingState$savedstate_release(boolean z) {
        this.isAllowingSavingState = z;
    }

    public final Bundle consumeRestoredStateForKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (!this.isRestored) {
            throw new IllegalStateException("You can 'consumeRestoredStateForKey' only after the corresponding component has moved to the 'CREATED' state".toString());
        }
        Bundle bundle = this.restoredState;
        if (bundle == null) {
            return null;
        }
        Bundle bundleM147constructorimpl = SavedStateReader.m147constructorimpl(bundle);
        Bundle bundleM204getSavedStateimpl = SavedStateReader.m148containsimpl(bundleM147constructorimpl, key) ? SavedStateReader.m204getSavedStateimpl(bundleM147constructorimpl, key) : null;
        SavedStateWriter.m269removeimpl(SavedStateWriter.m233constructorimpl(bundle), key);
        if (SavedStateReader.m225isEmptyimpl(SavedStateReader.m147constructorimpl(bundle))) {
            this.restoredState = null;
        }
        return bundleM204getSavedStateimpl;
    }

    public final void registerSavedStateProvider(String key, SavedStateRegistry.SavedStateProvider provider) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(provider, "provider");
        synchronized (this.lock) {
            if (!this.keyToProviders.containsKey(key)) {
                this.keyToProviders.put(key, provider);
                Unit unit = Unit.INSTANCE;
            } else {
                throw new IllegalArgumentException("SavedStateProvider with the given key is already registered".toString());
            }
        }
    }

    public final SavedStateRegistry.SavedStateProvider getSavedStateProvider(String key) {
        SavedStateRegistry.SavedStateProvider savedStateProvider;
        Intrinsics.checkNotNullParameter(key, "key");
        synchronized (this.lock) {
            Iterator it = this.keyToProviders.entrySet().iterator();
            do {
                savedStateProvider = null;
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                String str = (String) entry.getKey();
                SavedStateRegistry.SavedStateProvider savedStateProvider2 = (SavedStateRegistry.SavedStateProvider) entry.getValue();
                if (Intrinsics.areEqual(str, key)) {
                    savedStateProvider = savedStateProvider2;
                }
            } while (savedStateProvider == null);
        }
        return savedStateProvider;
    }

    public final void unregisterSavedStateProvider(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        synchronized (this.lock) {
        }
    }

    public final void performAttach() {
        if (this.owner.getLifecycle().getState() != Lifecycle.State.INITIALIZED) {
            throw new IllegalStateException("Restarter must be created only during owner's initialization stage".toString());
        }
        if (this.attached) {
            throw new IllegalStateException("SavedStateRegistry was already attached.".toString());
        }
        this.onAttach.invoke();
        this.owner.getLifecycle().addObserver(new LifecycleEventObserver() { // from class: androidx.savedstate.internal.SavedStateRegistryImpl$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                SavedStateRegistryImpl.performAttach$lambda$12(this.f$0, lifecycleOwner, event);
            }
        });
        this.attached = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void performAttach$lambda$12(SavedStateRegistryImpl savedStateRegistryImpl, LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "<unused var>");
        Intrinsics.checkNotNullParameter(event, "event");
        if (event == Lifecycle.Event.ON_START) {
            savedStateRegistryImpl.isAllowingSavingState = true;
        } else if (event == Lifecycle.Event.ON_STOP) {
            savedStateRegistryImpl.isAllowingSavingState = false;
        }
    }

    public final void performRestore$savedstate_release(Bundle savedState) {
        if (!this.attached) {
            performAttach();
        }
        if (this.owner.getLifecycle().getState().isAtLeast(Lifecycle.State.STARTED)) {
            throw new IllegalStateException(("performRestore cannot be called when owner is " + this.owner.getLifecycle().getState()).toString());
        }
        if (this.isRestored) {
            throw new IllegalStateException("SavedStateRegistry was already restored.".toString());
        }
        Bundle bundleM204getSavedStateimpl = null;
        if (savedState != null) {
            Bundle bundleM147constructorimpl = SavedStateReader.m147constructorimpl(savedState);
            if (SavedStateReader.m148containsimpl(bundleM147constructorimpl, SAVED_COMPONENTS_KEY)) {
                bundleM204getSavedStateimpl = SavedStateReader.m204getSavedStateimpl(bundleM147constructorimpl, SAVED_COMPONENTS_KEY);
            }
        }
        this.restoredState = bundleM204getSavedStateimpl;
        this.isRestored = true;
    }

    /* JADX INFO: compiled from: SavedStateRegistryImpl.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Landroidx/savedstate/internal/SavedStateRegistryImpl$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SAVED_COMPONENTS_KEY", "", "savedstate_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final void performSave$savedstate_release(Bundle outBundle) {
        Pair[] pairArr;
        Intrinsics.checkNotNullParameter(outBundle, "outBundle");
        Map mapEmptyMap = MapsKt.emptyMap();
        if (mapEmptyMap.isEmpty()) {
            pairArr = new Pair[0];
        } else {
            ArrayList arrayList = new ArrayList(mapEmptyMap.size());
            for (Map.Entry entry : mapEmptyMap.entrySet()) {
                arrayList.add(TuplesKt.to((String) entry.getKey(), entry.getValue()));
            }
            pairArr = (Pair[]) arrayList.toArray(new Pair[0]);
        }
        Bundle bundleBundleOf = BundleKt.bundleOf((Pair[]) Arrays.copyOf(pairArr, pairArr.length));
        Bundle bundleM233constructorimpl = SavedStateWriter.m233constructorimpl(bundleBundleOf);
        Bundle bundle = this.restoredState;
        if (bundle != null) {
            SavedStateWriter.m237putAllimpl(bundleM233constructorimpl, bundle);
        }
        synchronized (this.lock) {
            for (Map.Entry entry2 : this.keyToProviders.entrySet()) {
                SavedStateWriter.m260putSavedStateimpl(bundleM233constructorimpl, (String) entry2.getKey(), ((SavedStateRegistry.SavedStateProvider) entry2.getValue()).saveState());
            }
            Unit unit = Unit.INSTANCE;
        }
        if (SavedStateReader.m225isEmptyimpl(SavedStateReader.m147constructorimpl(bundleBundleOf))) {
            return;
        }
        SavedStateWriter.m260putSavedStateimpl(SavedStateWriter.m233constructorimpl(outBundle), SAVED_COMPONENTS_KEY, bundleBundleOf);
    }
}
