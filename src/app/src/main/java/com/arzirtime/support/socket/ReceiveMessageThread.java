package com.arzirtime.support.socket;

import android.os.Handler;
import android.util.Log;

import com.arzirtime.support.util.MessageUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ReceiveMessageThread extends Thread {

    private ConnectManager connectManager = ConnectManager.getInstance();

    private DataInputStream in = null;
    private Socket socket;
    private boolean stop = false;
    private Handler handler = null;

    public ReceiveMessageThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        while (!stop || !isInterrupted()) {
            try {
                if (this.socket != null) {
                    if (!socket.isConnected() || socket.isClosed()) {
                        MessageUtils.sendMessage(handler, ConnectMessageType.CONNECT_FAILSE, "与服务的连接已断开");
                        return;
                    }

                    in = new DataInputStream(this.socket.getInputStream());
                    byte[] bytes = new byte[in.available()];
                    if (bytes.length != 0) {
                        in.read(bytes);
                        String receiveStr = new String(bytes, "UTF-8");
                        //MessageUtils.sendMessage(handlers, ConnectMessageType.RECEIVE_MSG, receiveStr);
                    }
                } else {
                    MessageUtils.sendMessage(handler, ConnectMessageType.CONNECT_FAILSE, "连接已失效");
                    return;
                }
            } catch (ConnectException e) {
                MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "无法连接");
            } catch (SocketException e) {
                e.printStackTrace();
                MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "连接已失效，请重新连接");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("sss", "run: " + e.getMessage() + e.getClass());
                MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "消息接收失败，系统发生异常");
            }
        }
    }

    public void stopRun() {
        stop = true;
    }

    public void registerHandler(Handler handler) {
        this.handler = handler;
    }
}

