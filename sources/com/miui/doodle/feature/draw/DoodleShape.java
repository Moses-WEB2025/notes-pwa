package com.miui.doodle.feature.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.DoodleItemBase;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.document.Dot;
import com.miui.doodle.document.Layer;
import com.miui.doodle.utils.DoodleCacheManager;
import com.miui.doodle.utils.DrawUtil;
import com.miui.doodle.utils.HapticFeedbackTool;
import com.miui.doodle.utils.UIUtils;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.note.algorithm.common.GraphType;
import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DoodleShape.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 _2\u00020\u0001:\u0001_BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0004\b\u0010\u0010\u0011J\b\u0010@\u001a\u00020AH\u0014J>\u0010B\u001a\u00020A2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\t2\u0006\u0010F\u001a\u00020\t2\u0006\u0010G\u001a\u00020\t2\u0006\u0010H\u001a\u00020\t2\u000e\u0010I\u001a\n\u0018\u00010Jj\u0004\u0018\u0001`KJ0\u0010L\u001a\u00020A2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0M2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u00107\u001a\u00020\t2\b\b\u0002\u0010:\u001a\u00020\tJ(\u0010N\u001a\u00020A2\u0006\u0010O\u001a\u00020\u001e2\u0006\u0010P\u001a\u00020\u001e2\u0006\u0010Q\u001a\u00020\u001e2\u0006\u0010R\u001a\u00020\u001eH\u0002J\u001e\u0010S\u001a\u00020A2\u0006\u0010#\u001a\u00020\t2\u0006\u0010)\u001a\u00020\t2\u0006\u0010,\u001a\u00020\tJ.\u0010T\u001a\u00020A2\u0006\u0010#\u001a\u00020\t2\u0006\u0010)\u001a\u00020\t2\u0006\u00101\u001a\u00020\t2\u0006\u00104\u001a\u00020\t2\u0006\u00107\u001a\u00020\tJ\u0006\u0010U\u001a\u00020AJ(\u0010V\u001a\u00020A2\u0006\u0010W\u001a\u00020\t2\u0006\u0010X\u001a\u00020\t2\u0006\u0010Y\u001a\u00020\t2\u0006\u0010Z\u001a\u00020[H\u0016J\b\u0010\\\u001a\u00020\u0000H\u0016J\b\u0010]\u001a\u00020^H\u0016R\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR \u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001e\u0010#\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u0010\n\u0002\u0010(\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001e\u0010)\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u0010\n\u0002\u0010(\u001a\u0004\b*\u0010%\"\u0004\b+\u0010'R\u001a\u0010,\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010.\"\u0004\b3\u00100R\u001a\u00104\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010.\"\u0004\b6\u00100R\u001a\u00107\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010.\"\u0004\b9\u00100R\u001a\u0010:\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010.\"\u0004\b<\u00100R\u001a\u0010=\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010.\"\u0004\b?\u00100¨\u0006`"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleShape;", "Lcom/miui/doodle/feature/draw/DoodleBrushPath;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "pen", "Lcom/miui/doodle/feature/draw/DoodlePen;", "id", "", "itemRotate", "", "itemScale", "location", "Landroid/graphics/PointF;", "pivotPoint", "alpha", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodle;Lcom/miui/doodle/feature/draw/DoodlePen;Ljava/lang/String;FFLandroid/graphics/PointF;Landroid/graphics/PointF;I)V", "getAlpha", "()I", "setAlpha", "(I)V", DoodleShape.KEY_GRAPH_TYPE, "Lcom/miui/note/algorithm/common/GraphType;", "getGraphType", "()Lcom/miui/note/algorithm/common/GraphType;", "setGraphType", "(Lcom/miui/note/algorithm/common/GraphType;)V", DoodleShape.KEY_FITTED_DOTS, "", "Lcom/miui/doodle/document/Dot;", "getFittedDots", "()Ljava/util/List;", "setFittedDots", "(Ljava/util/List;)V", "centerX", "getCenterX", "()Ljava/lang/Float;", "setCenterX", "(Ljava/lang/Float;)V", "Ljava/lang/Float;", "centerY", "getCenterY", "setCenterY", "radius", "getRadius", "()F", "setRadius", "(F)V", "majorAxis", "getMajorAxis", "setMajorAxis", "minorAxis", "getMinorAxis", "setMinorAxis", "rotationAngle", "getRotationAngle", "setRotationAngle", DoodleShape.KEY_INITIAL_ANGLE, "getInitialAngle", "setInitialAngle", "lastTouchDistance", "getLastTouchDistance", "setLastTouchDistance", "initLayerAndCanvas", "", "changeByTouch", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", "rotateDiff", "minDistance", "touchX", "touchY", "touchCallBack", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "setShape", "", "calcCrossedDot", "d1", "md1", "d2", "md2", "setCircleShape", "setEllipseShape", "endShape", "endPath", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "z", Constants.TIMESTAMP, "", "copy", "toJSONObject", "Lorg/json/JSONObject;", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleShape extends DoodleBrushPath {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String GRAPH_TYPE_ARC_LINE = "type_arc_line";
    private static final String GRAPH_TYPE_BROKEN_LINE = "type_broken_line";
    private static final String GRAPH_TYPE_CIRCLE = "type_circle";
    private static final String GRAPH_TYPE_ELLIPSE = "type_ellipse";
    private static final String GRAPH_TYPE_OTHER = "type_other";
    private static final String GRAPH_TYPE_PENTAGON = "type_pentagon";
    private static final String GRAPH_TYPE_PENTAGRAM = "type_pentagram";
    private static final String GRAPH_TYPE_RECTANGLE = "type_rectangle";
    private static final String GRAPH_TYPE_SQUARE = "type_square";
    private static final String GRAPH_TYPE_STRAIGHT_LINE = "type_straight_line";
    private static final String GRAPH_TYPE_TRIANGLE = "type_triangle";
    public static final String KEY_CENTER_X = "centerX";
    public static final String KEY_CENTER_Y = "centerY";
    public static final String KEY_FITTED_DOTS = "fittedDots";
    public static final String KEY_GRAPH_TYPE = "graphType";
    public static final String KEY_INITIAL_ANGLE = "initialAngle";
    public static final String KEY_MAJOR_AXIS = "majorAxis";
    public static final String KEY_MINOR_AXIS = "minorAxis";
    public static final String KEY_RADIUS = "radius";
    public static final String KEY_ROTATION_ANGLE = "rotationAngle";
    public static final String TAG = "DoodleShape";
    private int alpha;
    private Float centerX;
    private Float centerY;
    private List<Dot> fittedDots;
    private GraphType graphType;
    private float initialAngle;
    private float lastTouchDistance;
    private float majorAxis;
    private float minorAxis;
    private float radius;
    private float rotationAngle;

    /* JADX INFO: compiled from: DoodleShape.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GraphType.values().length];
            try {
                iArr[GraphType.STRAIGHT_LINE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[GraphType.CIRCLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[GraphType.ELLIPSE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[GraphType.RECTANGLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[GraphType.SQUARE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[GraphType.PENTAGON.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[GraphType.PENTAGRAM.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[GraphType.ARC_LINE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[GraphType.BROKEN_LINE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[GraphType.OTHER.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[GraphType.TRIANGLE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // com.miui.doodle.feature.draw.DoodleBrushPath
    public void endPath(float x, float y, float z, long t) {
    }

    @Override // com.miui.doodle.feature.draw.DoodleBrushPath
    protected void initLayerAndCanvas() {
    }

    public /* synthetic */ DoodleShape(IDoodle iDoodle, DoodlePen doodlePen, String str, float f, float f2, PointF pointF, PointF pointF2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(iDoodle, (i2 & 2) != 0 ? DoodlePenManager.INSTANCE.getPencil() : doodlePen, (i2 & 4) != 0 ? DoodleItemBase.INSTANCE.generateId() : str, (i2 & 8) != 0 ? 0.0f : f, (i2 & 16) != 0 ? 1.0f : f2, (i2 & 32) != 0 ? new PointF() : pointF, (i2 & 64) != 0 ? new PointF() : pointF2, (i2 & 128) != 0 ? 255 : i);
    }

    @Override // com.miui.doodle.feature.draw.DoodleBrushPath, com.miui.doodle.base.core.IDoodleItem
    public int getAlpha() {
        return this.alpha;
    }

    @Override // com.miui.doodle.feature.draw.DoodleBrushPath, com.miui.doodle.base.core.IDoodleItem
    public void setAlpha(int i) {
        this.alpha = i;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoodleShape(IDoodle doodle, DoodlePen pen, String id, float f, float f2, PointF location, PointF pivotPoint, int i) {
        super(doodle, pen, id, f, f2, location, pivotPoint, 0, 128, null);
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        Intrinsics.checkNotNullParameter(pen, "pen");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(pivotPoint, "pivotPoint");
        this.alpha = i;
        this.fittedDots = new ArrayList();
    }

    public final GraphType getGraphType() {
        return this.graphType;
    }

    public final void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }

    public final List<Dot> getFittedDots() {
        return this.fittedDots;
    }

    public final void setFittedDots(List<Dot> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.fittedDots = list;
    }

    public final Float getCenterX() {
        return this.centerX;
    }

    public final void setCenterX(Float f) {
        this.centerX = f;
    }

    public final Float getCenterY() {
        return this.centerY;
    }

    public final void setCenterY(Float f) {
        this.centerY = f;
    }

    public final float getRadius() {
        return this.radius;
    }

    public final void setRadius(float f) {
        this.radius = f;
    }

    public final float getMajorAxis() {
        return this.majorAxis;
    }

    public final void setMajorAxis(float f) {
        this.majorAxis = f;
    }

    public final float getMinorAxis() {
        return this.minorAxis;
    }

    public final void setMinorAxis(float f) {
        this.minorAxis = f;
    }

    public final float getRotationAngle() {
        return this.rotationAngle;
    }

    public final void setRotationAngle(float f) {
        this.rotationAngle = f;
    }

    public final float getInitialAngle() {
        return this.initialAngle;
    }

    public final void setInitialAngle(float f) {
        this.initialAngle = f;
    }

    public final float getLastTouchDistance() {
        return this.lastTouchDistance;
    }

    public final void setLastTouchDistance(float f) {
        this.lastTouchDistance = f;
    }

    public final void changeByTouch(DoodleView doodleView, float rotateDiff, float minDistance, float touchX, float touchY, Runnable touchCallBack) {
        boolean z;
        Dot dot;
        float f;
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        if (this.graphType == null) {
        }
        Dot dot2 = new Dot(doodleView.toX(touchX), doodleView.toY(touchY), 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null);
        GraphType graphType = this.graphType;
        float f2 = 0.0f;
        switch (graphType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[graphType.ordinal()]) {
            case 1:
                List<IDoodleItem> straightLineItems = doodleView.getStraightLineItems();
                Iterator<IDoodleItem> it = straightLineItems.iterator();
                while (true) {
                    if (it.hasNext()) {
                        IDoodleItem next = it.next();
                        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type com.miui.doodle.feature.draw.DoodleShape");
                        DoodleShape doodleShape = (DoodleShape) next;
                        Dot dot3 = doodleShape.fittedDots.get(0);
                        Dot dot4 = doodleShape.fittedDots.get(1);
                        if (dot3.distanceTo(dot2) <= minDistance) {
                            if ((this.fittedDots.get(1).getX() != dot3.getX() || this.fittedDots.get(1).getY() != dot3.getY()) && touchCallBack != null) {
                                touchCallBack.run();
                                Unit unit = Unit.INSTANCE;
                            }
                            z = true;
                            dot2 = dot3;
                        } else if (dot4.distanceTo(dot2) <= minDistance) {
                            if ((this.fittedDots.get(1).getX() != dot4.getX() || this.fittedDots.get(1).getY() != dot4.getY()) && touchCallBack != null) {
                                touchCallBack.run();
                                Unit unit2 = Unit.INSTANCE;
                            }
                            dot2 = dot4;
                            z = true;
                        }
                    } else {
                        z = false;
                    }
                }
                if (!z) {
                    for (IDoodleItem iDoodleItem : straightLineItems) {
                        Intrinsics.checkNotNull(iDoodleItem, "null cannot be cast to non-null type com.miui.doodle.feature.draw.DoodleShape");
                        DoodleShape doodleShape2 = (DoodleShape) iDoodleItem;
                        Dot dotCalcCrossedDot = Dot.INSTANCE.calcCrossedDot(doodleShape2.fittedDots.get(0), doodleShape2.fittedDots.get(1), this.fittedDots.get(0), dot2);
                        if (dotCalcCrossedDot != null && dotCalcCrossedDot.distanceTo(dot2) <= minDistance) {
                            dot2 = dotCalcCrossedDot;
                            z = true;
                        }
                    }
                }
                if (!z) {
                    Dot dot5 = this.fittedDots.get(0);
                    if (Math.abs(dot2.getX() - dot5.getX()) <= minDistance) {
                        if (this.fittedDots.get(1).getX() != dot5.getX()) {
                            HapticFeedbackTool.INSTANCE.performHapticFeedback(doodleView);
                        }
                        dot = new Dot(dot5.getX(), dot2.getY(), 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null);
                    } else if (Math.abs(dot2.getY() - dot5.getY()) <= minDistance) {
                        if (this.fittedDots.get(1).getY() != dot5.getY()) {
                            HapticFeedbackTool.INSTANCE.performHapticFeedback(doodleView);
                        }
                        dot = new Dot(dot2.getX(), dot5.getY(), 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null);
                    }
                    dot2 = dot;
                }
                this.fittedDots.set(1, new Dot(dot2.getX(), dot2.getY(), 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null));
                setShape$default(this, this.fittedDots, GraphType.STRAIGHT_LINE, 0.0f, 0.0f, 12, null);
                Unit unit3 = Unit.INSTANCE;
                break;
            case 2:
                Float f3 = this.centerX;
                Float f4 = this.centerY;
                if (f3 != null && f4 != null) {
                    setCircleShape(f3.floatValue(), f4.floatValue(), UIUtils.INSTANCE.dotsDistance(dot2.getX(), dot2.getY(), f3.floatValue(), f4.floatValue()));
                }
                Unit unit4 = Unit.INSTANCE;
                break;
            case 3:
                Float f5 = this.centerX;
                Float f6 = this.centerY;
                if (f5 != null && f6 != null) {
                    float fDotsDistance = UIUtils.INSTANCE.dotsDistance(dot2.getX(), dot2.getY(), f5.floatValue(), f6.floatValue());
                    float f7 = fDotsDistance / this.lastTouchDistance;
                    this.lastTouchDistance = fDotsDistance;
                    float f8 = this.majorAxis * f7;
                    float f9 = f7 * this.minorAxis;
                    float fComputeAngle = rotateDiff + DrawUtil.INSTANCE.computeAngle(f5.floatValue(), f6.floatValue(), dot2.getX(), dot2.getY());
                    if (Math.abs(fComputeAngle) > 10.0f) {
                        if (Math.abs(fComputeAngle - 90.0f) <= 10.0f) {
                            if (90.0f != fComputeAngle && touchCallBack != null) {
                                touchCallBack.run();
                                Unit unit5 = Unit.INSTANCE;
                            }
                            f = 90.0f;
                        } else if (Math.abs(fComputeAngle - 180.0f) <= 10.0f) {
                            if (180.0f != fComputeAngle && touchCallBack != null) {
                                touchCallBack.run();
                                Unit unit6 = Unit.INSTANCE;
                            }
                            f = 180.0f;
                        } else if (Math.abs(fComputeAngle - 270.0f) <= 10.0f) {
                            if (270.0f != fComputeAngle && touchCallBack != null) {
                                touchCallBack.run();
                                Unit unit7 = Unit.INSTANCE;
                            }
                            f = 270.0f;
                        } else if (Math.abs(fComputeAngle - 360.0f) > 10.0f) {
                            f = fComputeAngle;
                        } else if (0.0f != fComputeAngle && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit8 = Unit.INSTANCE;
                        }
                        setEllipseShape(f5.floatValue(), f6.floatValue(), f8, f9, f);
                    } else if (0.0f != fComputeAngle && touchCallBack != null) {
                        touchCallBack.run();
                        Unit unit9 = Unit.INSTANCE;
                    }
                    f = 0.0f;
                    setEllipseShape(f5.floatValue(), f6.floatValue(), f8, f9, f);
                }
                Unit unit10 = Unit.INSTANCE;
                break;
            case 4:
            case 5:
                Float f10 = this.centerX;
                Float f11 = this.centerY;
                float f12 = this.initialAngle;
                if (f10 != null && f11 != null) {
                    float fDotsDistance2 = UIUtils.INSTANCE.dotsDistance(dot2.getX(), dot2.getY(), f10.floatValue(), f11.floatValue());
                    float f13 = fDotsDistance2 / this.lastTouchDistance;
                    this.lastTouchDistance = fDotsDistance2;
                    List<Dot> list = this.fittedDots;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (Dot dot6 : list) {
                        arrayList.add(new Dot(((dot6.getX() - f10.floatValue()) * f13) + f10.floatValue(), ((dot6.getY() - f11.floatValue()) * f13) + f11.floatValue(), 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null));
                    }
                    ArrayList arrayList2 = arrayList;
                    float f14 = this.rotationAngle - f12;
                    float fComputeAngle2 = (((rotateDiff + DrawUtil.INSTANCE.computeAngle(f10.floatValue(), f11.floatValue(), dot2.getX(), dot2.getY())) - f12) + 720.0f) % 360.0f;
                    if (Math.abs(fComputeAngle2) <= 10.0f) {
                        if (0.0f != f14 && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit11 = Unit.INSTANCE;
                        }
                    } else if (Math.abs(fComputeAngle2 - 90) <= 10.0f) {
                        if (90.0f != f14 && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit12 = Unit.INSTANCE;
                        }
                        f2 = 90.0f;
                    } else if (Math.abs(fComputeAngle2 - R2.array.ai_tag_top_item_gradient_border_colors) <= 10.0f) {
                        if (180.0f != f14 && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit13 = Unit.INSTANCE;
                        }
                        f2 = 180.0f;
                    } else if (Math.abs(fComputeAngle2 - R2.attr.actionBarPauseIcon) <= 10.0f) {
                        if (270.0f != f14 && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit14 = Unit.INSTANCE;
                        }
                        f2 = 270.0f;
                    } else if (Math.abs(fComputeAngle2 - 360) > 10.0f) {
                        f2 = fComputeAngle2;
                    } else if (0.0f != f14 && touchCallBack != null) {
                        touchCallBack.run();
                        Unit unit15 = Unit.INSTANCE;
                    }
                    GraphType graphType2 = this.graphType;
                    Intrinsics.checkNotNull(graphType2);
                    setShape(arrayList2, graphType2, f2 + f12, f12);
                }
                Unit unit16 = Unit.INSTANCE;
                break;
            case 6:
            case 7:
                Float f15 = this.centerX;
                Float f16 = this.centerY;
                float f17 = this.initialAngle;
                if (f15 != null && f16 != null) {
                    float fDotsDistance3 = UIUtils.INSTANCE.dotsDistance(dot2.getX(), dot2.getY(), f15.floatValue(), f16.floatValue());
                    float f18 = fDotsDistance3 / this.lastTouchDistance;
                    this.lastTouchDistance = fDotsDistance3;
                    List<Dot> list2 = this.fittedDots;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    for (Dot dot7 : list2) {
                        arrayList3.add(new Dot(((dot7.getX() - f15.floatValue()) * f18) + f15.floatValue(), ((dot7.getY() - f16.floatValue()) * f18) + f16.floatValue(), 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null));
                    }
                    ArrayList arrayList4 = arrayList3;
                    float f19 = this.rotationAngle - f17;
                    float fComputeAngle3 = (((rotateDiff + DrawUtil.INSTANCE.computeAngle(f15.floatValue(), f16.floatValue(), dot2.getX(), dot2.getY())) - f17) + 720.0f) % 360.0f;
                    if (Math.abs(fComputeAngle3) <= 10.0f) {
                        if (0.0f != f19 && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit17 = Unit.INSTANCE;
                        }
                    } else if (Math.abs(fComputeAngle3 - 72) <= 10.0f) {
                        f2 = 72.0f;
                        if (72.0f != f19 && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit18 = Unit.INSTANCE;
                        }
                    } else if (Math.abs(fComputeAngle3 - 144) <= 10.0f) {
                        f2 = 144.0f;
                        if (144.0f != f19 && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit19 = Unit.INSTANCE;
                        }
                    } else if (Math.abs(fComputeAngle3 - R2.array.global_ai_panel_blend_colors) <= 10.0f) {
                        f2 = 216.0f;
                        if (216.0f != f19 && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit20 = Unit.INSTANCE;
                        }
                    } else if (Math.abs(fComputeAngle3 - 288) <= 10.0f) {
                        f2 = 288.0f;
                        if (288.0f != f19 && touchCallBack != null) {
                            touchCallBack.run();
                            Unit unit21 = Unit.INSTANCE;
                        }
                    } else if (Math.abs(fComputeAngle3 - 360) > 10.0f) {
                        f2 = fComputeAngle3;
                    } else if (0.0f != f19 && touchCallBack != null) {
                        touchCallBack.run();
                        Unit unit22 = Unit.INSTANCE;
                    }
                    GraphType graphType3 = this.graphType;
                    Intrinsics.checkNotNull(graphType3);
                    setShape(arrayList4, graphType3, f2 + f17, f17);
                }
                Unit unit23 = Unit.INSTANCE;
                break;
            case 8:
            case 9:
            case 10:
                Unit unit24 = Unit.INSTANCE;
                break;
            default:
                Float f20 = this.centerX;
                Float f21 = this.centerY;
                if (f20 != null && f21 != null) {
                    float fDotsDistance4 = UIUtils.INSTANCE.dotsDistance(dot2.getX(), dot2.getY(), f20.floatValue(), f21.floatValue());
                    float f22 = fDotsDistance4 / this.lastTouchDistance;
                    this.lastTouchDistance = fDotsDistance4;
                    List<Dot> list3 = this.fittedDots;
                    ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                    for (Dot dot8 : list3) {
                        arrayList5.add(new Dot(((dot8.getX() - f20.floatValue()) * f22) + f20.floatValue(), ((dot8.getY() - f21.floatValue()) * f22) + f21.floatValue(), 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null));
                    }
                    float fComputeAngle4 = rotateDiff + DrawUtil.INSTANCE.computeAngle(f20.floatValue(), f21.floatValue(), dot2.getX(), dot2.getY());
                    GraphType graphType4 = this.graphType;
                    Intrinsics.checkNotNull(graphType4);
                    setShape$default(this, arrayList5, graphType4, fComputeAngle4, 0.0f, 8, null);
                }
                Unit unit25 = Unit.INSTANCE;
                break;
        }
    }

    public static /* synthetic */ void setShape$default(DoodleShape doodleShape, List list, GraphType graphType, float f, float f2, int i, Object obj) {
        if ((i & 4) != 0) {
            f = 0.0f;
        }
        if ((i & 8) != 0) {
            f2 = 0.0f;
        }
        doodleShape.setShape(list, graphType, f, f2);
    }

    public final void setShape(List<Dot> fittedDots, GraphType graphType, float rotationAngle, float initialAngle) {
        Intrinsics.checkNotNullParameter(fittedDots, "fittedDots");
        Intrinsics.checkNotNullParameter(graphType, "graphType");
        setLastDrawDistance(0.0f);
        setTotalDistance(0.0f);
        getDots().clear();
        getPath().reset();
        if (fittedDots.isEmpty()) {
            return;
        }
        setLastDot(null);
        getPath().moveTo(fittedDots.get(0).getX(), fittedDots.get(0).getY());
        for (Dot dot : fittedDots) {
            getPath().lineTo(dot.getX(), dot.getY());
        }
        if (this.centerX != null && this.centerY != null) {
            Matrix matrix = new Matrix();
            Float f = this.centerX;
            Intrinsics.checkNotNull(f);
            float fFloatValue = f.floatValue();
            Float f2 = this.centerY;
            Intrinsics.checkNotNull(f2);
            matrix.postRotate(rotationAngle, fFloatValue, f2.floatValue());
            getPath().transform(matrix);
        }
        DoodleBrushPath.updatePathInner$default(this, fittedDots.get(fittedDots.size() - 1), true, false, false, 8, null);
        resetBounds();
        setShape(true);
        this.graphType = graphType;
        ArrayList arrayList = new ArrayList();
        this.fittedDots = arrayList;
        arrayList.addAll(fittedDots);
        this.rotationAngle = rotationAngle;
        this.initialAngle = initialAngle;
        switch (WhenMappings.$EnumSwitchMapping$0[graphType.ordinal()]) {
            case 4:
            case 5:
                getDoodle().getMarkDoodleOperation().invoke(6);
                calcCrossedDot(fittedDots.get(0), fittedDots.get(2), fittedDots.get(1), fittedDots.get(3));
                break;
            case 6:
                getDoodle().getMarkDoodleOperation().invoke(4);
                Dot dot2 = fittedDots.get(0);
                Dot dot3 = fittedDots.get(1);
                Dot dot4 = fittedDots.get(2);
                Dot dot5 = fittedDots.get(3);
                calcCrossedDot(dot2, dot4.middleDot(dot5), dot3, dot5.middleDot(fittedDots.get(4)));
                break;
            case 7:
                getDoodle().getMarkDoodleOperation().invoke(5);
                calcCrossedDot(fittedDots.get(0), fittedDots.get(5), fittedDots.get(2), fittedDots.get(7));
                break;
            case 8:
                getDoodle().getMarkDoodleOperation().invoke(8);
                break;
            case 9:
                getDoodle().getMarkDoodleOperation().invoke(7);
                break;
            case 11:
                getDoodle().getMarkDoodleOperation().invoke(3);
                Dot dot6 = fittedDots.get(0);
                Dot dot7 = fittedDots.get(1);
                Dot dot8 = fittedDots.get(2);
                calcCrossedDot(dot6, dot7.middleDot(dot8), dot7, dot6.middleDot(dot8));
                break;
        }
    }

    private final void calcCrossedDot(Dot d1, Dot md1, Dot d2, Dot md2) {
        Dot dotCalcCrossedDot = Dot.INSTANCE.calcCrossedDot(d1, md1, d2, md2);
        if (dotCalcCrossedDot != null) {
            this.centerX = Float.valueOf(dotCalcCrossedDot.getX());
            this.centerY = Float.valueOf(dotCalcCrossedDot.getY());
        }
    }

    public final void setCircleShape(float centerX, float centerY, float radius) {
        setLastDrawDistance(0.0f);
        setTotalDistance(0.0f);
        getDots().clear();
        getPath().reset();
        float f = centerX - radius;
        Path path = getPath();
        path.arcTo(f, centerY - radius, centerX + radius, centerY + radius, 0.0f, 359.99f, true);
        DoodleBrushPath.updatePathInner$default(this, new Dot(f, centerY, 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null), false, false, false, 8, null);
        setShape(true);
        this.centerX = Float.valueOf(centerX);
        this.centerY = Float.valueOf(centerY);
        this.radius = radius;
        this.graphType = GraphType.CIRCLE;
        resetBounds();
    }

    public final void setEllipseShape(float centerX, float centerY, float majorAxis, float minorAxis, float rotationAngle) {
        setLastDrawDistance(0.0f);
        setTotalDistance(0.0f);
        getDots().clear();
        getPath().reset();
        float f = centerX - majorAxis;
        Path path = getPath();
        path.arcTo(f, centerY - minorAxis, centerX + majorAxis, centerY + minorAxis, 0.0f, 359.99f, true);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotationAngle, centerX, centerY);
        getPath().transform(matrix);
        DoodleBrushPath.updatePathInner$default(this, new Dot(f, centerY, 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null), false, false, false, 8, null);
        setShape(true);
        this.centerX = Float.valueOf(centerX);
        this.centerY = Float.valueOf(centerY);
        this.majorAxis = majorAxis;
        this.minorAxis = minorAxis;
        this.rotationAngle = rotationAngle;
        this.graphType = GraphType.ELLIPSE;
        resetBounds();
    }

    public final void endShape() {
        setBitmap(Bitmap.createBitmap(getDoodle().getDoodleWidth(), getDoodle().getDoodleHeight(), Bitmap.Config.ARGB_8888));
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            setBitmapCanvas(new Canvas(bitmap));
            Canvas bitmapCanvas = getBitmapCanvas();
            if (bitmapCanvas != null) {
                Iterator<T> it = getDots().iterator();
                while (it.hasNext()) {
                    drawDot(bitmapCanvas, (Dot) it.next());
                }
            }
        }
        DoodleCacheManager.INSTANCE.put(getId(), getBitmap());
    }

    @Override // com.miui.doodle.feature.draw.DoodleBrushPath, com.miui.doodle.base.core.IDoodleItem
    public DoodleShape copy() {
        DoodleShape doodleShape = new DoodleShape(getDoodle(), getPen().copy(), null, getItemRotate(), 0.0f, getLocation(), getPivotPoint(), 0, R2.array.ai_material_view_bg_blend_modes, null);
        doodleShape.setAlpha(getAlpha());
        doodleShape.getBounds().set(getBounds());
        doodleShape.getDots().addAll(getDots());
        doodleShape.getOriginDots().addAll(getOriginDots());
        doodleShape.getEraserDots().addAll(getEraserDots());
        doodleShape.graphType = this.graphType;
        doodleShape.fittedDots.addAll(this.fittedDots);
        doodleShape.centerX = this.centerX;
        doodleShape.centerY = this.centerY;
        doodleShape.radius = this.radius;
        doodleShape.majorAxis = this.majorAxis;
        doodleShape.minorAxis = this.minorAxis;
        doodleShape.rotationAngle = this.rotationAngle;
        doodleShape.lastTouchDistance = this.lastTouchDistance;
        doodleShape.initialAngle = this.initialAngle;
        return doodleShape;
    }

    @Override // com.miui.doodle.feature.draw.DoodleBrushPath, com.miui.doodle.base.DoodleItemBase, com.miui.doodle.base.core.IDoodleItem
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = super.toJSONObject();
        jSONObject.put(KEY_GRAPH_TYPE, INSTANCE.graphTypeToString(this.graphType));
        jSONObject.put("centerX", this.centerX);
        jSONObject.put("centerY", this.centerY);
        jSONObject.put("radius", Float.valueOf(this.radius));
        jSONObject.put("majorAxis", Float.valueOf(this.majorAxis));
        jSONObject.put("minorAxis", Float.valueOf(this.minorAxis));
        jSONObject.put("rotationAngle", Float.valueOf(this.rotationAngle));
        jSONObject.put(KEY_INITIAL_ANGLE, Float.valueOf(this.initialAngle));
        JSONArray jSONArray = new JSONArray();
        Iterator<T> it = this.fittedDots.iterator();
        while (it.hasNext()) {
            jSONArray.put(((Dot) it.next()).toJSONObject());
        }
        jSONObject.put(KEY_FITTED_DOTS, jSONArray);
        jSONObject.put("type", 13);
        return jSONObject;
    }

    /* JADX INFO: compiled from: DoodleShape.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JÞ\u0001\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020 2\u0006\u0010$\u001a\u00020 2\u0006\u0010%\u001a\u00020 2\u0006\u0010&\u001a\u00020'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020*0)2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020*0)2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020'2\u0006\u00100\u001a\u00020'2\u0006\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u00020 2\u0006\u00103\u001a\u00020 2\u0006\u00104\u001a\u00020 2\u0006\u00105\u001a\u00020 2\u0006\u00106\u001a\u00020 2\u0006\u00107\u001a\u00020 2\u0006\u00108\u001a\u00020 2\f\u00109\u001a\b\u0012\u0004\u0012\u00020*0)J\u0012\u0010:\u001a\u00020\u00052\b\u00101\u001a\u0004\u0018\u00010;H\u0002J\u0012\u0010<\u001a\u0004\u0018\u00010;2\u0006\u0010=\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleShape$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "KEY_GRAPH_TYPE", "KEY_FITTED_DOTS", "KEY_CENTER_X", "KEY_CENTER_Y", "KEY_RADIUS", "KEY_MAJOR_AXIS", "KEY_MINOR_AXIS", "KEY_ROTATION_ANGLE", "KEY_INITIAL_ANGLE", "GRAPH_TYPE_STRAIGHT_LINE", "GRAPH_TYPE_BROKEN_LINE", "GRAPH_TYPE_ARC_LINE", "GRAPH_TYPE_TRIANGLE", "GRAPH_TYPE_SQUARE", "GRAPH_TYPE_RECTANGLE", "GRAPH_TYPE_PENTAGON", "GRAPH_TYPE_PENTAGRAM", "GRAPH_TYPE_CIRCLE", "GRAPH_TYPE_ELLIPSE", "GRAPH_TYPE_OTHER", "createShapeItem", "", "id", "hasAdded", "", "locationX", "", "locationY", "pivotX", "pivotY", "itemRotate", "itemScale", "alpha", "", DoodleBrushPath.KEY_DOTS, "", "Lcom/miui/doodle/document/Dot;", "originDots", "eraserDots", CloudPushConstants.XML_ITEM, "Lcom/miui/doodle/feature/draw/DoodleShape;", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, DoodleShape.KEY_GRAPH_TYPE, "centerX", "centerY", "radius", "majorAxis", "minorAxis", "rotationAngle", DoodleShape.KEY_INITIAL_ANGLE, DoodleShape.KEY_FITTED_DOTS, "graphTypeToString", "Lcom/miui/note/algorithm/common/GraphType;", "graphTypeFromString", "typeString", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: DoodleShape.kt */
        @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
        public static final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[GraphType.values().length];
                try {
                    iArr[GraphType.STRAIGHT_LINE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[GraphType.BROKEN_LINE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[GraphType.ARC_LINE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[GraphType.TRIANGLE.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[GraphType.SQUARE.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[GraphType.RECTANGLE.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr[GraphType.PENTAGON.ordinal()] = 7;
                } catch (NoSuchFieldError unused7) {
                }
                try {
                    iArr[GraphType.PENTAGRAM.ordinal()] = 8;
                } catch (NoSuchFieldError unused8) {
                }
                try {
                    iArr[GraphType.CIRCLE.ordinal()] = 9;
                } catch (NoSuchFieldError unused9) {
                }
                try {
                    iArr[GraphType.ELLIPSE.ordinal()] = 10;
                } catch (NoSuchFieldError unused10) {
                }
                try {
                    iArr[GraphType.OTHER.ordinal()] = 11;
                } catch (NoSuchFieldError unused11) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void createShapeItem(String id, boolean hasAdded, float locationX, float locationY, float pivotX, float pivotY, float itemRotate, float itemScale, int alpha, List<Dot> dots, List<Dot> originDots, List<Dot> eraserDots, DoodleShape item, int width, int height, String graphType, float centerX, float centerY, float radius, float majorAxis, float minorAxis, float rotationAngle, float initialAngle, List<Dot> fittedDots) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(dots, "dots");
            Intrinsics.checkNotNullParameter(originDots, "originDots");
            Intrinsics.checkNotNullParameter(eraserDots, "eraserDots");
            Intrinsics.checkNotNullParameter(item, "item");
            Intrinsics.checkNotNullParameter(graphType, "graphType");
            Intrinsics.checkNotNullParameter(fittedDots, "fittedDots");
            DoodleBrushPath.INSTANCE.createBrushItem(id, hasAdded, locationX, locationY, pivotX, pivotY, itemRotate, itemScale, alpha, dots, originDots, eraserDots, item, width, height);
            item.setGraphType(graphTypeFromString(graphType));
            item.setCenterX(Float.valueOf(centerX));
            item.setCenterY(Float.valueOf(centerY));
            item.setRadius(radius);
            item.setMajorAxis(majorAxis);
            item.setMinorAxis(minorAxis);
            item.setRotationAngle(rotationAngle);
            item.setInitialAngle(initialAngle);
            item.setFittedDots(new ArrayList());
            List<Dot> list = fittedDots;
            if (list.isEmpty()) {
                return;
            }
            item.getFittedDots().addAll(list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String graphTypeToString(GraphType graphType) {
            switch (graphType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[graphType.ordinal()]) {
                case 1:
                    return DoodleShape.GRAPH_TYPE_STRAIGHT_LINE;
                case 2:
                    return DoodleShape.GRAPH_TYPE_BROKEN_LINE;
                case 3:
                    return DoodleShape.GRAPH_TYPE_ARC_LINE;
                case 4:
                    return DoodleShape.GRAPH_TYPE_TRIANGLE;
                case 5:
                    return DoodleShape.GRAPH_TYPE_SQUARE;
                case 6:
                    return DoodleShape.GRAPH_TYPE_RECTANGLE;
                case 7:
                    return DoodleShape.GRAPH_TYPE_PENTAGON;
                case 8:
                    return DoodleShape.GRAPH_TYPE_PENTAGRAM;
                case 9:
                    return DoodleShape.GRAPH_TYPE_CIRCLE;
                case 10:
                    return DoodleShape.GRAPH_TYPE_ELLIPSE;
                case 11:
                    return DoodleShape.GRAPH_TYPE_OTHER;
                default:
                    return "";
            }
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        private final GraphType graphTypeFromString(String typeString) {
            switch (typeString.hashCode()) {
                case -1968390700:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_STRAIGHT_LINE)) {
                        return GraphType.STRAIGHT_LINE;
                    }
                    return null;
                case -1854507879:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_BROKEN_LINE)) {
                        return GraphType.BROKEN_LINE;
                    }
                    return null;
                case -1710674579:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_TRIANGLE)) {
                        return GraphType.TRIANGLE;
                    }
                    return null;
                case -1083728437:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_OTHER)) {
                        return GraphType.OTHER;
                    }
                    return null;
                case -638012122:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_ARC_LINE)) {
                        return GraphType.ARC_LINE;
                    }
                    return null;
                case 410744181:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_CIRCLE)) {
                        return GraphType.CIRCLE;
                    }
                    return null;
                case 444004010:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_RECTANGLE)) {
                        return GraphType.RECTANGLE;
                    }
                    return null;
                case 876286402:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_SQUARE)) {
                        return GraphType.SQUARE;
                    }
                    return null;
                case 936353240:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_PENTAGRAM)) {
                        return GraphType.PENTAGRAM;
                    }
                    return null;
                case 1415678181:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_PENTAGON)) {
                        return GraphType.PENTAGON;
                    }
                    return null;
                case 1703704537:
                    if (typeString.equals(DoodleShape.GRAPH_TYPE_ELLIPSE)) {
                        return GraphType.ELLIPSE;
                    }
                    return null;
                default:
                    return null;
            }
        }
    }
}
