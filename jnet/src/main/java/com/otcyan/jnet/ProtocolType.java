package com.otcyan.jnet;

/**
 * 协议的类型.
 */
public enum ProtocolType {

    JSON(0),
    PROTOCOLBUFFER(1);

    private int mValue;

    ProtocolType(int value) {
        mValue = value;
    }
}
