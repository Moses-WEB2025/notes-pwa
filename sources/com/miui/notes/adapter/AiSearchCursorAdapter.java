package com.miui.notes.adapter;

import android.database.Cursor;
import android.util.SparseIntArray;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AiSearchCursorAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u001a\u0010\u0004\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00020\u0006\u0018\u0001`\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\"\u0010\u0004\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00020\u0006\u0018\u0001`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/miui/notes/adapter/AiSearchCursorAdapter;", "Lcom/miui/notes/adapter/SearchCursorAdapter;", "cursor", "Landroid/database/Cursor;", "mAiQueryIdArr", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/database/Cursor;Ljava/util/ArrayList;)V", "refreshSparseArray", "", "moveToPosition", "", "position", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AiSearchCursorAdapter extends SearchCursorAdapter {
    private final ArrayList<Long> mAiQueryIdArr;

    public AiSearchCursorAdapter(Cursor cursor, ArrayList<Long> arrayList) {
        super(cursor, arrayList);
        this.mAiQueryIdArr = arrayList;
        refreshSparseArray();
    }

    @Override // com.miui.notes.adapter.SearchCursorAdapter
    public void refreshSparseArray() {
        if (this.mSparseIntArray != null) {
            this.mSparseIntArray.clear();
        } else {
            this.mSparseIntArray = new SparseIntArray();
        }
        HashMap map = new HashMap();
        HashSet hashSet = new HashSet();
        if (this.mCursor != null && this.mCursor.getCount() != 0) {
            this.mCursor.moveToPosition(-1);
            int i = 0;
            while (this.mCursor.moveToNext()) {
                long j = this.mCursor.getLong(0);
                map.put(Long.valueOf(j), Integer.valueOf(i));
                if (this.mCursor.getLong(11) > 0) {
                    hashSet.add(Long.valueOf(j));
                }
                i++;
            }
        }
        ArrayList<Long> arrayList = this.mAiQueryIdArr;
        if (arrayList != null) {
            int size = arrayList.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                Long l = this.mAiQueryIdArr.get(i3);
                Intrinsics.checkNotNullExpressionValue(l, "get(...)");
                long jLongValue = l.longValue();
                if (hashSet.contains(Long.valueOf(jLongValue))) {
                    SparseIntArray sparseIntArray = this.mSparseIntArray;
                    Object obj = map.get(Long.valueOf(jLongValue));
                    Intrinsics.checkNotNull(obj);
                    sparseIntArray.put(i2, ((Number) obj).intValue());
                    i2++;
                    map.remove(Long.valueOf(jLongValue));
                }
            }
            int size2 = this.mAiQueryIdArr.size();
            for (int i4 = 0; i4 < size2; i4++) {
                Long l2 = this.mAiQueryIdArr.get(i4);
                Intrinsics.checkNotNullExpressionValue(l2, "get(...)");
                long jLongValue2 = l2.longValue();
                if (map.containsKey(Long.valueOf(jLongValue2))) {
                    SparseIntArray sparseIntArray2 = this.mSparseIntArray;
                    Object obj2 = map.get(Long.valueOf(jLongValue2));
                    Intrinsics.checkNotNull(obj2);
                    sparseIntArray2.put(i2, ((Number) obj2).intValue());
                    i2++;
                    map.remove(Long.valueOf(jLongValue2));
                }
            }
        }
    }

    @Override // com.miui.notes.adapter.SearchCursorAdapter
    public boolean moveToPosition(int position) {
        return (this.mCursor == null || this.mCursor.isClosed() || this.mSparseIntArray == null || !this.mCursor.moveToPosition(this.mSparseIntArray.get(position))) ? false : true;
    }
}
