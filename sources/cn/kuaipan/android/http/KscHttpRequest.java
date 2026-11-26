package cn.kuaipan.android.http;

import android.net.Uri;
import android.util.Log;
import cn.kuaipan.android.http.multipart.ByteArrayValuePair;
import cn.kuaipan.android.http.multipart.FilePart;
import cn.kuaipan.android.http.multipart.FileValuePair;
import cn.kuaipan.android.http.multipart.MultipartEntity;
import cn.kuaipan.android.http.multipart.Part;
import cn.kuaipan.android.http.multipart.StringPart;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpRequest {
    private static final String LOG_TAG = "KscHttpRequest";
    private final IKscDecoder mDecoder;
    private final IKscTransferListener mListener;
    private final HttpMethod mMethod;
    private AbstractHttpEntity mPostEntity;
    private final ArrayList<NameValuePair> mPostForm;
    private HttpUriRequest mRequest;
    private boolean mTryGzip;
    private Uri mUri;

    public enum HttpMethod {
        GET,
        POST
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KscHttpRequest() {
        this((Uri) null);
    }

    public KscHttpRequest(String str) {
        this((HttpMethod) null, Uri.parse(str), (IKscDecoder) null, (IKscTransferListener) null);
    }

    public KscHttpRequest(HttpMethod httpMethod, String str) {
        this(httpMethod, Uri.parse(str), null, null, null);
    }

    public KscHttpRequest(Uri uri) {
        this((HttpMethod) null, uri, (IKscDecoder) null, (IKscTransferListener) null);
    }

    public KscHttpRequest(HttpMethod httpMethod, Uri uri) {
        this(httpMethod, uri, null, null, null);
    }

    public KscHttpRequest(HttpMethod httpMethod, String str, IKscTransferListener iKscTransferListener) {
        this(httpMethod, Uri.parse(str), null, null, iKscTransferListener);
    }

    public KscHttpRequest(HttpMethod httpMethod, Uri uri, IKscTransferListener iKscTransferListener) {
        this(httpMethod, uri, null, null, iKscTransferListener);
    }

    public KscHttpRequest(HttpMethod httpMethod, String str, IKscDecoder iKscDecoder, IKscTransferListener iKscTransferListener) {
        this(httpMethod, Uri.parse(str), null, iKscDecoder, iKscTransferListener);
    }

    public KscHttpRequest(HttpMethod httpMethod, Uri uri, IKscDecoder iKscDecoder, IKscTransferListener iKscTransferListener) {
        this(httpMethod, uri, null, iKscDecoder, iKscTransferListener);
    }

    public KscHttpRequest(HttpMethod httpMethod, Uri uri, AbstractHttpEntity abstractHttpEntity, IKscDecoder iKscDecoder, IKscTransferListener iKscTransferListener) {
        this.mPostForm = new ArrayList<>();
        this.mTryGzip = false;
        this.mMethod = httpMethod;
        this.mUri = uri;
        this.mPostEntity = abstractHttpEntity;
        this.mDecoder = iKscDecoder;
        this.mListener = iKscTransferListener;
    }

    public void setGZip(boolean z) {
        this.mTryGzip = z;
    }

    private void checkRequest() {
        if (this.mRequest != null) {
            throw new RuntimeException("HttpRequest has been created. All input can't be changed.");
        }
    }

    public void setUri(String str) {
        checkRequest();
        this.mUri = str == null ? null : Uri.parse(str);
    }

    public void setUri(Uri uri) {
        checkRequest();
        this.mUri = uri;
    }

    public void setUri(URI uri) {
        checkRequest();
        this.mUri = uri == null ? null : Uri.parse(uri.toString());
    }

    public void setUri(URL url) {
        checkRequest();
        this.mUri = url == null ? null : Uri.parse(url.toString());
    }

    public Uri getUri() {
        return this.mUri;
    }

    public void appendParameters(NameValuePair... nameValuePairArr) {
        appendParameters(Arrays.asList(nameValuePairArr));
    }

    public void appendParameters(Collection<? extends NameValuePair> collection) {
        checkRequest();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (collection != null) {
            for (NameValuePair nameValuePair : collection) {
                if ((nameValuePair instanceof FileValuePair) || (nameValuePair instanceof ByteArrayValuePair)) {
                    arrayList2.add(nameValuePair);
                } else {
                    arrayList.add(nameValuePair);
                }
            }
        }
        if (!arrayList.isEmpty()) {
            appendQueryParameters(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        appendPostParameters(arrayList2);
    }

    public void appendQueryParameter(String str, String str2) {
        checkRequest();
        Uri uri = this.mUri;
        if (uri == null) {
            throw new RuntimeException("A uri should be set firstly");
        }
        this.mUri = uri.buildUpon().appendQueryParameter(str, str2).build();
    }

    public void appendQueryParameters(NameValuePair... nameValuePairArr) {
        appendQueryParameters(Arrays.asList(nameValuePairArr));
    }

    public void appendQueryParameters(Collection<? extends NameValuePair> collection) {
        checkRequest();
        Uri uri = this.mUri;
        if (uri == null) {
            throw new RuntimeException("A uri should be set firstly");
        }
        if (collection == null) {
            return;
        }
        Uri.Builder builderBuildUpon = uri.buildUpon();
        for (NameValuePair nameValuePair : collection) {
            builderBuildUpon.appendQueryParameter(nameValuePair.getName(), nameValuePair.getValue());
        }
        this.mUri = builderBuildUpon.build();
    }

    public void appendPostParameters(NameValuePair... nameValuePairArr) {
        appendPostParameters(Arrays.asList(nameValuePairArr));
    }

    public void appendPostParameters(Collection<? extends NameValuePair> collection) {
        checkRequest();
        if (collection == null) {
            return;
        }
        if (!isFormEntity(this.mPostEntity)) {
            throw new RuntimeException("Http not support send form data and binary data in one request.");
        }
        this.mPostForm.addAll(collection);
    }

    public String getQuery() {
        Uri uri = this.mUri;
        if (uri == null) {
            return null;
        }
        return uri.getQuery();
    }

    public String getPostString() {
        AbstractHttpEntity abstractHttpEntity = this.mPostEntity;
        if (abstractHttpEntity == null) {
            return URLEncodedUtils.format(filterStringPair(this.mPostForm), "UTF-8");
        }
        if (abstractHttpEntity instanceof MultipartEntity) {
            ArrayList arrayList = new ArrayList();
            Part[] parts = ((MultipartEntity) abstractHttpEntity).getParts();
            if (parts != null) {
                arrayList.addAll(filterStringPart(parts));
            }
            arrayList.addAll(filterStringPair(this.mPostForm));
            return URLEncodedUtils.format(arrayList, "UTF-8");
        }
        if (URLEncodedUtils.isEncoded(abstractHttpEntity)) {
            return URLEncodedUtils.format(filterStringPair(getMergedPostValue(abstractHttpEntity, this.mPostForm)), "UTF-8");
        }
        if (!this.mPostForm.isEmpty()) {
            return null;
        }
        try {
            return EntityUtils.toString(abstractHttpEntity);
        } catch (Exception e) {
            Log.i(LOG_TAG, "Entity can't be cover to a String", e);
            return null;
        }
    }

    private static List<NameValuePair> filterStringPair(Collection<NameValuePair> collection) {
        ArrayList arrayList = new ArrayList();
        if (collection != null) {
            for (NameValuePair nameValuePair : collection) {
                if (!(nameValuePair instanceof FileValuePair) && !(nameValuePair instanceof ByteArrayValuePair)) {
                    arrayList.add(nameValuePair);
                } else {
                    Log.i(LOG_TAG, "Lost a non-string valuePair when getPostString. pair=" + nameValuePair);
                }
            }
        }
        return arrayList;
    }

    private static List<NameValuePair> filterStringPart(Part[] partArr) {
        ArrayList arrayList = new ArrayList();
        if (partArr != null) {
            for (Part part : partArr) {
                if (part instanceof StringPart) {
                    arrayList.add(new BasicNameValuePair(part.getName(), ((StringPart) part).getValue()));
                } else {
                    Log.i(LOG_TAG, "Lost a non-string in parts. part=" + part);
                }
            }
        }
        return arrayList;
    }

    private static ArrayList<NameValuePair> getMergedPostValue(AbstractHttpEntity abstractHttpEntity, List<NameValuePair> list) {
        ArrayList<NameValuePair> arrayList = new ArrayList<>();
        if (abstractHttpEntity != null) {
            try {
                arrayList.addAll(URLEncodedUtils.parse(abstractHttpEntity));
            } catch (IOException e) {
                Log.e(LOG_TAG, "Failed parse an user entity.", e);
                throw new RuntimeException("Failed parse an user entity. The user entity should be parseable by URLEncodedUtils.parse(HttpEntity)", e);
            }
        }
        arrayList.addAll(list);
        return arrayList;
    }

    public void setPostData(byte[] bArr) {
        setPostEntity(new ByteArrayEntity(bArr));
    }

    public void setPostEntity(AbstractHttpEntity abstractHttpEntity) {
        checkRequest();
        this.mPostEntity = abstractHttpEntity;
        if (isFormEntity(abstractHttpEntity)) {
            return;
        }
        this.mPostForm.clear();
    }

    public HttpUriRequest getRequest() {
        if (this.mRequest == null) {
            this.mRequest = createHttpRequest();
        }
        return this.mRequest;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x005d  */
    /* JADX WARN: Code duplicated, block: B:29:0x0069  */
    private HttpUriRequest createHttpRequest() {
        HttpUriRequest httpUriRequest;
        HttpUriRequest httpUriRequest2;
        if (!isValidUri(this.mUri)) {
            throw new IllegalArgumentException("Request uri is not valid. uri=" + this.mUri);
        }
        String string = this.mUri.toString();
        HttpMethod httpMethod = this.mMethod;
        if (httpMethod == null) {
            httpMethod = (this.mPostEntity == null && this.mPostForm.isEmpty()) ? HttpMethod.GET : HttpMethod.POST;
        }
        int i = AnonymousClass1.$SwitchMap$cn$kuaipan$android$http$KscHttpRequest$HttpMethod[httpMethod.ordinal()];
        if (i == 1) {
            HttpGet httpGet = new HttpGet(string);
            if (this.mPostEntity == null) {
                httpUriRequest = httpGet;
                if (!this.mPostForm.isEmpty()) {
                    Log.w(LOG_TAG, "Post data is not empty, but method is GET. All post data is lost.");
                    httpUriRequest = httpGet;
                }
            } else {
                Log.w(LOG_TAG, "Post data is not empty, but method is GET. All post data is lost.");
                httpUriRequest = httpGet;
            }
        } else {
            if (i != 2) {
                httpUriRequest2 = null;
            } else {
                HttpPost httpPost = new HttpPost(string);
                if (!this.mPostForm.isEmpty()) {
                    this.mPostEntity = makeFormEntity();
                }
                httpPost.setEntity(this.mPostEntity);
                httpUriRequest = httpPost;
            }
            if (this.mTryGzip) {
                httpUriRequest2.setHeader("Accept-Encoding", "gzip");
            }
            this.mRequest = httpUriRequest2;
            return httpUriRequest2;
        }
        httpUriRequest2 = httpUriRequest;
        if (this.mTryGzip) {
            httpUriRequest2.setHeader("Accept-Encoding", "gzip");
        }
        this.mRequest = httpUriRequest2;
        return httpUriRequest2;
    }

    /* JADX INFO: renamed from: cn.kuaipan.android.http.KscHttpRequest$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$kuaipan$android$http$KscHttpRequest$HttpMethod;

        static {
            int[] iArr = new int[HttpMethod.values().length];
            $SwitchMap$cn$kuaipan$android$http$KscHttpRequest$HttpMethod = iArr;
            try {
                iArr[HttpMethod.GET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$http$KscHttpRequest$HttpMethod[HttpMethod.POST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private AbstractHttpEntity makeFormEntity() {
        NameValuePair next;
        AbstractHttpEntity abstractHttpEntity = this.mPostEntity;
        ArrayList<NameValuePair> arrayList = this.mPostForm;
        if (arrayList.isEmpty()) {
            return this.mPostEntity;
        }
        boolean z = true;
        boolean z2 = abstractHttpEntity != null && (abstractHttpEntity instanceof MultipartEntity);
        if (!z2) {
            Iterator<NameValuePair> it = arrayList.iterator();
            do {
                if (!it.hasNext()) {
                    z = z2;
                    break;
                }
                next = it.next();
                if (next instanceof FileValuePair) {
                    break;
                }
            } while (!(next instanceof ByteArrayValuePair));
        } else {
            z = z2;
            break;
        }
        if (z) {
            if (abstractHttpEntity != null && (abstractHttpEntity instanceof MultipartEntity)) {
                MultipartEntity multipartEntity = (MultipartEntity) abstractHttpEntity;
                multipartEntity.appendPart(toPartArray(arrayList));
                return multipartEntity;
            }
            return new MultipartEntity(toPartArray(getMergedPostValue(abstractHttpEntity, arrayList)));
        }
        try {
            return new UrlEncodedFormEntity(this.mPostForm, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "JVM not support UTF_8?", e);
            throw new RuntimeException("JVM not support UTF_8?", e);
        }
    }

    private boolean isValidUri(Uri uri) {
        if (uri == null) {
            return false;
        }
        String scheme = uri.getScheme();
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    public IKscDecoder getDecoder() {
        return this.mDecoder;
    }

    private static boolean isFormEntity(AbstractHttpEntity abstractHttpEntity) {
        return abstractHttpEntity == null || (abstractHttpEntity instanceof MultipartEntity) || URLEncodedUtils.isEncoded(abstractHttpEntity);
    }

    private static Part[] toPartArray(List<NameValuePair> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        Part[] partArr = new Part[size];
        for (int i = 0; i < size; i++) {
            NameValuePair nameValuePair = list.get(i);
            if (nameValuePair instanceof FileValuePair) {
                try {
                    partArr[i] = new FilePart(nameValuePair.getName(), ((FileValuePair) nameValuePair).getFile());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("The file to be sent should be exist. file=" + ((FileValuePair) nameValuePair).getFile(), e);
                }
            } else if (nameValuePair instanceof ByteArrayValuePair) {
                partArr[i] = new FilePart(nameValuePair.getName(), nameValuePair.getValue(), ((ByteArrayValuePair) nameValuePair).getData());
            } else {
                partArr[i] = new StringPart(nameValuePair.getName(), nameValuePair.getValue(), "UTF-8");
            }
        }
        return partArr;
    }

    public IKscTransferListener getListener() {
        return this.mListener;
    }

    public long getContentLength() {
        AbstractHttpEntity abstractHttpEntity = this.mPostEntity;
        if (abstractHttpEntity == null) {
            return -1L;
        }
        return abstractHttpEntity.getContentLength();
    }
}
