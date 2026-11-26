package com.miui.pc.component;

import android.text.TextUtils;
import android.view.View;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.tool.SnippetFormatter;
import com.miui.pc.view.PcPreviewTextView;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PcNoteListContent extends PcNoteBaseContent {
    private PcPreviewTextView mPreviewTextView;

    public static PcNoteListContent newInstance(PcNoteListItem pcNoteListItem) {
        return new PcNoteListContent(UIUtils.inflateView(pcNoteListItem.getNoteContentContainer(), R.layout.pc_note_content_list));
    }

    protected PcNoteListContent(View view) {
        super(view);
        this.mPreviewTextView = (PcPreviewTextView) view.findViewById(R.id.preview);
        if (UIUtils.isMiproFontSupported()) {
            DisplayUtils.setMiuiMediumTypeFace(this.mPreviewTextView);
        }
    }

    @Override // com.miui.pc.component.PcNoteBaseContent
    protected void bind(NoteCache noteCache, BindContext bindContext, boolean z) {
        this.mPreviewTextView.setTitleStyle(R.style.V12_PC_TextAppearance_List_Primary);
        this.mPreviewTextView.setBodyStyle(R.style.V12_PC_TextAppearance_List_Secondary);
        this.mContent = noteCache.getNote().getPlainText();
        StringBuilder sb = new StringBuilder();
        String title = noteCache.getNote().getTitle();
        String snippet = noteCache.getNote().getSnippet();
        if (!TextUtils.isEmpty(title)) {
            if (noteCache.getNoteContentType() == 5) {
                snippet = noteCache.getNote().getMindContent();
            }
            sb.append(title);
            sb.append(StringUtils.LF);
            if (noteCache.getNoteContentType() == 1) {
                sb.append(TextProcessUtils.removeEditSpans(SnippetFormatter.format(getItemView().getContext(), snippet, bindContext.getSearchToken())));
            }
            this.mPreviewTextView.setContainTitle(true);
        } else {
            if (noteCache.getNoteContentType() == 3) {
                String strExtractTitle = extractTitle(noteCache.getNote().getPlainText());
                if (TextUtils.isEmpty(strExtractTitle)) {
                    sb.append(getItemView().getResources().getString(R.string.snippet_audio_stub));
                } else {
                    sb.append(TextProcessUtils.subStringByMaxLine(TextProcessUtils.removeEditSpans(strExtractTitle), 2));
                }
            } else if (noteCache.getNoteContentType() == 2) {
                String strExtractTitle2 = extractTitle(noteCache.getNote().getPlainText());
                if (TextUtils.isEmpty(strExtractTitle2)) {
                    sb.append(getItemView().getResources().getString(R.string.snippet_image_stub));
                } else {
                    sb.append(TextProcessUtils.subStringByMaxLine(TextProcessUtils.removeEditSpans(strExtractTitle2), 2));
                }
            } else if (noteCache.getNoteContentType() == 4) {
                CharSequence charSequenceRemoveEditSpans = TextProcessUtils.removeEditSpans(noteCache.getFormattedSnippet(bindContext.getSearchToken()));
                if (charSequenceRemoveEditSpans != null && charSequenceRemoveEditSpans.length() > 0) {
                    sb.append(TextProcessUtils.subStringByMaxLine(extractCleanContent(charSequenceRemoveEditSpans.toString()), 2));
                }
            } else {
                sb.append(TextProcessUtils.subStringByMaxLine(TextProcessUtils.removeEditSpans(noteCache.getFormattedSnippet(snippet)), 2));
            }
            this.mPreviewTextView.setContainTitle(false);
        }
        if (TextUtils.isEmpty(bindContext.getSearchToken())) {
            this.mPreviewTextView.setSearchToken(bindContext.getSearchTokenBackUp());
        } else {
            this.mPreviewTextView.setSearchToken(bindContext.getSearchToken());
        }
        this.mPreviewTextView.setText(sb);
    }
}
