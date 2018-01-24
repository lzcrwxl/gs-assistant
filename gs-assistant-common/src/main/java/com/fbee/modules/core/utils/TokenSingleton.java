package com.fbee.modules.core.utils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 单例获取access_token和jsapi_token，避免过度获取导致日上限
 * @author Baron
 * @version 2017-04-10
 */
public class TokenSingleton {
    private String appId = "wx3ba7ee60b26f2955"; // 必填，公众号的唯一标识
    private String secret = "e08223d116a6d9a60fc4bc76d49c3b3a";

    private Map<String, String> map = new HashMap<>();

    private TokenSingleton() {
    }

    private static TokenSingleton single = null;

    // 静态工厂方法
    public static synchronized TokenSingleton getInstance() {
        if (single == null) {
            single = new TokenSingleton();
        }
        return single;
    }

    public Map<String, String> getMap() {
        String time = map.get("time");
        String accessToken = map.get("access_token");
        Long nowDate = new Date().getTime();

        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 3000 * 1000) {
//                result = accessToken;
            System.out.println("accessToken存在，且没有超时 ， 返回单例");
        } else {
            System.out.println("accessToken 超时 ， 或者不存在 ， 重新获取");
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId + "&secret=" + secret;
            String resultTonket = TokenSingleton.httpRequest(url,"GET",null);
            System.out.println(resultTonket);
            String access_token= "";
            String jsapi_token = "";
            if(resultTonket != null && !"".equals(resultTonket) ){
                String[] tokenInfo = resultTonket.split("\"");
                if(tokenInfo.length == 7){
                    access_token = tokenInfo[3];
                    System.out.println(tokenInfo[3]);
                    System.out.println(tokenInfo.length);
                    url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token + "&type=jsapi";
                    String resultJsapi_ticket = TokenSingleton.httpRequest(url,"GET",null);
                    System.out.println(resultJsapi_ticket);
                    if(resultJsapi_ticket.split("\"").length == 13){
                        jsapi_token = resultJsapi_ticket.split("\"")[9];
                        System.out.println(resultJsapi_ticket.split("\"")[9]);
                        System.out.println(resultJsapi_ticket.split("\"").length);
                    }
                }
            }
            //"获取jsapi_token";
            map.put("time", nowDate + "");
            map.put("access_token", access_token);
            map.put("jsapi_token", jsapi_token);
//                result = access_token;
        }

        return map;
    }

    public static void main(String[] args) {
        Map temp1 = TokenSingleton.getInstance().getMap();
        Map temp2 = TokenSingleton.getInstance().getMap();
        System.out.println(temp1.get("access_token"));
        System.out.println(temp2.get("access_token"));
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public static TokenSingleton getSingle() {
        return single;
    }

    public static void setSingle(TokenSingleton single) {
        TokenSingleton.single = single;
    }

    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        //JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            //jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

}