package miuix.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import androidx.transition.MiuixTransitionUtils;
import miuix.animation.listener.TransitionListener;

/* JADX INFO: loaded from: classes6.dex */
public class Visibility extends MiuixTransition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final int OVERLAY_VIEW_TAG = 1;
    private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
    private boolean mForceUseOverlay;
    private int mMode;
    private static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String[] sTransitionProperties = {PROPNAME_VISIBILITY, PROPNAME_PARENT};

    public void onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
    }

    public void onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2, TransitionListener transitionListener) {
    }

    private static class VisibilityInfo {
        ViewGroup endParent;
        int endVisibility;
        boolean fadeIn;
        ViewGroup startParent;
        int startVisibility;
        boolean visibilityChange;

        private VisibilityInfo() {
        }
    }

    public Visibility() {
        this.mMode = 3;
        this.mForceUseOverlay = false;
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMode = 3;
        this.mForceUseOverlay = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VisibilityTransition);
        int i = typedArrayObtainStyledAttributes.getInt(R.styleable.VisibilityTransition_transitionVisibilityMode, 0);
        typedArrayObtainStyledAttributes.recycle();
        if (i != 0) {
            setMode(i);
        }
    }

    public Visibility(int i) {
        this.mMode = 3;
        this.mForceUseOverlay = false;
        setMode(i);
    }

    public void setMode(int i) {
        if ((i & (-4)) != 0) {
            throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
        this.mMode = i;
    }

    public int getMode() {
        return this.mMode;
    }

    @Override // miuix.transition.MiuixTransition
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public Visibility setForceUseOverlay(boolean z) {
        this.mForceUseOverlay = z;
        return this;
    }

    protected void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_VISIBILITY, Integer.valueOf(view.getVisibility()));
        transitionValues.values.put(PROPNAME_PARENT, view.getParent());
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put(PROPNAME_SCREEN_LOCATION, iArr);
    }

    @Override // miuix.transition.MiuixTransition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // miuix.transition.MiuixTransition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public boolean isVisible(TransitionValues transitionValues) {
        if (transitionValues == null) {
            return false;
        }
        return ((Integer) transitionValues.values.get(PROPNAME_VISIBILITY)).intValue() == 0 && ((View) transitionValues.values.get(PROPNAME_PARENT)) != null;
    }

    private static VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.visibilityChange = false;
        visibilityInfo.fadeIn = false;
        if (transitionValues != null && transitionValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.startVisibility = ((Integer) transitionValues.values.get(PROPNAME_VISIBILITY)).intValue();
            visibilityInfo.startParent = (ViewGroup) transitionValues.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.startVisibility = -1;
            visibilityInfo.startParent = null;
        }
        if (transitionValues2 != null && transitionValues2.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.endVisibility = ((Integer) transitionValues2.values.get(PROPNAME_VISIBILITY)).intValue();
            visibilityInfo.endParent = (ViewGroup) transitionValues2.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.endVisibility = -1;
            visibilityInfo.endParent = null;
        }
        if (transitionValues != null && transitionValues2 != null) {
            if (visibilityInfo.startVisibility != visibilityInfo.endVisibility || visibilityInfo.startParent != visibilityInfo.endParent) {
                if (visibilityInfo.startVisibility != visibilityInfo.endVisibility) {
                    if (visibilityInfo.startVisibility == 0) {
                        visibilityInfo.fadeIn = false;
                        visibilityInfo.visibilityChange = true;
                        return visibilityInfo;
                    }
                    if (visibilityInfo.endVisibility == 0) {
                        visibilityInfo.fadeIn = true;
                        visibilityInfo.visibilityChange = true;
                        return visibilityInfo;
                    }
                } else if (visibilityInfo.startParent != visibilityInfo.endParent) {
                    if (visibilityInfo.endParent == null) {
                        visibilityInfo.fadeIn = false;
                        visibilityInfo.visibilityChange = true;
                        return visibilityInfo;
                    }
                    if (visibilityInfo.startParent == null) {
                        visibilityInfo.fadeIn = true;
                        visibilityInfo.visibilityChange = true;
                        return visibilityInfo;
                    }
                }
            }
        } else {
            if (transitionValues == null && visibilityInfo.endVisibility == 0) {
                visibilityInfo.fadeIn = true;
                visibilityInfo.visibilityChange = true;
                return visibilityInfo;
            }
            if (transitionValues2 == null && visibilityInfo.startVisibility == 0) {
                visibilityInfo.fadeIn = false;
                visibilityInfo.visibilityChange = true;
            }
        }
        return visibilityInfo;
    }

    @Override // miuix.transition.MiuixTransition
    public void createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityChangeInfo.visibilityChange) {
            if (visibilityChangeInfo.startParent == null && visibilityChangeInfo.endParent == null) {
                return;
            }
            if (visibilityChangeInfo.fadeIn) {
                onAppear(viewGroup, transitionValues, visibilityChangeInfo.startVisibility, transitionValues2, visibilityChangeInfo.endVisibility);
            } else {
                onDisappear(viewGroup, transitionValues, visibilityChangeInfo.startVisibility, transitionValues2, visibilityChangeInfo.endVisibility);
            }
        }
    }

    public void onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        if ((this.mMode & 1) != 1 || transitionValues2 == null) {
            return;
        }
        if (transitionValues == null) {
            View view = (View) transitionValues2.view.getParent();
            if (getVisibilityChangeInfo(getMatchedTransitionValues(view, false), getTransitionValues(view, false)).visibilityChange) {
                return;
            }
        }
        onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x003f  */
    public void onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        View view;
        boolean z;
        boolean z2;
        final ViewGroupOverlay overlay;
        if ((this.mMode & 2) == 2 && transitionValues != null) {
            View view2 = transitionValues.view;
            View view3 = null;
            View viewCopyViewImage = transitionValues2 != null ? transitionValues2.view : null;
            final View view4 = (View) view2.getTag(1);
            if (view4 != null) {
                z2 = true;
            } else {
                if (viewCopyViewImage == null || viewCopyViewImage.getParent() == null) {
                    if (viewCopyViewImage != null) {
                        view = null;
                        z = false;
                    } else {
                        viewCopyViewImage = null;
                        view = null;
                        z = true;
                    }
                } else if (i2 == 4 || view2 == viewCopyViewImage) {
                    view = viewCopyViewImage;
                    z = false;
                    viewCopyViewImage = null;
                } else {
                    viewCopyViewImage = null;
                    view = null;
                    z = true;
                }
                if (this.mForceUseOverlay || z) {
                    if (view2.getParent() != null) {
                        if (view2.getParent() instanceof View) {
                            View view5 = (View) view2.getParent();
                            if (!getVisibilityChangeInfo(getTransitionValues(view5, true), getMatchedTransitionValues(view5, true)).visibilityChange) {
                                viewCopyViewImage = MiuixTransitionUtils.copyViewImage(viewGroup, view2, view5);
                            } else {
                                int id = view5.getId();
                                if (view5.getParent() != null || id == -1 || viewGroup.findViewById(id) == null || !this.mCanRemoveViews) {
                                }
                            }
                        }
                        view3 = view;
                        view4 = viewCopyViewImage;
                        z2 = false;
                    }
                    view3 = view;
                    z2 = false;
                    view4 = view2;
                } else {
                    view3 = view;
                    view4 = viewCopyViewImage;
                    z2 = false;
                }
            }
            if (view4 == null) {
                if (view3 != null) {
                    view3.setVisibility(0);
                    onDisappear(viewGroup, view3, transitionValues, transitionValues2, (TransitionListener) null);
                    return;
                }
                return;
            }
            if (z2) {
                overlay = null;
            } else {
                overlay = viewGroup.getOverlay();
                int[] iArr = (int[]) transitionValues.values.get(PROPNAME_SCREEN_LOCATION);
                int i3 = iArr[0];
                int i4 = iArr[1];
                int[] iArr2 = new int[2];
                viewGroup.getLocationOnScreen(iArr2);
                view4.offsetLeftAndRight((i3 - iArr2[0]) - view4.getLeft());
                view4.offsetTopAndBottom((i4 - iArr2[1]) - view4.getTop());
                overlay.add(view4);
            }
            view2.setTag(1);
            onDisappear(viewGroup, view4, transitionValues, transitionValues2, new TransitionListener() { // from class: miuix.transition.Visibility.1
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    overlay.remove(view4);
                }
            });
        }
    }

    @Override // miuix.transition.MiuixTransition
    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null && transitionValues2 == null) {
            return false;
        }
        if (transitionValues != null && transitionValues2 != null && transitionValues2.values.containsKey(PROPNAME_VISIBILITY) != transitionValues.values.containsKey(PROPNAME_VISIBILITY)) {
            return false;
        }
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityChangeInfo.visibilityChange) {
            return visibilityChangeInfo.startVisibility == 0 || visibilityChangeInfo.endVisibility == 0;
        }
        return false;
    }
}
