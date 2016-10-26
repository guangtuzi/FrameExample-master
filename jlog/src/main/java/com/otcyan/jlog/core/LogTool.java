package com.otcyan.jlog.core;

import android.support.annotation.NonNull;
import android.util.Log;

import static com.otcyan.jlog.core.LogLevel.DEBUG;
import static com.otcyan.jlog.core.LogLevel.ERROR;
import static com.otcyan.jlog.core.LogLevel.INFO;
import static com.otcyan.jlog.core.LogLevel.JSON;
import static com.otcyan.jlog.core.LogLevel.VERBOSE;
import static com.otcyan.jlog.core.LogLevel.WARN;

/**
 * log工具类.
 */

public class LogTool {

    /** logcat里日志的最大长度. */
    private static final int MAX_LOG_LENGTH = 4000;

    /**
     * 通过全限定类名来获取类名。
     *
     * @param className 全限定类名
     * @return 类名
     */
    public static String getSimpleClassName(@NonNull String className) {
        int lastIndex = className.lastIndexOf(".");
        int index = lastIndex + 1;
        if (lastIndex > 0 && index < className.length()) {
            return className.substring(index);
        }
        return className;
    }

    /**
     * 使用LogCat输出日志，字符长度超过4000则自动换行.
     *
     * @param level   级别
     * @param tag     标签
     * @param message 信息
     */
    public static void log(@LogLevel String level, @NonNull String tag, @NonNull String message) {
        int subNum = message.length() / MAX_LOG_LENGTH;
        if (subNum > 0) {
            int index = 0;
            for (int i = 0; i < subNum; i++) {
                int lastIndex = index + MAX_LOG_LENGTH;
                String sub = message.substring(index, lastIndex);
                logSub(level, tag, sub);
                index = lastIndex;
            }
            logSub(level, tag, message.substring(index, message.length()));
        } else {
            logSub(level, tag, message);
        }
    }
    /**
     * 使用LogCat输出日志.
     *
     * @param level 级别
     * @param tag   标签
     * @param sub   信息
     */
    private static void logSub(@LogLevel String level, @NonNull String tag, @NonNull String sub) {
        switch (level) {
            case VERBOSE:
                Log.v(tag, sub);
                break;
            case DEBUG:
                Log.d(tag, sub);
                break;
            case JSON:
                Log.d(tag, sub);
                break;
            case INFO:
                Log.i(tag, sub);
                break;
            case WARN:
                Log.w(tag, sub);
                break;
            case ERROR:
                Log.e(tag, sub);
                break;
            default:
                break;
        }
    }



}
