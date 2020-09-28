package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import android.view.WindowManager;
import android.widget.TextView;

import com.nj.baijiayun.basic.widget.dialog.BaseBottomDialog;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PublicBottomListDialog extends BaseBottomDialog {

    private final RecyclerView rv;
    private BaseRecyclerAdapter adapter;

    public PublicBottomListDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.public_dialog_bottom);
        findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(context));
//        rv.addItemDecoration(new ListItemDecoration(context,0,R.color.white));
        setCanceledOnTouchOutside(true);
        //点击Dialog外部消失
    }

    public PublicBottomListDialog setTitle(String text) {
        ((TextView) findViewById(R.id.tv_title)).setText(text);
        return this;

    }


    public PublicBottomListDialog setSpace(int space) {
        rv.addItemDecoration(SpaceItemDecoration.create().setSpace(space).setIncludeEdge(false));
        return this;
    }


    public PublicBottomListDialog setAdapter(BaseRecyclerAdapter adapter) {
        this.adapter = adapter;
        rv.setAdapter(adapter);
        return this;
    }

    public PublicBottomListDialog addData(List<? extends Object> data) {
        if (adapter != null) {
            adapter.addAll(data);
        }
        return this;
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);

    }

}
