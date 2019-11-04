package com.yx.weather.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.util.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class HttpUtil {
    /**
     * 发送get请求
     *
     * @param url
     * @param param
     * @return
     * @Author yx
     */
    public static String getRequest(String url, String param) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        if(Strings.isNotEmpty(param)){
            url = url + "?" + param;
        }
        HttpGet httpGet = new HttpGet(url);
        StringBuilder builder = new StringBuilder();
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();

            InputStream inputStream = responseEntity.getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                builder.append(str);
            }
            //释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            log.error("HttpUtil: getRequest: hit exception ", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("HttpUtil: getRequest: hit exception ", e);
            }
        }

        return builder.toString();
    }
}
