package com.miui.todo.data.mode;

import android.database.Cursor;
import android.os.Bundle;
import com.miui.common.stat.GlobalStat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.base.utils.ThreadPoolUtils;
import com.miui.notes.tool.SyncUtils;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.provider.TodoDao;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import com.miui.todo.data.utils.TimeUtils;
import com.miui.todo.data.utils.TodoDataUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CustomSortModeImpl implements CustomSortMode {
    private static final String TAG = "CustomSortModeImpl";
    private final boolean mIsTwoColumns;
    private final DataBrowseConfig mBrowseConfig = new DataBrowseConfig(2);
    private final List<TodoEntity> mUnFinishList = new LinkedList();
    private final List<TodoEntity> mFinishList = new LinkedList();
    private Runnable mPendingFinishTask = null;
    private Runnable mPendingAddNewDataTask = null;
    private final Object mLock = new Object();

    public CustomSortModeImpl(boolean z) {
        this.mIsTwoColumns = z;
        refreshData();
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public TodoEntity addItemToDb(TodoBaseEntity todoBaseEntity) {
        Logger.INSTANCE.d(TAG, "addItemToDb: ");
        final TodoEntity todoEntityQueryItemEntity = TodoDao.queryItemEntity(TodoDao.addItem(todoBaseEntity));
        this.mPendingAddNewDataTask = new Runnable() { // from class: com.miui.todo.data.mode.CustomSortModeImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$addItemToDb$0(todoEntityQueryItemEntity);
            }
        };
        return todoEntityQueryItemEntity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addItemToDb$0(TodoEntity todoEntity) {
        synchronized (this.mLock) {
            try {
                this.mUnFinishList.add(0, todoEntity);
                UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "error in mUnFinishList add " + e);
            }
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public TodoEntity addItem(TodoBaseEntity todoBaseEntity) {
        Logger.INSTANCE.d(TAG, "addItem: ");
        TodoEntity todoEntityQueryItemEntity = TodoDao.queryItemEntity(TodoDao.addItem(todoBaseEntity));
        if (todoEntityQueryItemEntity == null) {
            return null;
        }
        synchronized (this.mLock) {
            this.mUnFinishList.add(0, todoEntityQueryItemEntity);
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
        }
        return todoEntityQueryItemEntity;
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void deleteItem(TodoEntity todoEntity, int i) {
        if (i < 0) {
            return;
        }
        TodoDao.deleteItem(todoEntity.getId());
        synchronized (this.mLock) {
            if (todoEntity.getIsFinish() == 1) {
                this.mFinishList.remove(i);
            } else {
                this.mUnFinishList.remove(i);
            }
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void updateNotChangeFinish(TodoEntity todoEntity, int i) {
        TodoDao.updateItem(todoEntity.getId(), todoEntity);
        updateNotChangeFinishCache(todoEntity, i);
        synchronized (this.mLock) {
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void updateItem(final TodoEntity todoEntity, final int i) {
        boolean z;
        int i2;
        Logger.INSTANCE.d(TAG, "updateItem: ");
        final long id = todoEntity.getId();
        int isFinish = todoEntity.getIsFinish();
        if (i != -1) {
            ThreadPoolUtils.INSTANCE.getBackgroundWorker().execute(new Runnable() { // from class: com.miui.todo.data.mode.CustomSortModeImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TodoDao.updateItem(id, todoEntity);
                }
            });
            synchronized (this.mLock) {
                UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
            }
        }
        synchronized (this.mLock) {
            int i3 = 0;
            while (true) {
                if (i3 >= this.mFinishList.size()) {
                    z = false;
                    break;
                }
                try {
                    if (this.mFinishList.get(i3).getId() == id) {
                        i = i3;
                        z = true;
                        break;
                    }
                    i3++;
                } catch (Exception e) {
                    Logger.INSTANCE.w("TodoCustomMode", "inFinishGet i = " + i3 + "  size() = " + this.mFinishList.size() + StringUtils.SPACE + e);
                }
            }
        }
        if (!z) {
            int i4 = 0;
            while (true) {
                if (i4 >= this.mUnFinishList.size()) {
                    i2 = 1;
                    break;
                }
                try {
                    if (this.mUnFinishList.get(i4).getId() == id) {
                        i = i4;
                        i2 = 0;
                        z = true;
                        break;
                    }
                    i4++;
                } catch (Exception e2) {
                    Logger.INSTANCE.w("TodoCustomMode", "inUnFinishGet i = " + i4 + "  size() = " + this.mUnFinishList.size() + StringUtils.SPACE + e2);
                }
            }
        } else {
            i2 = 1;
            break;
        }
        if (z) {
            Logger.INSTANCE.d(TAG, "updateItem originType: " + i2 + " newType: " + isFinish);
            if (i2 == isFinish) {
                updateNotChangeFinishCache(todoEntity, i);
            } else if (i2 == 1) {
                synchronized (this.mLock) {
                    this.mFinishList.remove(i);
                    try {
                        this.mUnFinishList.add(0, todoEntity);
                        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
                    } catch (Exception e3) {
                        Logger.INSTANCE.w("TodoCustomMode", "waring updateItem mUnFinishList.add(0, newEntity) :" + e3);
                    }
                }
            } else {
                synchronized (this.mLock) {
                    try {
                        if (this.mUnFinishList.size() > 0) {
                            this.mUnFinishList.set(i, todoEntity);
                        }
                    } catch (Exception e4) {
                        Logger.INSTANCE.e(TAG, "error in updateNotChangeFinishCache " + e4);
                    }
                }
                this.mPendingFinishTask = new Runnable() { // from class: com.miui.todo.data.mode.CustomSortModeImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$updateItem$2(i, todoEntity);
                    }
                };
            }
            ThreadPoolUtils.INSTANCE.getBackgroundWorker().execute(new Runnable() { // from class: com.miui.todo.data.mode.CustomSortModeImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    TodoDao.updateItem(id, todoEntity);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateItem$2(int i, TodoEntity todoEntity) {
        synchronized (this.mLock) {
            try {
                this.mUnFinishList.remove(i);
                this.mFinishList.add(0, todoEntity);
                UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
            } catch (Exception e) {
                Logger.INSTANCE.w("TodoCustomMode", "waring updateItem in mPendingFinishTask " + e);
            }
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void hideItem(TodoEntity todoEntity, int i) {
        TodoDao.hideItem(todoEntity.getId(), true);
        synchronized (this.mLock) {
            if (todoEntity.getIsFinish() == 1) {
                this.mFinishList.remove(i);
            } else {
                this.mUnFinishList.remove(i);
                UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
            }
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void cancelHideItem(TodoEntity todoEntity, int i) {
        Logger.INSTANCE.d(TAG, "cancelHideItem: ");
        TodoDao.hideItem(todoEntity.getId(), false);
        synchronized (this.mLock) {
            if (todoEntity.getIsFinish() == 1) {
                this.mFinishList.add(i, todoEntity);
            } else {
                this.mUnFinishList.add(i, todoEntity);
                UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
            }
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void unFinishItem(TodoEntity todoEntity, int i) {
        Logger.INSTANCE.d(TAG, "unFinishItem: ");
        if (todoEntity.getIsFinish() == 0) {
            return;
        }
        TodoDao.updateItemFinishStatus(todoEntity.getId(), false);
        synchronized (this.mLock) {
            this.mFinishList.remove(i);
            todoEntity.setIsFinish(0);
            this.mUnFinishList.add(0, todoEntity);
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void setTimeItem(TodoEntity todoEntity, int i) {
        updateNotChangeFinish(todoEntity, i);
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void dragItem(TodoEntity todoEntity, TodoEntity todoEntity2, TodoEntity todoEntity3, int i) {
        dragItemDb(todoEntity, todoEntity2, todoEntity3);
        dragItemCache(todoEntity, todoEntity2, todoEntity3, i);
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void dragItemDb(TodoEntity todoEntity, TodoEntity todoEntity2, TodoEntity todoEntity3) {
        TodoDao.dragItem(todoEntity.getId(), todoEntity3 == null ? -1L : todoEntity3.getId(), todoEntity2 != null ? todoEntity2.getId() : -1L);
        Bundle bundle = new Bundle();
        bundle.putInt("push_type", 2);
        SyncUtils.requestSync(NoteApp.getInstance(), bundle, true);
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void dragItemCache(TodoEntity todoEntity, TodoEntity todoEntity2, TodoEntity todoEntity3, int i) {
        int iIndexOf;
        Logger.INSTANCE.d(TAG, "dragItemCache: ");
        synchronized (this.mLock) {
            this.mUnFinishList.remove(i);
            if (todoEntity2 != null) {
                iIndexOf = this.mUnFinishList.indexOf(todoEntity2) + 1;
            } else if (todoEntity3 == null) {
                return;
            } else {
                iIndexOf = this.mUnFinishList.indexOf(todoEntity3);
            }
            this.mUnFinishList.add(iIndexOf, todoEntity);
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void swapItemCache(int i, int i2) {
        synchronized (this.mLock) {
            Collections.swap(this.mUnFinishList, i, i2);
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void afterDragItem(int i, int i2, boolean z) {
        if (!z || i2 == -1) {
            return;
        }
        TodoEntity childItem = getChildItem(getDataGroupInfo(i2));
        int i3 = i2 - 1;
        int i4 = i2 + 1;
        dragItemDb(childItem, i3 >= 0 ? getChildItem(getDataGroupInfo(i3)) : null, i4 < getList(0).size() ? getChildItem(getDataGroupInfo(i4)) : null);
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public List<TodoEntity> getList(int i) {
        List<TodoEntity> list;
        synchronized (this.mLock) {
            try {
                list = i == 1 ? this.mFinishList : this.mUnFinishList;
            } catch (Throwable th) {
                throw th;
            }
        }
        return list;
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void setBatchTime(int[] iArr, int i, long j) {
        long j2;
        long todayRemindMillis;
        CustomSortModeImpl customSortModeImpl;
        int i2;
        TodoEntity todoEntity;
        synchronized (this.mLock) {
            int size = this.mUnFinishList.size();
            if (i == 1) {
                long dateEndMillis = TimeUtils.getDateEndMillis(j);
                todayRemindMillis = TimeUtils.getTodayRemindMillis(j);
                j2 = dateEndMillis;
            } else {
                if (i != 2) {
                    j = 0;
                }
                j2 = j;
                todayRemindMillis = j2;
            }
            String[] strArr = new String[iArr.length];
            int i3 = 0;
            while (i3 < iArr.length) {
                int i4 = iArr[i3];
                if (i4 < size) {
                    todoEntity = this.mUnFinishList.get(i4);
                    customSortModeImpl = this;
                    i2 = i;
                    customSortModeImpl.updateEntityTime(todoEntity, i2, j2, todayRemindMillis);
                    customSortModeImpl.mUnFinishList.set(i4, todoEntity);
                } else {
                    customSortModeImpl = this;
                    i2 = i;
                    int i5 = (i4 - size) - (customSortModeImpl.mBrowseConfig.isShowGroup() ? 1 : 0);
                    todoEntity = customSortModeImpl.mFinishList.get(i5);
                    customSortModeImpl.updateEntityTime(todoEntity, i2, j2, todayRemindMillis);
                    customSortModeImpl.mFinishList.set(i5, todoEntity);
                }
                strArr[i3] = todoEntity.getId() + "";
                i3++;
                this = customSortModeImpl;
                i = i2;
            }
            TodoDao.setBatchTime(strArr, i, todayRemindMillis, j2);
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void deleteBatch(int[] iArr) {
        TodoEntity todoEntity;
        synchronized (this.mLock) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = this.mUnFinishList.size();
            String[] strArr = new String[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                int i2 = iArr[i];
                if (this.mIsTwoColumns) {
                    int i3 = getDataGroupInfo(i2).mGroupType;
                    if (i3 == 0) {
                        todoEntity = this.mUnFinishList.get(i2);
                        arrayList.add(todoEntity);
                    } else if (i3 != 1) {
                        todoEntity = null;
                    } else {
                        todoEntity = this.mFinishList.get(i2);
                        arrayList2.add(todoEntity);
                    }
                } else if (i2 < size) {
                    todoEntity = this.mUnFinishList.get(i2);
                    arrayList.add(todoEntity);
                } else {
                    todoEntity = this.mFinishList.get((i2 - size) - (this.mBrowseConfig.isShowGroup() ? 1 : 0));
                    arrayList2.add(todoEntity);
                }
                if (todoEntity != null) {
                    strArr[i] = todoEntity.getId() + "";
                }
            }
            this.mUnFinishList.removeAll(arrayList);
            this.mFinishList.removeAll(arrayList2);
            TodoDao.localDeleteBatch(strArr);
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mUnFinishList.size());
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getChangeCountAfterFinish(int i) {
        return (getList(0).size() - i) + 1;
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getFinishItemCount() {
        Logger.INSTANCE.d(TAG, "getFinishItemCount YES.size: " + getList(1).size());
        return Math.min(getList(1).size(), NoteApp.getInstance().getTodoNumLimit());
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getTotalItemCount() {
        return getList(0).size() + getList(1).size();
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getItemCount() {
        int size;
        int todoNumLimit = NoteApp.getInstance().getTodoNumLimit();
        int i = this.mBrowseConfig.mShowListType;
        if (i == 0) {
            size = getList(0).size();
        } else if (i == 1) {
            size = getList(1).size();
        } else if (!this.mBrowseConfig.isShowGroup()) {
            if (!this.mBrowseConfig.pShowFinished) {
                size = getList(0).size();
            } else {
                size = getList(1).size() + getList(0).size();
            }
        } else if (!getList(1).isEmpty()) {
            if (this.mBrowseConfig.pGroupExpandArray[1]) {
                if (getList(0).size() + 1 == todoNumLimit) {
                    NoteApp.getInstance().addTodoNumLimit();
                }
                size = getList(0).size() + getList(1).size() + 1;
            } else {
                size = getList(0).size() + 1;
            }
        } else {
            size = getList(0).size();
        }
        return size > todoNumLimit ? todoNumLimit : size;
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getListRealItemCount() {
        Logger.INSTANCE.d(TAG, "getRealItemCount: mFinishList YES: " + getList(1).size() + " NO: " + getList(0).size());
        int todoNumLimit = NoteApp.getInstance().getTodoNumLimit();
        if (getList(0).size() < todoNumLimit) {
            return Math.min(Math.min(getList(0).size(), todoNumLimit) + Math.min(getList(1).size(), todoNumLimit), todoNumLimit - 1);
        }
        return Math.min(Math.min(getList(0).size(), todoNumLimit) + Math.min(getList(1).size(), todoNumLimit), todoNumLimit);
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getCategoryItemCount() {
        int todoNumLimit = NoteApp.getInstance().getTodoNumLimit();
        return Math.min(getList(0).size(), todoNumLimit) + Math.min(getList(1).size(), todoNumLimit);
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getRealFinishItemCount() {
        return getList(0).size();
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getRealType(long j) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mFinishList.size(); i++) {
                try {
                    if (this.mFinishList.get(i).getId() == j) {
                        return 1;
                    }
                } catch (Exception e) {
                    Logger.INSTANCE.w("TodoCustomMode", "inFinishGet i = " + i + "  size() = " + this.mFinishList.size() + StringUtils.SPACE + e);
                }
            }
            for (int i2 = 0; i2 < this.mUnFinishList.size(); i2++) {
                try {
                    if (this.mUnFinishList.get(i2).getId() == j) {
                        return 0;
                    }
                } catch (Exception e2) {
                    Logger.INSTANCE.w("TodoCustomMode", "inUnFinishGet i = " + i2 + "  size() = " + this.mUnFinishList.size() + StringUtils.SPACE + e2);
                }
            }
            return 0;
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void refreshData() {
        int count;
        this.mPendingFinishTask = null;
        this.mPendingAddNewDataTask = null;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            Cursor cursorQueryAllUnFinishItemReverse = TodoProviderAccessUtils.queryAllUnFinishItemReverse();
            if (cursorQueryAllUnFinishItemReverse != null) {
                count = cursorQueryAllUnFinishItemReverse.getCount();
                GlobalStat.Status.unfinishedTodoCount = count;
                cursorQueryAllUnFinishItemReverse.moveToPosition(-1);
                while (cursorQueryAllUnFinishItemReverse.moveToNext()) {
                    TodoEntity todoEntity = TodoDataUtils.getTodoEntity(cursorQueryAllUnFinishItemReverse);
                    if (todoEntity.getLocalStatus() != 3 && todoEntity.getLocalStatus() != 4) {
                        arrayList.add(todoEntity);
                    }
                }
                cursorQueryAllUnFinishItemReverse.close();
            } else {
                count = 0;
            }
            Cursor cursorQueryAllFinishItemReverse = TodoProviderAccessUtils.queryAllFinishItemReverse();
            if (cursorQueryAllFinishItemReverse != null) {
                count += cursorQueryAllFinishItemReverse.getCount();
                GlobalStat.Status.finishedTodoCount = cursorQueryAllFinishItemReverse.getCount();
                cursorQueryAllFinishItemReverse.moveToPosition(-1);
                while (cursorQueryAllFinishItemReverse.moveToNext()) {
                    TodoEntity todoEntity2 = TodoDataUtils.getTodoEntity(cursorQueryAllFinishItemReverse);
                    if (todoEntity2.getLocalStatus() != 3 && todoEntity2.getLocalStatus() != 4) {
                        arrayList2.add(todoEntity2);
                    }
                }
                cursorQueryAllFinishItemReverse.close();
            }
            GlobalStat.Status.todoCount = count;
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "error in refreshData" + e);
        }
        synchronized (this.mLock) {
            updateList(this.mUnFinishList, arrayList);
            updateList(this.mFinishList, arrayList2);
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void setShowFinished(boolean z) {
        this.mBrowseConfig.setShowFinishedData(z);
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public DataBrowseConfig getBrowseConfig() {
        return this.mBrowseConfig;
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public TodoEntity getChildItem(int i) {
        return getChildItem(getDataGroupInfo(i));
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public TodoEntity getChildItem(DataGroupInfo dataGroupInfo) {
        int size;
        if (dataGroupInfo.mGroupType != -1 && (size = getList(dataGroupInfo.mGroupType).size()) > 0 && dataGroupInfo.mSubPosition < size && dataGroupInfo.mSubPosition >= 0) {
            try {
                return getList(dataGroupInfo.mGroupType).get(dataGroupInfo.mSubPosition);
            } catch (Exception e) {
                Logger.INSTANCE.w("TodoCustomMode", "getChildItem dataGroupInfo.mGroupType + " + dataGroupInfo.mGroupType + " i = " + dataGroupInfo.mSubPosition + "  size() = " + size + StringUtils.SPACE + e);
            }
        }
        return null;
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public DataGroupInfo getDataGroupInfo(int i) {
        DataGroupInfo dataGroupInfo = new DataGroupInfo();
        int i2 = this.mBrowseConfig.mShowListType;
        if (i2 == 0) {
            dataGroupInfo.mGroupType = 0;
            dataGroupInfo.mSubPosition = i;
            return dataGroupInfo;
        }
        if (i2 == 1) {
            dataGroupInfo.mGroupType = 1;
            dataGroupInfo.mSubPosition = i;
            return dataGroupInfo;
        }
        if (i < getList(0).size()) {
            dataGroupInfo.mGroupType = 0;
            dataGroupInfo.mSubPosition = i;
            return dataGroupInfo;
        }
        if (this.mBrowseConfig.isShowGroup()) {
            if (i == getList(0).size()) {
                dataGroupInfo.mGroupType = 1;
                dataGroupInfo.mSubPosition = -1;
                return dataGroupInfo;
            }
            if (!getList(1).isEmpty()) {
                dataGroupInfo.mGroupType = 1;
                dataGroupInfo.mSubPosition = (i - getList(0).size()) - 1;
                return dataGroupInfo;
            }
        } else if (!getList(1).isEmpty()) {
            dataGroupInfo.mGroupType = 1;
            dataGroupInfo.mSubPosition = i - getList(0).size();
        }
        return dataGroupInfo;
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getItemGroupType(int i) {
        if (!this.mBrowseConfig.isShowGroup()) {
            return -1;
        }
        synchronized (this.mLock) {
            return i == this.mUnFinishList.size() ? 1 : -1;
        }
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public int getTotalIndexByItem(TodoEntity todoEntity) {
        int i;
        List<TodoEntity> list;
        int size;
        int i2 = 0;
        if (todoEntity.getIsFinish() == 0) {
            list = getList(0);
            i = 0;
        } else {
            List<TodoEntity> list2 = getList(1);
            if (this.mIsTwoColumns) {
                i = 0;
            } else {
                if (this.mBrowseConfig.isShowGroup()) {
                    size = getList(0).size() + 1;
                } else {
                    size = getList(0).size();
                }
                i = size;
            }
            list = list2;
        }
        while (i2 < list.size()) {
            try {
                if (list.get(i2).getId() == todoEntity.getId()) {
                    return i2 + i;
                }
                i2++;
            } catch (Exception e) {
                Logger.INSTANCE.w("TodoCustomMode", "getTotalIndexByItem todoList i = " + i2 + StringUtils.SPACE + e);
            }
        }
        i2 = -1;
        return i2 + i;
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public boolean changeGroupExpand(int i, boolean z, boolean z2) {
        if (z) {
            this.mBrowseConfig.pGroupExpandArray[i] = !this.mBrowseConfig.pGroupExpandArray[i];
        } else {
            this.mBrowseConfig.pGroupExpandArray[i] = z2;
        }
        return this.mBrowseConfig.pGroupExpandArray[i];
    }

    @Override // com.miui.todo.data.mode.IDataSource
    public void executePendingTask() {
        Runnable runnable = this.mPendingFinishTask;
        if (runnable != null) {
            runnable.run();
            this.mPendingFinishTask = null;
        }
        Runnable runnable2 = this.mPendingAddNewDataTask;
        if (runnable2 != null) {
            runnable2.run();
            this.mPendingAddNewDataTask = null;
        }
    }

    private void updateNotChangeFinishCache(TodoEntity todoEntity, int i) {
        synchronized (this.mLock) {
            try {
                if (todoEntity.getIsFinish() == 1) {
                    if (!this.mFinishList.isEmpty()) {
                        this.mFinishList.set(i, todoEntity);
                    }
                } else if (!this.mUnFinishList.isEmpty()) {
                    this.mUnFinishList.set(i, todoEntity);
                }
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "error in updateNotChangeFinishCache " + e);
            }
        }
    }

    private void refreshUnFinishList(List<TodoEntity> list) {
        SortUtils.refreshAndSortFromBase(this.mUnFinishList, list, new SortUtils.customSortIdComparator());
    }

    private void refreshFinishList(List<TodoEntity> list) {
        SortUtils.refreshAndSortFromBase(this.mFinishList, list, new SortUtils.markFinishTimeComparator());
    }

    private void updateEntityTime(TodoEntity todoEntity, int i, long j, long j2) {
        todoEntity.setRemindType(i);
        todoEntity.setRemindTime(j2);
        todoEntity.setFirstRemindTime(j2);
        todoEntity.setExpireTime(j);
    }

    public static void updateList(List<TodoEntity> list, List<TodoEntity> list2) {
        if (list != null) {
            list.clear();
        } else {
            list = new LinkedList<>();
        }
        try {
            list.addAll(list2);
        } catch (Exception e) {
            Logger.INSTANCE.e("SortUtils ", " error in " + e);
        }
    }
}
