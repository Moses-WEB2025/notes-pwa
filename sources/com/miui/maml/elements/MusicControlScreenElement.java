package com.miui.maml.elements;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.Rating;
import android.os.Handler;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import java.util.ArrayList;
import net.lingala.zip4j.util.InternalZipConstants;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class MusicControlScreenElement extends ElementGroup implements ButtonScreenElement.ButtonActionListener {
    private static final String BUTTON_MUSIC_NEXT = "music_next";
    private static final String BUTTON_MUSIC_PAUSE = "music_pause";
    private static final String BUTTON_MUSIC_PLAY = "music_play";
    private static final String BUTTON_MUSIC_PREV = "music_prev";
    private static final String ELE_MUSIC_ALBUM_COVER = "music_album_cover";
    private static final String ELE_MUSIC_DISPLAY = "music_display";
    private static final int FRAMERATE_PLAYING = 30;
    private static final String LOG_TAG = "MusicControlScreenE";
    public static final String METADATA_KEY_LYRIC = "android.media.metadata.LYRIC";
    private static final String MIUI_PLAYER_PACKAGE_NAME = "com.miui.player";
    public static final int MUSIC_STATE_PLAY = 1;
    public static final int MUSIC_STATE_STOP = 0;
    public static final int PLAYSTATE_NONE = 0;
    public static final String TAG_NAME = "MusicControl";
    private AlbumInfo mAlbumInfo;
    private IndexedVariable mAlbumVar;
    private IndexedVariable mArtistVar;
    private IndexedVariable mArtworkVar;
    private boolean mAutoShow;
    private ButtonScreenElement mButtonNext;
    private ButtonScreenElement mButtonPause;
    private ButtonScreenElement mButtonPlay;
    private ButtonScreenElement mButtonPrev;
    private Bitmap mDefaultAlbumCoverBm;
    private Runnable mDelayToSetArtworkRunnable;
    private boolean mDisableNext;
    private IndexedVariable mDisableNextVar;
    private boolean mDisablePlay;
    private IndexedVariable mDisablePlayVar;
    private boolean mDisablePrev;
    private IndexedVariable mDisablePrevVar;
    private IndexedVariable mDurationVar;
    private boolean mEnableLyric;
    private boolean mEnableProgress;
    private ImageScreenElement mImageAlbumCover;
    private MusicLyricParser.Lyric mLyric;
    private IndexedVariable mLyricAfterVar;
    private IndexedVariable mLyricBeforeVar;
    private IndexedVariable mLyricCurrentLineProgressVar;
    private IndexedVariable mLyricCurrentVar;
    private IndexedVariable mLyricLastVar;
    private IndexedVariable mLyricNextVar;
    private IndexedVariable mLyricPrevVar;
    private MediaMetadata mMetadata;
    private Context mMiuiMusicContext;
    private MusicController mMusicController;
    private IndexedVariable mMusicStateVar;
    private MusicController.OnClientUpdateListener mMusicUpdateListener;
    private boolean mNeedUpdateLyric;
    private boolean mNeedUpdateProgress;
    private boolean mNeedUpdateUserRating;
    private IndexedVariable mPlayerClassVar;
    private IndexedVariable mPlayerPackageVar;
    private boolean mPlaying;
    private IndexedVariable mPositionVar;
    private Runnable mResetMusicControllerRunable;
    private String mSender;
    private SpectrumVisualizerScreenElement mSpectrumVisualizer;
    private TextScreenElement mTextDisplay;
    private IndexedVariable mTitleVar;
    private int mUpdateProgressInterval;
    private Runnable mUpdateProgressRunnable;
    private int mUserRatingStyle;
    private IndexedVariable mUserRatingStyleVar;
    private float mUserRatingValue;
    private IndexedVariable mUserRatingValueVar;

    @Override // com.miui.maml.elements.ButtonScreenElement.ButtonActionListener
    public boolean onButtonDoubleClick(String str) {
        return false;
    }

    @Override // com.miui.maml.elements.ButtonScreenElement.ButtonActionListener
    public boolean onButtonLongClick(String str) {
        return false;
    }

    public MusicControlScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mAlbumInfo = new AlbumInfo();
        this.mMusicUpdateListener = new MusicController.OnClientUpdateListener() { // from class: com.miui.maml.elements.MusicControlScreenElement.1
            private boolean mClientChanged;

            @Override // com.miui.maml.elements.MusicController.OnClientUpdateListener
            public void onClientPlaybackActionUpdate(long j) {
                Log.d(MusicControlScreenElement.LOG_TAG, "transportControlFlags: " + j);
                if ((128 & j) == 0) {
                    MusicControlScreenElement.this.resetUserRating();
                }
                MusicControlScreenElement.this.mDisablePlay = j != 0 && (519 & j) == 0;
                MusicControlScreenElement.this.mDisablePrev = j != 0 && (16 & j) == 0;
                MusicControlScreenElement.this.mDisableNext = j != 0 && (j & 32) == 0;
                if (MusicControlScreenElement.this.mHasName) {
                    MusicControlScreenElement.this.mDisablePlayVar.set(MusicControlScreenElement.this.mDisablePlay ? 1.0d : 0.0d);
                    MusicControlScreenElement.this.mDisablePrevVar.set(MusicControlScreenElement.this.mDisablePrev ? 1.0d : 0.0d);
                    MusicControlScreenElement.this.mDisableNextVar.set(MusicControlScreenElement.this.mDisableNext ? 1.0d : 0.0d);
                }
            }

            @Override // com.miui.maml.elements.MusicController.OnClientUpdateListener
            public void onClientPlaybackStateUpdate(int i) {
                onStateUpdate(i);
                Log.d(MusicControlScreenElement.LOG_TAG, "stateUpdate: " + i);
            }

            /* JADX WARN: Code duplicated, block: B:16:0x0029  */
            /* JADX WARN: Code duplicated, block: B:18:? A[RETURN, SYNTHETIC] */
            protected void onStateUpdate(int i) {
                boolean z;
                boolean z2 = false;
                if (i != 0) {
                    z = true;
                    if (i == 1 || i == 2) {
                        MusicControlScreenElement.this.performAction("state_stop");
                        z = false;
                        z2 = true;
                    } else if (i == 3) {
                        MusicControlScreenElement.this.performAction("state_play");
                        z2 = true;
                    }
                    if (z2) {
                        MusicControlScreenElement.this.onMusicStateChange(z);
                    }
                }
                MusicControlScreenElement.this.resetAll();
                z = false;
                if (z2) {
                    MusicControlScreenElement.this.onMusicStateChange(z);
                }
            }

            @Override // com.miui.maml.elements.MusicController.OnClientUpdateListener
            public void onClientMetadataUpdate(MediaMetadata mediaMetadata) {
                boolean z;
                MusicControlScreenElement.this.mMetadata = mediaMetadata;
                if (MusicControlScreenElement.this.mMetadata == null) {
                    return;
                }
                String string = MusicControlScreenElement.this.mMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
                String string2 = MusicControlScreenElement.this.mMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST);
                String string3 = MusicControlScreenElement.this.mMetadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM);
                Log.d(MusicControlScreenElement.LOG_TAG, "\ntitle: " + string + ", artist: " + string2 + ", album: " + string3);
                if (string == null && string2 == null && string3 == null) {
                    z = false;
                } else {
                    boolean zUpdate = MusicControlScreenElement.this.mAlbumInfo.update(string, string2, string3);
                    MusicControlScreenElement.this.updateAlbum(string, string2, string3);
                    z = zUpdate;
                }
                Bitmap bitmap = MusicControlScreenElement.this.mMetadata.getBitmap(MediaMetadataCompat.METADATA_KEY_ART);
                Log.d(MusicControlScreenElement.LOG_TAG, "artwork: " + (bitmap != null ? bitmap.toString() : "null"));
                if (bitmap != null || z) {
                    if (bitmap == null) {
                        MusicControlScreenElement.this.delayToSetDefaultArtwork(500L);
                    } else {
                        MusicControlScreenElement.this.updateArtwork(bitmap);
                    }
                }
                String string4 = MusicControlScreenElement.this.mMetadata.getString(MusicControlScreenElement.METADATA_KEY_LYRIC);
                Log.d(MusicControlScreenElement.LOG_TAG, "raw lyric: " + string4);
                MusicLyricParser.Lyric lyric = MusicLyricParser.parseLyric(string4);
                if (lyric != null) {
                    lyric.decorate();
                }
                if (lyric != null || z) {
                    MusicControlScreenElement.this.mLyric = lyric;
                    MusicControlScreenElement.this.updateLyric(lyric);
                }
                MusicControlScreenElement musicControlScreenElement = MusicControlScreenElement.this;
                musicControlScreenElement.requestFramerate(musicControlScreenElement.mLyric != null ? 30.0f : 0.0f);
                long j = MusicControlScreenElement.this.mMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
                long estimatedMediaPosition = MusicControlScreenElement.this.mMusicController.getEstimatedMediaPosition();
                Log.d(MusicControlScreenElement.LOG_TAG, "duration: " + j + ", position: " + estimatedMediaPosition);
                if ((j >= 0 && estimatedMediaPosition >= 0) || z) {
                    MusicControlScreenElement.this.updateProgress(j, estimatedMediaPosition);
                }
                Rating rating = MusicControlScreenElement.this.mMetadata.getRating(MediaMetadataCompat.METADATA_KEY_USER_RATING);
                Log.d(MusicControlScreenElement.LOG_TAG, "rating: " + rating);
                MusicControlScreenElement.this.updateUserRating(rating);
                if (this.mClientChanged) {
                    return;
                }
                onClientChange();
            }

            @Override // com.miui.maml.elements.MusicController.OnClientUpdateListener
            public void onClientChange() {
                this.mClientChanged = true;
                MusicControlScreenElement.this.resetAll();
                MusicControlScreenElement.this.readPackageName();
                Log.d(MusicControlScreenElement.LOG_TAG, "clientChange: " + (MusicControlScreenElement.this.mPlayerPackageVar != null ? MusicControlScreenElement.this.mPlayerPackageVar.getString() : "null") + InternalZipConstants.ZIP_FILE_SEPARATOR + (MusicControlScreenElement.this.mPlayerClassVar != null ? MusicControlScreenElement.this.mPlayerClassVar.getString() : "null"));
            }

            @Override // com.miui.maml.elements.MusicController.OnClientUpdateListener
            public void onSessionDestroyed() {
                if (MusicControlScreenElement.this.mAutoShow) {
                    MusicControlScreenElement.this.show(false);
                }
                MusicControlScreenElement.this.onMusicStateChange(false);
            }
        };
        this.mDelayToSetArtworkRunnable = new Runnable() { // from class: com.miui.maml.elements.MusicControlScreenElement.2
            @Override // java.lang.Runnable
            public void run() {
                MusicControlScreenElement musicControlScreenElement = MusicControlScreenElement.this;
                musicControlScreenElement.updateArtwork(musicControlScreenElement.mDefaultAlbumCoverBm);
            }
        };
        this.mResetMusicControllerRunable = new Runnable() { // from class: com.miui.maml.elements.MusicControlScreenElement.3
            @Override // java.lang.Runnable
            public void run() {
                if (MusicControlScreenElement.this.mMusicController != null) {
                    MusicControlScreenElement.this.mMusicController.reset();
                }
            }
        };
        this.mUpdateProgressRunnable = new Runnable() { // from class: com.miui.maml.elements.MusicControlScreenElement.4
            @Override // java.lang.Runnable
            public void run() {
                if (!MusicControlScreenElement.this.mPlaying || MusicControlScreenElement.this.mMetadata == null) {
                    return;
                }
                long j = MusicControlScreenElement.this.mMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
                long estimatedMediaPosition = MusicControlScreenElement.this.mMusicController.getEstimatedMediaPosition();
                if (j <= 0 || estimatedMediaPosition < 0) {
                    return;
                }
                MusicControlScreenElement.this.mDurationVar.set(j);
                MusicControlScreenElement.this.mPositionVar.set(estimatedMediaPosition);
                if (MusicControlScreenElement.this.mNeedUpdateLyric && MusicControlScreenElement.this.mLyric != null) {
                    MusicControlScreenElement.this.updateLyricVar(estimatedMediaPosition);
                }
                MusicControlScreenElement.this.requestUpdate();
                MusicControlScreenElement.this.getContext().getHandler().postDelayed(this, MusicControlScreenElement.this.mUpdateProgressInterval);
            }
        };
        this.mButtonPrev = (ButtonScreenElement) findElement(BUTTON_MUSIC_PREV);
        this.mButtonNext = (ButtonScreenElement) findElement(BUTTON_MUSIC_NEXT);
        this.mButtonPlay = (ButtonScreenElement) findElement(BUTTON_MUSIC_PLAY);
        this.mButtonPause = (ButtonScreenElement) findElement(BUTTON_MUSIC_PAUSE);
        this.mTextDisplay = (TextScreenElement) findElement(ELE_MUSIC_DISPLAY);
        this.mImageAlbumCover = (ImageScreenElement) findElement(ELE_MUSIC_ALBUM_COVER);
        this.mSpectrumVisualizer = findSpectrumVisualizer(this);
        setupButton(this.mButtonPrev);
        setupButton(this.mButtonNext);
        setupButton(this.mButtonPlay);
        setupButton(this.mButtonPause);
        ButtonScreenElement buttonScreenElement = this.mButtonPause;
        boolean z = false;
        if (buttonScreenElement != null) {
            buttonScreenElement.show(false);
        }
        if (this.mImageAlbumCover != null) {
            String attribute = element.getAttribute("defAlbumCover");
            if (!TextUtils.isEmpty(attribute)) {
                this.mDefaultAlbumCoverBm = getContext().mResourceManager.getBitmap(attribute);
            }
            Bitmap bitmap = this.mDefaultAlbumCoverBm;
            if (bitmap != null) {
                bitmap.setDensity(this.mRoot.getResourceDensity());
            }
        }
        this.mAutoShow = Boolean.parseBoolean(element.getAttribute("autoShow"));
        boolean z2 = Boolean.parseBoolean(element.getAttribute("enableLyric"));
        this.mEnableLyric = z2;
        this.mEnableProgress = z2 ? true : Boolean.parseBoolean(element.getAttribute("enableProgress"));
        this.mUpdateProgressInterval = getAttrAsInt(element, "updateProgressInterval", 1000);
        if (this.mHasName) {
            Variables variables = getVariables();
            this.mMusicStateVar = new IndexedVariable(this.mName + ".music_state", variables, true);
            this.mTitleVar = new IndexedVariable(this.mName + ".title", variables, false);
            this.mArtistVar = new IndexedVariable(this.mName + ".artist", variables, false);
            this.mAlbumVar = new IndexedVariable(this.mName + ".album", variables, false);
            if (this.mEnableLyric) {
                this.mLyricCurrentVar = new IndexedVariable(this.mName + ".lyric_current", variables, false);
                this.mLyricBeforeVar = new IndexedVariable(this.mName + ".lyric_before", variables, false);
                this.mLyricAfterVar = new IndexedVariable(this.mName + ".lyric_after", variables, false);
                this.mLyricLastVar = new IndexedVariable(this.mName + ".lyric_last", variables, false);
                this.mLyricPrevVar = new IndexedVariable(this.mName + ".lyric_prev", variables, false);
                this.mLyricNextVar = new IndexedVariable(this.mName + ".lyric_next", variables, false);
                this.mLyricCurrentLineProgressVar = new IndexedVariable(this.mName + ".lyric_current_line_progress", variables, true);
            }
            if (this.mEnableProgress) {
                this.mDurationVar = new IndexedVariable(this.mName + ".music_duration", variables, true);
                this.mPositionVar = new IndexedVariable(this.mName + ".music_position", variables, true);
            }
            this.mUserRatingStyleVar = new IndexedVariable(this.mName + ".user_rating_style", variables, true);
            this.mUserRatingValueVar = new IndexedVariable(this.mName + ".user_rating_value", variables, true);
            this.mDisablePlayVar = new IndexedVariable(this.mName + ".disable_play", variables, true);
            this.mDisablePrevVar = new IndexedVariable(this.mName + ".disable_prev", variables, true);
            this.mDisableNextVar = new IndexedVariable(this.mName + ".disable_next", variables, true);
            this.mArtworkVar = new IndexedVariable(this.mName + ".artwork", variables, false);
            this.mPlayerPackageVar = new IndexedVariable(this.mName + ".package", variables, false);
            this.mPlayerClassVar = new IndexedVariable(this.mName + ".class", variables, false);
        }
        this.mNeedUpdateLyric = this.mEnableLyric && this.mHasName;
        if (this.mEnableProgress && this.mHasName) {
            z = true;
        }
        this.mNeedUpdateProgress = z;
        this.mNeedUpdateUserRating = this.mHasName;
        try {
            this.mMiuiMusicContext = getContext().mContext.createPackageContext(MIUI_PLAYER_PACKAGE_NAME, 2);
        } catch (Exception e) {
            Log.w(LOG_TAG, "fail to get MiuiMusic preference", e);
        }
        this.mMusicController = new MusicController(getContext().mContext, getContext().getHandler());
        String rootTag = this.mRoot.getRootTag();
        this.mSender = "maml";
        if ("gadget".equalsIgnoreCase(rootTag)) {
            this.mSender = "home_widget";
        } else if ("statusbar".equalsIgnoreCase(rootTag)) {
            this.mSender = "notification_bar";
        } else if ("lockscreen".equalsIgnoreCase(rootTag)) {
            this.mSender = "lockscreen";
        }
    }

    private static class AlbumInfo {
        String album;
        String artist;
        String title;

        private AlbumInfo() {
        }

        boolean update(String str, String str2, String str3) {
            if (str != null) {
                str = str.trim();
            }
            if (str2 != null) {
                str2 = str2.trim();
            }
            if (str3 != null) {
                str3 = str3.trim();
            }
            boolean z = (TextUtils.equals(str, this.title) && TextUtils.equals(str2, this.artist) && TextUtils.equals(str3, this.album)) ? false : true;
            if (z) {
                this.title = str;
                this.artist = str2;
                this.album = str3;
            }
            return z;
        }
    }

    private SpectrumVisualizerScreenElement findSpectrumVisualizer(ElementGroup elementGroup) {
        SpectrumVisualizerScreenElement spectrumVisualizerScreenElementFindSpectrumVisualizer;
        for (ScreenElement screenElement : elementGroup.getElements()) {
            if (screenElement instanceof SpectrumVisualizerScreenElement) {
                return (SpectrumVisualizerScreenElement) screenElement;
            }
            if ((screenElement instanceof ElementGroup) && (spectrumVisualizerScreenElementFindSpectrumVisualizer = findSpectrumVisualizer((ElementGroup) screenElement)) != null) {
                return spectrumVisualizerScreenElementFindSpectrumVisualizer;
            }
        }
        return null;
    }

    private void setupButton(ButtonScreenElement buttonScreenElement) {
        if (buttonScreenElement != null) {
            buttonScreenElement.setListener(this);
            buttonScreenElement.setParent(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMusicStateChange(boolean z) {
        if (z && this.mAutoShow && !isVisible()) {
            show(true);
        }
        this.mPlaying = z;
        IndexedVariable indexedVariable = this.mMusicStateVar;
        if (indexedVariable != null) {
            indexedVariable.set(z ? 1.0d : 0.0d);
        }
        ButtonScreenElement buttonScreenElement = this.mButtonPause;
        if (buttonScreenElement != null) {
            buttonScreenElement.show(z);
        }
        ButtonScreenElement buttonScreenElement2 = this.mButtonPlay;
        if (buttonScreenElement2 != null) {
            buttonScreenElement2.show(!z);
        }
        if (this.mNeedUpdateProgress) {
            startProgressUpdate(z, z ? 100L : 0L);
        }
        SpectrumVisualizerScreenElement spectrumVisualizerScreenElement = this.mSpectrumVisualizer;
        if (spectrumVisualizerScreenElement != null) {
            spectrumVisualizerScreenElement.enableUpdate(z && this.mResumed);
        }
        requestFramerate((!z || this.mLyric == null) ? 0.0f : 30.0f);
        requestUpdate();
        Log.d(LOG_TAG, "music state change: playing=" + z);
    }

    private void startProgressUpdate(boolean z, long j) {
        getContext().getHandler().removeCallbacks(this.mUpdateProgressRunnable);
        if (z) {
            if (j > 0) {
                getContext().getHandler().postDelayed(this.mUpdateProgressRunnable, j);
            } else {
                getContext().getHandler().post(this.mUpdateProgressRunnable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAlbum(String str, String str2, String str3) {
        if (this.mHasName) {
            this.mTitleVar.set(str);
            this.mArtistVar.set(str2);
            this.mAlbumVar.set(str3);
        }
        if (this.mTextDisplay != null) {
            if (TextUtils.isEmpty(str)) {
                str = str2;
            } else if (!TextUtils.isEmpty(str2)) {
                str = String.format("%s   %s", str, str2);
            }
            this.mTextDisplay.setText(str);
        }
        requestUpdate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delayToSetDefaultArtwork(long j) {
        Handler handler = getContext().getHandler();
        handler.removeCallbacks(this.mDelayToSetArtworkRunnable);
        handler.postDelayed(this.mDelayToSetArtworkRunnable, j);
    }

    private void cancelSetDefaultArtwork() {
        getContext().getHandler().removeCallbacks(this.mDelayToSetArtworkRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateArtwork(Bitmap bitmap) {
        cancelSetDefaultArtwork();
        if (this.mHasName) {
            this.mArtworkVar.set(bitmap);
        }
        ImageScreenElement imageScreenElement = this.mImageAlbumCover;
        if (imageScreenElement != null) {
            imageScreenElement.setBitmap(bitmap);
        }
        requestUpdate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLyric(MusicLyricParser.Lyric lyric) {
        if (this.mNeedUpdateLyric) {
            if (lyric == null) {
                resetLyric();
                return;
            }
            int[] timeArr = lyric.getTimeArr();
            ArrayList<CharSequence> stringArr = lyric.getStringArr();
            MusicLyricParser.Lyric lyric2 = this.mLyric;
            if (lyric2 != null) {
                lyric2.set(timeArr, stringArr);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress(long j, long j2) {
        if (this.mNeedUpdateProgress) {
            if (j <= 0 || j2 < 0) {
                resetProgress();
                return;
            }
            this.mDurationVar.set(j);
            this.mPositionVar.set(j2);
            if (this.mNeedUpdateLyric) {
                if (this.mLyric != null) {
                    updateLyricVar(j2);
                } else {
                    resetLyric();
                }
            }
            requestUpdate();
            startProgressUpdate(this.mPlaying, 0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserRating(Rating rating) {
        if (this.mNeedUpdateUserRating) {
            if (rating == null) {
                resetUserRating();
                return;
            }
            int ratingStyle = rating.getRatingStyle();
            this.mUserRatingStyle = ratingStyle;
            switch (ratingStyle) {
                case 1:
                    this.mUserRatingValue = rating.hasHeart() ? 1.0f : 0.0f;
                    break;
                case 2:
                    this.mUserRatingValue = rating.isThumbUp() ? 1.0f : 0.0f;
                    break;
                case 3:
                case 4:
                case 5:
                    this.mUserRatingValue = rating.getStarRating();
                    break;
                case 6:
                    this.mUserRatingValue = rating.getPercentRating();
                    break;
                default:
                    this.mUserRatingValue = 0.0f;
                    break;
            }
            this.mUserRatingStyleVar.set(this.mUserRatingStyle);
            this.mUserRatingValueVar.set(this.mUserRatingValue);
            requestUpdate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLyricVar(long j) {
        this.mLyricCurrentLineProgressVar.set(this.mLyric.getLyricShot(j).percent);
        this.mLyricCurrentVar.set(this.mLyric.getLine(j));
        this.mLyricBeforeVar.set(this.mLyric.getBeforeLines(j));
        this.mLyricAfterVar.set(this.mLyric.getAfterLines(j));
        String lastLine = this.mLyric.getLastLine(j);
        this.mLyricLastVar.set(lastLine);
        this.mLyricPrevVar.set(lastLine);
        this.mLyricNextVar.set(this.mLyric.getNextLine(j));
    }

    private void resetProgress() {
        if (this.mNeedUpdateProgress) {
            this.mDurationVar.set(0.0d);
            this.mPositionVar.set(0.0d);
        }
        if (this.mNeedUpdateLyric) {
            this.mLyricCurrentLineProgressVar.set(0.0d);
        }
    }

    private void resetLyric() {
        if (this.mNeedUpdateLyric) {
            this.mLyricBeforeVar.set((Object) null);
            this.mLyricAfterVar.set((Object) null);
            this.mLyricLastVar.set((Object) null);
            this.mLyricPrevVar.set((Object) null);
            this.mLyricNextVar.set((Object) null);
            this.mLyricCurrentVar.set((Object) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetUserRating() {
        if (this.mNeedUpdateUserRating) {
            this.mUserRatingStyle = 0;
            this.mUserRatingValue = 0.0f;
            this.mUserRatingStyleVar.set(0.0d);
            this.mUserRatingValueVar.set(0.0d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetAll() {
        updateAlbum(null, null, null);
        resetProgress();
        resetLyric();
        resetUserRating();
        updateArtwork(this.mDefaultAlbumCoverBm);
        resetPackageName();
        resetMusicState();
    }

    private void resetMusicState() {
        onMusicStateChange(false);
    }

    private void resetPackageName() {
        IndexedVariable indexedVariable = this.mPlayerPackageVar;
        if (indexedVariable != null) {
            indexedVariable.set((Object) null);
        }
        IndexedVariable indexedVariable2 = this.mPlayerClassVar;
        if (indexedVariable2 != null) {
            indexedVariable2.set((Object) null);
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        initByPreference();
        this.mMusicController.registerListener(this.mMusicUpdateListener);
        if (this.mMusicController.isMusicActive()) {
            if (this.mAutoShow) {
                show(true);
            }
            onMusicStateChange(true);
            return;
        }
        onMusicStateChange(false);
    }

    public void initByPreference() {
        SharedPreferences sharedPreferences;
        Context context = this.mMiuiMusicContext;
        if (context != null) {
            try {
                sharedPreferences = context.getSharedPreferences("MiuiMusic", 4);
            } catch (IllegalStateException unused) {
                sharedPreferences = null;
            }
            if (sharedPreferences != null) {
                updateAlbum(sharedPreferences.getString("songName", null), sharedPreferences.getString("artistName", null), sharedPreferences.getString("albumName", null));
                updateArtwork(this.mDefaultAlbumCoverBm);
            }
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void onVisibilityChange(boolean z) {
        super.onVisibilityChange(z);
        boolean z2 = false;
        if (z) {
            requestUpdate();
            SpectrumVisualizerScreenElement spectrumVisualizerScreenElement = this.mSpectrumVisualizer;
            if (spectrumVisualizerScreenElement != null) {
                if (this.mPlaying && this.mResumed) {
                    z2 = true;
                }
                spectrumVisualizerScreenElement.enableUpdate(z2);
                return;
            }
            return;
        }
        requestFramerate(0.0f);
        SpectrumVisualizerScreenElement spectrumVisualizerScreenElement2 = this.mSpectrumVisualizer;
        if (spectrumVisualizerScreenElement2 != null) {
            spectrumVisualizerScreenElement2.enableUpdate(false);
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void pause() {
        super.pause();
        SpectrumVisualizerScreenElement spectrumVisualizerScreenElement = this.mSpectrumVisualizer;
        if (spectrumVisualizerScreenElement != null) {
            spectrumVisualizerScreenElement.enableUpdate(false);
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.ScreenElement
    public void resume() {
        super.resume();
        requestUpdate();
        SpectrumVisualizerScreenElement spectrumVisualizerScreenElement = this.mSpectrumVisualizer;
        if (spectrumVisualizerScreenElement != null) {
            spectrumVisualizerScreenElement.enableUpdate(this.mPlaying);
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        IndexedVariable indexedVariable = this.mArtworkVar;
        if (indexedVariable != null) {
            indexedVariable.set((Object) null);
        }
        this.mMusicController.unregisterListener();
        this.mMusicController.finish();
        Handler handler = getContext().getHandler();
        handler.removeCallbacks(this.mUpdateProgressRunnable);
        handler.removeCallbacks(this.mDelayToSetArtworkRunnable);
        handler.removeCallbacks(this.mResetMusicControllerRunable);
    }

    @Override // com.miui.maml.elements.ButtonScreenElement.ButtonActionListener
    public boolean onButtonDown(String str) {
        return sendMediaKeyEvent(0, str);
    }

    @Override // com.miui.maml.elements.ButtonScreenElement.ButtonActionListener
    public boolean onButtonUp(String str) {
        if (!sendMediaKeyEvent(1, str)) {
            return false;
        }
        if (BUTTON_MUSIC_PREV.equals(str) || BUTTON_MUSIC_NEXT.equals(str)) {
            cancelSetDefaultArtwork();
            getContext().getHandler().removeCallbacks(this.mUpdateProgressRunnable);
        }
        return true;
    }

    private boolean sendMediaKeyEvent(int i, String str) {
        int i2;
        Intent intent;
        if (BUTTON_MUSIC_PREV.equals(str)) {
            i2 = 88;
        } else if (BUTTON_MUSIC_NEXT.equals(str)) {
            i2 = 87;
        } else {
            i2 = (BUTTON_MUSIC_PLAY.equals(str) || BUTTON_MUSIC_PAUSE.equals(str)) ? 85 : 0;
        }
        if (i2 == 88 && this.mDisablePrev) {
            return false;
        }
        if (i2 == 87 && this.mDisableNext) {
            return false;
        }
        if (i2 == 85 && this.mDisablePlay) {
            return false;
        }
        if (this.mMusicController.sendMediaKeyEvent(i, i2)) {
            return true;
        }
        Log.d(LOG_TAG, "fail to dispatch by media controller, send to MiuiMusic.");
        if (i == 0) {
            return true;
        }
        if (BUTTON_MUSIC_PLAY.equals(str) || BUTTON_MUSIC_PAUSE.equals(str)) {
            intent = new Intent("com.miui.player.musicservicecommand.togglepause");
        } else if (BUTTON_MUSIC_PREV.equals(str)) {
            intent = new Intent("com.miui.player.musicservicecommand.previous");
        } else {
            intent = BUTTON_MUSIC_NEXT.equals(str) ? new Intent("com.miui.player.musicservicecommand.next") : null;
        }
        if (intent == null) {
            return false;
        }
        intent.setPackage(MIUI_PLAYER_PACKAGE_NAME);
        intent.putExtra("intent_sender", this.mSender);
        this.mRoot.getContext().mContext.startService(intent);
        getContext().getHandler().postDelayed(this.mResetMusicControllerRunable, 1000L);
        return true;
    }

    public void seekTo(double d) {
        this.mMusicController.seekTo((long) (d * this.mDurationVar.getDouble()));
    }

    public void ratingHeart() {
        if (this.mUserRatingStyle == 1) {
            Rating ratingNewHeartRating = Rating.newHeartRating(!(this.mUserRatingValue == 1.0f));
            this.mMusicController.rating(ratingNewHeartRating);
            updateUserRating(ratingNewHeartRating);
        }
    }

    protected void readPackageName() {
        if (this.mPlayerPackageVar == null || this.mPlayerClassVar == null) {
            return;
        }
        String clientPackageName = this.mMusicController.getClientPackageName();
        Log.d(LOG_TAG, "readPackage: " + clientPackageName);
        if (clientPackageName != null) {
            Intent launchIntentForPackage = this.mRoot.getContext().mContext.getPackageManager().getLaunchIntentForPackage(clientPackageName);
            if (launchIntentForPackage != null) {
                ComponentName component = launchIntentForPackage.getComponent();
                this.mPlayerPackageVar.set(component.getPackageName());
                this.mPlayerClassVar.set(component.getClassName());
            } else {
                this.mPlayerPackageVar.set(clientPackageName);
                this.mPlayerClassVar.set((Object) null);
                Log.w(LOG_TAG, "set player info fail.");
            }
        }
    }
}
