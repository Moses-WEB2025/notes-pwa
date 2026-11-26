package com.miui.floatwindow.feature.note;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.component.MindThumbnailView;
import com.miui.notes.component.OverviewThumbnailView;
import com.miui.notes.component.PreviewImageView;
import com.miui.notes.excerpt.ExcerptInfo;
import com.miui.notes.excerpt.LinkUtils;
import com.miui.notes.model.MindDataHelper;
import com.miui.notes.model.MindEntity;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.thumbnail.Thumbnail;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.BitmapDecoder;
import com.miui.notes.tool.SnippetFormatter;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWritePathUtils;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.theme.Theme;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.base.AnimConfig;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: FloatNoteItem.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 T2\u00020\u0001:\u0002STB\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0005J\u000e\u0010,\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0005J\u000e\u0010-\u001a\u00020*2\u0006\u0010.\u001a\u00020\u0005J\u000e\u0010/\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005J\u000e\u00101\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005J\u000e\u00102\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005J\u0010\u00106\u001a\u00020*2\u0006\u00100\u001a\u00020\u0005H\u0002J\u0018\u00107\u001a\u00020*2\u0006\u00100\u001a\u00020\u00052\u0006\u00108\u001a\u000209H\u0016J \u0010:\u001a\u00020*2\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u0002042\u0006\u0010>\u001a\u00020\u0017H\u0002J\u0010\u0010?\u001a\u00020*2\u0006\u0010@\u001a\u000204H\u0002J \u0010A\u001a\u00020*2\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u0002042\u0006\u0010>\u001a\u00020\u0017H\u0002J\u0010\u0010B\u001a\u00020*2\u0006\u0010;\u001a\u00020<H\u0002J \u00107\u001a\u00020*2\u0006\u00100\u001a\u00020\u00052\u0006\u0010C\u001a\u0002042\u0006\u0010D\u001a\u00020EH\u0016J\u0010\u0010F\u001a\u00020*2\b\u0010G\u001a\u0004\u0018\u00010&J\u0010\u0010H\u001a\u00020*2\u0006\u0010I\u001a\u00020\u0017H\u0002J\u0010\u0010J\u001a\u00020*2\u0006\u0010K\u001a\u00020\u0017H\u0002J\u0010\u0010L\u001a\u00020*2\u0006\u0010;\u001a\u00020<H\u0002J\u0010\u0010M\u001a\u00020*2\u0006\u0010;\u001a\u00020<H\u0002J\u0010\u0010N\u001a\u00020*2\u0006\u0010O\u001a\u00020\u0017H\u0002J\u0010\u0010P\u001a\u00020\u00172\u0006\u0010Q\u001a\u00020\u0017H\u0004J\u0010\u0010R\u001a\u00020\u00172\u0006\u0010Q\u001a\u00020\u0017H\u0004R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u00103\u001a\u0002048F¢\u0006\u0006\u001a\u0004\b3\u00105¨\u0006U"}, d2 = {"Lcom/miui/floatwindow/feature/note/FloatNoteItem;", "Lcom/miui/floatwindow/feature/note/FloatBaseItem;", "mRootView", "Landroid/view/View;", "itemHeight", "", "contentHeight", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;II)V", "mFloatNoteType", "mTitle", "Landroid/widget/TextView;", "mSummary", "mTime", "mStick", "Landroid/widget/ImageView;", "mVoiceImage", "mTimeRegion", "mSaveView", "mTitleStyle", "mBodyStyle", "mBackgroundRes", "mContent", "", "getMContent", "()Ljava/lang/String;", "setMContent", "(Ljava/lang/String;)V", "mContext", "Landroid/content/Context;", "mImage", "Lcom/miui/notes/component/PreviewImageView;", "mOverviewThumbnail", "Lcom/miui/notes/component/OverviewThumbnailView;", "mMindThumbnail", "Lcom/miui/notes/component/MindThumbnailView;", "mLastImageRes", "mSaveListener", "Landroid/view/View$OnClickListener;", "mContentView", "mBindGeneration", "setTitleStyle", "", "styleId", "setBodyStyle", "setBackground", "resId", "getTitleStyleByType", "type", "getBodyStyleByType", "getBackgroundResByType", "isNeedFolme", "", "()Z", "initCard", "bind", "itemCache", "Lcom/miui/notes/cache/ItemCache;", "displayContentArea", "noteCache", "Lcom/miui/notes/cache/NoteCache;", "isMap", "mindContent", "updateSummaryMaxLines", "hasVoice", "displayMindThumbnail", "displayImage", "isSaving", Constants.JSON_FILTER_INFO, "Lcom/miui/notes/excerpt/ExcerptInfo;", "setOnSaveListener", "listener", "displayExcerptTitle", "titleText", "displayExcerptContent", "excerptContent", "displayTitle", "displayContent", "setImage", AITextWidgetStat.TYPE_IMAGE, "extractCleanContent", "plantText", "extractTitle", "PreviewTextSpan", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FloatNoteItem extends FloatBaseItem {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "FloatNoteItem";
    public static final int TYPE_CONTINUOUS_EXCERPT = 2;
    public static final int TYPE_NORMAL_NOTE = 0;
    public static final int TYPE_SINGLE_EXCERPT = 1;
    private int mBackgroundRes;
    private int mBindGeneration;
    private int mBodyStyle;
    private String mContent;
    private final View mContentView;
    private final Context mContext;
    private int mFloatNoteType;
    private final PreviewImageView mImage;
    private String mLastImageRes;
    private final MindThumbnailView mMindThumbnail;
    private final OverviewThumbnailView mOverviewThumbnail;
    private final View mRootView;
    private View.OnClickListener mSaveListener;
    private final TextView mSaveView;
    private final ImageView mStick;
    private final TextView mSummary;
    private final TextView mTime;
    private final View mTimeRegion;
    private final TextView mTitle;
    private int mTitleStyle;
    private final ImageView mVoiceImage;

    @JvmStatic
    public static final FloatNoteItem newInstance(ViewGroup viewGroup, int i, int i2) {
        return INSTANCE.newInstance(viewGroup, i, i2);
    }

    public final int getBackgroundResByType(int type) {
        if (type != 0) {
            return (type == 1 || type == 2) ? R.drawable.float_excerpt_note_background : R.drawable.float_note_background;
        }
        return R.drawable.float_note_background;
    }

    public final int getBodyStyleByType(int type) {
        if (type != 0) {
            return (type == 1 || type == 2) ? R.style.V12_Float_Excerpt_TextAppearance_List_Secondary : R.style.V12_Float_TextAppearance_List_Secondary;
        }
        return R.style.V12_Float_TextAppearance_List_Secondary;
    }

    public final int getTitleStyleByType(int type) {
        if (type != 0) {
            return (type == 1 || type == 2) ? R.style.V12_Float_Excerpt_TextAppearance_List_Primary : R.style.V12_TextAppearance_List_Primary;
        }
        return R.style.V12_TextAppearance_List_Primary;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FloatNoteItem(View mRootView, int i, int i2) {
        super(mRootView);
        Intrinsics.checkNotNullParameter(mRootView, "mRootView");
        this.mRootView = mRootView;
        this.mTitleStyle = R.style.V12_TextAppearance_List_Primary;
        this.mBodyStyle = R.style.V12_Float_TextAppearance_List_Secondary;
        this.mBackgroundRes = R.drawable.float_note_background;
        Context context = mRootView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.mContext = context;
        mRootView.setLongClickable(false);
        View viewFindViewById = mRootView.findViewById(R.id.float_note_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.mTitle = (TextView) viewFindViewById;
        View viewFindViewById2 = mRootView.findViewById(R.id.float_note_summary);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.mSummary = (TextView) viewFindViewById2;
        View viewFindViewById3 = mRootView.findViewById(R.id.image_content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.mImage = (PreviewImageView) viewFindViewById3;
        View viewFindViewById4 = mRootView.findViewById(R.id.overview_thumbnail);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        this.mOverviewThumbnail = (OverviewThumbnailView) viewFindViewById4;
        View viewFindViewById5 = mRootView.findViewById(R.id.mind_thumbnail);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        this.mMindThumbnail = (MindThumbnailView) viewFindViewById5;
        View viewFindViewById6 = mRootView.findViewById(R.id.voice_image);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
        this.mVoiceImage = (ImageView) viewFindViewById6;
        this.mTimeRegion = mRootView.findViewById(R.id.time_info_region);
        View viewFindViewById7 = mRootView.findViewById(R.id.last_time);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(...)");
        this.mTime = (TextView) viewFindViewById7;
        View viewFindViewById8 = mRootView.findViewById(R.id.stick_icon);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(...)");
        this.mStick = (ImageView) viewFindViewById8;
        View viewFindViewById9 = mRootView.findViewById(R.id.save_excerpt);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(...)");
        this.mSaveView = (TextView) viewFindViewById9;
        View viewFindViewById10 = mRootView.findViewById(R.id.note_content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(...)");
        this.mContentView = viewFindViewById10;
        ViewGroup.LayoutParams layoutParams = mRootView.getLayoutParams();
        layoutParams.height = i;
        mRootView.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = viewFindViewById10.getLayoutParams();
        layoutParams2.height = i2;
        viewFindViewById10.setLayoutParams(layoutParams2);
    }

    protected final String getMContent() {
        return this.mContent;
    }

    protected final void setMContent(String str) {
        this.mContent = str;
    }

    public final void setTitleStyle(int styleId) {
        this.mTitleStyle = styleId;
    }

    public final void setBodyStyle(int styleId) {
        this.mBodyStyle = styleId;
    }

    public final void setBackground(int resId) {
        this.mBackgroundRes = resId;
    }

    public final boolean isNeedFolme() {
        int i = this.mFloatNoteType;
        return i == 0 || i == 1;
    }

    private final void initCard(int type) {
        this.mFloatNoteType = type;
        this.mBindGeneration++;
        setTitleStyle(getTitleStyleByType(type));
        setBodyStyle(getBodyStyleByType(this.mFloatNoteType));
        setBackground(getBackgroundResByType(this.mFloatNoteType));
        this.mSummary.setVisibility(0);
        this.mImage.setVisibility(8);
        this.mVoiceImage.setVisibility(8);
        this.mOverviewThumbnail.setVisibility(8);
        this.mMindThumbnail.setVisibility(8);
        this.mSaveView.setClickable(true);
        this.mSaveView.setOnClickListener(null);
        this.itemView.setClickable(true);
        if (isNeedFolme()) {
            this.mItemViewFolme = Folme.useAt(this.itemView);
            IFolme iFolme = this.mItemViewFolme;
            Intrinsics.checkNotNull(iFolme);
            iFolme.touch().setTint(0.03f, 0.0f, 0.0f, 0.0f).handleTouchOf(this.itemView, new AnimConfig[0]);
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x007a  */
    @Override // com.miui.floatwindow.feature.note.FloatBaseItem
    public void bind(int type, ItemCache itemCache) {
        String str;
        Intrinsics.checkNotNullParameter(itemCache, "itemCache");
        initCard(type);
        Object cacheObject = itemCache.getCacheObject();
        Intrinsics.checkNotNull(cacheObject, "null cannot be cast to non-null type com.miui.notes.cache.NoteCache");
        NoteCache noteCache = (NoteCache) cacheObject;
        this.mSaveView.setVisibility(8);
        View view = this.mTimeRegion;
        boolean z = false;
        if (view != null) {
            view.setVisibility(0);
        }
        this.mTime.setText(noteCache.getFormattedSortedDate());
        this.mStick.setVisibility(noteCache.getNote().getStickAt() > 0 ? 0 : 8);
        if (noteCache.getNoteContentType() == 5) {
            String mindContent = noteCache.getNote().getMindContent();
            if (!TextUtils.isEmpty(mindContent)) {
                Gson gson = new Gson();
                Intrinsics.checkNotNull(mindContent);
                String strSubstring = mindContent.substring(15);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                MindEntity mindEntity = (MindEntity) gson.fromJson(strSubstring, MindEntity.class);
                z = mindEntity.isMap;
                str = mindEntity.content;
                Intrinsics.checkNotNull(str);
            } else {
                str = "";
            }
        } else {
            str = "";
        }
        displayTitle(noteCache);
        displayContentArea(noteCache, z, str);
        this.itemView.setBackground(this.mContext.getDrawable(this.mBackgroundRes));
    }

    private final void displayContentArea(NoteCache noteCache, boolean isMap, String mindContent) {
        this.mImage.setVisibility(8);
        this.mOverviewThumbnail.setVisibility(8);
        this.mMindThumbnail.setVisibility(8);
        this.mVoiceImage.setVisibility(8);
        this.mSummary.setVisibility(8);
        String snippet = noteCache.getNote().getSnippet();
        boolean z = !TextUtils.isEmpty(noteCache.getFirstImage()) || noteCache.getNoteContentType() == 6 || noteCache.getNoteContentType() == 7;
        boolean z2 = noteCache.getNoteContentType() == 3;
        boolean zIsEmpty = TextUtils.isEmpty(snippet);
        if (noteCache.getNoteContentType() == 5) {
            this.mLastImageRes = null;
            displayMindThumbnail(noteCache, isMap, mindContent);
        } else {
            if (z) {
                displayImage(noteCache);
                return;
            }
            this.mLastImageRes = null;
            if (z2) {
                this.mVoiceImage.setVisibility(0);
            }
            if (!zIsEmpty) {
                this.mSummary.setVisibility(0);
                displayContent(noteCache);
            }
            updateSummaryMaxLines(z2);
        }
    }

    private final void updateSummaryMaxLines(final boolean hasVoice) {
        final int i = this.mBindGeneration;
        this.mContentView.post(new Runnable() { // from class: com.miui.floatwindow.feature.note.FloatNoteItem$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FloatNoteItem.updateSummaryMaxLines$lambda$0(i, this, hasVoice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateSummaryMaxLines$lambda$0(int i, FloatNoteItem floatNoteItem, boolean z) {
        if (i != floatNoteItem.mBindGeneration) {
            return;
        }
        int height = floatNoteItem.mContentView.getHeight() - (z ? floatNoteItem.mVoiceImage.getHeight() : 0);
        if (height <= 0 || floatNoteItem.mSummary.getLineHeight() <= 0) {
            return;
        }
        TextView textView = floatNoteItem.mSummary;
        textView.setMaxLines(RangesKt.coerceAtLeast(height / textView.getLineHeight(), 1));
    }

    private final void displayMindThumbnail(NoteCache noteCache, boolean isMap, String mindContent) {
        String mindContent2 = noteCache.getNote().getMindContent();
        if (mindContent2 == null) {
            return;
        }
        boolean zIsCorrectFormatContent = MindDataHelper.isCorrectFormatContent(mindContent2);
        Theme theme = ResourceManager.getTheme(0);
        if (!isMap) {
            this.mOverviewThumbnail.setVisibility(0);
            this.mOverviewThumbnail.setStyle(theme);
            this.mOverviewThumbnail.removeAllViews();
            if (TextUtils.isEmpty(mindContent)) {
                return;
            }
            if (!zIsCorrectFormatContent) {
                mindContent = MindDataHelper.convertToCorrectFormatContent(mindContent);
            }
            this.mOverviewThumbnail.setExcerptContent(mindContent);
            return;
        }
        this.mMindThumbnail.setVisibility(0);
        this.mMindThumbnail.setStyle(theme);
        this.mMindThumbnail.removeAllViews();
        this.mMindThumbnail.resetAllViews();
        if (TextUtils.isEmpty(mindContent)) {
            return;
        }
        if (!zIsCorrectFormatContent) {
            mindContent = MindDataHelper.convertToCorrectFormatContent(mindContent);
        }
        this.mMindThumbnail.setExcerptContent(mindContent);
    }

    private final void displayImage(NoteCache noteCache) {
        String attachmentPath;
        String firstImage = noteCache.getFirstImage();
        if (noteCache.getNoteContentType() == 6) {
            firstImage = NoteSchema.retrieveHandWriteThumbnail(noteCache.getNote().getMindContent());
        } else if (noteCache.getNoteContentType() == 7) {
            firstImage = noteCache.getNote().getThumbnail();
        }
        if (TextUtils.isEmpty(firstImage)) {
            this.mLastImageRes = null;
            return;
        }
        this.mImage.setVisibility(0);
        if (TextUtils.isEmpty(this.mLastImageRes) || !Intrinsics.areEqual(this.mLastImageRes, firstImage)) {
            this.mLastImageRes = firstImage;
            if (AttachmentUtils.isDoodleSuffix(firstImage)) {
                attachmentPath = AttachmentUtils.getDoodleThumbnailPath(this.mContext, firstImage);
            } else if (noteCache.getNoteContentType() == 7) {
                attachmentPath = MultiHandWritePathUtils.getEntFileCompletePath(firstImage);
            } else {
                attachmentPath = AttachmentUtils.getAttachmentPath(this.mContext, firstImage);
            }
            BitmapDecoder bitmapDecoder = new BitmapDecoder(this.mContext, attachmentPath);
            this.mImage.setMaxHeightSize(RomUtils.isPadDevice() ? 120 : R2.array.ai_shader_recommend_bg_colors);
            this.mImage.setRadiusType(1);
            this.mImage.setBitmapDecoder(bitmapDecoder);
            this.mImage.setRadius(BaseNoteApp.INSTANCE.getInstance().getResources().getDimension(R.dimen.float_note_preview_image_round));
            this.mImage.setImageDrawable(null);
            Thumbnail.instance().load(noteCache.getNote()).type(Thumbnail.GRID).into(this.mImage);
        }
    }

    @Override // com.miui.floatwindow.feature.note.FloatBaseItem
    public void bind(int type, boolean isSaving, final ExcerptInfo info) {
        String string;
        Intrinsics.checkNotNullParameter(info, "info");
        initCard(type);
        boolean z = this.mFloatNoteType == 2;
        View view = this.mTimeRegion;
        if (view != null) {
            view.setVisibility(8);
        }
        if (z) {
            this.mSaveView.setVisibility(8);
            string = this.mContext.getString(R.string.continuous_new_excerpt_title);
        } else {
            this.mSaveView.setVisibility(0);
            if (!isSaving) {
                this.mSaveView.setText(R.string.save_single_excerpt);
                this.itemView.setClickable(true);
                this.mSaveView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.note.FloatNoteItem$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        FloatNoteItem.bind$lambda$1(info, this, view2);
                    }
                });
            } else {
                this.mSaveView.setText(R.string.single_link_excerpt_saving);
                this.mSaveView.setClickable(false);
                this.itemView.setClickable(false);
                Folme.clean(this.itemView);
            }
            string = this.mContext.getString(R.string.single_new_excerpt_title);
        }
        Intrinsics.checkNotNull(string);
        displayExcerptTitle(string);
        if (info.getExcerptType() != 1) {
            displayExcerptContent(info.getExcerptContent());
        } else {
            setImage(info.getExcerptContent());
        }
        this.itemView.setBackground(this.mContext.getDrawable(this.mBackgroundRes));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bind$lambda$1(ExcerptInfo excerptInfo, FloatNoteItem floatNoteItem, View view) {
        if (LinkUtils.isAccurateValidUrl(excerptInfo.getExcerptContent())) {
            floatNoteItem.mSaveView.setText(R.string.single_link_excerpt_saving);
            floatNoteItem.mSaveView.setClickable(false);
            floatNoteItem.itemView.setClickable(false);
            Folme.clean(floatNoteItem.itemView);
        }
        View.OnClickListener onClickListener = floatNoteItem.mSaveListener;
        Intrinsics.checkNotNull(onClickListener);
        onClickListener.onClick(view);
    }

    public final void setOnSaveListener(View.OnClickListener listener) {
        this.mSaveListener = listener;
    }

    private final void displayExcerptTitle(String titleText) {
        PreviewTextSpan previewTextSpan = new PreviewTextSpan(this.mContext, this.mTitleStyle);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(titleText);
        spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        this.mTitle.setMaxLines(1);
        this.mTitle.setEllipsize(TextUtils.TruncateAt.END);
        this.mTitle.setText(spannableStringBuilder);
    }

    private final void displayExcerptContent(String excerptContent) {
        PreviewTextSpan previewTextSpan = new PreviewTextSpan(this.mContext, this.mBodyStyle);
        CharSequence charSequenceRemoveEditSpans = TextProcessUtils.removeEditSpans(SnippetFormatter.format(this.mContext, excerptContent, null));
        if (!TextUtils.isEmpty(charSequenceRemoveEditSpans)) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequenceRemoveEditSpans);
            spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
            this.mSummary.setEllipsize(TextUtils.TruncateAt.END);
            this.mSummary.setText(spannableStringBuilder);
            final int i = this.mBindGeneration;
            this.mContentView.post(new Runnable() { // from class: com.miui.floatwindow.feature.note.FloatNoteItem$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FloatNoteItem.displayExcerptContent$lambda$2(i, this);
                }
            });
        }
        this.mSummary.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void displayExcerptContent$lambda$2(int i, FloatNoteItem floatNoteItem) {
        int height;
        if (i == floatNoteItem.mBindGeneration && (height = floatNoteItem.mContentView.getHeight()) > 0 && floatNoteItem.mSummary.getLineHeight() > 0) {
            TextView textView = floatNoteItem.mSummary;
            textView.setMaxLines(RangesKt.coerceAtLeast(height / textView.getLineHeight(), 1));
        }
    }

    private final void displayTitle(NoteCache noteCache) {
        String strReplace$default;
        PreviewTextSpan previewTextSpan = new PreviewTextSpan(this.mContext, this.mTitleStyle);
        String title = noteCache.getNote().getTitle();
        String string = (title == null || (strReplace$default = StringsKt.replace$default(title, StringUtils.LF, "", false, 4, (Object) null)) == null) ? null : StringsKt.trim((CharSequence) strReplace$default).toString();
        String str = string;
        if (str == null || str.length() == 0) {
            if (noteCache.getNoteContentType() == 6 || noteCache.getNoteContentType() == 7) {
                string = this.mContext.getString(R.string.handwrite_description);
                Intrinsics.checkNotNull(string);
            } else {
                String plainText = noteCache.getNote().getPlainText();
                String string2 = plainText != null ? StringsKt.trim((CharSequence) plainText).toString() : null;
                String str2 = string2;
                if (str2 != null && str2.length() != 0) {
                    string = extractTitle(string2);
                } else if (noteCache.getNoteContentType() == 5) {
                    string = this.mContext.getString(R.string.pc_action_create_new_mind_note);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                } else if (NoteSchema.retrieveFirstImage(noteCache.getNote().getSnippet()) != null) {
                    string = this.mContext.getString(R.string.snippet_image_stub);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                } else if (noteCache.getNoteContentType() == 3) {
                    string = this.mContext.getString(R.string.snippet_audio_stub);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                } else {
                    string = "";
                }
            }
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        SpannableStringBuilder spannableStringBuilder2 = spannableStringBuilder;
        if (spannableStringBuilder2.length() > 0) {
            spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
        }
        this.mTitle.setMaxLines(1);
        this.mTitle.setEllipsize(TextUtils.TruncateAt.END);
        this.mTitle.setText(spannableStringBuilder2);
    }

    private final void displayContent(NoteCache noteCache) {
        String strTrimEmptyLineSequence;
        PreviewTextSpan previewTextSpan = new PreviewTextSpan(this.mContext, this.mBodyStyle);
        String snippet = noteCache.getNote().getSnippet();
        if (!TextUtils.isEmpty(snippet)) {
            String strRemoveEditSpans = TextProcessUtils.removeEditSpans(SnippetFormatter.format(this.mContext, snippet, null));
            if (strRemoveEditSpans == null) {
            }
            strTrimEmptyLineSequence = TextProcessUtils.trimEmptyLineSequence(strRemoveEditSpans);
            Intrinsics.checkNotNull(strTrimEmptyLineSequence);
        }
        if (strTrimEmptyLineSequence.length() > 0) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(strTrimEmptyLineSequence);
            spannableStringBuilder.setSpan(previewTextSpan, 0, spannableStringBuilder.length(), 17);
            this.mSummary.setEllipsize(TextUtils.TruncateAt.END);
            this.mSummary.setText(spannableStringBuilder);
            return;
        }
        this.mSummary.setText("");
    }

    private final void setImage(String image) {
        if (!TextUtils.isEmpty(image)) {
            this.mSummary.setVisibility(8);
            this.mImage.setVisibility(0);
            if (TextUtils.isEmpty(this.mLastImageRes) || !Intrinsics.areEqual(this.mLastImageRes, image)) {
                this.mLastImageRes = image;
                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(image);
                this.mImage.setBitmapDecoder(new BitmapDecoder(this.mContext, image));
                this.mImage.setImageBitmap(bitmapDecodeFile);
                this.mImage.setMaxHeightSize(RomUtils.isPadDevice() ? 120 : R2.array.ai_shader_recommend_bg_colors);
                this.mImage.setRadiusType(1);
                this.mImage.setRadius(20.0f);
                return;
            }
            return;
        }
        this.mLastImageRes = null;
        this.mImage.setImageDrawable(null);
        this.mImage.setVisibility(8);
    }

    protected final String extractCleanContent(String plantText) {
        Intrinsics.checkNotNullParameter(plantText, "plantText");
        int length = plantText.length();
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = plantText.charAt(i);
            if (z || (cCharAt != '\n' && cCharAt != ' ' && cCharAt != 12288)) {
                if (!z && cCharAt != '\n' && cCharAt != ' ' && cCharAt != 12288) {
                    stringBuffer.append(cCharAt);
                    z = true;
                } else if (z) {
                    stringBuffer.append(cCharAt);
                }
            }
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    protected final String extractTitle(String plantText) {
        Intrinsics.checkNotNullParameter(plantText, "plantText");
        int length = plantText.length();
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = plantText.charAt(i);
            if (z || cCharAt != '\n') {
                if (!z && cCharAt != '\n' && cCharAt != ' ' && cCharAt != 12288) {
                    stringBuffer.append(cCharAt);
                    z = true;
                } else if (!z || cCharAt == '\n') {
                    if (z && cCharAt == '\n') {
                        break;
                    }
                } else {
                    stringBuffer.append(cCharAt);
                }
            }
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: compiled from: FloatNoteItem.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007B\u0013\b\u0016\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\u0006\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/miui/floatwindow/feature/note/FloatNoteItem$PreviewTextSpan;", "Landroid/text/style/TextAppearanceSpan;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "appearance", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;I)V", "src", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "updateMeasureState", "", "ds", "Landroid/text/TextPaint;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class PreviewTextSpan extends TextAppearanceSpan {
        public PreviewTextSpan(Context context, int i) {
            super(context, i);
        }

        public PreviewTextSpan(Parcel parcel) {
            super(parcel);
        }

        @Override // android.text.style.TextAppearanceSpan, android.text.style.MetricAffectingSpan
        public void updateMeasureState(TextPaint ds) {
            Typeface typefaceCreate;
            Intrinsics.checkNotNullParameter(ds, "ds");
            Typeface typeface = ds.getTypeface();
            int textStyle = getTextStyle();
            if (getFamily() != null) {
                typefaceCreate = Typeface.create(getFamily(), textStyle);
            } else if (typeface == null) {
                typefaceCreate = Typeface.defaultFromStyle(textStyle);
            } else {
                typefaceCreate = Typeface.create(typeface, textStyle);
            }
            int i = textStyle & (~typefaceCreate.getStyle());
            if ((i & 1) != 0) {
                ds.setFakeBoldText(true);
            }
            if ((i & 2) != 0) {
                ds.setTextSkewX(-0.25f);
            }
            ds.setTypeface(typefaceCreate);
            if (getTextSize() > 0) {
                ds.setTextSize(getTextSize());
            }
        }
    }

    /* JADX INFO: compiled from: FloatNoteItem.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/miui/floatwindow/feature/note/FloatNoteItem$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TYPE_NORMAL_NOTE", "", "TYPE_SINGLE_EXCERPT", "TYPE_CONTINUOUS_EXCERPT", "TAG", "", "newInstance", "Lcom/miui/floatwindow/feature/note/FloatNoteItem;", "parent", "Landroid/view/ViewGroup;", "itemHeight", "contentHeight", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final FloatNoteItem newInstance(ViewGroup parent, int itemHeight, int contentHeight) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            View viewInflateView = UIUtils.inflateView(parent, R.layout.float_note_list_item);
            Intrinsics.checkNotNull(viewInflateView);
            return new FloatNoteItem(viewInflateView, itemHeight, contentHeight);
        }
    }
}
