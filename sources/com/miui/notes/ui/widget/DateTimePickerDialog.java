package com.miui.notes.ui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import miuix.appcompat.app.AlertDialog;
import miuix.pickerwidget.widget.DateTimePicker;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes4.dex */
public class DateTimePickerDialog extends AlertDialog {
    private ViewGroup mAlarmbar;
    private TextView mAlertTextView;
    private SlidingButton mEnabler;
    private OnTimeSetListener mTimeListener;
    private DateTimePicker mTimePicker;

    public interface OnTimeSetListener {
        void cancelAlarm();

        void onTimeSet(DateTimePickerDialog dateTimePickerDialog, long j);
    }

    public DateTimePickerDialog(Context context, OnTimeSetListener onTimeSetListener) {
        this(context, onTimeSetListener, 1);
    }

    public DateTimePickerDialog(Context context, OnTimeSetListener onTimeSetListener, int i) {
        super(context, R.style.AlertDialog_Theme_DayNight_Note);
        this.mTimeListener = onTimeSetListener;
        init(i);
        setTitle(R.string.date_time_picker_dialog_title);
    }

    private void init(int i) {
        setButton(-1, getContext().getText(android.R.string.ok), new DialogInterface.OnClickListener() { // from class: com.miui.notes.ui.widget.DateTimePickerDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                if (DateTimePickerDialog.this.mTimeListener != null) {
                    if (DateTimePickerDialog.this.mAlarmbar.getVisibility() == 0) {
                        if (DateTimePickerDialog.this.mEnabler.isChecked()) {
                            OnTimeSetListener onTimeSetListener = DateTimePickerDialog.this.mTimeListener;
                            DateTimePickerDialog dateTimePickerDialog = DateTimePickerDialog.this;
                            onTimeSetListener.onTimeSet(dateTimePickerDialog, dateTimePickerDialog.mTimePicker.getTimeInMillis());
                        } else {
                            DateTimePickerDialog.this.mTimeListener.cancelAlarm();
                            Toast.makeText(DateTimePickerDialog.this.getContext(), R.string.date_time_picker_has_not_set_alarm, 0).show();
                        }
                    } else {
                        OnTimeSetListener onTimeSetListener2 = DateTimePickerDialog.this.mTimeListener;
                        DateTimePickerDialog dateTimePickerDialog2 = DateTimePickerDialog.this;
                        onTimeSetListener2.onTimeSet(dateTimePickerDialog2, dateTimePickerDialog2.mTimePicker.getTimeInMillis());
                    }
                }
                Utils.checkAndSwitchAutoStart();
            }
        });
        setButton(-2, getContext().getText(android.R.string.cancel), (DialogInterface.OnClickListener) null);
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.datetime_picker_dialog, (ViewGroup) null);
        setView(viewInflate);
        DateTimePicker dateTimePicker = (DateTimePicker) viewInflate.findViewById(R.id.dateTimePicker);
        this.mTimePicker = dateTimePicker;
        dateTimePicker.setMinuteInterval(i);
        this.mAlertTextView = (TextView) viewInflate.findViewById(R.id.alarm_at);
        SlidingButton slidingButton = (SlidingButton) viewInflate.findViewById(R.id.alarm_enabler);
        this.mEnabler = slidingButton;
        slidingButton.setChecked(true);
        this.mEnabler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.notes.ui.widget.DateTimePickerDialog.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    DateTimePickerDialog.this.mEnabler.setContentDescription(NoteApp.getInstance().getResources().getString(R.string.set_alarm_description));
                } else {
                    DateTimePickerDialog.this.mEnabler.setContentDescription(NoteApp.getInstance().getResources().getString(R.string.cancel_alarm_description));
                }
            }
        });
        this.mAlarmbar = (ViewGroup) viewInflate.findViewById(R.id.alarm_bar);
    }

    public void update(long j) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (j <= 0) {
            this.mAlarmbar.setVisibility(8);
        } else if (j > jCurrentTimeMillis) {
            this.mAlarmbar.setVisibility(0);
            this.mTimePicker.update(j);
        } else {
            this.mAlarmbar.setVisibility(8);
            this.mTimePicker.update(jCurrentTimeMillis);
        }
    }

    public void setMinDateTime(long j) {
        this.mTimePicker.setMinDateTime(j);
    }

    public void setMaxDateTime(long j) {
        this.mTimePicker.setMaxDateTime(j);
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }
}
