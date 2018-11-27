package com.course.httpclient.cookies;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CloseableHttpClientDemo {

    private String urlf;
    private String urls;
    private String result;
    private CookieStore cookieStore;
    private ResourceBundle bundle;

    @BeforeTest
    public void beforeTest(){
        bundle=ResourceBundle.getBundle("application", Locale.CHINA);
        urlf=bundle.getString("test.url")+bundle.getString("getCookies.uri");
        urls=bundle.getString("test.url")+bundle.getString("getPostWithCookies.uri");

        System.out.println(urlf);
        System.out.println(urls);
    }

    @Test
    public void getCookiesTest() throws Exception{
       // CloseableHttpClient client= HttpClients.createDefault();
        DefaultHttpClient client=new DefaultHttpClient();
        HttpGet get=new HttpGet(urlf);

        CloseableHttpResponse response=client.execute(get);
        HttpEntity entity=response.getEntity();

        result=EntityUtils.toString(entity,"utf-8");
        cookieStore=client.getCookieStore();
        List<Cookie> list=cookieStore.getCookies();
        for(Cookie cookie:list){
            System.out.println(cookie.getName()+"---------"+cookie.getValue());
        }
        System.out.println("---------------------------------");
        System.out.println(result);
    }


    @Test(dependsOnMethods = {"getCookiesTest"})
    public void getPostWithCookiesTest() throws Exception{

        //拼接最终的测试连接
        //通过ResourceBundle的getBundle("application", Locale.CHINA)获取连接地址

        //声明一个client对象用来执行post或者get
        DefaultHttpClient client=new DefaultHttpClient();

        //声明一个方法
       HttpPost post=new HttpPost(urls);
        //添加参数
        JSONObject param=new JSONObject();
        param.put("name","zhangsan");
        param.put("age","18");

        StringEntity stringEntity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(stringEntity);


        //添加请求头信息
        post.setHeader("content-type","application/json");


        //声明一个对象来进行响应结果的存储
        //已声明result

        //设置cookies信息
       client.setCookieStore(cookieStore);

        //执行方法
        HttpResponse response=client.execute(post);

        //获取响应结果
        HttpEntity entity=response.getEntity();
        result=EntityUtils.toString(entity,"utf-8");
       int status=response.getStatusLine().getStatusCode();



        //处理响应结果

        if(200==status){
            System.out.println(result);
        }


    }


}
