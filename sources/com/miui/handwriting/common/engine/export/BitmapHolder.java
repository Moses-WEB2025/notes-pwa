package com.miui.handwriting.common.engine.export;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import java.io.File;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.richtext.data.SpannedData;

/* JADX INFO: compiled from: BitmapHolder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b*\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010,\u001a\u00020\u000e2\u0006\u0010-\u001a\u00020.J\u0010\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u00010\u001aJ\b\u00102\u001a\u0004\u0018\u00010\u001aJ\u0010\u00103\u001a\u0002002\b\u00104\u001a\u0004\u0018\u00010\u001aJ\b\u00105\u001a\u0004\u0018\u00010\u001aJ\u000e\u00106\u001a\u0002002\u0006\u00107\u001a\u00020\u001dJ\b\u00108\u001a\u0004\u0018\u00010\u001dJ\u0006\u00109\u001a\u00020\u001fJ\u000e\u0010:\u001a\u0002002\u0006\u0010;\u001a\u00020\u001fJ\u0006\u0010<\u001a\u00020!J\u000e\u0010=\u001a\u0002002\u0006\u0010>\u001a\u00020!J\u0006\u0010?\u001a\u00020!J\u000e\u0010@\u001a\u0002002\u0006\u0010A\u001a\u00020!J\b\u0010B\u001a\u0004\u0018\u00010\u001aJ\u0010\u0010C\u001a\u0002002\b\u00104\u001a\u0004\u0018\u00010\u001aJ\u0006\u0010D\u001a\u00020\u001fJ\u000e\u0010E\u001a\u0002002\u0006\u0010F\u001a\u00020\u001fJ\u0006\u0010G\u001a\u00020\u001fJ\u000e\u0010H\u001a\u0002002\u0006\u0010I\u001a\u00020\u001fJ\u0006\u0010J\u001a\u00020!J\u000e\u0010K\u001a\u0002002\u0006\u0010L\u001a\u00020!J\u0006\u0010M\u001a\u00020!J\u000e\u0010N\u001a\u0002002\u0006\u0010O\u001a\u00020!J\u0006\u0010P\u001a\u00020!J\u000e\u0010Q\u001a\u0002002\u0006\u0010R\u001a\u00020!J\u0006\u0010S\u001a\u00020*J\u000e\u0010T\u001a\u0002002\u0006\u0010U\u001a\u00020*J\u0006\u0010V\u001a\u00020*J\u000e\u0010W\u001a\u0002002\u0006\u0010X\u001a\u00020*J\u0006\u0010Y\u001a\u000200R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020*X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006Z"}, d2 = {"Lcom/miui/handwriting/common/engine/export/BitmapHolder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "bitmapByAllSelect", "Landroid/graphics/Bitmap;", "getBitmapByAllSelect", "()Landroid/graphics/Bitmap;", "setBitmapByAllSelect", "(Landroid/graphics/Bitmap;)V", "allDateByAllSelect", "getAllDateByAllSelect", "setAllDateByAllSelect", "currentPathFile", "Ljava/io/File;", "getCurrentPathFile", "()Ljava/io/File;", "setCurrentPathFile", "(Ljava/io/File;)V", "canvasBitmapsBySelect", "Lcom/miui/libhandwritingpage/newbean/CanvasMaskEntity;", "getCanvasBitmapsBySelect", "()Lcom/miui/libhandwritingpage/newbean/CanvasMaskEntity;", "setCanvasBitmapsBySelect", "(Lcom/miui/libhandwritingpage/newbean/CanvasMaskEntity;)V", "mReactF", "Landroid/graphics/RectF;", "mTransactRectF", "mSelectedSpanImageData", "Lxc/richtext/data/SpannedData;", "mIsFromText", "", "mOffsetX", "", "mOffsetY", "mInsertRectFForLimit", "mIsOverLimitPageNum", "mIsNeedNewPage", "mNewPageIndex", "mNewPageWidth", "mNewPageHeight", "mImageWidth", "", "mImageHeight", "getFilePath", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "setBitmapReactF", "", "reactF", "getBitmapReactF", "setTransactRect", "rectF", "getTransactRect", "setSelectedSpanImageData", "spannedData", "getSelectedSpanImageData", "getIsFromText", "setIsFromText", "isFromText", "getOffsetX", "setOffsetX", "offsetX", "getOffsetY", "setOffsetY", "offsetY", "getInsertRectFForLimit", "setInsertRectFForLimit", "getIsNeedNewPage", "setIsNeedNewPage", "isNeedNewPage", "getIsOverLimitPageNum", "setIsOverLimitPageNum", "isOverLimitPageNum", "getNewPageIndex", "setNewPageIndex", "newPageIndex", "getNewPageWidth", "setNewPageWidth", "newPageWidth", "getNewPageHeight", "setNewPageHeight", "newPageHeight", "getImageWidth", "setImageWidth", "imageWidth", "getImageHeight", "setImageHeight", "imageHeight", "resetReactF", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BitmapHolder {
    private static Bitmap allDateByAllSelect;
    private static Bitmap bitmapByAllSelect;
    private static CanvasMaskEntity canvasBitmapsBySelect;
    private static File currentPathFile;
    private static float mImageHeight;
    private static float mImageWidth;
    private static RectF mInsertRectFForLimit;
    private static boolean mIsFromText;
    private static boolean mIsNeedNewPage;
    private static boolean mIsOverLimitPageNum;
    private static int mNewPageHeight;
    private static int mNewPageWidth;
    private static int mOffsetX;
    private static int mOffsetY;
    private static RectF mReactF;
    private static SpannedData mSelectedSpanImageData;
    private static RectF mTransactRectF;
    public static final BitmapHolder INSTANCE = new BitmapHolder();
    private static int mNewPageIndex = -1;

    private BitmapHolder() {
    }

    public final Bitmap getBitmapByAllSelect() {
        return bitmapByAllSelect;
    }

    public final void setBitmapByAllSelect(Bitmap bitmap) {
        bitmapByAllSelect = bitmap;
    }

    public final Bitmap getAllDateByAllSelect() {
        return allDateByAllSelect;
    }

    public final void setAllDateByAllSelect(Bitmap bitmap) {
        allDateByAllSelect = bitmap;
    }

    public final File getCurrentPathFile() {
        return currentPathFile;
    }

    public final void setCurrentPathFile(File file) {
        currentPathFile = file;
    }

    public final CanvasMaskEntity getCanvasBitmapsBySelect() {
        return canvasBitmapsBySelect;
    }

    public final void setCanvasBitmapsBySelect(CanvasMaskEntity canvasMaskEntity) {
        canvasBitmapsBySelect = canvasMaskEntity;
    }

    public final File getFilePath(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return new File(new File(context.getFilesDir().toURI()), string);
    }

    public final void setBitmapReactF(RectF reactF) {
        mReactF = reactF;
    }

    public final RectF getBitmapReactF() {
        return mReactF;
    }

    public final void setTransactRect(RectF rectF) {
        mTransactRectF = rectF;
    }

    public final RectF getTransactRect() {
        return mTransactRectF;
    }

    public final void setSelectedSpanImageData(SpannedData spannedData) {
        Intrinsics.checkNotNullParameter(spannedData, "spannedData");
        mSelectedSpanImageData = spannedData;
    }

    public final SpannedData getSelectedSpanImageData() {
        return mSelectedSpanImageData;
    }

    public final boolean getIsFromText() {
        return mIsFromText;
    }

    public final void setIsFromText(boolean isFromText) {
        mIsFromText = isFromText;
    }

    public final int getOffsetX() {
        return mOffsetX;
    }

    public final void setOffsetX(int offsetX) {
        mOffsetX = offsetX;
    }

    public final int getOffsetY() {
        return mOffsetY;
    }

    public final void setOffsetY(int offsetY) {
        mOffsetY = offsetY;
    }

    public final RectF getInsertRectFForLimit() {
        return mInsertRectFForLimit;
    }

    public final void setInsertRectFForLimit(RectF rectF) {
        mInsertRectFForLimit = rectF;
    }

    public final boolean getIsNeedNewPage() {
        return mIsNeedNewPage;
    }

    public final void setIsNeedNewPage(boolean isNeedNewPage) {
        mIsNeedNewPage = isNeedNewPage;
    }

    public final boolean getIsOverLimitPageNum() {
        return mIsOverLimitPageNum;
    }

    public final void setIsOverLimitPageNum(boolean isOverLimitPageNum) {
        mIsOverLimitPageNum = isOverLimitPageNum;
    }

    public final int getNewPageIndex() {
        return mNewPageIndex;
    }

    public final void setNewPageIndex(int newPageIndex) {
        mNewPageIndex = newPageIndex;
    }

    public final int getNewPageWidth() {
        return mNewPageWidth;
    }

    public final void setNewPageWidth(int newPageWidth) {
        mNewPageWidth = newPageWidth;
    }

    public final int getNewPageHeight() {
        return mNewPageHeight;
    }

    public final void setNewPageHeight(int newPageHeight) {
        mNewPageHeight = newPageHeight;
    }

    public final float getImageWidth() {
        return mImageWidth;
    }

    public final void setImageWidth(float imageWidth) {
        mImageWidth = imageWidth;
    }

    public final float getImageHeight() {
        return mImageHeight;
    }

    public final void setImageHeight(float imageHeight) {
        mImageHeight = imageHeight;
    }

    public final void resetReactF() {
        mReactF = null;
        mTransactRectF = null;
        mSelectedSpanImageData = null;
        mInsertRectFForLimit = null;
    }
}
