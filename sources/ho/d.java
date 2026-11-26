package ho;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public final class d implements Parcelable {
    public static final Parcelable.Creator<d> CREATOR = new a();
    public int a;
    public int b;
    public String c;
    public String d;

    public class a implements Parcelable.Creator<d> {
        @Override // android.os.Parcelable.Creator
        public final d createFromParcel(Parcel parcel) {
            return new d(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final d[] newArray(int i) {
            return new d[i];
        }
    }

    public d(int i, int i2, String str, String str2) {
        this.a = i;
        this.b = i2;
        this.c = str;
        this.d = str2;
    }

    public d(Parcel parcel) {
        a(parcel);
    }

    public final void a(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.d = parcel.readString();
        this.c = parcel.readString();
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && d.class == obj.getClass()) {
            d dVar = (d) obj;
            if (this.a == dVar.a && this.b == dVar.b && this.c.equals(dVar.c) && this.d.equals(dVar.d)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.a), Integer.valueOf(this.b), this.c, this.d);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("HandoffSession{activityInstanceId='");
        sb.append(this.a).append("', activityTaskId='");
        sb.append(this.b).append("', packageName='");
        sb.append(this.d).append("', activityFullName='");
        sb.append(this.c).append("'}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeString(this.d);
        parcel.writeString(this.c);
    }
}
