package com.mai.xmai_fast_lib.utils;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 将Log日志写入文件中
 * Created by mai on 2017/3/14.
 */
public class LogToFile {


    private static Lock lock = new ReentrantLock();// 锁对象

    private static String logPath = Environment.getExternalStorageDirectory().getPath()+"/MaiLog";//log日志存放路径

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);//日期格式;

    private static Date date = new Date();//因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中;

    /**
     * 将log信息写入文件中
     *
     * @param tag
     * @param msg
     */
    public static void writeToFile(String tag, String msg) {

        lock.lock();
        String fileName = logPath + "/log_" + dateFormat.format(new Date()) + ".log";//log日志名，使用时间命名，保证不重复
        String log = dateFormat.format(date) + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制

        //如果父路径不存在
        File file = new File(logPath);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {

            fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            lock.unlock();
        }

    }

}