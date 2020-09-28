package com.nj.baijiayun.module_common.widget.tabs;

import android.content.Context;

import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-07-30
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.widget.dropdownmenu
 * @describe
 */
public class SingleListView extends RecyclerView {

    private BaseRecyclerAdapter<SingleListModel> adapter;

    public SingleListView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseRecyclerAdapter<SingleListModel>(getContext()) {
            @Override
            protected int attachLayoutRes() {
                return R.layout.common_item_text;
            }

            @Override
            protected void bindViewAndData(BaseViewHolder holder, SingleListModel o, int position) {
                holder.setText(R.id.tv, o.getText());
                holder.setTextColor(R.id.tv, o.isSelect() ? ContextCompat.getColor(getContext(), R.color.common_main_color) :
                        ContextCompat.getColor(getContext(), R.color.common_FF595959));
            }

        };

        addItemDecoration(SpaceItemDecoration.create().setSpace(20).setIncludeEdge(false));

//        addItemDecoration(new CommonLineDividerDecoration(getContext(), CommonLineDividerDecoration.VERTICAL)
//                .setLineColor(Color.parseColor("#FFF5F5F5")).setLineWidthDp(1).setNeedDrawLast(false));
        setAdapter(adapter);

    }

    public void selectPosition(int position) {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).isSelect()) {
                adapter.getItem(i).setSelect(false);
            }
        }
        adapter.getItem(position).setSelect(true);
        adapter.notifyDataSetChanged();
    }

    public void reset() {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            adapter.getItem(i).setSelect(false);
        }
        adapter.notifyDataSetChanged();

    }

    public boolean isHasSelect() {
        boolean result = false;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).isSelect()) {
                result = true;
                break;
            }
        }
        return result;
    }
    public String getSelectText() {
        String result = "";
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).isSelect()) {
                result = adapter.getItem(i).getText();
                break;
            }
        }
        return result;
    }
    public int getSelectId() {
        int result = 0;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).isSelect()) {
                result = adapter.getItem(i).getId();
                break;
            }
        }
        return result;
    }

    @Nullable
    @Override
    public BaseRecyclerAdapter<SingleListModel> getAdapter() {
        return adapter;
    }
}
