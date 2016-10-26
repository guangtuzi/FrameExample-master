package com.otcyan.jlog.print;

import android.support.annotation.NonNull;

/**
 * 打印工具.
 */

public class PrintTool {

    /** 控制台打印的内容格式. */
    private static final String PRINT_CONSOLE_FORMAT = "(%1$s:%2$d)#%3$s Thread:%4$s" + System.getProperty("line.separator")
            + "%5$s" + System.getProperty("line.separator") + " ";
    /**
     * 装饰打印到控制台的信息.
     *
     * @param message 信息
     * @param element 对战元素
     * @return 装饰后的信息
     */
    public static String decorateMsgForConsole(String message, @NonNull StackTraceElement element) {
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();
        String fileName = element.getFileName();
        String threadName = Thread.currentThread().getName();
        return String.format(PRINT_CONSOLE_FORMAT, fileName, lineNumber, methodName, threadName, message);
    }

}
