package pers.xiaofeng.daqiaobugong;

import com.google.gson.Gson;
import oracle.jrockit.jfr.VMJFR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.*;

/**
 * @description：HTTPS请求工具类（借鉴别人博客）
 * @author：xiaofeng
 * @date：2021/1/15/14:17
 */

public final class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
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

    /**
     * description: 使用异步线程进行HTTP请求，此时主线程处于阻塞状态，阻塞时间手动确定，超时或其他异常时主线程都会抛出异常然后进行异常处理，以使
     * 主线程不会一直处于阻塞状态
     */
    @Test
    public static void demo() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        System.out.println("main方法开始运行=====>线程：" + Thread.currentThread().getName());

        // 使用新的线程池进行异步处理
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("正在向HTTP发送请求=====>线程：" + Thread.currentThread().getName());
                return test1();
            } catch (Exception e) {
                System.out.println("请求出现异常了！！！");
                return null;
            }
        }, executorService);

        try {
            Thread.sleep(1);
            System.out.println("线程 " + Thread.currentThread().getName() + " 睡眠已醒，正在杀掉线程 " + executorService.getClass().getName());
//            executorService.shutdownNow();
            /**
             * Thread.currentThread.interrupt() 只对阻塞线程起作用，
             *
             * 当线程阻塞时，调用interrupt方法后，该线程会得到一个interrupt异常，可以通过对该异常的处理而退出线程
             *
             * 对于正在运行的线程，没有任何作用！
             */
//            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Thread.currentThread().isInterrupted()) {
            System.out.println("主线程处于阻塞状态，收到中断请求=====>线程：" + Thread.currentThread().getName());
        }

//        future.thenAccept((result) -> {
//            System.out.println(result);
//            System.out.println("Http请求结束，开始进行下一步的处理");
//            Reporter.log("Http请求结束，开始进行下一步的处理=====>线程：" + Thread.currentThread().getName());
//            executorService.shutdownNow();
//        });

        try {
            if (future.get(500, TimeUnit.MILLISECONDS).equals(true)) {
                System.out.println("HTTP请求正常处理，可以进行下一步操作=====>线程：" + Thread.currentThread().getName());
//                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

            System.out.println("主线程处于阻塞状态，收到中断请求！！！=====>线程：" + Thread.currentThread().getName());

        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("主线程出现问题！！！=====>线程：" + Thread.currentThread().getName());

        } catch (TimeoutException e) {
            e.printStackTrace();
            System.out.println("主线程处理超时！！！=====>线程：" + Thread.currentThread().getName());

        }

        System.out.println("main方法运行结束=====>线程：" + Thread.currentThread().getName());

    }

    private static void test2() {
        System.out.println("测试方法2！=====>线程：" + Thread.currentThread().getName());
    }

    @Test
    private static boolean test1() {
        System.out.println("测试方法1！start=====>线程：" + Thread.currentThread().getName());
        Thread.currentThread().interrupt();
        String path = "/shop/buy";
        String url = URL + path;
        Map<String, Object> params = new HashMap<>();
        // 游戏平台中的订单号，唯一
        String platOrderId = "031cecdb31000000";
        // 获取用户个人信息
        String str = "{\"username\":\"张三\",\"userphone\":\"15171507551\",\"useraddress\":{\"proname\": \"湖北\",\"cityname\":\"武汉\",\"areaname\":\"洪山\",\"streetname\":\"关东\",\"address\":\"17楼4号\"}}";
        UserInformation userInfo = gson.fromJson(str, UserInformation.class);
        params.put("platorderid", platOrderId);
        params.put("userid", 656818);
        params.put("userphone", userInfo.getUserphone());
        params.put("useraddress", userInfo.getUseraddress());
        params.put("username", userInfo.getUsername());
        params.put("goodsid", 232253);
        params.put("amount", 1);
        params.put("createtime", new Date().getTime());
        String string = gson.toJson(params);
        System.out.println(params);
        System.out.println("兑换悦点商城商品发送请求=====>线程：" + Thread.currentThread().getName());
        String result = HttpRequest.post(url, path, string);
        System.out.println("兑换悦点商城商品发送返回结果result:" + result);

        System.out.println("测试方法1！end=====>线程：" + Thread.currentThread().getName());

        return result != null;
    }

    /**
     * @Name: post。
     * @Description: 发送post请求，并返回响应数据。
     * @Parameters: URL，要访问的url。
     * @Parameters: path，访问路径
     * @Parameters: param，访问参数。
     * @Return: String，响应数据。
     */
    public static String post(String URL, String path, String param) {

        String timestamp = Long.toString(new Date().getTime());
        String sign_str = PLAT_ID + timestamp + PLAT_KEY + path;
        String sign = HMACSHA256.getSignature(sign_str, PLAT_KEY);

        System.out.println(timestamp);
        System.out.println(sign);


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

            /*
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

            // 输出连接信息，实际使用时去掉
//            outConnInfo(conn, url);

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

    /**
     * 输出连接信息
     */
    private static void outConnInfo(HttpURLConnection conn, URL url) throws IOException {
        // url与url = conn.getURL()是等价的
        System.out.println("conn.getResponseCode():" + conn.getResponseCode());
        System.out.println("conn.getURL():" + conn.getURL());
        System.out.println("conn.getRequestMethod():" + conn.getRequestMethod());
        System.out.println("conn.getContentType():" + conn.getContentType());
        System.out.println("conn.getReadTimeout():" + conn.getReadTimeout());
        System.out.println("conn.getResponseMessage():" + conn.getResponseMessage());
        System.out.println("url.getDefaultPort():" + url.getDefaultPort());
        System.out.println("url.getFile():" + url.getFile());
        System.out.println("url.getHost():" + url.getHost());
        System.out.println("url.getPath():" + url.getPath());
        System.out.println("url.getPort():" + url.getPort());
        System.out.println("url.getProtocol():" + url.getProtocol());
        System.out.println("url.getQuery():" + url.getQuery());
        System.out.println("url.getRef():" + url.getRef());
        System.out.println("url.getUserInfo():" + url.getUserInfo());
    }
}

/**
 * 用户个人信息
 */
class UserInformation {
    private String username;
    private String userphone;
    private Address useraddress;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public Address getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(Address useraddress) {
        this.useraddress = useraddress;
    }
}

/**
 * 用户详细地址
 */
class Address {
    private String proname;
    private String cityname;
    private String areaname;
    private String streetname;
    private String address;

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}