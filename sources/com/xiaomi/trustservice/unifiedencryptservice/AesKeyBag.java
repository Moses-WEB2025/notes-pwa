package com.xiaomi.trustservice.unifiedencryptservice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class AesKeyBag implements Parcelable {
    public static final Parcelable.Creator<AesKeyBag> CREATOR = new Parcelable.Creator<AesKeyBag>() { // from class: com.xiaomi.trustservice.unifiedencryptservice.AesKeyBag.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AesKeyBag createFromParcel(Parcel parcel) {
            AesKeyBag aesKeyBag = new AesKeyBag();
            aesKeyBag.readFromParcel(parcel);
            return aesKeyBag;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AesKeyBag[] newArray(int i) {
            return new AesKeyBag[i];
        }
    };
    public String alias;
    public String blockModes;
    public byte[] iv;
    public byte[] key;
    public String paddings;
    public int purposes = 0;
    public int securityLevel = 0;
    public int operationLevel = 0;
    public boolean isEncrypted = false;
    public int keyLength = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.alias);
        parcel.writeInt(this.purposes);
        parcel.writeString(this.paddings);
        parcel.writeString(this.blockModes);
        parcel.writeInt(this.securityLevel);
        parcel.writeInt(this.operationLevel);
        parcel.writeInt(this.isEncrypted ? 1 : 0);
        parcel.writeByteArray(this.iv);
        parcel.writeInt(this.keyLength);
        parcel.writeByteArray(this.key);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.alias = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.purposes = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.paddings = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.blockModes = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.securityLevel = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.operationLevel = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.isEncrypted = parcel.readInt() != 0;
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.iv = parcel.createByteArray();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.keyLength = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.key = parcel.createByteArray();
                                                    if (iDataPosition > Integer.MAX_VALUE - i) {
                                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                                    }
                                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
