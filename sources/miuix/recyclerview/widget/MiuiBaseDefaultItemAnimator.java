package miuix.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.SimpleItemAnimator;
import ch.qos.logback.core.CoreConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miuix.animation.Folme;

/* JADX INFO: loaded from: classes6.dex */
public abstract class MiuiBaseDefaultItemAnimator extends SimpleItemAnimator {
    private ArrayList<androidx.recyclerview.widget.RecyclerView.ViewHolder> mPendingRemovals = new ArrayList<>();
    private ArrayList<androidx.recyclerview.widget.RecyclerView.ViewHolder> mPendingAdditions = new ArrayList<>();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList<>();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList<>();
    private ArrayList<ArrayList<androidx.recyclerview.widget.RecyclerView.ViewHolder>> mAdditionsList = new ArrayList<>();
    private ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList<>();
    private ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList<>();
    private ArrayList<androidx.recyclerview.widget.RecyclerView.ViewHolder> mAddAnimations = new ArrayList<>();
    private ArrayList<androidx.recyclerview.widget.RecyclerView.ViewHolder> mMoveAnimations = new ArrayList<>();
    private ArrayList<androidx.recyclerview.widget.RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<>();
    private ArrayList<androidx.recyclerview.widget.RecyclerView.ViewHolder> mChangeAnimations = new ArrayList<>();

    abstract void animateAddImpl(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder);

    abstract void animateChangeImpl(ChangeInfo changeInfo);

    abstract void animateMoveImpl(MoveInfo moveInfo);

    abstract void animateRemoveImpl(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder);

    abstract void prepareAdd(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder);

    abstract void prepareChange(ChangeInfo changeInfo);

    abstract void prepareMove(MoveInfo moveInfo);

    static class MoveInfo {
        int fromX;
        int fromY;
        androidx.recyclerview.widget.RecyclerView.ViewHolder holder;
        int toX;
        int toY;

        MoveInfo(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4) {
            this.holder = viewHolder;
            this.fromX = i;
            this.fromY = i2;
            this.toX = i3;
            this.toY = i4;
        }
    }

    static class ChangeInfo {
        int fromX;
        int fromY;
        androidx.recyclerview.widget.RecyclerView.ViewHolder newHolder;
        androidx.recyclerview.widget.RecyclerView.ViewHolder oldHolder;
        int toX;
        int toY;

        private ChangeInfo(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder2) {
            this.oldHolder = viewHolder;
            this.newHolder = viewHolder2;
        }

        ChangeInfo(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
            this(viewHolder, viewHolder2);
            this.fromX = i;
            this.fromY = i2;
            this.toX = i3;
            this.toY = i4;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + CoreConstants.CURLY_RIGHT;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public void runPendingAnimations() {
        boolean zIsEmpty = this.mPendingRemovals.isEmpty();
        boolean zIsEmpty2 = this.mPendingMoves.isEmpty();
        boolean zIsEmpty3 = this.mPendingChanges.isEmpty();
        boolean zIsEmpty4 = this.mPendingAdditions.isEmpty();
        if (zIsEmpty && zIsEmpty2 && zIsEmpty4 && zIsEmpty3) {
            return;
        }
        if (!zIsEmpty3) {
            Iterator<ChangeInfo> it = this.mPendingChanges.iterator();
            while (it.hasNext()) {
                animateChangeImpl(it.next());
            }
            this.mPendingChanges.clear();
        }
        if (!zIsEmpty) {
            Iterator<androidx.recyclerview.widget.RecyclerView.ViewHolder> it2 = this.mPendingRemovals.iterator();
            while (it2.hasNext()) {
                animateRemoveImpl(it2.next());
            }
            this.mPendingRemovals.clear();
        }
        if (!zIsEmpty2) {
            Iterator<MoveInfo> it3 = this.mPendingMoves.iterator();
            while (it3.hasNext()) {
                animateMoveImpl(it3.next());
            }
            this.mPendingMoves.clear();
        }
        if (zIsEmpty4) {
            return;
        }
        Iterator<androidx.recyclerview.widget.RecyclerView.ViewHolder> it4 = this.mPendingAdditions.iterator();
        while (it4.hasNext()) {
            animateAddImpl(it4.next());
        }
        this.mPendingAdditions.clear();
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateRemove(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        this.mPendingRemovals.add(viewHolder);
        return true;
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateAdd(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        prepareAdd(viewHolder);
        this.mPendingAdditions.add(viewHolder);
        return true;
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateMove(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4) {
        resetAnimation(viewHolder);
        int i5 = i4 - i2;
        if (i3 - i == 0 && i5 == 0) {
            dispatchMoveFinished(viewHolder);
            return false;
        }
        MoveInfo moveInfo = new MoveInfo(viewHolder, i, i2, i3, i4);
        prepareMove(moveInfo);
        this.mPendingMoves.add(moveInfo);
        return true;
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateChange(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
        if (viewHolder == viewHolder2) {
            return animateMove(viewHolder2, i, i2, i3, i4);
        }
        ChangeInfo changeInfo = new ChangeInfo(viewHolder, viewHolder2, i, i2, i3, i4);
        prepareChange(changeInfo);
        animateChangeImpl(changeInfo);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public void endAnimation(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        cancelAnimate(view);
        int size = this.mPendingMoves.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            if (this.mPendingMoves.get(size).holder == viewHolder) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                dispatchMoveFinished(viewHolder);
                this.mPendingMoves.remove(size);
            }
        }
        endChangeAnimation(this.mPendingChanges, viewHolder);
        if (this.mPendingRemovals.remove(viewHolder)) {
            view.setAlpha(1.0f);
            dispatchRemoveFinished(viewHolder);
        }
        if (this.mPendingAdditions.remove(viewHolder)) {
            view.setAlpha(1.0f);
            dispatchAddFinished(viewHolder);
        }
        for (int size2 = this.mChangesList.size() - 1; size2 >= 0; size2--) {
            ArrayList<ChangeInfo> arrayList = this.mChangesList.get(size2);
            endChangeAnimation(arrayList, viewHolder);
            if (arrayList.isEmpty()) {
                this.mChangesList.remove(size2);
            }
        }
        for (int size3 = this.mMovesList.size() - 1; size3 >= 0; size3--) {
            ArrayList<MoveInfo> arrayList2 = this.mMovesList.get(size3);
            for (int size4 = arrayList2.size() - 1; size4 >= 0; size4--) {
                if (arrayList2.get(size4).holder == viewHolder) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    dispatchMoveFinished(viewHolder);
                    arrayList2.remove(size4);
                    if (!arrayList2.isEmpty()) {
                        break;
                    }
                    this.mMovesList.remove(size3);
                    break;
                }
            }
        }
        for (int size5 = this.mAdditionsList.size() - 1; size5 >= 0; size5--) {
            ArrayList<androidx.recyclerview.widget.RecyclerView.ViewHolder> arrayList3 = this.mAdditionsList.get(size5);
            if (arrayList3.remove(viewHolder)) {
                view.setAlpha(1.0f);
                dispatchAddFinished(viewHolder);
                if (arrayList3.isEmpty()) {
                    this.mAdditionsList.remove(size5);
                }
            }
        }
        this.mRemoveAnimations.remove(viewHolder);
        this.mAddAnimations.remove(viewHolder);
        this.mChangeAnimations.remove(viewHolder);
        this.mMoveAnimations.remove(viewHolder);
        dispatchFinishedWhenDone();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public void endAnimations() {
        int size = this.mPendingMoves.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            MoveInfo moveInfo = this.mPendingMoves.get(size);
            View view = moveInfo.holder.itemView;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            dispatchMoveFinished(moveInfo.holder);
            this.mPendingMoves.remove(size);
        }
        for (int size2 = this.mPendingRemovals.size() - 1; size2 >= 0; size2--) {
            dispatchRemoveFinished(this.mPendingRemovals.get(size2));
            this.mPendingRemovals.remove(size2);
        }
        int size3 = this.mPendingAdditions.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder = this.mPendingAdditions.get(size3);
            viewHolder.itemView.setAlpha(1.0f);
            dispatchAddFinished(viewHolder);
            this.mPendingAdditions.remove(size3);
        }
        for (int size4 = this.mPendingChanges.size() - 1; size4 >= 0; size4--) {
            endChangeAnimationIfNecessary(this.mPendingChanges.get(size4));
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            for (int size5 = this.mMovesList.size() - 1; size5 >= 0; size5--) {
                ArrayList<MoveInfo> arrayList = this.mMovesList.get(size5);
                for (int size6 = arrayList.size() - 1; size6 >= 0; size6--) {
                    MoveInfo moveInfo2 = arrayList.get(size6);
                    View view2 = moveInfo2.holder.itemView;
                    view2.setTranslationY(0.0f);
                    view2.setTranslationX(0.0f);
                    dispatchMoveFinished(moveInfo2.holder);
                    arrayList.remove(size6);
                    if (arrayList.isEmpty()) {
                        this.mMovesList.remove(arrayList);
                    }
                }
            }
            for (int size7 = this.mAdditionsList.size() - 1; size7 >= 0; size7--) {
                ArrayList<androidx.recyclerview.widget.RecyclerView.ViewHolder> arrayList2 = this.mAdditionsList.get(size7);
                for (int size8 = arrayList2.size() - 1; size8 >= 0; size8--) {
                    androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder2 = arrayList2.get(size8);
                    viewHolder2.itemView.setAlpha(1.0f);
                    dispatchAddFinished(viewHolder2);
                    arrayList2.remove(size8);
                    if (arrayList2.isEmpty()) {
                        this.mAdditionsList.remove(arrayList2);
                    }
                }
            }
            for (int size9 = this.mChangesList.size() - 1; size9 >= 0; size9--) {
                ArrayList<ChangeInfo> arrayList3 = this.mChangesList.get(size9);
                for (int size10 = arrayList3.size() - 1; size10 >= 0; size10--) {
                    endChangeAnimationIfNecessary(arrayList3.get(size10));
                    if (arrayList3.isEmpty()) {
                        this.mChangesList.remove(arrayList3);
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    private void endChangeAnimation(List<ChangeInfo> list, androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ChangeInfo changeInfo = list.get(size);
            if (endChangeAnimationIfNecessary(changeInfo, viewHolder) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                list.remove(changeInfo);
            }
        }
    }

    private void cancelAll(List<androidx.recyclerview.widget.RecyclerView.ViewHolder> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            cancelAnimate(list.get(size).itemView);
        }
        list.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public boolean isRunning() {
        return (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty()) ? false : true;
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public boolean animateAppearance(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        if (itemHolderInfo != null && (itemHolderInfo.left != itemHolderInfo2.left || itemHolderInfo.top != itemHolderInfo2.top)) {
            return animateMove(viewHolder, itemHolderInfo.left, itemHolderInfo.top, itemHolderInfo2.left, itemHolderInfo2.top);
        }
        return animateAdd(viewHolder);
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        boolean z = false;
        if (changeInfo.newHolder == viewHolder) {
            changeInfo.newHolder = null;
        } else {
            if (changeInfo.oldHolder != viewHolder) {
                return false;
            }
            changeInfo.oldHolder = null;
            z = true;
        }
        viewHolder.itemView.setAlpha(1.0f);
        viewHolder.itemView.setTranslationX(0.0f);
        viewHolder.itemView.setTranslationY(0.0f);
        dispatchChangeFinished(viewHolder, z);
        return true;
    }

    private void dispatchFinishedWhenDone() {
        if (isRunning()) {
            return;
        }
        dispatchAnimationsFinished();
    }

    void notifyAddStarting(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        this.mAddAnimations.add(viewHolder);
        dispatchAddStarting(viewHolder);
    }

    void notifyAddFinished(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        dispatchAddFinished(viewHolder);
        this.mAddAnimations.remove(viewHolder);
        dispatchFinishedWhenDone();
    }

    void notifyRemoveStarting(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        this.mRemoveAnimations.add(viewHolder);
        dispatchRemoveStarting(viewHolder);
    }

    void notifyRemoveFinished(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        dispatchRemoveFinished(viewHolder);
        this.mRemoveAnimations.remove(viewHolder);
        dispatchFinishedWhenDone();
    }

    void notifyMoveStarting(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        this.mMoveAnimations.add(viewHolder);
        dispatchMoveStarting(viewHolder);
    }

    void notifyMoveFinished(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        dispatchMoveFinished(viewHolder);
        this.mMoveAnimations.remove(viewHolder);
        dispatchFinishedWhenDone();
    }

    void notifyChangeStarting(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, boolean z) {
        this.mChangeAnimations.add(viewHolder);
        dispatchChangeStarting(viewHolder, z);
    }

    void notifyChangeFinished(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, boolean z) {
        dispatchChangeFinished(viewHolder, z);
        this.mChangeAnimations.remove(viewHolder);
        dispatchFinishedWhenDone();
    }

    private void cancelAnimate(View view) {
        Folme.end(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public boolean canReuseUpdatedViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, List<Object> list) {
        return !list.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, list);
    }

    void resetAnimation(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        endAnimation(viewHolder);
        resetAnimation(viewHolder.itemView);
    }

    static void resetAnimation(View view) {
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
        view.setAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
    }
}
