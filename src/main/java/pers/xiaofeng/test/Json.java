package pers.xiaofeng.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
        test();

    }

    public static void test() throws IOException {
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
        } finally {
        }

        // 使用Gson将字符串序列化为UserOpt对象
        Gson gson = new Gson();
        List<UserOpt> list = gson.fromJson(jsonStr, new TypeToken<List<UserOpt>>() {
        }.getType());
        // 将序列化后的对象中的相应的字段拼接组成userOptMap的key,最后将对应的用户服操作方式赋值给userOptMap的value
        for (int i = 0; i < list.size(); ++i) {
            String str = String.valueOf(list.get(i).getGameId()) + String.valueOf(list.get(i).getOpt())
                    + String.valueOf(list.get(i).getParameter());
            userOptMap.put(str,list.get(i).getModule());
        }

        // for (int i = 0; i < userOpts.length; ++i) {
        //     for (int j = 0; j < 2; ++j) {
        //         System.out.println(userOpts[i][j]);
        //     }
        // }

        for(String key : userOptMap.keySet() ){
            System.out.println(key);
        }

        for(Integer value : userOptMap.values()){
            System.out.println(value);
        }
    }
}
