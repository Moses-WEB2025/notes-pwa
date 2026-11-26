package com.miui.notes.feature.notelist;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.ItemAnimHelper;
import com.miui.handwriting.common.tools.RecognizeDataHelper;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.ainlsquery.AiQueryNoteExtra;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.component.MindThumbnailView;
import com.miui.notes.component.OverviewThumbnailView;
import com.miui.notes.component.PreviewImageView;
import com.miui.notes.data.Contact;
import com.miui.notes.model.MindDataHelper;
import com.miui.notes.model.MindEntity;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.ColorTheme;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.thumbnail.Thumbnail;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.BitmapDecoder;
import com.miui.notes.tool.BitmapTask;
import com.miui.richeditor.theme.Theme;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes4.dex */
public class CommonNoteGridItem extends CommonNoteBaseItem {
    private View bar;
    private final int mCheckBoxWidth;
    private final int mCheckboxInitMarginEnd;
    private TextView mContent;
    protected float mContentRadius;
    private ImageView mHandWrite;
    private PreviewImageView mImage;
    private LoadImageContentTask mLoadImageContentTask;
    private ImageView mMind;
    private ImageView mMindOverview;
    private MindThumbnailView mMindThumbnail;
    private OverviewThumbnailView mOverviewThumbnail;
    private BitmapTask mThumbnailLoadTask;
    private TextView mTitle;
    private ImageView mVoice;

    public static CommonNoteGridItem newInstance(ViewGroup viewGroup, boolean z) {
        Logger.INSTANCE.d("CommonNoteGridItem", "newInstance");
        CommonNoteGridItem commonNoteGridItem = new CommonNoteGridItem(UIUtils.inflateView(viewGroup, R.layout.common_note_item_grid));
        commonNoteGridItem.setIsCallNote(z);
        return commonNoteGridItem;
    }

    protected CommonNoteGridItem(final View view) {
        super(view);
        this.mLoadImageContentTask = null;
        this.mContentRadius = view.getContext().getResources().getDimension(R.dimen.v12_grid_item_shape_radius);
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.feature.notelist.CommonNoteGridItem.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (CommonNoteGridItem.this.mItemViewFolme == null) {
                    return false;
                }
                if (motionEvent.getAction() == 0) {
                    CommonNoteGridItem.this.mItemViewFolme.state().setEase(EaseManager.getStyle(-2, 0.85f, 0.3f), new FloatProperty[0]).to(CommonNoteGridItem.this.touchDownScale, new AnimConfig[0]);
                } else if (view.getScaleX() != 1.0d) {
                    CommonNoteGridItem.this.mItemViewFolme.state().setEase(EaseManager.getStyle(-2, 0.85f, 0.3f), new FloatProperty[0]).to(CommonNoteGridItem.this.touchUpScale, new AnimConfig[0]);
                }
                return false;
            }
        });
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.feature.notelist.CommonNoteGridItem.2
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                outline.setRoundRect(new Rect(0, 0, view2.getWidth(), view2.getHeight()), CommonNoteGridItem.this.mContentRadius);
                view2.setClipToOutline(true);
            }
        });
        this.mCheckboxInitMarginEnd = (int) view.getContext().getResources().getDimension(R.dimen.grid_checkbox_margin_end);
        this.mCheckBoxWidth = (int) view.getContext().getResources().getDimension(R.dimen.multi_checkbox_size);
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem
    protected void initNoteGroup() {
        super.initNoteGroup();
        this.mImage = (PreviewImageView) this.mNoteContainer.findViewById(R.id.image);
        this.mOverviewThumbnail = (OverviewThumbnailView) this.mNoteContainer.findViewById(R.id.overview_thumbnail);
        this.mMindThumbnail = (MindThumbnailView) this.mNoteContainer.findViewById(R.id.mind_thumbnail);
        this.mVoice = (ImageView) this.mNoteContainer.findViewById(R.id.voice);
        this.mMind = (ImageView) this.mNoteContainer.findViewById(R.id.mind_map);
        this.mMindOverview = (ImageView) this.mNoteContainer.findViewById(R.id.mind_outline);
        this.mHandWrite = (ImageView) this.mNoteContainer.findViewById(R.id.hand_write_icon);
        this.bar = this.mNoteContainer.findViewById(R.id.bar);
        this.mTitle = (TextView) this.mNoteContainer.findViewById(R.id.title);
        this.mContent = (TextView) this.mNoteContainer.findViewById(R.id.content_body);
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem
    public ViewGroup getNoteContentContainer() {
        return this.mNoteContent;
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem
    protected ItemAnimHelper.AnchorInfo getAnchorInfoFromView(View view) {
        View view2 = (View) view.getParent();
        View view3 = (View) view2.getParent();
        View view4 = (View) view3.getParent();
        return new ItemAnimHelper.AnchorInfo(view2.getX() + view3.getX() + view4.getX() + view.getX(), view2.getY() + view3.getY() + view4.getY() + view.getY(), view.getWidth(), view.getHeight(), view.getRotation());
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem, com.miui.notes.component.BaseItem
    public void bind(ItemCache itemCache, BindContext bindContext, int i) {
        bind(itemCache, bindContext, i, false);
    }

    /* JADX WARN: Code duplicated, block: B:107:0x0376  */
    /* JADX WARN: Code duplicated, block: B:108:0x0387  */
    /* JADX WARN: Code duplicated, block: B:111:0x039a  */
    /* JADX WARN: Code duplicated, block: B:112:0x039c  */
    @Override // com.miui.notes.component.BaseItem
    public void bind(ItemCache itemCache, BindContext bindContext, int i, boolean z) {
        Theme theme;
        boolean z2;
        boolean z3;
        int i2;
        int i3;
        String attachmentPath;
        boolean z4;
        super.bind(itemCache, bindContext, i);
        if (itemCache.getCacheType() != 0) {
            this.mImage.setVisibility(8);
            this.mOverviewThumbnail.setVisibility(8);
            this.mMindThumbnail.setVisibility(8);
            return;
        }
        NoteCache noteCache = (NoteCache) itemCache.getCacheObject();
        Context context = this.itemView.getContext();
        int themeId = noteCache.getNote().getThemeId();
        if (RomUtils.isPadMode() || (theme = ResourceManager.getTheme(themeId)) == null) {
            theme = ResourceManager.getTheme(0);
        }
        Theme theme2 = theme;
        Theme.GridStyle gridStyle = theme2.getGridStyle(context);
        this.mVoice.setVisibility(noteCache.getNoteContentType() == 3 ? 0 : 8);
        this.mTime.setTextAppearance(this.mTime.getContext(), gridStyle.mTimeStyle);
        this.mTitle.setTextAppearance(gridStyle.mTitleStyle);
        if (this.mIsCallNote) {
            this.mTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_call_record, 0);
            this.mTitle.setCompoundDrawablePadding((int) NoteApp.getInstance().getResources().getDimension(R.dimen.ic_call_record_padding));
        }
        this.mContent.setTextAppearance(gridStyle.mBodyStyle);
        this.mAlarm.setImageDrawable(gridStyle.getAlertIcon());
        this.mStick.setImageDrawable(gridStyle.getStickIcon());
        this.mVoice.setImageDrawable(gridStyle.getVoiceIcon());
        this.mMind.setVisibility(8);
        this.mMindOverview.setVisibility(8);
        this.mHandWrite.setVisibility(8);
        String firstImage = noteCache.getFirstImage();
        int noteContentType = noteCache.getNoteContentType();
        String searchTokenBackUp = TextUtils.isEmpty(bindContext.getSearchToken()) ? bindContext.getSearchTokenBackUp() : bindContext.getSearchToken();
        ArrayList<String> highlightKeywords = bindContext.getAiQueryNoteList(noteCache.getNote().getId()) != null ? bindContext.getAiQueryNoteList(noteCache.getNote().getId()).getHighlightKeywords() : null;
        if (highlightKeywords == null || highlightKeywords.isEmpty()) {
            highlightKeywords = new ArrayList<>();
            highlightKeywords.add(searchTokenBackUp);
        }
        ArrayList<String> arrayList = highlightKeywords;
        String strConvertToCorrectFormatContent = "";
        if (noteCache.getNoteContentType() == 5) {
            String mindContent = noteCache.getNote().getMindContent();
            if (TextUtils.isEmpty(mindContent)) {
                z4 = false;
                z3 = false;
            } else {
                boolean zIsCorrectFormatContent = MindDataHelper.isCorrectFormatContent(mindContent);
                MindEntity mindEntity = (MindEntity) new Gson().fromJson(mindContent.substring(15), MindEntity.class);
                z3 = mindEntity.isMap;
                this.mMind.setVisibility(z3 ? 8 : 0);
                this.mMindOverview.setVisibility(z3 ? 0 : 8);
                strConvertToCorrectFormatContent = mindEntity.content;
                z4 = zIsCorrectFormatContent;
            }
            z2 = z4;
        } else {
            if (noteCache.getNoteContentType() == 6 || noteCache.getNoteContentType() == 7) {
                this.mHandWrite.setVisibility(0);
            }
            z2 = false;
            z3 = false;
        }
        boolean z5 = gridStyle.mShowImage && !TextUtils.isEmpty(firstImage);
        setTitleAndContent(noteCache, noteContentType, searchTokenBackUp, z5, bindContext);
        boolean z6 = z5;
        if (this.mLoadImageContentTask != null) {
            this.mNoteContent.removeCallbacks(this.mLoadImageContentTask);
            this.mLoadImageContentTask.destroy();
            this.mLoadImageContentTask = null;
        }
        if (z6) {
            this.mOverviewThumbnail.setVisibility(8);
            this.mMindThumbnail.setVisibility(8);
            if (TextUtils.isEmpty(this.mLastImageRes) || !this.mLastImageRes.equals(firstImage)) {
                this.mLastImageRes = firstImage;
                this.mNoteContent.setPadding(this.mNoteContent.getPaddingLeft(), (int) (((double) this.mNoteContent.getPaddingTop()) * 0.5d), this.mNoteContent.getPaddingRight(), this.mNoteContainer.getPaddingBottom());
                if (AttachmentUtils.isDoodleSuffix(firstImage)) {
                    attachmentPath = AttachmentUtils.getDoodleThumbnailPath(context, firstImage);
                } else {
                    attachmentPath = AttachmentUtils.getAttachmentPath(context, firstImage);
                }
                if (!TextUtils.isEmpty(attachmentPath) ? new File(attachmentPath).exists() : false) {
                    this.mImage.setVisibility(0);
                    this.mImage.setBitmapDecoder(new BitmapDecoder(context, attachmentPath));
                    this.mImage.setRadius(this.mContentRadius);
                    this.mThumbnailLoadTask = Thumbnail.instance().load(noteCache.getNote()).type(Thumbnail.GRID).into(this.mImage);
                } else {
                    this.mLastImageRes = null;
                    this.mImage.setVisibility(8);
                }
                this.mNoteContent.setMinimumHeight(0);
                this.mNoteContent.setBackground(ContextCompat.getDrawable(this.mImage.getContext(), R.drawable.v12_grid_item_bottom_radius_bg));
            }
            this.mNoteContainer.setBackground(null);
        } else {
            this.mContent.setMaxLines(noteContentType == 3 ? 1 : 4);
            this.mLastImageRes = null;
            this.mImage.setVisibility(8);
            this.mOverviewThumbnail.setVisibility(8);
            this.mMindThumbnail.setVisibility(8);
            this.mContent.setVisibility(0);
            if (theme2 instanceof AnimTheme) {
                this.mNoteContent.setOutlineProvider(null);
                this.mNoteContent.setBackground(context.getResources().getDrawable(R.drawable.v12_theme_bg_frame));
                this.mLoadImageContentTask = new LoadImageContentTask(this.mNoteContainer, theme2);
                this.mNoteContent.post(this.mLoadImageContentTask);
            } else {
                if (theme2 instanceof ColorTheme) {
                    this.mNoteContent.setMinimumHeight((int) context.getResources().getDimension(R.dimen.v12_default_theme_grid_bg_min_height));
                    this.mNoteContent.setOutlineProvider(null);
                    this.mNoteContent.setBackground(gridStyle.mBackground);
                } else {
                    this.mNoteContent.setMinimumHeight((int) context.getResources().getDimension(R.dimen.v12_anim_theme_grid_bg_min_height));
                    this.mNoteContent.setBackground(gridStyle.mBackground);
                }
                this.mNoteContainer.setBackground(null);
            }
            this.bar.setPadding(0, context.getResources().getDimensionPixelOffset(R.dimen.v12_item_grid_label_margin_top), 0, 0);
            if (noteCache.getNoteContentType() == 5) {
                this.bar.setPadding(0, context.getResources().getDimensionPixelOffset(R.dimen.common_mind_grid_label_padding_top), 0, 0);
                this.mLastImageRes = null;
                if (!z3) {
                    this.mOverviewThumbnail.setVisibility(0);
                    this.mContent.setVisibility(8);
                    this.mMindThumbnail.setVisibility(8);
                    this.mOverviewThumbnail.setStyle(theme2);
                    this.mOverviewThumbnail.removeAllViews();
                    if (!TextUtils.isEmpty(strConvertToCorrectFormatContent)) {
                        if (!z2) {
                            strConvertToCorrectFormatContent = MindDataHelper.convertToCorrectFormatContent(strConvertToCorrectFormatContent);
                        }
                        this.mOverviewThumbnail.setContent(strConvertToCorrectFormatContent, arrayList);
                    }
                } else {
                    i2 = 8;
                    this.mOverviewThumbnail.setVisibility(8);
                    this.mMindThumbnail.setVisibility(0);
                    this.mContent.setVisibility(8);
                    this.mMindThumbnail.setStyle(theme2);
                    this.mMindThumbnail.removeAllViews();
                    this.mMindThumbnail.resetAllViews();
                    if (!TextUtils.isEmpty(strConvertToCorrectFormatContent)) {
                        if (!z2) {
                            strConvertToCorrectFormatContent = MindDataHelper.convertToCorrectFormatContent(strConvertToCorrectFormatContent);
                        }
                        this.mMindThumbnail.setContent(strConvertToCorrectFormatContent, arrayList);
                    }
                }
            }
            if (bindContext.isInActionMode()) {
                onUpdateEditable(true, bindContext.isItemIdChecked(noteCache.getNote().getId()));
            } else {
                onUpdateEditable(false, false);
            }
            ImageView imageView = this.mStick;
            if (noteCache.getNote().getStickAt() > 0) {
                i3 = 0;
            } else {
                i3 = i2;
            }
            imageView.setVisibility(i3);
        }
        i2 = 8;
        if (bindContext.isInActionMode()) {
            onUpdateEditable(true, bindContext.isItemIdChecked(noteCache.getNote().getId()));
        } else {
            onUpdateEditable(false, false);
        }
        ImageView imageView2 = this.mStick;
        if (noteCache.getNote().getStickAt() > 0) {
            i3 = 0;
        } else {
            i3 = i2;
        }
        imageView2.setVisibility(i3);
    }

    @Override // com.miui.notes.component.BaseItem
    public void onViewDetachedFromWindow() {
        releaseIoTask();
    }

    /* JADX WARN: Code duplicated, block: B:74:0x01e9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:75:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:77:0x0200  */
    /* JADX WARN: Code duplicated, block: B:82:0x0216  */
    /* JADX WARN: Code duplicated, block: B:86:0x0230  */
    /* JADX WARN: Code duplicated, block: B:87:0x0240 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:88:0x0242  */
    /* JADX WARN: Code duplicated, block: B:93:0x0252  */
    public void setTitleAndContent(NoteCache noteCache, int i, String str, boolean z, BindContext bindContext) {
        CharSequence charSequenceSubStringByStartAndEndLine;
        ArrayList<String> arrayList;
        String str2;
        boolean z2;
        int iIntValue;
        CharSequence charSequenceSubStringByMaxLine;
        CharSequence charSequenceSubStringByMaxLine2;
        if (TextUtils.isEmpty(str) || bindContext.getAiQueryNoteList(noteCache.getNote().getId()) == null) {
            charSequenceSubStringByStartAndEndLine = null;
            arrayList = null;
            str2 = null;
        } else {
            AiQueryNoteExtra aiQueryNoteList = bindContext.getAiQueryNoteList(noteCache.getNote().getId());
            ArrayList<String> highlightKeywords = aiQueryNoteList.getHighlightKeywords();
            String title = aiQueryNoteList.getTitle();
            charSequenceSubStringByStartAndEndLine = aiQueryNoteList.getChunkText();
            arrayList = highlightKeywords;
            str2 = title;
        }
        boolean zIsEmpty = TextUtils.isEmpty(str2);
        boolean zIsEmpty2 = TextUtils.isEmpty(charSequenceSubStringByStartAndEndLine);
        CharSequence title2 = noteCache.getNote().getTitle();
        int i2 = 3;
        if (this.mIsCallNote) {
            Contact contact = noteCache.getNote().getContact();
            if (!zIsEmpty) {
                this.mTitle.setText(ContentUtils.changeArrHighLight(str2, arrayList));
                i2 = 3;
            } else {
                if (contact != null && !TextUtils.isEmpty(contact.getDisplayName()) && !this.mTitle.getText().equals(contact.getDisplayName())) {
                    this.mTitle.setText(ContentUtils.changeHighLight(contact.getDisplayName(), str));
                }
                if (contact != null) {
                    contact.loadNameInto(this.mTitle, noteCache.getNote().getId(), noteCache.getNote().getFolderId(), str, noteCache.getNote().getTitle());
                }
                TextView textView = this.mTitle;
                if (textView != null && TextUtils.isEmpty(textView.getText())) {
                    this.mTitle.setText(title2);
                }
            }
            if (i == i2) {
                this.mContent.setText(NoteApp.getInstance().getResources().getString(R.string.snippet_audio_stub));
            } else if (!zIsEmpty2) {
                this.mContent.setText(ContentUtils.changeArrHighLight(str2, arrayList));
            } else {
                this.mContent.setText(ContentUtils.changeHighLight(noteCache.getFormattedSnippet(str), str));
            }
            if (z) {
                this.mContent.setVisibility(8);
                return;
            } else {
                this.mContent.setVisibility(0);
                return;
            }
        }
        if (z) {
            this.mContent.setMaxLines(1);
            this.mContent.setVisibility(0);
        }
        CharSequence charSequenceRemoveEditSpans = TextProcessUtils.removeEditSpans(noteCache.getFormattedSnippet(str));
        if (zIsEmpty2) {
            charSequenceSubStringByStartAndEndLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
        }
        CharSequence charSequenceChangeHighLight = "";
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
                title2 = ContentUtils.changeArrHighLight(charSequenceSubStringByMaxLine2, arrayList);
                z2 = true;
            } else if (!TextUtils.isEmpty(charSequenceSubStringByStartAndEndLine)) {
                title2 = ContentUtils.changeHighLight(TextProcessUtils.subStringByMaxLine(TextProcessUtils.removeEditSpans(noteCache.getFormattedSnippet("")).toString().trim(), 1), str);
                iIntValue = 1;
                z2 = true;
            } else if (i == 3) {
                title2 = NoteApp.getInstance().getResources().getString(R.string.snippet_audio_stub);
            } else if (i == 2) {
                this.mContent.setVisibility(8);
                title2 = NoteApp.getInstance().getResources().getString(R.string.snippet_image_stub);
            }
            if (noteCache.getNoteContentType() != 5 && ((noteCache.getNoteContentType() != 6 && noteCache.getNoteContentType() != 7) || !TextUtils.isEmpty(str))) {
                if (!zIsEmpty2) {
                    if (z2) {
                        charSequenceSubStringByStartAndEndLine = TextProcessUtils.subStringByStartAndEndLine(charSequenceSubStringByStartAndEndLine, iIntValue, false);
                    }
                    charSequenceChangeHighLight = ContentUtils.changeArrHighLight((CharSequence) extractAiQueryCleanContent(charSequenceSubStringByStartAndEndLine.toString()).second, arrayList);
                } else {
                    if (TextUtils.isEmpty(str) || !z2) {
                        charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
                    } else {
                        charSequenceSubStringByMaxLine = TextProcessUtils.subStringByStartAndEndLine(charSequenceRemoveEditSpans.toString().trim(), iIntValue, false);
                    }
                    charSequenceChangeHighLight = extractCleanContent(charSequenceSubStringByMaxLine.toString());
                }
            }
            if (TextUtils.isEmpty(charSequenceChangeHighLight)) {
                charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.note_not_text_stub);
            } else if (zIsEmpty2) {
                charSequenceChangeHighLight = ContentUtils.changeHighLight(charSequenceChangeHighLight, str);
            }
            if (!TextUtils.isEmpty(title2) || RecognizeDataHelper.containsSeparators(title2)) {
                title2 = NoteApp.getInstance().getResources().getString(R.string.pad_note_list_not_text_stub);
            }
            this.mTitle.setText(title2);
            this.mContent.setText(charSequenceChangeHighLight);
        }
        if (!zIsEmpty) {
            title2 = ContentUtils.changeArrHighLight(title2, arrayList);
        } else {
            title2 = ContentUtils.changeHighLight(title2, str);
        }
        z2 = false;
        iIntValue = 1;
        if (noteCache.getNoteContentType() != 5) {
            if (!zIsEmpty2) {
                if (z2) {
                    charSequenceSubStringByStartAndEndLine = TextProcessUtils.subStringByStartAndEndLine(charSequenceSubStringByStartAndEndLine, iIntValue, false);
                }
                charSequenceChangeHighLight = ContentUtils.changeArrHighLight((CharSequence) extractAiQueryCleanContent(charSequenceSubStringByStartAndEndLine.toString()).second, arrayList);
            } else {
                if (TextUtils.isEmpty(str)) {
                    charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
                } else {
                    charSequenceSubStringByMaxLine = TextProcessUtils.subStringByMaxLine(charSequenceRemoveEditSpans.toString().trim(), 1);
                }
                charSequenceChangeHighLight = extractCleanContent(charSequenceSubStringByMaxLine.toString());
            }
        }
        if (TextUtils.isEmpty(charSequenceChangeHighLight)) {
            charSequenceChangeHighLight = NoteApp.getInstance().getResources().getString(R.string.note_not_text_stub);
        } else if (zIsEmpty2) {
            charSequenceChangeHighLight = ContentUtils.changeHighLight(charSequenceChangeHighLight, str);
        }
        if (!TextUtils.isEmpty(title2)) {
            title2 = NoteApp.getInstance().getResources().getString(R.string.pad_note_list_not_text_stub);
        } else {
            title2 = NoteApp.getInstance().getResources().getString(R.string.pad_note_list_not_text_stub);
        }
        this.mTitle.setText(title2);
        this.mContent.setText(charSequenceChangeHighLight);
    }

    @Override // com.miui.notes.feature.notelist.CommonNoteBaseItem, com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        setBarMargin(z ? 1.0f : 0.0f);
        return super.onUpdateEditable(z, z2);
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationUpdate(boolean z, float f) {
        super.onAnimationUpdate(z, f);
        if (!z) {
            f = 1.0f - f;
        }
        setBarMargin(f);
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
        if (!z) {
            setBarMargin(0.0f);
        } else {
            setBarMargin(1.0f);
        }
        super.onAnimationStop(z);
    }

    private void setBarMargin(float f) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.bar.getLayoutParams();
        layoutParams.setMarginEnd((int) ((this.mCheckboxInitMarginEnd + this.mCheckBoxWidth) * f));
        this.bar.setLayoutParams(layoutParams);
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        Logger.INSTANCE.d("CommonNoteGridItem", "onRecycled");
        super.onRecycled();
        releaseIoTask();
    }

    private void releaseIoTask() {
        BitmapTask bitmapTask = this.mThumbnailLoadTask;
        if (bitmapTask != null && bitmapTask.isRunning()) {
            this.mThumbnailLoadTask.cancel();
        }
        if (this.mLoadImageContentTask == null || this.mNoteContent == null) {
            return;
        }
        this.mNoteContent.removeCallbacks(this.mLoadImageContentTask);
        this.mLoadImageContentTask.destroy();
        this.mLoadImageContentTask = null;
    }

    private static class LoadImageContentTask implements Runnable {
        private AsyncLoadTheme asyncLoadTheme;
        private final WeakReference<Theme> themeWeakReference;
        private final WeakReference<ViewGroup> viewGroupWeakReference;

        public LoadImageContentTask(ViewGroup viewGroup, Theme theme) {
            this.viewGroupWeakReference = new WeakReference<>(viewGroup);
            this.themeWeakReference = new WeakReference<>(theme);
        }

        @Override // java.lang.Runnable
        public void run() {
            AsyncLoadTheme asyncLoadTheme = new AsyncLoadTheme(this.viewGroupWeakReference, this.themeWeakReference);
            this.asyncLoadTheme = asyncLoadTheme;
            asyncLoadTheme.load();
        }

        public void destroy() {
            AsyncLoadTheme asyncLoadTheme = this.asyncLoadTheme;
            if (asyncLoadTheme != null) {
                asyncLoadTheme.destroy();
            }
        }
    }
}
