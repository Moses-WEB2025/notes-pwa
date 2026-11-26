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
import com.miui.pc.view.PadMindPreviewTextView;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PcMindListContent extends PcNoteBaseContent {
    private PadMindPreviewTextView mPreviewTextView;

    public static PcMindListContent newInstance(PcNoteListItem pcNoteListItem) {
        return new PcMindListContent(UIUtils.inflateView(pcNoteListItem.getNoteContentContainer(), R.layout.pc_mind_content_list));
    }

    protected PcMindListContent(View view) {
        super(view);
        this.mPreviewTextView = (PadMindPreviewTextView) view.findViewById(R.id.preview);
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
            sb.append(TextProcessUtils.subStringByMaxLine(TextProcessUtils.removeEditSpans(noteCache.getFormattedSnippet(bindContext.getSearchToken())), 2));
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
