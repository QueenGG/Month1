package com.bawei.chenxiaoxu.month1.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.chenxiaoxu.month1.R;
import com.bawei.chenxiaoxu.month1.adapter.CartAdapter;
import com.bawei.chenxiaoxu.month1.adapter.CartAllCheckboxListener;
import com.bawei.chenxiaoxu.month1.bean.CartBean;
import com.bawei.chenxiaoxu.month1.pesenter.IPesenterImp;
import com.bawei.chenxiaoxu.month1.view.IView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView,CartAllCheckboxListener{

    public static final String URL_STRING="https://www.zhaoapi.cn/product/getCarts";
    private XRecyclerView xlist_cart;
    private CheckBox allcheckbox;
    private TextView totalprice;
    private List<CartBean.DataBean> list;
    private int page=1;
    private IPesenterImp iPesenter;
    private CartAdapter cartAdapter;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String string = (String) msg.obj;
                //Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                CartBean cartBean = gson.fromJson(string, CartBean.class);
                showas(cartBean);
            }
        }
    };

    private void showas(CartBean cartBean) {

        List<CartBean.DataBean> data = cartBean.getData();
        if(cartBean!=null&&data!=null){
            if(page==1){
                list=data;
                cartAdapter = new CartAdapter(MainActivity.this,list);
                xlist_cart.setAdapter(cartAdapter);
                xlist_cart.refreshComplete();
            }else{
                if(cartAdapter!=null){
                    cartAdapter.addPageData(cartBean.getData());
                }
                xlist_cart.loadMoreComplete();
            }
            cartAdapter.setCartAllCheckboxListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        loadData();
    }

    private void initview() {
        list = new ArrayList<>();
        xlist_cart = findViewById(R.id.xlist_cart);
        allcheckbox = findViewById(R.id.allcheckbox);
        totalprice = findViewById(R.id.totalprice);

        //设置线性布局
        xlist_cart.setLayoutManager(new LinearLayoutManager(this));

        //加载更多
        xlist_cart.setLoadingMoreEnabled(true);
        xlist_cart.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData();
            }
        });
        //全选
        allcheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allcheckbox.isChecked()){
                    if(list != null & list.size()>0){
                        for (int i = 0; i <list.size() ; i++) {
                            list.get(i).setSelected(true);
                            for (int j = 0; j < list.get(i).getList().size(); j++) {
                                list.get(i).getList().get(j).setSelected(true);
                            }
                        }
                    }
                }else{
                    if(list != null & list.size()>0){
                        for (int i = 0; i <list.size() ; i++) {
                            list.get(i).setSelected(false);
                            for (int j = 0; j < list.get(i).getList().size(); j++) {
                                list.get(i).getList().get(j).setSelected(false);
                            }
                        }
                    }

                }
                cartAdapter.notifyDataSetChanged();
                totalPrice();
            }
        });
    }

    private void loadData() {
       /* HashMap<String,String> params=new HashMap<>();
        params.put("uid","71");
        params.put("page",page+"");
*/
        iPesenter = new IPesenterImp(this);
        iPesenter.getData(page,URL_STRING);
    }

    @Override
    public void Show(final String string) {
        Message message = new Message();
        message.what=0;
        message.obj=string;
        handler.sendMessage(message);
    }

    @Override
    public void notifyAllCheckboxStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        if(cartAdapter!=null){
            for (int i = 0; i < cartAdapter.getCartlist().size(); i++) {
                stringBuilder.append(cartAdapter.getCartlist().get(i).isSelected());
                for (int j = 0; j < cartAdapter.getCartlist().get(i).getList().size(); j++) {
                    stringBuilder.append(cartAdapter.getCartlist().get(i).getList().get(j).isSelected());
                }
            }
        }
        System.out.println("sb=====" + stringBuilder.toString());
        if(stringBuilder.toString().contains("false")){
            allcheckbox.setChecked(false);
        }else{
            allcheckbox.setChecked(true);
        }
        totalPrice();
    }

    private void totalPrice() {
        double totalPrice=0;
        for (int i = 0; i < cartAdapter.getCartlist().size(); i++) {
            for (int j = 0; j < cartAdapter.getCartlist().get(i).getList().size(); j++) {
                if(cartAdapter.getCartlist().get(i).getList().get(j).isSelected()){
                    CartBean.DataBean.ListBean listBean = cartAdapter.getCartlist().get(i).getList().get(j);
                    totalPrice +=listBean.getBargainPrice()*listBean.getToNum();
                }
            }
        }
        totalprice.setText("总价：¥"+totalPrice);
    }
}
