package com.nj.baijiayun.module_public.bean;

public class PublicUploadBean {

    /**
     * path : http://xx.com/uploads/images/20190515/f46eda3088b746b5abaafde4124e384a.jpg
     * ext : jpg
     * type : image/jpeg
     * size : 2342
     * originalName : favicon.jpg
     */

    private String path;
    private String ext;
    private String type;
    private int size;
    private String originalName;
    private String localPath;

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
}
