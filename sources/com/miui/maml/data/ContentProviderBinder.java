package com.miui.maml.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteFullException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.elements.ImageScreenElement;
import com.miui.maml.elements.ListScreenElement;
import com.miui.maml.elements.ScreenElement;
import com.miui.maml.util.SystemProperties;
import com.miui.maml.util.TextFormatter;
import com.miui.maml.util.Utils;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class ContentProviderBinder extends VariableBinder {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "ContentProviderBinder";
    private static final int QUERY_TOKEN = 100;
    public static final String TAG_NAME = "ContentProviderBinder";
    private volatile boolean mAllowReg;
    protected String[] mArgs;
    private boolean mAwareChangeWhilePause;
    private ChangeObserver mChangeObserver;
    protected String[] mColumns;
    protected String mCountName;
    private IndexedVariable mCountVar;
    private Handler mHandler;
    private long mLastQueryTime;
    private Uri mLastUri;
    private List mList;
    private final Object mLock;
    private boolean mNeedsRequery;
    protected String mOrder;
    private QueryHandler mQueryHandler;
    private boolean mSystemBootCompleted;
    private int mUpdateInterval;
    private Runnable mUpdater;
    protected TextFormatter mUriFormatter;
    protected TextFormatter mWhereFormatter;

    public interface QueryCompleteListener {
        void onQueryCompleted(String str);
    }

    public static class Builder {
        private ContentProviderBinder mBinder;

        protected Builder(ContentProviderBinder contentProviderBinder) {
            this.mBinder = contentProviderBinder;
        }

        public Builder setName(String str) {
            this.mBinder.mName = str;
            return this;
        }

        public Builder setWhere(String str) {
            this.mBinder.mWhereFormatter = new TextFormatter(this.mBinder.getVariables(), str);
            return this;
        }

        public Builder setWhere(String str, String str2) {
            this.mBinder.mWhereFormatter = new TextFormatter(this.mBinder.getVariables(), str, str2);
            return this;
        }

        public Builder setColumns(String[] strArr) {
            this.mBinder.mColumns = strArr;
            return this;
        }

        public Builder setArgs(String[] strArr) {
            this.mBinder.mArgs = strArr;
            return this;
        }

        public Builder setOrder(String str) {
            this.mBinder.mOrder = str;
            return this;
        }

        public Builder setCountName(String str) {
            this.mBinder.mCountName = str;
            this.mBinder.createCountVar();
            return this;
        }

        public void addVariable(String str, String str2, String str3, int i, Variables variables) {
            Variable variable = new Variable(str, str2, variables);
            variable.mColumn = str3;
            variable.mRow = i;
            this.mBinder.addVariable(variable);
        }
    }

    private static class Variable extends VariableBinder.Variable {
        public static final int BLOB_BITMAP = 1001;
        public boolean mBlocked;
        public String mColumn;
        private ImageScreenElement mImageVar;
        private boolean mNoImageElement;
        public int mRow;

        public Variable(String str, String str2, Variables variables) {
            super(str, str2, variables);
        }

        public Variable(Element element, Variables variables) {
            super(element, variables);
            this.mColumn = element.getAttribute("column");
            this.mRow = Utils.getAttrAsInt(element, "row", 0);
        }

        @Override // com.miui.maml.data.VariableBinder.TypedValue
        protected int parseType(String str) {
            int type = super.parseType(str);
            if ("blob.bitmap".equalsIgnoreCase(this.mTypeStr)) {
                return 1001;
            }
            this.mNoImageElement = true;
            return type;
        }

        public void setNull(ScreenElementRoot screenElementRoot) {
            if (getImageElement(screenElementRoot) != null) {
                getImageElement(screenElementRoot).setBitmap(null);
            } else {
                set((Object) null);
            }
        }

        public ImageScreenElement getImageElement(ScreenElementRoot screenElementRoot) {
            if (this.mImageVar == null && !this.mNoImageElement) {
                ImageScreenElement imageScreenElement = (ImageScreenElement) screenElementRoot.findElement(this.mName);
                this.mImageVar = imageScreenElement;
                this.mNoImageElement = imageScreenElement == null;
            }
            return this.mImageVar;
        }
    }

    private static class List {
        private ListScreenElement mList;
        private int mMaxCount;
        private String mName;
        private ScreenElementRoot mRoot;

        public List(Element element, ScreenElementRoot screenElementRoot) {
            this.mName = element.getAttribute("name");
            this.mMaxCount = Utils.getAttrAsInt(element, "maxCount", Integer.MAX_VALUE);
            this.mRoot = screenElementRoot;
        }

        public void fill(Cursor cursor) {
            if (cursor == null) {
                return;
            }
            if (this.mList == null) {
                ScreenElement screenElementFindElement = this.mRoot.findElement(this.mName);
                if (screenElementFindElement instanceof ListScreenElement) {
                    this.mList = (ListScreenElement) screenElementFindElement;
                }
                if (this.mList == null) {
                    Log.e("ContentProviderBinder", "fail to find list: " + this.mName);
                    return;
                }
            }
            this.mList.removeAllItems();
            ArrayList<ListScreenElement.ColumnInfo> columnsInfo = this.mList.getColumnsInfo();
            int size = columnsInfo.size();
            int[] iArr = new int[size];
            Object[] objArr = new Object[size];
            for (int i = 0; i < size; i++) {
                try {
                    iArr[i] = cursor.getColumnIndexOrThrow(columnsInfo.get(i).mVarName);
                } catch (IllegalArgumentException e) {
                    Log.e("ContentProviderBinder", "illegal column:" + columnsInfo.get(i).mVarName + StringUtils.SPACE + e.toString());
                    return;
                }
            }
            cursor.moveToFirst();
            int count = cursor.getCount();
            int i2 = this.mMaxCount;
            if (count > i2) {
                count = i2;
            }
            for (int i3 = 0; i3 < count; i3++) {
                for (int i4 = 0; i4 < size; i4++) {
                    objArr[i4] = null;
                    ListScreenElement.ColumnInfo columnInfo = columnsInfo.get(i4);
                    int i5 = iArr[i4];
                    if (!cursor.isNull(i5)) {
                        int i6 = AnonymousClass2.$SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[columnInfo.mType.ordinal()];
                        if (i6 == 5) {
                            objArr[i4] = cursor.getString(i5);
                        } else if (i6 == 6) {
                            byte[] blob = cursor.getBlob(i5);
                            if (blob != null) {
                                objArr[i4] = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                            }
                        } else {
                            int i7 = AnonymousClass2.$SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[columnInfo.mType.ordinal()];
                            if (i7 == 1) {
                                objArr[i4] = Double.valueOf(cursor.getDouble(i5));
                            } else if (i7 == 2) {
                                objArr[i4] = Float.valueOf(cursor.getFloat(i5));
                            } else if (i7 == 3) {
                                objArr[i4] = Integer.valueOf(cursor.getInt(i5));
                            } else if (i7 == 4) {
                                objArr[i4] = Long.valueOf(cursor.getLong(i5));
                            }
                        }
                    }
                }
                this.mList.addItem(objArr);
                cursor.moveToNext();
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.maml.data.ContentProviderBinder$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type;

        static {
            int[] iArr = new int[ListScreenElement.ColumnInfo.Type.values().length];
            $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type = iArr;
            try {
                iArr[ListScreenElement.ColumnInfo.Type.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ListScreenElement.ColumnInfo.Type.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ListScreenElement.ColumnInfo.Type.INTEGER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ListScreenElement.ColumnInfo.Type.LONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ListScreenElement.ColumnInfo.Type.STRING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ListScreenElement$ColumnInfo$Type[ListScreenElement.ColumnInfo.Type.BITMAP.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public void createCountVar() {
        if (this.mCountName == null) {
            this.mCountVar = null;
        } else {
            this.mCountVar = new IndexedVariable(this.mCountName, getContext().mVariables, true);
        }
    }

    public ContentProviderBinder(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mLock = new Object();
        this.mAllowReg = true;
        this.mChangeObserver = new ChangeObserver();
        this.mUpdateInterval = -1;
        this.mNeedsRequery = true;
        this.mHandler = screenElementRoot.getContext().getHandler();
        this.mQueryHandler = new QueryHandler(getContext().mContext);
        if (element != null) {
            load(element);
        }
    }

    public ContentProviderBinder(ScreenElementRoot screenElementRoot) {
        this(null, screenElementRoot);
    }

    @Override // com.miui.maml.data.VariableBinder
    public void finish() {
        synchronized (this.mLock) {
            this.mAllowReg = false;
        }
        this.mLastUri = null;
        registerObserver(null, false);
        this.mHandler.removeCallbacks(this.mUpdater);
        setBlockedColumns(null);
        super.finish();
    }

    @Override // com.miui.maml.data.VariableBinder
    public void pause() {
        super.pause();
        this.mHandler.removeCallbacks(this.mUpdater);
    }

    @Override // com.miui.maml.data.VariableBinder
    public void resume() {
        super.resume();
        if (this.mNeedsRequery) {
            startQuery();
        } else {
            checkUpdate();
        }
    }

    @Override // com.miui.maml.data.VariableBinder
    public void refresh() {
        super.refresh();
        startQuery();
    }

    private void load(Element element) {
        Variables variables = getVariables();
        this.mUriFormatter = new TextFormatter(variables, element.getAttribute(CallMethod.ARG_URI), element.getAttribute("uriFormat"), element.getAttribute("uriParas"), Expression.build(variables, element.getAttribute("uriExp")), Expression.build(variables, element.getAttribute("uriFormatExp")));
        String attribute = element.getAttribute("columns");
        this.mColumns = TextUtils.isEmpty(attribute) ? null : attribute.split(",");
        this.mWhereFormatter = new TextFormatter(variables, element.getAttribute("where"), element.getAttribute("whereFormat"), element.getAttribute("whereParas"), Expression.build(variables, element.getAttribute("whereExp")), Expression.build(variables, element.getAttribute("whereFormatExp")));
        String attribute2 = element.getAttribute("args");
        this.mArgs = TextUtils.isEmpty(attribute2) ? null : attribute2.split(",");
        String attribute3 = element.getAttribute(HtmlParser.OrderElement.TAG);
        if (TextUtils.isEmpty(attribute3)) {
            attribute3 = null;
        }
        this.mOrder = attribute3;
        String attribute4 = element.getAttribute("countName");
        String str = TextUtils.isEmpty(attribute4) ? null : attribute4;
        this.mCountName = str;
        if (str != null) {
            this.mCountVar = new IndexedVariable(this.mCountName, variables, true);
        }
        int attrAsInt = Utils.getAttrAsInt(element, "updateInterval", -1);
        this.mUpdateInterval = attrAsInt;
        if (attrAsInt > 0) {
            this.mUpdater = new Runnable() { // from class: com.miui.maml.data.ContentProviderBinder.1
                @Override // java.lang.Runnable
                public void run() {
                    ContentProviderBinder.this.checkUpdate();
                }
            };
        }
        loadVariables(element);
        Element child = Utils.getChild(element, ListScreenElement.TAG_NAME);
        if (child != null) {
            try {
                this.mList = new List(child, this.mRoot);
            } catch (IllegalArgumentException unused) {
                Log.e("ContentProviderBinder", "invalid List");
            }
        }
        this.mAwareChangeWhilePause = Boolean.parseBoolean(element.getAttribute("vigilant"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.maml.data.VariableBinder
    public Variable onLoadVariable(Element element) {
        return new Variable(element, getContext().mVariables);
    }

    @Override // com.miui.maml.data.VariableBinder
    public void startQuery() {
        if (this.mFinished) {
            return;
        }
        String uriText = getUriText();
        if (uriText == null) {
            Log.e("ContentProviderBinder", "start query: uri null");
            return;
        }
        if (!this.mSystemBootCompleted) {
            boolean zEquals = "1".equals(SystemProperties.get("sys.boot_completed"));
            this.mSystemBootCompleted = zEquals;
            if (!zEquals) {
                return;
            }
        }
        this.mNeedsRequery = false;
        this.mQueryHandler.cancelOperation(100);
        Uri uri = Uri.parse(uriText);
        if (uri == null) {
            return;
        }
        if (this.mUpdateInterval == -1 && !uri.equals(this.mLastUri)) {
            registerObserver(uri, true);
            this.mLastUri = uri;
        }
        this.mQueryHandler.startQuery(100, null, uri, this.mColumns, this.mWhereFormatter.getText(), this.mArgs, this.mOrder);
        this.mLastQueryTime = System.currentTimeMillis();
        checkUpdate();
    }

    public final String getUriText() {
        return this.mUriFormatter.getText();
    }

    public final void setBlockedColumns(String[] strArr) {
        HashSet hashSet;
        if (strArr != null) {
            hashSet = new HashSet();
            for (String str : strArr) {
                hashSet.add(str);
            }
        } else {
            hashSet = null;
        }
        Iterator<VariableBinder.Variable> it = this.mVariables.iterator();
        while (it.hasNext()) {
            Variable variable = (Variable) it.next();
            variable.mBlocked = hashSet != null ? hashSet.contains(variable.mColumn) : false;
        }
    }

    private final class QueryHandler extends AsyncQueryHandler {

        protected class CatchingWorkerHandler extends AsyncQueryHandler.WorkerHandler {
            public CatchingWorkerHandler(Looper looper) {
                super(looper);
            }

            @Override // com.miui.maml.data.AsyncQueryHandler.WorkerHandler, android.os.Handler
            public void handleMessage(Message message) {
                try {
                    super.handleMessage(message);
                } catch (SQLiteDatabaseCorruptException e) {
                    Log.w("ContentProviderBinder", "Exception on background worker thread", e);
                } catch (SQLiteDiskIOException e2) {
                    Log.w("ContentProviderBinder", "Exception on background worker thread", e2);
                } catch (SQLiteFullException e3) {
                    Log.w("ContentProviderBinder", "Exception on background worker thread", e3);
                }
            }
        }

        @Override // com.miui.maml.data.AsyncQueryHandler
        protected Handler createHandler(Looper looper) {
            return new CatchingWorkerHandler(looper);
        }

        public QueryHandler(Context context) {
            super(Looper.getMainLooper(), context.getContentResolver());
        }

        @Override // com.miui.maml.data.AsyncQueryHandler
        protected void onQueryComplete(int i, Object obj, Cursor cursor) {
            ContentProviderBinder.this.onQueryComplete(cursor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onQueryComplete(Cursor cursor) {
        if (!this.mFinished) {
            updateVariables(cursor);
        }
        if (cursor != null) {
            cursor.close();
        }
        onUpdateComplete();
    }

    private void registerObserver(Uri uri, boolean z) {
        ContentResolver contentResolver = getContext().mContext.getContentResolver();
        contentResolver.unregisterContentObserver(this.mChangeObserver);
        if (z && this.mAllowReg) {
            synchronized (this.mLock) {
                if (this.mAllowReg) {
                    try {
                        contentResolver.registerContentObserver(uri, true, this.mChangeObserver);
                    } catch (IllegalArgumentException e) {
                        Log.e("ContentProviderBinder", e.toString() + "  uri:" + uri);
                    } catch (SecurityException e2) {
                        Log.e("ContentProviderBinder", e2.toString() + "  uri:" + uri);
                    }
                }
            }
        }
    }

    private void updateVariables(Cursor cursor) {
        double d;
        int count = cursor == null ? 0 : cursor.getCount();
        IndexedVariable indexedVariable = this.mCountVar;
        if (indexedVariable != null) {
            indexedVariable.set(count);
        }
        List list = this.mList;
        if (list != null) {
            list.fill(cursor);
        }
        if (cursor == null || count == 0) {
            Iterator<VariableBinder.Variable> it = this.mVariables.iterator();
            while (it.hasNext()) {
                ((Variable) it.next()).setNull(this.mRoot);
            }
            return;
        }
        for (VariableBinder.Variable variable : this.mVariables) {
            Variable variable2 = (Variable) variable;
            if (!variable2.mBlocked && cursor.moveToPosition(variable2.mRow)) {
                try {
                    int columnIndexOrThrow = cursor.getColumnIndexOrThrow(variable2.mColumn);
                    if (!cursor.isNull(columnIndexOrThrow)) {
                        int i = variable.mType;
                        if (i == 2) {
                            variable.set(cursor.getString(columnIndexOrThrow));
                        } else if (i == 1001 || i == 7) {
                            byte[] blob = cursor.getBlob(columnIndexOrThrow);
                            Bitmap bitmapDecodeByteArray = blob != null ? BitmapFactory.decodeByteArray(blob, 0, blob.length) : null;
                            if (variable.mType == 7) {
                                variable.set(bitmapDecodeByteArray);
                            } else {
                                ImageScreenElement imageElement = variable2.getImageElement(this.mRoot);
                                if (imageElement != null) {
                                    imageElement.setBitmap(bitmapDecodeByteArray);
                                }
                            }
                        } else if (i == 8) {
                            ArrayList arrayList = new ArrayList();
                            do {
                                arrayList.add(Double.valueOf(cursor.getDouble(columnIndexOrThrow)));
                            } while (cursor.moveToNext());
                            variable.set(arrayList.toArray());
                        } else if (i == 9) {
                            ArrayList arrayList2 = new ArrayList();
                            do {
                                arrayList2.add(cursor.getString(columnIndexOrThrow));
                            } while (cursor.moveToNext());
                            variable.set(arrayList2.toArray());
                        } else {
                            int i2 = variable.mType;
                            if (i2 == 3) {
                                d = cursor.getInt(columnIndexOrThrow);
                            } else if (i2 == 4) {
                                d = cursor.getLong(columnIndexOrThrow);
                            } else if (i2 == 5) {
                                d = cursor.getFloat(columnIndexOrThrow);
                            } else if (i2 != 6) {
                                Log.w("ContentProviderBinder", "invalide type" + variable.mTypeStr);
                                d = 0.0d;
                            } else {
                                d = cursor.getDouble(columnIndexOrThrow);
                            }
                            variable.set(d);
                        }
                    } else {
                        variable2.setNull(this.mRoot);
                    }
                } catch (NumberFormatException unused) {
                    Log.w("ContentProviderBinder", String.format("failed to get value from cursor", new Object[0]));
                } catch (IllegalArgumentException unused2) {
                    Log.e("ContentProviderBinder", "column does not exist: " + variable2.mColumn);
                } catch (Exception e) {
                    Log.e("ContentProviderBinder", e.toString());
                }
            }
        }
    }

    protected class ChangeObserver extends ContentObserver {
        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return true;
        }

        public ChangeObserver() {
            super(ContentProviderBinder.this.mHandler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            ContentProviderBinder.this.onContentChanged();
        }
    }

    public void onContentChanged() {
        Log.i("ContentProviderBinder", "ChangeObserver: content changed.");
        if (this.mFinished) {
            return;
        }
        if (this.mPaused && !this.mAwareChangeWhilePause) {
            this.mNeedsRequery = true;
        } else {
            startQuery();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkUpdate() {
        if (this.mUpdateInterval <= 0) {
            return;
        }
        this.mHandler.removeCallbacks(this.mUpdater);
        long jCurrentTimeMillis = System.currentTimeMillis() - this.mLastQueryTime;
        if (jCurrentTimeMillis >= this.mUpdateInterval * 1000) {
            startQuery();
            jCurrentTimeMillis = 0;
        }
        this.mHandler.postDelayed(this.mUpdater, ((long) (this.mUpdateInterval * 1000)) - jCurrentTimeMillis);
    }
}
