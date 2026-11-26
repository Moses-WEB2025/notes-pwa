package com.miui.pc.component;

import android.view.View;
import android.widget.TextView;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.data.Contact;

/* JADX INFO: loaded from: classes4.dex */
public class PcCallNoteListContent extends PcNoteBaseContent {
    private TextView mName;
    private TextView mPreview;

    public static PcCallNoteListContent newInstance(PcNoteListItem pcNoteListItem) {
        return new PcCallNoteListContent(UIUtils.inflateView(pcNoteListItem.getNoteContentContainer(), R.layout.call_note_content_list));
    }

    protected PcCallNoteListContent(View view) {
        super(view);
        this.mName = (TextView) view.findViewById(R.id.name);
        this.mPreview = (TextView) view.findViewById(R.id.preview);
    }

    @Override // com.miui.pc.component.PcNoteBaseContent
    protected void bind(NoteCache noteCache, BindContext bindContext, boolean z) {
        Contact contact = noteCache.getNote().getContact();
        if (contact != null) {
            contact.loadNameInto(this.mName, noteCache.getNote().getId(), noteCache.getNote().getFolderId(), bindContext.getSearchToken(), noteCache.getNote().getTitle());
        }
        this.mPreview.setText(ContentUtils.changePcHighLight(noteCache.getFormattedSnippet(bindContext.getSearchToken()), bindContext.getSearchToken()));
        this.mContent = this.mPreview.getText().toString();
    }
}
