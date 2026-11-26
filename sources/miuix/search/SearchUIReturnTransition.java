package miuix.search;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.transition.MiuixTransitionUtils;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import java.lang.ref.WeakReference;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.internal.util.ViewUtils;
import miuix.transition.Fade;
import miuix.transition.TransitionValues;

/* JADX INFO: loaded from: classes6.dex */
public class SearchUIReturnTransition extends Fade {
    private static final String PROPNAME_BOUNDS = "miuix:searchuiexit:bounds";
    private static final String PROPNAME_WINDOW_X = "miuix:searchuiexit:windowX";
    private static final String PROPNAME_WINDOW_Y = "miuix:searchuiexit:windowY";
    private final BottomProperty mBottomProperty;
    private ImageView mContainerSnapShot;
    private final LeftProperty mLeftProperty;
    private final View mOuterSearchIcon;
    private final Rect mOuterSearchRect;
    private final RightProperty mRightProperty;
    private final int mSearchIconStartX;
    private final int mSearchIconStartY;
    private int[] mTempLocation;
    private final TopProperty mTopProperty;
    private static final AnimConfig ANIMCONFIG_CANCEL_BTN = new AnimConfig();
    private static final AnimConfig ANIMCONFIG_SEARCH_INPUT = new AnimConfig();
    private static final AnimConfig ANIMCONFIG_SEARCH_ICON = new AnimConfig();
    private static final AnimConfig ANIMCONFIG_SEARCH_BG = new AnimConfig();

    public SearchUIReturnTransition(View view) {
        super(2);
        this.mTempLocation = new int[2];
        this.mLeftProperty = new LeftProperty();
        this.mTopProperty = new TopProperty();
        this.mRightProperty = new RightProperty();
        this.mBottomProperty = new BottomProperty();
        this.mOuterSearchIcon = view;
        this.mOuterSearchRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.getLocationInWindow(this.mTempLocation);
        int[] iArr = this.mTempLocation;
        this.mSearchIconStartX = iArr[0];
        this.mSearchIconStartY = iArr[1];
        initAnimConfig();
    }

    @Override // miuix.transition.Fade, miuix.transition.Visibility, miuix.transition.MiuixTransition
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        View view = transitionValues.view;
        if (view.getId() == R.id.container) {
            this.mContainerSnapShot = (ImageView) MiuixTransitionUtils.copyViewImage(view);
        }
    }

    @Override // miuix.transition.Visibility
    protected void captureValues(TransitionValues transitionValues) {
        super.captureValues(transitionValues);
        View view = transitionValues.view;
        view.getLocationInWindow(this.mTempLocation);
        transitionValues.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.mTempLocation[0]));
        transitionValues.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.mTempLocation[1]));
        transitionValues.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
    }

    @Override // miuix.transition.Fade, miuix.transition.Visibility
    public void onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2, TransitionListener transitionListener) {
        View view2 = transitionValues.view;
        if (view2.getId() == R.id.endView) {
            createEndViewAnimator(viewGroup, view);
        } else if (view2.getId() == R.id.search_container) {
            createSearchContainerAnimator(viewGroup, view, transitionValues, transitionValues2);
        } else if (view2.getId() == R.id.container) {
            createContainerAnimator(viewGroup, transitionValues);
        }
    }

    private void createContainerAnimator(final ViewGroup viewGroup, TransitionValues transitionValues) {
        if (this.mContainerSnapShot != null) {
            View view = transitionValues.view;
            ViewUtils.setLeftTopRightBottom(this.mContainerSnapShot, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            viewGroup.getOverlay().add(this.mContainerSnapShot);
            AnimConfig animConfig = ANIMCONFIG_SEARCH_BG;
            animConfig.addListeners(new TransitionListener() { // from class: miuix.search.SearchUIReturnTransition.1
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    viewGroup.getOverlay().remove(SearchUIReturnTransition.this.mContainerSnapShot);
                    SearchUIReturnTransition.ANIMCONFIG_SEARCH_BG.removeListeners(this);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    viewGroup.getOverlay().remove(SearchUIReturnTransition.this.mContainerSnapShot);
                    SearchUIReturnTransition.ANIMCONFIG_SEARCH_BG.removeListeners(this);
                }
            });
            Folme.useAt(this.mContainerSnapShot).state().to(ViewProperty.AUTO_ALPHA, 0, animConfig);
        }
    }

    private void createSearchContainerAnimator(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        startSearchIconAnimator(viewGroup, transitionValues);
        startSearchInputAnimtor(viewGroup, view, transitionValues);
    }

    private void startSearchInputAnimtor(ViewGroup viewGroup, View view, TransitionValues transitionValues) {
        viewGroup.getLocationInWindow(this.mTempLocation);
        int iIntValue = ((Integer) transitionValues.values.get(PROPNAME_WINDOW_X)).intValue() - this.mTempLocation[0];
        int iIntValue2 = ((Integer) transitionValues.values.get(PROPNAME_WINDOW_Y)).intValue();
        int[] iArr = this.mTempLocation;
        int i = iArr[1];
        int i2 = iIntValue2 - i;
        int i3 = this.mSearchIconStartX - iArr[0];
        int i4 = this.mSearchIconStartY - i;
        int width = view.getWidth();
        int height = view.getHeight();
        int iWidth = this.mOuterSearchRect.width();
        int iHeight = this.mOuterSearchRect.height();
        int i5 = i3 - iIntValue;
        int i6 = i4 - i2;
        if (i5 == 0 && i6 == 0 && width == iWidth && height == iHeight) {
            return;
        }
        int left = view.getLeft();
        int top = view.getTop();
        int i7 = i5 + left;
        int i8 = i6 + top;
        AnimState animStateAdd = new AnimState().add((ViewProperty) this.mLeftProperty, left, 4).add((ViewProperty) this.mTopProperty, top, 4).add((ViewProperty) this.mRightProperty, view.getRight(), 4).add((ViewProperty) this.mBottomProperty, view.getBottom(), 4);
        ViewBounds viewBounds = new ViewBounds(view);
        Folme.useValue(viewBounds).setTo(animStateAdd);
        AnimState animStateAdd2 = new AnimState().add((ViewProperty) this.mLeftProperty, i7, 4).add((ViewProperty) this.mTopProperty, i8, 4).add((ViewProperty) this.mRightProperty, iWidth + i7, 4).add((ViewProperty) this.mBottomProperty, iHeight + i8, 4);
        AnimState animStateAdd3 = new AnimState().add(ViewProperty.AUTO_ALPHA, 0.0d);
        IStateStyle iStateStyleUseValue = Folme.useValue(viewBounds);
        AnimConfig animConfig = ANIMCONFIG_SEARCH_INPUT;
        iStateStyleUseValue.to(animStateAdd2, animConfig);
        Folme.useAt(view).state().to(animStateAdd3, animConfig);
    }

    private void startSearchIconAnimator(ViewGroup viewGroup, TransitionValues transitionValues) {
        this.mOuterSearchIcon.setVisibility(4);
        View view = transitionValues.view;
        View viewFindViewById = view.findViewById(R.id.icon);
        viewGroup.getLocationInWindow(this.mTempLocation);
        int x = (int) (view.getX() + viewFindViewById.getX());
        int y = (int) (view.getY() + viewFindViewById.getY());
        int width = viewFindViewById.getWidth();
        int height = viewFindViewById.getHeight();
        float alpha = viewFindViewById.getAlpha();
        int i = this.mSearchIconStartX;
        int[] iArr = this.mTempLocation;
        int i2 = i - iArr[0];
        int i3 = this.mSearchIconStartY - iArr[1];
        int iWidth = this.mOuterSearchRect.width();
        int iHeight = this.mOuterSearchRect.height();
        int i4 = i2 - x;
        int i5 = i3 - y;
        if (i4 == 0 && i5 == 0 && width == iWidth && height == iHeight) {
            return;
        }
        int i6 = this.mOuterSearchRect.left - i4;
        int i7 = this.mOuterSearchRect.top - i5;
        int i8 = this.mOuterSearchRect.left;
        int i9 = this.mOuterSearchRect.top;
        AnimState animStateAdd = new AnimState().add((ViewProperty) this.mLeftProperty, i6, 4).add((ViewProperty) this.mTopProperty, i7, 4).add((ViewProperty) this.mRightProperty, width + i6, 4).add((ViewProperty) this.mBottomProperty, height + i7, 4);
        ViewBounds viewBounds = new ViewBounds(this.mOuterSearchIcon);
        Folme.useValue(viewBounds).setTo(animStateAdd);
        AnimState animStateAdd2 = new AnimState().add((ViewProperty) this.mLeftProperty, i8, 4).add((ViewProperty) this.mTopProperty, i9, 4).add((ViewProperty) this.mRightProperty, iWidth + i8, 4).add((ViewProperty) this.mBottomProperty, iHeight + i9, 4);
        IStateStyle iStateStyleUseValue = Folme.useValue(viewBounds);
        AnimConfig animConfig = ANIMCONFIG_SEARCH_ICON;
        iStateStyleUseValue.to(animStateAdd2, animConfig);
        AnimState animStateAdd3 = new AnimState().add(ViewProperty.ALPHA, alpha);
        AnimState animStateAdd4 = new AnimState().add(ViewProperty.AUTO_ALPHA, 1.0f);
        Folme.useAt(this.mOuterSearchIcon).state().setTo(animStateAdd3);
        Folme.useAt(this.mOuterSearchIcon).state().to(animStateAdd4, animConfig);
    }

    private void createEndViewAnimator(ViewGroup viewGroup, View view) {
        Folme.useAt(view).state().to(new AnimState().add(ViewProperty.X, 50, 1).add(ViewProperty.AUTO_ALPHA, 0.0d), ANIMCONFIG_CANCEL_BTN);
    }

    private void initAnimConfig() {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.35f);
        AnimConfig animConfig = ANIMCONFIG_SEARCH_BG;
        animConfig.setEase(style);
        ANIMCONFIG_CANCEL_BTN.setSpecial(ViewProperty.AUTO_ALPHA, EaseManager.getStyle(-2, 0.95f, 0.15f), new float[0]);
        ANIMCONFIG_SEARCH_ICON.setEase(style);
        ANIMCONFIG_SEARCH_INPUT.setEase(style);
        setAnimConfig(animConfig);
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

    protected static class LeftProperty extends ViewProperty implements IIntValueProperty<SearchUIEnterTransition.ViewBounds> {
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
        public void setIntValue(SearchUIEnterTransition.ViewBounds viewBounds, int i) {
            viewBounds.setLeft(i);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(SearchUIEnterTransition.ViewBounds viewBounds) {
            return viewBounds.getLeft();
        }
    }

    protected static class TopProperty extends ViewProperty implements IIntValueProperty<SearchUIEnterTransition.ViewBounds> {
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
        public void setIntValue(SearchUIEnterTransition.ViewBounds viewBounds, int i) {
            viewBounds.setTop(i);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(SearchUIEnterTransition.ViewBounds viewBounds) {
            return viewBounds.getTop();
        }
    }

    protected static class RightProperty extends ViewProperty implements IIntValueProperty<SearchUIEnterTransition.ViewBounds> {
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
        public void setIntValue(SearchUIEnterTransition.ViewBounds viewBounds, int i) {
            viewBounds.setRight(i);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(SearchUIEnterTransition.ViewBounds viewBounds) {
            return viewBounds.getRight();
        }
    }

    protected static class BottomProperty extends ViewProperty implements IIntValueProperty<SearchUIEnterTransition.ViewBounds> {
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
        public void setIntValue(SearchUIEnterTransition.ViewBounds viewBounds, int i) {
            viewBounds.setBottom(i);
        }

        @Override // miuix.animation.property.IIntValueProperty
        public int getIntValue(SearchUIEnterTransition.ViewBounds viewBounds) {
            return viewBounds.getBottom();
        }
    }
}
