package pers.xiaofeng.daqiaobugong;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description：读取Json文件练习
 * @author：晓峰
 * @date：2020/9/3/9:56
 */
public class Json {

    private static Map<String, Integer> userOptMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        test1();
        test2();

    }

    public static void test1() throws IOException {
        StringBuffer sb = null;
        String jsonStr = null;
        try {
            // 读取用户操作配置文件
            File f = new File("src/main/resources/userOptConfig.json");
            FileReader fileReader = new FileReader(f);
            Reader reader = new InputStreamReader(new FileInputStream(f), "Utf-8");
            int ch = 0;
            sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用Gson将字符串序列化为UserOpt对象
        Gson gson = new Gson();
        List<UserOpt> list = gson.fromJson(jsonStr, new TypeToken<List<UserOpt>>() {
        }.getType());
        // 将序列化后的对象中的相应的字段拼接组成userOptMap的key,最后将对应的用户服操作方式赋值给userOptMap的value
        for (int i = 0; i < list.size(); ++i) {
            String str = String.valueOf(list.get(i).getDeskId()) + String.valueOf(list.get(i).getGameId())
                    + String.valueOf(list.get(i).getOpt()) + String.valueOf(list.get(i).getParameter());
            userOptMap.put(str, list.get(i).getModule());
        }

        // for(String key : userOptMap.keySet() ){
        //     System.out.println(key + ":" + userOptMap.get(key));
        // }
    }

    public static void test2() {

        long deskId = 1197078913024000l;                    // 桌子id
        int gameId = 71;                                    // 游戏id
        int opt = 206;                                      // 操作码
        int parameter = 1;                                  // 操作码参数

        String key;     // 操作配置表中的主键（桌子id + 游戏id + 操作码 + 操作码参数）
        int module;     // 对应的处理模式

        // 拼接key
        String desk1 = Long.toBinaryString(deskId);
        String desk2 = desk1.substring(1, 3);
        int desk = Integer.parseInt(desk2, 2);
        key = String.valueOf(desk) + String.valueOf(gameId)
                + String.valueOf(opt) + String.valueOf(parameter);
        System.out.println("桌子id解析：" + deskId);
        System.out.println("二进制 = " + desk1);
        System.out.println("取二进制前两位 = " + desk2);
        System.out.println("取二进制前两位转为int = " + desk);
        try {
            // 获取key对应的module
            module = userOptMap.get(key);

            System.out.println("key =" + key);
            System.out.println("module =" + module);
        } catch (Exception e) {
            return;
        }
    }
}
