package miuix.viewpager.widget;

import android.util.Pair;
import android.view.View;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes6.dex */
public class SharedElementTransformer implements OnPageChangeListener {
    private PagerAdapter mPagerAdapter;
    private Set<Pair<Integer, Integer>> mSharedElementIds = new HashSet();

    @Override // miuix.viewpager.widget.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // miuix.viewpager.widget.OnPageChangeListener
    public void onPageSelected(int i) {
    }

    public SharedElementTransformer(PagerAdapter pagerAdapter) {
        this.mPagerAdapter = pagerAdapter;
    }

    @Override // miuix.viewpager.widget.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
        View view;
        View view2;
        SharedElementTransformer sharedElementTransformer;
        int i3 = i + 1;
        PagerAdapter pagerAdapter = this.mPagerAdapter;
        if (pagerAdapter == null) {
            return;
        }
        if (i3 >= pagerAdapter.getCount()) {
            resetView(i);
            return;
        }
        for (Pair<Integer, Integer> pair : this.mSharedElementIds) {
            Integer num = (Integer) pair.first;
            Integer num2 = (Integer) pair.second;
            View view3 = this.mPagerAdapter.getView(i);
            View view4 = this.mPagerAdapter.getView(i3);
            if (view3 == null || view4 == null) {
                view = null;
                view2 = null;
            } else {
                View viewFindViewById = view3.findViewById(num.intValue());
                View viewFindViewById2 = view4.findViewById(num2.intValue());
                if (viewFindViewById == null) {
                    viewFindViewById = null;
                }
                if (viewFindViewById2 != null) {
                    view2 = viewFindViewById2;
                    view = viewFindViewById;
                } else {
                    view = viewFindViewById;
                    view2 = null;
                }
            }
            if (view == null || view2 == null) {
                sharedElementTransformer = this;
            } else {
                sharedElementTransformer = this;
                sharedElementTransformer.modifyPositions(view, view2, view3, view4, view3, -f);
                sharedElementTransformer.modifyPositions(view, view2, view3, view4, view4, 1.0f - f);
            }
            this = sharedElementTransformer;
        }
    }

    public void addElement(int i, int i2) {
        this.mSharedElementIds.add(new Pair<>(Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public void removeElement(int i, int i2) {
        this.mSharedElementIds.remove(new Pair(Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public void clearElements() {
        this.mSharedElementIds.clear();
    }

    private void modifyPositions(View view, View view2, View view3, View view4, View view5, float f) {
        float left = view2.getLeft() - view.getLeft();
        float top = view2.getTop() - view.getTop();
        float left2 = view4.getLeft() - view3.getLeft();
        float width = view.getWidth();
        float height = view.getHeight();
        float width2 = view2.getWidth();
        float height2 = view2.getHeight();
        float f2 = width2 - width;
        float f3 = height2 - height;
        int id = view.getId();
        int id2 = view2.getId();
        if (-1.0f < f) {
            if (f < 1.0f) {
                float f4 = -f;
                float f5 = (top + (f3 / 2.0f)) * f4;
                float f6 = (left + left2 + (f2 / 2.0f)) * f4;
                if (view5.findViewById(id) != null) {
                    view.setTranslationX(f6);
                    view.setTranslationY(f5);
                    float f7 = width == 0.0f ? 1.0f : ((f2 * f4) + width) / width;
                    float f8 = height == 0.0f ? 1.0f : ((f3 * f4) + height) / height;
                    view.setScaleX(f7);
                    view.setScaleY(f8);
                }
                if (view5.findViewById(id2) != null) {
                    view2.setTranslationX(f6);
                    view2.setTranslationY(f5);
                    float f9 = width2 == 0.0f ? 1.0f : ((f2 * f4) + width2) / width2;
                    float f10 = height2 != 0.0f ? ((f3 * f4) + height2) / height2 : 1.0f;
                    view2.setScaleX(f9);
                    view2.setScaleY(f10);
                }
            }
        }
    }

    private void resetView(int i) {
        View view;
        PagerAdapter pagerAdapter = this.mPagerAdapter;
        if (pagerAdapter == null || (view = pagerAdapter.getView(i)) == null) {
            return;
        }
        for (Pair<Integer, Integer> pair : this.mSharedElementIds) {
            View viewFindViewById = view.findViewById(((Integer) pair.first).intValue());
            View viewFindViewById2 = view.findViewById(((Integer) pair.second).intValue());
            if (viewFindViewById != null) {
                viewFindViewById.setTranslationX(0.0f);
                viewFindViewById.setTranslationY(0.0f);
                viewFindViewById.setScaleX(1.0f);
                viewFindViewById.setScaleY(1.0f);
            }
            if (viewFindViewById2 != null) {
                viewFindViewById2.setTranslationX(0.0f);
                viewFindViewById2.setTranslationY(0.0f);
                viewFindViewById2.setScaleX(1.0f);
                viewFindViewById2.setScaleY(1.0f);
            }
        }
    }
}
