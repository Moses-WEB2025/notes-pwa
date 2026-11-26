package com.miui.pc.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.R;
import defpackage.R2;

/* JADX INFO: loaded from: classes4.dex */
public class RepeatReminderPopupWindow extends PopupWindow {
    private Context context;
    private View mContentView;

    public RepeatReminderPopupWindow(Context context, OnReminderItemClickListener onReminderItemClickListener, int i) {
        View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.repeat_reminder_popup_win, (ViewGroup) null);
        this.mContentView = viewInflate;
        setContentView(viewInflate);
        setWidth(R2.array.global_ai_panel_blend_colors);
        setHeight(-2);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        RecyclerView recyclerView = (RecyclerView) this.mContentView.findViewById(R.id.reminder_list);
        ReminderListAdapter reminderListAdapter = new ReminderListAdapter(context, this, System.currentTimeMillis(), i);
        recyclerView.setAdapter(reminderListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        reminderListAdapter.setOnItemClickListener(onReminderItemClickListener);
        update();
    }

    public void showPopupWindow(View view) {
        if (!isShowing()) {
            showAsDropDown(view, 0, -350);
        } else {
            dismiss();
        }
    }
}
