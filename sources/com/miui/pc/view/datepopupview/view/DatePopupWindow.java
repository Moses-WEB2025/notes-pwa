package com.miui.pc.view.datepopupview.view;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.R;
import com.miui.pc.feature.todo.PcTodoListFragment;
import com.miui.pc.view.OnReminderItemClickListener;
import com.miui.pc.view.ReminderListAdapter;
import com.miui.pc.view.RepeatReminderPopupWindow;
import com.miui.pc.view.datepopupview.adapter.DateAdapter;
import com.miui.pc.view.datepopupview.adapter.OnRecyItemClickListener;
import com.miui.pc.view.datepopupview.utils.DateUtils;
import com.miui.todo.data.bean.RemindTimeConfig;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class DatePopupWindow extends PopupWindow implements View.OnClickListener, View.OnFocusChangeListener {
    private static final int TIME_MAX_LENGTH = 2;
    private DateAdapter mAdapter;
    private Button mCancelButton;
    private View mContentView;
    private Context mContext;
    public String mCurrDate;
    private TextView mCurrentMonthView;
    private EditText mEditTextSetHour;
    private EditText mEditTextSetMin;
    private ImageView mFrontMonthArrow;
    private RecyclerView mGridView;
    private ImageView mNextMonthArrow;
    private PcTodoListFragment.OnTimeSetListener mOnTimeSetListener;
    private TextView mReminderView;
    private int mRepeatMode;
    private View mRepeatReminderRegion;
    public String mSelectedDate;
    private TextView mSelectedDateView;
    private Button mSubmitButton;
    private TextView mTimeSeparator;

    public DatePopupWindow(Context context, long j, int i, PcTodoListFragment.OnTimeSetListener onTimeSetListener) {
        String str = new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(j));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm");
        this.mContext = context;
        this.mOnTimeSetListener = onTimeSetListener;
        this.mRepeatMode = i;
        View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.pc_date_picker, (ViewGroup) null);
        this.mContentView = viewInflate;
        this.mSelectedDateView = (TextView) viewInflate.findViewById(R.id.selected_date_view);
        this.mCurrentMonthView = (TextView) this.mContentView.findViewById(R.id.current_month_view);
        this.mCurrDate = DateUtils.getCurrDate("yyyy-MM-dd");
        if (TextUtils.isEmpty(str)) {
            this.mSelectedDate = this.mCurrDate;
        } else {
            this.mSelectedDate = str;
        }
        this.mSelectedDateView.setText(DateUtils.getRealYearAndMonthAndDay(this.mSelectedDate));
        this.mCurrentMonthView.setText(DateUtils.getRealYearAndMonth(this.mSelectedDate));
        this.mFrontMonthArrow = (ImageView) this.mContentView.findViewById(R.id.front_month_arrow);
        this.mNextMonthArrow = (ImageView) this.mContentView.findViewById(R.id.next_month_arrow);
        this.mCancelButton = (Button) this.mContentView.findViewById(R.id.cancel_button);
        this.mSubmitButton = (Button) this.mContentView.findViewById(R.id.submit_button);
        this.mRepeatReminderRegion = this.mContentView.findViewById(R.id.set_reminder_region);
        this.mReminderView = (TextView) this.mContentView.findViewById(R.id.reminder_view);
        setRepeatText(i);
        this.mEditTextSetHour = (EditText) this.mContentView.findViewById(R.id.set_time_hour);
        this.mEditTextSetMin = (EditText) this.mContentView.findViewById(R.id.set_time_min);
        this.mTimeSeparator = (TextView) this.mContentView.findViewById(R.id.time_separator);
        this.mEditTextSetHour.setOnFocusChangeListener(this);
        this.mEditTextSetMin.setOnFocusChangeListener(this);
        setupClick(this.mFrontMonthArrow);
        setupClick(this.mNextMonthArrow);
        setupClick(this.mCancelButton);
        setupClick(this.mSubmitButton);
        setupClick(this.mRepeatReminderRegion);
        this.mEditTextSetHour.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2), new TimeInputFilter(0)});
        this.mEditTextSetHour.setText(simpleDateFormat.format(Long.valueOf(j)));
        this.mEditTextSetHour.setLongClickable(false);
        this.mEditTextSetMin.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2), new TimeInputFilter(1)});
        this.mEditTextSetMin.setText(simpleDateFormat2.format(Long.valueOf(j)));
        this.mEditTextSetMin.setLongClickable(false);
        setContentView(this.mContentView);
        setHeight(-2);
        setWidth(-2);
        setFocusable(true);
        setOutsideTouchable(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7) { // from class: com.miui.pc.view.datepopupview.view.DatePopupWindow.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        };
        this.mGridView = (RecyclerView) this.mContentView.findViewById(R.id.date_grid_picker);
        DateAdapter dateAdapter = new DateAdapter(context, DateUtils.getMonthDate(this.mSelectedDate), this.mSelectedDate);
        this.mAdapter = dateAdapter;
        this.mGridView.setAdapter(dateAdapter);
        this.mGridView.setLayoutManager(gridLayoutManager);
        this.mAdapter.setOnItemClickListener(new OnRecyItemClickListener() { // from class: com.miui.pc.view.datepopupview.view.DatePopupWindow.2
            @Override // com.miui.pc.view.datepopupview.adapter.OnRecyItemClickListener
            public void onClick(View view, String str2, int i2) {
                if (!view.hasFocus() && DatePopupWindow.this.mContentView.findFocus() != null) {
                    DatePopupWindow.this.mContentView.findFocus().clearFocus();
                    view.requestFocus();
                }
                DatePopupWindow.this.setSelectedDate(str2);
                if (i2 != 0) {
                    DatePopupWindow.this.mAdapter.setData(DateUtils.getMonthDate(DatePopupWindow.this.mSelectedDate));
                }
            }
        });
        update();
    }

    private void setupClick(View view) {
        view.setOnClickListener(this);
        view.setFocusable(true);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (!view.hasFocus() && this.mContentView.findFocus() != null) {
            this.mContentView.findFocus().clearFocus();
            view.requestFocus();
        }
        switch (view.getId()) {
            case R.id.cancel_button /* 2131362139 */:
                this.mOnTimeSetListener.onCancel();
                dismiss();
                break;
            case R.id.front_month_arrow /* 2131362605 */:
                setSelectedDate(DateUtils.getFirstMonthDay(this.mSelectedDate, -1));
                this.mAdapter.setData(DateUtils.getMonthDate(this.mSelectedDate));
                break;
            case R.id.next_month_arrow /* 2131363050 */:
                setSelectedDate(DateUtils.getFirstMonthDay(this.mSelectedDate, 1));
                this.mAdapter.setData(DateUtils.getMonthDate(this.mSelectedDate));
                break;
            case R.id.set_reminder_region /* 2131363415 */:
                new RepeatReminderPopupWindow(this.mContext, new OnReminderItemClickListener() { // from class: com.miui.pc.view.datepopupview.view.DatePopupWindow.3
                    @Override // com.miui.pc.view.OnReminderItemClickListener
                    public void onClick(View view2, int i) {
                        DatePopupWindow.this.mRepeatMode = i;
                        int repeatModeText = ReminderListAdapter.getRepeatModeText(i);
                        if (repeatModeText > 0) {
                            DatePopupWindow.this.mReminderView.setText(repeatModeText);
                        }
                    }
                }, this.mRepeatMode).showPopupWindow(this.mRepeatReminderRegion);
                break;
            case R.id.submit_button /* 2131363546 */:
                RemindTimeConfig remindTimeConfig = new RemindTimeConfig();
                remindTimeConfig.remindTime = getSetTime();
                remindTimeConfig.repeatType = this.mRepeatMode;
                this.mOnTimeSetListener.onTimeSet(remindTimeConfig);
                dismiss();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSelectedDate(String str) {
        this.mSelectedDate = str;
        this.mAdapter.setSelectedDate(str);
        this.mSelectedDateView.setText(DateUtils.getRealYearAndMonthAndDay(str));
        this.mCurrentMonthView.setText(DateUtils.getRealYearAndMonth(str));
    }

    private void setArrowStatus() {
        if (DateUtils.isCurrentMonth(this.mSelectedDate)) {
            this.mFrontMonthArrow.setImageResource(R.drawable.ic_month_left_arrow_unavaible);
            this.mFrontMonthArrow.setClickable(false);
        } else {
            this.mFrontMonthArrow.setImageResource(R.drawable.ic_month_left_arrow_avaible);
            this.mFrontMonthArrow.setClickable(true);
        }
    }

    public static String getValue(int i) {
        return String.valueOf(i > 9 ? Integer.valueOf(i) : "0" + i);
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(final View view, boolean z) {
        if (!z) {
            switch (view.getId()) {
                case R.id.set_time_hour /* 2131363418 */:
                    this.mEditTextSetHour.setText(getValue(TextUtils.isEmpty(this.mEditTextSetHour.getText().toString()) ? 0 : Integer.valueOf(this.mEditTextSetHour.getText().toString()).intValue()));
                    if (!this.mEditTextSetMin.isFocused()) {
                        onTimeRegionLoseFocus();
                    }
                    break;
                case R.id.set_time_min /* 2131363419 */:
                    this.mEditTextSetMin.setText(getValue(TextUtils.isEmpty(this.mEditTextSetMin.getText().toString()) ? 0 : Integer.valueOf(this.mEditTextSetMin.getText().toString()).intValue()));
                    if (!this.mEditTextSetHour.isFocused()) {
                        onTimeRegionLoseFocus();
                    }
                    break;
            }
            return;
        }
        onTimeRegionGetFocus();
        view.postDelayed(new Runnable() { // from class: com.miui.pc.view.datepopupview.view.DatePopupWindow.4
            @Override // java.lang.Runnable
            public void run() {
                ((EditText) view).setSelection(0, ((EditText) view).getText().length());
            }
        }, 10L);
    }

    private void onTimeRegionLoseFocus() {
        int color = Color.parseColor("#66000000");
        this.mEditTextSetMin.setTextColor(color);
        this.mEditTextSetHour.setTextColor(color);
        this.mTimeSeparator.setTextColor(color);
    }

    private void onTimeRegionGetFocus() {
        int color = Color.parseColor("#E6000000");
        this.mEditTextSetMin.setTextColor(color);
        this.mEditTextSetHour.setTextColor(color);
        this.mTimeSeparator.setTextColor(color);
    }

    public class TimeInputFilter implements InputFilter {
        private int mType;

        public TimeInputFilter(int i) {
            this.mType = i;
        }

        @Override // android.text.InputFilter
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if (charSequence.toString().equals("")) {
                return null;
            }
            while (i < i2) {
                if (!Character.isDigit(charSequence.charAt(i))) {
                    return "";
                }
                i++;
            }
            StringBuilder sb = new StringBuilder(spanned);
            sb.delete(i3, i4);
            sb.insert(i3, charSequence);
            int iIntValue = Integer.valueOf(sb.toString()).intValue();
            int i5 = this.mType;
            if (i5 == 0) {
                if (iIntValue > 23 || iIntValue < 0) {
                    return spanned.subSequence(i3, i4);
                }
                return null;
            }
            if (i5 != 1) {
                return null;
            }
            if (iIntValue > 59 || iIntValue < 0) {
                return spanned.subSequence(i3, i4);
            }
            return null;
        }
    }

    private void setRepeatText(int i) {
        int repeatModeText = ReminderListAdapter.getRepeatModeText(i);
        if (repeatModeText > 0) {
            this.mReminderView.setText(repeatModeText);
        }
    }

    private long getSetTime() {
        String string = this.mEditTextSetHour.getText().toString();
        String string2 = this.mEditTextSetMin.getText().toString();
        if (TextUtils.isEmpty(string)) {
            string = "00";
        }
        if (TextUtils.isEmpty(string2)) {
            string2 = "00";
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(this.mSelectedDate + StringUtils.SPACE + string + ":" + string2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
