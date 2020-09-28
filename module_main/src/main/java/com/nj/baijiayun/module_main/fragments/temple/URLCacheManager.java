package com.nj.baijiayun.module_main.fragments.temple;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;

/**
 * @author chengang
 * @date 2020-02-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments.temple
 * @describe
 */
public class URLCacheManager {
    private static volatile URLCacheManager singleton = null;
    private LinkedHashMap<String, URL> linkedHashMap = new LinkedHashMap<String, URL>() {
        @Override
        protected boolean removeEldestEntry(Entry eldest) {
            return size() > 20;
        }
    };

    private URLCacheManager() {
    }

    public static URLCacheManager getInstance() {
        if (singleton == null) {
            synchronized (URLCacheManager.class) {
                if (singleton == null) {
                    singleton = new URLCacheManager();
                }
            }
        }
        return singleton;
    }

    public URL getURL(String url) throws MalformedURLException {
        URL urlWrapper = linkedHashMap.get(url);
        if (urlWrapper == null) {
            urlWrapper = new URL(url);
            linkedHashMap.put(url, urlWrapper);
        }
        return urlWrapper;
    }


    public boolean isUrlInPathList(String url, String...pathList) {
        try {
            URL urlWrapper = getURL(url);
            if (urlWrapper.getPath() == null) {
                return false;
            }
            if(pathList==null)
            {
                return false;
            }
            for (String path : pathList) {
                if (urlWrapper.getPath().equals(path)) {
                    return true;
                }
            }
        } catch (MalformedURLException e) {
            return false;
        }
        return false;

    }


}
