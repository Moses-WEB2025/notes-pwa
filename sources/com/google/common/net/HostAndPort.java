package com.google.common.net;

import ch.qos.logback.core.CoreConstants;
import com.google.common.base.CharMatcher;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import javax.annotation.CheckForNull;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
@ElementTypesAreNonnullByDefault
public final class HostAndPort implements Serializable {
    private static final int NO_PORT = -1;
    private static final long serialVersionUID = 0;
    private final boolean hasBracketlessColons;
    private final String host;
    private final int port;

    private static boolean isValidPort(int port) {
        return port >= 0 && port <= 65535;
    }

    private HostAndPort(String host, int port, boolean hasBracketlessColons) {
        this.host = host;
        this.port = port;
        this.hasBracketlessColons = hasBracketlessColons;
    }

    public String getHost() {
        return this.host;
    }

    public boolean hasPort() {
        return this.port >= 0;
    }

    public int getPort() {
        Preconditions.checkState(hasPort());
        return this.port;
    }

    public int getPortOrDefault(int defaultPort) {
        return hasPort() ? this.port : defaultPort;
    }

    public static HostAndPort fromParts(String host, int port) {
        Preconditions.checkArgument(isValidPort(port), "Port out of range: %s", port);
        HostAndPort hostAndPortFromString = fromString(host);
        Preconditions.checkArgument(!hostAndPortFromString.hasPort(), "Host has a port: %s", host);
        return new HostAndPort(hostAndPortFromString.host, port, hostAndPortFromString.hasBracketlessColons);
    }

    public static HostAndPort fromHost(String host) {
        HostAndPort hostAndPortFromString = fromString(host);
        Preconditions.checkArgument(!hostAndPortFromString.hasPort(), "Host has a port: %s", host);
        return hostAndPortFromString;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0042  */
    /* JADX WARN: Code duplicated, block: B:19:0x004a  */
    /* JADX WARN: Code duplicated, block: B:22:0x0055  */
    public static HostAndPort fromString(String hostPortString) {
        boolean z;
        String strSubstring;
        String str;
        String strSubstring2;
        Preconditions.checkNotNull(hostPortString);
        int i = -1;
        if (hostPortString.startsWith("[")) {
            String[] hostAndPortFromBracketedHost = getHostAndPortFromBracketedHost(hostPortString);
            strSubstring2 = hostAndPortFromBracketedHost[0];
            strSubstring = hostAndPortFromBracketedHost[1];
        } else {
            int iIndexOf = hostPortString.indexOf(58);
            if (iIndexOf >= 0) {
                int i2 = iIndexOf + 1;
                if (hostPortString.indexOf(58, i2) == -1) {
                    strSubstring2 = hostPortString.substring(0, iIndexOf);
                    strSubstring = hostPortString.substring(i2);
                }
                if (!Strings.isNullOrEmpty(strSubstring)) {
                    Preconditions.checkArgument(strSubstring.startsWith(Marker.ANY_NON_NULL_MARKER) && CharMatcher.ascii().matchesAllOf(strSubstring), "Unparseable port number: %s", hostPortString);
                    try {
                        i = Integer.parseInt(strSubstring);
                        Preconditions.checkArgument(isValidPort(i), "Port number out of range: %s", hostPortString);
                    } catch (NumberFormatException unused) {
                        throw new IllegalArgumentException("Unparseable port number: " + hostPortString);
                    }
                }
                return new HostAndPort(str, i, z);
            }
            z = iIndexOf >= 0;
            strSubstring = null;
            str = hostPortString;
            if (!Strings.isNullOrEmpty(strSubstring)) {
                Preconditions.checkArgument(strSubstring.startsWith(Marker.ANY_NON_NULL_MARKER) && CharMatcher.ascii().matchesAllOf(strSubstring), "Unparseable port number: %s", hostPortString);
                i = Integer.parseInt(strSubstring);
                Preconditions.checkArgument(isValidPort(i), "Port number out of range: %s", hostPortString);
            }
            return new HostAndPort(str, i, z);
        }
        str = strSubstring2;
        z = false;
        if (!Strings.isNullOrEmpty(strSubstring)) {
            Preconditions.checkArgument(strSubstring.startsWith(Marker.ANY_NON_NULL_MARKER) && CharMatcher.ascii().matchesAllOf(strSubstring), "Unparseable port number: %s", hostPortString);
            i = Integer.parseInt(strSubstring);
            Preconditions.checkArgument(isValidPort(i), "Port number out of range: %s", hostPortString);
        }
        return new HostAndPort(str, i, z);
    }

    private static String[] getHostAndPortFromBracketedHost(String hostPortString) {
        Preconditions.checkArgument(hostPortString.charAt(0) == '[', "Bracketed host-port string must start with a bracket: %s", hostPortString);
        int iIndexOf = hostPortString.indexOf(58);
        int iLastIndexOf = hostPortString.lastIndexOf(93);
        Preconditions.checkArgument(iIndexOf > -1 && iLastIndexOf > iIndexOf, "Invalid bracketed host/port: %s", hostPortString);
        String strSubstring = hostPortString.substring(1, iLastIndexOf);
        int i = iLastIndexOf + 1;
        if (i == hostPortString.length()) {
            return new String[]{strSubstring, ""};
        }
        Preconditions.checkArgument(hostPortString.charAt(i) == ':', "Only a colon may follow a close bracket: %s", hostPortString);
        int i2 = iLastIndexOf + 2;
        for (int i3 = i2; i3 < hostPortString.length(); i3++) {
            Preconditions.checkArgument(Character.isDigit(hostPortString.charAt(i3)), "Port must be numeric: %s", hostPortString);
        }
        return new String[]{strSubstring, hostPortString.substring(i2)};
    }

    public HostAndPort withDefaultPort(int defaultPort) {
        Preconditions.checkArgument(isValidPort(defaultPort));
        return hasPort() ? this : new HostAndPort(this.host, defaultPort, this.hasBracketlessColons);
    }

    public HostAndPort requireBracketsForIPv6() {
        Preconditions.checkArgument(!this.hasBracketlessColons, "Possible bracketless IPv6 literal: %s", this.host);
        return this;
    }

    public boolean equals(@CheckForNull Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof HostAndPort) {
            HostAndPort hostAndPort = (HostAndPort) other;
            if (Objects.equal(this.host, hostAndPort.host) && this.port == hostAndPort.port) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.host, Integer.valueOf(this.port));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.host.length() + 8);
        if (this.host.indexOf(58) >= 0) {
            sb.append('[').append(this.host).append(']');
        } else {
            sb.append(this.host);
        }
        if (hasPort()) {
            sb.append(CoreConstants.COLON_CHAR).append(this.port);
        }
        return sb.toString();
    }
}
