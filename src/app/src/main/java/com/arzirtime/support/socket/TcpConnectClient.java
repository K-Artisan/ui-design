package com.arzirtime.support.socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.arzirtime.support.util.MessageUtils;

import java.net.Socket;
import java.util.UUID;

public class TcpConnectClient {

    private String id;
    private String ip;
    private int port;
    private int timeout = NetConstanct.SOCKET_TIMEOUT;//30000ms
    private Socket socket = null;

    //Socket连接线程
    private SocketConnecthread connectThread;
    //发送消息的线程
    private SendMessageThread sendMsgThread;
    //接收消息的线程
    private ReceiveMessageThread receiveMsgThread;
    //回调
    private Handler connectThreadHandler = null;
    private Handler sendMsgThreadHandler = null;
    private Handler receiveMsgThreadHandler = null;

    // Client内部连接Handler
    private Handler myConnectHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            //转发【连接线程】回调
            MessageUtils.sendMessage(connectThreadHandler, MessageUtils.copyFrom(msg));

            switch (msg.what) {
                case ConnectMessageType.CONNECT_SUCCESS:
                    doWhenConnectSuccess(msg);
                    break;
                default:
                    doWhenConnectFailse(msg);
                    break;
            }
        }

    };

    //region Set\Get 方法

    public String getId() {
        return id;
    }

    //endregion f fang

    public TcpConnectClient(String ip, int port, int timeout) {
        id = UUID.randomUUID().toString();
        this.ip = ip;
        this.port = port;
        this.timeout = timeout;
    }

    public boolean isConnected() {
        return this.socket != null && this.socket.isConnected() && this.socket.isClosed();
    }

    public void connect() {
        connect(ip, port, timeout);
    }

    private void connect(String ip, int port, int timeout) {
        if (!isConnected()) {
            socket = new Socket();
            connectThread = new SocketConnecthread(socket, ip, port, timeout > 0 ? timeout : this.timeout);
            connectThread.registerHandler(myConnectHandler);

            connectThread.start();  //开启链接服务器的线程
        }
    }

    /**
     * 连接成功后，做些初始化工作
     */
    private void doWhenConnectSuccess(Message message) {
        startSendMessageThread();
        startReceiveMessageThread();
    }

    private void startSendMessageThread() {
        sendMsgThread = new SendMessageThread(socket);
        sendMsgThread.registerHandler(sendMsgThreadHandler);

        sendMsgThread.start();
    }

    private void startReceiveMessageThread() {
        receiveMsgThread = new ReceiveMessageThread(socket);
        receiveMsgThread.registerHandler(receiveMsgThreadHandler);

        receiveMsgThread.start();
    }

    private void doWhenConnectFailse(Message message) {

    }

    public void close() {
        if (this.socket != null) {
            try {
                this.socket.close();
                this.socket = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (connectThread != null) {
            connectThread.interrupt();
            connectThread = null;
        }

        if (sendMsgThread != null) {
            sendMsgThread.stopRun();
            sendMsgThread.interrupt();
            sendMsgThread = null;
        }

        if (receiveMsgThread != null) {
            receiveMsgThread.stopRun();
            receiveMsgThread.interrupt();
            receiveMsgThread = null;
        }

    }

    public void registerConnectThreadHandler(Handler handler) {
        connectThreadHandler = handler;
    }

    public void registerSendMsgThreadThreadHandler(Handler handler) {
        sendMsgThreadHandler = handler;
    }

    public void registerReceiveMsgThreadHandler(Handler handler) {
        receiveMsgThreadHandler = handler;
    }

    /**
     * 检测连接是否有效，
     * 无效自动初始化新的连接
     * 适用场景：IP和端口保存不变
     */
    public void tryAutoConnect() {
        tryAutoConnect(ip, port, timeout);
    }

    /**
     * 检测连接是否有效，
     * 无效自动初始化新的连接
     * 适用场景：需要换IP和端口
     */
    public void tryAutoConnect(String ip, int port, int timeout) {
        if (isSameConnect(ip, port)) { //IP和端口不变
            if (!isConnected()) {
                connect(ip, port, timeout);
            }
        } else { //连接新的服务器,关闭旧连接
            close(); //连接新的服务器前需要将旧的连接销毁
            connect(ip, port, timeout);
        }
    }

    public boolean isSameConnect(String ip, int port) {
        return this.ip.equals(ip) && this.port == port;
    }

    /**
     * 向服务端发送消息
     */
    public void sendMsgToServer(String msg) throws Exception {
        if (sendMsgThread != null) {
            sendMsgThread.send(msg);
        } else {
            throw new Exception("sendMsgThread is null");
        }
    }
}
