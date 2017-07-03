package com.orderSystem.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.util.Log;
import android.widget.Toast;

import com.orderSystem.HttpUtils;
import com.orderSystem.R;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CT on 2017/6/10.
 */

public class NFCActivity extends Activity  {
    private static final String TAG = "NFCActivity";
    private String user = "";
    private Float totalPrice = 0.0f;
    float balance = 0.0f;
    NfcAdapter mNfcAdapter;
    boolean flag = true; //用于判断付款是否成功

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_payment);

        //获取NFC
        mNfcAdapter = mNfcAdapter.getDefaultAdapter(this);

        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        user = sharedPreferences.getString("user", "");
        totalPrice = sharedPreferences.getFloat("totalPrice", 0.0f);
        //使用toast信息提示框显示信息

        String url = "http://114.215.85.51/seprac/backend.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", user);
        params.put("method", "money");

        final String result = HttpUtils.submitPostData(url, params, "utf-8");

        balance = Float.parseFloat(result);

        Toast.makeText(getApplicationContext(), "剩余金额：" + balance,
                Toast.LENGTH_SHORT).show();

        if (balance < totalPrice) {
            flag = true;
        } else {
            flag = false;
        }
        //pay("trader@qq.com", "potato");

    }

    private String readFromTag(Intent intent) {
        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
        NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
        try {
            if (mNdefRecord != null) {
                String readResult = new String(mNdefRecord.getPayload(), "UTF-8");
                return readResult;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ;
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        //接收到数据后进行处理
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            String tradeInfo=readFromTag(getIntent());
            String[] info=tradeInfo.split(" ");
            pay(user,"wanghaobo",info[0],info[1]);
        }
    }

    private void pay(String trader, String food, String user, String totalPrice) {
        String url = "http://114.215.85.51/seprac/backend.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("money", totalPrice);
        params.put("email1", user);
        params.put("email2", trader);
        params.put("food", food);
        params.put("method", "trade");

        final String result = HttpUtils.submitPostData(url, params, "utf-8");
        Log.d("TRADE:", result);
        String[] resList = result.split("&");
        if (resList[0].equals("200")) {
            String msg = "";
            msg += "本次收款：" + totalPrice + " 元\n";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Payment Successful");
            builder.setMessage(msg);
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Payment Failed");
            builder.setMessage("对方余额不足！支付失败！");
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

    private void onPayFailed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Failed");
        builder.setMessage("余额不足！");
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void onPaySuccess(float balance, float totalPrice) {
        String msg = "";
        msg += "本次付款：" + totalPrice + " 元\n";
        msg += "账户余额：" + balance + " 元";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Successful");
        builder.setMessage(msg);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


}
