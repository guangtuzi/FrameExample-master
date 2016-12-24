package com.otcyan.jlog;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.otcyan.jlog.core.LogLevel;
import com.otcyan.jlog.core.LogTool;
import com.otcyan.jlog.print.DefaultPrinter;
import com.otcyan.jlog.print.JsonPrinter;
import com.otcyan.jlog.print.ObjPrinter;

import static android.util.Log.getStackTraceString;

/**
 * @author snamon
 * log对外接口.
 */

public class JLog {
    /** 日志类名. */
    private static final String LOG_CLASS_NAME = JLog.class.getName();
    /** 日志的打印方法名. */
    private static final String LOG_PRINT_METHOD_NAME = "printLog";

    private static DefaultPrinter mDefaultPrinter ;
    private static JsonPrinter mJsonPrinter ;
    private static ObjPrinter mObjPrinter;

    public static void v(String tag, @NonNull String message) {
        printLog(LogLevel.VERBOSE, tag, null, message);
    }

    public static void v(@NonNull String message) {
        printLog(LogLevel.VERBOSE, null, null, message);
    }

    public static void d(String tag, @NonNull String message) {
        printLog(LogLevel.DEBUG, tag, null, message);
    }

    public static void d(@NonNull String message) {
        printLog(LogLevel.DEBUG, null, null, message);
    }

    public static void i(String tag, @NonNull String message) {
        printLog(LogLevel.INFO, tag, null, message);
    }

    public static void i(@NonNull String message) {
        printLog(LogLevel.INFO, null, null, message);
    }

    public static void w(String tag, @NonNull String message) {
        printLog(LogLevel.WARN, tag, null, message);
    }

    public static void w(@NonNull String message) {
        printLog(LogLevel.WARN, null, null, message);
    }

    public static void e(String tag, Throwable t, String message) {
        printLog(LogLevel.ERROR, tag, t, message);
    }

    public static void e(Throwable t, String message) {
        printLog(LogLevel.ERROR, null, t, message);
    }

    public static void e(String tag, @NonNull String message) {
        printLog(LogLevel.ERROR, tag, null, message);
    }

    public static void e(@NonNull String message) {
        printLog(LogLevel.ERROR, null, null, message);
    }

    public static void e(String tag, @NonNull Throwable t) {
        printLog(LogLevel.ERROR, tag, t, null);
    }

    public static void e(@NonNull Throwable t) {
        printLog(LogLevel.ERROR, null, t, null);
    }

    public static void json(String tag, @NonNull String json) {
        printLog(LogLevel.JSON, tag, null, json);
    }

    public static void json(@NonNull String json) {
        printLog(LogLevel.JSON, null, null, json);
    }

    public static void obj(String tag, @NonNull Object object) {
        printLog(LogLevel.OBJ, tag, null, object);
    }

    public static void obj(@NonNull Object object) {
        printLog(LogLevel.OBJ, null, null, object);
    }

    private static void printLog(@LogLevel String level, String tag, Throwable t, Object message){
        if (message == null) {
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
            synchronized (JLog.class){
                if(mDefaultPrinter == null) {
                    mDefaultPrinter = new DefaultPrinter();
                    mJsonPrinter = new JsonPrinter();
                    mObjPrinter = new ObjPrinter();
                }
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
                mJsonPrinter.printConsole(level, tag, message, element);
                break;
            case LogLevel.OBJ:
                mObjPrinter.printConsole(level, tag, message, element);
                break;
            default:
                break;
        }
    }

    /**
     * @param elements 堆栈元素
     * @return 索引位置，-1 - 不可用
     */
    private static int getStackIndex(@NonNull StackTraceElement[] elements) {
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
