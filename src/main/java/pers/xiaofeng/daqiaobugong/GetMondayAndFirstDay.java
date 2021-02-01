package pers.xiaofeng.daqiaobugong;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description：获取当前时间所在一周的周一和当月一号
 * @author：xiaofeng
 * @date：2020/12/16/10:41
 */
public class GetMondayAndFirstDay {

    public static void main(String[] args) {
        getMondayAndFirstDay();
    }

    /**
     * 当前时间所在一周的周一和当月一号
     *
     * @return int[0]:当前时间周一所对应的时间 | int[1]:当前时间一号所对应的时间
     */
    public static long[] getMondayAndFirstDay() {
        long[] time = new long[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Calendar cal = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            dayWeek = 8;
        }
        // 输出要计算日期
        System.out.println("要计算日期为:" + sdf.format(cal.getTime()));
        System.out.println("要计算日期的时间戳为:" + cal.getTime().getTime());

        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);
        System.out.println("所在周星期一的日期：" + weekBegin);
        System.out.println("所在周星期一的日期：" + mondayDate.getTime());


        cal.add(Calendar.DATE, 4 + cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String weekEnd = sdf.format(sundayDate);
        System.out.println("所在周星期日的日期：" + weekEnd);

        Calendar cal2 = Calendar.getInstance();
        int day = cal2.get(Calendar.DAY_OF_MONTH);
        System.out.println("cal2:" + sdf.format(cal2.getTime()));
        System.out.println("day:" + day);
        cal2.add(Calendar.DAY_OF_MONTH, -1 * (day - 1));
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        Date firstDay = cal2.getTime();
        time[1] = firstDay.getTime() / 1000;
        System.out.println("所在月1号的日期：" + sdf.format(firstDay));
        System.out.println("所在月1号的日期：" + time[1]);

        return time;
    }
}
