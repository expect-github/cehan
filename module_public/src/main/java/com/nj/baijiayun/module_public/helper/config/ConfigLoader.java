package com.nj.baijiayun.module_public.helper.config;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nj.baijiayun.module_public.BaseApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chengang
 * @date 2018/8/15
 * @describe
 */
public class ConfigLoader<T extends BaseConfigBean> {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String TAG = ConfigLoader.class.getSimpleName();

    private volatile T mConfigBean;
    private final T mDefaultBean;
    private final String mFileName;

    private final AtomicBoolean mDoorOpened = new AtomicBoolean(false);

    private Object mLock = new Object();

    public ConfigLoader(T defaultObj, String fileName) {
        mDefaultBean = defaultObj;
        mConfigBean = mDefaultBean;
        mFileName = fileName;
    }

    public void openDoor() {
        mDoorOpened.set(true);
        reloadConfig();
    }

    public void closeDoor() {
        mDoorOpened.set(false);
        reloadConfig();
    }

    public T getConfig() {
        return mConfigBean;
    }

    private void reloadConfig() {
        loadConfig();
    }

    public void loadConfig() {
        String fileName = mFileName;
        if (mDoorOpened.get()) {
            if (!loadFromFile(fileName)) {
                // 优先加载SD卡中的配置
                if (!loadFromExternalFile(fileName)) {
                    // 最后才考虑Asset资源
                    if (!loadFromAssetFile(fileName)) {
                        // 全都失败，那么创建一个默认对象
                        mConfigBean = mDefaultBean;
                    }
                }
            }
        } else {
            // 内部配置文件
            long fileVersion = 0;
            long assetVerion = 0;
            boolean fileLoaded = loadFromFile(fileName);
            boolean assetLoaded;
            if (fileLoaded) {
                fileVersion = mConfigBean.getVersion();
                assetLoaded = loadFromAssetFile(fileName);
                if (assetLoaded) {
                    assetVerion = mConfigBean.getVersion();
                }
            }
            if (fileVersion >= assetVerion) {
                fileLoaded = loadFromFile(fileName);
            }

            if (!fileLoaded) {
                assetLoaded = loadFromAssetFile(fileName);
                if (!assetLoaded) {
                    mConfigBean = mDefaultBean;
                }
            }
        }
    }

    private boolean loadFromAssetFile(String fileName) {
        boolean result = false;
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(BaseApp.getInstance().getAssets().open(fileName), DEFAULT_CHARSET);
            result = parse(reader, mDefaultBean);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private boolean loadFromFile(String fileName) {
        boolean result = false;
        synchronized (mLock) {
            File fileA = new File(getContext().getFilesDir(), fileName);
            if (!fileA.getParentFile().exists()) {
                fileA.getParentFile().mkdirs();
            }
            InputStreamReader reader = null;
            try {
                reader = new InputStreamReader(new FileInputStream(fileA), DEFAULT_CHARSET);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            if (reader != null) {
                result = parse(reader, mDefaultBean);

                // 如果解析失败，可以任务文件被破坏
                if (!result) {
                    fileA.delete();
                }
            }
        }
        return result;
    }

    private static File getExternalStroageDirectory() {
        File directory = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory();
        }
        return directory;
    }


    private boolean loadFromExternalFile(String fileName) {
        boolean result = false;
        InputStreamReader reader = null;
        File file = new File(getExternalStroageDirectory(), fileName);
        if (!file.getParentFile().exists()) {
            System.out.println("//不存在" + file.getParentFile());
            file.getParentFile().mkdirs();
        }


        if (file.exists()) {
            try {
                reader = new InputStreamReader(new FileInputStream(file), DEFAULT_CHARSET);
            } catch (Exception e) {
                Log.w(TAG, e.getMessage());
            }
        }
        if (reader != null) {
            result = parse(reader, mDefaultBean);
        }
        return result;
    }

    private boolean parse(Reader reader, T obj) {
        boolean result = false;
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            mConfigBean = (T) gson.fromJson(reader, obj.getClass());
            if (mConfigBean != null) {
                result = true;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }


    public boolean updateConfig(T configBean, String json) {

        if (configBean != null && mConfigBean != null && mConfigBean.getVersion() <= configBean.getVersion()) {
            mConfigBean = configBean;
            saveToFile(json);
            return true;
        }
        return false;
    }

    private boolean saveToFile() {
        boolean result = false;
        Gson gson = new GsonBuilder().serializeNulls().create();
        String str = gson.toJson(mConfigBean);
        if (str != null) {
            synchronized (mLock) {
                try {

                    File file = new File(BaseApp.getInstance().getFilesDir(), mFileName);
                    if (!file.getParentFile().exists()) {
                        System.out.println("//不存在" + file.getParentFile());
                        file.getParentFile().mkdirs();
                    }
                    OutputStream out = new FileOutputStream(file);
                    OutputStreamWriter writer = new OutputStreamWriter(out, DEFAULT_CHARSET);
                    writer.write(str);
                    writer.close();
                    result = true;
                    Log.e(TAG, "saveToFile success");

                } catch (Exception e) {
                    Log.e(TAG, "saveToFile" + e.getMessage());
                }
            }
        }
        return result;
    }

    private boolean saveToFile(String json) {
        boolean result = false;
        if (json != null) {
            synchronized (mLock) {
                try {

                    File file = new File(getContext().getFilesDir(), mFileName);
                    if (!file.getParentFile().exists()) {
                        System.out.println("//不存在" + file.getParentFile());
                        file.getParentFile().mkdirs();
                    }
                    OutputStream out = new FileOutputStream(file);
                    OutputStreamWriter writer = new OutputStreamWriter(out, DEFAULT_CHARSET);
                    writer.write(json);
                    writer.close();
                    result = true;
                    Log.e(TAG, "saveToFile success");

                } catch (Exception e) {
                    Log.e(TAG, "saveToFile" + e.getMessage());
                }
            }
        }
        return result;
    }

    public Context getContext() {
        return BaseApp.getInstance();
    }


}

