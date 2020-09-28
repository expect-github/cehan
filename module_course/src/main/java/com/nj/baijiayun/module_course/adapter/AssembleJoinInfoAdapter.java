package com.nj.baijiayun.module_course.adapter;

import android.content.Context;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.basic.widget.XChronometer;
import com.nj.baijiayun.basic.widget.countdown.AbstractNewCountDownTimer;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.bean.wx.AssembleJoinInfoBean;
import com.nj.baijiayun.module_course.ui.wx.courseDetail.WxCourseDetailActivity;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;
import com.nj.baijiayun.module_public.helper.TimeManager;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import java.text.MessageFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2019-11-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter
 * @describe
 */
public class AssembleJoinInfoAdapter extends BaseRecyclerAdapter<AssembleJoinInfoBean> {
    private final String leftNumberFomrat;
    private final ForegroundColorSpan textColorSpan;
    private SparseArray<AbstractNewCountDownTimer> countDownMap;
    private CallBack callBack;

    public AssembleJoinInfoAdapter(Context context) {
        super(context);
        countDownMap = new SparseArray<>();

        textColorSpan = TextSpanHelper.getTextColorSpan(ContextCompat.getColor(context, R.color.public_orange));
        leftNumberFomrat = context.getString(R.string.course_fmt_assemble_left_join_number);

    }



    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, viewType);
        baseViewHolder.setOnClickListener(R.id.tv_action, v -> {
            if (getContext() instanceof WxCourseDetailActivity) {
                ((WxCourseDetailActivity) getContext()).getPresenter().assembleJoinGroup(getAllItems().get(baseViewHolder.getAdapterPositionExcludeHeadViewCount()).getGroupId());
            }
        });


        return baseViewHolder;
    }

    @Override
    public void bindViewAndData(BaseViewHolder holder, AssembleJoinInfoBean assembleJoinInfoBean, int position, List<Object> payloads) {
        super.bindViewAndData(holder, assembleJoinInfoBean, position, payloads);
    }

    @Override
    protected void bindViewAndData(BaseViewHolder holder, AssembleJoinInfoBean model, int position) {
        Logger.d("bindViewAndData" + position);
        holder.setText(R.id.tv_name, model.getGroupLeaderNickname());
        String format = MessageFormat.format(leftNumberFomrat, String.valueOf(model.getLeftNum()));
        holder.setText(R.id.tv_left_person_number, TextSpanHelper.matcherSearchKeyWord(format, String.valueOf(model.getLeftNum()), textColorSpan));
        ImageLoader.with(getContext()).load(model.getGroupLeaderAvatar()).asCircle().into((ImageView) holder.getView(R.id.iv_head));

        long time = model.getEndTime() * 1000 - TimeManager.getInstance().getServiceTime();
        holder.setText(R.id.tv_left_time, getContext().getString(R.string.course_count_down_end));
        int hashCode = holder.getView(R.id.tv_countdown).hashCode();
        ((ViewHolder) holder).bean = model;
        ((ViewHolder) holder).countDownTimer = countDownMap.get(hashCode);
        if (time > 0) {
            if (((ViewHolder) holder).countDownTimer == null) {
                ((ViewHolder) holder).countDownTimer = new AbstractNewCountDownTimer(time, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        holder.setText(R.id.tv_countdown, XChronometer.formatDuration(millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        holder.setText(R.id.tv_countdown, "已结束");
                        holder.setText(R.id.tv_left_time, "");
                        Logger.d("setCallBack--> onFinish ==" + ((ViewHolder) holder).bean.getGroupLeaderNickname());

                        if (callBack != null) {
                            //这里有可能回调两次
                            callBack.countDownEnd(((ViewHolder) holder).bean);
                        }

                    }
                };
                countDownMap.put(hashCode, ((ViewHolder) holder).countDownTimer);

            }

            ((ViewHolder) holder).countDownTimer.reStart(time, 1000);
        } else {
            holder.setText(R.id.tv_left_time, "");
            holder.setText(R.id.tv_countdown, "已结束");
        }

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.course_item_assemble;
    }


    @Override
    public BaseViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_assemble, parent, false));
    }

    class ViewHolder extends BaseViewHolder {
        AbstractNewCountDownTimer countDownTimer;
        AssembleJoinInfoBean bean;

        ViewHolder(View view) {
            super(view);
        }
    }

    public interface CallBack {

        void countDownEnd(AssembleJoinInfoBean assembleJoinInfoBean);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        for (int i = 0, length = countDownMap.size(); i < length; i++) {
            AbstractNewCountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }


}
