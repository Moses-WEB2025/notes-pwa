package com.miui.todo.feature.todolist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.StrikeTextView.StrikeTextView;
import com.miui.common.view.StrikeTextView.StrikeThroughPainting;
import com.miui.floatwindow.feature.todo.FwTodoListItemVh;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.utils.TodoUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SubTodoItemVh.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0016\u0018\u0000 02\u00020\u0001:\u00010B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0003H\u0002J\u0016\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\tJ\u001e\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010!H\u0014J\u0010\u0010#\u001a\u00020\u00182\b\u0010$\u001a\u0004\u0018\u00010%J\u0010\u0010&\u001a\u00020\u00182\b\u0010$\u001a\u0004\u0018\u00010'J\u0010\u0010(\u001a\u00020\u00182\b\u0010$\u001a\u0004\u0018\u00010\u000eJ\u0010\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020\u00182\u0006\u0010-\u001a\u00020+H\u0014J\u0010\u0010.\u001a\u00020\u00182\b\u0010/\u001a\u0004\u0018\u00010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00108\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u00061"}, d2 = {"Lcom/miui/todo/feature/todolist/SubTodoItemVh;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "mItemView", "Landroid/view/View;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;)V", "mCheckBoxClickCallback", "Lcom/miui/floatwindow/feature/todo/FwTodoListItemVh$SubTodoCheckBoxClickCallback;", "mBindContext", "Lcom/miui/todo/feature/todolist/SubTodoListBindContext;", "mCheckGroup", "mCheckChangeListener", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", "mDataListener", "Lcom/miui/todo/feature/todolist/SubTodoDataListener;", "mTvContent", "Lcom/miui/common/view/StrikeTextView/StrikeTextView;", "mCbSubTodo", "Landroid/widget/CheckBox;", "getMCbSubTodo", "()Landroid/widget/CheckBox;", "setMCbSubTodo", "(Landroid/widget/CheckBox;)V", "initView", "", "rootView", "bind", "todoData", "Lcom/miui/todo/data/bean/SubTodoEntity;", "bindContext", "getHighLightText", "", "content", "", "searchToken", "setOnClickListener", "listener", "Landroid/view/View$OnClickListener;", "setOnLongClickListener", "Landroid/view/View$OnLongClickListener;", "setDataListener", "changeDataFinishStatus", "isFinish", "", "setContentCheckState", "isChecked", "setCheckBoxCallBack", "checkBoxClickCallback", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class SubTodoItemVh extends RecyclerView.ViewHolder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private SubTodoListBindContext mBindContext;
    private CheckBox mCbSubTodo;
    private FwTodoListItemVh.SubTodoCheckBoxClickCallback mCheckBoxClickCallback;
    private final CompoundButton.OnCheckedChangeListener mCheckChangeListener;
    private View mCheckGroup;
    private SubTodoDataListener mDataListener;
    private final View mItemView;
    protected StrikeTextView mTvContent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubTodoItemVh(View mItemView) {
        super(mItemView);
        Intrinsics.checkNotNullParameter(mItemView, "mItemView");
        this.mItemView = mItemView;
        this.mCheckChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.todo.feature.todolist.SubTodoItemVh$$ExternalSyntheticLambda2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.setContentCheckState(z);
            }
        };
        initView(mItemView);
    }

    protected final CheckBox getMCbSubTodo() {
        return this.mCbSubTodo;
    }

    protected final void setMCbSubTodo(CheckBox checkBox) {
        this.mCbSubTodo = checkBox;
    }

    private final void initView(View rootView) {
        this.mTvContent = (StrikeTextView) rootView.findViewById(R.id.tv_content);
        this.mCheckGroup = rootView.findViewById(R.id.check_group);
        CheckBox checkBox = (CheckBox) rootView.findViewById(R.id.cb_subtodo);
        this.mCbSubTodo = checkBox;
        Intrinsics.checkNotNull(checkBox);
        checkBox.setOnCheckedChangeListener(null);
        CheckBox checkBox2 = this.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox2);
        checkBox2.setChecked(false);
        CheckBox checkBox3 = this.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox3);
        checkBox3.setOnCheckedChangeListener(this.mCheckChangeListener);
        CheckBox checkBox4 = this.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox4, "null cannot be cast to non-null type android.view.View");
        AccessibilityDelegateUtil.setDoneAccessibilityDelegate(checkBox4);
        View view = this.mCheckGroup;
        Intrinsics.checkNotNull(view);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.feature.todolist.SubTodoItemVh$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SubTodoItemVh.initView$lambda$2(this.f$0, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$2(final SubTodoItemVh subTodoItemVh, View view) {
        CheckBox checkBox = subTodoItemVh.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox);
        boolean zIsChecked = checkBox.isChecked();
        FwTodoListItemVh.SubTodoCheckBoxClickCallback subTodoCheckBoxClickCallback = subTodoItemVh.mCheckBoxClickCallback;
        if (subTodoCheckBoxClickCallback != null) {
            Intrinsics.checkNotNull(subTodoCheckBoxClickCallback);
            if (subTodoCheckBoxClickCallback.checkBoxClick()) {
                return;
            }
        }
        if (!zIsChecked) {
            CheckBox checkBox2 = subTodoItemVh.mCbSubTodo;
            Intrinsics.checkNotNull(checkBox2);
            checkBox2.setActivated(true);
            CheckBox checkBox3 = subTodoItemVh.mCbSubTodo;
            Intrinsics.checkNotNull(checkBox3);
            checkBox3.setChecked(true);
            StrikeTextView strikeTextView = subTodoItemVh.mTvContent;
            Intrinsics.checkNotNull(strikeTextView);
            final StrikeThroughPainting strikeThroughPainting = new StrikeThroughPainting(strikeTextView);
            strikeThroughPainting.cutTextEdge(true).color(TodoUtils.getColor(R.color.theme_main_color)).strokeWidth(6.0f).totalTime(300L);
            strikeThroughPainting.strikeThrough();
            strikeThroughPainting.callback(new StrikeThroughPainting.StrikeThroughPaintingCallback() { // from class: com.miui.todo.feature.todolist.SubTodoItemVh$$ExternalSyntheticLambda0
                @Override // com.miui.common.view.StrikeTextView.StrikeThroughPainting.StrikeThroughPaintingCallback
                public final void onStrikeThroughEnd() {
                    SubTodoItemVh.initView$lambda$2$lambda$1(this.f$0, strikeThroughPainting);
                }
            });
            return;
        }
        CheckBox checkBox4 = subTodoItemVh.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox4);
        checkBox4.setActivated(false);
        CheckBox checkBox5 = subTodoItemVh.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox5);
        checkBox5.setChecked(false);
        subTodoItemVh.changeDataFinishStatus(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$2$lambda$1(SubTodoItemVh subTodoItemVh, StrikeThroughPainting strikeThroughPainting) {
        subTodoItemVh.changeDataFinishStatus(true);
        strikeThroughPainting.clearStrikeThrough();
    }

    public final void bind(SubTodoEntity todoData, SubTodoListBindContext bindContext) {
        Intrinsics.checkNotNullParameter(todoData, "todoData");
        Intrinsics.checkNotNullParameter(bindContext, "bindContext");
        this.itemView.setHapticFeedbackEnabled(false);
        this.mBindContext = bindContext;
        CheckBox checkBox = this.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox);
        checkBox.setOnCheckedChangeListener(null);
        CheckBox checkBox2 = this.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox2);
        checkBox2.setActivated(false);
        CheckBox checkBox3 = this.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox3);
        checkBox3.setChecked(todoData.getIsFinish());
        StrikeTextView strikeTextView = this.mTvContent;
        Intrinsics.checkNotNull(strikeTextView);
        strikeTextView.setText(getHighLightText(todoData.getContent(), bindContext.getSearchToken()));
        CheckBox checkBox4 = this.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox4);
        setContentCheckState(checkBox4.isChecked());
        CheckBox checkBox5 = this.mCbSubTodo;
        Intrinsics.checkNotNull(checkBox5);
        checkBox5.setOnCheckedChangeListener(this.mCheckChangeListener);
    }

    protected CharSequence getHighLightText(String content, String searchToken) {
        return ContentUtils.changeHighLight(content, searchToken);
    }

    public final void setOnClickListener(View.OnClickListener listener) {
        this.mItemView.setOnClickListener(listener);
    }

    public final void setOnLongClickListener(View.OnLongClickListener listener) {
        this.mItemView.setOnLongClickListener(listener);
    }

    public final void setDataListener(SubTodoDataListener listener) {
        this.mDataListener = listener;
    }

    private final void changeDataFinishStatus(boolean isFinish) {
        SubTodoDataListener subTodoDataListener = this.mDataListener;
        if (subTodoDataListener != null) {
            Intrinsics.checkNotNull(subTodoDataListener);
            subTodoDataListener.onFinishStatusChanged(isFinish, getAdapterPosition());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setContentCheckState(boolean isChecked) {
        if (isChecked) {
            StrikeTextView strikeTextView = this.mTvContent;
            Intrinsics.checkNotNull(strikeTextView);
            strikeTextView.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
        } else {
            StrikeTextView strikeTextView2 = this.mTvContent;
            Intrinsics.checkNotNull(strikeTextView2);
            strikeTextView2.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_common_color));
        }
    }

    public final void setCheckBoxCallBack(FwTodoListItemVh.SubTodoCheckBoxClickCallback checkBoxClickCallback) {
        this.mCheckBoxClickCallback = checkBoxClickCallback;
    }

    /* JADX INFO: compiled from: SubTodoItemVh.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lcom/miui/todo/feature/todolist/SubTodoItemVh$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "newInstance", "Lcom/miui/todo/feature/todolist/SubTodoItemVh;", "parent", "Landroid/view/ViewGroup;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SubTodoItemVh newInstance(ViewGroup parent) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            View viewInflateView = UIUtils.inflateView(parent, R.layout.subtodo_item);
            Intrinsics.checkNotNull(viewInflateView);
            return new SubTodoItemVh(viewInflateView);
        }
    }
}
