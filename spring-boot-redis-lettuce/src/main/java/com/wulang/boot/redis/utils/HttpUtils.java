package com.wulang.boot.redis.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
public class HttpUtils {


    public static void sendGet(String url, Map<String, String> map) throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder(url);

        for (Map.Entry<String, String> parameter : map.entrySet()) {
            uriBuilder.addParameter(parameter.getKey(), parameter.getValue());
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        log.info("请求状态" + statusCode);
        HttpEntity entity = httpResponse.getEntity();
        String s = EntityUtils.toString(entity, "utf-8");
        log.info(s);
        httpResponse.close();
        httpClient.close();

    }

    public static void sendGet(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        log.info("请求状态" + statusCode);
        HttpEntity entity = httpResponse.getEntity();
        String s = EntityUtils.toString(entity, "utf-8");
        log.info(s);
        httpResponse.close();
        httpClient.close();
    }


    public static void doPost(String url, Integer userId) {
        Map<String, String> map = new HashMap<>(2);
        map.put("userID", userId.toString());
        sendPost(url, userId, map);
    }


    public static String sendPost(String url){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String s = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);
            httpResponse = httpClient.execute(httpPost);
            s = EntityUtils.toString(httpResponse.getEntity());
            log.info("请求状态:{ 返回状态:" + s + "}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (httpResponse != null){
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }

    public static void sendPost(String url, Integer userId, Map<String, String> map) {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);
            //TODO
            httpPost.addHeader("Authorization", MD5Utils.getMD5Format((userId.toString() + "wulang")));

            ArrayList<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> parameter : map.entrySet()) {
                list.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
            }
            StringEntity entity = new UrlEncodedFormEntity(list, "utf-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            String s = EntityUtils.toString(httpResponse.getEntity());
            log.info("请求状态:{ userId = " + userId + ",返回状态:" + s + "}");
            httpResponse.close();
            httpClient.close();
        } catch (IOException e) {
            log.info("请求失败:{ userId = " + userId + ",异常:" + Arrays.toString(e.getStackTrace()) + "}");
            e.printStackTrace();
        }
    }

    /**
     * httpGet 请求
     * @param url 请求地址
     * @param params    参数
     * @param charset   编码
     * @return
     */
    public static String doGet(String url, Map<String, String> params, String charset) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        List<NameValuePair> nvps = new ArrayList<>();
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpGet.setURI(URI.create(url + "?" + URLEncodedUtils.format(nvps, charset)));
        }
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return getEntity(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * httpPost 请求
     * @param url 请求地址
     * @param params    参数
     * @param charset   编码
     * @return
     */
    public static String doPost(String url, Map<String, String> params, String charset) {
        String result = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        //TODO
        httpPost.addHeader("Authorization", MD5Utils.getMD5Format((params.get("userID") + "wulang")));
        List<NameValuePair> nvps = new ArrayList<>();
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            if (!CollectionUtils.isEmpty(nvps)) {
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
                httpPost.setEntity(formEntity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            result = getEntity(httpResponse);
            log.info("请求状态:{ userId = " + params.get("userID") + ",返回状态:" + result + "}");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * httpPost 请求 JSON
     * @param url 请求地址
     * @param params    参数
     * @param charset   编码
     * @return
     */
    public static String doPostByJSON(String url, Map<String, String> params, String charset) {
        String result = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
        String entity = JSON.toJSONString(params);
        httpPost.setEntity(new StringEntity(entity, Charset.forName("utf-8")));
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            return getEntity(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getEntity(HttpResponse httpResponse) throws IOException {
        if (httpResponse != null) {
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity responseEntity = httpResponse.getEntity();
                if (responseEntity != null) {
                    return EntityUtils.toString(responseEntity);
                }
            } else {
                return "Error Response " + httpResponse.getStatusLine().toString();
            }
        }
        return "";
    }
}

