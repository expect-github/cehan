package com.nj.baijiayun.module_common.widget.tabs;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.baijiayun.basic.widget.attrtab.AbstractTab;
import com.nj.baijiayun.basic.widget.attrtab.TriangleView;
import com.nj.baijiayun.module_common.R;

import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2020-02-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.myapplication
 * @describe 不可以重复点击，就是点一次一定有状态改变
 */
public abstract class NoRepeatTab extends AbstractTab {
    private TextView mTv;
    private TriangleView mTlv1;
    View inflate;
    LinearLayout mTabView;
    private ViewGroup parent;
    private String title;
    private View maskView;
    private Animation dismissAnimation;
    private Animation occurAnimation;
    private Animation alphaDismissAnimation;
    private Animation alphaOccurAnimation;

    public NoRepeatTab setTitle(String title) {
        this.title = title;
        if (mTv != null && !TextUtils.isEmpty(title)) {
            mTv.setText(title);
        }
        return this;
    }


    public NoRepeatTab(ViewGroup parent) {
        this.parent = parent;
        if (parent == null) {
            throw new NullPointerException("ViewGroup is not allow null");
        }
    }


    @Override
    public void select(boolean isSelect) {
        super.select(isSelect);
        setTitleColor(isSelect ? R.color.colorSelect : R.color.colorUnSelect);
        mTlv1.setColorRes(R.color.colorTriangleUnSelect);
//        mTlv1.setDirection(isSelect ? TriangleView.DIR.BOTTOM : TriangleView.DIR.TOP);
        inflate.setVisibility(View.VISIBLE);
        maskView.setVisibility(View.VISIBLE);
        if (isSelect) {
            maskView.startAnimation(alphaOccurAnimation);
            inflate.startAnimation(occurAnimation);
        } else {
            maskView.startAnimation(alphaDismissAnimation);
            inflate.startAnimation(dismissAnimation);
        }

    }


    @Override
    public View createTabView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.common_layout_tab, null);
        this.mTabView= (LinearLayout) inflate;
        mTv = inflate.findViewById(R.id.tv);
        mTlv1 = inflate.findViewById(R.id.tlv_1);
        mTlv1.setColorRes(R.color.colorTriangleUnSelect);
        mTv.setText(title);
        return inflate;
    }

    @Override
    public boolean isRepeat() {
        return false;
    }


    @Override
    public View createContent(Context context) {
        this.inflate = createContentView(context);
        maskView = new FrameLayout(context);
        if (parent instanceof FrameLayout) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            maskView.setLayoutParams(params);
        }

        parent.addView(maskView);
        parent.addView(inflate);
        inflate.setClickable(true);
        initContentView(this.inflate);
        this.inflate.setVisibility(View.GONE);
        maskView.setBackgroundColor(ContextCompat.getColor(context, R.color.common_bg_filter));

        maskView.setOnClickListener(v -> {
            if (inflate.getVisibility() == View.VISIBLE) {
                getTabCloseCallBack().close();
            }
        });
        occurAnimation = AnimationUtils.loadAnimation(context, R.anim.top_in);
        dismissAnimation = AnimationUtils.loadAnimation(context, R.anim.top_out);
        dismissAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                NoRepeatTab.this.inflate.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        alphaDismissAnimation = AnimationUtils.loadAnimation(context, R.anim.alpha_to_zero);
        alphaDismissAnimation.setDuration(300);
        alphaDismissAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                maskView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        alphaOccurAnimation = AnimationUtils.loadAnimation(context, R.anim.alpha_to_one);
        alphaOccurAnimation.setDuration(300);

        return this.inflate;
    }


    public abstract View createContentView(Context context);

    public void initContentView(View view) {
        wrap(view);
    }

    @Override
    public View getContentView() {
        return inflate;
    }


    private void wrap(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
    }

    public void setTitleColor(int colorRes) {
        if (mTv != null) {
            mTv.setTextColor(ContextCompat.getColor(mTv.getContext(), colorRes));
        }
    }

    public void setTitleColorSelect() {
        if (mTv != null) {
            mTv.setTextColor(ContextCompat.getColor(mTv.getContext(), R.color.colorSelect));
        }
    }

    public void setTitleColorUnSelect() {
        if (mTv != null) {
            mTv.setTextColor(ContextCompat.getColor(mTv.getContext(), R.color.colorUnSelect));
        }
    }

    @Override
    public View getTabView() {
        return mTabView;
    }

    @Override
    public void setGravity(int gravity) {
        mTabView.setGravity(gravity);
    }
}
