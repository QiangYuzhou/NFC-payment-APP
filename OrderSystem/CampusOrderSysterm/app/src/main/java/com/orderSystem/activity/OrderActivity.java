package com.orderSystem.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orderSystem.HttpUtils;
import com.orderSystem.R;
import com.orderSystem.adapter.OrderAdapter;
import com.orderSystem.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView  mListView;
    private OrderAdapter orderAdapter;
    private List<Order> mlist;
    private RelativeLayout mainLayout;
    private TextView myBalance;
    private String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidingpane_home_layout);

        initData();
        initView();
        initAdapter();
    }
    public void initView(){
        mainLayout = (RelativeLayout)findViewById(R.id.order_layout);
        mListView = (RecyclerView)findViewById(R.id.mineList);
        myBalance = (TextView)findViewById(R.id.myBalance);

        myBalance.setText("账户余额：" + balance);
        mListView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData(){
        SharedPreferences sharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String user = sharedPreferences.getString("user", "");

        String url="http://114.215.85.51/seprac/backend.php";
        Map<String,String> params=new HashMap<String, String>();
        params.put("email", user);
        params.put("method", "orderList");

        String result= HttpUtils.submitPostData(url,params,"utf-8");
        Log.d("ORDERLIST:", result);
        balance = result.split("#")[0];
        result = result.split("#")[1];

        String[] resList = result.split("&");

        mlist = new ArrayList<Order>();
        for (int i = 0; i < resList.length; i++){
            Log.d("ORDERLIST2:", resList[i]);
            String[] temp = resList[i].split(",");
            mlist.add(new Order(temp[0], Double.parseDouble(temp[1]), temp[2]));
        }
    }

    private void initAdapter(){
        orderAdapter = new OrderAdapter(this,mlist);
        mListView.setAdapter(orderAdapter);
    }
}
