package com.nj.baijiayun.module_distribution.ui.list;

import android.view.View;

import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.temple.AbstractListPresenter;
import com.nj.baijiayun.module_distribution.api.DistruibutionService;
import com.nj.baijiayun.module_distribution.bean.ChannelBean;
import com.nj.baijiayun.module_distribution.bean.DtbListWrapperBean;
import com.nj.baijiayun.module_distribution.bean.res.DtbListResponse;
import com.nj.baijiayun.module_public.temple.BaseSimpleListFragment;
import com.nj.baijiayun.processor.Module_distributionAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution
 * @describe
 */
public class DistributionSimpleListFragment extends BaseSimpleListFragment {

    private DistruibutionService distruibutionService= NetMgr.getInstance().getDefaultRetrofit().create(DistruibutionService.class);;

    @Override
    public BaseRecyclerAdapter createRecyclerAdapter() {
        return Module_distributionAdapterHelper.getDefaultAdapter(getContext());
    }

    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
        getNxRefreshView().addItemDecoration(SpaceItemDecoration.create().setSpace(10));
    }

    @Override
    public void registerListener() {
        super.registerListener();
    }

    @Override
    protected void onPageItemClick(BaseViewHolder holder, int position, View view, Object item) {

    }

    @Override
    protected AbstractListPresenter createPresenter() {
        return new AbstractListPresenter<DtbListResponse>() {


            @Override
            public List resultCovertToList(DtbListResponse result) {
                DtbListWrapperBean data = result.getData();
                List tmp=new ArrayList();
                if(data.getCourseList()!=null&&data.getCourseList().size()>0) {
                    tmp.add(new ChannelBean().setCourseType());
                }
                tmp.addAll(data.getCourseList());
                if(data.getBookBeanList()!=null&&data.getBookBeanList().size()>0) {
                    tmp.add(new ChannelBean().setBookType());
                }
                tmp.addAll(data.getBookBeanList());
                return tmp;
            }

            @Override
            public Observable<DtbListResponse> getListObservable(int page) {
                return distruibutionService.getDtbList();
            }
        };
    }

    @Override
    public void processLogic() {

    }

    @Override
    public void loadFinish(boolean loadMoreEnable) {
        super.loadFinish(loadMoreEnable);
    }
}
