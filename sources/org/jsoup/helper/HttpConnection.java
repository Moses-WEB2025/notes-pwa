package org.jsoup.helper;

import com.miui.maml.elements.MusicLyricParser;
import com.xiaomi.aicr.paintservice.PaintErrorCode;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.net.ssl.SSLSocketFactory;
import kotlin.text.Typography;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Progress;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.internal.ControllableInputStream;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.StreamParser;

/* JADX INFO: loaded from: classes6.dex */
public class HttpConnection implements Connection {
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DEFAULT_UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36";
    static final String DefaultUploadType = "application/octet-stream";
    public static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final int HTTP_TEMP_REDIR = 307;
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String USER_AGENT = "User-Agent";
    Object client;
    RequestAuthenticator lastAuth;
    private Request req;
    private Connection.Response res;

    public static Connection connect(String str) {
        HttpConnection httpConnection = new HttpConnection();
        httpConnection.url(str);
        return httpConnection;
    }

    public static Connection connect(URL url) {
        HttpConnection httpConnection = new HttpConnection();
        httpConnection.url(url);
        return httpConnection;
    }

    public HttpConnection() {
        Request request = new Request();
        this.req = request;
        request.connection = this;
    }

    HttpConnection(Request request) {
        this.req = new Request(request);
    }

    static String encodeMimeName(String str) {
        return str.replace("\"", "%22");
    }

    @Override // org.jsoup.Connection
    public Connection newRequest() {
        return new HttpConnection(this.req);
    }

    private HttpConnection(Request request, Response response) {
        this.req = request;
        this.res = response;
    }

    @Override // org.jsoup.Connection
    public Connection url(URL url) {
        this.req.url(url);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection url(String str) {
        Validate.notEmptyParam(str, "url");
        try {
            this.req.url(new URL(str));
            return this;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("The supplied URL, '%s', is malformed. Make sure it is an absolute URL, and starts with 'http://' or 'https://'. See https://jsoup.org/cookbook/extracting-data/working-with-urls", str), e);
        }
    }

    @Override // org.jsoup.Connection
    public Connection proxy(Proxy proxy) {
        this.req.proxy(proxy);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection proxy(String str, int i) {
        this.req.proxy(str, i);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection userAgent(String str) {
        Validate.notNullParam(str, "userAgent");
        this.req.header("User-Agent", str);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection timeout(int i) {
        this.req.timeout(i);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection maxBodySize(int i) {
        this.req.maxBodySize(i);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection followRedirects(boolean z) {
        this.req.followRedirects(z);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection referrer(String str) {
        Validate.notNullParam(str, "referrer");
        this.req.header("Referer", str);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection method(Connection.Method method) {
        this.req.method(method);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection ignoreHttpErrors(boolean z) {
        this.req.ignoreHttpErrors(z);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection ignoreContentType(boolean z) {
        this.req.ignoreContentType(z);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection data(String str, String str2) {
        this.req.data((Connection.KeyVal) KeyVal.create(str, str2));
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.req.sslSocketFactory(sSLSocketFactory);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection data(String str, String str2, InputStream inputStream) {
        this.req.data((Connection.KeyVal) KeyVal.create(str, str2, inputStream));
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection data(String str, String str2, InputStream inputStream, String str3) {
        this.req.data(KeyVal.create(str, str2, inputStream).contentType(str3));
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection data(Map<String, String> map) {
        Validate.notNullParam(map, "data");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.req.data((Connection.KeyVal) KeyVal.create(entry.getKey(), entry.getValue()));
        }
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection data(String... strArr) {
        Validate.notNullParam(strArr, "keyvals");
        Validate.isTrue(strArr.length % 2 == 0, "Must supply an even number of key value pairs");
        for (int i = 0; i < strArr.length; i += 2) {
            String str = strArr[i];
            String str2 = strArr[i + 1];
            Validate.notEmpty(str, "Data key must not be empty");
            Validate.notNull(str2, "Data value must not be null");
            this.req.data((Connection.KeyVal) KeyVal.create(str, str2));
        }
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection data(Collection<Connection.KeyVal> collection) {
        Validate.notNullParam(collection, "data");
        Iterator<Connection.KeyVal> it = collection.iterator();
        while (it.hasNext()) {
            this.req.data(it.next());
        }
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection.KeyVal data(String str) {
        Validate.notEmptyParam(str, "key");
        for (Connection.KeyVal keyVal : request().data()) {
            if (keyVal.key().equals(str)) {
                return keyVal;
            }
        }
        return null;
    }

    @Override // org.jsoup.Connection
    public Connection requestBody(String str) {
        this.req.requestBody(str);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection requestBodyStream(InputStream inputStream) {
        this.req.requestBodyStream(inputStream);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection header(String str, String str2) {
        this.req.header(str, str2);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection headers(Map<String, String> map) {
        Validate.notNullParam(map, "headers");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.req.header(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection cookie(String str, String str2) {
        this.req.cookie(str, str2);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection cookies(Map<String, String> map) {
        Validate.notNullParam(map, "cookies");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.req.cookie(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection cookieStore(CookieStore cookieStore) {
        this.req.cookieManager = new CookieManager(cookieStore, null);
        return this;
    }

    @Override // org.jsoup.Connection
    public CookieStore cookieStore() {
        return this.req.cookieManager.getCookieStore();
    }

    @Override // org.jsoup.Connection
    public Connection parser(Parser parser) {
        this.req.parser(parser);
        return this;
    }

    @Override // org.jsoup.Connection
    public Document get() throws IOException {
        this.req.method(Connection.Method.GET);
        execute();
        Validate.notNull(this.res);
        return this.res.parse();
    }

    @Override // org.jsoup.Connection
    public Document post() throws IOException {
        this.req.method(Connection.Method.POST);
        execute();
        Validate.notNull(this.res);
        return this.res.parse();
    }

    @Override // org.jsoup.Connection
    public Connection.Response execute() throws IOException {
        Response responseExecute = Response.execute(this.req);
        this.res = responseExecute;
        return responseExecute;
    }

    @Override // org.jsoup.Connection
    public Connection.Request request() {
        return this.req;
    }

    @Override // org.jsoup.Connection
    public Connection request(Connection.Request request) {
        this.req = (Request) request;
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection.Response response() {
        Connection.Response response = this.res;
        if (response != null) {
            return response;
        }
        throw new IllegalArgumentException("You must execute the request before getting a response.");
    }

    @Override // org.jsoup.Connection
    public Connection response(Connection.Response response) {
        this.res = response;
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection postDataCharset(String str) {
        this.req.postDataCharset(str);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection auth(RequestAuthenticator requestAuthenticator) {
        this.req.auth(requestAuthenticator);
        return this;
    }

    @Override // org.jsoup.Connection
    public Connection onResponseProgress(Progress<Connection.Response> progress) {
        this.req.responseProgress = progress;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class Base<T extends Connection.Base<T>> implements Connection.Base<T> {
        private static final URL UnsetUrl;
        Map<String, String> cookies;
        Map<String, List<String>> headers;
        Connection.Method method;
        URL url;

        static {
            try {
                UnsetUrl = new URL("http://undefined/");
            } catch (MalformedURLException e) {
                throw new IllegalStateException(e);
            }
        }

        private Base() {
            this.url = UnsetUrl;
            this.method = Connection.Method.GET;
            this.headers = new LinkedHashMap();
            this.cookies = new LinkedHashMap();
        }

        private Base(Base<T> base) {
            this.url = UnsetUrl;
            this.method = Connection.Method.GET;
            this.url = base.url;
            this.method = base.method;
            this.headers = new LinkedHashMap();
            for (Map.Entry<String, List<String>> entry : base.headers.entrySet()) {
                this.headers.put(entry.getKey(), new ArrayList(entry.getValue()));
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            this.cookies = linkedHashMap;
            linkedHashMap.putAll(base.cookies);
        }

        @Override // org.jsoup.Connection.Base
        public URL url() {
            URL url = this.url;
            if (url != UnsetUrl) {
                return url;
            }
            throw new IllegalArgumentException("URL not set. Make sure to call #url(...) before executing the request.");
        }

        @Override // org.jsoup.Connection.Base
        public T url(URL url) {
            Validate.notNullParam(url, "url");
            this.url = new UrlBuilder(url).build();
            return this;
        }

        @Override // org.jsoup.Connection.Base
        public Connection.Method method() {
            return this.method;
        }

        @Override // org.jsoup.Connection.Base
        public T method(Connection.Method method) {
            Validate.notNullParam(method, XMLWriter.METHOD);
            this.method = method;
            return this;
        }

        @Override // org.jsoup.Connection.Base
        public String header(String str) {
            Validate.notNullParam(str, "name");
            List<String> headersCaseInsensitive = getHeadersCaseInsensitive(str);
            if (headersCaseInsensitive.isEmpty()) {
                return null;
            }
            return StringUtil.join(headersCaseInsensitive, ", ");
        }

        @Override // org.jsoup.Connection.Base
        public T addHeader(String str, String str2) {
            Validate.notEmptyParam(str, "name");
            if (str2 == null) {
                str2 = "";
            }
            List<String> listHeaders = headers(str);
            if (listHeaders.isEmpty()) {
                listHeaders = new ArrayList<>();
                this.headers.put(str, listHeaders);
            }
            listHeaders.add(str2);
            return this;
        }

        @Override // org.jsoup.Connection.Base
        public List<String> headers(String str) {
            Validate.notEmptyParam(str, "name");
            return getHeadersCaseInsensitive(str);
        }

        @Override // org.jsoup.Connection.Base
        public T header(String str, String str2) {
            Validate.notEmptyParam(str, "name");
            removeHeader(str);
            addHeader(str, str2);
            return this;
        }

        @Override // org.jsoup.Connection.Base
        public boolean hasHeader(String str) {
            Validate.notEmptyParam(str, "name");
            return !getHeadersCaseInsensitive(str).isEmpty();
        }

        @Override // org.jsoup.Connection.Base
        public boolean hasHeaderWithValue(String str, String str2) {
            Validate.notEmpty(str);
            Validate.notEmpty(str2);
            Iterator<String> it = headers(str).iterator();
            while (it.hasNext()) {
                if (str2.equalsIgnoreCase(it.next())) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jsoup.Connection.Base
        public T removeHeader(String str) {
            Validate.notEmptyParam(str, "name");
            Map.Entry<String, List<String>> entryScanHeaders = scanHeaders(str);
            if (entryScanHeaders != null) {
                this.headers.remove(entryScanHeaders.getKey());
            }
            return this;
        }

        @Override // org.jsoup.Connection.Base
        public Map<String, String> headers() {
            LinkedHashMap linkedHashMap = new LinkedHashMap(this.headers.size());
            for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if (!value.isEmpty()) {
                    linkedHashMap.put(key, value.get(0));
                }
            }
            return linkedHashMap;
        }

        @Override // org.jsoup.Connection.Base
        public Map<String, List<String>> multiHeaders() {
            return this.headers;
        }

        private List<String> getHeadersCaseInsensitive(String str) {
            Validate.notNull(str);
            for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
                if (str.equalsIgnoreCase(entry.getKey())) {
                    return entry.getValue();
                }
            }
            return Collections.emptyList();
        }

        private Map.Entry<String, List<String>> scanHeaders(String str) {
            String strLowerCase = Normalizer.lowerCase(str);
            for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
                if (Normalizer.lowerCase(entry.getKey()).equals(strLowerCase)) {
                    return entry;
                }
            }
            return null;
        }

        @Override // org.jsoup.Connection.Base
        public String cookie(String str) {
            Validate.notEmptyParam(str, "name");
            return this.cookies.get(str);
        }

        @Override // org.jsoup.Connection.Base
        public T cookie(String str, String str2) {
            Validate.notEmptyParam(str, "name");
            Validate.notNullParam(str2, "value");
            this.cookies.put(str, str2);
            return this;
        }

        @Override // org.jsoup.Connection.Base
        public boolean hasCookie(String str) {
            Validate.notEmptyParam(str, "name");
            return this.cookies.containsKey(str);
        }

        @Override // org.jsoup.Connection.Base
        public T removeCookie(String str) {
            Validate.notEmptyParam(str, "name");
            this.cookies.remove(str);
            return this;
        }

        @Override // org.jsoup.Connection.Base
        public Map<String, String> cookies() {
            return this.cookies;
        }
    }

    public static class Request extends Base<Connection.Request> implements Connection.Request {
        RequestAuthenticator authenticator;
        private Object body;
        HttpConnection connection;
        private CookieManager cookieManager;
        private final Collection<Connection.KeyVal> data;
        private final ReentrantLock executing;
        private boolean followRedirects;
        private boolean ignoreContentType;
        private boolean ignoreHttpErrors;
        private int maxBodySizeBytes;
        String mimeBoundary;
        private Parser parser;
        private boolean parserDefined;
        private String postDataCharset;
        private Proxy proxy;
        private Progress<Connection.Response> responseProgress;
        private SSLSocketFactory sslSocketFactory;
        private int timeoutMilliseconds;

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base addHeader(String str, String str2) {
            return super.addHeader(str, str2);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ String cookie(String str) {
            return super.cookie(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base cookie(String str, String str2) {
            return super.cookie(str, str2);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Map cookies() {
            return super.cookies();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ boolean hasCookie(String str) {
            return super.hasCookie(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ boolean hasHeader(String str) {
            return super.hasHeader(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ boolean hasHeaderWithValue(String str, String str2) {
            return super.hasHeaderWithValue(str, str2);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ String header(String str) {
            return super.header(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base header(String str, String str2) {
            return super.header(str, str2);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ List headers(String str) {
            return super.headers(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Map headers() {
            return super.headers();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base method(Connection.Method method) {
            return super.method(method);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Method method() {
            return super.method();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Map multiHeaders() {
            return super.multiHeaders();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base removeCookie(String str) {
            return super.removeCookie(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base removeHeader(String str) {
            return super.removeHeader(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ URL url() {
            return super.url();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base url(URL url) {
            return super.url(url);
        }

        static {
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        }

        Request() {
            super();
            this.body = null;
            this.ignoreHttpErrors = false;
            this.ignoreContentType = false;
            this.parserDefined = false;
            this.postDataCharset = DataUtil.defaultCharsetName;
            this.executing = new ReentrantLock();
            this.timeoutMilliseconds = 30000;
            this.maxBodySizeBytes = 2097152;
            this.followRedirects = true;
            this.data = new ArrayList();
            this.method = Connection.Method.GET;
            addHeader("Accept-Encoding", "gzip");
            addHeader("User-Agent", HttpConnection.DEFAULT_UA);
            this.parser = Parser.htmlParser();
            this.cookieManager = new CookieManager();
        }

        Request(Request request) {
            super(request);
            this.body = null;
            this.ignoreHttpErrors = false;
            this.ignoreContentType = false;
            this.parserDefined = false;
            this.postDataCharset = DataUtil.defaultCharsetName;
            this.executing = new ReentrantLock();
            this.connection = request.connection;
            this.proxy = request.proxy;
            this.postDataCharset = request.postDataCharset;
            this.timeoutMilliseconds = request.timeoutMilliseconds;
            this.maxBodySizeBytes = request.maxBodySizeBytes;
            this.followRedirects = request.followRedirects;
            this.data = new ArrayList();
            this.ignoreHttpErrors = request.ignoreHttpErrors;
            this.ignoreContentType = request.ignoreContentType;
            this.parser = request.parser.newInstance();
            this.parserDefined = request.parserDefined;
            this.sslSocketFactory = request.sslSocketFactory;
            this.cookieManager = request.cookieManager;
            this.authenticator = request.authenticator;
            this.responseProgress = request.responseProgress;
        }

        @Override // org.jsoup.Connection.Request
        public Proxy proxy() {
            return this.proxy;
        }

        @Override // org.jsoup.Connection.Request
        public Request proxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public Request proxy(String str, int i) {
            this.proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(str, i));
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public int timeout() {
            return this.timeoutMilliseconds;
        }

        @Override // org.jsoup.Connection.Request
        public Request timeout(int i) {
            Validate.isTrue(i >= 0, "Timeout milliseconds must be 0 (infinite) or greater");
            this.timeoutMilliseconds = i;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public int maxBodySize() {
            return this.maxBodySizeBytes;
        }

        @Override // org.jsoup.Connection.Request
        public Connection.Request maxBodySize(int i) {
            Validate.isTrue(i >= 0, "maxSize must be 0 (unlimited) or larger");
            this.maxBodySizeBytes = i;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public boolean followRedirects() {
            return this.followRedirects;
        }

        @Override // org.jsoup.Connection.Request
        public Connection.Request followRedirects(boolean z) {
            this.followRedirects = z;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public boolean ignoreHttpErrors() {
            return this.ignoreHttpErrors;
        }

        @Override // org.jsoup.Connection.Request
        public SSLSocketFactory sslSocketFactory() {
            return this.sslSocketFactory;
        }

        @Override // org.jsoup.Connection.Request
        public void sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.sslSocketFactory = sSLSocketFactory;
        }

        @Override // org.jsoup.Connection.Request
        public Connection.Request ignoreHttpErrors(boolean z) {
            this.ignoreHttpErrors = z;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public boolean ignoreContentType() {
            return this.ignoreContentType;
        }

        @Override // org.jsoup.Connection.Request
        public Connection.Request ignoreContentType(boolean z) {
            this.ignoreContentType = z;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public Request data(Connection.KeyVal keyVal) {
            Validate.notNullParam(keyVal, "keyval");
            this.data.add(keyVal);
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public Collection<Connection.KeyVal> data() {
            return this.data;
        }

        @Override // org.jsoup.Connection.Request
        public Connection.Request requestBody(String str) {
            this.body = str;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public String requestBody() {
            Object obj = this.body;
            if (obj instanceof String) {
                return (String) obj;
            }
            return null;
        }

        @Override // org.jsoup.Connection.Request
        public Connection.Request requestBodyStream(InputStream inputStream) {
            this.body = inputStream;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public Request parser(Parser parser) {
            this.parser = parser;
            this.parserDefined = true;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public Parser parser() {
            return this.parser;
        }

        @Override // org.jsoup.Connection.Request
        public Connection.Request postDataCharset(String str) {
            Validate.notNullParam(str, "charset");
            if (!Charset.isSupported(str)) {
                throw new IllegalCharsetNameException(str);
            }
            this.postDataCharset = str;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public String postDataCharset() {
            return this.postDataCharset;
        }

        CookieManager cookieManager() {
            return this.cookieManager;
        }

        @Override // org.jsoup.Connection.Request
        public Connection.Request auth(RequestAuthenticator requestAuthenticator) {
            this.authenticator = requestAuthenticator;
            return this;
        }

        @Override // org.jsoup.Connection.Request
        public RequestAuthenticator auth() {
            return this.authenticator;
        }
    }

    public static class Response extends Base<Connection.Response> implements Connection.Response {
        private static final String LOCATION = "Location";
        private static final int MAX_REDIRECTS = 20;
        private static final Pattern xmlContentTypeRxp = Pattern.compile("(\\w+)/\\w*\\+?xml.*");
        private ControllableInputStream bodyStream;
        private ByteBuffer byteData;
        private String charset;
        int contentLength;
        String contentType;
        private boolean executed;
        RequestExecutor executor;
        private boolean inputStreamRead;
        private int numRedirects;
        private final Request req;
        int statusCode;
        String statusMessage;

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base addHeader(String str, String str2) {
            return super.addHeader(str, str2);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ String cookie(String str) {
            return super.cookie(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base cookie(String str, String str2) {
            return super.cookie(str, str2);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Map cookies() {
            return super.cookies();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ boolean hasCookie(String str) {
            return super.hasCookie(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ boolean hasHeader(String str) {
            return super.hasHeader(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ boolean hasHeaderWithValue(String str, String str2) {
            return super.hasHeaderWithValue(str, str2);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ String header(String str) {
            return super.header(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base header(String str, String str2) {
            return super.header(str, str2);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ List headers(String str) {
            return super.headers(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Map headers() {
            return super.headers();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base method(Connection.Method method) {
            return super.method(method);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Method method() {
            return super.method();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Map multiHeaders() {
            return super.multiHeaders();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base removeCookie(String str) {
            return super.removeCookie(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base removeHeader(String str) {
            return super.removeHeader(str);
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ URL url() {
            return super.url();
        }

        @Override // org.jsoup.helper.HttpConnection.Base, org.jsoup.Connection.Base
        public /* bridge */ /* synthetic */ Connection.Base url(URL url) {
            return super.url(url);
        }

        Response() {
            super();
            this.executed = false;
            this.inputStreamRead = false;
            this.numRedirects = 0;
            this.statusCode = 400;
            this.statusMessage = "Request not made";
            this.req = new Request();
            this.contentType = null;
        }

        static Response execute(Request request) throws IOException {
            return execute(request, null);
        }

        /* JADX WARN: Code duplicated, block: B:77:0x019d A[Catch: IOException -> 0x01d4, all -> 0x01d7, TryCatch #1 {IOException -> 0x01d4, blocks: (B:24:0x008a, B:26:0x0090, B:28:0x0096, B:30:0x009c, B:31:0x00b0, B:33:0x00bf, B:35:0x00c8, B:36:0x00cc, B:41:0x00ec, B:47:0x00fc, B:49:0x0102, B:51:0x0108, B:53:0x0110, B:56:0x011d, B:57:0x012c, B:59:0x012f, B:61:0x013b, B:63:0x0141, B:64:0x0148, B:66:0x0154, B:68:0x015c, B:70:0x0168, B:75:0x0182, B:77:0x019d, B:72:0x016f, B:74:0x0177, B:78:0x01a9, B:45:0x00f6, B:84:0x01c2, B:85:0x01d3), top: B:100:0x008a }] */
        static Response execute(Request request, Response response) throws IOException {
            InputStream inflaterInputStream;
            Validate.isTrue(request.executing.tryLock(), "Multiple threads were detected trying to execute the same request concurrently. Make sure to use Connection#newRequest() and do not share an executing request between threads.");
            Validate.notNullParam(request, "req");
            URL url = request.url();
            Validate.notNull(url, "URL must be specified to connect");
            String protocol = url.getProtocol();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                throw new MalformedURLException("Only http & https protocols supported");
            }
            boolean zHasBody = request.method().hasBody();
            boolean z = request.body != null;
            if (!zHasBody) {
                Validate.isFalse(z, "Cannot set a request body for HTTP method " + request.method());
            }
            if (!request.data().isEmpty() && (!zHasBody || z)) {
                serialiseRequestUrl(request);
            } else if (zHasBody) {
                setOutputContentType(request);
            }
            long jNanoTime = System.nanoTime();
            RequestExecutor requestExecutor = RequestDispatch.get(request, response);
            Response response2 = null;
            try {
                try {
                    Response responseExecute = requestExecutor.execute();
                    try {
                        if (responseExecute.hasHeader("Location") && request.followRedirects()) {
                            if (responseExecute.statusCode != 307) {
                                request.method(Connection.Method.GET);
                                request.data().clear();
                                request.requestBody(null);
                                request.removeHeader("Content-Type");
                            }
                            String strHeader = responseExecute.header("Location");
                            Validate.notNull(strHeader);
                            if (strHeader.startsWith("http:/") && strHeader.charAt(6) != '/') {
                                strHeader = strHeader.substring(6);
                            }
                            request.url(StringUtil.resolve(request.url(), strHeader));
                            Response responseExecute2 = execute(request, responseExecute);
                            request.executing.unlock();
                            if (request.authenticator != null) {
                                AuthenticationHandler.handler.remove();
                            }
                            return responseExecute2;
                        }
                        int i = responseExecute.statusCode;
                        if ((i < 200 || i >= 400) && !request.ignoreHttpErrors()) {
                            throw new HttpStatusException("HTTP error fetching URL", responseExecute.statusCode, request.url().toString());
                        }
                        String strContentType = responseExecute.contentType();
                        if (strContentType != null && !request.ignoreContentType() && !strContentType.startsWith("text/") && !xmlContentTypeRxp.matcher(strContentType).matches()) {
                            throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, */xml, or */*+xml", strContentType, request.url().toString());
                        }
                        if (strContentType != null && xmlContentTypeRxp.matcher(strContentType).matches() && !request.parserDefined) {
                            request.parser(Parser.xmlParser());
                        }
                        responseExecute.charset = DataUtil.getCharsetFromContentType(responseExecute.contentType);
                        if (responseExecute.contentLength != 0 && request.method() != Connection.Method.HEAD) {
                            InputStream inputStreamResponseBody = requestExecutor.responseBody();
                            if (!responseExecute.hasHeaderWithValue("Content-Encoding", "gzip")) {
                                if (responseExecute.hasHeaderWithValue("Content-Encoding", "deflate")) {
                                    inflaterInputStream = new InflaterInputStream(inputStreamResponseBody, new Inflater(true));
                                } else {
                                    responseExecute.bodyStream = ControllableInputStream.wrap(inputStreamResponseBody, 8192, request.maxBodySize()).timeout(jNanoTime, request.timeout());
                                    if (request.responseProgress != null) {
                                        responseExecute.bodyStream.onProgress(responseExecute.contentLength, request.responseProgress, responseExecute);
                                    }
                                }
                            } else {
                                inflaterInputStream = new GZIPInputStream(inputStreamResponseBody);
                            }
                            inputStreamResponseBody = inflaterInputStream;
                            responseExecute.bodyStream = ControllableInputStream.wrap(inputStreamResponseBody, 8192, request.maxBodySize()).timeout(jNanoTime, request.timeout());
                            if (request.responseProgress != null) {
                                responseExecute.bodyStream.onProgress(responseExecute.contentLength, request.responseProgress, responseExecute);
                            }
                        } else {
                            responseExecute.byteData = DataUtil.emptyByteBuffer();
                        }
                        request.executing.unlock();
                        if (request.authenticator != null) {
                            AuthenticationHandler.handler.remove();
                        }
                        responseExecute.executed = true;
                        return responseExecute;
                    } catch (IOException e) {
                        e = e;
                        response2 = responseExecute;
                        if (response2 != null) {
                            response2.safeClose();
                        }
                        throw e;
                    }
                } catch (Throwable th) {
                    request.executing.unlock();
                    if (request.authenticator != null) {
                        AuthenticationHandler.handler.remove();
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        }

        @Override // org.jsoup.Connection.Response
        public int statusCode() {
            return this.statusCode;
        }

        @Override // org.jsoup.Connection.Response
        public String statusMessage() {
            return this.statusMessage;
        }

        @Override // org.jsoup.Connection.Response
        public String charset() {
            return this.charset;
        }

        @Override // org.jsoup.Connection.Response
        public Response charset(String str) {
            this.charset = str;
            return this;
        }

        @Override // org.jsoup.Connection.Response
        public String contentType() {
            return this.contentType;
        }

        private ControllableInputStream prepareParse() {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before parsing response");
            ControllableInputStream controllableInputStreamWrap = this.bodyStream;
            if (this.byteData != null) {
                controllableInputStreamWrap = ControllableInputStream.wrap(new ByteArrayInputStream(this.byteData.array(), 0, this.byteData.limit()), 0);
                this.inputStreamRead = false;
            }
            Validate.isFalse(this.inputStreamRead, "Input stream already read and parsed, cannot re-read.");
            Validate.notNull(controllableInputStreamWrap);
            this.inputStreamRead = true;
            return controllableInputStreamWrap;
        }

        @Override // org.jsoup.Connection.Response
        public Document parse() throws Throwable {
            Document inputStream = DataUtil.parseInputStream(prepareParse(), this.charset, this.url.toExternalForm(), this.req.parser());
            inputStream.connection(new HttpConnection(this.req, this));
            this.charset = inputStream.outputSettings().charset().name();
            safeClose();
            return inputStream;
        }

        @Override // org.jsoup.Connection.Response
        public StreamParser streamParser() throws IOException {
            ControllableInputStream controllableInputStreamPrepareParse = prepareParse();
            String externalForm = this.url.toExternalForm();
            DataUtil.CharsetDoc charsetDocDetectCharset = DataUtil.detectCharset(controllableInputStreamPrepareParse, this.charset, externalForm, this.req.parser());
            StreamParser streamParser = new StreamParser(this.req.parser());
            streamParser.parse(new BufferedReader(new InputStreamReader(controllableInputStreamPrepareParse, charsetDocDetectCharset.charset)), externalForm);
            streamParser.document().connection(new HttpConnection(this.req, this));
            this.charset = charsetDocDetectCharset.charset.name();
            return streamParser;
        }

        @Override // org.jsoup.Connection.Response
        public Connection.Response readFully() throws IOException {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            if (this.bodyStream == null || this.byteData != null) {
                return this;
            }
            Validate.isFalse(this.inputStreamRead, "Request has already been read (with .parse())");
            try {
                this.byteData = DataUtil.readToByteBuffer(this.bodyStream, this.req.maxBodySize());
                return this;
            } finally {
                this.inputStreamRead = true;
                safeClose();
            }
        }

        private void readByteDataUnchecked() {
            try {
                readFully();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override // org.jsoup.Connection.Response
        public String readBody() throws IOException {
            readFully();
            return body();
        }

        @Override // org.jsoup.Connection.Response
        public String body() {
            readByteDataUnchecked();
            Validate.notNull(this.byteData);
            String str = this.charset;
            String string = (str == null ? DataUtil.UTF_8 : Charset.forName(str)).decode(this.byteData).toString();
            this.byteData.rewind();
            return string;
        }

        @Override // org.jsoup.Connection.Response
        public byte[] bodyAsBytes() {
            readByteDataUnchecked();
            Validate.notNull(this.byteData);
            Validate.isTrue(this.byteData.hasArray());
            byte[] bArrArray = this.byteData.array();
            int iArrayOffset = this.byteData.arrayOffset();
            int iLimit = this.byteData.limit();
            if (iArrayOffset == 0 && iLimit == bArrArray.length) {
                return bArrArray;
            }
            byte[] bArr = new byte[iLimit];
            System.arraycopy(bArrArray, iArrayOffset, bArr, 0, iLimit);
            return bArr;
        }

        @Override // org.jsoup.Connection.Response
        public Connection.Response bufferUp() {
            readByteDataUnchecked();
            return this;
        }

        @Override // org.jsoup.Connection.Response
        public BufferedInputStream bodyStream() {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            if (this.byteData != null) {
                return new BufferedInputStream(new ByteArrayInputStream(this.byteData.array(), 0, this.byteData.limit()), 8192);
            }
            Validate.isFalse(this.inputStreamRead, "Request has already been read");
            Validate.notNull(this.bodyStream);
            this.inputStreamRead = true;
            return this.bodyStream.inputStream();
        }

        private void safeClose() {
            ControllableInputStream controllableInputStream = this.bodyStream;
            if (controllableInputStream != null) {
                try {
                    controllableInputStream.close();
                } catch (IOException unused) {
                } finally {
                    this.bodyStream = null;
                }
            }
            RequestExecutor requestExecutor = this.executor;
            if (requestExecutor != null) {
                requestExecutor.safeClose();
            }
        }

        Response(Request request) {
            super();
            this.executed = false;
            this.inputStreamRead = false;
            this.numRedirects = 0;
            this.req = request;
        }

        void prepareResponse(Map<String, List<String>> map, Response response) throws IOException {
            processResponseHeaders(map);
            CookieUtil.storeCookies(this.req, this, this.url, map);
            if (response != null) {
                for (Map.Entry entry : response.cookies().entrySet()) {
                    if (!hasCookie((String) entry.getKey())) {
                        cookie((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                response.safeClose();
                int i = response.numRedirects + 1;
                this.numRedirects = i;
                if (i >= 20) {
                    throw new IOException(String.format("Too many redirects occurred trying to load URL %s", response.url()));
                }
            }
        }

        void processResponseHeaders(Map<String, List<String>> map) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key != null) {
                    Iterator<String> it = entry.getValue().iterator();
                    while (it.hasNext()) {
                        addHeader(key, fixHeaderEncoding(it.next()));
                    }
                }
            }
        }

        static String fixHeaderEncoding(String str) {
            if (str == null || !StandardCharsets.ISO_8859_1.newEncoder().canEncode(str)) {
                return str;
            }
            byte[] bytes = str.getBytes(HttpConnection.ISO_8859_1);
            return looksLikeUtf8(bytes) ? new String(bytes, DataUtil.UTF_8) : str;
        }

        private static boolean looksLikeUtf8(byte[] bArr) {
            int i;
            int i2 = (bArr.length >= 3 && (bArr[0] & 255) == 239 && (bArr[1] & 255) == 187 && (bArr[2] & 255) == 191) ? 3 : 0;
            int length = bArr.length;
            boolean z = false;
            while (i2 < length) {
                byte b = bArr[i2];
                if ((b & 128) != 0) {
                    if ((b & 224) == 192) {
                        i = i2 + 1;
                    } else if ((b & 240) == 224) {
                        i = i2 + 2;
                    } else {
                        if ((b & PaintErrorCode.SECURITY_ERROR) != 240) {
                            return false;
                        }
                        i = i2 + 3;
                    }
                    if (i >= bArr.length) {
                        return false;
                    }
                    while (i2 < i) {
                        i2++;
                        if ((bArr[i2] & 192) != 128) {
                            return false;
                        }
                    }
                    z = true;
                }
                i2++;
            }
            return z;
        }

        private static void setOutputContentType(Request request) {
            String strMimeBoundary;
            String strHeader = request.header("Content-Type");
            if (strHeader == null) {
                if (!HttpConnection.needsMultipart(request)) {
                    request.header("Content-Type", "application/x-www-form-urlencoded; charset=" + request.postDataCharset());
                    strMimeBoundary = null;
                } else {
                    strMimeBoundary = DataUtil.mimeBoundary();
                    request.header("Content-Type", "multipart/form-data; boundary=" + strMimeBoundary);
                }
            } else if (!strHeader.contains("multipart/form-data") || strHeader.contains("boundary")) {
                strMimeBoundary = null;
            } else {
                strMimeBoundary = DataUtil.mimeBoundary();
                request.header("Content-Type", "multipart/form-data; boundary=" + strMimeBoundary);
            }
            request.mimeBoundary = strMimeBoundary;
        }

        static void writePost(Request request, OutputStream outputStream) throws IOException {
            Collection<Connection.KeyVal> collectionData = request.data();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, request.postDataCharset()));
            String str = request.mimeBoundary;
            if (str == null) {
                if (request.body != null) {
                    if (request.body instanceof String) {
                        bufferedWriter.write((String) request.body);
                    } else if (request.body instanceof InputStream) {
                        DataUtil.crossStreams((InputStream) request.body, outputStream);
                        outputStream.flush();
                    } else {
                        throw new IllegalStateException();
                    }
                } else {
                    boolean z = true;
                    for (Connection.KeyVal keyVal : collectionData) {
                        if (z) {
                            z = false;
                        } else {
                            bufferedWriter.append(Typography.amp);
                        }
                        bufferedWriter.write(URLEncoder.encode(keyVal.key(), request.postDataCharset()));
                        bufferedWriter.write(61);
                        bufferedWriter.write(URLEncoder.encode(keyVal.value(), request.postDataCharset()));
                    }
                }
            } else {
                for (Connection.KeyVal keyVal2 : collectionData) {
                    bufferedWriter.write("--");
                    bufferedWriter.write(str);
                    bufferedWriter.write(MusicLyricParser.CRLF);
                    bufferedWriter.write("Content-Disposition: form-data; name=\"");
                    bufferedWriter.write(HttpConnection.encodeMimeName(keyVal2.key()));
                    bufferedWriter.write("\"");
                    InputStream inputStream = keyVal2.inputStream();
                    if (inputStream != null) {
                        bufferedWriter.write("; filename=\"");
                        bufferedWriter.write(HttpConnection.encodeMimeName(keyVal2.value()));
                        bufferedWriter.write("\"\r\nContent-Type: ");
                        String strContentType = keyVal2.contentType();
                        if (strContentType == null) {
                            strContentType = "application/octet-stream";
                        }
                        bufferedWriter.write(strContentType);
                        bufferedWriter.write("\r\n\r\n");
                        bufferedWriter.flush();
                        DataUtil.crossStreams(inputStream, outputStream);
                        outputStream.flush();
                    } else {
                        bufferedWriter.write("\r\n\r\n");
                        bufferedWriter.write(keyVal2.value());
                    }
                    bufferedWriter.write(MusicLyricParser.CRLF);
                }
                bufferedWriter.write("--");
                bufferedWriter.write(str);
                bufferedWriter.write("--");
            }
            bufferedWriter.close();
        }

        private static void serialiseRequestUrl(Connection.Request request) throws IOException {
            UrlBuilder urlBuilder = new UrlBuilder(request.url());
            for (Connection.KeyVal keyVal : request.data()) {
                Validate.isFalse(keyVal.hasInputStream(), "InputStream data not supported in URL query string.");
                urlBuilder.appendKeyVal(keyVal);
            }
            request.url(urlBuilder.build());
            request.data().clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean needsMultipart(Connection.Request request) {
        Iterator<Connection.KeyVal> it = request.data().iterator();
        while (it.hasNext()) {
            if (it.next().hasInputStream()) {
                return true;
            }
        }
        return false;
    }

    public static class KeyVal implements Connection.KeyVal {
        private String contentType;
        private String key;
        private InputStream stream;
        private String value;

        public static KeyVal create(String str, String str2) {
            return new KeyVal(str, str2);
        }

        public static KeyVal create(String str, String str2, InputStream inputStream) {
            return new KeyVal(str, str2).inputStream(inputStream);
        }

        private KeyVal(String str, String str2) {
            Validate.notEmptyParam(str, "key");
            Validate.notNullParam(str2, "value");
            this.key = str;
            this.value = str2;
        }

        @Override // org.jsoup.Connection.KeyVal
        public KeyVal key(String str) {
            Validate.notEmptyParam(str, "key");
            this.key = str;
            return this;
        }

        @Override // org.jsoup.Connection.KeyVal
        public String key() {
            return this.key;
        }

        @Override // org.jsoup.Connection.KeyVal
        public KeyVal value(String str) {
            Validate.notNullParam(str, "value");
            this.value = str;
            return this;
        }

        @Override // org.jsoup.Connection.KeyVal
        public String value() {
            return this.value;
        }

        @Override // org.jsoup.Connection.KeyVal
        public KeyVal inputStream(InputStream inputStream) {
            Validate.notNullParam(this.value, "inputStream");
            this.stream = inputStream;
            return this;
        }

        @Override // org.jsoup.Connection.KeyVal
        public InputStream inputStream() {
            return this.stream;
        }

        @Override // org.jsoup.Connection.KeyVal
        public boolean hasInputStream() {
            return this.stream != null;
        }

        @Override // org.jsoup.Connection.KeyVal
        public Connection.KeyVal contentType(String str) {
            Validate.notEmpty(str);
            this.contentType = str;
            return this;
        }

        @Override // org.jsoup.Connection.KeyVal
        public String contentType() {
            return this.contentType;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}
