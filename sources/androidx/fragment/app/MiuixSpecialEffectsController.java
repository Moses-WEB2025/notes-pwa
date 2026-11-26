package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import miuix.reflect.Reflects;

/* JADX INFO: loaded from: classes.dex */
public class MiuixSpecialEffectsController extends SpecialEffectsController {
    @Override // androidx.fragment.app.SpecialEffectsController
    public /* bridge */ /* synthetic */ ViewGroup getContainer() {
        return super.getContainer();
    }

    MiuixSpecialEffectsController(ViewGroup viewGroup) {
        super(viewGroup);
    }

    public static SpecialEffectsControllerFactory createMiuixSpecialEffectsController() {
        return new SpecialEffectsControllerFactory() { // from class: androidx.fragment.app.MiuixSpecialEffectsController$$ExternalSyntheticLambda0
            @Override // androidx.fragment.app.SpecialEffectsControllerFactory
            public final SpecialEffectsController createController(ViewGroup viewGroup) {
                return MiuixSpecialEffectsController.lambda$createMiuixSpecialEffectsController$0(viewGroup);
            }
        };
    }

    static /* synthetic */ SpecialEffectsController lambda$createMiuixSpecialEffectsController$0(ViewGroup viewGroup) {
        return new MiuixSpecialEffectsController(viewGroup);
    }

    public static void setEnabled(FragmentManager fragmentManager, boolean z) {
        Field declaredField = Reflects.getDeclaredField((Class<?>) FragmentManager.class, "mSpecialEffectsControllerFactory");
        declaredField.setAccessible(true);
        if (z) {
            if (Reflects.get(fragmentManager, declaredField) instanceof MiuixSpecialEffectsController) {
                return;
            }
            Reflects.set(fragmentManager, declaredField, createMiuixSpecialEffectsController());
            return;
        }
        Reflects.set(fragmentManager, declaredField, null);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00a9  */
    void executeOperations(List<SpecialEffectsController.Operation> list, boolean z) {
        SpecialEffectsController.Operation operation = null;
        SpecialEffectsController.Operation operation2 = null;
        for (SpecialEffectsController.Operation operation3 : list) {
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(operation3.getFragment().mView);
            int i = AnonymousClass9.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[operation3.getFinalState().ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                if (stateFrom == SpecialEffectsController.Operation.State.VISIBLE && operation == null) {
                    operation = operation3;
                }
            } else if (i == 4 && stateFrom != SpecialEffectsController.Operation.State.VISIBLE) {
                operation2 = operation3;
            }
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(FragmentManager.TAG, "Executing operations from " + operation + " to " + operation2);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList(list);
        for (final SpecialEffectsController.Operation operation4 : list) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            operation4.markStartedSpecialEffect(cancellationSignal);
            arrayList.add(new AnimationInfo(operation4, cancellationSignal, z));
            CancellationSignal cancellationSignal2 = new CancellationSignal();
            operation4.markStartedSpecialEffect(cancellationSignal2);
            boolean z2 = false;
            if (z) {
                if (operation4 == operation) {
                    z2 = true;
                }
            } else if (operation4 == operation2) {
                z2 = true;
            }
            arrayList2.add(new TransitionInfo(operation4, cancellationSignal2, z, z2));
            operation4.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m109xb31e7d55(arrayList3, operation4);
                }
            });
        }
        Map<SpecialEffectsController.Operation, Boolean> mapStartTransitions = startTransitions(arrayList2, arrayList3, z, operation, operation2);
        startAnimations(arrayList, arrayList3, mapStartTransitions.containsValue(true), mapStartTransitions);
        Iterator<SpecialEffectsController.Operation> it = arrayList3.iterator();
        while (it.hasNext()) {
            applyContainerChanges(it.next());
        }
        arrayList3.clear();
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(FragmentManager.TAG, "Completed executing operations from " + operation + " to " + operation2);
        }
    }

    /* JADX INFO: renamed from: androidx.fragment.app.MiuixSpecialEffectsController$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State;

        static {
            int[] iArr = new int[SpecialEffectsController.Operation.State.values().length];
            $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State = iArr;
            try {
                iArr[SpecialEffectsController.Operation.State.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.INVISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.VISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: renamed from: lambda$executeOperations$1$androidx-fragment-app-MiuixSpecialEffectsController, reason: not valid java name */
    /* synthetic */ void m109xb31e7d55(List list, SpecialEffectsController.Operation operation) {
        if (list.contains(operation)) {
            list.remove(operation);
            applyContainerChanges(operation);
        }
    }

    private void startAnimations(List<AnimationInfo> list, List<SpecialEffectsController.Operation> list2, boolean z, Map<SpecialEffectsController.Operation, Boolean> map) {
        final SpecialEffectsController.Operation operation;
        final AnimationInfo animationInfo;
        final View view;
        final ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList<AnimationInfo> arrayList = new ArrayList();
        boolean z2 = false;
        for (final AnimationInfo animationInfo2 : list) {
            if (animationInfo2.isVisibilityUnchanged()) {
                animationInfo2.completeSpecialEffect();
            } else {
                FragmentAnim.AnimationOrAnimator animation = animationInfo2.getAnimation(context);
                if (animation == null) {
                    animationInfo2.completeSpecialEffect();
                } else {
                    final Animator animator = animation.animator;
                    if (animator == null) {
                        arrayList.add(animationInfo2);
                    } else {
                        final SpecialEffectsController.Operation operation2 = animationInfo2.getOperation();
                        Fragment fragment = operation2.getFragment();
                        if (Boolean.TRUE.equals(map.get(operation2))) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                            }
                            animationInfo2.completeSpecialEffect();
                        } else {
                            final boolean z3 = operation2.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                            if (z3) {
                                list2.remove(operation2);
                            }
                            final View view2 = fragment.mView;
                            container.startViewTransition(view2);
                            final ViewGroup viewGroup = container;
                            container = viewGroup;
                            animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator2) {
                                    viewGroup.endViewTransition(view2);
                                    if (z3) {
                                        operation2.getFinalState().applyState(view2);
                                    }
                                    animationInfo2.completeSpecialEffect();
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v(FragmentManager.TAG, "Animator from operation " + operation2 + " has ended.");
                                    }
                                }
                            });
                            animator.setTarget(view2);
                            animator.start();
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Animator from operation " + operation2 + " has started.");
                            }
                            animationInfo2.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.2
                                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                                public void onCancel() {
                                    animator.end();
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v(FragmentManager.TAG, "Animator from operation " + operation2 + " has been canceled.");
                                    }
                                }
                            });
                            z2 = true;
                        }
                    }
                }
            }
        }
        for (AnimationInfo animationInfo3 : arrayList) {
            SpecialEffectsController.Operation operation3 = animationInfo3.getOperation();
            Fragment fragment2 = operation3.getFragment();
            if (z) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                }
                animationInfo3.completeSpecialEffect();
            } else if (z2) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Animators.");
                }
                animationInfo3.completeSpecialEffect();
            } else {
                View view3 = fragment2.mView;
                Animation animation2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo3.getAnimation(context))).animation);
                if (operation3.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                    view3.startAnimation(animation2);
                    animationInfo3.completeSpecialEffect();
                    operation = operation3;
                    animationInfo = animationInfo3;
                    view = view3;
                } else {
                    container.startViewTransition(view3);
                    FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation2, container, view3);
                    operation = operation3;
                    animationInfo = animationInfo3;
                    view = view3;
                    endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.3
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation3) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation3) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Animation from operation " + operation + " has reached onAnimationStart.");
                            }
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation3) {
                            container.post(new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.3.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    container.endViewTransition(view);
                                    animationInfo.completeSpecialEffect();
                                }
                            });
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Animation from operation " + operation + " has ended.");
                            }
                        }
                    });
                    view.startAnimation(endViewTransitionAnimation);
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "Animation from operation " + operation + " has started.");
                    }
                }
                CancellationSignal signal = animationInfo.getSignal();
                final AnimationInfo animationInfo4 = animationInfo;
                final SpecialEffectsController.Operation operation4 = operation;
                final View view4 = view;
                signal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.4
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        view4.clearAnimation();
                        container.endViewTransition(view4);
                        animationInfo4.completeSpecialEffect();
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v(FragmentManager.TAG, "Animation from operation " + operation4 + " has been cancelled.");
                        }
                    }
                });
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Map<SpecialEffectsController.Operation, Boolean> startTransitions(List<TransitionInfo> list, List<SpecialEffectsController.Operation> list2, final boolean z, SpecialEffectsController.Operation operation, SpecialEffectsController.Operation operation2) {
        View view;
        String str;
        String str2;
        View view2;
        Object objMergeTransitionsTogether;
        String str3;
        ArrayList<View> arrayList;
        ArrayList arrayList2;
        ArrayList<View> arrayList3;
        Object obj;
        HashMap map;
        View view3;
        Object objMergeTransitionsTogether2;
        SpecialEffectsController.Operation operation3;
        int i;
        View view4;
        final Rect rect;
        ArrayMap arrayMap;
        HashMap map2;
        ArrayList<View> arrayList4;
        SharedElementCallback exitTransitionCallback;
        SharedElementCallback enterTransitionCallback;
        Rect rect2;
        int i2;
        final View view5;
        String strFindKeyForValue;
        int i3;
        final SpecialEffectsController.Operation operation4 = operation;
        final SpecialEffectsController.Operation operation5 = operation2;
        HashMap map3 = new HashMap();
        final FragmentTransitionImpl fragmentTransitionImpl = null;
        for (TransitionInfo transitionInfo : list) {
            if (!transitionInfo.isVisibilityUnchanged()) {
                FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl();
                if (fragmentTransitionImpl == null) {
                    fragmentTransitionImpl = handlingImpl;
                } else if (handlingImpl != null && fragmentTransitionImpl != handlingImpl) {
                    throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + transitionInfo.getOperation().getFragment() + " returned Transition " + transitionInfo.getTransition() + " which uses a different Transition  type than other Fragments.");
                }
            }
        }
        int i4 = 0;
        if (fragmentTransitionImpl == null) {
            for (TransitionInfo transitionInfo2 : list) {
                map3.put(transitionInfo2.getOperation(), false);
                transitionInfo2.completeSpecialEffect();
            }
            return map3;
        }
        View view6 = new View(getContainer().getContext());
        Rect rect3 = new Rect();
        ArrayList<View> arrayList5 = new ArrayList<>();
        ArrayList<View> arrayList6 = new ArrayList<>();
        ArrayMap arrayMap2 = new ArrayMap();
        Iterator<TransitionInfo> it = list.iterator();
        boolean z2 = false;
        Object obj2 = null;
        View view7 = null;
        ArrayMap arrayMap3 = arrayMap2;
        while (true) {
            boolean zHasNext = it.hasNext();
            view = view7;
            str = FragmentManager.TAG;
            if (!zHasNext) {
                break;
            }
            TransitionInfo next = it.next();
            if (!next.hasSharedElementTransition() || operation4 == null || operation5 == null) {
                i = i4;
                view4 = view6;
                rect = rect3;
                arrayMap = arrayMap3;
                map2 = map3;
                arrayList4 = arrayList5;
            } else {
                Object objWrapTransitionInSet = fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(next.getSharedElementTransition()));
                ArrayList<String> sharedElementSourceNames = operation5.getFragment().getSharedElementSourceNames();
                ArrayList<String> sharedElementSourceNames2 = operation4.getFragment().getSharedElementSourceNames();
                ArrayList<String> sharedElementTargetNames = operation4.getFragment().getSharedElementTargetNames();
                HashMap map4 = map3;
                int i5 = 0;
                while (i5 < sharedElementTargetNames.size()) {
                    int iIndexOf = sharedElementSourceNames.indexOf(sharedElementTargetNames.get(i5));
                    ArrayList<String> arrayList7 = sharedElementTargetNames;
                    if (iIndexOf != -1) {
                        sharedElementSourceNames.set(iIndexOf, sharedElementSourceNames2.get(i5));
                    }
                    i5++;
                    sharedElementTargetNames = arrayList7;
                }
                ArrayList<String> sharedElementTargetNames2 = operation5.getFragment().getSharedElementTargetNames();
                if (z == 0) {
                    enterTransitionCallback = operation4.getFragment().getExitTransitionCallback();
                    exitTransitionCallback = operation5.getFragment().getEnterTransitionCallback();
                } else {
                    enterTransitionCallback = operation4.getFragment().getEnterTransitionCallback();
                    exitTransitionCallback = operation5.getFragment().getExitTransitionCallback();
                }
                View view8 = view6;
                int i6 = 0;
                for (int size = sharedElementSourceNames.size(); i6 < size; size = size) {
                    arrayMap3.put(sharedElementSourceNames.get(i6), sharedElementTargetNames2.get(i6));
                    i6++;
                }
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, ">>> entering view names <<<");
                    Iterator<String> it2 = sharedElementTargetNames2.iterator();
                    while (true) {
                        Iterator<String> it3 = it2;
                        if (!it2.hasNext()) {
                            break;
                        }
                        Log.v(FragmentManager.TAG, "Name: " + it3.next());
                        it2 = it3;
                        rect3 = rect3;
                    }
                    rect2 = rect3;
                    Log.v(FragmentManager.TAG, ">>> exiting view names <<<");
                    for (Iterator<String> it4 = sharedElementSourceNames.iterator(); it4.hasNext(); it4 = it4) {
                        Log.v(FragmentManager.TAG, "Name: " + it4.next());
                    }
                } else {
                    rect2 = rect3;
                }
                ArrayMap<String, View> arrayMap4 = new ArrayMap<>();
                findNamedViews(arrayMap4, operation4.getFragment().mView);
                arrayMap4.retainAll(sharedElementSourceNames);
                if (enterTransitionCallback != null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "Executing exit callback for operation " + operation4);
                    }
                    enterTransitionCallback.onMapSharedElements(sharedElementSourceNames, arrayMap4);
                    int size2 = sharedElementSourceNames.size() - 1;
                    while (size2 >= 0) {
                        String str4 = sharedElementSourceNames.get(size2);
                        View view9 = arrayMap4.get(str4);
                        if (view9 == null) {
                            arrayMap3.remove(str4);
                            i3 = size2;
                        } else {
                            i3 = size2;
                            if (!str4.equals(ViewCompat.getTransitionName(view9))) {
                                arrayMap3.put(ViewCompat.getTransitionName(view9), (String) arrayMap3.remove(str4));
                            }
                        }
                        size2 = i3 - 1;
                    }
                } else {
                    arrayMap3.retainAll(arrayMap4.keySet());
                }
                final ArrayMap<String, View> arrayMap5 = new ArrayMap<>();
                findNamedViews(arrayMap5, operation5.getFragment().mView);
                arrayMap5.retainAll(sharedElementTargetNames2);
                arrayMap5.retainAll(arrayMap3.values());
                if (exitTransitionCallback != null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "Executing enter callback for operation " + operation5);
                    }
                    exitTransitionCallback.onMapSharedElements(sharedElementTargetNames2, arrayMap5);
                    for (int size3 = sharedElementTargetNames2.size() - 1; size3 >= 0; size3--) {
                        String str5 = sharedElementTargetNames2.get(size3);
                        View view10 = arrayMap5.get(str5);
                        if (view10 == null) {
                            String strFindKeyForValue2 = MiuixFragmentTransition.findKeyForValue(arrayMap3, str5);
                            if (strFindKeyForValue2 != null) {
                                arrayMap3.remove(strFindKeyForValue2);
                            }
                        } else if (!str5.equals(ViewCompat.getTransitionName(view10)) && (strFindKeyForValue = MiuixFragmentTransition.findKeyForValue(arrayMap3, str5)) != null) {
                            arrayMap3.put(strFindKeyForValue, ViewCompat.getTransitionName(view10));
                        }
                    }
                } else {
                    MiuixFragmentTransition.retainValues(arrayMap3, arrayMap5);
                }
                retainMatchingViews(arrayMap4, arrayMap3.keySet());
                retainMatchingViews(arrayMap5, arrayMap3.values());
                if (arrayMap3.isEmpty()) {
                    arrayList5.clear();
                    arrayList6.clear();
                    arrayList4 = arrayList5;
                    arrayMap = arrayMap3;
                    arrayList6 = arrayList6;
                    view7 = view;
                    map2 = map4;
                    view4 = view8;
                    rect = rect2;
                    obj2 = null;
                    i = 0;
                } else {
                    MiuixFragmentTransition.callSharedElementStartEnd(operation5.getFragment(), operation4.getFragment(), z, arrayMap4, true);
                    OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.5
                        @Override // java.lang.Runnable
                        public void run() {
                            MiuixFragmentTransition.callSharedElementStartEnd(operation5.getFragment(), operation4.getFragment(), z, arrayMap5, false);
                        }
                    });
                    arrayList5.addAll(arrayMap4.values());
                    if (sharedElementSourceNames.isEmpty()) {
                        i2 = 0;
                    } else {
                        i2 = 0;
                        View view11 = arrayMap4.get(sharedElementSourceNames.get(0));
                        fragmentTransitionImpl.setEpicenter(objWrapTransitionInSet, view11);
                        view = view11;
                    }
                    arrayList6.addAll(arrayMap5.values());
                    if (sharedElementTargetNames2.isEmpty() || (view5 = arrayMap5.get(sharedElementTargetNames2.get(i2))) == null) {
                        rect = rect2;
                    } else {
                        rect = rect2;
                        OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.6
                            @Override // java.lang.Runnable
                            public void run() {
                                fragmentTransitionImpl.getBoundsOnScreen(view5, rect);
                            }
                        });
                        z2 = true;
                    }
                    fragmentTransitionImpl.setSharedElementTargets(objWrapTransitionInSet, view8, arrayList5);
                    arrayMap = arrayMap3;
                    arrayList4 = arrayList5;
                    view4 = view8;
                    i = i2;
                    fragmentTransitionImpl.scheduleRemoveTargets(objWrapTransitionInSet, null, null, null, null, objWrapTransitionInSet, arrayList6);
                    map2 = map4;
                    map2.put(operation4, true);
                    map2.put(operation5, true);
                    obj2 = objWrapTransitionInSet;
                }
                view6 = view4;
                arrayList5 = arrayList4;
                arrayList6 = arrayList6;
                map3 = map2;
                arrayMap3 = arrayMap;
                i4 = i;
                rect3 = rect;
            }
            view7 = view;
            view6 = view4;
            arrayList5 = arrayList4;
            arrayList6 = arrayList6;
            map3 = map2;
            arrayMap3 = arrayMap;
            i4 = i;
            rect3 = rect;
        }
        int i7 = i4;
        View view12 = view6;
        Rect rect4 = rect3;
        ArrayMap arrayMap6 = arrayMap3;
        ArrayList<View> arrayList8 = arrayList6;
        HashMap map5 = map3;
        ArrayList<View> arrayList9 = arrayList5;
        ArrayList arrayList10 = new ArrayList();
        Object obj3 = null;
        Object obj4 = null;
        for (TransitionInfo transitionInfo3 : list) {
            if (transitionInfo3.isVisibilityUnchanged()) {
                boolean z3 = i7;
                map5.put(transitionInfo3.getOperation(), Boolean.valueOf(z3));
                transitionInfo3.completeSpecialEffect();
                i7 = z3 ? 1 : 0;
            } else {
                boolean z4 = i7;
                Object objCloneTransition = fragmentTransitionImpl.cloneTransition(transitionInfo3.getTransition());
                SpecialEffectsController.Operation operation6 = transitionInfo3.getOperation();
                boolean z5 = (obj2 == null || !(operation6 == operation4 || operation6 == operation5)) ? z4 ? 1 : 0 : true;
                if (objCloneTransition == null) {
                    if (!z5) {
                        map5.put(operation6, Boolean.valueOf(z4));
                        transitionInfo3.completeSpecialEffect();
                    }
                    view3 = view12;
                    arrayList3 = arrayList8;
                    map = map5;
                    str3 = str;
                    view2 = view;
                    arrayList = arrayList9;
                    arrayList2 = arrayList10;
                } else {
                    HashMap map6 = map5;
                    final ArrayList<View> arrayList11 = new ArrayList<>();
                    ArrayList arrayList12 = arrayList10;
                    captureTransitioningViews(arrayList11, operation6.getFragment().mView);
                    if (z5) {
                        if (operation6 == operation4) {
                            arrayList11.removeAll(arrayList9);
                        } else {
                            arrayList11.removeAll(arrayList8);
                        }
                    }
                    if (arrayList11.isEmpty()) {
                        fragmentTransitionImpl.addTarget(objCloneTransition, view12);
                        view3 = view12;
                        objMergeTransitionsTogether2 = obj3;
                        objMergeTransitionsTogether = obj4;
                        str3 = str;
                        operation3 = operation6;
                        view2 = view;
                        arrayList = arrayList9;
                        arrayList2 = arrayList12;
                        arrayList3 = arrayList8;
                        obj = objCloneTransition;
                        map = map6;
                    } else {
                        fragmentTransitionImpl.addTargets(objCloneTransition, arrayList11);
                        view2 = view;
                        objMergeTransitionsTogether = obj4;
                        str3 = str;
                        arrayList = arrayList9;
                        arrayList2 = arrayList12;
                        arrayList3 = arrayList8;
                        obj = objCloneTransition;
                        map = map6;
                        view3 = view12;
                        objMergeTransitionsTogether2 = obj3;
                        fragmentTransitionImpl.scheduleRemoveTargets(obj, objCloneTransition, arrayList11, null, null, null, null);
                        if (operation6.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                            operation3 = operation6;
                            list2.remove(operation3);
                            ArrayList<View> arrayList13 = new ArrayList<>(arrayList11);
                            arrayList13.remove(operation3.getFragment().mView);
                            fragmentTransitionImpl.scheduleHideFragmentView(obj, operation3.getFragment().mView, arrayList13);
                            OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.7
                                @Override // java.lang.Runnable
                                public void run() {
                                    MiuixFragmentTransition.setViewVisibility(arrayList11, 4);
                                }
                            });
                        } else {
                            operation3 = operation6;
                        }
                    }
                    if (operation3.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                        arrayList2.addAll(arrayList11);
                        if (z2) {
                            fragmentTransitionImpl.setEpicenter(obj, rect4);
                        }
                    } else {
                        fragmentTransitionImpl.setEpicenter(obj, view2);
                    }
                    map.put(operation3, true);
                    if (transitionInfo3.isOverlapAllowed()) {
                        objMergeTransitionsTogether2 = fragmentTransitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether2, obj, null);
                    } else {
                        objMergeTransitionsTogether = fragmentTransitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether, obj, null);
                    }
                    obj4 = objMergeTransitionsTogether;
                    obj3 = objMergeTransitionsTogether2;
                }
                arrayList8 = arrayList3;
                operation5 = operation2;
                map5 = map;
                arrayList10 = arrayList2;
                arrayList9 = arrayList;
                i7 = z4 ? 1 : 0;
                view12 = view3;
                str = str3;
                view = view2;
                operation4 = operation;
            }
        }
        int i8 = i7;
        ArrayList<View> arrayList14 = arrayList9;
        ArrayList<View> arrayList15 = arrayList8;
        ArrayList arrayList16 = arrayList10;
        HashMap map7 = map5;
        String str6 = str;
        Object objMergeTransitionsInSequence = fragmentTransitionImpl.mergeTransitionsInSequence(obj3, obj4, obj2);
        if (objMergeTransitionsInSequence == null) {
            return map7;
        }
        for (final TransitionInfo transitionInfo4 : list) {
            if (!transitionInfo4.isVisibilityUnchanged()) {
                Object transition = transitionInfo4.getTransition();
                final SpecialEffectsController.Operation operation7 = transitionInfo4.getOperation();
                boolean z6 = (obj2 == null || !(operation7 == operation || operation7 == operation2)) ? i8 == true ? 1 : 0 : true;
                if (transition == null && !z6) {
                    str2 = str6;
                } else if (!ViewCompat.isLaidOut(getContainer())) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        str2 = str6;
                        Log.v(str2, "SpecialEffectsController: Container " + getContainer() + " has not been laid out. Completing operation " + operation7);
                    } else {
                        str2 = str6;
                    }
                    transitionInfo4.completeSpecialEffect();
                } else {
                    str2 = str6;
                    fragmentTransitionImpl.setListenerForTransitionEnd(transitionInfo4.getOperation().getFragment(), objMergeTransitionsInSequence, transitionInfo4.getSignal(), new Runnable() { // from class: androidx.fragment.app.MiuixSpecialEffectsController.8
                        @Override // java.lang.Runnable
                        public void run() {
                            transitionInfo4.completeSpecialEffect();
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Transition for operation " + operation7 + "has completed");
                            }
                        }
                    });
                }
                str6 = str2;
            }
        }
        String str7 = str6;
        if (!ViewCompat.isLaidOut(getContainer())) {
            return map7;
        }
        MiuixFragmentTransition.setViewVisibility(arrayList16, 4);
        ArrayList<String> arrayListPrepareSetNameOverridesReordered = fragmentTransitionImpl.prepareSetNameOverridesReordered(arrayList15);
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(str7, ">>>>> Beginning transition <<<<<");
            Log.v(str7, ">>>>> SharedElementFirstOutViews <<<<<");
            for (View view13 : arrayList14) {
                Log.v(str7, "View: " + view13 + " Name: " + ViewCompat.getTransitionName(view13));
            }
            Log.v(str7, ">>>>> SharedElementLastInViews <<<<<");
            for (View view14 : arrayList15) {
                Log.v(str7, "View: " + view14 + " Name: " + ViewCompat.getTransitionName(view14));
            }
        }
        fragmentTransitionImpl.beginDelayedTransition(getContainer(), objMergeTransitionsInSequence);
        fragmentTransitionImpl.setNameOverridesReordered(getContainer(), arrayList14, arrayList15, arrayListPrepareSetNameOverridesReordered, arrayMap6);
        MiuixFragmentTransition.setViewVisibility(arrayList16, i8 == true ? 1 : 0);
        fragmentTransitionImpl.swapSharedElementTargets(obj2, arrayList14, arrayList15);
        return map7;
    }

    void retainMatchingViews(ArrayMap<String, View> arrayMap, Collection<String> collection) {
        Iterator<Map.Entry<String, View>> it = arrayMap.entrySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(ViewCompat.getTransitionName(it.next().getValue()))) {
                it.remove();
            }
        }
    }

    void captureTransitioningViews(ArrayList<View> arrayList, View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
                if (arrayList.contains(view)) {
                    return;
                }
                arrayList.add(viewGroup);
                return;
            }
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    captureTransitioningViews(arrayList, childAt);
                }
            }
            return;
        }
        if (arrayList.contains(view)) {
            return;
        }
        arrayList.add(view);
    }

    void findNamedViews(Map<String, View> map, View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            map.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    findNamedViews(map, childAt);
                }
            }
        }
    }

    void applyContainerChanges(SpecialEffectsController.Operation operation) {
        operation.getFinalState().applyState(operation.getFragment().mView);
    }

    private static class SpecialEffectsInfo {
        private final SpecialEffectsController.Operation mOperation;
        private final CancellationSignal mSignal;

        SpecialEffectsInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal) {
            this.mOperation = operation;
            this.mSignal = cancellationSignal;
        }

        SpecialEffectsController.Operation getOperation() {
            return this.mOperation;
        }

        CancellationSignal getSignal() {
            return this.mSignal;
        }

        boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            if (stateFrom != finalState) {
                return (stateFrom == SpecialEffectsController.Operation.State.VISIBLE || finalState == SpecialEffectsController.Operation.State.VISIBLE) ? false : true;
            }
            return true;
        }

        void completeSpecialEffect() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }
    }

    private static class AnimationInfo extends SpecialEffectsInfo {
        private FragmentAnim.AnimationOrAnimator mAnimation;
        private boolean mIsPop;
        private boolean mLoadedAnim;

        AnimationInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal, boolean z) {
            super(operation, cancellationSignal);
            this.mLoadedAnim = false;
            this.mIsPop = z;
        }

        FragmentAnim.AnimationOrAnimator getAnimation(Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            FragmentAnim.AnimationOrAnimator animationOrAnimatorLoadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mAnimation = animationOrAnimatorLoadAnimation;
            this.mLoadedAnim = true;
            return animationOrAnimatorLoadAnimation;
        }
    }

    private static class TransitionInfo extends SpecialEffectsInfo {
        private final boolean mOverlapAllowed;
        private final Object mSharedElementTransition;
        private final Object mTransition;

        TransitionInfo(SpecialEffectsController.Operation operation, CancellationSignal cancellationSignal, boolean z, boolean z2) {
            Object exitTransition;
            Object enterTransition;
            boolean allowEnterTransitionOverlap;
            super(operation, cancellationSignal);
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                if (z) {
                    enterTransition = operation.getFragment().getReenterTransition();
                } else {
                    enterTransition = operation.getFragment().getEnterTransition();
                }
                this.mTransition = enterTransition;
                if (z) {
                    allowEnterTransitionOverlap = operation.getFragment().getAllowReturnTransitionOverlap();
                } else {
                    allowEnterTransitionOverlap = operation.getFragment().getAllowEnterTransitionOverlap();
                }
                this.mOverlapAllowed = allowEnterTransitionOverlap;
            } else {
                if (z) {
                    exitTransition = operation.getFragment().getReturnTransition();
                } else {
                    exitTransition = operation.getFragment().getExitTransition();
                }
                this.mTransition = exitTransition;
                this.mOverlapAllowed = true;
            }
            if (!z2) {
                this.mSharedElementTransition = null;
            } else if (z) {
                this.mSharedElementTransition = operation.getFragment().getSharedElementReturnTransition();
            } else {
                this.mSharedElementTransition = operation.getFragment().getSharedElementEnterTransition();
            }
        }

        Object getTransition() {
            return this.mTransition;
        }

        boolean isOverlapAllowed() {
            return this.mOverlapAllowed;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }

        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        FragmentTransitionImpl getHandlingImpl() {
            FragmentTransitionImpl handlingImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl handlingImpl2 = getHandlingImpl(this.mSharedElementTransition);
            if (handlingImpl == null || handlingImpl2 == null || handlingImpl == handlingImpl2) {
                return handlingImpl != null ? handlingImpl : handlingImpl2;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
        }

        private FragmentTransitionImpl getHandlingImpl(Object obj) {
            if (obj == null) {
                return null;
            }
            if (MiuixFragmentTransition.PLATFORM_IMPL != null && MiuixFragmentTransition.PLATFORM_IMPL.canHandle(obj)) {
                return MiuixFragmentTransition.PLATFORM_IMPL;
            }
            if (MiuixFragmentTransition.SUPPORT_IMPL != null && MiuixFragmentTransition.SUPPORT_IMPL.canHandle(obj)) {
                return MiuixFragmentTransition.SUPPORT_IMPL;
            }
            if (MiuixFragmentTransition.MIUIX_IMPL != null && MiuixFragmentTransition.MIUIX_IMPL.canHandle(obj)) {
                return MiuixFragmentTransition.MIUIX_IMPL;
            }
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
