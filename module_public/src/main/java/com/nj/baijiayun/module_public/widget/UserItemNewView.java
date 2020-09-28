package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.widget.BadgeView;
import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.config.SpManger;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;


/**
 * @project zywx_android
 * @class name：com.baijiayun.zywx.module_main.view
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/11/14 下午3:27
 * @change
 * @time
 * @describe
 */
public class UserItemNewView extends LinearLayout {

    @Deprecated
    private String jumpPath;
    private ImageView mAttrIconIv;
    private TextView mTitleTv;
    private ImageView mImgRight;
    private TextView mConetntTv;
    private ImageView mImgContent;
    private View mViewLine;
    private SwitchCompat mSwitch;
    private FrameLayout mFlContent;
    private OnItemClickListener mListener;
    private String routePath = "";
    private String url = "";
    private ICheck iCheck;
    private View viewRoot;
    private BadgeView mBadgeView;


    public UserItemNewView(Context context) {
        this(context, null);

    }


    public UserItemNewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserItemNewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CommonUserItemView, 0, 0);

        String title = a.getString(R.styleable.CommonUserItemView_common_user_item_title);
        String content = a.getString(R.styleable.CommonUserItemView_common_user_item_content);
        int iconRes = a.getResourceId(R.styleable.CommonUserItemView_common_user_item_icon, 0);
        boolean iconVisible = a.getBoolean(R.styleable.CommonUserItemView_common_user_item_icon_visible, true);
        boolean rightIconVisible = a.getBoolean(R.styleable.CommonUserItemView_common_user_item_right_Icon_visible, true);
        boolean rightImageVisible = a.getBoolean(R.styleable.CommonUserItemView_common_user_item_rightContentImage_visible, false);
        boolean lineVisible = a.getBoolean(R.styleable.CommonUserItemView_common_user_item_line_visible, true);
        routePath = a.getString(R.styleable.CommonUserItemView_common_user_item_route_path);
        url = a.getString(R.styleable.CommonUserItemView_common_user_item_url);

        a.recycle();
        init();

        mTitleTv.setText(title);
        mConetntTv.setText(content);
        mAttrIconIv.setImageResource(iconRes);
        mAttrIconIv.setVisibility(iconVisible ? VISIBLE : GONE);
        mImgRight.setVisibility(rightIconVisible ? VISIBLE : INVISIBLE);
        mImgContent.setVisibility(rightImageVisible ? VISIBLE : GONE);
        mViewLine.setVisibility(GONE);
//        mViewLine.setVisibility(lineVisible ? VISIBLE : GONE);
    }


    public void setLeftIconVisible(boolean isShow) {
        mAttrIconIv.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void setLeftIcon(int resId) {
        mAttrIconIv.setImageResource(resId);
    }

    public void setViewLineVisible(boolean isShow) {
        mViewLine.setVisibility(isShow ? VISIBLE : GONE);
    }

    private void init() {
        inflate(getContext(), R.layout.common_item_info_new, this);
//        Logger.d("viewRoot"+viewRoot);
        viewRoot = findViewById(R.id.view_root);
        mAttrIconIv = findViewById(R.id.iv_attr_icon);
        mTitleTv = findViewById(R.id.tv_title);
        mImgRight = findViewById(R.id.img_right);
        mConetntTv = findViewById(R.id.tv_conetnt);
        mImgContent = findViewById(R.id.img_content);
        mViewLine = findViewById(R.id.view_line);
        mSwitch = findViewById(R.id.switch_view);
        mFlContent = findViewById(R.id.fl_content);
        mBadgeView = findViewById(R.id.badge_view);
        setOnClickListener(v -> {
            if (url != null && url.length() > 0) {
                JumpHelper.jumpWebViewNoNeedAppTitle(url);
            } else if (routePath != null && routePath.length() > 0) {
                ARouter.getInstance().build(routePath).navigation();
            } else if (mListener != null) {
                mListener.onItemClick(jumpPath, v);
            }
        });
//        mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (iCheck != null) {
//                iCheck.checked(isChecked);
//                SpManger.setProtectEyes(isChecked);
//            }
//        });
    }

    public void setCheck(boolean isCheck) {
//        mSwitch.setChecked(isCheck);
    }

    public void showSwitch(boolean show) {
        viewRoot.setPadding(viewRoot.getPaddingLeft(), viewRoot.getPaddingTop(), DensityUtil.dip2px(show ? 12 : 15), viewRoot.getPaddingBottom());
//        mSwitch.setVisibility(show ? VISIBLE : INVISIBLE);
//        mSwitch.setVisibility(GONE);
        //以前代碼mImgRight.setVisibility(show ? INVISIBLE : VISIBLE);
        mImgRight.setVisibility(GONE);
    }

    public void switchCheck() {
        mSwitch.setChecked(!mSwitch.isChecked());
    }


    public ImageView getRightImg() {
        return mImgRight;
    }

    public void setRightIconTint(int colorRes) {
        mImgRight.setColorFilter(ContextCompat.getColor(getContext(), colorRes));
    }

    public void setLineColor(int colorRes) {
        mViewLine.setBackgroundColor(ContextCompat.getColor(getContext(), colorRes));
    }

    public TextView getTitleTv() {
        return mTitleTv;
    }

    public TextView getContentTv() {
        return mConetntTv;
    }

    public View getViewLine() {
        return mViewLine;
    }

    public void setJumpPath(String jumpPath) {
        this.jumpPath = jumpPath;
    }

    public interface OnItemClickListener {
        void onItemClick(String path, View v);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setTitleStr(String content) {
        mTitleTv.setText(content);
    }

    public void setContent(String content) {
        mConetntTv.setText(content);
    }

    public String getContent() {
        return mConetntTv.getText().toString();
    }

    public ImageView getContentImage() {
        return mImgContent;
    }

    public void setCheckListener(ICheck iCheck) {
        this.iCheck = iCheck;
    }

    public interface ICheck {
        void checked(boolean isChecked);
    }

    public void addRightContentView(View view) {
        mFlContent.addView(view);
    }

    public void setImageIconSize(int sizeDp) {
//        ViewGroup.LayoutParams layoutParams = mAttrIconIv.getLayoutParams();
//        layoutParams.width = DensityUtil.dip2px(sizeDp);
//        layoutParams.height = layoutParams.width;
//        mAttrIconIv.setLayoutParams(layoutParams);


    }

    public void resetIconSize() {
        setImageIconSize(30);
    }

    public void setUnReadCount(int count) {
        mBadgeView.setBadgeCount(count);
    }

}
