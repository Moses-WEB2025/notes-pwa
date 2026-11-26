package com.market.sdk;

import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import com.market.sdk.utils.CollectionUtils;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes2.dex */
public class ImageManager {
    private static ConcurrentHashMap<String, Uri> sCachedUri = new ConcurrentHashMap<>();
    private static Map<String, HashSet<ImageCallback>> sLoadingIcons = CollectionUtils.newHashMap();

    public static void loadIcon(String str, String str2, ImageCallback imageCallback) {
        String str3 = !TextUtils.isEmpty(str2) ? str + "_" + str2 : str;
        Uri uri = sCachedUri.get(str3);
        if (uri != null && new File(uri.getPath()).exists()) {
            imageCallback.onImageLoadSuccess(str, uri);
            return;
        }
        synchronized (sLoadingIcons) {
            HashSet<ImageCallback> hashSetNewHashSet = sLoadingIcons.get(str3);
            boolean zContainsKey = sLoadingIcons.containsKey(str3);
            if (hashSetNewHashSet == null) {
                hashSetNewHashSet = CollectionUtils.newHashSet();
                sLoadingIcons.put(str3, hashSetNewHashSet);
            }
            hashSetNewHashSet.add(imageCallback);
            if (!zContainsKey) {
                new IconLoadTask(str, str2, imageCallback).start();
            }
        }
    }

    public static void loadImage(String str, int i, int i2, ImageCallback imageCallback) {
        Uri uri = sCachedUri.get(str);
        if (uri != null && new File(uri.getPath()).exists()) {
            imageCallback.onImageLoadSuccess(str, uri);
            return;
        }
        synchronized (sLoadingIcons) {
            HashSet<ImageCallback> hashSetNewHashSet = sLoadingIcons.get(str);
            boolean zContainsKey = sLoadingIcons.containsKey(str);
            if (hashSetNewHashSet == null) {
                hashSetNewHashSet = CollectionUtils.newHashSet();
                sLoadingIcons.put(str, hashSetNewHashSet);
            }
            hashSetNewHashSet.add(imageCallback);
            if (!zContainsKey) {
                new ImageLoadTask(str, i, i2, imageCallback).start();
            }
        }
    }

    private static class IconLoadTask {
        private String mAppId;
        private String mMask;
        private IImageCallback mResponse;

        public IconLoadTask(String str, String str2, ImageCallback imageCallback) {
            this.mAppId = str;
            this.mMask = str2;
            this.mResponse = new ImageLoadResponse(TextUtils.isEmpty(str2) ? str : str + "_" + str2);
        }

        public void start() {
            new RemoteMethodInvoker<Void>() { // from class: com.market.sdk.ImageManager.IconLoadTask.1
                @Override // com.market.sdk.RemoteMethodInvoker
                public Void innerInvoke(IMarketService iMarketService) throws RemoteException {
                    iMarketService.loadIcon(IconLoadTask.this.mAppId, IconLoadTask.this.mMask, IconLoadTask.this.mResponse);
                    return null;
                }
            }.invokeAsync();
        }
    }

    private static class ImageLoadTask {
        private int mMaxHeight;
        private int mMaxWidth;
        private IImageCallback mResponse;
        private String mUrl;

        public ImageLoadTask(String str, int i, int i2, ImageCallback imageCallback) {
            this.mUrl = str;
            this.mResponse = new ImageLoadResponse(this.mUrl);
            this.mMaxWidth = i;
            this.mMaxHeight = i2;
        }

        public void start() {
            new RemoteMethodInvoker<Void>() { // from class: com.market.sdk.ImageManager.ImageLoadTask.1
                @Override // com.market.sdk.RemoteMethodInvoker
                public Void innerInvoke(IMarketService iMarketService) throws RemoteException {
                    iMarketService.loadImage(ImageLoadTask.this.mUrl, ImageLoadTask.this.mMaxWidth, ImageLoadTask.this.mMaxHeight, ImageLoadTask.this.mResponse);
                    return null;
                }
            }.invokeAsync();
        }
    }

    private static class ImageLoadResponse extends IImageCallback.Stub {
        private String mKey;

        public ImageLoadResponse(String str) {
            this.mKey = str;
        }

        @Override // com.market.sdk.IImageCallback
        public void onImageLoadSuccess(String str, Uri uri) {
            ImageManager.sCachedUri.put(this.mKey, uri);
            synchronized (ImageManager.sLoadingIcons) {
                Set set = (Set) ImageManager.sLoadingIcons.remove(this.mKey);
                if (!CollectionUtils.isEmpty(set)) {
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        ((ImageCallback) it.next()).onImageLoadSuccess(str, uri);
                    }
                }
            }
        }

        @Override // com.market.sdk.IImageCallback
        public void onImageLoadFailed(String str) {
            synchronized (ImageManager.sLoadingIcons) {
                Set set = (Set) ImageManager.sLoadingIcons.remove(this.mKey);
                if (!CollectionUtils.isEmpty(set)) {
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        ((ImageCallback) it.next()).onImageLoadFailed(str);
                    }
                }
            }
        }
    }
}
