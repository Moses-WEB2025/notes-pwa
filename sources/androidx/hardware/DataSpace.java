package androidx.hardware;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;

/* JADX INFO: compiled from: DataSpace.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\u0018\u0000 \u00062\u00020\u0001:\u0003\u0004\u0005\u0006B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0007"}, d2 = {"Landroidx/hardware/DataSpace;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "NamedDataSpace", "DataSpaceRange", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DataSpace {
    public static final int DATASPACE_ADOBE_RGB = 151715840;
    public static final int DATASPACE_BT2020 = 147193856;
    public static final int DATASPACE_BT2020_HLG = 168165376;
    public static final int DATASPACE_BT2020_PQ = 163971072;
    public static final int DATASPACE_BT601_525 = 281280512;
    public static final int DATASPACE_BT601_625 = 281149440;
    public static final int DATASPACE_BT709 = 281083904;
    public static final int DATASPACE_DCI_P3 = 155844608;
    public static final int DATASPACE_DEPTH = 4096;
    public static final int DATASPACE_DISPLAY_P3 = 143261696;
    public static final int DATASPACE_DYNAMIC_DEPTH = 4098;
    public static final int DATASPACE_HEIF = 4100;
    public static final int DATASPACE_JFIF = 146931712;
    public static final int DATASPACE_JPEG_R = 4101;
    public static final int DATASPACE_SCRGB = 411107328;
    public static final int DATASPACE_SCRGB_LINEAR = 406913024;
    public static final int DATASPACE_SRGB = 142671872;
    public static final int DATASPACE_SRGB_LINEAR = 138477568;
    public static final int DATASPACE_UNKNOWN = 0;
    public static final int RANGE_EXTENDED = 402653184;
    public static final int RANGE_FULL = 134217728;
    public static final int RANGE_LIMITED = 268435456;
    public static final int RANGE_UNSPECIFIED = 0;

    /* JADX INFO: compiled from: DataSpace.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/hardware/DataSpace$DataSpaceRange;", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface DataSpaceRange {
    }

    /* JADX INFO: compiled from: DataSpace.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/hardware/DataSpace$NamedDataSpace;", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface NamedDataSpace {
    }

    private DataSpace() {
    }
}
