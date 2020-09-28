package com.nj.baijiayun.module_public.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.widget.MyRatingBar;
import com.nj.baijiayun.module_public.R;

/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.widget.mDialog
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/11/24 上午11:41
 * @change
 * @time
 * @describe
 */
public class CommentDialog {
    private final Dialog mDialog;
    private int mCurrentRate;
    private CommentCommitListener mListener;
    private final EditText mCommentEdit;
    private final MyRatingBar mRatingBar;
    private boolean isCommented = false;
    Button mbtnConfirm;

    public void setCommented(boolean commented, String commentContent, int grade) {
        isCommented = commented;
        mRatingBar.setClickable(!commented);
        mCommentEdit.setEnabled(!commented);
        mCommentEdit.setText(commentContent);
        mRatingBar.setStar(grade);
        mCommentEdit.setSelection(mCommentEdit.getText().toString().length());
        // 未评论情况下 grade=0
        if (!commented && grade == 0) {
            mRatingBar.setStar(5);
        }
        mbtnConfirm.setVisibility(commented ? View.GONE : View.VISIBLE);
    }

    public CommentDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.public_layout_comment, null);
        mRatingBar = view.findViewById(R.id.rating_bar);
        mCommentEdit = view.findViewById(R.id.etv);
        mbtnConfirm = view.findViewById(R.id.btn_confirm);
        ImageView mCloseIv = view.findViewById(R.id.iv_close);

        mRatingBar.setOnRatingChangeListener(ratingCount -> mCurrentRate = (int) ratingCount);
        mDialog = BJYDialogHelper.buildCommonDialog(context).setHeightWrapContent().setCustomView(view);
        mCloseIv.setOnClickListener(v -> dismiss());
        mbtnConfirm.setOnClickListener(v -> {
            if (isCommented) {
                ToastUtil.shortShow(v.getContext(), "已评价");
                dismiss();
                return;
            }
            String msg = mCommentEdit.getText().toString();
            if (StringUtils.isEmpty(msg)) {
                ToastUtil.shortShow(v.getContext(), "评价内容必须填写");
                return;
            }
            if (mListener != null) {
                mListener.commentCommit(msg, mCurrentRate);
                dismiss();

            }
        });
    }

    public CommentDialog setCommentCommitListener(CommentCommitListener listener) {
        this.mListener = listener;
        return this;
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public interface CommentCommitListener {
        void commentCommit(String comment, int rate);
    }

    public interface OnDissMissListener {
        void call(String comment, int rate);
    }


    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    public CommentDialog setOnDissMissListener(OnDissMissListener dissMissListener) {
        if (mDialog != null) {
            mDialog.setOnDismissListener(dialog -> dissMissListener.call(mCommentEdit.getText().toString(), mCurrentRate));
        }
        return this;
    }

    public void show() {
        if (mDialog != null && !mDialog.isShowing()) {
            mCommentEdit.setText("");
            mRatingBar.setStar(5);
            mDialog.show();
        }
    }
}
