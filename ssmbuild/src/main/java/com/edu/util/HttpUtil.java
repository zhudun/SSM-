package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;

import okhttp3.*;

import org.apache.http.*;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.io.IOException;

import java.lang.reflect.Field;

import java.util.List;

@Component

public class HttpUtil {

    private Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public String httpGet(String urlStr) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = null;

        HttpGet httpget = new HttpGet(urlStr);

        try {

            response = httpclient.execute(httpget);

            StatusLine status = response.getStatusLine();

            int state = status.getStatusCode();

            if (state == HttpStatus.SC_OK) {

                HttpEntity responseEntity = response.getEntity();

                String jsonString = EntityUtils.toString(responseEntity);

                return jsonString;

            }

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (response != null) {

                try {

                    response.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            try {

                httpclient.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return null;

    }

    public String httpPost(String urlStr, String data){

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = null;

// 创建httpPost

        try {

            HttpPost httpPost = new HttpPost(urlStr);

            httpPost.setHeader("Accept", "application/json");

            httpPost.setHeader("Content-Type", "application/json");

            String charSet = "UTF-8";

            StringEntity entity = new StringEntity(data, charSet);

            httpPost.setEntity(entity);

            response = httpclient.execute(httpPost);

            StatusLine status = response.getStatusLine();

            int state = status.getStatusCode();

            logger.info("state={}",state);

//logger.info("result={}",EntityUtils.toString(response.getEntity()));

            HttpEntity responseEntity = response.getEntity();

            String jsonString = EntityUtils.toString(responseEntity);

            return jsonString;

        } catch (Exception e) {

            logger.error("接口调用失败",e);

        }finally {

            if (response != null) {

                try {

                    response.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            try {

                httpclient.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return null;

    }

    public String httpURLPost(String url, List<NameValuePair> params){

        HttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);

        String s = "";

        try {

            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

            HttpResponse response = client.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();

            if(statusCode==200){

                HttpEntity entity = response.getEntity();

                s = EntityUtils.toString(entity);

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return s;

    }

    /**

     * 将对象转化为json数据

     *

     * @param o

     * 需要转化为json数据的对象

     * @return 对应对象的json数据格式

     */

    public JSONObject voToJson(Object o) {

        if (o == null) {

            return null;

        }

        Field[] fields = o.getClass().getDeclaredFields();

        JSONObject json = new JSONObject();

        for (Field field : fields) {

            field.setAccessible(true);

// 处理字段值

            try {

                json.put(field.getName(), field.get(o));

            } catch (IllegalAccessException e) {

                e.printStackTrace();

            }

        }

        return json;

    }

    /**

     * 将json数据包装

     *

     * @param jsonstr

     * 收到的json数据

     * @param o

     * 需要转化成的对象

     * @return 转化的对应对象

     */

    public Object toObject(String jsonstr, Class o) {

        JSONObject json = JSONObject.parseObject(jsonstr);

        Object obj = JSONObject.parseObject(json.toString(), o);

        return obj;

    }

    public String httpPost(String urlStr) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = null;

        HttpPost httpget = new HttpPost(urlStr);

        try {

            response = httpclient.execute(httpget);

            StatusLine status = response.getStatusLine();

            int state = status.getStatusCode();

            if (state == HttpStatus.SC_OK) {

                HttpEntity responseEntity = response.getEntity();

                String jsonString = EntityUtils.toString(responseEntity);

                return jsonString;

            }

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (response != null) {

                try {

                    response.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            try {

                httpclient.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return null;

    }

    public String doPost(String url,String json){

        String resp = null;

//1、创建OkHttpClient对象实例

        OkHttpClient okHttpClient = new OkHttpClient();

//2、创建Request对象

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(mediaType,json);

        Request request = new Request.Builder()

                .url(url)

                .post(requestBody)

                .build();

//3、执行Request请求

        try {

            Response response = okHttpClient.newCall(request).execute();

            logger.info("okhttp status==="+ JSON.toJSONString(response));

            resp = response.body().string();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return resp;

    }

}
