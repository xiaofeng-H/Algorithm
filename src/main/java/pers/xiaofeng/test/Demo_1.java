package pers.xiaofeng.test;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import javax.jws.soap.SOAPBinding;
import java.io.*;

/**
 * @className: pers.xiaofeng.test.Demo_1
 * @description:
 * @author: xiaofeng
 * @create: 2021-01-22 17:15
 */
public class Demo_1 {

    @Test
    private void test1() {
        String cur_dir = System.getProperty("user.dir");
        String configPath = cur_dir + File.separator + "info.txt";

        System.out.println("cur_dir:" + cur_dir);
        System.out.println("File.separator:" + File.separator);
        System.out.println("configPath:" + configPath);

        String content = readFileToString(configPath);
        System.out.println("content:" + content);

        UserInfo userInfo = new Gson().fromJson(content, UserInfo.class);
        System.out.println("userInfo:" + userInfo);
    }

    public String readFileToString(String fileName) {
        StringBuffer buffer = new StringBuffer();
        BufferedReader bf = null;
        String s = null;
        FileReader newFile = null;
        try {
            newFile = new FileReader(fileName);
            bf = new BufferedReader(newFile);
            if (bf != null) {
                while (true) {
                    try {
                        if ((s = bf.readLine()) == null) break;
                        buffer.append(s.trim());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (newFile != null) {
                try {
                    newFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }
}

class UserInfo {
    private String name;
    private int age;
    private byte sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }
}
