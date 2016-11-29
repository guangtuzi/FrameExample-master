package com.otcyan.jlog.core;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.otcyan.jlog.core.LogLevel.DEBUG;
import static com.otcyan.jlog.core.LogLevel.ERROR;
import static com.otcyan.jlog.core.LogLevel.INFO;
import static com.otcyan.jlog.core.LogLevel.JSON;
import static com.otcyan.jlog.core.LogLevel.OBJ;
import static com.otcyan.jlog.core.LogLevel.VERBOSE;
import static com.otcyan.jlog.core.LogLevel.WARN;

/**
 * 日志级别.
 */
@StringDef(value = {VERBOSE, DEBUG, INFO, JSON, WARN, ERROR , OBJ})
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
public @interface LogLevel {
    String VERBOSE = "VERBOSE";
    String DEBUG = "DEBUG";
    String INFO = "INFO";
    String JSON = "JSON";
    String WARN = "WARN";
    String ERROR = "ERROR";
    String OBJ = "OBJ";
}
