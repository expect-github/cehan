package com.nj.baijiayun.basic.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ToastUtil {


    @Deprecated
    public static void show(Context context, int resId) {
        show(resId);
    }

    @Deprecated
    public static void shortShow(Context context, int resId) {
        shortShow(resId);
    }

    @Deprecated
    public static void show(Context context, String msg) {
        show(msg);
    }

    @Deprecated
    public static void shortShow(Context context, String msg) {
        shortShow(msg);

    }


    public static void show(int resId) {
        Toast.makeText(initContext, initContext.getString(resId), Toast.LENGTH_LONG).show();
    }

    public static void shortShow(int resId) {
        Toast.makeText(initContext, initContext.getString(resId), Toast.LENGTH_SHORT).show();
    }

    public static void show(String msg) {
        Toast.makeText(initContext, msg, Toast.LENGTH_LONG).show();

    }

    public static void shortShow(String msg) {
        Toast.makeText(initContext, msg, Toast.LENGTH_SHORT).show();

    }

    private static Context initContext;

    public static class InitContentProvider extends ContentProvider {
        @Override
        public boolean onCreate() {
            initContext = getContext();
            return true;
        }

        @Nullable
        @Override
        public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
            return null;
        }

        @Nullable
        @Override
        public String getType(@NonNull Uri uri) {
            return null;
        }

        @Nullable
        @Override
        public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
            return null;
        }

        @Override
        public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
            return 0;
        }

        @Override
        public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
            return 0;
        }

    }
}
