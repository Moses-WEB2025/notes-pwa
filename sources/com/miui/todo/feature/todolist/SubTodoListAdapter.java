package com.miui.todo.feature.todolist;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.floatwindow.feature.todo.FwTodoListItemVh;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.mode.SubMode;
import com.miui.todo.utils.CharUtils;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SubTodoListAdapter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0016\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B%\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\t\u0010\nJ\u0010\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u001cJ\u0010\u0010:\u001a\u0002082\b\u0010;\u001a\u0004\u0018\u00010\"J\u0018\u0010<\u001a\u00020\u00022\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@H\u0016J\u0018\u0010A\u001a\u0002082\u0006\u0010B\u001a\u00020\u00022\u0006\u0010C\u001a\u00020@H\u0016J\u0010\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020@H\u0016J\b\u0010G\u001a\u00020@H\u0016J\u0010\u0010H\u001a\u0002082\u0006\u0010I\u001a\u00020JH\u0016J\u000e\u0010K\u001a\u0002082\u0006\u0010L\u001a\u00020MJ\u0010\u0010N\u001a\u0002082\b\u0010O\u001a\u0004\u0018\u00010(J\b\u0010P\u001a\u000208H\u0002J\u0010\u0010Q\u001a\u0002082\b\u0010R\u001a\u0004\u0018\u00010.R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u0018X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010'\u001a\u0004\u0018\u00010(X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00101\u001a\u000202X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106¨\u0006S"}, d2 = {"Lcom/miui/todo/feature/todolist/SubTodoListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/miui/todo/feature/todolist/SubTodoItemVh;", "mContext", "Landroid/content/Context;", "mListener", "Landroid/view/View$OnClickListener;", "mLongClickListener", "Landroid/view/View$OnLongClickListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroid/view/View$OnClickListener;Landroid/view/View$OnLongClickListener;)V", "getMContext", "()Landroid/content/Context;", "setMContext", "(Landroid/content/Context;)V", "getMListener", "()Landroid/view/View$OnClickListener;", "setMListener", "(Landroid/view/View$OnClickListener;)V", "getMLongClickListener", "()Landroid/view/View$OnLongClickListener;", "setMLongClickListener", "(Landroid/view/View$OnLongClickListener;)V", "tag", "", "getTag", "()Ljava/lang/String;", "mBindContext", "Lcom/miui/todo/feature/todolist/SubTodoListBindContext;", "getMBindContext", "()Lcom/miui/todo/feature/todolist/SubTodoListBindContext;", "setMBindContext", "(Lcom/miui/todo/feature/todolist/SubTodoListBindContext;)V", "mSubTodoList", "Lcom/miui/todo/data/mode/SubMode;", "getMSubTodoList", "()Lcom/miui/todo/data/mode/SubMode;", "setMSubTodoList", "(Lcom/miui/todo/data/mode/SubMode;)V", "mSubHolderListener", "Lcom/miui/todo/feature/todolist/SubHolderListener;", "getMSubHolderListener", "()Lcom/miui/todo/feature/todolist/SubHolderListener;", "setMSubHolderListener", "(Lcom/miui/todo/feature/todolist/SubHolderListener;)V", "mCheckBoxClickCallback", "Lcom/miui/floatwindow/feature/todo/FwTodoListItemVh$SubTodoCheckBoxClickCallback;", "mPendingRefreshChangedTask", "Ljava/lang/Runnable;", "mSubTodoDataListener", "Lcom/miui/todo/feature/todolist/SubTodoDataListener;", "getMSubTodoDataListener", "()Lcom/miui/todo/feature/todolist/SubTodoDataListener;", "setMSubTodoDataListener", "(Lcom/miui/todo/feature/todolist/SubTodoDataListener;)V", "setBindContext", "", "bindContext", "bindDataSource", "subTodoData", "onCreateViewHolder", "viewGroup", "Landroid/view/ViewGroup;", "viewType", "", "onBindViewHolder", "subTodoItemVh", "pos", "getItemId", "", "position", "getItemCount", "onDetachedFromRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "setExpandStatus", "isExpand", "", "setSubHolderListener", "subHolderListener", "notifySubTodoDataChanged", "setSubTodoCheckBoxClickCallback", "checkBoxClickCallback", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class SubTodoListAdapter extends RecyclerView.Adapter<SubTodoItemVh> {
    private SubTodoListBindContext mBindContext;
    private FwTodoListItemVh.SubTodoCheckBoxClickCallback mCheckBoxClickCallback;
    private Context mContext;
    private View.OnClickListener mListener;
    private View.OnLongClickListener mLongClickListener;
    private SubHolderListener mSubHolderListener;
    private SubMode mSubTodoList;
    private final String tag = "SubTodoListAdapter";
    private final Runnable mPendingRefreshChangedTask = new Runnable() { // from class: com.miui.todo.feature.todolist.SubTodoListAdapter$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            SubTodoListAdapter.mPendingRefreshChangedTask$lambda$0(this.f$0);
        }
    };
    private SubTodoDataListener mSubTodoDataListener = new SubTodoDataListener() { // from class: com.miui.todo.feature.todolist.SubTodoListAdapter$mSubTodoDataListener$1
        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onDataClick(View dataEditor, int index) {
        }

        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onDataSave(SubTodoEntity newData, int index, boolean isNeedRefresh, String oldData) {
            SubMode mSubTodoList = this.this$0.getMSubTodoList();
            Intrinsics.checkNotNull(mSubTodoList);
            mSubTodoList.update(newData, index);
            if (isNeedRefresh) {
                this.this$0.notifySubTodoDataChanged();
            }
        }

        /* JADX WARN: Code duplicated, block: B:11:0x0088  */
        /* JADX WARN: Code duplicated, block: B:9:0x007a  */
        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onDataDelete(int index) {
            if (index == 1) {
                SubMode mSubTodoList = this.this$0.getMSubTodoList();
                Intrinsics.checkNotNull(mSubTodoList);
                if (TextUtils.isEmpty(mSubTodoList.getTitle())) {
                    SubMode mSubTodoList2 = this.this$0.getMSubTodoList();
                    Intrinsics.checkNotNull(mSubTodoList2);
                    if (mSubTodoList2.getSubTodoSize() == 2) {
                        SubMode mSubTodoList3 = this.this$0.getMSubTodoList();
                        Intrinsics.checkNotNull(mSubTodoList3);
                        SubTodoEntity entity = mSubTodoList3.getEntity(0);
                        SubMode mSubTodoList4 = this.this$0.getMSubTodoList();
                        Intrinsics.checkNotNull(mSubTodoList4);
                        String content = entity.getContent();
                        Intrinsics.checkNotNullExpressionValue(content, "getContent(...)");
                        mSubTodoList4.setTitle(StringsKt.replace$default(content, CharUtils.ZERO_WIDTH_SPACE, "", false, 4, (Object) null));
                        SubMode mSubTodoList5 = this.this$0.getMSubTodoList();
                        Intrinsics.checkNotNull(mSubTodoList5);
                        mSubTodoList5.delete(0);
                        SubMode mSubTodoList6 = this.this$0.getMSubTodoList();
                        Intrinsics.checkNotNull(mSubTodoList6);
                        mSubTodoList6.delete(index);
                        this.this$0.notifyItemRangeChanged(0, 2);
                        this.this$0.notifySubTodoDataChanged();
                    } else {
                        SubMode mSubTodoList7 = this.this$0.getMSubTodoList();
                        Intrinsics.checkNotNull(mSubTodoList7);
                        mSubTodoList7.delete(index);
                        if (index > 0) {
                            this.this$0.notifyItemChanged(index - 1);
                        }
                        this.this$0.notifyItemRemoved(index);
                    }
                } else {
                    SubMode mSubTodoList8 = this.this$0.getMSubTodoList();
                    Intrinsics.checkNotNull(mSubTodoList8);
                    mSubTodoList8.delete(index);
                    if (index > 0) {
                        this.this$0.notifyItemChanged(index - 1);
                    }
                    this.this$0.notifyItemRemoved(index);
                }
            } else {
                SubMode mSubTodoList9 = this.this$0.getMSubTodoList();
                Intrinsics.checkNotNull(mSubTodoList9);
                mSubTodoList9.delete(index);
                if (index > 0) {
                    this.this$0.notifyItemChanged(index - 1);
                }
                this.this$0.notifyItemRemoved(index);
            }
            this.this$0.notifySubTodoDataChanged();
        }

        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onDataMerge(int index) {
            if (index == 0) {
                SubMode mSubTodoList = this.this$0.getMSubTodoList();
                Intrinsics.checkNotNull(mSubTodoList);
                if (mSubTodoList.getSubTodoSize() == 1) {
                    SubMode mSubTodoList2 = this.this$0.getMSubTodoList();
                    Intrinsics.checkNotNull(mSubTodoList2);
                    SubTodoEntity entity = mSubTodoList2.getEntity(index);
                    SubMode mSubTodoList3 = this.this$0.getMSubTodoList();
                    Intrinsics.checkNotNull(mSubTodoList3);
                    if (TextUtils.isEmpty(mSubTodoList3.getTitle())) {
                        SubMode mSubTodoList4 = this.this$0.getMSubTodoList();
                        Intrinsics.checkNotNull(mSubTodoList4);
                        String content = entity.getContent();
                        Intrinsics.checkNotNullExpressionValue(content, "getContent(...)");
                        mSubTodoList4.setTitle(StringsKt.replace$default(content, CharUtils.ZERO_WIDTH_SPACE, "", false, 4, (Object) null));
                    } else {
                        SubMode mSubTodoList5 = this.this$0.getMSubTodoList();
                        Intrinsics.checkNotNull(mSubTodoList5);
                        SubMode mSubTodoList6 = this.this$0.getMSubTodoList();
                        Intrinsics.checkNotNull(mSubTodoList6);
                        String title = mSubTodoList6.getTitle();
                        String content2 = entity.getContent();
                        Intrinsics.checkNotNullExpressionValue(content2, "getContent(...)");
                        mSubTodoList5.setTitle(title + StringsKt.replace$default(content2, CharUtils.ZERO_WIDTH_SPACE, "", false, 4, (Object) null));
                    }
                    SubMode mSubTodoList7 = this.this$0.getMSubTodoList();
                    Intrinsics.checkNotNull(mSubTodoList7);
                    mSubTodoList7.delete(index);
                    this.this$0.notifySubTodoDataChanged();
                    this.this$0.notifyDataSetChanged();
                    return;
                }
                return;
            }
            SubMode mSubTodoList8 = this.this$0.getMSubTodoList();
            Intrinsics.checkNotNull(mSubTodoList8);
            SubTodoEntity entity2 = mSubTodoList8.getEntity(index);
            SubMode mSubTodoList9 = this.this$0.getMSubTodoList();
            Intrinsics.checkNotNull(mSubTodoList9);
            SubTodoEntity entity3 = mSubTodoList9.getEntity(index - 1);
            String content3 = entity3.getContent();
            String content4 = entity2.getContent();
            Intrinsics.checkNotNullExpressionValue(content4, "getContent(...)");
            entity3.setContent(content3 + StringsKt.replace$default(content4, CharUtils.ZERO_WIDTH_SPACE, "", false, 4, (Object) null));
            SubMode mSubTodoList10 = this.this$0.getMSubTodoList();
            Intrinsics.checkNotNull(mSubTodoList10);
            mSubTodoList10.delete(index);
            this.this$0.notifyItemRemoved(index);
            this.this$0.notifySubTodoDataChanged();
        }

        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onMultiDataAdd(List<SubTodoEntity> newData, int firstIndex, boolean isNeedRefresh, String oldData) {
            Intrinsics.checkNotNullParameter(newData, "newData");
            SubMode mSubTodoList = this.this$0.getMSubTodoList();
            Intrinsics.checkNotNull(mSubTodoList);
            mSubTodoList.update(newData.get(0), firstIndex);
            int size = newData.size();
            for (int i = 1; i < size; i++) {
                SubMode mSubTodoList2 = this.this$0.getMSubTodoList();
                Intrinsics.checkNotNull(mSubTodoList2);
                mSubTodoList2.add(newData.get(i), firstIndex + i);
            }
            if (isNeedRefresh) {
                this.this$0.notifySubTodoDataChanged();
            }
            this.this$0.notifyDataSetChanged();
        }

        @Override // com.miui.todo.feature.todolist.SubTodoDataListener
        public void onFinishStatusChanged(boolean isFinish, int index) {
            SubTodoListBindContext mBindContext = this.this$0.getMBindContext();
            Intrinsics.checkNotNull(mBindContext);
            mBindContext.getBindView().removeCallbacks(this.this$0.mPendingRefreshChangedTask);
            SubMode mSubTodoList = this.this$0.getMSubTodoList();
            Intrinsics.checkNotNull(mSubTodoList);
            mSubTodoList.changeFinishStatus(index, isFinish);
            SubTodoListBindContext mBindContext2 = this.this$0.getMBindContext();
            Intrinsics.checkNotNull(mBindContext2);
            mBindContext2.getBindView().postDelayed(this.this$0.mPendingRefreshChangedTask, 100L);
        }
    };

    protected final Context getMContext() {
        return this.mContext;
    }

    protected final void setMContext(Context context) {
        this.mContext = context;
    }

    protected final View.OnClickListener getMListener() {
        return this.mListener;
    }

    protected final void setMListener(View.OnClickListener onClickListener) {
        this.mListener = onClickListener;
    }

    protected final View.OnLongClickListener getMLongClickListener() {
        return this.mLongClickListener;
    }

    protected final void setMLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mLongClickListener = onLongClickListener;
    }

    public SubTodoListAdapter(Context context, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        this.mContext = context;
        this.mListener = onClickListener;
        this.mLongClickListener = onLongClickListener;
    }

    public final String getTag() {
        return this.tag;
    }

    protected final SubTodoListBindContext getMBindContext() {
        return this.mBindContext;
    }

    protected final void setMBindContext(SubTodoListBindContext subTodoListBindContext) {
        this.mBindContext = subTodoListBindContext;
    }

    protected final SubMode getMSubTodoList() {
        return this.mSubTodoList;
    }

    protected final void setMSubTodoList(SubMode subMode) {
        this.mSubTodoList = subMode;
    }

    protected final SubHolderListener getMSubHolderListener() {
        return this.mSubHolderListener;
    }

    protected final void setMSubHolderListener(SubHolderListener subHolderListener) {
        this.mSubHolderListener = subHolderListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mPendingRefreshChangedTask$lambda$0(SubTodoListAdapter subTodoListAdapter) {
        subTodoListAdapter.notifySubTodoDataChanged();
        SubTodoListBindContext subTodoListBindContext = subTodoListAdapter.mBindContext;
        if (subTodoListBindContext != null) {
            Intrinsics.checkNotNull(subTodoListBindContext);
            if (subTodoListBindContext.getBindView() != null) {
                SubTodoListBindContext subTodoListBindContext2 = subTodoListAdapter.mBindContext;
                Intrinsics.checkNotNull(subTodoListBindContext2);
                subTodoListBindContext2.getBindView().stopScroll();
            }
        }
        subTodoListAdapter.notifyDataSetChanged();
    }

    protected final SubTodoDataListener getMSubTodoDataListener() {
        return this.mSubTodoDataListener;
    }

    protected final void setMSubTodoDataListener(SubTodoDataListener subTodoDataListener) {
        Intrinsics.checkNotNullParameter(subTodoDataListener, "<set-?>");
        this.mSubTodoDataListener = subTodoDataListener;
    }

    public final void setBindContext(SubTodoListBindContext bindContext) {
        this.mBindContext = bindContext;
    }

    public final void bindDataSource(SubMode subTodoData) {
        SubMode subMode = this.mSubTodoList;
        if (subMode != null && subTodoData != null) {
            Intrinsics.checkNotNull(subMode);
            if (Intrinsics.areEqual(subMode.getContent(), subTodoData.getContent())) {
                Logger logger = Logger.INSTANCE;
                String str = this.tag;
                int subTodoSize = subTodoData.getSubTodoSize();
                SubTodoListBindContext subTodoListBindContext = this.mBindContext;
                Intrinsics.checkNotNull(subTodoListBindContext);
                RecyclerView bindView = subTodoListBindContext.getBindView();
                Intrinsics.checkNotNullExpressionValue(bindView, "getBindView(...)");
                logger.d(str, "0 subTodoData size = " + subTodoSize + " rvChildCount " + bindView.getChildCount());
                return;
            }
        }
        if (subTodoData != null) {
            this.mSubTodoList = subTodoData.copy();
        }
        Logger logger2 = Logger.INSTANCE;
        String str2 = this.tag;
        Intrinsics.checkNotNull(subTodoData);
        int subTodoSize2 = subTodoData.getSubTodoSize();
        SubTodoListBindContext subTodoListBindContext2 = this.mBindContext;
        Intrinsics.checkNotNull(subTodoListBindContext2);
        RecyclerView bindView2 = subTodoListBindContext2.getBindView();
        Intrinsics.checkNotNullExpressionValue(bindView2, "getBindView(...)");
        logger2.d(str2, "1 subTodoData size = " + subTodoSize2 + " rvChildCount " + bindView2.getChildCount());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public SubTodoItemVh onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        return SubTodoItemVh.INSTANCE.newInstance(viewGroup);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(SubTodoItemVh subTodoItemVh, int pos) {
        Intrinsics.checkNotNullParameter(subTodoItemVh, "subTodoItemVh");
        SubMode subMode = this.mSubTodoList;
        Intrinsics.checkNotNull(subMode);
        SubTodoEntity entity = subMode.getEntity(pos);
        Intrinsics.checkNotNullExpressionValue(entity, "getEntity(...)");
        SubTodoListBindContext subTodoListBindContext = this.mBindContext;
        Intrinsics.checkNotNull(subTodoListBindContext);
        subTodoItemVh.bind(entity, subTodoListBindContext);
        subTodoItemVh.setDataListener(this.mSubTodoDataListener);
        subTodoItemVh.setOnClickListener(this.mListener);
        subTodoItemVh.setOnLongClickListener(this.mLongClickListener);
        subTodoItemVh.setCheckBoxCallBack(this.mCheckBoxClickCallback);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        SubMode subMode = this.mSubTodoList;
        Intrinsics.checkNotNull(subMode);
        return subMode.getEntity(position).getId();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        SubMode subMode = this.mSubTodoList;
        if (subMode == null) {
            return 0;
        }
        Intrinsics.checkNotNull(subMode);
        return subMode.getSubTodoSize();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeCallbacks(this.mPendingRefreshChangedTask);
    }

    public final void setExpandStatus(boolean isExpand) {
        SubMode subMode = this.mSubTodoList;
        if (subMode != null) {
            Intrinsics.checkNotNull(subMode);
            subMode.setExpand(isExpand);
        }
    }

    public final void setSubHolderListener(SubHolderListener subHolderListener) {
        this.mSubHolderListener = subHolderListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifySubTodoDataChanged() {
        SubHolderListener subHolderListener = this.mSubHolderListener;
        if (subHolderListener != null) {
            Intrinsics.checkNotNull(subHolderListener);
            subHolderListener.onDataChanged(this.mSubTodoList);
        }
    }

    public final void setSubTodoCheckBoxClickCallback(FwTodoListItemVh.SubTodoCheckBoxClickCallback checkBoxClickCallback) {
        this.mCheckBoxClickCallback = checkBoxClickCallback;
    }
}
