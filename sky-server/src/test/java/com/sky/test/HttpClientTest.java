package com.sky.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class HttpClientTest {

    @Test
    public void testGet() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");

        CloseableHttpResponse response = httpClient.execute(httpGet);

        System.out.println("状态码" + response.getStatusLine().getStatusCode());

        HttpEntity entity = response.getEntity();
        System.out.println("数据为" + EntityUtils.toString(entity));

        response.close();
        httpClient.close();
    }

    @Test
    public void testPost() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");

        JSONObject json = new JSONObject();
        json.put("username","admin");
        json.put("password","123456");

        StringEntity entity = new StringEntity(json.toString());
        entity.setContentEncoding("utf-8");
        entity.setContentType("application/json");

        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(httpPost);

        System.out.println("状态码" + response.getStatusLine().getStatusCode());
        System.out.println("数据为" + EntityUtils.toString(response.getEntity()));

        response.close();
        httpClient.close();

    }
}
