package com.miui.pad.feature.notes.commonedit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.component.CreateFolderDialog;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.FolderStore;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.ui.menu.MenuItemEntity;
import com.miui.notes.ui.menu.PopFolderUtils;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.richeditor.theme.Theme;
import java.util.ArrayList;
import miuix.appcompat.app.Fragment;
import miuix.popupwidget.widget.DropDownSingleChoiceMenu;

/* JADX INFO: loaded from: classes4.dex */
public class PadNoteInfoHeader extends LinearLayout implements INoteInfoHeader, View.OnClickListener {
    private static final String TAG = "PadNoteInfoHeader";
    private ImageView mAlarmDivider;
    private TextView mAlarmView;
    private Fragment mBindFragment;
    private long[] mCheckedIds;
    private Context mContext;
    private CreateFolderDialog mCreateFolderDialog;
    private long mCurrentId;
    private Cursor mCursor;
    private TextView mDateView;
    public LinearLayout mFolderGroup;
    private ImageView mFolderIcon;
    private long[] mHeaderIds;
    private int[] mHeaderTypes;
    private ImageView mLockDivider;
    private ImageView mLockView;
    private DropDownSingleChoiceMenu mMenu;
    private INoteInfoHeader.NoteMenuClickListener mMenuClickListener;
    private ArrayList<MenuItemEntity> mMenuCollections;
    private ArrayList<String> mMenuList;
    private int mMenuPos;
    private boolean mNotePointer;
    private ImageView mTextCountDivider;
    private TextView mTextCountView;
    private TextView mTimeView;
    private TextView mTvFolder;

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public View getView() {
        return this;
    }

    public PadNoteInfoHeader(Context context) {
        super(context);
        this.mMenuList = new ArrayList<>();
        this.mMenuCollections = new ArrayList<>();
        this.mMenuPos = 0;
        this.mNotePointer = true;
        this.mBindFragment = null;
        this.mContext = context;
    }

    public PadNoteInfoHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMenuList = new ArrayList<>();
        this.mMenuCollections = new ArrayList<>();
        this.mMenuPos = 0;
        this.mNotePointer = true;
        this.mBindFragment = null;
        this.mContext = context;
    }

    public PadNoteInfoHeader(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMenuList = new ArrayList<>();
        this.mMenuCollections = new ArrayList<>();
        this.mMenuPos = 0;
        this.mNotePointer = true;
        this.mBindFragment = null;
        this.mContext = context;
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void bindFragment(Fragment fragment) {
        this.mBindFragment = fragment;
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setFolderGroupVisibility(int i) {
        this.mFolderGroup.setVisibility(i);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDateView = (TextView) findViewById(R.id.date);
        this.mTimeView = (TextView) findViewById(R.id.time);
        this.mTextCountDivider = (ImageView) findViewById(R.id.text_count_divider);
        this.mTextCountView = (TextView) findViewById(R.id.text_count);
        this.mLockDivider = (ImageView) findViewById(R.id.lock_divider);
        this.mLockView = (ImageView) findViewById(R.id.lock);
        this.mAlarmDivider = (ImageView) findViewById(R.id.alarm_divider);
        this.mAlarmView = (TextView) findViewById(R.id.alarm);
        this.mTvFolder = (TextView) findViewById(R.id.folder_name);
        this.mFolderIcon = (ImageView) findViewById(R.id.folder_icon);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.folder_name_group);
        this.mFolderGroup = linearLayout;
        linearLayout.setOnClickListener(this);
        this.mMenu = new DropDownSingleChoiceMenu(this.mContext);
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void onDestroy() {
        DropDownSingleChoiceMenu dropDownSingleChoiceMenu = this.mMenu;
        if (dropDownSingleChoiceMenu != null) {
            dropDownSingleChoiceMenu.dismiss();
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void dismissMenu() {
        DropDownSingleChoiceMenu dropDownSingleChoiceMenu = this.mMenu;
        if (dropDownSingleChoiceMenu != null) {
            dropDownSingleChoiceMenu.dismiss();
        }
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateDataTime(WorkingNote workingNote) {
        if (workingNote == null) {
            return;
        }
        long sortDate = workingNote.getSortDate();
        this.mDateView.setText(((Object) DateFormatUtils.formatDateText(getContext(), sortDate)) + "  ");
        this.mTimeView.setText(DateFormatUtils.formatTimeText(getContext(), sortDate));
        this.mDateView.requestLayout();
        this.mDateView.invalidate();
        this.mTimeView.requestLayout();
        this.mTimeView.invalidate();
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateTextCount(int i) {
        if (this.mTextCountDivider.isSaveEnabled()) {
            this.mTextCountDivider.setVisibility(0);
        }
        this.mTextCountView.setText(Utils.obtainLocalInteger(i, R.plurals.word_count));
        this.mTextCountDivider.requestLayout();
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateFolderName(String str) {
        this.mTvFolder.setText(str);
        this.mTvFolder.requestLayout();
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateNoteHiddenState(WorkingNote workingNote) {
        if (workingNote == null) {
            Logger.INSTANCE.d(TAG, "workingNote == null");
            return;
        }
        if (workingNote.getFolderId() == -4) {
            this.mLockView.setVisibility(0);
            this.mLockDivider.setVisibility(0);
        } else {
            this.mLockView.setVisibility(8);
            this.mLockDivider.setVisibility(8);
        }
        this.mLockView.requestLayout();
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateAlarm(WorkingNote workingNote) {
        if (workingNote == null) {
            Logger.INSTANCE.d(TAG, "workingNote == null");
            return;
        }
        long alertDate = workingNote.getAlertDate();
        Logger.INSTANCE.i("updateAlarm", "alarmTime=" + alertDate);
        if (alertDate > 0) {
            System.currentTimeMillis();
            this.mAlarmView.setText(DateFormatUtils.getRelativeTime(getContext(), alertDate));
            this.mAlarmView.setVisibility(0);
            this.mAlarmDivider.setVisibility(0);
        } else {
            this.mAlarmView.setVisibility(8);
            this.mAlarmDivider.setVisibility(8);
        }
        this.mAlarmView.requestLayout();
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void update(WorkingNote workingNote, int i) {
        if (workingNote == null) {
            Logger.INSTANCE.d(TAG, "workingNote == null");
            return;
        }
        updateDataTime(workingNote);
        updateTextCount(i);
        updateNoteHiddenState(workingNote);
        updateAlarm(workingNote);
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void updateStyle(Theme theme, NoteThemeResCache noteThemeResCache) {
        Theme.ActionBarStyle actionBarStyle = theme.getActionBarStyle(getContext());
        int color = this.mContext.getColor(R.color.pad_note_edit_header_info);
        ((GradientDrawable) this.mTextCountDivider.getDrawable()).setColor(color);
        ((GradientDrawable) this.mLockDivider.getDrawable()).setColor(color);
        ((GradientDrawable) this.mAlarmDivider.getDrawable()).setColor(color);
        this.mAlarmView.setCompoundDrawablesWithIntrinsicBounds(actionBarStyle.filterDrawable(noteThemeResCache.actionBarAlertIcon), (Drawable) null, (Drawable) null, (Drawable) null);
        this.mLockView.setImageDrawable(noteThemeResCache.actionBarLockIcon);
        this.mDateView.setTextColor(color);
        this.mTimeView.setTextColor(color);
        this.mTextCountView.setTextColor(color);
        this.mAlarmView.setTextColor(color);
        this.mTvFolder.setTextColor(this.mContext.getColor(theme.getEditorStyle(getContext(), System.currentTimeMillis()).mDirIndicatorIconColor));
        ((GradientDrawable) this.mFolderGroup.getBackground()).setColor(this.mContext.getColor(theme.getEditorStyle(getContext(), System.currentTimeMillis()).mDirIndicatorBgColor));
        VectorDrawableCompat vectorDrawableCompatCreate = VectorDrawableCompat.create(getResources(), R.drawable.ic_note_dir_indicator, this.mContext.getTheme());
        if (vectorDrawableCompatCreate != null) {
            vectorDrawableCompatCreate.setTint(this.mContext.getColor(theme.getEditorStyle(getContext(), System.currentTimeMillis()).mDirIndicatorIconColor));
        }
        this.mFolderIcon.setImageDrawable(vectorDrawableCompatCreate);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        showMenu(view);
    }

    private void showMenu(View view) {
        String systemSubject;
        this.mMenuList.clear();
        this.mMenuCollections.clear();
        this.mMenuList.add(this.mContext.getResources().getString(R.string.unclassfied_folder_name));
        this.mMenuCollections.add(null);
        Cursor cursorQuery = NoteApp.getInstance().getContentResolver().query(Notes.Note.CONTENT_URI, PopFolderUtils.PROJECTION, FolderStore.SELECTION_FOLDER_USER, null, PopFolderUtils.QUERY_SORT_ORDER);
        if (cursorQuery != null) {
            FolderStore.updateEnableNewDefaultFolderNameForPop(this.mContext, cursorQuery);
            cursorQuery.moveToPosition(-1);
            while (cursorQuery.moveToNext()) {
                long j = cursorQuery.getLong(0);
                if (j != -3) {
                    if (j > 0) {
                        systemSubject = cursorQuery.getString(1);
                    } else {
                        systemSubject = FolderModel.getSystemSubject(j);
                    }
                    this.mMenuList.add(systemSubject);
                    this.mMenuCollections.add(new MenuItemEntity(j, systemSubject));
                }
            }
        }
        this.mMenuList.add(NoteApp.getInstance().getString(R.string.menu_new_folder));
        this.mMenuCollections.add(new MenuItemEntity(-10000L, NoteApp.getInstance().getString(R.string.menu_new_folder)));
        final String[] strArr = (String[]) this.mMenuList.toArray(new String[this.mMenuList.size()]);
        if (this.mMenuList.contains(this.mTvFolder.getText().toString())) {
            this.mMenuPos = this.mMenuList.indexOf(this.mTvFolder.getText().toString());
        }
        this.mMenu.setItems(strArr);
        this.mMenu.setSelectedItem(this.mMenuPos);
        this.mMenu.setAnchorView(view);
        this.mMenu.setOnMenuListener(new DropDownSingleChoiceMenu.OnMenuListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteInfoHeader.1
            @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
            public void onDismiss() {
            }

            @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
            public void onShow() {
            }

            @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
            public void onItemSelected(DropDownSingleChoiceMenu dropDownSingleChoiceMenu, int i) {
                if (PadNoteInfoHeader.this.mMenuPos == i) {
                    return;
                }
                PadNoteInfoHeader padNoteInfoHeader = PadNoteInfoHeader.this;
                padNoteInfoHeader.mNotePointer = padNoteInfoHeader.mMenuPos < i;
                if (i != PadNoteInfoHeader.this.mMenuList.size() - 1) {
                    PadNoteInfoHeader.this.mMenuPos = i;
                    PadNoteInfoHeader.this.updateFolderName(strArr[i]);
                }
                if (i != strArr.length - 1) {
                    PadNoteInfoHeader.this.mMenuClickListener.onItemPerformClick(i);
                }
                MenuItemEntity menuItemEntity = (MenuItemEntity) PadNoteInfoHeader.this.mMenuCollections.get(i);
                if (menuItemEntity != null && menuItemEntity.getId() == -10000 && i == PadNoteInfoHeader.this.mMenuList.size() - 1) {
                    PadNoteInfoHeader padNoteInfoHeader2 = PadNoteInfoHeader.this;
                    padNoteInfoHeader2.showCreateFolderDialog(i, padNoteInfoHeader2.mMenuPos);
                }
            }
        });
        this.mMenu.show();
        if (cursorQuery != null) {
            cursorQuery.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCreateFolderDialog(int i, final int i2) {
        CreateFolderDialog createFolderDialog = this.mCreateFolderDialog;
        if (createFolderDialog != null) {
            if (createFolderDialog.isShowing()) {
                this.mCreateFolderDialog.dismiss();
            }
            this.mCreateFolderDialog = null;
        }
        CreateFolderDialog createFolderDialog2 = new CreateFolderDialog(this.mBindFragment.getContext());
        this.mCreateFolderDialog = createFolderDialog2;
        createFolderDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteInfoHeader.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                CreateFolderDialog createFolderDialog3 = (CreateFolderDialog) dialogInterface;
                Logger.INSTANCE.d(PadNoteInfoHeader.TAG, "showCreateFolderDialog onDismiss id: " + createFolderDialog3.getFolderId() + " subject:" + createFolderDialog3.getFolderSubject());
            }
        });
        this.mCreateFolderDialog.setMenuUpdateListener(new CreateFolderDialog.MenuUpdateListener() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteInfoHeader.3
            @Override // com.miui.notes.component.CreateFolderDialog.MenuUpdateListener
            public void updateMenuList(String str) {
                String systemSubject;
                PadNoteInfoHeader.this.updateFolderName(str);
                Cursor cursorQuery = NoteApp.getInstance().getContentResolver().query(Notes.Note.CONTENT_URI, PopFolderUtils.PROJECTION, FolderStore.SELECTION_FOLDER_USER, null, PopFolderUtils.QUERY_SORT_ORDER);
                if (cursorQuery != null) {
                    cursorQuery.moveToPosition(-1);
                    int i3 = 0;
                    while (cursorQuery.moveToNext()) {
                        long j = cursorQuery.getLong(0);
                        if (j != -3) {
                            if (j > 0) {
                                systemSubject = cursorQuery.getString(1);
                            } else {
                                systemSubject = FolderModel.getSystemSubject(j);
                            }
                            if (systemSubject.equals(str)) {
                                break;
                            } else {
                                i3++;
                            }
                        }
                    }
                    int i4 = i3 + 1;
                    PadNoteInfoHeader.this.mMenuPos = i4;
                    PadNoteInfoHeader padNoteInfoHeader = PadNoteInfoHeader.this;
                    padNoteInfoHeader.mNotePointer = padNoteInfoHeader.mMenuPos > i2;
                    PadNoteInfoHeader.this.mMenuClickListener.onItemPerformClick(i4);
                    cursorQuery.close();
                }
            }

            @Override // com.miui.notes.component.CreateFolderDialog.MenuUpdateListener
            public void updateMenuListFailed() {
                PadNoteInfoHeader.this.mMenuPos = i2;
                PadNoteInfoHeader padNoteInfoHeader = PadNoteInfoHeader.this;
                padNoteInfoHeader.updateFolderName((String) padNoteInfoHeader.mMenuList.get(PadNoteInfoHeader.this.mMenuPos));
            }
        });
        this.mCreateFolderDialog.show();
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setHeadMenuInfo(Cursor cursor, long[] jArr, long j) {
        this.mCursor = cursor;
        this.mCheckedIds = jArr;
        this.mCurrentId = j;
        if (isNeedShiftOut()) {
            this.mHeaderTypes = new int[]{0};
            this.mHeaderIds = new long[]{0};
        } else {
            this.mHeaderTypes = new int[]{0, 1};
            this.mHeaderIds = new long[]{0, -1};
        }
    }

    public long getItemId(int i) {
        if (i == 0) {
            return this.mHeaderIds[i];
        }
        if (!isNeedShiftOut() && !this.mNotePointer) {
            this.mCursor.moveToPosition((i + 1) - this.mHeaderTypes.length);
        } else {
            this.mCursor.moveToPosition(i - this.mHeaderTypes.length);
        }
        return this.mCursor.getLong(0);
    }

    public String getFolderName(int i) {
        Resources resources = NoteApp.getInstance().getResources();
        if (i == 0) {
            return resources.getString(R.string.menu_move_parent_folder);
        }
        if (!isNeedShiftOut() && !this.mNotePointer) {
            this.mCursor.moveToPosition((i + 1) - this.mHeaderTypes.length);
        } else {
            this.mCursor.moveToPosition(i - this.mHeaderTypes.length);
        }
        if (this.mCursor.getLong(0) == -6) {
            return NoteApp.getInstance().getString(R.string.excerpt);
        }
        if (this.mCursor.getLong(0) == -7) {
            return NoteApp.getInstance().getString(R.string.blackboard_note);
        }
        return this.mCursor.getString(1);
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void onNoteMenuClick(int i) {
        if (this.mCursor == null) {
            return;
        }
        long itemId = getItemId(i);
        String folderName = getFolderName(i);
        INoteInfoHeader.NoteMenuClickListener noteMenuClickListener = this.mMenuClickListener;
        if (noteMenuClickListener != null) {
            noteMenuClickListener.onItemChecked(itemId, this.mCheckedIds, folderName);
        }
    }

    private boolean isNeedShiftOut() {
        long j = this.mCurrentId;
        return j == 0 || j == -4097 || j == -4;
    }

    @Override // com.miui.notes.ui.view.INoteInfoHeader
    public void setNoteMenuClickListener(INoteInfoHeader.NoteMenuClickListener noteMenuClickListener) {
        this.mMenuClickListener = noteMenuClickListener;
    }
}
