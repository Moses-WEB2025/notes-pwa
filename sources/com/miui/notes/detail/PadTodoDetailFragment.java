package com.miui.notes.detail;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SpringItemTouchHelper;
import com.miui.common.record.AudioInputListener;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.SoftKeyboardHelper;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.common.tool.softinputhelper.KeyboardMarginProcessor;
import com.miui.common.view.BaseAudioCreatePanel;
import com.miui.common.view.DoneSparkAnim;
import com.miui.common.view.IPopMenu;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.SyncStateController;
import com.miui.notes.ui.view.HomepageSpringBackLayout;
import com.miui.pad.feature.todo.edit.PadTodoEditController;
import com.miui.pad.feature.todo.list.PadTodoChildItemVh;
import com.miui.pad.feature.todo.list.PadTodoItemForAllFinishVh;
import com.miui.pad.feature.todo.list.PadTodoListAdapter;
import com.miui.pad.feature.todo.list.PadTodoListPresenter;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.EditableRecyclerViewWrapper;
import com.miui.todo.base.OnDragItemStateListener;
import com.miui.todo.base.ProTodoRecyclerView;
import com.miui.todo.base.TodoLayoutManager;
import com.miui.todo.base.TodoListOptionMenu;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
import com.miui.todo.base.todolist.IBaseTodoListContract;
import com.miui.todo.constant.TodoIntent;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.DataGroupInfo;
import com.miui.todo.feature.todoedit.BaseTodoEditController;
import com.miui.todo.feature.todolist.SubTodoListListener;
import com.miui.todo.feature.todolist.TodoItemOperationListener;
import com.miui.todo.feature.todolist.TodoListContract;
import com.miui.todo.feature.todolist.TodoListItemTouchHelperCallback;
import com.miui.todo.feature.todolist.TodoSearchCallback;
import com.miui.todo.utils.SchedulerProvider;
import com.miui.todo.utils.TodoAnimHelper;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.RemindTimePickerDialog;
import com.miui.todo.view.TodoAudioCreateWindow;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.lang.ref.WeakReference;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.IVisibleStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AlertDialog;
import miuix.core.util.MiuiBlurUtils;
import miuix.miuixbasewidget.widget.EmptyStateView;
import miuix.navigator.Navigator;
import miuix.recyclerview.widget.MiuiScaleItemAnimator;
import miuix.responsive.map.ScreenSpec;
import miuix.springback.view.SpringBackLayout;
import miuix.view.EditActionMode;
import miuix.view.animation.CubicEaseInOutInterpolator;

/* JADX INFO: loaded from: classes4.dex */
public class PadTodoDetailFragment extends BaseTodoDetailFragment implements TodoListContract.View, SearchView.OnQueryTextListener, TodoSearchCallback.OnSearchListener {
    private static final String TAG = "PadTodoDetailFragment";
    private HomeViewModel homeViewModel;
    private boolean isInFlowAnim;
    protected ActionMode mActionMode;
    private TodoAudioCreateWindow mAudioCreatePanel;
    private BaseListAdapterBindContext mBindContext;
    protected View mBlankContainer;
    private EmptyStateView mBlankView;
    private View mBtnContentAdd;
    private Rect mContentInset;
    private int mCurrentContentAccessibilityImportant;
    protected AlertDialog mDeleteTodoDialog;
    protected Handler mHandler;
    private ItemTouchHelper mItemTouchHelper;
    private TodoListItemTouchHelperCallback mItemTouchHelperCallBack;
    protected ViewGroup mListContainer;
    private TodoListContract.Presenter mPresenter;
    private ObservableEmitter<Boolean> mRemindTimeSettingDialogEmitter;
    private View mRootView;
    private TodoLayoutManager mRvLinearLayoutManager;
    protected ProTodoRecyclerView mRvTodo;
    protected EditableRecyclerViewWrapper mRvTodoWrapper;
    protected TextView mSearchResult;
    protected View mSearchResultGroup;
    protected SpringBackLayout mSpringBackLayout;
    private SyncStateController mSyncStateController;
    private RemindTimePickerDialog mTimePickerDialog;
    protected PadTodoListAdapter mTodoListAdapter;
    private TodoListOptionMenu mTodoListOptionMenu;
    private OnBackPressedCallback onBackPressedCallback;
    private int mTouchTodoItemPosFromRightClick = -1;
    private long mWorkingTodoIdFromIntent = -1;
    private boolean isNewTodo = false;
    float mSearchModePaddingTopExtra = 0.0f;
    private String mIntentAction = null;
    private long mLastClickAudioBtnTime = -1;
    private Runnable mPendingIntentTask = null;
    private DoneSparkAnim mRunningSparkAnim = null;
    private Runnable mPendingDataEmptyTask = null;
    private final BroadcastReceiver mTodoDoneActionReceiver = new BroadcastReceiver() { // from class: com.miui.notes.detail.PadTodoDetailFragment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (TodoIntent.ACTION_DONE_TODO.equals(intent.getAction())) {
                if (PadTodoDetailFragment.this.mRvTodo != null) {
                    if (PadTodoDetailFragment.this.mRvTodo.getChildCount() < 1) {
                        AiActionUtil.asyncNotifyResult(-1);
                        return;
                    }
                    int intExtra = intent.getIntExtra(TodoIntent.EXTRA_DONE_TODO_POSITION, 0);
                    RecyclerView.LayoutManager layoutManager = PadTodoDetailFragment.this.mRvTodo.getLayoutManager();
                    if (layoutManager != null && (layoutManager instanceof LinearLayoutManager)) {
                        intExtra += ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    }
                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = PadTodoDetailFragment.this.mRvTodo.findViewHolderForAdapterPosition(intExtra);
                    if (viewHolderFindViewHolderForAdapterPosition != null && (viewHolderFindViewHolderForAdapterPosition instanceof BaseTodoChildItemVh)) {
                        View viewFindViewById = viewHolderFindViewHolderForAdapterPosition.itemView.findViewById(R.id.checkbox_mask);
                        if (!((CheckBox) viewHolderFindViewHolderForAdapterPosition.itemView.findViewById(android.R.id.checkbox)).isChecked()) {
                            viewFindViewById.callOnClick();
                            AiActionUtil.asyncNotifyResult(0);
                            return;
                        } else {
                            AiActionUtil.asyncNotifyResult(-1);
                            return;
                        }
                    }
                    AiActionUtil.asyncNotifyResult(-1);
                    return;
                }
                AiActionUtil.asyncNotifyResult(-1);
            }
        }
    };
    private BaseAudioCreatePanel.StateCallback mAudioPanelCallBack = new BaseAudioCreatePanel.StateCallback() { // from class: com.miui.notes.detail.PadTodoDetailFragment.2
        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onAnimUpdate(float f) {
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onPrepareRecord() {
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onShow() {
            PadTodoDetailFragment.this.mPresenter.enterAudioEditMode();
            if (PadTodoDetailFragment.this.getActivity() != null) {
                PadTodoDetailFragment.this.getActivity().getWindow().addFlags(128);
            }
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onToast(String str) {
            Toast.makeText(PadTodoDetailFragment.this.getContext(), str, 0).show();
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onCompleteRecord() {
            PadTodoDetailFragment.this.mPresenter.exitEditMode(true, false);
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onCancelRecord() {
            PadTodoDetailFragment.this.mPresenter.exitEditMode(false, false);
        }

        @Override // com.miui.common.view.BaseAudioCreatePanel.StateCallback
        public void onDismiss() {
            PadTodoDetailFragment.this.mAudioCreatePanel.dismiss();
            if (PadTodoDetailFragment.this.getActivity() != null) {
                PadTodoDetailFragment.this.getActivity().getWindow().clearFlags(128);
            }
        }
    };
    private SubTodoListListener mSubTodoListListener = new SubTodoListListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.3
        @Override // com.miui.todo.feature.todolist.SubTodoListListener
        public void onDataChanged(TodoEntity todoEntity, int i, View view) {
            if (!PadTodoDetailFragment.this.isAdded() || PadTodoDetailFragment.this.mRvTodo.isLayoutSuppressed() || todoEntity == null) {
                return;
            }
            PadTodoDetailFragment.this.mPresenter.updateTodo(todoEntity, PadTodoDetailFragment.this.mTodoListAdapter.getItemInfo(i).mSubPosition, false);
            if (PadTodoDetailFragment.this.mInSearchMode) {
                return;
            }
            PadTodoDetailFragment.this.refreshTodoList();
        }

        @Override // com.miui.todo.feature.todolist.SubTodoListListener
        public void onDataFinishStateChanged(TodoEntity todoEntity, int i, View view, boolean z) {
            if (PadTodoDetailFragment.this.isAdded() && !PadTodoDetailFragment.this.mRvTodo.isLayoutSuppressed() && z) {
                if (!LiteUtils.isLiteOrMiddle() && PadTodoDetailFragment.this.mTodoListAdapter != null && PadTodoDetailFragment.this.mTodoListAdapter.isAllTodoWillFinished() && todoEntity.getRemindRepeatType() == 0) {
                    PadTodoDetailFragment.this.isInFlowAnim = true;
                    view.findViewById(R.id.ll_expand_group).setClickable(false);
                    PadTodoDetailFragment.this.doAllFinishAnim(todoEntity, i, view);
                } else {
                    if (!PadTodoDetailFragment.this.mRvTodo.isComputingLayout()) {
                        PadTodoDetailFragment.this.mRvTodo.suppressLayout(true);
                    }
                    view.findViewById(R.id.ll_expand_group).setClickable(false);
                    PadTodoDetailFragment.this.doFinishAnim(todoEntity, i, view);
                }
            }
        }

        @Override // com.miui.todo.feature.todolist.SubTodoListListener
        public void scroll(int i, int i2, int i3) {
            int windowHeight = UIUtils.getWindowHeight(NoteApp.getInstance());
            int realNavigationBarHeight = UIUtils.getRealNavigationBarHeight(PadTodoDetailFragment.this.getThisActivity());
            Logger.INSTANCE.d(PadTodoDetailFragment.TAG, "scroll: y = " + i2 + " todoItemHeight = " + i3 + " windowHeight = " + windowHeight + " navigationBarHeight = " + realNavigationBarHeight);
            if (i3 + i2 > windowHeight - realNavigationBarHeight) {
                PadTodoDetailFragment.this.mRvTodo.smoothScrollBy(i, i2 - ((int) PadTodoDetailFragment.this.getResources().getDimension(R.dimen.pad_todo_right_list_margin_top)));
            }
        }
    };
    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() { // from class: com.miui.notes.detail.PadTodoDetailFragment.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (PadTodoDetailFragment.this.isAdded() && PadTodoDetailFragment.this.isShowEdit()) {
                PadTodoDetailFragment.this.exitEdit(true);
            }
        }
    };
    protected KeyboardHelper mSearchKeyboardHelper = null;
    private boolean mCanItemLongClick = true;
    private AudioInputListener mEditViewAudioInputListener = new AudioInputListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.16
        @Override // com.miui.common.record.AudioInputListener
        public void onAppendText(String str, String str2) {
            if (PadTodoDetailFragment.this.mPresenter.getMWorkingTodo() == null) {
                Logger.INSTANCE.w(PadTodoDetailFragment.TAG, " mEditViewAudioInputListener->onAppendText workingTodo is null");
                return;
            }
            PadTodoDetailFragment.this.mPresenter.getMWorkingTodo().setContent(str);
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            PadTodoDetailFragment.this.mPresenter.getMWorkingTodo().setAudioFileName(str2);
        }
    };
    private OnDragItemStateListener mTouchDragItemStateListener = new OnDragItemStateListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.17
        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onCancelDrag(RecyclerView.ViewHolder viewHolder) {
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onEndDrag(RecyclerView.ViewHolder viewHolder) {
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        }
    };
    private OnDragItemStateListener mBtnDragItemStateListener = new OnDragItemStateListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.18
        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onEndDrag(RecyclerView.ViewHolder viewHolder) {
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            if (PadTodoDetailFragment.this.mTouchDragItemStateListener != null) {
                PadTodoDetailFragment.this.mTouchDragItemStateListener.onStartDrag(viewHolder);
            }
            if (PadTodoDetailFragment.this.mItemTouchHelper != null) {
                PadTodoDetailFragment.this.mItemTouchHelper.startDrag(viewHolder);
            }
        }

        @Override // com.miui.todo.base.OnDragItemStateListener
        public void onCancelDrag(RecyclerView.ViewHolder viewHolder) {
            if (PadTodoDetailFragment.this.mTouchDragItemStateListener != null) {
                PadTodoDetailFragment.this.mTouchDragItemStateListener.onCancelDrag(viewHolder);
            }
            if (PadTodoDetailFragment.this.mItemTouchHelperCallBack != null) {
                PadTodoDetailFragment.this.mItemTouchHelperCallBack.onSelectedChanged(viewHolder, 0);
            }
        }
    };
    private TodoItemOperationListener mItemOperationListener = new TodoItemOperationListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.19
        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public Observable<Boolean> onMoved(final TodoEntity todoEntity, final int i, final int i2) {
            return Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.miui.notes.detail.PadTodoDetailFragment.19.1
                @Override // io.reactivex.ObservableOnSubscribe
                public void subscribe(ObservableEmitter<Boolean> observableEmitter) throws Exception {
                    PadTodoDetailFragment.this.mRemindTimeSettingDialogEmitter = observableEmitter;
                    if (PadTodoDetailFragment.this.mPresenter.moveTodo(todoEntity, i, i2)) {
                        return;
                    }
                    observableEmitter.onNext(true);
                    observableEmitter.onComplete();
                }
            });
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public boolean onSimpleMoved(int i, int i2) {
            if (!PadTodoDetailFragment.this.mRvTodoWrapper.isInActionMode()) {
                return true;
            }
            PadTodoDetailFragment.this.mRvTodoWrapper.swapCheckedStates(i, i2);
            return true;
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onLongClick(View view) {
            if (view == null || PadTodoDetailFragment.this.mRvTodoWrapper.isInActionMode() || !PadTodoDetailFragment.this.canEnterEditMode() || !PadTodoDetailFragment.this.mCanItemLongClick) {
                return;
            }
            PadTodoDetailFragment.this.mItemTouchHelperCallBack.setEnableDrag(true);
            PadTodoDetailFragment.this.mRvTodoWrapper.setItemChecked(view, true);
            PadTodoDetailFragment.this.mRvTodoWrapper.startActionMode(PadTodoDetailFragment.this.createEditModeCallback(), PadTodoDetailFragment.this);
            PadTodoDetailFragment.this.mTodoListAdapter.notifyDataSetChanged();
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), PadTodoDetailFragment.this.mTodoListAdapter.getUnfinishItemCount());
            if (PadTodoDetailFragment.this.mBtnDragItemStateListener != null) {
                PadTodoDetailFragment.this.mBtnDragItemStateListener.onStartDrag(PadTodoDetailFragment.this.mRvTodo.getChildViewHolder(view));
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onClick(View view) {
            TodoEntity childItem;
            if (PadTodoDetailFragment.this.mRvTodo.isLayoutSuppressed()) {
                return;
            }
            PadTodoDetailFragment.this.cancelMarkTodo();
            if (PadTodoDetailFragment.this.mRvTodoWrapper.isInActionMode()) {
                onSelected(view);
                return;
            }
            if (PadTodoDetailFragment.this.mTodoListAdapter.isInDrag() || PadTodoDetailFragment.this.mTodoListAdapter == null || PadTodoDetailFragment.this.mPresenter.getMIsInEdit()) {
                return;
            }
            int childLayoutPosition = PadTodoDetailFragment.this.mRvTodo.getChildLayoutPosition(view);
            PadTodoDetailFragment padTodoDetailFragment = PadTodoDetailFragment.this;
            padTodoDetailFragment.mEditingTodoVh = (PadTodoChildItemVh) padTodoDetailFragment.mRvTodo.getChildViewHolder(view);
            int itemViewType = PadTodoDetailFragment.this.mTodoListAdapter.getItemViewType(childLayoutPosition);
            if ((itemViewType == 0 || itemViewType == 3) && (childItem = PadTodoDetailFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition)) != null) {
                PadTodoDetailFragment.this.mPresenter.enterEditMode(childItem, childLayoutPosition, false, false);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onSelected(View view) {
            if (PadTodoDetailFragment.this.mRvTodoWrapper.isInActionMode()) {
                PadTodoDetailFragment.this.mRvTodoWrapper.toggleItemChecked(view);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onDelete(View view) {
            int childLayoutPosition = PadTodoDetailFragment.this.mRvTodo.getChildLayoutPosition(view);
            PadTodoDetailFragment.this.cancelMarkTodo();
            TodoEntity childItem = PadTodoDetailFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition);
            if (childItem != null) {
                PadTodoDetailFragment.this.mRvTodo.setMenuSwiping(false);
                TodoUtils.setSwiping(false, "onDel");
                PadTodoDetailFragment.this.mPresenter.deleteTodo(childItem, childLayoutPosition);
                PadTodoDetailFragment.this.mTodoListAdapter.deleteData(childLayoutPosition, 1);
            }
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onFinished(View view) {
            int childLayoutPosition = PadTodoDetailFragment.this.mRvTodo.getChildLayoutPosition(view);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if ((PadTodoDetailFragment.this.mBindContext.pLastClickCheckTime == null || jCurrentTimeMillis - PadTodoDetailFragment.this.mBindContext.pLastClickCheckTime.longValue() > 1000) && PadTodoDetailFragment.this.mRvTodo.isAnimating() && PadTodoDetailFragment.this.mRvTodo.getItemAnimator() != null) {
                PadTodoDetailFragment.this.mRvTodo.getItemAnimator().endAnimations();
            }
            PadTodoDetailFragment.this.mBindContext.pLastClickCheckTime = Long.valueOf(jCurrentTimeMillis);
            if (PadTodoDetailFragment.this.mRvTodo.isLayoutSuppressed() || PadTodoDetailFragment.this.mRvTodo.isAnimating()) {
                return;
            }
            if (!PadTodoDetailFragment.this.mRvTodo.getChildViewHolder(view).isRecyclable()) {
                Logger.INSTANCE.d("DoFinish", " item recycleable is false, don't work");
                return;
            }
            int lastMarkIndex = PadTodoDetailFragment.this.mBindContext.getLastMarkIndex();
            if (lastMarkIndex != -1) {
                PadTodoDetailFragment.this.mBindContext.setLastMarkIndex(-1);
            }
            TodoEntity childItem = PadTodoDetailFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition);
            if (childItem != null) {
                childItem.setIsFinish(1);
                if (!LiteUtils.isLiteOrMiddle() && PadTodoDetailFragment.this.mTodoListAdapter.isAllTodoWillFinished() && childItem.getRemindRepeatType() == 0) {
                    if (lastMarkIndex != -1) {
                        PadTodoDetailFragment.this.mTodoListAdapter.notifyItemChanged(lastMarkIndex);
                    }
                    PadTodoDetailFragment.this.isInFlowAnim = true;
                    PadTodoDetailFragment.this.doAllFinishAnim(childItem, childLayoutPosition, view);
                    return;
                }
                PadTodoDetailFragment.this.doFinishAnim(childItem, childLayoutPosition, view);
                return;
            }
            if (lastMarkIndex != -1) {
                PadTodoDetailFragment.this.mTodoListAdapter.notifyItemChanged(lastMarkIndex);
            }
            if (PadTodoDetailFragment.this.mRvTodo.isComputingLayout()) {
                return;
            }
            PadTodoDetailFragment.this.mRvTodo.suppressLayout(false);
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onUnFinished(View view) {
            final int childLayoutPosition = PadTodoDetailFragment.this.mRvTodo.getChildLayoutPosition(view);
            final DataGroupInfo itemInfo = PadTodoDetailFragment.this.mTodoListAdapter.getItemInfo(childLayoutPosition);
            view.postDelayed(new Runnable() { // from class: com.miui.notes.detail.PadTodoDetailFragment.19.2
                @Override // java.lang.Runnable
                public void run() {
                    if (PadTodoDetailFragment.this.mTodoListAdapter != null) {
                        TodoEntity childItem = PadTodoDetailFragment.this.mTodoListAdapter.getChildItem(childLayoutPosition);
                        if (childItem != null) {
                            PadTodoDetailFragment.this.mPresenter.setTodoFinishState(childItem, itemInfo.mSubPosition, childLayoutPosition);
                        }
                        if (!PadTodoDetailFragment.this.mRvTodo.isComputingLayout()) {
                            PadTodoDetailFragment.this.mRvTodo.suppressLayout(false);
                        }
                        if (PadTodoDetailFragment.this.mInSearchMode) {
                            return;
                        }
                        PadTodoDetailFragment.this.refreshTodoList();
                    }
                }
            }, 50L);
        }

        @Override // com.miui.todo.feature.todolist.TodoItemOperationListener
        public void onToggleGroup(View view) {
            PadTodoDetailFragment.this.mTodoListAdapter.toggleGroupExpand(PadTodoDetailFragment.this.mRvTodo.getChildLayoutPosition(view));
        }
    };
    private View.OnDragListener mGroupDragListener = new View.OnDragListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.20
        boolean dragedResult = false;

        @Override // android.view.View.OnDragListener
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            if (action == 3) {
                try {
                    this.dragedResult = PadTodoDetailFragment.this.mTodoListAdapter.dragItemBetweenGroup((DataGroupInfo) dragEvent.getLocalState(), PadTodoDetailFragment.this.mRvTodo.getChildLayoutPosition(view));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return true;
                }
            }
            if (action == 4) {
                ((TextView) view.findViewById(R.id.tv_time)).setTextColor(TodoUtils.getColor(R.color.widget_text));
                return true;
            }
            if (action == 5) {
                ((TextView) view.findViewById(R.id.tv_time)).setTextColor(TodoUtils.getColor(R.color.pc_todo_item_menu_delete_color));
                return true;
            }
            if (action != 6) {
                return true;
            }
            ((TextView) view.findViewById(R.id.tv_time)).setTextColor(TodoUtils.getColor(R.color.widget_text));
            return true;
        }
    };

    private interface EditModeCallBackHandler {
        boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

        boolean onCreateActionMode(ActionMode actionMode, Menu menu);

        void onDestroyActionMode(ActionMode actionMode);

        void updateActionItems();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onAddNewTodo(boolean z) {
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String str) {
        return true;
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String str) {
        return false;
    }

    @Override // com.miui.notes.detail.BaseTodoDetailFragment
    protected BaseTodoEditController createEditController(TodoBaseEntity todoBaseEntity) {
        return new PadTodoEditController(getActivity(), this.mPresenter.getEditViewClickListener(), this.mPresenter.getTodoEditorListener(), todoBaseEntity == null, getResponsiveState().getType());
    }

    @Override // miuix.appcompat.app.Fragment, miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int i) {
        super.onExtraPaddingChanged(i);
        Logger.INSTANCE.d(TAG, "onExtraPaddingChanged:" + i);
        SpringBackLayout springBackLayout = this.mSpringBackLayout;
        springBackLayout.setPadding(i, springBackLayout.getPaddingTop(), i, this.mSpringBackLayout.getPaddingBottom());
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        Logger.INSTANCE.d(TAG, "onCreate");
        super.onCreate(bundle);
        this.mHandler = new Handler();
        this.homeViewModel = (HomeViewModel) new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        this.mBindContext = new BaseListAdapterBindContext();
        setThemeRes(R.style.NavigatorSecondaryContentTheme_Todo);
        if (getActivity() != null) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
            if (Utils.isAboveSDK34()) {
                getActivity().registerReceiver(this.mScreenOffReceiver, intentFilter, 2);
            } else {
                getActivity().registerReceiver(this.mScreenOffReceiver, intentFilter);
            }
        }
        new PadTodoListPresenter(this, SchedulerProvider.getInstance());
        IntentFilter intentFilter2 = new IntentFilter(TodoIntent.ACTION_DONE_TODO);
        if (getActivity() != null) {
            if (Utils.isAboveSDK34()) {
                getActivity().registerReceiver(this.mTodoDoneActionReceiver, intentFilter2, 2);
            } else {
                getActivity().registerReceiver(this.mTodoDoneActionReceiver, intentFilter2);
            }
        }
        this.homeViewModel.getTodoKeyboardChange().observe(this, new Observer<Boolean>() { // from class: com.miui.notes.detail.PadTodoDetailFragment.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean bool) {
                if (bool.booleanValue()) {
                    if (PadTodoDetailFragment.this.getActivity() == null) {
                        return;
                    }
                    ViewGroup viewGroup = (ViewGroup) PadTodoDetailFragment.this.getActivity().getWindow().getDecorView();
                    PadTodoDetailFragment.this.mSearchKeyboardHelper = new KeyboardHelper(PadTodoDetailFragment.this.mSpringBackLayout, viewGroup, new KeyboardMarginProcessor() { // from class: com.miui.notes.detail.PadTodoDetailFragment.5.1
                        @Override // com.miui.common.tool.softinputhelper.KeyboardMarginProcessor
                        protected void updateViewMarginBottom(View view, WindowInsets windowInsets, int i) {
                            super.updateViewMarginBottom(view, windowInsets, Math.max(i, 0));
                        }
                    });
                    PadTodoDetailFragment.this.mSearchKeyboardHelper.setForceMode(false);
                    return;
                }
                if (PadTodoDetailFragment.this.mSearchKeyboardHelper != null) {
                    PadTodoDetailFragment.this.mSearchKeyboardHelper.release();
                    PadTodoDetailFragment.this.mSearchKeyboardHelper = null;
                }
            }
        });
        this.onBackPressedCallback = new OnBackPressedCallback(true) { // from class: com.miui.notes.detail.PadTodoDetailFragment.6
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                Logger.INSTANCE.d(PadTodoDetailFragment.TAG, "onBackPressedDispatcher, handleOnBackPressed");
                if (PadTodoDetailFragment.this.onBackPressed()) {
                    Logger.INSTANCE.d(PadTodoDetailFragment.TAG, "handleOnBackPressed, fragment consumed the back event");
                } else if (PadTodoDetailFragment.this.getActivity() != null) {
                    Logger.INSTANCE.d(PadTodoDetailFragment.TAG, "handleOnBackPressed, fragment without consumption back event, consumed by activity");
                    setEnabled(false);
                    PadTodoDetailFragment.this.getActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        String action;
        super.onViewInflated(view, bundle);
        setExtraHorizontalPaddingEnable(true);
        if (isAdded()) {
            Intent intent = getActivity().getIntent();
            if (intent != null && (action = intent.getAction()) != null && (TodoIntent.ACTION_SHORTCUT_INSERT_OR_EDIT.equals(action) || TodoIntent.ACTION_INSERT_OR_EDIT.equals(action))) {
                this.mIntentAction = action;
            }
            initActionBar();
            setBottomMenuMode(2);
        }
        if (getArguments() != null) {
            this.mTodoListType = getArguments().getInt("todo_list_type", 0);
        }
        TodoListContract.Presenter presenter = this.mPresenter;
        if (presenter != null) {
            presenter.setShowListType(this.mTodoListType, false);
        }
        if (this.mTodoListType == 1) {
            this.mBtnContentAdd.setVisibility(8);
        }
    }

    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle bundle) {
        super.onUpdateArguments(bundle);
        if (bundle != null) {
            int i = bundle.getInt("todo_list_type");
            String string = bundle.getString(BaseTodoDetailFragment.SEARCH_STRING);
            this.mCanItemLongClick = bundle.getBoolean(BaseTodoDetailFragment.CAN_ITEM_LONG_CLICK);
            boolean z = bundle.getBoolean(BaseTodoDetailFragment.FORCE_EXIT, true);
            if (i == 3) {
                enterSearchMode(string);
                return;
            }
            if (this.mInSearchMode || TextUtils.isEmpty(string)) {
                exitSearchMode(string, z);
            }
            refreshTodoData(i);
            if (i == 1) {
                showCreateBtn(false);
            }
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.INSTANCE.i(TAG, "onResume");
        if (this.mIsShow) {
            this.mPresenter.subscribe();
        }
        if (this.mTodoEditController != null) {
            this.mTodoEditController.onResume();
        }
        if (getActivity() != null) {
            this.onBackPressedCallback.setEnabled(true);
            getActivity().getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
        }
    }

    @Override // com.miui.notes.detail.BaseTodoDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        dismissDialog();
        this.onBackPressedCallback.remove();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        TodoListContract.Presenter presenter = this.mPresenter;
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Logger.INSTANCE.d(TAG, "onInflateView");
        View viewInflate = layoutInflater.inflate(R.layout.pad_todo_fragment, viewGroup, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onInflateView$0(view);
            }
        });
        ((ViewGroup) this.mRootView).setMotionEventSplittingEnabled(false);
        initView(viewInflate);
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInflateView$0(View view) {
        finishActionMode();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        ProTodoRecyclerView proTodoRecyclerView = this.mRvTodo;
        if (proTodoRecyclerView != null) {
            proTodoRecyclerView.removeCallbacks(this.mPendingDataEmptyTask);
            this.mRvTodo.setOnRightClickListener(null);
            this.mRvTodo.getRecycledViewPool().clear();
        }
        this.mPendingDataEmptyTask = null;
        exitEdit(false);
        this.mSyncStateController = null;
        PermissionUtils.releaseListeners();
        KeyboardHelper keyboardHelper = this.mSearchKeyboardHelper;
        if (keyboardHelper != null) {
            keyboardHelper.release();
            this.mSearchKeyboardHelper = null;
        }
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Logger.INSTANCE.d(TAG, "onDestroy");
        this.mBtnDragItemStateListener = null;
        this.mItemOperationListener = null;
        this.mSubTodoListListener = null;
        if (getActivity() != null) {
            getActivity().unregisterReceiver(this.mScreenOffReceiver);
            getActivity().unregisterReceiver(this.mTodoDoneActionReceiver);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        if (this.mTodoEditController != null) {
            this.mTodoEditController.onMultiWindowModeChanged();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Logger.INSTANCE.d(TAG, "onConfigurationChanged getNavigationMode: " + Navigator.get(this).getNavigationMode());
        PadTodoListAdapter padTodoListAdapter = this.mTodoListAdapter;
        if (padTodoListAdapter != null) {
            padTodoListAdapter.setIsSingleColumn(Navigator.get(this).getNavigationMode() == Navigator.Mode.C ? 1 : 0);
        }
        if (this.mTodoEditController != null) {
            this.mTodoEditController.onConfigurationChanged();
        }
    }

    @Override // com.miui.notes.detail.BaseTodoDetailFragment
    protected void refreshTodoData(final int i) {
        ProTodoRecyclerView proTodoRecyclerView = this.mRvTodo;
        if (proTodoRecyclerView == null) {
            return;
        }
        if (!proTodoRecyclerView.isComputingLayout()) {
            this.mRvTodo.suppressLayout(true);
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$refreshTodoData$1(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshTodoData$1(int i) {
        if (isAdded()) {
            finishActionMode();
            exitEdit(false);
            this.mPresenter.setShowListType(i, true);
            if (!this.mRvTodo.isComputingLayout()) {
                this.mRvTodo.suppressLayout(false);
            }
            onUpdateView(i);
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            if (!isAdded()) {
                return false;
            }
            getAppCompatActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public boolean onBackPressed() {
        if (this.mActionMode != null) {
            return finishActionMode();
        }
        return false;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        super.onResponsiveLayout(configuration, screenSpec, z);
        if (this.mTodoEditController != null) {
            this.mTodoEditController.updateScreenSpec(screenSpec.type);
        }
    }

    private void initView(View view) {
        SyncStateController syncStateController;
        this.mListContainer = (ViewGroup) view.findViewById(R.id.list_container);
        SpringBackLayout springBackLayout = (SpringBackLayout) view.findViewById(R.id.scrollable_view_drawer);
        this.mSpringBackLayout = springBackLayout;
        springBackLayout.setNestedScrollingEnabled(true);
        this.mBlankContainer = view.findViewById(R.id.sv_blank);
        EmptyStateView emptyStateView = (EmptyStateView) view.findViewById(R.id.todo_list_blank_view);
        this.mBlankView = emptyStateView;
        emptyStateView.setIcon(R.drawable.todo_blank_icon);
        this.mBlankView.setTitle(getString(R.string.data_empty_todo));
        this.mSearchResultGroup = view.findViewById(R.id.todo_search_result_group);
        this.mSearchResult = (TextView) view.findViewById(R.id.todo_search_result);
        ProTodoRecyclerView proTodoRecyclerView = (ProTodoRecyclerView) view.findViewById(R.id.recycler_view);
        this.mRvTodo = proTodoRecyclerView;
        proTodoRecyclerView.setImportantForAccessibility(2);
        if (this.mRvTodo.getRootView() != null) {
            this.mRvTodo.getRootView().setImportantForAccessibility(2);
        }
        this.mRvTodo.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        ProTodoRecyclerView proTodoRecyclerView2 = this.mRvTodo;
        proTodoRecyclerView2.setPadding(proTodoRecyclerView2.getPaddingLeft(), 0, this.mRvTodo.getPaddingRight(), this.mRvTodo.getPaddingBottom());
        TodoLayoutManager todoLayoutManager = new TodoLayoutManager(getActivity());
        this.mRvLinearLayoutManager = todoLayoutManager;
        this.mRvTodo.setLayoutManager(todoLayoutManager);
        this.mRvTodo.setItemAnimator(new MiuiScaleItemAnimator());
        this.mRvTodo.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initView$2(view2);
            }
        });
        this.mRvTodo.setOnRightClickListener(new ProTodoRecyclerView.OnRightClickListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda5
            @Override // com.miui.todo.base.ProTodoRecyclerView.OnRightClickListener
            public final void onRightClick(float f, float f2, int i) {
                this.f$0.lambda$initView$3(f, f2, i);
            }
        });
        this.mRvTodo.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.8
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (NoteApp.getInstance().getTodoNumLimit() - PadTodoDetailFragment.this.mRvLinearLayoutManager.findFirstVisibleItemPosition() < 20) {
                    NoteApp.getInstance().addTodoNumLimit();
                    if (PadTodoDetailFragment.this.mPresenter != null) {
                        if (PadTodoDetailFragment.this.mInSearchMode) {
                            PadTodoDetailFragment.this.mPresenter.queryTodo(PadTodoDetailFragment.this.mBindContext.getSearchToken());
                        } else {
                            PadTodoDetailFragment.this.mPresenter.getAndUpdateDataToView();
                        }
                    }
                }
            }
        });
        this.mRvTodoWrapper = new EditableRecyclerViewWrapper(this.mRvTodo);
        initAdapterIfNeeded();
        this.mRvTodoWrapper.setAdapter(this.mTodoListAdapter);
        this.mBindContext.setEditableRecyclerViewWrapper(this.mRvTodoWrapper);
        this.mAudioCreatePanel = new TodoAudioCreateWindow(getActivity(), this.mAudioPanelCallBack, this.mEditViewAudioInputListener);
        WeakReference weakReference = new WeakReference(this.mRvTodo);
        HomepageSpringBackLayout homepageSpringBackLayout = (HomepageSpringBackLayout) getActivity().getWindow().getDecorView().findViewById(R.id.sbl_fragments);
        if (homepageSpringBackLayout != null) {
            homepageSpringBackLayout.setTodoRecyclerView((RecyclerView) weakReference.get());
        }
        if (PermissionUtils.supportNewPermissionStyle()) {
            if (PreferenceUtils.getAllowNetwork(getActivity()) && (syncStateController = this.mSyncStateController) != null) {
                syncStateController.onDataSetChanged();
            }
        } else if (!PreferenceUtils.isCTAAccepted(getContext())) {
            CTAManager.getInstance().addListener(new CTAManager.CTAListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.9
                @Override // com.miui.notes.cta.CTAManager.CTAListener
                public void onAccept() {
                    if (PadTodoDetailFragment.this.mSyncStateController != null) {
                        PadTodoDetailFragment.this.mSyncStateController.onDataSetChanged();
                    }
                }

                @Override // com.miui.notes.cta.CTAManager.CTAListener
                public void onReject() {
                    Logger.INSTANCE.w(PadTodoDetailFragment.TAG, "user deny to authorization");
                }
            });
        }
        View viewFindViewById = view.findViewById(R.id.todo_add);
        this.mBtnContentAdd = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initView$4(view2);
            }
        });
        LiveDataEventBus.with(LiveDataEventBus.CREATE_NEW_TODO, Boolean.class).observe(this, new Observer<Boolean>() { // from class: com.miui.notes.detail.PadTodoDetailFragment.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean bool) {
                Logger.INSTANCE.d(PadTodoDetailFragment.TAG, "CREATE_NEW_TODO  onChanged " + bool);
                if (!bool.booleanValue() || PadTodoDetailFragment.this.mBtnContentAdd == null) {
                    return;
                }
                PadTodoDetailFragment.this.mBtnContentAdd.callOnClick();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        finishActionMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(float f, float f2, int i) {
        if (isShowEdit()) {
            exitEdit(false);
            return;
        }
        if (this.mTodoListOptionMenu == null) {
            TodoListOptionMenu todoListOptionMenu = new TodoListOptionMenu(getContext());
            this.mTodoListOptionMenu = todoListOptionMenu;
            todoListOptionMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.7
                @Override // com.miui.common.view.IPopMenu.OnMenuListener
                public void onShow() {
                }

                @Override // com.miui.common.view.IPopMenu.OnMenuListener
                public void onDismiss() {
                    PadTodoDetailFragment.this.mTouchTodoItemPosFromRightClick = -1;
                    if (PadTodoDetailFragment.this.mRvTodoWrapper.isInActionMode()) {
                        return;
                    }
                    PadTodoDetailFragment.this.mRvTodoWrapper.clearChoices();
                }

                @Override // com.miui.common.view.IPopMenu.OnMenuListener
                public void onItemSelected(View view, int i2) {
                    int id = view.getId();
                    if (id != R.id.delete) {
                        if (id != R.id.multi_choice) {
                            return;
                        }
                        PadTodoDetailFragment.this.mRvTodoWrapper.startActionMode(PadTodoDetailFragment.this.createEditModeCallback(), PadTodoDetailFragment.this);
                    } else {
                        if (PadTodoDetailFragment.this.mRvTodo != null) {
                            PadTodoDetailFragment.this.mRvTodo.stopScroll();
                        }
                        PadTodoDetailFragment.this.deleteCheckedItems();
                    }
                }
            });
        }
        this.mTodoListOptionMenu.show(this.mRvTodo, new int[]{(int) f, (int) f2});
        this.mTouchTodoItemPosFromRightClick = i;
        this.mRvTodoWrapper.setItemChecked(i, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(View view) {
        if (isInEdit() || this.mTodoEditController != null) {
            return;
        }
        createCommonTodo();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        super.onContentInsetChanged(rect);
        Logger.INSTANCE.d(TAG, "todo detail onContentInsetChanged");
        this.mContentInset = rect;
        ProTodoRecyclerView proTodoRecyclerView = this.mRvTodo;
        proTodoRecyclerView.setPadding(proTodoRecyclerView.getPaddingLeft(), this.mRvTodo.getPaddingTop(), this.mRvTodo.getPaddingRight(), rect.bottom);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mBtnContentAdd.getLayoutParams();
        layoutParams.bottomMargin = rect.bottom + getResources().getDimensionPixelSize(R.dimen.pad_floating_action_bar_margin_bottom);
        this.mBtnContentAdd.setLayoutParams(layoutParams);
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
            actionBar.setExpandState(0);
            actionBar.setResizable(false);
            actionBar.setTitle("");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setEndView(null);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void onUpdateView(int i) {
        this.mTodoListType = i;
        Folme.clean(this.mBtnContentAdd);
        if (i == 0) {
            showCreateBtn(true);
            return;
        }
        DoneSparkAnim doneSparkAnim = this.mRunningSparkAnim;
        if (doneSparkAnim != null) {
            doneSparkAnim.cancel();
            this.mRunningSparkAnim = null;
        }
        showCreateBtn(false);
    }

    private void initAdapterIfNeeded() {
        if (this.mTodoListAdapter == null) {
            PadTodoListAdapter padTodoListAdapter = new PadTodoListAdapter(getActivity(), this.mSubTodoListListener);
            this.mTodoListAdapter = padTodoListAdapter;
            padTodoListAdapter.setIsSingleColumn(Navigator.get(this).getNavigationMode() == Navigator.Mode.C ? 1 : 0);
            this.mTodoListAdapter.setOnDragItemListener(this.mBtnDragItemStateListener, this.mTouchDragItemStateListener);
            this.mTodoListAdapter.setItemOperationListener(this.mItemOperationListener);
            this.mTodoListAdapter.setOnGroupDragItemListener(this.mGroupDragListener);
            this.mTodoListAdapter.setBindContext(this.mBindContext);
            this.mTodoListAdapter.setPresenter(this.mPresenter);
            this.mTodoListAdapter.notifyDataSetChanged();
            this.mItemTouchHelperCallBack = new TodoListItemTouchHelperCallback(this.mTodoListAdapter);
            SpringItemTouchHelper springItemTouchHelper = new SpringItemTouchHelper(this.mItemTouchHelperCallBack);
            this.mItemTouchHelper = springItemTouchHelper;
            springItemTouchHelper.attachToRecyclerView(this.mRvTodo);
        }
    }

    public boolean isInEdit() {
        return this.mPresenter.getMIsInEdit();
    }

    public boolean closeOpenedItem() {
        return this.mRvTodo.closeOpenedItem();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public Context getViewContext() {
        return getContext();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onEnterEditMode(TodoBaseEntity todoBaseEntity, int i, boolean z, boolean z2) {
        if (this.mEditingTodoVh != null) {
            this.mEditingTodoVh.itemView.findViewById(R.id.v_bg).setSelected(true);
        }
        if (this.mRootView != null && Utils.isAccessibilityEnable(getContext())) {
            View viewFindViewById = this.mRootView.getRootView().findViewById(R.id.fragment_content);
            View viewFindViewById2 = this.mRootView.getRootView().findViewById(R.id.action_bar_container);
            View viewFindViewById3 = this.mRootView.getRootView().findViewById(R.id.root_view);
            this.mCurrentContentAccessibilityImportant = viewFindViewById.getImportantForAccessibility();
            viewFindViewById.setImportantForAccessibility(4);
            viewFindViewById2.setImportantForAccessibility(4);
            viewFindViewById3.setImportantForAccessibility(4);
        }
        showTodoEditGroup(todoBaseEntity, z2);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onExitEditMode(int i, boolean z) {
        if (this.mEditingTodoVh != null) {
            this.mEditingTodoVh.itemView.findViewById(R.id.v_bg).setSelected(false);
            this.mEditingTodoVh = null;
        }
        if (this.mRootView != null && Utils.isAccessibilityEnable(getContext())) {
            View viewFindViewById = this.mRootView.getRootView().findViewById(R.id.fragment_content);
            View viewFindViewById2 = this.mRootView.getRootView().findViewById(R.id.action_bar_container);
            View viewFindViewById3 = this.mRootView.getRootView().findViewById(R.id.root_view);
            viewFindViewById.setImportantForAccessibility(this.mCurrentContentAccessibilityImportant);
            viewFindViewById2.setImportantForAccessibility(this.mCurrentContentAccessibilityImportant);
            viewFindViewById3.setImportantForAccessibility(this.mCurrentContentAccessibilityImportant);
        }
        refreshSearchResult();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void updateRemindCustomTag(boolean z, RemindTimeConfig remindTimeConfig) {
        if (this.mTodoEditController != null) {
            this.mTodoEditController.updateRemindCustomTag(z, remindTimeConfig);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void showRemindTimePickerDialog(Long l, int i) {
        if (isAdded()) {
            RemindTimePickerDialog remindTimePickerDialog = new RemindTimePickerDialog(getContext(), R.style.NoteTheme_RemindAlertDialog, new RemindTimePickerDialog.OnTimeSetListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.11
                @Override // com.miui.todo.view.RemindTimePickerDialog.OnTimeSetListener
                public void onCancel() {
                    PadTodoDetailFragment.this.mTimePickerDialog = null;
                }

                @Override // com.miui.todo.view.RemindTimePickerDialog.OnTimeSetListener
                public void onTimeSet(RemindTimePickerDialog remindTimePickerDialog2, RemindTimeConfig remindTimeConfig) {
                    PadTodoDetailFragment.this.mPresenter.setRemindTimeInEditMode(remindTimeConfig);
                }
            }, i);
            this.mTimePickerDialog = remindTimePickerDialog;
            remindTimePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$showRemindTimePickerDialog$5(dialogInterface);
                }
            });
            if (l != null) {
                this.mTimePickerDialog.update(l.longValue());
            }
            WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(this.mRootView);
            if (rootWindowInsets != null && rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime())) {
                SoftKeyboardHelper.listenKeyboardClose(getContext(), this.mRootView, new SoftKeyboardHelper.KeyboardCloseListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.12
                    @Override // com.miui.common.tool.SoftKeyboardHelper.KeyboardCloseListener
                    public void onEnd() {
                        if (PadTodoDetailFragment.this.mTimePickerDialog != null) {
                            PadTodoDetailFragment.this.mTimePickerDialog.show();
                        }
                    }
                });
                if (this.mTodoEditController != null) {
                    this.mTodoEditController.hideSoftInput();
                    return;
                }
                return;
            }
            this.mTimePickerDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRemindTimePickerDialog$5(DialogInterface dialogInterface) {
        if (this.mTodoEditController != null) {
            this.mTodoEditController.showSoftInput();
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public PadTodoListAdapter getListAdapter() {
        return this.mTodoListAdapter;
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public RecyclerView getListView() {
        return this.mRvTodo;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void onMarkTodo(final int i) {
        if (getView() != null) {
            getView().postDelayed(new Runnable() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onMarkTodo$6(i);
                }
            }, 50L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMarkTodo$6(int i) {
        if (!isAdded() || this.mRvLinearLayoutManager == null) {
            return;
        }
        this.mBindContext.setMarkIndex(i);
        this.mBindContext.setLastMarkIndex(i);
        this.mRvLinearLayoutManager.scrollToPositionWithOffset(i, 0);
        PadTodoListAdapter padTodoListAdapter = this.mTodoListAdapter;
        if (padTodoListAdapter != null) {
            padTodoListAdapter.notifyDataSetChanged();
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodo(final int i, final boolean z) {
        PadTodoListAdapter padTodoListAdapter = this.mTodoListAdapter;
        if (padTodoListAdapter == null) {
            return;
        }
        padTodoListAdapter.updateData(i);
        Logger.INSTANCE.d(TAG, "onUpdateTodo: updatePosition = " + i);
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onUpdateTodo$7(i, z);
            }
        });
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateTodo$7(int i, boolean z) {
        BaseTodoChildItemVh baseTodoChildItemVh;
        View viewFindViewByPosition = this.mRvLinearLayoutManager.findViewByPosition(i);
        if (viewFindViewByPosition == null || !z || (baseTodoChildItemVh = (BaseTodoChildItemVh) this.mRvTodo.getChildViewHolder(viewFindViewByPosition)) == null) {
            return;
        }
        PadTodoListAdapter padTodoListAdapter = this.mTodoListAdapter;
        if (padTodoListAdapter.getRealType(padTodoListAdapter.getItemId(i)) == 0) {
            baseTodoChildItemVh.setTodoItemFinished();
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onNewTodoAdded(TodoEntity todoEntity, int i, final boolean z) {
        if (isAdded()) {
            if (this.mRvTodo.getVisibility() != 0) {
                onDataEmpty(false);
            } else {
                PadTodoListAdapter padTodoListAdapter = this.mTodoListAdapter;
                if (padTodoListAdapter != null) {
                    padTodoListAdapter.notifyDataChanged();
                }
            }
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onNewTodoAdded$8(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNewTodoAdded$8(boolean z) {
        BaseTodoChildItemVh baseTodoChildItemVh;
        View viewFindViewByPosition = this.mRvLinearLayoutManager.findViewByPosition(0);
        Logger.INSTANCE.d(TAG, "run: newTodo = " + viewFindViewByPosition + " isFinish = " + z);
        if (viewFindViewByPosition == null || !z || (baseTodoChildItemVh = (BaseTodoChildItemVh) this.mRvTodo.getChildViewHolder(viewFindViewByPosition)) == null) {
            return;
        }
        baseTodoChildItemVh.setTodoItemFinished();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDataLoaded() {
        Logger.INSTANCE.d(TAG, "onDataLoaded");
        if (!this.mInSearchMode) {
            if (this.mRvTodo.getScrollState() == 0 && !this.mRvTodo.isComputingLayout()) {
                this.mTodoListAdapter.notifyDataSetChanged();
            }
            UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
            if (this.mTodoListAdapter.getItemCount() == 0 && this.mRvTodo.getVisibility() == 0) {
                Runnable runnable = new Runnable() { // from class: com.miui.notes.detail.PadTodoDetailFragment.13
                    @Override // java.lang.Runnable
                    public void run() {
                        PadTodoDetailFragment.this.onDataEmpty(true);
                        PadTodoDetailFragment.this.mPendingDataEmptyTask = null;
                    }
                };
                this.mPendingDataEmptyTask = runnable;
                this.mRvTodo.post(runnable);
            } else {
                onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
            }
        } else {
            refreshSearchResult();
        }
        if (!isAdded() || getActivity() == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "onDataLoaded intent" + getActivity().getIntent());
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            updateActionModeButton2(actionMode);
        }
        if (this.mPendingIntentTask != null) {
            if (isAdded()) {
                getActivity().setIntent(null);
            }
            this.mPendingIntentTask.run();
            this.mPendingIntentTask = null;
            return;
        }
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (this.mIntentAction == null && intent.getAction() != null && (TodoIntent.ACTION_SHORTCUT_INSERT_OR_EDIT.equals(intent.getAction()) || TodoIntent.ACTION_INSERT_OR_EDIT.equals(intent.getAction()))) {
                this.mIntentAction = intent.getAction();
            }
            deliverTodoIntent(intent);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDataEmpty(boolean z) {
        if (z) {
            if (this.mRvTodo.getVisibility() != 8) {
                if (this.mRvTodo.getChildCount() > 0) {
                    this.mRvTodo.removeAllViews();
                }
                this.mRvTodo.setVisibility(8);
                this.mBlankContainer.setVisibility(0);
                return;
            }
            return;
        }
        this.mRvTodo.removeCallbacks(this.mPendingDataEmptyTask);
        this.mPendingDataEmptyTask = null;
        if (this.mRvTodo.getVisibility() != 0) {
            this.mRvTodo.setVisibility(0);
            this.mBlankContainer.setVisibility(8);
            this.mSpringBackLayout.setTarget(this.mRvTodo);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodoFinishState(boolean z, boolean z2, int i) {
        if (isAdded()) {
            if (!this.mInSearchMode || TextUtils.isEmpty(this.mBindContext.getSearchToken())) {
                this.mTodoListAdapter.updateFinishData(z, i);
            } else {
                refreshSearchResult();
            }
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onUpdateTodoData() {
        if (isAdded()) {
            if (!this.mInSearchMode || TextUtils.isEmpty(this.mBindContext.getSearchToken())) {
                if (this.mTodoListAdapter != null) {
                    this.mRvTodo.stopScroll();
                    if (!this.mRvTodo.isComputingLayout() && !this.mRvTodo.isInLayout()) {
                        this.mTodoListAdapter.notifyDataSetChanged();
                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$onUpdateTodoData$9();
                            }
                        });
                    }
                    this.mTodoListAdapter.notifyDataSetChanged();
                    Logger.INSTANCE.d(TAG, "onUpdateTodoData UnfinishItemCount: " + this.mTodoListAdapter.getUnfinishItemCount());
                    UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
                    return;
                }
                return;
            }
            refreshSearchResult();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateTodoData$9() {
        this.mTodoListAdapter.notifyDataSetChanged();
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onDeleteTodo() {
        refreshSearchResult();
        this.mTodoListAdapter.notifyDataSetChanged();
        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void onDeleteTodos() {
        finishActionMode();
        this.mTodoListAdapter.notifyDataSetChanged();
        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void refreshTodoList() {
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
        this.mTodoListAdapter.notifyDataSetChanged();
        UIUtils.updateWidgetTodoNums(NoteApp.getInstance(), this.mTodoListAdapter.getUnfinishItemCount());
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void onShowSearchResult(List<TodoEntity> list) {
        if (this.mTodoListAdapter == null) {
            Logger.INSTANCE.w(TAG, "onShowSearchResult mTodoListAdapter is null");
            return;
        }
        exitEdit(false);
        this.mTodoListAdapter.showSearchResult(list);
        if (this.mInSearchMode && !TextUtils.isEmpty(this.mBindContext.getSearchToken())) {
            this.mSearchResult.setText(getResources().getQuantityString(R.plurals.pc_todo_search_result, list.size(), Integer.valueOf(list.size())));
            onDataEmpty(list.size() == 0);
        } else {
            onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
        }
    }

    private void deliverTodoIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "deliverTodoIntent:" + intent);
        if (this.mIntentAction == null || getActivity() == null) {
            return;
        }
        if (this.mIntentAction.equals(TodoIntent.ACTION_SHORTCUT_INSERT_OR_EDIT)) {
            intent.setAction("android.intent.action.MAIN");
            this.mIntentAction = null;
            if (TodoIntent.TYPE_ADD_TEXT.equals(intent.getType())) {
                getActivity().setIntent(null);
                createCommonTodo();
                return;
            } else {
                long longExtra = intent.getLongExtra(TodoIntent.EXTRA_TODO_ID, -1L);
                getActivity().setIntent(null);
                showTodoById(longExtra);
                return;
            }
        }
        if (this.mIntentAction.equals(TodoIntent.ACTION_INSERT_OR_EDIT)) {
            intent.setAction("android.intent.action.MAIN");
            this.mIntentAction = null;
            long longExtra2 = intent.getLongExtra(TodoIntent.EXTRA_TODO_ID, -1L);
            getActivity().setIntent(null);
            showTodoById(longExtra2);
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void exitEdit(boolean z) {
        if (isShowEdit()) {
            if (z) {
                this.mTodoEditController.dismiss();
            } else {
                this.mTodoEditController.dismissNoAnim();
            }
        }
    }

    private void refreshSearchResult() {
        if (this.mInSearchMode) {
            this.mPresenter.queryTodo(this.mBindContext.getSearchToken());
        }
    }

    private void showTodoEditGroup(TodoBaseEntity todoBaseEntity, boolean z) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        BaseTodoEditController baseTodoEditControllerCreateEditController = createEditController(todoBaseEntity);
        this.mTodoEditController = baseTodoEditControllerCreateEditController;
        baseTodoEditControllerCreateEditController.setContentData(todoBaseEntity);
        baseTodoEditControllerCreateEditController.setWindowListener(new BaseTodoEditController.OnWindowListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.14
            @Override // com.miui.todo.feature.todoedit.BaseTodoEditController.OnWindowListener
            public void onShow() {
            }

            @Override // com.miui.todo.feature.todoedit.BaseTodoEditController.OnWindowListener
            public void onDismiss() {
                if (PadTodoDetailFragment.this.mPresenter != null && PadTodoDetailFragment.this.mPresenter.getMIsInEdit()) {
                    PadTodoDetailFragment.this.mPresenter.exitEditMode(true, false);
                }
                PadTodoDetailFragment.this.mTodoEditController = null;
                if (PadTodoDetailFragment.this.mTimePickerDialog != null) {
                    PadTodoDetailFragment.this.mTimePickerDialog.dismiss();
                    PadTodoDetailFragment.this.mTimePickerDialog = null;
                }
            }
        });
        baseTodoEditControllerCreateEditController.updateScreenSpec(getResponsiveState().getType());
        baseTodoEditControllerCreateEditController.show(this.mRootView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteCheckedItems() {
        FragmentActivity activity = getActivity();
        String string = getString(R.string.todo_alert_title_delete);
        int checkedItemCount = this.mRvTodoWrapper.getCheckedItemCount();
        String quantityString = getResources().getQuantityString(R.plurals.todo_alert_message_delete, checkedItemCount, Integer.valueOf(checkedItemCount));
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, 2131951644);
        builder.setTitle(string);
        builder.setMessage(quantityString);
        builder.setPositiveButton(R.string.todo_menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.15
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                long[] checkedItemIndexs = PadTodoDetailFragment.this.mRvTodoWrapper.getCheckedItemIndexs();
                PadTodoDetailFragment.this.mRvTodoWrapper.clearChoices();
                if (PadTodoDetailFragment.this.mRvTodo != null) {
                    PadTodoDetailFragment.this.mRvTodo.stopScroll();
                }
                PadTodoDetailFragment.this.mPresenter.deleteTodos(checkedItemIndexs);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialog = this.mDeleteTodoDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDeleteTodoDialog.dismiss();
        }
        if (LiteUtils.isSuperLite()) {
            builder.setEnableDialogImmersive(true);
        }
        AlertDialog alertDialogShow = builder.show();
        this.mDeleteTodoDialog = alertDialogShow;
        manageDialog(alertDialogShow);
    }

    protected boolean canEnterEditMode() {
        return !this.mInSearchMode;
    }

    protected void showCreateBtn(boolean z) {
        showCreateBtn(this.mBtnContentAdd, z, false);
    }

    protected void showCreateBtn(View view, boolean z, boolean z2) {
        if (view == null) {
            return;
        }
        if (z2) {
            if (z) {
                view.setVisibility(0);
                view.setAlpha(1.0f);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                return;
            }
            view.cancelLongPress();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.8f), ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.8f), ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), 0.0f));
            animatorSet.setInterpolator(new CubicEaseInOutInterpolator());
            animatorSet.setDuration(100L).start();
            return;
        }
        Folme.clean(view);
        Folme.use(view).touch().setTint(0);
        Folme.use(view).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(view, new AnimConfig[0]);
        if (z) {
            if (view.getVisibility() != 0) {
                view.setVisibility(0);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                Folme.use(view).visible().show(new AnimConfig[0]);
                Folme.use(view).state().to(ViewProperty.SCALE_X, Float.valueOf(1.0f)).to(ViewProperty.SCALE_Y, Float.valueOf(1.0f));
                return;
            }
            return;
        }
        if (view.getVisibility() == 0) {
            view.cancelLongPress();
            AnimConfig animConfig = new AnimConfig();
            animConfig.setEase(EaseManager.getStyle(0, 250.0f, 0.9f, 0.8f));
            Folme.use(view).visible().setScale(0.8f, IVisibleStyle.VisibleType.HIDE).hide(animConfig);
        }
    }

    public void enterSearchMode(String str) {
        this.mInSearchMode = true;
        this.mPresenter.enterSearchMode();
        this.mBindContext.setSearchToken(str);
        if (this.mTodoListType == 0) {
            this.mBtnContentAdd.setVisibility(8);
        }
        this.mPresenter.queryTodo(this.mBindContext.getSearchToken());
    }

    public void exitSearchMode(String str, boolean z) {
        if (isAdded()) {
            if (this.mInSearchMode || z) {
                this.mInSearchMode = false;
                if (TextUtils.isEmpty(str) || z) {
                    this.mBindContext.setSearchToken("");
                }
                TodoListContract.Presenter presenter = this.mPresenter;
                if (presenter != null) {
                    presenter.exitSearchMode();
                }
                PadTodoListAdapter padTodoListAdapter = this.mTodoListAdapter;
                if (padTodoListAdapter != null) {
                    onDataEmpty(padTodoListAdapter.getItemCount() == 0);
                }
                if (this.mTodoListType == 0) {
                    this.mBtnContentAdd.setVisibility(0);
                }
                PadTodoListAdapter padTodoListAdapter2 = this.mTodoListAdapter;
                if (padTodoListAdapter2 != null) {
                    padTodoListAdapter2.exitSearchMode();
                }
            }
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onSearchModeAnimStart(boolean z) {
        if (isAdded()) {
            if (z) {
                this.mSearchModePaddingTopExtra = getResources().getDimension(R.dimen.search_result_list_padding_top);
            } else {
                this.mRvTodo.closeOpenedItem();
            }
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onSearchModeAnimUpdate(boolean z, float f) {
        if (isAdded()) {
            if (!z) {
                f = 1.0f - f;
            }
            float f2 = f * this.mSearchModePaddingTopExtra;
            try {
                View view = this.mRootView;
                view.setPadding(view.getPaddingLeft(), (int) f2, this.mRootView.getPaddingRight(), this.mRootView.getPaddingBottom());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onSearchModeAnimStop(boolean z) {
        if (isAdded() && !z) {
            this.mSearchModePaddingTopExtra = 0.0f;
            try {
                View view = this.mRootView;
                view.setPadding(view.getPaddingLeft(), 0, this.mRootView.getPaddingRight(), this.mRootView.getPaddingBottom());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onCreateSearchMode(ActionMode actionMode, Menu menu) {
        this.mInSearchMode = true;
        this.mPresenter.enterSearchMode();
    }

    @Override // com.miui.todo.feature.todolist.TodoSearchCallback.OnSearchListener
    public void onDestroySearchMode(ActionMode actionMode) {
        Logger.INSTANCE.d(TAG, "onDestroySearchMode: ");
        this.mInSearchMode = false;
        this.mPresenter.exitSearchMode();
        this.mTodoListAdapter.exitSearchMode();
        onDataEmpty(this.mTodoListAdapter.getItemCount() == 0);
    }

    @Override // com.miui.todo.base.IBaseContract.View
    public void setPresenter(IBaseTodoListContract.Presenter presenter) {
        this.mPresenter = (TodoListContract.Presenter) Preconditions.checkNotNull(presenter);
    }

    @Override // com.miui.notes.detail.BaseTodoDetailFragment
    public void setInSearchMode(boolean z) {
        super.setInSearchMode(z);
        if (z) {
            finishActionMode();
        }
    }

    public EditableRecyclerViewWrapper.MultiChoiceModeListener createEditModeCallback() {
        return new EditModeCallback();
    }

    protected class EditModeCallback implements EditableRecyclerViewWrapper.MultiChoiceModeListener {
        private EditModeCallBackHandler mCallbackHandler;

        public EditModeCallback() {
            this.mCallbackHandler = new TodoListEditModeCallBackHandler();
        }

        @Override // com.miui.todo.base.EditableRecyclerViewWrapper.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(ActionMode actionMode, boolean z) {
            PadTodoDetailFragment.this.updateActionModeButton2(actionMode);
            this.mCallbackHandler.updateActionItems();
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z) {
            PadTodoDetailFragment.this.updateActionModeButton2(actionMode);
            this.mCallbackHandler.updateActionItems();
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            SwipeMenuLayout.sForbidSwipe = true;
            PadTodoDetailFragment.this.mTodoListAdapter.setEditMode(true);
            PadTodoDetailFragment.this.updateActionModeButton2(actionMode);
            PadTodoDetailFragment.this.mActionMode = actionMode;
            PadTodoDetailFragment.this.mListContainer.setPadding(PadTodoDetailFragment.this.mListContainer.getPaddingLeft(), PadTodoDetailFragment.this.mListContainer.getResources().getDimensionPixelSize(R.dimen.pad_todo_right_list_margin_top) + (PadTodoDetailFragment.this.mContentInset != null ? PadTodoDetailFragment.this.mContentInset.top : 0), PadTodoDetailFragment.this.mListContainer.getPaddingRight(), PadTodoDetailFragment.this.mListContainer.getPaddingBottom());
            PadTodoDetailFragment.this.mListContainer.setClipToPadding(false);
            PadTodoDetailFragment.this.mListContainer.setClipChildren(true ^ MiuiBlurUtils.isEnable());
            PadTodoDetailFragment.this.mListContainer.requestLayout();
            return this.mCallbackHandler.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            this.mCallbackHandler.updateActionItems();
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == 16908313) {
                actionMode.finish();
            } else if (menuItem.getItemId() == 16908314) {
                Logger.INSTANCE.d(PadTodoDetailFragment.TAG, "onActionItemClicked: mTodoListType: " + PadTodoDetailFragment.this.mTodoListType);
                if (PadTodoDetailFragment.this.mTodoListType == 0) {
                    Logger.INSTANCE.d(PadTodoDetailFragment.TAG, "onActionItemClicked: !mRvTodoWrapper.isAllUnfinishItemsChecked()" + (!PadTodoDetailFragment.this.mRvTodoWrapper.isAllUnfinishItemsChecked()));
                    PadTodoDetailFragment.this.mRvTodoWrapper.setAllItemsChecked(true ^ PadTodoDetailFragment.this.mRvTodoWrapper.isAllUnfinishItemsChecked());
                } else if (PadTodoDetailFragment.this.mTodoListType == 1) {
                    PadTodoDetailFragment.this.mRvTodoWrapper.setAllItemsChecked(true ^ PadTodoDetailFragment.this.mRvTodoWrapper.isAllFinishedItemsChecked());
                }
            }
            return this.mCallbackHandler.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            if (!PadTodoDetailFragment.this.isAdded() || PadTodoDetailFragment.this.mTodoListAdapter == null) {
                return;
            }
            PadTodoDetailFragment.this.mTodoListAdapter.setEditMode(false);
            PadTodoDetailFragment.this.mTodoListAdapter.notifyDataSetChanged();
            PadTodoDetailFragment.this.mActionMode = null;
            PadTodoDetailFragment.this.cancelMarkTodo();
            PadTodoDetailFragment.this.mItemTouchHelperCallBack.setEnableDrag(false);
            this.mCallbackHandler.onDestroyActionMode(actionMode);
            SwipeMenuLayout.sForbidSwipe = false;
            PadTodoDetailFragment.this.mListContainer.setPadding(PadTodoDetailFragment.this.mListContainer.getPaddingLeft(), PadTodoDetailFragment.this.mListContainer.getResources().getDimensionPixelSize(R.dimen.pad_todo_right_list_margin_top), PadTodoDetailFragment.this.mListContainer.getPaddingRight(), PadTodoDetailFragment.this.mListContainer.getPaddingBottom());
            PadTodoDetailFragment.this.mListContainer.setClipToPadding(true);
            PadTodoDetailFragment.this.mListContainer.setClipChildren(true);
            PadTodoDetailFragment.this.mListContainer.requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class TodoListEditModeCallBackHandler implements EditModeCallBackHandler {
        @Override // com.miui.notes.detail.PadTodoDetailFragment.EditModeCallBackHandler
        public void updateActionItems() {
        }

        private TodoListEditModeCallBackHandler() {
        }

        @Override // com.miui.notes.detail.PadTodoDetailFragment.EditModeCallBackHandler
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            PadTodoDetailFragment.this.getMenuInflater().inflate(R.menu.todo_list_options, menu);
            PadTodoDetailFragment.this.showCreateBtn(false);
            return true;
        }

        @Override // com.miui.notes.detail.PadTodoDetailFragment.EditModeCallBackHandler
        public void onDestroyActionMode(ActionMode actionMode) {
            if (PadTodoDetailFragment.this.mTodoListType != 1) {
                PadTodoDetailFragment.this.showCreateBtn(true);
                PadTodoDetailFragment.this.mListContainer.setClipToPadding(true);
                PadTodoDetailFragment.this.mListContainer.setClipChildren(true);
            }
        }

        @Override // com.miui.notes.detail.PadTodoDetailFragment.EditModeCallBackHandler
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() != R.id.delete) {
                return false;
            }
            if (PadTodoDetailFragment.this.mRvTodo != null) {
                PadTodoDetailFragment.this.mRvTodo.stopScroll();
            }
            Handler handler = PadTodoDetailFragment.this.mHandler;
            final PadTodoDetailFragment padTodoDetailFragment = PadTodoDetailFragment.this;
            handler.post(new Runnable() { // from class: com.miui.notes.detail.PadTodoDetailFragment$TodoListEditModeCallBackHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    padTodoDetailFragment.deleteCheckedItems();
                }
            });
            return true;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void updateActionModeButton2(ActionMode actionMode) {
        boolean zIsAllFinishedItemsChecked;
        EditActionMode editActionMode = (EditActionMode) actionMode;
        if (this.mTodoListType == 0) {
            zIsAllFinishedItemsChecked = this.mRvTodoWrapper.isAllUnfinishItemsChecked();
        } else {
            zIsAllFinishedItemsChecked = this.mTodoListType == 1 ? this.mRvTodoWrapper.isAllFinishedItemsChecked() : false;
        }
        if (UIUtils.isMiuiXSdkSupported()) {
            int i = DisplayUtils.isNightMode() ? R.drawable.miuix_action_icon_deselect_all_dark : R.drawable.miuix_action_icon_deselect_all_light;
            int i2 = DisplayUtils.isNightMode() ? R.drawable.miuix_action_icon_select_all_dark : R.drawable.miuix_action_icon_select_all_light;
            if (!zIsAllFinishedItemsChecked) {
                i = i2;
            }
            editActionMode.setButton(16908314, (CharSequence) null, i);
            return;
        }
        editActionMode.setButton(16908314, zIsAllFinishedItemsChecked ? R.string.miuix_appcompat_action_mode_deselect_all : R.string.miuix_appcompat_action_mode_select_all);
    }

    public boolean finishActionMode() {
        EditableRecyclerViewWrapper editableRecyclerViewWrapper = this.mRvTodoWrapper;
        if (editableRecyclerViewWrapper == null) {
            return false;
        }
        boolean zIsInActionMode = editableRecyclerViewWrapper.isInActionMode();
        this.mRvTodoWrapper.finishActionMode();
        return zIsInActionMode;
    }

    private void showTodoById(long j) {
        finishActionMode();
        this.mWorkingTodoIdFromIntent = j;
        if (j != -1) {
            this.mPresenter.setEditTodoFromIntent(j);
        }
    }

    private void createCommonTodo() {
        if (isShowEdit()) {
            return;
        }
        finishActionMode();
        cancelMarkTodo();
        closeOpenedItem();
        this.isNewTodo = true;
        this.mPresenter.enterEditMode(null, -1, false, false);
    }

    public boolean isShowEdit() {
        return this.mTodoEditController != null && this.mTodoEditController.isShow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelMarkTodo() {
        if (this.mBindContext.getLastMarkIndex() != -1) {
            Logger.INSTANCE.d(TAG, "cancelMarkTodo: notifyItemChanged pos = " + this.mBindContext.getLastMarkIndex());
            this.mTodoListAdapter.notifyItemChanged(this.mBindContext.getLastMarkIndex());
            this.mBindContext.setLastMarkIndex(-1);
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void finishTodoInData(TodoEntity todoEntity, int i) {
        PadTodoListAdapter padTodoListAdapter = this.mTodoListAdapter;
        if (padTodoListAdapter == null) {
            return;
        }
        TodoEntity childItem = padTodoListAdapter.getChildItem(i);
        DataGroupInfo itemInfo = this.mTodoListAdapter.getItemInfo(i);
        if (childItem != null && todoEntity != null) {
            this.mPresenter.updateTodo(todoEntity, this.mTodoListAdapter.getItemInfo(i).mSubPosition, true);
        } else if (childItem != null) {
            this.mPresenter.setTodoFinishState(childItem, itemInfo.mSubPosition, i);
        }
        if (PreferenceUtils.getEnableFloatMode(getContext().getApplicationContext())) {
            FloatWindowManagerAgent.notifyDataChanged();
        }
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void doFinishAnim(TodoEntity todoEntity, int i, View view) {
        this.mListContainer.setClipChildren(false);
        TodoAnimHelper.doFinishAnim(this, todoEntity, i, view, new Runnable() { // from class: com.miui.notes.detail.PadTodoDetailFragment$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$doFinishAnim$10();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doFinishAnim$10() {
        this.mListContainer.setClipChildren(true);
    }

    @Override // com.miui.todo.base.todolist.IBaseTodoListContract.View
    public void doAllFinishAnim(TodoEntity todoEntity, int i, final View view) {
        ViewGroup viewGroup = (ViewGroup) getView();
        FrameLayout frameLayout = new FrameLayout(getContext());
        TodoAnimHelper.doAllFinishAnim(this, todoEntity, i, view, viewGroup, frameLayout, PadTodoItemForAllFinishVh.newInstance(frameLayout), new TodoAnimHelper.Listener() { // from class: com.miui.notes.detail.PadTodoDetailFragment.21
            @Override // com.miui.todo.utils.TodoAnimHelper.Listener
            public void onStart(DoneSparkAnim doneSparkAnim) {
                PadTodoDetailFragment.this.mRunningSparkAnim = doneSparkAnim;
                view.setVisibility(4);
            }

            @Override // com.miui.todo.utils.TodoAnimHelper.Listener
            public void onEnd() {
                PadTodoDetailFragment.this.isInFlowAnim = false;
                PadTodoDetailFragment.this.mRunningSparkAnim = null;
                view.setVisibility(0);
            }

            @Override // com.miui.todo.utils.TodoAnimHelper.Listener
            public void onCancel() {
                PadTodoDetailFragment.this.isInFlowAnim = false;
                PadTodoDetailFragment.this.mRunningSparkAnim = null;
                view.setVisibility(0);
            }
        });
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public boolean getIsNewTodo() {
        return this.isNewTodo;
    }

    @Override // com.miui.todo.feature.todolist.TodoListContract.View
    public void setIsNewTodo(boolean z) {
        this.isNewTodo = z;
    }
}
