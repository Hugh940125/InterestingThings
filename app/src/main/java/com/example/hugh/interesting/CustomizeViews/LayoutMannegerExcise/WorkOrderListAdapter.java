package com.example.hugh.interesting.CustomizeViews.LayoutMannegerExcise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hugh.interesting.R;
import com.example.hugh.interesting.RecyclerView.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hugh on 2019/9/10.
 */
public class WorkOrderListAdapter extends BaseRecyclerAdapter<String, WorkOrderListAdapter.ViewHolder> {

    private OnTapListener onTapListener;

    public WorkOrderListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_list;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, String content, int pos) {
        holder.tvContent.setText(content);
    }

    public interface OnTapListener {
        void OnTap(String orderInfo);
    }

    public void setOnTapListener(OnTapListener onTapListener) {
        this.onTapListener = onTapListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
