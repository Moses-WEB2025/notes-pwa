package com.miui.richeditor;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.BaseInputConnection;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.hybirdeditor.WebRichEditor;
import com.miui.maml.elements.MusicLyricParser;
import com.miui.notes.R;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.miui.richeditor.base.LinkifyEditText;
import com.miui.richeditor.constant.RichEditorConfig;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.schema.RichEditorContentHandler;
import com.miui.richeditor.style.AudioSpan;
import com.miui.richeditor.style.BaseAudioSpan;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.BgHighLightSpan;
import com.miui.richeditor.style.CheckboxSpan;
import com.miui.richeditor.style.ContactSpan;
import com.miui.richeditor.style.EditLineHeightSpan;
import com.miui.richeditor.style.EditorBulletSpan;
import com.miui.richeditor.style.EditorHintEntrySpan;
import com.miui.richeditor.style.FontLargeSizeSpan;
import com.miui.richeditor.style.FontMidSizeSpan;
import com.miui.richeditor.style.KeywordsSpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.LinkCardSpanHelper;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.style.SelectableImageSpan;
import com.miui.richeditor.style.SelectableSpanListener;
import com.miui.richeditor.style.lite.CheckboxLiteSpan;
import com.miui.richeditor.style.lite.EditorBulletLiteSpan;
import com.miui.richeditor.style.render.DefaultEditorImageRender;
import com.miui.richeditor.style.render.IEditorImageRender;
import com.miui.richeditor.theme.Theme;
import com.miui.richeditor.util.CommonUtils;
import com.miui.richeditor.util.ContactUtils;
import com.miui.richeditor.util.EditableFactory;
import com.miui.richeditor.util.EditorUtils;
import com.miui.richeditor.util.NoteClipManager;
import com.miui.richeditor.util.ResourceParser;
import com.miui.richeditor.util.SafeSpannableStringBuilder;
import com.miui.richeditor.util.SmoothDrawHelper;
import defpackage.R2;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import miuix.animation.utils.EaseManager;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class RichEditTextView extends LinkifyEditText implements IRichEditor, IAudioEditorContext, MenuItem.OnMenuItemClickListener {
    private static final String TAG = "RichEditTextView";
    private final MainHandler<RichEditTextView> handler;
    private boolean isThemeChanged;
    protected EditorActionListener mActionListener;
    private WeakReference<Activity> mActivityWeakRef;
    private boolean mAttachedToWindow;
    private int mAudioDuration;
    private AudioFocusChangeListener mAudioFocusChangeListener;
    private final Object mAudioFocusLock;
    private AudioFocusRequest mAudioFocusRequest;
    private boolean mAudioGestureMoveInWave;
    protected int mAudioInputEnd;
    protected int mAudioInputStart;
    private AudioManager mAudioManager;
    protected int mAudioSpanEnd;
    private boolean mBoldState;
    protected int[] mCacheEditSelection;
    protected int mContentWidth;
    protected IRichEditor.CursorClickListener mCursorClickListener;
    protected long mDataId;
    private DataHtmlParseTask mDataParseTask;
    protected BaseImageSpan mDraggingImageSpan;
    private View mEditAreaContainer;
    protected RichEditorConfig mEditorConfig;
    protected IEditorImageRender mEditorImageRender;
    protected Theme mEditorTheme;
    private RichEditTextViewAccessHelper mExploreByTouchHelper;
    protected View.OnClickListener mExternalClickListener;
    protected ExternalTextWatcher mExternalTextWatcher;
    protected float mFontInnerScale;
    protected float[] mFontLevelSize;
    protected float mFontScale;
    protected int mFontSizeId;
    private boolean mHasUnknownTag;
    protected ArrayList<String> mHighLightKeyWords;
    private boolean mHighLightState;
    protected ImageInsertTask mImageInsertTask;
    protected InputModeRichStyleHelper mInputModeInfo;
    private boolean mIsFirstLoad;
    protected boolean mIsHintSpanTwoLines;
    private boolean mIsInDragMode;
    protected boolean mIsPrivate;
    private boolean mIsRecording;
    protected boolean mIsRemoveFocus;
    private boolean mIsUseMouseWithRight;
    private boolean mItalicState;
    private String mLastPlayedAudioFile;
    protected float mLastTouchDownX;
    protected float mLastTouchDownY;
    protected LinkCardSpanHelper mLinkCardSpanHelper;
    private View mLoadingContainer;
    protected IRichEditor.DataLoadingLocker mLoadingLocker;
    private Rect mLocalVisibleRect;
    protected HtmlParser.IMediaHandler mMediaHandler;
    private MediaPlayer mMediaPlayer;
    private MediaPlayerCallback mMediaPlayerCallback;
    private Method mMethodStopTextActionMode;
    private final ArrayList<Runnable> mPendingTask;
    private boolean mPlaybackDelayed;
    private BaseAudioSpan mPlayingAudioSpan;
    private final Runnable mPrepareImagesRunnable;
    protected String mQueryContent;
    private boolean mReadOnly;
    private boolean mResumeOnFocusGain;
    protected CharSequence mRichTextData;
    protected RichTextWatcher mRichTextWatcher;
    private RichEditScrollView mScrollView;
    protected BaseImageSpan mSelectImageSpan;
    protected int mSelectLine;
    private int mSelectLineAlpha;
    private float mSelectLineAnimFraction;
    private ValueAnimator mSelectLineDrawAnim;
    private int mSelectLineLengthHalf;
    protected Paint mSelectLinePaint;
    protected int mSelectLineTop;
    protected SelectableSpanListener mSelectableSpanListener;
    private boolean mSkipRichText;
    private boolean mSkipSelectionChanged;
    protected SmoothDrawHelper mSmoothDrawHelper;
    protected SpanClickListener mSpanClickListener;
    protected ClickableSpanDetectorManager mSpanDetector;
    protected RichSavedState mState;
    protected VisualStyleFactory mStyleFactory;
    protected TextStyleStateListener mTextStyleStateListener;
    private Theme mTheme;
    protected String mTitle;
    private boolean mUnderLineState;
    protected UndoManager<RichEditUndoOperation> mUndoManager;
    private final ViewConfiguration mViewConfiguration;
    protected int mWindowWidth;
    public String pTempSaveAudioFile;
    private Runnable showLoadingViewTask;

    public SpannableString createHintEntrySpanText() {
        return null;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void enterDragZone() {
    }

    public int getHintSpanMarginStartByTheme(Theme theme) {
        return 0;
    }

    @Override // com.miui.richeditor.IRichEditor, com.miui.richeditor.IEditorContext
    public int getKeywordStyle() {
        return R.style.TextAppearance_HighLight;
    }

    public void getMarkdown(WebRichEditor.OnWriteFinishedListener onWriteFinishedListener) {
    }

    public void getMarkdown(WebRichEditor.OnWriteFinishedListener onWriteFinishedListener, String str) {
    }

    @Override // com.miui.richeditor.IEditorContext
    public AppCompatEditText getView() {
        return this;
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean hasShowingMenu() {
        return false;
    }

    @Override // com.miui.richeditor.IEditorContext
    public boolean isNeedEditorLineHeight() {
        return true;
    }

    @Override // android.widget.TextView
    public boolean moveCursorToVisibleOffset() {
        return false;
    }

    protected boolean needHandleEditMenuItem() {
        return true;
    }

    public void onImageSpanRightClick(BaseImageSpan baseImageSpan, int[] iArr) {
    }

    @Override // com.miui.richeditor.IEditorContext
    public void onSpanEditableContentClick(Object obj, int[] iArr) {
    }

    @Override // com.miui.richeditor.IEditorContext
    public void onSpanRightClick(Object obj, int[] iArr) {
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setDeline() {
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setH1() {
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setH2() {
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setH3() {
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setHr() {
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setIndent() {
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setOrderList() {
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setQuote() {
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setUndent() {
    }

    @Override // android.widget.TextView, com.miui.richeditor.IEditorContext
    public /* bridge */ /* synthetic */ Paint getPaint() {
        return super.getPaint();
    }

    protected static class MySelectableSpanListener implements SelectableSpanListener {
        private final WeakReference<RichEditTextView> viewWeakReference;

        public MySelectableSpanListener(RichEditTextView richEditTextView) {
            this.viewWeakReference = new WeakReference<>(richEditTextView);
        }

        @Override // com.miui.richeditor.style.SelectableSpanListener
        public void onSelectChanged(SelectableImageSpan selectableImageSpan, boolean z) {
            RichEditTextView richEditTextView = this.viewWeakReference.get();
            if (richEditTextView == null) {
                return;
            }
            if (z) {
                if (richEditTextView.mSelectImageSpan != selectableImageSpan) {
                    if (richEditTextView.mSelectImageSpan != null) {
                        richEditTextView.mSelectImageSpan.onSelected(false);
                    }
                    richEditTextView.mSelectImageSpan = selectableImageSpan;
                    return;
                }
                return;
            }
            if (richEditTextView.mSelectImageSpan == selectableImageSpan) {
                richEditTextView.mSelectImageSpan.onSelected(false);
                richEditTextView.mSelectImageSpan = null;
            }
        }
    }

    static class AudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
        private final WeakReference<RichEditTextView> viewWeakReference;

        public AudioFocusChangeListener(RichEditTextView richEditTextView) {
            this.viewWeakReference = new WeakReference<>(richEditTextView);
        }

        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            RichEditTextView richEditTextView = this.viewWeakReference.get();
            if (richEditTextView == null) {
                return;
            }
            if (i == -3 || i == -2) {
                synchronized (richEditTextView.mAudioFocusLock) {
                    if (richEditTextView.mMediaPlayer != null) {
                        richEditTextView.mResumeOnFocusGain = richEditTextView.mMediaPlayer.isPlaying();
                    } else {
                        richEditTextView.mResumeOnFocusGain = false;
                    }
                    richEditTextView.mPlaybackDelayed = false;
                }
                richEditTextView.pauseAudio();
                richEditTextView.pauseAudioAnim();
                return;
            }
            if (i == -1) {
                synchronized (richEditTextView.mAudioFocusLock) {
                    richEditTextView.mResumeOnFocusGain = false;
                    richEditTextView.mPlaybackDelayed = false;
                }
                richEditTextView.pauseAudio();
                richEditTextView.pauseAudioAnim();
                return;
            }
            boolean z = true;
            if (i != 1) {
                return;
            }
            synchronized (richEditTextView.mAudioFocusLock) {
                if (!richEditTextView.mPlaybackDelayed && !richEditTextView.mResumeOnFocusGain) {
                    z = false;
                }
                if (z) {
                    richEditTextView.mPlaybackDelayed = false;
                    richEditTextView.mResumeOnFocusGain = false;
                }
            }
            if (z) {
                richEditTextView.playAudioNow();
            }
        }
    }

    public RichEditTextView(Context context) {
        this(context, null);
    }

    public RichEditTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.editTextStyle);
    }

    public RichEditTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsFirstLoad = true;
        this.mAttachedToWindow = false;
        this.mSkipSelectionChanged = false;
        this.mSkipRichText = false;
        this.mIsInDragMode = false;
        this.mHasUnknownTag = false;
        this.mReadOnly = false;
        this.mIsHintSpanTwoLines = false;
        this.mFontLevelSize = new float[3];
        this.mContentWidth = 0;
        this.mCacheEditSelection = new int[]{-1, -1};
        this.mLocalVisibleRect = new Rect();
        this.showLoadingViewTask = null;
        this.mIsRemoveFocus = false;
        this.mIsUseMouseWithRight = false;
        this.mSelectableSpanListener = new MySelectableSpanListener(this);
        this.mExternalClickListener = null;
        this.mSpanClickListener = null;
        this.mActionListener = null;
        this.mTextStyleStateListener = null;
        this.mCursorClickListener = null;
        this.mLoadingLocker = null;
        this.mSelectLineDrawAnim = null;
        this.mSelectLinePaint = new Paint();
        this.mSelectLine = 0;
        this.mSelectLineTop = 0;
        this.mSelectLineLengthHalf = 0;
        this.mSelectLineAlpha = 0;
        this.mSelectLineAnimFraction = 0.0f;
        this.mLastTouchDownX = -1.0f;
        this.mLastTouchDownY = -1.0f;
        this.mEditorTheme = null;
        this.mRichTextData = null;
        this.mRichTextWatcher = null;
        this.mExternalTextWatcher = null;
        this.mUndoManager = null;
        this.mStyleFactory = null;
        this.mEditorImageRender = null;
        this.mSmoothDrawHelper = null;
        this.mLinkCardSpanHelper = null;
        this.mSelectImageSpan = null;
        this.mDraggingImageSpan = null;
        this.mMediaHandler = null;
        this.mInputModeInfo = null;
        this.mDataId = -1L;
        this.mTitle = null;
        this.mQueryContent = null;
        this.mHighLightKeyWords = null;
        this.handler = new MainHandler<>(Looper.getMainLooper(), null);
        this.mAudioFocusChangeListener = new AudioFocusChangeListener(this);
        this.mPlaybackDelayed = false;
        this.mResumeOnFocusGain = false;
        this.mAudioFocusLock = new Object();
        this.mIsRecording = false;
        this.mAudioGestureMoveInWave = false;
        this.mLastPlayedAudioFile = "";
        this.mExploreByTouchHelper = null;
        this.mPrepareImagesRunnable = new Runnable() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.prepareImages();
            }
        };
        setEditableFactory(EditableFactory.getInstance());
        initEditorConfig();
        initStyleFactory();
        initFontSettings();
        initImageRender();
        this.mMediaHandler = createMediaHandler();
        this.mSpanDetector = new ClickableSpanDetectorManager(this);
        this.mPendingTask = new ArrayList<>(1);
        this.mWindowWidth = CommonUtils.getWindowWidth(getContext());
        this.mSmoothDrawHelper = new SmoothDrawHelper();
        this.mViewConfiguration = ViewConfiguration.get(context);
        this.mUndoManager = createUndoManager();
        RichTextWatcher richTextWatcher = new RichTextWatcher(this);
        this.mRichTextWatcher = richTextWatcher;
        addTextChangedListener(richTextWatcher);
        this.mInputModeInfo = new InputModeRichStyleHelper(this);
        initAccessibility();
    }

    protected void initAccessibility() {
        RichEditTextViewAccessHelper richEditTextViewAccessHelper = new RichEditTextViewAccessHelper(this);
        this.mExploreByTouchHelper = richEditTextViewAccessHelper;
        ViewCompat.setAccessibilityDelegate(this, richEditTextViewAccessHelper);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getDataParseTask() == null || getDataParseTask().getStatus() != AsyncTask.Status.PENDING || getMeasuredWidth() <= 0) {
            return;
        }
        getDataParseTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getRichTextData());
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInDragMode() || this.mSelectLine == -2) {
            return;
        }
        canvas.save();
        int width = getWidth() / 2;
        int i = this.mSelectLineLengthHalf;
        int i2 = this.mSelectLineTop;
        canvas.drawLine((width - i) + 3, i2, (width + i) - 3, i2, this.mSelectLinePaint);
        canvas.restore();
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.mSpanDetector.hasHandleMotionEvent(motionEvent) || super.dispatchTouchEvent(motionEvent);
    }

    public void setActivity(Activity activity) {
        this.mActivityWeakRef = new WeakReference<>(activity);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (RomUtils.isPadDevice() && motionEvent.getToolType(0) == 3) {
            this.mIsUseMouseWithRight = motionEvent.getButtonState() == 2 || (this.mIsUseMouseWithRight && motionEvent.getButtonState() == 0);
        } else {
            this.mIsUseMouseWithRight = false;
        }
        try {
            if (!super.onTouchEvent(motionEvent)) {
                return false;
            }
            if (motionEvent.getActionMasked() == 0) {
                this.mLastTouchDownX = motionEvent.getX();
                this.mLastTouchDownY = motionEvent.getY();
            } else if (motionEvent.getActionMasked() == 1 && Math.abs(this.mLastTouchDownX - motionEvent.getX()) < this.mViewConfiguration.getScaledTouchSlop() && Math.abs(this.mLastTouchDownY - motionEvent.getY()) < this.mViewConfiguration.getScaledTouchSlop()) {
                updateCursorVisible();
                View.OnClickListener onClickListener = this.mExternalClickListener;
                if (onClickListener != null) {
                    onClickListener.onClick(this);
                }
            }
            return true;
        } catch (Exception e) {
            Logger.INSTANCE.w(TAG, "onTouchEvent error: " + e.toString());
            return false;
        }
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.TextView, android.view.View
    public boolean onDragEvent(DragEvent dragEvent) {
        if (dragEvent.getAction() == 1) {
            return isValidDragEventData(dragEvent);
        }
        return super.onDragEvent(dragEvent);
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        int selectionStart;
        if (i == 67 && (selectionStart = getSelectionStart()) == getSelectionEnd() && selectionStart > 1 && getText() != null) {
            int i2 = selectionStart - 1;
            if (getText().charAt(i2) == '\n' && getSpanStartAt(getText(), NormalImageSpan.class, selectionStart) != null && getSpanEndAt(getText(), NormalImageSpan.class, i2) == null && getText().charAt(selectionStart - 2) != '\n') {
                setSelection(i2);
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onCheckIsTextEditor() {
        return !isReadOnlyMode() && super.onCheckIsTextEditor();
    }

    @Override // android.widget.TextView
    public boolean hasSelection() {
        boolean zHasSelection = super.hasSelection();
        if (zHasSelection && StringUtils.LF.equals(getSelectedRichText())) {
            return false;
        }
        if (this.mSelectImageSpan != null) {
            return true;
        }
        return zHasSelection;
    }

    String getSelectedRichText() {
        Editable text = getText();
        if (text == null) {
            return "";
        }
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        return String.valueOf(selectionStart > selectionEnd ? text.subSequence(selectionEnd, selectionStart) : text.subSequence(selectionStart, selectionEnd));
    }

    /* JADX WARN: Code duplicated, block: B:66:0x014d  */
    /* JADX WARN: Code duplicated, block: B:69:0x015f  */
    /* JADX WARN: Code duplicated, block: B:71:0x0165  */
    /* JADX WARN: Code duplicated, block: B:72:0x016a  */
    /* JADX WARN: Code duplicated, block: B:80:0x019b  */
    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.EditText, android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        RichEditTextView richEditTextView;
        SafeSpannableStringBuilder safeSpannableStringBuilder;
        int iMin;
        int iMax;
        Editable editable;
        ClipData primaryClip;
        if (!needHandleEditMenuItem()) {
            return super.onTextContextMenuItem(i);
        }
        boolean zStartCopy = true;
        switch (i) {
            case android.R.id.cut:
                richEditTextView = this;
                if (richEditTextView.isReadOnlyMode()) {
                    return true;
                }
                Logger.INSTANCE.i(TAG, "onTextContextMenuItem COPY");
                safeSpannableStringBuilder = new SafeSpannableStringBuilder(richEditTextView.getText());
                iMin = Math.min(richEditTextView.getSelectionStart(), richEditTextView.getSelectionEnd());
                iMax = Math.max(richEditTextView.getSelectionStart(), richEditTextView.getSelectionEnd());
                if (richEditTextView.mSelectImageSpan != null && richEditTextView.getText() != null) {
                    iMin = richEditTextView.getText().getSpanStart(richEditTextView.mSelectImageSpan);
                    iMax = richEditTextView.getText().getSpanEnd(richEditTextView.mSelectImageSpan);
                }
                Logger.INSTANCE.i(TAG, iMin + StringUtils.SPACE + iMax);
                if (iMin >= 0 && iMax >= 0 && iMax > iMin) {
                    editable = (Editable) safeSpannableStringBuilder.subSequence(iMin, iMax);
                    if (richEditTextView.getText() != null) {
                        NoteClipManager.INSTANCE.clipContent(editable);
                    }
                    if (!"￼".equals(editable.toString())) {
                        if (!Utils.isSupportSuperClipboard()) {
                            zStartCopy = super.onTextContextMenuItem(i);
                        } else {
                            if (i == 16908320 && richEditTextView.getText() != null) {
                                richEditTextView.getText().delete(iMin, iMax);
                            }
                            richEditTextView.stopActionMode();
                            zStartCopy = NoteClipManager.INSTANCE.startCopy(richEditTextView.getContext(), editable, richEditTextView);
                        }
                    }
                    primaryClip = EditorUtils.getPrimaryClip((ClipboardManager) richEditTextView.getContext().getSystemService("clipboard"));
                    if (primaryClip != null) {
                        NoteClipManager.INSTANCE.setTimeStamp(primaryClip.getDescription().getTimestamp());
                    }
                }
                return zStartCopy;
            case android.R.id.copy:
                richEditTextView = this;
                Logger.INSTANCE.i(TAG, "onTextContextMenuItem COPY");
                safeSpannableStringBuilder = new SafeSpannableStringBuilder(richEditTextView.getText());
                iMin = Math.min(richEditTextView.getSelectionStart(), richEditTextView.getSelectionEnd());
                iMax = Math.max(richEditTextView.getSelectionStart(), richEditTextView.getSelectionEnd());
                if (richEditTextView.mSelectImageSpan != null) {
                    iMin = richEditTextView.getText().getSpanStart(richEditTextView.mSelectImageSpan);
                    iMax = richEditTextView.getText().getSpanEnd(richEditTextView.mSelectImageSpan);
                }
                Logger.INSTANCE.i(TAG, iMin + StringUtils.SPACE + iMax);
                if (iMin >= 0) {
                    editable = (Editable) safeSpannableStringBuilder.subSequence(iMin, iMax);
                    if (richEditTextView.getText() != null) {
                        NoteClipManager.INSTANCE.clipContent(editable);
                    }
                    if (!"￼".equals(editable.toString())) {
                        if (!Utils.isSupportSuperClipboard()) {
                            zStartCopy = super.onTextContextMenuItem(i);
                        } else {
                            if (i == 16908320) {
                                richEditTextView.getText().delete(iMin, iMax);
                            }
                            richEditTextView.stopActionMode();
                            zStartCopy = NoteClipManager.INSTANCE.startCopy(richEditTextView.getContext(), editable, richEditTextView);
                        }
                    }
                    primaryClip = EditorUtils.getPrimaryClip((ClipboardManager) richEditTextView.getContext().getSystemService("clipboard"));
                    if (primaryClip != null) {
                        NoteClipManager.INSTANCE.setTimeStamp(primaryClip.getDescription().getTimestamp());
                    }
                }
                return zStartCopy;
            case android.R.id.paste:
                if (isReadOnlyMode()) {
                    return true;
                }
                Logger.INSTANCE.i(TAG, "onTextContextMenuItem PASTE");
                this.mIsRemoveFocus = true;
                NoteClipManager.INSTANCE.getClipData(getContext(), false);
                if (!PreferenceUtils.isCTAAccepted(getContext()) && NoteClipManager.INSTANCE.isNeedSynchronize(getContext()) && NoteClipManager.INSTANCE.checkNeedNetwork()) {
                    WeakReference<Activity> weakReference = this.mActivityWeakRef;
                    Activity activity = weakReference == null ? null : weakReference.get();
                    if (activity instanceof NotesListActivity) {
                        ((NotesListActivity) activity).showCtaForPaste(this);
                    } else if (activity instanceof EditActivity) {
                        ((EditActivity) activity).showCtaForPaste(this);
                    }
                    return true;
                }
                if (PreferenceUtils.isCTAAccepted(getContext())) {
                    NoteClipManager.INSTANCE.imageDownloadAndPaste(getContext(), null, this, false, LiteUtils.isSuperLite());
                } else {
                    NoteClipManager.INSTANCE.buildSpanStringAndPaste(getContext(), this, LiteUtils.isSuperLite());
                }
                return true;
            case android.R.id.undo:
                if (isReadOnlyMode()) {
                    return true;
                }
                if (getUndoManager().canUndo()) {
                    getUndoManager().undo();
                    return true;
                }
                return false;
            case android.R.id.redo:
                if (isReadOnlyMode()) {
                    return true;
                }
                if (getUndoManager().canRedo()) {
                    getUndoManager().redo();
                    return true;
                }
                return false;
            default:
                return super.onTextContextMenuItem(i);
        }
    }

    private void handleFormatCompat(int i) {
        if (getText() != null) {
            String string = getText().toString();
            while (string.contains(MusicLyricParser.CRLF)) {
                int iIndexOf = string.indexOf(MusicLyricParser.CRLF);
                getText().replace(iIndexOf, iIndexOf + 1, "");
                string = getText().toString();
            }
            Editable text = getText();
            for (BackgroundColorSpan backgroundColorSpan : (BackgroundColorSpan[]) text.getSpans(0, text.length(), BackgroundColorSpan.class)) {
                if (text.getSpanStart(backgroundColorSpan) == i) {
                    getText().removeSpan(backgroundColorSpan);
                }
            }
        }
    }

    protected void processCopiedText(Spannable spannable) {
        for (NormalImageSpan normalImageSpan : (NormalImageSpan[]) spannable.getSpans(0, spannable.length(), NormalImageSpan.class)) {
            if (spannable.getSpanStart(normalImageSpan) != -1) {
                int spanStart = spannable.getSpanStart(normalImageSpan);
                int spanEnd = spannable.getSpanEnd(normalImageSpan);
                spannable.removeSpan(normalImageSpan);
                NormalImageSpan normalImageSpanCopyNormalImageSpan = getStyleFactory().copyNormalImageSpan(normalImageSpan);
                normalImageSpanCopyNormalImageSpan.initByContainer();
                spannable.setSpan(normalImageSpanCopyNormalImageSpan, spanStart, spanEnd, 33);
            }
        }
        for (LinkCardSpan linkCardSpan : (LinkCardSpan[]) spannable.getSpans(0, spannable.length(), LinkCardSpan.class)) {
            if (spannable.getSpanStart(linkCardSpan) != -1) {
                int spanStart2 = spannable.getSpanStart(linkCardSpan);
                int spanEnd2 = spannable.getSpanEnd(linkCardSpan);
                spannable.removeSpan(linkCardSpan);
                spannable.setSpan(getStyleFactory().copyLinkCardSpan(linkCardSpan), spanStart2, spanEnd2, 33);
            }
        }
        for (CheckboxSpan checkboxSpan : (CheckboxSpan[]) spannable.getSpans(0, spannable.length(), CheckboxSpan.class)) {
            if (spannable.getSpanStart(checkboxSpan) != -1) {
                int spanStart3 = spannable.getSpanStart(checkboxSpan);
                int spanEnd3 = spannable.getSpanEnd(checkboxSpan);
                spannable.removeSpan(checkboxSpan);
                spannable.setSpan(new CheckboxSpan(this, checkboxSpan.getElement().isChecked()), spanStart3, spanEnd3, 17);
            }
        }
        for (StyleSpan styleSpan : (StyleSpan[]) spannable.getSpans(0, spannable.length(), StyleSpan.class)) {
            if (spannable.getSpanStart(styleSpan) != -1) {
                int spanStart4 = spannable.getSpanStart(styleSpan);
                int spanEnd4 = spannable.getSpanEnd(styleSpan);
                spannable.removeSpan(styleSpan);
                spannable.setSpan(new StyleSpan(styleSpan.getStyle()), spanStart4, spanEnd4, 33);
            }
        }
        for (Object obj : (BgHighLightSpan[]) spannable.getSpans(0, spannable.length(), BgHighLightSpan.class)) {
            if (spannable.getSpanStart(obj) != -1) {
                int spanStart5 = spannable.getSpanStart(obj);
                int spanEnd5 = spannable.getSpanEnd(obj);
                spannable.removeSpan(obj);
                spannable.setSpan(new BgHighLightSpan(this), spanStart5, spanEnd5, 33);
            }
        }
        for (RelativeSizeSpan relativeSizeSpan : (RelativeSizeSpan[]) spannable.getSpans(0, spannable.length(), RelativeSizeSpan.class)) {
            if (spannable.getSpanStart(relativeSizeSpan) != -1) {
                int spanStart6 = spannable.getSpanStart(relativeSizeSpan);
                int spanEnd6 = spannable.getSpanEnd(relativeSizeSpan);
                spannable.removeSpan(relativeSizeSpan);
                spannable.setSpan(new RelativeSizeSpan(relativeSizeSpan.getSizeChange()), spanStart6, spanEnd6, 33);
            }
        }
        for (AbsoluteSizeSpan absoluteSizeSpan : (AbsoluteSizeSpan[]) spannable.getSpans(0, spannable.length(), AbsoluteSizeSpan.class)) {
            if (spannable.getSpanStart(absoluteSizeSpan) != -1) {
                int spanStart7 = spannable.getSpanStart(absoluteSizeSpan);
                int spanEnd7 = spannable.getSpanEnd(absoluteSizeSpan);
                spannable.removeSpan(absoluteSizeSpan);
                spannable.setSpan(new AbsoluteSizeSpan(absoluteSizeSpan.getSize(), false), spanStart7, spanEnd7, 33);
            }
        }
        for (Object obj2 : (UnderlineSpan[]) spannable.getSpans(0, spannable.length(), UnderlineSpan.class)) {
            if (spannable.getSpanStart(obj2) != -1) {
                int spanStart8 = spannable.getSpanStart(obj2);
                int spanEnd8 = spannable.getSpanEnd(obj2);
                spannable.removeSpan(obj2);
                spannable.setSpan(new UnderlineSpan(), spanStart8, spanEnd8, 33);
            }
        }
        for (AlignmentSpan alignmentSpan : (AlignmentSpan[]) spannable.getSpans(0, spannable.length(), AlignmentSpan.class)) {
            if (spannable.getSpanStart(alignmentSpan) != -1) {
                int spanStart9 = spannable.getSpanStart(alignmentSpan);
                int spanEnd9 = spannable.getSpanEnd(alignmentSpan);
                spannable.removeSpan(alignmentSpan);
                spannable.setSpan(new AlignmentSpan.Standard(alignmentSpan.getAlignment()), spanStart9, spanEnd9, 18);
            }
        }
    }

    public boolean doPaste(Spannable spannable) {
        setSkipRichText(true);
        getRichTextWatcher().mDoParse = true;
        clearRichTextData();
        int selectionStart = getSelectionStart();
        if (getText() != null) {
            for (BaseImageSpan baseImageSpan : (BaseImageSpan[]) getText().getSpans(getSelectionStart(), getSelectionEnd(), BaseImageSpan.class)) {
                int spanStart = getText().getSpanStart(baseImageSpan);
                int spanEnd = getText().getSpanEnd(baseImageSpan);
                if (spanStart >= getSelectionStart() && spanEnd <= getSelectionEnd()) {
                    getText().removeSpan(baseImageSpan);
                    baseImageSpan.recycle();
                }
            }
            if (spannable != null) {
                processCopiedText(spannable);
            }
            replaceSelection(getText(), spannable);
            refreshContainer();
        } else {
            updateContent(spannable, null);
            refreshContainer();
        }
        setSkipRichText(false);
        getRichTextWatcher().mDoParse = false;
        handleFormatCompat(selectionStart);
        return true;
    }

    protected static void replaceSelection(Editable editable, CharSequence charSequence) {
        if (editable == null || charSequence == null) {
            return;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        int iMax = Math.max(0, Math.min(selectionStart, selectionEnd));
        int iMax2 = Math.max(0, Math.max(selectionStart, selectionEnd));
        Selection.setSelection(editable, iMax2);
        editable.replace(iMax, iMax2, charSequence);
    }

    @Override // android.widget.TextView, android.view.View
    public void onCreateContextMenu(ContextMenu contextMenu) {
        super.onCreateContextMenu(contextMenu);
        IRichEditor.CursorClickListener cursorClickListener = this.mCursorClickListener;
        if (cursorClickListener != null) {
            cursorClickListener.onLongClick();
        }
    }

    @Deprecated
    private void addExtraMenuAction(ContextMenu contextMenu) {
        MenuItem menuItemFindItem;
        int iCalcTextOffset = calcTextOffset(this.mLastTouchDownX, this.mLastTouchDownY);
        if (iCalcTextOffset < 0) {
            return;
        }
        final URLSpan[] uRLSpanArr = (URLSpan[]) getText().getSpans(iCalcTextOffset, iCalcTextOffset, URLSpan.class);
        if (uRLSpanArr.length > 0) {
            new MenuInflater(getContext()).inflate(R.menu.rich_editor_link_action, contextMenu);
            final String url = uRLSpanArr[0].getURL();
            if (url.startsWith(IRichEditor.SCHEME_TEL)) {
                menuItemFindItem = contextMenu.findItem(R.id.menu_action_call);
            } else if (url.startsWith(IRichEditor.SCHEME_HTTP) || url.startsWith(IRichEditor.SCHEME_HTTPS)) {
                menuItemFindItem = contextMenu.findItem(R.id.menu_action_web);
            } else if (url.startsWith("mailto:")) {
                menuItemFindItem = contextMenu.findItem(R.id.menu_action_email);
            } else {
                menuItemFindItem = contextMenu.findItem(R.id.menu_action_other);
            }
            menuItemFindItem.setVisible(true);
            menuItemFindItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.miui.richeditor.RichEditTextView.1
                @Override // android.view.MenuItem.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem menuItem) {
                    try {
                        if (menuItem.getItemId() == R.id.menu_action_call) {
                            Context context = RichEditTextView.this.getContext();
                            context.startActivity(ExternalAppUtils.createSystemPhoneIntent(context, url));
                        } else {
                            uRLSpanArr[0].onClick(RichEditTextView.this);
                        }
                        return true;
                    } catch (ActivityNotFoundException e) {
                        Logger.INSTANCE.w(RichEditTextView.TAG, "Fail to perform url click" + e);
                        return true;
                    }
                }
            });
            int size = contextMenu.size();
            for (int i = 0; i < size; i++) {
                MenuItem item = contextMenu.getItem(i);
                if (item != menuItemFindItem && item.isVisible()) {
                    item.setOnMenuItemClickListener(this);
                }
            }
        }
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.menu_action_select) {
            return false;
        }
        int iCalcTextOffset = calcTextOffset(this.mLastTouchDownX, this.mLastTouchDownY);
        if (iCalcTextOffset < 0 || ((URLSpan[]) getText().getSpans(iCalcTextOffset, iCalcTextOffset, URLSpan.class)).length <= 0) {
            return true;
        }
        analogClickEvent(this.mLastTouchDownX, this.mLastTouchDownY);
        analogClickEvent(this.mLastTouchDownX, this.mLastTouchDownY);
        return true;
    }

    private int calcTextOffset(float f, float f2) {
        Layout layout;
        float paddingStart = f - ((-getScrollX()) + getPaddingStart());
        float paddingTop = f2 - ((-getScrollY()) + getPaddingTop());
        if (paddingStart < 0.0f || paddingTop < 0.0f || (layout = getLayout()) == null) {
            return -1;
        }
        int i = (int) paddingTop;
        int lineForVertical = layout.getLineForVertical(i);
        Rect rect = new Rect();
        layout.getLineBounds(lineForVertical, rect);
        if (!rect.contains((int) paddingStart, i)) {
            return -1;
        }
        int offsetForHorizontal = layout.getOffsetForHorizontal(lineForVertical, paddingStart);
        if (offsetForHorizontal > layout.getLineEnd(lineForVertical)) {
            return -1;
        }
        return offsetForHorizontal;
    }

    private void analogClickEvent(float f, float f2) {
        analogMotionEvent(f, f2, 0);
        analogMotionEvent(f, f2, 1);
    }

    private void analogMotionEvent(float f, float f2, int i) {
        MotionEvent motionEventObtain = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), i, f, f2, 0);
        try {
            super.dispatchTouchEvent(motionEventObtain);
        } finally {
            motionEventObtain.recycle();
        }
    }

    protected int getSelectLineWidth() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.notes_edit_selectline_height);
    }

    @Override // android.widget.TextView
    public void onSelectionChanged(int i, int i2) {
        int i3;
        int iIndexOf;
        int length;
        int i4;
        int i5;
        Logger.INSTANCE.d(TAG, "onSelectionChanged start " + i + " end " + i2);
        TextStyleStateListener textStyleStateListener = this.mTextStyleStateListener;
        if (textStyleStateListener != null) {
            textStyleStateListener.isNeedLock(i == i2);
        }
        if (i > i2) {
            iIndexOf = i;
            i3 = i2;
        } else {
            i3 = i;
            iIndexOf = i2;
        }
        if (i3 >= 0 && isAttachedToWindow() && getInputHelper().getInputModeIndex() != i3) {
            getInputHelper().updateInputModeIndex(-1);
        }
        Editable text = getText();
        if (TextUtils.isEmpty(text)) {
            super.onSelectionChanged(i, i2);
            changeStyleStatus(i3, iIndexOf);
            changeAlignStatus(i3, iIndexOf);
            return;
        }
        if (isSkipSelectionChanged()) {
            super.onSelectionChanged(i, i2);
            return;
        }
        if (i < 0 || i2 < 0) {
            return;
        }
        if (iIndexOf >= text.length() || !text.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, iIndexOf)) {
            length = i3;
            i4 = iIndexOf;
        } else {
            length = EditorUtils.ZERO_WIDTH_STRING.length() + iIndexOf;
            i4 = length;
            if (i3 != iIndexOf) {
                length = i3;
            }
        }
        if ((length == 0 || ((i5 = length - 1) >= 0 && i5 < text.length() && text.charAt(i5) == '\n')) && i4 != text.length() && i4 < text.length() && text.charAt(i4) != '\n' && text.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, length) && i4 - length > EditorUtils.ZERO_WIDTH_STRING.length()) {
            length += EditorUtils.ZERO_WIDTH_STRING.length();
        }
        if (i3 != length || iIndexOf != i4) {
            setSelection(length, i4);
            return;
        }
        if (i3 == iIndexOf) {
            if (getSpanEndAt(getText(), BaseImageSpan.class, iIndexOf) != null) {
                if (!CommonUtils.isAboveAndroidS()) {
                    setHapticFeedbackEnabled(false);
                }
                do {
                    iIndexOf = getText().toString().indexOf(10, iIndexOf + 1);
                } while (getSpanEndAt(getText(), BaseImageSpan.class, iIndexOf) != null);
                if (iIndexOf < 0) {
                    iIndexOf = getText().length();
                }
                setSelection(getText().toString().lastIndexOf(10, iIndexOf - 1) + 1);
                if (CommonUtils.isAboveAndroidS()) {
                    return;
                }
                setHapticFeedbackEnabled(true);
                return;
            }
            if (!CommonUtils.isAboveAndroidS()) {
                setHapticFeedbackEnabled(getSpansAt(getText(), BaseImageSpan.class, i3) == null);
            }
        }
        super.onSelectionChanged(i3, iIndexOf);
        changeStyleStatus(i3, iIndexOf);
        changeAlignStatus(i3, iIndexOf);
        Logger.INSTANCE.d(TAG, "onSelectionChanged: changeAlignStatus end! ");
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        this.mAttachedToWindow = true;
        super.onAttachedToWindow();
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.view.View
    protected void onDetachedFromWindow() {
        Logger.INSTANCE.d(TAG, "onDetachedFromWindow " + this.handler);
        MainHandler<RichEditTextView> mainHandler = this.handler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        cancelBackgroundTask();
        this.mAttachedToWindow = false;
        if (getText() != null) {
            for (BaseImageSpan baseImageSpan : (BaseImageSpan[]) getText().getSpans(0, getText().length(), BaseImageSpan.class)) {
                baseImageSpan.onDestroy();
            }
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.TextView, android.view.View
    public Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        if (getDataParseTask() != null) {
            return null;
        }
        return createSavedState(this);
    }

    protected RichSavedState createSavedState(RichEditTextView richEditTextView) {
        return new RichSavedState(richEditTextView);
    }

    protected void restoreSavedState() {
        setScrollY(this.mState.scrollY);
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(View.BaseSavedState.EMPTY_STATE);
        this.mState = (RichSavedState) parcelable;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setExternalClickListener(View.OnClickListener onClickListener) {
        this.mExternalClickListener = onClickListener;
    }

    public void cancelBackgroundTask() {
        Logger.INSTANCE.d(TAG, "cancelBackgroundTask " + this.mImageInsertTask + "," + this.mDataParseTask);
        DataHtmlParseTask dataHtmlParseTask = this.mDataParseTask;
        if (dataHtmlParseTask != null) {
            dataHtmlParseTask.restoreAndCancel();
        }
        ImageInsertTask imageInsertTask = this.mImageInsertTask;
        if (imageInsertTask != null) {
            imageInsertTask.restoreAndCancel();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setDataId(long j) {
        this.mDataId = j;
    }

    @Override // com.miui.richeditor.IRichEditor
    public long getDataId() {
        return this.mDataId;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setTitle(String str) {
        if (str.equals(this.mTitle)) {
            return;
        }
        this.mTitle = str;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void saveInstanceState(Bundle bundle) {
        int[] iArr = new int[2];
        if (this.mPlayingAudioSpan != null && getText() != null) {
            iArr[0] = getText().getSpanStart(this.mPlayingAudioSpan);
            iArr[1] = getText().getSpanEnd(this.mPlayingAudioSpan);
            bundle.putIntArray("playingAudioSpan_range", iArr);
            cancelPlayAnimIfNeed();
            stopMediaPlayerIfNeed();
        }
        if (this.mSelectImageSpan == null || getText() == null) {
            return;
        }
        iArr[0] = getText().getSpanStart(this.mSelectImageSpan);
        iArr[1] = getText().getSpanEnd(this.mSelectImageSpan);
        bundle.putIntArray("selectImageSpan_range", iArr);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void restoreInstanceState(Bundle bundle) {
        int[] intArray;
        int[] intArray2;
        int[] intArray3;
        if (bundle.containsKey("playingAudioSpan_range") && (intArray3 = bundle.getIntArray("playingAudioSpan_range")) != null && getText() != null && intArray3[1] > intArray3[0]) {
            BaseAudioSpan[] baseAudioSpanArr = (BaseAudioSpan[]) getText().getSpans(intArray3[0], intArray3[1], BaseAudioSpan.class);
            if (baseAudioSpanArr.length > 0) {
                this.mPlayingAudioSpan = baseAudioSpanArr[0];
                if (!TextUtils.isEmpty(this.pTempSaveAudioFile)) {
                    onAudioEndAndFileSaved(this.pTempSaveAudioFile);
                    this.pTempSaveAudioFile = null;
                }
                Logger.INSTANCE.d(TAG, "restore find playingAudioSpan");
            } else {
                this.mPlayingAudioSpan = this.mStyleFactory.setAudioSpan(getText(), intArray3[0], intArray3[1], "temp_audio_file");
            }
        }
        if (bundle.containsKey("selectImageSpan_range") && (intArray2 = bundle.getIntArray("selectImageSpan_range")) != null && getText() != null && intArray2[1] > intArray2[0]) {
            NormalImageSpan[] normalImageSpanArr = (NormalImageSpan[]) getText().getSpans(intArray2[0], intArray2[1], NormalImageSpan.class);
            if (normalImageSpanArr.length > 0) {
                this.mSelectImageSpan = normalImageSpanArr[0];
                Logger.INSTANCE.d(TAG, "find mSelectImageSpan");
            }
        }
        if (!bundle.containsKey("edit_cache_selection") || (intArray = bundle.getIntArray("edit_cache_selection")) == null) {
            return;
        }
        setSelection(intArray[0], intArray[1]);
        cacheSelection(true);
    }

    @Override // com.miui.richeditor.IRichEditor
    public HtmlParser.IMediaHandler createMediaHandler() {
        return new RichEditorContentHandler(this);
    }

    @Override // com.miui.richeditor.IRichEditor
    public HtmlParser.IMediaHandler getMediaHandler() {
        return this.mMediaHandler;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void updateContent(CharSequence charSequence, String str) {
        String str2 = this.mQueryContent;
        setQueryContent(str);
        if ((str2 == null && this.mQueryContent != null) || (str2 != null && !str2.equalsIgnoreCase(this.mQueryContent))) {
            clearRichTextData();
        }
        parseRichTextData(charSequence);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void clearImageInsertTask() {
        Logger.INSTANCE.d(TAG, "clearImageInsertTask,set mImageInsertTask to null");
        this.mImageInsertTask = null;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void onThemeChanged(Theme theme) {
        Logger.INSTANCE.d(TAG, "onThemeChanged");
        Theme theme2 = this.mEditorTheme;
        if (theme2 == null || theme2.getId() != theme.getId()) {
            this.mEditorTheme = theme;
            this.mEditorImageRender.onThemeChanged();
            this.mSelectLinePaint.setStyle(Paint.Style.STROKE);
            this.mSelectLinePaint.setStrokeCap(Paint.Cap.ROUND);
            this.mSelectLinePaint.setStrokeWidth(getSelectLineWidth());
            try {
                this.mSelectLinePaint.setColor(getContext().getColor(this.mEditorTheme.getEditorStyle(getContext(), System.currentTimeMillis()).mCursorColor));
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "onThemeChanged " + e);
            }
            this.mTheme = theme;
            this.isThemeChanged = true;
            stopMediaPlayerIfNeed();
            this.handler.post(new Runnable() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.updateAllAudioSpans();
                }
            });
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public ImageInsertTask getImageInsertTask() {
        return this.mImageInsertTask;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void parseRichTextData(CharSequence charSequence) {
        CharSequence charSequence2 = this.mRichTextData;
        if (charSequence2 != null && charSequence2.equals(charSequence)) {
            View view = this.mEditAreaContainer;
            if (view != null) {
                view.requestLayout();
                this.mEditAreaContainer.invalidate();
            }
            EditorActionListener editorActionListener = this.mActionListener;
            if (editorActionListener != null) {
                editorActionListener.onStopShowLoading();
                return;
            }
            return;
        }
        if (getText() != null) {
            getText().clearSpans();
        }
        this.mRichTextData = charSequence;
        DataHtmlParseTask dataHtmlParseTask = this.mDataParseTask;
        if (dataHtmlParseTask != null) {
            dataHtmlParseTask.restoreAndCancel();
        }
        this.mDataParseTask = createDataParseTask();
        if (getMeasuredWidth() > 0) {
            this.mDataParseTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.mRichTextData);
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public DataHtmlParseTask createDataParseTask() {
        return new DataHtmlParseTask(this);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void clearDataParseTask() {
        this.mDataParseTask = null;
    }

    @Override // com.miui.richeditor.IRichEditor
    public DataHtmlParseTask getDataParseTask() {
        return this.mDataParseTask;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void onDataParseStart(DataHtmlParseTask dataHtmlParseTask) {
        Logger.INSTANCE.w(TAG, "onDataParseStart ");
    }

    @Override // com.miui.richeditor.IRichEditor
    public void onDataParseCancel(DataHtmlParseTask dataHtmlParseTask) {
        Logger.INSTANCE.w(TAG, "onDataParseCancel ");
        if (getDataParseTask() == dataHtmlParseTask) {
            clearDataParseTask();
        }
        EditorActionListener editorActionListener = this.mActionListener;
        if (editorActionListener != null) {
            editorActionListener.onRichTextLoaded();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void onDataParseComplete(DataHtmlParseTask dataHtmlParseTask, CharSequence[] charSequenceArr) {
        Logger.INSTANCE.w(TAG, "onDataParseComplete ");
        if (getDataParseTask() != dataHtmlParseTask) {
            Logger.INSTANCE.w(TAG, "task has changed, don't apply result");
            return;
        }
        if (charSequenceArr == null) {
            EditorActionListener editorActionListener = this.mActionListener;
            if (editorActionListener != null) {
                editorActionListener.onRichTextLoaded();
                return;
            }
            return;
        }
        doSetRichText(charSequenceArr[0], (Spanned) charSequenceArr[1]);
        clearDataParseTask();
        View view = this.mEditAreaContainer;
        if (view != null) {
            view.requestLayout();
            this.mEditAreaContainer.invalidate();
        }
        EditorActionListener editorActionListener2 = this.mActionListener;
        if (editorActionListener2 != null) {
            editorActionListener2.onRichTextLoaded();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void doSetRichText(CharSequence charSequence, Spanned spanned) {
        int iMin;
        int iMin2;
        int iIndexOf;
        if (charSequence != this.mRichTextData) {
            return;
        }
        setSkipSelectionChanged(true);
        clearTextWatcher();
        URLSpan[] uRLSpanArr = (URLSpan[]) spanned.getSpans(0, spanned.length(), URLSpan.class);
        int[] iArr = new int[uRLSpanArr.length];
        int[] iArr2 = new int[uRLSpanArr.length];
        for (int i = 0; i < uRLSpanArr.length; i++) {
            iArr[i] = spanned.getSpanStart(uRLSpanArr[i]);
            iArr2[i] = spanned.getSpanEnd(uRLSpanArr[i]);
        }
        this.mRichTextWatcher.setReplacing(true);
        clearText();
        int inputType = getInputType();
        setInputType(524288 | inputType);
        setText(spanned);
        setInputType(inputType);
        this.mRichTextWatcher.setReplacing(false);
        Editable text = getText();
        for (int i2 = 0; i2 < uRLSpanArr.length; i2++) {
            for (URLSpan uRLSpan : (URLSpan[]) text.getSpans(iArr[i2], iArr2[i2], URLSpan.class)) {
                text.removeSpan(uRLSpan);
            }
            if (iArr[i2] <= getContentLengthMax() && iArr2[i2] <= getContentLengthMax()) {
                text.setSpan(uRLSpanArr[i2], iArr[i2], iArr2[i2], 33);
            }
        }
        this.mRichTextData = charSequence;
        getEditorMode();
        getUndoManager().clear();
        RichSavedState richSavedState = this.mState;
        if (richSavedState == null || richSavedState.noteId != getDataId()) {
            iMin = 0;
            iMin2 = 0;
        } else {
            restoreSavedState();
            iMin = Math.min(Math.max(0, this.mState.selectionStart), getText().length());
            iMin2 = Math.min(this.mState.selectionEnd, getText().length());
        }
        this.mState = null;
        if (this.mQueryContent != null && (iIndexOf = getText().toString().indexOf(this.mQueryContent)) >= 0) {
            iMin = iIndexOf;
            iMin2 = iMin;
        }
        setSelection(iMin, iMin2);
        Iterator<Runnable> it = this.mPendingTask.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        clearPendingTask();
        this.mIsFirstLoad = false;
        updateFontSize();
        restoreTextWatcher();
        setSkipSelectionChanged(false);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void clearText() {
        Editable text = getText();
        for (Object obj : text.getSpans(0, text.length(), Object.class)) {
            if ((obj instanceof HtmlParser.IElementSpan) || (obj instanceof StrikethroughSpan)) {
                text.removeSpan(obj);
            }
        }
        setText("");
        this.mHasUnknownTag = false;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setQueryContent(String str) {
        this.mQueryContent = str;
    }

    @Override // com.miui.richeditor.IRichEditor
    public String getQueryContent() {
        return this.mQueryContent;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setHighLightKeyWords(ArrayList<String> arrayList) {
        this.mHighLightKeyWords = arrayList;
    }

    @Override // com.miui.richeditor.IRichEditor
    public ArrayList<String> getHighLightKeyWords() {
        return this.mHighLightKeyWords;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void hideInsertionPointCursorController() {
        if (this.mIsUseMouseWithRight) {
            return;
        }
        setSelection(getSelectionEnd());
        clearFocus();
    }

    @Override // android.view.View
    public void clearFocus() {
        if (this.mIsUseMouseWithRight) {
            return;
        }
        super.clearFocus();
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getReadableTextCount() {
        Editable text = getText();
        char cCharAt = EditorUtils.ZERO_WIDTH_STRING.charAt(0);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < text.length(); i6++) {
            char cCharAt2 = text.charAt(i6);
            int i7 = 1;
            i3 += cCharAt2 == '\n' ? 1 : 0;
            i4 += cCharAt2 == ' ' ? 1 : 0;
            i5 += "/[·~！@￥%…&（）\\-\\+\\*=【】{}、\\|；‘’：“”《》？，。、`~!#\\$%\\^&\\(\\)_\\[\\]\\{\\}|;'':\"\",\\\\\\.\\/<>\\?]/".indexOf(cCharAt2) != -1 ? 1 : 0;
            i += cCharAt2 == cCharAt ? 1 : 0;
            if (cCharAt2 != 65532) {
                i7 = 0;
            }
            i2 += i7;
        }
        int length = text.length() - ((((i + i2) + i3) + i4) + i5);
        if (length > 0) {
            return length;
        }
        return 0;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void addPendingTask(Runnable runnable) {
        if (getDataParseTask() != null || this.mRichTextData == null) {
            this.mPendingTask.add(runnable);
        } else {
            runnable.run();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void clearPendingTask() {
        ArrayList<Runnable> arrayList = this.mPendingTask;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setScrollView(RichEditScrollView richEditScrollView) {
        this.mScrollView = richEditScrollView;
    }

    @Override // com.miui.richeditor.IRichEditor
    public RichEditScrollView getScrollView() {
        return this.mScrollView;
    }

    @Override // com.miui.richeditor.IRichEditor, com.miui.richeditor.IEditorContext
    public void setEditAreaView(View view) {
        this.mEditAreaContainer = view;
    }

    @Override // com.miui.richeditor.IRichEditor, com.miui.richeditor.IEditorContext
    public View getEditAreaView() {
        return this.mEditAreaContainer;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setLoadingContainer(View view) {
        this.mLoadingContainer = view;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void updateContentWidth(int i) {
        this.mContentWidth = i;
        this.mEditorImageRender.updateContentWidth(i);
    }

    @Override // com.miui.richeditor.IRichEditor, com.miui.richeditor.IEditorContext
    public int getContentWidth() {
        return this.mContentWidth;
    }

    public void initStyleFactory() {
        this.mStyleFactory = new VisualStyleFactory(getContext(), this);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void initEditorConfig() {
        RichEditorConfig richEditorConfig = new RichEditorConfig();
        this.mEditorConfig = richEditorConfig;
        this.mFontLevelSize[0] = richEditorConfig.fontNormalSizeScale;
        this.mFontLevelSize[1] = this.mEditorConfig.fontMidSizeScale;
        this.mFontLevelSize[2] = this.mEditorConfig.fontLargeSizeScale;
    }

    @Override // com.miui.richeditor.IRichEditor
    public RichEditorConfig getEditorConfig() {
        return this.mEditorConfig;
    }

    public LinkCardSpanHelper createLinkCardSpanHelper() {
        return new LinkCardSpanHelper(getEditorTheme());
    }

    public void updateFontSizeId(int i) {
        if (this.mFontSizeId == i) {
            return;
        }
        this.mFontSizeId = i;
        this.mEditorImageRender.clearSpanDrawableCache();
        this.mFontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        this.mFontInnerScale = ResourceParser.TextAppearanceResources.FONT_SCALE[this.mFontSizeId];
        updateLineStyleSpanByFontSizeChange();
        setTextSize(2, ResourceParser.TextAppearanceResources.getEditFontSpSize(getContext(), this.mFontSizeId));
        updateFontSize();
        refreshContainer();
    }

    protected void updateLineStyleSpanByFontSizeChange() {
        if (getEditableText() != null) {
            for (EditLineHeightSpan editLineHeightSpan : (EditLineHeightSpan[]) getEditableText().getSpans(0, getEditableText().length(), EditLineHeightSpan.class)) {
                editLineHeightSpan.updateFontParams();
            }
            for (CheckboxSpan checkboxSpan : (CheckboxSpan[]) getEditableText().getSpans(0, getEditableText().length(), CheckboxSpan.class)) {
                checkboxSpan.updateFontParams();
            }
            for (EditorBulletSpan editorBulletSpan : (EditorBulletSpan[]) getEditableText().getSpans(0, getEditableText().length(), EditorBulletSpan.class)) {
                editorBulletSpan.updateFontParams();
            }
        }
    }

    protected void updateFontSize() {
        for (FontLargeSizeSpan fontLargeSizeSpan : getFontLargeSizeSpans(getEditableText(), 0, getEditableText().length())) {
            SpanInfo spanInfo = getSpanInfo(fontLargeSizeSpan);
            removeSpan(fontLargeSizeSpan);
            if (spanInfo.isValid()) {
                styleValid(spanInfo.getStyle(), spanInfo.getStart(), spanInfo.getEnd());
            }
        }
        for (FontMidSizeSpan fontMidSizeSpan : getFontMidSizeSpans(getEditableText(), 0, getEditableText().length())) {
            SpanInfo spanInfo2 = getSpanInfo(fontMidSizeSpan);
            removeSpan(fontMidSizeSpan);
            if (spanInfo2.isValid()) {
                styleValid(spanInfo2.getStyle(), spanInfo2.getStart(), spanInfo2.getEnd());
            }
        }
    }

    public void initFontSettings() {
        this.mFontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        this.mFontInnerScale = ResourceParser.TextAppearanceResources.FONT_SCALE[PreferenceUtils.getFontSize(getContext(), 1)];
    }

    public void initImageRender() {
        this.mEditorImageRender = new DefaultEditorImageRender(this, getContentWidth());
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getUndoStepCapacity() {
        return this.mEditorConfig.undoStepCapacity;
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getContentLengthMax() {
        return this.mEditorConfig.contentTextLengthMax;
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean canAddAttachments() {
        boolean z = getText().length() < getContentLengthMax();
        if (!z) {
            makeTextToToast(getContext(), R.string.toast_reach_text_limit_attachment, 0);
        }
        return z;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void forceUpdateLayout() {
        Logger.INSTANCE.d(TAG, "forceUpdateLayout");
        Editable text = getText();
        if (text == null) {
            return;
        }
        for (TextWatcher textWatcher : (TextWatcher[]) text.getSpans(0, text.length(), TextWatcher.class)) {
            if (textWatcher.getClass().toString().contains("android.text.DynamicLayout$ChangeWatcher")) {
                textWatcher.onTextChanged(text, 0, text.length(), text.length());
                return;
            }
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void invalidateDeeply() {
        Logger.INSTANCE.d(TAG, "invalidateDeeply");
        forceUpdateLayout();
        super.invalidate();
    }

    @Override // com.miui.richeditor.IRichEditor
    @Deprecated
    public void setReadOnly(boolean z) {
        this.mReadOnly = z;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void startDragMode() {
        this.mIsInDragMode = true;
        this.mScrollView.setIgnoreForbid(true);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void exitDragZone() {
        if (this.mSelectLine == 0) {
            this.mSelectLine = -2;
            this.mSelectLineTop = getLayout().getLineTop(0) + ((int) (this.mSelectLinePaint.getStrokeWidth() / 2.0f)) + getPaddingTop();
            startDrawSelectLineAnim();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void endDragMode() {
        this.mIsInDragMode = false;
        BaseImageSpan baseImageSpan = this.mDraggingImageSpan;
        if (baseImageSpan != null) {
            moveImageSpanAfterDragDrop(baseImageSpan, false);
            this.mDraggingImageSpan = null;
        }
        this.mSelectLine = -2;
        this.mScrollView.setIgnoreForbid(false);
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean isDragEventContainImage(DragEvent dragEvent) {
        String[] strArrFilterMimeTypes;
        ClipDescription clipDescription = dragEvent.getClipDescription();
        return (clipDescription == null || (strArrFilterMimeTypes = clipDescription.filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE)) == null || strArrFilterMimeTypes.length <= 0) ? false : true;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void getDragEventMimeType(DragEvent dragEvent) {
        ClipDescription clipDescription = dragEvent.getClipDescription();
        if (clipDescription != null) {
            if (clipDescription.hasMimeType(ShareAppsAdapter.TYPE_IMAGE)) {
                NotesTextStat.reportNoteDragEvent(AITextWidgetStat.TYPE_IMAGE);
            } else if (clipDescription.hasMimeType("audio/*")) {
                NotesTextStat.reportNoteDragEvent(AITextWidgetStat.TYPE_AUDIO);
            } else if (clipDescription.hasMimeType("linkcard/*")) {
                NotesTextStat.reportNoteDragEvent("linkcard");
            }
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean isValidDragEventData(DragEvent dragEvent) {
        if (dragEvent.getClipDescription() != null) {
            String[] strArrFilterMimeTypes = dragEvent.getClipDescription().filterMimeTypes("text/*");
            String[] strArrFilterMimeTypes2 = dragEvent.getClipDescription().filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE);
            if ((strArrFilterMimeTypes2 != null && strArrFilterMimeTypes2.length > 0) || (strArrFilterMimeTypes != null && strArrFilterMimeTypes.length > 0)) {
                return true;
            }
        }
        if (dragEvent.getClipData() == null || dragEvent.getClipData().getDescription() == null) {
            return false;
        }
        String[] strArrFilterMimeTypes3 = dragEvent.getClipData().getDescription().filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE);
        String[] strArrFilterMimeTypes4 = dragEvent.getClipData().getDescription().filterMimeTypes("text/*");
        return (strArrFilterMimeTypes3 != null && strArrFilterMimeTypes3.length > 0) || (strArrFilterMimeTypes4 != null && strArrFilterMimeTypes4.length > 0);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void prepareImages() {
        Logger.INSTANCE.d(TAG, "prepareImages");
        refreshVisibleRect();
        if (this.mLocalVisibleRect.isEmpty()) {
            this.handler.removeCallbacks(this.mPrepareImagesRunnable);
            if (this.mAttachedToWindow) {
                this.handler.postDelayed(this.mPrepareImagesRunnable, 50L);
                return;
            }
            return;
        }
        int y = (int) (this.mLocalVisibleRect.top - getY());
        int iHeight = this.mLocalVisibleRect.height();
        int iMax = Math.max(0, y - getPaddingTop());
        int paddingTop = (y + iHeight) - getPaddingTop();
        Layout layout = getLayout();
        if (layout == null) {
            return;
        }
        int lineForVertical = layout.getLineForVertical(iMax);
        int lineForVertical2 = layout.getLineForVertical(paddingTop);
        int lineStart = layout.getLineStart(lineForVertical);
        int lineEnd = layout.getLineEnd(lineForVertical2);
        Editable text = getText();
        for (BaseImageSpan baseImageSpan : (BaseImageSpan[]) text.getSpans(0, text.length(), BaseImageSpan.class)) {
            int spanStart = text.getSpanStart(baseImageSpan);
            if (spanStart >= lineStart && spanStart <= lineEnd) {
                baseImageSpan.prepare();
            } else {
                baseImageSpan.recycle();
            }
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void drawImages(Canvas canvas) {
        if (getVisibility() != 0) {
            return;
        }
        refreshVisibleRect();
        if (this.mLocalVisibleRect.isEmpty()) {
            return;
        }
        for (BaseImageSpan baseImageSpan : (BaseImageSpan[]) getText().getSpans(0, getText().length(), BaseImageSpan.class)) {
            if (Rect.intersects(this.mLocalVisibleRect, baseImageSpan.getBounds())) {
                baseImageSpan.doDraw(canvas);
            }
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void insertImages(final Runnable runnable, final Uri... uriArr) {
        Logger.INSTANCE.d(TAG, "insertImages");
        if (isInDragMode()) {
            int i = this.mSelectLine;
            if (i == -1) {
                i = 0;
            }
            if (i >= 0) {
                setSelection(getLayout().getLineEnd(i));
                cacheSelection(true);
            }
        }
        if (this.mDataParseTask != null && getVisibility() != 0) {
            addPendingTask(new Runnable() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$insertImages$0(runnable, uriArr);
                }
            });
            return;
        }
        ImageInsertTask imageInsertTask = this.mImageInsertTask;
        if (imageInsertTask != null) {
            imageInsertTask.restoreAndCancel();
        }
        ImageInsertTask imageInsertTask2 = new ImageInsertTask(runnable, this);
        this.mImageInsertTask = imageInsertTask2;
        imageInsertTask2.execute(uriArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$insertImages$0(Runnable runnable, Uri[] uriArr) {
        clearDataParseTask();
        insertImages(runnable, uriArr);
    }

    @Override // com.miui.richeditor.IRichEditor
    public SpannableStringBuilder trimImageText(SpannableStringBuilder spannableStringBuilder) {
        NormalImageSpan[] normalImageSpanArr = (NormalImageSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), NormalImageSpan.class);
        int length = normalImageSpanArr.length;
        while (true) {
            length--;
            if (spannableStringBuilder.length() <= this.mEditorConfig.contentTextLengthMax - getText().length() || length < 0) {
                break;
            }
            spannableStringBuilder = spannableStringBuilder.delete(spannableStringBuilder.getSpanStart(normalImageSpanArr[length]), spannableStringBuilder.length());
        }
        if (length < normalImageSpanArr.length - 1) {
            onSurpassContentLengthLimit();
        }
        if (length < 0) {
            return null;
        }
        return spannableStringBuilder;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void insertDoodleByName(final String str, final NormalImageSpan normalImageSpan) {
        int spanStart;
        int i;
        if (getText() == null) {
            Logger.INSTANCE.w(TAG, "text is null, don't apply image result");
            return;
        }
        if (this.mDataParseTask != null) {
            addPendingTask(new Runnable() { // from class: com.miui.richeditor.RichEditTextView.2
                @Override // java.lang.Runnable
                public void run() {
                    RichEditTextView.this.clearDataParseTask();
                    RichEditTextView.this.insertDoodleByName(str, normalImageSpan);
                }
            });
            return;
        }
        String string = getText().toString();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(HtmlParser.MEDIA_DUMMY_CHAR).append('\n');
        this.mStyleFactory.setNormalImageSpan(spannableStringBuilder, length, length + 1, str, normalImageSpan);
        if (string.equals(getText().toString())) {
            Editable text = getText();
            int[] iArr = this.mCacheEditSelection;
            int preferSelectionStart = iArr[0];
            int i2 = iArr[1];
            if (normalImageSpan != null) {
                spanStart = getText().getSpanStart(normalImageSpan);
                setSkipRichText(true);
                deleteImageSpan(normalImageSpan, false);
                setSkipRichText(false);
            } else {
                if (preferSelectionStart == -1) {
                    preferSelectionStart = getPreferSelectionStart(false);
                }
                if (i2 == -1) {
                    getSelectionEnd();
                }
                spanStart = preferSelectionStart;
            }
            if (spanStart > 0 && text.length() > (i = spanStart - 1) && text.charAt(i) != '\n') {
                setSkipRichText(true);
                text.insert(spanStart, StringUtils.LF);
                spanStart++;
                setSkipRichText(false);
            }
            SpannableStringBuilder spannableStringBuilderTrimImageText = trimImageText(spannableStringBuilder);
            if (spannableStringBuilderTrimImageText == null) {
                return;
            }
            setSkipRichText(true);
            text.insert(spanStart, spannableStringBuilderTrimImageText);
            setSkipRichText(false);
            int length2 = spanStart + spannableStringBuilderTrimImageText.length();
            setSelection(length2);
            requestFocus();
            try {
                bringPointIntoView(length2);
                return;
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "bringPointIntoView:" + e.getMessage());
                return;
            }
        }
        Logger.INSTANCE.w(TAG, "text has changed, don't apply image result");
    }

    @Override // com.miui.richeditor.IRichEditor
    public void insertAudio() {
        Logger.INSTANCE.d(TAG, "insertAudio");
        cancelPlayAnimIfNeed();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int selectionStart = getSelectionStart();
        if (selectionStart == -1) {
            selectionStart = getText().length();
        }
        setSkipRichText(true);
        if (selectionStart > 0 && getText().charAt(selectionStart - 1) != '\n') {
            getText().insert(selectionStart, StringUtils.LF);
            selectionStart++;
        }
        cancelOtherStyleOnInsertAudio();
        spannableStringBuilder.append(HtmlParser.MEDIA_DUMMY_CHAR);
        int length = spannableStringBuilder.length() + selectionStart;
        spannableStringBuilder.append((CharSequence) StringUtils.LF);
        spannableStringBuilder.setSpan(getStyleFactory().createEditLineHeightSpan(0), spannableStringBuilder.length(), spannableStringBuilder.length(), 18);
        getInputHelper().updateInputModeIndex(spannableStringBuilder.length() + selectionStart);
        getText().insert(selectionStart, spannableStringBuilder);
        setSkipRichText(false);
        this.mPlayingAudioSpan = this.mStyleFactory.setAudioSpan(getText(), selectionStart, length, "temp_audio_file");
        setSelection(length + 1);
        this.handler.post(new Runnable() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$insertAudio$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$insertAudio$1() {
        requestLayout();
        invalidateDeeply();
    }

    protected void cancelOtherStyleOnInsertAudio() {
        for (AlignmentSpan alignmentSpan : (AlignmentSpan[]) getText().getSpans(Math.min(getSelectionStart(), getSelectionEnd()), Math.max(getSelectionStart(), getSelectionEnd()), AlignmentSpan.class)) {
            getText().removeSpan(alignmentSpan);
        }
    }

    public void refreshExternalTextWatcher() {
        clearRichTextData();
        ExternalTextWatcher externalTextWatcher = this.mExternalTextWatcher;
        if (externalTextWatcher != null) {
            externalTextWatcher.afterTextActuallyChanged(getText());
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setImage(Editable editable, SpannableStringBuilder spannableStringBuilder, int i, int i2) {
        clearTextWatcher();
        setSkipRichText(true);
        editable.replace(i, i2, "");
        insertImageString(editable, i, spannableStringBuilder);
        setSkipRichText(false);
        restoreTextWatcher();
        refreshContainer();
    }

    @Override // com.miui.richeditor.IRichEditor
    public void removeSelection() {
        Selection.removeSelection(getText());
    }

    private boolean isImageBeginAt(int i) {
        int iIndexOf;
        Editable text = getText();
        if (i >= text.length() || (iIndexOf = text.toString().indexOf(10, i)) < 0) {
            return false;
        }
        for (BaseImageSpan baseImageSpan : (BaseImageSpan[]) text.getSpans(i, iIndexOf, BaseImageSpan.class)) {
            if (text.getSpanStart(baseImageSpan) == i) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getPreferSelectionStart(boolean z) {
        if (!isCursorVisible() && !z) {
            setSelection(getText().length());
        }
        return getSelectionStart();
    }

    @Override // com.miui.richeditor.IRichEditor
    public void cacheSelection(boolean z) {
        if (z) {
            this.mCacheEditSelection[0] = getSelectionStart();
            this.mCacheEditSelection[1] = getSelectionEnd();
        } else {
            int[] iArr = this.mCacheEditSelection;
            iArr[0] = -1;
            iArr[1] = -1;
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getCacheSelectionStart() {
        return this.mCacheEditSelection[0];
    }

    @Override // com.miui.richeditor.IRichEditor
    public int[] getCacheEditSelection() {
        return this.mCacheEditSelection;
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean isHintSpanTwoLines() {
        return this.mIsHintSpanTwoLines;
    }

    @Override // com.miui.richeditor.IRichEditor
    public RichTextWatcher getRichTextWatcher() {
        return this.mRichTextWatcher;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void clearTextWatcher() {
        removeTextChangedListener(this.mRichTextWatcher);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void restoreTextWatcher() {
        removeTextChangedListener(this.mRichTextWatcher);
        addTextChangedListener(this.mRichTextWatcher);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setExternalTextWatcher(ExternalTextWatcher externalTextWatcher) {
        this.mExternalTextWatcher = externalTextWatcher;
    }

    @Override // com.miui.richeditor.IRichEditor
    public ExternalTextWatcher getExternalTextWatcher() {
        return this.mExternalTextWatcher;
    }

    @Override // com.miui.richeditor.IRichEditor
    public InputModeRichStyleHelper getInputHelper() {
        return this.mInputModeInfo;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setSpanClickListener(SpanClickListener spanClickListener) {
        this.mSpanClickListener = spanClickListener;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setEditorActionListener(EditorActionListener editorActionListener) {
        this.mActionListener = editorActionListener;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setTextStyleStateListener(TextStyleStateListener textStyleStateListener) {
        this.mTextStyleStateListener = textStyleStateListener;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setCursorClickListener(IRichEditor.CursorClickListener cursorClickListener) {
        this.mCursorClickListener = cursorClickListener;
    }

    @Override // com.miui.richeditor.IRichEditor, com.miui.richeditor.IEditorContext
    public VisualStyleFactory getStyleFactory() {
        return this.mStyleFactory;
    }

    private UndoManager<RichEditUndoOperation> createUndoManager() {
        return new UndoManager<>(new UndoManager.UndoOperationCreator<RichEditUndoOperation>() { // from class: com.miui.richeditor.RichEditTextView.3
            @Override // com.miui.richeditor.UndoManager.UndoOperationCreator
            public RichEditUndoOperation createOperation() {
                return new RichEditUndoOperation(RichEditTextView.this);
            }
        }, getUndoStepCapacity());
    }

    @Override // com.miui.richeditor.IRichEditor
    public UndoManager<RichEditUndoOperation> getUndoManager() {
        return this.mUndoManager;
    }

    @Override // com.miui.richeditor.IRichEditor
    public SpannableStringBuilder getBuildableText() {
        Editable text = getText();
        if (text == null) {
            return new SafeSpannableStringBuilder("");
        }
        try {
            checkBuildableTextSafe(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int iNextSpanTransition = text.nextSpanTransition(-1, text.length(), EditorBulletSpan.class);
        int iNextSpanTransition2 = text.nextSpanTransition(-1, text.length(), CheckboxSpan.class);
        int iNextSpanTransition3 = text.nextSpanTransition(-1, text.length(), FontLargeSizeSpan.class);
        int iNextSpanTransition4 = text.nextSpanTransition(-1, text.length(), FontMidSizeSpan.class);
        if (iNextSpanTransition2 >= text.length() && iNextSpanTransition >= text.length() && iNextSpanTransition3 >= text.length() && iNextSpanTransition4 >= text.length()) {
            return new SafeSpannableStringBuilder(text);
        }
        SpannableStringBuilder safeSpannableStringBuilder = new SafeSpannableStringBuilder(text);
        while (iNextSpanTransition < safeSpannableStringBuilder.length()) {
            EditorBulletSpan[] editorBulletSpanArr = (EditorBulletSpan[]) getSpansAt(safeSpannableStringBuilder, EditorBulletSpan.class, iNextSpanTransition);
            if (editorBulletSpanArr != null) {
                for (EditorBulletSpan editorBulletSpan : editorBulletSpanArr) {
                    int spanStart = safeSpannableStringBuilder.getSpanStart(editorBulletSpan);
                    safeSpannableStringBuilder.removeSpan(editorBulletSpan);
                    if (spanStart >= 0 && safeSpannableStringBuilder.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, spanStart)) {
                        safeSpannableStringBuilder.delete(spanStart, EditorUtils.ZERO_WIDTH_STRING.length() + spanStart);
                    }
                }
                if (editorBulletSpanArr.length > 0) {
                    String string = Character.toString(HtmlParser.MEDIA_DUMMY_CHAR);
                    safeSpannableStringBuilder.insert(iNextSpanTransition, (CharSequence) string);
                    safeSpannableStringBuilder.setSpan(new EditorBulletLiteSpan(editorBulletSpanArr[0].getElement()), iNextSpanTransition, string.length() + iNextSpanTransition, 17);
                    adjustLineStyleSpanAfterInsertCheckboxOrBullet(safeSpannableStringBuilder, iNextSpanTransition);
                }
                iNextSpanTransition = safeSpannableStringBuilder.nextSpanTransition(iNextSpanTransition, safeSpannableStringBuilder.length(), EditorBulletSpan.class);
            }
        }
        while (iNextSpanTransition2 < safeSpannableStringBuilder.length()) {
            CheckboxSpan[] checkboxSpanArr = (CheckboxSpan[]) getSpansAt(safeSpannableStringBuilder, CheckboxSpan.class, iNextSpanTransition2);
            for (CheckboxSpan checkboxSpan : checkboxSpanArr) {
                int spanStart2 = safeSpannableStringBuilder.getSpanStart(checkboxSpan);
                safeSpannableStringBuilder.removeSpan(checkboxSpan);
                if (spanStart2 >= 0 && safeSpannableStringBuilder.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, spanStart2)) {
                    safeSpannableStringBuilder.delete(spanStart2, EditorUtils.ZERO_WIDTH_STRING.length() + spanStart2);
                }
            }
            if (checkboxSpanArr.length > 0) {
                String string2 = Character.toString(HtmlParser.MEDIA_DUMMY_CHAR);
                safeSpannableStringBuilder.insert(iNextSpanTransition2, (CharSequence) string2);
                safeSpannableStringBuilder.setSpan(new CheckboxLiteSpan(checkboxSpanArr[0].getElement()), iNextSpanTransition2, string2.length() + iNextSpanTransition2, 17);
            }
            iNextSpanTransition2 = safeSpannableStringBuilder.nextSpanTransition(iNextSpanTransition2, safeSpannableStringBuilder.length(), CheckboxSpan.class);
        }
        return safeSpannableStringBuilder;
    }

    private boolean checkBuildableTextSafe(CharSequence charSequence) {
        int length = charSequence.length();
        boolean z = true;
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            Object[] spans = spanned.getSpans(0, length, Object.class);
            for (int i = 0; i < spans.length; i++) {
                Object obj = spans[i];
                if (!(obj instanceof NoCopySpan)) {
                    spanned.getSpanStart(obj);
                    int spanEnd = spanned.getSpanEnd(spans[i]);
                    spanned.getSpanFlags(spans[i]);
                    if (spanEnd < 0) {
                        spanEnd = 0;
                    }
                    if (spanEnd > length) {
                        spanEnd = length;
                    }
                    if (spanEnd < 0) {
                        Logger.INSTANCE.v(TAG, "checkBuildableTextSafe waring(0 ... " + length + ") has end before start spaninfo: " + spans[i].toString());
                        z = false;
                    }
                }
            }
        }
        return z;
    }

    private void adjustLineStyleSpanAfterInsertCheckboxOrBullet(SpannableStringBuilder spannableStringBuilder, int i) {
        int iIndexOf = spannableStringBuilder.toString().indexOf(65532, i);
        int i2 = iIndexOf + 1;
        RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) spannableStringBuilder.getSpans(i2, i2, RelativeSizeSpan.class);
        TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) spannableStringBuilder.getSpans(i2, i2, TypefaceSpan.class);
        EditLineHeightSpan[] editLineHeightSpanArr = (EditLineHeightSpan[]) spannableStringBuilder.getSpans(i2, i2, EditLineHeightSpan.class);
        for (RelativeSizeSpan relativeSizeSpan : relativeSizeSpanArr) {
            int spanStart = spannableStringBuilder.getSpanStart(relativeSizeSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(relativeSizeSpan);
            int spanFlags = spannableStringBuilder.getSpanFlags(relativeSizeSpan);
            if (spanEnd == i2) {
                spannableStringBuilder.setSpan(relativeSizeSpan, spanStart, spanEnd - 1, spanFlags);
            } else if (spanStart == iIndexOf) {
                spannableStringBuilder.setSpan(relativeSizeSpan, spanStart + 1, spanEnd + 1, spanFlags);
            }
        }
        for (TypefaceSpan typefaceSpan : typefaceSpanArr) {
            int spanStart2 = spannableStringBuilder.getSpanStart(typefaceSpan);
            int spanEnd2 = spannableStringBuilder.getSpanEnd(typefaceSpan);
            int spanFlags2 = spannableStringBuilder.getSpanFlags(typefaceSpan);
            if (spanEnd2 == i2) {
                spannableStringBuilder.setSpan(typefaceSpan, spanStart2, spanEnd2 - 1, spanFlags2);
            } else if (spanStart2 == iIndexOf) {
                spannableStringBuilder.setSpan(typefaceSpan, spanStart2 + 1, spanEnd2 + 1, spanFlags2);
            }
        }
        for (EditLineHeightSpan editLineHeightSpan : editLineHeightSpanArr) {
            int spanStart3 = spannableStringBuilder.getSpanStart(editLineHeightSpan);
            int spanEnd3 = spannableStringBuilder.getSpanEnd(editLineHeightSpan);
            int spanFlags3 = spannableStringBuilder.getSpanFlags(editLineHeightSpan);
            if (spanEnd3 == i2) {
                spannableStringBuilder.setSpan(editLineHeightSpan, spanStart3, spanEnd3 - 1, spanFlags3);
            } else if (spanStart3 == iIndexOf) {
                spannableStringBuilder.setSpan(editLineHeightSpan, spanStart3 + 1, spanEnd3 + 1, spanFlags3);
            }
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public CharSequence getRichTextData() {
        if (TextUtils.isEmpty(this.mRichTextData)) {
            this.mRichTextData = NoteSchema.buildAncient(getContext(), getBuildableText());
        }
        return this.mRichTextData;
    }

    @Override // com.miui.richeditor.IRichEditor
    public CharSequence createNewRichTextData() {
        if (this.mDataParseTask != null) {
            return getRichTextData();
        }
        clearRichTextData();
        return getRichTextData();
    }

    @Override // com.miui.richeditor.IRichEditor
    public void clearRichTextData() {
        this.mRichTextData = null;
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean isLoadingViewVisible() {
        return this.mLoadingContainer.getVisibility() == 0;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void onPlayingRecord(boolean z) {
        EditorActionListener editorActionListener = this.mActionListener;
        if (editorActionListener != null) {
            editorActionListener.onPlayingRecord(z);
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean hasDataLoadingLocker() {
        return this.mLoadingLocker != null;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setDataLoadingLocker(IRichEditor.DataLoadingLocker dataLoadingLocker) {
        this.mLoadingLocker = dataLoadingLocker;
    }

    @Override // com.miui.richeditor.IRichEditor
    public IRichEditor.DataLoadingLocker getDataLoadingLocker() {
        return this.mLoadingLocker;
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getEditorMode() {
        return isCursorVisible() ? 1 : 0;
    }

    @Override // com.miui.richeditor.IRichEditor
    public SpannableStringBuilder getSubSequence(int i, int i2) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getText().subSequence(i, i2));
        List<SpanInfo> arrayList = new ArrayList<>();
        List<SpanInfo> arrayList2 = new ArrayList<>();
        List<SpanInfo> arrayList3 = new ArrayList<>();
        List<SpanInfo> arrayList4 = new ArrayList<>();
        for (Object obj : (FontLargeSizeSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), FontLargeSizeSpan.class)) {
            SpanInfo spanInfo = getSpanInfo(spannableStringBuilder, obj);
            if (!arrayList.contains(spanInfo)) {
                arrayList.add(spanInfo);
            }
            spannableStringBuilder.removeSpan(obj);
            for (TypefaceSpan typefaceSpan : (TypefaceSpan[]) spannableStringBuilder.getSpans(spanInfo.getStart(), spanInfo.getEnd(), TypefaceSpan.class)) {
                spannableStringBuilder.removeSpan(typefaceSpan);
            }
        }
        for (Object obj2 : (FontMidSizeSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), FontMidSizeSpan.class)) {
            SpanInfo spanInfo2 = getSpanInfo(spannableStringBuilder, obj2);
            if (!arrayList2.contains(spanInfo2)) {
                arrayList2.add(spanInfo2);
            }
            spannableStringBuilder.removeSpan(obj2);
            for (TypefaceSpan typefaceSpan2 : (TypefaceSpan[]) spannableStringBuilder.getSpans(spanInfo2.getStart(), spanInfo2.getEnd(), TypefaceSpan.class)) {
                spannableStringBuilder.removeSpan(typefaceSpan2);
            }
        }
        for (StyleSpan styleSpan : (StyleSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), StyleSpan.class)) {
            SpanInfo spanInfo3 = getSpanInfo(spannableStringBuilder, styleSpan);
            if (spanInfo3.isValid()) {
                if (styleSpan.getStyle() == 1 && !arrayList3.contains(spanInfo3)) {
                    arrayList3.add(spanInfo3);
                } else if (styleSpan.getStyle() == 2 && !arrayList4.contains(spanInfo3)) {
                    arrayList4.add(spanInfo3);
                }
            }
            spannableStringBuilder.removeSpan(styleSpan);
        }
        makeSpanContinuous(arrayList3, arrayList4);
        makeFontSpanContinuous(arrayList, arrayList2);
        Iterator<SpanInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            getStyleFactory().setPlainFontSizeSpanByInfo(spannableStringBuilder, it.next(), 2);
        }
        Iterator<SpanInfo> it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            getStyleFactory().setPlainFontSizeSpanByInfo(spannableStringBuilder, it2.next(), 1);
        }
        for (SpanInfo spanInfo4 : arrayList3) {
            spannableStringBuilder.setSpan(new StyleSpan(1), spanInfo4.getStart(), spanInfo4.getEnd(), 33);
        }
        for (SpanInfo spanInfo5 : arrayList4) {
            spannableStringBuilder.setSpan(new StyleSpan(2), spanInfo5.getStart(), spanInfo5.getEnd(), 33);
        }
        return spannableStringBuilder;
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean isInDragMode() {
        return this.mIsInDragMode;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void selectLineInDragMode(int i) {
        if ((this.mDraggingImageSpan != null ? getLayout().getLineForOffset(getText().getSpanStart(this.mDraggingImageSpan)) : -2) == i) {
            this.mSelectLine = -2;
            return;
        }
        if (this.mSelectLine != i) {
            this.mSelectLine = i;
            if (i == -1) {
                this.mSelectLineTop = getLayout().getLineTop(0) + ((int) (this.mSelectLinePaint.getStrokeWidth() / 2.0f)) + getPaddingTop();
            } else {
                this.mSelectLineTop = (getLayout().getLineBottom(this.mSelectLine) + getPaddingTop()) - getResources().getDimensionPixelSize(R.dimen.notes_edit_last_selectline_padding_bottom);
            }
            startDrawSelectLineAnim();
        }
    }

    private void startDrawSelectLineAnim() {
        ValueAnimator valueAnimator = this.mSelectLineDrawAnim;
        if (valueAnimator != null) {
            if (valueAnimator.isRunning()) {
                this.mSelectLineDrawAnim.cancel();
            } else {
                this.mSelectLineAlpha = 0;
                this.mSelectLineLengthHalf = 0;
                this.mSelectLineAnimFraction = 0.0f;
            }
        }
        int contentWidth = getContentWidth() / 2;
        final int i = this.mSelectLineAlpha;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mSelectLineLengthHalf, contentWidth);
        this.mSelectLineDrawAnim = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(150L);
        this.mSelectLineDrawAnim.setCurrentFraction(this.mSelectLineAnimFraction);
        this.mSelectLineDrawAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.RichEditTextView.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                RichEditTextView.this.mSelectLineAnimFraction = valueAnimator2.getAnimatedFraction();
                RichEditTextView.this.mSelectLineLengthHalf = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                RichEditTextView.this.mSelectLineAlpha = (int) (((255 - i) * valueAnimator2.getAnimatedFraction()) + i);
                RichEditTextView.this.mSelectLinePaint.setAlpha(RichEditTextView.this.mSelectLineAlpha);
                RichEditTextView.this.invalidate();
            }
        });
        this.mSelectLineDrawAnim.start();
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean overScrollByPosition(int i, int i2) {
        selectLineInDragMode(getLineAtCoordinate(i2));
        int y = (((int) getY()) - getPaddingTop()) - this.mScrollView.getScrollY();
        if (y < 0) {
            y = 0;
        }
        int scrollY = (i2 - this.mScrollView.getScrollY()) + ((int) getY());
        int height = this.mScrollView.getHeight();
        Logger.INSTANCE.d(TAG, "overScrollByPosition y = " + i2 + " posY = " + scrollY + " visibleTop = " + y + " visibleBottom = " + height);
        if (scrollY - 200 < y) {
            this.mScrollView.smoothScrollBy(0, -50);
            return true;
        }
        if (scrollY + 300 <= height) {
            return false;
        }
        this.mScrollView.smoothScrollBy(0, 50);
        return true;
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getLineAtCoordinate(float f) {
        return getLayout().getLineForVertical((int) (Math.min((getHeight() - getTotalPaddingBottom()) - 1, Math.max(0.0f, f - getTotalPaddingTop())) + getScrollY()));
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getLineStart(String str, int i) {
        if (i == 0) {
            return 0;
        }
        return str.lastIndexOf(10, i - 1) + 1;
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getLineEnd(String str, int i) {
        if (i < str.length()) {
            int iIndexOf = str.indexOf(10, i);
            return iIndexOf < 0 ? str.length() : iIndexOf;
        }
        return str.length();
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getParagraphStart(String str, int i) {
        if (i == 0) {
            return 0;
        }
        return str.lastIndexOf(10, i - 1) + 1;
    }

    @Override // com.miui.richeditor.IRichEditor
    public int getParagraphEnd(String str, int i) {
        if (i < str.length()) {
            int iIndexOf = str.indexOf(10, i);
            return iIndexOf < 0 ? str.length() : iIndexOf + 1;
        }
        return str.length();
    }

    @Override // com.miui.richeditor.IRichEditor
    public <T> T[] getSpansAt(Spannable spannable, Class<T> cls, int i) {
        if (i < 0 || i > spannable.length()) {
            return null;
        }
        return (T[]) spannable.getSpans(i, i, cls);
    }

    @Override // com.miui.richeditor.IRichEditor
    public <T> T getSpanStartAt(Spannable spannable, Class<T> cls, int i) {
        Object[] spansAt = getSpansAt(spannable, cls, i);
        if (spansAt == null) {
            return null;
        }
        for (Object obj : spansAt) {
            T t = (T) obj;
            if (spannable.getSpanStart(t) == i) {
                return t;
            }
        }
        return null;
    }

    @Override // com.miui.richeditor.IRichEditor
    public <T> T getSpanEndAt(Spannable spannable, Class<T> cls, int i) {
        Object[] spansAt = getSpansAt(spannable, cls, i);
        if (spansAt == null) {
            return null;
        }
        for (Object obj : spansAt) {
            T t = (T) obj;
            if (spannable.getSpanEnd(t) == i) {
                return t;
            }
        }
        return null;
    }

    @Override // com.miui.richeditor.IRichEditor
    public <T> boolean deleteSpans(Editable editable, int i, int i2, Class<T> cls) {
        Object[] spans = editable.getSpans(i, i2, cls);
        for (Object obj : spans) {
            editable.removeSpan(obj);
        }
        return spans.length != 0;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void removeSpan(Object obj) {
        getText().removeSpan(obj);
    }

    public void removeSpan(Object obj, int i, int i2) {
        int spanStart = getText().getSpanStart(obj);
        int spanEnd = getText().getSpanEnd(obj);
        getText().removeSpan(obj);
        if (i2 > spanEnd) {
            i2 = spanEnd;
        }
        if (i <= spanStart) {
            i = spanStart;
        }
        if (obj instanceof StyleSpan) {
            StyleSpan styleSpan = (StyleSpan) obj;
            if (styleSpan.getStyle() == 1) {
                styleValid(new StyleSpan(1), spanStart, i);
                styleValid(new StyleSpan(1), i2, spanEnd);
                return;
            } else {
                if (styleSpan.getStyle() == 2) {
                    styleValid(new StyleSpan(2), spanStart, i);
                    styleValid(new StyleSpan(2), i2, spanEnd);
                    return;
                }
                return;
            }
        }
        if (obj instanceof UnderlineSpan) {
            styleValid(5, spanStart, i);
            styleValid(5, i2, spanEnd);
        } else if (obj instanceof BgHighLightSpan) {
            styleValid(11, spanStart, i);
            styleValid(11, i2, spanEnd);
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void notifySaveData() {
        if (this.mActionListener != null) {
            clearRichTextData();
            this.mActionListener.onSaveData();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void undo() {
        if (getUndoManager().canUndo()) {
            getUndoManager().undo();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void redo() {
        if (getUndoManager().canRedo()) {
            getUndoManager().redo();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setFontBold() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        if (iMin == iMax) {
            if (getInputHelper().checkStyleInputMode(getText(), iMin, 1)) {
                setNewInputModeRichStyle(new StyleSpan(1), iMin, iMax);
            }
            changeStyleStatusOnPoint(iMin, false);
        } else {
            setStyleSpan(1, iMin, iMax);
        }
        if (iMax > iMin) {
            refreshExternalTextWatcher();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setFontItalic() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        if (iMin == iMax) {
            if (getInputHelper().checkStyleInputMode(getText(), iMin, 2)) {
                setNewInputModeRichStyle(new StyleSpan(2), iMin, iMax);
            }
            changeStyleStatusOnPoint(iMin, false);
        } else {
            setStyleSpan(2, iMin, iMax);
        }
        if (iMax > iMin) {
            refreshExternalTextWatcher();
        }
    }

    private void setNewInputModeRichStyle(Object obj, int i, int i2) {
        if (getText() != null) {
            getText().setSpan(obj, i, i2, 18);
        }
        commitComposingContent();
    }

    @Override // com.miui.richeditor.IRichEditor
    public void increaseFontSize() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        if (((FontLargeSizeSpan[]) getText().getSpans(iMin, iMax, FontLargeSizeSpan.class)).length > 0) {
            return;
        }
        if (((FontMidSizeSpan[]) getText().getSpans(iMin, iMax, FontMidSizeSpan.class)).length > 0) {
            setFontSizeLevel(2);
        } else {
            setFontSizeLevel(1);
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void reduceFontSize() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        if (((FontLargeSizeSpan[]) getText().getSpans(iMin, iMax, FontLargeSizeSpan.class)).length > 0) {
            setFontSizeLevel(1);
        } else if (((FontMidSizeSpan[]) getText().getSpans(iMin, iMax, FontMidSizeSpan.class)).length > 0) {
            setFontSizeLevel(0);
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setFontSizeLevel(int i) {
        boolean z;
        boolean z2;
        String string = getText().toString();
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        Logger.INSTANCE.d("SizeLevelDebug", "setFontSizeLevel start selStart " + iMin + " selEnd " + iMax);
        int paragraphStart = getParagraphStart(string, iMin);
        if (paragraphStart < string.length() && string.charAt(paragraphStart) == 8204) {
            paragraphStart++;
        }
        int paragraphEnd = getParagraphEnd(string, iMax);
        int i2 = paragraphEnd - paragraphStart;
        int i3 = 0;
        ((RichEditUndoOperation) getUndoManager().getOperation()).track(paragraphStart, i2, 0);
        boolean zRemoveStyleSizeInRangeOnSetFontSize = true;
        if (i != 0) {
            removeStyleSizeInRangeOnSetFontSize(getText(), paragraphStart, paragraphEnd);
            if (paragraphStart == paragraphEnd) {
                getInputHelper().updateInputModeIndex(paragraphStart);
                getStyleFactory().setInputFontSizeSpan(getText(), paragraphStart, i);
            } else {
                int i4 = paragraphStart;
                while (i4 < paragraphEnd) {
                    int iIndexOf = string.indexOf(10, i4);
                    if (iIndexOf == -1) {
                        iIndexOf = string.length();
                    }
                    getStyleFactory().setFontSizeSpan(getText(), i4, iIndexOf, i);
                    i4 = iIndexOf + 1;
                    i3 = 1;
                }
                zRemoveStyleSizeInRangeOnSetFontSize = i3;
            }
        } else {
            NormalImageSpan[] normalImageSpanArr = (NormalImageSpan[]) getText().getSpans(paragraphStart, paragraphStart, NormalImageSpan.class);
            BaseAudioSpan[] baseAudioSpanArr = (BaseAudioSpan[]) getText().getSpans(paragraphStart, paragraphStart, BaseAudioSpan.class);
            if (normalImageSpanArr.length >= 1 || baseAudioSpanArr.length >= 1) {
                z = false;
                z2 = false;
            } else {
                z = true;
                z2 = false;
            }
            while (i3 <= i2) {
                removeStyleSizeInRangeOnSetFontSize(getText(), paragraphStart, paragraphEnd);
                if (z) {
                    getStyleFactory().setFontSizeSpan(getText(), paragraphStart, paragraphEnd, i);
                }
                i3 = paragraphEnd + 1;
                z2 = true;
            }
            zRemoveStyleSizeInRangeOnSetFontSize = z2;
        }
        if (!zRemoveStyleSizeInRangeOnSetFontSize) {
            zRemoveStyleSizeInRangeOnSetFontSize = removeStyleSizeInRangeOnSetFontSize(getText(), paragraphStart, paragraphEnd);
        }
        if (zRemoveStyleSizeInRangeOnSetFontSize) {
            ((RichEditUndoOperation) getUndoManager().getOperation()).updateRange(paragraphStart, string.length() - paragraphEnd);
            ((RichEditUndoOperation) getUndoManager().getOperation()).untrack();
            getUndoManager().commit();
            refreshContainer();
            clearRichTextData();
            return;
        }
        ((RichEditUndoOperation) getUndoManager().getOperation()).untrack();
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setAlignCenter() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        if (isParagraphCentered(iMin, iMax)) {
            cancelCenter();
            refreshExternalTextWatcher();
        } else {
            centerWithPosition(iMin, iMax);
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setAlignRight() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        if (isParagraphRighted(iMin, iMax)) {
            cancelRight();
            refreshExternalTextWatcher();
        } else {
            rightWithPosition(iMin, iMax);
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setAlignNormal() {
        boolean z;
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        boolean z2 = true;
        if (isParagraphCentered(iMin, iMax)) {
            cancelCenter();
            z = true;
        } else {
            z = false;
        }
        if (isParagraphRighted(iMin, iMax)) {
            cancelRight();
        } else {
            z2 = z;
        }
        Logger.INSTANCE.d(TAG, "setAlignNormal isChanged = " + z2);
        if (z2) {
            refreshExternalTextWatcher();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean setAlign(Layout.Alignment alignment, int i, int i2) {
        int iIndexOf;
        String string = getText().toString();
        int i3 = 0;
        if (string.length() == 0) {
            setSkipRichText(true);
            this.mRichTextWatcher.mDoSetNull = true;
            setText("");
            setSkipRichText(false);
            return false;
        }
        if (i == i2) {
            if (i == string.length()) {
                int i4 = i - 1;
                if (string.charAt(i4) != '\n') {
                    return setAlign(alignment, i4, i2 - 1);
                }
                return false;
            }
            int iLastIndexOf = string.lastIndexOf(10, i - 1) + 1;
            if (iLastIndexOf == -1) {
                iLastIndexOf = 0;
            }
            int iIndexOf2 = string.indexOf(10, i2);
            if (iIndexOf2 == -1) {
                iIndexOf2 = string.length();
            }
            if (iLastIndexOf == iIndexOf2 && iIndexOf2 == string.length() - 1) {
                iIndexOf2 = string.length();
            }
            AlignmentSpan[] alignmentSpanArr = (AlignmentSpan[]) getText().getSpans(iLastIndexOf, iIndexOf2, AlignmentSpan.class);
            int length = alignmentSpanArr.length;
            boolean z = false;
            while (i3 < length) {
                AlignmentSpan alignmentSpan = alignmentSpanArr[i3];
                removeSpan(alignmentSpan);
                if (alignmentSpan.getAlignment() == alignment) {
                    z = true;
                }
                i3++;
            }
            if ((alignment != Layout.Alignment.ALIGN_CENTER && alignment != Layout.Alignment.ALIGN_OPPOSITE) || hasCheckbox(iLastIndexOf, iIndexOf2) || hasEditorBullet(iLastIndexOf, iIndexOf2) || iIndexOf2 <= iLastIndexOf) {
                return z;
            }
            getText().setSpan(new AlignmentSpan.Standard(alignment), iLastIndexOf, iIndexOf2, 18);
            return true;
        }
        int iLastIndexOf2 = string.lastIndexOf(10, i - 1) + 1;
        if (iLastIndexOf2 == -1) {
            iLastIndexOf2 = 0;
        }
        int iIndexOf3 = string.indexOf(10, i2);
        if (iIndexOf3 == -1) {
            iIndexOf3 = string.length();
        }
        AlignmentSpan[] alignmentSpanArr2 = (AlignmentSpan[]) getText().getSpans(iLastIndexOf2, iIndexOf3, AlignmentSpan.class);
        int length2 = alignmentSpanArr2.length;
        boolean z2 = false;
        while (i3 < length2) {
            AlignmentSpan alignmentSpan2 = alignmentSpanArr2[i3];
            removeSpan(alignmentSpan2);
            if (alignmentSpan2.getAlignment() == alignment) {
                z2 = true;
            }
            i3++;
        }
        if (z2) {
            return z2;
        }
        if (iLastIndexOf2 == iIndexOf3) {
            return setAlign(alignment, iLastIndexOf2, iIndexOf3);
        }
        for (int i5 = iLastIndexOf2; i5 < iIndexOf3; i5 = iIndexOf + 1) {
            iIndexOf = string.indexOf(10, i5);
            if (iIndexOf == -1) {
                iIndexOf = string.length();
            }
            if ((alignment == Layout.Alignment.ALIGN_CENTER || alignment == Layout.Alignment.ALIGN_OPPOSITE) && !hasCheckbox(i5, iIndexOf) && !hasEditorBullet(iLastIndexOf2, iIndexOf3) && iIndexOf > i5) {
                getText().setSpan(new AlignmentSpan.Standard(alignment), i5, iIndexOf, 18);
                z2 = true;
            }
        }
        return z2;
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean setAlignCenter(int i, int i2) {
        return setAlign(Layout.Alignment.ALIGN_CENTER, i, i2);
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean setAlignRight(int i, int i2) {
        return setAlign(Layout.Alignment.ALIGN_OPPOSITE, i, i2);
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean setAlignNormal(int i, int i2) {
        return setAlign(Layout.Alignment.ALIGN_NORMAL, i, i2);
    }

    public boolean canAlignNormal() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        return isParagraphCentered(iMin, iMax) || isParagraphRighted(iMin, iMax);
    }

    public boolean canAlignCenter() {
        int selectionStart = getSelectionStart();
        return (hasEditorBullet(selectionStart, selectionStart) || hasCheckbox(selectionStart, selectionStart)) ? false : true;
    }

    public boolean canAlignRight() {
        int selectionStart = getSelectionStart();
        return (hasEditorBullet(selectionStart, selectionStart) || hasCheckbox(selectionStart, selectionStart)) ? false : true;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setCheckbox() {
        int[] linePos = getLinePos();
        SpannableStringBuilder subSequence = getSubSequence(linePos[0], linePos[1]);
        if (((EditorBulletSpan[]) subSequence.getSpans(0, subSequence.length(), EditorBulletSpan.class)).length > 0) {
            removeEditorBulletInRange(getText(), linePos[0], linePos[1]);
        }
        cancelRight();
        cancelCenter();
        int[] linePos2 = getLinePos();
        SpannableStringBuilder subSequence2 = getSubSequence(linePos2[0], linePos2[1]);
        int i = 0;
        boolean zRemoveCheckboxInRange = false;
        while (i <= subSequence2.length()) {
            int lineEnd = getLineEnd(subSequence2.toString(), i);
            if (getSpanStartAt(subSequence2, BaseImageSpan.class, i) == null && getSpanStartAt(subSequence2, CheckboxSpan.class, i) == null) {
                clearTextWatcher();
                int i2 = linePos2[0];
                int checkboxSpan = getStyleFactory().setCheckboxSpan(getText(), getStyleFactory().createCheckboxSpan(false), i2 + i, i2 + lineEnd) - linePos2[0];
                restoreTextWatcher();
                changeStyleStatusOnPoint(linePos2[0] + i, true);
                int i3 = linePos2[1] + (checkboxSpan - lineEnd);
                linePos2[1] = i3;
                lineEnd = checkboxSpan;
                zRemoveCheckboxInRange = true;
                subSequence2 = getSubSequence(linePos2[0], i3);
            }
            i = lineEnd + 1;
        }
        if (!zRemoveCheckboxInRange) {
            clearTextWatcher();
            Editable text = getText();
            int i4 = linePos2[0];
            zRemoveCheckboxInRange = removeCheckboxInRange(text, i4, subSequence2.length() + i4);
            restoreTextWatcher();
        }
        if (zRemoveCheckboxInRange) {
            forceUpdateLayout();
            refreshBoxAndBullet();
            refreshExternalTextWatcher();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setEditorBullet() {
        int[] linePos = getLinePos();
        SpannableStringBuilder subSequence = getSubSequence(linePos[0], linePos[1]);
        if (((CheckboxSpan[]) subSequence.getSpans(0, subSequence.length(), CheckboxSpan.class)).length > 0) {
            removeCheckboxInRange(getText(), linePos[0], linePos[1]);
        }
        cancelRight();
        cancelCenter();
        int[] linePos2 = getLinePos();
        SpannableStringBuilder subSequence2 = getSubSequence(linePos2[0], linePos2[1]);
        int i = 0;
        boolean zRemoveEditorBulletInRange = false;
        while (i <= subSequence2.length()) {
            int lineEnd = getLineEnd(subSequence2.toString(), i);
            if (getSpanStartAt(subSequence2, BaseImageSpan.class, i) == null && getSpanStartAt(subSequence2, EditorBulletSpan.class, i) == null) {
                clearTextWatcher();
                int i2 = linePos2[0];
                int editorBulletSpan = getStyleFactory().setEditorBulletSpan(getText(), getStyleFactory().createEditorBulletSpan(), i2 + i, i2 + lineEnd);
                int i3 = linePos2[0];
                int i4 = editorBulletSpan - i3;
                changeStyleStatusOnPoint(i3 + i, true);
                restoreTextWatcher();
                int i5 = linePos2[1] + (i4 - lineEnd);
                linePos2[1] = i5;
                lineEnd = i4;
                zRemoveEditorBulletInRange = true;
                subSequence2 = getSubSequence(linePos2[0], i5);
            }
            i = lineEnd + 1;
        }
        if (!zRemoveEditorBulletInRange) {
            clearTextWatcher();
            Editable text = getText();
            int i6 = linePos2[0];
            zRemoveEditorBulletInRange = removeEditorBulletInRange(text, i6, subSequence2.length() + i6);
            restoreTextWatcher();
        }
        if (zRemoveEditorBulletInRange) {
            refreshContainer();
            refreshBoxAndBullet();
            refreshExternalTextWatcher();
        }
    }

    private void refreshBoxAndBullet() {
        getText().setSpan(Selection.SELECTION_START, getSelectionStart(), getSelectionStart(), R2.attr.buttonInlineDeleteBackground);
        getText().setSpan(Selection.SELECTION_END, getSelectionEnd(), getSelectionEnd(), 34);
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setBgHighLight() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        if (iMin == iMax) {
            if (getInputHelper().checkRichStyleInputMode(getText(), iMin, BgHighLightSpan.class)) {
                setNewInputModeRichStyle(new BgHighLightSpan(this), iMin, iMax);
            }
            changeStyleStatusOnPoint(iMin, false);
        } else {
            setRichStyleSpan(BgHighLightSpan.class, iMin, iMax);
        }
        if (iMax - iMin > 0) {
            refreshExternalTextWatcher();
        }
        forceUpdateLayout();
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setUnderline() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        if (iMin == iMax) {
            if (getInputHelper().checkRichStyleInputMode(getText(), iMin, UnderlineSpan.class)) {
                setNewInputModeRichStyle(new UnderlineSpan(), iMin, iMax);
            }
            changeStyleStatusOnPoint(iMin, false);
        } else {
            setRichStyleSpan(UnderlineSpan.class, iMin, iMax);
        }
        if (iMax - iMin > 0) {
            refreshExternalTextWatcher();
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void makeSpanContinuous(List<SpanInfo> list) throws IllegalArgumentException {
        if (list.size() <= 1) {
            return;
        }
        Collections.sort(list);
        boolean z = false;
        SpanInfo spanInfo = list.get(0);
        int style = spanInfo.getStyle();
        if (style == -1) {
            throw new IllegalArgumentException("Span style in List<SpanInfo> must be defined");
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i < list.size(); i++) {
            SpanInfo spanInfo2 = list.get(i);
            if (spanInfo2.getStyle() != style) {
                throw new IllegalArgumentException("Span style in List<SpanInfo> must be same");
            }
            if (spanInfo2.getStart() <= spanInfo.getEnd()) {
                spanInfo.setEnd(Math.max(spanInfo.getEnd(), spanInfo2.getEnd()));
                arrayList.add(Integer.valueOf(i));
                z = true;
            } else {
                spanInfo = spanInfo2;
            }
        }
        if (z) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                list.remove(((Integer) arrayList.get(size)).intValue());
            }
        }
    }

    public void makeFontSpanContinuous(List<SpanInfo> list) throws IllegalArgumentException {
        if (list.size() <= 1) {
            return;
        }
        Collections.sort(list);
        boolean z = false;
        SpanInfo spanInfo = list.get(0);
        int style = spanInfo.getStyle();
        if (style == -1) {
            throw new IllegalArgumentException("Span style in List<SpanInfo> must be defined");
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i < list.size(); i++) {
            SpanInfo spanInfo2 = list.get(i);
            if (spanInfo2.getStyle() != style) {
                throw new IllegalArgumentException("Span style in List<SpanInfo> must be same");
            }
            if (spanInfo2.getStart() > spanInfo.getStart() || spanInfo2.getEnd() < spanInfo.getEnd()) {
                spanInfo = spanInfo2;
            } else {
                spanInfo.setStart(Math.min(spanInfo.getStart(), spanInfo2.getStart()));
                spanInfo.setEnd(Math.max(spanInfo.getEnd(), spanInfo2.getEnd()));
                arrayList.add(Integer.valueOf(i));
                z = true;
            }
        }
        if (z) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                list.remove(((Integer) arrayList.get(size)).intValue());
            }
        }
    }

    private void makeSpanContinuous(List<SpanInfo>... listArr) throws IllegalArgumentException {
        for (List<SpanInfo> list : listArr) {
            makeSpanContinuous(list);
        }
    }

    private void makeFontSpanContinuous(List<SpanInfo>... listArr) throws IllegalArgumentException {
        for (List<SpanInfo> list : listArr) {
            makeFontSpanContinuous(list);
        }
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setSkipSelectionChanged(boolean z) {
        this.mSkipSelectionChanged = z;
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean isSkipSelectionChanged() {
        return this.mSkipSelectionChanged;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void setSkipRichText(boolean z) {
        this.mSkipRichText = z;
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean isSkipRichText() {
        return this.mSkipRichText;
    }

    @Override // com.miui.richeditor.IRichEditor
    public boolean isFirstLoad() {
        return this.mIsFirstLoad;
    }

    @Override // com.miui.richeditor.IRichEditor
    public void onSurpassContentLengthLimit() {
        makeTextToToast(getContext(), R.string.toast_reach_text_limit_truncate, 0);
    }

    public void makeTextToToast(Context context, int i, int i2) {
        Toast.makeText(context, i, i2).show();
    }

    public View getRootContainer() {
        return (ViewGroup) getRootView().findViewById(android.R.id.content);
    }

    @Override // com.miui.richeditor.IEditorContext
    public Theme getEditorTheme() {
        return this.mEditorTheme;
    }

    @Override // com.miui.richeditor.IEditorContext
    public Rect getVisibleRect() {
        if (this.mLocalVisibleRect == null) {
            this.mLocalVisibleRect = new Rect();
            refreshVisibleRect();
        }
        return this.mLocalVisibleRect;
    }

    @Override // com.miui.richeditor.IEditorContext
    public void refreshContainer() {
        Logger.INSTANCE.d(TAG, "refreshContainer");
        if (getScrollView() != null) {
            getScrollView().invalidate();
        } else {
            requestLayout();
        }
        invalidateDeeply();
    }

    @Override // com.miui.richeditor.IEditorContext
    public void refreshAllImageSpans() {
        prepareImages();
        invalidateDeeply();
    }

    @Override // com.miui.richeditor.IEditorContext
    public boolean isReadOnlyMode() {
        return this.mReadOnly;
    }

    @Override // com.miui.richeditor.IEditorContext
    public SmoothDrawHelper getRadiusDrawHelper() {
        return this.mSmoothDrawHelper;
    }

    @Override // com.miui.richeditor.IEditorContext
    public IEditorImageRender getImageRender() {
        return this.mEditorImageRender;
    }

    @Override // com.miui.richeditor.IEditorContext
    public LinkCardSpanHelper getLinkCardSpanHelper() {
        LinkCardSpanHelper linkCardSpanHelper = this.mLinkCardSpanHelper;
        if (linkCardSpanHelper == null) {
            this.mLinkCardSpanHelper = createLinkCardSpanHelper();
        } else if (this.isThemeChanged) {
            linkCardSpanHelper.updateTheme(this.mTheme);
        }
        this.isThemeChanged = false;
        return this.mLinkCardSpanHelper;
    }

    @Override // com.miui.richeditor.IEditorContext
    public int getWindowWidth() {
        return this.mWindowWidth;
    }

    @Override // com.miui.richeditor.IEditorContext
    public int getViewDensityDpi() {
        return getContext().getResources().getDisplayMetrics().densityDpi;
    }

    @Override // com.miui.richeditor.IEditorContext
    public int getBgHighLightColor() {
        try {
            return getContext().getColor(this.mEditorTheme.getEditorStyle(getContext()).mBgHighLightColor);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "getBgHighLightColor() error " + e.toString());
            return getContext().getColor(R.color.theme_default_bg_highlight_color);
        }
    }

    @Override // com.miui.richeditor.IEditorContext
    public SelectableSpanListener getSelectableSpanListener() {
        return this.mSelectableSpanListener;
    }

    @Override // com.miui.richeditor.IEditorContext
    public void onSpanClick(Object obj, int[] iArr) {
        EditorActionListener editorActionListener;
        if (obj instanceof NormalImageSpan) {
            EditorActionListener editorActionListener2 = this.mActionListener;
            if (editorActionListener2 != null) {
                editorActionListener2.onImageSpanClick((NormalImageSpan) obj, iArr);
                return;
            }
            return;
        }
        if (!(obj instanceof LinkCardSpan) || (editorActionListener = this.mActionListener) == null) {
            return;
        }
        editorActionListener.onLinkSpanClick((LinkCardSpan) obj, iArr);
    }

    public void onImageSpanClick(BaseImageSpan baseImageSpan, int[] iArr) {
        EditorActionListener editorActionListener;
        if (!(baseImageSpan instanceof NormalImageSpan) || baseImageSpan.isMissed() || (editorActionListener = this.mActionListener) == null) {
            return;
        }
        editorActionListener.onImageSpanClick(baseImageSpan, iArr);
    }

    public void onImageSpanLongClick(final BaseImageSpan baseImageSpan, final int[] iArr) {
        if (isEnabled()) {
            Logger.INSTANCE.d(TAG, "onImageSpanLongClick");
            refreshContainer();
            this.handler.post(new Runnable() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onImageSpanLongClick$2(baseImageSpan, iArr);
                }
            });
        }
    }

    public void onLinkCardClick(LinkCardSpan linkCardSpan, int[] iArr) {
        if (getText() != null) {
            setSelection(getText().getSpanEnd(linkCardSpan) + 1);
        }
        EditorActionListener editorActionListener = this.mActionListener;
        if (editorActionListener != null) {
            editorActionListener.onLinkSpanClick(linkCardSpan, iArr);
        }
    }

    @Override // com.miui.richeditor.IEditorContext
    public void onLinkCardLongClick(final LinkCardSpan linkCardSpan, final int[] iArr) {
        refreshContainer();
        this.handler.post(new Runnable() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onLinkCardLongClick$3(linkCardSpan, iArr);
            }
        });
    }

    @Override // com.miui.richeditor.IEditorContext
    public void onContactClick(BaseImageSpan baseImageSpan, int[] iArr) {
        ContactSpan contactSpan = (ContactSpan) baseImageSpan;
        Uri contactUri = ContactUtils.getContactUri(getContext(), contactSpan.getContactName(), contactSpan.getPhoneNumber());
        if (contactUri != null) {
            ContactsContract.QuickContact.showQuickContact(getContext(), this, contactUri, 3, (String[]) null);
        } else {
            makeTextToToast(getContext(), R.string.error_contact_not_exist, 0);
        }
    }

    @Override // com.miui.richeditor.IEditorContext
    public void onCheckboxClick(int[] iArr) {
        clearRichTextData();
        shouldNotifyEditChanged();
        invalidateDeeply();
    }

    public void onHintSpanClick(EditorHintEntrySpan editorHintEntrySpan, boolean z, int[] iArr) {
        if (!z) {
            requestFocus();
            setSelection(0);
            View.OnClickListener onClickListener = this.mExternalClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(this);
                return;
            } else {
                if (isReadOnlyMode()) {
                    return;
                }
                performClick();
                return;
            }
        }
        if (this.mActionListener == null || isReadOnlyMode()) {
            return;
        }
        this.mActionListener.onHintSpanClick(editorHintEntrySpan, z, iArr);
    }

    @Override // com.miui.richeditor.IEditorContext
    public void onImageAnnotationClick(BaseImageSpan baseImageSpan, int[] iArr) {
        setSelection(getText().getSpanStart(baseImageSpan), getText().getSpanEnd(baseImageSpan));
        EditorActionListener editorActionListener = this.mActionListener;
        if (editorActionListener != null) {
            editorActionListener.onImageSpanAnnotationClick(baseImageSpan, iArr);
        }
    }

    @Override // com.miui.richeditor.IEditorContext
    public void deleteImageSpan(final BaseImageSpan baseImageSpan, boolean z) {
        final int spanStart = getText().getSpanStart(baseImageSpan);
        final int spanEnd = getText().getSpanEnd(baseImageSpan);
        if (spanStart < 0 || spanEnd < 0) {
            return;
        }
        if (spanEnd < length() && getText().charAt(spanEnd) == '\n') {
            spanEnd++;
        }
        if (spanStart < spanEnd) {
            if (z) {
                ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(255, 0);
                valueAnimatorOfInt.setDuration(200L);
                valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.RichEditTextView.5
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        BaseImageSpan baseImageSpan2 = baseImageSpan;
                        if (baseImageSpan2 != null) {
                            baseImageSpan2.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        }
                        RichEditTextView.this.refreshContainer();
                    }
                });
                valueAnimatorOfInt.addListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.RichEditTextView.6
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        RichEditTextView.this.getText().delete(spanStart, spanEnd);
                        RichEditTextView.this.setSelection(spanStart);
                        RichEditTextView.this.forceUpdateLayout();
                        BaseImageSpan baseImageSpan2 = baseImageSpan;
                        if (baseImageSpan2 != null) {
                            baseImageSpan2.setAlpha(255);
                        }
                        RichEditTextView.this.requestFocus();
                        RichEditTextView.this.updateCursorVisible();
                        if (RichEditTextView.this.mExternalClickListener != null) {
                            RichEditTextView.this.mExternalClickListener.onClick(RichEditTextView.this);
                        }
                    }
                });
                valueAnimatorOfInt.start();
                return;
            }
            getText().delete(spanStart, spanEnd);
            setSelection(spanStart);
        }
    }

    @Override // com.miui.richeditor.IEditorContext
    public void startDragSpanAnim(final BaseImageSpan baseImageSpan, final int i, final int i2) {
        int[] iArr = new int[2];
        getLocationInWindow(iArr);
        int y = (baseImageSpan.getBounds().top + iArr[1]) - ((int) getY());
        int x = (baseImageSpan.getBounds().left + iArr[0]) - ((int) getX());
        final FrameLayout frameLayout = new FrameLayout(getContext());
        final ViewGroup viewGroup = (ViewGroup) getRootContainer();
        ShadowAnimImageView shadowAnimImageView = new ShadowAnimImageView(getContext());
        shadowAnimImageView.setImageDrawable(baseImageSpan.getDrawableForShadow());
        viewGroup.addView(frameLayout, new ViewGroup.LayoutParams(viewGroup.getWidth(), viewGroup.getHeight()));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(baseImageSpan.getImageWidth(), baseImageSpan.getImageHeight());
        layoutParams.setMargins(x, y, 0, 0);
        frameLayout.addView(shadowAnimImageView, layoutParams);
        shadowAnimImageView.setPivotX(i - baseImageSpan.getBounds().left);
        shadowAnimImageView.setPivotY((i2 - baseImageSpan.getBounds().top) + getY());
        shadowAnimImageView.animate().scaleX(0.685f).scaleY(0.685f).alpha(0.29f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(200L).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.RichEditTextView.8
            boolean isEnd = false;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (this.isEnd || valueAnimator.getAnimatedFraction() <= 0.98f) {
                    return;
                }
                this.isEnd = true;
                RichEditTextView.this.onImageSpanLongClick(baseImageSpan, new int[]{i, i2});
            }
        }).setListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.RichEditTextView.7
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                frameLayout.setAlpha(0.1f);
                viewGroup.removeView(frameLayout);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                viewGroup.removeView(frameLayout);
            }
        }).start();
    }

    @Override // com.miui.richeditor.IEditorContext
    public void deleteImageSpanAfterDragDrop(BaseImageSpan baseImageSpan) {
        Editable text = getText();
        if (text != null) {
            text.delete(text.getSpanStart(baseImageSpan), text.getSpanEnd(baseImageSpan));
        }
    }

    @Override // com.miui.richeditor.IEditorContext
    public void startDragLinkCardAnim(final LinkCardSpan linkCardSpan, final int[] iArr) {
        int[] iArr2 = new int[2];
        getLocationInWindow(iArr2);
        int i = linkCardSpan.getBounds().top + iArr2[1];
        int x = (linkCardSpan.getBounds().left + iArr2[0]) - ((int) getX());
        final FrameLayout frameLayout = new FrameLayout(getContext());
        final ViewGroup viewGroup = (ViewGroup) getRootContainer();
        ShadowAnimImageView shadowAnimImageView = new ShadowAnimImageView(getContext());
        shadowAnimImageView.setImageDrawable(linkCardSpan.getDrawableForShadow());
        viewGroup.addView(frameLayout, new ViewGroup.LayoutParams(viewGroup.getWidth(), viewGroup.getHeight()));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(linkCardSpan.getBounds().width(), linkCardSpan.getBounds().height());
        layoutParams.setMargins(x, i, 0, 0);
        frameLayout.addView(shadowAnimImageView, layoutParams);
        shadowAnimImageView.setPivotX(iArr[0] - linkCardSpan.getBounds().left);
        shadowAnimImageView.setPivotY(iArr[1] - linkCardSpan.getBounds().top);
        shadowAnimImageView.animate().scaleX(0.685f).scaleY(0.685f).alpha(0.29f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(200L).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.RichEditTextView.10
            boolean isEnd = false;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (this.isEnd || valueAnimator.getAnimatedFraction() <= 0.98f) {
                    return;
                }
                this.isEnd = true;
                RichEditTextView.this.onLinkCardLongClick(linkCardSpan, iArr);
            }
        }).setListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.RichEditTextView.9
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                frameLayout.setAlpha(0.1f);
                viewGroup.removeView(frameLayout);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                viewGroup.removeView(frameLayout);
            }
        }).start();
    }

    @Override // com.miui.richeditor.IEditorContext
    public void setHasUnknownTag(boolean z) {
        this.mHasUnknownTag = z;
    }

    @Override // com.miui.richeditor.IEditorContext
    public boolean hasUnknownTag() {
        return this.mHasUnknownTag;
    }

    @Override // com.miui.richeditor.IEditorContext
    public int getFontSizeId() {
        return this.mFontSizeId;
    }

    @Override // com.miui.richeditor.IEditorContext
    public float getFontScale() {
        return this.mFontScale;
    }

    @Override // com.miui.richeditor.IEditorContext
    public float getFontInnerScale() {
        return this.mFontInnerScale;
    }

    @Override // com.miui.richeditor.IEditorContext
    public float[] getFontSizesByLevel() {
        return this.mFontLevelSize;
    }

    @Override // com.miui.richeditor.IEditorContext
    public boolean shouldNotifyEditChanged() {
        Editable text = getText();
        int spanStart = text.getSpanStart(this);
        if (spanStart == 0) {
            return true;
        }
        try {
            return text.subSequence(0, spanStart).toString().trim().isEmpty();
        } catch (Exception unused) {
            return true;
        }
    }

    @Override // com.miui.richeditor.IEditorContext
    public int[] getPressedEnabledStateSet() {
        return PRESSED_ENABLED_STATE_SET;
    }

    @Override // com.miui.richeditor.IEditorContext
    public int[] getEnabledStateSet() {
        return ENABLED_STATE_SET;
    }

    @Override // com.miui.richeditor.IEditorContext
    public int getLineSpacing() {
        return (int) (getLineSpacingExtra() + ((getLineSpacingMultiplier() - 1.0f) * getLineHeight()));
    }

    @Override // com.miui.richeditor.IEditorContext
    public int getLineTextHeight(boolean z, float f) {
        int lineHeight = getLineHeight() - getLineSpacing();
        float lineHeight2 = getLineHeight() - (getPaint().getFontMetrics().bottom - getPaint().getFontMetrics().top);
        if (!z) {
            return lineHeight;
        }
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(getTextSize() * f);
        return Math.max(lineHeight, (int) ((textPaint.getFontMetrics().bottom - textPaint.getFontMetrics().top) + lineHeight2));
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void onAudioTextAppend(String str, boolean z) {
        if (!z) {
            if (str.equals("。")) {
                if (this.mAudioInputEnd == this.mAudioSpanEnd) {
                    return;
                }
                String string = getText().subSequence(0, this.mAudioInputStart).toString();
                if (string.length() > 0) {
                    if (string.toString().endsWith("。")) {
                        return;
                    }
                    if (string.toString().endsWith(",")) {
                        this.mAudioInputStart--;
                    }
                }
            }
            getText().replace(this.mAudioInputStart, this.mAudioInputEnd, str);
            int length = this.mAudioInputStart + str.length();
            this.mAudioInputStart = length;
            this.mAudioInputEnd = length;
            return;
        }
        getText().replace(this.mAudioInputStart, this.mAudioInputEnd, str);
        this.mAudioInputEnd = this.mAudioInputStart + str.length();
        requestLayout();
        invalidateDeeply();
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void onAudioEndAndFileSaved(final String str) {
        if (this.mPlayingAudioSpan != null) {
            lambda$onAudioEndAndFileSaved$4(str);
            return;
        }
        MainHandler<RichEditTextView> mainHandler = this.handler;
        if (mainHandler != null) {
            mainHandler.postDelayed(new Runnable() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAudioEndAndFileSaved$4(str);
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: solveAudioEndAndFileSaved, reason: merged with bridge method [inline-methods] */
    public void lambda$onAudioEndAndFileSaved$4(String str) {
        if (this.mPlayingAudioSpan == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.mPlayingAudioSpan.updateElement(str);
        this.mPlayingAudioSpan.setAnimRunningState(false);
        this.mPlayingAudioSpan = null;
        clearRichTextData();
        createNewRichTextData();
        ExternalTextWatcher externalTextWatcher = getExternalTextWatcher();
        if (externalTextWatcher != null) {
            externalTextWatcher.afterTextActuallyChanged(getEditableText());
        }
        this.handler.post(new Runnable() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$solveAudioEndAndFileSaved$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$solveAudioEndAndFileSaved$5() {
        requestLayout();
        invalidateDeeply();
    }

    public void startAudioInput() {
        this.mIsRecording = true;
        insertAudio();
        int selectionStart = getSelectionStart();
        this.mAudioInputStart = selectionStart;
        this.mAudioInputEnd = selectionStart;
        this.mAudioSpanEnd = selectionStart;
        setSelection(selectionStart, selectionStart);
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void stopAudioInput() {
        this.mIsRecording = false;
        cancelPlayAnimIfNeed();
    }

    public String getAudioFileDir() {
        return "";
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public MediaPlayer getMediaPlayer() {
        return this.mMediaPlayer;
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void clearMediaPlayer() {
        pauseAudio();
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
            this.mMediaPlayer = null;
        }
        this.mAudioManager = null;
    }

    public void startMediaPlayer(BaseAudioSpan baseAudioSpan, String str) {
        this.mMediaPlayer = new MediaPlayer();
        this.mAudioManager = (AudioManager) getContext().getSystemService(AITextWidgetStat.TYPE_AUDIO);
        this.mPlayingAudioSpan = baseAudioSpan;
        if (this.mMediaPlayerCallback == null) {
            this.mMediaPlayerCallback = new MediaPlayerCallback(this);
        }
        try {
            AudioAttributes audioAttributesBuild = new AudioAttributes.Builder().setUsage(1).setContentType(1).build();
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mMediaPlayer = mediaPlayer;
            mediaPlayer.setAudioAttributes(audioAttributesBuild);
            this.mMediaPlayer.setDataSource(str);
            this.mMediaPlayer.setOnCompletionListener(this.mMediaPlayerCallback);
            this.mMediaPlayer.setOnPreparedListener(this.mMediaPlayerCallback);
            this.mMediaPlayer.setOnErrorListener(this.mMediaPlayerCallback);
            this.mMediaPlayer.prepare();
            this.mAudioFocusRequest = new AudioFocusRequest.Builder(1).setAudioAttributes(audioAttributesBuild).setAcceptsDelayedFocusGain(true).setWillPauseWhenDucked(true).setOnAudioFocusChangeListener(this.mAudioFocusChangeListener).build();
        } catch (IOException e) {
            Logger.INSTANCE.e(TAG, "error in startMediaPlayer " + e.toString());
        }
    }

    public void stopMediaPlayerIfNeed() {
        pauseAudio();
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
                this.mMediaPlayer.release();
            }
            onPlayingRecord(false);
            this.mMediaPlayer = null;
            cancelPlayAnimIfNeed();
            this.mPlayingAudioSpan = null;
        }
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void playAudio() {
        Logger.INSTANCE.d(TAG, "playAudio");
        if (this.mMediaPlayer != null) {
            AudioFocusRequest audioFocusRequest = this.mAudioFocusRequest;
            if (audioFocusRequest != null) {
                int iRequestAudioFocus = this.mAudioManager.requestAudioFocus(audioFocusRequest);
                synchronized (this.mAudioFocusLock) {
                    try {
                        if (iRequestAudioFocus == 0) {
                            this.mPlaybackDelayed = false;
                        } else if (iRequestAudioFocus == 1) {
                            this.mPlaybackDelayed = false;
                            playAudioNow();
                        } else if (iRequestAudioFocus == 2) {
                            this.mPlaybackDelayed = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            }
            Logger.INSTANCE.e(TAG, "mAudioFocusRequest is null in playAudio");
        }
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void pauseAudio() {
        if (this.mAudioManager == null || this.mAudioFocusRequest == null) {
            return;
        }
        int iAbandonAudioFocusRequest = this.mAudioManager.abandonAudioFocusRequest(this.mAudioFocusRequest);
        Logger.INSTANCE.d(TAG, "pauseAudio result " + iAbandonAudioFocusRequest);
        if (iAbandonAudioFocusRequest == 0) {
            try {
                MediaPlayer mediaPlayer = this.mMediaPlayer;
                if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
                    return;
                }
                this.mMediaPlayer.pause();
                return;
            } catch (IllegalStateException e) {
                Logger.INSTANCE.d(TAG, e.toString());
                return;
            }
        }
        if (iAbandonAudioFocusRequest == 1) {
            try {
                MediaPlayer mediaPlayer2 = this.mMediaPlayer;
                if (mediaPlayer2 == null || !mediaPlayer2.isPlaying()) {
                    return;
                }
                this.mMediaPlayer.pause();
            } catch (IllegalStateException e2) {
                Logger.INSTANCE.d(TAG, e2.toString());
            }
        }
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void updateAllAudioSpans() {
        BaseAudioSpan[] baseAudioSpanArr = (BaseAudioSpan[]) getText().getSpans(0, length(), BaseAudioSpan.class);
        for (BaseAudioSpan baseAudioSpan : baseAudioSpanArr) {
            baseAudioSpan.reset();
            baseAudioSpan.setAnimRunningState(false);
        }
        if (baseAudioSpanArr.length > 0) {
            refreshAllImageSpans();
        }
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void pauseAudioAnim() {
        BaseAudioSpan baseAudioSpan = this.mPlayingAudioSpan;
        if (baseAudioSpan != null) {
            baseAudioSpan.pauseAnim();
        }
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void pauseAudioAnimAndRemoveListener() {
        BaseAudioSpan baseAudioSpan = this.mPlayingAudioSpan;
        if (baseAudioSpan != null) {
            baseAudioSpan.pauseAudioAnimAndRemoveListener();
        }
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void cancelPlayAnimIfNeed() {
        BaseAudioSpan baseAudioSpan = this.mPlayingAudioSpan;
        if (baseAudioSpan != null) {
            baseAudioSpan.cancelPlayAnimIfNeed();
        }
    }

    public boolean isSameComplete() {
        BaseAudioSpan baseAudioSpan = this.mPlayingAudioSpan;
        return baseAudioSpan != null && baseAudioSpan.getAudioDrawable().getmPointerOffset() == 0;
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public boolean isRecording() {
        return this.mIsRecording;
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void setLastPlayedAudioFile(String str) {
        this.mLastPlayedAudioFile = str;
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public String getLastPlayedAudioFile() {
        return this.mLastPlayedAudioFile;
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public BaseAudioSpan getPlayingAudioSpan() {
        return this.mPlayingAudioSpan;
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void setAudioGestureMoveInWave(boolean z) {
        this.mAudioGestureMoveInWave = z;
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public boolean isAudioGestureMoveInWave() {
        return this.mAudioGestureMoveInWave;
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void onPlayAudioError(int i) {
        if (i == 0) {
            makeTextToToast(getContext(), R.string.audio_toast_file_invalid, 0);
        } else if (i == 1) {
            makeTextToToast(getContext(), R.string.audio_toast_file_undownload, 0);
        }
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void requestDisallowInterceptTouchEvent(boolean z) {
        RichEditScrollView richEditScrollView = this.mScrollView;
        if (richEditScrollView != null) {
            richEditScrollView.requestDisallowParentInterceptTouchEvent(true);
        }
        getParent().requestDisallowInterceptTouchEvent(true);
    }

    @Override // com.miui.richeditor.IAudioEditorContext
    public void startDragAudioSpan(final BaseAudioSpan baseAudioSpan, final int[] iArr) {
        int[] iArr2 = new int[2];
        getLocationInWindow(iArr2);
        int y = (baseAudioSpan.getBounds().top + iArr2[1]) - ((int) getY());
        int x = (baseAudioSpan.getBounds().left + iArr2[0]) - ((int) getX());
        final FrameLayout frameLayout = new FrameLayout(getContext());
        final ViewGroup viewGroup = (ViewGroup) getRootContainer();
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(baseAudioSpan.getDrawableForShadow());
        imageView.setBackgroundColor(0);
        viewGroup.addView(frameLayout, new ViewGroup.LayoutParams(viewGroup.getWidth(), viewGroup.getHeight()));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(baseAudioSpan.getImageWidth(), baseAudioSpan.getImageHeight());
        layoutParams.setMargins(x, y, 0, 0);
        frameLayout.addView(imageView, layoutParams);
        imageView.setPivotX(iArr[0] - baseAudioSpan.getBounds().left);
        imageView.setPivotY((iArr[1] - baseAudioSpan.getBounds().top) + getY());
        imageView.animate().scaleX(0.69f).scaleY(0.69f).alpha(0.29f).setInterpolator(new EaseManager.SpringInterpolator()).setDuration(200L).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.RichEditTextView.12
            boolean isEnd = false;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (this.isEnd || valueAnimator.getAnimatedFraction() <= 0.98f) {
                    return;
                }
                this.isEnd = true;
                RichEditTextView.this.onImageSpanLongClick(baseAudioSpan, iArr);
            }
        }).setListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.RichEditTextView.11
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                frameLayout.setAlpha(0.1f);
                viewGroup.removeView(frameLayout);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                viewGroup.removeView(frameLayout);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playAudioNow() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public boolean commitComposingContent() {
        if (BaseInputConnection.getComposingSpanStart(getText()) < 0) {
            return false;
        }
        clearComposingText();
        return true;
    }

    public void refreshVisibleRect() {
        if (this.mLocalVisibleRect == null) {
            this.mLocalVisibleRect = new Rect();
        }
        if (getLocalVisibleRect(this.mLocalVisibleRect)) {
            this.mLocalVisibleRect.offset(0, (int) getY());
        } else {
            this.mLocalVisibleRect.setEmpty();
        }
    }

    public void showLoadingView() {
        Runnable runnable = new Runnable() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showLoadingView$6();
            }
        };
        this.showLoadingViewTask = runnable;
        this.handler.postDelayed(runnable, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLoadingView$6() {
        setVisibility(4);
        requestLayout();
        View view = this.mLoadingContainer;
        if (view != null) {
            view.setVisibility(0);
            ProgressBar progressBar = (ProgressBar) this.mLoadingContainer.findViewById(R.id.progress_bar);
            progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.edit_fragment_loading_color), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void hideLoadingView() {
        Runnable runnable = this.showLoadingViewTask;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
            this.showLoadingViewTask = null;
        }
        setVisibility(0);
        View view = this.mLoadingContainer;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    public void cutImageSpan(final BaseImageSpan baseImageSpan, boolean z) {
        final int spanStart = getText() == null ? -1 : getText().getSpanStart(baseImageSpan);
        final int spanEnd = getText() != null ? getText().getSpanEnd(baseImageSpan) : -1;
        if (spanStart < 0 || spanEnd < 0) {
            return;
        }
        if (spanEnd < length() && getText().charAt(spanEnd) == '\n') {
            spanEnd++;
        }
        Editable editable = (Editable) getText().subSequence(spanStart, spanEnd);
        if (getText() != null) {
            NoteClipManager.INSTANCE.clipContent(editable);
        }
        adjustCopiedText(editable);
        if (spanStart < spanEnd) {
            if (z) {
                ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(255, 0);
                valueAnimatorOfInt.setDuration(200L);
                valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.RichEditTextView$$ExternalSyntheticLambda7
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$cutImageSpan$7(baseImageSpan, valueAnimator);
                    }
                });
                valueAnimatorOfInt.addListener(new Animator.AnimatorListener() { // from class: com.miui.richeditor.RichEditTextView.13
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        RichEditTextView.this.getText().delete(spanStart, spanEnd);
                        RichEditTextView.this.setSelection(spanStart);
                        RichEditTextView.this.forceUpdateLayout();
                        BaseImageSpan baseImageSpan2 = baseImageSpan;
                        if (baseImageSpan2 != null) {
                            baseImageSpan2.setAlpha(255);
                        }
                        RichEditTextView.this.requestFocus();
                        RichEditTextView.this.updateCursorVisible();
                        if (RichEditTextView.this.mExternalClickListener != null) {
                            RichEditTextView.this.mExternalClickListener.onClick(RichEditTextView.this);
                        }
                    }
                });
                valueAnimatorOfInt.start();
                return;
            }
            getText().delete(spanStart, spanEnd);
            setSelection(spanStart);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cutImageSpan$7(BaseImageSpan baseImageSpan, ValueAnimator valueAnimator) {
        if (baseImageSpan != null) {
            baseImageSpan.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
        getParent().requestLayout();
    }

    public void moveImageSpanAfterDragDrop(BaseImageSpan baseImageSpan, boolean z) {
        int i;
        int lineForOffset = getLayout().getLineForOffset(getText().getSpanStart(this.mDraggingImageSpan));
        BaseImageSpan baseImageSpanCreateLinkCardSpan = null;
        baseImageSpanCreateLinkCardSpan = null;
        this.mDraggingImageSpan = null;
        Editable text = getText();
        if (text == null || (i = this.mSelectLine) == -2) {
            return;
        }
        int spanStart = text.getSpanStart(baseImageSpan);
        int spanEnd = text.getSpanEnd(baseImageSpan);
        Spannable spannableNewSpannable = Spannable.Factory.getInstance().newSpannable(text.subSequence(spanStart, spanEnd < text.length() ? spanEnd + 1 : spanEnd));
        setSkipSelectionChanged(true);
        setSkipRichText(true);
        removeTextChangedListener(getRichTextWatcher());
        int lineCount = getLineCount();
        if (spanEnd < text.length()) {
            int i2 = spanEnd + 1;
            if (StringUtils.LF.equals(text.subSequence(spanEnd, i2).toString())) {
                text.delete(spanEnd, i2);
            }
        }
        int lineCount2 = getLineCount();
        int spanStart2 = spannableNewSpannable.getSpanStart(baseImageSpan);
        int spanEnd2 = spannableNewSpannable.getSpanEnd(baseImageSpan);
        spannableNewSpannable.removeSpan(baseImageSpan);
        if (baseImageSpan instanceof NormalImageSpan) {
            NormalImageSpan normalImageSpan = (NormalImageSpan) baseImageSpan;
            NormalImageSpan normalImageSpanCopyNormalImageSpan = this.mStyleFactory.copyNormalImageSpan(normalImageSpan);
            normalImageSpanCopyNormalImageSpan.copyImage(normalImageSpan.getOriginBitmap());
            normalImageSpanCopyNormalImageSpan.initByContainer();
            baseImageSpanCreateLinkCardSpan = normalImageSpanCopyNormalImageSpan;
        } else if (baseImageSpan instanceof BaseAudioSpan) {
            stopMediaPlayerIfNeed();
            try {
                baseImageSpanCreateLinkCardSpan = ((BaseAudioSpan) baseImageSpan).mo2118clone();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (baseImageSpan instanceof LinkCardSpan) {
            baseImageSpanCreateLinkCardSpan = this.mStyleFactory.createLinkCardSpan(baseImageSpan.getElement());
        }
        if (spanStart2 >= 0 && spanEnd2 >= 0) {
            spannableNewSpannable.setSpan(baseImageSpanCreateLinkCardSpan, spanStart2, spanEnd2, 33);
        }
        if (i == -1) {
            insertImageString(text, 0, spannableNewSpannable);
            baseImageSpan.startDismissAnim();
            if (baseImageSpanCreateLinkCardSpan != null) {
                baseImageSpanCreateLinkCardSpan.startInsertAnim();
            }
        } else {
            if (lineCount2 < lineCount && lineForOffset < i && (i = (i - lineCount) + lineCount2) < 0) {
                i = 0;
            }
            insertImageString(text, getLayout().getLineEnd(i), spannableNewSpannable);
            baseImageSpan.startDismissAnim();
            if (baseImageSpanCreateLinkCardSpan != null) {
                baseImageSpanCreateLinkCardSpan.startInsertAnim();
            }
            Logger.INSTANCE.d(TAG, "moveImageSpan linesBefore = " + lineCount + " linesAfter = " + lineCount2 + " insertLine = " + i + " selectLine = " + this.mSelectLine);
        }
        addTextChangedListener(getRichTextWatcher());
        setSkipRichText(false);
        setSkipSelectionChanged(false);
    }

    private void insertImageString(Editable editable, int i, CharSequence charSequence) {
        CheckboxSpan[] checkboxSpanArr;
        int spanStart;
        CheckboxSpan checkboxSpan;
        int spanEnd;
        EditorBulletSpan[] editorBulletSpanArr;
        int i2;
        EditorBulletSpan editorBulletSpan;
        int spanEnd2;
        AlignmentSpan[] alignmentSpanArr;
        int spanStart2;
        int i3 = i;
        AlignmentSpan alignmentSpan = null;
        int spanEnd3 = 0;
        if ((i3 < editable.length() || i3 == 0) && (checkboxSpanArr = (CheckboxSpan[]) editable.getSpans(i3, i3 + 1, CheckboxSpan.class)) != null && checkboxSpanArr.length > 0) {
            if (i3 == 0) {
                i3 = 2;
            }
            editable.insert(i3 - 1, StringUtils.LF);
            spanStart = editable.getSpanStart(checkboxSpanArr[0]);
            int spanEnd4 = editable.getSpanEnd(checkboxSpanArr[0]);
            if (spanStart > i3 || spanEnd4 < i3) {
                checkboxSpan = null;
                spanEnd = 0;
            } else {
                checkboxSpan = checkboxSpanArr[0];
                spanEnd = editable.getSpanEnd(checkboxSpan) - editable.getSpanStart(checkboxSpan);
            }
        } else {
            checkboxSpan = null;
            spanStart = 0;
            spanEnd = 0;
        }
        if ((i3 < editable.length() || i3 == 0) && (editorBulletSpanArr = (EditorBulletSpan[]) editable.getSpans(i3, i3 + 1, EditorBulletSpan.class)) != null && editorBulletSpanArr.length > 0) {
            int i4 = i3 != 0 ? i3 : 2;
            editable.insert(i4 - 1, StringUtils.LF);
            int spanStart3 = editable.getSpanStart(editorBulletSpanArr[0]);
            int spanEnd5 = editable.getSpanEnd(editorBulletSpanArr[0]);
            if (spanStart3 > i4 || spanEnd5 < i4) {
                int i5 = i4;
                i2 = spanStart3;
                i3 = i5;
                editorBulletSpan = null;
                spanEnd2 = 0;
            } else {
                editorBulletSpan = editorBulletSpanArr[0];
                spanEnd2 = editable.getSpanEnd(editorBulletSpan) - editable.getSpanStart(editorBulletSpan);
                int i6 = i4;
                i2 = spanStart3;
                i3 = i6;
            }
        } else {
            editorBulletSpan = null;
            i2 = 0;
            spanEnd2 = 0;
        }
        if ((i3 < editable.length() || i3 == 0) && (alignmentSpanArr = (AlignmentSpan[]) editable.getSpans(i3, i3 + 1, AlignmentSpan.class)) != null && alignmentSpanArr.length > 0) {
            spanStart2 = editable.getSpanStart(alignmentSpanArr[0]);
            int spanEnd6 = editable.getSpanEnd(alignmentSpanArr[0]);
            if (spanStart2 <= i3 && spanEnd6 >= i3) {
                alignmentSpan = alignmentSpanArr[0];
                spanEnd3 = editable.getSpanEnd(alignmentSpan) - editable.getSpanStart(alignmentSpan);
            }
        } else {
            spanStart2 = 0;
        }
        if (i3 > 0 && !StringUtils.LF.equals(editable.subSequence(i3 - 1, i3).toString())) {
            editable.insert(i3, StringUtils.LF);
            i3++;
        }
        editable.insert(i3, charSequence);
        if (checkboxSpan != null) {
            int length = charSequence.length() + i3;
            this.mStyleFactory.setCheckboxSpan(editable, checkboxSpan, spanStart, i3);
            this.mStyleFactory.setCheckboxSpan(editable, getStyleFactory().createCheckboxSpan(checkboxSpan.getElement().isChecked()), length, spanEnd + length);
        }
        if (editorBulletSpan != null) {
            int length2 = charSequence.length() + i3;
            this.mStyleFactory.setEditorBulletSpan(editable, editorBulletSpan, i2, i3);
            this.mStyleFactory.setEditorBulletSpan(editable, getStyleFactory().createEditorBulletSpan(), length2, spanEnd2 + length2);
        }
        if (alignmentSpan != null) {
            int length3 = charSequence.length() + i3;
            getText().setSpan(alignmentSpan, spanStart2, i3, 18);
            getText().setSpan(new AlignmentSpan.Standard(alignmentSpan.getAlignment()), length3, spanEnd3 + length3, 18);
        }
    }

    public void restoreNormalImageSpan(NormalImageSpan normalImageSpan) {
        normalImageSpan.restoreImage();
        refreshContainer();
    }

    protected void adjustCopiedText(Editable editable) {
        boolean z;
        int i;
        ClipData.Item itemAt;
        boolean z2;
        int length = editable.length();
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
        if (clipboardManager == null) {
            return;
        }
        ClipData primaryClip = EditorUtils.getPrimaryClip(clipboardManager);
        int i2 = 0;
        ClipData clipData = (primaryClip == null || primaryClip.getItemCount() <= 0) ? null : new ClipData(new ClipDescription(IRichEditor.CLIP_LABEL_RICH_EDITOR, new String[]{"text/plain"}), primaryClip.getItemAt(0));
        ArrayList arrayList = new ArrayList();
        BaseImageSpan[] baseImageSpanArr = (BaseImageSpan[]) editable.getSpans(0, length, BaseImageSpan.class);
        int length2 = baseImageSpanArr.length;
        int i3 = 0;
        boolean z3 = false;
        while (i3 < length2) {
            BaseImageSpan baseImageSpan = baseImageSpanArr[i3];
            int i4 = i2;
            if (!(baseImageSpan instanceof AudioSpan)) {
                ClipData.Item itemCreateImageClipDataItem = createImageClipDataItem(baseImageSpan);
                if (itemCreateImageClipDataItem != null) {
                    arrayList.add(itemCreateImageClipDataItem);
                }
            } else {
                int spanStart = editable.getSpanStart(baseImageSpan);
                int spanEnd = editable.getSpanEnd(baseImageSpan);
                editable.removeSpan(baseImageSpan);
                editable.delete(spanStart, spanEnd);
                if (!z3) {
                    z3 = true;
                }
            }
            i3++;
            i2 = i4;
        }
        int i5 = i2;
        if (z3) {
            String[] strArr = new String[1];
            strArr[i5] = "text/plain";
            clipData = new ClipData(new ClipDescription(IRichEditor.CLIP_LABEL_RICH_EDITOR, strArr), new ClipData.Item(editable));
            clipboardManager.setPrimaryClip(clipData);
        }
        if (arrayList.isEmpty()) {
            z = true;
        } else {
            if (clipData == null || clipData.getItemAt(i5).getText() == null || "￼".equals(clipData.getItemAt(i5).getText().toString()) || "￼".equals(editable.toString())) {
                z = true;
                ClipData clipData2 = new ClipData(new ClipDescription(IRichEditor.CLIP_LABEL_RICH_EDITOR, new String[]{"image/jpeg"}), (ClipData.Item) arrayList.get(0));
                for (int i6 = 1; i6 < arrayList.size(); i6++) {
                    clipData2.addItem((ClipData.Item) arrayList.get(i6));
                }
                clipData = clipData2;
            } else {
                for (int i7 = 0; i7 < arrayList.size(); i7++) {
                    if (primaryClip != null) {
                        primaryClip.addItem((ClipData.Item) arrayList.get(i7));
                    }
                    clipData.addItem(getContext().getContentResolver(), (ClipData.Item) arrayList.get(i7));
                }
                z = true;
            }
            clipboardManager.setPrimaryClip(clipData);
        }
        int i8 = 0;
        LinkCardSpan[] linkCardSpanArr = (LinkCardSpan[]) editable.getSpans(0, length, LinkCardSpan.class);
        int length3 = linkCardSpanArr.length;
        int i9 = 0;
        while (i9 < length3) {
            LinkCardSpan linkCardSpan = linkCardSpanArr[i9];
            int iMax = Math.max(i8, editable.getSpanStart(linkCardSpan));
            int iMin = Math.min(length, editable.getSpanEnd(linkCardSpan));
            Object objCreateLinkCardSpan = this.mStyleFactory.createLinkCardSpan(linkCardSpan.getElement());
            editable.removeSpan(linkCardSpan);
            editable.setSpan(objCreateLinkCardSpan, iMax, iMin, 33);
            i9++;
            i8 = 0;
        }
        if (clipData != null) {
            i = 0;
            itemAt = clipData.getItemAt(0);
        } else {
            i = 0;
            if (primaryClip == null) {
                return;
            } else {
                itemAt = primaryClip.getItemAt(0);
            }
        }
        CharSequence text = itemAt.getText();
        if (text instanceof Spannable) {
            Spannable spannable = (Spannable) text;
            CheckboxSpan[] checkboxSpanArr = (CheckboxSpan[]) editable.getSpans(i, length, CheckboxSpan.class);
            if (checkboxSpanArr.length > 0) {
                boolean z4 = false;
                for (CheckboxSpan checkboxSpan : checkboxSpanArr) {
                    if (!checkboxSpan.getElement().isChecked()) {
                        StrikethroughSpan[] strikethroughSpanArr = (StrikethroughSpan[]) spannable.getSpans(Math.max(0, editable.getSpanStart(checkboxSpan)), Math.min(length, editable.getSpanEnd(checkboxSpan)), StrikethroughSpan.class);
                        int length4 = strikethroughSpanArr.length;
                        boolean z5 = z4;
                        int i10 = 0;
                        while (i10 < length4) {
                            spannable.removeSpan(strikethroughSpanArr[i10]);
                            i10++;
                            z5 = z;
                        }
                        z4 = z5;
                    }
                }
                z2 = z4;
            } else {
                z2 = false;
            }
            if (z2) {
                if (clipData != null) {
                    clipboardManager.setPrimaryClip(clipData);
                } else {
                    clipboardManager.setPrimaryClip(primaryClip);
                }
            }
        }
    }

    protected ClipData.Item createImageClipDataItem(BaseImageSpan baseImageSpan) {
        File file = new File(AttachmentUtils.getAttachmentPath(getContext(), baseImageSpan.getSrcFileId()));
        if (file.exists()) {
            return new ClipData.Item(ImageUtils.INSTANCE.getImageContentUri(getContext(), file));
        }
        return null;
    }

    protected void normalWithPosition(int i, int i2) {
        if (setAlignNormal(i, i2)) {
            refreshExternalTextWatcher();
        }
        clearFocus();
        performClick();
        invalidate();
        forceUpdateLayout();
    }

    protected void rightWithPosition(int i, int i2) {
        if (setAlignRight(i, i2)) {
            refreshExternalTextWatcher();
        }
        performClick();
        invalidate();
        forceUpdateLayout();
    }

    protected void centerWithPosition(int i, int i2) {
        if (setAlignCenter(i, i2)) {
            refreshExternalTextWatcher();
        }
        performClick();
        invalidate();
        forceUpdateLayout();
    }

    protected void cancelCenter() {
        for (AlignmentSpan alignmentSpan : (AlignmentSpan[]) getText().getSpans(Math.min(getSelectionStart(), getSelectionEnd()), Math.max(getSelectionStart(), getSelectionEnd()), AlignmentSpan.class)) {
            if (alignmentSpan.getAlignment() == Layout.Alignment.ALIGN_CENTER) {
                getText().removeSpan(alignmentSpan);
            }
        }
    }

    protected void cancelRight() {
        for (AlignmentSpan alignmentSpan : (AlignmentSpan[]) getText().getSpans(Math.min(getSelectionStart(), getSelectionEnd()), Math.max(getSelectionStart(), getSelectionEnd()), AlignmentSpan.class)) {
            if (alignmentSpan.getAlignment() == Layout.Alignment.ALIGN_OPPOSITE) {
                getText().removeSpan(alignmentSpan);
            }
        }
    }

    protected boolean hasCheckbox(int i, int i2) {
        return ((CheckboxSpan[]) getText().getSpans(i, i2, CheckboxSpan.class)).length > 0;
    }

    private boolean hasEditorBullet(int i, int i2) {
        return ((EditorBulletSpan[]) getText().getSpans(i, i2, EditorBulletSpan.class)).length > 0;
    }

    public SpanInfo getSpanInfo(Object obj) {
        return getSpanInfo(getText(), obj);
    }

    public SpanInfo getSpanInfo(Spannable spannable, Object obj) {
        return new SpanInfo(obj, spannable.getSpanStart(obj), spannable.getSpanEnd(obj), spannable.getSpanFlags(obj), getTextSize());
    }

    public ArrayList<ParagraphInfo> getParagraphs(String str, int i, int i2) {
        ArrayList<ParagraphInfo> arrayList = new ArrayList<>();
        if (str.length() != 0) {
            if (i == i2) {
                if (i == str.length()) {
                    int i3 = i - 1;
                    if (str.charAt(i3) != '\n') {
                        return getParagraphs(i3, i2 - 1);
                    }
                } else {
                    int iLastIndexOf = str.lastIndexOf(10, i - 1) + 1;
                    int iIndexOf = str.indexOf(10, i2);
                    if (iIndexOf == -1) {
                        iIndexOf = str.length() - 1;
                    }
                    arrayList.add(new ParagraphInfo(iLastIndexOf, iIndexOf));
                    return arrayList;
                }
            } else {
                int iLastIndexOf2 = str.lastIndexOf(10, i - 1) + 1;
                int iIndexOf2 = str.indexOf(10, i2);
                if (iIndexOf2 == -1) {
                    iIndexOf2 = str.length() - 1;
                }
                while (iLastIndexOf2 < iIndexOf2) {
                    int iIndexOf3 = str.indexOf(10, iLastIndexOf2);
                    if (iIndexOf3 == -1) {
                        iIndexOf3 = str.length() - 1;
                    }
                    arrayList.add(new ParagraphInfo(iLastIndexOf2, iIndexOf3));
                    iLastIndexOf2 = iIndexOf3 + 1;
                }
            }
        }
        return arrayList;
    }

    private ArrayList<ParagraphInfo> getParagraphs(int i, int i2) {
        return getParagraphs(getText().toString(), i, i2);
    }

    protected <T> T[] sortSpans(T[] tArr, final Spannable spannable) {
        if (tArr.length < 2) {
            return tArr;
        }
        ArrayList arrayList = new ArrayList(tArr.length);
        Collections.addAll(arrayList, tArr);
        Collections.sort(arrayList, new Comparator<T>() { // from class: com.miui.richeditor.RichEditTextView.14
            @Override // java.util.Comparator
            public int compare(T t, T t2) {
                return spannable.getSpanStart(t) - spannable.getSpanStart(t2);
            }
        });
        return (T[]) arrayList.toArray(tArr);
    }

    protected void styleValid(Object obj, int i, int i2) {
        if (i >= i2 || i < 0 || i2 < 0) {
            return;
        }
        if (obj.getClass() != AlignmentSpan.Standard.class) {
            for (Object obj2 : getText().getSpans(i, i2, obj.getClass())) {
                SpanInfo spanInfo = getSpanInfo(obj2);
                if ((!(obj instanceof StyleSpan) || ((StyleSpan) obj).getStyle() == ((StyleSpan) obj2).getStyle()) && spanInfo.isValid()) {
                    if (spanInfo.getStart() <= i && spanInfo.getEnd() >= i2) {
                        if (i2 - i == 1 && getText().charAt(i) == '\n') {
                            forceUpdateLayout();
                            return;
                        }
                        return;
                    }
                    if (spanInfo.getStart() > i && spanInfo.getEnd() < i2) {
                        removeSpan(obj2);
                    }
                }
            }
            SpanInfo beforeAdjacentSpan = getBeforeAdjacentSpan(obj, i);
            if (beforeAdjacentSpan != null && beforeAdjacentSpan.isValid()) {
                i = beforeAdjacentSpan.getStart();
                removeSpan(beforeAdjacentSpan.getSpan());
            }
            SpanInfo afterAdjacentSpan = getAfterAdjacentSpan(obj, i2);
            if (afterAdjacentSpan != null && afterAdjacentSpan.isValid()) {
                i2 = afterAdjacentSpan.getEnd();
                removeSpan(afterAdjacentSpan.getSpan());
            }
        }
        getText().setSpan(obj, i, i2, 33);
    }

    protected SpanInfo getBeforeAdjacentSpan(Object obj, int i) {
        if (i > 0 && i < getText().length()) {
            for (Object obj2 : getText().getSpans(0, i, obj.getClass())) {
                if (obj.getClass() != StyleSpan.class || ((StyleSpan) obj2).getStyle() == ((StyleSpan) obj).getStyle()) {
                    SpanInfo spanInfo = getSpanInfo(obj2);
                    if (spanInfo.isValid() && spanInfo.getEnd() == i) {
                        return spanInfo;
                    }
                }
            }
        }
        return null;
    }

    protected SpanInfo getAfterAdjacentSpan(Object obj, int i) {
        if (i > 0 && i < getText().length()) {
            for (Object obj2 : getText().getSpans(i, getText().length(), obj.getClass())) {
                if (obj.getClass() != StyleSpan.class || ((StyleSpan) obj2).getStyle() == ((StyleSpan) obj).getStyle()) {
                    SpanInfo spanInfo = getSpanInfo(obj2);
                    if (spanInfo.isValid() && spanInfo.getStart() == i) {
                        return spanInfo;
                    }
                }
            }
        }
        return null;
    }

    protected void styleValid(int i, int i2, int i3) {
        switch (i) {
            case 1:
            case 2:
                styleValid(new StyleSpan(i), i2, i3);
                break;
            case 3:
                removeStyleSizeInRange(getText(), i2, i3);
                getStyleFactory().setFontSizeSpan(getText(), i2, i3, 2);
                break;
            case 4:
                setAlignCenter(i2, i3);
                break;
            case 5:
                styleValid(new UnderlineSpan(), i2, i3);
                break;
            case 6:
                setAlignRight(i2, i3);
                break;
            case 7:
                styleValid(getStyleFactory().createKeywordsSpan(), i2, i3);
                break;
            case 10:
                removeStyleSizeInRange(getText(), i2, i3);
                getStyleFactory().setFontSizeSpan(getText(), i2, i3, 1);
                break;
            case 11:
                styleValid(new BgHighLightSpan(this), i2, i3);
                break;
            case 12:
                removeStyleSizeInRange(getText(), i2, i3);
                getStyleFactory().setFontSizeSpan(getText(), i2, i3, 0);
                break;
        }
    }

    protected <T> void styleInValid(Class<T> cls, int i, int i2, int i3) {
        Object[] objArr;
        int i4;
        if (i2 >= i3) {
            return;
        }
        Object[] spans = getEditableText().getSpans(i2, i3, cls);
        ArrayList<SpanInfo> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        int i5 = 0;
        for (int length = spans.length; i5 < length; length = i4) {
            Object obj = spans[i5];
            SpanInfo spanInfo = getSpanInfo(obj);
            if (spanInfo.isValid()) {
                objArr = spans;
                if ((obj instanceof StyleSpan) && ((StyleSpan) obj).getStyle() == i) {
                    if (i == 1 && !arrayList4.contains(spanInfo)) {
                        arrayList4.add(spanInfo);
                    } else if (i == 2 && !arrayList5.contains(spanInfo)) {
                        arrayList5.add(spanInfo);
                    }
                    removeSpan(obj);
                } else if ((obj instanceof AbsoluteSizeSpan) || (obj instanceof RelativeSizeSpan)) {
                    i4 = length;
                    if (!arrayList2.contains(spanInfo) && i == 3) {
                        arrayList2.add(spanInfo);
                    } else if (!arrayList3.contains(spanInfo) && i == 10) {
                        arrayList3.add(spanInfo);
                    }
                    for (TypefaceSpan typefaceSpan : (TypefaceSpan[]) getText().getSpans(spanInfo.getStart(), spanInfo.getEnd(), TypefaceSpan.class)) {
                        removeSpan(typefaceSpan);
                    }
                    removeSpan(obj);
                } else {
                    if (obj instanceof AlignmentSpan) {
                        AlignmentSpan alignmentSpan = (AlignmentSpan) obj;
                        i4 = length;
                        if (alignmentSpan.getAlignment() == Layout.Alignment.ALIGN_CENTER || alignmentSpan.getAlignment() == Layout.Alignment.ALIGN_OPPOSITE) {
                            removeSpan(obj);
                        }
                    } else {
                        i4 = length;
                    }
                    if (obj instanceof UnderlineSpan) {
                        if (!arrayList6.contains(spanInfo)) {
                            arrayList6.add(spanInfo);
                        }
                        removeSpan(obj);
                    } else if (obj instanceof BackgroundColorSpan) {
                        if (!arrayList7.contains(spanInfo)) {
                            arrayList7.add(spanInfo);
                        }
                        removeSpan(obj);
                    }
                }
                i5++;
                spans = objArr;
            } else {
                objArr = spans;
            }
            i4 = length;
            i5++;
            spans = objArr;
        }
        makeSpanContinuous(arrayList4, arrayList5, arrayList6, arrayList7);
        makeFontSpanContinuous(arrayList2, arrayList3);
        arrayList.addAll(arrayList2);
        arrayList.addAll(arrayList3);
        arrayList.addAll(arrayList4);
        arrayList.addAll(arrayList5);
        arrayList.addAll(arrayList6);
        arrayList.addAll(arrayList7);
        for (SpanInfo spanInfo2 : arrayList) {
            if (spanInfo2.getStart() < i2) {
                styleValid(i, spanInfo2.getStart(), i2);
            }
            if (spanInfo2.getEnd() > i3) {
                styleValid(i, i3, spanInfo2.getEnd());
            }
        }
    }

    protected <T> void reSetSpan(Class<T> cls, int i, int i2, int i3) {
        if (i2 >= i3) {
            return;
        }
        Object[] spans = getEditableText().getSpans(i2, i3, cls);
        ArrayList<RegionInfo> arrayList = new ArrayList();
        for (Object obj : spans) {
            SpanInfo spanInfo = getSpanInfo(obj);
            if ((spanInfo.getStart() == i2 || spanInfo.getEnd() == i3) && obj.getClass() == StyleSpan.class && ((StyleSpan) obj).getStyle() == i) {
                arrayList.add(spanInfo);
                removeSpan(obj);
            }
        }
        for (RegionInfo regionInfo : arrayList) {
            if (regionInfo.isValid()) {
                styleValid(i, regionInfo.getStart(), regionInfo.getEnd());
            }
        }
    }

    private StyleSpan[] getStyleSpans(Spannable spannable, int i, int i2) {
        StyleSpan[] styleSpanArr = (StyleSpan[]) spannable.getSpans(i, i2, StyleSpan.class);
        return styleSpanArr == null ? (StyleSpan[]) Array.newInstance((Class<?>) StyleSpan.class, new int[0]) : styleSpanArr;
    }

    private UnderlineSpan[] getUnderlineSpans(Spannable spannable, int i, int i2) {
        UnderlineSpan[] underlineSpanArr = (UnderlineSpan[]) spannable.getSpans(i, i2, UnderlineSpan.class);
        return underlineSpanArr == null ? (UnderlineSpan[]) Array.newInstance((Class<?>) UnderlineSpan.class, new int[0]) : underlineSpanArr;
    }

    private BackgroundColorSpan[] getBgHighLightSpans(Spannable spannable, int i, int i2) {
        BackgroundColorSpan[] backgroundColorSpanArr = (BackgroundColorSpan[]) spannable.getSpans(i, i2, BackgroundColorSpan.class);
        return backgroundColorSpanArr == null ? (BackgroundColorSpan[]) Array.newInstance((Class<?>) BackgroundColorSpan.class, new int[0]) : backgroundColorSpanArr;
    }

    private KeywordsSpan[] getKeywordsSpans(Spannable spannable, int i, int i2) {
        KeywordsSpan[] keywordsSpanArr = (KeywordsSpan[]) spannable.getSpans(i, i2, KeywordsSpan.class);
        return keywordsSpanArr == null ? (KeywordsSpan[]) Array.newInstance((Class<?>) KeywordsSpan.class, new int[0]) : keywordsSpanArr;
    }

    protected CheckboxSpan[] getCheckboxSpans(Spannable spannable, int i, int i2) {
        CheckboxSpan[] checkboxSpanArr = (CheckboxSpan[]) spannable.getSpans(i, i2, CheckboxSpan.class);
        return checkboxSpanArr == null ? (CheckboxSpan[]) Array.newInstance((Class<?>) CheckboxSpan.class, new int[0]) : checkboxSpanArr;
    }

    protected EditorBulletSpan[] getEditorBulletSpans(Spannable spannable, int i, int i2) {
        EditorBulletSpan[] editorBulletSpanArr = (EditorBulletSpan[]) spannable.getSpans(i, i2, EditorBulletSpan.class);
        return editorBulletSpanArr == null ? (EditorBulletSpan[]) Array.newInstance((Class<?>) EditorBulletSpan.class, new int[0]) : editorBulletSpanArr;
    }

    private RelativeSizeSpan[] getRelativeSizeSpans(Spannable spannable, int i, int i2) {
        RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) spannable.getSpans(i, i2, RelativeSizeSpan.class);
        return relativeSizeSpanArr == null ? (RelativeSizeSpan[]) Array.newInstance((Class<?>) RelativeSizeSpan.class, new int[0]) : relativeSizeSpanArr;
    }

    private AbsoluteSizeSpan[] getAbsoluteSizeSpans(Spannable spannable, int i, int i2) {
        AbsoluteSizeSpan[] absoluteSizeSpanArr = (AbsoluteSizeSpan[]) spannable.getSpans(i, i2, AbsoluteSizeSpan.class);
        return absoluteSizeSpanArr == null ? (AbsoluteSizeSpan[]) Array.newInstance((Class<?>) AbsoluteSizeSpan.class, new int[0]) : absoluteSizeSpanArr;
    }

    private FontLargeSizeSpan[] getFontLargeSizeSpans(Spannable spannable, int i, int i2) {
        FontLargeSizeSpan[] fontLargeSizeSpanArr = (FontLargeSizeSpan[]) spannable.getSpans(i, i2, FontLargeSizeSpan.class);
        return fontLargeSizeSpanArr == null ? (FontLargeSizeSpan[]) Array.newInstance((Class<?>) FontLargeSizeSpan.class, new int[0]) : fontLargeSizeSpanArr;
    }

    private FontMidSizeSpan[] getFontMidSizeSpans(Spannable spannable, int i, int i2) {
        FontMidSizeSpan[] fontMidSizeSpanArr = (FontMidSizeSpan[]) spannable.getSpans(i, i2, FontMidSizeSpan.class);
        return fontMidSizeSpanArr == null ? (FontMidSizeSpan[]) Array.newInstance((Class<?>) FontMidSizeSpan.class, new int[0]) : fontMidSizeSpanArr;
    }

    protected void setSizeSpanInRange(int i, int i2) {
        int i3;
        if (getText().getSpans(i, i2, FontLargeSizeSpan.class).length > 0) {
            i3 = 2;
        } else {
            i3 = getText().getSpans(i, i2, FontMidSizeSpan.class).length > 0 ? 1 : 0;
        }
        setSizeSpanInRange(i3, i, i2);
    }

    protected void setSizeSpanInRange(int i, int i2, int i3) {
        for (Object obj : getText().getSpans(i2, i3, RelativeSizeSpan.class)) {
            SpanInfo spanInfo = getSpanInfo(obj);
            if (spanInfo.isValid()) {
                if (spanInfo.getStart() <= i2 && spanInfo.getEnd() >= i3) {
                    if (i3 - i2 == 1 && getText().charAt(i2) == '\n') {
                        forceUpdateLayout();
                        return;
                    }
                } else if (spanInfo.getStart() > i2 && spanInfo.getEnd() < i3) {
                    removeSpan(obj);
                }
            }
        }
        for (Object obj2 : getText().getSpans(i2, i3, AbsoluteSizeSpan.class)) {
            SpanInfo spanInfo2 = getSpanInfo(obj2);
            if ((!(obj2 instanceof AbsoluteSizeSpan) || ((AbsoluteSizeSpan) obj2).getSize() == getTextSize()) && spanInfo2.isValid()) {
                if (spanInfo2.getStart() <= i2 && spanInfo2.getEnd() >= i3) {
                    if (i3 - i2 == 1 && getText().charAt(i2) == '\n') {
                        forceUpdateLayout();
                        return;
                    }
                } else if (spanInfo2.getStart() > i2 && spanInfo2.getEnd() < i3) {
                    removeSpan(obj2);
                }
            }
        }
        if (i == 2 || i == 1) {
            getStyleFactory().setFontSizeSpan(getText(), i2, i3, i);
            return;
        }
        NormalImageSpan[] normalImageSpanArr = (NormalImageSpan[]) getText().getSpans(i2, i2, NormalImageSpan.class);
        AudioSpan[] audioSpanArr = (AudioSpan[]) getText().getSpans(i2, i2, AudioSpan.class);
        if (normalImageSpanArr.length >= 1 || audioSpanArr.length >= 1) {
            return;
        }
        getStyleFactory().setFontSizeSpan(getText(), i2, i3, i);
    }

    protected boolean removeStyleSizeInRangeOnSetFontSize(Editable editable, int i, int i2) {
        RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) editable.getSpans(i, i2, RelativeSizeSpan.class);
        TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) editable.getSpans(i, i2, TypefaceSpan.class);
        for (RelativeSizeSpan relativeSizeSpan : relativeSizeSpanArr) {
            int spanStart = editable.getSpanStart(relativeSizeSpan);
            int spanEnd = editable.getSpanEnd(relativeSizeSpan);
            if (spanStart <= i && spanEnd >= i2) {
                editable.removeSpan(relativeSizeSpan);
            } else if (spanStart <= i && spanEnd == i2 - 1 && spanEnd < editable.length() && editable.charAt(spanEnd) == '\n') {
                editable.removeSpan(relativeSizeSpan);
            } else if (spanStart >= i && spanEnd <= i2) {
                Logger.INSTANCE.d(TAG, "removeStyleSizeInRangeOnSetFontSize warining sizespan spanstart = " + editable.getSpanStart(relativeSizeSpan) + " spanend = " + editable.getSpanEnd(relativeSizeSpan) + " start = " + i + " end = " + i2);
                editable.removeSpan(relativeSizeSpan);
            }
        }
        for (TypefaceSpan typefaceSpan : typefaceSpanArr) {
            int spanStart2 = editable.getSpanStart(typefaceSpan);
            int spanEnd2 = editable.getSpanEnd(typefaceSpan);
            if (spanStart2 <= i && spanEnd2 >= i2) {
                editable.removeSpan(typefaceSpan);
            } else if (spanStart2 <= i && spanEnd2 == i2 - 1 && spanEnd2 < editable.length() && editable.charAt(spanEnd2) == '\n') {
                editable.removeSpan(typefaceSpan);
            } else if (spanStart2 >= i && spanEnd2 <= i2) {
                Logger.INSTANCE.d(TAG, "removeStyleSizeInRangeOnSetFontSize warining typespan spanstart = " + editable.getSpanStart(typefaceSpan) + " spanend = " + editable.getSpanEnd(typefaceSpan) + " start = " + i + " end = " + i2);
                editable.removeSpan(typefaceSpan);
            }
        }
        return (relativeSizeSpanArr.length == 0 && typefaceSpanArr.length == 0) ? false : true;
    }

    protected boolean removeStyleSizeInRange(Editable editable, int i, int i2) {
        AbsoluteSizeSpan[] absoluteSizeSpanArr = (AbsoluteSizeSpan[]) editable.getSpans(i, i2, AbsoluteSizeSpan.class);
        RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) editable.getSpans(i, i2, RelativeSizeSpan.class);
        TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) editable.getSpans(i, i2, TypefaceSpan.class);
        for (AbsoluteSizeSpan absoluteSizeSpan : absoluteSizeSpanArr) {
            if (editable.getSpanStart(absoluteSizeSpan) <= i && editable.getSpanEnd(absoluteSizeSpan) >= i2) {
                editable.removeSpan(absoluteSizeSpan);
            }
        }
        for (RelativeSizeSpan relativeSizeSpan : relativeSizeSpanArr) {
            if (editable.getSpanStart(relativeSizeSpan) <= i && editable.getSpanEnd(relativeSizeSpan) >= i2) {
                editable.removeSpan(relativeSizeSpan);
            }
        }
        for (TypefaceSpan typefaceSpan : typefaceSpanArr) {
            if (editable.getSpanStart(typefaceSpan) <= i && editable.getSpanEnd(typefaceSpan) >= i2) {
                editable.removeSpan(typefaceSpan);
            }
        }
        return (absoluteSizeSpanArr.length == 0 && relativeSizeSpanArr.length == 0 && typefaceSpanArr.length == 0) ? false : true;
    }

    public void setStates(int i, boolean z) {
        if (i == 1) {
            this.mBoldState = z;
            return;
        }
        if (i == 2) {
            this.mItalicState = z;
        } else if (i == 5) {
            this.mUnderLineState = z;
        } else {
            if (i != 11) {
                return;
            }
            this.mHighLightState = z;
        }
    }

    protected void setStyleSpan(int i, int i2, int i3) {
        if (i3 - i2 > 0) {
            boolean z = false;
            for (StyleSpan styleSpan : (StyleSpan[]) getEditableText().getSpans(i2, i3, StyleSpan.class)) {
                if (styleSpan.getStyle() == i) {
                    removeSpan(styleSpan, i2, i3);
                    z = true;
                }
            }
            if (!z || ((i == 1 && this.mBoldState) || (i == 2 && this.mItalicState))) {
                styleValid(new StyleSpan(i), i2, i3);
                this.mBoldState = false;
                this.mItalicState = false;
            }
        }
    }

    protected <T> void setRichStyleSpan(Class<T> cls, int i, int i2) {
        int i3;
        if (i2 - i > 0) {
            Class<T>[] clsArr = {AlignmentSpan.Standard.class, UnderlineSpan.class, BgHighLightSpan.class};
            int[] iArr = {4, 5, 11};
            for (int i4 = 0; i4 < 3; i4++) {
                if (cls == clsArr[i4]) {
                    Object[] spans = getEditableText().getSpans(i, i2, clsArr[i4]);
                    int length = spans.length;
                    int i5 = 0;
                    boolean z = false;
                    while (i5 < length) {
                        removeSpan(spans[i5], i, i2);
                        i5++;
                        z = true;
                    }
                    if (!z || (((i3 = iArr[i4]) == 11 && this.mHighLightState) || (i3 == 5 && this.mUnderLineState))) {
                        styleValid(iArr[i4], i, i2);
                        this.mUnderLineState = false;
                        this.mHighLightState = false;
                        return;
                    }
                    return;
                }
            }
        }
    }

    public int[] getLinePos() {
        String string = getText().toString();
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        Logger.INSTANCE.d(TAG, "toggleCheckableState: selectionStart=" + selectionStart);
        Logger.INSTANCE.d(TAG, "toggleCheckableState: selectionEnd=" + selectionEnd);
        if (selectionStart > selectionEnd) {
            Logger.INSTANCE.d(TAG, "toggleCheckableState: swap selectionStart and selectionEnd");
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        int lineStart = getLineStart(string, selectionStart);
        int lineEnd = getLineEnd(string, selectionEnd);
        Logger.INSTANCE.d(TAG, "toggleCheckableState: start=" + lineStart);
        Logger.INSTANCE.d(TAG, "toggleCheckableState: end=" + lineEnd);
        return new int[]{lineStart, lineEnd};
    }

    public boolean removeEditorBulletInRange(Editable editable, int i, int i2) {
        boolean zDeleteSpans = deleteSpans(editable, i, i2, EditorBulletSpan.class);
        if (i2 == editable.length()) {
            i2--;
        }
        while (i2 >= i) {
            int iLastIndexOf = editable.toString().lastIndexOf(EditorUtils.ZERO_WIDTH_STRING, i2);
            if (iLastIndexOf < 0) {
                break;
            }
            editable.delete(iLastIndexOf, EditorUtils.ZERO_WIDTH_STRING.length() + iLastIndexOf);
            i2 = iLastIndexOf - 1;
            zDeleteSpans = true;
        }
        return zDeleteSpans;
    }

    public boolean removeCheckboxInRange(Editable editable, int i, int i2) {
        boolean zDeleteSpans = deleteSpans(editable, i, i2, CheckboxSpan.class);
        if (i2 == editable.length()) {
            i2--;
        }
        while (i2 >= i) {
            int iLastIndexOf = editable.toString().lastIndexOf(EditorUtils.ZERO_WIDTH_STRING, i2);
            if (iLastIndexOf < 0) {
                break;
            }
            editable.delete(iLastIndexOf, EditorUtils.ZERO_WIDTH_STRING.length() + iLastIndexOf);
            i2 = iLastIndexOf - 1;
            zDeleteSpans = true;
        }
        return zDeleteSpans;
    }

    protected void updateCursorVisible() {
        isCursorVisible();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: setupDraggableLinkCard, reason: merged with bridge method [inline-methods] */
    public void lambda$onLinkCardLongClick$3(LinkCardSpan linkCardSpan, int[] iArr) {
        this.mDraggingImageSpan = linkCardSpan;
        iArr[0] = (int) ((iArr[0] - linkCardSpan.getBounds().left) * 0.69f);
        iArr[1] = (int) ((iArr[1] - linkCardSpan.getBounds().top) * 0.69f);
        AppCompatImageView appCompatImageView = (AppCompatImageView) View.inflate(getContext(), R.layout.note_edit_image_drag_thumbnail, null);
        if (appCompatImageView == null) {
            throw new IllegalArgumentException("Unable to inflate text drag thumbnail");
        }
        Drawable drawableForShadow = linkCardSpan.getDrawableForShadow();
        drawableForShadow.setAlpha(76);
        appCompatImageView.setImageDrawable(drawableForShadow);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        appCompatImageView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        appCompatImageView.layout(0, 0, (int) (appCompatImageView.getMeasuredWidth() * 0.69f), (int) (appCompatImageView.getMeasuredHeight() * 0.69f));
        appCompatImageView.invalidate();
        try {
            startDragAndDrop(new ClipData(new ClipDescription(IRichEditor.CLIP_LABEL_RICH_EDITOR, new String[]{"linkcard/*"}), new ClipData.Item("")), new DragShadowBuilder(appCompatImageView, iArr), linkCardSpan, 512);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "startDragAndDrop error " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: setupDraggableImage, reason: merged with bridge method [inline-methods] */
    public void lambda$onImageSpanLongClick$2(BaseImageSpan baseImageSpan, int[] iArr) {
        ClipDescription clipDescription;
        this.mDraggingImageSpan = baseImageSpan;
        iArr[0] = (int) ((iArr[0] - baseImageSpan.getBounds().left) * 0.69f);
        iArr[1] = (int) (((iArr[1] - baseImageSpan.getBounds().top) + getY()) * 0.69f);
        AppCompatImageView appCompatImageView = (AppCompatImageView) View.inflate(getContext(), R.layout.note_edit_image_drag_thumbnail, null);
        if (appCompatImageView == null) {
            throw new IllegalArgumentException("Unable to inflate text drag thumbnail");
        }
        Drawable drawableForShadow = baseImageSpan.getDrawableForShadow();
        if (drawableForShadow != null) {
            drawableForShadow.setAlpha(76);
        }
        appCompatImageView.setImageDrawable(drawableForShadow);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        appCompatImageView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        if (baseImageSpan instanceof NormalImageSpan) {
            appCompatImageView.layout(0, 0, (int) Math.ceil(appCompatImageView.getMeasuredWidth() * 0.69f * getImageRender().getImagePreviewMaxSizeScale()), (int) Math.ceil(appCompatImageView.getMeasuredHeight() * 0.69f * getImageRender().getImagePreviewMaxSizeScale()));
        } else {
            appCompatImageView.layout(0, 0, (int) (appCompatImageView.getMeasuredWidth() * 0.69f), (int) (appCompatImageView.getMeasuredHeight() * 0.69f));
        }
        appCompatImageView.invalidate();
        DragShadowBuilder dragShadowBuilder = new DragShadowBuilder(appCompatImageView, iArr);
        File file = new File(AttachmentUtils.getAttachmentPath(getContext(), baseImageSpan.getSrcFileId()));
        if (file.exists()) {
            Uri imageContentUri = ImageUtils.INSTANCE.getImageContentUri(getContext(), file);
            if (EditorUtils.isMp3Suffix(baseImageSpan.getSrcFileId())) {
                clipDescription = new ClipDescription(IRichEditor.CLIP_LABEL_RICH_EDITOR, new String[]{"audio/*"});
            } else {
                clipDescription = new ClipDescription(IRichEditor.CLIP_LABEL_RICH_EDITOR, new String[]{ShareAppsAdapter.TYPE_IMAGE});
            }
            try {
                startDragAndDrop(new ClipData(clipDescription, new ClipData.Item(imageContentUri)), dragShadowBuilder, baseImageSpan, 512);
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "startDragAndDrop error " + e.getMessage());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0074, code lost:
    
        if (r3.charAt(r8) != '\n') goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void changeStyleStatus(int r18, int r19) {
        /*
            Method dump skipped, instruction units count: 633
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.richeditor.RichEditTextView.changeStyleStatus(int, int):void");
    }

    private boolean changeStyleStatusOnPoint(int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, getStyleSpans(getText(), i, i));
        Collections.addAll(arrayList, getRelativeSizeSpans(getText(), i, i));
        Collections.addAll(arrayList, getUnderlineSpans(getText(), i, i));
        Collections.addAll(arrayList, getBgHighLightSpans(getText(), i, i));
        Collections.addAll(arrayList, getCheckboxSpans(getText(), i, i));
        Collections.addAll(arrayList, getEditorBulletSpans(getText(), i, i));
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        for (Object obj : arrayList) {
            SpanInfo spanInfo = getSpanInfo(obj);
            if (spanInfo.getStart() <= i && spanInfo.getEnd() >= i && spanInfo.getFlag() != 33) {
                if (obj instanceof StyleSpan) {
                    StyleSpan styleSpan = (StyleSpan) obj;
                    if (styleSpan.getStyle() == 1) {
                        i2++;
                        clearTextWatcher();
                        setSkipSelectionChanged(true);
                        getInputHelper().updateInputModeIndex(i);
                        getInputHelper().openRichStyleSpanByInfoOnParaStart(getText(), spanInfo);
                        setSkipSelectionChanged(false);
                        restoreTextWatcher();
                    } else if (styleSpan.getStyle() == 2) {
                        i3++;
                        clearTextWatcher();
                        setSkipSelectionChanged(true);
                        getInputHelper().updateInputModeIndex(i);
                        getInputHelper().openRichStyleSpanByInfoOnParaStart(getText(), spanInfo);
                        setSkipSelectionChanged(false);
                        restoreTextWatcher();
                    }
                } else if (obj instanceof FontLargeSizeSpan) {
                    i4++;
                    getInputHelper().openFontSizeSpanByInfo(getText(), spanInfo);
                } else if (obj instanceof FontMidSizeSpan) {
                    i5++;
                    getInputHelper().openFontSizeSpanByInfo(getText(), spanInfo);
                } else if (obj instanceof UnderlineSpan) {
                    if ((spanInfo.getFlag() & 256) == 0) {
                        i6++;
                        clearTextWatcher();
                        setSkipSelectionChanged(true);
                        getInputHelper().updateInputModeIndex(i);
                        getInputHelper().openRichStyleSpanByInfoOnParaStart(getText(), spanInfo);
                        setSkipSelectionChanged(false);
                    }
                    restoreTextWatcher();
                } else if (obj instanceof BgHighLightSpan) {
                    i7++;
                    clearTextWatcher();
                    setSkipSelectionChanged(true);
                    getInputHelper().updateInputModeIndex(i);
                    getInputHelper().openRichStyleSpanByInfoOnParaStart(getText(), spanInfo);
                    setSkipSelectionChanged(false);
                    restoreTextWatcher();
                } else if (!(obj instanceof KeywordsSpan)) {
                    if (obj instanceof CheckboxSpan) {
                        i8++;
                    } else if (obj instanceof EditorBulletSpan) {
                        i9++;
                    }
                }
            }
        }
        boolean z2 = i2 > 0;
        boolean z3 = i3 > 0;
        boolean z4 = i4 > 0;
        boolean z5 = i5 > 0;
        boolean z6 = i6 > 0;
        boolean z7 = i7 > 0;
        boolean z8 = i8 > 0;
        boolean z9 = i9 > 0;
        int i10 = z4 ? 2 : z5 ? 1 : 0;
        Logger.INSTANCE.d("SizeSpanTest", "onPoint sizeLevel " + i10 + " pointIndex " + i);
        TextStyleStateListener textStyleStateListener = this.mTextStyleStateListener;
        if (textStyleStateListener == null) {
            return false;
        }
        if (!z) {
            textStyleStateListener.onBold(z2);
            this.mTextStyleStateListener.onItalic(z3);
            this.mTextStyleStateListener.onFontSize(i10);
            this.mTextStyleStateListener.onUnderline(z6);
            this.mTextStyleStateListener.onBgHighLight(z7);
        }
        this.mTextStyleStateListener.onCheckbox(z8);
        this.mTextStyleStateListener.onBullet(z9);
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0095  */
    private void changeAlignStatus(int i, int i2) {
        boolean zIsParagraphCentered;
        boolean zIsParagraphRighted;
        if (getText() == null) {
            return;
        }
        String string = getText().toString();
        Logger.INSTANCE.d(TAG, "changeAlignStatus: selStart=" + i + " selEnd: " + i2 + " len:" + string.length());
        boolean z = false;
        if (string.isEmpty()) {
            Logger.INSTANCE.d(TAG, "changeAlignStatus:str empty");
            TextStyleStateListener textStyleStateListener = this.mTextStyleStateListener;
            if (textStyleStateListener != null) {
                textStyleStateListener.onLeft(true);
                this.mTextStyleStateListener.onCenter(false);
                this.mTextStyleStateListener.onRight(false);
                return;
            }
            return;
        }
        if (i == i2) {
            try {
                if (i == string.length()) {
                    int i3 = i - 1;
                    if (string.charAt(i3) == '\n') {
                        changeAlignStatus(i3, i2 - 1);
                        return;
                    } else {
                        changeAlignStatus(i3, i2);
                        return;
                    }
                }
                int iLastIndexOf = string.lastIndexOf(10, i - 1) + 1;
                int iIndexOf = string.indexOf(10, i2);
                if (iIndexOf == -1) {
                    iIndexOf = string.length();
                }
                if (iLastIndexOf == iIndexOf && string.charAt(iIndexOf) == '\n') {
                    zIsParagraphRighted = false;
                    zIsParagraphCentered = false;
                } else {
                    zIsParagraphCentered = isParagraphCentered(iLastIndexOf, iIndexOf);
                    try {
                        zIsParagraphRighted = isParagraphRighted(iLastIndexOf, iIndexOf);
                    } catch (Exception e) {
                        e = e;
                        Logger.INSTANCE.d(TAG, "changeAlignStatus with start=end, Exception: " + Log.getStackTraceString(new Throwable(e)));
                        zIsParagraphRighted = false;
                    }
                }
            } catch (Exception e2) {
                e = e2;
                zIsParagraphCentered = false;
            }
        } else {
            try {
                int iLastIndexOf2 = string.lastIndexOf(10, i - 1) + 1;
                int iIndexOf2 = string.indexOf(10, i2);
                Logger.INSTANCE.d(TAG, "parStart: " + iLastIndexOf2 + "parEnd: " + iIndexOf2);
                if (iIndexOf2 == -1) {
                    iIndexOf2 = string.length();
                }
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                int i7 = 0;
                int i8 = 0;
                while (iLastIndexOf2 < iIndexOf2) {
                    int iIndexOf3 = string.indexOf(10, iLastIndexOf2);
                    if (iIndexOf3 == -1) {
                        iIndexOf3 = string.length();
                    }
                    i4++;
                    if (isParagraphCentered(iLastIndexOf2, iIndexOf3)) {
                        i5++;
                    }
                    if (isParagraphRighted(iLastIndexOf2, iIndexOf3)) {
                        i8++;
                    }
                    if (hasCheckbox(iLastIndexOf2, iIndexOf3)) {
                        i6++;
                    }
                    if (hasEditorBullet(iLastIndexOf2, iIndexOf3)) {
                        i7++;
                    }
                    iLastIndexOf2 = iIndexOf3 + 1;
                }
                if (i4 > 0) {
                    int i9 = (i4 - i6) - i7;
                    boolean z2 = i5 >= i9 && i5 != 0;
                    boolean z3 = i8 >= i9 && i8 != 0;
                    zIsParagraphCentered = z2;
                    zIsParagraphRighted = z3;
                } else {
                    zIsParagraphRighted = false;
                    zIsParagraphCentered = false;
                }
            } catch (Exception e3) {
                Logger.INSTANCE.e(TAG, "changeAlignStatus Exception: " + e3);
                throw new RuntimeException(e3);
            }
        }
        TextStyleStateListener textStyleStateListener2 = this.mTextStyleStateListener;
        if (textStyleStateListener2 != null) {
            if (!zIsParagraphCentered && !zIsParagraphRighted) {
                z = true;
            }
            textStyleStateListener2.onLeft(z);
            this.mTextStyleStateListener.onCenter(zIsParagraphCentered);
            this.mTextStyleStateListener.onRight(zIsParagraphRighted);
            if (zIsParagraphCentered && zIsParagraphRighted) {
                Logger.INSTANCE.e(TAG, "paragraph is both centered and righted. impossible!");
            }
        }
    }

    public boolean isParagraphCentered(int i, int i2) {
        for (AlignmentSpan alignmentSpan : (AlignmentSpan[]) getText().getSpans(i, i2, AlignmentSpan.class)) {
            if (alignmentSpan.getAlignment() == Layout.Alignment.ALIGN_CENTER) {
                return true;
            }
        }
        return false;
    }

    public boolean isParagraphRighted(int i, int i2) {
        for (AlignmentSpan alignmentSpan : (AlignmentSpan[]) getText().getSpans(i, i2, AlignmentSpan.class)) {
            if (alignmentSpan.getAlignment() == Layout.Alignment.ALIGN_OPPOSITE) {
                return true;
            }
        }
        return false;
    }

    public void clearQueryInContent() {
        for (KeywordsSpan keywordsSpan : (KeywordsSpan[]) getEditableText().getSpans(0, getEditableText().length(), KeywordsSpan.class)) {
            getEditableText().removeSpan(keywordsSpan);
        }
    }

    public void updateQueryInContent() {
        clearQueryInContent();
        ArrayList<String> arrayList = this.mHighLightKeyWords;
        if (arrayList != null && !arrayList.isEmpty()) {
            for (int i = 0; i < this.mHighLightKeyWords.size(); i++) {
                changeHighLight(this.mHighLightKeyWords.get(i));
            }
        } else {
            changeHighLight(getQueryContent());
        }
        RichEditScrollView richEditScrollView = this.mScrollView;
        if (richEditScrollView != null) {
            richEditScrollView.requestLayout();
            this.mScrollView.invalidate();
        }
    }

    protected void changeHighLight(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String lowerCase = getEditableText().toString().toLowerCase();
        int length = lowerCase.length();
        int length2 = str.length();
        int iIndexOf = 0;
        while (iIndexOf >= 0 && iIndexOf < length) {
            iIndexOf = lowerCase.indexOf(str, iIndexOf);
            if (iIndexOf >= 0) {
                int i = iIndexOf + length2;
                getEditableText().setSpan(getStyleFactory().createKeywordsSpan(), iIndexOf, i, 33);
                iIndexOf = i;
            }
        }
    }

    protected class DragShadowBuilder extends View.DragShadowBuilder {
        private static final float BG_SCALE_SIZE = 1.6f;
        private static final int TRANSLATE_PX = 0;
        private int mDeltaX;
        private int mDeltaY;
        private float mShadowRadius;

        public DragShadowBuilder(View view, int[] iArr) {
            super(view);
            this.mDeltaX = iArr[0];
            this.mDeltaY = iArr[1];
            this.mShadowRadius = RichEditTextView.this.mEditorImageRender.getImageRadius() * 0.685f;
        }

        @Override // android.view.View.DragShadowBuilder
        public void onProvideShadowMetrics(Point point, Point point2) {
            super.onProvideShadowMetrics(point, point2);
            point.set(Math.max(1, (int) (point.x * 1.6f)), Math.max(1, (int) (point.y * 1.6f)));
            point2.set(this.mDeltaX, this.mDeltaY);
        }

        @Override // android.view.View.DragShadowBuilder
        public void onDrawShadow(Canvas canvas) {
            View view = getView();
            if (view != null) {
                canvas.save();
                Rect rect = new Rect();
                view.getDrawingRect(rect);
                PointF pointF = new PointF(view.getScaleX(), view.getScaleY());
                canvas.translate((pointF.x == 1.0f ? 0.0f : ((view.getWidth() / 2) - ((view.getWidth() * pointF.x) / 2.0f)) + 0.5f) + 0.0f, (pointF.y == 1.0f ? 0.0f : ((view.getHeight() / 2) - ((view.getHeight() * pointF.y) / 2.0f)) + 0.5f) + 0.0f);
                canvas.scale(pointF.x, pointF.y);
                RectF rectF = new RectF(rect);
                Path path = new Path();
                float f = this.mShadowRadius;
                path.addRoundRect(rectF, f, f, Path.Direction.CW);
                canvas.clipPath(path);
                view.draw(canvas);
                canvas.restore();
                return;
            }
            Logger.INSTANCE.e("View", "Asked to draw drag shadow but no view");
        }
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        RichEditTextViewAccessHelper richEditTextViewAccessHelper = this.mExploreByTouchHelper;
        if (richEditTextViewAccessHelper == null || !richEditTextViewAccessHelper.dispatchHoverEvent(motionEvent)) {
            return super.dispatchHoverEvent(motionEvent);
        }
        return true;
    }

    private void stopActionMode() {
        try {
            if (this.mMethodStopTextActionMode == null) {
                this.mMethodStopTextActionMode = TextView.class.getDeclaredMethod("stopTextActionMode", new Class[0]);
            }
            this.mMethodStopTextActionMode.setAccessible(true);
            this.mMethodStopTextActionMode.invoke(this, new Object[0]);
        } catch (Throwable th) {
            Logger.INSTANCE.e(TAG, "stopTextActionMode error: " + th);
            th.printStackTrace();
        }
    }

    @Override // android.widget.TextView, android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        try {
            return super.onPreDraw();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "onPreDraw error:" + e.getMessage());
            e.printStackTrace();
            return true;
        }
    }

    private static class RichEditTextViewAccessHelper extends ExploreByTouchHelper {
        private RichEditTextView mEditor;

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            return false;
        }

        public RichEditTextViewAccessHelper(RichEditTextView richEditTextView) {
            super(richEditTextView);
            this.mEditor = richEditTextView;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            return this.mEditor.mSpanDetector.getVirtualViewId(f, f2);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(List<Integer> list) {
            this.mEditor.mSpanDetector.getVisibleVirtualViews(list);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            try {
                RichEditSpanAccessibilityInfo accessibilityInfoByVid = this.mEditor.mSpanDetector.getAccessibilityInfoByVid(i);
                accessibilityNodeInfoCompat.addAction(16);
                accessibilityNodeInfoCompat.setContentDescription(accessibilityInfoByVid.contentDescription);
                accessibilityNodeInfoCompat.setBoundsInParent(accessibilityInfoByVid.bounds);
            } catch (Exception e) {
                Logger.INSTANCE.d(RichEditTextView.TAG, e + "");
            }
        }
    }
}
