package pers.xiaofeng.daqiaobugong;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @description：HTTPS请求工具类（借鉴别人博客）
 * @author：xiaofeng
 * @date：2021/1/15/14:17
 */

public final class HttpRequest {
    private static Gson gson = new Gson();

    // 中传悦众赛事平台接入，悦点商城接入。商户id（2020项目）
    public final static String PLAT_ID = "10001";
    // 中传悦众赛事平台接入，悦点商城接入。生成签名密钥
    public final static String PLAT_KEY = "ZW71W8XBJ4I3E2LRHLGE4T0MABX3MOXO2RUFTZ5SVMT7ZUKPUEKAA49QQT4KDVPK";
    // 中传悦众赛事平台接入，悦点商城接入。URL
    public final static String URL = "http://192.168.50.118:8900";


    // charset=utf-8 必须要，不然会出现乱码
    /**
     * 表单数据 <br/>
     * <br/>
     * 在发送前编码所有字符（默认）<br/>
     * <br/>
     * application/x-www-form-urlencoded;charset=utf-8
     */
    public static String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded;charset=utf-8";

    /**
     * 表单数据 <br/>
     * <br/>
     * 不对字符编码。在使用包含文件上传控件的表单时，必须使用该值 <br/>
     * <br/>
     * multipart/form-data;charset=utf-8
     */
    public static String CONTENT_TYPE_FORM_DATA = "multipart/form-data;charset=utf-8";

    /**
     * text/plain;charset=utf-8
     */
    public static String CONTENT_TYPE_PLAIN = "text/plain;charset=utf-8";

    /**
     * 本次开发要求的格式
     * application/json;charset=utf-8
     */
    public static String CONTENT_TYPE_JSON = "application/json;charset=utf-8";

    /**
     * @Name: post。
     * @Description: 拼接url，将map转为String。
     * @Parameters: URL，要访问的url。
     * @Parameters: plat_id，赛事平台商户id
     * @Parameters: plat_key
     * @Parameters: path，访问路径
     * @Parameters: param，访问参数,Map形式。
     * @Return: String，转换后的url。
     */
    public static String post(String URL, String path, Map<String, String> params) throws Exception {
        StringBuilder param = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Entry<String, String> entry : params.entrySet()) {
                param.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return post(URL, path, param.toString());
    }

    public static void main(String[] args) throws Exception {
        String path = "/shop/goodslist";
        String url = URL + path;
        String result = post(url, path, "");
        System.out.println("\n" + gson.toJson(result));
    }

    /**
     * @Name: post。
     * @Description: 发送post请求，并返回响应数据。
     * @Parameters: URL，要访问的url。
     * @Parameters: path，访问路径
     * @Parameters: param，访问参数。
     * @Return: String，响应数据。
     */
    public static String post(String URL, String path, String param) throws Exception {

        String timestamp = Long.toString(new Date().getTime());
        String sign_str = PLAT_ID + timestamp + PLAT_KEY + path;
        String sign = HMACSHA256.getSignature(sign_str, PLAT_KEY);

        HttpURLConnection conn = null;
        // 数据输出流，输出参数信息
        DataOutputStream dataOut = null;
        // 数据输入流，处理服务器响应数据
        BufferedReader dataIn = null;

        try {
            URL url = new URL(URL);

            // 将url以open方法返回的urlConnection连接强转为HttpURLConnection连接(标识一个url所引用的远程对象连接)
            // 此时cnnection只是为一个连接对象,待连接中
            conn = (HttpURLConnection) url.openConnection();

            // 设置连接输出流为true,默认false (post请求是以流的方式隐式的传递参数)
            conn.setDoOutput(true);
            // 设置连接输入流为true
            conn.setDoInput(true);
            // 设置请求方式为post
            conn.setRequestMethod("POST");
            // post请求缓存设为false
            conn.setUseCaches(false);
            // 设置该HttpURLConnection实例是否自动执行重定向
            conn.setInstanceFollowRedirects(true);

            /**
             * addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}
             * setRequestProperty添加相同的key会覆盖value信息
             * setRequestProperty方法，如果key存在，则覆盖；不存在，直接添加。
             * addRequestProperty方法，不管key存在不存在，直接添加。
             */
            // 设置内容的类型,设置为经过urlEncoded编码过的from参数
            conn.setRequestProperty("Content-Type", HttpRequest.CONTENT_TYPE_JSON);
            conn.setRequestProperty("platid", PLAT_ID);
            conn.setRequestProperty("timestamp", timestamp);
            conn.setRequestProperty("sign", sign);

            // 建立连接
            // (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            conn.connect();

            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            dataOut = new DataOutputStream(conn.getOutputStream());
            // 将参数输出到连接
            dataOut.writeBytes(param != null ? param : "");
            // 输出完成后刷新并关闭流
            dataOut.flush();

            // 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
            dataIn = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            // 用来存储响应数据
            StringBuilder sb = new StringBuilder();
            // 循环读取流
            while ((line = dataIn.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 重要且易忽略步骤 (关闭流,切记!)
                if (dataOut != null) {
                    dataOut.close();
                }
                if (dataIn != null) {
                    dataIn.close();
                }
                // 销毁连接
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}