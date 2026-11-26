package com.miui.pad.feature.notes.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.tool.PriorityThreadFactory;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import miuix.appcompat.view.menu.HyperMenuUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ShareAppsAdapter extends RecyclerView.Adapter<CellHolder> {
    private static final int FIRST_CELL = 0;
    private static final String GALLERY_PACKAGE_NAME = "com.miui.gallery";
    private static final String INTENT_CLASSNAME_SHARE_GALLEY = "com.miui.gallery.ShareImgUI";
    private static final String INTENT_CLASSNAME_SHARE_QQ = "com.tencent.mobileqq.activity.JumpActivity";
    private static final String INTENT_CLASSNAME_SHARE_WECHAT = "com.tencent.mm.ui.tools.ShareImgUI";
    private static final String INTENT_CLASSNAME_SHARE_WECHAT_FAVORITE = "com.tencent.mm.ui.tools.AddFavoriteUI";
    private static final String INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    private static final String INTENT_CLASSNAME_SHARE_WEIBO = "com.sina.weibo.composerinde.ComposerDispatchActivity";
    private static final String INTENT_PACKAGENAME_SHARE_QZONE = "com.qzone";
    private static final int OTHER_CELL = 1;
    private static final String TAG = "ShareAppsAdapter";
    public static final String TYPE_IMAGE = "image/*";
    public static final String TYPE_PDF = "application/pdf";
    private static final Map<String, Integer> appSortMap;
    private static final List<String> blackFileResolver;
    private static final List<String> blackMultiImageResolver;
    private static final List<String> blackResolver;
    public boolean isMulti;
    private Context mContext;
    private OnIntentSelectedListener mListener;
    private PackageManager mPm;
    private boolean mResumed;
    private Intent mTarIntent;
    private int shareType;
    private LinkedList<WeakReference<View>> mCachedViews = new LinkedList<>();
    private List<DisplayResolvedInfo> mData = new ArrayList();
    private Comparator mDataComparator = new Comparator() { // from class: com.miui.pad.feature.notes.share.ShareAppsAdapter$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ShareAppsAdapter.lambda$new$0((ShareAppsAdapter.DisplayResolvedInfo) obj, (ShareAppsAdapter.DisplayResolvedInfo) obj2);
        }
    };
    private ResolveInfoLoader mLoader = new ResolveInfoLoader();

    public interface OnIntentSelectedListener {
        void onIntentSelected(Intent intent, int i);
    }

    public interface OnSaveSelectedListener extends OnIntentSelectedListener {
        void onSaveToLocal(int i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i == 0 ? 0 : 1;
    }

    static {
        HashMap map = new HashMap();
        appSortMap = map;
        ArrayList arrayList = new ArrayList();
        blackResolver = arrayList;
        ArrayList arrayList2 = new ArrayList();
        blackMultiImageResolver = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        blackFileResolver = arrayList3;
        arrayList.add("com.taou.maimai.share.ShareToFeedActivity");
        arrayList.add("com.tencent.mm.ui.tools.ShareToStatusUI");
        arrayList2.add("com.miui.notes.ui.activity.IntermediaryActivity");
        arrayList2.add("com.miui.bugreport.ui.FeedbackActivity");
        arrayList2.add("com.tencent.mm.ui.tools.ShareToStatusUI");
        arrayList2.add("cooperation.qqfav.widget.QfavJumpActivity");
        arrayList2.add("com.tencent.mobileqq.activity.qfileJumpActivity");
        arrayList3.add("cooperation.qqfav.widget.QfavJumpActivity");
        arrayList3.add("com.xiaomi.vipaccount.ui.publish.PublishNewActivity");
        arrayList3.add("com.android.mms.ui.ComposeMessageRouterActivity");
        arrayList3.add("com.miui.bugreport.ui.FeedbackActivity");
        arrayList3.add("com.xingin.xhs.routers.RouterPageActivity");
        arrayList3.add("com.xiaomi.vipaccount.ui.publish.PublishNewActivity");
        arrayList3.add(INTENT_CLASSNAME_SHARE_WEIBO);
        arrayList3.add("com.sina.weibo.weiyou.share.WeiyouShareDispatcher");
        arrayList3.add(INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS);
        map.put(INTENT_CLASSNAME_SHARE_WECHAT, 1);
        map.put(INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS, 2);
        map.put(INTENT_CLASSNAME_SHARE_WECHAT_FAVORITE, 3);
        map.put(INTENT_CLASSNAME_SHARE_QQ, 4);
        map.put(INTENT_PACKAGENAME_SHARE_QZONE, 5);
        map.put(INTENT_CLASSNAME_SHARE_WEIBO, 6);
    }

    static /* synthetic */ int lambda$new$0(DisplayResolvedInfo displayResolvedInfo, DisplayResolvedInfo displayResolvedInfo2) {
        if (displayResolvedInfo.getIndex() == displayResolvedInfo2.getIndex()) {
            return displayResolvedInfo.getResolvedName().compareTo(displayResolvedInfo2.getResolvedName());
        }
        return displayResolvedInfo.getIndex() - displayResolvedInfo2.getIndex();
    }

    public ShareAppsAdapter(Context context, Intent intent, boolean z, int i, boolean z2) {
        this.isMulti = false;
        this.mResumed = false;
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mTarIntent = intent;
        this.shareType = i;
        this.isMulti = z2;
        reBuildList();
        this.mResumed = z;
    }

    public void resume() {
        if (this.mResumed) {
            return;
        }
        this.mResumed = true;
        notifyDataSetChanged();
    }

    public void pause() {
        this.mResumed = false;
    }

    public void release() {
        this.mLoader.release();
        this.mListener = null;
    }

    public void setOnIntentSelectedListener(OnIntentSelectedListener onIntentSelectedListener) {
        this.mListener = onIntentSelectedListener;
    }

    public boolean requery(Intent intent) {
        if (intent == null || intent.filterEquals(this.mTarIntent)) {
            return false;
        }
        this.mTarIntent = intent;
        reBuildList();
        notifyDataSetChanged();
        return true;
    }

    public void reBuildList() {
        DisplayResolvedInfo displayResolvedInfo;
        if (this.mTarIntent == null) {
            return;
        }
        this.mData.clear();
        System.currentTimeMillis();
        Log.d(TAG, "queryIntentActivities");
        List<ResolveInfo> listQueryIntentActivities = this.mPm.queryIntentActivities(this.mTarIntent, 65536);
        Log.d(TAG, "queryIntentActivities infos" + listQueryIntentActivities.size());
        filterResolveInfoList(listQueryIntentActivities);
        LinkedList linkedList = new LinkedList();
        linkedList.add(generateSaveToGalleryInfo());
        addResolveListDedupe(linkedList, listQueryIntentActivities);
        int size = linkedList.size();
        if (size > 0) {
            ResolveInfo resolveInfo = linkedList.get(0);
            for (int i = 1; i < size; i++) {
                ResolveInfo resolveInfo2 = linkedList.get(i);
                if (resolveInfo.priority != resolveInfo2.priority || resolveInfo.isDefault != resolveInfo2.isDefault) {
                    while (i < size) {
                        linkedList.remove(i);
                        size--;
                    }
                }
            }
            for (int i2 = 0; i2 < size; i2++) {
                ActivityInfo activityInfo = linkedList.get(i2).activityInfo;
                String str = activityInfo.name;
                String str2 = activityInfo.packageName;
                Log.d(TAG, "resolve:" + str);
                if (TextUtils.equals(str, INTENT_CLASSNAME_SHARE_GALLEY)) {
                    displayResolvedInfo = new DisplayResolvedInfo(this.mTarIntent, null, 0);
                } else {
                    if (!str.equals(INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS) || !this.isMulti) {
                        Integer num = appSortMap.get(str);
                        displayResolvedInfo = new DisplayResolvedInfo(this.mTarIntent, linkedList.get(i2), num != null ? num.intValue() : Integer.MAX_VALUE);
                    }
                }
                displayResolvedInfo.setName(loadLabel(this.mContext, displayResolvedInfo).toString());
                this.mData.add(displayResolvedInfo);
            }
            sortResolveList(this.mData);
        }
    }

    private boolean isFileShare() {
        return this.shareType != 1;
    }

    private void filterResolveInfoList(List<ResolveInfo> list) {
        if (list == null) {
            return;
        }
        int i = 0;
        while (i < list.size()) {
            ResolveInfo resolveInfo = list.get(i);
            String str = resolveInfo.activityInfo.name;
            if (!resolveInfo.activityInfo.exported || blackResolver.contains(resolveInfo.activityInfo.name) || ((isFileShare() && blackFileResolver.contains(str)) || ((!isFileShare() && this.isMulti && blackMultiImageResolver.contains(str)) || NoteApp.getInstance().getPackageName().equals(resolveInfo.activityInfo.applicationInfo.packageName)))) {
                Log.d(TAG, "not export");
                list.remove(i);
            } else {
                String str2 = resolveInfo.activityInfo.permission;
                if (!TextUtils.isEmpty(str2) && this.mContext.checkPermission(str2, Process.myPid(), Process.myUid()) != 0) {
                    list.remove(i);
                }
                i++;
            }
            i--;
            i++;
        }
    }

    private void sortResolveList(List<DisplayResolvedInfo> list) {
        Collections.sort(list, this.mDataComparator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSameResolvedComponent(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
        return resolveInfo != null && resolveInfo2 != null && TextUtils.equals(resolveInfo.activityInfo.packageName, resolveInfo2.activityInfo.packageName) && TextUtils.equals(resolveInfo.activityInfo.name, resolveInfo2.activityInfo.name);
    }

    private void addResolveListDedupe(List<ResolveInfo> list, List<ResolveInfo> list2) {
        int size = list2.size();
        int size2 = list.size();
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = list2.get(i);
            int i2 = 0;
            while (true) {
                if (i2 < size2) {
                    if (isSameResolvedComponent(resolveInfo, list.get(i))) {
                        break;
                    } else {
                        i2++;
                    }
                } else {
                    list.add(resolveInfo);
                    break;
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CellHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CellHolder(viewGroup, i == 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CellHolder cellHolder, int i) {
        List<DisplayResolvedInfo> list = this.mData;
        if (list == null || list.size() == 0 || i >= this.mData.size()) {
            return;
        }
        cellHolder.mText.setTextColor(this.mContext.getColor(R.color.sns_chooser_text_color));
        DisplayResolvedInfo displayResolvedInfo = this.mData.get(i);
        if (cellHolder.needRefresh(displayResolvedInfo)) {
            cellHolder.clShare.setContentDescription(displayResolvedInfo.mResolvedName);
            cellHolder.mInfo = displayResolvedInfo;
            this.mLoader.loadInfo(this.mContext, cellHolder.mIcon, cellHolder.mText, cellHolder.mInfo);
            return;
        }
        cellHolder.mInfo = displayResolvedInfo;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<DisplayResolvedInfo> list = this.mData;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void updateShareApp(int i) {
        if (this.isMulti != (i > 1)) {
            this.isMulti = i > 1;
            reBuildList();
            notifyDataSetChanged();
        }
    }

    private static class ResolveInfoLoader {
        private Map<Integer, String> mCacheKey = Collections.synchronizedMap(new HashMap());
        private Map<String, LoadResult> mCacheResult = new HashMap();
        private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(1, 2, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(), new PriorityThreadFactory("thread-pool", 10));

        public void release() {
            try {
                this.mCacheKey.clear();
                this.mCacheResult.clear();
                this.mExecutor.shutdownNow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void loadInfo(Context context, ImageView imageView, TextView textView, DisplayResolvedInfo displayResolvedInfo) {
            if (displayResolvedInfo == null) {
                return;
            }
            String strGenerateKey = generateKey(displayResolvedInfo.getResolveInfo());
            LoadingInfo loadingInfo = new LoadingInfo(imageView, textView, displayResolvedInfo);
            this.mCacheKey.put(Integer.valueOf(loadingInfo.getViewId()), strGenerateKey);
            LoadResult loadResult = this.mCacheResult.get(strGenerateKey);
            if (loadResult == null) {
                Log.d(ShareAppsAdapter.TAG, "load from file");
                submit(context, loadingInfo);
            } else {
                loadResult.setLoadingInfo(loadingInfo);
                setResult(loadResult);
                Log.d(ShareAppsAdapter.TAG, "load from cache");
            }
        }

        private void submit(Context context, LoadingInfo loadingInfo) {
            new LoadTask(context).executeOnExecutor(this.mExecutor, loadingInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setResult(LoadResult loadResult) {
            if (needApplyResult(loadResult)) {
                ImageView iconView = loadResult.mLoadingInfo.getIconView();
                if (iconView != null) {
                    iconView.setImageDrawable(loadResult.mIcon);
                }
                TextView labelView = loadResult.mLoadingInfo.getLabelView();
                if (labelView != null) {
                    labelView.setText(loadResult.mLabel);
                }
                this.mCacheKey.remove(generateKey(loadResult.mLoadingInfo.mResolve.getResolveInfo()));
            }
        }

        private boolean needApplyResult(LoadResult loadResult) {
            if (loadResult == null || loadResult.mLoadingInfo == null) {
                return false;
            }
            this.mCacheResult.put(generateKey(loadResult.mLoadingInfo.mResolve.getResolveInfo()), loadResult);
            return TextUtils.equals(generateKey(loadResult.mLoadingInfo.mResolve.getResolveInfo()), this.mCacheKey.get(Integer.valueOf(loadResult.mLoadingInfo.getViewId())));
        }

        private static String generateKey(ResolveInfo resolveInfo) {
            StringBuilder sb = new StringBuilder();
            if (resolveInfo != null) {
                sb.append(resolveInfo.activityInfo.packageName).append(HyperMenuUtils.GROUP_TITLE_START).append(resolveInfo.activityInfo.name);
            }
            return sb.toString();
        }

        private static class LoadResult {
            final Drawable mIcon;
            final CharSequence mLabel;
            LoadingInfo mLoadingInfo;

            public LoadResult(Drawable drawable, CharSequence charSequence) {
                this.mIcon = drawable;
                this.mLabel = charSequence;
            }

            LoadResult setLoadingInfo(LoadingInfo loadingInfo) {
                this.mLoadingInfo = loadingInfo;
                return this;
            }
        }

        private static class LoadingInfo {
            final WeakReference<ImageView> mIcon;
            final WeakReference<TextView> mLabel;
            final DisplayResolvedInfo mResolve;

            public LoadingInfo(ImageView imageView, TextView textView, DisplayResolvedInfo displayResolvedInfo) {
                this.mIcon = new WeakReference<>(imageView);
                this.mLabel = new WeakReference<>(textView);
                this.mResolve = displayResolvedInfo;
            }

            public int getViewId() {
                ImageView iconView = getIconView();
                if (iconView != null) {
                    return iconView.hashCode();
                }
                return 0;
            }

            public ImageView getIconView() {
                WeakReference<ImageView> weakReference = this.mIcon;
                if (weakReference != null) {
                    return weakReference.get();
                }
                return null;
            }

            public TextView getLabelView() {
                WeakReference<TextView> weakReference = this.mLabel;
                if (weakReference != null) {
                    return weakReference.get();
                }
                return null;
            }

            public void setIcon(Drawable drawable) {
                ImageView iconView = getIconView();
                if (iconView != null) {
                    iconView.setImageDrawable(drawable);
                }
            }

            public void setLabel(CharSequence charSequence) {
                TextView labelView = getLabelView();
                if (labelView != null) {
                    labelView.setText(charSequence);
                }
            }
        }

        private class LoadTask extends AsyncTask<LoadingInfo, Void, LoadResult> {
            private WeakReference<Context> mContextRef;

            LoadTask(Context context) {
                this.mContextRef = new WeakReference<>(context);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public LoadResult doInBackground(LoadingInfo... loadingInfoArr) {
                LoadingInfo loadingInfo = loadingInfoArr[0];
                if (loadingInfo == null) {
                    return null;
                }
                IconResult iconResultLoadIcon = loadIcon(loadingInfo);
                return new LoadResult(iconResultLoadIcon.drawable, loadingInfo.mResolve.getResolvedName()).setLoadingInfo(loadingInfo);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(LoadResult loadResult) {
                ResolveInfoLoader.this.setResult(loadResult);
            }

            private IconResult loadIcon(LoadingInfo loadingInfo) {
                Drawable drawableLoadIcon = null;
                try {
                    DisplayResolvedInfo displayResolvedInfo = loadingInfo.mResolve;
                    displayResolvedInfo.getResolveInfo();
                    Context context = this.mContextRef.get();
                    if (context != null) {
                        drawableLoadIcon = ShareAppsAdapter.loadIcon(context, displayResolvedInfo);
                    }
                } catch (Exception e) {
                    Log.e(ShareAppsAdapter.TAG, e.toString());
                }
                return ResolveInfoLoader.this.new IconResult(drawableLoadIcon);
            }

            private CharSequence loadLabel(DisplayResolvedInfo displayResolvedInfo) {
                Context context = this.mContextRef.get();
                if (context != null) {
                    return ShareAppsAdapter.loadLabel(context, displayResolvedInfo);
                }
                return null;
            }
        }

        private class IconResult {
            final Drawable drawable;

            public IconResult(Drawable drawable) {
                this.drawable = drawable;
            }
        }
    }

    public class CellHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ConstraintLayout clShare;
        public ImageView mIcon;
        public DisplayResolvedInfo mInfo;
        public TextView mText;

        public CellHolder(ViewGroup viewGroup, boolean z) {
            super(LayoutInflater.from(ShareAppsAdapter.this.mContext).inflate(R.layout.item_chooser_cell, viewGroup, false));
            if (z) {
                int paddingStart = this.itemView.getPaddingStart();
                int paddingEnd = this.itemView.getPaddingEnd();
                int paddingTop = this.itemView.getPaddingTop();
                int paddingBottom = this.itemView.getPaddingBottom();
                this.itemView.setPaddingRelative(paddingStart + ShareAppsAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.chooser_item_first_cell_extra_padding_start), paddingTop, paddingEnd, paddingBottom);
            }
            this.mIcon = (ImageView) this.itemView.findViewById(R.id.chooser_icon);
            this.mText = (TextView) this.itemView.findViewById(R.id.chooser_text);
            ConstraintLayout constraintLayout = (ConstraintLayout) this.itemView.findViewById(R.id.cl_share);
            this.clShare = constraintLayout;
            constraintLayout.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            DisplayResolvedInfo displayResolvedInfo;
            if (ShareAppsAdapter.this.mListener == null || (displayResolvedInfo = this.mInfo) == null) {
                return;
            }
            if (displayResolvedInfo.getIndex() == 0 && (ShareAppsAdapter.this.mListener instanceof OnSaveSelectedListener)) {
                ((OnSaveSelectedListener) ShareAppsAdapter.this.mListener).onSaveToLocal(getAdapterPosition());
            } else {
                ShareAppsAdapter.this.mListener.onIntentSelected(new Intent(this.mInfo.getResolvedIntent()), getAdapterPosition());
            }
        }

        public boolean needRefresh(DisplayResolvedInfo displayResolvedInfo) {
            return !displayResolvedInfo.equals(this.mInfo) || this.mIcon.getDrawable() == null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DisplayResolvedInfo {
        private int mIndex;
        private ResolveInfo mResolveInfo;
        private Intent mResolvedIntent;
        private String mResolvedName;

        public DisplayResolvedInfo(ShareAppsAdapter shareAppsAdapter, Intent intent, ResolveInfo resolveInfo) {
            this(intent, resolveInfo, -1);
        }

        public DisplayResolvedInfo(Intent intent, ResolveInfo resolveInfo, int i) {
            this.mResolvedName = "";
            this.mResolveInfo = resolveInfo;
            this.mIndex = i;
            this.mResolvedIntent = new Intent(intent);
            ResolveInfo resolveInfo2 = this.mResolveInfo;
            if (resolveInfo2 != null) {
                ActivityInfo activityInfo = resolveInfo2.activityInfo;
                this.mResolvedIntent.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            }
        }

        public Intent getResolvedIntent() {
            return this.mResolvedIntent;
        }

        public ResolveInfo getResolveInfo() {
            return this.mResolveInfo;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public void setName(String str) {
            this.mResolvedName = str;
        }

        public String getResolvedName() {
            return this.mResolvedName;
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof DisplayResolvedInfo)) {
                return false;
            }
            return ShareAppsAdapter.isSameResolvedComponent(getResolveInfo(), ((DisplayResolvedInfo) obj).getResolveInfo());
        }

        public int hashCode() {
            return (this.mResolveInfo.activityInfo.packageName + this.mResolveInfo.activityInfo.name).hashCode();
        }
    }

    private static ResolveInfo generateSaveToGalleryInfo() {
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.name = INTENT_CLASSNAME_SHARE_GALLEY;
        activityInfo.packageName = "com.miui.gallery";
        ResolveInfo resolveInfo = new ResolveInfo();
        resolveInfo.activityInfo = activityInfo;
        return resolveInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Drawable loadIcon(Context context, DisplayResolvedInfo displayResolvedInfo) {
        try {
            ResolveInfo resolveInfo = displayResolvedInfo.getResolveInfo();
            if (context == null) {
                return null;
            }
            if (displayResolvedInfo.getIndex() != 0) {
                context.getResources();
                String str = resolveInfo.activityInfo.name;
                String str2 = resolveInfo.activityInfo.packageName;
                return resolveInfo.loadIcon(context.getPackageManager());
            }
            return context.getDrawable(R.drawable.ic_save_to_gallery);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence loadLabel(Context context, DisplayResolvedInfo displayResolvedInfo) {
        if (displayResolvedInfo.getIndex() != 0) {
            ResolveInfo resolveInfo = displayResolvedInfo.getResolveInfo();
            try {
                Resources resources = context.getResources();
                String str = resolveInfo.activityInfo.name;
                String str2 = resolveInfo.activityInfo.packageName;
                int identifier = resources.getIdentifier(resolveInfo.activityInfo.packageName + "_" + resolveInfo.activityInfo.name, TypedValues.Custom.S_STRING, context.getPackageName());
                if (identifier != 0) {
                    return resources.getString(identifier);
                }
            } catch (Exception unused) {
            }
            return resolveInfo.loadLabel(context.getPackageManager());
        }
        return context.getResources().getString(R.string.save_to_local_icon_name);
    }
}
