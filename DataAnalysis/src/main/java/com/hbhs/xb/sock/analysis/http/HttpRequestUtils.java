package com.hbhs.xb.sock.analysis.http;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookiePolicy;
import java.util.List;


/**
 * Http request common tools, based on httpClient
 *
 * @author Walter.xu
 */
public class HttpRequestUtils {
    public static String ENCODE = "GBK";
    /**
     * @param url           Example: <br>http://localhost:8080/sigmad/api/tracking/advertiser/query <br>
     *                      http://localhost:8080/sigmad/api/tracking/advertiser/query?advertiserID=1
     * @param headerList    could be null, if not, add the not-null values into http header
     * @param parameterList could be null, if not, add the not-null values after 'url' parameter
     * @return response data
     */
    public static String get(String url, List<KeyValuePair> headerList, List<KeyValuePair> parameterList) {
        return get(url, null, headerList, parameterList);
    }
    /**
     * @param url           Example: <br>http://localhost:8080/sigmad/api/tracking/advertiser/query <br>
     *                      http://localhost:8080/sigmad/api/tracking/advertiser/query?advertiserID=1
     * @param cookieList    Cookie list
     * @param headerList    could be null, if not, add the not-null values into http header
     * @param parameterList could be null, if not, add the not-null values after 'url' parameter
     * @return response data
     */
    public static String get(String url, List<Cookie> cookieList, List<KeyValuePair> headerList, List<KeyValuePair> parameterList) {
        return get(url, cookieList, headerList, parameterList, 0);
    }

    /**
     * @param url           Example: <br>http://localhost:8080/sigmad/api/tracking/advertiser/query <br>
     *                      http://localhost:8080/sigmad/api/tracking/advertiser/query?advertiserID=1
     * @param cookieList    Cookie list
     * @param headerList    could be null, if not, add the not-null values into http header
     * @param parameterList could be null, if not, add the not-null values after 'url' parameter
     * @param timeoutSeconds timeout by seconds
     * @return response data
     */
    public static String get(String url, List<Cookie> cookieList, List<KeyValuePair> headerList, List<KeyValuePair> parameterList, int timeoutSeconds) {
        HttpClient client = new HttpClient();
        client.getParams().setSoTimeout(timeoutSeconds * 1000);
        if (cookieList!=null&&cookieList.size()>0)
            cookieList.forEach(cookie -> client.getState().addCookie(cookie));
        if (parameterList != null && parameterList.size() > 0) {
            StringBuilder queryStr = new StringBuilder();
            for (KeyValuePair pair : parameterList) {
                if (pair != null && pair.checkValid()) {
                    queryStr.append(pair.key).append("=").append(pair.value).append("&");
                }
            }
            if (queryStr.length() > 0) {
                if (!url.contains("?")) {
                    url += "?";
                }
                url += queryStr.substring(0, queryStr.length() - 1);
            }
        }
        GetMethod get = new GetMethod(url);
        if (headerList != null && headerList.size() > 0) {
            for (KeyValuePair pair : headerList) {
                if (pair != null && pair.checkValid()) {
                    get.addRequestHeader(pair.key, pair.value);
                }
            }
        }
        try {
            client.executeMethod(get);
            if (200 == get.getStatusCode()) {
//				return new String(get.getResponseBody(), "UTF-8");
                return transInputstreamToString(get.getResponseBodyAsStream());
            } else {
                // If http body return values and is json format, then return http body
                String responseData = transInputstreamToString(get.getResponseBodyAsStream());
                if (responseData.startsWith("{")) {
                    return responseData;
                }
                // else throw exception
                throw new IllegalArgumentException(get.getStatusLine().toString());
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Http access error for url:" + url + ", message=" + e.getMessage(), e);
        }
    }

    /**
     * trans inputsteam into str
     *
     * @param is
     * @return
     */
    private static String transInputstreamToString(InputStream is) {
        StringBuilder str = new StringBuilder();
        BufferedReader reader = null;
        try {
            if (ENCODE!=null&&!ENCODE.trim().equals(""))
                reader = new BufferedReader(new InputStreamReader(is, ENCODE));
            else
                reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                str.append(line + "\n");
                line = reader.readLine();
            }
        } catch (Exception e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
        return str.toString();
    }

    /**
     * Post url by http
     *
     * @param url  post url address.
     * @param headerList header list need to add
     * @param parameterList  parameter list need to add
     * @return response data
     */
    public static String post(String url, List<KeyValuePair> headerList, List<KeyValuePair> parameterList) {
        return post(url, null, headerList, parameterList);
    }
    /**
     * Post url by http
     *
     * @param url  post url address.
     * @param cookieList cookie list need to add
     * @param headerList header list need to add
     * @param parameterList  parameter list need to add
     * @return response data
     */
    public static String post(String url, List<Cookie> cookieList, List<KeyValuePair> headerList, List<KeyValuePair> parameterList) {
        return post(url, cookieList, headerList, parameterList, 0);
    }

    /**
     * Post url by http
     *
     * @param url  post url address.
     * @param cookieList cookie list need to add
     * @param headerList header list need to add
     * @param parameterList  parameter list need to add
     * @param timeoutSeconds time out
     * @return response data
     */
    public static String post(String url, List<Cookie> cookieList, List<KeyValuePair> headerList, List<KeyValuePair> parameterList, int timeoutSeconds) {
        HttpClient client = new HttpClient();
        client.getParams().setSoTimeout(timeoutSeconds * 1000);
        if (cookieList!=null&&cookieList.size()>0)
            cookieList.forEach(cookie->client.getState().addCookie(cookie));
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        if (headerList != null && headerList.size() > 0) {
            for (KeyValuePair pair : headerList) {
                if (pair != null && pair.checkValid()) {
                    post.addRequestHeader(pair.key, pair.value);
                }
            }
        }
        if (parameterList != null && parameterList.size() > 0) {
            for (KeyValuePair pair : parameterList) {
                if (pair != null && pair.checkValid()) {
                    post.setParameter(pair.key, pair.value);
                }
            }
        }
        try {
            client.executeMethod(post);
            if (200 == post.getStatusCode()) {
//				return new String(post.getResponseBody(), "UTF-8");
                return transInputstreamToString(post.getResponseBodyAsStream());
            } else {
                // If http body return values and is json format, then return http body
                String responseData = transInputstreamToString(post.getResponseBodyAsStream());
                if (responseData != null && responseData.startsWith("{")) {
                    return responseData;
                }
                // else throw exception
                throw new IllegalArgumentException(post.getStatusLine().toString());
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Http access error for url:" + url + ", message=" + e.getMessage(), e);
        }
    }
    /**
     * Post json data to server
     * @param url  post url address.
     * @param headerList header list need to add
     * @param jsonBody  json body data
     * @return response data
     */
    public static String postJSON(String url, List<KeyValuePair> headerList, String jsonBody){
        return postJSON(url,null,headerList,jsonBody);
    }
    /**
     * Post json data to server
     * @param url  post url address.
     * @param cookieList cookie list need to add
     * @param headerList header list need to add
     * @param jsonBody  json body data
     * @return response data
     */
    public static String postJSON(String url, List<Cookie> cookieList, List<KeyValuePair> headerList, String jsonBody) {
        HttpClient client = new HttpClient();
        if (cookieList!=null&&cookieList.size()>0)
            cookieList.forEach(cookie->client.getState().addCookie(cookie));
        PostMethod post = new PostMethod(url);
        if (headerList != null && headerList.size() > 0) {
            for (KeyValuePair pair : headerList) {
                if (pair != null && pair.checkValid()) {
                    post.addRequestHeader(pair.key, pair.value);
                }
            }
        }
        try {
            if (jsonBody != null && !"".equals(jsonBody)) {
                RequestEntity entity = new StringRequestEntity(jsonBody, "application/json", "UTF-8");
                post.setRequestEntity(entity);
            }
            client.executeMethod(post);
            if (200 == post.getStatusCode()) {
                return new String(post.getResponseBody(), "UTF-8");
            } else
                throw new IllegalArgumentException(post.getStatusLine().toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Http access error for url:" + url + ", message=" + e.getMessage(), e);
        }
    }

    /**
     * patch request to server
     * @param url url
     * @param headerList header list need to add
     * @param parameterList parameter list need to add
     * @return response data
     */
    public static String patch(String url, List<KeyValuePair> headerList, List<KeyValuePair> parameterList) {
        return patch(url, null, headerList, parameterList);
    }
    /**
     * patch request to server
     * @param url url
     * @param cookieList cookie list need to add
     * @param headerList header list need to add
     * @param parameterList parameter list need to add
     * @return response data
     */
    public static String patch(String url, List<Cookie> cookieList, List<KeyValuePair> headerList, List<KeyValuePair> parameterList) {
        return patch(url, cookieList, headerList, parameterList, 0);
    }

    /**
     * patch request to server
     * @param url url
     * @param cookieList cookie list need to add
     * @param headerList header list need to add
     * @param parameterList parameter list need to add
     * @param timeoutSeconds time out
     * @return response data
     */
    public static String patch(String url, List<Cookie> cookieList, List<KeyValuePair> headerList, List<KeyValuePair> parameterList, int timeoutSeconds) {
        HttpClient client = new HttpClient();
        client.getParams().setSoTimeout(timeoutSeconds * 1000);

        PostMethod patch = new PostMethod(url) {
            @Override
            public String getName() {
                return "PATCH";
            }
        };
        patch.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        if (headerList != null && headerList.size() > 0) {
            for (KeyValuePair pair : headerList) {
                if (pair != null && pair.checkValid()) {
                    patch.addRequestHeader(pair.key, pair.value);
                }
            }
        }
        if (parameterList != null && parameterList.size() > 0) {
            for (KeyValuePair pair : parameterList) {
                if (pair != null && pair.checkValid()) {
                    patch.setParameter(pair.key, pair.value);
                }
            }
        }
        try {
            client.executeMethod(patch);
            if (200 == patch.getStatusCode()) {
//				return new String(post.getResponseBody(), "UTF-8");
                return transInputstreamToString(patch.getResponseBodyAsStream());
            } else {
                // If http body return values and is json format, then return http body
                String responseData = transInputstreamToString(patch.getResponseBodyAsStream());
                if (responseData != null && responseData.startsWith("{")) {
                    return responseData;
                }
                // else throw exception
                throw new IllegalArgumentException(patch.getStatusLine().toString());
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Http access error for url:" + url + ", message=" + e.getMessage(), e);
        }
    }
    /**
     * Patch data to server
     * @param url url
     * @param headerList header list need to add
     * @param jsonBody json body
     * @return
     */
    public static String patchJSON(String url, List<KeyValuePair> headerList, String jsonBody){
        return patchJSON(url, null, headerList, jsonBody);
    }
    /**
     * Patch data to server
     * @param url url
     * @param cookieList cookie list need to add
     * @param headerList header list need to add
     * @param jsonBody json body
     * @return
     */
    public static String patchJSON(String url, List<Cookie> cookieList, List<KeyValuePair> headerList, String jsonBody) {
        HttpClient client = new HttpClient();
        PostMethod patch = new PostMethod(url) {
            @Override
            public String getName() {
                return "PATCH";
            }
        };
        if (headerList != null && headerList.size() > 0) {
            for (KeyValuePair pair : headerList) {
                if (pair != null && pair.checkValid()) {
                    patch.addRequestHeader(pair.key, pair.value);
                }
            }
        }
        try {
            if (jsonBody != null && !"".equals(jsonBody)) {
                RequestEntity entity = new StringRequestEntity(jsonBody, "application/json", "UTF-8");
                patch.setRequestEntity(entity);
            }
            client.executeMethod(patch);
            if (200 == patch.getStatusCode()) {
                return new String(patch.getResponseBody(), "UTF-8");
            } else
                throw new IllegalArgumentException(patch.getStatusLine().toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Http access error for url:" + url + ", message=" + e.getMessage(), e);
        }
    }

    /**
     * 键值对
     *
     * @author Walter.xu
     * @ClassName: KeyValuePair
     * @date 2016年2月17日 下午6:44:45
     */
    public static class KeyValuePair {
        private String key;
        private String value;

        public KeyValuePair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public boolean checkValid() {
            if (key != null && !"".equals(key.trim()) &&
                    value != null && !"".equals(value.trim())) {
                return true;
            }
            return false;
        }
    }
}
