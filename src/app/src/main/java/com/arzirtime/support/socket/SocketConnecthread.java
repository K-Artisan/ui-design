package com.arzirtime.support.socket;

import android.os.Handler;
import android.util.Log;

import com.arzirtime.support.util.MessageUtils;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class SocketConnecthread extends Thread {

    private ConnectManager connectManager = ConnectManager.getInstance();
    private String ip;
    private int port;
    private int timeout;
    private Socket socket = null;
    private Handler handler = null;

    public SocketConnecthread(Socket socket, String ip, int port, int timeout) {
        this.socket = socket;
        this.ip = ip;
        this.port = port;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.setKeepAlive(true);//设置为长连接

            MessageUtils.sendMessage(handler, ConnectMessageType.CONNECT_SUCCESS, "连接成功 O(∩_∩)O ");
        } catch (ConnectException e) {
            MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "网络不可用，请尝试检测网络是否可用");
        } catch (SocketTimeoutException e) {
            MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "\n连接超时，请检查：" +
                    "\n 1.服务器地址是否正确； " +
                    "\n 2.服务器是否开启； " +
                    "\n 3.是否与服务器位于同一网段");

        } catch (SocketException e) {
            e.printStackTrace();
            Log.d("sss", "run: " + e.getMessage() + e.getClass());
            MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "连接已失效，请重新连接");

            return;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("sss", "run: " + e.getMessage() + e.getClass());
            MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "连接服务器时，系统发生异常 ");
        }
    }

    public void registerHandler(Handler handler) {
        this.handler = handler;
    }

}