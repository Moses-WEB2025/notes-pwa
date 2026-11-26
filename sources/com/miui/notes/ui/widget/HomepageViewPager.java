package com.miui.notes.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.notes.page.BaseNoteListPageFragment;
import com.miui.notes.page.PadTodoPageContainerFragment;
import com.miui.notes.page.TodoListPageFragment;
import com.miui.todo.utils.TodoUtils;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.Fragment;
import miuix.viewpager2.widget.ViewPager2;

/* JADX INFO: compiled from: HomepageViewPager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\bJ\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010\u001a\u001a\u00020\u000e2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000eJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020\u001fR\u0014\u0010\t\u001a\u00020\nX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014¨\u0006!"}, d2 = {"Lcom/miui/notes/ui/widget/HomepageViewPager;", "Lmiuix/viewpager2/widget/ViewPager2;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "(Landroid/content/Context;)V", "TAG", "", "getTAG", "()Ljava/lang/String;", "isScroll", "", "mScaledTouchSlop", "", "gestureDetector", "Landroid/view/GestureDetector;", "getGestureDetector", "()Landroid/view/GestureDetector;", "gestureDetector$delegate", "Lkotlin/Lazy;", "onInterceptTouchEvent", "ev", "Landroid/view/MotionEvent;", "onTouchEvent", "setScroll", "", "scroll", "findCurrentFragment", "Lmiuix/appcompat/app/Fragment;", "host", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HomepageViewPager extends ViewPager2 {
    private final String TAG;

    /* JADX INFO: renamed from: gestureDetector$delegate, reason: from kotlin metadata */
    private final Lazy gestureDetector;
    private boolean isScroll;
    private int mScaledTouchSlop;

    public final String getTAG() {
        return this.TAG;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomepageViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.TAG = "HomepageViewPager";
        this.gestureDetector = LazyKt.lazy(new Function0() { // from class: com.miui.notes.ui.widget.HomepageViewPager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return HomepageViewPager.gestureDetector_delegate$lambda$0(this.f$0);
            }
        });
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomepageViewPager(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.TAG = "HomepageViewPager";
        this.gestureDetector = LazyKt.lazy(new Function0() { // from class: com.miui.notes.ui.widget.HomepageViewPager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return HomepageViewPager.gestureDetector_delegate$lambda$0(this.f$0);
            }
        });
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private final GestureDetector getGestureDetector() {
        return (GestureDetector) this.gestureDetector.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final GestureDetector gestureDetector_delegate$lambda$0(final HomepageViewPager homepageViewPager) {
        return new GestureDetector(homepageViewPager.getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.miui.notes.ui.widget.HomepageViewPager$gestureDetector$2$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Intrinsics.checkNotNullParameter(e2, "e2");
                if (Math.abs(distanceX) > Math.abs(distanceY) && !this.this$0.isScroll && !TodoUtils.isMenuSwiping()) {
                    Logger.INSTANCE.e(this.this$0.getTAG(), "GestureDetector onScroll intercept!!!");
                    return true;
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Intrinsics.checkNotNullParameter(e2, "e2");
                Logger.INSTANCE.d(this.this$0.getTAG(), "GestureDetector onFling " + velocityY + " vs " + velocityY);
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        return getGestureDetector().onTouchEvent(ev);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.isScroll) {
            return true;
        }
        try {
            return super.onTouchEvent(ev);
        } catch (Exception e) {
            Logger.INSTANCE.e(this.TAG, "onTouchEvent with exception " + e.getMessage());
            return true;
        }
    }

    public final void setScroll(boolean scroll) {
        this.isScroll = scroll;
    }

    public final Fragment findCurrentFragment(Fragment host) {
        Intrinsics.checkNotNullParameter(host, "host");
        List<androidx.fragment.app.Fragment> fragments = host.getChildFragmentManager().getFragments();
        Intrinsics.checkNotNullExpressionValue(fragments, "getFragments(...)");
        int size = fragments.size();
        Fragment fragment = null;
        int i = 0;
        while (i < size) {
            androidx.fragment.app.Fragment fragment2 = fragments.get(i);
            Fragment fragment3 = (Fragment) fragment2;
            Logger.INSTANCE.d(this.TAG, "findCurrentFragment: fragment child : " + i + "->" + fragment2 + " ,totalSize: " + size + "," + getCurrentItem());
            int currentItem = getCurrentItem();
            if (currentItem != 0) {
                if (currentItem == 1) {
                    if (fragment3 instanceof PadTodoPageContainerFragment) {
                        com.miui.notes.base.utils.Logger.INSTANCE.i(this.TAG, "findCurrentFragment: fragment child : " + i + "->" + fragment2 + ", find target!!!");
                    } else if (fragment3 instanceof TodoListPageFragment) {
                        com.miui.notes.base.utils.Logger.INSTANCE.i(this.TAG, "findCurrentFragment: fragment child : " + i + "->" + fragment2 + ", find target!!!");
                    } else {
                        Logger.INSTANCE.d(this.TAG, "findCurrentFragment: fragment child : " + i + "->" + fragment2 + ",can not found target");
                    }
                    fragment = fragment3;
                    break;
                }
                continue;
                i++;
                fragment = fragment3;
            } else {
                if (fragment2 instanceof BaseNoteListPageFragment) {
                    com.miui.notes.base.utils.Logger.INSTANCE.i(this.TAG, "findCurrentFragment: fragment child : " + i + "->" + fragment2 + ", find target!!!");
                    fragment = fragment3;
                    break;
                }
                Logger.INSTANCE.d(this.TAG, "findCurrentFragment: fragment child : " + i + ",can not find target");
                i++;
                fragment = fragment3;
            }
        }
        Logger.INSTANCE.d(this.TAG, "findCurrentFragment: fragment target current: " + fragment);
        return fragment;
    }
}
