package com.miui.todo.view;

import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.AdapterView;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.utils.TimeUtils;
import com.miui.todo.utils.CalenderUtils;
import miuix.appcompat.adapter.SpinnerDoubleLineContentAdapter;
import miuix.appcompat.app.CalendarDatePickerDialog;
import miuix.appcompat.widget.Spinner;

/* JADX INFO: loaded from: classes4.dex */
public class RemindTimePickerDialog extends CalendarDatePickerDialog {
    private int mRepeatMode;
    private OnTimeSetListener mTimeListener;
    private Spinner mTvSetRepeat;
    private SpinnerDoubleLineContentAdapter mTvSetRepeatAdapter;

    public interface OnTimeSetListener {
        void onCancel();

        void onTimeSet(RemindTimePickerDialog remindTimePickerDialog, RemindTimeConfig remindTimeConfig);
    }

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

    public static int getSelectIndexFromRepeatMode(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 3;
            if (i != 2) {
                if (i == 3) {
                    return 2;
                }
                if (i != 7) {
                    return i != 8 ? 0 : 5;
                }
                return 4;
            }
        }
        return i2;
    }

    public RemindTimePickerDialog(Context context, int i, OnTimeSetListener onTimeSetListener, int i2) {
        this(context, i, onTimeSetListener, i2, 1);
    }

    public RemindTimePickerDialog(Context context, int i, OnTimeSetListener onTimeSetListener, int i2, int i3) {
        super(context, i);
        this.mTimeListener = onTimeSetListener;
        this.mRepeatMode = i2;
        init(i3);
    }

    private void init(int i) {
        setTitle(R.string.todo_time_picker_dialog_title);
        setTimePickerEnabled(true, getContext().getString(R.string.todo_time_picker_dialog_spinner_title));
        if (getTimePicker() != null) {
            getTimePicker().setCurrentMinute(Integer.valueOf(getTimePicker().getCurrentMinute().intValue() + 1));
        }
        setLunarMode(true);
        setSpinnerEnabled(true);
        setSpinnerEnabled(true, getContext().getString(R.string.repeat_mode_set_title), initItemsArray());
        setDate(System.currentTimeMillis() + 60000);
        setOnDataConfirmListener(new CalendarDatePickerDialog.OnDataConfirmListener() { // from class: com.miui.todo.view.RemindTimePickerDialog.1
            @Override // miuix.appcompat.app.CalendarDatePickerDialog.OnDataConfirmListener
            public void onDataConfirm(int i2, int i3, int i4, int i5, int i6, int i7, String str) {
                if (RemindTimePickerDialog.this.mTimeListener != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(i2, i3, i4, i5, i6, 0);
                    long timeInMillis = calendar.getTimeInMillis();
                    RemindTimePickerDialog.this.checkTimeValidForRepeatMode(timeInMillis);
                    RemindTimeConfig remindTimeConfig = new RemindTimeConfig();
                    remindTimeConfig.isWholeDay = false;
                    remindTimeConfig.remindTime = timeInMillis;
                    remindTimeConfig.repeatType = RemindTimePickerDialog.this.mRepeatMode;
                    RemindTimePickerDialog.this.mTimeListener.onTimeSet(RemindTimePickerDialog.this, remindTimeConfig);
                    Utils.checkAndSwitchAutoStart();
                }
            }
        });
        setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.todo.view.RemindTimePickerDialog.2
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                if (RemindTimePickerDialog.this.mTimeListener != null) {
                    RemindTimePickerDialog.this.mTimeListener.onCancel();
                }
            }
        });
        this.mTvSetRepeat = getSpinner();
        this.mTvSetRepeatAdapter = new SpinnerDoubleLineContentAdapter(getContext(), initItemsArray(), null, null);
        this.mTvSetRepeat.setWindowManagerFlags(0);
        if (getSpinner() != null) {
            getSpinner().setDoubleLineContentAdapter(this.mTvSetRepeatAdapter);
            getSpinner().setSelection(getSelectIndexFromRepeatMode(this.mRepeatMode));
            getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.miui.todo.view.RemindTimePickerDialog.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                    RemindTimePickerDialog.this.mRepeatMode = RemindTimePickerDialog.getRepeatModeFromSelectIndex(i2);
                }
            });
        }
    }

    private CharSequence[] initItemsArray() {
        return new CharSequence[]{getContext().getResources().getString(R.string.repeat_mode_default), getContext().getResources().getString(R.string.repeat_mode_day), getContext().getResources().getString(R.string.repeat_mode_normal_workday), getContext().getResources().getString(R.string.repeat_mode_week), getContext().getResources().getString(R.string.repeat_mode_month), getContext().getResources().getString(R.string.repeat_mode_year)};
    }

    public void update(long j) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (j <= 0) {
            setDate(jCurrentTimeMillis + 60000);
        } else if (j > jCurrentTimeMillis) {
            setDate(j);
        } else {
            setDate(jCurrentTimeMillis + 60000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkTimeValidForRepeatMode(long j) {
        if (this.mRepeatMode == 3) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(j);
            if (CalenderUtils.isSunOrSat(calendar)) {
                TimeUtils.setNextWorkDay(calendar);
                long timeInMillis = calendar.getTimeInMillis();
                setDate(timeInMillis);
                setDate(timeInMillis);
            }
        }
    }
}
