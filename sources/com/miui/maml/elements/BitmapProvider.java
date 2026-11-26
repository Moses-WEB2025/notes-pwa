package com.miui.maml.elements;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.ObjectFactory;
import com.miui.maml.ResourceManager;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.util.Utils;
import com.miui.maml.util.net.IOUtils;
import java.io.File;
import java.io.InputStream;
import java.net.URI;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BitmapProvider {
    private static final String LOG_TAG = "BitmapProvider";
    protected ScreenElementRoot mRoot;
    protected VersionedBitmap mVersionedBitmap = new VersionedBitmap(null);

    public interface IBitmapHolder {
        VersionedBitmap getBitmap(String str);
    }

    public void reset() {
    }

    public static class VersionedBitmap {
        private Bitmap mBitmap;
        private int mVersion;

        public VersionedBitmap(Bitmap bitmap) {
            this.mBitmap = bitmap;
        }

        public void set(VersionedBitmap versionedBitmap) {
            if (versionedBitmap != null) {
                this.mBitmap = versionedBitmap.mBitmap;
                this.mVersion = versionedBitmap.mVersion;
            } else {
                reset();
            }
        }

        public boolean setBitmap(Bitmap bitmap) {
            if (bitmap != this.mBitmap) {
                this.mBitmap = bitmap;
                this.mVersion++;
            }
            return bitmap != this.mBitmap;
        }

        public int updateVersion() {
            int i = this.mVersion;
            this.mVersion = i + 1;
            return i;
        }

        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        public void reset() {
            this.mBitmap = null;
            this.mVersion = 0;
        }

        public static boolean equals(VersionedBitmap versionedBitmap, VersionedBitmap versionedBitmap2) {
            return versionedBitmap != null && versionedBitmap2 != null && versionedBitmap.mBitmap == versionedBitmap2.mBitmap && versionedBitmap.mVersion == versionedBitmap2.mVersion;
        }
    }

    public static BitmapProvider create(ScreenElementRoot screenElementRoot, String str) {
        BitmapProvider bitmapProviderCreate;
        str.hashCode();
        switch (str) {
            case "BitmapVar":
                return new BitmapVariableProvider(screenElementRoot);
            case "BitmapHolder":
                return new BitmapHolderProvider(screenElementRoot);
            case "FileSystem":
                return new FileSystemProvider(screenElementRoot);
            case "Uri":
                return new UriProvider(screenElementRoot);
            case "VirtualScreen":
                return new VirtualScreenProvider(screenElementRoot);
            case "ResourceImage":
                return new ResourceImageProvider(screenElementRoot);
            case "ApplicationIcon":
                return new AppIconProvider(screenElementRoot);
            default:
                ObjectFactory.BitmapProviderFactory bitmapProviderFactory = (ObjectFactory.BitmapProviderFactory) screenElementRoot.getContext().getObjectFactory("BitmapProvider");
                return (bitmapProviderFactory == null || (bitmapProviderCreate = bitmapProviderFactory.create(screenElementRoot, str)) == null) ? new ResourceImageProvider(screenElementRoot) : bitmapProviderCreate;
        }
    }

    public BitmapProvider(ScreenElementRoot screenElementRoot) {
        this.mRoot = screenElementRoot;
    }

    public void init(String str) {
        reset();
    }

    public void finish() {
        this.mVersionedBitmap.reset();
    }

    public VersionedBitmap getBitmap(String str, boolean z, int i, int i2) {
        return this.mVersionedBitmap;
    }

    private static class AppIconProvider extends BitmapProvider {
        public static final String TAG_NAME = "ApplicationIcon";
        private String mCls;
        private boolean mNoIcon;
        private String mPkg;
        private String mSrc;

        public AppIconProvider(ScreenElementRoot screenElementRoot) {
            super(screenElementRoot);
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public void init(String str) {
            super.init(str);
            this.mSrc = str;
            parseSrc(str);
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public VersionedBitmap getBitmap(String str, boolean z, int i, int i2) {
            if (!TextUtils.equals(str, this.mSrc)) {
                this.mSrc = str;
                parseSrc(str);
            }
            if (this.mVersionedBitmap.getBitmap() == null && !this.mNoIcon) {
                tryToSetBitmap();
            }
            return this.mVersionedBitmap;
        }

        private void parseSrc(String str) {
            this.mNoIcon = false;
            this.mVersionedBitmap.mBitmap = null;
            if (!TextUtils.isEmpty(str)) {
                String[] strArrSplit = str.split(",");
                if (strArrSplit.length == 2) {
                    this.mPkg = strArrSplit[0];
                    this.mCls = strArrSplit[1];
                    return;
                } else if (strArrSplit.length == 1) {
                    this.mPkg = strArrSplit[0];
                    return;
                } else {
                    Log.e("BitmapProvider", "invalid src of ApplicationIcon type: " + str);
                    this.mNoIcon = true;
                    return;
                }
            }
            Log.e("BitmapProvider", "invalid src of ApplicationIcon type: " + str);
            this.mNoIcon = true;
        }

        private void tryToSetBitmap() {
            Drawable applicationIcon;
            try {
                if (this.mCls != null) {
                    applicationIcon = this.mRoot.getContext().mContext.getPackageManager().getActivityIcon(new ComponentName(this.mPkg, this.mCls));
                } else {
                    applicationIcon = this.mRoot.getContext().mContext.getPackageManager().getApplicationIcon(this.mPkg);
                }
                if (applicationIcon instanceof BitmapDrawable) {
                    this.mVersionedBitmap.setBitmap(((BitmapDrawable) applicationIcon).getBitmap());
                    return;
                }
                int intrinsicWidth = applicationIcon.getIntrinsicWidth();
                int intrinsicHeight = applicationIcon.getIntrinsicHeight();
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, applicationIcon.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                applicationIcon.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
                applicationIcon.draw(canvas);
                this.mVersionedBitmap.setBitmap(bitmapCreateBitmap);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("BitmapProvider", "fail to get icon for src of ApplicationIcon type: " + this.mSrc);
                this.mNoIcon = true;
            }
        }
    }

    private static class ResourceImageProvider extends BitmapProvider {
        public static final String TAG_NAME = "ResourceImage";
        private ResourceManager.AsyncLoadListener mAsyncLoadListener;
        private String mCachedBitmapName;
        String mLoadingBitmapName;
        Object mSrcNameLock;

        public ResourceImageProvider(ScreenElementRoot screenElementRoot) {
            super(screenElementRoot);
            this.mSrcNameLock = new Object();
            this.mAsyncLoadListener = new ResourceManager.AsyncLoadListener() { // from class: com.miui.maml.elements.BitmapProvider.ResourceImageProvider.1
                @Override // com.miui.maml.ResourceManager.AsyncLoadListener
                public void onLoadComplete(String str, ResourceManager.BitmapInfo bitmapInfo) {
                    synchronized (ResourceImageProvider.this.mSrcNameLock) {
                        if (TextUtils.equals(str, ResourceImageProvider.this.mLoadingBitmapName)) {
                            Log.i("BitmapProvider", "load image async complete: " + str + " last cached " + ResourceImageProvider.this.mCachedBitmapName);
                            ResourceImageProvider.this.mVersionedBitmap.setBitmap(bitmapInfo == null ? null : bitmapInfo.mBitmap);
                            ResourceImageProvider.this.mCachedBitmapName = str;
                            ResourceImageProvider.this.mLoadingBitmapName = null;
                        } else {
                            Log.i("BitmapProvider", "load image async complete: " + str + " not equals " + ResourceImageProvider.this.mLoadingBitmapName);
                        }
                    }
                    ResourceImageProvider.this.mRoot.requestUpdate();
                }
            };
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public VersionedBitmap getBitmap(String str, boolean z, int i, int i2) {
            Bitmap bitmap = this.mVersionedBitmap.getBitmap();
            if ((bitmap != null && bitmap.isRecycled()) || !TextUtils.equals(this.mCachedBitmapName, str)) {
                if (z) {
                    ResourceManager.BitmapInfo bitmapInfo = this.mRoot.getContext().mResourceManager.getBitmapInfo(str);
                    this.mVersionedBitmap.setBitmap(bitmapInfo != null ? bitmapInfo.mBitmap : null);
                    this.mCachedBitmapName = str;
                } else {
                    ResourceManager.BitmapInfo bitmapInfoAsync = this.mRoot.getContext().mResourceManager.getBitmapInfoAsync(str, this.mAsyncLoadListener);
                    synchronized (this.mSrcNameLock) {
                        if (bitmapInfoAsync != null) {
                            if (bitmapInfoAsync.mLoading) {
                                this.mLoadingBitmapName = str;
                            }
                        }
                        this.mVersionedBitmap.setBitmap(bitmapInfoAsync == null ? null : bitmapInfoAsync.mBitmap);
                        this.mCachedBitmapName = str;
                        this.mLoadingBitmapName = null;
                    }
                }
            }
            return this.mVersionedBitmap;
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public void finish() {
            super.finish();
            synchronized (this.mSrcNameLock) {
                this.mLoadingBitmapName = null;
                this.mCachedBitmapName = null;
                this.mVersionedBitmap.reset();
            }
        }
    }

    private static class VirtualScreenProvider extends BitmapProvider {
        public static final String TAG_NAME = "VirtualScreen";
        private VirtualScreen mVirtualScreen;

        public VirtualScreenProvider(ScreenElementRoot screenElementRoot) {
            super(screenElementRoot);
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public void init(String str) {
            super.init(str);
            ScreenElement screenElementFindElement = this.mRoot.findElement(str);
            if (screenElementFindElement instanceof VirtualScreen) {
                this.mVirtualScreen = (VirtualScreen) screenElementFindElement;
            }
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public VersionedBitmap getBitmap(String str, boolean z, int i, int i2) {
            VersionedBitmap versionedBitmap = this.mVersionedBitmap;
            VirtualScreen virtualScreen = this.mVirtualScreen;
            versionedBitmap.setBitmap(virtualScreen != null ? virtualScreen.getBitmap() : null);
            return this.mVersionedBitmap;
        }
    }

    public static class BitmapHolderProvider extends BitmapProvider {
        public static final String TAG_NAME = "BitmapHolder";
        private IBitmapHolder mBitmapHolder;
        private String mId;

        public BitmapHolderProvider(ScreenElementRoot screenElementRoot) {
            super(screenElementRoot);
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public void init(String str) {
            super.init(str);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            int iIndexOf = str.indexOf(46);
            if (iIndexOf != -1) {
                String strSubstring = str.substring(0, iIndexOf);
                this.mId = str.substring(iIndexOf + 1);
                str = strSubstring;
            }
            Object objFindElement = this.mRoot.findElement(str);
            if (objFindElement instanceof IBitmapHolder) {
                this.mBitmapHolder = (IBitmapHolder) objFindElement;
            }
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public VersionedBitmap getBitmap(String str, boolean z, int i, int i2) {
            IBitmapHolder iBitmapHolder = this.mBitmapHolder;
            if (iBitmapHolder != null) {
                return iBitmapHolder.getBitmap(this.mId);
            }
            return null;
        }
    }

    private static class FileSystemProvider extends UriProvider {
        public static final String TAG_NAME = "FileSystem";

        public FileSystemProvider(ScreenElementRoot screenElementRoot) {
            super(screenElementRoot);
        }

        @Override // com.miui.maml.elements.BitmapProvider.UriProvider, com.miui.maml.elements.BitmapProvider
        public VersionedBitmap getBitmap(String str, boolean z, int i, int i2) {
            if (TextUtils.isEmpty(str)) {
                this.mVersionedBitmap.setBitmap(null);
                return this.mVersionedBitmap;
            }
            URI uri = new File(str).toURI();
            if (uri == null) {
                this.mVersionedBitmap.setBitmap(null);
                return this.mVersionedBitmap;
            }
            return super.getBitmap(uri.toString(), z, i, i2);
        }
    }

    private static class UriProvider extends BitmapProvider {
        public static final String TAG_NAME = "Uri";
        private String mCachedBitmapUri;
        private String mCurLoadingBitmapUri;
        private Object mLock;

        public UriProvider(ScreenElementRoot screenElementRoot) {
            super(screenElementRoot);
            this.mLock = new Object();
        }

        private class LoaderAsyncTask extends AsyncTask<Object, Object, Bitmap> {
            private int mHeight;
            private String mUri;
            private int mWidth;

            public LoaderAsyncTask(String str, int i, int i2) {
                this.mUri = str;
                this.mWidth = i;
                this.mHeight = i2;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.AsyncTask
            public Bitmap doInBackground(Object... objArr) {
                Bitmap bitmapFromUri = UriProvider.this.getBitmapFromUri(Uri.parse(this.mUri), this.mWidth, this.mHeight);
                if (bitmapFromUri == null) {
                    Log.w("BitmapProvider", "fail to decode bitmap: " + this.mUri);
                }
                synchronized (UriProvider.this.mLock) {
                    if (TextUtils.equals(this.mUri, UriProvider.this.mCurLoadingBitmapUri)) {
                        UriProvider.this.mVersionedBitmap.setBitmap(bitmapFromUri);
                        UriProvider uriProvider = UriProvider.this;
                        uriProvider.mCachedBitmapUri = uriProvider.mCurLoadingBitmapUri;
                        UriProvider.this.mRoot.requestUpdate();
                        UriProvider.this.mCurLoadingBitmapUri = null;
                    }
                }
                return bitmapFromUri;
            }
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public VersionedBitmap getBitmap(String str, boolean z, int i, int i2) {
            if (TextUtils.isEmpty(str)) {
                this.mVersionedBitmap.setBitmap(null);
                return this.mVersionedBitmap;
            }
            Bitmap bitmap = this.mVersionedBitmap.getBitmap();
            if ((bitmap != null && bitmap.isRecycled()) || !TextUtils.equals(this.mCachedBitmapUri, str)) {
                synchronized (this.mLock) {
                    if (!TextUtils.equals(this.mCurLoadingBitmapUri, str) && !TextUtils.equals(this.mCachedBitmapUri, str)) {
                        this.mCurLoadingBitmapUri = str;
                        new LoaderAsyncTask(str, i, i2).execute(new Object[0]);
                    }
                }
            }
            this.mVersionedBitmap.setBitmap(bitmap);
            return this.mVersionedBitmap;
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public void finish() {
            super.finish();
            synchronized (this.mLock) {
                this.mCachedBitmapUri = null;
                this.mCurLoadingBitmapUri = null;
                this.mVersionedBitmap.reset();
            }
        }
    }

    public static class BitmapVariableProvider extends BitmapProvider {
        public static final String TAG_NAME = "BitmapVar";
        private String mCurSrc;
        private Expression mIndexExpression;
        private IndexedVariable mVar;

        public BitmapVariableProvider(ScreenElementRoot screenElementRoot) {
            super(screenElementRoot);
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public void init(String str) {
            super.init(str);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.mVar = new IndexedVariable(str, this.mRoot.getVariables(), false);
            this.mCurSrc = str;
        }

        @Override // com.miui.maml.elements.BitmapProvider
        public VersionedBitmap getBitmap(String str, boolean z, int i, int i2) {
            Bitmap bitmap;
            int i3;
            Bitmap bitmap2 = null;
            if (!Utils.equals(this.mCurSrc, str)) {
                this.mVar = null;
                this.mIndexExpression = null;
                if (!TextUtils.isEmpty(str)) {
                    int iIndexOf = str.indexOf(91);
                    int length = str.length();
                    if (iIndexOf != -1 && iIndexOf < (i3 = length - 1) && str.charAt(i3) == ']') {
                        this.mIndexExpression = Expression.build(this.mRoot.getVariables(), str.substring(iIndexOf + 1, i3));
                    }
                    this.mVar = new IndexedVariable(this.mIndexExpression == null ? str : str.substring(0, iIndexOf), this.mRoot.getVariables(), false);
                }
                this.mCurSrc = str;
            }
            try {
                IndexedVariable indexedVariable = this.mVar;
                if (indexedVariable != null) {
                    Expression expression = this.mIndexExpression;
                    if (expression != null) {
                        bitmap = (Bitmap) indexedVariable.getArr((int) expression.evaluate());
                    } else {
                        bitmap = (Bitmap) indexedVariable.get();
                    }
                    bitmap2 = bitmap;
                }
            } catch (ClassCastException unused) {
                Log.w("BitmapProvider", "fail to cast as Bitmap from object: " + str);
            }
            this.mVersionedBitmap.setBitmap(bitmap2);
            return this.mVersionedBitmap;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.InputStream] */
    protected Bitmap getBitmapFromUri(Uri uri, int i, int i2) throws Throwable {
        ?? r4;
        InputStream inputStreamOpenInputStream;
        InputStream inputStreamOpenInputStream2;
        InputStream inputStream = null;
        try {
            try {
                inputStreamOpenInputStream2 = this.mRoot.getContext().mContext.getContentResolver().openInputStream(uri);
                try {
                    if (i <= 0 || i2 <= 0) {
                        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream2, null, null);
                        IOUtils.closeQuietly(inputStreamOpenInputStream2);
                        IOUtils.closeQuietly((InputStream) null);
                        return bitmapDecodeStream;
                    }
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStreamOpenInputStream2, null, options);
                    options.inSampleSize = computeSampleSize(options, i * i2);
                    options.inJustDecodeBounds = false;
                    options.outHeight = i2;
                    options.outWidth = i;
                    inputStreamOpenInputStream = this.mRoot.getContext().mContext.getContentResolver().openInputStream(uri);
                    try {
                        Bitmap bitmapDecodeStream2 = BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                        IOUtils.closeQuietly(inputStreamOpenInputStream2);
                        IOUtils.closeQuietly(inputStreamOpenInputStream);
                        return bitmapDecodeStream2;
                    } catch (Exception e) {
                        e = e;
                        Log.d("BitmapProvider", "getBitmapFromUri Exception", e);
                        IOUtils.closeQuietly(inputStreamOpenInputStream2);
                        IOUtils.closeQuietly(inputStreamOpenInputStream);
                        return null;
                    }
                } catch (Exception e2) {
                    e = e2;
                    inputStreamOpenInputStream = null;
                } catch (Throwable th) {
                    th = th;
                    this = null;
                    inputStream = inputStreamOpenInputStream2;
                    r4 = this;
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly((InputStream) r4);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                inputStreamOpenInputStream = null;
                inputStreamOpenInputStream2 = null;
            } catch (Throwable th2) {
                th = th2;
                r4 = 0;
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly((InputStream) r4);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static int computeSampleSize(BitmapFactory.Options options, int i) {
        int i2 = 1;
        while (true) {
            int i3 = i2 * 2;
            if (i3 > Math.sqrt((((double) options.outHeight) * ((double) options.outWidth)) / ((double) i))) {
                return i2;
            }
            i2 = i3;
        }
    }
}
