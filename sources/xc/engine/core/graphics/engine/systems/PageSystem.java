package xc.engine.core.graphics.engine.systems;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.notes.home.ContentFragment;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.util.a;
import defpackage.R2;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.core.BaseEngineLayer;
import xc.engine.core.graphics.engine.core.Camera;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.PageMaskRender;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.core.graphics.engine.task.AsyncGLTaskManager;
import xc.engine.core.graphics.engine.task.BaseAsyncGLTask;

/* JADX INFO: compiled from: PageSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b \u0018\u0000 n2\u00020\u0001:\u0005nopqrB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010&\u001a\u00020'H\u0014J\u001e\u0010(\u001a\u00020'2\u0016\u0010)\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bJ\b\u0010*\u001a\u00020'H\u0002J\u000e\u0010+\u001a\u00020'2\u0006\u0010,\u001a\u00020\nJ\u000e\u0010-\u001a\u00020'2\u0006\u0010.\u001a\u00020\u0017J\u001a\u0010/\u001a\u00020'2\u0012\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170101J.\u00102\u001a\u00020'2\u0006\u0010.\u001a\u00020\u00172\u0006\u00103\u001a\u00020\u00172\u0006\u00104\u001a\u00020\u00172\u0006\u00105\u001a\u00020\u00172\u0006\u00106\u001a\u00020\u0017J\u0016\u00107\u001a\u00020'2\u0006\u00108\u001a\u00020\u00172\u0006\u00109\u001a\u00020\u000fJ\u0016\u0010:\u001a\u00020'2\u0006\u00108\u001a\u00020\u00172\u0006\u0010;\u001a\u00020<J \u0010=\u001a\u00020'2\u0006\u00108\u001a\u00020\u00172\u0006\u0010>\u001a\u00020?2\b\b\u0002\u0010@\u001a\u00020\u0019J\u000e\u0010A\u001a\u00020'2\u0006\u00108\u001a\u00020\u0017J\b\u0010B\u001a\u00020'H\u0002J\u001a\u0010C\u001a\u00020'2\u0006\u0010D\u001a\u00020\u00172\b\b\u0002\u0010E\u001a\u00020\u0019H\u0002J*\u0010F\u001a\u00020\r2\u0006\u0010G\u001a\u00020\n2\u0006\u0010.\u001a\u00020\u00172\u0006\u0010H\u001a\u00020\u00122\b\b\u0002\u0010E\u001a\u00020\u0019H\u0002J\u0010\u0010I\u001a\u00020\u00192\u0006\u0010J\u001a\u00020KH\u0014J(\u0010L\u001a\u00020'2\u0006\u0010M\u001a\u00020K2\u0018\u0010N\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020'0OJ\"\u0010P\u001a\u00020'2\u0018\u0010N\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020'0OH\u0002J\u000e\u0010Q\u001a\u00020\r2\u0006\u00108\u001a\u00020\u0017J\u0016\u0010R\u001a\u00020S2\u0006\u0010T\u001a\u00020S2\u0006\u00108\u001a\u00020\u0017J\u0016\u0010U\u001a\u00020K2\u0006\u0010V\u001a\u00020K2\u0006\u00108\u001a\u00020\u0017J\u000e\u0010W\u001a\u00020\u00172\u0006\u0010T\u001a\u00020SJ\f\u0010X\u001a\b\u0012\u0004\u0012\u00020\u001701J\u000e\u0010Y\u001a\u00020\u00172\u0006\u0010Z\u001a\u00020SJ\u0006\u0010[\u001a\u00020\u0019J\u0006\u0010\\\u001a\u00020\u0012J\u0006\u0010]\u001a\u00020\u0019J\u0006\u0010^\u001a\u00020\u0012J\u0006\u0010_\u001a\u00020\u0012J\u0006\u0010`\u001a\u00020\u0012J\u0006\u0010a\u001a\u00020\u0012J\u0006\u0010b\u001a\u00020\u0019J\u0006\u0010c\u001a\u00020\u0012J\u0006\u0010d\u001a\u00020KJ\u000e\u0010e\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u0017J\u0010\u0010f\u001a\u00020\u00192\b\b\u0002\u00108\u001a\u00020\u0017J\u0016\u0010g\u001a\u00020S2\u0006\u0010h\u001a\u00020\u00172\u0006\u0010i\u001a\u00020\u0017J\u000e\u0010j\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u0017J\u0006\u0010k\u001a\u00020\u0017J\u000e\u0010l\u001a\u00020'2\u0006\u0010.\u001a\u00020\u0017J\u0006\u0010m\u001a\u00020\u0017R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\r0\tj\b\u0012\u0004\u0012\u00020\r`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0012X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u001a\"\u0004\b\u001b\u0010\u001cR$\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0017@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001a\"\u0004\b%\u0010\u001c¨\u0006s"}, d2 = {"Lxc/engine/core/graphics/engine/systems/PageSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineLayer;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "pageMaskRenderer", "Lxc/engine/core/graphics/engine/renderers/PageMaskRender;", "pageInfoList", "Ljava/util/ArrayList;", "Lxc/engine/core/graphics/engine/systems/PageSystem$PageInfo;", "Lkotlin/collections/ArrayList;", "pageDataList", "Lxc/engine/core/graphics/engine/systems/PageSystem$PageData;", "bgColor", "Lxc/engine/core/graphics/engine/core/Color;", "bgColorDark", "pagePadding", "", "getPagePadding", "()F", "pageCornerRadius", "currentPageIndex", "", "isPdfMode", "", "()Z", "setPdfMode", "(Z)V", "value", "startPageIndex", "getStartPageIndex", "()I", "setStartPageIndex", "(I)V", "startPageJumped", "getStartPageJumped", "setStartPageJumped", "onDestroy", "", "setAllPageInfo", "pages", "jumpToStartPage", "addPage", ContentFragment.ARG_PAGE, "deletePage", "index", "adjustAllPageSize", "pageSize", "", "setPageSize", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "maxWidth", "maxHeight", "setPageBGColor", "pageIndex", "color", "setPageBGStyle", "style", "Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;", "setPageBgImage", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "bIsThumbnail", "releasePageBgImage", "generateAllPageData", "calculatePageDataFromIndex", "startIndex", "bShouldUploadBgImage", "createPageDataFromPageInfo", "pageInfo", "startHeight", "onRenderLayer", "screenRect", "Landroid/graphics/RectF;", "loopPagesInTargetArea", a.e, CallMethod.ARG_CALLBACK, "Lkotlin/Function2;", "loopCurrentScreenPages", "getPageData", "getPageSpacePointFromScreenSpace", "Landroid/graphics/PointF;", "pointSS", "getRectWSFromRectPS", "rectPS", "getPageIndexByScreenSpacePoint", "getVisiblePageIndexListByScreenSpacePoint", "getPageIndexByWorldSpacePoint", "pointWS", "isInfinite", "getScreenTopToPagesTopDistance", "hasReachedTop", "getScreenBottomToPagesBottomDistance", "getAllowedMaxHeight", "getAllowedMaxLeft", "getAllowedMaxRight", "hasReachedBottom", "getContentTotalHeight", "getCurrentViewportPagesArea", "isValidPageIndex", "isBgDark", "getDeltaOffsetWS", "curPageIndex", "targetPageIndex", "pageHasBgImage", "getPageCount", "setCurrentPageIndex", "getCurrentPageIndex", "Companion", "PageInfo", "PageData", "ReleaseBGImageTask", "UploadBGImageTask", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PageSystem extends BaseEngineLayer {
    private static final String TAG = "PageSystem";
    public static final int THUMBNAIL_SIZE = 192;
    private final Color bgColor;
    private final Color bgColorDark;
    private int currentPageIndex;
    private boolean isPdfMode;
    private final float pageCornerRadius;
    private final ArrayList<PageData> pageDataList;
    private final ArrayList<PageInfo> pageInfoList;
    private final PageMaskRender pageMaskRenderer;
    private int startPageIndex;
    private boolean startPageJumped;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PageSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.pageMaskRenderer = getCurveEngine().getPageMaskRenderer();
        this.pageInfoList = new ArrayList<>();
        this.pageDataList = new ArrayList<>();
        this.bgColor = new Color(0.9686f, 0.9686f, 0.9686f, 1.0f);
        this.bgColorDark = new Color(0.094f, 0.094f, 0.094f, 1.0f);
        this.pageCornerRadius = 40.0f;
    }

    /* JADX INFO: compiled from: PageSystem.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001d\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0004\b\r\u0010\u000eB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\r\u0010\u000fB)\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\r\u0010\u0010B1\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\r\u0010\u0011B9\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\r\u0010\u0012R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0018\u0010\u0016R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0014\"\u0004\b\u001a\u0010\u0016R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0014\"\u0004\b\u001c\u0010\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(¨\u0006)"}, d2 = {"Lxc/engine/core/graphics/engine/systems/PageSystem$PageInfo;", "", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "maxWidth", "maxHeight", "bgColor", "Lxc/engine/core/graphics/engine/core/Color;", "bgStyle", "Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;", "bgImagePath", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IIIILxc/engine/core/graphics/engine/core/Color;Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;Ljava/lang/String;)V", "(II)V", "(IIII)V", "(IIIILxc/engine/core/graphics/engine/core/Color;)V", "(IIIILxc/engine/core/graphics/engine/core/Color;Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;)V", "getWidth", "()I", "setWidth", "(I)V", "getHeight", "setHeight", "getMaxWidth", "setMaxWidth", "getMaxHeight", "setMaxHeight", "getBgColor", "()Lxc/engine/core/graphics/engine/core/Color;", "setBgColor", "(Lxc/engine/core/graphics/engine/core/Color;)V", "getBgStyle", "()Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;", "setBgStyle", "(Lxc/engine/core/graphics/engine/systems/BgSystem$EBgStyle;)V", "getBgImagePath", "()Ljava/lang/String;", "setBgImagePath", "(Ljava/lang/String;)V", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class PageInfo {
        private Color bgColor;
        private String bgImagePath;
        private BgSystem.EBgStyle bgStyle;
        private int height;
        private int maxHeight;
        private int maxWidth;
        private int width;

        public PageInfo(int i, int i2, int i3, int i4, Color bgColor, BgSystem.EBgStyle bgStyle, String str) {
            Intrinsics.checkNotNullParameter(bgColor, "bgColor");
            Intrinsics.checkNotNullParameter(bgStyle, "bgStyle");
            this.width = i;
            this.height = i2;
            this.maxWidth = i3;
            this.maxHeight = i4;
            this.bgColor = bgColor;
            this.bgStyle = bgStyle;
            this.bgImagePath = str;
        }

        public /* synthetic */ PageInfo(int i, int i2, int i3, int i4, Color color, BgSystem.EBgStyle eBgStyle, String str, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, i3, i4, color, eBgStyle, (i5 & 64) != 0 ? null : str);
        }

        public final int getWidth() {
            return this.width;
        }

        public final void setWidth(int i) {
            this.width = i;
        }

        public final int getHeight() {
            return this.height;
        }

        public final void setHeight(int i) {
            this.height = i;
        }

        public final int getMaxWidth() {
            return this.maxWidth;
        }

        public final void setMaxWidth(int i) {
            this.maxWidth = i;
        }

        public final int getMaxHeight() {
            return this.maxHeight;
        }

        public final void setMaxHeight(int i) {
            this.maxHeight = i;
        }

        public final Color getBgColor() {
            return this.bgColor;
        }

        public final void setBgColor(Color color) {
            Intrinsics.checkNotNullParameter(color, "<set-?>");
            this.bgColor = color;
        }

        public final BgSystem.EBgStyle getBgStyle() {
            return this.bgStyle;
        }

        public final void setBgStyle(BgSystem.EBgStyle eBgStyle) {
            Intrinsics.checkNotNullParameter(eBgStyle, "<set-?>");
            this.bgStyle = eBgStyle;
        }

        public final String getBgImagePath() {
            return this.bgImagePath;
        }

        public final void setBgImagePath(String str) {
            this.bgImagePath = str;
        }

        public PageInfo(int i, int i2) {
            this(i, i2, i, i2, new Color(1.0f, 1.0f, 1.0f, 1.0f), BgSystem.EBgStyle.None);
        }

        public PageInfo(int i, int i2, int i3, int i4) {
            this(i, i2, i3, i4, new Color(1.0f, 1.0f, 1.0f, 1.0f), BgSystem.EBgStyle.None);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public PageInfo(int i, int i2, int i3, int i4, Color bgColor) {
            this(i, i2, i3, i4, bgColor, BgSystem.EBgStyle.None);
            Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public PageInfo(int i, int i2, int i3, int i4, Color bgColor, BgSystem.EBgStyle bgStyle) {
            this(i, i2, i3, i4, bgColor, bgStyle, null);
            Intrinsics.checkNotNullParameter(bgColor, "bgColor");
            Intrinsics.checkNotNullParameter(bgStyle, "bgStyle");
        }
    }

    /* JADX INFO: compiled from: PageSystem.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0007\"\u0004\b!\u0010\tR\u001a\u0010\"\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0007\"\u0004\b$\u0010\t¨\u0006%"}, d2 = {"Lxc/engine/core/graphics/engine/systems/PageSystem$PageData;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "id", "", "getId", "()I", "setId", "(I)V", a.e, "Landroid/graphics/RectF;", "getArea", "()Landroid/graphics/RectF;", "setArea", "(Landroid/graphics/RectF;)V", "originXInWS", "", "getOriginXInWS", "()F", "setOriginXInWS", "(F)V", "originYInWS", "getOriginYInWS", "setOriginYInWS", "bHasBgImage", "", "getBHasBgImage", "()Z", "setBHasBgImage", "(Z)V", "bgImageId", "getBgImageId", "setBgImageId", "bgThumbnailId", "getBgThumbnailId", "setBgThumbnailId", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class PageData {
        private RectF area = new RectF();
        private boolean bHasBgImage;
        private int bgImageId;
        private int bgThumbnailId;
        private int id;
        private float originXInWS;
        private float originYInWS;

        public final int getId() {
            return this.id;
        }

        public final void setId(int i) {
            this.id = i;
        }

        public final RectF getArea() {
            return this.area;
        }

        public final void setArea(RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "<set-?>");
            this.area = rectF;
        }

        public final float getOriginXInWS() {
            return this.originXInWS;
        }

        public final void setOriginXInWS(float f) {
            this.originXInWS = f;
        }

        public final float getOriginYInWS() {
            return this.originYInWS;
        }

        public final void setOriginYInWS(float f) {
            this.originYInWS = f;
        }

        public final boolean getBHasBgImage() {
            return this.bHasBgImage;
        }

        public final void setBHasBgImage(boolean z) {
            this.bHasBgImage = z;
        }

        public final int getBgImageId() {
            return this.bgImageId;
        }

        public final void setBgImageId(int i) {
            this.bgImageId = i;
        }

        public final int getBgThumbnailId() {
            return this.bgThumbnailId;
        }

        public final void setBgThumbnailId(int i) {
            this.bgThumbnailId = i;
        }
    }

    private final float getPagePadding() {
        float drawRatio = getCurveEngine().getPenEngine().getCanvasOperator().getDrawRatio();
        if (drawRatio == 0.0f) {
            return 50.0f;
        }
        return drawRatio * 20.0f;
    }

    /* JADX INFO: renamed from: isPdfMode, reason: from getter */
    public final boolean getIsPdfMode() {
        return this.isPdfMode;
    }

    public final void setPdfMode(boolean z) {
        this.isPdfMode = z;
    }

    public final int getStartPageIndex() {
        return this.startPageIndex;
    }

    public final void setStartPageIndex(int i) {
        this.startPageIndex = i;
        Log.d(TAG, "xl-- [PageSystem] set start page: " + i);
        jumpToStartPage();
    }

    public final boolean getStartPageJumped() {
        return this.startPageJumped;
    }

    public final void setStartPageJumped(boolean z) {
        this.startPageJumped = z;
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer, xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        super.onDestroy();
        for (PageData pageData : this.pageDataList) {
            GLUtils.INSTANCE.deleteTexture(pageData.getBgImageId());
            GLUtils.INSTANCE.deleteTexture(pageData.getBgThumbnailId());
        }
    }

    public final void setAllPageInfo(ArrayList<PageInfo> pages) {
        Intrinsics.checkNotNullParameter(pages, "pages");
        if (pages.isEmpty()) {
            return;
        }
        Log.d(TAG, "xl-- [PageSystem] setAllPageInfo pagesCount: " + pages.size());
        this.pageInfoList.clear();
        this.pageInfoList.addAll(pages);
        getCurveEngine().setPageMode(MiCurveEngine.EPageMode.Paginated2);
        generateAllPageData();
        jumpToStartPage();
    }

    private final void jumpToStartPage() {
        int i = this.startPageIndex;
        if (i <= 0 || !isValidPageIndex(i) || this.startPageJumped) {
            return;
        }
        Log.d(TAG, "xl-- [PageSystem] jump to start page " + this.startPageIndex);
        getCurveEngine().getCamera().jumpOrScrollToPage(this.startPageIndex, false);
        this.startPageJumped = true;
    }

    public final void addPage(PageInfo page) {
        Intrinsics.checkNotNullParameter(page, "page");
        Log.d(TAG, "xl-- [PageSystem] addPage: " + page.getWidth() + StringUtils.SPACE + page.getHeight());
        this.pageInfoList.add(page);
        getCurveEngine().setPageMode(MiCurveEngine.EPageMode.Paginated2);
        calculatePageDataFromIndex(this.pageInfoList.size() - 1, this.pageInfoList.size() == 1);
        markDirty();
        getCurveEngine().getBgSystem().markDirty();
        Camera.refreshView$default(getCurveEngine().getCamera(), false, 1, null);
    }

    public final void deletePage(int index) {
        if (index > this.pageInfoList.size() - 1) {
            return;
        }
        if (this.pageDataList.get(index).getBgImageId() > 0 || this.pageDataList.get(index).getBgThumbnailId() > 0) {
            AsyncGLTaskManager taskManager = getCurveEngine().getTaskManager();
            PageData pageData = this.pageDataList.get(index);
            Intrinsics.checkNotNullExpressionValue(pageData, "get(...)");
            taskManager.enqueueTask(new ReleaseBGImageTask(pageData, true));
        }
        this.pageInfoList.remove(index);
        this.pageDataList.remove(index);
        calculatePageDataFromIndex$default(this, index, false, 2, null);
        getCurveEngine().getCurveSystem().clearAllTiles();
        markDirty();
        getCurveEngine().getBgSystem().markDirty();
        getCurveEngine().getTextSystem().markDirty();
        getCurveEngine().getImgSystem().markDirty();
        getCurveEngine().getCamera().refreshView(true);
    }

    public final void adjustAllPageSize(List<? extends List<Integer>> pageSize) {
        Intrinsics.checkNotNullParameter(pageSize, "pageSize");
        if (pageSize.size() == this.pageInfoList.size()) {
            int i = 0;
            for (Object obj : pageSize) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                List list = (List) obj;
                this.pageInfoList.get(i).setWidth(((Number) list.get(0)).intValue());
                this.pageInfoList.get(i).setHeight(((Number) list.get(1)).intValue());
                this.pageInfoList.get(i).setMaxWidth(((Number) list.get(2)).intValue());
                this.pageInfoList.get(i).setMaxHeight(((Number) list.get(3)).intValue());
                i = i2;
            }
            calculatePageDataFromIndex$default(this, 0, false, 2, null);
            markDirty();
        }
        getCurveEngine().getCurveSystem().clearAllTiles();
        getCurveEngine().getBgSystem().markDirty();
        getCurveEngine().getTextSystem().markDirty();
        getCurveEngine().getImgSystem().markDirty();
        getCurveEngine().getCamera().refreshView(false);
    }

    public final void setPageSize(int index, int width, int height, int maxWidth, int maxHeight) {
        if (index > this.pageInfoList.size() - 1) {
            return;
        }
        int height2 = this.pageInfoList.get(index).getHeight();
        this.pageInfoList.get(index).setWidth(width);
        this.pageInfoList.get(index).setHeight(height);
        this.pageInfoList.get(index).setMaxWidth(maxWidth);
        this.pageInfoList.get(index).setMaxHeight(maxHeight);
        if (height2 != height) {
            calculatePageDataFromIndex$default(this, index, false, 2, null);
        } else {
            float f = width * 0.5f;
            this.pageDataList.get(index).getArea().left = -f;
            this.pageDataList.get(index).getArea().right = f;
        }
        markDirty();
        getCurveEngine().getBgSystem().markDirty();
        getCurveEngine().getTextSystem().markDirty();
        getCurveEngine().getImgSystem().markDirty();
        Camera.refreshView$default(getCurveEngine().getCamera(), false, 1, null);
    }

    public final void setPageBGColor(int pageIndex, Color color) {
        Intrinsics.checkNotNullParameter(color, "color");
        if (getCurveEngine().getPageMode() != MiCurveEngine.EPageMode.Infinite && pageIndex >= 0 && pageIndex < this.pageDataList.size()) {
            this.pageInfoList.get(pageIndex).setBgColor(color);
        }
    }

    public final void setPageBGStyle(int pageIndex, BgSystem.EBgStyle style) {
        Intrinsics.checkNotNullParameter(style, "style");
        if (getCurveEngine().getPageMode() != MiCurveEngine.EPageMode.Infinite && pageIndex >= 0 && pageIndex < this.pageDataList.size()) {
            this.pageInfoList.get(pageIndex).setBgStyle(style);
        }
    }

    public static /* synthetic */ void setPageBgImage$default(PageSystem pageSystem, int i, Bitmap bitmap, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        pageSystem.setPageBgImage(i, bitmap, z);
    }

    public final void setPageBgImage(int pageIndex, Bitmap bitmap, boolean bIsThumbnail) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        if (!isInfinite() && isValidPageIndex(pageIndex)) {
            Log.d(TAG, "xl-- [PageSystem] setPageBgImage: " + pageIndex + ", bIsThumbnail " + bIsThumbnail + StringUtils.SPACE);
            AsyncGLTaskManager taskManager = getCurveEngine().getTaskManager();
            MiCurveEngine curveEngine = getCurveEngine();
            PageInfo pageInfo = this.pageInfoList.get(pageIndex);
            Intrinsics.checkNotNullExpressionValue(pageInfo, "get(...)");
            PageData pageData = this.pageDataList.get(pageIndex);
            Intrinsics.checkNotNullExpressionValue(pageData, "get(...)");
            taskManager.enqueueTask(new UploadBGImageTask(curveEngine, pageInfo, pageData, bitmap, bIsThumbnail));
        }
    }

    public final void releasePageBgImage(int pageIndex) {
        if (getCurveEngine().getPageMode() != MiCurveEngine.EPageMode.Infinite && pageIndex >= 0 && pageIndex < this.pageDataList.size()) {
            AsyncGLTaskManager taskManager = getCurveEngine().getTaskManager();
            PageData pageData = this.pageDataList.get(pageIndex);
            Intrinsics.checkNotNullExpressionValue(pageData, "get(...)");
            taskManager.enqueueTask(new ReleaseBGImageTask(pageData, false, 2, null));
        }
    }

    private final void generateAllPageData() {
        if (this.pageInfoList.isEmpty()) {
            return;
        }
        Log.w(TAG, "xl-- [PageSystem] generate all page data");
        this.pageDataList.clear();
        float height = 0.0f;
        int i = 0;
        for (Object obj : this.pageInfoList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            PageInfo pageInfo = (PageInfo) obj;
            if (i != 0) {
                height += getPagePadding();
            }
            this.pageDataList.add(createPageDataFromPageInfo(pageInfo, i, height, true));
            height += pageInfo.getHeight();
            i = i2;
        }
    }

    static /* synthetic */ void calculatePageDataFromIndex$default(PageSystem pageSystem, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        pageSystem.calculatePageDataFromIndex(i, z);
    }

    private final void calculatePageDataFromIndex(int startIndex, boolean bShouldUploadBgImage) {
        if (!this.pageInfoList.isEmpty() && this.pageInfoList.size() >= startIndex + 1) {
            float height = startIndex == 0 ? 0.0f : this.pageDataList.get(startIndex - 1).getArea().bottom;
            int size = this.pageInfoList.size();
            while (startIndex < size) {
                if (startIndex != 0) {
                    height += getPagePadding();
                }
                PageInfo pageInfo = this.pageInfoList.get(startIndex);
                Intrinsics.checkNotNullExpressionValue(pageInfo, "get(...)");
                PageData pageDataCreatePageDataFromPageInfo = createPageDataFromPageInfo(pageInfo, startIndex, height, bShouldUploadBgImage);
                if (startIndex > this.pageDataList.size() - 1) {
                    this.pageDataList.add(pageDataCreatePageDataFromPageInfo);
                } else {
                    PageData pageData = this.pageDataList.get(startIndex);
                    Intrinsics.checkNotNullExpressionValue(pageData, "get(...)");
                    PageData pageData2 = pageData;
                    pageData2.setId(startIndex);
                    pageData2.setArea(pageDataCreatePageDataFromPageInfo.getArea());
                    pageData2.setOriginXInWS(pageDataCreatePageDataFromPageInfo.getOriginXInWS());
                    pageData2.setOriginYInWS(pageDataCreatePageDataFromPageInfo.getOriginYInWS());
                }
                height += this.pageInfoList.get(startIndex).getHeight();
                startIndex++;
            }
        }
    }

    /* JADX INFO: compiled from: PageSystem.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lxc/engine/core/graphics/engine/systems/PageSystem$ReleaseBGImageTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inPageData", "Lxc/engine/core/graphics/engine/systems/PageSystem$PageData;", "bReleaseThumbnail", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/PageSystem$PageData;Z)V", "pageData", "bNeedReleaseThumbnail", "execute", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class ReleaseBGImageTask extends BaseAsyncGLTask {
        private boolean bNeedReleaseThumbnail;
        private final PageData pageData;

        public ReleaseBGImageTask(PageData inPageData, boolean z) {
            Intrinsics.checkNotNullParameter(inPageData, "inPageData");
            this.pageData = inPageData;
            this.bNeedReleaseThumbnail = z;
        }

        public /* synthetic */ ReleaseBGImageTask(PageData pageData, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(pageData, (i & 2) != 0 ? false : z);
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) {
            Intrinsics.checkNotNullParameter(res, "res");
            GLUtils.INSTANCE.deleteTexture(this.pageData.getBgImageId());
            this.pageData.setBgImageId(0);
            if (this.bNeedReleaseThumbnail) {
                GLUtils.INSTANCE.deleteTexture(this.pageData.getBgThumbnailId());
                this.pageData.setBgThumbnailId(0);
            }
            return false;
        }
    }

    /* JADX INFO: compiled from: PageSystem.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lxc/engine/core/graphics/engine/systems/PageSystem$UploadBGImageTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inCurveEngine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", "inPageInfo", "Lxc/engine/core/graphics/engine/systems/PageSystem$PageInfo;", "inPageData", "Lxc/engine/core/graphics/engine/systems/PageSystem$PageData;", "inBitmap", "Landroid/graphics/Bitmap;", "bIsThumbnail", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;Lxc/engine/core/graphics/engine/systems/PageSystem$PageInfo;Lxc/engine/core/graphics/engine/systems/PageSystem$PageData;Landroid/graphics/Bitmap;Z)V", "curveEngine", "pageInfo", "pageData", "uploadedTexId", "", "thumbnailTexId", DoodleBitmap.KEY_BITMAP, "bThumbnail", "execute", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onRenderCompleted", "", "getOptimizedBitmap", "imagePath", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class UploadBGImageTask extends BaseAsyncGLTask {
        private boolean bThumbnail;
        private Bitmap bitmap;
        private final MiCurveEngine curveEngine;
        private final PageData pageData;
        private final PageInfo pageInfo;
        private int thumbnailTexId;
        private int uploadedTexId;

        public /* synthetic */ UploadBGImageTask(MiCurveEngine miCurveEngine, PageInfo pageInfo, PageData pageData, Bitmap bitmap, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(miCurveEngine, pageInfo, pageData, (i & 8) != 0 ? null : bitmap, (i & 16) != 0 ? false : z);
        }

        public UploadBGImageTask(MiCurveEngine inCurveEngine, PageInfo inPageInfo, PageData inPageData, Bitmap bitmap, boolean z) {
            Intrinsics.checkNotNullParameter(inCurveEngine, "inCurveEngine");
            Intrinsics.checkNotNullParameter(inPageInfo, "inPageInfo");
            Intrinsics.checkNotNullParameter(inPageData, "inPageData");
            this.curveEngine = inCurveEngine;
            this.pageInfo = inPageInfo;
            this.pageData = inPageData;
            this.bitmap = bitmap;
            this.bThumbnail = z;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) {
            Intrinsics.checkNotNullParameter(res, "res");
            if (this.bitmap == null) {
                if (this.pageInfo.getBgImagePath() == null) {
                    return false;
                }
                String bgImagePath = this.pageInfo.getBgImagePath();
                Intrinsics.checkNotNull(bgImagePath);
                Bitmap optimizedBitmap = getOptimizedBitmap(bgImagePath);
                if (optimizedBitmap == null) {
                    return false;
                }
                this.bitmap = optimizedBitmap;
            }
            if (this.bitmap == null) {
                return false;
            }
            GLUtils.Companion companion = GLUtils.INSTANCE;
            Bitmap bitmap = this.bitmap;
            Intrinsics.checkNotNull(bitmap);
            this.uploadedTexId = companion.createTexture(bitmap);
            GLUtils.INSTANCE.generateMipmap(this.uploadedTexId);
            if (this.bThumbnail) {
                this.thumbnailTexId = GLUtils.INSTANCE.createTexture(192, 192);
                int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(this.thumbnailTexId);
                GLES30.glViewport(0, 0, 192, 192);
                GLES30.glBindFramebuffer(36160, iCreateFramebufferFromTexture);
                GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES30.glClear(16384);
                TileRenderer.drawFullScreen$default(this.curveEngine.getTileRenderer(), this.uploadedTexId, true, false, 0.0f, 12, null);
                GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
            }
            createSyncHandle();
            return true;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public void onRenderCompleted() {
            if (this.bThumbnail) {
                GLUtils.INSTANCE.deleteTexture(this.pageData.getBgThumbnailId());
                GLUtils.INSTANCE.deleteTexture(this.uploadedTexId);
                this.pageData.setBgThumbnailId(this.thumbnailTexId);
            } else {
                GLUtils.INSTANCE.deleteTexture(this.pageData.getBgImageId());
                this.pageData.setBgImageId(this.uploadedTexId);
            }
            this.pageData.setBHasBgImage(true);
            setBNeedRefreshRender(RectF.intersects(this.pageData.getArea(), this.curveEngine.getScreenAreaWS()));
            if (getBNeedRefreshRender()) {
                this.curveEngine.getBgSystem().markDirty();
            }
            Log.d(PageSystem.TAG, "xl-- [PageSystem] UploadBGImageTask end, set page " + this.pageData.getId() + " bg " + (this.bThumbnail ? "thumbnail " : "") + "img, refresh: " + getBNeedRefreshRender());
        }

        private final Bitmap getOptimizedBitmap(String imagePath) {
            if (imagePath.length() == 0) {
                return null;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            File file = new File(imagePath);
            if (!file.exists()) {
                return null;
            }
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            while ((options.outWidth / i) * (options.outHeight / i) > 8388608) {
                i *= 2;
            }
            options.inSampleSize = i;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        }
    }

    static /* synthetic */ PageData createPageDataFromPageInfo$default(PageSystem pageSystem, PageInfo pageInfo, int i, float f, boolean z, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = false;
        }
        return pageSystem.createPageDataFromPageInfo(pageInfo, i, f, z);
    }

    private final PageData createPageDataFromPageInfo(PageInfo pageInfo, int index, float startHeight, boolean bShouldUploadBgImage) {
        float width = pageInfo.getWidth() * 0.5f;
        PageData pageData = new PageData();
        pageData.setId(index);
        pageData.setArea(new RectF(-width, startHeight, width, pageInfo.getHeight() + startHeight));
        pageData.setOriginXInWS((-pageInfo.getMaxWidth()) * 0.5f);
        pageData.setOriginYInWS(startHeight);
        if (pageInfo.getBgImagePath() != null && bShouldUploadBgImage) {
            Log.d(TAG, "xl-- [PageSystem] create UploadBGImageTask, bgImagePath: " + pageInfo.getBgImagePath());
            getCurveEngine().getTaskManager().enqueueTask(new UploadBGImageTask(getCurveEngine(), pageInfo, pageData, null, false, 24, null));
        }
        return pageData;
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer
    protected boolean onRenderLayer(final RectF screenRect) {
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        final Color color = getCurveEngine().getBIsDarkMode() ? this.bgColorDark : this.bgColor;
        GLES30.glBindFramebuffer(36160, getLayerFramebufferId());
        GLES30.glViewport(0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
        GLES30.glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
        GLES30.glClear(16384);
        if (!getCurveEngine().getCamera().getBViewportInitialized()) {
            return false;
        }
        GLES30.glBlendFuncSeparate(1, 0, 1, 0);
        loopPagesInTargetArea(screenRect, new Function2() { // from class: xc.engine.core.graphics.engine.systems.PageSystem$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return PageSystem.onRenderLayer$lambda$3(this.f$0, screenRect, color, (PageSystem.PageInfo) obj, (PageSystem.PageData) obj2);
            }
        });
        GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onRenderLayer$lambda$3(PageSystem pageSystem, RectF rectF, Color color, PageInfo pageInfo, PageData pageData) {
        Intrinsics.checkNotNullParameter(pageInfo, "<unused var>");
        Intrinsics.checkNotNullParameter(pageData, "pageData");
        pageSystem.pageMaskRenderer.render(rectF, pageData.getArea(), color, pageSystem.pageCornerRadius);
        return Unit.INSTANCE;
    }

    public final void loopPagesInTargetArea(RectF area, Function2<? super PageInfo, ? super PageData, Unit> callback) {
        Intrinsics.checkNotNullParameter(area, "area");
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = this.pageDataList.size();
        for (int i = 0; i < size; i++) {
            PageData pageData = this.pageDataList.get(i);
            Intrinsics.checkNotNullExpressionValue(pageData, "get(...)");
            PageData pageData2 = pageData;
            if (pageData2.getArea().top > area.bottom) {
                return;
            }
            if (pageData2.getArea().bottom >= area.top && RectF.intersects(area, pageData2.getArea())) {
                PageInfo pageInfo = this.pageInfoList.get(i);
                Intrinsics.checkNotNullExpressionValue(pageInfo, "get(...)");
                callback.invoke(pageInfo, pageData2);
            }
        }
    }

    private final void loopCurrentScreenPages(Function2<? super PageInfo, ? super PageData, Unit> callback) {
        loopPagesInTargetArea(getCurveEngine().getScreenAreaWS(), callback);
    }

    public final PageData getPageData(int pageIndex) {
        if (pageIndex < 0 || pageIndex >= this.pageDataList.size()) {
            return new PageData();
        }
        PageData pageData = this.pageDataList.get(pageIndex);
        Intrinsics.checkNotNullExpressionValue(pageData, "get(...)");
        return pageData;
    }

    public final PointF getPageSpacePointFromScreenSpace(PointF pointSS, int pageIndex) {
        Intrinsics.checkNotNullParameter(pointSS, "pointSS");
        if (pageIndex < 0 || pageIndex >= this.pageDataList.size()) {
            return new PointF();
        }
        PointF worldSpacePointFromScreenSpacePoint = getCurveEngine().getWorldSpacePointFromScreenSpacePoint(pointSS);
        PageData pageData = this.pageDataList.get(pageIndex);
        Intrinsics.checkNotNullExpressionValue(pageData, "get(...)");
        PageData pageData2 = pageData;
        return new PointF(worldSpacePointFromScreenSpacePoint.x - pageData2.getOriginXInWS(), worldSpacePointFromScreenSpacePoint.y - pageData2.getOriginYInWS());
    }

    public final RectF getRectWSFromRectPS(RectF rectPS, int pageIndex) {
        Intrinsics.checkNotNullParameter(rectPS, "rectPS");
        if (!isValidPageIndex(pageIndex)) {
            return new RectF();
        }
        PageData pageData = this.pageDataList.get(pageIndex);
        Intrinsics.checkNotNullExpressionValue(pageData, "get(...)");
        PageData pageData2 = pageData;
        return new RectF(rectPS.left + pageData2.getOriginXInWS(), rectPS.top + pageData2.getOriginYInWS(), rectPS.right + pageData2.getOriginXInWS(), rectPS.bottom + pageData2.getOriginYInWS());
    }

    public final int getPageIndexByScreenSpacePoint(PointF pointSS) {
        Intrinsics.checkNotNullParameter(pointSS, "pointSS");
        if (getCurveEngine().getPageMode() == MiCurveEngine.EPageMode.Infinite) {
            return 0;
        }
        return getPageIndexByWorldSpacePoint(getCurveEngine().getWorldSpacePointFromScreenSpacePoint(pointSS));
    }

    public final List<Integer> getVisiblePageIndexListByScreenSpacePoint() {
        final ArrayList arrayList = new ArrayList();
        loopCurrentScreenPages(new Function2() { // from class: xc.engine.core.graphics.engine.systems.PageSystem$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return PageSystem.getVisiblePageIndexListByScreenSpacePoint$lambda$4(arrayList, (PageSystem.PageInfo) obj, (PageSystem.PageData) obj2);
            }
        });
        Log.d(TAG, "visiblePageIndexList = " + arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getVisiblePageIndexListByScreenSpacePoint$lambda$4(List list, PageInfo indo, PageData data) {
        Intrinsics.checkNotNullParameter(indo, "indo");
        Intrinsics.checkNotNullParameter(data, "data");
        list.add(Integer.valueOf(data.getId()));
        return Unit.INSTANCE;
    }

    public final int getPageIndexByWorldSpacePoint(PointF pointWS) {
        Object next;
        PageData pageData;
        Intrinsics.checkNotNullParameter(pointWS, "pointWS");
        if (this.pageDataList.isEmpty()) {
            return 0;
        }
        Iterator<T> it = this.pageDataList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            PageData pageData2 = (PageData) next;
            if (pageData2.getArea().top - getPagePadding() < pointWS.y && pointWS.y <= pageData2.getArea().bottom) {
                break;
            }
        }
        PageData pageData3 = (PageData) next;
        if (pageData3 != null) {
            return pageData3.getId();
        }
        if (pointWS.y > 0.0f && (pageData = (PageData) CollectionsKt.lastOrNull((List) this.pageDataList)) != null) {
            return pageData.getId();
        }
        return 0;
    }

    public final boolean isInfinite() {
        return getCurveEngine().isInfinite();
    }

    public final float getScreenTopToPagesTopDistance() {
        if (isInfinite()) {
            return getCurveEngine().getCamera().getCurrentTop() - (getCurveEngine().getDataManager().getInfiniteBoundary().top - ((getCurveEngine().getCamera().getSizeInPixel().y * 0.5f) / getCurveEngine().getScaleRatio()));
        }
        return getCurveEngine().getCamera().getCurrentTop();
    }

    public final boolean hasReachedTop() {
        return !isInfinite() && getScreenTopToPagesTopDistance() <= 0.0f;
    }

    public final float getScreenBottomToPagesBottomDistance() {
        return getCurveEngine().getScreenAreaWS().bottom - ((PageData) CollectionsKt.last((List) this.pageDataList)).getArea().bottom;
    }

    public final float getAllowedMaxHeight() {
        if (isInfinite()) {
            return Float.MAX_VALUE;
        }
        return Math.max(getContentTotalHeight(), getCurveEngine().getCamera().getSizeInPixel().y / getCurveEngine().getScaleRatio());
    }

    public final float getAllowedMaxLeft() {
        if (isInfinite()) {
            return Float.MAX_VALUE;
        }
        RectF currentViewportPagesArea = getCurrentViewportPagesArea();
        RectF screenAreaWS = getCurveEngine().getScreenAreaWS();
        if (screenAreaWS.width() < currentViewportPagesArea.width()) {
            return currentViewportPagesArea.left;
        }
        return currentViewportPagesArea.centerX() - (screenAreaWS.width() * 0.5f);
    }

    public final float getAllowedMaxRight() {
        if (isInfinite()) {
            return Float.MAX_VALUE;
        }
        RectF currentViewportPagesArea = getCurrentViewportPagesArea();
        RectF screenAreaWS = getCurveEngine().getScreenAreaWS();
        if (screenAreaWS.width() < currentViewportPagesArea.width()) {
            return currentViewportPagesArea.right;
        }
        return currentViewportPagesArea.centerX() + (screenAreaWS.width() * 0.5f);
    }

    public final boolean hasReachedBottom() {
        return !isInfinite() && getScreenBottomToPagesBottomDistance() > 0.0f;
    }

    public final float getContentTotalHeight() {
        if (isInfinite()) {
            return 0.0f;
        }
        return ((PageData) CollectionsKt.last((List) this.pageDataList)).getArea().bottom;
    }

    public final RectF getCurrentViewportPagesArea() {
        final RectF rectF = new RectF();
        loopCurrentScreenPages(new Function2() { // from class: xc.engine.core.graphics.engine.systems.PageSystem$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return PageSystem.getCurrentViewportPagesArea$lambda$6(rectF, (PageSystem.PageInfo) obj, (PageSystem.PageData) obj2);
            }
        });
        return rectF;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getCurrentViewportPagesArea$lambda$6(RectF rectF, PageInfo pageInfo, PageData pageData) {
        Intrinsics.checkNotNullParameter(pageInfo, "<unused var>");
        Intrinsics.checkNotNullParameter(pageData, "pageData");
        rectF.union(pageData.getArea());
        return Unit.INSTANCE;
    }

    public final boolean isValidPageIndex(int index) {
        return !isInfinite() && index >= 0 && index < this.pageDataList.size();
    }

    public static /* synthetic */ boolean isBgDark$default(PageSystem pageSystem, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return pageSystem.isBgDark(i);
    }

    public final boolean isBgDark(int pageIndex) {
        if (isInfinite()) {
            return getCurveEngine().getBgSystem().getBgColor().getIntValue() == Color.INSTANCE.getBLACK().getIntValue();
        }
        return isValidPageIndex(pageIndex) && this.pageInfoList.get(pageIndex).getBgColor().getIntValue() == Color.INSTANCE.getBLACK().getIntValue();
    }

    public final PointF getDeltaOffsetWS(int curPageIndex, int targetPageIndex) {
        PageData pageData = getPageData(curPageIndex);
        PageData pageData2 = getPageData(targetPageIndex);
        return new PointF(pageData2.getOriginXInWS() - pageData.getOriginXInWS(), pageData2.getOriginYInWS() - pageData.getOriginYInWS());
    }

    public final boolean pageHasBgImage(int index) {
        return index <= this.pageInfoList.size() - 1 && this.pageDataList.get(index).getBgImageId() > 0;
    }

    public final int getPageCount() {
        return this.pageInfoList.size();
    }

    public final void setCurrentPageIndex(int index) {
        this.currentPageIndex = index;
    }

    public final int getCurrentPageIndex() {
        return this.currentPageIndex;
    }
}
