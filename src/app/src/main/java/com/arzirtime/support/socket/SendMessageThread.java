package com.arzirtime.support.socket;

import android.os.Handler;

import com.arzirtime.support.util.MessageUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SendMessageThread extends Thread {

    private Socket socket;
    private DataOutputStream out = null;
    private boolean stop = false;

    Queue<String> queue = new LinkedList<String>(); //消息队列：用于暂存用户需要发向服务器的消息
    private Handler handler = null;

    //构造方法(Socket, Handler)
    public SendMessageThread(Socket socket) {
        this.socket = socket;
    }

    //发向服务器的消息
    public void send(String message) {
        queue.offer(message);
    }

    @Override
    public void run() {
        while (!stop || !isInterrupted()) {
            if (!queue.isEmpty()) {
                sendMsg(queue.poll());
            }
        }
    }

    public void stopRun() {
        stop = true;
    }

    //发送消息的方法
    protected void sendMsg(String message) {

        try {
            if (this.socket != null) {
                if (!socket.isConnected()) {
                    MessageUtils.sendMessage(handler, ConnectMessageType.CONNECT_FAILSE, "与服务的连接已断开");
                    return;
                }
                out = new DataOutputStream(this.socket.getOutputStream());
                byte[] by = (message + "\n").getBytes();
                out.write(by);
                out.flush();

                MessageUtils.sendMessage(handler, ConnectMessageType.SEND_MSG,  message);
            } else {
                MessageUtils.sendMessage(handler, ConnectMessageType.CONNECT_FAILSE, "连接已失效");
                return;
            }
        } catch (ConnectException e) {
            MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "无法连接");
        } catch (SocketException e) {
            e.printStackTrace();
            MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "连接已失效，请重新连接");

            return;
        } catch (IOException e) {
            MessageUtils.sendMessage(handler, ConnectMessageType.SYSTEM_EORROR, "发送失败，系统发生异常");
            return;
        }

    }

    public void registerHandler(Handler handler) {
        this.handler = handler;
    }

}
