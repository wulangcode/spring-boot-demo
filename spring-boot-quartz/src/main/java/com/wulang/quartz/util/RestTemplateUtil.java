package com.wulang.quartz.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * spring restTemplate 工具类
 */
public final class RestTemplateUtil {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    private static SimpleClientHttpRequestFactory requestFactory = null;

    static {
        requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);                // 可配置化
        requestFactory.setReadTimeout(10000);                   // 可配置化
    }

    private RestTemplateUtil() {}

    private static HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", USER_AGENT);
        return headers;
    }

    /**
     * get 请求
     * @param remoteUrl
     * @param clazz
     * @param <T>
     * @return
     */
    public static<T> T getRequest(String remoteUrl, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpEntity<String> entity = new HttpEntity<>("parameters", getHttpHeader());
        T result = restTemplate.exchange(remoteUrl, HttpMethod.GET, entity, clazz).getBody();
        return result;
    }

    /**
     * post 请求
     * @param remoteUrl
     * @param clazz
     * @param params post参数
     * @param <T> 返回值
     * @return
     */
    public static<T> T postMethod(String remoteUrl, Class<T> clazz, MultiValueMap<String, String> params) {
        HttpHeaders httpHeader = getHttpHeader();
        httpHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);   // 表单提交方式
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeader);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        T result = restTemplate.exchange(remoteUrl, HttpMethod.POST, requestEntity, clazz).getBody();
        return result;
    }
}
