package com.orderSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orderSystem.activity.CartActivity;
import com.orderSystem.activity.LoginActivity;
import com.orderSystem.activity.OrderActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        这里先调用主页
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.order_list_button) {
            // 查看订单列表
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goOrderList(View v)
    {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    public void goFoodList(View v)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

}
