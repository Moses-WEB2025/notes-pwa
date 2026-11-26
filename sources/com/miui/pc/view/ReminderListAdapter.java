package com.miui.pc.view;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.R;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class ReminderListAdapter extends RecyclerView.Adapter<ReminderViewHolder> {
    private Context mContext;
    private CharSequence[] mDatas;
    private OnReminderItemClickListener mOnItemClickListener;
    private int mSelectedMode;
    private PopupWindow mWindow;

    public static int getRepeatModeFromSelectIndex(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 3;
            if (i != 2) {
                if (i == 3) {
                    return 2;
                }
                if (i != 4) {
                    return i != 5 ? 0 : 8;
                }
                return 7;
            }
        }
        return i2;
    }

    public static int getRepeatModeText(int i) {
        if (i == 0) {
            return R.string.repeat_mode_default;
        }
        if (i == 1) {
            return R.string.repeat_mode_day;
        }
        if (i == 2) {
            return R.string.repeat_mode_week;
        }
        if (i == 3) {
            return R.string.repeat_mode_normal_workday;
        }
        if (i == 7) {
            return R.string.repeat_mode_month;
        }
        if (i != 8) {
            return 0;
        }
        return R.string.repeat_mode_year;
    }

    public ReminderListAdapter(Context context, PopupWindow popupWindow, long j, int i) {
        this.mContext = context;
        this.mWindow = popupWindow;
        this.mDatas = initItemsArray(j);
        this.mSelectedMode = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ReminderViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ReminderViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.repeat_reminder, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ReminderViewHolder reminderViewHolder, final int i) {
        reminderViewHolder.mTitle.setText(this.mDatas[i]);
        if (this.mSelectedMode == getRepeatModeFromSelectIndex(i)) {
            reminderViewHolder.mTitle.setTextColor(this.mContext.getColor(R.color.pc_todo_btn_done_color));
        } else {
            reminderViewHolder.mTitle.setTextColor(Color.parseColor("#CC000000"));
        }
        reminderViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.view.ReminderListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ReminderListAdapter.this.mOnItemClickListener.onClick(view, ReminderListAdapter.getRepeatModeFromSelectIndex(i));
                ReminderListAdapter.this.mWindow.dismiss();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDatas.length;
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;

        public ReminderViewHolder(View view) {
            super(view);
            this.mTitle = (TextView) view.findViewById(R.id.repeat_mode);
        }
    }

    public void setOnItemClickListener(OnReminderItemClickListener onReminderItemClickListener) {
        this.mOnItemClickListener = onReminderItemClickListener;
    }

    private CharSequence[] initItemsArray(long j) {
        CharSequence[] charSequenceArr = new CharSequence[6];
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        charSequenceArr[0] = this.mContext.getResources().getString(R.string.repeat_mode_set_item_none);
        charSequenceArr[1] = this.mContext.getResources().getString(R.string.repeat_mode_set_item_day);
        charSequenceArr[2] = this.mContext.getResources().getString(R.string.repeat_mode_set_item_normal_workday);
        sb.append(String.format(this.mContext.getResources().getString(R.string.repeat_mode_set_item_week), String.format(this.mContext.getResources().getString(R.string.repeat_mode_set_item_week_detail), new SimpleDateFormat("EEEE").format(calendar.getTime()))));
        charSequenceArr[3] = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        if (calendar.getActualMaximum(5) == 31 && calendar.getActualMaximum(5) == calendar.get(5)) {
            sb2.append(this.mContext.getResources().getString(R.string.repeat_mode_set_item_month_detail_lastday));
        } else if (Locale.getDefault().getLanguage().equals(new Locale("zh").getLanguage())) {
            sb2.append(String.format(this.mContext.getResources().getString(R.string.repeat_mode_set_item_month), String.format(this.mContext.getResources().getString(R.string.repeat_mode_set_item_month_detail), new SimpleDateFormat(this.mContext.getResources().getString(R.string.repeat_mode_set_item_month_detail_day)).format(calendar.getTime()))));
        } else {
            sb2.append(this.mContext.getResources().getString(R.string.repeat_mode_set_item_month_global));
        }
        charSequenceArr[4] = sb2.toString();
        charSequenceArr[5] = String.format(this.mContext.getResources().getString(R.string.repeat_mode_set_item_year), String.format(this.mContext.getResources().getString(R.string.repeat_mode_set_item_year_detail), new SimpleDateFormat(this.mContext.getResources().getString(R.string.repeat_mode_set_item_year_detail_day)).format(calendar.getTime())));
        return charSequenceArr;
    }

    public void setSelectedMode(int i) {
        this.mSelectedMode = i;
    }
}
