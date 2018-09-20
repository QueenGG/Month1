package com.bawei.chenxiaoxu.month1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.chenxiaoxu.month1.R;
import com.bawei.chenxiaoxu.month1.bean.CartBean;
import com.bawei.chenxiaoxu.month1.witget.MyJIaJianView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/19.
 */

public class PuduAdapter extends RecyclerView.Adapter<PuduAdapter.PudeViewHolder> {
    Context context;
    CartCheckListener checkListener;
    List<CartBean.DataBean.ListBean> listBeanList;
    private CartAllCheckboxListener cartAllCheckboxListener;



    public PuduAdapter(Context context, List<CartBean.DataBean.ListBean> list) {
        this.context=context;
        this.listBeanList=list;
    }

    public void setCartAllCheckboxListener(CartAllCheckboxListener cartAllCheckboxListener) {
        this.cartAllCheckboxListener = cartAllCheckboxListener;
    }

    public void setCheckListener(CartCheckListener checkListener) {
        this.checkListener = checkListener;
    }

    @NonNull
    @Override
    public PudeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pude_item, parent, false);
        PudeViewHolder pudeViewHolder = new PudeViewHolder(inflate);
        return  pudeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PudeViewHolder holder, int position) {
        final CartBean.DataBean.ListBean listBean = listBeanList.get(position);
        holder.paduprice.setText("优惠价:¥"+listBean.getBargainPrice());
        holder.paduName.setText(listBean.getTitle());
        String[] img = listBean.getImages().split("\\|");
        if(img!=null&&img.length>0){
            Glide.with(context).load(img[0]).into(holder.paduicon);
        }else{
            holder.paduicon.setImageResource(R.mipmap.ic_launcher);
        }

        holder.paduCheckbox.setChecked(listBean.isSelected());

        holder.jiajianqi.setNum(listBean.getToNum());
        holder.jiajianqi.setJiaJianListener(new MyJIaJianView.JiaJianListener() {
            @Override
            public void getNum(int num) {
                listBean.setToNum(num);
                if(checkListener!=null){
                    checkListener.notityParent();
                }
            }
        });
        holder.paduCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.paduCheckbox.isChecked()){
                    listBean.setSelected(true);
                }else{
                    listBean.setSelected(false);
                }
                if(checkListener!=null){
                    checkListener.notityParent();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeanList.size();
    }

    class PudeViewHolder extends RecyclerView.ViewHolder {

        private  CheckBox paduCheckbox;
        private  ImageView paduicon;
        private  MyJIaJianView jiajianqi;
        private  TextView paduprice;
        private  TextView paduName;

        public PudeViewHolder(View itemView) {
            super(itemView);
            paduCheckbox = itemView.findViewById(R.id.paduCheckbox);
            paduicon = itemView.findViewById(R.id.paduicon);
            paduName = itemView.findViewById(R.id.paduName);
            jiajianqi = itemView.findViewById(R.id.jiajianqi);
            paduprice = itemView.findViewById(R.id.paduprice);
        }
    }

}
