package pers.xiaofeng.test;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @description：byte[]、字符串转换为Json对象练习
 * @author：xiaofeng
 * @date：2020/11/4/22:03
 */
public class StringTransformJson {

    private static Gson gson = new Gson();

    public static void main(String[] args) {
        /**
         * 注意：在将 byte[] 转换为 String 对象时，采用 new String() 而不是 byte.toString(),
         *      后者会出现问题
         */
        String str1 = "[{\"colId\":360,\"state\":0,\"target\":53,\"taskId\":297},{\"colId\":360,\"state\":0,\"target\":49,\"taskId\":298},{\"colId\":360,\"state\":0,\"target\":45,\"taskId\":299},{\"colId\":360,\"state\":0,\"target\":41,\"taskId\":300},{\"colId\":360,\"state\":0,\"target\":34,\"taskId\":301},{\"colId\":360,\"state\":0,\"target\":31,\"taskId\":302},{\"colId\":360,\"state\":0,\"target\":28,\"taskId\":303},{\"colId\":360,\"state\":0,\"target\":23,\"taskId\":308},{\"colId\":360,\"state\":0,\"target\":19,\"taskId\":309},{\"colId\":360,\"state\":0,\"target\":15,\"taskId\":310},{\"colId\":360,\"state\":0,\"target\":11,\"taskId\":311},{\"colId\":360,\"state\":0,\"target\":7,\"taskId\":312}]";
        byte[] aByte = {91, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 53, 51, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 50, 57, 55, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 52, 57, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 50, 57, 56, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 52, 53, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 50, 57, 57, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 52, 49, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 51, 48, 48, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 51, 52, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 51, 48, 49, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 51, 49, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 51, 48, 50, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 50, 56, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 51, 48, 51, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 50, 51, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 51, 48, 56, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 49, 57, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 51, 48, 57, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 49, 53, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 51, 49, 48, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 49, 49, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 51, 49, 49, 125, 44, 123, 34, 99, 111, 108, 73, 100, 34, 58, 51, 54, 48, 44, 34, 115, 116, 97, 116, 101, 34, 58, 48, 44, 34, 116, 97, 114, 103, 101, 116, 34, 58, 55, 44, 34, 116, 97, 115, 107, 73, 100, 34, 58, 51, 49, 50, 125, 93};
        // byte[] bytes = str.getBytes();
        String str2 = new String(aByte);
        // String str3 = aByte.toString();
        System.out.println(str2);
        Type taskListType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> tasks1 = gson.fromJson(str2, taskListType);
        System.out.println("json字符串转为Java对象列表完成！taskList1 = " + gson.toJson(tasks1));
        List<Task> tasks2 = new ArrayList<>();
        int i = 0;
        for (Task task : tasks1) {
            // System.out.println("【验证前】的Task{}" + i + " = " + gson.toJson(task));
            task.setState(10);
            tasks2.add(task);
            // System.out.println("【验证后】的Task{}" + i + " = " + gson.toJson(task));
            i++;
        }
        System.out.println("任务检查完成！返回给任务服的任务列表taskList2 = " + gson.toJson(tasks2));
        String str = gson.toJson(tasks2);
        System.out.println(gson.toJson(str.getBytes()));
    }

}

class Task {
    private int taskId;     // 任务id
    private int colId;      // 列id
    private int target;
    private int state;      // =10表示已完成，=0未完成

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getColId() {
        return colId;
    }

    public void setColId(int colId) {
        this.colId = colId;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}