package com.android.soundrecorder.ai.base;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.soundrecorder.ai.base.config.RecordConfig;

/* JADX INFO: loaded from: classes.dex */
public interface IAIRecordService extends IInterface {

    public static class Default implements IAIRecordService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public int getCurrentAmplitude() throws RemoteException {
            return 0;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public String getCurrentCallerPackageName() throws RemoteException {
            return null;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public String getCurrentFileSha1() throws RemoteException {
            return null;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public long getCurrentRecordDuration() throws RemoteException {
            return 0L;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public String getCurrentRecordFilePath() throws RemoteException {
            return null;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public int getMaxAmplitude() throws RemoteException {
            return 0;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public String getRecognizeLanguage() throws RemoteException {
            return null;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public int getState() throws RemoteException {
            return 0;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void initAIRecognizeEngine(IAIRecognizeCallback iAIRecognizeCallback) throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public boolean isAIAbilityEnable() throws RemoteException {
            return false;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public boolean isAIRecognizeAgreeCTA() throws RemoteException {
            return false;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void pauseRecording() throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void registerClient(IAIRecorderClientStub iAIRecorderClientStub) throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void registerRecorderCallback(String str, IAIRecorderServiceCallback iAIRecorderServiceCallback) throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void releaseAIRecognizeEngine() throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void reset() throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void resumeRecording() throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void setRecordConfig(RecordConfig recordConfig) throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public boolean startAIRecognize(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public int startRecording() throws RemoteException {
            return 0;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public boolean stopAIRecognize() throws RemoteException {
            return false;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void stopRecording() throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecordService
        public void unregisterRecorderCallback(String str, IAIRecorderServiceCallback iAIRecorderServiceCallback) throws RemoteException {
        }
    }

    int getCurrentAmplitude() throws RemoteException;

    String getCurrentCallerPackageName() throws RemoteException;

    String getCurrentFileSha1() throws RemoteException;

    long getCurrentRecordDuration() throws RemoteException;

    String getCurrentRecordFilePath() throws RemoteException;

    int getMaxAmplitude() throws RemoteException;

    String getRecognizeLanguage() throws RemoteException;

    int getState() throws RemoteException;

    void initAIRecognizeEngine(IAIRecognizeCallback iAIRecognizeCallback) throws RemoteException;

    boolean isAIAbilityEnable() throws RemoteException;

    boolean isAIRecognizeAgreeCTA() throws RemoteException;

    void pauseRecording() throws RemoteException;

    void registerClient(IAIRecorderClientStub iAIRecorderClientStub) throws RemoteException;

    void registerRecorderCallback(String str, IAIRecorderServiceCallback iAIRecorderServiceCallback) throws RemoteException;

    void releaseAIRecognizeEngine() throws RemoteException;

    void reset() throws RemoteException;

    void resumeRecording() throws RemoteException;

    void setRecordConfig(RecordConfig recordConfig) throws RemoteException;

    boolean startAIRecognize(String str) throws RemoteException;

    int startRecording() throws RemoteException;

    boolean stopAIRecognize() throws RemoteException;

    void stopRecording() throws RemoteException;

    void unregisterRecorderCallback(String str, IAIRecorderServiceCallback iAIRecorderServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAIRecordService {
        private static final String DESCRIPTOR = "com.android.soundrecorder.ai.base.IAIRecordService";
        static final int TRANSACTION_getCurrentAmplitude = 11;
        static final int TRANSACTION_getCurrentCallerPackageName = 9;
        static final int TRANSACTION_getCurrentFileSha1 = 23;
        static final int TRANSACTION_getCurrentRecordDuration = 21;
        static final int TRANSACTION_getCurrentRecordFilePath = 22;
        static final int TRANSACTION_getMaxAmplitude = 10;
        static final int TRANSACTION_getRecognizeLanguage = 18;
        static final int TRANSACTION_getState = 8;
        static final int TRANSACTION_initAIRecognizeEngine = 15;
        static final int TRANSACTION_isAIAbilityEnable = 14;
        static final int TRANSACTION_isAIRecognizeAgreeCTA = 19;
        static final int TRANSACTION_pauseRecording = 5;
        static final int TRANSACTION_registerClient = 1;
        static final int TRANSACTION_registerRecorderCallback = 12;
        static final int TRANSACTION_releaseAIRecognizeEngine = 16;
        static final int TRANSACTION_reset = 7;
        static final int TRANSACTION_resumeRecording = 6;
        static final int TRANSACTION_setRecordConfig = 2;
        static final int TRANSACTION_startAIRecognize = 17;
        static final int TRANSACTION_startRecording = 3;
        static final int TRANSACTION_stopAIRecognize = 20;
        static final int TRANSACTION_stopRecording = 4;
        static final int TRANSACTION_unregisterRecorderCallback = 13;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAIRecordService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAIRecordService)) {
                return (IAIRecordService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerClient(IAIRecorderClientStub.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    setRecordConfig(parcel.readInt() != 0 ? RecordConfig.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iStartRecording = startRecording();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartRecording);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    stopRecording();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    pauseRecording();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    resumeRecording();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    reset();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    int state = getState();
                    parcel2.writeNoException();
                    parcel2.writeInt(state);
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    String currentCallerPackageName = getCurrentCallerPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(currentCallerPackageName);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    int maxAmplitude = getMaxAmplitude();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxAmplitude);
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    int currentAmplitude = getCurrentAmplitude();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentAmplitude);
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerRecorderCallback(parcel.readString(), IAIRecorderServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterRecorderCallback(parcel.readString(), IAIRecorderServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsAIAbilityEnable = isAIAbilityEnable();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsAIAbilityEnable ? 1 : 0);
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    initAIRecognizeEngine(IAIRecognizeCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    releaseAIRecognizeEngine();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zStartAIRecognize = startAIRecognize(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zStartAIRecognize ? 1 : 0);
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    String recognizeLanguage = getRecognizeLanguage();
                    parcel2.writeNoException();
                    parcel2.writeString(recognizeLanguage);
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsAIRecognizeAgreeCTA = isAIRecognizeAgreeCTA();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsAIRecognizeAgreeCTA ? 1 : 0);
                    return true;
                case 20:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zStopAIRecognize = stopAIRecognize();
                    parcel2.writeNoException();
                    parcel2.writeInt(zStopAIRecognize ? 1 : 0);
                    return true;
                case 21:
                    parcel.enforceInterface(DESCRIPTOR);
                    long currentRecordDuration = getCurrentRecordDuration();
                    parcel2.writeNoException();
                    parcel2.writeLong(currentRecordDuration);
                    return true;
                case 22:
                    parcel.enforceInterface(DESCRIPTOR);
                    String currentRecordFilePath = getCurrentRecordFilePath();
                    parcel2.writeNoException();
                    parcel2.writeString(currentRecordFilePath);
                    return true;
                case 23:
                    parcel.enforceInterface(DESCRIPTOR);
                    String currentFileSha1 = getCurrentFileSha1();
                    parcel2.writeNoException();
                    parcel2.writeString(currentFileSha1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAIRecordService {
            public static IAIRecordService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void registerClient(IAIRecorderClientStub iAIRecorderClientStub) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iAIRecorderClientStub != null ? iAIRecorderClientStub.asBinder() : null);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerClient(iAIRecorderClientStub);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void setRecordConfig(RecordConfig recordConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (recordConfig != null) {
                        parcelObtain.writeInt(1);
                        recordConfig.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRecordConfig(recordConfig);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public int startRecording() throws RemoteException {
                int iStartRecording;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iStartRecording = Stub.getDefaultImpl().startRecording();
                    } else {
                        parcelObtain2.readException();
                        iStartRecording = parcelObtain2.readInt();
                    }
                    return iStartRecording;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void stopRecording() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopRecording();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void pauseRecording() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pauseRecording();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void resumeRecording() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resumeRecording();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void reset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reset();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public int getState() throws RemoteException {
                int state;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        state = Stub.getDefaultImpl().getState();
                    } else {
                        parcelObtain2.readException();
                        state = parcelObtain2.readInt();
                    }
                    return state;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public String getCurrentCallerPackageName() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getCurrentCallerPackageName();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public int getMaxAmplitude() throws RemoteException {
                int maxAmplitude;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        maxAmplitude = Stub.getDefaultImpl().getMaxAmplitude();
                    } else {
                        parcelObtain2.readException();
                        maxAmplitude = parcelObtain2.readInt();
                    }
                    return maxAmplitude;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public int getCurrentAmplitude() throws RemoteException {
                int currentAmplitude;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        currentAmplitude = Stub.getDefaultImpl().getCurrentAmplitude();
                    } else {
                        parcelObtain2.readException();
                        currentAmplitude = parcelObtain2.readInt();
                    }
                    return currentAmplitude;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void registerRecorderCallback(String str, IAIRecorderServiceCallback iAIRecorderServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iAIRecorderServiceCallback != null ? iAIRecorderServiceCallback.asBinder() : null);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerRecorderCallback(str, iAIRecorderServiceCallback);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void unregisterRecorderCallback(String str, IAIRecorderServiceCallback iAIRecorderServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iAIRecorderServiceCallback != null ? iAIRecorderServiceCallback.asBinder() : null);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterRecorderCallback(str, iAIRecorderServiceCallback);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public boolean isAIAbilityEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAIAbilityEnable();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void initAIRecognizeEngine(IAIRecognizeCallback iAIRecognizeCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iAIRecognizeCallback != null ? iAIRecognizeCallback.asBinder() : null);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().initAIRecognizeEngine(iAIRecognizeCallback);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public void releaseAIRecognizeEngine() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseAIRecognizeEngine();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public boolean startAIRecognize(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startAIRecognize(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public String getRecognizeLanguage() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getRecognizeLanguage();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public boolean isAIRecognizeAgreeCTA() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAIRecognizeAgreeCTA();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public boolean stopAIRecognize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopAIRecognize();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public long getCurrentRecordDuration() throws RemoteException {
                long currentRecordDuration;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(21, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        currentRecordDuration = Stub.getDefaultImpl().getCurrentRecordDuration();
                    } else {
                        parcelObtain2.readException();
                        currentRecordDuration = parcelObtain2.readLong();
                    }
                    return currentRecordDuration;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public String getCurrentRecordFilePath() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getCurrentRecordFilePath();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecordService
            public String getCurrentFileSha1() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getCurrentFileSha1();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAIRecordService iAIRecordService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iAIRecordService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAIRecordService;
            return true;
        }

        public static IAIRecordService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
