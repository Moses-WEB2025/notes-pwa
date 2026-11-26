package com.miui.notes.feature.notelist;

import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.ItemAnimHelper;
import com.miui.handwriting.common.tools.RecognizeDataHelper;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.ainlsquery.AiQueryNoteExtra;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.data.Contact;
import com.miui.notes.model.MindEntity;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.AntiqueTheme;
import com.miui.notes.theme.DarkTheme;
import com.miui.notes.theme.SimpleTheme;
import com.miui.notes.theme.StarTheme;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.thumbnail.Thumbnail;
import com.miui.notes.tool.BitmapTask;
import com.miui.notes.tool.util.ColorMatrixHelper;
import com.miui.richeditor.theme.Theme;
import java.util.ArrayList;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class CommonNoteListItem extends CommonNoteBaseItem {
    private static final int itemBgResId = 2131231121;
    private static final int itemSelectBgResId = 2131231123;
    private static final int itemTrashBgResId = 2131233106;
    private final int mCheckBoxWidth;
    private ImageView mColor;
    private TextView mContent;
    private final int mContentInitMarginEnd;
    private View mGroup;
    private ImageView mHandWrite;
    private ImageView mIcMindMap;
    private ImageView mIcMindOutline;
    private ImageView mImage;
    private final int mImageViewInitMarginEnd;
    private boolean mIsRtl;
    private boolean mIsSelect;
    private View mLabel;
    private BitmapTask mThumbnailLoadTask;
    private TextView mTitle;
    private ImageView mVoice;
    private static final Drawable clockResId = ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.ic_data_item_clock);
    private static final Drawable audioResId = ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.ic_data_item_audio);
    private static final Drawable stickResId = ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.ic_data_item_stick);
    private static String TAG = "CommonNoteListItem";

    @Override // com.miui.notes.component.BaseItem
    public void onViewDetachedFromWindow() {
    }

    public static CommonNoteListItem newInstance(ViewGroup viewGroup, boolean z) {
        CommonNoteListItem commonNoteListItem;
        if (LiteUtils.isLiteOrMiddle()) {
            commonNoteListItem = new CommonNoteListItem(UIUtils.inflateView(viewGroup, R.layout.common_note_item_list_lite));
        } else {
            commonNoteListItem = new CommonNoteListItem(UIUtils.inflateView(viewGroup, R.layout.common_note_item_list));
        }
        commonNoteListItem.setIsCallNote(z);
        return commonNoteListItem;
    }

    protected CommonNoteListItem(final View view) {
        super(view);
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.feature.notelist.CommonNoteListItem$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.lambda$new$0(view, view2, motionEvent);
            }
        });
        this.mImageViewInitMarginEnd = (int) view.getContext().getResources().getDimension(R.dimen.list_item_image_margin_end);
        this.mCheckBoxWidth = (int) view.getContext().getResources().getDimension(R.dimen.multi_checkbox_size);
        this.mContentInitMarginEnd = (int) view.getContext().getResources().getDimension(R.dimen.list_item_content_margin_start);
        this.mIsRtl = Utils.isRTL();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(View view, View view2, MotionEvent motionEvent) {
        if (this.mItemViewFolme == null) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            this.mItemViewFolme.state().to(this.touchDownScale, new AnimConfig[0]);
        } else if (view.getScaleX() != 1.0d || motionEvent.getAction() == 1 || motionEvent.getAction() == 2 || motionEvent.getAction() == 3) {
            this.mItemViewFolme.state().to(this.touchUpScale, new AnimConfig[0]);
        }
        return false;
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem
    protected void initNoteGroup() {
        super.initNoteGroup();
        if (!LiteUtils.isLiteOrMiddle()) {
            this.mNoteContent = this.mNoteContainer;
        }
        this.mLabel = this.mNoteContainer.findViewById(R.id.label);
        this.mColor = (ImageView) this.mNoteContainer.findViewById(R.id.color);
        this.mImage = (ImageView) this.mNoteContainer.findViewById(R.id.image);
        this.mVoice = (ImageView) this.mNoteContainer.findViewById(R.id.voice);
        this.mIcMindMap = (ImageView) this.mNoteContainer.findViewById(R.id.mind_map);
        this.mIcMindOutline = (ImageView) this.mNoteContainer.findViewById(R.id.mind_outline);
        this.mHandWrite = (ImageView) this.mNoteContainer.findViewById(R.id.hand_write_icon);
        this.mGroup = this.mNoteContainer.findViewById(R.id.note_list_group);
        TextView textView = (TextView) this.mNoteContainer.findViewById(R.id.title);
        this.mTitle = textView;
        textView.setTextDirection(5);
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

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem, com.miui.notes.component.BaseItem
    public void bind(ItemCache itemCache, BindContext bindContext, int i) {
        bind(itemCache, bindContext, i, false);
    }

    @Override // com.miui.notes.component.BaseItem
    public void bind(ItemCache itemCache, BindContext bindContext, int i, boolean z) {
        Theme theme;
        boolean z2;
        int iconColor;
        super.bind(itemCache, bindContext, i);
        this.mIsSelect = z;
        if (z && !bindContext.isInActionMode()) {
            this.mNoteContainer.setForeground(NoteApp.getInstance().getDrawable(R.drawable.common_note_list_item_select_bg));
        } else {
            setItemBackground(this.isTrash ? R.drawable.trash_list_item_bg : R.drawable.common_note_list_item_bg);
        }
        if (itemCache.getCacheType() != 0) {
            this.mImage.setVisibility(8);
            return;
        }
        NoteCache noteCache = (NoteCache) itemCache.getCacheObject();
        this.mBindContext = bindContext;
        this.mVoice.setVisibility(noteCache.getNoteContentType() == 3 ? 0 : 8);
        this.mIcMindMap.setVisibility(8);
        this.mIcMindOutline.setVisibility(8);
        this.mHandWrite.setVisibility(8);
        int themeId = noteCache.getNote().getThemeId();
        if (RomUtils.isPadMode() || (theme = ResourceManager.getTheme(themeId)) == null) {
            theme = ResourceManager.getTheme(0);
        }
        if ((theme instanceof StarTheme) || (theme instanceof DarkTheme) || !(theme instanceof SimpleTheme) || (iconColor = ((SimpleTheme) theme).getIconColor()) == 0 || (theme instanceof AntiqueTheme) || (theme instanceof AnimTheme)) {
            this.mAlarm.setImageDrawable(clockResId);
            this.mVoice.setImageDrawable(audioResId);
            this.mStick.setImageDrawable(stickResId);
        } else {
            ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(ColorMatrixHelper.colorToMatrix(this.itemView.getResources().getColor(iconColor)));
            Drawable drawableMutate = this.itemView.getResources().getDrawable(R.drawable.ic_data_item_clock).mutate();
            drawableMutate.setColorFilter(colorMatrixColorFilter);
            this.mAlarm.setImageDrawable(drawableMutate);
            if (this.mVoice.getVisibility() == 0) {
                Drawable drawableMutate2 = this.itemView.getResources().getDrawable(R.drawable.ic_data_item_audio).mutate();
                drawableMutate2.setColorFilter(colorMatrixColorFilter);
                this.mVoice.setImageDrawable(drawableMutate2);
            }
            Drawable drawableMutate3 = this.itemView.getResources().getDrawable(R.drawable.ic_data_item_stick).mutate();
            drawableMutate3.setColorFilter(colorMatrixColorFilter);
            this.mStick.setImageDrawable(drawableMutate3);
        }
        int indicatorResource = ResourceManager.getIndicatorResource(noteCache.getNote().getThemeId());
        if (indicatorResource != 0 && !RomUtils.isPadMode()) {
            this.mColor.setVisibility(0);
            this.mColor.setImageResource(indicatorResource);
        } else {
            this.mColor.setVisibility(8);
        }
        String firstImage = noteCache.getFirstImage();
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
        int noteContentType = noteCache.getNoteContentType();
        String searchTokenBackUp = TextUtils.isEmpty(bindContext.getSearchToken()) ? bindContext.getSearchTokenBackUp() : bindContext.getSearchToken();
        boolean zIsEmpty = TextUtils.isEmpty(firstImage);
        setTitleAndContent(noteCache, noteContentType, searchTokenBackUp, !zIsEmpty, bindContext);
        if (!zIsEmpty) {
            this.mImage.setVisibility(0);
            if (TextUtils.isEmpty(this.mLastImageRes) || !this.mLastImageRes.equals(firstImage)) {
                this.mLastImageRes = firstImage;
                this.mImage.setImageDrawable(null);
                this.mThumbnailLoadTask = Thumbnail.instance().load(noteCache.getNote()).resize(NoteConfig.LIST_THUMBNAIL_SIZE, NoteConfig.LIST_THUMBNAIL_SIZE).into(this.mImage);
            }
        } else {
            this.mLastImageRes = null;
            this.mImage.setImageDrawable(null);
            this.mImage.setVisibility(8);
            if (noteCache.getNoteContentType() == 5) {
                String mindContent = noteCache.getNote().getMindContent();
                if (mindContent.length() <= 15) {
                    mindContent = MindEntity.createNewMindContentByTitle("");
                }
                try {
                    z2 = ((MindEntity) new Gson().fromJson(mindContent.substring(15), MindEntity.class)).isMap;
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "bind,fromJson error: " + e.getMessage());
                    z2 = false;
                }
                this.mIcMindMap.setVisibility(!z2 ? 0 : 8);
                this.mIcMindOutline.setVisibility(z2 ? 0 : 8);
            } else if (noteCache.getNoteContentType() == 6 || noteCache.getNoteContentType() == 7) {
                this.mHandWrite.setVisibility(0);
            }
        }
        if (bindContext.isInActionMode()) {
            onUpdateEditable(true, bindContext.isItemIdChecked(noteCache.getNote().getId()));
        } else if (!z) {
            onUpdateEditable(false, false);
        }
        this.mStick.setVisibility(noteCache.getNote().getStickAt() > 0 ? 0 : 8);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0089  */
    /* JADX WARN: Code duplicated, block: B:19:0x0093  */
    /* JADX WARN: Code duplicated, block: B:56:0x019e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:57:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:58:0x01a5  */
    /* JADX WARN: Code duplicated, block: B:59:0x01b6  */
    /* JADX WARN: Code duplicated, block: B:67:0x01cc A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:68:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:70:0x01e3  */
    /* JADX WARN: Code duplicated, block: B:72:0x01e9 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:75:0x01f9  */
    /* JADX WARN: Code duplicated, block: B:79:0x0213  */
    /* JADX WARN: Code duplicated, block: B:80:0x0223 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:81:0x0225  */
    public void setTitleAndContent(NoteCache noteCache, int i, String str, boolean z, BindContext bindContext) {
        String title;
        CharSequence charSequenceSubStringByStartAndEndLine;
        ArrayList<String> arrayList;
        CharSequence charSequenceChangeHighLight;
        int iIntValue;
        boolean z2;
        CharSequence charSequenceSubStringByMaxLine;
        String string;
        CharSequence charSequenceSubStringByMaxLine2;
        String str2;
        if (TextUtils.isEmpty(str) || bindContext.getAiQueryNoteList(noteCache.getNote().getId()) == null) {
            title = null;
            charSequenceSubStringByStartAndEndLine = null;
            arrayList = null;
        } else {
            AiQueryNoteExtra aiQueryNoteList = bindContext.getAiQueryNoteList(noteCache.getNote().getId());
            ArrayList<String> highlightKeywords = aiQueryNoteList.getHighlightKeywords();
            title = aiQueryNoteList.getTitle();
            charSequenceSubStringByStartAndEndLine = aiQueryNoteList.getChunkText();
            arrayList = highlightKeywords;
        }
        boolean zIsEmpty = TextUtils.isEmpty(title);
        boolean zIsEmpty2 = TextUtils.isEmpty(charSequenceSubStringByStartAndEndLine);
        String title2 = noteCache.getNote().getTitle();
        if (this.mIsCallNote) {
            if (!zIsEmpty) {
                this.mTitle.setText(ContentUtils.changeArrHighLight(title, arrayList));
            } else {
                Contact contact = noteCache.getNote().getContact();
                if (contact != null) {
                    str2 = str;
                    contact.loadNameInto(this.mTitle, noteCache.getNote().getId(), noteCache.getNote().getFolderId(), str2, noteCache.getNote().getTitle());
                }
                if (!zIsEmpty2) {
                    this.mContent.setText(ContentUtils.changeArrHighLight(charSequenceSubStringByStartAndEndLine, arrayList));
                    return;
                } else {
                    this.mContent.setText(ContentUtils.changeHighLight(noteCache.getFormattedSnippet(str2), str2));
                    return;
                }
            }
            str2 = str;
            if (!zIsEmpty2) {
                this.mContent.setText(ContentUtils.changeArrHighLight(charSequenceSubStringByStartAndEndLine, arrayList));
                return;
            } else {
                this.mContent.setText(ContentUtils.changeHighLight(noteCache.getFormattedSnippet(str2), str2));
                return;
            }
        }
        CharSequence charSequenceRemoveEditSpans = TextProcessUtils.removeEditSpans(noteCache.getFormattedSnippet(str));
        if (zIsEmpty2) {
            charSequenceSubStringByStartAndEndLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
        }
        CharSequence charSequenceExtractCleanContent = "";
        if (TextUtils.isEmpty(title2)) {
            if (!zIsEmpty2) {
                Pair<Integer, String> pairExtractAiQueryCleanContent = extractAiQueryCleanContent(charSequenceSubStringByStartAndEndLine.toString());
                if (((Integer) pairExtractAiQueryCleanContent.first).intValue() > 1) {
                    charSequenceSubStringByMaxLine2 = TextProcessUtils.subStringByStartAndEndLine(charSequenceSubStringByStartAndEndLine, ((Integer) pairExtractAiQueryCleanContent.first).intValue() - 1, true);
                    iIntValue = ((Integer) pairExtractAiQueryCleanContent.first).intValue();
                } else {
                    charSequenceSubStringByMaxLine2 = TextProcessUtils.subStringByMaxLine(charSequenceSubStringByStartAndEndLine, 1);
                    iIntValue = 1;
                }
                charSequenceChangeHighLight = ContentUtils.changeArrHighLight(charSequenceSubStringByMaxLine2, arrayList);
                z2 = true;
            } else if (TextUtils.isEmpty(charSequenceSubStringByStartAndEndLine)) {
                if (i == 3) {
                    charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.snippet_audio_stub);
                } else if (i == 2) {
                    charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.snippet_image_stub);
                } else if (i == 5) {
                    charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.pc_action_create_new_mind_note);
                } else if (i == 7) {
                    charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.handwrite_description);
                } else {
                    charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.pc_action_create_new_note);
                }
            } else if (noteCache.getNoteContentType() == 7) {
                charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.handwrite_description);
            } else {
                charSequenceChangeHighLight = ContentUtils.changeHighLight(TextProcessUtils.subStringByMaxLine(TextProcessUtils.removeEditSpans(noteCache.getFormattedSnippet("")).toString().trim(), 1), str);
                iIntValue = 1;
                z2 = true;
            }
            if (noteCache.getNoteContentType() == 5) {
                if (!zIsEmpty2) {
                    charSequenceExtractCleanContent = ContentUtils.changeArrHighLight(charSequenceSubStringByStartAndEndLine, arrayList);
                } else {
                    charSequenceExtractCleanContent = ContentUtils.changeHighLight(Utils.getMindContent(noteCache.getNote().getMindContent()), str);
                }
            } else if ((noteCache.getNoteContentType() == 6 && noteCache.getNoteContentType() != 7) || !TextUtils.isEmpty(str)) {
                if (!zIsEmpty2) {
                    if (z2) {
                        charSequenceSubStringByStartAndEndLine = TextProcessUtils.subStringByStartAndEndLine(charSequenceSubStringByStartAndEndLine, iIntValue, false);
                    }
                    charSequenceExtractCleanContent = ContentUtils.changeArrHighLight((CharSequence) extractAiQueryCleanContent(charSequenceSubStringByStartAndEndLine.toString()).second, arrayList);
                } else {
                    if (TextUtils.isEmpty(str) || !z2) {
                        charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
                    } else {
                        charSequenceSubStringByMaxLine = TextProcessUtils.subStringByStartAndEndLine(charSequenceRemoveEditSpans.toString().trim(), iIntValue, false);
                    }
                    charSequenceExtractCleanContent = extractCleanContent(charSequenceSubStringByMaxLine.toString());
                }
            }
            if (TextUtils.isEmpty(charSequenceExtractCleanContent)) {
                charSequenceExtractCleanContent = NoteApp.getInstance().getResources().getString(R.string.note_not_text_stub);
            } else if (zIsEmpty2) {
                charSequenceExtractCleanContent = ContentUtils.changeHighLight(charSequenceExtractCleanContent, str);
                string = charSequenceExtractCleanContent.toString();
                if ((TextUtils.isEmpty(string) && string.contains(RecognizeDataHelper.SPLIT)) || string.contains(RecognizeDataHelper.SPANNED_SPLIT)) {
                    charSequenceExtractCleanContent = RecognizeDataHelper.removeRecognizeSplits(string, str);
                    this.mContent.setTextColor(NoteApp.getInstance().getResources().getColor(R.color.user_query_highlight));
                }
            }
            this.mTitle.setText(charSequenceChangeHighLight);
            this.mContent.setText(charSequenceExtractCleanContent);
        }
        if (!zIsEmpty) {
            charSequenceChangeHighLight = ContentUtils.changeArrHighLight(title2, arrayList);
        } else {
            charSequenceChangeHighLight = ContentUtils.changeHighLight(title2, str);
        }
        iIntValue = 1;
        z2 = false;
        if (noteCache.getNoteContentType() == 5) {
            if (!zIsEmpty2) {
                charSequenceExtractCleanContent = ContentUtils.changeArrHighLight(charSequenceSubStringByStartAndEndLine, arrayList);
            } else {
                charSequenceExtractCleanContent = ContentUtils.changeHighLight(Utils.getMindContent(noteCache.getNote().getMindContent()), str);
            }
        } else if (noteCache.getNoteContentType() == 6) {
            if (!zIsEmpty2) {
                if (z2) {
                    charSequenceSubStringByStartAndEndLine = TextProcessUtils.subStringByStartAndEndLine(charSequenceSubStringByStartAndEndLine, iIntValue, false);
                }
                charSequenceExtractCleanContent = ContentUtils.changeArrHighLight((CharSequence) extractAiQueryCleanContent(charSequenceSubStringByStartAndEndLine.toString()).second, arrayList);
            } else {
                if (TextUtils.isEmpty(str)) {
                    charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
                } else {
                    charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
                }
                charSequenceExtractCleanContent = extractCleanContent(charSequenceSubStringByMaxLine.toString());
            }
        } else if (!zIsEmpty2) {
            if (z2) {
                charSequenceSubStringByStartAndEndLine = TextProcessUtils.subStringByStartAndEndLine(charSequenceSubStringByStartAndEndLine, iIntValue, false);
            }
            charSequenceExtractCleanContent = ContentUtils.changeArrHighLight((CharSequence) extractAiQueryCleanContent(charSequenceSubStringByStartAndEndLine.toString()).second, arrayList);
        } else {
            if (TextUtils.isEmpty(str)) {
                charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
            } else {
                charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
            }
            charSequenceExtractCleanContent = extractCleanContent(charSequenceSubStringByMaxLine.toString());
        }
        if (TextUtils.isEmpty(charSequenceExtractCleanContent)) {
            charSequenceExtractCleanContent = NoteApp.getInstance().getResources().getString(R.string.note_not_text_stub);
        } else if (zIsEmpty2) {
            charSequenceExtractCleanContent = ContentUtils.changeHighLight(charSequenceExtractCleanContent, str);
            string = charSequenceExtractCleanContent.toString();
            if (TextUtils.isEmpty(string)) {
                charSequenceExtractCleanContent = RecognizeDataHelper.removeRecognizeSplits(string, str);
                this.mContent.setTextColor(NoteApp.getInstance().getResources().getColor(R.color.user_query_highlight));
            } else {
                charSequenceExtractCleanContent = RecognizeDataHelper.removeRecognizeSplits(string, str);
                this.mContent.setTextColor(NoteApp.getInstance().getResources().getColor(R.color.user_query_highlight));
            }
        }
        this.mTitle.setText(charSequenceChangeHighLight);
        this.mContent.setText(charSequenceExtractCleanContent);
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem, com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        boolean zOnUpdateEditable = super.onUpdateEditable(z, z2);
        if (this.mIsSelect) {
            this.mNoteContainer.setForeground(NoteApp.getInstance().getDrawable(R.drawable.common_note_list_item_select_bg));
        }
        float f = z ? 1.0f : 0.0f;
        if (this.mImage.getVisibility() == 0) {
            setImageMarginEnd(f);
            return zOnUpdateEditable;
        }
        setContentMarginEnd(f);
        return zOnUpdateEditable;
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        BitmapTask bitmapTask = this.mThumbnailLoadTask;
        if (bitmapTask != null && bitmapTask.isRunning()) {
            this.mThumbnailLoadTask.cancel();
        }
        super.onRecycled();
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationUpdate(boolean z, float f) {
        super.onAnimationUpdate(z, f);
        if (!z) {
            f = 1.0f - f;
        }
        if (this.mImage.getVisibility() == 0) {
            setImageMarginEnd(f);
        } else {
            setContentMarginEnd(f);
        }
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
        super.onAnimationStop(z);
        if (z) {
            if (this.mImage.getVisibility() == 0) {
                setImageMarginEnd(1.0f);
            } else {
                setContentMarginEnd(1.0f);
            }
        }
    }

    private void setImageMarginEnd(float f) {
        if (LiteUtils.isLiteOrMiddle()) {
            return;
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mImage.getLayoutParams();
        int i = this.mImageViewInitMarginEnd;
        layoutParams.setMarginEnd(i + ((int) ((this.mCheckBoxWidth + i) * f)));
        this.mImage.setLayoutParams(layoutParams);
    }

    private void setContentMarginEnd(float f) {
        if (LiteUtils.isLiteOrMiddle()) {
            return;
        }
        if (f == 0.0f || f == 1.0f || this.mIsRtl) {
            int i = ((int) (this.mCheckBoxWidth * f)) + 30;
            View view = this.mLabel;
            boolean z = this.mIsRtl;
            view.setPadding(z ? i : 0, 0, z ? 0 : i, 0);
            View view2 = this.mGroup;
            if (view2 != null) {
                boolean z2 = this.mIsRtl;
                int i2 = z2 ? i : 0;
                if (z2) {
                    i = 0;
                }
                view2.setPadding(i2, 0, i, 0);
            }
        }
    }

    @Override // com.miui.notes.component.BaseItem
    public void setTrash(boolean z) {
        this.isTrash = z;
    }
}
