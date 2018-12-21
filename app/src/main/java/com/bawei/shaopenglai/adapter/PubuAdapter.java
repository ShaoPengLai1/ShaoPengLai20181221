package com.bawei.shaopenglai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.DaoHangBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PubuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DaoHangBean.DataBean> mData=new ArrayList<>();
    private Context context;

    public PubuAdapter(Context context) {
        this.context = context;
    }

    public void setmData(List<DaoHangBean.DataBean> datas) {
        mData.clear();
        if (datas!=null){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public List<DaoHangBean.DataBean> getmData() {
        return mData;
    }

    public void addData(List<DaoHangBean.DataBean> datas) {
        if (datas!=null){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_image,viewGroup,false);
        return new ViewImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewImageHolder imageHolder= (ViewImageHolder) viewHolder;
        Glide.with(context).load(mData.get(i).getIcon()).into(imageHolder.img);
        imageHolder.ll_item_recycle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongItemClickListener!=null){
                    mOnLongItemClickListener.OnLongItemClick(i);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class ViewImageHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private LinearLayout ll_item_recycle;
        public ViewImageHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.iv_img);
            ll_item_recycle=itemView.findViewById(R.id.ll_item_recycle);
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public interface OnLongItemClickListener{
        void OnLongItemClick(int position);
    }
    private ShoppingAdapter.OnItemClickListener mOnItemClickListener;
    private ShoppingAdapter.OnLongItemClickListener mOnLongItemClickListener;

    public void setOnItemClickListener(ShoppingAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(ShoppingAdapter.OnLongItemClickListener onLongItemClickListener) {
        this.mOnLongItemClickListener = onLongItemClickListener;
    }
}
