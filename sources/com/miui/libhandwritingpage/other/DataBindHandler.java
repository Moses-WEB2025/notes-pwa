package com.miui.libhandwritingpage.other;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.miui.libhandwritingpage.adapter.PageAdapter;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.view.PageItemView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.ICreationEngine;
import xc.engine.api.PenEngine;
import xc.engine.data.IDataListener;
import xc.engine.operate.edit.select.onSelectStepChangedListener;
import xc.engine.utils.FileUtil;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes3.dex */
public class DataBindHandler implements Handler.Callback {
    private static final String TAG = "DataBindHandler";
    private static final int WHAT_BIND = 2;
    private static final int WHAT_LOAD = 1;
    private static final int WHAT_REDO = 5;
    private static final int WHAT_UNBIND = 3;
    private static final int WHAT_UNBIND_ENGINE = 6;
    private static final int WHAT_UNDO = 4;
    private static final int WHAT_WAIT_IDLE = 7;
    private final PageAdapter adapter;
    private final Handler handler;
    private final HandlerThread thread;

    public DataBindHandler(PageAdapter pageAdapter) {
        this.adapter = pageAdapter;
        HandlerThread handlerThread = new HandlerThread("");
        this.thread = handlerThread;
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper(), this);
    }

    public void quitLooper() {
        try {
            Handler handler = this.handler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                this.handler.getLooper().quitSafely();
            }
            this.thread.quitSafely();
        } catch (Exception unused) {
        }
    }

    public void addLoadTask(PageItemView pageItemView, PageItemData pageItemData) {
        this.handler.obtainMessage(pageItemData.getPageIndex(), 1, -1, new BindMessage(pageItemView, pageItemData)).sendToTarget();
    }

    public void addUnBindEngineDataTask(PageItemView pageItemView, PageItemData pageItemData) {
        pageItemView.setTag(pageItemData);
        Message messageObtainMessage = this.handler.obtainMessage(pageItemData.getPageIndex() + 400, 6, -1, new BindMessage(pageItemView, pageItemData));
        this.handler.removeMessages(pageItemData.getPageIndex() + 300);
        this.handler.removeMessages(pageItemData.getPageIndex() + 100);
        messageObtainMessage.sendToTarget();
    }

    public void addBindTask(PageItemView pageItemView, PageItemData pageItemData) {
        pageItemView.setTag(pageItemData);
        Message messageObtainMessage = this.handler.obtainMessage(pageItemData.getPageIndex() + 100, 2, -1, new BindMessage(pageItemView, pageItemData));
        this.handler.removeMessages(pageItemData.getPageIndex() + 300);
        this.handler.removeMessages(pageItemData.getPageIndex() + 100);
        messageObtainMessage.sendToTarget();
    }

    public void addUnBindTask(PageItemView pageItemView, PageItemData pageItemData) {
        pageItemView.setTag(null);
        Message messageObtainMessage = this.handler.obtainMessage(pageItemData.getPageIndex() + 200, 3, -1, new BindMessage(null, pageItemData));
        this.handler.removeMessages(pageItemData.getPageIndex() + 300);
        this.handler.removeMessages(pageItemData.getPageIndex() + 100);
        messageObtainMessage.sendToTarget();
    }

    public void addUndoTask(PageItemData pageItemData) {
        this.handler.obtainMessage(4, 4, -1, new BindMessage(null, pageItemData)).sendToTarget();
    }

    public void addRedoTask(PageItemData pageItemData) {
        this.handler.obtainMessage(5, 5, -1, new BindMessage(null, pageItemData)).sendToTarget();
    }

    public void addWaitIdleTask(Runnable runnable) {
        this.handler.obtainMessage(7, 7, -1, runnable).sendToTarget();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        switch (message.arg1) {
            case 1:
                doLoad((BindMessage) message.obj);
                return true;
            case 2:
                doBind((BindMessage) message.obj);
                return true;
            case 3:
                doUnBind((BindMessage) message.obj);
                return true;
            case 4:
                doUndo((BindMessage) message.obj);
                return true;
            case 5:
                doRedo((BindMessage) message.obj);
                return true;
            case 6:
                doUnBindEngineData((BindMessage) message.obj);
                return true;
            case 7:
                doWaitIdleTask((Runnable) message.obj);
                return true;
            default:
                return false;
        }
    }

    private void doUnBindEngineData(BindMessage bindMessage) {
        synchronized (bindMessage.itemData.lock) {
            if (bindMessage.itemData.hadLoad && !bindMessage.itemData.serialized) {
                bindMessage.itemData.engineData = bindMessage.itemView.unBindEngineData();
                bindMessage.itemData.serialized = false;
                doUnBind(bindMessage);
            }
        }
    }

    private void doLoad(BindMessage bindMessage) {
        if (!bindMessage.itemData.hadLoad) {
            bindMessage.itemData.hadLoad = false;
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            bindMessage.itemView.load(bindMessage.itemData.filePath, bindMessage.itemData, bindMessage.itemView, new IDataListener() { // from class: com.miui.libhandwritingpage.other.DataBindHandler$$ExternalSyntheticLambda0
                @Override // xc.engine.data.IDataListener
                public final void onDataLoaded(boolean z) {
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bindMessage.itemData.hadLoad = true;
            bindMessage.itemData.engineData = bindMessage.itemView.unBindEngineData();
            bindMessage.itemData.serialized = false;
            this.adapter.onLoadFinish(bindMessage.itemView, bindMessage.itemData);
            return;
        }
        doBind(bindMessage);
    }

    private void doBind(BindMessage bindMessage) {
        synchronized (bindMessage.itemData.lock) {
            bind(bindMessage.itemData);
        }
        this.adapter.onTaskFinish(bindMessage.itemView, bindMessage.itemData, bindMessage.itemView.getTag() == bindMessage.itemData && bindMessage.itemData.hadLoad);
    }

    private void doUnBind(BindMessage bindMessage) {
        synchronized (bindMessage.itemData.lock) {
            unBind(bindMessage.itemData);
        }
        if (bindMessage == null || bindMessage.itemView == null) {
            return;
        }
        bindMessage.itemView.unBind();
    }

    private void doUndo(BindMessage bindMessage) {
        try {
            undo(bindMessage);
        } catch (Exception unused) {
        }
        this.adapter.onUndoFinish();
    }

    private void undo(BindMessage bindMessage) {
        synchronized (bindMessage.itemData.lock) {
            bind(bindMessage.itemData);
        }
        final CompletableFuture completableFuture = new CompletableFuture();
        ICreationEngine iCreationEngineCreateEngine = PenEngine.createEngine(CreationPageSDK.sApplication, null, null);
        try {
            iCreationEngineCreateEngine.getCanvasOperator().setVisibleSize(1000.0f, 1000.0f);
            iCreationEngineCreateEngine.resetCache();
            iCreationEngineCreateEngine.getSelectOperator().setOnSelectStepChangedListener(new onSelectStepChangedListener() { // from class: com.miui.libhandwritingpage.other.DataBindHandler.1
                @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
                public void onStepChanged(int i, int i2, boolean z) {
                    if (i == 1) {
                        completableFuture.complete(null);
                    }
                }

                @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
                public void onPageSizeChanged(PageSizeConfig pageSizeConfig) {
                    Log.d(DataBindHandler.TAG, "onPageSizeChanged  " + pageSizeConfig);
                    DataBindHandler.this.adapter.onPageSizeChanged(pageSizeConfig);
                }
            });
            iCreationEngineCreateEngine.getDataAccess().loadPathInfo(bindMessage.itemData.engineData, bindMessage.itemData.redoList, bindMessage.itemData.undoList, bindMessage.itemData.getPageIndex());
            iCreationEngineCreateEngine.getSelectOperator().cancelSelect(false);
            iCreationEngineCreateEngine.getStepManager().undo();
            try {
                completableFuture.get(3L, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            iCreationEngineCreateEngine.getSelectOperator().setOnSelectStepChangedListener(null);
            bindMessage.itemData.engineData.resetData(iCreationEngineCreateEngine.getDataAccess().getCurrentPathInfo());
            bindMessage.itemData.resetStep(iCreationEngineCreateEngine.getDataAccess().getRedoList(), iCreationEngineCreateEngine.getDataAccess().getUndoList());
            synchronized (bindMessage.itemData.lock) {
                unBind(bindMessage.itemData);
            }
            iCreationEngineCreateEngine.release();
        } catch (Throwable th) {
            iCreationEngineCreateEngine.release();
            throw th;
        }
    }

    private void doRedo(BindMessage bindMessage) {
        try {
            redo(bindMessage);
        } catch (Exception unused) {
        }
        this.adapter.onRedoFinish();
    }

    private void redo(BindMessage bindMessage) {
        synchronized (bindMessage.itemData.lock) {
            bind(bindMessage.itemData);
        }
        final CompletableFuture completableFuture = new CompletableFuture();
        ICreationEngine iCreationEngineCreateEngine = PenEngine.createEngine(CreationPageSDK.sApplication, null, null);
        try {
            iCreationEngineCreateEngine.getCanvasOperator().setVisibleSize(1000.0f, 1000.0f);
            iCreationEngineCreateEngine.resetCache();
            iCreationEngineCreateEngine.getSelectOperator().setOnSelectStepChangedListener(new onSelectStepChangedListener() { // from class: com.miui.libhandwritingpage.other.DataBindHandler.2
                @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
                public void onStepChanged(int i, int i2, boolean z) {
                    if (i == 1) {
                        completableFuture.complete(null);
                    }
                }

                @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
                public void onPageSizeChanged(PageSizeConfig pageSizeConfig) {
                    Log.d(DataBindHandler.TAG, "redo onPageSizeChanged width " + pageSizeConfig);
                    DataBindHandler.this.adapter.onPageSizeChanged(pageSizeConfig);
                }
            });
            iCreationEngineCreateEngine.getDataAccess().loadPathInfo(bindMessage.itemData.engineData, bindMessage.itemData.redoList, bindMessage.itemData.undoList);
            iCreationEngineCreateEngine.getStepManager().redo();
            try {
                completableFuture.get(3L, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            iCreationEngineCreateEngine.getSelectOperator().setOnSelectStepChangedListener(null);
            bindMessage.itemData.engineData.resetData(iCreationEngineCreateEngine.getDataAccess().getCurrentPathInfo());
            bindMessage.itemData.resetStep(iCreationEngineCreateEngine.getDataAccess().getRedoList(), iCreationEngineCreateEngine.getDataAccess().getUndoList());
            synchronized (bindMessage.itemData.lock) {
                unBind(bindMessage.itemData);
            }
            iCreationEngineCreateEngine.release();
        } catch (Throwable th) {
            iCreationEngineCreateEngine.release();
            throw th;
        }
    }

    private void doWaitIdleTask(Runnable runnable) {
        runnable.run();
        Log.i(TAG, "doWaitIdleTask: finish !");
    }

    public static class BindMessage {
        PageItemData itemData;
        PageItemView itemView;

        private BindMessage(PageItemView pageItemView, PageItemData pageItemData) {
            this.itemView = pageItemView;
            this.itemData = pageItemData;
        }
    }

    public static void bind(PageItemData pageItemData) throws Throwable {
        if (!pageItemData.serialized) {
            return;
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                if (pageItemData.unbindDataPath == null) {
                    pageItemData.unbindDataPath = FileUtil.getUnbindDataPath();
                }
                FileInputStream fileInputStream2 = new FileInputStream(pageItemData.unbindDataPath);
                try {
                    pageItemData.unSerialize(pageItemData.engineData.unSerialize(new DataInputStream(fileInputStream2), pageItemData.getPageIndex()));
                    pageItemData.serialized = false;
                    closeInputStream(fileInputStream2);
                } catch (Exception e) {
                    e = e;
                    fileInputStream = fileInputStream2;
                    e.printStackTrace();
                    closeInputStream(fileInputStream);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    closeInputStream(fileInputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void closeInputStream(FileInputStream fileInputStream) {
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void unBind(PageItemData pageItemData) throws Throwable {
        if (pageItemData.serialized) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            if (pageItemData.unbindDataPath == null) {
                pageItemData.unbindDataPath = FileUtil.getUnbindDataPath();
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(pageItemData.unbindDataPath);
            try {
                pageItemData.engineData.serialize(new DataOutputStream(fileOutputStream2), pageItemData.getPageIndex());
                pageItemData.serialize();
                pageItemData.serialized = true;
                closeOutputStream(fileOutputStream2);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                try {
                    closeOutputStream(fileOutputStream);
                    throw th;
                } catch (Exception e) {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        closeOutputStream(fileOutputStream);
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void closeOutputStream(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
