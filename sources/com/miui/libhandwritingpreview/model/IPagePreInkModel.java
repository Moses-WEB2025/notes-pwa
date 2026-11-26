package com.miui.libhandwritingpreview.model;

import android.content.Context;
import android.content.res.Configuration;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.libhandwritingpage.listener.PageFooterView;
import com.miui.libhandwritingpage.listener.PageLoadListener;
import com.miui.libhandwritingpage.listener.PageStateListener;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageBean;
import com.miui.libhandwritingpreview.adapter.PagePreAdapter;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.manager.PagePreLayoutManager;
import com.miui.libhandwritingpreview.system.PreCamera;
import com.miui.libhandwritingpreview.view.CreationShieldPrePageInkView;
import com.miui.libhandwritingpreview.view.PagePreDrawView;
import com.miui.libhandwritingpreview.view.PagePreItemView;
import com.miui.maml.data.VariableNames;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.utils.EngineConfig;

/* JADX INFO: compiled from: IPagePreInkModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u001c\b&\u0018\u0000 ½\u00012\u00020\u00012\u00020\u00022\u00020\u0003:\u0004¼\u0001½\u0001B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0014\u0010\u0090\u0001\u001a\u00030\u0091\u00012\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001H\u0016J\b\u0010\u0094\u0001\u001a\u00030\u0091\u0001J\b\u0010\u0095\u0001\u001a\u00030\u0091\u0001J\u0013\u0010\u0096\u0001\u001a\u0002092\b\u0010\u0097\u0001\u001a\u00030\u0098\u0001H\u0016J\u0013\u0010\u0099\u0001\u001a\u0002092\b\u0010\u0097\u0001\u001a\u00030\u0098\u0001H\u0016J\u0013\u0010\u009a\u0001\u001a\u0002092\b\u0010\u0097\u0001\u001a\u00030\u0098\u0001H\u0016J\u0013\u0010\u009b\u0001\u001a\u0002092\b\u0010\u0097\u0001\u001a\u00030\u0098\u0001H\u0016J\u0014\u0010\u009c\u0001\u001a\u00030\u0091\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u0001H\u0016J\u0013\u0010\u009d\u0001\u001a\u0002092\b\u0010\u0097\u0001\u001a\u00030\u0098\u0001H\u0016J3\u0010\u009e\u0001\u001a\u0002092\n\u0010\u009f\u0001\u001a\u0005\u0018\u00010\u0098\u00012\b\u0010 \u0001\u001a\u00030\u0098\u00012\b\u0010¡\u0001\u001a\u00030¢\u00012\b\u0010£\u0001\u001a\u00030¢\u0001H\u0016J\u001e\u0010¤\u0001\u001a\u00030\u0091\u00012\b\u0010¡\u0001\u001a\u00030¢\u00012\b\u0010£\u0001\u001a\u00030¢\u0001H\u0002J\u001e\u0010¥\u0001\u001a\u00030\u0091\u00012\b\u0010¦\u0001\u001a\u00030¢\u00012\b\u0010§\u0001\u001a\u00030¢\u0001H\u0002J\u0014\u0010¨\u0001\u001a\u00030\u0091\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u0001H\u0016J3\u0010©\u0001\u001a\u0002092\n\u0010ª\u0001\u001a\u0005\u0018\u00010\u0098\u00012\b\u0010«\u0001\u001a\u00030\u0098\u00012\b\u0010¦\u0001\u001a\u00030¢\u00012\b\u0010§\u0001\u001a\u00030¢\u0001H\u0016J\u0012\u0010¬\u0001\u001a\u0002092\u0007\u0010\u00ad\u0001\u001a\u00020\u000fH\u0016J\u0012\u0010®\u0001\u001a\u0002092\u0007\u0010\u00ad\u0001\u001a\u00020\u000fH\u0016J\u0013\u0010¯\u0001\u001a\u00030\u0091\u00012\u0007\u0010\u00ad\u0001\u001a\u00020\u000fH\u0016J\u0013\u0010°\u0001\u001a\u00030\u0091\u00012\t\u0010±\u0001\u001a\u0004\u0018\u00010JJ,\u0010²\u0001\u001a\u00030\u0091\u00012\u0007\u0010³\u0001\u001a\u00020?2\u0007\u0010´\u0001\u001a\u00020?2\u0007\u0010µ\u0001\u001a\u00020?2\u0007\u0010¶\u0001\u001a\u00020?J\u0012\u0010·\u0001\u001a\u00030\u0091\u00012\b\u0010¸\u0001\u001a\u00030\u0085\u0001J\b\u0010¹\u0001\u001a\u00030\u0091\u0001R\u0010\u0010\u0004\u001a\u00020\u00058\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\u0004\u0018\u00010!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010,\u001a\u0004\u0018\u00010-X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001c\u00102\u001a\u0004\u0018\u000103X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001a\u00108\u001a\u000209X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001a\u0010>\u001a\u00020?X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u001a\u0010D\u001a\u00020?X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010A\"\u0004\bF\u0010CR\u001a\u0010G\u001a\u000209X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010;\"\u0004\bH\u0010=R\u001c\u0010I\u001a\u0004\u0018\u00010JX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010L\"\u0004\bM\u0010NR\u001c\u0010O\u001a\u0004\u0018\u00010PX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u001a\u0010U\u001a\u00020?X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010A\"\u0004\bW\u0010CR\u001a\u0010X\u001a\u00020?X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010A\"\u0004\bZ\u0010CR\u001a\u0010[\u001a\u00020?X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010A\"\u0004\b]\u0010CR\u001a\u0010^\u001a\u00020?X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b_\u0010A\"\u0004\b`\u0010CR\u001a\u0010a\u001a\u000209X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010;\"\u0004\bb\u0010=R\u001a\u0010c\u001a\u00020dX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010f\"\u0004\bg\u0010hR\u001a\u0010i\u001a\u000209X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010;\"\u0004\bj\u0010=R\u001a\u0010k\u001a\u000209X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bk\u0010;\"\u0004\bl\u0010=R\u001a\u0010m\u001a\u00020nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR\u001c\u0010s\u001a\u0004\u0018\u00010tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bu\u0010v\"\u0004\bw\u0010xR\u001a\u0010y\u001a\u00020?X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u0010A\"\u0004\b{\u0010CR\u001a\u0010|\u001a\u000209X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b}\u0010;\"\u0004\b~\u0010=R\u001b\u0010\u007f\u001a\u000209X\u0096\u000e¢\u0006\u000f\n\u0000\u001a\u0004\b\u007f\u0010;\"\u0005\b\u0080\u0001\u0010=R\u001d\u0010\u0081\u0001\u001a\u000209X\u0084\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0081\u0001\u0010;\"\u0005\b\u0082\u0001\u0010=R\u000f\u0010\u0083\u0001\u001a\u000209X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u0084\u0001\u001a\u0005\u0018\u00010\u0085\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001\"\u0006\b\u0088\u0001\u0010\u0089\u0001R\"\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u008c\u0001\u0010\u008d\u0001\"\u0006\b\u008e\u0001\u0010\u008f\u0001R\u0013\u0010º\u0001\u001a\u00020?8F¢\u0006\u0007\u001a\u0005\b»\u0001\u0010A¨\u0006¾\u0001"}, d2 = {"Lcom/miui/libhandwritingpreview/model/IPagePreInkModel;", "Landroid/view/GestureDetector$OnGestureListener;", "Landroid/view/GestureDetector$OnDoubleTapListener;", "Landroid/view/ScaleGestureDetector$OnScaleGestureListener;", "mPageInkView", "Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;)V", "mGestureDetector", "Landroid/view/GestureDetector;", "getMGestureDetector", "()Landroid/view/GestureDetector;", "setMGestureDetector", "(Landroid/view/GestureDetector;)V", "mScaleGestureDetector", "Landroid/view/ScaleGestureDetector;", "getMScaleGestureDetector", "()Landroid/view/ScaleGestureDetector;", "setMScaleGestureDetector", "(Landroid/view/ScaleGestureDetector;)V", "mContext", "Landroid/content/Context;", "getMContext", "()Landroid/content/Context;", "setMContext", "(Landroid/content/Context;)V", "mPageBean", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageBean;", "getMPageBean", "()Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageBean;", "setMPageBean", "(Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageBean;)V", "mEntFilePath", "", "getMEntFilePath", "()Ljava/lang/String;", "setMEntFilePath", "(Ljava/lang/String;)V", "adapter", "Lcom/miui/libhandwritingpreview/adapter/PagePreAdapter;", "getAdapter", "()Lcom/miui/libhandwritingpreview/adapter/PagePreAdapter;", "setAdapter", "(Lcom/miui/libhandwritingpreview/adapter/PagePreAdapter;)V", "layoutManager", "Lcom/miui/libhandwritingpreview/manager/PagePreLayoutManager;", "getLayoutManager", "()Lcom/miui/libhandwritingpreview/manager/PagePreLayoutManager;", "setLayoutManager", "(Lcom/miui/libhandwritingpreview/manager/PagePreLayoutManager;)V", "currentItemView", "Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "getCurrentItemView", "()Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "setCurrentItemView", "(Lcom/miui/libhandwritingpreview/view/PagePreItemView;)V", "mIsFirstjump", "", "getMIsFirstjump", "()Z", "setMIsFirstjump", "(Z)V", "mFirstJumpPosition", "", "getMFirstJumpPosition", "()I", "setMFirstJumpPosition", "(I)V", "currentPosition", "getCurrentPosition", "setCurrentPosition", "isCancelTouch", "setCancelTouch", "mStateListener", "Lcom/miui/libhandwritingpage/listener/PageStateListener;", "getMStateListener", "()Lcom/miui/libhandwritingpage/listener/PageStateListener;", "setMStateListener", "(Lcom/miui/libhandwritingpage/listener/PageStateListener;)V", "mLoadListener", "Lcom/miui/libhandwritingpage/listener/PageLoadListener;", "getMLoadListener", "()Lcom/miui/libhandwritingpage/listener/PageLoadListener;", "setMLoadListener", "(Lcom/miui/libhandwritingpage/listener/PageLoadListener;)V", "mVisibleWidth", "getMVisibleWidth", "setMVisibleWidth", "mVisibleHeight", "getMVisibleHeight", "setMVisibleHeight", "pointCount", "getPointCount", "setPointCount", VariableNames.ORIENTATION, "getOrientation", "setOrientation", "isDownWithNavigationBar", "setDownWithNavigationBar", "hasDownTime", "", "getHasDownTime", "()J", "setHasDownTime", "(J)V", "isDrawing", "setDrawing", "isDownFlinging", "setDownFlinging", "engineConfig", "Lxc/engine/utils/EngineConfig;", "getEngineConfig", "()Lxc/engine/utils/EngineConfig;", "setEngineConfig", "(Lxc/engine/utils/EngineConfig;)V", "mFootView", "Lcom/miui/libhandwritingpage/listener/PageFooterView;", "getMFootView", "()Lcom/miui/libhandwritingpage/listener/PageFooterView;", "setMFootView", "(Lcom/miui/libhandwritingpage/listener/PageFooterView;)V", "downPos", "getDownPos", "setDownPos", "mIsScrollByTouch", "getMIsScrollByTouch", "setMIsScrollByTouch", "isUnlimited", "setUnlimited", "isScaling", "setScaling", "canSingerFingerScroll", "preViewClickListener", "Lcom/miui/libhandwritingpreview/model/IPagePreInkModel$IPreviewClickListener;", "getPreViewClickListener", "()Lcom/miui/libhandwritingpreview/model/IPagePreInkModel$IPreviewClickListener;", "setPreViewClickListener", "(Lcom/miui/libhandwritingpreview/model/IPagePreInkModel$IPreviewClickListener;)V", "mDrawView", "Lcom/miui/libhandwritingpreview/view/PagePreDrawView;", "getMDrawView", "()Lcom/miui/libhandwritingpreview/view/PagePreDrawView;", "setMDrawView", "(Lcom/miui/libhandwritingpreview/view/PagePreDrawView;)V", "onConfigurationChanged", "", "newConfig", "Landroid/content/res/Configuration;", "onLayout", "computeScroll", "onSingleTapConfirmed", "motionEvent", "Landroid/view/MotionEvent;", "onDoubleTap", "onDoubleTapEvent", "onDown", "onShowPress", "onSingleTapUp", "onScroll", "ev1", "ev2", "distanceX", "", "distanceY", "doScroll", "doFling", "velocityX", "velocityY", "onLongPress", "onFling", "p0", "motionEvent1", "onScale", "scaleGestureDetector", "onScaleBegin", "onScaleEnd", "setPageStateListener", "stateListener", "setPadding", "left", Layer.KEY_TOP, EditStat.ELEMENT_RIGHT, "bottom", "setClickListener", "clickListener", CallMethod.METHOD_UPDATE_TITLE, "toolViewHeight", "getToolViewHeight", "IPreviewClickListener", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class IPagePreInkModel implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {
    public static final int VISIBLE_SIZE = 6;
    private PagePreAdapter adapter;
    private boolean canSingerFingerScroll;
    private PagePreItemView currentItemView;
    private int currentPosition;
    private int downPos;
    private EngineConfig engineConfig;
    private long hasDownTime;
    private boolean isCancelTouch;
    private boolean isDownFlinging;
    private boolean isDownWithNavigationBar;
    private boolean isDrawing;
    private boolean isScaling;
    private boolean isUnlimited;
    private PagePreLayoutManager layoutManager;
    private Context mContext;
    private PagePreDrawView mDrawView;
    private String mEntFilePath;
    private int mFirstJumpPosition;
    private PageFooterView mFootView;
    private GestureDetector mGestureDetector;
    private boolean mIsFirstjump;
    private boolean mIsScrollByTouch;
    private PageLoadListener mLoadListener;
    private PageBean mPageBean;
    protected final CreationShieldPrePageInkView mPageInkView;
    private ScaleGestureDetector mScaleGestureDetector;
    private PageStateListener mStateListener;
    private int mVisibleHeight;
    private int mVisibleWidth;
    private int orientation;
    private int pointCount;
    private IPreviewClickListener preViewClickListener;

    /* JADX INFO: compiled from: IPagePreInkModel.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004À\u0006\u0003"}, d2 = {"Lcom/miui/libhandwritingpreview/model/IPagePreInkModel$IPreviewClickListener;", "", "onClick", "", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface IPreviewClickListener {
        void onClick();
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
        return false;
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
        return false;
    }

    public IPagePreInkModel(CreationShieldPrePageInkView mPageInkView) {
        Intrinsics.checkNotNullParameter(mPageInkView, "mPageInkView");
        this.mPageInkView = mPageInkView;
        Context context = mPageInkView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.mContext = context;
        this.mIsFirstjump = true;
        this.mFirstJumpPosition = -1;
        this.engineConfig = new EngineConfig();
        this.downPos = -1;
        this.isUnlimited = true;
        this.canSingerFingerScroll = true;
        this.mGestureDetector = new GestureDetector(this.mContext, this);
        this.mScaleGestureDetector = new ScaleGestureDetector(this.mContext, this);
        this.orientation = this.mContext.getResources().getConfiguration().orientation;
    }

    public final GestureDetector getMGestureDetector() {
        return this.mGestureDetector;
    }

    public final void setMGestureDetector(GestureDetector gestureDetector) {
        Intrinsics.checkNotNullParameter(gestureDetector, "<set-?>");
        this.mGestureDetector = gestureDetector;
    }

    public final ScaleGestureDetector getMScaleGestureDetector() {
        return this.mScaleGestureDetector;
    }

    public final void setMScaleGestureDetector(ScaleGestureDetector scaleGestureDetector) {
        Intrinsics.checkNotNullParameter(scaleGestureDetector, "<set-?>");
        this.mScaleGestureDetector = scaleGestureDetector;
    }

    public final Context getMContext() {
        return this.mContext;
    }

    public final void setMContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        this.mContext = context;
    }

    public final PageBean getMPageBean() {
        return this.mPageBean;
    }

    public final void setMPageBean(PageBean pageBean) {
        this.mPageBean = pageBean;
    }

    public final String getMEntFilePath() {
        return this.mEntFilePath;
    }

    public final void setMEntFilePath(String str) {
        this.mEntFilePath = str;
    }

    public final PagePreAdapter getAdapter() {
        return this.adapter;
    }

    public final void setAdapter(PagePreAdapter pagePreAdapter) {
        this.adapter = pagePreAdapter;
    }

    public final PagePreLayoutManager getLayoutManager() {
        return this.layoutManager;
    }

    public final void setLayoutManager(PagePreLayoutManager pagePreLayoutManager) {
        this.layoutManager = pagePreLayoutManager;
    }

    public final PagePreItemView getCurrentItemView() {
        return this.currentItemView;
    }

    public final void setCurrentItemView(PagePreItemView pagePreItemView) {
        this.currentItemView = pagePreItemView;
    }

    protected final boolean getMIsFirstjump() {
        return this.mIsFirstjump;
    }

    protected final void setMIsFirstjump(boolean z) {
        this.mIsFirstjump = z;
    }

    protected final int getMFirstJumpPosition() {
        return this.mFirstJumpPosition;
    }

    protected final void setMFirstJumpPosition(int i) {
        this.mFirstJumpPosition = i;
    }

    public final int getCurrentPosition() {
        return this.currentPosition;
    }

    public final void setCurrentPosition(int i) {
        this.currentPosition = i;
    }

    /* JADX INFO: renamed from: isCancelTouch, reason: from getter */
    protected final boolean getIsCancelTouch() {
        return this.isCancelTouch;
    }

    protected final void setCancelTouch(boolean z) {
        this.isCancelTouch = z;
    }

    protected final PageStateListener getMStateListener() {
        return this.mStateListener;
    }

    protected final void setMStateListener(PageStateListener pageStateListener) {
        this.mStateListener = pageStateListener;
    }

    protected final PageLoadListener getMLoadListener() {
        return this.mLoadListener;
    }

    protected final void setMLoadListener(PageLoadListener pageLoadListener) {
        this.mLoadListener = pageLoadListener;
    }

    protected final int getMVisibleWidth() {
        return this.mVisibleWidth;
    }

    protected final void setMVisibleWidth(int i) {
        this.mVisibleWidth = i;
    }

    protected final int getMVisibleHeight() {
        return this.mVisibleHeight;
    }

    protected final void setMVisibleHeight(int i) {
        this.mVisibleHeight = i;
    }

    protected final int getPointCount() {
        return this.pointCount;
    }

    protected final void setPointCount(int i) {
        this.pointCount = i;
    }

    protected final int getOrientation() {
        return this.orientation;
    }

    protected final void setOrientation(int i) {
        this.orientation = i;
    }

    /* JADX INFO: renamed from: isDownWithNavigationBar, reason: from getter */
    protected final boolean getIsDownWithNavigationBar() {
        return this.isDownWithNavigationBar;
    }

    protected final void setDownWithNavigationBar(boolean z) {
        this.isDownWithNavigationBar = z;
    }

    protected final long getHasDownTime() {
        return this.hasDownTime;
    }

    protected final void setHasDownTime(long j) {
        this.hasDownTime = j;
    }

    /* JADX INFO: renamed from: isDrawing, reason: from getter */
    public final boolean getIsDrawing() {
        return this.isDrawing;
    }

    public final void setDrawing(boolean z) {
        this.isDrawing = z;
    }

    /* JADX INFO: renamed from: isDownFlinging, reason: from getter */
    protected final boolean getIsDownFlinging() {
        return this.isDownFlinging;
    }

    protected final void setDownFlinging(boolean z) {
        this.isDownFlinging = z;
    }

    public EngineConfig getEngineConfig() {
        return this.engineConfig;
    }

    public void setEngineConfig(EngineConfig engineConfig) {
        Intrinsics.checkNotNullParameter(engineConfig, "<set-?>");
        this.engineConfig = engineConfig;
    }

    public final PageFooterView getMFootView() {
        return this.mFootView;
    }

    public final void setMFootView(PageFooterView pageFooterView) {
        this.mFootView = pageFooterView;
    }

    protected final int getDownPos() {
        return this.downPos;
    }

    protected final void setDownPos(int i) {
        this.downPos = i;
    }

    protected final boolean getMIsScrollByTouch() {
        return this.mIsScrollByTouch;
    }

    protected final void setMIsScrollByTouch(boolean z) {
        this.mIsScrollByTouch = z;
    }

    /* JADX INFO: renamed from: isUnlimited, reason: from getter */
    public boolean getIsUnlimited() {
        return this.isUnlimited;
    }

    public void setUnlimited(boolean z) {
        this.isUnlimited = z;
    }

    /* JADX INFO: renamed from: isScaling, reason: from getter */
    protected final boolean getIsScaling() {
        return this.isScaling;
    }

    protected final void setScaling(boolean z) {
        this.isScaling = z;
    }

    public final IPreviewClickListener getPreViewClickListener() {
        return this.preViewClickListener;
    }

    public final void setPreViewClickListener(IPreviewClickListener iPreviewClickListener) {
        this.preViewClickListener = iPreviewClickListener;
    }

    public final PagePreDrawView getMDrawView() {
        return this.mDrawView;
    }

    public final void setMDrawView(PagePreDrawView pagePreDrawView) {
        this.mDrawView = pagePreDrawView;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        if (this.orientation == newConfig.orientation) {
            return;
        }
        if (this.mPageInkView.getScrollY() != 0) {
            this.mPageInkView.scrollTo(0, 0);
        }
        this.orientation = newConfig.orientation;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void onLayout() {
        PreICreationEngine creationEngine;
        PagePreItemView pagePreItemView = this.currentItemView;
        if (pagePreItemView != null) {
            Intrinsics.checkNotNull(pagePreItemView);
            int i = this.mVisibleWidth;
            int i2 = this.mVisibleHeight;
            PagePreLayoutManager pagePreLayoutManager = this.layoutManager;
            Intrinsics.checkNotNull(pagePreLayoutManager);
            pagePreItemView.updateLayout(i, i2, pagePreLayoutManager.getStandardWidth());
        }
        if (!this.mIsScrollByTouch || getIsUnlimited() || this.mFootView == null) {
            return;
        }
        PagePreDrawView pagePreDrawView = this.mDrawView;
        PreMiCurveEngine curveEngine = (pagePreDrawView == null || (creationEngine = pagePreDrawView.getCreationEngine()) == null) ? null : creationEngine.getCurveEngine();
        if (curveEngine != null) {
            PageFooterView pageFooterView = this.mFootView;
            Intrinsics.checkNotNull(pageFooterView);
            boolean zUpdateFootView = pageFooterView.UpdateFootView(curveEngine.getViewportOffset().y, curveEngine.getPageSystem().getAllowedMaxHeight() * curveEngine.getScaleRatio(), this.mVisibleWidth, this.mVisibleHeight);
            PagePreLayoutManager pagePreLayoutManager2 = this.layoutManager;
            Intrinsics.checkNotNull(pagePreLayoutManager2);
            pagePreLayoutManager2.setDraggingState(zUpdateFootView ? 1 : 0);
        }
    }

    public final void computeScroll() {
        PagePreLayoutManager pagePreLayoutManager = this.layoutManager;
        Intrinsics.checkNotNull(pagePreLayoutManager);
        pagePreLayoutManager.computeScroll();
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
        PagePreLayoutManager pagePreLayoutManager = this.layoutManager;
        Intrinsics.checkNotNull(pagePreLayoutManager);
        pagePreLayoutManager.onTouchDown();
        this.canSingerFingerScroll = true;
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
        IPreviewClickListener iPreviewClickListener = this.preViewClickListener;
        if (iPreviewClickListener == null) {
            return false;
        }
        iPreviewClickListener.onClick();
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent ev1, MotionEvent ev2, float distanceX, float distanceY) {
        Intrinsics.checkNotNullParameter(ev2, "ev2");
        this.mIsScrollByTouch = true;
        if (this.hasDownTime < 100) {
            return false;
        }
        if (ev2.getPointerCount() == 1) {
            if (this.canSingerFingerScroll) {
                doScroll(distanceX, distanceY);
            }
            this.canSingerFingerScroll = true;
            return true;
        }
        if (this.currentItemView != null && ev2.getPointerCount() != 2) {
            return false;
        }
        doScroll(distanceX, distanceY);
        this.canSingerFingerScroll = false;
        return true;
    }

    private final void doScroll(float distanceX, float distanceY) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView == null || (creationEngine = pagePreDrawView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null) {
            return;
        }
        curveEngine.getCamera().scrollByOffsetSS(distanceX, distanceY);
        if (curveEngine.getPageSystem().hasReachedBottom()) {
            onLayout();
        }
    }

    private final void doFling(float velocityX, float velocityY) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView == null || (creationEngine = pagePreDrawView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null) {
            return;
        }
        curveEngine.getCamera().fling(velocityX, velocityY);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent p0, MotionEvent motionEvent1, float velocityX, float velocityY) {
        Intrinsics.checkNotNullParameter(motionEvent1, "motionEvent1");
        PagePreLayoutManager pagePreLayoutManager = this.layoutManager;
        Intrinsics.checkNotNull(pagePreLayoutManager);
        if (pagePreLayoutManager.isDragging() || this.isScaling) {
            return false;
        }
        if ((this.isCancelTouch || this.downPos == -1 || (motionEvent1.getToolType(0) & 1) != 0) && !getIsUnlimited()) {
            doFling(velocityX, velocityY);
        }
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreCamera camera;
        Intrinsics.checkNotNullParameter(scaleGestureDetector, "scaleGestureDetector");
        if (this.pointCount != 2 || this.hasDownTime < 100) {
            return false;
        }
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView != null && (creationEngine = pagePreDrawView.getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && (camera = curveEngine.getCamera()) != null) {
            camera.scale(scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
        }
        PagePreLayoutManager pagePreLayoutManager = this.layoutManager;
        Intrinsics.checkNotNull(pagePreLayoutManager);
        pagePreLayoutManager.notifyDataChangedNew();
        this.isScaling = true;
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreCamera camera;
        Intrinsics.checkNotNullParameter(scaleGestureDetector, "scaleGestureDetector");
        if (this.pointCount != 2 || this.hasDownTime < 100) {
            return false;
        }
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView != null && (creationEngine = pagePreDrawView.getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && (camera = curveEngine.getCamera()) != null) {
            camera.scale(scaleFactor, scaleGestureDetector.getPreviousSpanX(), scaleGestureDetector.getFocusY());
        }
        PagePreLayoutManager pagePreLayoutManager = this.layoutManager;
        Intrinsics.checkNotNull(pagePreLayoutManager);
        pagePreLayoutManager.notifyDataChangedNew();
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreCamera camera;
        Intrinsics.checkNotNullParameter(scaleGestureDetector, "scaleGestureDetector");
        if (this.hasDownTime < 100) {
            return;
        }
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        PagePreDrawView pagePreDrawView = this.mDrawView;
        if (pagePreDrawView != null && (creationEngine = pagePreDrawView.getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && (camera = curveEngine.getCamera()) != null) {
            camera.scale(scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
        }
        PagePreLayoutManager pagePreLayoutManager = this.layoutManager;
        Intrinsics.checkNotNull(pagePreLayoutManager);
        pagePreLayoutManager.notifyDataChangedNew();
    }

    public final void setPageStateListener(PageStateListener stateListener) {
        this.mStateListener = stateListener;
        PagePreAdapter pagePreAdapter = this.adapter;
        if (pagePreAdapter != null) {
            Intrinsics.checkNotNull(pagePreAdapter);
            pagePreAdapter.setPageStateListener(stateListener);
        }
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        PagePreLayoutManager pagePreLayoutManager = this.layoutManager;
        Intrinsics.checkNotNull(pagePreLayoutManager);
        pagePreLayoutManager.setPadding(left, top, right, bottom);
    }

    public final void setClickListener(IPreviewClickListener clickListener) {
        Intrinsics.checkNotNullParameter(clickListener, "clickListener");
        this.preViewClickListener = clickListener;
    }

    public final void updateTitle() {
        this.mPageInkView.updateHeadView();
    }

    public final int getToolViewHeight() {
        return this.mPageInkView.getToolViewHeight();
    }
}
