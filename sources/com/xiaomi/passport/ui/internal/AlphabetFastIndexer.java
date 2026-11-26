package com.xiaomi.passport.ui.internal;

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
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.WrapperListAdapter;
import androidx.appcompat.widget.AppCompatTextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class AlphabetFastIndexer extends AppCompatTextView {
    private static final int FADE_DELAYED = 500;
    private static final int MSG_FADE = 1;
    private static final String STARRED_LABEL = "★";
    public static final String STARRED_TITLE = "!";
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_NONE = 0;
    private String[] mAlphabets;
    private Handler mHandler;
    private int mLastAlphabetIndex;
    private int mListScrollState;
    private AdapterView<?> mListView;
    private TextView mOverlay;
    private Runnable mRefreshMaskRunnable;
    private int mState;
    private ValueAnimator.AnimatorUpdateListener mTextHilightAnimListener;
    private TextHilighter mTextHilighter;

    public AlphabetFastIndexer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AlphabetFastIndexer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextHilightAnimListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.passport.ui.internal.AlphabetFastIndexer.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AlphabetFastIndexer.this.mTextHilighter.update(AlphabetFastIndexer.this.getWidth() / 2.0f, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                AlphabetFastIndexer.this.postInvalidate();
            }
        };
        int i2 = 0;
        this.mListScrollState = 0;
        this.mState = 0;
        this.mRefreshMaskRunnable = new Runnable() { // from class: com.xiaomi.passport.ui.internal.AlphabetFastIndexer.2
            @Override // java.lang.Runnable
            public void run() {
                AlphabetFastIndexer.this.refreshMask();
            }
        };
        this.mHandler = new Handler() { // from class: com.xiaomi.passport.ui.internal.AlphabetFastIndexer.3
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1 && AlphabetFastIndexer.this.mOverlay != null) {
                    AlphabetFastIndexer.this.mOverlay.setVisibility(8);
                }
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Passport_AlphabetFastIndexer, i, 0);
        Resources resources = context.getResources();
        CharSequence[] textArray = typedArrayObtainStyledAttributes.getTextArray(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTable);
        if (textArray != null) {
            this.mAlphabets = new String[textArray.length];
            int length = textArray.length;
            int i3 = 0;
            while (i2 < length) {
                this.mAlphabets[i3] = textArray[i2].toString();
                i2++;
                i3++;
            }
        } else {
            this.mAlphabets = resources.getStringArray(R.array.passport_alphabet_table);
        }
        this.mTextHilighter = new TextHilighter(context, typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setSectionAlphabets(String[] strArr) {
        this.mAlphabets = (String[]) Arrays.copyOf(strArr, strArr.length);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mLastAlphabetIndex = -1;
        post(this.mRefreshMaskRunnable);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingTop = getPaddingTop();
        int height = (getHeight() - paddingTop) - getPaddingBottom();
        if (height <= 0) {
            return;
        }
        String[] strArr = this.mAlphabets;
        float length = height / strArr.length;
        float width = getWidth() / 2.0f;
        float f = paddingTop + (length / 2.0f);
        for (int i = 0; i < strArr.length; i++) {
            this.mTextHilighter.draw(canvas, isPressed(), i, width, f);
            f += length;
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attach();
    }

    public void attach() {
        this.mLastAlphabetIndex = -1;
        ViewGroup viewGroup = (ViewGroup) getParent();
        this.mOverlay = (TextView) viewGroup.findViewById(R.id.fast_indexer_high_light);
        this.mListView = (AdapterView) viewGroup.findViewById(R.id.fast_indexer_list);
        this.mOverlay.setVisibility(8);
        refreshMask();
    }

    public void drawThumb(CharSequence charSequence) {
        if (this.mState == 0 && this.mListScrollState == 2) {
            drawThumbInternal(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x0032  */
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
                iBinarySearch = Arrays.binarySearch(this.mAlphabets, str);
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

    /* JADX WARN: Code duplicated, block: B:17:0x0025  */
    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int length = 0;
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
                        postInvalidate();
                    }
                }
                return true;
            }
            postInvalidate();
            stop(500);
            return true;
        }
        int postion = getPostion(motionEvent.getY(), sectionIndexer);
        if (postion >= 0) {
            length = postion >= sectionIndexer.getSections().length ? sectionIndexer.getSections().length - 1 : postion;
        }
        scrollTo(sectionIndexer, length);
        TextHilighter textHilighter = this.mTextHilighter;
        if (textHilighter != null) {
            textHilighter.update(getWidth() / 2, motionEvent.getY());
        }
        postInvalidate();
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
        String[] strArr = this.mAlphabets;
        int length = (int) (strArr.length * ((f - paddingTop) / height));
        if (length < 0) {
            return -1;
        }
        if (length >= strArr.length) {
            return sections.length;
        }
        int iBinarySearch = Arrays.binarySearch(sections, strArr[length]);
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
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), 500L);
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
        float height = ((getHeight() - paddingTop) - getPaddingBottom()) / this.mAlphabets.length;
        this.mTextHilighter.startSlidding((i * height) + paddingTop + (height / 2.0f) + 1.0f, this.mTextHilightAnimListener);
    }

    private class TextHilighter {
        int mActivatedColor;
        ValueAnimator mAnimator;
        BitmapDrawable mBackground;
        Bitmap mBmpBuffer;
        Canvas mCanvas;
        Xfermode mClearMode;
        Xfermode mDstInMode;
        int mHilightColor;
        int mNormalColor;
        Rect mTextBoundIntersect;
        Paint mPaint = new Paint();
        Rect mTextBound = new Rect();
        Rect mSrcBounds = new Rect();

        TextHilighter(Context context, TypedArray typedArray) {
            this.mNormalColor = typedArray.getColor(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextColor, 0);
            this.mActivatedColor = typedArray.getColor(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextActivatedColor, 0);
            this.mHilightColor = typedArray.getColor(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextHighlightColor, 0);
            this.mBackground = (BitmapDrawable) typedArray.getDrawable(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextHighlightBackground);
            this.mPaint.setTextSize(typedArray.getDimension(R.styleable.Passport_AlphabetFastIndexer_passport_indexerTextSize, 0.0f));
            this.mPaint.setAntiAlias(true);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            this.mBmpBuffer = this.mBackground.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
            this.mCanvas = new Canvas(this.mBmpBuffer);
            this.mTextBoundIntersect = new Rect();
            this.mClearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
            this.mDstInMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
            update(0.0f, 0.0f);
        }

        void update(float f, float f2) {
            float intrinsicWidth = (this.mBackground.getIntrinsicWidth() / 2.0f) / 2.0f;
            float height = (AlphabetFastIndexer.this.getHeight() / AlphabetFastIndexer.this.mAlphabets.length) / 2.0f;
            this.mTextBound.set((int) ((f - intrinsicWidth) - 1.0f), (int) ((f2 - height) - 1.0f), (int) (f + intrinsicWidth + 1.0f), (int) (f2 + height + 1.0f));
            if (this.mTextBound.top < 0) {
                Rect rect = this.mTextBound;
                rect.offset(0, -rect.top);
            }
            if (this.mTextBound.bottom > AlphabetFastIndexer.this.getHeight()) {
                this.mTextBound.offset(0, AlphabetFastIndexer.this.getHeight() - this.mTextBound.bottom);
            }
        }

        void draw(Canvas canvas, boolean z, int i, float f, float f2) {
            Paint paint = this.mPaint;
            String str = TextUtils.equals(AlphabetFastIndexer.this.mAlphabets[i], "!") ? AlphabetFastIndexer.STARRED_LABEL : AlphabetFastIndexer.this.mAlphabets[i];
            paint.getTextBounds(str, 0, str.length(), this.mSrcBounds);
            this.mSrcBounds.width();
            this.mSrcBounds.height();
            if (this.mTextBound.contains((int) f, (int) f2)) {
                paint.setColor(this.mHilightColor);
            } else {
                paint.setColor(this.mNormalColor);
            }
            canvas.drawText(str, f, f2 - ((this.mSrcBounds.top + this.mSrcBounds.bottom) / 2.0f), paint);
        }

        void beginDraw() {
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

    public static class OnScrollerDecorator implements AbsListView.OnScrollListener {
        private final WeakReference<AlphabetFastIndexer> mIndexerRef;
        private final AbsListView.OnScrollListener mListener;
        private String mPreviousThumb = "";

        protected String itemToString(Object obj) {
            return null;
        }

        public OnScrollerDecorator(AlphabetFastIndexer alphabetFastIndexer, AbsListView.OnScrollListener onScrollListener) {
            this.mIndexerRef = new WeakReference<>(alphabetFastIndexer);
            this.mListener = onScrollListener;
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            AlphabetFastIndexer alphabetFastIndexer = this.mIndexerRef.get();
            if (alphabetFastIndexer != null) {
                alphabetFastIndexer.refreshMask();
                String strItemToString = itemToString(((ListAdapter) absListView.getAdapter()).getItem(i));
                if (TextUtils.isEmpty(strItemToString)) {
                    String upperCase = strItemToString.substring(0, 1).toUpperCase();
                    if (!TextUtils.equals(upperCase, this.mPreviousThumb)) {
                        alphabetFastIndexer.drawThumb(upperCase);
                        this.mPreviousThumb = upperCase;
                    }
                }
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
}
