package com.nj.baijiayun.module_public.provider;

/**
 * @author chengang
 * @date 2019-06-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public
 * @describe
 */
public class FileUploadProvider extends NetWorkProvider {

    public FileUploadProvider(boolean debugEnable) {
        super(debugEnable);
    }

    @Override
    public long configConnectTimeoutMills() {
        return 60*1000;
    }

    @Override
    public long configReadTimeoutMills() {
        return 60*1000;
    }

    @Override
    public long configWriteTimeoutMills() {
        return 60*1000;
    }


}
