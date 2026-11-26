package com.miui.maml.elements.video;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.elements.ViewHolderScreenElement;
import com.miui.maml.util.MamlMediaDataSource;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class VideoElement extends ViewHolderScreenElement {
    public static final String LOG_TAG = "VideoElement";
    public static final String TAG_NAME = "Video";
    private MamlMediaDataSource mSource;
    private BaseVideoView mView;

    public VideoElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        load();
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement, com.miui.maml.elements.ElementGroupRC, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        BaseVideoView baseVideoView = this.mView;
        if (baseVideoView != null) {
            baseVideoView.init(getVariables());
        }
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        MamlMediaDataSource mamlMediaDataSource = this.mSource;
        if (mamlMediaDataSource != null) {
            mamlMediaDataSource.close();
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void pause() {
        Log.d(LOG_TAG, "pause");
        BaseVideoView baseVideoView = this.mView;
        if (baseVideoView != null) {
            baseVideoView.pause();
        }
    }

    public void play() {
        Log.d(LOG_TAG, "play");
        MamlMediaDataSource mamlMediaDataSource = this.mSource;
        if (mamlMediaDataSource != null) {
            mamlMediaDataSource.tryToGenerateMemoryFile();
        }
        BaseVideoView baseVideoView = this.mView;
        if (baseVideoView != null) {
            baseVideoView.start();
        }
    }

    public void stop() {
        Log.d(LOG_TAG, "stop");
        BaseVideoView baseVideoView = this.mView;
        if (baseVideoView != null) {
            baseVideoView.stopPlayback();
        }
        MamlMediaDataSource mamlMediaDataSource = this.mSource;
        if (mamlMediaDataSource != null) {
            mamlMediaDataSource.releaseMemoryFile();
        }
    }

    public void seekTo(int i) {
        Log.d(LOG_TAG, "seekTo " + i);
        BaseVideoView baseVideoView = this.mView;
        if (baseVideoView != null) {
            baseVideoView.seekTo(i);
        }
    }

    public void setVolume(float f) {
        Log.d(LOG_TAG, "setVolume " + f);
        BaseVideoView baseVideoView = this.mView;
        if (baseVideoView != null) {
            baseVideoView.setVolume(f);
        }
    }

    public void config(boolean z, int i, String str) {
        Log.d(LOG_TAG, "config: path " + str + " looping " + z + " scaleMode " + i);
        if (this.mView == null) {
            return;
        }
        MamlMediaDataSource mamlMediaDataSource = this.mSource;
        if (mamlMediaDataSource == null || !TextUtils.equals(mamlMediaDataSource.getPath(), str)) {
            this.mSource = new MamlMediaDataSource(getContext().mContext, getContext().mResourceManager, str);
        }
        this.mView.setLooping(z);
        this.mView.setScaleMode(i);
        this.mView.setVideoDataSource(this.mSource);
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement
    protected void onViewAdded(View view) {
        super.onViewAdded(view);
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement
    protected void onViewRemoved(View view) {
        super.onViewRemoved(view);
        stop();
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement
    protected void updateView() {
        if (this.mView != null) {
            if (this.mUpdatePosition || this.mUpdateTranslation || this.mUpdateSize) {
                onUpdateView(this.mView);
            }
        }
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void doTick(long j) {
        super.doTick(j);
        BaseVideoView baseVideoView = this.mView;
        if (baseVideoView != null) {
            baseVideoView.doTick();
        }
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement
    protected View getView() {
        return this.mView;
    }

    private void load() {
        if (getRoot().getMamlSurface() != null) {
            this.mView = new SurfaceVideoView(this.mRoot.getContext().mContext, getRoot().getMamlSurface());
            if (this.mLayer == 1) {
                this.mView.setZOrderOnTop(true);
            }
        } else {
            this.mView = new NormalVideoView(this.mRoot.getContext().mContext);
        }
        this.mView.setName(this.mName);
        this.mView.setFormat(-2);
        this.mView.setBackgroundColor(0);
    }
}
