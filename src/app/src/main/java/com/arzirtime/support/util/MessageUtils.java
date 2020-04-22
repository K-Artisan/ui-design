package com.arzirtime.support.util;

import android.os.Handler;
import android.os.Message;

import java.util.List;

public class MessageUtils {

    public static void sendMessage(Handler handler, int what, Object object) {
        if (handler != null) {
            Message msg = new Message();
            msg.what = what;
            msg.obj = object;
            handler.sendMessage(msg);
        }
    }

    public static void sendMessage(List<Handler> handlers, int what, Object object) {

        if (handlers != null) {
            for (Handler handler : handlers) {
                Message msg = new Message();
                msg.what = what;
                msg.obj = object;
                handler.sendMessage(msg);
            }
        }
    }

    public static void sendMessage(Handler handler, Message msg) {
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    public static Message copyFrom(Message msg){
        Message  message= new Message();
        message.copyFrom(msg);
        return message;
    }
}
