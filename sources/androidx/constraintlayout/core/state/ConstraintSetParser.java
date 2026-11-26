package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.parser.CLArray;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLKey;
import androidx.constraintlayout.core.parser.CLNumber;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParser;
import androidx.constraintlayout.core.parser.CLParsingException;
import androidx.constraintlayout.core.parser.CLString;
import androidx.constraintlayout.core.state.helpers.BarrierReference;
import androidx.constraintlayout.core.state.helpers.ChainReference;
import androidx.constraintlayout.core.state.helpers.FlowReference;
import androidx.constraintlayout.core.state.helpers.GridReference;
import androidx.constraintlayout.core.state.helpers.GuidelineReference;
import com.google.common.base.Ascii;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import io.reactivex.annotations.SchedulerSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import miuix.appcompat.view.menu.HyperMenuUtils;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintSetParser {
    private static final boolean PARSER_DEBUG = false;

    interface GeneratedValue {
        float value();
    }

    public enum MotionLayoutDebugFlags {
        NONE,
        SHOW_ALL,
        UNKNOWN
    }

    public static class DesignElement {
        String mId;
        HashMap<String, String> mParams;
        String mType;

        public String getId() {
            return this.mId;
        }

        public String getType() {
            return this.mType;
        }

        public HashMap<String, String> getParams() {
            return this.mParams;
        }

        DesignElement(String str, String str2, HashMap<String, String> map) {
            this.mId = str;
            this.mType = str2;
            this.mParams = map;
        }
    }

    public static class LayoutVariables {
        HashMap<String, Integer> mMargins = new HashMap<>();
        HashMap<String, GeneratedValue> mGenerators = new HashMap<>();
        HashMap<String, ArrayList<String>> mArrayIds = new HashMap<>();

        void put(String str, int i) {
            this.mMargins.put(str, Integer.valueOf(i));
        }

        void put(String str, float f, float f2) {
            if (this.mGenerators.containsKey(str) && (this.mGenerators.get(str) instanceof OverrideValue)) {
                return;
            }
            this.mGenerators.put(str, new Generator(f, f2));
        }

        void put(String str, float f, float f2, float f3, String str2, String str3) {
            if (this.mGenerators.containsKey(str) && (this.mGenerators.get(str) instanceof OverrideValue)) {
                return;
            }
            FiniteGenerator finiteGenerator = new FiniteGenerator(f, f2, f3, str2, str3);
            this.mGenerators.put(str, finiteGenerator);
            this.mArrayIds.put(str, finiteGenerator.array());
        }

        public void putOverride(String str, float f) {
            this.mGenerators.put(str, new OverrideValue(f));
        }

        float get(Object obj) {
            if (obj instanceof CLString) {
                String strContent = ((CLString) obj).content();
                if (this.mGenerators.containsKey(strContent)) {
                    return this.mGenerators.get(strContent).value();
                }
                if (this.mMargins.containsKey(strContent)) {
                    return this.mMargins.get(strContent).floatValue();
                }
                return 0.0f;
            }
            if (obj instanceof CLNumber) {
                return ((CLNumber) obj).getFloat();
            }
            return 0.0f;
        }

        ArrayList<String> getList(String str) {
            if (this.mArrayIds.containsKey(str)) {
                return this.mArrayIds.get(str);
            }
            return null;
        }

        void put(String str, ArrayList<String> arrayList) {
            this.mArrayIds.put(str, arrayList);
        }
    }

    static class Generator implements GeneratedValue {
        float mCurrent;
        float mIncrementBy;
        float mStart;
        boolean mStop = false;

        Generator(float f, float f2) {
            this.mStart = f;
            this.mIncrementBy = f2;
            this.mCurrent = f;
        }

        @Override // androidx.constraintlayout.core.state.ConstraintSetParser.GeneratedValue
        public float value() {
            if (!this.mStop) {
                this.mCurrent += this.mIncrementBy;
            }
            return this.mCurrent;
        }
    }

    static class FiniteGenerator implements GeneratedValue {
        float mFrom;
        float mInitial;
        float mMax;
        String mPostfix;
        String mPrefix;
        float mStep;
        float mTo;
        boolean mStop = false;
        float mCurrent = 0.0f;

        FiniteGenerator(float f, float f2, float f3, String str, String str2) {
            this.mFrom = f;
            this.mTo = f2;
            this.mStep = f3;
            this.mPrefix = str == null ? "" : str;
            this.mPostfix = str2 == null ? "" : str2;
            this.mMax = f2;
            this.mInitial = f;
        }

        @Override // androidx.constraintlayout.core.state.ConstraintSetParser.GeneratedValue
        public float value() {
            float f = this.mCurrent;
            if (f >= this.mMax) {
                this.mStop = true;
            }
            if (!this.mStop) {
                this.mCurrent = f + this.mStep;
            }
            return this.mCurrent;
        }

        public ArrayList<String> array() {
            ArrayList<String> arrayList = new ArrayList<>();
            int i = (int) this.mInitial;
            int i2 = (int) this.mMax;
            int i3 = i;
            while (i <= i2) {
                arrayList.add(this.mPrefix + i3 + this.mPostfix);
                i3 += (int) this.mStep;
                i++;
            }
            return arrayList;
        }
    }

    static class OverrideValue implements GeneratedValue {
        float mValue;

        OverrideValue(float f) {
            this.mValue = f;
        }

        @Override // androidx.constraintlayout.core.state.ConstraintSetParser.GeneratedValue
        public float value() {
            return this.mValue;
        }
    }

    public static void parseJSON(String str, Transition transition, int i) {
        CLObject objectOrNull;
        try {
            CLObject cLObject = CLParser.parse(str);
            ArrayList<String> arrayListNames = cLObject.names();
            if (arrayListNames == null) {
                return;
            }
            for (String str2 : arrayListNames) {
                CLElement cLElement = cLObject.get(str2);
                if ((cLElement instanceof CLObject) && (objectOrNull = ((CLObject) cLElement).getObjectOrNull(SchedulerSupport.CUSTOM)) != null) {
                    for (String str3 : objectOrNull.names()) {
                        CLElement cLElement2 = objectOrNull.get(str3);
                        if (cLElement2 instanceof CLNumber) {
                            transition.addCustomFloat(i, str2, str3, cLElement2.getFloat());
                        } else if (cLElement2 instanceof CLString) {
                            long colorString = parseColorString(cLElement2.content());
                            if (colorString != -1) {
                                transition.addCustomColor(i, str2, str3, (int) colorString);
                            }
                        }
                    }
                }
            }
        } catch (CLParsingException e) {
            System.err.println("Error parsing JSON " + e);
        }
    }

    public static void parseMotionSceneJSON(CoreMotionScene coreMotionScene, String str) {
        try {
            CLObject cLObject = CLParser.parse(str);
            ArrayList<String> arrayListNames = cLObject.names();
            if (arrayListNames == null) {
                return;
            }
            for (String str2 : arrayListNames) {
                CLElement cLElement = cLObject.get(str2);
                if (cLElement instanceof CLObject) {
                    CLObject cLObject2 = (CLObject) cLElement;
                    int iHashCode = str2.hashCode();
                    if (iHashCode != -2137403731) {
                        if (iHashCode != -241441378) {
                            if (iHashCode == 1101852654 && str2.equals("ConstraintSets")) {
                                parseConstraintSets(coreMotionScene, cLObject2);
                            }
                        } else if (str2.equals(TypedValues.TransitionType.NAME)) {
                            parseTransitions(coreMotionScene, cLObject2);
                        }
                    } else if (str2.equals("Header")) {
                        parseHeader(coreMotionScene, cLObject2);
                    }
                }
            }
        } catch (CLParsingException e) {
            System.err.println("Error parsing JSON " + e);
        }
    }

    static void parseConstraintSets(CoreMotionScene coreMotionScene, CLObject cLObject) throws CLParsingException {
        ArrayList<String> arrayListNames = cLObject.names();
        if (arrayListNames == null) {
            return;
        }
        for (String str : arrayListNames) {
            CLObject object = cLObject.getObject(str);
            String stringOrNull = object.getStringOrNull("Extends");
            if (stringOrNull != null && !stringOrNull.isEmpty()) {
                String constraintSet = coreMotionScene.getConstraintSet(stringOrNull);
                if (constraintSet != null) {
                    CLObject cLObject2 = CLParser.parse(constraintSet);
                    ArrayList<String> arrayListNames2 = object.names();
                    if (arrayListNames2 != null) {
                        for (String str2 : arrayListNames2) {
                            CLElement cLElement = object.get(str2);
                            if (cLElement instanceof CLObject) {
                                override(cLObject2, str2, (CLObject) cLElement);
                            }
                        }
                        coreMotionScene.setConstraintSetContent(str, cLObject2.toJSON());
                    }
                }
            } else {
                coreMotionScene.setConstraintSetContent(str, object.toJSON());
            }
        }
    }

    static void override(CLObject cLObject, String str, CLObject cLObject2) throws CLParsingException {
        if (!cLObject.has(str)) {
            cLObject.put(str, cLObject2);
            return;
        }
        CLObject object = cLObject.getObject(str);
        for (String str2 : cLObject2.names()) {
            if (!str2.equals("clear")) {
                object.put(str2, cLObject2.get(str2));
            } else {
                CLArray array = cLObject2.getArray("clear");
                for (int i = 0; i < array.size(); i++) {
                    String stringOrNull = array.getStringOrNull(i);
                    if (stringOrNull != null) {
                        stringOrNull.hashCode();
                        switch (stringOrNull) {
                            case "transforms":
                                object.remove("visibility");
                                object.remove("alpha");
                                object.remove("pivotX");
                                object.remove("pivotY");
                                object.remove("rotationX");
                                object.remove("rotationY");
                                object.remove("rotationZ");
                                object.remove("scaleX");
                                object.remove("scaleY");
                                object.remove("translationX");
                                object.remove("translationY");
                                break;
                            case "constraints":
                                object.remove("start");
                                object.remove("end");
                                object.remove(Layer.KEY_TOP);
                                object.remove("bottom");
                                object.remove("baseline");
                                object.remove(EditStat.ELEMENT_CENTER);
                                object.remove("centerHorizontally");
                                object.remove("centerVertically");
                                break;
                            case "dimensions":
                                object.remove(Layer.KEY_WIDTH);
                                object.remove(Layer.KEY_HEIGHT);
                                break;
                            default:
                                object.remove(stringOrNull);
                                break;
                        }
                    }
                }
            }
        }
    }

    static void parseTransitions(CoreMotionScene coreMotionScene, CLObject cLObject) throws CLParsingException {
        ArrayList<String> arrayListNames = cLObject.names();
        if (arrayListNames == null) {
            return;
        }
        for (String str : arrayListNames) {
            coreMotionScene.setTransitionContent(str, cLObject.getObject(str).toJSON());
        }
    }

    static void parseHeader(CoreMotionScene coreMotionScene, CLObject cLObject) {
        String stringOrNull = cLObject.getStringOrNull("export");
        if (stringOrNull != null) {
            coreMotionScene.setDebugName(stringOrNull);
        }
    }

    public static void parseJSON(String str, State state, LayoutVariables layoutVariables) throws CLParsingException {
        try {
            populateState(CLParser.parse(str), state, layoutVariables);
        } catch (CLParsingException e) {
            System.err.println("Error parsing JSON " + e);
        }
    }

    public static void populateState(CLObject cLObject, State state, LayoutVariables layoutVariables) throws CLParsingException {
        ArrayList<String> arrayListNames = cLObject.names();
        if (arrayListNames == null) {
            return;
        }
        for (String str : arrayListNames) {
            CLElement cLElement = cLObject.get(str);
            str.hashCode();
            switch (str) {
                case "Helpers":
                    if (!(cLElement instanceof CLArray)) {
                        break;
                    } else {
                        parseHelpers(state, layoutVariables, (CLArray) cLElement);
                        break;
                    }
                    break;
                case "Generate":
                    if (!(cLElement instanceof CLObject)) {
                        break;
                    } else {
                        parseGenerate(state, layoutVariables, (CLObject) cLElement);
                        break;
                    }
                    break;
                case "Variables":
                    if (!(cLElement instanceof CLObject)) {
                        break;
                    } else {
                        parseVariables(state, layoutVariables, (CLObject) cLElement);
                        break;
                    }
                    break;
                default:
                    if (cLElement instanceof CLObject) {
                        CLObject cLObject2 = (CLObject) cLElement;
                        String strLookForType = lookForType(cLObject2);
                        if (strLookForType != null) {
                            strLookForType.hashCode();
                            switch (strLookForType) {
                                case "vGuideline":
                                    parseGuidelineParams(1, state, str, cLObject2);
                                    break;
                                case "column":
                                case "row":
                                case "grid":
                                    parseGridType(strLookForType, state, str, layoutVariables, cLObject2);
                                    break;
                                case "hChain":
                                case "vChain":
                                    parseChainType(strLookForType, state, str, layoutVariables, cLObject2);
                                    break;
                                case "barrier":
                                    parseBarrier(state, str, cLObject2);
                                    break;
                                case "hFlow":
                                case "vFlow":
                                    parseFlowType(strLookForType, state, str, layoutVariables, cLObject2);
                                    break;
                                case "hGuideline":
                                    parseGuidelineParams(0, state, str, cLObject2);
                                    break;
                            }
                        } else {
                            parseWidget(state, layoutVariables, str, cLObject2);
                            break;
                        }
                    } else {
                        if (cLElement instanceof CLNumber) {
                            layoutVariables.put(str, cLElement.getInt());
                        }
                        break;
                    }
                    break;
            }
        }
    }

    private static void parseVariables(State state, LayoutVariables layoutVariables, CLObject cLObject) throws CLParsingException {
        ArrayList<String> arrayListNames = cLObject.names();
        if (arrayListNames == null) {
            return;
        }
        for (String str : arrayListNames) {
            CLElement cLElement = cLObject.get(str);
            if (cLElement instanceof CLNumber) {
                layoutVariables.put(str, cLElement.getInt());
            } else if (cLElement instanceof CLObject) {
                CLObject cLObject2 = (CLObject) cLElement;
                if (cLObject2.has("from") && cLObject2.has("to")) {
                    layoutVariables.put(str, layoutVariables.get(cLObject2.get("from")), layoutVariables.get(cLObject2.get("to")), 1.0f, cLObject2.getStringOrNull("prefix"), cLObject2.getStringOrNull("postfix"));
                } else if (cLObject2.has("from") && cLObject2.has("step")) {
                    layoutVariables.put(str, layoutVariables.get(cLObject2.get("from")), layoutVariables.get(cLObject2.get("step")));
                } else if (cLObject2.has("ids")) {
                    CLArray array = cLObject2.getArray("ids");
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        arrayList.add(array.getString(i));
                    }
                    layoutVariables.put(str, arrayList);
                } else if (cLObject2.has("tag")) {
                    layoutVariables.put(str, state.getIdsForTag(cLObject2.getString("tag")));
                }
            }
        }
    }

    public static void parseDesignElementsJSON(String str, ArrayList<DesignElement> arrayList) throws CLParsingException {
        CLObject cLObject = CLParser.parse(str);
        ArrayList<String> arrayListNames = cLObject.names();
        if (arrayListNames != null && arrayListNames.size() > 0) {
            String str2 = arrayListNames.get(0);
            CLElement cLElement = cLObject.get(str2);
            str2.hashCode();
            if (str2.equals("Design") && (cLElement instanceof CLObject)) {
                CLObject cLObject2 = (CLObject) cLElement;
                ArrayList<String> arrayListNames2 = cLObject2.names();
                for (int i = 0; i < arrayListNames2.size(); i++) {
                    String str3 = arrayListNames2.get(i);
                    CLObject cLObject3 = (CLObject) cLObject2.get(str3);
                    System.out.printf("element found " + str3 + "", new Object[0]);
                    String stringOrNull = cLObject3.getStringOrNull("type");
                    if (stringOrNull != null) {
                        HashMap map = new HashMap();
                        int size = cLObject3.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            CLKey cLKey = (CLKey) cLObject3.get(i);
                            String strContent = cLKey.content();
                            String strContent2 = cLKey.getValue().content();
                            if (strContent2 != null) {
                                map.put(strContent, strContent2);
                            }
                        }
                        arrayList.add(new DesignElement(str2, stringOrNull, map));
                    }
                }
            }
        }
    }

    static void parseHelpers(State state, LayoutVariables layoutVariables, CLArray cLArray) throws CLParsingException {
        for (int i = 0; i < cLArray.size(); i++) {
            CLElement cLElement = cLArray.get(i);
            if (cLElement instanceof CLArray) {
                CLArray cLArray2 = (CLArray) cLElement;
                if (cLArray2.size() > 1) {
                    String string = cLArray2.getString(0);
                    string.hashCode();
                    switch (string) {
                        case "vGuideline":
                            parseGuideline(1, state, cLArray2);
                            break;
                        case "hChain":
                            parseChain(0, state, layoutVariables, cLArray2);
                            break;
                        case "vChain":
                            parseChain(1, state, layoutVariables, cLArray2);
                            break;
                        case "hGuideline":
                            parseGuideline(0, state, cLArray2);
                            break;
                    }
                }
            }
        }
    }

    static void parseGenerate(State state, LayoutVariables layoutVariables, CLObject cLObject) throws CLParsingException {
        ArrayList<String> arrayListNames = cLObject.names();
        if (arrayListNames == null) {
            return;
        }
        for (String str : arrayListNames) {
            CLElement cLElement = cLObject.get(str);
            ArrayList<String> list = layoutVariables.getList(str);
            if (list != null && (cLElement instanceof CLObject)) {
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    parseWidget(state, layoutVariables, it.next(), (CLObject) cLElement);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0087  */
    static void parseChain(int i, State state, LayoutVariables layoutVariables, CLArray cLArray) throws CLParsingException {
        String strContent;
        ChainReference chainReferenceHorizontalChain = i == 0 ? state.horizontalChain() : state.verticalChain();
        CLElement cLElement = cLArray.get(1);
        if (cLElement instanceof CLArray) {
            CLArray cLArray2 = (CLArray) cLElement;
            if (cLArray2.size() < 1) {
                return;
            }
            for (int i2 = 0; i2 < cLArray2.size(); i2++) {
                chainReferenceHorizontalChain.add(cLArray2.getString(i2));
            }
            if (cLArray.size() > 2) {
                CLElement cLElement2 = cLArray.get(2);
                if (cLElement2 instanceof CLObject) {
                    CLObject cLObject = (CLObject) cLElement2;
                    for (String str : cLObject.names()) {
                        str.hashCode();
                        if (str.equals("style")) {
                            CLElement cLElement3 = cLObject.get(str);
                            if (cLElement3 instanceof CLArray) {
                                CLArray cLArray3 = (CLArray) cLElement3;
                                if (cLArray3.size() > 1) {
                                    strContent = cLArray3.getString(0);
                                    chainReferenceHorizontalChain.bias(cLArray3.getFloat(1));
                                } else {
                                    strContent = cLElement3.content();
                                }
                            } else {
                                strContent = cLElement3.content();
                            }
                            strContent.hashCode();
                            if (strContent.equals("packed")) {
                                chainReferenceHorizontalChain.style(State.Chain.PACKED);
                            } else if (strContent.equals("spread_inside")) {
                                chainReferenceHorizontalChain.style(State.Chain.SPREAD_INSIDE);
                            } else {
                                chainReferenceHorizontalChain.style(State.Chain.SPREAD);
                            }
                        } else {
                            parseConstraint(state, layoutVariables, cLObject, chainReferenceHorizontalChain, str);
                        }
                    }
                }
            }
        }
    }

    private static float toPix(State state, float f) {
        return state.getDpToPixel().toPixels(f);
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00c5  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static void parseChainType(String str, State state, String str2, LayoutVariables layoutVariables, CLObject cLObject) throws CLParsingException {
        CLArray cLArray;
        int i;
        float f;
        float pix;
        float pix2;
        float f2;
        float f3;
        String strContent;
        int i2 = 0;
        ChainReference chainReferenceHorizontalChain = str.charAt(0) == 'h' ? state.horizontalChain() : state.verticalChain();
        chainReferenceHorizontalChain.setKey(str2);
        for (String str3 : cLObject.names()) {
            str3.hashCode();
            int i3 = 6;
            int i4 = 3;
            int i5 = 2;
            int i6 = 1;
            int i7 = -1;
            switch (str3.hashCode()) {
                case -1383228885:
                    if (str3.equals("bottom")) {
                        i7 = i2;
                    }
                    break;
                case -567445985:
                    if (str3.equals("contains")) {
                        i7 = 1;
                    }
                    break;
                case 100571:
                    if (str3.equals("end")) {
                        i7 = 2;
                    }
                    break;
                case 115029:
                    if (str3.equals(Layer.KEY_TOP)) {
                        i7 = 3;
                    }
                    break;
                case 3317767:
                    if (str3.equals("left")) {
                        i7 = 4;
                    }
                    break;
                case 108511772:
                    if (str3.equals(EditStat.ELEMENT_RIGHT)) {
                        i7 = 5;
                    }
                    break;
                case 109757538:
                    if (str3.equals("start")) {
                        i7 = 6;
                    }
                    break;
                case 109780401:
                    if (str3.equals("style")) {
                        i7 = 7;
                    }
                    break;
            }
            switch (i7) {
                case 0:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    parseConstraint(state, layoutVariables, cLObject, chainReferenceHorizontalChain, str3);
                    continue;
                    i2 = 0;
                    break;
                case 1:
                    CLElement cLElement = cLObject.get(str3);
                    if (cLElement instanceof CLArray) {
                        CLArray cLArray2 = (CLArray) cLElement;
                        if (cLArray2.size() >= 1) {
                            int i8 = i2;
                            while (i8 < cLArray2.size()) {
                                CLElement cLElement2 = cLArray2.get(i8);
                                if (cLElement2 instanceof CLArray) {
                                    CLArray cLArray3 = (CLArray) cLElement2;
                                    if (cLArray3.size() > 0) {
                                        String strContent2 = cLArray3.get(i2).content();
                                        int size = cLArray3.size();
                                        if (size == i5) {
                                            float f4 = cLArray3.getFloat(i6);
                                            CLArray cLArray4 = cLArray2;
                                            f = f4;
                                            cLArray = cLArray4;
                                            pix = Float.NaN;
                                        } else {
                                            if (size == i4) {
                                                CLArray cLArray5 = cLArray2;
                                                f = cLArray3.getFloat(i6);
                                                cLArray = cLArray5;
                                                i8 = i8;
                                                i6 = i6;
                                                pix = toPix(state, cLArray3.getFloat(i5));
                                                strContent2 = strContent2;
                                                pix2 = Float.NaN;
                                                i = i5;
                                                f3 = pix;
                                                f2 = Float.NaN;
                                            } else if (size == 4) {
                                                float f5 = cLArray3.getFloat(i6);
                                                float pix3 = toPix(state, cLArray3.getFloat(i5));
                                                i4 = 3;
                                                CLArray cLArray6 = cLArray2;
                                                f = f5;
                                                cLArray = cLArray6;
                                                i6 = i6;
                                                pix = toPix(state, cLArray3.getFloat(3));
                                                f2 = Float.NaN;
                                                i = i5;
                                                f3 = pix3;
                                                i8 = i8;
                                                strContent2 = strContent2;
                                                pix2 = Float.NaN;
                                            } else if (size != i3) {
                                                cLArray = cLArray2;
                                                f = Float.NaN;
                                                pix = Float.NaN;
                                            } else {
                                                float f6 = cLArray3.getFloat(i6);
                                                float pix4 = toPix(state, cLArray3.getFloat(i5));
                                                float pix5 = toPix(state, cLArray3.getFloat(i4));
                                                float pix6 = toPix(state, cLArray3.getFloat(4));
                                                int i9 = i5;
                                                f3 = pix4;
                                                cLArray = cLArray2;
                                                f = f6;
                                                i = i9;
                                                i6 = i6;
                                                pix = pix5;
                                                i8 = i8;
                                                strContent2 = strContent2;
                                                pix2 = toPix(state, cLArray3.getFloat(5));
                                                f2 = pix6;
                                                i4 = 3;
                                            }
                                            chainReferenceHorizontalChain.addChainElement(strContent2, f, f3, pix, f2, pix2);
                                        }
                                        f2 = pix;
                                        pix2 = f2;
                                        i = i5;
                                        f3 = pix2;
                                        chainReferenceHorizontalChain.addChainElement(strContent2, f, f3, pix, f2, pix2);
                                    } else {
                                        i8 = i8;
                                        cLArray = cLArray2;
                                        i = i5;
                                        i6 = i6;
                                    }
                                } else {
                                    i8 = i8;
                                    cLArray = cLArray2;
                                    i = i5;
                                    i6 = i6;
                                    chainReferenceHorizontalChain.add(cLElement2.content());
                                }
                                i8++;
                                cLArray2 = cLArray;
                                i5 = i;
                                i6 = i6;
                                i2 = 0;
                                i3 = 6;
                            }
                            break;
                        }
                    }
                    System.err.println(str2 + " contains should be an array \"" + cLElement.content() + "\"");
                    return;
                case 7:
                    CLElement cLElement3 = cLObject.get(str3);
                    if (cLElement3 instanceof CLArray) {
                        CLArray cLArray7 = (CLArray) cLElement3;
                        if (cLArray7.size() > 1) {
                            strContent = cLArray7.getString(i2);
                            chainReferenceHorizontalChain.bias(cLArray7.getFloat(1));
                        } else {
                            strContent = cLElement3.content();
                        }
                    } else {
                        strContent = cLElement3.content();
                    }
                    strContent.hashCode();
                    if (strContent.equals("packed")) {
                        chainReferenceHorizontalChain.style(State.Chain.PACKED);
                    } else if (strContent.equals("spread_inside")) {
                        chainReferenceHorizontalChain.style(State.Chain.SPREAD_INSIDE);
                    } else {
                        chainReferenceHorizontalChain.style(State.Chain.SPREAD);
                    }
                    break;
            }
            i2 = 0;
        }
    }

    /* JADX WARN: Code duplicated, block: B:116:0x01f3  */
    private static void parseGridType(String str, State state, String str2, LayoutVariables layoutVariables, CLObject cLObject) throws CLParsingException {
        float f;
        float f2;
        float f3;
        float f4;
        GridReference grid = state.getGrid(str2, str);
        for (String str3 : cLObject.names()) {
            str3.hashCode();
            int i = 0;
            switch (str3) {
                case "orientation":
                    grid.setOrientation(cLObject.get(str3).getInt());
                    break;
                case "padding":
                    CLElement cLElement = cLObject.get(str3);
                    if (cLElement instanceof CLArray) {
                        CLArray cLArray = (CLArray) cLElement;
                        if (cLArray.size() > 1) {
                            f = cLArray.getInt(0);
                            f4 = cLArray.getInt(1);
                            if (cLArray.size() > 2) {
                                f3 = cLArray.getInt(2);
                                try {
                                    f2 = ((CLArray) cLElement).getInt(3);
                                } catch (ArrayIndexOutOfBoundsException unused) {
                                    f2 = 0.0f;
                                }
                            } else {
                                f2 = f4;
                                f3 = f;
                            }
                        } else {
                            f = cLElement.getInt();
                            f2 = f;
                            f3 = f2;
                            f4 = f3;
                        }
                    } else {
                        f = cLElement.getInt();
                        f2 = f;
                        f3 = f2;
                        f4 = f3;
                    }
                    grid.setPaddingStart(Math.round(toPix(state, f)));
                    grid.setPaddingTop(Math.round(toPix(state, f4)));
                    grid.setPaddingEnd(Math.round(toPix(state, f3)));
                    grid.setPaddingBottom(Math.round(toPix(state, f2)));
                    break;
                case "contains":
                    CLArray arrayOrNull = cLObject.getArrayOrNull(str3);
                    if (arrayOrNull != null) {
                        while (i < arrayOrNull.size()) {
                            grid.add(state.constraints(arrayOrNull.get(i).content()));
                            i++;
                        }
                        break;
                    } else {
                        break;
                    }
                    break;
                case "hGap":
                    grid.setHorizontalGaps(toPix(state, cLObject.get(str3).getFloat()));
                    break;
                case "rows":
                    int i2 = cLObject.get(str3).getInt();
                    if (i2 > 0) {
                        grid.setRowsSet(i2);
                        break;
                    } else {
                        break;
                    }
                    break;
                case "vGap":
                    grid.setVerticalGaps(toPix(state, cLObject.get(str3).getFloat()));
                    break;
                case "flags":
                    String strContent = "";
                    try {
                        CLElement cLElement2 = cLObject.get(str3);
                        if (cLElement2 instanceof CLNumber) {
                            i = cLElement2.getInt();
                        } else {
                            strContent = cLElement2.content();
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing grid flags " + e);
                    }
                    if (strContent != null && !strContent.isEmpty()) {
                        grid.setFlags(strContent);
                        break;
                    } else {
                        grid.setFlags(i);
                        break;
                    }
                    break;
                case "skips":
                    String strContent2 = cLObject.get(str3).content();
                    if (strContent2 == null || !strContent2.contains(":")) {
                        break;
                    } else {
                        grid.setSkips(strContent2);
                        break;
                    }
                    break;
                case "spans":
                    String strContent3 = cLObject.get(str3).content();
                    if (strContent3 == null || !strContent3.contains(":")) {
                        break;
                    } else {
                        grid.setSpans(strContent3);
                        break;
                    }
                    break;
                case "rowWeights":
                    String strContent4 = cLObject.get(str3).content();
                    if (strContent4 == null || !strContent4.contains(",")) {
                        break;
                    } else {
                        grid.setRowWeights(strContent4);
                        break;
                    }
                    break;
                case "columns":
                    int i3 = cLObject.get(str3).getInt();
                    if (i3 > 0) {
                        grid.setColumnsSet(i3);
                        break;
                    } else {
                        break;
                    }
                    break;
                case "columnWeights":
                    String strContent5 = cLObject.get(str3).content();
                    if (strContent5 == null || !strContent5.contains(",")) {
                        break;
                    } else {
                        grid.setColumnWeights(strContent5);
                        break;
                    }
                    break;
                default:
                    applyAttribute(state, layoutVariables, state.constraints(str2), cLObject, str3);
                    break;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:108:0x0238  */
    /* JADX WARN: Code duplicated, block: B:127:0x029c  */
    /* JADX WARN: Code duplicated, block: B:137:0x02fa  */
    /* JADX WARN: Code duplicated, block: B:174:0x03b3  */
    /* JADX WARN: Code duplicated, block: B:190:0x040a  */
    private static void parseFlowType(String str, State state, String str2, LayoutVariables layoutVariables, CLObject cLObject) throws CLParsingException {
        String strContent;
        String string;
        String string2;
        Float fValueOf;
        String strContent2;
        String string3;
        String string4;
        float f;
        float f2;
        float f3;
        float f4;
        Float fValueOf2;
        float f5;
        float pix;
        float pix2;
        float f6;
        FlowReference flow = state.getFlow(str2, str.charAt(0) == 'v');
        for (String str3 : cLObject.names()) {
            str3.hashCode();
            int i = 4;
            switch (str3) {
                case "hAlign":
                    String strContent3 = cLObject.get(str3).content();
                    strContent3.hashCode();
                    if (!strContent3.equals("end")) {
                        if (strContent3.equals("start")) {
                            flow.setHorizontalAlign(0);
                        } else {
                            flow.setHorizontalAlign(2);
                        }
                        break;
                    } else {
                        flow.setHorizontalAlign(1);
                        break;
                    }
                    break;
                case "hStyle":
                    CLElement cLElement = cLObject.get(str3);
                    if (cLElement instanceof CLArray) {
                        CLArray cLArray = (CLArray) cLElement;
                        if (cLArray.size() > 1) {
                            string = cLArray.getString(0);
                            strContent = cLArray.getString(1);
                            if (cLArray.size() > 2) {
                                string2 = cLArray.getString(2);
                            } else {
                                string2 = "";
                            }
                        } else {
                            strContent = cLElement.content();
                            string = "";
                            string2 = string;
                        }
                    } else {
                        strContent = cLElement.content();
                        string = "";
                        string2 = string;
                    }
                    if (!strContent.equals("")) {
                        flow.setHorizontalStyle(State.Chain.getValueByString(strContent));
                    }
                    if (!string.equals("")) {
                        flow.setFirstHorizontalStyle(State.Chain.getValueByString(string));
                    }
                    if (!string2.equals("")) {
                        flow.setLastHorizontalStyle(State.Chain.getValueByString(string2));
                        break;
                    } else {
                        break;
                    }
                    break;
                case "hFlowBias":
                    CLElement cLElement2 = cLObject.get(str3);
                    Float.valueOf(0.5f);
                    Float fValueOf3 = Float.valueOf(0.5f);
                    Float fValueOf4 = Float.valueOf(0.5f);
                    if (cLElement2 instanceof CLArray) {
                        CLArray cLArray2 = (CLArray) cLElement2;
                        if (cLArray2.size() > 1) {
                            fValueOf3 = Float.valueOf(cLArray2.getFloat(0));
                            fValueOf = Float.valueOf(cLArray2.getFloat(1));
                            if (cLArray2.size() > 2) {
                                fValueOf4 = Float.valueOf(cLArray2.getFloat(2));
                            }
                        } else {
                            fValueOf = Float.valueOf(cLElement2.getFloat());
                        }
                    } else {
                        fValueOf = Float.valueOf(cLElement2.getFloat());
                    }
                    flow.horizontalBias(fValueOf.floatValue());
                    if (fValueOf3.floatValue() != 0.5f) {
                        flow.setFirstHorizontalBias(fValueOf3.floatValue());
                    }
                    if (fValueOf4.floatValue() == 0.5f) {
                        break;
                    } else {
                        flow.setLastHorizontalBias(fValueOf4.floatValue());
                        break;
                    }
                    break;
                case "vAlign":
                    String strContent4 = cLObject.get(str3).content();
                    strContent4.hashCode();
                    switch (strContent4) {
                        case "baseline":
                            flow.setVerticalAlign(3);
                            break;
                        case "bottom":
                            flow.setVerticalAlign(1);
                            break;
                        case "top":
                            flow.setVerticalAlign(0);
                            break;
                        default:
                            flow.setVerticalAlign(2);
                            break;
                    }
                    break;
                case "vStyle":
                    CLElement cLElement3 = cLObject.get(str3);
                    if (cLElement3 instanceof CLArray) {
                        CLArray cLArray3 = (CLArray) cLElement3;
                        if (cLArray3.size() > 1) {
                            string3 = cLArray3.getString(0);
                            strContent2 = cLArray3.getString(1);
                            if (cLArray3.size() > 2) {
                                string4 = cLArray3.getString(2);
                            } else {
                                string4 = "";
                            }
                        } else {
                            strContent2 = cLElement3.content();
                            string3 = "";
                            string4 = string3;
                        }
                    } else {
                        strContent2 = cLElement3.content();
                        string3 = "";
                        string4 = string3;
                    }
                    if (!strContent2.equals("")) {
                        flow.setVerticalStyle(State.Chain.getValueByString(strContent2));
                    }
                    if (!string3.equals("")) {
                        flow.setFirstVerticalStyle(State.Chain.getValueByString(string3));
                    }
                    if (!string4.equals("")) {
                        flow.setLastVerticalStyle(State.Chain.getValueByString(string4));
                        break;
                    } else {
                        break;
                    }
                    break;
                case "padding":
                    CLElement cLElement4 = cLObject.get(str3);
                    if (cLElement4 instanceof CLArray) {
                        CLArray cLArray4 = (CLArray) cLElement4;
                        if (cLArray4.size() > 1) {
                            f = cLArray4.getInt(0);
                            f4 = cLArray4.getInt(1);
                            if (cLArray4.size() > 2) {
                                f3 = cLArray4.getInt(2);
                                try {
                                    f2 = ((CLArray) cLElement4).getInt(3);
                                } catch (ArrayIndexOutOfBoundsException unused) {
                                    f2 = 0.0f;
                                }
                            } else {
                                f3 = f;
                                f2 = f4;
                            }
                        } else {
                            f = cLElement4.getInt();
                            f2 = f;
                            f3 = f2;
                            f4 = f3;
                        }
                    } else {
                        f = cLElement4.getInt();
                        f2 = f;
                        f3 = f2;
                        f4 = f3;
                    }
                    flow.setPaddingLeft(Math.round(toPix(state, f)));
                    flow.setPaddingTop(Math.round(toPix(state, f4)));
                    flow.setPaddingRight(Math.round(toPix(state, f3)));
                    flow.setPaddingBottom(Math.round(toPix(state, f2)));
                    break;
                case "vFlowBias":
                    CLElement cLElement5 = cLObject.get(str3);
                    Float.valueOf(0.5f);
                    Float fValueOf5 = Float.valueOf(0.5f);
                    Float fValueOf6 = Float.valueOf(0.5f);
                    if (cLElement5 instanceof CLArray) {
                        CLArray cLArray5 = (CLArray) cLElement5;
                        if (cLArray5.size() > 1) {
                            fValueOf5 = Float.valueOf(cLArray5.getFloat(0));
                            fValueOf2 = Float.valueOf(cLArray5.getFloat(1));
                            if (cLArray5.size() > 2) {
                                fValueOf6 = Float.valueOf(cLArray5.getFloat(2));
                            }
                        } else {
                            fValueOf2 = Float.valueOf(cLElement5.getFloat());
                        }
                    } else {
                        fValueOf2 = Float.valueOf(cLElement5.getFloat());
                    }
                    try {
                        flow.verticalBias(fValueOf2.floatValue());
                        if (fValueOf5.floatValue() != 0.5f) {
                            flow.setFirstVerticalBias(fValueOf5.floatValue());
                        }
                        if (fValueOf6.floatValue() != 0.5f) {
                            flow.setLastVerticalBias(fValueOf6.floatValue());
                        }
                        break;
                    } catch (NumberFormatException unused2) {
                        break;
                    }
                    break;
                case "contains":
                    CLElement cLElement6 = cLObject.get(str3);
                    if (cLElement6 instanceof CLArray) {
                        CLArray cLArray6 = (CLArray) cLElement6;
                        if (cLArray6.size() >= 1) {
                            int i2 = 0;
                            while (i2 < cLArray6.size()) {
                                CLElement cLElement7 = cLArray6.get(i2);
                                if (cLElement7 instanceof CLArray) {
                                    CLArray cLArray7 = (CLArray) cLElement7;
                                    if (cLArray7.size() > 0) {
                                        String strContent5 = cLArray7.get(0).content();
                                        int size = cLArray7.size();
                                        if (size != 2) {
                                            if (size == 3) {
                                                f6 = cLArray7.getFloat(1);
                                                pix = toPix(state, cLArray7.getFloat(2));
                                                pix2 = pix;
                                            } else if (size != i) {
                                                pix = Float.NaN;
                                                f5 = Float.NaN;
                                                pix2 = Float.NaN;
                                            } else {
                                                f6 = cLArray7.getFloat(1);
                                                pix2 = toPix(state, cLArray7.getFloat(2));
                                                pix = toPix(state, cLArray7.getFloat(3));
                                            }
                                            f5 = f6;
                                        } else {
                                            f5 = cLArray7.getFloat(1);
                                            pix = Float.NaN;
                                            pix2 = Float.NaN;
                                        }
                                        flow.addFlowElement(strContent5, f5, pix2, pix);
                                    }
                                } else {
                                    flow.add(cLElement7.content());
                                }
                                i2++;
                                i = 4;
                            }
                            break;
                        }
                    }
                    System.err.println(str2 + " contains should be an array \"" + cLElement6.content() + "\"");
                    return;
                case "maxElement":
                    flow.setMaxElementsWrap(cLObject.get(str3).getInt());
                    break;
                case "hGap":
                    flow.setHorizontalGap(cLObject.get(str3).getInt());
                    break;
                case "type":
                    if (cLObject.get(str3).content().equals("hFlow")) {
                        flow.setOrientation(0);
                        break;
                    } else {
                        flow.setOrientation(1);
                        break;
                    }
                    break;
                case "vGap":
                    flow.setVerticalGap(cLObject.get(str3).getInt());
                    break;
                case "wrap":
                    flow.setWrapMode(State.Wrap.getValueByString(cLObject.get(str3).content()));
                    break;
                default:
                    applyAttribute(state, layoutVariables, state.constraints(str2), cLObject, str3);
                    break;
            }
        }
    }

    static void parseGuideline(int i, State state, CLArray cLArray) throws CLParsingException {
        CLObject cLObject;
        String stringOrNull;
        CLElement cLElement = cLArray.get(1);
        if ((cLElement instanceof CLObject) && (stringOrNull = (cLObject = (CLObject) cLElement).getStringOrNull("id")) != null) {
            parseGuidelineParams(i, state, stringOrNull, cLObject);
        }
    }

    /* JADX WARN: switch over string: strings are not added: [[left]] */
    static void parseGuidelineParams(int i, State state, String str, CLObject cLObject) throws CLParsingException {
        String next;
        ArrayList<String> arrayListNames = cLObject.names();
        if (arrayListNames == null) {
            return;
        }
        ConstraintReference constraintReferenceConstraints = state.constraints(str);
        if (i == 0) {
            state.horizontalGuideline(str);
        } else {
            state.verticalGuideline(str);
        }
        boolean z = !state.isRtl() || i == 0;
        GuidelineReference guidelineReference = (GuidelineReference) constraintReferenceConstraints.getFacade();
        Iterator<String> it = arrayListNames.iterator();
        float pix = 0.0f;
        boolean z2 = false;
        while (true) {
            boolean z3 = true;
            while (true) {
                if (!it.hasNext()) {
                    if (z2) {
                        if (z3) {
                            guidelineReference.percent(pix);
                            return;
                        } else {
                            guidelineReference.percent(1.0f - pix);
                            return;
                        }
                    }
                    if (z3) {
                        guidelineReference.start(Float.valueOf(pix));
                        return;
                    } else {
                        guidelineReference.end(Float.valueOf(pix));
                        return;
                    }
                }
                next = it.next();
                next.hashCode();
                switch (next) {
                    case "percent":
                        CLArray arrayOrNull = cLObject.getArrayOrNull(next);
                        if (arrayOrNull == null) {
                            pix = cLObject.getFloat(next);
                            z2 = true;
                            z3 = true;
                            break;
                        } else {
                            if (arrayOrNull.size() > 1) {
                                String string = arrayOrNull.getString(0);
                                float f = arrayOrNull.getFloat(1);
                                string.hashCode();
                                switch (string) {
                                    case "end":
                                        z3 = !z;
                                        pix = f;
                                        break;
                                    case "left":
                                        z3 = true;
                                        pix = f;
                                        z2 = true;
                                        break;
                                    case "right":
                                        z3 = false;
                                        pix = f;
                                        break;
                                    case "start":
                                        z3 = z;
                                        pix = f;
                                        break;
                                    default:
                                        pix = f;
                                        break;
                                }
                            }
                            z2 = true;
                            break;
                        }
                        break;
                    case "end":
                        pix = toPix(state, cLObject.getFloat(next));
                        z3 = !z;
                        break;
                    case "right":
                        pix = toPix(state, cLObject.getFloat(next));
                        z3 = false;
                        break;
                    case "start":
                        pix = toPix(state, cLObject.getFloat(next));
                        z3 = z;
                        break;
                }
            }
            pix = toPix(state, cLObject.getFloat(next));
        }
    }

    static void parseBarrier(State state, String str, CLObject cLObject) throws CLParsingException {
        boolean zIsRtl = state.isRtl();
        BarrierReference barrierReferenceBarrier = state.barrier(str, State.Direction.END);
        ArrayList<String> arrayListNames = cLObject.names();
        if (arrayListNames == null) {
            return;
        }
        for (String str2 : arrayListNames) {
            str2.hashCode();
            byte b = 2;
            switch (str2) {
                case "margin":
                    float floatOrNaN = cLObject.getFloatOrNaN(str2);
                    if (!Float.isNaN(floatOrNaN)) {
                        barrierReferenceBarrier.margin(Float.valueOf(toPix(state, floatOrNaN)));
                        break;
                    } else {
                        break;
                    }
                    break;
                case "direction":
                    String string = cLObject.getString(str2);
                    string.hashCode();
                    switch (string.hashCode()) {
                        case -1383228885:
                            b = string.equals("bottom") ? (byte) 0 : (byte) -1;
                            break;
                        case 100571:
                            b = string.equals("end") ? (byte) 1 : (byte) -1;
                            break;
                        case 115029:
                            if (!string.equals(Layer.KEY_TOP)) {
                                b = -1;
                            }
                            break;
                        case 3317767:
                            b = string.equals("left") ? (byte) 3 : (byte) -1;
                            break;
                        case 108511772:
                            b = string.equals(EditStat.ELEMENT_RIGHT) ? (byte) 4 : (byte) -1;
                            break;
                        case 109757538:
                            b = string.equals("start") ? (byte) 5 : (byte) -1;
                            break;
                        default:
                            b = -1;
                            break;
                    }
                    switch (b) {
                        case 0:
                            barrierReferenceBarrier.setBarrierDirection(State.Direction.BOTTOM);
                            break;
                        case 1:
                            if (!zIsRtl) {
                                barrierReferenceBarrier.setBarrierDirection(State.Direction.RIGHT);
                            } else {
                                barrierReferenceBarrier.setBarrierDirection(State.Direction.LEFT);
                            }
                            break;
                        case 2:
                            barrierReferenceBarrier.setBarrierDirection(State.Direction.TOP);
                            break;
                        case 3:
                            barrierReferenceBarrier.setBarrierDirection(State.Direction.LEFT);
                            break;
                        case 4:
                            barrierReferenceBarrier.setBarrierDirection(State.Direction.RIGHT);
                            break;
                        case 5:
                            if (!zIsRtl) {
                                barrierReferenceBarrier.setBarrierDirection(State.Direction.LEFT);
                            } else {
                                barrierReferenceBarrier.setBarrierDirection(State.Direction.RIGHT);
                            }
                            break;
                    }
                    break;
                case "contains":
                    CLArray arrayOrNull = cLObject.getArrayOrNull(str2);
                    if (arrayOrNull == null) {
                        break;
                    } else {
                        for (int i = 0; i < arrayOrNull.size(); i++) {
                            barrierReferenceBarrier.add(state.constraints(arrayOrNull.get(i).content()));
                        }
                        break;
                    }
                    break;
            }
        }
    }

    static void parseWidget(State state, LayoutVariables layoutVariables, String str, CLObject cLObject) throws CLParsingException {
        parseWidget(state, layoutVariables, state.constraints(str), cLObject);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    static void applyAttribute(State state, LayoutVariables layoutVariables, ConstraintReference constraintReference, CLObject cLObject, String str) throws CLParsingException {
        byte b;
        ConstraintReference constraintReferenceConstraints;
        str.hashCode();
        switch (str.hashCode()) {
            case -1448775240:
                b = !str.equals("centerVertically") ? (byte) -1 : (byte) 0;
                break;
            case -1364013995:
                b = !str.equals(EditStat.ELEMENT_CENTER) ? (byte) -1 : (byte) 1;
                break;
            case -1349088399:
                b = !str.equals(SchedulerSupport.CUSTOM) ? (byte) -1 : (byte) 2;
                break;
            case -1249320806:
                b = !str.equals("rotationX") ? (byte) -1 : (byte) 3;
                break;
            case -1249320805:
                b = !str.equals("rotationY") ? (byte) -1 : (byte) 4;
                break;
            case -1249320804:
                b = !str.equals("rotationZ") ? (byte) -1 : (byte) 5;
                break;
            case -1225497657:
                b = !str.equals("translationX") ? (byte) -1 : (byte) 6;
                break;
            case -1225497656:
                b = !str.equals("translationY") ? (byte) -1 : (byte) 7;
                break;
            case -1225497655:
                b = !str.equals("translationZ") ? (byte) -1 : (byte) 8;
                break;
            case -1221029593:
                b = !str.equals(Layer.KEY_HEIGHT) ? (byte) -1 : (byte) 9;
                break;
            case -1068318794:
                b = !str.equals("motion") ? (byte) -1 : (byte) 10;
                break;
            case -987906986:
                b = !str.equals("pivotX") ? (byte) -1 : (byte) 11;
                break;
            case -987906985:
                b = !str.equals("pivotY") ? (byte) -1 : (byte) 12;
                break;
            case -908189618:
                b = !str.equals("scaleX") ? (byte) -1 : (byte) 13;
                break;
            case -908189617:
                b = !str.equals("scaleY") ? (byte) -1 : (byte) 14;
                break;
            case -247669061:
                b = !str.equals("hRtlBias") ? (byte) -1 : (byte) 15;
                break;
            case -61505906:
                b = !str.equals("vWeight") ? (byte) -1 : (byte) 16;
                break;
            case 92909918:
                b = !str.equals("alpha") ? (byte) -1 : (byte) 17;
                break;
            case 98116417:
                b = !str.equals("hBias") ? (byte) -1 : (byte) 18;
                break;
            case 111045711:
                b = !str.equals("vBias") ? (byte) -1 : (byte) 19;
                break;
            case 113126854:
                b = !str.equals(Layer.KEY_WIDTH) ? (byte) -1 : Ascii.DC4;
                break;
            case 398344448:
                b = !str.equals("hWeight") ? (byte) -1 : Ascii.NAK;
                break;
            case 1404070310:
                b = !str.equals("centerHorizontally") ? (byte) -1 : Ascii.SYN;
                break;
            case 1941332754:
                b = !str.equals("visibility") ? (byte) -1 : Ascii.ETB;
                break;
            default:
                b = -1;
                break;
        }
        switch (b) {
            case 0:
                String string = cLObject.getString(str);
                boolean zEquals = string.equals("parent");
                Object obj = string;
                if (zEquals) {
                    obj = State.PARENT;
                }
                ConstraintReference constraintReferenceConstraints2 = state.constraints(obj);
                constraintReference.topToTop(constraintReferenceConstraints2);
                constraintReference.bottomToBottom(constraintReferenceConstraints2);
                break;
            case 1:
                String string2 = cLObject.getString(str);
                if (string2.equals("parent")) {
                    constraintReferenceConstraints = state.constraints(State.PARENT);
                } else {
                    constraintReferenceConstraints = state.constraints(string2);
                }
                constraintReference.startToStart(constraintReferenceConstraints);
                constraintReference.endToEnd(constraintReferenceConstraints);
                constraintReference.topToTop(constraintReferenceConstraints);
                constraintReference.bottomToBottom(constraintReferenceConstraints);
                break;
            case 2:
                parseCustomProperties(cLObject, constraintReference, str);
                break;
            case 3:
                constraintReference.rotationX(layoutVariables.get(cLObject.get(str)));
                break;
            case 4:
                constraintReference.rotationY(layoutVariables.get(cLObject.get(str)));
                break;
            case 5:
                constraintReference.rotationZ(layoutVariables.get(cLObject.get(str)));
                break;
            case 6:
                constraintReference.translationX(toPix(state, layoutVariables.get(cLObject.get(str))));
                break;
            case 7:
                constraintReference.translationY(toPix(state, layoutVariables.get(cLObject.get(str))));
                break;
            case 8:
                constraintReference.translationZ(toPix(state, layoutVariables.get(cLObject.get(str))));
                break;
            case 9:
                constraintReference.setHeight(parseDimension(cLObject, str, state, state.getDpToPixel()));
                break;
            case 10:
                parseMotionProperties(cLObject.get(str), constraintReference);
                break;
            case 11:
                constraintReference.pivotX(layoutVariables.get(cLObject.get(str)));
                break;
            case 12:
                constraintReference.pivotY(layoutVariables.get(cLObject.get(str)));
                break;
            case 13:
                constraintReference.scaleX(layoutVariables.get(cLObject.get(str)));
                break;
            case 14:
                constraintReference.scaleY(layoutVariables.get(cLObject.get(str)));
                break;
            case 15:
                float f = layoutVariables.get(cLObject.get(str));
                if (state.isRtl()) {
                    f = 1.0f - f;
                }
                constraintReference.horizontalBias(f);
                break;
            case 16:
                constraintReference.setVerticalChainWeight(layoutVariables.get(cLObject.get(str)));
                break;
            case 17:
                constraintReference.alpha(layoutVariables.get(cLObject.get(str)));
                break;
            case 18:
                constraintReference.horizontalBias(layoutVariables.get(cLObject.get(str)));
                break;
            case 19:
                constraintReference.verticalBias(layoutVariables.get(cLObject.get(str)));
                break;
            case 20:
                constraintReference.setWidth(parseDimension(cLObject, str, state, state.getDpToPixel()));
                break;
            case 21:
                constraintReference.setHorizontalChainWeight(layoutVariables.get(cLObject.get(str)));
                break;
            case 22:
                String string3 = cLObject.getString(str);
                boolean zEquals2 = string3.equals("parent");
                Object obj2 = string3;
                if (zEquals2) {
                    obj2 = State.PARENT;
                }
                ConstraintReference constraintReferenceConstraints3 = state.constraints(obj2);
                constraintReference.startToStart(constraintReferenceConstraints3);
                constraintReference.endToEnd(constraintReferenceConstraints3);
                break;
            case 23:
                String string4 = cLObject.getString(str);
                string4.hashCode();
                switch (string4) {
                    case "invisible":
                        constraintReference.visibility(4);
                        constraintReference.alpha(0.0f);
                        break;
                    case "gone":
                        constraintReference.visibility(8);
                        break;
                    case "visible":
                        constraintReference.visibility(0);
                        break;
                }
                break;
            default:
                parseConstraint(state, layoutVariables, cLObject, constraintReference, str);
                break;
        }
    }

    static void parseWidget(State state, LayoutVariables layoutVariables, ConstraintReference constraintReference, CLObject cLObject) throws CLParsingException {
        if (constraintReference.getWidth() == null) {
            constraintReference.setWidth(Dimension.createWrap());
        }
        if (constraintReference.getHeight() == null) {
            constraintReference.setHeight(Dimension.createWrap());
        }
        ArrayList<String> arrayListNames = cLObject.names();
        if (arrayListNames == null) {
            return;
        }
        Iterator<String> it = arrayListNames.iterator();
        while (it.hasNext()) {
            applyAttribute(state, layoutVariables, constraintReference, cLObject, it.next());
        }
    }

    static void parseCustomProperties(CLObject cLObject, ConstraintReference constraintReference, String str) throws CLParsingException {
        ArrayList<String> arrayListNames;
        CLObject objectOrNull = cLObject.getObjectOrNull(str);
        if (objectOrNull == null || (arrayListNames = objectOrNull.names()) == null) {
            return;
        }
        for (String str2 : arrayListNames) {
            CLElement cLElement = objectOrNull.get(str2);
            if (cLElement instanceof CLNumber) {
                constraintReference.addCustomFloat(str2, cLElement.getFloat());
            } else if (cLElement instanceof CLString) {
                long colorString = parseColorString(cLElement.content());
                if (colorString != -1) {
                    constraintReference.addCustomColor(str2, (int) colorString);
                }
            }
        }
    }

    private static int indexOf(String str, String... strArr) {
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private static void parseMotionProperties(CLElement cLElement, ConstraintReference constraintReference) throws CLParsingException {
        if (cLElement instanceof CLObject) {
            CLObject cLObject = (CLObject) cLElement;
            TypedBundle typedBundle = new TypedBundle();
            ArrayList<String> arrayListNames = cLObject.names();
            if (arrayListNames == null) {
                return;
            }
            for (String str : arrayListNames) {
                str.hashCode();
                switch (str) {
                    case "stagger":
                        typedBundle.add(600, cLObject.getFloat(str));
                        break;
                    case "easing":
                        typedBundle.add(603, cLObject.getString(str));
                        break;
                    case "quantize":
                        CLElement cLElement2 = cLObject.get(str);
                        if (cLElement2 instanceof CLArray) {
                            CLArray cLArray = (CLArray) cLElement2;
                            int size = cLArray.size();
                            if (size > 0) {
                                typedBundle.add(610, cLArray.getInt(0));
                                if (size > 1) {
                                    typedBundle.add(611, cLArray.getString(1));
                                    if (size > 2) {
                                        typedBundle.add(602, cLArray.getFloat(2));
                                    }
                                }
                            }
                            break;
                        } else {
                            typedBundle.add(610, cLObject.getInt(str));
                            break;
                        }
                        break;
                    case "pathArc":
                        String string = cLObject.getString(str);
                        int iIndexOf = indexOf(string, "none", "startVertical", "startHorizontal", "flip", "below", "above");
                        if (iIndexOf == -1) {
                            System.err.println(cLObject.getLine() + " pathArc = '" + string + "'");
                            break;
                        } else {
                            typedBundle.add(607, iIndexOf);
                            break;
                        }
                        break;
                    case "relativeTo":
                        typedBundle.add(605, cLObject.getString(str));
                        break;
                }
            }
            constraintReference.mMotionProperties = typedBundle;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:53:0x00d7. Please report as an issue. */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r16v5 */
    /* JADX WARN: Type inference failed for: r16v6 */
    /* JADX WARN: Type inference failed for: r16v7 */
    /* JADX WARN: Type inference failed for: r16v8 */
    /* JADX WARN: Type inference failed for: r16v9 */
    static void parseConstraint(State state, LayoutVariables layoutVariables, CLObject cLObject, ConstraintReference constraintReference, String str) throws CLParsingException {
        ConstraintReference constraintReferenceConstraints;
        ConstraintReference constraintReferenceConstraints2;
        boolean z;
        char c;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean zIsRtl = state.isRtl();
        boolean z5 = !zIsRtl;
        CLArray arrayOrNull = cLObject.getArrayOrNull(str);
        ?? r16 = -1;
        r16 = -1;
        r16 = -1;
        r16 = -1;
        r16 = -1;
        if (arrayOrNull != null && arrayOrNull.size() > 1) {
            String string = arrayOrNull.getString(0);
            String stringOrNull = arrayOrNull.getStringOrNull(1);
            float pix = arrayOrNull.size() > 2 ? toPix(state, layoutVariables.get(arrayOrNull.getOrNull(2))) : 0.0f;
            float pix2 = arrayOrNull.size() > 3 ? toPix(state, layoutVariables.get(arrayOrNull.getOrNull(3))) : 0.0f;
            if (string.equals("parent")) {
                constraintReferenceConstraints2 = state.constraints(State.PARENT);
            } else {
                constraintReferenceConstraints2 = state.constraints(string);
            }
            str.hashCode();
            float f = pix;
            switch (str) {
                case "baseline":
                    z = true;
                    c = 2;
                    stringOrNull.hashCode();
                    switch (stringOrNull) {
                        case "baseline":
                            state.baselineNeededFor(constraintReference.getKey());
                            state.baselineNeededFor(constraintReferenceConstraints2.getKey());
                            constraintReference.baselineToBaseline(constraintReferenceConstraints2);
                            break;
                        case "bottom":
                            state.baselineNeededFor(constraintReference.getKey());
                            constraintReference.baselineToBottom(constraintReferenceConstraints2);
                            break;
                        case "top":
                            state.baselineNeededFor(constraintReference.getKey());
                            constraintReference.baselineToTop(constraintReferenceConstraints2);
                            break;
                    }
                    z2 = z;
                    z3 = false;
                    break;
                case "circular":
                    z = true;
                    constraintReference.circularConstraint(constraintReferenceConstraints2, layoutVariables.get(arrayOrNull.get(1)), arrayOrNull.size() > 2 ? toPix(state, layoutVariables.get(arrayOrNull.getOrNull(2))) : 0.0f);
                    c = 2;
                    z2 = z;
                    z3 = false;
                    break;
                case "bottom":
                    stringOrNull.hashCode();
                    switch (stringOrNull) {
                        case "baseline":
                            state.baselineNeededFor(constraintReferenceConstraints2.getKey());
                            constraintReference.bottomToBaseline(constraintReferenceConstraints2);
                            break;
                        case "bottom":
                            constraintReference.bottomToBottom(constraintReferenceConstraints2);
                            break;
                        case "top":
                            constraintReference.bottomToTop(constraintReferenceConstraints2);
                            break;
                    }
                    z = true;
                    c = 2;
                    z2 = z;
                    z3 = false;
                    break;
                case "end":
                    z2 = zIsRtl;
                    z = true;
                    c = 2;
                    z3 = true;
                    break;
                case "top":
                    stringOrNull.hashCode();
                    switch (stringOrNull) {
                        case "baseline":
                            state.baselineNeededFor(constraintReferenceConstraints2.getKey());
                            constraintReference.topToBaseline(constraintReferenceConstraints2);
                            break;
                        case "bottom":
                            constraintReference.topToBottom(constraintReferenceConstraints2);
                            break;
                        case "top":
                            constraintReference.topToTop(constraintReferenceConstraints2);
                            break;
                    }
                    z = true;
                    c = 2;
                    z2 = z;
                    z3 = false;
                    break;
                case "left":
                    z2 = true;
                    z = true;
                    c = 2;
                    z3 = true;
                    break;
                case "right":
                    z2 = false;
                    z = true;
                    c = 2;
                    z3 = true;
                    break;
                case "start":
                    z2 = z5;
                    z = true;
                    c = 2;
                    z3 = true;
                    break;
                default:
                    z = true;
                    c = 2;
                    z2 = z;
                    z3 = false;
                    break;
            }
            if (z3) {
                stringOrNull.hashCode();
                switch (stringOrNull.hashCode()) {
                    case 100571:
                        if (stringOrNull.equals("end")) {
                            r16 = 0;
                        }
                        break;
                    case 3317767:
                        if (stringOrNull.equals("left")) {
                            r16 = z;
                        }
                        break;
                    case 108511772:
                        if (stringOrNull.equals(EditStat.ELEMENT_RIGHT)) {
                            r16 = c;
                        }
                        break;
                    case 109757538:
                        if (stringOrNull.equals("start")) {
                            r16 = 3;
                        }
                        break;
                }
                switch (r16) {
                    case 0:
                        z4 = zIsRtl;
                        break;
                    case 1:
                    default:
                        z4 = z;
                        break;
                    case 2:
                        z4 = false;
                        break;
                    case 3:
                        z4 = z5;
                        break;
                }
                if (z2) {
                    if (z4) {
                        constraintReference.leftToLeft(constraintReferenceConstraints2);
                    } else {
                        constraintReference.leftToRight(constraintReferenceConstraints2);
                    }
                } else if (z4) {
                    constraintReference.rightToLeft(constraintReferenceConstraints2);
                } else {
                    constraintReference.rightToRight(constraintReferenceConstraints2);
                }
            }
            constraintReference.margin(Float.valueOf(f)).marginGone(Float.valueOf(pix2));
        }
        String stringOrNull2 = cLObject.getStringOrNull(str);
        if (stringOrNull2 != null) {
            if (stringOrNull2.equals("parent")) {
                constraintReferenceConstraints = state.constraints(State.PARENT);
            } else {
                constraintReferenceConstraints = state.constraints(stringOrNull2);
            }
            str.hashCode();
            switch (str) {
                case "baseline":
                    state.baselineNeededFor(constraintReference.getKey());
                    state.baselineNeededFor(constraintReferenceConstraints.getKey());
                    constraintReference.baselineToBaseline(constraintReferenceConstraints);
                    break;
                case "bottom":
                    constraintReference.bottomToBottom(constraintReferenceConstraints);
                    break;
                case "end":
                    if (!zIsRtl) {
                        constraintReference.rightToRight(constraintReferenceConstraints);
                        break;
                    } else {
                        constraintReference.leftToLeft(constraintReferenceConstraints);
                        break;
                    }
                    break;
                case "top":
                    constraintReference.topToTop(constraintReferenceConstraints);
                    break;
                case "start":
                    if (!zIsRtl) {
                        constraintReference.leftToLeft(constraintReferenceConstraints);
                        break;
                    } else {
                        constraintReference.rightToRight(constraintReferenceConstraints);
                        break;
                    }
                    break;
            }
        }
    }

    static Dimension parseDimensionMode(String str) {
        Dimension dimensionCreateFixed = Dimension.createFixed(0);
        str.hashCode();
        switch (str) {
            case "preferWrap":
                return Dimension.createSuggested(Dimension.WRAP_DIMENSION);
            case "parent":
                return Dimension.createParent();
            case "spread":
                return Dimension.createSuggested(Dimension.SPREAD_DIMENSION);
            case "wrap":
                return Dimension.createWrap();
            default:
                if (str.endsWith("%")) {
                    return Dimension.createPercent(0, Float.parseFloat(str.substring(0, str.indexOf(37))) / 100.0f).suggested(0);
                }
                return str.contains(":") ? Dimension.createRatio(str).suggested(Dimension.SPREAD_DIMENSION) : dimensionCreateFixed;
        }
    }

    static Dimension parseDimension(CLObject cLObject, String str, State state, CorePixelDp corePixelDp) throws CLParsingException {
        CLElement cLElement = cLObject.get(str);
        Dimension dimensionCreateFixed = Dimension.createFixed(0);
        if (cLElement instanceof CLString) {
            return parseDimensionMode(cLElement.content());
        }
        if (cLElement instanceof CLNumber) {
            return Dimension.createFixed(state.convertDimension(Float.valueOf(corePixelDp.toPixels(cLObject.getFloat(str)))));
        }
        if (cLElement instanceof CLObject) {
            CLObject cLObject2 = (CLObject) cLElement;
            String stringOrNull = cLObject2.getStringOrNull("value");
            if (stringOrNull != null) {
                dimensionCreateFixed = parseDimensionMode(stringOrNull);
            }
            CLElement orNull = cLObject2.getOrNull("min");
            if (orNull != null) {
                if (orNull instanceof CLNumber) {
                    dimensionCreateFixed.min(state.convertDimension(Float.valueOf(corePixelDp.toPixels(((CLNumber) orNull).getFloat()))));
                } else if (orNull instanceof CLString) {
                    dimensionCreateFixed.min(Dimension.WRAP_DIMENSION);
                }
            }
            CLElement orNull2 = cLObject2.getOrNull("max");
            if (orNull2 != null) {
                if (orNull2 instanceof CLNumber) {
                    dimensionCreateFixed.max(state.convertDimension(Float.valueOf(corePixelDp.toPixels(((CLNumber) orNull2).getFloat()))));
                    return dimensionCreateFixed;
                }
                if (orNull2 instanceof CLString) {
                    dimensionCreateFixed.max(Dimension.WRAP_DIMENSION);
                }
            }
        }
        return dimensionCreateFixed;
    }

    static long parseColorString(String str) {
        if (!str.startsWith(HyperMenuUtils.GROUP_TITLE_START)) {
            return -1L;
        }
        String strSubstring = str.substring(1);
        if (strSubstring.length() == 6) {
            strSubstring = "FF" + strSubstring;
        }
        return Long.parseLong(strSubstring, 16);
    }

    static String lookForType(CLObject cLObject) throws CLParsingException {
        Iterator<String> it = cLObject.names().iterator();
        while (it.hasNext()) {
            if (it.next().equals("type")) {
                return cLObject.getString("type");
            }
        }
        return null;
    }
}
