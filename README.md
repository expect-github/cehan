Android 标准产品 修改说明

1.基础配置全部在gradle.properties

升级是根据VersionCode ,每次升级记得 更改服务器接口的VersionCode
修改版本号，服务器接口 api/app/version/latest/2


2.默认情况不用修改

version.gradle 一般情况packageName 不要修改
packageName 为你的包路径，写错了会导致友盟通知收不到,由于友盟默认你的资源在applicationId所在目录


3.默认会内置  WXPayEntryActivity,如果自己实现 看以下注意事项  

去掉这个
@GeneratePay(appId = BuildConfig.APPLICATION_ID,needGenerate = true)
ByjApp

- 记得wxapi/WXPayEntryActivity  wxapi/WXEntryActivity 这两个类一定要放在 同applicationId名字相同的文件路径
  譬如 applicationId 为com.baijiayun.zywx   那么一定是com.baijiayun.zywx.wxapi/WXPayEntryActivity 否则会收不到回调
- 清单文件已经默认内置了WXPayEntryActivity
     
    
4.启动图 如果比较特殊 

修改public 模块下面的 public_bg_app_launch_theme 对于的启动图，以及背景
如何设置bitmap 见链接https://www.jianshu.com/p/577165985576，
确保图片不会被拉伸


5.开发中切换不同环境

在app_config.gradle 设置自己的需要的环境，仅仅针对build模式


6.登陆按钮的渐变色是
<color name="common_start_color">#FF8A6FE2</color>
<color name="common_center_color">#FF7B93E4</color>
<color name="common_end_color">#FF57BBF6</color>

7.自动化打包地址    
http://114.67.101.29:9898/

