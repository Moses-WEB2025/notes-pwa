package androidx.fragment.app;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.activity.BackEventCompat;
import androidx.fragment.R;
import ch.qos.logback.core.CoreConstants;
import com.miui.common.stat.ToDoStat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SpecialEffectsController.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\b \u0018\u0000 92\u00020\u0001:\u00049:;<B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0015\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0000¢\u0006\u0002\b\u0012J#\u0010\u0013\u001a\u00020\u00102\u0011\u0010\u0014\u001a\r\u0012\t\u0012\u00070\f¢\u0006\u0002\b\u00160\u00152\u0006\u0010\u0017\u001a\u00020\bH&J \u0010\u0018\u001a\u00020\u00102\u0011\u0010\u0014\u001a\r\u0012\t\u0012\u00070\f¢\u0006\u0002\b\u00160\u0015H\u0010¢\u0006\u0002\b\u0019J\u0006\u0010\u001a\u001a\u00020\u0010J \u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\u0016\u0010\"\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!J\u000e\u0010#\u001a\u00020\u00102\u0006\u0010 \u001a\u00020!J\u000e\u0010$\u001a\u00020\u00102\u0006\u0010 \u001a\u00020!J\u000e\u0010%\u001a\u00020\u00102\u0006\u0010 \u001a\u00020!J\u0006\u0010&\u001a\u00020\u0010J\u0012\u0010'\u001a\u0004\u0018\u00010\f2\u0006\u0010(\u001a\u00020)H\u0002J\u0012\u0010*\u001a\u0004\u0018\u00010\f2\u0006\u0010(\u001a\u00020)H\u0002J\u0006\u0010+\u001a\u00020\u0010J\u0006\u0010,\u001a\u00020\u0010J\u0010\u0010-\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020!J\u0016\u0010.\u001a\u00020\b2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0016\u00100\u001a\u00020\b2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0006\u00101\u001a\u00020\bJ\u0006\u00102\u001a\u00020\u0010J\u000e\u00103\u001a\u00020\u00102\u0006\u00104\u001a\u000205J\u001b\u00106\u001a\u00020\u00102\u0011\u0010\u0014\u001a\r\u0012\t\u0012\u00070\f¢\u0006\u0002\b\u00160\u0015H\u0002J\b\u00107\u001a\u00020\u0010H\u0002J\u000e\u00108\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006="}, d2 = {"Landroidx/fragment/app/SpecialEffectsController;", "", "container", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "getContainer", "()Landroid/view/ViewGroup;", "isContainerPostponed", "", "operationDirectionIsPop", "pendingOperations", "", "Landroidx/fragment/app/SpecialEffectsController$Operation;", "runningNonSeekableTransition", "runningOperations", "applyContainerChangesToOperation", "", "operation", "applyContainerChangesToOperation$fragment_release", "collectEffects", "operations", "", "Lkotlin/jvm/JvmSuppressWildcards;", "isPop", "commitEffects", "commitEffects$fragment_release", "completeBack", "enqueue", "finalState", "Landroidx/fragment/app/SpecialEffectsController$Operation$State;", "lifecycleImpact", "Landroidx/fragment/app/SpecialEffectsController$Operation$LifecycleImpact;", "fragmentStateManager", "Landroidx/fragment/app/FragmentStateManager;", "enqueueAdd", "enqueueHide", "enqueueRemove", "enqueueShow", "executePendingOperations", "findPendingOperation", "fragment", "Landroidx/fragment/app/Fragment;", "findRunningOperation", "forceCompleteAllOperations", "forcePostponedExecutePendingOperations", "getAwaitingCompletionLifecycleImpact", "isOperationSeekable", "newPendingOperations", "isOperationTransitioning", "isPendingExecute", "markPostponedState", "processProgress", "backEvent", "Landroidx/activity/BackEventCompat;", "processStart", "updateFinalState", "updateOperationDirection", "Companion", "Effect", "FragmentStateManagerOperation", "Operation", "fragment_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public abstract class SpecialEffectsController {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ViewGroup container;
    private boolean isContainerPostponed;
    private boolean operationDirectionIsPop;
    private final List<Operation> pendingOperations;
    private boolean runningNonSeekableTransition;
    private final List<Operation> runningOperations;

    /* JADX INFO: compiled from: SpecialEffectsController.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Operation.LifecycleImpact.values().length];
            try {
                iArr[Operation.LifecycleImpact.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @JvmStatic
    public static final SpecialEffectsController getOrCreateController(ViewGroup viewGroup, FragmentManager fragmentManager) {
        return INSTANCE.getOrCreateController(viewGroup, fragmentManager);
    }

    @JvmStatic
    public static final SpecialEffectsController getOrCreateController(ViewGroup viewGroup, SpecialEffectsControllerFactory specialEffectsControllerFactory) {
        return INSTANCE.getOrCreateController(viewGroup, specialEffectsControllerFactory);
    }

    public abstract void collectEffects(List<Operation> operations, boolean isPop);

    public SpecialEffectsController(ViewGroup container) {
        Intrinsics.checkNotNullParameter(container, "container");
        this.container = container;
        this.pendingOperations = new ArrayList();
        this.runningOperations = new ArrayList();
    }

    public final ViewGroup getContainer() {
        return this.container;
    }

    public final Operation.LifecycleImpact getAwaitingCompletionLifecycleImpact(FragmentStateManager fragmentStateManager) {
        Intrinsics.checkNotNullParameter(fragmentStateManager, "fragmentStateManager");
        Fragment fragment = fragmentStateManager.getFragment();
        Intrinsics.checkNotNullExpressionValue(fragment, "fragmentStateManager.fragment");
        Operation operationFindPendingOperation = findPendingOperation(fragment);
        Operation.LifecycleImpact lifecycleImpact = operationFindPendingOperation != null ? operationFindPendingOperation.getLifecycleImpact() : null;
        Operation operationFindRunningOperation = findRunningOperation(fragment);
        Operation.LifecycleImpact lifecycleImpact2 = operationFindRunningOperation != null ? operationFindRunningOperation.getLifecycleImpact() : null;
        int i = lifecycleImpact == null ? -1 : WhenMappings.$EnumSwitchMapping$0[lifecycleImpact.ordinal()];
        return (i == -1 || i == 1) ? lifecycleImpact2 : lifecycleImpact;
    }

    private final Operation findPendingOperation(Fragment fragment) {
        Object next;
        Iterator<T> it = this.pendingOperations.iterator();
        while (it.hasNext()) {
            next = it.next();
            Operation operation = (Operation) next;
            if (Intrinsics.areEqual(operation.getFragment(), fragment) && !operation.getIsCanceled()) {
                return (Operation) next;
            }
        }
        next = null;
        return (Operation) next;
    }

    private final Operation findRunningOperation(Fragment fragment) {
        Object next;
        Iterator<T> it = this.runningOperations.iterator();
        while (it.hasNext()) {
            next = it.next();
            Operation operation = (Operation) next;
            if (Intrinsics.areEqual(operation.getFragment(), fragment) && !operation.getIsCanceled()) {
                return (Operation) next;
            }
        }
        next = null;
        return (Operation) next;
    }

    public final void enqueueAdd(Operation.State finalState, FragmentStateManager fragmentStateManager) {
        Intrinsics.checkNotNullParameter(finalState, "finalState");
        Intrinsics.checkNotNullParameter(fragmentStateManager, "fragmentStateManager");
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(FragmentManager.TAG, "SpecialEffectsController: Enqueuing add operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(finalState, Operation.LifecycleImpact.ADDING, fragmentStateManager);
    }

    public final void enqueueShow(FragmentStateManager fragmentStateManager) {
        Intrinsics.checkNotNullParameter(fragmentStateManager, "fragmentStateManager");
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(FragmentManager.TAG, "SpecialEffectsController: Enqueuing show operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(Operation.State.VISIBLE, Operation.LifecycleImpact.NONE, fragmentStateManager);
    }

    public final void enqueueHide(FragmentStateManager fragmentStateManager) {
        Intrinsics.checkNotNullParameter(fragmentStateManager, "fragmentStateManager");
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(FragmentManager.TAG, "SpecialEffectsController: Enqueuing hide operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(Operation.State.GONE, Operation.LifecycleImpact.NONE, fragmentStateManager);
    }

    public final void enqueueRemove(FragmentStateManager fragmentStateManager) {
        Intrinsics.checkNotNullParameter(fragmentStateManager, "fragmentStateManager");
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(FragmentManager.TAG, "SpecialEffectsController: Enqueuing remove operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(Operation.State.REMOVED, Operation.LifecycleImpact.REMOVING, fragmentStateManager);
    }

    private final void enqueue(Operation.State finalState, Operation.LifecycleImpact lifecycleImpact, FragmentStateManager fragmentStateManager) {
        synchronized (this.pendingOperations) {
            Fragment fragment = fragmentStateManager.getFragment();
            Intrinsics.checkNotNullExpressionValue(fragment, "fragmentStateManager.fragment");
            Operation operationFindPendingOperation = findPendingOperation(fragment);
            if (operationFindPendingOperation == null) {
                if (fragmentStateManager.getFragment().mTransitioning || fragmentStateManager.getFragment().mRemoving) {
                    Fragment fragment2 = fragmentStateManager.getFragment();
                    Intrinsics.checkNotNullExpressionValue(fragment2, "fragmentStateManager.fragment");
                    operationFindPendingOperation = findRunningOperation(fragment2);
                } else {
                    operationFindPendingOperation = null;
                }
            }
            if (operationFindPendingOperation != null) {
                operationFindPendingOperation.mergeWith(finalState, lifecycleImpact);
                return;
            }
            final FragmentStateManagerOperation fragmentStateManagerOperation = new FragmentStateManagerOperation(finalState, lifecycleImpact, fragmentStateManager);
            this.pendingOperations.add(fragmentStateManagerOperation);
            fragmentStateManagerOperation.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.SpecialEffectsController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SpecialEffectsController.enqueue$lambda$4$lambda$2(this.f$0, fragmentStateManagerOperation);
                }
            });
            fragmentStateManagerOperation.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.SpecialEffectsController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SpecialEffectsController.enqueue$lambda$4$lambda$3(this.f$0, fragmentStateManagerOperation);
                }
            });
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void enqueue$lambda$4$lambda$2(SpecialEffectsController this$0, FragmentStateManagerOperation operation) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(operation, "$operation");
        if (this$0.pendingOperations.contains(operation)) {
            Operation.State finalState = operation.getFinalState();
            View view = operation.getFragment().mView;
            Intrinsics.checkNotNullExpressionValue(view, "operation.fragment.mView");
            finalState.applyState(view, this$0.container);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void enqueue$lambda$4$lambda$3(SpecialEffectsController this$0, FragmentStateManagerOperation operation) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(operation, "$operation");
        this$0.pendingOperations.remove(operation);
        this$0.runningOperations.remove(operation);
    }

    public final void updateOperationDirection(boolean isPop) {
        this.operationDirectionIsPop = isPop;
    }

    public final void markPostponedState() {
        Operation operationPrevious;
        synchronized (this.pendingOperations) {
            updateFinalState();
            List<Operation> list = this.pendingOperations;
            ListIterator<Operation> listIterator = list.listIterator(list.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    operationPrevious = null;
                    break;
                }
                operationPrevious = listIterator.previous();
                Operation operation = operationPrevious;
                Operation.State.Companion companion = Operation.State.INSTANCE;
                View view = operation.getFragment().mView;
                Intrinsics.checkNotNullExpressionValue(view, "operation.fragment.mView");
                Operation.State stateAsOperationState = companion.asOperationState(view);
                if (operation.getFinalState() == Operation.State.VISIBLE && stateAsOperationState != Operation.State.VISIBLE) {
                    break;
                }
            }
            Operation operation2 = operationPrevious;
            Fragment fragment = operation2 != null ? operation2.getFragment() : null;
            this.isContainerPostponed = fragment != null ? fragment.isPostponed() : false;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean isPendingExecute() {
        return !this.pendingOperations.isEmpty();
    }

    public final void forcePostponedExecutePendingOperations() {
        if (this.isContainerPostponed) {
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(FragmentManager.TAG, "SpecialEffectsController: Forcing postponed operations");
            }
            this.isContainerPostponed = false;
            executePendingOperations();
        }
    }

    public final void executePendingOperations() {
        boolean z;
        if (this.isContainerPostponed) {
            return;
        }
        if (!this.container.isAttachedToWindow()) {
            forceCompleteAllOperations();
            this.operationDirectionIsPop = false;
            return;
        }
        synchronized (this.pendingOperations) {
            List<Operation> mutableList = CollectionsKt.toMutableList((Collection) this.runningOperations);
            this.runningOperations.clear();
            Iterator it = mutableList.iterator();
            while (true) {
                z = true;
                if (!it.hasNext()) {
                    break;
                }
                Operation operation = (Operation) it.next();
                if (this.pendingOperations.isEmpty() || !operation.getFragment().mTransitioning) {
                    z = false;
                }
                operation.setSeeking$fragment_release(z);
            }
            for (Operation operation2 : mutableList) {
                if (this.runningNonSeekableTransition) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "SpecialEffectsController: Completing non-seekable operation " + operation2);
                    }
                    operation2.complete$fragment_release();
                } else {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "SpecialEffectsController: Cancelling operation " + operation2);
                    }
                    operation2.cancel(this.container);
                }
                this.runningNonSeekableTransition = false;
                if (!operation2.getIsComplete()) {
                    this.runningOperations.add(operation2);
                }
            }
            if (!this.pendingOperations.isEmpty()) {
                updateFinalState();
                List<Operation> mutableList2 = CollectionsKt.toMutableList((Collection) this.pendingOperations);
                if (mutableList2.isEmpty()) {
                    return;
                }
                this.pendingOperations.clear();
                this.runningOperations.addAll(mutableList2);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "SpecialEffectsController: Executing pending operations");
                }
                collectEffects(mutableList2, this.operationDirectionIsPop);
                boolean zIsOperationSeekable = isOperationSeekable(mutableList2);
                boolean zIsOperationTransitioning = isOperationTransitioning(mutableList2);
                if (!zIsOperationTransitioning || zIsOperationSeekable) {
                    z = false;
                }
                this.runningNonSeekableTransition = z;
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "SpecialEffectsController: Operation seekable = " + zIsOperationSeekable + " \ntransition = " + zIsOperationTransitioning);
                }
                if (!zIsOperationTransitioning) {
                    processStart(mutableList2);
                    commitEffects$fragment_release(mutableList2);
                } else if (zIsOperationSeekable) {
                    processStart(mutableList2);
                    int size = mutableList2.size();
                    for (int i = 0; i < size; i++) {
                        applyContainerChangesToOperation$fragment_release(mutableList2.get(i));
                    }
                }
                this.operationDirectionIsPop = false;
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "SpecialEffectsController: Finished executing pending operations");
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final boolean isOperationTransitioning(List<Operation> newPendingOperations) {
        Iterator<T> it = newPendingOperations.iterator();
        boolean z = true;
        while (it.hasNext()) {
            if (!((Operation) it.next()).getFragment().mTransitioning) {
                z = false;
            }
        }
        return z;
    }

    private final boolean isOperationSeekable(List<Operation> newPendingOperations) {
        boolean z;
        List<Operation> list = newPendingOperations;
        Iterator<T> it = list.iterator();
        loop0: while (true) {
            z = true;
            while (true) {
                if (!it.hasNext()) {
                    break loop0;
                }
                Operation operation = (Operation) it.next();
                if (!operation.getEffects$fragment_release().isEmpty()) {
                    List<Effect> effects$fragment_release = operation.getEffects$fragment_release();
                    if (!(effects$fragment_release instanceof Collection) || !effects$fragment_release.isEmpty()) {
                        Iterator<T> it2 = effects$fragment_release.iterator();
                        do {
                            if (!it2.hasNext()) {
                                break;
                            }
                        } while (((Effect) it2.next()).getIsSeekingSupported());
                    } else {
                        break;
                    }
                }
                z = false;
            }
        }
        if (z) {
            ArrayList arrayList = new ArrayList();
            Iterator<T> it3 = list.iterator();
            while (it3.hasNext()) {
                CollectionsKt.addAll(arrayList, ((Operation) it3.next()).getEffects$fragment_release());
            }
            if (!arrayList.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public final void applyContainerChangesToOperation$fragment_release(Operation operation) {
        Intrinsics.checkNotNullParameter(operation, "operation");
        if (operation.getIsAwaitingContainerChanges()) {
            Operation.State finalState = operation.getFinalState();
            View viewRequireView = operation.getFragment().requireView();
            Intrinsics.checkNotNullExpressionValue(viewRequireView, "operation.fragment.requireView()");
            finalState.applyState(viewRequireView, this.container);
            operation.setAwaitingContainerChanges(false);
        }
    }

    public final void forceCompleteAllOperations() {
        String str;
        String str2;
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(FragmentManager.TAG, "SpecialEffectsController: Forcing all operations to complete");
        }
        boolean zIsAttachedToWindow = this.container.isAttachedToWindow();
        synchronized (this.pendingOperations) {
            updateFinalState();
            processStart(this.pendingOperations);
            List<Operation> mutableList = CollectionsKt.toMutableList((Collection) this.runningOperations);
            Iterator it = mutableList.iterator();
            while (it.hasNext()) {
                ((Operation) it.next()).setSeeking$fragment_release(false);
            }
            for (Operation operation : mutableList) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    if (zIsAttachedToWindow) {
                        str2 = "";
                    } else {
                        str2 = "Container " + this.container + " is not attached to window. ";
                    }
                    Log.v(FragmentManager.TAG, "SpecialEffectsController: " + str2 + "Cancelling running operation " + operation);
                }
                operation.cancel(this.container);
            }
            List<Operation> mutableList2 = CollectionsKt.toMutableList((Collection) this.pendingOperations);
            Iterator it2 = mutableList2.iterator();
            while (it2.hasNext()) {
                ((Operation) it2.next()).setSeeking$fragment_release(false);
            }
            for (Operation operation2 : mutableList2) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    if (zIsAttachedToWindow) {
                        str = "";
                    } else {
                        str = "Container " + this.container + " is not attached to window. ";
                    }
                    Log.v(FragmentManager.TAG, "SpecialEffectsController: " + str + "Cancelling pending operation " + operation2);
                }
                operation2.cancel(this.container);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void updateFinalState() {
        for (Operation operation : this.pendingOperations) {
            if (operation.getLifecycleImpact() == Operation.LifecycleImpact.ADDING) {
                View viewRequireView = operation.getFragment().requireView();
                Intrinsics.checkNotNullExpressionValue(viewRequireView, "fragment.requireView()");
                operation.mergeWith(Operation.State.INSTANCE.from(viewRequireView.getVisibility()), Operation.LifecycleImpact.NONE);
            }
        }
    }

    public void commitEffects$fragment_release(List<Operation> operations) {
        Intrinsics.checkNotNullParameter(operations, "operations");
        List<Operation> list = operations;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((Operation) it.next()).getEffects$fragment_release());
        }
        List list2 = CollectionsKt.toList(CollectionsKt.toSet(arrayList));
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            ((Effect) list2.get(i)).onCommit(this.container);
        }
        int size2 = operations.size();
        for (int i2 = 0; i2 < size2; i2++) {
            applyContainerChangesToOperation$fragment_release(operations.get(i2));
        }
        List list3 = CollectionsKt.toList(list);
        int size3 = list3.size();
        for (int i3 = 0; i3 < size3; i3++) {
            Operation operation = (Operation) list3.get(i3);
            if (operation.getEffects$fragment_release().isEmpty()) {
                operation.complete$fragment_release();
            }
        }
    }

    private final void processStart(List<Operation> operations) {
        int size = operations.size();
        for (int i = 0; i < size; i++) {
            operations.get(i).onStart();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = operations.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((Operation) it.next()).getEffects$fragment_release());
        }
        List list = CollectionsKt.toList(CollectionsKt.toSet(arrayList));
        int size2 = list.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((Effect) list.get(i2)).performStart(this.container);
        }
    }

    public final void processProgress(BackEventCompat backEvent) {
        Intrinsics.checkNotNullParameter(backEvent, "backEvent");
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(FragmentManager.TAG, "SpecialEffectsController: Processing Progress " + backEvent.getProgress());
        }
        List<Operation> list = this.runningOperations;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((Operation) it.next()).getEffects$fragment_release());
        }
        List list2 = CollectionsKt.toList(CollectionsKt.toSet(arrayList));
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            ((Effect) list2.get(i)).onProgress(backEvent, this.container);
        }
    }

    public final void completeBack() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(FragmentManager.TAG, "SpecialEffectsController: Completing Back ");
        }
        processStart(this.runningOperations);
        commitEffects$fragment_release(this.runningOperations);
    }

    /* JADX INFO: compiled from: SpecialEffectsController.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0010\u0018\u00002\u00020\u0001:\u000267B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\rJ\u000e\u0010*\u001a\u00020(2\u0006\u0010+\u001a\u00020\u000bJ\u000e\u0010,\u001a\u00020(2\u0006\u0010-\u001a\u00020.J\r\u0010/\u001a\u00020(H\u0011¢\u0006\u0002\b0J\u000e\u00101\u001a\u00020(2\u0006\u0010+\u001a\u00020\u000bJ\u0016\u00102\u001a\u00020(2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005J\b\u00103\u001a\u00020(H\u0017J\b\u00104\u001a\u000205H\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001e\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u0019@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u001e\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u0019@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001aR$\u0010 \u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u0019@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001a\"\u0004\b!\u0010\u001cR\u001e\u0010\"\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u0019@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001aR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&¨\u00068"}, d2 = {"Landroidx/fragment/app/SpecialEffectsController$Operation;", "", "finalState", "Landroidx/fragment/app/SpecialEffectsController$Operation$State;", "lifecycleImpact", "Landroidx/fragment/app/SpecialEffectsController$Operation$LifecycleImpact;", "fragment", "Landroidx/fragment/app/Fragment;", "(Landroidx/fragment/app/SpecialEffectsController$Operation$State;Landroidx/fragment/app/SpecialEffectsController$Operation$LifecycleImpact;Landroidx/fragment/app/Fragment;)V", "_effects", "", "Landroidx/fragment/app/SpecialEffectsController$Effect;", "completionListeners", "Ljava/lang/Runnable;", "effects", "", "getEffects$fragment_release", "()Ljava/util/List;", "getFinalState", "()Landroidx/fragment/app/SpecialEffectsController$Operation$State;", "setFinalState", "(Landroidx/fragment/app/SpecialEffectsController$Operation$State;)V", "getFragment", "()Landroidx/fragment/app/Fragment;", "isAwaitingContainerChanges", "", "()Z", "setAwaitingContainerChanges", "(Z)V", "<set-?>", "isCanceled", "isComplete", "isSeeking", "setSeeking$fragment_release", "isStarted", "getLifecycleImpact", "()Landroidx/fragment/app/SpecialEffectsController$Operation$LifecycleImpact;", "setLifecycleImpact", "(Landroidx/fragment/app/SpecialEffectsController$Operation$LifecycleImpact;)V", "addCompletionListener", "", "listener", "addEffect", "effect", ToDoStat.CANCEL, "container", "Landroid/view/ViewGroup;", "complete", "complete$fragment_release", "completeEffect", "mergeWith", "onStart", "toString", "", "LifecycleImpact", "State", "fragment_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static class Operation {
        private final List<Effect> _effects;
        private final List<Runnable> completionListeners;
        private final List<Effect> effects;
        private State finalState;
        private final Fragment fragment;
        private boolean isAwaitingContainerChanges;
        private boolean isCanceled;
        private boolean isComplete;
        private boolean isSeeking;
        private boolean isStarted;
        private LifecycleImpact lifecycleImpact;

        /* JADX INFO: compiled from: SpecialEffectsController.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Landroidx/fragment/app/SpecialEffectsController$Operation$LifecycleImpact;", "", "(Ljava/lang/String;I)V", "NONE", "ADDING", "REMOVING", "fragment_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public enum LifecycleImpact {
            NONE,
            ADDING,
            REMOVING
        }

        /* JADX INFO: compiled from: SpecialEffectsController.kt */
        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[LifecycleImpact.values().length];
                try {
                    iArr[LifecycleImpact.ADDING.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[LifecycleImpact.REMOVING.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[LifecycleImpact.NONE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public Operation(State finalState, LifecycleImpact lifecycleImpact, Fragment fragment) {
            Intrinsics.checkNotNullParameter(finalState, "finalState");
            Intrinsics.checkNotNullParameter(lifecycleImpact, "lifecycleImpact");
            Intrinsics.checkNotNullParameter(fragment, "fragment");
            this.finalState = finalState;
            this.lifecycleImpact = lifecycleImpact;
            this.fragment = fragment;
            this.completionListeners = new ArrayList();
            this.isAwaitingContainerChanges = true;
            ArrayList arrayList = new ArrayList();
            this._effects = arrayList;
            this.effects = arrayList;
        }

        public final State getFinalState() {
            return this.finalState;
        }

        public final void setFinalState(State state) {
            Intrinsics.checkNotNullParameter(state, "<set-?>");
            this.finalState = state;
        }

        public final LifecycleImpact getLifecycleImpact() {
            return this.lifecycleImpact;
        }

        public final void setLifecycleImpact(LifecycleImpact lifecycleImpact) {
            Intrinsics.checkNotNullParameter(lifecycleImpact, "<set-?>");
            this.lifecycleImpact = lifecycleImpact;
        }

        public final Fragment getFragment() {
            return this.fragment;
        }

        /* JADX INFO: compiled from: SpecialEffectsController.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0080\u0001\u0018\u0000 \r2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\rB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\u000e"}, d2 = {"Landroidx/fragment/app/SpecialEffectsController$Operation$State;", "", "(Ljava/lang/String;I)V", "applyState", "", "view", "Landroid/view/View;", "container", "Landroid/view/ViewGroup;", "REMOVED", "VISIBLE", "GONE", "INVISIBLE", "Companion", "fragment_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public enum State {
            REMOVED,
            VISIBLE,
            GONE,
            INVISIBLE;


            /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
            public static final Companion INSTANCE = new Companion(null);

            /* JADX INFO: compiled from: SpecialEffectsController.kt */
            @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[State.values().length];
                    try {
                        iArr[State.REMOVED.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[State.VISIBLE.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[State.GONE.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[State.INVISIBLE.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @JvmStatic
            public static final State from(int i) {
                return INSTANCE.from(i);
            }

            public final void applyState(View view, ViewGroup container) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(container, "container");
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "SpecialEffectsController: Calling apply state");
                }
                int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
                if (i == 1) {
                    ViewParent parent = view.getParent();
                    ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                    if (viewGroup != null) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v(FragmentManager.TAG, "SpecialEffectsController: Removing view " + view + " from container " + viewGroup);
                        }
                        viewGroup.removeView(view);
                        return;
                    }
                    return;
                }
                if (i == 2) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "SpecialEffectsController: Setting view " + view + " to VISIBLE");
                    }
                    ViewParent parent2 = view.getParent();
                    if ((parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null) == null) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v(FragmentManager.TAG, "SpecialEffectsController: Adding view " + view + " to Container " + container);
                        }
                        container.addView(view);
                    }
                    view.setVisibility(0);
                    return;
                }
                if (i == 3) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "SpecialEffectsController: Setting view " + view + " to GONE");
                    }
                    view.setVisibility(8);
                } else {
                    if (i != 4) {
                        return;
                    }
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "SpecialEffectsController: Setting view " + view + " to INVISIBLE");
                    }
                    view.setVisibility(4);
                }
            }

            /* JADX INFO: compiled from: SpecialEffectsController.kt */
            @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\n\u0010\u0007\u001a\u00020\u0004*\u00020\b¨\u0006\t"}, d2 = {"Landroidx/fragment/app/SpecialEffectsController$Operation$State$Companion;", "", "()V", "from", "Landroidx/fragment/app/SpecialEffectsController$Operation$State;", "visibility", "", "asOperationState", "Landroid/view/View;", "fragment_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
            public static final class Companion {
                public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                    this();
                }

                private Companion() {
                }

                public final State asOperationState(View view) {
                    Intrinsics.checkNotNullParameter(view, "<this>");
                    if (view.getAlpha() == 0.0f && view.getVisibility() == 0) {
                        return State.INVISIBLE;
                    }
                    return from(view.getVisibility());
                }

                @JvmStatic
                public final State from(int visibility) {
                    if (visibility == 0) {
                        return State.VISIBLE;
                    }
                    if (visibility == 4) {
                        return State.INVISIBLE;
                    }
                    if (visibility == 8) {
                        return State.GONE;
                    }
                    throw new IllegalArgumentException("Unknown visibility " + visibility);
                }
            }
        }

        /* JADX INFO: renamed from: isCanceled, reason: from getter */
        public final boolean getIsCanceled() {
            return this.isCanceled;
        }

        /* JADX INFO: renamed from: isComplete, reason: from getter */
        public final boolean getIsComplete() {
            return this.isComplete;
        }

        /* JADX INFO: renamed from: isSeeking, reason: from getter */
        public final boolean getIsSeeking() {
            return this.isSeeking;
        }

        public final void setSeeking$fragment_release(boolean z) {
            this.isSeeking = z;
        }

        /* JADX INFO: renamed from: isStarted, reason: from getter */
        public final boolean getIsStarted() {
            return this.isStarted;
        }

        /* JADX INFO: renamed from: isAwaitingContainerChanges, reason: from getter */
        public final boolean getIsAwaitingContainerChanges() {
            return this.isAwaitingContainerChanges;
        }

        public final void setAwaitingContainerChanges(boolean z) {
            this.isAwaitingContainerChanges = z;
        }

        public final List<Effect> getEffects$fragment_release() {
            return this.effects;
        }

        public String toString() {
            return "Operation {" + Integer.toHexString(System.identityHashCode(this)) + "} {finalState = " + this.finalState + " lifecycleImpact = " + this.lifecycleImpact + " fragment = " + this.fragment + CoreConstants.CURLY_RIGHT;
        }

        public final void cancel(ViewGroup container) {
            Intrinsics.checkNotNullParameter(container, "container");
            this.isStarted = false;
            if (this.isCanceled) {
                return;
            }
            this.isCanceled = true;
            if (this._effects.isEmpty()) {
                complete$fragment_release();
                return;
            }
            Iterator it = CollectionsKt.toList(this.effects).iterator();
            while (it.hasNext()) {
                ((Effect) it.next()).cancel(container);
            }
        }

        public final void mergeWith(State finalState, LifecycleImpact lifecycleImpact) {
            Intrinsics.checkNotNullParameter(finalState, "finalState");
            Intrinsics.checkNotNullParameter(lifecycleImpact, "lifecycleImpact");
            int i = WhenMappings.$EnumSwitchMapping$0[lifecycleImpact.ordinal()];
            if (i == 1) {
                if (this.finalState == State.REMOVED) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "SpecialEffectsController: For fragment " + this.fragment + " mFinalState = REMOVED -> VISIBLE. mLifecycleImpact = " + this.lifecycleImpact + " to ADDING.");
                    }
                    this.finalState = State.VISIBLE;
                    this.lifecycleImpact = LifecycleImpact.ADDING;
                    this.isAwaitingContainerChanges = true;
                    return;
                }
                return;
            }
            if (i == 2) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "SpecialEffectsController: For fragment " + this.fragment + " mFinalState = " + this.finalState + " -> REMOVED. mLifecycleImpact  = " + this.lifecycleImpact + " to REMOVING.");
                }
                this.finalState = State.REMOVED;
                this.lifecycleImpact = LifecycleImpact.REMOVING;
                this.isAwaitingContainerChanges = true;
                return;
            }
            if (i == 3 && this.finalState != State.REMOVED) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "SpecialEffectsController: For fragment " + this.fragment + " mFinalState = " + this.finalState + " -> " + finalState + '.');
                }
                this.finalState = finalState;
            }
        }

        public final void addCompletionListener(Runnable listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.completionListeners.add(listener);
        }

        public final void addEffect(Effect effect) {
            Intrinsics.checkNotNullParameter(effect, "effect");
            this._effects.add(effect);
        }

        public final void completeEffect(Effect effect) {
            Intrinsics.checkNotNullParameter(effect, "effect");
            if (this._effects.remove(effect) && this._effects.isEmpty()) {
                complete$fragment_release();
            }
        }

        public void onStart() {
            this.isStarted = true;
        }

        public void complete$fragment_release() {
            this.isStarted = false;
            if (this.isComplete) {
                return;
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(FragmentManager.TAG, "SpecialEffectsController: " + this + " has called complete.");
            }
            this.isComplete = true;
            Iterator<T> it = this.completionListeners.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: SpecialEffectsController.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\r\u0010\t\u001a\u00020\nH\u0010¢\u0006\u0002\b\u000bJ\b\u0010\f\u001a\u00020\nH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Landroidx/fragment/app/SpecialEffectsController$FragmentStateManagerOperation;", "Landroidx/fragment/app/SpecialEffectsController$Operation;", "finalState", "Landroidx/fragment/app/SpecialEffectsController$Operation$State;", "lifecycleImpact", "Landroidx/fragment/app/SpecialEffectsController$Operation$LifecycleImpact;", "fragmentStateManager", "Landroidx/fragment/app/FragmentStateManager;", "(Landroidx/fragment/app/SpecialEffectsController$Operation$State;Landroidx/fragment/app/SpecialEffectsController$Operation$LifecycleImpact;Landroidx/fragment/app/FragmentStateManager;)V", "complete", "", "complete$fragment_release", "onStart", "fragment_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    static final class FragmentStateManagerOperation extends Operation {
        private final FragmentStateManager fragmentStateManager;

        /* JADX WARN: Illegal instructions before constructor call */
        public FragmentStateManagerOperation(Operation.State finalState, Operation.LifecycleImpact lifecycleImpact, FragmentStateManager fragmentStateManager) {
            Intrinsics.checkNotNullParameter(finalState, "finalState");
            Intrinsics.checkNotNullParameter(lifecycleImpact, "lifecycleImpact");
            Intrinsics.checkNotNullParameter(fragmentStateManager, "fragmentStateManager");
            Fragment fragment = fragmentStateManager.getFragment();
            Intrinsics.checkNotNullExpressionValue(fragment, "fragmentStateManager.fragment");
            super(finalState, lifecycleImpact, fragment);
            this.fragmentStateManager = fragmentStateManager;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Operation
        public void onStart() {
            if (getIsStarted()) {
                return;
            }
            super.onStart();
            if (getLifecycleImpact() == Operation.LifecycleImpact.ADDING) {
                Fragment fragment = this.fragmentStateManager.getFragment();
                Intrinsics.checkNotNullExpressionValue(fragment, "fragmentStateManager.fragment");
                View viewFindFocus = fragment.mView.findFocus();
                if (viewFindFocus != null) {
                    fragment.setFocusedView(viewFindFocus);
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "requestFocus: Saved focused view " + viewFindFocus + " for Fragment " + fragment);
                    }
                }
                View viewRequireView = getFragment().requireView();
                Intrinsics.checkNotNullExpressionValue(viewRequireView, "this.fragment.requireView()");
                if (viewRequireView.getParent() == null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "Adding fragment " + fragment + " view " + viewRequireView + " to container in onStart");
                    }
                    this.fragmentStateManager.addViewToContainer();
                    viewRequireView.setAlpha(0.0f);
                }
                if (viewRequireView.getAlpha() == 0.0f && viewRequireView.getVisibility() == 0) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "Making view " + viewRequireView + " INVISIBLE in onStart");
                    }
                    viewRequireView.setVisibility(4);
                }
                viewRequireView.setAlpha(fragment.getPostOnViewCreatedAlpha());
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "Setting view alpha to " + fragment.getPostOnViewCreatedAlpha() + " in onStart");
                    return;
                }
                return;
            }
            if (getLifecycleImpact() == Operation.LifecycleImpact.REMOVING) {
                Fragment fragment2 = this.fragmentStateManager.getFragment();
                Intrinsics.checkNotNullExpressionValue(fragment2, "fragmentStateManager.fragment");
                View viewRequireView2 = fragment2.requireView();
                Intrinsics.checkNotNullExpressionValue(viewRequireView2, "fragment.requireView()");
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "Clearing focus " + viewRequireView2.findFocus() + " on view " + viewRequireView2 + " for Fragment " + fragment2);
                }
                viewRequireView2.clearFocus();
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Operation
        public void complete$fragment_release() {
            super.complete$fragment_release();
            getFragment().mTransitioning = false;
            this.fragmentStateManager.moveToExpectedState();
        }
    }

    /* JADX INFO: compiled from: SpecialEffectsController.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0010\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u000e\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Landroidx/fragment/app/SpecialEffectsController$Effect;", "", "()V", "isCancelled", "", "isSeekingSupported", "()Z", "isStarted", ToDoStat.CANCEL, "", "container", "Landroid/view/ViewGroup;", "onCancel", "onCommit", "onProgress", "backEvent", "Landroidx/activity/BackEventCompat;", "onStart", "performStart", "fragment_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static class Effect {
        private boolean isCancelled;
        private final boolean isSeekingSupported;
        private boolean isStarted;

        public void onCancel(ViewGroup container) {
            Intrinsics.checkNotNullParameter(container, "container");
        }

        public void onCommit(ViewGroup container) {
            Intrinsics.checkNotNullParameter(container, "container");
        }

        public void onProgress(BackEventCompat backEvent, ViewGroup container) {
            Intrinsics.checkNotNullParameter(backEvent, "backEvent");
            Intrinsics.checkNotNullParameter(container, "container");
        }

        public void onStart(ViewGroup container) {
            Intrinsics.checkNotNullParameter(container, "container");
        }

        /* JADX INFO: renamed from: isSeekingSupported, reason: from getter */
        public boolean getIsSeekingSupported() {
            return this.isSeekingSupported;
        }

        public final void performStart(ViewGroup container) {
            Intrinsics.checkNotNullParameter(container, "container");
            if (!this.isStarted) {
                onStart(container);
            }
            this.isStarted = true;
        }

        public final void cancel(ViewGroup container) {
            Intrinsics.checkNotNullParameter(container, "container");
            if (!this.isCancelled) {
                onCancel(container);
            }
            this.isCancelled = true;
        }
    }

    /* JADX INFO: compiled from: SpecialEffectsController.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0007¨\u0006\u000b"}, d2 = {"Landroidx/fragment/app/SpecialEffectsController$Companion;", "", "()V", "getOrCreateController", "Landroidx/fragment/app/SpecialEffectsController;", "container", "Landroid/view/ViewGroup;", "fragmentManager", "Landroidx/fragment/app/FragmentManager;", "factory", "Landroidx/fragment/app/SpecialEffectsControllerFactory;", "fragment_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final SpecialEffectsController getOrCreateController(ViewGroup container, FragmentManager fragmentManager) {
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(fragmentManager, "fragmentManager");
            SpecialEffectsControllerFactory specialEffectsControllerFactory = fragmentManager.getSpecialEffectsControllerFactory();
            Intrinsics.checkNotNullExpressionValue(specialEffectsControllerFactory, "fragmentManager.specialEffectsControllerFactory");
            return getOrCreateController(container, specialEffectsControllerFactory);
        }

        @JvmStatic
        public final SpecialEffectsController getOrCreateController(ViewGroup container, SpecialEffectsControllerFactory factory) {
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(factory, "factory");
            Object tag = container.getTag(R.id.special_effects_controller_view_tag);
            if (tag instanceof SpecialEffectsController) {
                return (SpecialEffectsController) tag;
            }
            SpecialEffectsController specialEffectsControllerCreateController = factory.createController(container);
            Intrinsics.checkNotNullExpressionValue(specialEffectsControllerCreateController, "factory.createController(container)");
            container.setTag(R.id.special_effects_controller_view_tag, specialEffectsControllerCreateController);
            return specialEffectsControllerCreateController;
        }
    }
}
