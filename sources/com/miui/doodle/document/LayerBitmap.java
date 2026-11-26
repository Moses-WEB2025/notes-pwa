package com.miui.doodle.document;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.stream.JsonReader;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.base.DoodleItemBase;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.feature.action.ErasedBrushPathInfo;
import com.miui.doodle.feature.action.RemovedBrushPathInfo;
import com.miui.doodle.feature.action.SubRemovedItemInfo;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.doodle.feature.draw.DoodleBrushPath;
import com.miui.doodle.feature.draw.DoodleShape;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.feature.draw.LayerView;
import com.miui.doodle.utils.DoodleCacheManager;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miui.cloud.CloudPushConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: LayerBitmap.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\b\u0018\u0000 |2\u00020\u0001:\u0001|B?\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0004\b\r\u0010\u000eB\u0011\b\u0016\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0004\b\r\u0010\u0011J\"\u0010L\u001a\u00020\u00002\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\f0N2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\f0NJ\u0006\u0010P\u001a\u00020QJ\u001c\u0010R\u001a\u00020B2\f\u0010S\u001a\b\u0012\u0004\u0012\u00020T0N2\u0006\u0010U\u001a\u00020VJ\u0006\u0010W\u001a\u00020XJ\u0006\u0010Y\u001a\u00020XJ\u000e\u0010Z\u001a\u00020B2\u0006\u0010[\u001a\u00020\fJ\f\u0010\\\u001a\b\u0012\u0004\u0012\u0002050\u000bJ\u0006\u0010]\u001a\u00020XJ\u0016\u0010^\u001a\u00020X2\u0006\u0010_\u001a\u00020\u00072\u0006\u0010`\u001a\u00020aJ&\u0010b\u001a\u00020X2\u0006\u0010_\u001a\u00020\u00072\u0006\u0010`\u001a\u00020a2\u0006\u0010c\u001a\u00020\t2\u0006\u0010d\u001a\u00020\tJ\u000e\u0010e\u001a\u00020X2\u0006\u0010`\u001a\u00020aJ\u000e\u0010e\u001a\u00020X2\u0006\u0010f\u001a\u00020\u0005J\u0016\u0010g\u001a\u00020X2\u0006\u0010c\u001a\u00020\t2\u0006\u0010d\u001a\u00020\tJ\u0018\u0010h\u001a\u00020X2\u0006\u0010i\u001a\u00020\u00102\u0006\u0010j\u001a\u00020\tH\u0016J\b\u0010k\u001a\u00020\tH\u0016J\u0006\u0010l\u001a\u00020XJ\b\u0010m\u001a\u0004\u0018\u00010\u0005J\u0006\u0010n\u001a\u00020XJ\b\u0010o\u001a\u0004\u0018\u00010\u0005J\u0006\u0010p\u001a\u00020XJ\u0006\u0010q\u001a\u00020XJ\t\u0010r\u001a\u00020\u0003HÆ\u0003J\u000b\u0010s\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010t\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010u\u001a\u00020\tHÆ\u0003J\u000f\u0010v\u001a\b\u0012\u0004\u0012\u00020\f0\u000bHÆ\u0003JE\u0010L\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bHÆ\u0001J\u0013\u0010w\u001a\u00020B2\b\u0010x\u001a\u0004\u0018\u00010yHÖ\u0003J\t\u0010z\u001a\u00020\tHÖ\u0001J\t\u0010{\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020\u0003X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0013R\u001c\u0010$\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0013\"\u0004\b&\u0010'R\u001c\u0010(\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0015\"\u0004\b*\u0010\u0017R\u001a\u0010+\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0013\"\u0004\b-\u0010'R\u001c\u0010.\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0015\"\u0004\b0\u0010\u0017R\u001a\u00101\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u0013\"\u0004\b3\u0010'R\"\u00104\u001a\n\u0012\u0004\u0012\u000205\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010!\"\u0004\b7\u00108R\"\u00109\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010!\"\u0004\b<\u00108R\"\u0010=\u001a\n\u0012\u0004\u0012\u00020>\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010!\"\u0004\b@\u00108R\u001a\u0010A\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001a\u0010G\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010D\"\u0004\bI\u0010FR\u001a\u0010J\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010D\"\u0004\bK\u0010F¨\u0006}"}, d2 = {"Lcom/miui/doodle/document/LayerBitmap;", "Landroid/os/Parcelable;", "id", "", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "canvas", "Landroid/graphics/Canvas;", "type", "", Layer.KEY_ITEMS, "", "Lcom/miui/doodle/base/core/IDoodleItem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Landroid/graphics/Bitmap;Landroid/graphics/Canvas;ILjava/util/List;)V", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "getId", "()Ljava/lang/String;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "getCanvas", "()Landroid/graphics/Canvas;", "setCanvas", "(Landroid/graphics/Canvas;)V", "getType", "()I", "setType", "(I)V", "getItems", "()Ljava/util/List;", "TAG", "getTAG", "cacheImageKey", "getCacheImageKey", "setCacheImageKey", "(Ljava/lang/String;)V", "cacheBitmapBeforeChanged", "getCacheBitmapBeforeChanged", "setCacheBitmapBeforeChanged", "cacheImageKeyBeforeChanged", "getCacheImageKeyBeforeChanged", "setCacheImageKeyBeforeChanged", "cacheBitmapAfterChanged", "getCacheBitmapAfterChanged", "setCacheBitmapAfterChanged", "cacheImageKeyAfterChanged", "getCacheImageKeyAfterChanged", "setCacheImageKeyAfterChanged", "subRemovedItems", "Lcom/miui/doodle/feature/action/SubRemovedItemInfo;", "getSubRemovedItems", "setSubRemovedItems", "(Ljava/util/List;)V", "erasedBrushPathInfos", "Lcom/miui/doodle/feature/action/ErasedBrushPathInfo;", "getErasedBrushPathInfos", "setErasedBrushPathInfos", "removedBrushPathInfos", "Lcom/miui/doodle/feature/action/RemovedBrushPathInfo;", "getRemovedBrushPathInfos", "setRemovedBrushPathInfos", "startChanged", "", "getStartChanged", "()Z", "setStartChanged", "(Z)V", "startEraser", "getStartEraser", "setStartEraser", "isDirty", "setDirty", "copy", "allItems", "", "newAllItems", "toJSONObject", "Lorg/json/JSONObject;", "doEraser", "eraserDots", "Lcom/miui/doodle/document/Dot;", "clearPaint", "Landroid/graphics/Paint;", "finishChangedAction", "", "createBrushPathInfoListAfterErased", "removeItem", CloudPushConstants.XML_ITEM, "getSubRemovedItemInfoList", "finishRemoveAction", "drawToView", "viewCanvas", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", "elementRedrawToView", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "updateBitmap", "newBm", "rebuildBitmap", "writeToParcel", "dest", "flags", "describeContents", "cacheImageToFile", "cacheImageBeforeChanged", "cacheImageBeforeChangedToFile", "cacheImageAfterChanged", "cacheImageAfterChangedToFile", "releaseCacheImageBeforeAndAfterChanged", "component1", "component2", "component3", "component4", "component5", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "CREATOR", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class LayerBitmap implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String KEY_ID = "id";
    private static final String KEY_ITEM_IDS = "item_ids";
    private static final String KEY_TYPE = "type";
    public static final int TYPE_NONE = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_TEXT_BITMAP = 2;
    private final String TAG;
    private Bitmap bitmap;
    private Bitmap cacheBitmapAfterChanged;
    private Bitmap cacheBitmapBeforeChanged;
    private String cacheImageKey;
    private String cacheImageKeyAfterChanged;
    private String cacheImageKeyBeforeChanged;
    private Canvas canvas;
    private List<ErasedBrushPathInfo> erasedBrushPathInfos;
    private final String id;
    private boolean isDirty;
    private final List<IDoodleItem> items;
    private List<RemovedBrushPathInfo> removedBrushPathInfos;
    private boolean startChanged;
    private boolean startEraser;
    private List<SubRemovedItemInfo> subRemovedItems;
    private int type;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ LayerBitmap copy$default(LayerBitmap layerBitmap, String str, Bitmap bitmap, Canvas canvas, int i, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = layerBitmap.id;
        }
        if ((i2 & 2) != 0) {
            bitmap = layerBitmap.bitmap;
        }
        if ((i2 & 4) != 0) {
            canvas = layerBitmap.canvas;
        }
        if ((i2 & 8) != 0) {
            i = layerBitmap.type;
        }
        if ((i2 & 16) != 0) {
            list = layerBitmap.items;
        }
        List list2 = list;
        Canvas canvas2 = canvas;
        return layerBitmap.copy(str, bitmap, canvas2, i, list2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Canvas getCanvas() {
        return this.canvas;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getType() {
        return this.type;
    }

    public final List<IDoodleItem> component5() {
        return this.items;
    }

    public final LayerBitmap copy(String id, Bitmap bitmap, Canvas canvas, int type, List<IDoodleItem> items) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(items, "items");
        return new LayerBitmap(id, bitmap, canvas, type, items);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LayerBitmap)) {
            return false;
        }
        LayerBitmap layerBitmap = (LayerBitmap) other;
        return Intrinsics.areEqual(this.id, layerBitmap.id) && Intrinsics.areEqual(this.bitmap, layerBitmap.bitmap) && Intrinsics.areEqual(this.canvas, layerBitmap.canvas) && this.type == layerBitmap.type && Intrinsics.areEqual(this.items, layerBitmap.items);
    }

    public int hashCode() {
        int iHashCode = this.id.hashCode() * 31;
        Bitmap bitmap = this.bitmap;
        int iHashCode2 = (iHashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        Canvas canvas = this.canvas;
        return ((((iHashCode2 + (canvas != null ? canvas.hashCode() : 0)) * 31) + Integer.hashCode(this.type)) * 31) + this.items.hashCode();
    }

    public String toString() {
        return "LayerBitmap(id=" + this.id + ", bitmap=" + this.bitmap + ", canvas=" + this.canvas + ", type=" + this.type + ", items=" + this.items + ")";
    }

    public LayerBitmap(String id, Bitmap bitmap, Canvas canvas, int i, List<IDoodleItem> items) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(items, "items");
        this.id = id;
        this.bitmap = bitmap;
        this.canvas = canvas;
        this.type = i;
        this.items = items;
        this.TAG = "LayerBitmap";
        this.cacheImageKeyBeforeChanged = "";
        this.cacheImageKeyAfterChanged = "";
    }

    public /* synthetic */ LayerBitmap(String str, Bitmap bitmap, Canvas canvas, int i, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? DoodleItemBase.INSTANCE.generateId() : str, (i2 & 2) != 0 ? null : bitmap, (i2 & 4) != 0 ? null : canvas, i, list);
    }

    public final String getId() {
        return this.id;
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public final Canvas getCanvas() {
        return this.canvas;
    }

    public final void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int i) {
        this.type = i;
    }

    public final List<IDoodleItem> getItems() {
        return this.items;
    }

    public final String getTAG() {
        return this.TAG;
    }

    public final String getCacheImageKey() {
        return this.cacheImageKey;
    }

    public final void setCacheImageKey(String str) {
        this.cacheImageKey = str;
    }

    public final Bitmap getCacheBitmapBeforeChanged() {
        return this.cacheBitmapBeforeChanged;
    }

    public final void setCacheBitmapBeforeChanged(Bitmap bitmap) {
        this.cacheBitmapBeforeChanged = bitmap;
    }

    public final String getCacheImageKeyBeforeChanged() {
        return this.cacheImageKeyBeforeChanged;
    }

    public final void setCacheImageKeyBeforeChanged(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.cacheImageKeyBeforeChanged = str;
    }

    public final Bitmap getCacheBitmapAfterChanged() {
        return this.cacheBitmapAfterChanged;
    }

    public final void setCacheBitmapAfterChanged(Bitmap bitmap) {
        this.cacheBitmapAfterChanged = bitmap;
    }

    public final String getCacheImageKeyAfterChanged() {
        return this.cacheImageKeyAfterChanged;
    }

    public final void setCacheImageKeyAfterChanged(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.cacheImageKeyAfterChanged = str;
    }

    public final List<SubRemovedItemInfo> getSubRemovedItems() {
        return this.subRemovedItems;
    }

    public final void setSubRemovedItems(List<SubRemovedItemInfo> list) {
        this.subRemovedItems = list;
    }

    public final List<ErasedBrushPathInfo> getErasedBrushPathInfos() {
        return this.erasedBrushPathInfos;
    }

    public final void setErasedBrushPathInfos(List<ErasedBrushPathInfo> list) {
        this.erasedBrushPathInfos = list;
    }

    public final List<RemovedBrushPathInfo> getRemovedBrushPathInfos() {
        return this.removedBrushPathInfos;
    }

    public final void setRemovedBrushPathInfos(List<RemovedBrushPathInfo> list) {
        this.removedBrushPathInfos = list;
    }

    public final boolean getStartChanged() {
        return this.startChanged;
    }

    public final void setStartChanged(boolean z) {
        this.startChanged = z;
    }

    public final boolean getStartEraser() {
        return this.startEraser;
    }

    public final void setStartEraser(boolean z) {
        this.startEraser = z;
    }

    /* JADX INFO: renamed from: isDirty, reason: from getter */
    public final boolean getIsDirty() {
        return this.isDirty;
    }

    public final void setDirty(boolean z) {
        this.isDirty = z;
    }

    public final LayerBitmap copy(List<? extends IDoodleItem> allItems, List<? extends IDoodleItem> newAllItems) {
        Intrinsics.checkNotNullParameter(allItems, "allItems");
        Intrinsics.checkNotNullParameter(newAllItems, "newAllItems");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = this.items.iterator();
        while (it.hasNext()) {
            int iIndexOf = allItems.indexOf((IDoodleItem) it.next());
            if (iIndexOf >= 0 && iIndexOf < newAllItems.size()) {
                arrayList.add(newAllItems.get(iIndexOf));
            }
        }
        return new LayerBitmap(this.id, this.bitmap, this.canvas, this.type, arrayList);
    }

    public final JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", this.id);
        jSONObject.put("type", this.type);
        JSONArray jSONArray = new JSONArray();
        Iterator<T> it = this.items.iterator();
        while (it.hasNext()) {
            jSONArray.put(((IDoodleItem) it.next()).getId());
        }
        jSONObject.put(KEY_ITEM_IDS, jSONArray);
        return jSONObject;
    }

    public final boolean doEraser(List<Dot> eraserDots, Paint clearPaint) {
        Intrinsics.checkNotNullParameter(eraserDots, "eraserDots");
        Intrinsics.checkNotNullParameter(clearPaint, "clearPaint");
        if (this.type == 1) {
            Iterator<T> it = this.items.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (((IDoodleItem) it.next()).getIsDirty()) {
                    z = true;
                }
            }
            if (z) {
                if (!this.startChanged) {
                    cacheImageBeforeChanged();
                    this.startChanged = true;
                }
                Canvas canvas = this.canvas;
                if (canvas != null) {
                    for (Dot dot : eraserDots) {
                        canvas.drawCircle(dot.getX(), dot.getY(), dot.getHalfSize(), clearPaint);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public final void finishChangedAction() {
        this.startChanged = false;
        for (IDoodleItem iDoodleItem : this.items) {
            if (iDoodleItem instanceof DoodleBrushPath) {
                DoodleBrushPath doodleBrushPath = (DoodleBrushPath) iDoodleItem;
                if (doodleBrushPath.getIsDirty()) {
                    doodleBrushPath.finishChangeAction();
                }
            }
        }
        this.erasedBrushPathInfos = null;
        this.removedBrushPathInfos = null;
        releaseCacheImageBeforeAndAfterChanged();
    }

    public final void createBrushPathInfoListAfterErased() {
        List<RemovedBrushPathInfo> list;
        this.erasedBrushPathInfos = new ArrayList();
        this.removedBrushPathInfos = new ArrayList();
        ArrayList<DoodleBrushPath> arrayList = new ArrayList();
        List list2 = CollectionsKt.toList(this.items);
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            IDoodleItem iDoodleItem = (IDoodleItem) list2.get(i);
            if (iDoodleItem instanceof DoodleBrushPath) {
                DoodleBrushPath doodleBrushPath = (DoodleBrushPath) iDoodleItem;
                if (doodleBrushPath.getIsDirty()) {
                    List<ErasedBrushPathInfo> list3 = this.erasedBrushPathInfos;
                    if (list3 != null) {
                        list3.add(doodleBrushPath.createEraserBrushPathInfo());
                    }
                    if (doodleBrushPath.getDots().isEmpty()) {
                        arrayList.add(iDoodleItem);
                    }
                }
            }
        }
        for (DoodleBrushPath doodleBrushPath2 : arrayList) {
            int iIndexOf = list2.indexOf(doodleBrushPath2);
            if (this.items.remove(doodleBrushPath2) && (list = this.removedBrushPathInfos) != null) {
                list.add(new RemovedBrushPathInfo(doodleBrushPath2, iIndexOf));
            }
        }
        arrayList.clear();
    }

    public final boolean removeItem(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (!this.items.contains(item)) {
            return false;
        }
        if (!this.startChanged) {
            cacheImageBeforeChanged();
            this.startChanged = true;
            this.subRemovedItems = new ArrayList();
        }
        int iIndexOf = this.items.indexOf(item);
        this.items.remove(item);
        List<SubRemovedItemInfo> list = this.subRemovedItems;
        if (list != null) {
            list.add(new SubRemovedItemInfo(item, iIndexOf, this.items.isEmpty()));
        }
        this.isDirty = true;
        return true;
    }

    public final List<SubRemovedItemInfo> getSubRemovedItemInfoList() {
        List<SubRemovedItemInfo> list = this.subRemovedItems;
        if (list == null) {
            return new ArrayList();
        }
        Intrinsics.checkNotNull(list);
        return list;
    }

    public final void finishRemoveAction() {
        this.startChanged = false;
        this.subRemovedItems = null;
        releaseCacheImageBeforeAndAfterChanged();
    }

    public final void drawToView(Canvas viewCanvas, DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(viewCanvas, "viewCanvas");
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        if (this.type != 2) {
            if (this.isDirty) {
                if (this.items.size() > 50) {
                    Bitmap bitmap = this.bitmap;
                    if (bitmap == null || bitmap.isRecycled()) {
                        return;
                    }
                    viewCanvas.drawBitmap(bitmap, 0.0f, 0.0f, doodleView.getLayerPaint());
                    return;
                }
                Iterator<T> it = this.items.iterator();
                while (it.hasNext()) {
                    IDoodleItem.draw$default((IDoodleItem) it.next(), viewCanvas, null, null, null, 14, null);
                }
                return;
            }
            Bitmap bitmap2 = this.bitmap;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return;
            }
            viewCanvas.drawBitmap(bitmap2, 0.0f, 0.0f, doodleView.getLayerPaint());
            return;
        }
        Iterator<T> it2 = this.items.iterator();
        while (it2.hasNext()) {
            IDoodleItem.draw$default((IDoodleItem) it2.next(), viewCanvas, null, null, null, 14, null);
        }
    }

    public final void elementRedrawToView(Canvas viewCanvas, DoodleView doodleView, int width, int height) {
        Intrinsics.checkNotNullParameter(viewCanvas, "viewCanvas");
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        if (this.type != 2) {
            if (this.isDirty) {
                if (this.items.size() > 50) {
                    Bitmap bitmap = this.bitmap;
                    if (bitmap == null || bitmap.isRecycled()) {
                        return;
                    }
                    viewCanvas.drawBitmap(bitmap, 0.0f, 0.0f, doodleView.getLayerPaint());
                    return;
                }
                Iterator<T> it = this.items.iterator();
                while (it.hasNext()) {
                    ((IDoodleItem) it.next()).draw(viewCanvas, null, true, true);
                }
                return;
            }
            Bitmap bitmap2 = this.bitmap;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return;
            }
            viewCanvas.drawBitmap(bitmap2, 0.0f, 0.0f, doodleView.getLayerPaint());
            return;
        }
        Iterator<T> it2 = this.items.iterator();
        while (it2.hasNext()) {
            IDoodleItem.draw$default((IDoodleItem) it2.next(), viewCanvas, null, null, null, 14, null);
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.document.LayerBitmap$updateBitmap$1, reason: invalid class name */
    /* JADX INFO: compiled from: LayerBitmap.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.document.LayerBitmap$updateBitmap$1", f = "LayerBitmap.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        int label;
        final /* synthetic */ LayerBitmap this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(DoodleView doodleView, LayerBitmap layerBitmap, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
            this.this$0 = layerBitmap;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$doodleView, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Layer layer;
            Layer layer2;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            LayerView currentLayerView = this.$doodleView.getCurrentLayerView();
            Integer numBoxInt = null;
            Integer numBoxInt2 = (currentLayerView == null || (layer2 = currentLayerView.getLayer()) == null) ? null : Boxing.boxInt(layer2.getWidth());
            Intrinsics.checkNotNull(numBoxInt2);
            int iIntValue = numBoxInt2.intValue();
            LayerView currentLayerView2 = this.$doodleView.getCurrentLayerView();
            if (currentLayerView2 != null && (layer = currentLayerView2.getLayer()) != null) {
                numBoxInt = Boxing.boxInt(layer.getHeight());
            }
            Intrinsics.checkNotNull(numBoxInt);
            int iIntValue2 = numBoxInt.intValue();
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iIntValue, iIntValue2, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            LayerBitmap layerBitmap = this.this$0;
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint();
            Paint paint2 = new Paint();
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(iIntValue, iIntValue2, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap2, "createBitmap(...)");
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            canvas2.setBitmap(bitmapCreateBitmap2);
            canvas2.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            for (IDoodleItem iDoodleItem : layerBitmap.getItems()) {
                if ((iDoodleItem instanceof DoodleBrushPath) && !(iDoodleItem instanceof DoodleShape)) {
                    DoodleBrushPath doodleBrushPath = (DoodleBrushPath) iDoodleItem;
                    if (doodleBrushPath.getBitmap() != null) {
                        if (paint.getAlpha() != doodleBrushPath.getAlpha()) {
                            paint.setAlpha(doodleBrushPath.getAlpha());
                        }
                        Bitmap bitmap = doodleBrushPath.getBitmap();
                        Intrinsics.checkNotNull(bitmap);
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                    }
                }
                canvas2.drawPaint(paint2);
                IDoodleItem.draw$default(iDoodleItem, canvas2, null, null, null, 12, null);
                if (paint.getAlpha() != iDoodleItem.getAlpha()) {
                    paint.setAlpha(iDoodleItem.getAlpha());
                }
                canvas.drawBitmap(bitmapCreateBitmap2, 0.0f, 0.0f, paint);
            }
            this.this$0.updateBitmap(bitmapCreateBitmap);
            return Unit.INSTANCE;
        }
    }

    public final void updateBitmap(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new AnonymousClass1(doodleView, this, null), 3, null);
    }

    public final void updateBitmap(Bitmap newBm) {
        Intrinsics.checkNotNullParameter(newBm, "newBm");
        if (newBm.isRecycled()) {
            return;
        }
        Bitmap.Config config = newBm.getConfig();
        Bitmap bitmapCopy = config != null ? newBm.copy(config, true) : null;
        this.bitmap = bitmapCopy;
        this.canvas = bitmapCopy != null ? new Canvas(bitmapCopy) : null;
        this.isDirty = false;
    }

    public final void rebuildBitmap(int width, int height) {
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = this.bitmap;
        Intrinsics.checkNotNull(bitmap);
        this.canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.setBitmap(bitmapCreateBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        for (IDoodleItem iDoodleItem : this.items) {
            canvas.save();
            canvas.drawPaint(paint2);
            IDoodleItem.draw$default(iDoodleItem, canvas, null, null, null, 12, null);
            paint.setAlpha(iDoodleItem.getAlpha());
            canvas.restore();
            Canvas canvas2 = this.canvas;
            if (canvas2 != null) {
                canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
            }
        }
        this.isDirty = false;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public LayerBitmap(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        String string = parcel.readString();
        Intrinsics.checkNotNull(string, "null cannot be cast to non-null type kotlin.String");
        this(string, null, null, parcel.readInt(), new ArrayList());
        Bitmap bitmap = this.bitmap;
        this.canvas = bitmap != null ? new Canvas(bitmap) : null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.id);
        dest.writeInt(this.type);
    }

    /* JADX INFO: renamed from: com.miui.doodle.document.LayerBitmap$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: LayerBitmap.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J8\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u001a\u0010\u0013\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0014J\u0010\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u001d\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u001dR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/miui/doodle/document/LayerBitmap$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/miui/doodle/document/LayerBitmap;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "KEY_ID", "", "KEY_TYPE", "KEY_ITEM_IDS", "TYPE_NONE", "", "TYPE_NORMAL", "TYPE_TEXT_BITMAP", "fromJSONReader", "mJsonReader", "Lcom/google/gson/stream/JsonReader;", "allItems", "", "Lcom/miui/doodle/base/core/IDoodleItem;", "readBitmapFunc", "Lkotlin/Function2;", "", "Landroid/graphics/Bitmap;", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "(I)[Lcom/miui/doodle/document/LayerBitmap;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion implements Parcelable.Creator<LayerBitmap> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LayerBitmap fromJSONReader(JsonReader mJsonReader, List<? extends IDoodleItem> allItems, Function2<? super String, ? super Boolean, Bitmap> readBitmapFunc) throws IOException {
            Intrinsics.checkNotNullParameter(mJsonReader, "mJsonReader");
            Intrinsics.checkNotNullParameter(allItems, "allItems");
            Intrinsics.checkNotNullParameter(readBitmapFunc, "readBitmapFunc");
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            mJsonReader.beginObject();
            String strNextString = "";
            int iNextInt = 0;
            while (mJsonReader.hasNext()) {
                String strNextName = mJsonReader.nextName();
                if (strNextName != null) {
                    int iHashCode = strNextName.hashCode();
                    if (iHashCode != 3355) {
                        if (iHashCode != 3575610) {
                            if (iHashCode == 1177845644 && strNextName.equals(LayerBitmap.KEY_ITEM_IDS)) {
                                mJsonReader.beginArray();
                                while (mJsonReader.hasNext()) {
                                    String strNextString2 = mJsonReader.nextString();
                                    Intrinsics.checkNotNullExpressionValue(strNextString2, "nextString(...)");
                                    linkedHashSet.add(strNextString2);
                                }
                                mJsonReader.endArray();
                            }
                        } else if (strNextName.equals("type")) {
                            iNextInt = mJsonReader.nextInt();
                        }
                    } else if (strNextName.equals("id")) {
                        strNextString = mJsonReader.nextString();
                        Intrinsics.checkNotNullExpressionValue(strNextString, "nextString(...)");
                    }
                }
            }
            mJsonReader.endObject();
            ArrayList arrayList = new ArrayList();
            for (Object obj : allItems) {
                if (linkedHashSet.contains(((IDoodleItem) obj).getId())) {
                    arrayList.add(obj);
                }
            }
            List mutableList = CollectionsKt.toMutableList((Collection) arrayList);
            Bitmap bitmapInvoke = readBitmapFunc.invoke(strNextString, Boolean.valueOf(iNextInt == 11));
            return new LayerBitmap(strNextString, bitmapInvoke, bitmapInvoke != null ? new Canvas(bitmapInvoke) : null, iNextInt, mutableList);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LayerBitmap createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new LayerBitmap(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LayerBitmap[] newArray(int size) {
            return new LayerBitmap[size];
        }
    }

    public final void cacheImageToFile() {
        this.cacheImageKey = DoodleCacheManager.INSTANCE.saveImageToFile(this.bitmap, "");
    }

    public final Bitmap cacheImageBeforeChanged() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            Bitmap.Config config = bitmap.getConfig();
            this.cacheBitmapBeforeChanged = config != null ? bitmap.copy(config, true) : null;
        }
        return this.cacheBitmapBeforeChanged;
    }

    public final void cacheImageBeforeChangedToFile() {
        this.cacheImageKeyBeforeChanged = DoodleCacheManager.INSTANCE.saveImageToFile(this.cacheBitmapBeforeChanged, DoodleCacheManager.HIS_CACHE_HEAD);
    }

    public final Bitmap cacheImageAfterChanged() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            Bitmap.Config config = bitmap.getConfig();
            this.cacheBitmapAfterChanged = config != null ? bitmap.copy(config, true) : null;
        }
        return this.cacheBitmapAfterChanged;
    }

    public final void cacheImageAfterChangedToFile() {
        this.cacheImageKeyAfterChanged = DoodleCacheManager.INSTANCE.saveImageToFile(this.cacheBitmapAfterChanged, DoodleCacheManager.HIS_CACHE_HEAD);
    }

    public final void releaseCacheImageBeforeAndAfterChanged() {
        this.cacheBitmapBeforeChanged = null;
        this.cacheImageKeyBeforeChanged = "";
        this.cacheBitmapAfterChanged = null;
        this.cacheImageKeyAfterChanged = "";
    }
}
