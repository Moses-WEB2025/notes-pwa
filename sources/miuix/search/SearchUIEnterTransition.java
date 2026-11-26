package miuix.search;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import java.lang.ref.WeakReference;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.internal.util.ViewUtils;
import miuix.transition.Fade;
import miuix.transition.MiuixTransition;
import miuix.transition.TransitionValues;

/* JADX INFO: loaded from: classes6.dex */
public final class SearchUIEnterTransition extends Fade {
    private static final String PROPNAME_BOUNDS = "android:transition:bounds";
    private static final String PROPNAME_TAG = "android:transition:tag";
    private static final String PROPNAME_WINDOW_X = "android:transition:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:transition:windowY";
    private static final String PROPNAME_X = "android:transition:x";
    private static final String PROPNAME_Y = "android:transition:y";
    private static final String TAG = "SearchUiTransition";
    private final BottomProperty mBottomProperty;
    private final LeftProperty mLeftProperty;
    private final RightProperty mRightProperty;
    private final View mSearchIcon;
    private final int mSearchIconStartX;
    private final int mSearchIconStartY;
    private final Rect mStartRect;
    private int[] mTempLocation;
    private final TopProperty mTopProperty;
    private static final AnimConfig ANIMCONFIG_CANCEL_BTN = new AnimConfig();
    private static final AnimConfig ANIMCONFIG_SEARCH_INPUT = new AnimConfig();
    private static final AnimConfig ANIMCONFIG_SEARCH_ICON = new AnimConfig();
    private static final AnimConfig ANIMCONFIG_SEARCH_BG = new AnimConfig();

    public SearchUIEnterTransition(View view) {
        super(1);
        this.mTempLocation = new int[2];
        this.mLeftProperty = new LeftProperty();
        this.mTopProperty = new TopProperty();
        this.mRightProperty = new RightProperty();
        this.mBottomProperty = new BottomProperty();
        this.mStartRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        this.mSearchIcon = view;
        view.getLocationInWindow(this.mTempLocation);
        int[] iArr = this.mTempLocation;
        this.mSearchIconStartX = iArr[0];
        this.mSearchIconStartY = iArr[1];
        initAnimConfig();
    }

    @Override // miuix.transition.Fade, miuix.transition.Visibility, miuix.transition.MiuixTransition
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
        transitionValues.values.put(PROPNAME_TAG, true);
    }

    @Override // miuix.transition.Fade, miuix.transition.Visibility, miuix.transition.MiuixTransition
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
        transitionValues.values.put(PROPNAME_TAG, false);
    }

    @Override // miuix.transition.Visibility
    public void captureValues(TransitionValues transitionValues) {
        super.captureValues(transitionValues);
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        transitionValues.values.put(PROPNAME_X, Float.valueOf(view.getX()));
        transitionValues.values.put(PROPNAME_Y, Float.valueOf(view.getY()));
        view.getLocationInWindow(this.mTempLocation);
        transitionValues.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.mTempLocation[0]));
        transitionValues.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.mTempLocation[1]));
    }

    @Override // miuix.transition.Fade, miuix.transition.Visibility
    public void onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (view.getId() == R.id.endView) {
            createEndViewAnimator(viewGroup, transitionValues, transitionValues2);
        } else if (view.getId() == R.id.search_container) {
            createSearchInputAnimator(viewGroup, transitionValues, transitionValues2);
        } else {
            super.onAppear(viewGroup, view, transitionValues, transitionValues2);
        }
    }

    private void createEndViewAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 != null) {
            View view = transitionValues2.view;
            float x = view.getX() + 50.0f;
            float x2 = view.getX();
            AnimState animStateAdd = new AnimState().add(ViewProperty.X, x).add(ViewProperty.ALPHA, 0.0d);
            Folme.useAt(view).state().setTo(animStateAdd);
            addTransitionRunner(new MiuixTransition.TransitionRunner(view, animStateAdd, new AnimState().add(ViewProperty.X, x2).add(ViewProperty.ALPHA, 1.0d), ANIMCONFIG_CANCEL_BTN));
        }
    }

    private void createSearchInputAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        View view = transitionValues2.view;
        createSearchIconAnimator(viewGroup, view.findViewById(R.id.icon), transitionValues2);
        createSearchInputAnimator(viewGroup, view, transitionValues2);
    }

    private void createSearchInputAnimator(ViewGroup viewGroup, View view, TransitionValues transitionValues) {
        Rect rect = (Rect) transitionValues.values.get(PROPNAME_BOUNDS);
        int i = this.mStartRect.left;
        int i2 = this.mStartRect.right;
        int i3 = rect.left;
        int i4 = rect.right;
        AnimState animStateAdd = new AnimState().add((ViewProperty) this.mLeftProperty, i, 4).add((ViewProperty) this.mRightProperty, i2, 4);
        ViewBounds viewBounds = new ViewBounds(view);
        Folme.useValue(viewBounds).setTo(animStateAdd);
        addTransitionRunner(new MiuixTransition.TransitionRunner(viewBounds, animStateAdd, new AnimState().add((ViewProperty) this.mLeftProperty, i3, 4).add((ViewProperty) this.mRightProperty, i4, 4), ANIMCONFIG_SEARCH_INPUT));
    }

    private void createSearchIconAnimator(final ViewGroup viewGroup, final View view, TransitionValues transitionValues) {
        view.setVisibility(4);
        viewGroup.getLocationInWindow(this.mTempLocation);
        int i = this.mSearchIconStartX;
        int[] iArr = this.mTempLocation;
        int i2 = i - iArr[0];
        int i3 = this.mSearchIconStartY - iArr[1];
        int[] iArr2 = new int[2];
        view.getLocationInWindow(iArr2);
        int i4 = iArr2[0];
        int[] iArr3 = this.mTempLocation;
        int i5 = i4 - iArr3[0];
        int i6 = iArr2[1] - iArr3[1];
        int iWidth = this.mStartRect.width();
        int width = view.getWidth();
        int iHeight = this.mStartRect.height();
        int height = view.getHeight();
        int i7 = i5 - i2;
        int i8 = i6 - i3;
        if (i2 == i5 && i3 == i6 && iWidth == width && iHeight == height) {
            return;
        }
        viewGroup.getOverlay().clear();
        viewGroup.getOverlay().add(this.mSearchIcon);
        int i9 = this.mStartRect.left;
        int i10 = this.mStartRect.top;
        int i11 = this.mStartRect.left + i7;
        int i12 = this.mStartRect.top + i8;
        AnimState animStateAdd = new AnimState().add((ViewProperty) this.mLeftProperty, i9, 4).add((ViewProperty) this.mTopProperty, i10, 4).add((ViewProperty) this.mRightProperty, iWidth + i9, 4).add((ViewProperty) this.mBottomProperty, iHeight + i10, 4);
        AnimState animStateAdd2 = new AnimState().add((ViewProperty) this.mLeftProperty, i11, 4).add((ViewProperty) this.mTopProperty, i12, 4).add((ViewProperty) this.mRightProperty, width + i11, 4).add((ViewProperty) this.mBottomProperty, height + i12, 4);
        AnimConfig animConfig = ANIMCONFIG_SEARCH_ICON;
        animConfig.addListeners(new TransitionListener() { // from class: miuix.search.SearchUIEnterTransition.1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                view.setVisibility(0);
                viewGroup.getOverlay().remove(SearchUIEnterTransition.this.mSearchIcon);
                SearchUIEnterTransition.ANIMCONFIG_SEARCH_ICON.removeListeners(this);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                viewGroup.getOverlay().remove(SearchUIEnterTransition.this.mSearchIcon);
                SearchUIEnterTransition.ANIMCONFIG_SEARCH_ICON.removeListeners(this);
            }
        });
        ViewBounds viewBounds = new ViewBounds(this.mSearchIcon);
        Folme.useValue(viewBounds).setTo(animStateAdd);
        Folme.useValue(viewBounds).fromTo(animStateAdd, animStateAdd2, animConfig);
        AnimState animStateAdd3 = new AnimState().add(ViewProperty.ALPHA, 1.0f);
        AnimState animStateAdd4 = new AnimState().add(ViewProperty.ALPHA, 0.4f);
        Folme.useAt(this.mSearchIcon).state().setTo(animStateAdd3);
        Folme.useAt(this.mSearchIcon).state().to(animStateAdd4, animConfig);
    }

    private void initAnimConfig() {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.35f);
        EaseManager.EaseStyle style2 = EaseManager.getStyle(-2, 0.95f, 0.2f);
        AnimConfig animConfig = ANIMCONFIG_SEARCH_BG;
        animConfig.setEase(style2);
        AnimConfig animConfig2 = ANIMCONFIG_CANCEL_BTN;
        animConfig2.setEase(style);
        animConfig2.setSpecial(ViewProperty.ALPHA, style2, 100L, new float[0]);
        AnimConfig animConfig3 = ANIMCONFIG_SEARCH_INPUT;
        animConfig3.setEase(style);
        animConfig3.setSpecial(this.mLeftProperty, style2, new float[0]).setSpecial(this.mTopProperty, style2, new float[0]).setSpecial(this.mRightProperty, style2, new float[0]).setSpecial(this.mBottomProperty, style2, new float[0]);
        AnimConfig animConfig4 = ANIMCONFIG_SEARCH_ICON;
        animConfig4.setEase(style);
        setAnimConfig(animConfig);
        animConfig.enableStartImmediately(true);
        animConfig2.enableStartImmediately(true);
        animConfig3.enableStartImmediately(true);
        animConfig4.enableStartImmediately(true);
    }

    protected static class ViewBounds {
        private WeakReference<View> mView;
        int left = -1;
        int top = -1;
        int right = -1;
        int bottom = -1;

        ViewBounds(View view) {
            this.mView = new WeakReference<>(view);
        }

        public void setLeft(int i) {
            this.left = i;
            setLeftTopRightBottom();
        }

        int getLeft() {
            return this.left;
        }

        public void setTop(int i) {
            this.top = i;
            setLeftTopRightBottom();
        }

        int getTop() {
            return this.top;
        }

        public void setRight(int i) {
            this.right = i;
            setLeftTopRightBottom();
        }

        int getRight() {
            return this.right;
        }

        public void setBottom(int i) {
            this.bottom = i;
            setLeftTopRightBottom();
        }

        int getBottom() {
            return this.bottom;
        }

        private void setLeftTopRightBottom() {
            View view = this.mView.get();
            if (view != null) {
                int left = this.left;
                if (left == -1) {
                    left = view.getLeft();
                }
                int top = this.top;
                if (top == -1) {
                    top = view.getTop();
                }
                int right = this.right;
                if (right == -1) {
                    right = view.getRight();
                }
                int bottom = this.bottom;
                if (bottom == -1) {
                    bottom = view.getBottom();
                }
                ViewUtils.setLeftTopRightBottom(view, left, top, right, bottom);
            }
        }
    }

    protected static class LeftProperty extends ViewProperty implements IIntValueProperty<ViewBounds> {
        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            return 0.0f;
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
        }

        public LeftProperty() {
            super("left");
        }

        @Override // miuix.animation.property.IIntValueProperty
        public void setIntValue(ViewBounds viewBounds, int i) {
            viewBounds.setLeft(i);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(ViewBounds viewBounds) {
            return viewBounds.getLeft();
        }
    }

    protected static class TopProperty extends ViewProperty implements IIntValueProperty<ViewBounds> {
        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            return 0.0f;
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
        }

        public TopProperty() {
            super(Layer.KEY_TOP);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public void setIntValue(ViewBounds viewBounds, int i) {
            viewBounds.setTop(i);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(ViewBounds viewBounds) {
            return viewBounds.getTop();
        }
    }

    protected static class RightProperty extends ViewProperty implements IIntValueProperty<ViewBounds> {
        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            return 0.0f;
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
        }

        public RightProperty() {
            super(EditStat.ELEMENT_RIGHT);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public void setIntValue(ViewBounds viewBounds, int i) {
            viewBounds.setRight(i);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(ViewBounds viewBounds) {
            return viewBounds.getRight();
        }
    }

    protected static class BottomProperty extends ViewProperty implements IIntValueProperty<ViewBounds> {
        @Override // miuix.animation.property.FloatProperty
        public float getValue(View view) {
            return 0.0f;
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(View view, float f) {
        }

        public BottomProperty() {
            super("bottom");
        }

        @Override // miuix.animation.property.IIntValueProperty
        public void setIntValue(ViewBounds viewBounds, int i) {
            viewBounds.setBottom(i);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(ViewBounds viewBounds) {
            return viewBounds.getBottom();
        }
    }
}
