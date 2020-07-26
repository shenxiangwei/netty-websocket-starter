package com.sxw.websocket.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by shenxiangwei on 2020/7/25 下午 7:24
 */
public class MessageListenerImpl implements MessageListener{

    private static final Logger logger = LoggerFactory.getLogger(MessageListenerImpl.class);

    @Override
    public void handleMessage(String result, String channelId){
        System.out.println(String.format("收到客户端：%s的消息，消息内容：%s",channelId,result));
        logger.info(String.format("收到客户端：%s的消息，消息内容：%s",channelId,result));
    }
}
