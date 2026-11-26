package xc.engine.storage.ent.file_io;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: InkEntFileIOHelper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u0000 \u00052\u00020\u0001:\u0002\u0004\u0005B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"Lxc/engine/storage/ent/file_io/InkEntFileIOHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MiniPage", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class InkEntFileIOHelper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static List<MiniPage> PageList = new ArrayList();
    private static final float originWidth = 1000.0f;
    private static int screenWidth;

    @JvmStatic
    public static final void addPage(int i, int i2, int i3) {
        INSTANCE.addPage(i, i2, i3);
    }

    @JvmStatic
    public static final void addPage(int i, int i2, int i3, boolean z) {
        INSTANCE.addPage(i, i2, i3, z);
    }

    @JvmStatic
    public static final void clear() {
        INSTANCE.clear();
    }

    @JvmStatic
    public static final float getOffsetX(int i) {
        return INSTANCE.getOffsetX(i);
    }

    @JvmStatic
    public static final float getOffsetY(int i) {
        return INSTANCE.getOffsetY(i);
    }

    @JvmStatic
    public static final float getWSOffsetX(int i) {
        return INSTANCE.getWSOffsetX(i);
    }

    @JvmStatic
    public static final float getWSOffsetY(int i) {
        return INSTANCE.getWSOffsetY(i);
    }

    @JvmStatic
    public static final void setScreenWidth(int i) {
        INSTANCE.setScreenWidth(i);
    }

    /* JADX INFO: compiled from: InkEntFileIOHelper.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001e\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0007HÆ\u0003J\t\u0010\"\u001a\u00020\u0007HÆ\u0003J\t\u0010#\u001a\u00020\nHÆ\u0003JE\u0010$\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010%\u001a\u00020\n2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020\u0003HÖ\u0001J\t\u0010(\u001a\u00020)HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0016\"\u0004\b\u001a\u0010\u0018R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u0006*"}, d2 = {"Lxc/engine/storage/ent/file_io/InkEntFileIOHelper$MiniPage;", "", "pageIndex", "", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "offsetX", "", "offsetY", "isUnLimit", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IIIFFZ)V", "getPageIndex", "()I", "setPageIndex", "(I)V", "getWidth", "setWidth", "getHeight", "setHeight", "getOffsetX", "()F", "setOffsetX", "(F)V", "getOffsetY", "setOffsetY", "()Z", "setUnLimit", "(Z)V", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final /* data */ class MiniPage {
        private int height;
        private boolean isUnLimit;
        private float offsetX;
        private float offsetY;
        private int pageIndex;
        private int width;

        public static /* synthetic */ MiniPage copy$default(MiniPage miniPage, int i, int i2, int i3, float f, float f2, boolean z, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                i = miniPage.pageIndex;
            }
            if ((i4 & 2) != 0) {
                i2 = miniPage.width;
            }
            if ((i4 & 4) != 0) {
                i3 = miniPage.height;
            }
            if ((i4 & 8) != 0) {
                f = miniPage.offsetX;
            }
            if ((i4 & 16) != 0) {
                f2 = miniPage.offsetY;
            }
            if ((i4 & 32) != 0) {
                z = miniPage.isUnLimit;
            }
            float f3 = f2;
            boolean z2 = z;
            return miniPage.copy(i, i2, i3, f, f3, z2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getPageIndex() {
            return this.pageIndex;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getWidth() {
            return this.width;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getHeight() {
            return this.height;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final float getOffsetX() {
            return this.offsetX;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final float getOffsetY() {
            return this.offsetY;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final boolean getIsUnLimit() {
            return this.isUnLimit;
        }

        public final MiniPage copy(int pageIndex, int width, int height, float offsetX, float offsetY, boolean isUnLimit) {
            return new MiniPage(pageIndex, width, height, offsetX, offsetY, isUnLimit);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MiniPage)) {
                return false;
            }
            MiniPage miniPage = (MiniPage) other;
            return this.pageIndex == miniPage.pageIndex && this.width == miniPage.width && this.height == miniPage.height && Float.compare(this.offsetX, miniPage.offsetX) == 0 && Float.compare(this.offsetY, miniPage.offsetY) == 0 && this.isUnLimit == miniPage.isUnLimit;
        }

        public int hashCode() {
            return (((((((((Integer.hashCode(this.pageIndex) * 31) + Integer.hashCode(this.width)) * 31) + Integer.hashCode(this.height)) * 31) + Float.hashCode(this.offsetX)) * 31) + Float.hashCode(this.offsetY)) * 31) + Boolean.hashCode(this.isUnLimit);
        }

        public String toString() {
            return "MiniPage(pageIndex=" + this.pageIndex + ", width=" + this.width + ", height=" + this.height + ", offsetX=" + this.offsetX + ", offsetY=" + this.offsetY + ", isUnLimit=" + this.isUnLimit + ")";
        }

        public MiniPage(int i, int i2, int i3, float f, float f2, boolean z) {
            this.pageIndex = i;
            this.width = i2;
            this.height = i3;
            this.offsetX = f;
            this.offsetY = f2;
            this.isUnLimit = z;
        }

        public /* synthetic */ MiniPage(int i, int i2, int i3, float f, float f2, boolean z, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, i3, f, f2, (i4 & 32) != 0 ? false : z);
        }

        public final int getHeight() {
            return this.height;
        }

        public final float getOffsetX() {
            return this.offsetX;
        }

        public final float getOffsetY() {
            return this.offsetY;
        }

        public final int getPageIndex() {
            return this.pageIndex;
        }

        public final int getWidth() {
            return this.width;
        }

        public final boolean isUnLimit() {
            return this.isUnLimit;
        }

        public final void setHeight(int i) {
            this.height = i;
        }

        public final void setOffsetX(float f) {
            this.offsetX = f;
        }

        public final void setOffsetY(float f) {
            this.offsetY = f;
        }

        public final void setPageIndex(int i) {
            this.pageIndex = i;
        }

        public final void setUnLimit(boolean z) {
            this.isUnLimit = z;
        }

        public final void setWidth(int i) {
            this.width = i;
        }
    }

    /* JADX INFO: compiled from: InkEntFileIOHelper.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nH\u0007J\b\u0010\u0011\u001a\u00020\u0005H\u0002J \u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\nH\u0007J*\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\nH\u0007J\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\nH\u0007J\u0010\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\nH\u0007J\u0010\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\nH\u0007J\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\nH\u0002J\b\u0010\u001c\u001a\u00020\u000fH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lxc/engine/storage/ent/file_io/InkEntFileIOHelper$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "originWidth", "", "padding", "getPadding", "()F", "screenWidth", "", "PageList", "", "Lxc/engine/storage/ent/file_io/InkEntFileIOHelper$MiniPage;", "setScreenWidth", "", Layer.KEY_WIDTH, "getSaveRatio", "addPage", "pageIndex", Layer.KEY_HEIGHT, "isUnLimit", "", "getOffsetX", "getOffsetY", "getWSOffsetX", "getWSOffsetY", "checkPageIsNull", "clear", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final float getPadding() {
            float saveRatio = getSaveRatio();
            if (saveRatio == 0.0f) {
                return 50.0f;
            }
            return 20.0f / saveRatio;
        }

        @JvmStatic
        public final void setScreenWidth(int width) {
            InkEntFileIOHelper.screenWidth = width;
        }

        private final float getSaveRatio() {
            if (InkEntFileIOHelper.screenWidth == 0) {
                return 1.0f;
            }
            return InkEntFileIOHelper.originWidth / InkEntFileIOHelper.screenWidth;
        }

        @JvmStatic
        public final void addPage(int pageIndex, int width, int height) {
            addPage(pageIndex, width, height, false);
        }

        public static /* synthetic */ void addPage$default(Companion companion, int i, int i2, int i3, boolean z, int i4, Object obj) {
            if ((i4 & 8) != 0) {
                z = false;
            }
            companion.addPage(i, i2, i3, z);
        }

        @JvmStatic
        public final void addPage(int pageIndex, int width, int height, boolean isUnLimit) {
            float f = width * (-0.5f);
            if (InkEntFileIOHelper.PageList.isEmpty()) {
                InkEntFileIOHelper.PageList.add(new MiniPage(0, width, height, f, 0.0f, isUnLimit));
                return;
            }
            MiniPage miniPage = (MiniPage) CollectionsKt.last(InkEntFileIOHelper.PageList);
            InkEntFileIOHelper.PageList.add(new MiniPage(pageIndex, width, height, f, miniPage.getOffsetY() + miniPage.getHeight() + getPadding(), false, 32, null));
        }

        @JvmStatic
        public final float getOffsetX(int pageIndex) {
            if (checkPageIsNull(pageIndex)) {
                return 0.0f;
            }
            MiniPage miniPage = (MiniPage) InkEntFileIOHelper.PageList.get(pageIndex);
            if (miniPage.isUnLimit()) {
                return 0.0f;
            }
            return miniPage.getOffsetX() * getSaveRatio();
        }

        @JvmStatic
        public final float getOffsetY(int pageIndex) {
            if (checkPageIsNull(pageIndex)) {
                return 0.0f;
            }
            return ((MiniPage) InkEntFileIOHelper.PageList.get(pageIndex)).getOffsetY() * getSaveRatio();
        }

        @JvmStatic
        public final float getWSOffsetX(int pageIndex) {
            if (checkPageIsNull(pageIndex)) {
                return 0.0f;
            }
            MiniPage miniPage = (MiniPage) InkEntFileIOHelper.PageList.get(pageIndex);
            if (miniPage.isUnLimit()) {
                return 0.0f;
            }
            return miniPage.getOffsetX();
        }

        @JvmStatic
        public final float getWSOffsetY(int pageIndex) {
            if (checkPageIsNull(pageIndex)) {
                return 0.0f;
            }
            return ((MiniPage) InkEntFileIOHelper.PageList.get(pageIndex)).getOffsetY();
        }

        private final boolean checkPageIsNull(int pageIndex) {
            return pageIndex < 0 || pageIndex >= InkEntFileIOHelper.PageList.size();
        }

        @JvmStatic
        public final void clear() {
            InkEntFileIOHelper.screenWidth = 0;
            InkEntFileIOHelper.PageList.clear();
        }
    }
}
