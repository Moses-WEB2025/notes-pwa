package com.miui.todo.base.todolist;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.Logger;
import com.miui.common.view.StrikeTextView.StrikeTextView;
import com.miui.common.view.StrikeTextView.StrikeThroughPainting;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.utils.CalenderUtils;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;
import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import org.apache.commons.text.StringEscapeUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseTodoChildItemVh extends BaseTodoItemVh {
    private static final String TAG = "BaseTodoChildItemVh";
    private static final int UI_MODE_NIGHT_NOT_CACHED = -1;
    private static Drawable sCachedClockD = null;
    private static Drawable sCachedClockExpire = null;
    private static Drawable sCachedClockN = null;
    private static int sCachedClockUiModeNight = -1;
    private static Drawable sCachedRepeatExpire;
    private static Drawable sCachedRepeatN;
    protected AccessibilityManager accessibilityManager;
    protected BaseListAdapterBindContext mBindContext;
    protected CheckBox mCheckBox;
    protected CompoundButton.OnCheckedChangeListener mCheckBoxListener;
    protected View mCheckboxMask;
    protected StrikeTextView mContent;
    protected View mContentRootView;
    protected boolean mIsFinished;
    protected boolean mIsSearchMode;
    protected ImageView mIvAudio;
    protected ImageView mIvClock;
    protected View mLabelGroup;
    protected TextView mLeftMenu;
    protected int mRepeatType;
    protected ImageView mRightMenu;
    protected SwipeMenuLayout mRootView;
    protected long mScheduleTime;
    protected StrikeThroughPainting mStrikeThroughPainting;
    protected TextView mTime;
    protected AccessibilityManager.TouchExplorationStateChangeListener mTouchExplorationStateChangeListener;
    public Long pLastCheckBoxClickTime;
    protected int todo_type;

    protected abstract int getNormalTimeTextColor();

    protected abstract int getSelectedFg();

    protected void setListItemFinished() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(CompoundButton compoundButton, boolean z) {
        if (z) {
            onChecked();
        } else {
            onUnChecked();
        }
    }

    public BaseTodoChildItemVh(final View view) {
        super(view);
        this.pLastCheckBoxClickTime = null;
        this.mStrikeThroughPainting = null;
        this.mCheckBoxListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.todo.base.todolist.BaseTodoChildItemVh$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$new$0(compoundButton, z);
            }
        };
        this.mRootView = (SwipeMenuLayout) view.findViewById(R.id.sl_item_group);
        AccessibilityManager accessibilityManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        this.accessibilityManager = accessibilityManager;
        boolean z = accessibilityManager != null && accessibilityManager.isEnabled();
        Logger.INSTANCE.d(TAG, "isAccessibilityEnabled: " + z);
        if (z) {
            view.setFocusable(false);
            view.setFocusableInTouchMode(false);
            view.setImportantForAccessibility(2);
            SwipeMenuLayout swipeMenuLayout = this.mRootView;
            if (swipeMenuLayout != null) {
                swipeMenuLayout.setFocusable(false);
                this.mRootView.setFocusableInTouchMode(false);
                this.mRootView.setImportantForAccessibility(2);
            }
        }
        this.mContentRootView = view.findViewById(R.id.item_content);
        this.mLabelGroup = view.findViewById(R.id.label);
        this.mTime = (TextView) view.findViewById(R.id.time);
        StrikeTextView strikeTextView = (StrikeTextView) view.findViewById(R.id.content);
        this.mContent = strikeTextView;
        strikeTextView.setFocusable(true);
        this.mContent.setFocusableInTouchMode(true);
        this.mContent.setImportantForAccessibility(1);
        this.mLeftMenu = (TextView) this.mRootView.findViewById(R.id.left_menu);
        this.mRightMenu = (ImageView) this.mRootView.findViewById(R.id.right_menu);
        this.mIvAudio = (ImageView) view.findViewById(R.id.iv_audio);
        this.mIvClock = (ImageView) view.findViewById(R.id.iv_clock);
        this.mCheckboxMask = view.findViewById(R.id.checkbox_mask);
        CheckBox checkBox = (CheckBox) view.findViewById(android.R.id.checkbox);
        this.mCheckBox = checkBox;
        checkBox.setImportantForAccessibility(2);
        this.mCheckBox.setOnCheckedChangeListener(this.mCheckBoxListener);
        this.mRootView.setSwipeEnable(true);
        this.mRootView.setHandReleaseDragMode(false, false);
        DisplayUtils.setMiuiMediumTypeFace(this.mContent);
        DisplayUtils.setMiuiRegularTypeFace(this.mTime);
        this.mStrikeThroughPainting = new StrikeThroughPainting(this.mContent);
        ImageView imageView = this.mRightMenu;
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.base.todolist.BaseTodoChildItemVh$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$new$1(view, view2);
                }
            });
        }
        AccessibilityDelegateUtil.setCheckBoxAccessibilityDelegate(this.mCheckboxMask);
        this.mCheckboxMask.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.base.todolist.BaseTodoChildItemVh$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$2(view2);
            }
        });
        this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.base.todolist.BaseTodoChildItemVh$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$3(view, view2);
            }
        });
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() { // from class: com.miui.todo.base.todolist.BaseTodoChildItemVh$$ExternalSyntheticLambda4
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return this.f$0.lambda$new$4(view, view2);
            }
        };
        this.mRootView.setOnLongClickListener(onLongClickListener);
        this.mCheckboxMask.setOnLongClickListener(onLongClickListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(final View view, View view2) {
        view2.setClickable(false);
        view2.clearFocus();
        this.mRootView.setClickable(false);
        this.mRootView.smoothCloseMenu(200);
        this.mRootView.postDelayed(new Runnable() { // from class: com.miui.todo.base.todolist.BaseTodoChildItemVh.1
            @Override // java.lang.Runnable
            public void run() {
                if (BaseTodoChildItemVh.this.mItemOperationListener != null) {
                    BaseTodoChildItemVh.this.mItemOperationListener.onDelete(view);
                }
            }
        }, 201L);
        ToDoStat.markTodoCardOperate(ToDoStat.LEFT_SLIDE_DELETE, this.todo_type, this.mIsFinished);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        this.pLastCheckBoxClickTime = Long.valueOf(System.currentTimeMillis());
        if (this.mBindContext == null) {
            return;
        }
        if (this.mCheckBox.isChecked()) {
            onCheckBoxUnChecked();
        } else {
            onCheckBoxChecked(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(View view, View view2) {
        Logger.INSTANCE.d(TAG, "RootView-onClick");
        int id = view2.getId();
        if (id == R.id.group_item_group) {
            if (this.mItemOperationListener != null) {
                this.mItemOperationListener.onToggleGroup(view);
            }
        } else if (id == R.id.multi_checkbox) {
            if (this.mItemOperationListener != null) {
                this.mItemOperationListener.onSelected(view);
            }
        } else if (id == R.id.sl_item_group) {
            if (this.mItemOperationListener != null) {
                this.mItemOperationListener.onClick(view);
            }
        } else if (this.mOtherViewClickListener != null) {
            this.mOtherViewClickListener.onClick(view2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$4(View view, View view2) {
        if (this.mItemOperationListener == null) {
            return true;
        }
        if (this.pLastCheckBoxClickTime != null && System.currentTimeMillis() - this.pLastCheckBoxClickTime.longValue() <= 500) {
            return true;
        }
        this.mItemOperationListener.onLongClick(view);
        return true;
    }

    protected void setContentClickable(boolean z) {
        if (this.mContent == null) {
            Logger.INSTANCE.d(TAG, "mContent is null");
            return;
        }
        Logger.INSTANCE.d(TAG, "setContentClickable-isClickable = " + z);
        if (z) {
            this.mContent.setClickable(true);
            this.mContent.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.base.todolist.BaseTodoChildItemVh.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Logger.INSTANCE.d(BaseTodoChildItemVh.TAG, "Content-onClick");
                    if (BaseTodoChildItemVh.this.mItemOperationListener != null) {
                        BaseTodoChildItemVh.this.mItemOperationListener.onClick(BaseTodoChildItemVh.this.itemView);
                    }
                }
            });
        } else {
            this.mContent.setOnClickListener(null);
            this.mContent.setClickable(false);
        }
    }

    public void bind(TodoEntity todoEntity, BaseListAdapterBindContext baseListAdapterBindContext, int i) {
        this.mStrikeThroughPainting.clearStrikeThrough();
        this.mIsFinished = todoEntity.getIsFinish() != 0;
        if (baseListAdapterBindContext.getLastFinishId() != -1 && baseListAdapterBindContext.getLastFinishId() == todoEntity.getId()) {
            this.mIsFinished = true;
            this.mIsSearchMode = false;
        }
        this.mRootView.setClickable(true);
        updateViewStateByMenuState();
        this.mRootView.setMovable((this.mIsFinished || this.mIsSearchMode) ? false : true);
        this.mScheduleTime = todoEntity.getRemindTime();
        this.mRepeatType = todoEntity.getRemindRepeatType();
        this.mTime.setText(CalenderUtils.getScheduleTimeString(todoEntity.getRemindType(), todoEntity.getRemindTime(), todoEntity.getRemindRepeatType()));
        TextView textView = this.mTime;
        textView.setContentDescription(textView.getText());
        setTimeTextColor(this.mTime, this.mIvClock, this.mScheduleTime, this.mRepeatType, this.mIsFinished);
        this.mCheckBox.setActivated(false);
        this.mCheckBox.setChecked(this.mIsFinished);
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.itemView.getContext().getSystemService("accessibility");
        this.accessibilityManager = accessibilityManager;
        if (accessibilityManager != null && accessibilityManager.isEnabled()) {
            setContentClickable(this.accessibilityManager.isTouchExplorationEnabled());
        }
        if (this.mIsFinished) {
            this.mCheckboxMask.setContentDescription(this.mCheckBox.getContext().getString(R.string.todo_item_status_finished));
        } else {
            this.mCheckboxMask.setContentDescription(this.mCheckBox.getContext().getString(R.string.todo_item_status_unfinished));
        }
        if (todoEntity.getInputType() == 1) {
            this.mIvAudio.setVisibility(0);
        } else {
            this.mIvAudio.setVisibility(8);
        }
        if (todoEntity.getRemindType() == 0) {
            this.mTime.setVisibility(8);
            this.mIvClock.setVisibility(8);
        } else {
            this.mTime.setVisibility(0);
            this.mIvClock.setVisibility(0);
        }
        if (baseListAdapterBindContext.isInActionMode()) {
            onUpdateEditable(true, baseListAdapterBindContext.isItemIdChecked(todoEntity.getId()));
        } else {
            onUpdateEditable(false, false);
        }
        this.todo_type = todoEntity.getListType();
        this.mBindContext = baseListAdapterBindContext;
        setContent(todoEntity);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoItemVh
    public void onAttached(BaseListAdapterBindContext baseListAdapterBindContext) {
        Logger.INSTANCE.d(TAG, "onAttached");
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = this.mTouchExplorationStateChangeListener;
        if (touchExplorationStateChangeListener != null) {
            this.accessibilityManager.removeTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
        }
        WeakTouchExplorationListener weakTouchExplorationListener = new WeakTouchExplorationListener(this);
        this.mTouchExplorationStateChangeListener = weakTouchExplorationListener;
        this.accessibilityManager.addTouchExplorationStateChangeListener(weakTouchExplorationListener);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoItemVh
    public void onDettached(BaseListAdapterBindContext baseListAdapterBindContext) {
        AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener;
        Logger.INSTANCE.d(TAG, "onDetached");
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager == null || (touchExplorationStateChangeListener = this.mTouchExplorationStateChangeListener) == null) {
            return;
        }
        accessibilityManager.removeTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
        this.mTouchExplorationStateChangeListener = null;
    }

    public void setSearchMode(boolean z) {
        this.mIsSearchMode = z;
    }

    public boolean onUpdateEditable(boolean z, boolean z2) {
        if (z2) {
            this.mContentRootView.setForeground(this.mContentRootView.getContext().getDrawable(getSelectedFg()));
        } else {
            this.mContentRootView.setForeground(null);
        }
        this.itemView.setHapticFeedbackEnabled(z);
        StrikeTextView strikeTextView = this.mContent;
        if (strikeTextView == null) {
            return true;
        }
        AccessibilityDelegateUtil.setSelectedAccessibilityDelegate(strikeTextView, z2);
        return true;
    }

    protected void setContent(TodoEntity todoEntity) {
        String content = todoEntity.getContent();
        Matcher audioLabelIndex = AudioUtils.getAudioLabelIndex(todoEntity.getContent());
        if (audioLabelIndex != null && audioLabelIndex.find()) {
            content = todoEntity.getContent().replace(todoEntity.getContent().substring(audioLabelIndex.start(), audioLabelIndex.end() + 1), "");
        }
        if (TextUtils.isEmpty(todoEntity.getPlainText()) && todoEntity.getInputType() == 1) {
            this.mContent.setText(TodoUtils.getString(R.string.todo_audio_default_content));
            StrikeTextView strikeTextView = this.mContent;
            strikeTextView.setContentDescription(strikeTextView.getText());
        } else {
            this.mContent.setText(getHighLightText(StringEscapeUtils.unescapeHtml4(content), this.mBindContext.getSearchToken()));
            this.mContent.setContentDescription(todoEntity.getPlainText());
        }
        this.mCheckBox.setChecked(this.mIsFinished);
    }

    protected CharSequence getHighLightText(String str, String str2) {
        return ContentUtils.changeHighLight(str, str2);
    }

    public static void clearCachedDrawables() {
        Logger.INSTANCE.d(TAG, "clearCachedDrawables: prevCachedUiModeNight=0x" + Integer.toHexString(sCachedClockUiModeNight));
        sCachedClockD = null;
        sCachedClockN = null;
        sCachedClockExpire = null;
        sCachedRepeatN = null;
        sCachedRepeatExpire = null;
        sCachedClockUiModeNight = -1;
    }

    private static void invalidateClockCacheIfUiModeChanged() {
        int i = TodoUtils.getConfiguration().uiMode & 48;
        int i2 = sCachedClockUiModeNight;
        if (i2 != -1 && i2 != i) {
            Logger.INSTANCE.i(TAG, "invalidateClockCacheIfUiModeChanged: uiMode changed 0x" + Integer.toHexString(sCachedClockUiModeNight) + " -> 0x" + Integer.toHexString(i) + ", drop cached clock drawables");
            sCachedClockD = null;
            sCachedClockN = null;
            sCachedClockExpire = null;
            sCachedRepeatN = null;
            sCachedRepeatExpire = null;
        }
        sCachedClockUiModeNight = i;
    }

    private static Drawable getClockDrawable(int i) {
        invalidateClockCacheIfUiModeChanged();
        switch (i) {
            case R.drawable.ic_todo_item_clock_d /* 2131231515 */:
                if (sCachedClockD == null) {
                    sCachedClockD = TodoUtils.getDrawable(i);
                    Logger.INSTANCE.d(TAG, "getClockDrawable: fill clock_d at uiModeNight=0x" + Integer.toHexString(sCachedClockUiModeNight));
                }
                return sCachedClockD.getConstantState().newDrawable().mutate();
            case R.drawable.ic_todo_item_clock_expire /* 2131231516 */:
                if (sCachedClockExpire == null) {
                    sCachedClockExpire = TodoUtils.getDrawable(i);
                    Logger.INSTANCE.d(TAG, "getClockDrawable: fill clock_expire at uiModeNight=0x" + Integer.toHexString(sCachedClockUiModeNight));
                }
                return sCachedClockExpire.getConstantState().newDrawable().mutate();
            case R.drawable.ic_todo_item_clock_n /* 2131231517 */:
                if (sCachedClockN == null) {
                    sCachedClockN = TodoUtils.getDrawable(i);
                    Logger.INSTANCE.d(TAG, "getClockDrawable: fill clock_n at uiModeNight=0x" + Integer.toHexString(sCachedClockUiModeNight));
                }
                return sCachedClockN.getConstantState().newDrawable().mutate();
            case R.drawable.ic_todo_item_delete /* 2131231518 */:
            case R.drawable.ic_todo_item_drag /* 2131231519 */:
            case R.drawable.ic_todo_item_drag_n /* 2131231520 */:
            default:
                return TodoUtils.getDrawable(i).mutate();
            case R.drawable.ic_todo_item_repeat_expire /* 2131231521 */:
                if (sCachedRepeatExpire == null) {
                    sCachedRepeatExpire = TodoUtils.getDrawable(i);
                    Logger.INSTANCE.d(TAG, "getClockDrawable: fill repeat_expire at uiModeNight=0x" + Integer.toHexString(sCachedClockUiModeNight));
                }
                return sCachedRepeatExpire.getConstantState().newDrawable().mutate();
            case R.drawable.ic_todo_item_repeat_n /* 2131231522 */:
                if (sCachedRepeatN == null) {
                    sCachedRepeatN = TodoUtils.getDrawable(i);
                    Logger.INSTANCE.d(TAG, "getClockDrawable: fill repeat_n at uiModeNight=0x" + Integer.toHexString(sCachedClockUiModeNight));
                }
                return sCachedRepeatN.getConstantState().newDrawable().mutate();
        }
    }

    protected void setTimeTextColor(TextView textView, ImageView imageView, long j, int i, boolean z) {
        int i2;
        if (z) {
            textView.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
            Drawable clockDrawable = getClockDrawable(R.drawable.ic_todo_item_clock_d);
            clockDrawable.setBounds(0, 0, clockDrawable.getMinimumWidth(), clockDrawable.getMinimumHeight());
            imageView.setImageDrawable(clockDrawable);
            return;
        }
        textView.setTextColor(getNormalTimeTextColor());
        boolean zIsTimeExpired = CalenderUtils.isTimeExpired(j);
        boolean z2 = i > 0;
        if (zIsTimeExpired) {
            i2 = z2 ? R.drawable.ic_todo_item_repeat_expire : R.drawable.ic_todo_item_clock_expire;
        } else {
            i2 = z2 ? R.drawable.ic_todo_item_repeat_n : R.drawable.ic_todo_item_clock_n;
        }
        Drawable clockDrawable2 = getClockDrawable(i2);
        clockDrawable2.setBounds(0, 0, clockDrawable2.getMinimumWidth(), clockDrawable2.getMinimumHeight());
        imageView.setImageDrawable(clockDrawable2);
    }

    protected void updateViewStateByMenuState() {
        SwipeMenuLayout swipeMenuLayout = this.mRootView;
        if (swipeMenuLayout != null) {
            if (swipeMenuLayout.isMenuOpen()) {
                this.mRightMenu.setClickable(true);
                this.itemView.setLongClickable(false);
                this.mRootView.setLongClickable(false);
            } else {
                this.mRightMenu.setClickable(false);
                this.itemView.setLongClickable(true);
                this.mRootView.setLongClickable(true);
            }
        }
    }

    protected void onCheckBoxChecked(boolean z) {
        if (this.mBindContext.pLastClickCheckTime == null || System.currentTimeMillis() - this.mBindContext.pLastClickCheckTime.longValue() > 500) {
            if (this.mBindContext.getRecyclerView() != null && this.mBindContext.getRecyclerView().isAnimating() && this.mBindContext.getRecyclerView().getItemAnimator() != null) {
                this.mBindContext.getRecyclerView().getItemAnimator().endAnimations();
            }
            BaseListAdapterBindContext baseListAdapterBindContext = this.mBindContext;
            if (baseListAdapterBindContext != null && baseListAdapterBindContext.getRecyclerView().isInEditMode() && this.mItemOperationListener != null) {
                this.mItemOperationListener.onFinished(this.itemView);
                return;
            }
            BaseListAdapterBindContext baseListAdapterBindContext2 = this.mBindContext;
            if (baseListAdapterBindContext2 != null) {
                baseListAdapterBindContext2.pLastClickCheckTime = Long.valueOf(System.currentTimeMillis());
                this.mBindContext.pLastClickCheckType = 0;
            }
            this.mCheckBox.setActivated(true);
            this.mCheckBox.setChecked(true);
            setListItemFinished();
            if (this.mItemOperationListener != null && !z) {
                this.mItemOperationListener.onFinished(this.itemView);
            }
            View view = this.mCheckboxMask;
            view.setContentDescription(view.getContext().getString(R.string.todo_item_status_finished));
            View view2 = this.mCheckboxMask;
            view2.announceForAccessibility(view2.getContentDescription());
            ToDoStat.markTodoCardOperate(ToDoStat.CHECK_DONE, this.todo_type, this.mIsFinished);
        }
    }

    protected void onCheckBoxUnChecked() {
        int i = this.mBindContext.pLastClickCheckType == 1 ? 500 : 800;
        if (this.mBindContext.pLastClickCheckTime == null || System.currentTimeMillis() - this.mBindContext.pLastClickCheckTime.longValue() > i) {
            if (this.mBindContext.getRecyclerView() != null && this.mBindContext.getRecyclerView().isAnimating() && this.mBindContext.getRecyclerView().getItemAnimator() != null) {
                this.mBindContext.getRecyclerView().getItemAnimator().endAnimations();
            }
            if (this.mItemOperationListener != null) {
                this.mItemOperationListener.onUnFinished(this.itemView);
            }
            if (this.mBindContext.getRecyclerView().isInEditMode()) {
                return;
            }
            this.mBindContext.pLastClickCheckTime = Long.valueOf(System.currentTimeMillis());
            this.mBindContext.pLastClickCheckType = 1;
            this.mCheckBox.setChecked(false);
            View view = this.mCheckboxMask;
            view.setContentDescription(view.getContext().getString(R.string.todo_item_status_unfinished));
            View view2 = this.mCheckboxMask;
            view2.announceForAccessibility(view2.getContentDescription());
            ToDoStat.markTodoCardOperate(ToDoStat.CANCEL_DONE, this.todo_type, this.mIsFinished);
        }
    }

    protected void onChecked() {
        this.mContent.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
        setTimeTextColor(this.mTime, this.mIvClock, this.mScheduleTime, this.mRepeatType, true);
    }

    protected void onUnChecked() {
        this.mContent.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_common_color));
        setTimeTextColor(this.mTime, this.mIvClock, this.mScheduleTime, this.mRepeatType, false);
    }

    public void onCheckBox() {
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null) {
            checkBox.setChecked(true);
        }
    }

    public void onRecycled() {
        AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener;
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager == null || (touchExplorationStateChangeListener = this.mTouchExplorationStateChangeListener) == null) {
            return;
        }
        accessibilityManager.removeTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
        this.mTouchExplorationStateChangeListener = null;
    }

    public boolean isFinished() {
        return this.mIsFinished;
    }

    public void setTodoItemFinished() {
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null) {
            checkBox.setChecked(true);
        }
        onCheckBoxChecked(true);
    }

    private static class WeakTouchExplorationListener implements AccessibilityManager.TouchExplorationStateChangeListener {
        private final WeakReference<BaseTodoChildItemVh> mVhRef;

        WeakTouchExplorationListener(BaseTodoChildItemVh baseTodoChildItemVh) {
            this.mVhRef = new WeakReference<>(baseTodoChildItemVh);
        }

        @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
        public void onTouchExplorationStateChanged(boolean z) {
            BaseTodoChildItemVh baseTodoChildItemVh = this.mVhRef.get();
            if (baseTodoChildItemVh != null) {
                baseTodoChildItemVh.setContentClickable(z);
            }
        }
    }
}
