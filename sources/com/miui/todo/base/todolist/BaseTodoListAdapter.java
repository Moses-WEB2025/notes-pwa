package com.miui.todo.base.todolist;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.OnDragItemListener;
import com.miui.todo.base.OnDragItemStateListener;
import com.miui.todo.base.OnSwipeItemListener;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.CustomSortMode;
import com.miui.todo.data.mode.DataGroupInfo;
import com.miui.todo.data.mode.IDataSource;
import com.miui.todo.feature.todolist.SubTodoListListener;
import com.miui.todo.feature.todolist.TodoGroupItem;
import com.miui.todo.feature.todolist.TodoItemOperationListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseTodoListAdapter extends RecyclerView.Adapter<BaseTodoItemVh> implements OnDragItemListener {
    private static final String TAG = "BaseTodoListAdapter";
    public static final int TYPE_INVAILD_ITEM = -1;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_ITEM_GROUP = 1;
    public static final int TYPE_LIST_ITEM = 3;
    public static final int TYPE_PLACEHOLDER = 2;
    protected BaseListAdapterBindContext mBindContext;
    protected RecyclerView mBindRv;
    protected Context mContext;
    protected IDataSource mDataSource;
    protected boolean mIsInDrag;
    protected OnDragItemStateListener mOnBtnDragItemListener;
    protected View.OnDragListener mOnGroupDragItemListener;
    protected OnSwipeItemListener mOnSwipeItemListener;
    protected OnDragItemStateListener mOnTouchDragItemListener;
    protected View.OnClickListener mOtherViewClickListener;
    protected IBaseTodoListContract.Presenter mPresenter;
    protected SubTodoListListener mSubTodoListListener;
    protected TodoItemOperationListener mTodoItemOperationListener;
    private int isSingleColumn = -1;
    protected List<TodoEntity> mSearchData = new ArrayList();
    protected List<TodoGroupItem> mCustomSortGroupItems = new ArrayList();
    protected boolean mIsInSearchMode = false;
    protected boolean mIsFrozenDataChanged = false;
    protected int mDragStartPos = -1;
    protected int mDragCurrentPos = -1;
    protected int mDragEndPos = -1;
    protected int mBeforeDragItemIndex = -1;

    private boolean isEnableDragByItemType(int i) {
        return i == 0 || i == 3;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseTodoItemVh baseTodoItemVh, int i) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public abstract BaseTodoItemVh onCreateViewHolder(ViewGroup viewGroup, int i);

    public BaseTodoListAdapter(Context context, SubTodoListListener subTodoListListener) {
        this.mContext = context;
        this.mSubTodoListListener = subTodoListListener;
        setHasStableIds(true);
        initBindResource();
    }

    private void initBindResource() {
        this.mCustomSortGroupItems.add(new TodoGroupItem(0, -2L));
        this.mCustomSortGroupItems.add(new TodoGroupItem(1, -3L));
    }

    public void bindDataSource(IDataSource iDataSource) {
        this.mDataSource = iDataSource;
    }

    public IDataSource getDataSource() {
        return this.mDataSource;
    }

    public void insertData(int i, int i2) {
        notifyItemInserted(i);
        notifyDataChanged();
    }

    public void deleteData(int i, int i2) {
        notifyItemRemoved(i);
        notifyDataChanged();
    }

    public void updateFinishData(boolean z, int i) {
        if (z) {
            notifyDataChanged();
        }
    }

    public void updateData() {
        updateData(-1);
    }

    public void updateData(final int i) {
        RecyclerView recyclerView = this.mBindRv;
        if (recyclerView != null) {
            if (!recyclerView.isComputingLayout()) {
                if (i != -1) {
                    notifyItemChanged(i);
                }
                notifyDataChanged();
                return;
            }
            this.mBindRv.postOnAnimation(new Runnable() { // from class: com.miui.todo.base.todolist.BaseTodoListAdapter.1
                @Override // java.lang.Runnable
                public void run() {
                    int i2 = i;
                    if (i2 != -1) {
                        BaseTodoListAdapter.this.notifyItemChanged(i2);
                    }
                    BaseTodoListAdapter.this.notifyDataChanged();
                }
            });
            return;
        }
        if (i != -1) {
            notifyItemChanged(i);
        }
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        RecyclerView recyclerView = this.mBindRv;
        if (recyclerView != null) {
            if (!recyclerView.isComputingLayout()) {
                notifyDataSetChanged();
                return;
            } else {
                this.mBindRv.post(new Runnable() { // from class: com.miui.todo.base.todolist.BaseTodoListAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$notifyDataChanged$0();
                    }
                });
                return;
            }
        }
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyDataChanged$0() {
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "exitSearchMode:" + e);
        }
    }

    public void setIsAnimLoad(boolean z) {
        notifyDataChanged();
    }

    public boolean toggleGroupExpand(int i) {
        int itemGroupType;
        if (i == -1 || (itemGroupType = getItemGroupType(i)) == -1) {
            return false;
        }
        this.mCustomSortGroupItems.get(itemGroupType).toggleExpand();
        if (this.mDataSource.changeGroupExpand(itemGroupType, true, false)) {
            notifyDataChanged();
            return true;
        }
        notifyItemRangeRemoved(i + 1, this.mDataSource.getFinishItemCount());
        return false;
    }

    public void toggleGroupExpandByType(int i) {
        this.mCustomSortGroupItems.get(i).toggleExpand();
        this.mDataSource.changeGroupExpand(i, true, false);
        notifyDataChanged();
    }

    public void showSearchResult(List<TodoEntity> list) {
        setInSearchMode(true);
        this.mSearchData = list;
        notifyDataChanged();
    }

    public void exitSearchMode() {
        setInSearchMode(false);
        List<TodoEntity> list = this.mSearchData;
        if (list != null) {
            list.clear();
            this.mSearchData = null;
        }
        notifyDataChanged();
    }

    private void setInSearchMode(boolean z) {
        this.mIsInSearchMode = z;
    }

    public void frozenDataChanged(boolean z) {
        this.mIsFrozenDataChanged = z;
    }

    public boolean isFrozenDataChanged() {
        return this.mIsFrozenDataChanged;
    }

    public void setOtherViewClickListener(View.OnClickListener onClickListener) {
        this.mOtherViewClickListener = onClickListener;
    }

    public void setOnSwipeItemListener(OnSwipeItemListener onSwipeItemListener) {
        this.mOnSwipeItemListener = onSwipeItemListener;
    }

    public void setOnDragItemListener(OnDragItemStateListener onDragItemStateListener, OnDragItemStateListener onDragItemStateListener2) {
        this.mOnBtnDragItemListener = onDragItemStateListener;
        this.mOnTouchDragItemListener = onDragItemStateListener2;
    }

    public void setItemOperationListener(TodoItemOperationListener todoItemOperationListener) {
        this.mTodoItemOperationListener = todoItemOperationListener;
    }

    public void setOnGroupDragItemListener(View.OnDragListener onDragListener) {
        this.mOnGroupDragItemListener = onDragListener;
    }

    public void setBindContext(BaseListAdapterBindContext baseListAdapterBindContext) {
        this.mBindContext = baseListAdapterBindContext;
        baseListAdapterBindContext.setListAdapter(this);
    }

    public void setLastFinishId(long j) {
        this.mBindContext.setLastFinishId(j);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mBindRv = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mBindRv = null;
    }

    public boolean isInDrag() {
        return this.mIsInDrag;
    }

    protected boolean isUseSearchData() {
        return this.mIsInSearchMode && !TextUtils.isEmpty(this.mBindContext.getSearchToken());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (isUseSearchData()) {
            List<TodoEntity> list = this.mSearchData;
            if (list != null) {
                return list.size();
            }
            return 0;
        }
        IDataSource iDataSource = this.mDataSource;
        if (iDataSource == null) {
            return 0;
        }
        return iDataSource.getItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        if (isUseSearchData()) {
            try {
                return this.mSearchData.get(i).getId();
            } catch (Exception unused) {
                Logger.INSTANCE.d(TAG, "mSearchData IndexOutOfBoundsException");
                return -1L;
            }
        }
        int itemGroupType = getItemGroupType(i);
        if (itemGroupType == -1) {
            TodoEntity childItem = getChildItem(i);
            if (childItem != null) {
                return childItem.getId();
            }
            return -1L;
        }
        return getGroupItem(itemGroupType).getId();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseTodoItemVh baseTodoItemVh) {
        baseTodoItemVh.onRecycled();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(BaseTodoItemVh baseTodoItemVh) {
        super.onViewAttachedToWindow(baseTodoItemVh);
        baseTodoItemVh.onAttached(this.mBindContext);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(BaseTodoItemVh baseTodoItemVh) {
        super.onViewDetachedFromWindow(baseTodoItemVh);
        baseTodoItemVh.onDettached(this.mBindContext);
    }

    @Override // com.miui.todo.base.OnDragItemListener
    public boolean onDragStart(RecyclerView.ViewHolder viewHolder, int i) {
        this.mIsInDrag = true;
        this.mDragStartPos = viewHolder.getAdapterPosition();
        if (!(this.mDataSource instanceof CustomSortMode)) {
            return false;
        }
        OnDragItemStateListener onDragItemStateListener = this.mOnBtnDragItemListener;
        if (onDragItemStateListener != null) {
            onDragItemStateListener.onStartDrag(viewHolder);
        }
        return true;
    }

    @Override // com.miui.todo.base.OnDragItemListener
    public boolean onDragMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        int adapterPosition = viewHolder.getAdapterPosition();
        int adapterPosition2 = viewHolder2.getAdapterPosition();
        Logger.INSTANCE.i(TAG, " toPosition = " + adapterPosition2);
        if (isEnableDragByItemType(viewHolder.getItemViewType()) && isEnableDragByItemType(viewHolder2.getItemViewType())) {
            IDataSource iDataSource = this.mDataSource;
            if (iDataSource instanceof CustomSortMode) {
                DataGroupInfo dataGroupInfo = iDataSource.getDataGroupInfo(adapterPosition);
                TodoEntity childItem = this.mDataSource.getChildItem(dataGroupInfo);
                this.mBeforeDragItemIndex = dataGroupInfo.mSubPosition;
                TodoEntity childItem2 = this.mDataSource.getChildItem(adapterPosition2);
                if (childItem == null || childItem2 == null) {
                    Logger.INSTANCE.i(TAG, "item is Null! fromItem = " + childItem + " toItem = " + childItem2);
                } else if (childItem.getIsFinish() == 0 && childItem2.getIsFinish() == 0) {
                    TodoItemOperationListener todoItemOperationListener = this.mTodoItemOperationListener;
                    if (todoItemOperationListener != null) {
                        todoItemOperationListener.onSimpleMoved(adapterPosition, adapterPosition2);
                    }
                    this.mDataSource.swapItemCache(adapterPosition, adapterPosition2);
                    notifyItemMoved(adapterPosition, adapterPosition2);
                    this.mDragEndPos = adapterPosition2;
                    return true;
                }
            }
        }
        return false;
    }

    public void onDragEnd(RecyclerView.ViewHolder viewHolder, int i, boolean z) {
        this.mIsInDrag = false;
        this.mDataSource.afterDragItem(this.mDragStartPos, this.mDragEndPos, z);
        OnDragItemStateListener onDragItemStateListener = this.mOnBtnDragItemListener;
        if (onDragItemStateListener != null) {
            onDragItemStateListener.onEndDrag(viewHolder);
        }
        this.mDragStartPos = -1;
        this.mDragEndPos = -1;
    }

    protected void initViewHolder(BaseTodoItemVh baseTodoItemVh) {
        if (baseTodoItemVh != null) {
            baseTodoItemVh.setOtherViewClickListener(this.mOtherViewClickListener);
            baseTodoItemVh.setItemOperationListener(this.mTodoItemOperationListener);
            baseTodoItemVh.setOnDragItemListener(this.mOnBtnDragItemListener);
            baseTodoItemVh.setOnSwipeItemListener(this.mOnSwipeItemListener);
        }
    }

    protected int getItemGroupType(int i) {
        IDataSource iDataSource = this.mDataSource;
        if (iDataSource == null) {
            return -1;
        }
        return iDataSource.getItemGroupType(i);
    }

    public int getItemTotalPosByData(TodoEntity todoEntity) {
        return this.mDataSource.getTotalIndexByItem(todoEntity);
    }

    public boolean dragItemBetweenGroup(DataGroupInfo dataGroupInfo, int i) {
        Observable<Boolean> observableOnMoved;
        int itemGroupType = getItemGroupType(i);
        if (itemGroupType == dataGroupInfo.mGroupType) {
            return false;
        }
        Logger.INSTANCE.d(TAG, dataGroupInfo.mGroupType + " to " + itemGroupType);
        TodoEntity childItem = this.mDataSource.getChildItem(dataGroupInfo);
        TodoItemOperationListener todoItemOperationListener = this.mTodoItemOperationListener;
        if (todoItemOperationListener == null || (observableOnMoved = todoItemOperationListener.onMoved(childItem, dataGroupInfo.mGroupType, itemGroupType)) == null) {
            return true;
        }
        observableOnMoved.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() { // from class: com.miui.todo.base.todolist.BaseTodoListAdapter.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                BaseTodoListAdapter.this.notifyDataChanged();
                bool.booleanValue();
            }
        });
        return true;
    }

    public void notifyItemsMoved(int i, int i2) {
        try {
            notifyItemMoved(i, i2);
            notifyItemRangeChanged(Math.min(i, i2), Math.abs(i - i2) + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataGroupInfo getItemInfo(int i) {
        return this.mDataSource.getDataGroupInfo(i);
    }

    public int getItemCountWithoutHeader() {
        int i = this.isSingleColumn;
        IDataSource iDataSource = this.mDataSource;
        return i == 0 ? iDataSource.getCategoryItemCount() : iDataSource.getListRealItemCount();
    }

    public int getRealType(long j) {
        return this.mDataSource.getRealType(j);
    }

    public int getUnfinishItemCount() {
        IDataSource iDataSource = this.mDataSource;
        if (iDataSource == null) {
            return 0;
        }
        return iDataSource.getRealFinishItemCount();
    }

    public int getUnFinishedItemCount() {
        int listRealItemCount;
        int finishItemCount;
        IDataSource iDataSource = this.mDataSource;
        if (iDataSource == null) {
            return 0;
        }
        if (this.isSingleColumn == 0) {
            listRealItemCount = iDataSource.getCategoryItemCount();
            finishItemCount = this.mDataSource.getFinishItemCount();
        } else {
            listRealItemCount = iDataSource.getListRealItemCount();
            finishItemCount = this.mDataSource.getFinishItemCount();
        }
        return listRealItemCount - finishItemCount;
    }

    public int getFinishedItemCount() {
        return this.mDataSource.getFinishItemCount();
    }

    public int getBeforeDragItemIndex() {
        return this.mBeforeDragItemIndex;
    }

    public TodoGroupItem getGroupItem(int i) {
        return this.mCustomSortGroupItems.get(i);
    }

    public TodoEntity getChildItem(int i) {
        if (i == -1) {
            return null;
        }
        if (isUseSearchData()) {
            if (i >= this.mSearchData.size()) {
                return null;
            }
            return this.mSearchData.get(i);
        }
        IDataSource iDataSource = this.mDataSource;
        if (iDataSource == null) {
            return null;
        }
        return iDataSource.getChildItem(i);
    }

    public boolean isAllTodoWillFinished() {
        if (this.isSingleColumn == 0) {
            return this.mDataSource.getListRealItemCount() - this.mDataSource.getFinishItemCount() == 1;
        }
        return this.mDataSource.getCategoryItemCount() - this.mDataSource.getFinishItemCount() == 1;
    }

    public boolean isExpandGroup(int i) {
        return this.mDataSource.getBrowseConfig().pGroupExpandArray[i];
    }

    public int getGroupItemSize(int i) {
        return this.mDataSource.getList(i).size();
    }

    public void setPresenter(IBaseTodoListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    public IBaseTodoListContract.Presenter getPresenter() {
        return this.mPresenter;
    }

    public void setIsSingleColumn(int i) {
        this.isSingleColumn = i;
    }

    public int getIsSingleColumn() {
        return this.isSingleColumn;
    }
}
