package com.nj.baijiayun.module_common.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.basic.widget.dialog.BaseBottomDialog;
import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.config.ShapeTypeConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.widget.dialog
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/11/29 下午2:04
 * @change
 * @time
 * @describe
 */
public class CommonShareDialog extends BaseBottomDialog {
    private TextView mCancelTxt;
    private TextView mEmptyView;
    private RecyclerView mRecyclerView;
    private TextView mShareTipTv;
    private CommonShareDialog.CommonBottomDialogAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;
    private boolean autoDismiss = true;
    public static List<ShapeTypeConfig> FILTER_SHARE_TYPE = new ArrayList<>();

    public void setAutoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
    }

    private CommonBottomDialogAdapter.OnItemClickListener mAdapterClickListener = new CommonBottomDialogAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View view, ShareBean item) {
            if (autoDismiss) {
                CommonShareDialog.this.dismiss();
            }
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(position, view, item);
            }
        }
    };

    public CommonShareDialog(Context context) {
        this(context, false);
    }

    private boolean needAddImageShare = false;


    public CommonShareDialog(Context context, boolean needAddImageShare) {
        super(context);
        this.needAddImageShare = needAddImageShare;
        setContentView(bindLayout());
        mShareTipTv = findViewById(R.id.tv_share_tip);
        mEmptyView = findViewById(R.id.tv_empty);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        initDialog();
    }

    public CommonShareDialog setImageShareNoNeedOauthShare(boolean imageShareNoNeedOauthShare) {
        if (getAdapter() != null) {
            getAdapter().setImageShareNoNeedOauthShare(imageShareNoNeedOauthShare);
        }
        return this;
    }

    public TextView getmEmptyView() {
        return mEmptyView;
    }

    public int bindLayout() {
        return R.layout.common_bottom_dialog;
    }

    public static List<ShareBean> generateDefaultShareList() {
        List<ShareBean> list = new ArrayList<>();
        list.add(new ShareBean(R.drawable.common_share_wx, "微信", ShapeTypeConfig.WX));
        list.add(new ShareBean(R.drawable.common_share_wx_space, "朋友圈", ShapeTypeConfig.WXP));
        list.add(new ShareBean(R.drawable.common_share_qq, "QQ", ShapeTypeConfig.QQ));
        list.add(new ShareBean(R.drawable.common_share_qq_space, "QQ空间", ShapeTypeConfig.QQZONE));
        return list;
    }

    public static ShareBean createImageBean() {
        return new ShareBean(R.drawable.common_share_code, "生成海报", ShapeTypeConfig.IMG);

    }


    private void initDialog() {
        mCancelTxt = findViewById(R.id.tv_cancel);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CommonShareDialog.CommonBottomDialogAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        List<ShareBean> items = generateDefaultShareList();
        if (needAddImageShare) {
            items.add(createImageBean());
        }
        mAdapter.setItems(items);

        mEmptyView.setVisibility(mAdapter.getDatas().size() == 0 ? View.VISIBLE : View.GONE);
        mCancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    public CommonShareDialog setOnItemClickListener(CommonShareDialog.OnItemClickListener onItemClickListener) {
        mAdapter.setOnItemClickListener(mAdapterClickListener);
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }


    public CommonShareDialog setShareTip(String shareTip) {
        if (!StringUtils.isEmpty(shareTip)) {
            mShareTipTv.setVisibility(View.VISIBLE);
            mShareTipTv.setText(shareTip);
        } else {
            mShareTipTv.setVisibility(View.GONE);
        }
        return this;
    }


    public CommonBottomDialogAdapter getAdapter() {
        return mAdapter;
    }

    public static class CommonBottomDialogAdapter extends RecyclerView.Adapter<CommonShareDialog.ViewHolder> {
        private List<ShareBean> contents;
        private Context mContext;
        private CommonBottomDialogAdapter.OnItemClickListener mListener;
        private boolean isImageShareNoNeedOauthShare = false;
        int padding= DensityUtil.dip2px(15);

        public void setImageShareNoNeedOauthShare(boolean imageShareNoNeedOauthShare) {
            isImageShareNoNeedOauthShare = imageShareNoNeedOauthShare;
        }

        public List<ShareBean> getDatas() {
            return contents;
        }

        public CommonBottomDialogAdapter(Context context) {
            this.mContext = context;
            contents = new ArrayList<>();

        }

        @Override
        public CommonShareDialog.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommonShareDialog.ViewHolder(View.inflate(mContext, R.layout.common_item_share_dialog, null));
        }

        @Override
        public void onBindViewHolder(final CommonShareDialog.ViewHolder holder, final int position) {

            holder.itemView.setPadding(padding, position / 4 == 0 ? padding : 0, padding, padding);
            holder.shareImg.setImageResource(contents.get(position).getRes());
            holder.shareTxt.setText(contents.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (mListener != null && adapterPosition >= 0) {
                        mListener.onItemClick(position, v, contents.get(position));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return contents.size();
        }

        public void setItems(List<ShareBean> items) {
            contents.clear();
            List<ShareBean> filter = filter(items);
            contents.addAll(filter);
            notifyDataSetChanged();
        }

        public void addItem(ShareBean shareBean)
        {
            contents.add(shareBean);
            notifyDataSetChanged();
        }

        private List<ShareBean> filter(List<ShareBean> items) {
            if (FILTER_SHARE_TYPE == null || FILTER_SHARE_TYPE.size() == 0) {
                return items;
            }
            int startIndex = items.size() - 1;
            for (int i = startIndex; i >= 0; i--) {
                for (int j = 0; j < FILTER_SHARE_TYPE.size(); j++) {
                    if (items.get(i).getType() == FILTER_SHARE_TYPE.get(j)) {
                        items.remove(i);
                        break;
                    }
                }
            }
            //只有图片的话 设置为空
            if (items.size() == 1 && items.get(0).getType() == ShapeTypeConfig.IMG) {
                //图片分享不依赖第三方分享
                if (isImageShareNoNeedOauthShare) {
                    return items;
                }
                return new ArrayList<>();
            }

            return items;
        }

        public void setOnItemClickListener(CommonBottomDialogAdapter.OnItemClickListener onItemClickListener) {
            this.mListener = onItemClickListener;
        }

        public interface OnItemClickListener {
            void onItemClick(int position, View view, ShareBean item);
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView shareTxt;
        ImageView shareImg;

        public ViewHolder(View itemView) {
            super(itemView);
            shareTxt = itemView.findViewById(R.id.tv_share);
            shareImg = itemView.findViewById(R.id.iv_share);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view, ShareBean item);
    }

    @Keep
    public static class ShareBean {

        public ShareBean() {
        }

        public ShareBean(@DrawableRes int res, String name, ShapeTypeConfig type) {
            this.res = res;
            this.name = name;
            this.type = type;
        }

        @DrawableRes
        private int res;

        private String name;
        private ShapeTypeConfig type;
        private Object tag;

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public Object getTag() {
            return tag;
        }

        public int getRes() {
            return res;
        }

        public void setRes(@DrawableRes int res) {
            this.res = res;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ShapeTypeConfig getType() {
            return type;
        }

        public void setType(ShapeTypeConfig type) {
            this.type = type;
        }
    }

    public static void setFilterShareType(List<ShapeTypeConfig> filterShareType) {
        FILTER_SHARE_TYPE = filterShareType;
    }

    public static void setFilterQq(boolean needShow) {
        if (needShow) {
            FILTER_SHARE_TYPE.remove(ShapeTypeConfig.QQ);
            FILTER_SHARE_TYPE.remove(ShapeTypeConfig.QQZONE);
            return;
        }
        if (FILTER_SHARE_TYPE.contains(ShapeTypeConfig.QQ)) {
            return;
        }
        FILTER_SHARE_TYPE.add(ShapeTypeConfig.QQ);
        FILTER_SHARE_TYPE.add(ShapeTypeConfig.QQZONE);
    }

    public static void setFilterWx(boolean needShow) {
        if (needShow) {
            FILTER_SHARE_TYPE.remove(ShapeTypeConfig.WX);
            FILTER_SHARE_TYPE.remove(ShapeTypeConfig.WXP);
            return;
        }
        if (FILTER_SHARE_TYPE.contains(ShapeTypeConfig.WX)) {
            return;
        }
        FILTER_SHARE_TYPE.add(ShapeTypeConfig.WX);
        FILTER_SHARE_TYPE.add(ShapeTypeConfig.WXP);
    }


}
