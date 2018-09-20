package com.bawei.chenxiaoxu.month1.adapter;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bawei.chenxiaoxu.month1.R;
import com.bawei.chenxiaoxu.month1.activity.MainActivity;
import com.bawei.chenxiaoxu.month1.bean.CartBean;

import java.util.List;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/19.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements CartCheckListener{
    Context context;
    List<CartBean.DataBean> cartlist;
    private CartAllCheckboxListener allCheckboxListener;
    private PuduAdapter puduAdapter;

    public CartAdapter(Context context, List<CartBean.DataBean> list) {
        this.context=context;
        this.cartlist=list;
    }
    public void addPageData(List<CartBean.DataBean> list){
            if(cartlist!=null){
                list.addAll(list);
                notifyDataSetChanged();
            }
    }

    public void setCartAllCheckboxListener(CartAllCheckboxListener allCheckboxListener) {
        this.allCheckboxListener = allCheckboxListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(inflate);

        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, int position) {
        final CartBean.DataBean dataBean = cartlist.get(position);
        holder.sellerName.setText(dataBean.getSellerName());
       holder.sellerCheckbox.setChecked(dataBean.isSelected());
       holder.relist_zi.setLayoutManager(new LinearLayoutManager(context));

        puduAdapter = new PuduAdapter(context,dataBean.getList());
        holder.relist_zi.setAdapter(puduAdapter);

        puduAdapter.setCheckListener(this);
        for (int i = 0; i < dataBean.getList().size(); i++) {
            if(!dataBean.getList().get(i).isSelected()){
                holder.sellerCheckbox.setChecked(false);
                break;
            }else{
                holder.sellerCheckbox.setChecked(true);
            }
        }

        holder.sellerCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.sellerCheckbox.isChecked()){
                        dataBean.setSelected(true);
                    for (int i = 0; i < dataBean.getList().size(); i++) {
                            dataBean.getList().get(i).setSelected(true);
                    }
                }else{
                    dataBean.setSelected(false);
                    for (int i = 0; i < dataBean.getList().size(); i++) {
                        dataBean.getList().get(i).setSelected(false);
                    }
                }
                notifyDataSetChanged();
                if(allCheckboxListener!=null){
                    allCheckboxListener.notifyAllCheckboxStatus();
                }
            }
        });
    }

    public List<CartBean.DataBean> getCartlist() {
        return cartlist;
    }

    @Override
    public int getItemCount() {
        return cartlist.size()==0?0:cartlist.size();
    }

    @Override
    public void notityParent() {
        notifyDataSetChanged();
        if(allCheckboxListener!=null){
            allCheckboxListener.notifyAllCheckboxStatus();
        }
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        private  TextView sellerName;
        private  CheckBox sellerCheckbox;
        private  RecyclerView relist_zi;

        public CartViewHolder(View itemView) {
            super(itemView);
            sellerCheckbox = itemView.findViewById(R.id.sellerCheckbox);
            sellerName = itemView.findViewById(R.id.sellerName);
            relist_zi = itemView.findViewById(R.id.relist_zi);

        }
    }

}
