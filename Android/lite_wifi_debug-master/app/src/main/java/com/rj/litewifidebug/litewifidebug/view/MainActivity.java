package com.rj.litewifidebug.litewifidebug.view;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rj.litewifidebug.litewifidebug.R;
import com.rj.litewifidebug.litewifidebug.utils.Utility;

/**
 * Created by rosejames on 15/2/3.
 */
public class MainActivity extends Activity {

    private EditText mEtPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtPort = (EditText) findViewById(R.id.et_port);
    }

    public void onClickConnect(View v) {
        String port = mEtPort.getText().toString();
        if (port.equals("") || Integer.valueOf(port) <= 0) {
            port = "5555";
        }
        if (Utility.exec("setprop service.adb.tcp.port " + port)) {
            if (Utility.exec("stop adbd")) {
                setTip("stop adbd successful");
                if (Utility.exec("start adbd")) {
                    setTip("Connect successful! Please enter those common in computer:\n" +
                            "adb connect " + getIp() + ":" + port);
                } else {
                    setTip("start adbd failed");
                }
            } else {
                setTip("stop adbd failed");
            }
        } else {
            setTip("setprop failed");
        }
    }

    public void onClickDisconnect(View v) {
        if (Utility.exec("setprop service.adb.tcp.port -1")) {
            if (Utility.exec("stop adbd")) {
                setTip("stop adbd successful");
                if (Utility.exec("start adbd")) {
                    setTip("Disconnect successful");
                } else {
                    setTip("start adbd failed");
                }
            } else {
                setTip("stop adbd failed");
            }
        } else {
            setTip("setprop failed");
        }

    }

    public void onClickReconnect(View v) {
        onClickDisconnect(v);
        onClickConnect(v);
    }

    private void setTip(String str) {
        ((TextView) findViewById(R.id.tv_tip)).setText(str);
    }

    private String getIp() {    //获取wifi服务
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return intToIp(ipAddress);
    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
}
