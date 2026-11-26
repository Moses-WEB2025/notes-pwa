package com.xiaomi.mirror.synergy;

import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.mirror.ICallRelayListener;
import com.xiaomi.mirror.ICallRelayService;

/* JADX INFO: loaded from: classes4.dex */
public class CallRelayService implements IBinder.DeathRecipient {
    public static final int CALL_STATE_IDLE = 0;
    public static final int CALL_STATE_IN_USE = 1;
    public final Object mLock = new Object();
    public ICallRelayService mService;
    public RelayListenerWrapper mWrapper;

    public static class RelayListenerWrapper extends ICallRelayListener.Stub {
        public CallRelayListener mListener;

        public RelayListenerWrapper() {
        }

        @Override // com.xiaomi.mirror.ICallRelayListener
        public void onAddressUpdate(String str) {
            CallRelayListener callRelayListener = this.mListener;
            if (callRelayListener != null) {
                callRelayListener.onAddressUpdate(str);
            }
        }

        @Override // com.xiaomi.mirror.ICallRelayListener
        public void onMessage(String str) {
            CallRelayListener callRelayListener = this.mListener;
            if (callRelayListener != null) {
                callRelayListener.onMessage(str);
            }
        }
    }

    public CallRelayService(IBinder iBinder) {
        ICallRelayService iCallRelayServiceAsInterface = ICallRelayService.Stub.asInterface(iBinder);
        this.mService = iCallRelayServiceAsInterface;
        if (iCallRelayServiceAsInterface == null) {
            return;
        }
        try {
            iCallRelayServiceAsInterface.asBinder().linkToDeath(this, 0);
        } catch (RemoteException unused) {
            this.mService = null;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mLock) {
            RelayListenerWrapper relayListenerWrapper = this.mWrapper;
            if (relayListenerWrapper != null && relayListenerWrapper.mListener != null) {
                this.mWrapper.mListener.onLost();
            }
            this.mService = null;
        }
    }

    public void release() {
        synchronized (this.mLock) {
            ICallRelayService iCallRelayService = this.mService;
            if (iCallRelayService != null) {
                try {
                    iCallRelayService.release();
                    this.mService = null;
                } catch (RemoteException unused) {
                }
            }
            this.mWrapper = null;
        }
    }

    public void sendRelayMessage(String str) {
        synchronized (this.mLock) {
            ICallRelayService iCallRelayService = this.mService;
            if (iCallRelayService != null) {
                try {
                    iCallRelayService.sendRelayMessage(str);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public void setCallState(int i) {
        synchronized (this.mLock) {
            ICallRelayService iCallRelayService = this.mService;
            if (iCallRelayService != null) {
                try {
                    iCallRelayService.setCallState(i);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public void setRelayListener(CallRelayListener callRelayListener) {
        synchronized (this.mLock) {
            if (this.mWrapper == null) {
                this.mWrapper = new RelayListenerWrapper();
            }
            this.mWrapper.mListener = callRelayListener;
            ICallRelayService iCallRelayService = this.mService;
            if (iCallRelayService != null) {
                try {
                    iCallRelayService.registerCallRelayListener(this.mWrapper);
                } catch (RemoteException unused) {
                }
            } else {
                binderDied();
            }
        }
    }
}
