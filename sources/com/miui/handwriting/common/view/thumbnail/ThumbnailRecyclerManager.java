package com.miui.handwriting.common.view.thumbnail;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.creation.common.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ThumbnailRecyclerManager {
    private ThumbnailAdapter mAdapter;
    private ObjectAnimator mHideAnimator;
    private LinearLayoutManager mLayoutManager;
    private ThumbnailListener mListener;
    private RecyclerView mRecyclerView;
    private View mRootView;
    private ObjectAnimator mShowAnimator;
    private boolean mShow = false;
    private final String TAG = "ThumbnailRecyclerManager";

    public interface ThumbnailListener {
        void onImageLoad(List<Integer> list);

        void onItemClick(int i);
    }

    public ThumbnailRecyclerManager(View view, ThumbnailListener thumbnailListener) {
        this.mRootView = view;
        this.mListener = thumbnailListener;
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        this.mAdapter = new ThumbnailAdapter(thumbnailListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mRecyclerView.getContext(), 1, false);
        this.mLayoutManager = linearLayoutManager;
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    ThumbnailRecyclerManager.this.loadFromScroll();
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadFromScroll() {
        int iFindFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
        int iFindLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
        List<Bitmap> imageList = this.mAdapter.getImageList();
        ArrayList arrayList = new ArrayList();
        int i = (iFindLastVisibleItemPosition - iFindFirstVisibleItemPosition) + 1;
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 + iFindFirstVisibleItemPosition;
            if (imageList.get(i3) == null) {
                arrayList.add(Integer.valueOf(i3));
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mListener.onImageLoad(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadVisibleItem() {
        int iFindFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
        int iFindLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
        Log.d(this.TAG, "load visible  first:" + iFindFirstVisibleItemPosition + "------last:" + iFindLastVisibleItemPosition);
        ArrayList arrayList = new ArrayList();
        int i = (iFindLastVisibleItemPosition - iFindFirstVisibleItemPosition) + 1;
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(Integer.valueOf(i2 + iFindFirstVisibleItemPosition));
        }
        this.mListener.onImageLoad(arrayList);
    }

    public void setBitmap(Bitmap bitmap, int i) {
        this.mAdapter.setBitmap(bitmap, i);
    }

    public void setBitmapList(List<Integer> list, List<Bitmap> list2) {
        this.mAdapter.setBitmapList(list, list2);
    }

    public void scrollToPosition(int i) {
        this.mAdapter.pageChange(i);
        this.mRecyclerView.smoothScrollToPosition(i);
    }

    public void addNewPage(int i) {
        Log.d(this.TAG, "add new page:" + i);
        this.mAdapter.addNewPage(i);
    }

    public void insetPage(int i, int i2) {
        Log.d(this.TAG, "insert page index:" + i + "----count:" + i2);
        this.mAdapter.insertPage(i, i2);
    }

    public void removePage(List<Integer> list) {
        Log.d(this.TAG, "remove page:" + list.size());
        this.mAdapter.removePages(list);
    }

    public void show(int i, int i2) {
        this.mAdapter.initAdapter(i, i2);
        showRecyclerview();
        this.mRecyclerView.scrollToPosition(i);
        this.mRecyclerView.post(new Runnable() { // from class: com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.loadVisibleItem();
            }
        });
    }

    public void hide() {
        hideRecyclerview();
    }

    public boolean getShow() {
        return this.mShow;
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    private void showRecyclerview() {
        if (this.mShowAnimator == null) {
            View view = this.mRootView;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", 0.0f, -view.getWidth());
            this.mShowAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(300L);
        }
        if (this.mShowAnimator.isRunning()) {
            Log.i(this.TAG, "show anim return");
        } else {
            this.mShow = true;
            this.mShowAnimator.start();
        }
    }

    private void hideRecyclerview() {
        if (this.mHideAnimator == null) {
            View view = this.mRootView;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", -view.getWidth(), 0.0f);
            this.mHideAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(300L);
        }
        if (this.mHideAnimator.isRunning()) {
            Log.i(this.TAG, "hide anim return");
        } else {
            this.mShow = false;
            this.mHideAnimator.start();
        }
    }

    public void pageLimitChange(boolean z) {
        if (z) {
            hideRecyclerview();
        }
    }
}
