package com.lids.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value="/websocketTest")
public class WebSocketTest {

    @OnMessage      //这里是方法注解，在接收到客户端的消息时触发
    public String echo(String clientMessage){
        return "收到来自客户端的消息：" + clientMessage;
    }

//    private Logger logger = LoggerFactory.getLogger(WebSocketTest.class);
//
//    private static String userId;
//
//    //连接时执行
//    @OnOpen
//    public void onOpen(Session session) throws IOException {
//        logger.debug("新连接");
//    }
//
//    //关闭时执行
//    @OnClose
//    public void onClose(){
//        logger.debug("连接关闭");
//    }
//
//    //收到消息时执行
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException {
//        logger.debug("收到用户消息{}",message);
//        session.getBasicRemote().sendText("收到消息 "); //回复用户
//    }
//
//    //连接错误时执行
//    @OnError
//    public void onError(Session session, Throwable error){
//        logger.debug("用户id为：的连接发送错误");
//        error.printStackTrace();
//    }
}
