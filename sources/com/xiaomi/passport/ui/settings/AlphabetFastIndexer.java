package com.xiaomi.passport.ui.settings;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.WrapperListAdapter;
import androidx.core.view.GravityCompat;
import com.xiaomi.account.passportsdk.account_sso.R;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class AlphabetFastIndexer extends ImageView {
    private static final int FADE_DELAYED = 1500;
    private static final int MSG_FADE = 1;
    private static final String STARRED_LABEL = "★";
    public static final String STARRED_TITLE = "!";
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_NONE = 0;
    private Handler mHandler;
    private int mLastAlphabetIndex;
    private int mListScrollState;
    private AdapterView<?> mListView;
    private TextView mOverlay;
    private Drawable mOverlayBackground;
    private int mOverlayLeftMargin;
    private int mOverlayTextColor;
    private int mOverlayTextSize;
    private int mOverlayTopMargin;
    private Runnable mRefreshMaskRunnable;
    private int mState;
    private ValueAnimator.AnimatorUpdateListener mTextHilightAnimListener;
    private TextHilighter mTextHilighter;
    private int mVerticalPosition;

    private static class TextHilighter {
        int mActivatedColor;
        ValueAnimator mAnimator;
        BitmapDrawable mBackground;
        Bitmap mBmpBuffer;
        Canvas mCanvas;
        Xfermode mClearMode;
        Xfermode mDstInMode;
        int mHilightColor;
        String[] mIndexes;
        int mNormalColor;
        Rect mTextBoundIntersect;
        float mXCenter;
        float mYCenter;
        Paint mPaint = new Paint();
        Rect mTextBound = new Rect();
        Rect mSrcBounds = new Rect();

        TextHilighter(Context context, TypedArray typedArray) {
            Resources resources = context.getResources();
            CharSequence[] textArray = typedArray.getTextArray(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTable);
            if (textArray != null) {
                this.mIndexes = new String[textArray.length];
                int length = textArray.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    this.mIndexes[i2] = textArray[i].toString();
                    i++;
                    i2++;
                }
            } else {
                this.mIndexes = resources.getStringArray(R.array.passport_alphabet_table);
            }
            this.mNormalColor = typedArray.getColor(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextColor, resources.getColor(R.color.passport_alphabet_indexer_text_color));
            this.mActivatedColor = typedArray.getColor(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextActivatedColor, resources.getColor(R.color.passport_alphabet_indexer_activated_text_color));
            this.mHilightColor = typedArray.getColor(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextHighlightColor, resources.getColor(R.color.passport_alphabet_indexer_highlight_text_color));
            this.mPaint.setTextSize(typedArray.getDimension(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextSize, resources.getDimension(R.dimen.passport_alphabet_indexer_text_size)));
            this.mPaint.setAntiAlias(true);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            Drawable drawable = typedArray.getDrawable(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextHighlightBackground);
            drawable = drawable == null ? resources.getDrawable(R.drawable.passport_alphabet_indexer_text_highlight_bg) : drawable;
            if (drawable != null && (drawable instanceof BitmapDrawable)) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                this.mBackground = bitmapDrawable;
                this.mBmpBuffer = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                this.mCanvas = new Canvas(this.mBmpBuffer);
                this.mTextBoundIntersect = new Rect();
                this.mClearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
                this.mDstInMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
            }
            update(0.0f, 0.0f);
        }

        void update(float f, float f2) {
            this.mXCenter = f;
            this.mYCenter = f2;
            float intrinsicWidth = this.mBackground.getIntrinsicWidth() / 2.0f;
            float intrinsicHeight = this.mBackground.getIntrinsicHeight() / 2.0f;
            Rect rect = this.mTextBound;
            float f3 = this.mXCenter;
            float f4 = this.mYCenter;
            rect.set((int) ((f3 - intrinsicWidth) + 1.0f), (int) (f4 - intrinsicHeight), (int) (f3 + intrinsicWidth + 1.0f), (int) (f4 + intrinsicHeight));
        }

        void draw(Canvas canvas, boolean z, int i, float f, float f2) {
            Paint paint = this.mPaint;
            String str = TextUtils.equals(this.mIndexes[i], "!") ? AlphabetFastIndexer.STARRED_LABEL : this.mIndexes[i];
            paint.getTextBounds(str, 0, str.length(), this.mSrcBounds);
            float fWidth = this.mSrcBounds.width();
            float fHeight = this.mSrcBounds.height();
            paint.setColor(z ? this.mActivatedColor : this.mNormalColor);
            canvas.drawText(str, f, f2 - ((this.mSrcBounds.top + this.mSrcBounds.bottom) / 2.0f), paint);
            float f3 = fWidth / 2.0f;
            float f4 = fHeight / 2.0f;
            if (this.mTextBoundIntersect.intersect((int) (f - f3), (int) (f2 - f4), (int) (f3 + f), (int) (f4 + f2))) {
                float f5 = f - this.mTextBound.left;
                float f6 = f2 - this.mTextBound.top;
                paint.setColor(this.mHilightColor);
                this.mCanvas.drawText(str, f5, f6 - ((this.mSrcBounds.top + this.mSrcBounds.bottom) / 2.0f), paint);
                this.mTextBoundIntersect.set(this.mTextBound);
            }
        }

        void beginDraw() {
            Paint paint = this.mPaint;
            Xfermode xfermode = paint.getXfermode();
            paint.setXfermode(this.mClearMode);
            this.mCanvas.drawPaint(paint);
            paint.setXfermode(xfermode);
            this.mBackground.setBounds(0, 0, this.mTextBound.width(), this.mTextBound.height());
            this.mBackground.draw(this.mCanvas);
            this.mTextBoundIntersect.set(this.mTextBound);
        }

        void endDraw(Canvas canvas) {
            Paint paint = this.mBackground.getPaint();
            Xfermode xfermode = paint.getXfermode();
            paint.setXfermode(this.mDstInMode);
            this.mBackground.draw(this.mCanvas);
            paint.setXfermode(xfermode);
            canvas.drawBitmap(this.mBmpBuffer, (Rect) null, this.mTextBound, (Paint) null);
        }

        void startSlidding(float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
            ValueAnimator valueAnimator = this.mAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            Rect rect = this.mTextBound;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(rect == null ? f : (rect.top + this.mTextBound.bottom) / 2.0f, f);
            this.mAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
            this.mAnimator.setDuration(200L);
            this.mAnimator.start();
        }
    }

    public AlphabetFastIndexer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AlphabetFastIndexer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextHilightAnimListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.passport.ui.settings.AlphabetFastIndexer.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AlphabetFastIndexer.this.mTextHilighter.update(AlphabetFastIndexer.this.getWidth() / 2.0f, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                AlphabetFastIndexer.this.postInvalidate();
            }
        };
        this.mListScrollState = 0;
        this.mState = 0;
        this.mRefreshMaskRunnable = new Runnable() { // from class: com.xiaomi.passport.ui.settings.AlphabetFastIndexer.2
            @Override // java.lang.Runnable
            public void run() {
                AlphabetFastIndexer.this.refreshMask();
            }
        };
        this.mHandler = new Handler() { // from class: com.xiaomi.passport.ui.settings.AlphabetFastIndexer.3
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1 && AlphabetFastIndexer.this.mOverlay != null) {
                    AlphabetFastIndexer.this.mOverlay.setVisibility(8);
                }
            }
        };
        Resources resources = context.getResources();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Passport_AlphabetFastIndexer, 0, attributeSet.getStyleAttribute() != 0 ? attributeSet.getStyleAttribute() : i);
        this.mTextHilighter = new TextHilighter(context, typedArrayObtainStyledAttributes);
        this.mOverlayLeftMargin = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Passport_AlphabetFastIndexer_passport_overlayMarginLeft, resources.getDimensionPixelOffset(R.dimen.passport_alphabet_indexer_overlay_offset));
        this.mOverlayTopMargin = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Passport_AlphabetFastIndexer_passport_overlayMarginTop, resources.getDimensionPixelOffset(R.dimen.passport_alphabet_indexer_overlay_padding_top));
        this.mOverlayTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Passport_AlphabetFastIndexer_passport_overlayTextSize, resources.getDimensionPixelSize(R.dimen.passport_alphabet_indexer_overlay_text_size));
        this.mOverlayTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.Passport_AlphabetFastIndexer_passport_overlayTextColor, resources.getColor(R.color.passport_alphabet_indexer_overlay_text_color));
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.Passport_AlphabetFastIndexer_passport_overlayBackground);
        this.mOverlayBackground = drawable;
        if (drawable == null) {
            this.mOverlayBackground = resources.getDrawable(R.drawable.passport_alphabet_indexer_overlay);
        }
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.Passport_AlphabetFastIndexer_passport_indexerBackground);
        setBackground(drawable2 == null ? resources.getDrawable(R.drawable.passport_alphabet_indexer_bg) : drawable2);
        typedArrayObtainStyledAttributes.recycle();
        this.mVerticalPosition = GravityCompat.END;
    }

    public void setVerticalPosition(boolean z) {
        this.mVerticalPosition = z ? GravityCompat.END : GravityCompat.START;
    }

    public void setOverlayOffset(int i, int i2) {
        this.mOverlayLeftMargin = i;
        this.mOverlayTopMargin = i2;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mLastAlphabetIndex = -1;
        post(this.mRefreshMaskRunnable);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingTop = getPaddingTop();
        int height = (getHeight() - paddingTop) - getPaddingBottom();
        if (height <= 0) {
            return;
        }
        String[] strArr = this.mTextHilighter.mIndexes;
        float length = height / strArr.length;
        float width = getWidth() / 2.0f;
        float f = paddingTop + (length / 2.0f);
        if (this.mTextHilighter.mXCenter == 0.0f || this.mTextHilighter.mYCenter == 0.0f) {
            this.mTextHilighter.update(width, f);
        }
        this.mTextHilighter.beginDraw();
        float f2 = f;
        for (int i = 0; i < strArr.length; i++) {
            this.mTextHilighter.draw(canvas, isPressed(), i, width, f2);
            f2 += length;
        }
        this.mTextHilighter.endDraw(canvas);
    }

    public void attatch(AdapterView<?> adapterView) {
        if (this.mListView == adapterView) {
            return;
        }
        detach();
        if (adapterView == null) {
            return;
        }
        this.mLastAlphabetIndex = -1;
        this.mListView = adapterView;
        Context context = getContext();
        FrameLayout frameLayout = (FrameLayout) getParent();
        this.mOverlay = new TextView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2, 17);
        layoutParams.leftMargin = this.mOverlayLeftMargin;
        layoutParams.topMargin = this.mOverlayTopMargin;
        this.mOverlay.setLayoutParams(layoutParams);
        this.mOverlay.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        this.mOverlay.setBackground(this.mOverlayBackground);
        this.mOverlay.setGravity(17);
        this.mOverlay.setTextSize(this.mOverlayTextSize);
        this.mOverlay.setTextColor(this.mOverlayTextColor);
        this.mOverlay.setVisibility(8);
        frameLayout.addView(this.mOverlay);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams2.gravity = this.mVerticalPosition | 48;
        setLayoutParams(layoutParams2);
        refreshMask();
    }

    public void detach() {
        if (this.mListView != null) {
            stop(0);
            ((FrameLayout) getParent()).removeView(this.mOverlay);
            setVisibility(8);
            this.mListView = null;
        }
    }

    public AbsListView.OnScrollListener decorateScrollListener(AbsListView.OnScrollListener onScrollListener) {
        return new OnScrollerDecorator(this, onScrollListener);
    }

    public void drawThumb(CharSequence charSequence) {
        if (this.mState == 0 && this.mListScrollState == 2) {
            drawThumbInternal(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x0034  */
    public void refreshMask() {
        int iBinarySearch;
        int sectionForPosition;
        if (this.mListView == null) {
            return;
        }
        SectionIndexer sectionIndexer = getSectionIndexer();
        if (sectionIndexer == null || (sectionForPosition = sectionIndexer.getSectionForPosition(this.mListView.getFirstVisiblePosition() - getListOffset())) == -1) {
            iBinarySearch = 0;
        } else {
            String str = (String) sectionIndexer.getSections()[sectionForPosition];
            if (TextUtils.isEmpty(str)) {
                iBinarySearch = 0;
            } else {
                iBinarySearch = Arrays.binarySearch(this.mTextHilighter.mIndexes, str);
            }
        }
        if (this.mLastAlphabetIndex != iBinarySearch) {
            this.mLastAlphabetIndex = iBinarySearch;
            if (1 != this.mState) {
                slidTextHilightBackground(iBinarySearch);
            }
            invalidate();
        }
    }

    public int getIndexerIntrinsicWidth() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicWidth();
        }
        return 0;
    }

    private SectionIndexer getSectionIndexer() {
        boolean z;
        AdapterView<?> adapterView = this.mListView;
        if (adapterView == null) {
            return null;
        }
        Object adapter = adapterView.getAdapter();
        while (true) {
            z = adapter instanceof SectionIndexer;
            if (z || !(adapter instanceof WrapperListAdapter)) {
                break;
            }
            adapter = ((WrapperListAdapter) adapter).getWrappedAdapter();
        }
        if (z) {
            return (SectionIndexer) adapter;
        }
        return null;
    }

    private int getListOffset() {
        AdapterView<?> adapterView = this.mListView;
        if (adapterView instanceof ListView) {
            return ((ListView) adapterView).getHeaderViewsCount();
        }
        return 0;
    }

    private static class OnScrollerDecorator implements AbsListView.OnScrollListener {
        private final WeakReference<AlphabetFastIndexer> mIndexerRef;
        private final AbsListView.OnScrollListener mListener;

        public OnScrollerDecorator(AlphabetFastIndexer alphabetFastIndexer, AbsListView.OnScrollListener onScrollListener) {
            this.mIndexerRef = new WeakReference<>(alphabetFastIndexer);
            this.mListener = onScrollListener;
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            AlphabetFastIndexer alphabetFastIndexer = this.mIndexerRef.get();
            if (alphabetFastIndexer != null) {
                alphabetFastIndexer.refreshMask();
            }
            AbsListView.OnScrollListener onScrollListener = this.mListener;
            if (onScrollListener != null) {
                onScrollListener.onScroll(absListView, i, i2, i3);
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            AlphabetFastIndexer alphabetFastIndexer = this.mIndexerRef.get();
            if (alphabetFastIndexer != null) {
                alphabetFastIndexer.mListScrollState = i;
            }
            AbsListView.OnScrollListener onScrollListener = this.mListener;
            if (onScrollListener != null) {
                onScrollListener.onScrollStateChanged(absListView, i);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0025  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mListView == null) {
            stop(0);
            return false;
        }
        SectionIndexer sectionIndexer = getSectionIndexer();
        if (sectionIndexer == null) {
            stop(0);
            return false;
        }
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mState = 1;
            setPressed(true);
        } else {
            if (action != 1) {
                if (action != 2) {
                    if (action == 3) {
                        slidTextHilightBackground(this.mLastAlphabetIndex);
                    }
                }
                return true;
            }
            slidTextHilightBackground(this.mLastAlphabetIndex);
            stop(1500);
            return true;
        }
        int postion = getPostion(motionEvent.getY(), sectionIndexer);
        if (postion < 0) {
            this.mListView.setSelection(0);
        } else {
            scrollTo(sectionIndexer, postion);
            if (this.mTextHilighter != null && motionEvent.getY() > getTop() + getPaddingTop() && motionEvent.getY() < getBottom() - getPaddingBottom()) {
                this.mTextHilighter.update(getWidth() / 2, motionEvent.getY());
                postInvalidate();
            }
        }
        return true;
    }

    void stop(int i) {
        setPressed(false);
        this.mState = 0;
        postInvalidate();
        this.mHandler.removeMessages(1);
        if (i <= 0) {
            TextView textView = this.mOverlay;
            if (textView != null) {
                textView.setVisibility(8);
                return;
            }
            return;
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), i);
    }

    private int getPostion(float f, SectionIndexer sectionIndexer) {
        Object[] sections = sectionIndexer.getSections();
        if (sections == null) {
            return -1;
        }
        int paddingTop = getPaddingTop();
        int height = (getHeight() - paddingTop) - getPaddingBottom();
        if (height <= 0) {
            return -1;
        }
        int length = (int) (this.mTextHilighter.mIndexes.length * ((f - paddingTop) / height));
        if (length < 0) {
            return -1;
        }
        if (length >= this.mTextHilighter.mIndexes.length) {
            return sections.length;
        }
        int iBinarySearch = Arrays.binarySearch(sections, this.mTextHilighter.mIndexes[length]);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 2;
        }
        if (iBinarySearch < 0) {
            return 0;
        }
        return iBinarySearch;
    }

    private void scrollTo(SectionIndexer sectionIndexer, int i) {
        int i2;
        int i3;
        int count = this.mListView.getCount();
        int listOffset = getListOffset();
        float f = (1.0f / count) / 8.0f;
        Object[] sections = sectionIndexer.getSections();
        if (sections != null && sections.length > 1) {
            int length = sections.length;
            int i4 = i >= length ? length - 1 : i;
            int positionForSection = sectionIndexer.getPositionForSection(i4);
            int i5 = i4 + 1;
            int positionForSection2 = i4 < length + (-1) ? sectionIndexer.getPositionForSection(i5) : count;
            int i6 = i4;
            if (positionForSection2 == positionForSection) {
                int positionForSection3 = positionForSection;
                while (true) {
                    if (i6 <= 0) {
                        i6 = i4;
                    } else {
                        i6--;
                        positionForSection3 = sectionIndexer.getPositionForSection(i6);
                        if (positionForSection3 == positionForSection) {
                            if (i6 == 0) {
                                i6 = i4;
                                positionForSection = positionForSection3;
                                i3 = 0;
                            }
                        }
                    }
                    positionForSection = positionForSection3;
                    i3 = i6;
                }
            } else {
                i3 = i6;
            }
            i2 = 1;
            int i7 = i4 + 2;
            while (i7 < length && sectionIndexer.getPositionForSection(i7) == positionForSection2) {
                i7++;
                i5++;
            }
            float f2 = length;
            float f3 = i6 / f2;
            float f4 = i5 / f2;
            float f5 = i / f2;
            if (i6 != i4 || f5 - f3 >= f) {
                positionForSection += Math.round(((positionForSection2 - positionForSection) * (f5 - f3)) / (f4 - f3));
            }
            int i8 = count - 1;
            if (positionForSection > i8) {
                positionForSection = i8;
            }
            AdapterView<?> adapterView = this.mListView;
            if (adapterView instanceof ExpandableListView) {
                ExpandableListView expandableListView = (ExpandableListView) adapterView;
                expandableListView.setSelectionFromTop(expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(positionForSection + listOffset)), 0);
            } else if (adapterView instanceof ListView) {
                ((ListView) adapterView).setSelectionFromTop(positionForSection + listOffset, 0);
            } else {
                adapterView.setSelection(positionForSection + listOffset);
            }
        } else {
            i2 = 1;
            int iRound = Math.round(i * count);
            AdapterView<?> adapterView2 = this.mListView;
            if (adapterView2 instanceof ExpandableListView) {
                ExpandableListView expandableListView2 = (ExpandableListView) adapterView2;
                expandableListView2.setSelectionFromTop(expandableListView2.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(iRound + listOffset)), 0);
            } else if (adapterView2 instanceof ListView) {
                ((ListView) adapterView2).setSelectionFromTop(iRound + listOffset, 0);
            } else {
                adapterView2.setSelection(iRound + listOffset);
            }
            i3 = -1;
        }
        if (i3 < 0 || sections == null) {
            return;
        }
        String string = sections[i3].toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        drawThumbInternal(string.subSequence(0, i2));
    }

    private void drawThumbInternal(CharSequence charSequence) {
        if (this.mListView == null) {
            return;
        }
        if (TextUtils.equals(charSequence, "!")) {
            charSequence = STARRED_LABEL;
        }
        this.mOverlay.setText(charSequence);
        if (getVisibility() == 0) {
            this.mOverlay.setVisibility(0);
            this.mHandler.removeMessages(1);
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), 1500L);
        }
    }

    private void slidTextHilightBackground(int i) {
        if (this.mTextHilighter == null) {
            return;
        }
        if (i < 0) {
            i = 0;
        }
        int paddingTop = getPaddingTop();
        float height = ((getHeight() - paddingTop) - getPaddingBottom()) / this.mTextHilighter.mIndexes.length;
        this.mTextHilighter.startSlidding((i * height) + paddingTop + (height / 2.0f) + 1.0f, this.mTextHilightAnimListener);
    }
}
