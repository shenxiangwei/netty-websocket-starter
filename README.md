# netty-websocket-starter
基于netty的websocket实现

功能说明
实现了websocket协议的服务端
客户端测试可使用在线客户端 http://www.websocket-test.com/

使用说明

1.添加maven依赖（jdk版本大于1.8）

2.实现MessageListener接口，可对服务端收到的消息自定义处理如下，下面的代码实现了服务端收到的消息，打印到控制台
```java
@Component
public class ListenerImpl implements MessageListener {
    @Override
    public void handleMessage(String result, String channelId) {
        System.out.println(result);
    }
}
```
3.添加配置类,将ListenerImpl注入，并实例化WebsocketServer
```java
@Component
public class TestBean {

    @Autowired
    private ListenerImpl listenerImpl;

    @Bean
    WebsocketServer websocketServer(){
        return new WebsocketServer(listenerImpl);
    }
}
```
4.服务端默认端口8090，可在springboot配置文件，添加如下配置，更改启动端口
websocket.port=8091

5.启动springboot，websocket服务端会在实例化WebsocketServer Bean的时候，启动服务

6.没了，反正也没人看，写给自己当个笔记吧，详细说明见 https://blog.csdn.net/m0_38089214/article/details/107602477


