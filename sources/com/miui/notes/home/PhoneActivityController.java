package com.miui.notes.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.NoteProviderAccessUtils;
import com.miui.notes.store.ItemStore;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.MiuiPlusHelper;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.KoreaRegionUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.NotesPreferenceActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.activity.PhoneMultiHandWriteActivity;
import com.miui.notes.ui.activity.WebViewBrainActivity;
import com.miui.richeditor.EditorActionMode;
import com.miui.todo.constant.TodoIntent;
import com.miui.todo.feature.settings.QuickNotePreferenceActivity;
import com.xiaomi.dist.handoff.sdk.HandoffSession;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneActivityController extends BaseNoteListActivityController {
    private HandoffSession mHandoffSession;

    public PhoneActivityController(NotesListActivity notesListActivity) {
        super(notesListActivity);
        logDebug(ToDoStat.NEW);
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onCreate() {
        this.mHandoffSession = HandoffHelper.onPhoneHomepage(getActivity());
        logDebug("onCreate");
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onPostCreate(Bundle bundle) {
        logDebug("onPostCreate");
        deliverIntent(getActivity().getIntent());
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onNewIntent(Intent intent) {
        logDebug("onNewIntent");
        if (this.mHandoffSession != null) {
            this.mHandoffSession.onNewIntent(intent);
        }
        deliverIntent(intent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void openNoteById(long j, ArrayList<String> arrayList) {
        ArrayList<String> arrayList2;
        Intent intent;
        Intent intent2;
        logDebug("openNoteById:" + j);
        NoteEntityHelper noteEntityHelper = NoteEntityHelper.INSTANCE;
        NoteEntity noteEntityLoadById = NoteEntityHelper.loadById(getActivity(), j);
        if (noteEntityLoadById == null) {
            return;
        }
        if (NoteModel.NoteType.TYPE_MIND.equals(noteEntityLoadById.getNoteType())) {
            Intent intent3 = new Intent(getActivity(), (Class<?>) WebViewBrainActivity.class);
            intent3.putExtra(NoteIntent.KEY_CREATE_OR_OPEN_MIND, 1);
            Cursor cursorQueryNote = NoteProviderAccessUtils.queryNote(NoteApp.getInstance(), j);
            cursorQueryNote.moveToFirst();
            if (cursorQueryNote.getCount() > 0) {
                NoteCache noteCache = new NoteCache(ItemStore.parseNote(cursorQueryNote));
                String mindContent = noteCache.getNote().getMindContent();
                intent2 = intent3;
                arrayList2 = arrayList;
                NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity(noteCache.getNote().getId(), noteEntityLoadById.getParentId(), "", 1, noteCache.getNote().getUpdatedAt(), !(!TextUtils.isEmpty(mindContent) ? ((MindEntity) new Gson().fromJson(mindContent.substring(15), MindEntity.class)).isMap : 1), 1, noteCache.getNote().getTitle(), false, arrayList2);
                Bundle bundle = new Bundle();
                bundle.putSerializable("entity", noteLoadDataEntity);
                intent2.putExtras(bundle);
            } else {
                arrayList2 = arrayList;
                intent2 = intent3;
            }
            cursorQueryNote.close();
            intent = intent2;
        } else {
            arrayList2 = arrayList;
            if ("handwrite".equals(noteEntityLoadById.getNoteType()) || "multi_handwrite".equals(noteEntityLoadById.getNoteType())) {
                intent = new Intent(getActivity(), (Class<?>) PhoneMultiHandWriteActivity.class);
            } else if ("common".equals(noteEntityLoadById.getNoteType())) {
                intent = new Intent(getActivity(), (Class<?>) EditActivity.class);
                intent.putExtra(NoteIntent.KEY_NOTE_THEME_ID, noteEntityLoadById.getThemeId());
                intent.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.VIEW);
                intent.putExtra("com.miui.notes.folder_id", noteEntityLoadById.getParentId());
            } else {
                intent = null;
            }
        }
        if (intent == null) {
            return;
        }
        if (arrayList2 != null && !arrayList2.isEmpty()) {
            intent.setFlags(268468224);
            intent.putStringArrayListExtra(NoteIntent.ACTION_WITH_AI_QUERY, arrayList2);
        }
        intent.setAction("android.intent.action.VIEW");
        intent.putExtra("android.intent.extra.UID", j);
        getActivity().startActivity(intent);
    }

    public void deliverIntent(Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();
        boolean z = data != null && "minote".equals(data.getScheme());
        boolean z2 = intent.getData() != null && "mishare".equals(intent.getData().getScheme()) && "/notes/Share".equals(intent.getData().getPath());
        if (z) {
            logDebug("deliverIntent,isMiuiPlus：true");
            MiuiPlusHelper.checkMiuiplusDataFromIntent(getActivity(), intent, new MiuiPlusHelper.MiuiPlusCallBack() { // from class: com.miui.notes.home.PhoneActivityController.1
                @Override // com.miui.notes.tool.MiuiPlusHelper.MiuiPlusCallBack
                public void onError(int i) {
                }

                @Override // com.miui.notes.tool.MiuiPlusHelper.MiuiPlusCallBack
                public void onOpenNote(long j, long j2) {
                    if (j2 == -4) {
                        PrivateNotesListActivity.openPrivateNote(PhoneActivityController.this.getActivity(), j);
                    } else {
                        PhoneActivityController.this.getHomeViewModel().selectPage(0);
                        PhoneActivityController.this.openNoteById(j, null);
                    }
                }

                @Override // com.miui.notes.tool.MiuiPlusHelper.MiuiPlusCallBack
                public void onOpenAppPage(String str) {
                    if ("app_setting".equals(str)) {
                        NotesPreferenceActivity.open(PhoneActivityController.this.getActivity());
                    } else if ("quick_note_setting".equals(str)) {
                        QuickNotePreferenceActivity.open(PhoneActivityController.this.getActivity());
                    }
                }
            });
            return;
        }
        if (!TextUtils.isEmpty(action) && (action.equals("android.intent.action.VIEW") || action.equals("android.intent.action.INSERT_OR_EDIT") || (action.equals("android.intent.action.SEND") && !z2))) {
            logDebug("deliverIntent,action-view/insert/edit/send");
            boolean booleanExtra = intent.getBooleanExtra(EditActivity.KEYGUARD_SHORTCUT, false);
            if (!Utils.isInThisCountry(KoreaRegionUtils.REGION_KOREA) || PreferenceUtils.getKoreaPermissionInfoShowed(NoteApp.getInstance()) || booleanExtra) {
                ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(NoteIntent.ACTION_WITH_AI_QUERY);
                if (stringArrayListExtra != null || intent.getStringExtra("mishow_name") != null) {
                    openNoteById(NoteProviderAccessUtils.getExtraNoteIdFromIntent(intent), stringArrayListExtra);
                    return;
                }
                intent.setClass(getActivity(), EditActivity.class);
                intent.setFlags(268468224);
                getActivity().startActivity(intent);
                return;
            }
            return;
        }
        boolean z3 = !TextUtils.isEmpty(action) && action.equals(TodoIntent.ACTION_INSERT_OR_EDIT);
        boolean z4 = !TextUtils.isEmpty(action) && action.equals(TodoIntent.ACTION_SHORTCUT_INSERT_OR_EDIT);
        boolean z5 = !TextUtils.isEmpty(action) && action.equals(TodoIntent.ACTION_VIEW);
        boolean z6 = !TextUtils.isEmpty(action) && action.equals(NoteIntent.ACTION_VIEW);
        boolean z7 = !TextUtils.isEmpty(action) && action.equals(NoteIntent.ACTION_SEARCH_NOTES);
        boolean z8 = !TextUtils.isEmpty(action) && action.equals(TodoIntent.ACTION_SEARCH_NOTES);
        boolean zEquals = NoteIntent.ACTION_VIEW_HIDDEN_NOTE_BY_ID.equals(action);
        logDebug("deliverIntent,action:" + action + ", action type:" + intent.getType() + ", class name: " + getActivity().hashCode());
        logDebug("original,action:" + getActivity().getIntent().getAction() + ", action type:" + getActivity().getIntent().getType());
        if (z8) {
            if (getHomeViewModel().getPageSelected() != 1) {
                getHomeViewModel().setCurrentItem(1);
            }
            getHomeViewModel().setIntent(intent);
        }
        if (z6 || z7 || zEquals || z2) {
            if (getHomeViewModel().getPageSelected() != 0) {
                getHomeViewModel().setCurrentItem(0);
            }
            getHomeViewModel().setIntent(intent);
            if (z6) {
                checkAndOpenPendingTapShareNote();
                return;
            }
            return;
        }
        if (z3 || z4 || z5) {
            getHomeViewModel().setCurrentItem(1);
            getHomeViewModel().setIntent(intent);
        }
    }

    private void checkAndOpenPendingTapShareNote() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("tapshare_prefs", 0);
        long j = sharedPreferences.getLong("pending_note_id", -1L);
        if (j > 0) {
            sharedPreferences.edit().remove("pending_note_id").remove("pending_timestamp").apply();
            logDebug("Opening pending TapShare note: " + j);
            openNoteById(j, null);
        }
    }
}
