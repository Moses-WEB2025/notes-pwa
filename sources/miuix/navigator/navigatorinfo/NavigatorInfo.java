package miuix.navigator.navigatorinfo;

import miuix.navigator.Navigator;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public abstract class NavigatorInfo {
    public static final int NO_ID = -1;
    private final int mId;

    public abstract boolean onNavigate(Navigator navigator);

    public boolean shouldCloseOverlay() {
        return false;
    }

    public NavigatorInfo() {
        this(-1);
    }

    public NavigatorInfo(int i) {
        this.mId = i;
    }

    public int getNavigationId() {
        return this.mId;
    }

    public boolean isStable() {
        return getNavigationId() >= 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof NavigatorInfo) && isStable()) {
            NavigatorInfo navigatorInfo = (NavigatorInfo) obj;
            if (navigatorInfo.isStable() && navigatorInfo.getNavigationId() == getNavigationId()) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return getClass().getSimpleName() + "{" + getNavigationId() + StringSubstitutor.DEFAULT_VAR_END;
    }

    public int hashCode() {
        return Integer.hashCode(getNavigationId());
    }
}
