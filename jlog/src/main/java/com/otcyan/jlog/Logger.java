package com.otcyan.jlog;

import com.otcyan.jlog.core.LogLevel;
import com.otcyan.jlog.core.LogTool;
import com.otcyan.jlog.print.DefaultPrinter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import static android.util.Log.getStackTraceString;

/**
 * @author snamon log对外接口.
 */

public class Logger {
    /** 日志类名. */
    private static final String LOG_CLASS_NAME = Logger.class.getName();
    /** 日志的打印方法名. */
    private static final String LOG_PRINT_METHOD_NAME = "printLog";

    private DefaultPrinter mDefaultPrinter ;

    public void v(String tag, @NonNull String message) {
        printLog(LogLevel.VERBOSE, tag, null, message);
    }

    public void v(@NonNull String message) {
        printLog(LogLevel.VERBOSE, null, null, message);
    }

    public void d(String tag, @NonNull String message) {
        printLog(LogLevel.DEBUG, tag, null, message);
    }

    public void d(@NonNull String message) {
        printLog(LogLevel.DEBUG, null, null, message);
    }

    public void i(String tag, @NonNull String message) {
        printLog(LogLevel.INFO, tag, null, message);
    }

    public void i(@NonNull String message) {
        printLog(LogLevel.INFO, null, null, message);
    }

    public void w(String tag, @NonNull String message) {
        printLog(LogLevel.WARN, tag, null, message);
    }

    public void w(@NonNull String message) {
        printLog(LogLevel.WARN, null, null, message);
    }

    public void e(String tag, Throwable t, String message) {
        printLog(LogLevel.ERROR, tag, t, message);
    }

    public void e(Throwable t, String message) {
        printLog(LogLevel.ERROR, null, t, message);
    }

    public void e(String tag, @NonNull String message) {
        printLog(LogLevel.ERROR, tag, null, message);
    }

    public void e(@NonNull String message) {
        printLog(LogLevel.ERROR, null, null, message);
    }

    public void e(String tag, @NonNull Throwable t) {
        printLog(LogLevel.ERROR, tag, t, null);
    }

    public void e(@NonNull Throwable t) {
        printLog(LogLevel.ERROR, null, t, null);
    }

    private void printLog(@LogLevel String level, String tag, Throwable t, String message){
        if (TextUtils.isEmpty(message)) {
            if (t == null) {
                return; //
            }
            message = getStackTraceString(t);
        } else {
            if (t != null) {
                message += System.getProperty("line.separator") + getStackTraceString(t);
            }
        }
        StackTraceElement[] elements = new Throwable().getStackTrace();
        int index = getStackIndex(elements);
        if (index == -1) {
            throw new IllegalStateException("get StackTraceElement error!");
        }
        StackTraceElement element = elements[index];
        if (TextUtils.isEmpty(tag)) {
            tag = LogTool.getSimpleClassName(element.getClassName()); //默认以类名为tag
        }

        if(mDefaultPrinter==null){ //考虑并发
            synchronized (Logger.class){
                if(mDefaultPrinter == null)
                    mDefaultPrinter = new DefaultPrinter();
            }
        }
        switch (level) {
            case LogLevel.VERBOSE:
            case LogLevel.DEBUG:
            case LogLevel.INFO:
            case LogLevel.WARN:
            case LogLevel.ERROR:
                mDefaultPrinter.printConsole(level, tag, message, element);
                break;
            case LogLevel.JSON:

                break;
            default:
                break;
        }
    }

    /**
     * @param elements 堆栈元素
     * @return 索引位置，-1 - 不可用
     */
    private int getStackIndex(@NonNull StackTraceElement[] elements) {
        boolean isChecked = false;
        StackTraceElement element;
        for (int i = 0; i < elements.length; i++) {
            element = elements[i];
            if (LOG_CLASS_NAME.equals(element.getClassName())
                    && LOG_PRINT_METHOD_NAME.equals(element.getMethodName())) {
                isChecked = true;
            }
            if (isChecked) {
                int index = i + 2;
                if (index < elements.length) {
                    return index;
                }
            }
        }
        return -1;
    }

}
