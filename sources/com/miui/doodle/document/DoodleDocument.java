package com.miui.doodle.document;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Parcel;
import android.os.Parcelable;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.exception.InvalidSizeException;
import com.miui.doodle.utils.Utils;
import com.miui.notes.base.utils.Logger;
import defpackage.R2;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: DoodleDocument.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u000f\u0018\u0000 N2\u00020\u0001:\u0001NB%\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bB\u0011\b\u0016\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u0007\u0010\u000bJ\u000e\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u0019J\u0006\u0010C\u001a\u00020@J\u0006\u0010D\u001a\u00020@J\u0006\u0010E\u001a\u00020@J\u0018\u0010F\u001a\u00020@2\u0006\u0010G\u001a\u00020\n2\u0006\u0010H\u001a\u00020\u0003H\u0016J\b\u0010I\u001a\u00020\u0003H\u0016J\b\u0010J\u001a\u00020\u001fH\u0016J\u000e\u0010K\u001a\u00020@2\u0006\u0010L\u001a\u00020\u001fJ\u0006\u0010M\u001a\u00020@R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010$\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u001b\"\u0004\b&\u0010\u001dR\u0017\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u000e\u0010,\u001a\u00020)X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010-\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\r\"\u0004\b/\u00100R\u0011\u00101\u001a\u00020)8F¢\u0006\u0006\u001a\u0004\b2\u00103R\u001c\u00104\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010!\"\u0004\b6\u0010#R\u001c\u00107\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010!\"\u0004\b9\u0010#R\u000e\u0010:\u001a\u00020;X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010<\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\u000f\"\u0004\b>\u0010\u0011R\u0011\u0010B\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\bB\u0010\u000f¨\u0006O"}, d2 = {"Lcom/miui/doodle/document/DoodleDocument;", "Landroid/os/Parcelable;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "isRestore", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IIZ)V", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "getWidth", "()I", "getHeight", "()Z", "setRestore", "(Z)V", "documentInfo", "Lcom/miui/doodle/document/DocumentInfo;", "getDocumentInfo", "()Lcom/miui/doodle/document/DocumentInfo;", "setDocumentInfo", "(Lcom/miui/doodle/document/DocumentInfo;)V", "backgroundBitmap", "Landroid/graphics/Bitmap;", "getBackgroundBitmap", "()Landroid/graphics/Bitmap;", "setBackgroundBitmap", "(Landroid/graphics/Bitmap;)V", "cacheBgImageKey", "", "getCacheBgImageKey", "()Ljava/lang/String;", "setCacheBgImageKey", "(Ljava/lang/String;)V", "previewBitmap", "getPreviewBitmap", "setPreviewBitmap", DocumentWriter.LAYERS_BASE_PATH, "", "Lcom/miui/doodle/document/Layer;", "getLayers", "()Ljava/util/List;", "backgroundLayer", "currentLayerIndex", "getCurrentLayerIndex", "setCurrentLayerIndex", "(I)V", "currentLayer", "getCurrentLayer", "()Lcom/miui/doodle/document/Layer;", "snapShotPath", "getSnapShotPath", "setSnapShotPath", "editImageId", "getEditImageId", "setEditImageId", "clearPaint", "Landroid/graphics/Paint;", "mIsNeedCacheBgImageToFile", "getMIsNeedCacheBgImageToFile", "setMIsNeedCacheBgImageToFile", "setBg", "", "bg", "isEmpty", "clear", "clearBitmap", "cacheImage", "writeToParcel", "dest", "flags", "describeContents", "toString", "cacheBgImageToFileFromFile", "cacheKey", "cacheBgImageToFile", "CREATOR", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleDocument implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int MAX_HEIGHT = Integer.MAX_VALUE;
    public static final int MAX_WIDTH = Integer.MAX_VALUE;
    public Bitmap backgroundBitmap;
    private Layer backgroundLayer;
    private String cacheBgImageKey;
    private final Paint clearPaint;
    private int currentLayerIndex;
    public DocumentInfo documentInfo;
    private String editImageId;
    private final int height;
    private boolean isRestore;
    private final List<Layer> layers;
    private boolean mIsNeedCacheBgImageToFile;
    private Bitmap previewBitmap;
    private String snapShotPath;
    private final int width;

    public DoodleDocument() {
        this(0, 0, false, 7, null);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DoodleDocument(int i, int i2, boolean z) {
        this.width = i;
        this.height = i2;
        this.isRestore = z;
        this.layers = new ArrayList();
        this.currentLayerIndex = 1;
        this.clearPaint = new Paint();
    }

    public /* synthetic */ DoodleDocument(int i, int i2, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 1 : i, (i3 & 2) != 0 ? 1 : i2, (i3 & 4) != 0 ? false : z);
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    /* JADX INFO: renamed from: isRestore, reason: from getter */
    public final boolean getIsRestore() {
        return this.isRestore;
    }

    public final void setRestore(boolean z) {
        this.isRestore = z;
    }

    public final DocumentInfo getDocumentInfo() {
        DocumentInfo documentInfo = this.documentInfo;
        if (documentInfo != null) {
            return documentInfo;
        }
        Intrinsics.throwUninitializedPropertyAccessException("documentInfo");
        return null;
    }

    public final void setDocumentInfo(DocumentInfo documentInfo) {
        Intrinsics.checkNotNullParameter(documentInfo, "<set-?>");
        this.documentInfo = documentInfo;
    }

    public final Bitmap getBackgroundBitmap() {
        Bitmap bitmap = this.backgroundBitmap;
        if (bitmap != null) {
            return bitmap;
        }
        Intrinsics.throwUninitializedPropertyAccessException("backgroundBitmap");
        return null;
    }

    public final void setBackgroundBitmap(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "<set-?>");
        this.backgroundBitmap = bitmap;
    }

    public final String getCacheBgImageKey() {
        return this.cacheBgImageKey;
    }

    public final void setCacheBgImageKey(String str) {
        this.cacheBgImageKey = str;
    }

    public final Bitmap getPreviewBitmap() {
        return this.previewBitmap;
    }

    public final void setPreviewBitmap(Bitmap bitmap) {
        this.previewBitmap = bitmap;
    }

    public final List<Layer> getLayers() {
        return this.layers;
    }

    public final int getCurrentLayerIndex() {
        return this.currentLayerIndex;
    }

    public final void setCurrentLayerIndex(int i) {
        this.currentLayerIndex = i;
    }

    public final Layer getCurrentLayer() {
        return this.layers.get(this.currentLayerIndex);
    }

    public final String getSnapShotPath() {
        return this.snapShotPath;
    }

    public final void setSnapShotPath(String str) {
        this.snapShotPath = str;
    }

    public final String getEditImageId() {
        return this.editImageId;
    }

    public final void setEditImageId(String str) {
        this.editImageId = str;
    }

    public final boolean getMIsNeedCacheBgImageToFile() {
        return this.mIsNeedCacheBgImageToFile;
    }

    public final void setMIsNeedCacheBgImageToFile(boolean z) {
        this.mIsNeedCacheBgImageToFile = z;
    }

    public final void setBg(Bitmap bg) {
        Intrinsics.checkNotNullParameter(bg, "bg");
        setBackgroundBitmap(bg);
        Layer layer = new Layer(this.width, this.height, getBackgroundBitmap(), null, 0, 0, 0, 0, true, false, R2.attr.colorOnTertiaryContainer, null);
        this.backgroundLayer = layer;
        this.layers.add(layer);
        Logger.INSTANCE.d("DoodleDocument", "setBg w " + this.width + " h " + this.height);
        if (this.width < 0.0f || this.height < 0.0f) {
            throw new InvalidSizeException();
        }
        if (!this.isRestore) {
            int i = this.width;
            int i2 = this.height;
            this.layers.add(new Layer(i, i2, Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888), null, 0, 0, 0, 0, false, false, 1016, null));
            setDocumentInfo(new DocumentInfo());
            getDocumentInfo().setBackgroundType("dot");
        }
        this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public final boolean isEmpty() {
        for (Layer layer : this.layers) {
            Layer layer2 = this.backgroundLayer;
            if (layer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("backgroundLayer");
                layer2 = null;
            }
            if (!Intrinsics.areEqual(layer, layer2) && !layer.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public final void clear() {
        clearBitmap();
        this.layers.clear();
    }

    public final void clearBitmap() {
        Bitmap bitmap;
        Iterator<T> it = this.layers.iterator();
        while (it.hasNext()) {
            for (LayerBitmap layerBitmap : ((Layer) it.next()).getLayerBitmapList()) {
                Bitmap bitmap2 = layerBitmap.getBitmap();
                if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = layerBitmap.getBitmap()) != null) {
                    bitmap.recycle();
                }
                layerBitmap.setBitmap(null);
                layerBitmap.setCanvas(null);
            }
        }
    }

    public final void cacheImage() {
        for (Layer layer : this.layers) {
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DoodleDocument(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), false, 4, null);
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        this.isRestore = true;
        this.currentLayerIndex = parcel.readInt();
        int i = parcel.readInt();
        if (i < 0) {
            return;
        }
        int i2 = 0;
        while (true) {
            Layer layer = (Layer) parcel.readParcelable(Layer.class.getClassLoader());
            if (layer != null) {
                this.layers.add(layer);
            }
            if (i2 == i) {
                return;
            } else {
                i2++;
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        Logger.INSTANCE.d("DoodleDoc", "writeToParcel ");
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.currentLayerIndex);
        dest.writeInt(this.layers.size());
        Iterator<T> it = this.layers.iterator();
        while (it.hasNext()) {
            dest.writeParcelable((Layer) it.next(), 0);
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.document.DoodleDocument$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: DoodleDocument.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\nH\u0016J\u001d\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010\u000eR\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/doodle/document/DoodleDocument$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/miui/doodle/document/DoodleDocument;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MAX_WIDTH", "", "MAX_HEIGHT", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "(I)[Lcom/miui/doodle/document/DoodleDocument;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion implements Parcelable.Creator<DoodleDocument> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DoodleDocument createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new DoodleDocument(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DoodleDocument[] newArray(int size) {
            return new DoodleDocument[size];
        }
    }

    public String toString() {
        String string = super.toString();
        Layer layer = this.backgroundLayer;
        if (layer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundLayer");
            layer = null;
        }
        List<Layer> list = this.layers;
        return string + StringUtils.SPACE + layer + StringUtils.SPACE + list + " layers size " + list.size();
    }

    public final void cacheBgImageToFileFromFile(String cacheKey) {
        Intrinsics.checkNotNullParameter(cacheKey, "cacheKey");
        this.cacheBgImageKey = cacheKey;
    }

    public final void cacheBgImageToFile() {
        this.cacheBgImageKey = null;
        try {
            String strValueOf = String.valueOf(getBackgroundBitmap().hashCode());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(Utils.INSTANCE.getDoodleCacheDir(), strValueOf));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            Bitmap backgroundBitmap = getBackgroundBitmap();
            if (backgroundBitmap != null) {
                backgroundBitmap.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            this.cacheBgImageKey = strValueOf;
        } catch (Exception e) {
            e.printStackTrace();
            this.cacheBgImageKey = null;
        }
    }
}
