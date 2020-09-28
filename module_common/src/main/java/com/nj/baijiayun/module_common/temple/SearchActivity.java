package com.nj.baijiayun.module_common.temple;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.SharedPrefsUtil;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.basic.widget.MultipleStatusView;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.helper.RefreshHelper;
import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.smartrv.NxRefreshView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class SearchActivity<T extends SearchPresenter> extends BaseAppActivity<T> implements SearchView {

    private ImageView mBackImg;
    private EditText mSearchEdit;
    private TextView mSearchTxt;
    private MultipleStatusView mMultipleStatusView;
    private LinearLayout mSearchHistroyLl;
    private ImageView mClearHistoryImg;
    private TagFlowLayout mFlowLayout;
    protected BaseRecyclerAdapter mAdapter;
    protected BaseRecyclerAdapter mRelativeAdapter;
    private NxRefreshView mRefreshLayout;
    private RecyclerView mRelativeSearchRv;

    private MyHandler mHandler = new MyHandler(this);

    static class MyHandler extends Handler {
        private final WeakReference<SearchActivity> activity;

        public MyHandler(SearchActivity presenter) {
            this.activity = new WeakReference<>(presenter);
        }
    }

    public RecyclerView getSearchContentRv()
    {
        return mRefreshLayout.getRecyclerView();
    }
    private String searchStr;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
          mPresenter.getList(searchStr);
        }
    };

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.common_search_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        hideToolBar();
        mAdapter = createRecyclerAdapter();
        mRelativeAdapter = createRelativeAdapter();
        mBackImg = findViewById(R.id.back_img);
        mSearchEdit = findViewById(R.id.search_edit);
        mSearchTxt = findViewById(R.id.search_txt);
        mMultipleStatusView = findViewById(R.id.multiple_status_view);
        mSearchHistroyLl = findViewById(R.id.ll_search_histroy);
        mRelativeSearchRv = findViewById(R.id.rv_relative_search);
        mClearHistoryImg = findViewById(R.id.clear_history_img);
        mFlowLayout = findViewById(R.id.flow_layout);

        mMultipleStatusView.setContentViewResId(R.layout.common_nxrefresh_layout);
        mRefreshLayout = findViewById(R.id.refreshLayout);

        mRefreshLayout.setLayoutManager(new LinearLayoutManager(this));
        mRefreshLayout.setAdapter(getAdapter());

        mRelativeSearchRv.setLayoutManager(new LinearLayoutManager(this));
        mRelativeSearchRv.setAdapter(getRelativeAdapter());
        showSoftInputFromWindow(this,mSearchEdit);
    }

    /**
     * create Adapter
     *
     * @return BaseMultipleTypeRvAdapter
     */
    public abstract BaseRecyclerAdapter createRecyclerAdapter();

    public abstract BaseRecyclerAdapter createRelativeAdapter();

    public BaseRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    public BaseRecyclerAdapter getRelativeAdapter() {
        return mRelativeAdapter;
    }


    @Override
    protected void registerListener() {
        mClearHistoryImg.setOnClickListener(v -> {
            CommonMDDialog dialog = new CommonMDDialog(this);
            dialog.setContentTxt("确认删除历史记录吗?")
                    .setNegativeTxt("取消")
                    .setPositiveTxt("确认")
                    .setOnNegativeClickListener(dialog::dismiss).setOnPositiveClickListener(() -> {
                mHistory.clear();
                mHistoryAdapter.notifyDataChanged();
                SharedPrefsUtil.clearName(SearchActivity.this, getSPName());
                SharedPrefsUtil.putValue(SearchActivity.this, getSPName(),getSPFileName(),"");
                dialog.dismiss();
            }).show();
        });
        mBackImg.setOnClickListener(v -> onBackPressed());
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchTxt.setEnabled(true);
                mHandler.removeCallbacks(runnable);
                searchStr = s.toString().trim();
                if (TextUtils.isEmpty(searchStr)) {
                    clearRelative();
                    mSearchTxt.setEnabled(false);
                    return;
                }
                mHandler.postDelayed(runnable, 200);
                if (TextUtils.isEmpty(s.toString())) {
                    setHistroyView();
                } else {
                    setRelativeView();
                }
            }
        });
        mSearchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                String currentT = mSearchEdit.getText().toString().trim();
                if (TextUtils.isEmpty(currentT)) {
                    showToastMsg("请输入要搜索的关键字");
                } else {
                    hintKeyBoard();
                    doSearch(currentT);
                }
                return true;
            }
            return false;
        });
        mSearchTxt.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mSearchEdit.getText().toString())) {
                return;
            }
            if ("搜索".equals(mSearchTxt.getText().toString())) {
                doSearch(mSearchEdit.getText().toString());
            }
        });
//        mSearchEdit.setOnFocusChangeListener((v, hasFocus) -> {
//            if (!hasFocus) {
//                setSearchView();
//            }
//        });
        mFlowLayout.setOnTagClickListener((view, position, parent) -> {
            String searchString = mHistory.get(position);
            doSearch(searchString);
            return true;
        });
        getAdapter().setOnItemClickListener((holder, position, view, item) -> courseClick(item));
        getRelativeAdapter().setOnItemClickListener((holder, position, view, item) -> {
            String title = (String) item;
            doSearch(title);
        });
    }


    private void setSearchView() {
        mMultipleStatusView.setVisibility(View.VISIBLE);
        mSearchHistroyLl.setVisibility(View.GONE);
        mRelativeSearchRv.setVisibility(View.GONE);
    }

    private void setRelativeView() {
        mMultipleStatusView.setVisibility(View.GONE);
        mSearchHistroyLl.setVisibility(View.GONE);
        mRelativeSearchRv.setVisibility(View.VISIBLE);
    }

    private void setHistroyView() {
        mMultipleStatusView.setVisibility(View.GONE);
        mSearchHistroyLl.setVisibility(View.VISIBLE);
        mRelativeSearchRv.setVisibility(View.GONE);
    }

    private void clearRelative() {
        getRelativeAdapter().clear();
        setHistroyView();
    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String history = SharedPrefsUtil.getValue(SearchActivity.this, getSPFileName(), getSPName(), "");
        Logger.d("get search history from sp," + history);
        mHistory = StringUtils.convertStrToList(history);
        mHistoryAdapter = new TagAdapter<String>(mHistory) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = View.inflate(SearchActivity.this, R.layout.common_item_search_history, null);
                TextView historyTxt = view.findViewById(R.id.history_txt);
                historyTxt.setText(mHistory.get(position));
                return view;
            }
        };
        mFlowLayout.setAdapter(mHistoryAdapter);
    }


    @Override
    public void dataSuccess(List data, boolean isRefresh) {
        setSearchView();
        mMultipleStatusView.showContent();
        mSearchEdit.clearFocus();
        getAdapter().addAll(data, isRefresh);
    }

    @Override
    public void loadFinish(boolean loadMoreEnable) {
        RefreshHelper.finishLoading(loadMoreEnable, mRefreshLayout);
    }

    private List<String> mHistory;
    private TagAdapter<String> mHistoryAdapter;

    @Override
    public void saveSearchString(String searchString) {
        if (TextUtils.isEmpty(searchString)) {
            return;
        }
        for (int i = 0; i < mHistory.size(); i++) {
            if (mHistory.get(i).equals(searchString)) {
                mHistory.remove(searchString);
            }
        }
        mHistory.add(0, searchString);
        if (mHistory.size() > 5) {
            mHistory.remove(5);
        }
        mHistoryAdapter.notifyDataChanged();
        String history = StringUtils.ListToStr(mHistory);
        Logger.d("save history to sp," + history);
        SharedPrefsUtil.putValue(SearchActivity.this, getSPFileName(), getSPName(), history);
    }

    public void doSearch(String searchString) {
//        mSearchEdit.setText(searchString);
//        mSearchEdit.setSelection(searchString.length());
        mPresenter.getList(searchString, true);
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void showNoDataView() {
        setSearchView();
        mMultipleStatusView.showEmpty(R.layout.common_no_data_search, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    protected abstract String getSPFileName();

    protected abstract String getSPName();

    protected abstract void courseClick(Object o);

    public String getSearchStr() {
        return searchStr;
    }



    /**
     * EditText获取焦点并显示软键盘
     */
    public  void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public ImageView getBackImg() {
        return mBackImg;
    }
}
