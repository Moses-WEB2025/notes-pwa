package miuix.internal.hybrid.webkit;

import android.content.Intent;

/* JADX INFO: loaded from: classes5.dex */
public class FileChooserParams extends miuix.hybrid.FileChooserParams {
    public static final int MODE_OPEN = 0;
    public static final int MODE_OPEN_FOLDER = 2;
    public static final int MODE_OPEN_MULTIPLE = 1;
    public static final int MODE_SAVE = 3;
    private final android.webkit.WebChromeClient.FileChooserParams mFileChooserParams;

    public FileChooserParams(android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
        this.mFileChooserParams = fileChooserParams;
    }

    @Override // miuix.hybrid.FileChooserParams
    public int getMode() {
        return this.mFileChooserParams.getMode();
    }

    @Override // miuix.hybrid.FileChooserParams
    public String[] getAcceptTypes() {
        return this.mFileChooserParams.getAcceptTypes();
    }

    @Override // miuix.hybrid.FileChooserParams
    public boolean isCaptureEnabled() {
        return this.mFileChooserParams.isCaptureEnabled();
    }

    @Override // miuix.hybrid.FileChooserParams
    public CharSequence getTitle() {
        return this.mFileChooserParams.getTitle();
    }

    @Override // miuix.hybrid.FileChooserParams
    public String getFilenameHint() {
        return this.mFileChooserParams.getFilenameHint();
    }

    @Override // miuix.hybrid.FileChooserParams
    public Intent createIntent() {
        return this.mFileChooserParams.createIntent();
    }
}
