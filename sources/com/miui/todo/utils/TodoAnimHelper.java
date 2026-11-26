package com.miui.todo.utils;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ch.qos.logback.core.joran.action.Action;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.common.view.DoneSparkAnim;
import com.miui.common.view.StrikeTextView.StrikeTextView;
import com.miui.common.view.StrikeTextView.StrikeThroughPainting;
import com.miui.notes.R;
import com.miui.todo.base.todolist.BaseTodoListAdapter;
import com.miui.todo.base.todolist.IBaseTodoListContract;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.feature.todolist.ITodoItemFakeVh;
import com.miui.todo.view.SparkFrameLayout;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.recyclerview.widget.MiuiScaleItemAnimator;

/* JADX INFO: compiled from: TodoAnimHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001cB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J4\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007JL\u0010\u0014\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/miui/todo/utils/TodoAnimHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", Action.SCOPE_ATTRIBUTE, "Lkotlinx/coroutines/CoroutineScope;", "doFinishAnim", "", "todoListView", "Lcom/miui/todo/base/todolist/IBaseTodoListContract$View;", "changedData", "Lcom/miui/todo/data/bean/TodoEntity;", "position", "", "itemView", "Landroid/view/View;", "endAction", "Ljava/lang/Runnable;", "doAllFinishAnim", "contentView", "Landroid/view/ViewGroup;", "maskView", "itemFakeVh", "Lcom/miui/todo/feature/todolist/ITodoItemFakeVh;", "processListener", "Lcom/miui/todo/utils/TodoAnimHelper$Listener;", "Listener", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TodoAnimHelper {
    private static final String TAG = "TodoAnimHelper";
    public static final TodoAnimHelper INSTANCE = new TodoAnimHelper();
    private static final CoroutineScope scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault());

    /* JADX INFO: compiled from: TodoAnimHelper.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&¨\u0006\bÀ\u0006\u0003"}, d2 = {"Lcom/miui/todo/utils/TodoAnimHelper$Listener;", "", "onStart", "", "sparkAnim", "Lcom/miui/common/view/DoneSparkAnim;", "onEnd", "onCancel", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Listener {
        void onCancel();

        void onEnd();

        void onStart(DoneSparkAnim sparkAnim);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean doAllFinishAnim$lambda$0(View view, MotionEvent motionEvent) {
        return true;
    }

    private TodoAnimHelper() {
    }

    @JvmStatic
    public static final void doFinishAnim(IBaseTodoListContract.View todoListView, TodoEntity changedData, int position, final View itemView, final Runnable endAction) {
        TodoEntity childItem;
        Intrinsics.checkNotNullParameter(todoListView, "todoListView");
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            Logger.INSTANCE.d(TAG, "doFinishAnim");
            Trace.beginSection("doFinishAnim");
            final BaseTodoListAdapter listAdapter = todoListView.getListAdapter();
            final RecyclerView listView = todoListView.getListView();
            if (listAdapter != null) {
                listAdapter.frozenDataChanged(true);
            }
            if (!listView.isComputingLayout()) {
                listView.suppressLayout(true);
            }
            if (listAdapter != null && (childItem = listAdapter.getChildItem(position)) != null) {
                listAdapter.setLastFinishId(childItem.getId());
            }
            todoListView.finishTodoInData(changedData, position);
            HapticFeedbackTool.INSTANCE.getInstance().performFinishOneTodo(itemView);
            TextView textView = (TextView) itemView.findViewById(R.id.time);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_clock);
            StrikeTextView strikeTextView = (StrikeTextView) itemView.findViewById(R.id.content);
            final StrikeThroughPainting strikeThroughPainting = new StrikeThroughPainting(strikeTextView);
            final CheckBox checkBox = (CheckBox) itemView.findViewById(android.R.id.checkbox);
            int color = TodoUtils.getColor(R.color.todo_item_content_text_disable_color);
            strikeTextView.setTextColor(color);
            textView.setTextColor(color);
            Drawable drawable = TodoUtils.getDrawable(R.drawable.ic_todo_item_clock_d);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            imageView.setImageDrawable(drawable);
            checkBox.setActivated(true);
            checkBox.setChecked(true);
            AnimConfig animConfig = new AnimConfig();
            animConfig.addListeners(new TransitionListener() { // from class: com.miui.todo.utils.TodoAnimHelper.doFinishAnim.1
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object toTag) {
                    super.onComplete(toTag);
                    Logger.INSTANCE.d(TodoAnimHelper.TAG, "onComplete: 第一段动画完成");
                    strikeThroughPainting.changeStrikeStyle(150L, TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
                    checkBox.setActivated(false);
                    AnimConfig animConfig2 = new AnimConfig();
                    final BaseTodoListAdapter baseTodoListAdapter = listAdapter;
                    final RecyclerView recyclerView = listView;
                    final StrikeThroughPainting strikeThroughPainting2 = strikeThroughPainting;
                    final Runnable runnable = endAction;
                    final View view = itemView;
                    animConfig2.addListeners(new TransitionListener() { // from class: com.miui.todo.utils.TodoAnimHelper$doFinishAnim$1$onComplete$1
                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object toTag2) {
                            super.onComplete(toTag2);
                            Logger.INSTANCE.d("TodoAnimHelper", "onComplete: second animation finish ");
                            if (baseTodoListAdapter != null) {
                                Logger.INSTANCE.d("TodoAnimHelper", "onComplete: setting is finished ");
                                recyclerView.suppressLayout(false);
                                strikeThroughPainting2.clearStrikeThrough();
                                baseTodoListAdapter.frozenDataChanged(false);
                                baseTodoListAdapter.getPresenter().executePendingDataTask();
                                baseTodoListAdapter.setLastFinishId(-1L);
                            }
                            Runnable runnable2 = runnable;
                            if (runnable2 != null) {
                                runnable2.run();
                            }
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onCancel(Object toTag2) {
                            super.onCancel(toTag2);
                            Logger.INSTANCE.d("TodoAnimHelper", "onCancel: second animation is shut down");
                            view.setScaleY(1.0f);
                            view.setScaleX(1.0f);
                            if (baseTodoListAdapter != null) {
                                recyclerView.suppressLayout(false);
                                strikeThroughPainting2.clearStrikeThrough();
                                baseTodoListAdapter.frozenDataChanged(false);
                                baseTodoListAdapter.getPresenter().executePendingDataTask();
                                baseTodoListAdapter.setLastFinishId(-1L);
                            }
                            Logger.INSTANCE.d("TodoAnimHelper", "config complete config2 onCancel: ");
                        }
                    });
                    IStateStyle iStateStyleState = Folme.useAt(itemView).state();
                    ViewProperty viewProperty = ViewProperty.SCALE_Y;
                    Float fValueOf = Float.valueOf(1.0f);
                    iStateStyleState.to(viewProperty, fValueOf, ViewProperty.SCALE_X, fValueOf, animConfig2);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object toTag) {
                    super.onCancel(toTag);
                    Logger.INSTANCE.d(TodoAnimHelper.TAG, "onCancel: first animation is shut down ");
                }
            });
            Folme.useAt(itemView).state().to(ViewProperty.SCALE_Y, Float.valueOf(1.03f), ViewProperty.SCALE_X, Float.valueOf(1.03f), animConfig);
            strikeThroughPainting.cutTextEdge(true).color(TodoUtils.getColor(R.color.theme_main_color)).strokeWidth(6.0f).totalTime(300L);
            strikeThroughPainting.strikeThrough();
        } finally {
            Trace.endSection();
            Logger.INSTANCE.d(TAG, "doFinishAnim:" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
        }
    }

    @JvmStatic
    public static final void doAllFinishAnim(IBaseTodoListContract.View todoListView, TodoEntity changedData, int position, View itemView, ViewGroup contentView, ViewGroup maskView, ITodoItemFakeVh itemFakeVh, final Listener processListener) {
        TodoEntity childItem;
        Intrinsics.checkNotNullParameter(todoListView, "todoListView");
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        Intrinsics.checkNotNullParameter(contentView, "contentView");
        Intrinsics.checkNotNullParameter(maskView, "maskView");
        Intrinsics.checkNotNullParameter(itemFakeVh, "itemFakeVh");
        Logger.INSTANCE.d(TAG, "doAllFinishAnim");
        View viewFindViewById = itemView.findViewById(android.R.id.checkbox);
        if (viewFindViewById != null) {
            viewFindViewById.setActivated(false);
        }
        BaseTodoListAdapter listAdapter = todoListView.getListAdapter();
        RecyclerView listView = todoListView.getListView();
        if (listAdapter != null) {
            listAdapter.frozenDataChanged(true);
        }
        if (!listView.isComputingLayout()) {
            listView.suppressLayout(true);
        }
        CoroutineScope coroutineScope = scope;
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(todoListView, changedData, position, null), 3, null);
        int[] iArr = new int[2];
        contentView.getLocationInWindow(iArr);
        int[] iArr2 = new int[2];
        listView.setItemAnimator(new MiuiScaleItemAnimator());
        maskView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.todo.utils.TodoAnimHelper$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return TodoAnimHelper.doAllFinishAnim$lambda$0(view, motionEvent);
            }
        });
        if (listAdapter != null && (childItem = listAdapter.getChildItem(position)) != null) {
            listAdapter.setLastFinishId(childItem.getId());
        }
        itemFakeVh.bind(changedData);
        itemView.getLocationInWindow(iArr2);
        contentView.addView(maskView, new ViewGroup.LayoutParams(contentView.getWidth(), contentView.getHeight()));
        maskView.addView(itemFakeVh.getItemView());
        if (Utils.isRTL()) {
            itemFakeVh.getItemView().setTranslationX((-iArr2[0]) + iArr[0]);
        } else {
            itemFakeVh.getItemView().setTranslationX(iArr2[0] - iArr[0]);
        }
        itemFakeVh.getItemView().setTranslationY((iArr2[1] - iArr[1]) - contentView.getPaddingTop());
        maskView.setClipChildren(false);
        itemFakeVh.startCheckAnim();
        final SparkFrameLayout sparkFrameLayout = (SparkFrameLayout) itemFakeVh.getItemView().findViewById(R.id.item_content_root);
        sparkFrameLayout.setClipChildren(false);
        final TodoAnimHelper$doAllFinishAnim$listener$1 todoAnimHelper$doAllFinishAnim$listener$1 = new TodoAnimHelper$doAllFinishAnim$listener$1(listAdapter, listView, contentView, maskView, processListener);
        sparkFrameLayout.post(new Runnable() { // from class: com.miui.todo.utils.TodoAnimHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TodoAnimHelper.doAllFinishAnim$lambda$1(sparkFrameLayout, todoAnimHelper$doAllFinishAnim$listener$1, processListener);
            }
        });
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass4(null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.todo.utils.TodoAnimHelper$doAllFinishAnim$1, reason: invalid class name */
    /* JADX INFO: compiled from: TodoAnimHelper.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.todo.utils.TodoAnimHelper$doAllFinishAnim$1", f = "TodoAnimHelper.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ TodoEntity $changedData;
        final /* synthetic */ int $position;
        final /* synthetic */ IBaseTodoListContract.View $todoListView;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(IBaseTodoListContract.View view, TodoEntity todoEntity, int i, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$todoListView = view;
            this.$changedData = todoEntity;
            this.$position = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$todoListView, this.$changedData, this.$position, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Thread.currentThread().setName("todoAniWorker");
            this.$todoListView.finishTodoInData(this.$changedData, this.$position);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doAllFinishAnim$lambda$1(SparkFrameLayout sparkFrameLayout, Animator.AnimatorListener animatorListener, Listener listener) {
        sparkFrameLayout.startSparkAnim(animatorListener, null);
        if (listener != null) {
            listener.onStart(sparkFrameLayout.doneSparkAnim);
        }
    }

    /* JADX INFO: renamed from: com.miui.todo.utils.TodoAnimHelper$doAllFinishAnim$4, reason: invalid class name */
    /* JADX INFO: compiled from: TodoAnimHelper.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.todo.utils.TodoAnimHelper$doAllFinishAnim$4", f = "TodoAnimHelper.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Thread.currentThread().setName("todoAniWorker");
            HapticFeedbackTool.INSTANCE.getInstance().performFinishAllTodo();
            AudioUtils.playAllFinishRingtone();
            return Unit.INSTANCE;
        }
    }
}
