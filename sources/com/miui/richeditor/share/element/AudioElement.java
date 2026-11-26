package com.miui.richeditor.share.element;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import com.miui.common.tool.AudioUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.xms.ai.recorder.AIRecorderManager;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class AudioElement extends Element {
    private static final String TAG = "AudioElement";
    private File mAudioFile;
    private Uri mUri;
    private String mAnnotation = "";
    private int mTime = 0;
    private boolean isShowSummaryIcon = false;

    @Override // com.miui.richeditor.share.element.Element
    public int getType() {
        return 6;
    }

    public static class Builder extends Element.Builder<AudioElement> {
        public Builder() {
            this(null);
        }

        protected Builder(AudioElement audioElement) {
            super(new AudioElement(), audioElement);
        }

        public Builder setUri(Uri uri) {
            ((AudioElement) this.iElement).mUri = uri;
            return this;
        }

        public Builder setAnnotation(String str) {
            ((AudioElement) this.iElement).mAnnotation = str;
            return this;
        }

        public Builder setTime(int i) {
            ((AudioElement) this.iElement).mTime = i;
            return this;
        }

        public Builder setAudioFile(File file) {
            ((AudioElement) this.iElement).mAudioFile = file;
            return this;
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginLeft(int i) {
            super.setMarginLeft(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginTop(int i) {
            super.setMarginTop(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginRight(int i) {
            super.setMarginRight(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginBottom(int i) {
            super.setMarginBottom(i);
            return this;
        }
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String getAnnotation() {
        return this.mAnnotation;
    }

    public int getTime() throws Throwable {
        int timeFromFile = AudioUtils.parseTimeFromFile(AudioUtils.NOTE_MP3_DIR_NAME, this.mAudioFile.getName());
        this.mTime = timeFromFile;
        return timeFromFile;
    }

    public String getAudioDescription() throws Throwable {
        ParcelFileDescriptor parcelFileDescriptorOpen;
        File file = new File(AudioUtils.obtainMp3FileDir(AudioUtils.NOTE_MP3_DIR_NAME), this.mAudioFile.getName());
        try {
            if (file.exists()) {
                try {
                    parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 805306368);
                    try {
                        String strGenerateFileSha1 = AIRecorderManager.instance.generateFileSha1(parcelFileDescriptorOpen.getFileDescriptor());
                        String strQuerySummary = AIRecorderManager.instance.querySummary(strGenerateFileSha1, NoteApp.getInstance());
                        String strQueryRecognizeData = AIRecorderManager.instance.queryRecognizeData(strGenerateFileSha1, NoteApp.getInstance());
                        Logger.INSTANCE.d(TAG, "getAudioDescription: audioSummaryDescription -> " + strQuerySummary);
                        Logger.INSTANCE.d(TAG, "getAudioDescription: audioRecognizeDataDescription -> " + strQueryRecognizeData);
                        if (!TextUtils.isEmpty(strQueryRecognizeData) || !TextUtils.isEmpty(strQuerySummary)) {
                            if (TextUtils.isEmpty(strQuerySummary)) {
                                if (parcelFileDescriptorOpen != null) {
                                    try {
                                        parcelFileDescriptorOpen.close();
                                        return strQueryRecognizeData;
                                    } catch (IOException e) {
                                        Logger.INSTANCE.e(TAG, "Failed to close audioFileDescriptor:" + e);
                                    }
                                }
                                return strQueryRecognizeData;
                            }
                            this.isShowSummaryIcon = true;
                            if (parcelFileDescriptorOpen != null) {
                                try {
                                    parcelFileDescriptorOpen.close();
                                    return strQuerySummary;
                                } catch (IOException e2) {
                                    Logger.INSTANCE.e(TAG, "Failed to close audioFileDescriptor:" + e2);
                                }
                            }
                            return strQuerySummary;
                        }
                        if (parcelFileDescriptorOpen != null) {
                            try {
                                parcelFileDescriptorOpen.close();
                            } catch (IOException e3) {
                                Logger.INSTANCE.e(TAG, "Failed to close audioFileDescriptor:" + e3);
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (parcelFileDescriptorOpen != null) {
                            try {
                                parcelFileDescriptorOpen.close();
                            } catch (IOException e4) {
                                Logger.INSTANCE.e(TAG, "Failed to close audioFileDescriptor:" + e4);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    parcelFileDescriptorOpen = null;
                }
                Logger.INSTANCE.i(TAG, "getAudioDescription: " + e.getMessage());
            } else {
                Logger.INSTANCE.d(TAG, "getAudioDescription: " + this.mAudioFile.getName() + " not exist!");
            }
        } catch (Exception e5) {
            Logger.INSTANCE.i(TAG, "getAudioDescription: " + e5.getMessage());
        }
        return null;
    }

    public boolean isShowSummaryIcon() {
        return this.isShowSummaryIcon;
    }

    public File getAudioFile() {
        return this.mAudioFile;
    }

    @Override // com.miui.richeditor.share.element.Element
    public Builder edit() {
        return new Builder(this);
    }

    @Override // com.miui.richeditor.share.element.Element
    protected void copyFrom(Element element) {
        super.copyFrom(element);
        AudioElement audioElement = (AudioElement) element;
        this.mAnnotation = audioElement.getAnnotation();
        this.mUri = audioElement.getUri();
        this.mTime = audioElement.getTime();
        this.mAudioFile = audioElement.getAudioFile();
        this.isShowSummaryIcon = audioElement.isShowSummaryIcon();
    }
}
