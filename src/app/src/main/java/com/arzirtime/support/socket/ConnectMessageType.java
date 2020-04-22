package com.arzirtime.support.socket;

public class ConnectMessageType {

    public static  final  int SEND_MSG = 1;      //发向服务器的消息
    public static  final  int RECEIVE_MSG = 2;   //接收来自服务器的消息

    public static  final  int CONNECT_SUCCESS = 1001;   //连接成功
    public static  final  int CONNECT_FAILSE = 1002;   //连接失败
    public static  final  int CONNECT_TIMEOUT = 1003;   //连接超时

    public static  final  int SYSTEM_EORROR = 2020;    //系统消息异常
}
