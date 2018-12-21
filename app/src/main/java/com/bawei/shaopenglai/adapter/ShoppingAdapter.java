package com.bawei.shaopenglai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.DaoHangBean;
import com.bawei.shaopenglai.bean.ShoppingBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShoppingBean.DataBean> mList=new ArrayList<>();
    private Context context;
    private Boolean boo;

    public ShoppingAdapter(Context context) {
        this.context = context;
    }

    public ShoppingAdapter(Context context, Boolean isLiener) {
        this.context = context;
        this.boo = isLiener;
    }

    public void delData(int i){
        mList.remove(i);
        notifyDataSetChanged();
    }

    public List<ShoppingBean.DataBean> getmData(){
        return mList;
    }
    public void setData(List<ShoppingBean.DataBean> datas) {
        mList.clear();
        if (datas!=null){
            mList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addData(List<ShoppingBean.DataBean> datas) {

        if (datas!=null){
            mList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder=null;
        if (boo){
            View view=LayoutInflater.from(context).inflate(R.layout.item_trecycle_linear,viewGroup,false);
            holder= new ViewHolderLinear(view);
        }else {
            View view=LayoutInflater.from(context).inflate(R.layout.item_trecycle_grid,viewGroup,false);
            holder= new ViewHolderGrid(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        String images = mList.get(i).getImages();
        String[] split = images.split("\\|");
        if (boo){
            ViewHolderLinear holderLinear= (ViewHolderLinear) viewHolder;
            holderLinear.tv1.setText(mList.get(i).getTitle());
            holderLinear.tv2.setText(mList.get(i).getPrice()+"");
            Glide.with(context).load(split[0]).into(holderLinear.img);
            holderLinear.ll_item_recycle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener!=null){
                        mOnItemClickListener.OnItemClick(i);
                    }
                }
            });
            holderLinear.ll_item_recycle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnLongItemClickListener!=null){
                        mOnLongItemClickListener.OnLongItemClick(i);
                    }
                    return true;
                }

            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public static class ViewHolderLinear extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tv1,tv2;
        private LinearLayout ll_item_recycle;

        public ViewHolderLinear(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_linear);
            tv1=itemView.findViewById(R.id.tv1_linear);
            tv2=itemView.findViewById(R.id.tv2_linear);
            ll_item_recycle=itemView.findViewById(R.id.ll_item_recycle);
        }
    }
    public static class ViewHolderGrid extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tv1,tv2;
        public ViewHolderGrid(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_grid);
            tv1=itemView.findViewById(R.id.tv1_grid);
            tv2=itemView.findViewById(R.id.tv2_grid);
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public interface OnLongItemClickListener{
        void OnLongItemClick(int position);
    }
    private OnItemClickListener mOnItemClickListener;
    private OnLongItemClickListener mOnLongItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        this.mOnLongItemClickListener = onLongItemClickListener;
    }
}
