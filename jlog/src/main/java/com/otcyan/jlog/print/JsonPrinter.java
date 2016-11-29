package com.otcyan.jlog.print;

import com.otcyan.jlog.core.LogLevel;
import com.otcyan.jlog.core.LogTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * json方式打印.
 */

public class JsonPrinter implements Printer {
    @Override
    public void printConsole(@LogLevel String level, String tag, Object object, StackTraceElement element) {
        String json;
        String message  = (String) object;
        try {
            if (message.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(message);
                json = jsonObject.toString(4);
            } else if (message.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(message);
                json = jsonArray.toString(4);
            } else {
                json = message;
            }
        } catch (JSONException e) {
            json = message;
        }
        LogTool.log(level, tag, LogTool.parseMessage(json, element));
    }
}
