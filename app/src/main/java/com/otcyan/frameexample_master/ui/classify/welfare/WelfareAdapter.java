package com.otcyan.frameexample_master.ui.classify.welfare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.otcyan.frameexample_master.R;
import com.otcyan.frameexample_master.bean.Welfare;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author snamon
 * 福利数据适配器.
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.WelfareViewHole> {

    private List<Welfare> mWelfares;
    private Context mContext;
    public WelfareAdapter(Context context , List<Welfare> welfares){
        this.mWelfares = welfares;
        this.mContext = context;
    }

    @Override
    public WelfareViewHole onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WelfareViewHole(LayoutInflater.from(mContext).inflate(R.layout.item_welfare  , null));
    }

    @Override
    public void onBindViewHolder(WelfareViewHole holder, int position) {
        Welfare welfare = mWelfares.get(position);
        Picasso.with(mContext)
                .load(welfare.url)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mWelfares==null?0:mWelfares.size();
    }

    public static class WelfareViewHole extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_item_welfare)
        ImageView mImageView;
        public WelfareViewHole(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
