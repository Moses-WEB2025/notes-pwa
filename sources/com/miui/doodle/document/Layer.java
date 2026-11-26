package com.miui.doodle.document;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.stream.JsonReader;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.DoodleItemBase;
import com.miui.doodle.base.DoodleSelectableItemBase;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.exception.InvalidSizeException;
import com.miui.doodle.feature.action.EraserLayerBitmapInfo;
import com.miui.doodle.feature.action.LayerRemovedItemInfo;
import com.miui.doodle.feature.action.RemovedItemInfo;
import com.miui.doodle.feature.action.RemovedLayerBitmapInfo;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.doodle.feature.draw.DoodleBrushPath;
import com.miui.doodle.feature.draw.DoodleShape;
import com.miui.doodle.feature.draw.DoodleText;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.utils.Utils;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.onetrack.api.ba;
import defpackage.R2;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miui.cloud.CloudPushConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Layer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0003\u0018\u0000 \u0084\u00012\u00020\u0001:\u0002\u0084\u0001Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e¢\u0006\u0004\b\u0010\u0010\u0011B\u0011\b\u0016\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0004\b\u0010\u0010\u0014J\u000e\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020\u0003J\u000e\u0010N\u001a\u00020\u000e2\u0006\u0010O\u001a\u00020@J\u0006\u0010P\u001a\u00020LJ\u0016\u0010N\u001a\u00020L2\u0006\u0010Q\u001a\u00020\u00032\u0006\u0010O\u001a\u00020@J\u0006\u0010R\u001a\u00020SJ\u001c\u0010e\u001a\u00020L2\f\u0010f\u001a\b\u0012\u0004\u0012\u00020h0g2\u0006\u0010i\u001a\u00020jJ\f\u0010k\u001a\b\u0012\u0004\u0012\u00020l02J\u0006\u0010m\u001a\u00020LJ\u0018\u0010n\u001a\u00020L2\u0006\u0010O\u001a\u00020@2\b\u0010o\u001a\u0004\u0018\u000103J\f\u0010p\u001a\b\u0012\u0004\u0012\u00020q02J\u000e\u0010r\u001a\u00020L2\u0006\u0010s\u001a\u00020tJ\u000e\u0010u\u001a\u00020\u000e2\u0006\u0010o\u001a\u000203J\u0006\u0010v\u001a\u00020LJ\u0010\u0010w\u001a\u0004\u0018\u00010<2\u0006\u0010O\u001a\u00020xJ\u0016\u0010y\u001a\n\u0012\u0004\u0012\u00020@\u0018\u00010g2\u0006\u0010O\u001a\u00020xJ\u000e\u0010z\u001a\u00020L2\u0006\u0010{\u001a\u00020|J\u0018\u0010}\u001a\u00020L2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010~\u001a\u00020\u0003H\u0016J\b\u0010\u007f\u001a\u00020\u0003H\u0016J\u0007\u0010\u0080\u0001\u001a\u00020LJ\b\u0010\u0081\u0001\u001a\u00030\u0082\u0001J\u0007\u0010\u0083\u0001\u001a\u00020LR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0016\"\u0004\b\u001a\u0010\u0018R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0016\"\u0004\b\"\u0010\u0018R\u001a\u0010\n\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0016\"\u0004\b$\u0010\u0018R\u001a\u0010\u000b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0016\"\u0004\b&\u0010\u0018R\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0016\"\u0004\b(\u0010\u0018R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010)R\u001c\u0010*\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010 \"\u0004\b,\u0010-R\u001c\u0010.\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010 \"\u0004\b0\u0010-R \u00101\u001a\b\u0012\u0004\u0012\u00020302X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u0013\u00108\u001a\u0004\u0018\u0001038F¢\u0006\u0006\u001a\u0004\b9\u0010:R\u0013\u0010;\u001a\u0004\u0018\u00010<8F¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0019\u0010?\u001a\n\u0012\u0004\u0012\u00020@\u0018\u0001028F¢\u0006\u0006\u001a\u0004\bA\u00105R\u0011\u0010B\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\bB\u0010)R\u0011\u0010C\u001a\u00020D8F¢\u0006\u0006\u001a\u0004\bE\u0010FR\u001a\u0010G\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010)\"\u0004\bI\u0010JR \u0010T\u001a\b\u0012\u0004\u0012\u00020U02X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bV\u00105\"\u0004\bW\u00107R \u0010X\u001a\b\u0012\u0004\u0012\u0002030YX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]R \u0010^\u001a\b\u0012\u0004\u0012\u00020_02X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b`\u00105\"\u0004\ba\u00107R\u001a\u0010b\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bc\u0010)\"\u0004\bd\u0010J¨\u0006\u0085\u0001"}, d2 = {"Lcom/miui/doodle/document/Layer;", "Landroid/os/Parcelable;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "backgroundBitmap", "Landroid/graphics/Bitmap;", "id", "", Layer.KEY_TOP, "left", "alpha", "blendMode", Layer.KEY_IS_BACKGROUND_LAYER, "", "isRestore", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IILandroid/graphics/Bitmap;Ljava/lang/String;IIIIZZ)V", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "getWidth", "()I", "setWidth", "(I)V", "getHeight", "setHeight", "getBackgroundBitmap", "()Landroid/graphics/Bitmap;", "setBackgroundBitmap", "(Landroid/graphics/Bitmap;)V", "getId", "()Ljava/lang/String;", "getTop", "setTop", "getLeft", "setLeft", "getAlpha", "setAlpha", "getBlendMode", "setBlendMode", "()Z", "cacheImageKey", "getCacheImageKey", "setCacheImageKey", "(Ljava/lang/String;)V", "cacheDataInfoKey", "getCacheDataInfoKey", "setCacheDataInfoKey", "layerBitmapList", "", "Lcom/miui/doodle/document/LayerBitmap;", "getLayerBitmapList", "()Ljava/util/List;", "setLayerBitmapList", "(Ljava/util/List;)V", "currentLayerBitmap", "getCurrentLayerBitmap", "()Lcom/miui/doodle/document/LayerBitmap;", "currentCanvas", "Landroid/graphics/Canvas;", "getCurrentCanvas", "()Landroid/graphics/Canvas;", "currentItems", "Lcom/miui/doodle/base/core/IDoodleItem;", "getCurrentItems", "isEmpty", "boundsInCanvas", "Landroid/graphics/Rect;", "getBoundsInCanvas", "()Landroid/graphics/Rect;", "needReset", "getNeedReset", "setNeedReset", "(Z)V", "addNewBitmap", "", "type", "addItem", CloudPushConstants.XML_ITEM, "addNewLayerBitmap", "index", "toJSONObject", "Lorg/json/JSONObject;", "layerBitmapsRemovedItem", "Lcom/miui/doodle/feature/action/LayerRemovedItemInfo;", "getLayerBitmapsRemovedItem", "setLayerBitmapsRemovedItem", "layerBitmapsChanged", "", "getLayerBitmapsChanged", "()Ljava/util/Set;", "setLayerBitmapsChanged", "(Ljava/util/Set;)V", "removedLayerBitmapList", "Lcom/miui/doodle/feature/action/RemovedLayerBitmapInfo;", "getRemovedLayerBitmapList", "setRemovedLayerBitmapList", "startChanged", "getStartChanged", "setStartChanged", "doEraser", "eraserDots", "", "Lcom/miui/doodle/document/Dot;", "clearPaint", "Landroid/graphics/Paint;", "createEraserLayerBitmapInfoList", "Lcom/miui/doodle/feature/action/EraserLayerBitmapInfo;", "finishEraserAction", "removeItem", "layerBitmap", "createRemovedItemInfoList", "Lcom/miui/doodle/feature/action/RemovedItemInfo;", "finishRemoveAction", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", "removeLayerBitmap", "clear", "findCanvas", "Lcom/miui/doodle/base/DoodleSelectableItemBase;", "findItemsInSameLayer", "refreshDoodle", "newDoodle", "Lcom/miui/doodle/base/core/IDoodle;", "writeToParcel", "flags", "describeContents", "cacheLayerJson", "getLayerJsonCache", "", "cacheBgImageToFile", "CREATOR", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Layer implements Parcelable {
    public static final int BLEND_MODE_NORMAL = 1;

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String KEY_ALPHA = "alpha";
    public static final String KEY_BACKGROUND = "background";
    public static final String KEY_BLEND_MODE = "blend_mode";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_ID = "id";
    public static final String KEY_IS_BACKGROUND_LAYER = "isBackgroundLayer";
    public static final String KEY_ITEMS = "items";
    public static final String KEY_LAYER_BITMAPS = "layer_bitmaps";
    public static final String KEY_LEFT = "left";
    public static final String KEY_TOP = "top";
    public static final String KEY_WIDTH = "width";
    private static final String TAG = "Layer";
    private int alpha;
    private Bitmap backgroundBitmap;
    private int blendMode;
    private String cacheDataInfoKey;
    private String cacheImageKey;
    private int height;
    private final String id;
    private final boolean isBackgroundLayer;
    private List<LayerBitmap> layerBitmapList;
    private Set<LayerBitmap> layerBitmapsChanged;
    private List<LayerRemovedItemInfo> layerBitmapsRemovedItem;
    private int left;
    private boolean needReset;
    private List<RemovedLayerBitmapInfo> removedLayerBitmapList;
    private boolean startChanged;
    private int top;
    private int width;

    @JvmStatic
    public static final IDoodleItem createItems(JsonReader jsonReader, IDoodle iDoodle, int i, int i2, float f, float f2, Function2<? super String, ? super Boolean, Bitmap> function2) throws JSONException {
        return INSTANCE.createItems(jsonReader, iDoodle, i, i2, f, f2, function2);
    }

    @JvmStatic
    public static final Layer fromJSONReader(IDoodle iDoodle, JsonReader jsonReader, int i, int i2, Function2<? super String, ? super Boolean, Bitmap> function2) throws JSONException {
        return INSTANCE.fromJSONReader(iDoodle, jsonReader, i, i2, function2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Layer(int i, int i2, Bitmap bitmap, String id, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(id, "id");
        this.width = i;
        this.height = i2;
        this.backgroundBitmap = bitmap;
        this.id = id;
        this.top = i3;
        this.left = i4;
        this.alpha = i5;
        this.blendMode = i6;
        this.isBackgroundLayer = z;
        this.layerBitmapList = new CopyOnWriteArrayList();
        if (this.width < 0.0f || this.height < 0.0f) {
            throw new InvalidSizeException();
        }
        if (!z && !z2) {
            addNewBitmap(0);
        }
        this.layerBitmapsRemovedItem = new ArrayList();
        this.layerBitmapsChanged = new LinkedHashSet();
        this.removedLayerBitmapList = new ArrayList();
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

    public final Bitmap getBackgroundBitmap() {
        return this.backgroundBitmap;
    }

    public final void setBackgroundBitmap(Bitmap bitmap) {
        this.backgroundBitmap = bitmap;
    }

    public /* synthetic */ Layer(int i, int i2, Bitmap bitmap, String str, int i3, int i4, int i5, int i6, boolean z, boolean z2, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i7 & 4) != 0 ? null : bitmap, (i7 & 8) != 0 ? DoodleItemBase.INSTANCE.generateId() : str, (i7 & 16) != 0 ? 0 : i3, (i7 & 32) != 0 ? 0 : i4, (i7 & 64) != 0 ? 255 : i5, (i7 & 128) != 0 ? 1 : i6, (i7 & 256) != 0 ? false : z, (i7 & 512) != 0 ? false : z2);
    }

    public final String getId() {
        return this.id;
    }

    public final int getTop() {
        return this.top;
    }

    public final void setTop(int i) {
        this.top = i;
    }

    public final int getLeft() {
        return this.left;
    }

    public final void setLeft(int i) {
        this.left = i;
    }

    public final int getAlpha() {
        return this.alpha;
    }

    public final void setAlpha(int i) {
        this.alpha = i;
    }

    public final int getBlendMode() {
        return this.blendMode;
    }

    public final void setBlendMode(int i) {
        this.blendMode = i;
    }

    /* JADX INFO: renamed from: isBackgroundLayer, reason: from getter */
    public final boolean getIsBackgroundLayer() {
        return this.isBackgroundLayer;
    }

    public final String getCacheImageKey() {
        return this.cacheImageKey;
    }

    public final void setCacheImageKey(String str) {
        this.cacheImageKey = str;
    }

    public final String getCacheDataInfoKey() {
        return this.cacheDataInfoKey;
    }

    public final void setCacheDataInfoKey(String str) {
        this.cacheDataInfoKey = str;
    }

    public final List<LayerBitmap> getLayerBitmapList() {
        return this.layerBitmapList;
    }

    public final void setLayerBitmapList(List<LayerBitmap> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.layerBitmapList = list;
    }

    public final LayerBitmap getCurrentLayerBitmap() {
        return (LayerBitmap) CollectionsKt.lastOrNull((List) this.layerBitmapList);
    }

    public final Canvas getCurrentCanvas() {
        LayerBitmap currentLayerBitmap = getCurrentLayerBitmap();
        if (currentLayerBitmap != null) {
            return currentLayerBitmap.getCanvas();
        }
        return null;
    }

    public final List<IDoodleItem> getCurrentItems() {
        LayerBitmap currentLayerBitmap = getCurrentLayerBitmap();
        if (currentLayerBitmap != null) {
            return currentLayerBitmap.getItems();
        }
        return null;
    }

    public final boolean isEmpty() {
        Iterator<T> it = this.layerBitmapList.iterator();
        while (it.hasNext()) {
            if (!((LayerBitmap) it.next()).getItems().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public final Rect getBoundsInCanvas() {
        Rect rect = new Rect();
        synchronized (this.layerBitmapList) {
            Iterator<T> it = this.layerBitmapList.iterator();
            while (it.hasNext()) {
                for (IDoodleItem iDoodleItem : ((LayerBitmap) it.next()).getItems()) {
                    if (iDoodleItem instanceof DoodleSelectableItemBase) {
                        rect.union(((DoodleSelectableItemBase) iDoodleItem).getBoundsInCanvas());
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        return rect;
    }

    public final boolean getNeedReset() {
        return this.needReset;
    }

    public final void setNeedReset(boolean z) {
        this.needReset = z;
    }

    public final void addNewBitmap(int type) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        this.layerBitmapList.add(new LayerBitmap(null, bitmapCreateBitmap, new Canvas(bitmapCreateBitmap), type, new ArrayList(), 1, null));
    }

    public final boolean addItem(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        List<IDoodleItem> currentItems = getCurrentItems();
        boolean z = false;
        if (currentItems != null && currentItems.contains(item)) {
            return false;
        }
        LayerBitmap currentLayerBitmap = getCurrentLayerBitmap();
        if (currentLayerBitmap != null) {
            if (currentLayerBitmap.getType() == 0) {
                if ((item instanceof DoodleText) || (item instanceof DoodleBitmap)) {
                    currentLayerBitmap.setType(1);
                    addNewBitmap(2);
                    z = true;
                } else if (item instanceof DoodleBrushPath) {
                    currentLayerBitmap.setType(1);
                }
            } else if (currentLayerBitmap.getType() == 2) {
                if (!(item instanceof DoodleText) && !(item instanceof DoodleBitmap)) {
                    addNewBitmap(1);
                    z = true;
                }
            } else if ((item instanceof DoodleText) || (item instanceof DoodleBitmap)) {
                currentLayerBitmap.setType(1);
                addNewBitmap(2);
                z = true;
            }
        }
        List<IDoodleItem> currentItems2 = getCurrentItems();
        if (currentItems2 != null) {
            currentItems2.add(item);
        }
        return z;
    }

    public final void addNewLayerBitmap() {
        LayerBitmap currentLayerBitmap = getCurrentLayerBitmap();
        if (currentLayerBitmap == null || currentLayerBitmap.getType() != 2) {
            return;
        }
        addNewBitmap(1);
    }

    public final void addItem(int index, IDoodleItem item) {
        List<IDoodleItem> currentItems;
        Intrinsics.checkNotNullParameter(item, "item");
        List<IDoodleItem> currentItems2 = getCurrentItems();
        if ((currentItems2 == null || !currentItems2.contains(item)) && (currentItems = getCurrentItems()) != null) {
            currentItems.add(index, item);
        }
    }

    public final JSONObject toJSONObject() {
        JSONObject jSONObject = new JSONObject();
        long jCurrentTimeMillis = System.currentTimeMillis();
        System.currentTimeMillis();
        try {
            jSONObject.put("id", this.id);
            jSONObject.put(KEY_TOP, this.top);
            jSONObject.put("left", this.left);
            jSONObject.put(KEY_WIDTH, this.width);
            jSONObject.put(KEY_HEIGHT, this.height);
            jSONObject.put("alpha", this.alpha);
            jSONObject.put(KEY_BLEND_MODE, this.blendMode);
            jSONObject.put(KEY_IS_BACKGROUND_LAYER, this.isBackgroundLayer);
            Logger.INSTANCE.d("DocumentWriter", "write: save Layer base tojson cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray = new JSONArray();
            for (LayerBitmap layerBitmap : this.layerBitmapList) {
                jSONArray.put(layerBitmap.toJSONObject());
                arrayList.addAll(layerBitmap.getItems());
            }
            JSONArray jSONArray2 = new JSONArray();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                jSONArray2.put(((IDoodleItem) it.next()).toJSONObject());
            }
            jSONObject.put(KEY_ITEMS, jSONArray2);
            Logger.INSTANCE.d("DocumentWriter", "write: save Layer path tojson cost " + (System.currentTimeMillis() - jCurrentTimeMillis2));
            long jCurrentTimeMillis3 = System.currentTimeMillis();
            if (this.backgroundBitmap != null) {
                jSONObject.put("background", this.id);
            }
            jSONObject.put(KEY_LAYER_BITMAPS, jSONArray);
            Logger.INSTANCE.d("DocumentWriter", "write: save Layer bitmap tojson cost " + (System.currentTimeMillis() - jCurrentTimeMillis3));
            return jSONObject;
        } catch (JSONException e) {
            Logger.INSTANCE.e("Layer", "toJson failed." + e);
            return jSONObject;
        }
    }

    public final List<LayerRemovedItemInfo> getLayerBitmapsRemovedItem() {
        return this.layerBitmapsRemovedItem;
    }

    public final void setLayerBitmapsRemovedItem(List<LayerRemovedItemInfo> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.layerBitmapsRemovedItem = list;
    }

    public final Set<LayerBitmap> getLayerBitmapsChanged() {
        return this.layerBitmapsChanged;
    }

    public final void setLayerBitmapsChanged(Set<LayerBitmap> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.layerBitmapsChanged = set;
    }

    public final List<RemovedLayerBitmapInfo> getRemovedLayerBitmapList() {
        return this.removedLayerBitmapList;
    }

    public final void setRemovedLayerBitmapList(List<RemovedLayerBitmapInfo> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.removedLayerBitmapList = list;
    }

    public final boolean getStartChanged() {
        return this.startChanged;
    }

    public final void setStartChanged(boolean z) {
        this.startChanged = z;
    }

    public final void doEraser(List<Dot> eraserDots, Paint clearPaint) {
        Intrinsics.checkNotNullParameter(eraserDots, "eraserDots");
        Intrinsics.checkNotNullParameter(clearPaint, "clearPaint");
        int size = this.layerBitmapList.size();
        for (int i = 0; i < size; i++) {
            LayerBitmap layerBitmap = this.layerBitmapList.get(i);
            if (layerBitmap.doEraser(eraserDots, clearPaint)) {
                if (!this.startChanged) {
                    this.startChanged = true;
                }
                if (!this.layerBitmapsChanged.contains(layerBitmap)) {
                    this.layerBitmapsChanged.add(layerBitmap);
                }
            }
        }
    }

    public final List<EraserLayerBitmapInfo> createEraserLayerBitmapInfoList() {
        ArrayList arrayList = new ArrayList();
        for (LayerBitmap layerBitmap : this.layerBitmapsChanged) {
            layerBitmap.cacheImageAfterChanged();
            layerBitmap.createBrushPathInfoListAfterErased();
            arrayList.add(new EraserLayerBitmapInfo(layerBitmap, layerBitmap.getErasedBrushPathInfos(), layerBitmap.getRemovedBrushPathInfos(), layerBitmap.getCacheBitmapBeforeChanged(), layerBitmap.getCacheBitmapAfterChanged()));
            layerBitmap.finishChangedAction();
        }
        return arrayList;
    }

    public final void finishEraserAction() {
        this.startChanged = false;
        this.layerBitmapsChanged.clear();
    }

    public final void removeItem(IDoodleItem item, LayerBitmap layerBitmap) {
        int size;
        Intrinsics.checkNotNullParameter(item, "item");
        if (layerBitmap != null) {
            size = this.layerBitmapList.indexOf(layerBitmap);
        } else {
            size = this.layerBitmapList.size() - 1;
            while (true) {
                if (-1 >= size) {
                    size = -1;
                    break;
                } else if (this.layerBitmapList.get(size).getItems().indexOf(item) >= 0) {
                    break;
                } else {
                    size--;
                }
            }
        }
        if (size >= 0) {
            LayerBitmap layerBitmap2 = this.layerBitmapList.get(size);
            if (layerBitmap2.removeItem(item)) {
                if (!this.startChanged) {
                    this.startChanged = true;
                }
                if (layerBitmap2.getItems().isEmpty() && this.layerBitmapList.size() > 1) {
                    this.layerBitmapList.remove(layerBitmap2);
                    this.removedLayerBitmapList.add(new RemovedLayerBitmapInfo(layerBitmap2, size));
                }
                List<LayerRemovedItemInfo> list = this.layerBitmapsRemovedItem;
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(((LayerRemovedItemInfo) it.next()).getLayerBitmap(), layerBitmap2)) {
                        return;
                    }
                }
                list.add(new LayerRemovedItemInfo(layerBitmap2, size));
            }
        }
    }

    public final List<RemovedItemInfo> createRemovedItemInfoList() {
        ArrayList arrayList = new ArrayList();
        for (LayerRemovedItemInfo layerRemovedItemInfo : this.layerBitmapsRemovedItem) {
            layerRemovedItemInfo.getLayerBitmap().cacheImageAfterChanged();
            arrayList.add(new RemovedItemInfo(layerRemovedItemInfo.getLayerBitmap().getSubRemovedItemInfoList(), layerRemovedItemInfo.getLayerBitmapIndexRemovedItem(), layerRemovedItemInfo.getLayerBitmap(), layerRemovedItemInfo.getLayerBitmap().getItems().isEmpty(), layerRemovedItemInfo.getLayerBitmap().getCacheBitmapBeforeChanged(), layerRemovedItemInfo.getLayerBitmap().getCacheBitmapAfterChanged()));
            layerRemovedItemInfo.getLayerBitmap().finishRemoveAction();
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: com.miui.doodle.document.Layer$finishRemoveAction$1, reason: invalid class name */
    /* JADX INFO: compiled from: Layer.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.document.Layer$finishRemoveAction$1", f = "Layer.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(DoodleView doodleView, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return Layer.this.new AnonymousClass1(this.$doodleView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (!Layer.this.getStartChanged()) {
                return Unit.INSTANCE;
            }
            this.$doodleView.setEnabled(false);
            this.$doodleView.onProcessing(false);
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C00341(Layer.this, this.$doodleView, null), 3, null);
            this.$doodleView.setDrawing(false);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.doodle.document.Layer$finishRemoveAction$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: Layer.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.document.Layer$finishRemoveAction$1$1", f = "Layer.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, l = {R2.attr.addElevationShadow}, m = "invokeSuspend", n = {"$this$launch", "$this$forEach$iv", "element$iv", "layerBitmap", "redrawJobs", DoodleBitmap.KEY_BITMAP, "bit", "$this$forEach$iv", "element$iv", ba.e, "$i$f$forEach", "$i$a$-forEach-Layer$finishRemoveAction$1$1$1", "$i$a$-let-Layer$finishRemoveAction$1$1$1$1", "$i$f$forEach", "$i$a$-forEach-Layer$finishRemoveAction$1$1$1$1$2"}, s = {"L$0", "L$1", "L$4", "L$5", "L$6", "L$7", "L$8", "L$9", "L$11", "L$12", "I$0", "I$1", "I$2", "I$3", "I$4"})
        static final class C00341 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ DoodleView $doodleView;
            int I$0;
            int I$1;
            int I$2;
            int I$3;
            int I$4;
            private /* synthetic */ Object L$0;
            Object L$1;
            Object L$10;
            Object L$11;
            Object L$12;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            Object L$6;
            Object L$7;
            Object L$8;
            Object L$9;
            int label;
            final /* synthetic */ Layer this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00341(Layer layer, DoodleView doodleView, Continuation<? super C00341> continuation) {
                super(2, continuation);
                this.this$0 = layer;
                this.$doodleView = doodleView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00341 c00341 = new C00341(this.this$0, this.$doodleView, continuation);
                c00341.L$0 = obj;
                return c00341;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00341) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code duplicated, block: B:11:0x0082  */
            /* JADX WARN: Code duplicated, block: B:13:0x008f  */
            /* JADX WARN: Code duplicated, block: B:16:0x00cb A[LOOP:0: B:14:0x00c5->B:16:0x00cb, LOOP_END] */
            /* JADX WARN: Code duplicated, block: B:26:0x01a0  */
            /* JADX WARN: Type inference failed for: r2v11, types: [T, java.util.List] */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x00f3 -> B:18:0x0108). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x015f -> B:24:0x0164). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x01a0 -> B:27:0x01a8). Please report as a decompilation issue!!! */
            /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
                jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
                	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
                	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
                	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
                */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final java.lang.Object invokeSuspend(java.lang.Object r24) {
                /*
                    Method dump skipped, instruction units count: 529
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.miui.doodle.document.Layer.AnonymousClass1.C00341.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }
    }

    public final void finishRemoveAction(DoodleView doodleView) throws InterruptedException {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(doodleView, null), 1, null);
    }

    public final boolean removeLayerBitmap(LayerBitmap layerBitmap) {
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        return this.layerBitmapList.remove(layerBitmap);
    }

    public final void clear() {
        for (LayerBitmap layerBitmap : this.layerBitmapList) {
            Bitmap bitmap = layerBitmap.getBitmap();
            if (bitmap != null) {
                bitmap.recycle();
            }
            layerBitmap.setBitmap(null);
            layerBitmap.setCanvas(null);
            Iterator<T> it = layerBitmap.getItems().iterator();
            while (it.hasNext()) {
                ((IDoodleItem) it.next()).clear();
            }
            layerBitmap.getItems().clear();
        }
        this.layerBitmapList.clear();
    }

    public final Canvas findCanvas(DoodleSelectableItemBase item) {
        Object next;
        Intrinsics.checkNotNullParameter(item, "item");
        Iterator<T> it = this.layerBitmapList.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((LayerBitmap) next).getItems().contains(item));
        LayerBitmap layerBitmap = (LayerBitmap) next;
        if (layerBitmap != null) {
            return layerBitmap.getCanvas();
        }
        return null;
    }

    public final List<IDoodleItem> findItemsInSameLayer(DoodleSelectableItemBase item) {
        Object next;
        Intrinsics.checkNotNullParameter(item, "item");
        Iterator<T> it = this.layerBitmapList.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((LayerBitmap) next).getItems().contains(item));
        LayerBitmap layerBitmap = (LayerBitmap) next;
        if (layerBitmap != null) {
            return layerBitmap.getItems();
        }
        return null;
    }

    public final void refreshDoodle(IDoodle newDoodle) {
        Intrinsics.checkNotNullParameter(newDoodle, "newDoodle");
        Iterator<T> it = this.layerBitmapList.iterator();
        while (it.hasNext()) {
            Iterator<T> it2 = ((LayerBitmap) it.next()).getItems().iterator();
            while (it2.hasNext()) {
                ((IDoodleItem) it2.next()).refreshDoodle(newDoodle);
            }
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Layer(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        String string = parcel.readString();
        Intrinsics.checkNotNull(string, "null cannot be cast to non-null type kotlin.String");
        int i3 = 0;
        this(i, i2, null, string, parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readByte() != 0, true);
        this.needReset = parcel.readByte() != 0;
        int i4 = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        if (i4 >= 0) {
            while (true) {
                LayerBitmap layerBitmap = (LayerBitmap) parcel.readParcelable(LayerBitmap.class.getClassLoader());
                if (layerBitmap != null) {
                    arrayList.add(layerBitmap);
                }
                if (i3 == i4) {
                    break;
                } else {
                    i3++;
                }
            }
        }
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.layerBitmapList = copyOnWriteArrayList;
        copyOnWriteArrayList.addAll(arrayList);
        arrayList.clear();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeString(this.id);
        parcel.writeInt(this.top);
        parcel.writeInt(this.left);
        parcel.writeInt(this.alpha);
        parcel.writeInt(this.blendMode);
        parcel.writeByte(this.isBackgroundLayer ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.needReset ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.layerBitmapList.size());
        Iterator<T> it = this.layerBitmapList.iterator();
        while (it.hasNext()) {
            parcel.writeParcelable((LayerBitmap) it.next(), flags);
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.document.Layer$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: Layer.kt */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004JD\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u00132\u001a\u0010\u001b\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u001d\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001cH\u0007JV\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010!\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u001a\u0010\u001b\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u001d\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001cH\u0007J:\u0010&\u001a\u00020'2\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010(\u001a\b\u0012\u0004\u0012\u00020 0)2\u001a\u0010\u001b\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u001d\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001cH\u0002J\u0010\u0010*\u001a\u00020\u00022\u0006\u0010+\u001a\u00020,H\u0016J\u001d\u0010-\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020.2\u0006\u0010/\u001a\u00020\u0013H\u0016¢\u0006\u0002\u00100R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0086T¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lcom/miui/doodle/document/Layer$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/miui/doodle/document/Layer;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "KEY_ID", "KEY_TOP", "KEY_LEFT", "KEY_WIDTH", "KEY_HEIGHT", "KEY_ITEMS", "KEY_BACKGROUND", "KEY_ALPHA", "KEY_BLEND_MODE", "KEY_LAYER_BITMAPS", "KEY_IS_BACKGROUND_LAYER", "BLEND_MODE_NORMAL", "", "fromJSONReader", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "mJsonReader", "Lcom/google/gson/stream/JsonReader;", "disW", "disH", "readBitmapFunc", "Lkotlin/Function2;", "", "Landroid/graphics/Bitmap;", "createItems", "Lcom/miui/doodle/base/core/IDoodleItem;", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "offsetX", "", "offsetY", "createLayerBitmap", "Lcom/miui/doodle/document/LayerBitmap;", "allItems", "", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "(I)[Lcom/miui/doodle/document/Layer;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion implements Parcelable.Creator<Layer> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Layer fromJSONReader(IDoodle doodle, JsonReader mJsonReader, int disW, int disH, Function2<? super String, ? super Boolean, Bitmap> readBitmapFunc) throws JSONException, IOException {
            String str;
            doodle = doodle;
            Intrinsics.checkNotNullParameter(doodle, "doodle");
            Intrinsics.checkNotNullParameter(mJsonReader, "mJsonReader");
            Intrinsics.checkNotNullParameter(readBitmapFunc, "readBitmapFunc");
            ArrayList arrayList = new ArrayList();
            mJsonReader.beginObject();
            Layer layer = null;
            float f = 0.0f;
            float f2 = 0.0f;
            String strNextString = "";
            String str2 = strNextString;
            int iNextInt = 0;
            int iMax = 0;
            int iMax2 = 0;
            int iNextInt2 = 0;
            int iNextInt3 = 0;
            int iNextInt4 = 0;
            int iNextInt5 = 0;
            boolean zNextBoolean = false;
            while (mJsonReader.hasNext()) {
                String strNextName = mJsonReader.nextName();
                if (strNextName != null) {
                    switch (strNextName.hashCode()) {
                        case -1332194002:
                            str = strNextString;
                            if (strNextName.equals("background")) {
                                strNextString = mJsonReader.nextString();
                                Intrinsics.checkNotNullExpressionValue(strNextString, "nextString(...)");
                                doodle = doodle;
                            }
                            mJsonReader.skipValue();
                            strNextString = str;
                            break;
                        case -1221029593:
                            str = strNextString;
                            if (strNextName.equals(Layer.KEY_HEIGHT)) {
                                int iNextInt6 = mJsonReader.nextInt();
                                if (iNextInt < disW || iNextInt6 < disH) {
                                    iMax = Math.max(iNextInt, disW);
                                    iMax2 = Math.max(iNextInt6, disH);
                                } else {
                                    iMax = iNextInt;
                                    iMax2 = iNextInt6;
                                }
                                f2 = (iMax2 - iNextInt6) * 0.5f;
                                f = (iMax - iNextInt) * 0.5f;
                            } else {
                                mJsonReader.skipValue();
                            }
                            strNextString = str;
                            break;
                        case -677982154:
                            if (strNextName.equals(Layer.KEY_LAYER_BITMAPS)) {
                                String str3 = strNextString;
                                Layer layer2 = new Layer(iMax, iMax2, readBitmapFunc.invoke(strNextString, false), str2, iNextInt2, iNextInt3, iNextInt4, iNextInt5, zNextBoolean, true);
                                mJsonReader.beginArray();
                                while (mJsonReader.hasNext()) {
                                    layer2.getLayerBitmapList().add(createLayerBitmap(mJsonReader, arrayList, readBitmapFunc));
                                }
                                mJsonReader.endArray();
                                doodle = doodle;
                                layer = layer2;
                                strNextString = str3;
                            }
                            break;
                        case R2.color.m3_calendar_item_stroke_color /* 3355 */:
                            if (strNextName.equals("id")) {
                                String strNextString2 = mJsonReader.nextString();
                                Intrinsics.checkNotNullExpressionValue(strNextString2, "nextString(...)");
                                str2 = strNextString2;
                                doodle = doodle;
                            }
                            break;
                        case 115029:
                            if (strNextName.equals(Layer.KEY_TOP)) {
                                iNextInt2 = mJsonReader.nextInt();
                                doodle = doodle;
                            }
                            break;
                        case 3317767:
                            if (strNextName.equals("left")) {
                                iNextInt3 = mJsonReader.nextInt();
                                doodle = doodle;
                            }
                            break;
                        case 92909918:
                            if (strNextName.equals("alpha")) {
                                iNextInt4 = mJsonReader.nextInt();
                                doodle = doodle;
                            }
                            break;
                        case 100526016:
                            if (strNextName.equals(Layer.KEY_ITEMS)) {
                                mJsonReader.beginArray();
                                while (mJsonReader.hasNext()) {
                                    int i = iMax;
                                    IDoodleItem iDoodleItemCreateItems = createItems(mJsonReader, doodle, i, iMax2, f, f2, readBitmapFunc);
                                    iNextInt = iNextInt;
                                    iMax = i;
                                    if (iDoodleItemCreateItems != null) {
                                        arrayList.add(iDoodleItemCreateItems);
                                    }
                                    doodle = doodle;
                                }
                                mJsonReader.endArray();
                                str = strNextString;
                            }
                            strNextString = str;
                            break;
                        case 113126854:
                            if (strNextName.equals(Layer.KEY_WIDTH)) {
                                iNextInt = mJsonReader.nextInt();
                            }
                            break;
                        case 2010307793:
                            if (strNextName.equals(Layer.KEY_BLEND_MODE)) {
                                iNextInt5 = mJsonReader.nextInt();
                            }
                            break;
                        case 2079723065:
                            if (strNextName.equals(Layer.KEY_IS_BACKGROUND_LAYER)) {
                                zNextBoolean = mJsonReader.nextBoolean();
                            }
                            break;
                        default:
                            break;
                    }
                }
                str = strNextString;
                mJsonReader.skipValue();
                strNextString = str;
            }
            String str4 = strNextString;
            mJsonReader.endObject();
            if (layer != null) {
                return layer;
            }
            Layer layer3 = new Layer(iMax, iMax2, readBitmapFunc.invoke(str4, false), str2, iNextInt2, iNextInt3, iNextInt4, iNextInt5, zNextBoolean, true);
            layer3.addNewBitmap(0);
            layer3.setNeedReset(true);
            return layer3;
        }

        /* JADX WARN: Code duplicated, block: B:139:0x0477 A[PHI: r3 r14 r27 r28
  0x0477: PHI (r3v7 java.util.ArrayList) = 
  (r3v8 java.util.ArrayList)
  (r3v12 java.util.ArrayList)
  (r3v13 java.util.ArrayList)
  (r3v14 java.util.ArrayList)
  (r3v15 java.util.ArrayList)
  (r3v16 java.util.ArrayList)
  (r3v17 java.util.ArrayList)
  (r3v18 java.util.ArrayList)
  (r3v19 java.util.ArrayList)
  (r3v20 java.util.ArrayList)
  (r3v21 java.util.ArrayList)
  (r3v22 java.util.ArrayList)
  (r3v23 java.util.ArrayList)
  (r3v24 java.util.ArrayList)
 binds: [B:138:0x046f, B:135:0x0467, B:131:0x0447, B:127:0x0426, B:123:0x0403, B:119:0x03e2, B:115:0x03c0, B:111:0x039e, B:107:0x037c, B:99:0x0351, B:95:0x0330, B:91:0x030d, B:83:0x02e3, B:79:0x02c0] A[DONT_GENERATE, DONT_INLINE]
  0x0477: PHI (r14v0 java.util.ArrayList) = 
  (r14v1 java.util.ArrayList)
  (r14v4 java.util.ArrayList)
  (r14v5 java.util.ArrayList)
  (r14v6 java.util.ArrayList)
  (r14v7 java.util.ArrayList)
  (r14v8 java.util.ArrayList)
  (r14v9 java.util.ArrayList)
  (r14v10 java.util.ArrayList)
  (r14v11 java.util.ArrayList)
  (r14v12 java.util.ArrayList)
  (r14v13 java.util.ArrayList)
  (r14v14 java.util.ArrayList)
  (r14v15 java.util.ArrayList)
  (r14v16 java.util.ArrayList)
 binds: [B:138:0x046f, B:135:0x0467, B:131:0x0447, B:127:0x0426, B:123:0x0403, B:119:0x03e2, B:115:0x03c0, B:111:0x039e, B:107:0x037c, B:99:0x0351, B:95:0x0330, B:91:0x030d, B:83:0x02e3, B:79:0x02c0] A[DONT_GENERATE, DONT_INLINE]
  0x0477: PHI (r27v0 java.util.ArrayList) = 
  (r27v1 java.util.ArrayList)
  (r27v5 java.util.ArrayList)
  (r27v6 java.util.ArrayList)
  (r27v7 java.util.ArrayList)
  (r27v8 java.util.ArrayList)
  (r27v9 java.util.ArrayList)
  (r27v10 java.util.ArrayList)
  (r27v11 java.util.ArrayList)
  (r27v12 java.util.ArrayList)
  (r27v13 java.util.ArrayList)
  (r27v14 java.util.ArrayList)
  (r27v15 java.util.ArrayList)
  (r27v16 java.util.ArrayList)
  (r27v17 java.util.ArrayList)
 binds: [B:138:0x046f, B:135:0x0467, B:131:0x0447, B:127:0x0426, B:123:0x0403, B:119:0x03e2, B:115:0x03c0, B:111:0x039e, B:107:0x037c, B:99:0x0351, B:95:0x0330, B:91:0x030d, B:83:0x02e3, B:79:0x02c0] A[DONT_GENERATE, DONT_INLINE]
  0x0477: PHI (r28v0 java.util.ArrayList) = 
  (r28v1 java.util.ArrayList)
  (r28v5 java.util.ArrayList)
  (r28v6 java.util.ArrayList)
  (r28v7 java.util.ArrayList)
  (r28v8 java.util.ArrayList)
  (r28v9 java.util.ArrayList)
  (r28v10 java.util.ArrayList)
  (r28v11 java.util.ArrayList)
  (r28v12 java.util.ArrayList)
  (r28v13 java.util.ArrayList)
  (r28v14 java.util.ArrayList)
  (r28v15 java.util.ArrayList)
  (r28v16 java.util.ArrayList)
  (r28v17 java.util.ArrayList)
 binds: [B:138:0x046f, B:135:0x0467, B:131:0x0447, B:127:0x0426, B:123:0x0403, B:119:0x03e2, B:115:0x03c0, B:111:0x039e, B:107:0x037c, B:99:0x0351, B:95:0x0330, B:91:0x030d, B:83:0x02e3, B:79:0x02c0] A[DONT_GENERATE, DONT_INLINE]] */
        @JvmStatic
        public final IDoodleItem createItems(JsonReader mJsonReader, IDoodle doodle, int width, int height, float offsetX, float offsetY, Function2<? super String, ? super Boolean, Bitmap> readBitmapFunc) throws JSONException, IOException {
            ArrayList arrayList;
            ArrayList arrayList2;
            ArrayList arrayList3;
            ArrayList arrayList4;
            Intrinsics.checkNotNullParameter(mJsonReader, "mJsonReader");
            doodle = doodle;
            Intrinsics.checkNotNullParameter(doodle, "doodle");
            Intrinsics.checkNotNullParameter(readBitmapFunc, "readBitmapFunc");
            ArrayList arrayList5 = new ArrayList();
            ArrayList arrayList6 = new ArrayList();
            ArrayList arrayList7 = new ArrayList();
            ArrayList arrayList8 = new ArrayList();
            mJsonReader.beginObject();
            DoodleText doodleText = null;
            String str = "";
            String str2 = str;
            String str3 = str2;
            String str4 = str3;
            boolean zNextBoolean = false;
            int iNextInt = 0;
            float fNextDouble = 0.0f;
            float fNextDouble2 = 0.0f;
            float fNextDouble3 = 0.0f;
            float fNextDouble4 = 0.0f;
            float fNextDouble5 = 0.0f;
            float fNextDouble6 = 0.0f;
            float fNextDouble7 = 0.0f;
            float fNextDouble8 = 0.0f;
            float fNextDouble9 = 0.0f;
            float fNextDouble10 = 0.0f;
            float fNextDouble11 = 0.0f;
            float fNextDouble12 = 0.0f;
            float fNextDouble13 = 0.0f;
            float fNextDouble14 = 0.0f;
            int iNextInt2 = 255;
            while (mJsonReader.hasNext()) {
                String strNextName = mJsonReader.nextName();
                if (strNextName != null) {
                    switch (strNextName.hashCode()) {
                        case -2115337775:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleText.KEY_TEXT_COLOR)) {
                                iNextInt = mJsonReader.nextInt();
                            } else {
                                mJsonReader.skipValue();
                            }
                            arrayList8 = arrayList4;
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case -1952510690:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleItemBase.KEY_ITEM_SCALE)) {
                                arrayList8 = arrayList4;
                                fNextDouble6 = (float) mJsonReader.nextDouble();
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case -1388777169:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleBitmap.KEY_BITMAP)) {
                                String strNextString = mJsonReader.nextString();
                                Intrinsics.checkNotNullExpressionValue(strNextString, "nextString(...)");
                                arrayList8 = arrayList4;
                                str3 = strNextString;
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case -1173656152:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleShape.KEY_GRAPH_TYPE)) {
                                String strNextString2 = mJsonReader.nextString();
                                Intrinsics.checkNotNullExpressionValue(strNextString2, "nextString(...)");
                                arrayList8 = arrayList4;
                                str2 = strNextString2;
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case -1037596717:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleText.KEY_TEXT_SIZE)) {
                                arrayList8 = arrayList4;
                                fNextDouble14 = (float) mJsonReader.nextDouble();
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case -938578798:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals("radius")) {
                                arrayList8 = arrayList4;
                                fNextDouble9 = (float) mJsonReader.nextDouble();
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case -560345157:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleItemBase.KEY_PIVOT_X)) {
                                arrayList8 = arrayList4;
                                fNextDouble3 = (float) mJsonReader.nextDouble();
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case -560345156:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleItemBase.KEY_PIVOT_Y)) {
                                arrayList8 = arrayList4;
                                fNextDouble4 = (float) mJsonReader.nextDouble();
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case -508885556:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleShape.KEY_FITTED_DOTS)) {
                                mJsonReader.beginArray();
                                while (mJsonReader.hasNext()) {
                                    arrayList4.add(Dot.INSTANCE.fromJSONReader(mJsonReader));
                                }
                                mJsonReader.endArray();
                            } else {
                                mJsonReader.skipValue();
                            }
                            arrayList8 = arrayList4;
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case -437380113:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleShape.KEY_INITIAL_ANGLE)) {
                                arrayList8 = arrayList4;
                                fNextDouble13 = (float) mJsonReader.nextDouble();
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case R2.color.m3_calendar_item_stroke_color /* 3355 */:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals("id")) {
                                String strNextString3 = mJsonReader.nextString();
                                Intrinsics.checkNotNullExpressionValue(strNextString3, "nextString(...)");
                                arrayList8 = arrayList4;
                                str = strNextString3;
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case 3089482:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals(DoodleBrushPath.KEY_DOTS)) {
                                mJsonReader.beginArray();
                                while (mJsonReader.hasNext()) {
                                    arrayList3.add(Dot.INSTANCE.fromJSONReader(mJsonReader));
                                }
                                mJsonReader.endArray();
                            } else {
                                mJsonReader.skipValue();
                            }
                            arrayList8 = arrayList4;
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case 3556653:
                            arrayList2 = arrayList6;
                            arrayList = arrayList7;
                            arrayList3 = arrayList5;
                            arrayList4 = arrayList8;
                            if (strNextName.equals("text")) {
                                String strNextString4 = mJsonReader.nextString();
                                Intrinsics.checkNotNullExpressionValue(strNextString4, "nextString(...)");
                                arrayList8 = arrayList4;
                                str4 = strNextString4;
                            } else {
                                mJsonReader.skipValue();
                                arrayList8 = arrayList4;
                            }
                            arrayList5 = arrayList3;
                            arrayList6 = arrayList2;
                            arrayList7 = arrayList;
                            break;
                        case 3575610:
                            if (strNextName.equals("type")) {
                                int iNextInt3 = mJsonReader.nextInt();
                                if (iNextInt3 == 0) {
                                    ArrayList arrayList9 = arrayList6;
                                    ArrayList arrayList10 = arrayList7;
                                    DoodleBrushPath doodleBrushPath = new DoodleBrushPath(doodle, null, null, 0.0f, 0.0f, null, null, 0, R2.attr.actionBarEmbedTabs, null);
                                    String str5 = str;
                                    DoodleBrushPath doodleBrushPath2 = doodleBrushPath;
                                    boolean z = zNextBoolean;
                                    float f = fNextDouble;
                                    DoodleBrushPath.INSTANCE.createBrushItem(str5, z, f, fNextDouble2, fNextDouble3, fNextDouble4, fNextDouble5, fNextDouble6, iNextInt2, arrayList5, arrayList9, arrayList10, doodleBrushPath2, width, height);
                                    zNextBoolean = z;
                                    fNextDouble = f;
                                    str = str5;
                                    doodleBrushPath2.resetBounds(offsetX, offsetY);
                                    doodle = doodle;
                                    arrayList6 = arrayList9;
                                    arrayList7 = arrayList10;
                                    doodleText = doodleBrushPath;
                                } else {
                                    switch (iNextInt3) {
                                        case 11:
                                            arrayList2 = arrayList6;
                                            arrayList = arrayList7;
                                            DoodleBitmap doodleBitmap = new DoodleBitmap(doodle, null, null, 0.0f, 0.0f, null, null, 0, R2.attr.actionBarEmbedTabs, null);
                                            DoodleBitmap doodleBitmap2 = doodleBitmap;
                                            DoodleBitmap.INSTANCE.createBitmapItem(str, zNextBoolean, fNextDouble, fNextDouble2, fNextDouble3, fNextDouble4, fNextDouble5, fNextDouble6, doodleBitmap2, str3, readBitmapFunc);
                                            doodleBitmap2.restLocation(offsetX, offsetY);
                                            doodle = doodle;
                                            doodleText = doodleBitmap;
                                            break;
                                        case 12:
                                            arrayList2 = arrayList6;
                                            arrayList = arrayList7;
                                            doodle = doodle;
                                            doodleText = new DoodleText(doodle, null, 0, 0.0f, null, 0.0f, 0.0f, null, null, 0, R2.attr.endActionButtonStyle, null);
                                            DoodleText doodleText2 = doodleText;
                                            String str6 = str;
                                            boolean z2 = zNextBoolean;
                                            float f2 = fNextDouble;
                                            DoodleText.INSTANCE.createTextItem(str6, z2, f2, fNextDouble2, fNextDouble3, fNextDouble4, fNextDouble5, fNextDouble6, doodleText2, str4, fNextDouble14, iNextInt);
                                            str = str6;
                                            zNextBoolean = z2;
                                            fNextDouble = f2;
                                            doodleText2.restLocation(offsetX, offsetY);
                                            break;
                                        case 13:
                                            arrayList = arrayList7;
                                            doodleText = new DoodleShape(doodle, null, null, 0.0f, 0.0f, null, null, 0, R2.attr.actionBarEmbedTabs, null);
                                            DoodleShape doodleShape = doodleText;
                                            arrayList2 = arrayList6;
                                            DoodleShape.INSTANCE.createShapeItem(str, zNextBoolean, fNextDouble, fNextDouble2, fNextDouble3, fNextDouble4, fNextDouble5, fNextDouble6, iNextInt2, arrayList5, arrayList2, arrayList, doodleShape, width, height, str2, fNextDouble7, fNextDouble8, fNextDouble9, fNextDouble10, fNextDouble11, fNextDouble12, fNextDouble13, arrayList8);
                                            doodleShape.resetBounds(offsetX, offsetY);
                                            doodle = doodle;
                                            break;
                                        default:
                                            mJsonReader.skipValue();
                                            arrayList2 = arrayList6;
                                            arrayList = arrayList7;
                                            arrayList3 = arrayList5;
                                            arrayList4 = arrayList8;
                                            arrayList8 = arrayList4;
                                            arrayList5 = arrayList3;
                                            break;
                                    }
                                    arrayList6 = arrayList2;
                                    arrayList7 = arrayList;
                                }
                            }
                            break;
                        case 92909918:
                            if (strNextName.equals("alpha")) {
                                iNextInt2 = mJsonReader.nextInt();
                            }
                            break;
                        case 97243677:
                            if (strNextName.equals(DoodleBrushPath.KEY_ERASER_DOTS)) {
                                mJsonReader.beginArray();
                                while (mJsonReader.hasNext()) {
                                    arrayList7.add(Dot.INSTANCE.fromJSONReader(mJsonReader));
                                }
                                mJsonReader.endArray();
                                arrayList2 = arrayList6;
                                arrayList = arrayList7;
                                arrayList3 = arrayList5;
                                arrayList4 = arrayList8;
                                arrayList8 = arrayList4;
                                arrayList5 = arrayList3;
                                arrayList6 = arrayList2;
                                arrayList7 = arrayList;
                            }
                            break;
                        case 226654197:
                            if (strNextName.equals("rotationAngle")) {
                                fNextDouble12 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 347769642:
                            if (strNextName.equals(DoodleItemBase.KEY_ITEM_ROTATION)) {
                                fNextDouble5 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 665239203:
                            if (strNextName.equals("centerX")) {
                                fNextDouble7 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 665239204:
                            if (strNextName.equals("centerY")) {
                                fNextDouble8 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 681151738:
                            if (strNextName.equals("majorAxis")) {
                                fNextDouble10 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 697261436:
                            if (strNextName.equals(DoodleItemBase.KEY_HAS_ADD)) {
                                zNextBoolean = mJsonReader.nextBoolean();
                            }
                            break;
                        case 993779958:
                            if (strNextName.equals("minorAxis")) {
                                fNextDouble11 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 1541837422:
                            if (strNextName.equals(DoodleItemBase.KEY_LOCATION_X)) {
                                fNextDouble = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 1541837423:
                            if (strNextName.equals(DoodleItemBase.KEY_LOCATION_Y)) {
                                fNextDouble2 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 1862913528:
                            if (strNextName.equals(DoodleBrushPath.KEY_ORIGINAL_DOTS)) {
                                mJsonReader.beginArray();
                                while (mJsonReader.hasNext()) {
                                    arrayList6.add(Dot.INSTANCE.fromJSONReader(mJsonReader));
                                }
                                mJsonReader.endArray();
                                arrayList2 = arrayList6;
                                arrayList = arrayList7;
                                arrayList3 = arrayList5;
                                arrayList4 = arrayList8;
                                arrayList8 = arrayList4;
                                arrayList5 = arrayList3;
                                arrayList6 = arrayList2;
                                arrayList7 = arrayList;
                            }
                            break;
                        default:
                            break;
                    }
                }
                arrayList2 = arrayList6;
                arrayList = arrayList7;
                arrayList3 = arrayList5;
                arrayList4 = arrayList8;
                mJsonReader.skipValue();
                arrayList8 = arrayList4;
                arrayList5 = arrayList3;
                arrayList6 = arrayList2;
                arrayList7 = arrayList;
            }
            mJsonReader.endObject();
            if (doodleText != null) {
                return (DoodleItemBase) doodleText;
            }
            throw new JSONException("Doodle item type is invalid or missing");
        }

        private final LayerBitmap createLayerBitmap(JsonReader mJsonReader, List<? extends IDoodleItem> allItems, Function2<? super String, ? super Boolean, Bitmap> readBitmapFunc) {
            return LayerBitmap.INSTANCE.fromJSONReader(mJsonReader, allItems, readBitmapFunc);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Layer createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new Layer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Layer[] newArray(int size) {
            return new Layer[size];
        }
    }

    public final void cacheLayerJson() {
        this.cacheDataInfoKey = null;
        try {
            String strValueOf = String.valueOf(hashCode());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(Utils.INSTANCE.getDoodleCacheDir(), strValueOf));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            String string = toJSONObject().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            Charset UTF_8 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
            byte[] bytes = string.getBytes(UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            this.cacheDataInfoKey = strValueOf;
        } catch (Exception unused) {
            this.cacheDataInfoKey = null;
        }
    }

    public final byte[] getLayerJsonCache() throws JSONException, IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(Utils.INSTANCE.getDoodleCacheDir(), this.cacheDataInfoKey));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i = fileInputStream.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                fileInputStream.close();
                byteArrayOutputStream.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                return byteArray;
            }
        }
    }

    public final void cacheBgImageToFile() {
        this.cacheImageKey = null;
        try {
            Bitmap bitmap = this.backgroundBitmap;
            String strValueOf = String.valueOf(bitmap != null ? bitmap.hashCode() : 0);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(Utils.INSTANCE.getDoodleCacheDir(), strValueOf));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            Bitmap bitmap2 = this.backgroundBitmap;
            if (bitmap2 != null) {
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            bufferedOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            this.cacheImageKey = strValueOf;
        } catch (Exception unused) {
            this.cacheImageKey = null;
        }
    }
}
