package com.nj.baijiayun.module_common.widget.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.nj.baijiayun.basic.widget.dialog.BaseBottomDialog;
import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.widget.CommonLineDividerDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.widget.dialog
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/11/26 下午3:42
 * @change
 * @time
 * @describe
 */
public class CommonBottomDialog extends BaseBottomDialog {
    private TextView mCancelTxt;
    private RecyclerView mRecyclerView;
    private CommonBottomDialogAdapter mAdapter;
    private OnCancelListener mCancelListener;

    public CommonBottomDialog(Context context) {
        super(context);
        setContentView(R.layout.common_bottom_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        initDialog();
    }

    private void initDialog() {
        mCancelTxt = findViewById(R.id.tv_cancel);
        findViewById(R.id.divider).setVisibility(View.GONE);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new CommonLineDividerDecoration(getContext(), CommonLineDividerDecoration.VERTICAL)
                .setLineWidthDp(1)
                .setLineColor(getContext().getResources().getColor(R.color.common_main_line_color)));
        mAdapter = new CommonBottomDialogAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        mCancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        this.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mCancelListener != null) {
                    mCancelListener.onCancelClick();
                }
            }
        });
    }

    public CommonBottomDialog setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mAdapter.setOnItemClickListener(onItemClickListener);
        return this;
    }

    public CommonBottomDialog setOnCancelListener(OnCancelListener onCancelListener) {
        this.mCancelListener = onCancelListener;
        return this;
    }

    public CommonBottomDialog setContents(List<String> contents) {
        mAdapter.setItems(contents);
        return this;
    }


    private static class CommonBottomDialogAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<String> contents;
        private Context mContext;
        private OnItemClickListener mListener;

        public CommonBottomDialogAdapter(Context context) {
            this.mContext = context;
            contents = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(mContext, R.layout.common_item_bottom_dialog, null));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.contentTxt.setText(contents.get(position));
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

        public void setItems(List<String> items) {
            contents.clear();
            contents.addAll(items);
            notifyDataSetChanged();
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mListener = onItemClickListener;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contentTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            contentTxt = itemView.findViewById(R.id.content_txt);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view, String item);
    }

    public interface OnCancelListener {
        void onCancelClick();
    }
}
