package com.example.signin.util;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:HttpClient
 * @Description: TODO
 */
@Slf4j
public class HttpClientUtis {

    /**
     * 处理get请求
     *
     * @param params
     * @param url
     * @return
     */
    public static String doGet(Map<String, String> params, String url) {

        //获取httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //封装响应结果
        String result = "";
        CloseableHttpResponse response = null;
        try {
            //封装url
            URIBuilder builder = new URIBuilder(url);
            //拼接参数
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key));
                }
            }
            //生成uri
            URI uri = builder.build();
            //创建HttpGet请求
            HttpGet httpGet = new HttpGet(uri);
            //执行get请求,获取响应
            response = httpClient.execute(httpGet);
            //获取响应状态
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                //正常请求-响应
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            log.error("httpclient doGet method Exception:--->" + e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回响应
        return result;
    }

    /**
     * 普通post请求
     *
     * @param params
     * @param url
     * @return
     */
    public static String doPost(Map<String, String> params, String url) {

        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //封装结果
        String result = "";
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            //HttpPost httpPost = new HttpPost(url);
            HttpPost httpPost = new HttpPost(url);
            //创建参数列表
            if (params != null) {
                List<NameValuePair> list = new ArrayList<>();
                for (String key : params.keySet()) {
                    list.add(new BasicNameValuePair(key, params.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            //响应
            response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                //成功请求获得响应
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("httpclient doPost method Exception:--->" + e.getMessage());
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 无参get请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(null, url);
    }

    /**
     * 无参post请求
     *
     * @param url
     * @return
     */
    public static String doPost(String url) {
        return doPost(null, url);
    }
}
