package com.otcyan.jlog.print;
import com.otcyan.jlog.core.LogLevel;

/**
 * 打印接口.
 */
@SuppressWarnings("unused")
public interface Printer {


    /**
     * 日志打印输出到控制台.
     *
     * @param level   级别
     * @param tag     标签
     * @param message 信息
     * @param element 堆栈元素
     */
    void printConsole(@LogLevel String level, String tag, String message, StackTraceElement element);
}
