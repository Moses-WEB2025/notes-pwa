package com.miui.notes.feature.noteedit;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.richeditor.theme.Theme;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public class NoteInfoController implements INoteInfoHeader {
    protected ImageView mAlarmDivider;
    protected TextView mAlarmView;
    protected Fragment mBindFragment = null;
    protected Context mContext;
    protected TextView mDateView;
    protected View mExtraRootView;
    protected ImageView mLockDivider;
    protected ImageView mLockView;
    protected NoteFolderHeader mNoteFolderHeader;
    protected ImageView mTextCountDivider;
    protected TextView mTextCountView;
    protected TextView mTimeView;

    public NoteInfoController(Context context, View view, View view2) {
        this.mNoteFolderHeader = null;
        this.mContext = context;
        this.mNoteFolderHeader = new NoteFolderHeader(context, view2);
        this.mExtraRootView = view;
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mTimeView = (TextView) view.findViewById(R.id.time);
        this.mTextCountDivider = (ImageView) view.findViewById(R.id.text_count_divider);
        this.mTextCountView = (TextView) view.findViewById(R.id.text_count);
        this.mLockDivider = (ImageView) view.findViewById(R.id.lock_divider);
        this.mLockView = (ImageView) view.findViewById(R.id.lock);
        this.mAlarmDivider = (ImageView) view.findViewById(R.id.alarm_divider);
        this.mAlarmView = (TextView) view.findViewById(R.id.alarm);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.note_editor_padding);
        this.mExtraRootView.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public View getView() {
        return this.mNoteFolderHeader.getView();
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void onDestroy() {
        NoteFolderHeader noteFolderHeader = this.mNoteFolderHeader;
        if (noteFolderHeader != null) {
            noteFolderHeader.onDestroy();
        }
        this.mContext = null;
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void dismissMenu() {
        NoteFolderHeader noteFolderHeader = this.mNoteFolderHeader;
        if (noteFolderHeader != null) {
            noteFolderHeader.dismissMenu();
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void bindFragment(Fragment fragment) {
        this.mBindFragment = fragment;
        this.mNoteFolderHeader.bindFragment(fragment);
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateDataTime(WorkingNote workingNote) {
        long sortDate = workingNote.getSortDate();
        this.mDateView.setText(((Object) DateFormatUtils.formatDateText(this.mContext, sortDate)) + "  ");
        this.mTimeView.setText(DateFormatUtils.formatTimeText(this.mContext, sortDate));
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateTextCount(int i) {
        if (this.mTextCountDivider.isSaveEnabled()) {
            this.mTextCountDivider.setVisibility(0);
        }
        this.mTextCountView.setText(Utils.obtainLocalInteger(i, R.plurals.word_count));
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateNoteHiddenState(WorkingNote workingNote) {
        if (workingNote.getFolderId() == -4) {
            this.mLockView.setVisibility(0);
            this.mLockDivider.setVisibility(0);
        } else {
            this.mLockView.setVisibility(8);
            this.mLockDivider.setVisibility(8);
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateAlarm(WorkingNote workingNote) {
        if (workingNote.getAlertDate() > 0) {
            this.mAlarmView.setText(workingNote.getExtraInfo().remindInfo);
            this.mAlarmView.setVisibility(0);
            this.mAlarmDivider.setVisibility(0);
        } else {
            this.mAlarmView.setVisibility(8);
            this.mAlarmDivider.setVisibility(8);
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void update(WorkingNote workingNote, int i) {
        updateDataTime(workingNote);
        updateTextCount(i);
        updateNoteHiddenState(workingNote);
        updateAlarm(workingNote);
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateStyle(Theme theme, NoteThemeResCache noteThemeResCache) {
        NoteFolderHeader noteFolderHeader = this.mNoteFolderHeader;
        if (noteFolderHeader != null) {
            noteFolderHeader.updateStyle(theme, noteThemeResCache);
        }
        if (noteThemeResCache == null) {
            return;
        }
        try {
            Theme.ActionBarStyle actionBarStyle = theme.getActionBarStyle(this.mContext);
            ((GradientDrawable) this.mTextCountDivider.getDrawable()).setColor(actionBarStyle.getDividerColor());
            ((GradientDrawable) this.mLockDivider.getDrawable()).setColor(actionBarStyle.getDividerColor());
            ((GradientDrawable) this.mAlarmDivider.getDrawable()).setColor(actionBarStyle.getDividerColor());
            this.mLockView.setImageDrawable(actionBarStyle.filterDrawable(noteThemeResCache.actionBarLockIcon));
            this.mDateView.setTextColor(actionBarStyle.getHeaderInfoTextColor());
            this.mTimeView.setTextColor(actionBarStyle.getHeaderInfoTextColor());
            this.mTextCountView.setTextColor(actionBarStyle.getHeaderInfoTextColor());
            this.mAlarmView.setCompoundDrawablesWithIntrinsicBounds(actionBarStyle.filterDrawable(noteThemeResCache.actionBarAlertIcon), (Drawable) null, (Drawable) null, (Drawable) null);
            this.mAlarmView.setTextColor(actionBarStyle.getHeaderInfoTextColor());
        } catch (Exception e) {
            Logger.INSTANCE.e("NoteInfoController", "error in updateStyle " + e.toString());
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateFolderName(String str) {
        NoteFolderHeader noteFolderHeader = this.mNoteFolderHeader;
        if (noteFolderHeader != null) {
            noteFolderHeader.updateFolderName(str);
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setFolderGroupVisibility(int i) {
        NoteFolderHeader noteFolderHeader = this.mNoteFolderHeader;
        if (noteFolderHeader != null) {
            noteFolderHeader.setFolderGroupVisibility(i);
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setHeadMenuInfo(Cursor cursor, long[] jArr, long j) {
        NoteFolderHeader noteFolderHeader = this.mNoteFolderHeader;
        if (noteFolderHeader != null) {
            noteFolderHeader.setHeadMenuInfo(cursor, jArr, j);
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void onNoteMenuClick(int i) {
        NoteFolderHeader noteFolderHeader = this.mNoteFolderHeader;
        if (noteFolderHeader != null) {
            noteFolderHeader.onNoteMenuClick(i);
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setNoteMenuClickListener(INoteInfoHeader.NoteMenuClickListener noteMenuClickListener) {
        NoteFolderHeader noteFolderHeader = this.mNoteFolderHeader;
        if (noteFolderHeader != null) {
            noteFolderHeader.setNoteMenuClickListener(noteMenuClickListener);
        }
    }
}
