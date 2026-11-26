package com.miui.todo.feature.todolist;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.StrikeTextView.StrikeTextView;
import com.miui.common.view.StrikeTextView.StrikeThroughPainting;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.utils.CalenderUtils;
import com.miui.todo.utils.TodoUtils;
import java.util.regex.Matcher;
import miuix.animation.utils.EaseManager;
import org.apache.commons.text.StringEscapeUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TodoItemForAllFinishVh implements ITodoItemFakeVh {
    protected CheckBox mCheckBox;
    protected StrikeTextView mContent;
    protected View mContentGroup;
    protected View mContentRootView;
    private LinearLayout mExpandGroup;
    protected ImageView mIvAudio;
    protected ImageView mIvClock;
    private ImageView mIvExpand;
    protected View mLabelGroup;
    protected int mRepeatType;
    protected View mRootView;
    protected long mScheduleTime;
    protected StrikeThroughPainting mStrikeThroughPainting;
    protected TextView mTime;
    private TodoEntity mTodoEntity;
    private TextView mTvProgress;

    public static TodoItemForAllFinishVh newInstance(ViewGroup viewGroup) {
        return new TodoItemForAllFinishVh(UIUtils.inflateView(viewGroup, R.layout.todo_task_item_for_allfinish), false);
    }

    public static TodoItemForAllFinishVh newInstance(ViewGroup viewGroup, boolean z) {
        return new TodoItemForAllFinishVh(UIUtils.inflateView(viewGroup, R.layout.todo_task_item_for_allfinish), z);
    }

    public TodoItemForAllFinishVh(View view, boolean z) {
        this.mStrikeThroughPainting = null;
        this.mRootView = view;
        this.mContentGroup = view.findViewById(R.id.content_group);
        this.mContentRootView = view.findViewById(R.id.item_content);
        this.mLabelGroup = view.findViewById(R.id.label);
        this.mTime = (TextView) view.findViewById(R.id.time);
        this.mContent = (StrikeTextView) view.findViewById(R.id.content);
        this.mIvAudio = (ImageView) view.findViewById(R.id.iv_audio);
        this.mIvClock = (ImageView) view.findViewById(R.id.iv_clock);
        CheckBox checkBox = (CheckBox) view.findViewById(android.R.id.checkbox);
        this.mCheckBox = checkBox;
        checkBox.setActivated(true);
        this.mCheckBox.setChecked(true);
        this.mCheckBox.setClickable(false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_expand_group);
        this.mExpandGroup = linearLayout;
        linearLayout.setClickable(false);
        this.mIvExpand = (ImageView) view.findViewById(R.id.iv_expand);
        TextView textView = (TextView) view.findViewById(R.id.tv_progress);
        this.mTvProgress = textView;
        DisplayUtils.setMiuiMediumTypeFace(textView);
        this.mRootView.setImportantForAccessibility(4);
        this.mRootView.setAccessibilityDelegate(new View.AccessibilityDelegate());
        this.mStrikeThroughPainting = new StrikeThroughPainting(this.mContent);
        if (z) {
            this.mContent.setTextColor(TodoUtils.getColor(R.color.user_query_highlight));
        } else {
            this.mContent.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
        }
        this.mTime.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
        DisplayUtils.setMiuiMediumTypeFace(this.mContent);
        DisplayUtils.setMiuiMediumTypeFace(this.mTime);
        Drawable drawable = TodoUtils.getDrawable(R.drawable.ic_todo_item_clock_d);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.mIvClock.setImageDrawable(drawable);
    }

    @Override // com.miui.todo.feature.todolist.ITodoItemFakeVh
    public void bind(TodoEntity todoEntity) {
        this.mStrikeThroughPainting.clearStrikeThrough();
        this.mScheduleTime = todoEntity.getRemindTime();
        this.mRepeatType = todoEntity.getRemindRepeatType();
        this.mTime.setText(CalenderUtils.getScheduleTimeString(todoEntity.getRemindType(), todoEntity.getRemindTime(), todoEntity.getRemindRepeatType()));
        TextView textView = this.mTime;
        textView.setContentDescription(textView.getText());
        setTimeTextColor(this.mTime, this.mIvClock, this.mScheduleTime, this.mRepeatType, true);
        if (todoEntity.getListType() == 1) {
            this.mTvProgress.setVisibility(0);
            this.mExpandGroup.setVisibility(0);
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
        if (todoEntity.getRemindType() == 0 && todoEntity.getInputType() != 1) {
            this.mLabelGroup.setVisibility(8);
        } else {
            this.mLabelGroup.setVisibility(0);
        }
        setContent(todoEntity);
    }

    protected void setTimeTextColor(TextView textView, ImageView imageView, long j, int i, boolean z) {
        Drawable drawableMutate;
        if (z) {
            textView.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
            Drawable drawableMutate2 = TodoUtils.getDrawable(R.drawable.ic_todo_item_clock_d).mutate();
            drawableMutate2.setBounds(0, 0, drawableMutate2.getMinimumWidth(), drawableMutate2.getMinimumHeight());
            imageView.setImageDrawable(drawableMutate2);
            return;
        }
        if (CalenderUtils.isTimeExpired(j)) {
            textView.setTextColor(getNormalTimeTextColor());
            if (i > 0) {
                drawableMutate = TodoUtils.getDrawable(R.drawable.ic_todo_item_repeat_expire).mutate();
                drawableMutate.setBounds(0, 0, drawableMutate.getMinimumWidth(), drawableMutate.getMinimumHeight());
            } else {
                drawableMutate = TodoUtils.getDrawable(R.drawable.ic_todo_item_clock_expire).mutate();
                drawableMutate.setBounds(0, 0, drawableMutate.getMinimumWidth(), drawableMutate.getMinimumHeight());
            }
        } else {
            textView.setTextColor(getNormalTimeTextColor());
            if (i > 0) {
                drawableMutate = TodoUtils.getDrawable(R.drawable.ic_todo_item_repeat_n).mutate();
                drawableMutate.setBounds(0, 0, drawableMutate.getMinimumWidth(), drawableMutate.getMinimumHeight());
            } else {
                drawableMutate = TodoUtils.getDrawable(R.drawable.ic_todo_item_clock_n).mutate();
                drawableMutate.setBounds(0, 0, drawableMutate.getMinimumWidth(), drawableMutate.getMinimumHeight());
            }
        }
        imageView.setImageDrawable(drawableMutate);
    }

    protected void setContent(TodoEntity todoEntity) {
        String string;
        String content = todoEntity.getContent();
        if (todoEntity.getListType() == 1) {
            SubModeImpl subModeImpl = new SubModeImpl(todoEntity.getContent());
            string = TextUtils.isEmpty(subModeImpl.getTitle()) ? NoteApp.getInstance().getString(R.string.todo_list) : subModeImpl.getTitle();
            this.mIvExpand.setImageResource(R.drawable.ic_list_expand);
            this.mTvProgress.setText(subModeImpl.getCompleteSchedule());
        } else {
            Matcher audioLabelIndex = AudioUtils.getAudioLabelIndex(content);
            if (audioLabelIndex != null && audioLabelIndex.find()) {
                content = content.replace(content.substring(audioLabelIndex.start(), audioLabelIndex.end() + 1), "");
            }
            this.mExpandGroup.setVisibility(8);
            string = content;
        }
        if (TextUtils.isEmpty(todoEntity.getPlainText()) && todoEntity.getInputType() == 1) {
            this.mContent.setText(TodoUtils.getString(R.string.todo_audio_default_content));
            StrikeTextView strikeTextView = this.mContent;
            strikeTextView.setContentDescription(strikeTextView.getText());
        } else {
            this.mContent.setText(StringEscapeUtils.unescapeHtml4(string));
            this.mContent.setContentDescription(todoEntity.getPlainText());
        }
    }

    protected int getNormalTimeTextColor() {
        return TodoUtils.getColor(R.color.todo_item_time_text_common_color);
    }

    protected CharSequence getHighLightText(String str, String str2) {
        return ContentUtils.changeHighLight(str, str2);
    }

    @Override // com.miui.todo.feature.todolist.ITodoItemFakeVh
    public View getItemView() {
        return this.mRootView;
    }

    @Override // com.miui.todo.feature.todolist.ITodoItemFakeVh
    public void startCheckAnim() {
        this.mRootView.animate().scaleX(1.03f).scaleY(1.03f).setDuration(300L).withEndAction(new Runnable() { // from class: com.miui.todo.feature.todolist.TodoItemForAllFinishVh.1
            @Override // java.lang.Runnable
            public void run() {
                TodoItemForAllFinishVh.this.mStrikeThroughPainting.changeStrikeStyle(300L, TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
                TodoItemForAllFinishVh.this.mCheckBox.setActivated(false);
                TodoItemForAllFinishVh.this.mRootView.animate().scaleX(1.0f).scaleY(1.0f).setInterpolator(new EaseManager.SpringInterpolator()).setStartDelay(100L).start();
            }
        }).setInterpolator(new EaseManager.SpringInterpolator()).start();
        this.mStrikeThroughPainting.cutTextEdge(true).color(TodoUtils.getColor(R.color.theme_main_color)).strokeWidth(6.0f).totalTime(300L);
        this.mStrikeThroughPainting.strikeThrough();
    }
}
