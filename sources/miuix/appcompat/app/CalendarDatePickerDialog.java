package miuix.appcompat.app;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.RecyclerView;
import java.time.ZonedDateTime;
import miuix.appcompat.adapter.SpinnerDoubleLineContentAdapter;
import miuix.appcompat.internal.util.DrawableUtil;
import miuix.appcompat.internal.widget.DialogParentPanel2;
import miuix.appcompat.widget.Spinner;
import miuix.core.util.EnvStateManager;
import miuix.core.widget.NestedScrollView;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.widget.Calendar.CalendarDatePicker;
import miuix.pickerwidget.widget.DatePicker;
import miuix.pickerwidget.widget.TimePicker;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes5.dex */
public class CalendarDatePickerDialog extends AlertDialog {
    private static final float FULL_SCROLL_THRESHOLD = 1.2f;
    private Calendar mCalendar;
    private CalendarDatePicker mCalendarDatePicker;
    private View mContentView;
    private Drawable mCustomPositiveButtonBg;
    private View mDateTimePanel;
    private TextView mDateTimePanelTitleView;
    private TextView mDateView;
    private DialogParentPanel2 mDialogParentPanel;
    private View mLunarModePanel;
    private SlidingButton mLunarModeSwitchButton;
    private NestedScrollView mNestedScrollView;
    private CalendarDatePickerDialogCallBack mOnDataChangeCallback;
    private OnDataChangeListener mOnDataChangeListener;
    private CalendarDatePickerDialogCallBack mOnDataConfirmCallback;
    private OnDataConfirmListener mOnDataConfirmListener;
    private View.OnLayoutChangeListener mOnDialogParentPanelLayoutChangeListener;
    private DateTimePickerContainer mPickerContainer;
    private int[] mPickerContainerLocation;
    private int[] mScrollViewLocation;
    private Spinner mSpinner;
    private CharSequence[] mSpinnerEntries;
    private View mSpinnerPanel;
    private TextView mSpinnerPanelTitleView;
    private int mSpinnerPosition;
    private TimePicker mTimePicker;
    private TextView mTimeView;

    public interface OnDataChangeListener {
        void onDataChange(int i, int i2, int i3, int i4, int i5, int i6, String str);
    }

    public interface OnDataConfirmListener {
        void onDataConfirm(int i, int i2, int i3, int i4, int i5, int i6, String str);
    }

    public CalendarDatePickerDialog(Context context) {
        this(context, true, null);
    }

    public CalendarDatePickerDialog(Context context, int i) {
        super(context, i);
        this.mSpinnerPosition = 0;
        init();
    }

    public CalendarDatePickerDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.mSpinnerPosition = 0;
        init();
    }

    private void init() {
        this.mCalendar = new Calendar();
        setButton(-1, getContext().getText(R.string.ok), (DialogInterface.OnClickListener) null);
        setButton(-2, getContext().getText(R.string.cancel), (DialogInterface.OnClickListener) null);
        View viewInflate = LayoutInflater.from(getContext()).inflate(miuix.appcompat.R.layout.miuix_appcompat_calendar_date_picker_dialog, (ViewGroup) null);
        this.mContentView = viewInflate;
        setView(viewInflate);
        setupCalendarDatePicker();
    }

    private void setupCalendarDatePicker() {
        CalendarDatePicker calendarDatePicker = (CalendarDatePicker) this.mContentView.findViewById(miuix.appcompat.R.id.calendarDatePicker);
        this.mCalendarDatePicker = calendarDatePicker;
        calendarDatePicker.setOnDateChangedListener(new CalendarDatePicker.OnDateChangedListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.1
            @Override // miuix.pickerwidget.widget.Calendar.CalendarDatePicker.OnDateChangedListener
            public void onDateChanged(CalendarDatePicker calendarDatePicker2, int i, int i2, int i3, String str) {
                if (CalendarDatePickerDialog.this.mOnDataConfirmCallback != null) {
                    CalendarDatePickerDialog.this.mOnDataConfirmCallback.setDate(i, i2, i3);
                    CalendarDatePickerDialog.this.mOnDataConfirmCallback.setLunarMessage(str);
                }
                if (CalendarDatePickerDialog.this.mOnDataChangeCallback != null) {
                    CalendarDatePickerDialog.this.mOnDataChangeCallback.setDate(i, i2, i3);
                    CalendarDatePickerDialog.this.mOnDataChangeCallback.setLunarMessage(str);
                }
                CalendarDatePickerDialog.this.onDataChange();
                CalendarDatePickerDialog.this.updateDateView();
            }
        });
        final boolean zIsCanceledOnTouchOutside = this.mAlert.isCanceledOnTouchOutside();
        this.mCalendarDatePicker.setRecyclerViewExtraOnScrollListener(new RecyclerView.OnScrollListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                CalendarDatePickerDialog.this.setCanceledOnTouchOutside(i == 0 ? zIsCanceledOnTouchOutside : false);
            }
        });
    }

    private void setupLunarPanel() {
        this.mLunarModePanel = this.mContentView.findViewById(miuix.appcompat.R.id.lunarModePanel);
        SlidingButton slidingButton = (SlidingButton) this.mContentView.findViewById(miuix.appcompat.R.id.lunarModeSwitchButton);
        this.mLunarModeSwitchButton = slidingButton;
        slidingButton.setOnPerformCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CalendarDatePickerDialog.this.mCalendarDatePicker.setLunarMode(z);
                CalendarDatePickerDialog.this.onDataChange();
            }
        });
        ViewCompat.setAccessibilityDelegate(this.mLunarModePanel, new AccessibilityDelegateCompat() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.4
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCheckable(true);
                accessibilityNodeInfoCompat.setClickable(true);
                accessibilityNodeInfoCompat.setClassName(Switch.class.getName());
                if (CalendarDatePickerDialog.this.mLunarModeSwitchButton != null) {
                    accessibilityNodeInfoCompat.setChecked(CalendarDatePickerDialog.this.mLunarModeSwitchButton.isChecked());
                    accessibilityNodeInfoCompat.setContentDescription(CalendarDatePickerDialog.this.mLunarModeSwitchButton.getContentDescription());
                }
            }
        });
        ViewCompat.replaceAccessibilityAction(this.mLunarModePanel, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, "", new AccessibilityViewCommand() { // from class: miuix.appcompat.app.CalendarDatePickerDialog$$ExternalSyntheticLambda1
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public final boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                return this.f$0.m4222x8346efe7(view, commandArguments);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupLunarPanel$0$miuix-appcompat-app-CalendarDatePickerDialog, reason: not valid java name */
    /* synthetic */ boolean m4222x8346efe7(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
        SlidingButton slidingButton = this.mLunarModeSwitchButton;
        if (slidingButton != null) {
            slidingButton.performClick();
        }
        view.sendAccessibilityEvent(1);
        return true;
    }

    private void setupTimePicker() {
        TimePicker timePicker = (TimePicker) this.mContentView.findViewById(miuix.appcompat.R.id.timePicker);
        this.mTimePicker = timePicker;
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.5
            @Override // miuix.pickerwidget.widget.TimePicker.OnTimeChangedListener
            public void onTimeChanged(TimePicker timePicker2, int i, int i2) {
                if (CalendarDatePickerDialog.this.mOnDataConfirmCallback != null) {
                    CalendarDatePickerDialog.this.mOnDataConfirmCallback.setTime(i, i2);
                }
                if (CalendarDatePickerDialog.this.mOnDataChangeCallback != null) {
                    CalendarDatePickerDialog.this.mOnDataChangeCallback.setTime(i, i2);
                }
                CalendarDatePickerDialog.this.onDataChange();
                CalendarDatePickerDialog.this.updateTimeView();
            }
        });
    }

    private void setupSpinnerPanel() {
        this.mSpinnerPanel = this.mContentView.findViewById(miuix.appcompat.R.id.spinnerPanel);
        this.mSpinnerPanelTitleView = (TextView) this.mContentView.findViewById(miuix.appcompat.R.id.spinnerDescription);
        Spinner spinner = (Spinner) this.mContentView.findViewById(miuix.appcompat.R.id.spinner);
        this.mSpinner = spinner;
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.6
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (CalendarDatePickerDialog.this.mOnDataConfirmCallback != null) {
                    CalendarDatePickerDialog.this.mOnDataConfirmCallback.setSpinnerSelection(i);
                }
                if (CalendarDatePickerDialog.this.mOnDataChangeCallback != null) {
                    CalendarDatePickerDialog.this.mOnDataChangeCallback.setSpinnerSelection(i);
                }
                CalendarDatePickerDialog.this.mSpinnerPosition = i;
                CalendarDatePickerDialog.this.onDataChange();
            }
        });
        ViewCompat.setAccessibilityDelegate(this.mSpinnerPanel, new AccessibilityDelegateCompat() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.7
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setClickable(true);
                accessibilityNodeInfoCompat.setClassName(android.widget.Spinner.class.getName());
                if (CalendarDatePickerDialog.this.mSpinner == null || CalendarDatePickerDialog.this.mSpinnerPanelTitleView == null) {
                    return;
                }
                accessibilityNodeInfoCompat.setContentDescription(CalendarDatePickerDialog.this.mSpinnerPanelTitleView.getText().toString() + CalendarDatePickerDialog.this.mSpinnerEntries[CalendarDatePickerDialog.this.mSpinnerPosition].toString());
            }
        });
        ViewCompat.replaceAccessibilityAction(this.mSpinnerPanel, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, "", new AccessibilityViewCommand() { // from class: miuix.appcompat.app.CalendarDatePickerDialog$$ExternalSyntheticLambda4
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public final boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                return this.f$0.m4223xe1751129(view, commandArguments);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupSpinnerPanel$1$miuix-appcompat-app-CalendarDatePickerDialog, reason: not valid java name */
    /* synthetic */ boolean m4223xe1751129(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            spinner.performClick();
        }
        view.sendAccessibilityEvent(1);
        return true;
    }

    private void setupDateTimePanel() {
        this.mDateTimePanel = this.mContentView.findViewById(miuix.appcompat.R.id.dateTimePanel);
        this.mDateTimePanelTitleView = (TextView) this.mContentView.findViewById(miuix.appcompat.R.id.dateTimePanelTitle);
        this.mDateView = (TextView) this.mContentView.findViewById(miuix.appcompat.R.id.dateView);
        this.mTimeView = (TextView) this.mContentView.findViewById(miuix.appcompat.R.id.timeView);
        this.mDateView.setSelected(true);
        this.mDateView.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CalendarDatePickerDialog.this.mDateView.setSelected(true);
                CalendarDatePickerDialog.this.mTimeView.setSelected(false);
                CalendarDatePickerDialog.this.mCalendarDatePicker.setVisibility(0);
                CalendarDatePickerDialog.this.mTimePicker.setVisibility(8);
            }
        });
        this.mTimeView.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CalendarDatePickerDialog.this.mDateView.setSelected(false);
                CalendarDatePickerDialog.this.mTimeView.setSelected(true);
                CalendarDatePickerDialog.this.mCalendarDatePicker.setVisibility(8);
                CalendarDatePickerDialog.this.mTimePicker.setVisibility(0);
            }
        });
        updateDateView();
        updateTimeView();
    }

    private View.OnLayoutChangeListener getOnDialogParentPanelLayoutChangeListener() {
        return new View.OnLayoutChangeListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.10
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                boolean zVerticallyCenterDatePickerInScrollView = ((float) (i4 - i2)) / ((float) (i3 - i)) < 1.2f || (CalendarDatePickerDialog.this.isFreeFormMode(view.getContext()) && CalendarDatePickerDialog.this.isLargeFontLevel());
                if (!zVerticallyCenterDatePickerInScrollView) {
                    if (CalendarDatePickerDialog.this.mTimePicker == null || CalendarDatePickerDialog.this.mTimePicker.getVisibility() != 0) {
                        if (!CalendarDatePickerDialog.this.isCalendarDatePickerCompletelyDisplay()) {
                            zVerticallyCenterDatePickerInScrollView = CalendarDatePickerDialog.this.verticallyCenterDatePickerInScrollView();
                            CalendarDatePickerDialog.this.mNestedScrollView.setNestedScrollingEnabled((CalendarDatePickerDialog.this.mCalendarDatePicker.getDatePicker().getVisibility() == 8) || zVerticallyCenterDatePickerInScrollView);
                        } else {
                            CalendarDatePickerDialog.this.mNestedScrollView.setNestedScrollingEnabled(true);
                        }
                    } else {
                        zVerticallyCenterDatePickerInScrollView = CalendarDatePickerDialog.this.verticallyCenterTimePickerInScrollView();
                        CalendarDatePickerDialog.this.mNestedScrollView.setNestedScrollingEnabled(zVerticallyCenterDatePickerInScrollView);
                    }
                }
                CalendarDatePickerDialog.this.setContentForceFullScroll(zVerticallyCenterDatePickerInScrollView);
                if (CalendarDatePickerDialog.this.mPickerContainer == null) {
                    CalendarDatePickerDialog calendarDatePickerDialog = CalendarDatePickerDialog.this;
                    calendarDatePickerDialog.mPickerContainer = (DateTimePickerContainer) calendarDatePickerDialog.mContentView.findViewById(miuix.appcompat.R.id.dateTimePickerContainer);
                }
                CalendarDatePickerDialog.this.mPickerContainer.setWrapContent(zVerticallyCenterDatePickerInScrollView);
                CalendarDatePickerDialog.this.mCalendarDatePicker.setWrapContent(zVerticallyCenterDatePickerInScrollView);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean verticallyCenterDatePickerInScrollView() {
        DatePicker datePicker = this.mCalendarDatePicker.getDatePicker();
        View headerLayout = this.mCalendarDatePicker.getHeaderLayout();
        int measuredHeight = this.mNestedScrollView.getMeasuredHeight();
        int measuredHeight2 = datePicker.getMeasuredHeight();
        if (measuredHeight < measuredHeight2) {
            return true;
        }
        int measuredHeight3 = headerLayout.getMeasuredHeight();
        int i = measuredHeight3 + (((measuredHeight - measuredHeight3) - measuredHeight2) / 2);
        datePicker.layout(datePicker.getLeft(), i, datePicker.getRight(), measuredHeight2 + i);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean verticallyCenterTimePickerInScrollView() {
        NestedScrollView nestedScrollView;
        TimePicker timePicker = this.mTimePicker;
        if (timePicker != null && timePicker.getVisibility() == 0 && (nestedScrollView = this.mNestedScrollView) != null) {
            int measuredHeight = nestedScrollView.getMeasuredHeight();
            int measuredHeight2 = this.mTimePicker.getMeasuredHeight();
            if (measuredHeight < measuredHeight2) {
                return true;
            }
            int iCalculateTopOffset = calculateTopOffset() + ((measuredHeight - measuredHeight2) / 2);
            this.mTimePicker.layout(this.mTimePicker.getLeft(), iCalculateTopOffset, this.mTimePicker.getRight(), measuredHeight2 + iCalculateTopOffset);
        }
        return false;
    }

    private int calculateTopOffset() {
        if (this.mScrollViewLocation == null && this.mPickerContainerLocation == null) {
            this.mScrollViewLocation = new int[2];
            this.mPickerContainerLocation = new int[2];
        }
        this.mNestedScrollView.getLocationOnScreen(this.mScrollViewLocation);
        this.mPickerContainer.getLocationOnScreen(this.mPickerContainerLocation);
        return this.mScrollViewLocation[1] - this.mPickerContainerLocation[1];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCalendarDatePickerCompletelyDisplay() {
        return this.mCalendarDatePicker.getVisibility() == 0 && this.mCalendarDatePicker.getMeasuredHeight() <= this.mNestedScrollView.getMeasuredHeight();
    }

    public boolean isFreeFormMode(Context context) {
        return EnvStateManager.isFreeFormMode(context);
    }

    public boolean isLargeFontLevel() {
        return getCalendarDatePicker().isLargeFontLevel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDataConfirm() {
        if (this.mOnDataConfirmListener == null || this.mOnDataConfirmCallback == null) {
            return;
        }
        SlidingButton slidingButton = this.mLunarModeSwitchButton;
        this.mOnDataConfirmListener.onDataConfirm(this.mOnDataConfirmCallback.getYear(), this.mOnDataConfirmCallback.getMonth(), this.mOnDataConfirmCallback.getDayOfMonth(), this.mOnDataConfirmCallback.getHourOfDay(), this.mOnDataConfirmCallback.getMinute(), this.mOnDataConfirmCallback.getSpinnerSelection(), slidingButton != null && slidingButton.isChecked() ? this.mOnDataConfirmCallback.getLunarMessage() : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDataChange() {
        if (this.mOnDataChangeListener == null || this.mOnDataChangeCallback == null) {
            return;
        }
        SlidingButton slidingButton = this.mLunarModeSwitchButton;
        this.mOnDataChangeListener.onDataChange(this.mOnDataChangeCallback.getYear(), this.mOnDataChangeCallback.getMonth(), this.mOnDataChangeCallback.getDayOfMonth(), this.mOnDataChangeCallback.getHourOfDay(), this.mOnDataChangeCallback.getMinute(), this.mOnDataChangeCallback.getSpinnerSelection(), slidingButton != null && slidingButton.isChecked() ? this.mOnDataChangeCallback.getLunarMessage() : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDateView() {
        if (this.mDateView != null) {
            int year = getYear();
            int month = getMonth();
            this.mDateView.setText(formatDate(year, month, Math.min(getDayOfMonth(), this.mCalendar.daysInMonth(year, month))));
        }
    }

    private static String formatDate(int i, int i2, int i3) {
        String string = Integer.toString(i);
        String string2 = Integer.toString(i2 + 1);
        String string3 = Integer.toString(i3);
        if (string2.length() == 1) {
            string2 = "0" + string2;
        }
        if (string3.length() == 1) {
            string3 = "0" + string3;
        }
        return String.format("%s/%s/%s", string, string2, string3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTimeView() {
        if (this.mTimeView != null) {
            String string = Integer.toString(getHourOfDay());
            String string2 = Integer.toString(getMinute());
            if (string.length() == 1) {
                string = "0" + string;
            }
            if (string2.length() == 1) {
                string2 = "0" + string2;
            }
            this.mTimeView.setText(string + ":" + string2);
        }
    }

    @Override // miuix.appcompat.app.AlertDialog
    public void setButton(int i, CharSequence charSequence, Message message) {
        this.mAlert.setButton(i, charSequence, new DialogInterface.OnClickListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.11
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                if (i2 == -1) {
                    CalendarDatePickerDialog.this.onDataConfirm();
                }
            }
        }, message);
    }

    @Override // miuix.appcompat.app.AlertDialog
    public void setButton(int i, CharSequence charSequence, final DialogInterface.OnClickListener onClickListener) {
        super.setButton(i, charSequence, new DialogInterface.OnClickListener() { // from class: miuix.appcompat.app.CalendarDatePickerDialog.12
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                DialogInterface.OnClickListener onClickListener2 = onClickListener;
                if (onClickListener2 != null) {
                    onClickListener2.onClick(dialogInterface, i2);
                }
                if (i2 == -1) {
                    CalendarDatePickerDialog.this.onDataConfirm();
                }
            }
        });
    }

    public int getYear() {
        return getCalendarDatePicker().getYear();
    }

    public int getMonth() {
        return getCalendarDatePicker().getMonth();
    }

    public int getDayOfMonth() {
        return getCalendarDatePicker().getDayOfMonth();
    }

    public int getHourOfDay() {
        if (getTimePicker() != null) {
            return getTimePicker().getCurrentHour().intValue();
        }
        return this.mCalendar.get(18);
    }

    public int getMinute() {
        if (getTimePicker() != null) {
            return getTimePicker().getCurrentMinute().intValue();
        }
        return this.mCalendar.get(20);
    }

    public void setSpinnerEnabled(boolean z, CharSequence charSequence, CharSequence[] charSequenceArr, CharSequence[] charSequenceArr2, int[] iArr) {
        if (this.mSpinnerPanel == null) {
            setupSpinnerPanel();
        }
        if (!z) {
            this.mSpinnerPanel.setVisibility(8);
            return;
        }
        this.mSpinnerPanel.setVisibility(0);
        this.mSpinnerPanelTitleView.setText(charSequence);
        this.mSpinner.setDoubleLineContentAdapter(new SpinnerDoubleLineContentAdapter(getContext(), charSequenceArr, charSequenceArr2, iArr));
        this.mSpinnerEntries = charSequenceArr;
    }

    public void setSpinnerEnabled(boolean z, CharSequence charSequence, CharSequence[] charSequenceArr, CharSequence[] charSequenceArr2) {
        setSpinnerEnabled(z, charSequence, charSequenceArr, charSequenceArr2, null);
    }

    public void setSpinnerEnabled(boolean z, CharSequence charSequence, CharSequence[] charSequenceArr) {
        setSpinnerEnabled(z, charSequence, charSequenceArr, null);
    }

    public void setSpinnerEnabled(boolean z, CharSequence charSequence) {
        setSpinnerEnabled(z, charSequence, null);
    }

    public void setSpinnerEnabled(boolean z) {
        setSpinnerEnabled(z, null);
    }

    public void setTimePickerEnabled(boolean z, CharSequence charSequence) {
        if (this.mDateTimePanel == null && this.mTimePicker == null) {
            setupDateTimePanel();
            setupTimePicker();
        }
        if (!z) {
            this.mDateTimePanel.setVisibility(8);
        } else {
            this.mDateTimePanel.setVisibility(0);
            this.mDateTimePanelTitleView.setText(charSequence);
        }
    }

    public void setTimePickerEnabled(boolean z) {
        setTimePickerEnabled(z, null);
    }

    public void setLunarMode(boolean z) {
        if (this.mLunarModePanel == null) {
            setupLunarPanel();
        }
        this.mLunarModePanel.setVisibility(z ? 0 : 8);
    }

    public void switchLunarState(boolean z) {
        SlidingButton slidingButton = this.mLunarModeSwitchButton;
        if (slidingButton != null) {
            slidingButton.setChecked(z);
        }
        this.mCalendarDatePicker.setLunarMode(z);
    }

    public void setHighlightColor(int i, boolean z, boolean z2, boolean z3, boolean z4) {
        SlidingButton slidingButton;
        TimePicker timePicker;
        this.mCalendarDatePicker.setHighlightColor(i, z);
        Drawable selectedStateDrawable = this.mCalendarDatePicker.getSelectedStateDrawable(this.mDateView);
        Drawable selectedStateDrawable2 = this.mCalendarDatePicker.getSelectedStateDrawable(this.mTimeView);
        if (selectedStateDrawable instanceof GradientDrawable) {
            ((GradientDrawable) selectedStateDrawable).setColor(i);
        }
        if (selectedStateDrawable2 instanceof GradientDrawable) {
            ((GradientDrawable) selectedStateDrawable2).setColor(i);
        }
        if (z2 && (timePicker = this.mTimePicker) != null) {
            timePicker.setLabelTextColor(i);
            this.mTimePicker.setTextColorHighlight(i);
        }
        if (z4 && (slidingButton = this.mLunarModeSwitchButton) != null) {
            slidingButton.setTintOfBarOn(i);
        }
        this.mCustomPositiveButtonBg = z3 ? DrawableUtil.createDialogButtonBackground(getContext(), i) : null;
    }

    public void setHighlightColor(int i) {
        setHighlightColor(i, true, true, true, true);
    }

    public void showAsCalendar() {
        show();
        TextView textView = this.mDateView;
        if (textView != null) {
            textView.post(new Runnable() { // from class: miuix.appcompat.app.CalendarDatePickerDialog$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m4224xea0734e9();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$showAsCalendar$2$miuix-appcompat-app-CalendarDatePickerDialog, reason: not valid java name */
    /* synthetic */ void m4224xea0734e9() {
        this.mDateView.performClick();
    }

    public void showAsTimePicker() {
        TextView textView;
        show();
        if (this.mTimePicker == null || (textView = this.mTimeView) == null) {
            return;
        }
        textView.post(new Runnable() { // from class: miuix.appcompat.app.CalendarDatePickerDialog$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4225x8b2a696b();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showAsTimePicker$3$miuix-appcompat-app-CalendarDatePickerDialog, reason: not valid java name */
    /* synthetic */ void m4225x8b2a696b() {
        this.mTimeView.performClick();
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog
    public void show() {
        super.show();
        onDataChange();
        applyCustomPositiveButtonBg();
        postCalculateDialogHeight();
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDialogParentPanel == null) {
            DialogParentPanel2 dialogParentPanel2 = (DialogParentPanel2) findViewById(miuix.appcompat.R.id.parentPanel);
            this.mDialogParentPanel = dialogParentPanel2;
            dialogParentPanel2.setFenceEnabled(true);
        }
        if (this.mOnDialogParentPanelLayoutChangeListener == null) {
            this.mOnDialogParentPanelLayoutChangeListener = getOnDialogParentPanelLayoutChangeListener();
        }
        this.mDialogParentPanel.addOnLayoutChangeListener(this.mOnDialogParentPanelLayoutChangeListener);
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDialogParentPanel.removeOnLayoutChangeListener(this.mOnDialogParentPanelLayoutChangeListener);
    }

    private void applyCustomPositiveButtonBg() {
        Button button = getButton(-1);
        if (this.mCustomPositiveButtonBg == null || button == null) {
            return;
        }
        Drawable background = button.getBackground();
        Drawable drawable = this.mCustomPositiveButtonBg;
        if (background != drawable) {
            button.setBackground(drawable);
        }
    }

    private void postCalculateDialogHeight() {
        if (this.mNestedScrollView == null) {
            this.mNestedScrollView = (NestedScrollView) this.mContentView.findViewById(miuix.appcompat.R.id.nestedScrollView);
        }
        this.mCalendarDatePicker.post(new Runnable() { // from class: miuix.appcompat.app.CalendarDatePickerDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4221xbb44c999();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$postCalculateDialogHeight$4$miuix-appcompat-app-CalendarDatePickerDialog, reason: not valid java name */
    /* synthetic */ void m4221xbb44c999() {
        int measuredHeight = this.mDialogParentPanel.getMeasuredHeight();
        int panelMaxLimitHeight = this.mDialogParentPanel.getPanelMaxLimitHeight();
        int measuredHeight2 = this.mCalendarDatePicker.getMeasuredHeight() - this.mNestedScrollView.getMeasuredHeight();
        if (measuredHeight2 > 0) {
            this.mDialogParentPanel.setPanelFixedHeight(Math.min(measuredHeight + measuredHeight2, panelMaxLimitHeight));
            this.mDialogParentPanel.requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setContentForceFullScroll(boolean z) {
        this.mAlert.setContentForceFullScroll(z);
    }

    public void setDate(long j, boolean z) {
        this.mCalendarDatePicker.setDate(j, z);
    }

    public void setDate(long j) {
        this.mCalendarDatePicker.setDate(j);
    }

    public void setDate(ZonedDateTime zonedDateTime, boolean z) {
        this.mCalendarDatePicker.setDate(zonedDateTime, z);
    }

    public void setDate(ZonedDateTime zonedDateTime) {
        this.mCalendarDatePicker.setDate(zonedDateTime);
    }

    public long getMinDate() {
        return this.mCalendarDatePicker.getMinDate();
    }

    public long getMaxDate() {
        return this.mCalendarDatePicker.getMaxDate();
    }

    public void setMinDate(long j) {
        this.mCalendarDatePicker.setMinDate(j);
    }

    public void setMaxDate(long j) {
        this.mCalendarDatePicker.setMaxDate(j);
    }

    public CalendarDatePicker getCalendarDatePicker() {
        return this.mCalendarDatePicker;
    }

    public TimePicker getTimePicker() {
        return this.mTimePicker;
    }

    public Spinner getSpinner() {
        return this.mSpinner;
    }

    public SlidingButton getLunarModeSwitchButton() {
        return this.mLunarModeSwitchButton;
    }

    public TextView getDateView() {
        return this.mDateView;
    }

    public TextView getTimeView() {
        return this.mTimeView;
    }

    public void setOnDataConfirmListener(OnDataConfirmListener onDataConfirmListener) {
        if (this.mOnDataConfirmCallback == null) {
            this.mOnDataConfirmCallback = new CalendarDatePickerDialogCallBack(this);
        }
        this.mOnDataConfirmListener = onDataConfirmListener;
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        if (this.mOnDataChangeCallback == null) {
            this.mOnDataChangeCallback = new CalendarDatePickerDialogCallBack(this);
        }
        this.mOnDataChangeListener = onDataChangeListener;
    }
}
