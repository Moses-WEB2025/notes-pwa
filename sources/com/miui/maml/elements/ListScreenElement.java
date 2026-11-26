package com.miui.maml.elements;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.miui.common.stat.ToDoStat;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.ContextVariables;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.util.Utils;
import java.util.ArrayList;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class ListScreenElement extends ElementGroup {
    private static double ACC = -800.0d;
    private static String DATA_TYPE_BITMAP = "bitmap";
    private static String DATA_TYPE_DOUBLE = "double";
    private static String DATA_TYPE_FLOAT = "float";
    private static String DATA_TYPE_INTEGER = "int";
    private static String DATA_TYPE_INTEGER1 = "integer";
    private static String DATA_TYPE_LONG = "long";
    private static String DATA_TYPE_STRING = "string";
    private static final String LOG_TAG = "ListScreenElement";
    public static final String TAG_NAME = "List";
    protected AttrDataBinders mAttrDataBinders;
    private int mBottomIndex;
    private int mCachedItemCount;
    private boolean mClearOnFinish;
    private ArrayList<ColumnInfo> mColumnsInfo;
    private int mCurrentIndex;
    private long mCurrentTime;
    private ArrayList<DataIndexMap> mDataList;
    private ArrayList<Integer> mIndexOrder;
    private IndexedVariable[] mIndexedVariables;
    private ElementGroup mInnerGroup;
    private boolean mIsChildScroll;
    private boolean mIsScroll;
    private boolean mIsUpDirection;
    private ListItemElement mItem;
    private int mItemCount;
    private long mLastTime;
    protected ListData mListData;
    private final Object mLock;
    private Expression mMaxHeight;
    private boolean mMoving;
    private double mOffsetX;
    private double mOffsetY;
    private boolean mPressed;
    private ArrayList<Integer> mReuseIndex;
    private AnimatedScreenElement mScrollBar;
    private int mSelectedId;
    private IndexedVariable mSelectedIdVar;
    private double mSpeed;
    private long mStartAnimTime;
    private float mStartAnimY;
    private int mTopIndex;
    private double mTouchStartX;
    private double mTouchStartY;
    private int mVisibleItemCount;

    private static class ListItemElement extends ElementGroup {
        public static final String TAG_NAME = "Item";
        private int mDataIndex;
        private AnimatedScreenElement mDivider;
        protected Element mNode;

        public int getDataIndex() {
            return this.mDataIndex;
        }

        public void setDataIndex(int i) {
            this.mDataIndex = i;
            AnimatedScreenElement animatedScreenElement = this.mDivider;
            if (animatedScreenElement != null) {
                if (i <= 0) {
                    animatedScreenElement.show(false);
                } else {
                    animatedScreenElement.show(true);
                }
            }
        }

        public ListItemElement(Element element, ScreenElementRoot screenElementRoot) {
            super(element, screenElementRoot);
            this.mDataIndex = -1;
            this.mNode = element;
            ScreenElement screenElementFindElement = findElement("divider");
            if (screenElementFindElement instanceof AnimatedScreenElement) {
                this.mDivider = (AnimatedScreenElement) screenElementFindElement;
                removeElement(screenElementFindElement);
                addElement(this.mDivider);
            }
            this.mAlignV = ScreenElement.AlignV.TOP;
        }
    }

    private static class DataIndexMap {
        public Object[] mData;
        public int mElementIndex;
        public boolean mNeedRebind;

        public DataIndexMap(Object[] objArr, int i) {
            this.mData = objArr;
            this.mElementIndex = i;
        }

        public DataIndexMap(Object[] objArr) {
            this(objArr, -1);
        }

        public void setData(int i, Object obj) {
            Object[] objArr = this.mData;
            if (objArr == null || objArr.length <= i) {
                return;
            }
            objArr[i] = obj;
            this.mNeedRebind = true;
        }
    }

    public static class ColumnInfo {
        public Type mType;
        public String mVarName;

        public enum Type {
            STRING,
            BITMAP,
            INTEGER,
            DOUBLE,
            LONG,
            FLOAT;

            public boolean isNumber() {
                return this == INTEGER || this == DOUBLE || this == LONG || this == FLOAT;
            }
        }

        public ColumnInfo(String str) {
            int iIndexOf = str.indexOf(":");
            if (iIndexOf == -1) {
                throw new IllegalArgumentException("List: invalid item data " + str);
            }
            this.mVarName = str.substring(0, iIndexOf);
            String strSubstring = str.substring(iIndexOf + 1);
            if (!ListScreenElement.DATA_TYPE_STRING.equals(strSubstring)) {
                if (!ListScreenElement.DATA_TYPE_BITMAP.equals(strSubstring)) {
                    if (!ListScreenElement.DATA_TYPE_INTEGER.equals(strSubstring) && !ListScreenElement.DATA_TYPE_INTEGER1.equals(strSubstring)) {
                        if (!ListScreenElement.DATA_TYPE_DOUBLE.equals(strSubstring)) {
                            if (!ListScreenElement.DATA_TYPE_LONG.equals(strSubstring)) {
                                if (ListScreenElement.DATA_TYPE_FLOAT.equals(strSubstring)) {
                                    this.mType = Type.FLOAT;
                                    return;
                                }
                                throw new IllegalArgumentException("List: invalid item data type:" + strSubstring);
                            }
                            this.mType = Type.LONG;
                            return;
                        }
                        this.mType = Type.DOUBLE;
                        return;
                    }
                    this.mType = Type.INTEGER;
                    return;
                }
                this.mType = Type.BITMAP;
                return;
            }
            this.mType = Type.STRING;
        }

        public boolean validate(Object obj) {
            if (obj == null) {
                return true;
            }
            switch (AnonymousClass1.$SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[this.mType.ordinal()]) {
                case 1:
                    return obj instanceof String;
                case 2:
                    return obj instanceof Bitmap;
                case 3:
                    return obj instanceof Integer;
                case 4:
                    return obj instanceof Double;
                case 5:
                    return obj instanceof Long;
                case 6:
                    return obj instanceof Float;
                default:
                    return false;
            }
        }

        public static ArrayList<ColumnInfo> createColumnsInfo(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            ArrayList<ColumnInfo> arrayList = new ArrayList<>();
            for (String str2 : str.split(",")) {
                arrayList.add(new ColumnInfo(str2));
            }
            return arrayList;
        }
    }

    /* JADX INFO: renamed from: com.miui.maml.elements.ListScreenElement$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type;

        static {
            int[] iArr = new int[ColumnInfo.Type.values().length];
            $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type = iArr;
            try {
                iArr[ColumnInfo.Type.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ColumnInfo.Type.BITMAP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ColumnInfo.Type.INTEGER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ColumnInfo.Type.DOUBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ColumnInfo.Type.LONG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ColumnInfo.Type.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static class Column {
        public ListScreenElement mList;
        public String mName;
        public VariableArrayElement.VarObserver mObserver;
        public ScreenElementRoot mRoot;
        public String mTarget;
        public VariableArrayElement mTargetElement;

        public Column(Element element, ScreenElementRoot screenElementRoot, ListScreenElement listScreenElement) {
            this.mRoot = screenElementRoot;
            this.mList = listScreenElement;
            if (element != null) {
                load(element);
            }
        }

        private void load(Element element) {
            this.mName = element.getAttribute("name");
            this.mTarget = element.getAttribute(TypedValues.AttributesType.S_TARGET);
            this.mObserver = new VariableArrayElement.VarObserver() { // from class: com.miui.maml.elements.ListScreenElement.Column.1
                @Override // com.miui.maml.elements.VariableArrayElement.VarObserver
                public void onDataChange(Object[] objArr) {
                    Column.this.mList.addColumn(Column.this.mName, objArr);
                }
            };
        }

        public void init() {
            if (this.mTargetElement == null) {
                ScreenElement screenElementFindElement = this.mRoot.findElement(this.mTarget);
                if (screenElementFindElement instanceof VariableArrayElement) {
                    this.mTargetElement = (VariableArrayElement) screenElementFindElement;
                } else {
                    Log.e(ListScreenElement.LOG_TAG, "can't find VarArray:" + this.mTarget);
                    return;
                }
            }
            this.mTargetElement.registerVarObserver(this.mObserver, true);
        }

        public void finish() {
            VariableArrayElement variableArrayElement = this.mTargetElement;
            if (variableArrayElement != null) {
                variableArrayElement.registerVarObserver(this.mObserver, false);
            }
        }
    }

    public static class ListData {
        public ArrayList<Column> mColumns = new ArrayList<>();
        public ListScreenElement mList;
        public ScreenElementRoot mRoot;

        public ListData(Element element, ScreenElementRoot screenElementRoot, ListScreenElement listScreenElement) {
            this.mRoot = screenElementRoot;
            this.mList = listScreenElement;
            if (element != null) {
                load(element);
            }
        }

        private void load(Element element) {
            Utils.traverseXmlElementChildren(element, "Column", new Utils.XmlTraverseListener() { // from class: com.miui.maml.elements.ListScreenElement.ListData.1
                @Override // com.miui.maml.util.Utils.XmlTraverseListener
                public void onChild(Element element2) {
                    ListData.this.mColumns.add(new Column(element2, ListData.this.mRoot, ListData.this.mList));
                }
            });
        }

        public void init() {
            for (Column column : this.mColumns) {
                if (column != null) {
                    column.init();
                }
            }
        }

        public void finish() {
            for (Column column : this.mColumns) {
                if (column != null) {
                    column.finish();
                }
            }
        }
    }

    public ListScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mLock = new Object();
        this.mDataList = new ArrayList<>();
        this.mIndexOrder = new ArrayList<>();
        this.mReuseIndex = new ArrayList<>();
        this.mCurrentIndex = -1;
        if (this.mItem == null) {
            Log.e(LOG_TAG, "no item");
            throw new IllegalArgumentException("List: no item");
        }
        setClip(true);
        this.mMaxHeight = Expression.build(getVariables(), element.getAttribute("maxHeight"));
        this.mClearOnFinish = Boolean.parseBoolean(element.getAttribute("clearOnFinish"));
        String attribute = element.getAttribute("data");
        if (TextUtils.isEmpty(attribute)) {
            Log.e(LOG_TAG, "no data");
            throw new IllegalArgumentException("List: no data");
        }
        ArrayList<ColumnInfo> arrayListCreateColumnsInfo = ColumnInfo.createColumnsInfo(attribute);
        this.mColumnsInfo = arrayListCreateColumnsInfo;
        if (arrayListCreateColumnsInfo == null) {
            Log.e(LOG_TAG, "invalid item data");
            throw new IllegalArgumentException("List: invalid item data");
        }
        this.mIndexedVariables = new IndexedVariable[arrayListCreateColumnsInfo.size()];
        Element child = Utils.getChild(element, AttrDataBinders.TAG);
        if (child == null) {
            Log.e(LOG_TAG, "no attr data binder");
            throw new IllegalArgumentException("List: no attr data binder");
        }
        this.mAttrDataBinders = new AttrDataBinders(child, this.mRoot.getContext().mContextVariables);
        Element child2 = Utils.getChild(element, "Data");
        if (child2 != null) {
            this.mListData = new ListData(child2, this.mRoot, this);
        }
        ScreenElement screenElementFindElement = findElement("scrollbar");
        if (screenElementFindElement instanceof AnimatedScreenElement) {
            AnimatedScreenElement animatedScreenElement = (AnimatedScreenElement) screenElementFindElement;
            this.mScrollBar = animatedScreenElement;
            animatedScreenElement.mAlignV = ScreenElement.AlignV.TOP;
            removeElement(screenElementFindElement);
            addElement(this.mScrollBar);
        }
        this.mSelectedIdVar = new IndexedVariable(this.mName + ".selectedId", this.mRoot.getContext().mVariables, true);
    }

    public ArrayList<ColumnInfo> getColumnsInfo() {
        return this.mColumnsInfo;
    }

    public void removeAllItems() {
        synchronized (this.mLock) {
            this.mInnerGroup.removeAllElements();
            this.mInnerGroup.setY(0.0d);
            this.mDataList.clear();
            this.mIndexOrder.clear();
            this.mReuseIndex.clear();
            this.mCurrentIndex = -1;
            this.mItemCount = 0;
            setActualHeight(descale(getHeight()));
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        if (this.mClearOnFinish) {
            removeAllItems();
        }
        ListData listData = this.mListData;
        if (listData != null) {
            listData.finish();
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        resetInner();
        this.mInnerGroup.setY(0.0d);
        setActualHeight(descale(getHeight()));
        this.mSelectedId = -1;
        this.mSelectedIdVar.set(-1);
        setVariables();
        ListData listData = this.mListData;
        if (listData != null) {
            listData.init();
        }
    }

    public void addItem(Object... objArr) {
        if (objArr == null) {
            return;
        }
        if (objArr.length != this.mColumnsInfo.size()) {
            Log.e(LOG_TAG, "invalid item data count");
            return;
        }
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            if (!this.mColumnsInfo.get(i).validate(objArr[i])) {
                Log.e(LOG_TAG, "invalid item data type: " + objArr[i]);
                return;
            }
        }
        synchronized (this.mLock) {
            this.mDataList.add(new DataIndexMap((Object[]) objArr.clone()));
            this.mCurrentIndex++;
            this.mItemCount++;
            setActualHeight(descale(getHeight()));
            int iMax = (int) (Math.max(super.getHeight(), scale(evaluate(this.mMaxHeight))) / this.mItem.getHeight());
            this.mVisibleItemCount = iMax;
            this.mCachedItemCount = iMax * 2;
            int size = this.mInnerGroup.getElements().size();
            if (size < this.mCachedItemCount) {
                ListItemElement listItemElement = new ListItemElement(this.mItem.mNode, this.mItem.mRoot);
                this.mInnerGroup.addElement(listItemElement);
                this.mDataList.get(this.mCurrentIndex).mElementIndex = size;
                this.mSelectedId = this.mCurrentIndex;
                listItemElement.init();
                this.mSelectedId = -1;
                bindDataLocked(listItemElement, size, this.mCurrentIndex);
                this.mIndexOrder.add(Integer.valueOf(this.mCurrentIndex));
            }
        }
        requestUpdate();
    }

    /* JADX WARN: Code duplicated, block: B:56:0x0143  */
    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public boolean onTouch(MotionEvent motionEvent) {
        boolean z;
        if (!isVisible()) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                this.mPressed = false;
                if (this.mMoving) {
                    Log.i(LOG_TAG, "unlock touch up");
                    performAction("up");
                    onActionUp();
                    if (this.mSpeed < 400.0d) {
                        resetInner();
                    } else {
                        startAnimation();
                    }
                    z = true;
                } else {
                    z = false;
                }
            } else if (actionMasked != 2) {
                if (actionMasked == 3) {
                    this.mPressed = false;
                    if (this.mMoving) {
                        performAction(ToDoStat.CANCEL);
                        resetInner();
                        this.mStartAnimTime = -1L;
                        z = true;
                    }
                }
                z = false;
            } else {
                if (this.mMoving) {
                    this.mCurrentTime = SystemClock.elapsedRealtime();
                    double d = y;
                    double d2 = d - this.mTouchStartY;
                    this.mOffsetY = d2;
                    this.mOffsetX = ((double) x) - this.mTouchStartX;
                    if (!this.mIsScroll && !this.mIsChildScroll) {
                        double dAbs = Math.abs(d2);
                        double dAbs2 = Math.abs(this.mOffsetX);
                        if (dAbs > 5.0d && !this.mIsChildScroll && dAbs >= dAbs2) {
                            this.mIsScroll = true;
                        } else if (dAbs2 > 5.0d && !this.mIsScroll && dAbs < dAbs2) {
                            this.mIsChildScroll = true;
                        }
                    }
                    this.mIsUpDirection = this.mOffsetY < 0.0d || this.mIsChildScroll;
                    if (this.mIsScroll) {
                        motionEvent.setAction(3);
                        performAction("move");
                        onActionMove(x, y);
                        this.mSpeed = (Math.abs(this.mOffsetY) / (this.mCurrentTime - this.mLastTime)) * 1000.0d;
                        moveTo(((double) this.mInnerGroup.getY()) + this.mOffsetY);
                        this.mTouchStartY = d;
                        this.mLastTime = this.mCurrentTime;
                    }
                    z = true;
                }
                z = false;
            }
        } else if (touched(x, y)) {
            this.mMoving = true;
            this.mPressed = true;
            performAction("down");
            onActionDown(x, y);
            this.mStartAnimTime = -1L;
            this.mSpeed = 0.0d;
            this.mLastTime = SystemClock.elapsedRealtime();
            int iFloor = (int) Math.floor((y - this.mInnerGroup.getAbsoluteTop()) / this.mItem.getHeight());
            this.mSelectedId = iFloor;
            this.mSelectedIdVar.set(iFloor);
            setVariables();
            this.mTouchStartX = x;
            this.mTouchStartY = y;
            updateScorllBar();
            z = true;
        } else {
            z = false;
        }
        return super.onTouch(motionEvent) || (z && this.mInterceptTouch);
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public float getHeight() {
        return this.mMaxHeight == null ? super.getHeight() : Math.min(this.mItemCount * this.mItem.getHeight(), scale(evaluate(this.mMaxHeight)));
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.ScreenElement
    public ScreenElement findElement(String str) {
        int i;
        ScreenElement screenElementFindElement;
        synchronized (this.mLock) {
            int i2 = this.mSelectedId;
            return (i2 < 0 || i2 >= this.mItemCount || (i = this.mDataList.get(i2).mElementIndex) < 0 || (screenElementFindElement = ((ListItemElement) this.mInnerGroup.getElements().get(i)).findElement(str)) == null) ? super.findElement(str) : screenElementFindElement;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.maml.elements.ElementGroup
    public ScreenElement onCreateChild(Element element) {
        if (element.getTagName().equalsIgnoreCase(ListItemElement.TAG_NAME) && this.mInnerGroup == null) {
            this.mItem = new ListItemElement(element, this.mRoot);
            ElementGroup elementGroup = new ElementGroup((Element) null, this.mRoot);
            this.mInnerGroup = elementGroup;
            return elementGroup;
        }
        return super.onCreateChild(element);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0046  */
    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void doTick(long j) {
        super.doTick(j);
        long j2 = this.mStartAnimTime;
        if (j2 < 0 || this.mPressed) {
            return;
        }
        long j3 = j - j2;
        if (j2 != 0) {
            double d = this.mSpeed;
            double d2 = ACC;
            double d3 = j3;
            if (((d2 * d3) / 1000.0d) + d < 0.0d) {
                resetInner();
            } else {
                double d4 = ((d * d3) / 1000.0d) + ((((d2 * 0.5d) * d3) * d3) / 1000000.0d);
                this.mOffsetY = d4;
                double d5 = this.mStartAnimY;
                if (this.mIsUpDirection) {
                    d4 = -d4;
                }
                moveTo(d5 + d4);
            }
        } else {
            resetInner();
        }
        requestUpdate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addColumn(String str, Object[] objArr) {
        if (str == null || objArr == null) {
            return;
        }
        int size = this.mColumnsInfo.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (str.equals(this.mColumnsInfo.get(i2).mVarName)) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 < 0) {
            return;
        }
        synchronized (this.mLock) {
            int length = objArr.length;
            int size2 = this.mDataList.size();
            while (i < size2) {
                this.mDataList.get(i).setData(i2, i < length ? objArr[i] : null);
                if (this.mDataList.get(i).mElementIndex >= 0) {
                    getItem(i);
                }
                i++;
            }
            while (size2 < length) {
                Object[] objArr2 = new Object[this.mColumnsInfo.size()];
                objArr2[i2] = objArr[size2];
                addItem(objArr2);
                size2++;
            }
            clearEmptyRowLocked();
        }
        requestUpdate();
    }

    private void clearEmptyRowLocked() {
        for (int size = this.mDataList.size() - 1; size >= 0; size--) {
            for (Object obj : this.mDataList.get(size).mData) {
                if (obj != null) {
                    return;
                }
            }
            removeItemLocked(size);
        }
    }

    private void bindDataLocked(ListItemElement listItemElement, int i, int i2) {
        if (i2 < 0 || i2 >= this.mItemCount) {
            Log.e(LOG_TAG, "invalid item data");
            return;
        }
        Object[] objArr = this.mDataList.get(i2).mData;
        listItemElement.setDataIndex(i2);
        this.mDataList.get(i2).mElementIndex = i;
        this.mDataList.get(i2).mNeedRebind = false;
        listItemElement.setY(i2 * this.mItem.getHeight());
        int size = this.mColumnsInfo.size();
        ContextVariables contextVariables = getContext().mContextVariables;
        for (int i3 = 0; i3 < size; i3++) {
            contextVariables.setVar(this.mColumnsInfo.get(i3).mVarName, objArr[i3]);
        }
        AttrDataBinders attrDataBinders = this.mAttrDataBinders;
        if (attrDataBinders != null) {
            attrDataBinders.bind(listItemElement);
        }
    }

    private void checkVisibility() {
        synchronized (this.mLock) {
            ArrayList<ScreenElement> elements = this.mInnerGroup.getElements();
            for (int i = 0; i < elements.size(); i++) {
                ListItemElement listItemElement = (ListItemElement) elements.get(i);
                int dataIndex = listItemElement.getDataIndex();
                if (dataIndex >= 0 && dataIndex >= this.mTopIndex && dataIndex <= this.mBottomIndex) {
                    if (!listItemElement.isVisible()) {
                        listItemElement.show(true);
                    }
                } else if (listItemElement.isVisible()) {
                    listItemElement.show(false);
                }
            }
        }
    }

    private void setVariables() {
        int size = this.mColumnsInfo.size();
        for (int i = 0; i < size; i++) {
            ColumnInfo columnInfo = this.mColumnsInfo.get(i);
            if (columnInfo.mType != ColumnInfo.Type.BITMAP) {
                IndexedVariable[] indexedVariableArr = this.mIndexedVariables;
                if (indexedVariableArr[i] == null) {
                    indexedVariableArr[i] = new IndexedVariable(this.mName + "." + columnInfo.mVarName, this.mRoot.getContext().mVariables, columnInfo.mType.isNumber());
                }
                synchronized (this.mLock) {
                    IndexedVariable indexedVariable = this.mIndexedVariables[i];
                    int i2 = this.mSelectedId;
                    indexedVariable.set((i2 < 0 || i2 >= this.mDataList.size()) ? null : this.mDataList.get(this.mSelectedId).mData[i]);
                }
            }
        }
    }

    private void removeItemLocked(int i) {
        if (i < 0 || i >= this.mItemCount) {
            return;
        }
        this.mDataList.remove(i);
        this.mItemCount--;
        setActualHeight(descale(getHeight()));
        int size = this.mIndexOrder.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            ListItemElement listItemElement = (ListItemElement) this.mInnerGroup.getElements().get(this.mIndexOrder.get(i3).intValue());
            int dataIndex = listItemElement.getDataIndex();
            if (dataIndex == i) {
                listItemElement.setDataIndex(-1);
                listItemElement.setY(-1.7976931348623157E308d);
                listItemElement.show(false);
                i2 = i3;
            } else if (dataIndex > i) {
                int i4 = dataIndex - 1;
                listItemElement.setDataIndex(i4);
                listItemElement.setY(i4 * this.mItem.getHeight());
            }
        }
        if (size > 0) {
            int iIntValue = this.mIndexOrder.remove(i2).intValue();
            moveTo(this.mInnerGroup.getY());
            this.mReuseIndex.add(Integer.valueOf(iIntValue));
        }
        requestUpdate();
    }

    private int getUseableElementIndex() {
        int iIntValue;
        if (this.mReuseIndex.size() > 0) {
            iIntValue = this.mReuseIndex.remove(0).intValue();
        } else if (this.mIsUpDirection) {
            iIntValue = this.mIndexOrder.remove(0).intValue();
        } else {
            ArrayList<Integer> arrayList = this.mIndexOrder;
            iIntValue = arrayList.remove(arrayList.size() - 1).intValue();
        }
        if (this.mIsUpDirection) {
            this.mIndexOrder.add(Integer.valueOf(iIntValue));
            return iIntValue;
        }
        this.mIndexOrder.add(0, Integer.valueOf(iIntValue));
        return iIntValue;
    }

    private ListItemElement getItem(int i) {
        synchronized (this.mLock) {
            ListItemElement listItemElement = null;
            if (i >= 0) {
                if (i < this.mItemCount) {
                    int useableElementIndex = this.mDataList.get(i).mElementIndex;
                    if (useableElementIndex >= 0 && useableElementIndex < this.mInnerGroup.getElements().size()) {
                        listItemElement = (ListItemElement) this.mInnerGroup.getElements().get(useableElementIndex);
                    }
                    if (useableElementIndex < 0 || (listItemElement != null && listItemElement.getDataIndex() != i)) {
                        useableElementIndex = getUseableElementIndex();
                        listItemElement = (ListItemElement) this.mInnerGroup.getElements().get(useableElementIndex);
                        if (listItemElement != null && listItemElement.getDataIndex() < 0) {
                            listItemElement.reset();
                        }
                    }
                    if (listItemElement != null && (listItemElement.getDataIndex() != i || this.mDataList.get(i).mNeedRebind)) {
                        bindDataLocked(listItemElement, useableElementIndex, i);
                    }
                    return listItemElement;
                }
            }
            return null;
        }
    }

    private void updateScorllBar() {
        boolean z;
        if (this.mScrollBar == null || !this.mIsScroll) {
            return;
        }
        double height = this.mItemCount * this.mItem.getHeight();
        double height2 = getHeight();
        double d = height2 / height;
        if (d >= 1.0d) {
            d = 0.0d;
            z = false;
        } else {
            z = true;
        }
        double y = ((double) this.mInnerGroup.getY()) / (height2 - height);
        if (y > 1.0d) {
            y = 1.0d;
        }
        this.mScrollBar.setY((float) ((1.0d - d) * height2 * y));
        this.mScrollBar.setHeight((float) (height2 * d));
        if (this.mScrollBar.isVisible() != z) {
            this.mScrollBar.show(z);
        }
    }

    private void startAnimation() {
        this.mStartAnimTime = SystemClock.elapsedRealtime();
        this.mStartAnimY = this.mInnerGroup.getY();
    }

    private void moveTo(double d) {
        if (d < getHeight() - (this.mItemCount * this.mItem.getHeight())) {
            d = getHeight() - (this.mItemCount * this.mItem.getHeight());
            this.mStartAnimTime = 0L;
        }
        if (d > 0.0d) {
            this.mStartAnimTime = 0L;
            d = 0.0d;
        }
        this.mInnerGroup.setY((float) d);
        this.mTopIndex = Math.min((int) Math.floor((-d) / ((double) this.mItem.getHeight())), (this.mItemCount - ((int) (getHeight() / this.mItem.getHeight()))) - 1);
        this.mBottomIndex = Math.min(((int) (getHeight() / this.mItem.getHeight())) + this.mTopIndex, this.mItemCount - 1);
        for (int i = this.mTopIndex; i <= this.mBottomIndex; i++) {
            getItem(i);
        }
        checkVisibility();
        updateScorllBar();
    }

    private void resetInner() {
        AnimatedScreenElement animatedScreenElement = this.mScrollBar;
        if (animatedScreenElement != null) {
            animatedScreenElement.show(false);
        }
        this.mMoving = false;
        this.mIsScroll = false;
        this.mIsChildScroll = false;
        this.mStartAnimTime = -1L;
        this.mSpeed = 0.0d;
    }
}
