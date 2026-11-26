package xc.engine.core.fast.data;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.CsPoint;

/* JADX INFO: compiled from: CurveSegment.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u001b\u0018\u0000 \"2\u00020\u0001:\u0002\"#B[\b\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u0012\b\b\u0002\u0010\r\u001a\u00020\t¢\u0006\u0004\b\u000e\u0010\u000fB1\b\u0012\u0012\u0006\u0010\u0010\u001a\u00020\t\u0012\u0006\u0010\u0011\u001a\u00020\u0007\u0012\u0006\u0010\u0012\u001a\u00020\u0007\u0012\u0006\u0010\u0013\u001a\u00020\t\u0012\u0006\u0010\u0014\u001a\u00020\t¢\u0006\u0004\b\u000e\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010\f\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0011\u0010\r\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001d¨\u0006$"}, d2 = {"Lxc/engine/core/fast/data/CurveSegment;", "", "scenario", "Lxc/engine/core/fast/data/CurveSegment$EScenario;", "hoverPoint", "Lxc/engine/data/CsPoint;", "postureDegree", "", "drawingCurve", "", "drawingOffset", "drawingCount", "estimatedCurve", "shapeCurve", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/fast/data/CurveSegment$EScenario;Lxc/engine/data/CsPoint;IJIIJJ)V", "drawingCurveId", "offset", "count", "estimatedCurveId", "shapeCurveId", "(JIIJJ)V", "getScenario", "()Lxc/engine/core/fast/data/CurveSegment$EScenario;", "getHoverPoint", "()Lxc/engine/data/CsPoint;", "getPostureDegree", "()I", "getDrawingCurve", "()J", "getDrawingOffset", "getDrawingCount", "getEstimatedCurve", "getShapeCurve", "Companion", "EScenario", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CurveSegment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int drawingCount;
    private final long drawingCurve;
    private final int drawingOffset;
    private final long estimatedCurve;
    private final CsPoint hoverPoint;
    private final int postureDegree;
    private final EScenario scenario;
    private final long shapeCurve;

    /* JADX INFO: compiled from: CurveSegment.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lxc/engine/core/fast/data/CurveSegment$EScenario;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "PenDrawing", "PenHover", "EraserHover", "EraserTouch", "ExitHover", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum EScenario {
        PenDrawing,
        PenHover,
        EraserHover,
        EraserTouch,
        ExitHover;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<EScenario> getEntries() {
            return $ENTRIES;
        }
    }

    public /* synthetic */ CurveSegment(long j, int i, int i2, long j2, long j3, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, i, i2, j2, j3);
    }

    private CurveSegment(EScenario eScenario, CsPoint csPoint, int i, long j, int i2, int i3, long j2, long j3) {
        this.scenario = eScenario;
        this.hoverPoint = csPoint;
        this.postureDegree = i;
        this.drawingCurve = j;
        this.drawingOffset = i2;
        this.drawingCount = i3;
        this.estimatedCurve = j2;
        this.shapeCurve = j3;
    }

    /* synthetic */ CurveSegment(EScenario eScenario, CsPoint csPoint, int i, long j, int i2, int i3, long j2, long j3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? EScenario.PenDrawing : eScenario, (i4 & 2) != 0 ? null : csPoint, (i4 & 4) != 0 ? 0 : i, (i4 & 8) != 0 ? 0L : j, (i4 & 16) != 0 ? -1 : i2, (i4 & 32) == 0 ? i3 : -1, (i4 & 64) != 0 ? 0L : j2, (i4 & 128) != 0 ? 0L : j3);
    }

    public final EScenario getScenario() {
        return this.scenario;
    }

    public final CsPoint getHoverPoint() {
        return this.hoverPoint;
    }

    public final int getPostureDegree() {
        return this.postureDegree;
    }

    public final long getDrawingCurve() {
        return this.drawingCurve;
    }

    public final int getDrawingOffset() {
        return this.drawingOffset;
    }

    public final int getDrawingCount() {
        return this.drawingCount;
    }

    public final long getEstimatedCurve() {
        return this.estimatedCurve;
    }

    public final long getShapeCurve() {
        return this.shapeCurve;
    }

    /* JADX INFO: compiled from: CurveSegment.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J.\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007J\u0016\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\tJ\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0013\u001a\u00020\u0005¨\u0006\u0014"}, d2 = {"Lxc/engine/core/fast/data/CurveSegment$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "constructForPenDrawing", "Lxc/engine/core/fast/data/CurveSegment;", "drawingCurveId", "", "offset", "", "count", "estimatedCurveId", "shapeCurveId", "constructForPenHover", "point", "Lxc/engine/data/CsPoint;", "angle", "constructForEraserHover", "constructForEraserTouch", "constructForExit", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CurveSegment constructForPenDrawing(long drawingCurveId, int offset, int count, long estimatedCurveId, long shapeCurveId) {
            return new CurveSegment(drawingCurveId, offset, count, estimatedCurveId, shapeCurveId, null);
        }

        public final CurveSegment constructForPenHover(CsPoint point, int angle) {
            Intrinsics.checkNotNullParameter(point, "point");
            return new CurveSegment(EScenario.PenHover, point, angle, 0L, 0, 0, 0L, 0L, R2.attr.actionBarCutIcon, null);
        }

        public final CurveSegment constructForEraserHover(CsPoint point) {
            Intrinsics.checkNotNullParameter(point, "point");
            return new CurveSegment(EScenario.EraserHover, point, 0, 0L, 0, 0, 0L, 0L, R2.attr.actionBarCutIcon, null);
        }

        public final CurveSegment constructForEraserTouch(CsPoint point) {
            Intrinsics.checkNotNullParameter(point, "point");
            return new CurveSegment(EScenario.EraserTouch, point, 0, 0L, 0, 0, 0L, 0L, R2.attr.actionBarCutIcon, null);
        }

        public final CurveSegment constructForExit() {
            return new CurveSegment(EScenario.ExitHover, null, 0, 0L, 0, 0, 0L, 0L, R2.attr.actionBarEmbedTabs, null);
        }
    }

    private CurveSegment(long j, int i, int i2, long j2, long j3) {
        this(EScenario.PenDrawing, null, 0, j, i, i2, j2, j3);
    }
}
