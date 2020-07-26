package com.sxw.websocket.listener;

/**
 * create by shenxiangwei on 2020/7/25 下午 7:01
 */
public interface MessageListener {

    /**
     * 服务端收到客户端消息时会调用此方法
     *
     * @param result 客户端发送的消息
     * @param channelId 对应的channelId,可看作客户端id
     */
    void handleMessage(String result, String channelId);
}
