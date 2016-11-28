package com.otcyan.jutil;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Toast工具，使用前需要调用init进行初始化.
 */
public class ToastUtil {

    public static void showShort(@NonNull String content) {
        Toast.makeText(UtilGlobal.getContext(), content, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(@StringRes int resId) {
        Toast.makeText(UtilGlobal.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(@NonNull String content) {
        Toast.makeText(UtilGlobal.getContext(), content, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(@StringRes int resId) {
        Toast.makeText(UtilGlobal.getContext(), resId, Toast.LENGTH_SHORT).show();
    }
}
