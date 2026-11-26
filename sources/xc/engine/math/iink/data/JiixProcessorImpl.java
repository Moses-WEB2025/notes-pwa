package xc.engine.math.iink.data;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.todo.data.Todo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import xc.engine.math.iink.bean.BoundingBox;
import xc.engine.math.iink.bean.JiixMathNode;

/* JADX INFO: compiled from: JiixParserImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\bH\u0016J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0002J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u000fJ\u0018\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00120\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u0016H\u0002J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00162\b\u0010\u0019\u001a\u0004\u0018\u00010\u0016J\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH\u0016J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH\u0016J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH\u0016J \u0010 \u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH\u0016J\u0014\u0010!\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u000fH\u0002J\u0018\u0010\"\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00052\u0006\u0010#\u001a\u00020\u000fH\u0002¨\u0006$"}, d2 = {"Lxc/engine/math/iink/data/JiixProcessorImpl;", "Lxc/engine/math/iink/data/JiixProcessor;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "parseMathElements", "", "Lxc/engine/math/iink/data/ParsedMathElement;", "jiixData", "", "parseItemElements", "parseAndFormatResult", "Lxc/engine/math/iink/data/ParsedComputationResult;", "resultData", "parseSingleMathElement", "element", "Lorg/json/JSONObject;", "buildExpressionString", "parseBoundingBox", "Lxc/engine/math/iink/bean/BoundingBox;", "formatNumber", "value", "parseMathNode", "Lxc/engine/math/iink/bean/JiixMathNode;", "json", "collectBaselineNumberBoundingBoxes", "node", "findEqualNode", "getSmartAlignedBoundingBox", "blockId", "getFormulaBoundingBox", "containsEqualSign", "", "getEqualSignStrokeIds", "findEqualItemInOperands", "extractStrokeIdsFromEqualItem", "equalItem", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class JiixProcessorImpl implements JiixProcessor {
    @Override // xc.engine.math.iink.data.JiixProcessor
    public List<ParsedMathElement> parseMathElements(String jiixData) {
        Intrinsics.checkNotNullParameter(jiixData, "jiixData");
        try {
            JSONArray jSONArrayOptJSONArray = new JSONObject(jiixData).optJSONArray("elements");
            if (jSONArrayOptJSONArray != null) {
                int length = jSONArrayOptJSONArray.length();
                ArrayList arrayList = new ArrayList(length);
                for (int i = 0; i < length; i++) {
                    arrayList.add(jSONArrayOptJSONArray.optJSONObject(i));
                }
                ArrayList<JSONObject> arrayList2 = new ArrayList();
                for (Object obj : arrayList) {
                    if (Intrinsics.areEqual(((JSONObject) obj).optString("type"), "Math")) {
                        arrayList2.add(obj);
                    }
                }
                ArrayList arrayList3 = new ArrayList();
                for (JSONObject jSONObject : arrayList2) {
                    Intrinsics.checkNotNull(jSONObject);
                    ParsedMathElement singleMathElement = parseSingleMathElement(jSONObject);
                    if (singleMathElement != null) {
                        arrayList3.add(singleMathElement);
                    }
                }
                return arrayList3;
            }
            return CollectionsKt.emptyList();
        } catch (Exception e) {
            Log.e("JiixProcessor", "Failed to parse JIIX data", e);
            return CollectionsKt.emptyList();
        }
    }

    @Override // xc.engine.math.iink.data.JiixProcessor
    public List<ParsedMathElement> parseItemElements(String jiixData) {
        List<ParsedMathElement> listListOf;
        Intrinsics.checkNotNullParameter(jiixData, "jiixData");
        try {
            JSONObject jSONObject = new JSONObject(jiixData);
            if (jSONObject.has("elements")) {
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("elements");
                if (jSONArrayOptJSONArray != null) {
                    int length = jSONArrayOptJSONArray.length();
                    ArrayList arrayList = new ArrayList(length);
                    for (int i = 0; i < length; i++) {
                        arrayList.add(jSONArrayOptJSONArray.optJSONObject(i));
                    }
                    ArrayList<JSONObject> arrayList2 = new ArrayList();
                    for (Object obj : arrayList) {
                        if (Intrinsics.areEqual(((JSONObject) obj).optString("type"), "Math")) {
                            arrayList2.add(obj);
                        }
                    }
                    ArrayList arrayList3 = new ArrayList();
                    for (JSONObject jSONObject2 : arrayList2) {
                        Intrinsics.checkNotNull(jSONObject2);
                        ParsedMathElement singleMathElement = parseSingleMathElement(jSONObject2);
                        if (singleMathElement != null) {
                            arrayList3.add(singleMathElement);
                        }
                    }
                    return arrayList3;
                }
                return CollectionsKt.emptyList();
            }
            ParsedMathElement singleMathElement2 = parseSingleMathElement(jSONObject);
            if (singleMathElement2 != null && (listListOf = CollectionsKt.listOf(singleMathElement2)) != null) {
                return listListOf;
            }
            return CollectionsKt.emptyList();
        } catch (Exception e) {
            Log.e("JiixProcessor", "Failed to parse JIIX data", e);
            return null;
        }
    }

    @Override // xc.engine.math.iink.data.JiixProcessor
    public ParsedComputationResult parseAndFormatResult(String resultData) {
        Intrinsics.checkNotNullParameter(resultData, "resultData");
        try {
            JSONArray jSONArrayOptJSONArray = new JSONObject(resultData).optJSONArray("expressions");
            if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                JSONObject jSONObject = jSONArrayOptJSONArray.getJSONObject(0);
                String strOptString = jSONObject.optString("exact-value", jSONObject.optString("value", ""));
                Intrinsics.checkNotNull(strOptString);
                return new ParsedComputationResult(formatNumber(strOptString), true);
            }
            return new ParsedComputationResult("", false);
        } catch (Exception e) {
            Log.e("JiixProcessor", "Failed to parse computation result", e);
            return new ParsedComputationResult("", false);
        }
    }

    private final ParsedMathElement parseSingleMathElement(JSONObject element) {
        try {
            String strOptString = element.optString("id");
            Intrinsics.checkNotNull(strOptString);
            if (strOptString.length() <= 0) {
                strOptString = null;
            }
            if (strOptString != null) {
                return new ParsedMathElement(strOptString, parseBoundingBox(element));
            }
            return null;
        } catch (Exception e) {
            Log.e("JiixProcessor", "Failed to parse math element", e);
            return null;
        }
    }

    private final String buildExpressionString(JSONObject element) {
        try {
            JSONArray jSONArrayOptJSONArray = element.optJSONArray("expressions");
            if (jSONArrayOptJSONArray != null) {
                StringBuilder sb = new StringBuilder();
                int length = jSONArrayOptJSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                    if (jSONObjectOptJSONObject != null && SetsKt.setOf((Object[]) new String[]{"number", "operator", "symbol"}).contains(jSONObjectOptJSONObject.optString("type"))) {
                        sb.append(jSONObjectOptJSONObject.optString("value"));
                    }
                }
                String string = sb.toString();
                if (string != null) {
                    return string;
                }
            }
            return "";
        } catch (Exception e) {
            Log.e("JiixProcessor", "Failed to build expression", e);
            return "";
        }
    }

    private final BoundingBox parseBoundingBox(JSONObject element) {
        try {
            if (!element.has("bounding-box")) {
                return null;
            }
            JSONObject jSONObject = element.getJSONObject("bounding-box");
            return new BoundingBox((float) jSONObject.optDouble(AnimatedProperty.PROPERTY_NAME_X, 0.0d), (float) jSONObject.optDouble(AnimatedProperty.PROPERTY_NAME_Y, 0.0d), (float) jSONObject.optDouble(Layer.KEY_WIDTH, 0.0d), (float) jSONObject.optDouble(Layer.KEY_HEIGHT, 0.0d));
        } catch (Exception e) {
            Log.e("JiixProcessor", "Failed to parse bounding box", e);
            return null;
        }
    }

    private final String formatNumber(String value) {
        String plainString;
        Double doubleOrNull = StringsKt.toDoubleOrNull(value);
        if (doubleOrNull != null) {
            double dDoubleValue = doubleOrNull.doubleValue();
            if (Math.abs(dDoubleValue) >= 1.0E7d) {
                String str = new DecimalFormat("0.#####E0").format(dDoubleValue);
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                plainString = new Regex("e(\\d+)$").replace(StringsKt.replace$default(str, ExifInterface.LONGITUDE_EAST, "e", false, 4, (Object) null), "e+$1");
            } else {
                plainString = new BigDecimal(dDoubleValue).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            }
            if (plainString != null) {
                return plainString;
            }
        }
        return value;
    }

    public final JiixMathNode parseMathNode(JSONObject json) {
        ArrayList arrayList = null;
        if (json == null) {
            return null;
        }
        String strOptString = json.optString("type", null);
        String strOptString2 = json.optString(Todo.LABEL, null);
        BoundingBox boundingBox = parseBoundingBox(json);
        JSONArray jSONArrayOptJSONArray = json.optJSONArray("operands");
        if (jSONArrayOptJSONArray != null) {
            IntRange intRangeUntil = RangesKt.until(0, jSONArrayOptJSONArray.length());
            ArrayList arrayList2 = new ArrayList();
            Iterator<Integer> it = intRangeUntil.iterator();
            while (it.hasNext()) {
                JiixMathNode mathNode = parseMathNode(jSONArrayOptJSONArray.optJSONObject(((IntIterator) it).nextInt()));
                if (mathNode != null) {
                    arrayList2.add(mathNode);
                }
            }
            arrayList = arrayList2;
        }
        return new JiixMathNode(strOptString, strOptString2, arrayList, boundingBox);
    }

    private final List<BoundingBox> collectBaselineNumberBoundingBoxes(JiixMathNode node) {
        List<JiixMathNode> operands;
        ArrayList arrayList = new ArrayList();
        if (node != null) {
            if ((Intrinsics.areEqual(node.getType(), "number") || Intrinsics.areEqual(node.getType(), "variable")) && node.getBoundingBox() != null) {
                arrayList.add(node.getBoundingBox());
            }
            if (Intrinsics.areEqual(node.getType(), "power") && (operands = node.getOperands()) != null && (!operands.isEmpty())) {
                arrayList.addAll(collectBaselineNumberBoundingBoxes(node.getOperands().get(0)));
                return arrayList;
            }
            List<JiixMathNode> operands2 = node.getOperands();
            if (operands2 != null) {
                Iterator<T> it = operands2.iterator();
                while (it.hasNext()) {
                    arrayList.addAll(collectBaselineNumberBoundingBoxes((JiixMathNode) it.next()));
                }
            }
        }
        return arrayList;
    }

    public final JiixMathNode findEqualNode(JiixMathNode node) {
        if (node == null) {
            return null;
        }
        if (Intrinsics.areEqual(node.getType(), "=") && node.getBoundingBox() != null) {
            return node;
        }
        List<JiixMathNode> operands = node.getOperands();
        if (operands != null) {
            Iterator<T> it = operands.iterator();
            while (it.hasNext()) {
                JiixMathNode jiixMathNodeFindEqualNode = findEqualNode((JiixMathNode) it.next());
                if (jiixMathNodeFindEqualNode != null) {
                    return jiixMathNodeFindEqualNode;
                }
            }
        }
        return null;
    }

    @Override // xc.engine.math.iink.data.JiixProcessor
    public BoundingBox getSmartAlignedBoundingBox(String jiixData, String blockId) {
        JSONObject jSONObjectOptJSONObject;
        float fFloatValue;
        BoundingBox boundingBox;
        JSONArray jSONArrayOptJSONArray;
        Intrinsics.checkNotNullParameter(jiixData, "jiixData");
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        try {
            Log.d("SmartBBox", "blockId=" + blockId);
            JSONArray jSONArrayOptJSONArray2 = new JSONObject(jiixData).optJSONArray("elements");
            if (jSONArrayOptJSONArray2 == null) {
                jSONObjectOptJSONObject = null;
                break;
            }
            int length = jSONArrayOptJSONArray2.length();
            int i = 0;
            while (true) {
                if (i < length) {
                    JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray2.optJSONObject(i);
                    if (jSONObjectOptJSONObject2 != null && Intrinsics.areEqual(jSONObjectOptJSONObject2.optString("id"), blockId) && (jSONArrayOptJSONArray = jSONObjectOptJSONObject2.optJSONArray("expressions")) != null && jSONArrayOptJSONArray.length() > 0) {
                        jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(0);
                        break;
                    }
                    i++;
                } else {
                    jSONObjectOptJSONObject = null;
                    break;
                }
            }
            Log.d("SmartBBox", "exprJson=" + jSONObjectOptJSONObject);
            JiixMathNode mathNode = parseMathNode(jSONObjectOptJSONObject);
            Log.d("SmartBBox", "jiixMathNode=" + mathNode);
            JiixMathNode jiixMathNodeFindEqualNode = findEqualNode(mathNode);
            Log.d("SmartBBox", "eqNode=" + jiixMathNodeFindEqualNode);
            if ((jiixMathNodeFindEqualNode != null ? jiixMathNodeFindEqualNode.getBoundingBox() : null) == null) {
                Log.d("SmartBBox", "fallback to main bbox: " + (mathNode != null ? mathNode.getBoundingBox() : null));
                if (mathNode != null) {
                    return mathNode.getBoundingBox();
                }
                return null;
            }
            List<JiixMathNode> operands = jiixMathNodeFindEqualNode.getOperands();
            JiixMathNode jiixMathNode = operands != null ? (JiixMathNode) CollectionsKt.getOrNull(operands, 0) : null;
            Log.d("SmartBBox", "lhs=" + jiixMathNode);
            float height = (jiixMathNode == null || (boundingBox = jiixMathNode.getBoundingBox()) == null) ? 0.0f : boundingBox.getHeight();
            List<BoundingBox> listCollectBaselineNumberBoundingBoxes = collectBaselineNumberBoundingBoxes(jiixMathNode);
            Log.d("SmartBBox", "numberBboxes=" + listCollectBaselineNumberBoundingBoxes);
            if (listCollectBaselineNumberBoundingBoxes.isEmpty()) {
                fFloatValue = height / 2;
            } else {
                List<BoundingBox> list = listCollectBaselineNumberBoundingBoxes;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Float.valueOf(((BoundingBox) it.next()).getHeight()));
                }
                fFloatValue = (float) CollectionsKt.averageOfFloat(arrayList);
            }
            Log.d("SmartBBox", "avgHeight=" + fFloatValue + ", mainHeight=" + height);
            if (fFloatValue <= 1.0f) {
                Float fValueOf = Float.valueOf(jiixMathNodeFindEqualNode.getBoundingBox().getHeight());
                if (fValueOf.floatValue() <= 1.0f) {
                    fValueOf = null;
                }
                if (fValueOf == null) {
                    fValueOf = Float.valueOf(height);
                    if (fValueOf.floatValue() <= 1.0f) {
                        fValueOf = null;
                    }
                    fFloatValue = fValueOf != null ? fValueOf.floatValue() : 30.0f;
                }
            }
            float f = 0.6f * fFloatValue;
            if (f <= 1.0f) {
                f = 18.0f;
            }
            float f2 = 2;
            float y = (jiixMathNodeFindEqualNode.getBoundingBox().getY() + (jiixMathNodeFindEqualNode.getBoundingBox().getHeight() / f2)) - (fFloatValue / f2);
            float fMin = Math.min(jiixMathNodeFindEqualNode.getBoundingBox().getWidth(), jiixMathNodeFindEqualNode.getBoundingBox().getHeight()) * 0.5f;
            Log.d("SmartBBox", "final bbox: x=" + (jiixMathNodeFindEqualNode.getBoundingBox().getX() + jiixMathNodeFindEqualNode.getBoundingBox().getWidth() + fMin) + ", y=" + y + ", width=" + f + ", height=" + fFloatValue + " (center aligned) estimateSpace=" + fMin);
            return new BoundingBox(jiixMathNodeFindEqualNode.getBoundingBox().getX() + jiixMathNodeFindEqualNode.getBoundingBox().getWidth() + fMin, y, f, fFloatValue);
        } catch (Exception e) {
            Log.e("JiixProcessor", "getSmartAlignedBoundingBox error: " + e.getMessage());
            return null;
        }
    }

    @Override // xc.engine.math.iink.data.JiixProcessor
    public BoundingBox getFormulaBoundingBox(String jiixData, String blockId) {
        Intrinsics.checkNotNullParameter(jiixData, "jiixData");
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        try {
            JSONArray jSONArrayOptJSONArray = new JSONObject(jiixData).optJSONArray("elements");
            if (jSONArrayOptJSONArray != null) {
                int length = jSONArrayOptJSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                    if (jSONObjectOptJSONObject != null && Intrinsics.areEqual(jSONObjectOptJSONObject.optString("id"), blockId)) {
                        return parseBoundingBox(jSONObjectOptJSONObject);
                    }
                }
            }
            Log.d("FormulaBBox", "No formula bbox found for blockId=" + blockId);
            return null;
        } catch (Exception e) {
            Log.e("JiixProcessor", "getFormulaBoundingBox error: " + e.getMessage());
            return null;
        }
    }

    @Override // xc.engine.math.iink.data.JiixProcessor
    public boolean containsEqualSign(String jiixData, String blockId) {
        JSONObject jSONObjectOptJSONObject;
        JSONArray jSONArrayOptJSONArray;
        Intrinsics.checkNotNullParameter(jiixData, "jiixData");
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        try {
            JSONArray jSONArrayOptJSONArray2 = new JSONObject(jiixData).optJSONArray("elements");
            if (jSONArrayOptJSONArray2 == null) {
                jSONObjectOptJSONObject = null;
                break;
            }
            int length = jSONArrayOptJSONArray2.length();
            int i = 0;
            while (true) {
                if (i < length) {
                    JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray2.optJSONObject(i);
                    if (jSONObjectOptJSONObject2 != null && Intrinsics.areEqual(jSONObjectOptJSONObject2.optString("id"), blockId) && (jSONArrayOptJSONArray = jSONObjectOptJSONObject2.optJSONArray("expressions")) != null && jSONArrayOptJSONArray.length() > 0) {
                        jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(0);
                        break;
                    }
                    i++;
                } else {
                    jSONObjectOptJSONObject = null;
                    break;
                }
            }
            return findEqualNode(parseMathNode(jSONObjectOptJSONObject)) != null;
        } catch (Exception e) {
            Log.e("JiixProcessor", "containsEqualSign error: " + e.getMessage());
            return false;
        }
    }

    @Override // xc.engine.math.iink.data.JiixProcessor
    public List<String> getEqualSignStrokeIds(String jiixData, String blockId) {
        JSONObject jSONObjectFindEqualItemInOperands;
        JSONArray jSONArrayOptJSONArray;
        Intrinsics.checkNotNullParameter(jiixData, "jiixData");
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        try {
            if (!StringsKt.isBlank(jiixData) && !StringsKt.isBlank(blockId)) {
                JSONArray jSONArrayOptJSONArray2 = new JSONObject(jiixData).optJSONArray("elements");
                if (jSONArrayOptJSONArray2 == null) {
                    JiixProcessorImpl jiixProcessorImpl = this;
                    Log.w("JiixProcessor", "getEqualSignStrokeIds: no elements found");
                    return null;
                }
                int length = jSONArrayOptJSONArray2.length();
                int i = 0;
                while (true) {
                    if (i < length) {
                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray2.optJSONObject(i);
                        if (jSONObjectOptJSONObject != null && Intrinsics.areEqual(jSONObjectOptJSONObject.optString("id"), blockId) && (jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("expressions")) != null && jSONArrayOptJSONArray.length() > 0) {
                            jSONObjectFindEqualItemInOperands = jSONArrayOptJSONArray.optJSONObject(0);
                            break;
                        }
                        i++;
                    } else {
                        jSONObjectFindEqualItemInOperands = null;
                        break;
                    }
                }
                if (jSONObjectFindEqualItemInOperands == null) {
                    Log.w("JiixProcessor", "getEqualSignStrokeIds: no expression found for blockId=" + blockId);
                    return null;
                }
                if (!Intrinsics.areEqual(jSONObjectFindEqualItemInOperands.optString("type"), "=")) {
                    jSONObjectFindEqualItemInOperands = findEqualItemInOperands(jSONObjectFindEqualItemInOperands);
                }
                if (jSONObjectFindEqualItemInOperands == null) {
                    Log.d("JiixProcessor", "getEqualSignStrokeIds: no equal sign found for blockId=" + blockId);
                    return null;
                }
                return extractStrokeIdsFromEqualItem(jSONObjectFindEqualItemInOperands);
            }
            Log.w("JiixProcessor", "getEqualSignStrokeIds: empty jiixData or blockId");
            return null;
        } catch (Exception e) {
            Log.e("JiixProcessor", "getEqualSignStrokeIds error: " + e.getMessage(), e);
            return null;
        }
    }

    private final JSONObject findEqualItemInOperands(JSONObject json) {
        JSONArray jSONArrayOptJSONArray;
        if (json == null || (jSONArrayOptJSONArray = json.optJSONArray("operands")) == null) {
            return null;
        }
        int length = jSONArrayOptJSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
            if (jSONObjectOptJSONObject != null) {
                if (Intrinsics.areEqual(jSONObjectOptJSONObject.optString("type"), "=")) {
                    return jSONObjectOptJSONObject;
                }
                JSONObject jSONObjectFindEqualItemInOperands = findEqualItemInOperands(jSONObjectOptJSONObject);
                if (jSONObjectFindEqualItemInOperands != null) {
                    return jSONObjectFindEqualItemInOperands;
                }
            }
        }
        return null;
    }

    private final List<String> extractStrokeIdsFromEqualItem(JSONObject equalItem) {
        JSONArray jSONArrayOptJSONArray = equalItem.optJSONArray(Layer.KEY_ITEMS);
        if (jSONArrayOptJSONArray == null) {
            Log.w("JiixProcessor", "extractStrokeIdsFromEqualItem: no items found in equal sign");
            return null;
        }
        if (jSONArrayOptJSONArray.length() == 0) {
            Log.w("JiixProcessor", "extractStrokeIdsFromEqualItem: items array is empty");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int length = jSONArrayOptJSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
            if (jSONObjectOptJSONObject != null && Intrinsics.areEqual(jSONObjectOptJSONObject.optString("type"), "stroke")) {
                String strOptString = jSONObjectOptJSONObject.optString("id", "");
                Intrinsics.checkNotNull(strOptString);
                if (strOptString.length() > 0) {
                    arrayList.add(strOptString);
                }
            }
        }
        if (arrayList.size() >= 2) {
            return arrayList.size() == 2 ? arrayList : CollectionsKt.take(arrayList, 2);
        }
        Log.w("JiixProcessor", "extractStrokeIdsFromEqualItem: found " + arrayList.size() + " strokes, need at least 2");
        return null;
    }
}
