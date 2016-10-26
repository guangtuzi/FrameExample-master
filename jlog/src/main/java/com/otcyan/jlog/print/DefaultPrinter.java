package com.otcyan.jlog.print;


import com.otcyan.jlog.core.LogLevel;
import com.otcyan.jlog.core.LogTool;

/**
 * 默认打印机.
 */
public class DefaultPrinter implements Printer {

    @Override
    public void printConsole(@LogLevel String level, String tag, String message, StackTraceElement element) {
        LogTool.log(level, tag, PrintTool.decorateMsgForConsole(message, element));
    }
}