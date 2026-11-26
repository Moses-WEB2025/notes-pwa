package com.miui.notes.widget.notelist;

import android.appwidget.AppWidgetManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.ItemAnimHelper;
import com.miui.handwriting.common.tools.RecognizeDataHelper;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.data.Contact;
import com.miui.notes.feature.notelist.CommonNoteBaseItem;
import com.miui.notes.model.MindEntity;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.thumbnail.Thumbnail;

/* JADX INFO: loaded from: classes4.dex */
public class WidgetNoteListItem extends CommonNoteBaseItem {
    private ImageView mColor;
    private TextView mContent;
    private View mGroup;
    private ImageView mIcMindMap;
    private ImageView mIcMindOutline;
    private ImageView mImage;
    private View mLabel;
    private TextView mTitle;
    private ImageView mVoice;
    private View mask;
    private View selectIcon;

    @Override // com.miui.notes.component.BaseItem
    public void onViewDetachedFromWindow() {
    }

    public static WidgetNoteListItem newInstance(ViewGroup viewGroup, boolean z) {
        WidgetNoteListItem widgetNoteListItem = new WidgetNoteListItem(UIUtils.inflateView(viewGroup, R.layout.widget_note_list_item));
        widgetNoteListItem.setIsCallNote(z);
        return widgetNoteListItem;
    }

    protected WidgetNoteListItem(View view) {
        super(view);
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem
    protected void initNoteGroup() {
        super.initNoteGroup();
        this.mLabel = this.mNoteContainer.findViewById(R.id.label);
        this.mColor = (ImageView) this.mNoteContainer.findViewById(R.id.color);
        this.mImage = (ImageView) this.mNoteContainer.findViewById(R.id.image);
        this.mVoice = (ImageView) this.mNoteContainer.findViewById(R.id.voice);
        this.mIcMindMap = (ImageView) this.mNoteContainer.findViewById(R.id.mind_map);
        this.mIcMindOutline = (ImageView) this.mNoteContainer.findViewById(R.id.mind_outline);
        this.mask = this.mNoteContainer.findViewById(R.id.widget_list_item_masking);
        this.selectIcon = this.mNoteContainer.findViewById(R.id.widget_item_select_icon);
        this.mGroup = this.mNoteContainer.findViewById(R.id.note_list_group);
        this.mTitle = (TextView) this.mNoteContainer.findViewById(R.id.title);
        this.mContent = (TextView) this.mNoteContainer.findViewById(R.id.content_body);
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem
    public ViewGroup getNoteContentContainer() {
        return this.mNoteContainer;
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem
    protected ItemAnimHelper.AnchorInfo getAnchorInfoFromView(View view) {
        View view2 = (View) view.getParent();
        View view3 = (View) view2.getParent();
        return new ItemAnimHelper.AnchorInfo(view2.getX() + view3.getX() + view.getX(), view2.getY() + view3.getY() + view.getY(), view.getWidth(), view.getHeight(), view.getRotation());
    }

    @Override // com.miui.notes.component.BaseItem
    public void bind(ItemCache itemCache, BindContext bindContext, int i, boolean z) {
        super.bind(itemCache, bindContext, i);
        if (itemCache.getCacheType() != 0) {
            this.mImage.setVisibility(8);
            return;
        }
        this.mImage.setVisibility(0);
        NoteCache noteCache = (NoteCache) itemCache.getCacheObject();
        this.mBindContext = bindContext;
        this.mVoice.setVisibility(noteCache.getNoteContentType() == 3 ? 0 : 8);
        this.mIcMindMap.setVisibility(8);
        this.mIcMindOutline.setVisibility(8);
        this.mAlarm.setImageResource(R.drawable.ic_data_item_clock);
        this.mVoice.setImageResource(R.drawable.ic_data_item_audio);
        this.mStick.setImageResource(R.drawable.ic_data_item_stick);
        int indicatorResource = ResourceManager.getIndicatorResource(noteCache.getNote().getThemeId());
        if (indicatorResource != 0) {
            this.mColor.setVisibility(0);
            this.mColor.setImageResource(indicatorResource);
        } else {
            this.mColor.setVisibility(8);
        }
        if (UIUtils.isMiproFontSupported()) {
            this.mTitle.setTextAppearance(R.style.V12_TextAppearance_List_Primary);
            this.mContent.setTextAppearance(R.style.V11_TextAppearance_List_Secondary);
        } else {
            this.mTitle.setTextAppearance(R.style.V8_TextAppearance_List_Primary);
            this.mContent.setTextAppearance(R.style.V8_TextAppearance_List_Secondary);
        }
        if (this.mIsCallNote) {
            this.mTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_call_record, 0);
            this.mTitle.setCompoundDrawablePadding((int) NoteApp.getInstance().getResources().getDimension(R.dimen.ic_call_record_padding));
        }
        String firstImage = noteCache.getFirstImage();
        String searchTokenBackUp = TextUtils.isEmpty(bindContext.getSearchToken()) ? bindContext.getSearchTokenBackUp() : bindContext.getSearchToken();
        boolean zIsEmpty = TextUtils.isEmpty(firstImage);
        setTitleAndContent(noteCache, noteCache.getNoteContentType(), searchTokenBackUp, !zIsEmpty);
        if (!zIsEmpty) {
            if (TextUtils.isEmpty(this.mLastImageRes) || !this.mLastImageRes.equals(firstImage)) {
                this.mLastImageRes = firstImage;
                this.mImage.setVisibility(0);
                Thumbnail.instance().load(noteCache.getNote()).resize(NoteConfig.LIST_THUMBNAIL_SIZE, NoteConfig.LIST_THUMBNAIL_SIZE).into(this.mImage);
            }
        } else {
            this.mLastImageRes = null;
            this.mImage.setImageDrawable(null);
            this.mImage.setVisibility(8);
            if (noteCache.getNoteContentType() == 5) {
                String mindContent = noteCache.getNote().getMindContent();
                boolean z2 = !TextUtils.isEmpty(mindContent) ? ((MindEntity) new Gson().fromJson(mindContent.substring(15), MindEntity.class)).isMap : false;
                this.mIcMindMap.setVisibility(!z2 ? 0 : 8);
                this.mIcMindOutline.setVisibility(z2 ? 0 : 8);
            }
        }
        this.mStick.setVisibility(noteCache.getNote().getStickAt() > 0 ? 0 : 8);
        this.mask.setVisibility(8);
        this.selectIcon.setVisibility(8);
        boolean z3 = (noteCache.getNote().getWidgetId() == 0 || AppWidgetManager.getInstance(this.itemView.getContext()).getAppWidgetInfo(noteCache.getNote().getWidgetId()) == null) ? false : true;
        if (z && z3) {
            setItemBackground(R.drawable.widget_list_item_select_bg);
            this.selectIcon.setVisibility(0);
        } else if (z3) {
            this.mask.setVisibility(0);
            setItemBackground(R.drawable.widget_list_item_bg);
        } else {
            setItemBackground(R.drawable.widget_list_item_bg);
        }
    }

    public void setTitleAndContent(NoteCache noteCache, int i, String str, boolean z) {
        CharSequence charSequenceChangeHighLight;
        boolean z2;
        Object objSubStringByMaxLine;
        String str2;
        String title = noteCache.getNote().getTitle();
        if (this.mIsCallNote) {
            Contact contact = noteCache.getNote().getContact();
            if (contact != null) {
                str2 = str;
                contact.loadNameInto(this.mTitle, noteCache.getNote().getId(), noteCache.getNote().getFolderId(), str2, noteCache.getNote().getTitle());
            } else {
                str2 = str;
            }
            this.mContent.setText(ContentUtils.changeHighLight(noteCache.getFormattedSnippet(str2), str2));
            return;
        }
        CharSequence charSequenceRemoveEditSpans = TextProcessUtils.removeEditSpans(noteCache.getFormattedSnippet(str));
        CharSequence charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
        if (TextUtils.isEmpty(title)) {
            if (TextUtils.isEmpty(charSequenceSubStringByMaxLine)) {
                if (i == 3) {
                    charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.snippet_audio_stub);
                } else if (i == 2) {
                    charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.snippet_image_stub);
                } else {
                    charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.pc_action_create_new_note);
                }
                z2 = false;
            } else {
                charSequenceChangeHighLight = ContentUtils.changeHighLight(TextProcessUtils.subStringByMaxLine(TextProcessUtils.removeEditSpans(noteCache.getFormattedSnippet("")).toString().trim(), 1), str);
                z2 = true;
            }
            if (noteCache.getNoteContentType() == 7) {
                charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.handwrite_description);
            }
        } else {
            charSequenceChangeHighLight = ContentUtils.changeHighLight(title, str);
            z2 = false;
        }
        if (noteCache.getNoteContentType() == 5) {
            objSubStringByMaxLine = Utils.getMindContent(noteCache.getNote().getMindContent());
        } else if (!TextUtils.isEmpty(str) || !z2) {
            objSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
        } else {
            objSubStringByMaxLine = TextProcessUtils.subStringByStartAndEndLine(charSequenceRemoveEditSpans.toString().trim(), 1, false);
        }
        String strExtractCleanContent = extractCleanContent((((noteCache.getNoteContentType() == 6 || noteCache.getNoteContentType() == 7) && TextUtils.isEmpty(str)) ? "" : objSubStringByMaxLine).toString());
        if (TextUtils.isEmpty(strExtractCleanContent) || RecognizeDataHelper.containsSeparators(ContentUtils.changeHighLight(strExtractCleanContent, str))) {
            strExtractCleanContent = NoteApp.getInstance().getResources().getString(R.string.note_not_text_stub);
        }
        this.mTitle.setText(charSequenceChangeHighLight);
        this.mContent.setText(strExtractCleanContent);
    }
}
