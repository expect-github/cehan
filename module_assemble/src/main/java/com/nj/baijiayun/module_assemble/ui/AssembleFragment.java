package com.nj.baijiayun.module_assemble.ui;

import android.os.Bundle;
import android.view.View;

import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_assemble.api.AssembleService;
import com.nj.baijiayun.module_assemble.bean.response.AssembleListResponse;
import com.nj.baijiayun.module_common.temple.AbstractListPresenter;
import com.nj.baijiayun.module_public.temple.BaseSimpleListFragment;
import com.nj.baijiayun.processor.Module_assembleAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.ui
 * @describe
 */
public class AssembleFragment extends BaseSimpleListFragment {
    static final int TYPE_COURSE = 1;
    static final int TYPE_BOOKS = 2;
    private int type;
    private int count = 0;

    @Override
    public void initParms(Bundle params) {
        super.initParms(params);
        type = params.getInt("type");
    }

    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
//        getNxRefreshView().addItemDecoration(SpaceItemDecoration.create().setIncludeFirst(true).setSpace(10));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (count > 0) {
            refreshData();
        }
        count++;
    }

    @Override
    public void registerListener() {
        super.registerListener();
    }

    @Override
    protected AbstractListPresenter createPresenter() {
        return new AbstractListPresenter<AssembleListResponse>() {
            private AssembleService assembleService;

            @Override
            public List resultCovertToList(AssembleListResponse result) {
                return result.getData().getList();
            }

            @Override
            public Observable<AssembleListResponse> getListObservable(int page) {
                if (assembleService == null) {
                    assembleService = NetMgr.getInstance().getDefaultRetrofit().create(AssembleService.class);
                }
                return assembleService.getAssembleList(type == TYPE_BOOKS ? "book" : "course", page);
            }

        };
    }

    @Override
    public BaseRecyclerAdapter createRecyclerAdapter() {
        return Module_assembleAdapterHelper.getDefaultAdapter(getContext());
    }

    @Override
    protected void onPageItemClick(BaseViewHolder holder, int position, View view, Object item) {

    }
}
