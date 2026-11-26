package com.miui.notes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.miui.common.base.BaseActivity;
import com.miui.common.tool.RomUtils;
import com.miui.doodle.base.DoodleParams;
import com.miui.notes.NoteApp;
import com.miui.notes.doodle.feature.DoodleActivity;
import com.miui.notes.doodle.feature.DoodleFragment;
import com.miui.notes.doodle.feature.PadDoodleActivity;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.util.ResourceParser;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class CreateDoodleNoteBridgeActivity extends BaseActivity {
    public static final int REQUEST_CODE_DOODLE = 32;
    private WorkingNote mNewNote = null;

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mNewNote = WorkingNote.createEmptyNote(0L, 0, -1, ResourceParser.getDefaultBgId(this));
        startToDoodle();
    }

    private void startToDoodle() {
        Intent intent;
        if (!RomUtils.isPadMode()) {
            intent = new Intent(this, (Class<?>) DoodleActivity.class);
        } else {
            intent = new Intent(this, (Class<?>) PadDoodleActivity.class);
        }
        DoodleParams doodleParams = new DoodleParams();
        doodleParams.setFullScreen(false);
        intent.putExtra(DoodleFragment.KEY_PARAMS, doodleParams);
        intent.putExtra(DoodleActivity.EXTRA_KEY_IS_HIDDEN, this.mNewNote.isHidden());
        startActivityForResult(intent, 32);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 32) {
            String stringExtra = intent.getStringExtra(DoodleFragment.KEY_IMAGE_NAME);
            if (!TextUtils.isEmpty(stringExtra)) {
                insertDoodle(AttachmentUtils.saveImageFile(NoteApp.getInstance(), new File(stringExtra)));
            }
        }
        finish();
    }

    private void insertDoodle(String str) {
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(this);
        simpleBuilder.appendImage(str);
        simpleBuilder.appendNewLine();
        this.mNewNote.setWorkingText(simpleBuilder.toAncient());
        this.mNewNote.saveNote(true);
    }
}
