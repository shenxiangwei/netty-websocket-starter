package com.sxw.websocket.util;

import com.sxw.websocket.Global;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.ArrayList;
import java.util.List;

/**
 * create by shenxiangwei on 2020/7/25 下午 7:35
 */
public class Message {

    /**
     * 向对应客户端发送消息
     *
     * @param message 消息内容
     * @param channelId 客户端channelId
     * @throws RuntimeException 客户端不存在异常
     */
    public static void send(String message,String channelId) throws RuntimeException{
        if(null == Global.channelIdMap.get(channelId)){
            throw new RuntimeException(String.format("客户端：%s 不存在",channelId));
        }
        Channel channel = Global.group.find(Global.channelIdMap.get(channelId));
        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        channel.writeAndFlush(tws);
    }

    /**
     * 向所有客户端发送消息
     *
     * @param message 消息内容
     */
    public static void sendToAll(String message){
        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        Global.group.writeAndFlush(tws);
    }

    /**
     * 获取所有channelId
     *
     * @return channelId集合
     */
    public static List<String> getAllChannelId(){
        return new ArrayList<>(Global.channelIdMap.keySet());
    }
}
