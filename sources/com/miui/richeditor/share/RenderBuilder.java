package com.miui.richeditor.share;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.handwritecommon.api.HandViewProvider;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.theme.ArtyTheme;
import com.miui.notes.theme.DefaultTheme;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.util.ColorMatrixHelper;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.share.element.AudioElement;
import com.miui.richeditor.share.element.Element;
import com.miui.richeditor.share.element.ImageElement;
import com.miui.richeditor.share.element.LineElement;
import com.miui.richeditor.share.element.LinkCardElement;
import com.miui.richeditor.share.element.ListElement;
import com.miui.richeditor.share.element.ScaledTextElement;
import com.miui.richeditor.share.element.TextElement;
import com.miui.richeditor.share.render.CommonRender;
import com.miui.richeditor.share.render.MamlViewRender;
import com.miui.richeditor.share.render.Render;
import com.miui.richeditor.style.LinkCardInfo;
import com.miui.richeditor.style.LinkCardShareSpan;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.theme.Theme;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class RenderBuilder {
    private static final String TAG = "RenderBuilder";
    protected int mCheckRes;
    protected RenderConfig mConfig;
    protected String mContent;
    protected Context mContext;
    protected Uri mShareUri;
    protected Theme.ShareStyle mStyle;
    protected long mThemeId;
    protected String mTitle;
    protected int mUnCheckRes;

    /* JADX INFO: Access modifiers changed from: private */
    public static int setAlpha(float f, int i) {
        return (((int) (f * 255.0f)) << 24) | (i & 16777215);
    }

    public RenderBuilder(Context context) {
        Logger.INSTANCE.d(TAG, TAG);
        this.mContext = context;
        this.mConfig = createConfig(context);
    }

    protected RenderConfig createConfig(Context context) {
        return RenderConfig.getConfig(context);
    }

    public RenderBuilder setTitle(String str) {
        if (TextUtils.isEmpty(str) && AudioUtils.hasAudioLabel(this.mContent)) {
            this.mTitle = NoteApp.getInstance().getResources().getString(R.string.snippet_audio_stub);
            return this;
        }
        this.mTitle = str;
        return this;
    }

    public RenderBuilder setContent(String str) {
        this.mContent = str;
        return this;
    }

    public RenderBuilder setCheckRes(int i) {
        this.mCheckRes = i;
        return this;
    }

    public RenderBuilder setUnCheckRes(int i) {
        this.mUnCheckRes = i;
        return this;
    }

    public RenderBuilder setThemeId(long j) {
        this.mThemeId = j;
        return this;
    }

    public RenderBuilder setStyle(Theme.ShareStyle shareStyle) {
        this.mStyle = shareStyle;
        return this;
    }

    public Render build() {
        return new CommonRender(this.mContext, parse(false), this.mStyle.mBackground, this.mConfig.getWidth(), this.mThemeId);
    }

    public Render buildHandWrite(List<Uri> list, int i, String str) {
        Drawable colorDrawable;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            arrayList.add(new ImageElement.Builder().setRadius(0).setUri(list.get(i2)).build());
        }
        ContextCompat.getColor(this.mContext, i == HandViewProvider.getSinglePageBlackBg() ? R.color.phone_hand_write_share_title_color_dark : R.color.phone_hand_write_share_title_color_normal);
        ContextCompat.getColor(this.mContext, i == HandViewProvider.getSinglePageBlackBg() ? R.color.phone_hand_write_share_time_color_dark : R.color.phone_hand_write_share_time_color_normal);
        this.mContext.getResources().getDimension(R.dimen.phone_hand_write_share_text_horizontal_padding);
        if (i == 2) {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mContext, R.color.singlepage_bg_black));
        } else if (i == 0) {
            colorDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.singlepage_doodle_canvas_bg);
        } else {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mContext, R.color.singlepage_bg_white));
        }
        CommonRender commonRender = new CommonRender(this.mContext, arrayList, colorDrawable, this.mConfig.getWidth(), this.mThemeId);
        commonRender.setStrokeStyle(ContextCompat.getColor(this.mContext, R.color.phone_hand_write_share_stroke_color), this.mContext.getResources().getDimensionPixelSize(R.dimen.phone_hand_write_share_stroke_width));
        return commonRender;
    }

    protected void addHandWriteTitleElements(List<Element> list, String str, int i, int i2, int i3) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        list.add(0, new TextElement.Builder().setFontSize(this.mContext.getResources().getDimensionPixelSize(R.dimen.phone_hand_write_share_title_text_size)).setFontColor(i).setBold(true).setText(str).setMarginLeft(i2).setMarginRight(i3).setMarginBottom(this.mContext.getResources().getDimensionPixelOffset(R.dimen.phone_hand_write_share_title_margin_bottom)).setGravity(1).build());
    }

    protected void addHandWriteTimeElements(List<Element> list, String str, int i, int i2, int i3) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        list.add(0, new TextElement.Builder().setFontSize(this.mContext.getResources().getDimensionPixelSize(R.dimen.phone_hand_write_share_time_text_size)).setFontColor(i).setBold(false).setText(str).setMarginLeft(i2).setMarginRight(i3).setMarginTop(this.mContext.getResources().getDimensionPixelOffset(R.dimen.phone_hand_write_share_time_margin_top)).setMarginBottom(this.mContext.getResources().getDimensionPixelOffset(R.dimen.phone_hand_write_share_time_margin_bottom)).setGravity(1).build());
    }

    protected void addAudioElements(List<Element> list, Uri uri, File file, int i, int i2) {
        list.add(new AudioElement.Builder().setUri(uri).setAudioFile(file).setMarginLeft(i).setMarginRight(i2).setMarginBottom((int) this.mContext.getResources().getDimension(R.dimen.audio_share_margin_bottom)).setMarginTop((int) this.mContext.getResources().getDimension(R.dimen.audio_share_margin_top)).build());
    }

    public Render buildFromUri(Uri uri) {
        this.mShareUri = uri;
        ImageElement imageElementBuild = new ImageElement.Builder().setUri(this.mShareUri).build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(imageElementBuild);
        if (RomUtils.isPadMode() && !TextUtils.isEmpty(this.mTitle)) {
            Rect rect = new Rect();
            if (this.mStyle.mBackground != null) {
                this.mStyle.mBackground.getPadding(rect);
            }
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_editor_padding);
            rect.left += dimensionPixelSize;
            rect.right += dimensionPixelSize;
            addHeaderElements(arrayList, this.mTitle, this.mContext.getResources().getColor(this.mStyle.mTextColor), rect.left, rect.right);
        }
        Context context = this.mContext;
        return new CommonRender(context, arrayList, context.getResources().getDrawable(R.drawable.v12_mind_share_bg), this.mConfig.getWidth(), this.mThemeId);
    }

    public Render buildFromHybrid(Uri uri) {
        CommonRender commonRender = new CommonRender(this.mContext, parseHybrid(uri), this.mStyle.mBackground, this.mConfig.getWidth(), this.mThemeId);
        commonRender.setStrokeStyle(ContextCompat.getColor(this.mContext, R.color.phone_hand_write_share_stroke_color), this.mContext.getResources().getDimensionPixelSize(R.dimen.phone_hand_write_share_stroke_width));
        return commonRender;
    }

    public Render buildWithCallback(View view, Runnable runnable) {
        return new MamlViewRender(this.mContext, parse(false), this.mStyle.mBackground, view, this.mConfig.getWidth(), runnable, this.mThemeId);
    }

    public Render buildFromHybridWithCallback(Uri uri, View view, Runnable runnable) {
        return new MamlViewRender(this.mContext, parseHybrid(uri), this.mStyle.mBackground, view, this.mConfig.getWidth(), runnable, this.mThemeId);
    }

    private List<Element> parseHybrid(Uri uri) {
        Rect rect = new Rect();
        if (this.mStyle.mBackground != null) {
            this.mStyle.mBackground.getPadding(rect);
        }
        if (this.mThemeId == 0) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_share_theme_default_text_padding);
            rect.left += dimensionPixelSize;
            rect.right += dimensionPixelSize;
        }
        ImageElement imageElementBuild = new ImageElement.Builder().setMarginLeft(rect.left).setMarginRight(rect.right).setUri(uri).build();
        if (this.mThemeId == 0) {
            imageElementBuild.edit().setMarginTop(this.mContext.getResources().getDimensionPixelSize(R.dimen.default_theme_hybrid_margin_top)).apply();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(imageElementBuild);
        addFooterElements(arrayList, this.mStyle, this.mThemeId);
        return arrayList;
    }

    private List<Element> parse(boolean z) {
        int dimensionPixelSize;
        Uri uri;
        Rect rect = new Rect();
        if (this.mStyle.mBackground != null) {
            this.mStyle.mBackground.getPadding(rect);
        }
        if (this.mThemeId == 0) {
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_share_theme_default_text_padding);
        } else {
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_editor_padding);
        }
        rect.left += dimensionPixelSize;
        rect.right += dimensionPixelSize;
        int color = this.mContext.getResources().getColor(this.mStyle.mTextColor);
        MyMediaHandler myMediaHandler = new MyMediaHandler(this.mContext, this.mThemeId, this.mCheckRes, this.mUnCheckRes, this.mConfig.getWidth(), rect.left, rect.right, setAlpha(0.9f, color), this.mStyle.mTextGravity == 1 ? 3 : 4, this.mStyle.mIconColor);
        NoteSchema.parseAncient(this.mContent, myMediaHandler, this.mContext);
        if (z && (uri = this.mShareUri) != null) {
            return myMediaHandler.getMindElements(uri);
        }
        ArrayList<Element> elements = myMediaHandler.getElements();
        int size = elements.size();
        if (size > 0) {
            elements.get(elements.size() - 1).edit().setMarginBottom(0).apply();
            if (this.mThemeId == 0 && TextUtils.isEmpty(this.mTitle)) {
                elements.get(0).edit().setMarginBottom(1).setMarginTop(20).apply();
            }
        }
        for (int i = 1; i < size; i++) {
            Element element = elements.get(i);
            if (!myMediaHandler.isEmptyElement(element) && !(element instanceof ImageElement) && !(element instanceof LinkCardElement)) {
                element.edit().setMarginTop(this.mConfig.getParagraphSpacing()).apply();
            }
        }
        addHeaderElements(elements, this.mTitle, color, rect.left, rect.right);
        addFooterElements(elements, this.mStyle, this.mThemeId);
        return elements;
    }

    private void addHeaderElements(List<Element> list, String str, int i, int i2, int i3) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        list.add(0, new ScaledTextElement.Builder().setFontSize(this.mConfig.getMaxTitleTextSize()).setMinFontSize(this.mConfig.getMaxTitleTextSize()).setFontColor(i).setBold(false).setIsTitle(true).setText((CharSequence) str).setLineGap(this.mConfig.getTitleLineGap()).setMarginLeft(i2).setMarginRight(i3).setMarginBottom(this.mConfig.getHeaderMarginBottom()).setMarginTop(this.mStyle.mHeaderMarginTop).setGravity(1).build());
    }

    private void addFooterElements(List<Element> list, Theme.ShareStyle shareStyle, long j) {
        if (shareStyle.mFooterMarginTop < 0) {
            return;
        }
        int color = this.mContext.getResources().getColor(shareStyle.mTextColor);
        int color2 = this.mContext.getResources().getColor(shareStyle.mTextColor);
        int alpha = setAlpha(0.6f, color);
        int alpha2 = setAlpha(0.25f, color2);
        if (shareStyle.mShowFooterDivider) {
            list.add(new LineElement.Builder().setColor(alpha2).setStrokeWidth(1).setMarginLeft(this.mConfig.getFooterDividerMarginHorizontal()).setMarginTop(shareStyle.mFooterLineMarginTop).setMarginRight(this.mConfig.getFooterDividerMarginHorizontal()).build());
        }
        list.add(new TextElement.Builder().setText(this.mContext.getString(R.string.sns_footer_text)).setFontSize(this.mConfig.getFooterTextSize()).setFontColor(alpha).setMarginTop(shareStyle.mFooterMarginTop).setMarginBottom(shareStyle.mFooterMarginBottom).setGravity(3).build());
    }

    private class MyMediaHandler implements HtmlParser.RichTextMediaHandler {
        private int iCheckedBitmapRes;
        private Context iContext;
        private int iIconColor;
        private int iIndent;
        private Drawable iInputDrawable;
        private boolean iIsBullet;
        private boolean iIsChecked;
        private boolean iIsInQuote;
        private boolean iIsListOutQuote;
        private boolean iIsOrder;
        private boolean iIsQuote;
        private boolean iIsSplitLine;
        private int iPaddingLeft;
        private int iPaddingRight;
        private SpannableStringBuilder iTextBuilder;
        private int iTextColor;
        private int iTextGravity;
        private long iThemeId;
        private int iUncheckedBitmapRes;
        private int iWidth;
        private boolean isLastHandleLinkCard;
        private boolean isOutQuote;
        private int preIndent;
        private int sharePicVerticalMargin;
        private Map<Integer, Integer> orderMap = new HashMap();
        private final int indentMax = 6;
        private ArrayList<Element> iElements = new ArrayList<>();
        private ArrayList<Element> iEmptyElements = new ArrayList<>();
        private ArrayList<Integer> iTextIndents = new ArrayList<>();
        private int getIndex = 0;
        private boolean preIsOrder = false;

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleMiui(SpannableStringBuilder spannableStringBuilder, HtmlParser.MiuiElement miuiElement) {
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleTable(SpannableStringBuilder spannableStringBuilder, HtmlParser.TableElement tableElement) {
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public void handleTag(SpannableStringBuilder spannableStringBuilder, String str, boolean z) {
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleVideo(SpannableStringBuilder spannableStringBuilder, HtmlParser.VideoElement videoElement) {
            return null;
        }

        public MyMediaHandler(Context context, long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.iContext = context;
            this.iThemeId = j;
            this.iCheckedBitmapRes = i;
            this.iUncheckedBitmapRes = i2;
            this.iWidth = i3;
            this.iPaddingLeft = i4;
            this.iPaddingRight = i5;
            this.iTextColor = i6;
            this.iTextGravity = i7;
            this.iIconColor = i8;
            this.sharePicVerticalMargin = context.getResources().getDimensionPixelSize(R.dimen.image_content_margin_bottom);
            initOrderMap();
        }

        private void initOrderMap() {
            for (int i = 0; i <= 6; i++) {
                this.orderMap.put(Integer.valueOf(i), 0);
            }
        }

        private void initAfterMap(int i) {
            while (i <= 6) {
                this.orderMap.put(Integer.valueOf(i), 0);
                i++;
            }
        }

        private int getOrderMapValue() {
            int i = this.iIndent;
            if (i <= 0 || i > 6) {
                return 1;
            }
            if (this.orderMap.get(Integer.valueOf(i)).intValue() == 0) {
                this.orderMap.put(Integer.valueOf(this.iIndent), 1);
            }
            return this.orderMap.get(Integer.valueOf(this.iIndent)).intValue();
        }

        private int getTextIndent() {
            try {
                return this.iTextIndents.get(this.getIndex).intValue() - 1;
            } catch (Exception unused) {
                return 0;
            }
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleInput(SpannableStringBuilder spannableStringBuilder, HtmlParser.InputElement inputElement, int i) {
            if (!"checkbox".equals(inputElement.getType())) {
                return null;
            }
            commitText();
            HtmlParser.CheckBoxElement checkBoxElement = (HtmlParser.CheckBoxElement) inputElement;
            Theme theme = ResourceManager.getTheme((int) this.iThemeId);
            if (checkBoxElement.isChecked()) {
                if (theme instanceof DefaultTheme) {
                    this.iInputDrawable = Theme.getThemeDrawable(this.iCheckedBitmapRes, new ArtyTheme((int) this.iThemeId).getResTheme(this.iContext));
                } else {
                    this.iInputDrawable = Theme.getThemeDrawable(this.iCheckedBitmapRes, theme.getResTheme(this.iContext));
                }
            } else {
                this.iInputDrawable = Theme.getThemeDrawable(this.iUncheckedBitmapRes, theme.getResTheme(this.iContext));
            }
            Drawable drawable = this.iInputDrawable;
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.iInputDrawable.getIntrinsicHeight());
            this.iIsChecked = checkBoxElement.isChecked();
            this.iIndent = i;
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleBullet(SpannableStringBuilder spannableStringBuilder, HtmlParser.BulletElement bulletElement, int i) {
            commitText();
            Drawable drawableSelectShareBulletDrawable = selectShareBulletDrawable();
            this.iInputDrawable = drawableSelectShareBulletDrawable;
            drawableSelectShareBulletDrawable.setBounds(0, 0, drawableSelectShareBulletDrawable.getIntrinsicWidth(), this.iInputDrawable.getIntrinsicHeight());
            this.iIsBullet = true;
            this.iIndent = i;
            return null;
        }

        private Drawable selectShareBulletDrawable() {
            Drawable filterDrawable = getFilterDrawable(this.iContext.getResources().getDrawable(R.drawable.ic_richtext_share_bullet));
            filterDrawable.setTint(RenderBuilder.setAlpha(0.9f, RenderBuilder.this.mContext.getResources().getColor(RenderBuilder.this.mStyle.mTextColor)));
            return filterDrawable;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleOrder(SpannableStringBuilder spannableStringBuilder, HtmlParser.OrderElement orderElement, int i, int i2) {
            commitText();
            this.iIndent = i;
            if (this.preIsOrder && !this.isOutQuote) {
                this.orderMap.put(Integer.valueOf(i), Integer.valueOf(this.orderMap.get(Integer.valueOf(i)).intValue() + 1));
                int i3 = this.iIndent;
                if (i3 < this.preIndent) {
                    initAfterMap(i3 + 1);
                }
            } else if (i2 != 0) {
                this.orderMap.put(Integer.valueOf(i), Integer.valueOf(i2));
                if (this.isOutQuote) {
                    this.isOutQuote = false;
                }
            } else {
                this.isOutQuote = false;
                initOrderMap();
            }
            this.preIndent = i;
            this.iIsOrder = true;
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleQuote(SpannableStringBuilder spannableStringBuilder, HtmlParser.QuoteElement quoteElement) {
            commitText();
            Drawable drawableSelectShareQuoteDrawable = selectShareQuoteDrawable();
            this.iInputDrawable = drawableSelectShareQuoteDrawable;
            drawableSelectShareQuoteDrawable.setBounds(0, 0, drawableSelectShareQuoteDrawable.getIntrinsicWidth(), this.iInputDrawable.getIntrinsicHeight());
            this.iIsListOutQuote = false;
            this.iIsInQuote = true;
            this.iIsQuote = true;
            this.isOutQuote = false;
            return null;
        }

        private Drawable selectShareQuoteDrawable() {
            Drawable filterDrawable = getFilterDrawable(this.iContext.getResources().getDrawable(R.drawable.ic_richtext_share_quote));
            filterDrawable.setTint(RenderBuilder.setAlpha(0.5f, RenderBuilder.this.mContext.getResources().getColor(RenderBuilder.this.mStyle.mTextColor)));
            return filterDrawable;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleSplitLine(SpannableStringBuilder spannableStringBuilder, HtmlParser.SplitLineElement splitLineElement) {
            commitText();
            Drawable drawableSelectShareSplitLineDrawable = selectShareSplitLineDrawable();
            this.iInputDrawable = drawableSelectShareSplitLineDrawable;
            drawableSelectShareSplitLineDrawable.setBounds(0, 0, drawableSelectShareSplitLineDrawable.getIntrinsicWidth(), this.iInputDrawable.getIntrinsicHeight());
            this.iIsSplitLine = true;
            return null;
        }

        private Drawable selectShareSplitLineDrawable() {
            Drawable filterDrawable = getFilterDrawable(this.iContext.getResources().getDrawable(R.drawable.ic_richtext_share_splitline));
            filterDrawable.setTint(RenderBuilder.setAlpha(0.5f, RenderBuilder.this.mContext.getResources().getColor(RenderBuilder.this.mStyle.mTextColor)));
            return filterDrawable;
        }

        private Drawable getFilterDrawable(Drawable drawable) {
            if (this.iIconColor == 0) {
                return drawable;
            }
            ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(ColorMatrixHelper.colorToMatrix(this.iContext.getResources().getColor(this.iIconColor)));
            Drawable drawableMutate = drawable.mutate();
            drawableMutate.setColorFilter(colorMatrixColorFilter);
            return drawableMutate;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleImage(SpannableStringBuilder spannableStringBuilder, HtmlParser.ImageElement imageElement) {
            commitText();
            File file = new File(AttachmentUtils.getAttachmentPath(this.iContext, imageElement.getFileId()));
            if (!file.exists()) {
                return null;
            }
            this.iElements.add(new ImageElement.Builder().setRadius(RenderBuilder.this.mConfig.getImageRadius()).setUri(Uri.fromFile(file)).setAnnotation(imageElement.getImgDes()).setIsShowBig(!"1".equals(imageElement.getImgShow())).setFontColor(RenderBuilder.this.mContext.getResources().getColor(RenderBuilder.this.mStyle.mTextColor, null)).setMarginLeft(this.iPaddingLeft).setMarginRight(this.iPaddingRight).setMarginBottom(0).setMarginTop(this.sharePicVerticalMargin).build());
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleLinkCard(SpannableStringBuilder spannableStringBuilder, HtmlParser.LinkCardElement linkCardElement, boolean z) {
            if (!z) {
                return null;
            }
            commitText();
            LinkCardShareSpan linkCardShareSpan = new LinkCardShareSpan(linkCardElement);
            LinkCardElement linkCardElementBuild = new LinkCardElement.Builder().setInfo(new LinkCardInfo(linkCardElement.getLink(), linkCardElement.getTitle(), linkCardElement.getContent(), "")).setMarginLeft(this.iPaddingLeft).setMarginRight(this.iPaddingRight).setMarginTop(this.sharePicVerticalMargin).setRadius(RenderBuilder.this.mConfig.getLinkCardRadius()).build();
            linkCardShareSpan.setShareElement(linkCardElementBuild);
            this.iElements.add(linkCardElementBuild);
            this.isLastHandleLinkCard = true;
            return linkCardShareSpan;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleDoodle(SpannableStringBuilder spannableStringBuilder, HtmlParser.MiDoodleElement miDoodleElement) {
            commitText();
            File file = new File(AttachmentUtils.getDoodleThumbnailPath(this.iContext, miDoodleElement.getFileId()));
            if (!file.exists()) {
                return null;
            }
            this.iElements.add(new ImageElement.Builder().setUri(Uri.fromFile(file)).setMarginLeft(this.iPaddingLeft).setMarginRight(this.iPaddingRight).setMarginBottom(this.sharePicVerticalMargin).setMarginTop(this.sharePicVerticalMargin).build());
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleAudio(SpannableStringBuilder spannableStringBuilder, HtmlParser.SoundElement soundElement) {
            commitText();
            Uri uri = Uri.parse("android.resource://" + RenderBuilder.this.mContext.getPackageName() + "/drawable/" + selectShareAudioDrawable());
            if (AudioUtils.isSupportAIRecorder()) {
                String attachmentPath = AttachmentUtils.getAttachmentPath(this.iContext, soundElement.getFileId());
                if (attachmentPath == null) {
                    return null;
                }
                File file = new File(attachmentPath);
                if (!file.exists()) {
                    return null;
                }
                RenderBuilder.this.addAudioElements(this.iElements, uri, file, this.iPaddingLeft, this.iPaddingRight);
            } else {
                this.iElements.add(new ImageElement.Builder().setUri(uri).setMarginLeft(this.iPaddingLeft).setMarginRight(this.iPaddingRight).setMarginBottom(12).setMarginTop(50).setIsScaled(false).build());
            }
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:8:0x0019  */
        private String selectShareAudioDrawable() {
            StringBuilder sb = new StringBuilder();
            int i = (int) this.iThemeId;
            if (i == 6 || i == 9) {
                sb.append("v12_icon_share_audio_dark");
            } else {
                switch (i) {
                    case 12:
                    case 13:
                    case 14:
                        sb.append("v12_icon_share_audio_dark");
                        break;
                    default:
                        sb.append("v12_icon_share_audio");
                        break;
                }
            }
            return sb.toString();
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public HtmlParser.IElementSpan handleContact(SpannableStringBuilder spannableStringBuilder, HtmlParser.ContactElement contactElement) {
            appendText(contactElement.getName());
            return null;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public boolean handleText(SpannableStringBuilder spannableStringBuilder, String str) {
            appendText(str);
            return false;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public void handleUpdateIndent(int i) {
            this.iTextIndents.add(Integer.valueOf(i));
        }

        @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
        public void handleEndQuote() {
            this.isOutQuote = true;
            this.iIsListOutQuote = true;
            this.iIsInQuote = false;
        }

        public ArrayList<Element> getElements() {
            commitText();
            return this.iElements;
        }

        public ArrayList<Element> getMindElements(Uri uri) {
            this.iElements.add(new ImageElement.Builder().setUri(uri).setMarginLeft(this.iPaddingLeft).setMarginRight(this.iPaddingRight).setMarginBottom(this.sharePicVerticalMargin).setMarginTop(this.sharePicVerticalMargin).build());
            return this.iElements;
        }

        public boolean isEmptyElement(Element element) {
            return this.iEmptyElements.contains(element);
        }

        private void appendText(CharSequence charSequence) {
            if (charSequence == null) {
                return;
            }
            if (this.iTextBuilder == null) {
                this.iTextBuilder = new SpannableStringBuilder();
            }
            if (charSequence != null) {
                this.iTextBuilder.append(charSequence);
            }
        }

        /* JADX WARN: Type inference failed for: r2v9, types: [android.graphics.drawable.Drawable, android.text.SpannableStringBuilder] */
        private void commitText() {
            int i;
            int i2;
            Element listElement;
            int i3;
            String str;
            int i4;
            int i5;
            SpannableStringBuilder spannableStringBuilder = this.iTextBuilder;
            Drawable drawable = null;
            boolean z = true;
            if (spannableStringBuilder != null) {
                String string = spannableStringBuilder.toString();
                int i6 = 1;
                int i7 = 0;
                while (true) {
                    i = 10;
                    if (i7 >= string.length()) {
                        break;
                    }
                    if (string.charAt(i7) == '\n') {
                        i6++;
                    }
                    i7++;
                }
                int i8 = 0;
                int i9 = 0;
                while (i8 < string.length()) {
                    int iIndexOf = string.indexOf(i, i8);
                    if (iIndexOf < 0) {
                        iIndexOf = string.length();
                    }
                    int i10 = i9 + (z ? 1 : 0);
                    boolean z2 = i10 == i6 ? z ? 1 : 0 : false;
                    SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(StringUtils.SPACE);
                    if (!TextUtils.isEmpty(this.iTextBuilder)) {
                        spannableStringBuilder2 = (SpannableStringBuilder) this.iTextBuilder.subSequence(i8, iIndexOf);
                    }
                    if (TextUtils.isEmpty(spannableStringBuilder2)) {
                        if (i8 == 0 && !this.iElements.isEmpty()) {
                            ArrayList<Element> arrayList = this.iElements;
                            if (arrayList.get(arrayList.size() - (z ? 1 : 0)).getType() != z) {
                                ArrayList<Element> arrayList2 = this.iElements;
                                if (arrayList2.get(arrayList2.size() - (z ? 1 : 0)).getType() != 5) {
                                    ArrayList<Element> arrayList3 = this.iElements;
                                    if (arrayList3.get(arrayList3.size() - (z ? 1 : 0)).getType() == 6) {
                                    }
                                }
                            }
                            int i11 = iIndexOf + 1;
                            if (this.iInputDrawable != null || this.iIsOrder) {
                                i3 = i;
                                str = string;
                                i4 = i6;
                                i5 = i10;
                                ListElement listElement2 = getListElement(new SpannableStringBuilder(StringUtils.SPACE), this.iIsChecked, this.iIsBullet, this.iIsQuote, this.iIsListOutQuote, this.iIsInQuote, this.iIsOrder, this.iIsSplitLine, this.iPaddingLeft, this.iPaddingRight, this.iIndent, getOrderMapValue());
                                this.iIsBullet = false;
                                this.iIsQuote = false;
                                this.iIsListOutQuote = false;
                                this.iIsSplitLine = false;
                                this.iIsChecked = false;
                                if (this.iIsOrder) {
                                    this.preIsOrder = z;
                                } else {
                                    this.preIsOrder = false;
                                }
                                this.iIsOrder = false;
                                this.iInputDrawable = drawable;
                                this.iElements.add(listElement2);
                            } else {
                                str = string;
                                i4 = i6;
                                i5 = i10;
                                i3 = i;
                            }
                            i8 = i11;
                            i = i3;
                            string = str;
                            i6 = i4;
                            i9 = i5;
                        }
                        spannableStringBuilder2 = new SpannableStringBuilder(StringUtils.SPACE);
                    }
                    SpannableStringBuilder spannableStringBuilder3 = spannableStringBuilder2;
                    boolean zIsEmpty = spannableStringBuilder3.toString().trim().isEmpty();
                    if (this.iInputDrawable != null || this.iIsOrder) {
                        boolean z3 = this.iIsQuote;
                        if (z3) {
                            i2 = iIndexOf;
                            this.iElements.add(getListElement(new SpannableStringBuilder(StringUtils.SPACE), this.iIsChecked, this.iIsBullet, this.iIsQuote, this.iIsListOutQuote, this.iIsInQuote, this.iIsOrder, this.iIsSplitLine, this.iPaddingLeft, this.iPaddingRight, this.iIndent, getOrderMapValue()));
                            listElement = getTextElement(spannableStringBuilder3, this.iPaddingLeft + (getIndentMargin(this.iPaddingRight) * getTextIndent()) + getQuoteMargin(), this.iPaddingRight, z2);
                            this.getIndex++;
                        } else {
                            i2 = iIndexOf;
                            listElement = getListElement(spannableStringBuilder3, this.iIsChecked, this.iIsBullet, z3, this.iIsListOutQuote, this.iIsInQuote, this.iIsOrder, this.iIsSplitLine, this.iPaddingLeft, this.iPaddingRight, this.iIndent, getOrderMapValue());
                        }
                        this.iIsBullet = false;
                        this.iIsQuote = false;
                        this.iIsListOutQuote = false;
                        this.iIsSplitLine = false;
                        if (this.iIsOrder) {
                            this.preIsOrder = z ? 1 : 0;
                        } else {
                            this.preIsOrder = false;
                        }
                        this.iIsOrder = false;
                        this.iIsChecked = false;
                        this.iInputDrawable = null;
                        zIsEmpty = false;
                    } else {
                        int indentMargin = this.iPaddingLeft + (getIndentMargin(this.iPaddingRight) * getTextIndent());
                        if (((EditorQuoteLiteSpan[]) spannableStringBuilder3.getSpans(0, spannableStringBuilder3.length(), EditorQuoteLiteSpan.class)).length > 0) {
                            indentMargin += getQuoteMargin();
                        }
                        listElement = getTextElement(spannableStringBuilder3, indentMargin, this.iPaddingRight, z2);
                        this.getIndex += z ? 1 : 0;
                        this.preIsOrder = false;
                        i2 = iIndexOf;
                    }
                    this.iElements.add(listElement);
                    if (zIsEmpty) {
                        this.iEmptyElements.add(listElement);
                    }
                    i8 = i2 + 1;
                    i = i;
                    string = string;
                    i6 = i6;
                    i9 = i10;
                    drawable = null;
                    z = true;
                }
                ?? r2 = drawable;
                this.iTextBuilder = r2;
                this.iInputDrawable = r2;
                return;
            }
            if (this.iInputDrawable != null || this.iIsOrder) {
                ListElement listElement3 = getListElement(new SpannableStringBuilder(), this.iIsChecked, this.iIsBullet, this.iIsQuote, this.iIsListOutQuote, this.iIsInQuote, this.iIsOrder, this.iIsSplitLine, this.iPaddingLeft, this.iPaddingRight, this.iIndent, getOrderMapValue());
                this.iIsBullet = false;
                this.iIsQuote = false;
                this.iIsListOutQuote = false;
                if (this.iIsOrder) {
                    this.preIsOrder = true;
                } else {
                    this.preIsOrder = false;
                }
                this.iIsOrder = false;
                this.iIsSplitLine = false;
                this.iIsChecked = false;
                this.iElements.add(listElement3);
                this.iInputDrawable = null;
            }
        }

        private TextElement getTextElement(SpannableStringBuilder spannableStringBuilder, int i, int i2) {
            return getTextElement(spannableStringBuilder, i, i2, false);
        }

        private TextElement getTextElement(SpannableStringBuilder spannableStringBuilder, int i, int i2, boolean z) {
            Context context;
            int i3;
            if (RomUtils.isInternationalBuild()) {
                context = RenderBuilder.this.mContext;
                i3 = R.string.ai_content_generates_reminders;
            } else {
                context = RenderBuilder.this.mContext;
                i3 = R.string.ai_generate_tip;
            }
            return new TextElement.Builder().setFontSize(RenderBuilder.this.mConfig.getContentTextSize()).setFontColor(this.iTextColor).setBold(false).setMarginLeft(i).setMarginRight(i2).setLineGap(RenderBuilder.this.mConfig.getContentLineGap()).setText(applyAiTextStyle(spannableStringBuilder, context.getString(i3), z)).setGravity(this.iTextGravity).build();
        }

        private SpannableStringBuilder applyAiTextStyle(SpannableStringBuilder spannableStringBuilder, String str, boolean z) {
            int iIndexOf;
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(spannableStringBuilder);
            if (z && (iIndexOf = spannableStringBuilder2.toString().indexOf(str)) != -1) {
                int length = str.length() + iIndexOf;
                spannableStringBuilder2.setSpan(new RelativeSizeSpan(0.8f), iIndexOf, length, 33);
                spannableStringBuilder2.setSpan(new ForegroundColorSpan(Color.argb(102, 0, 0, 0)), iIndexOf, length, 33);
            }
            return spannableStringBuilder2;
        }

        private ListElement getListElement(SpannableStringBuilder spannableStringBuilder, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i, int i2, int i3, int i4) {
            return new ListElement.Builder().setTextElement(getTextElement(spannableStringBuilder, 0, 0)).setIsChecked(z).setListIcon(this.iInputDrawable).setIsBullet(z2).setIndent(i3).setIsOrder(z6).setOrderNum(i4).setIsQuote(z3).setIsInQuote(z5).setIsLIstOutQuote(z4).setIsSplitLine(z7).setMarginLeft(i).setMarginRight(i2).build();
        }

        private int getIndentMargin(int i) {
            int dimensionPixelSize = this.iContext.getResources().getDimensionPixelSize(R.dimen.indent_share_margin);
            return i > 250 ? dimensionPixelSize / 2 : dimensionPixelSize;
        }

        private int getQuoteMargin() {
            return (int) ((RenderBuilder.this.mContext.getResources().getDimensionPixelSize(R.dimen.share_quote_margin_right) * RenderBuilder.this.mContext.getResources().getDisplayMetrics().scaledDensity) / RenderBuilder.this.mContext.getResources().getDisplayMetrics().density);
        }

        @Override // com.miui.richeditor.schema.HtmlParser.RichTextMediaHandler
        public boolean handleRichText(SpannableStringBuilder spannableStringBuilder) {
            if (this.isLastHandleLinkCard) {
                this.isLastHandleLinkCard = false;
                return true;
            }
            appendText(spannableStringBuilder);
            return false;
        }
    }
}
