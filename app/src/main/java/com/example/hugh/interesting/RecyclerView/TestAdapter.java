package com.example.hugh.interesting.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.hugh.interesting.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hugh on 2019/4/19.
 */
public class TestAdapter extends BaseRecyclerAdapter<String, TestAdapter.ViewHolder> {

    private OnActionSelectListener onActionSelectListener;

    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.device_action_chose_item;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, String mData, final int pos) {
        holder.tvActionName.setText(mData);
    }

    public interface OnActionSelectListener {
        void onActionSelect(String mData);
    }

    public void setOnActionSelectListener(OnActionSelectListener onActionSelectListener) {
        this.onActionSelectListener = onActionSelectListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_action_name)
        TextView tvActionName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
