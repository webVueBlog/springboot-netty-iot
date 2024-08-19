package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.SpringLoader;


@Slf4j
public class App {
    private static final String addr="127.0.0.1";//"127.0.0.1";
    private static final  Integer port=8888;
    public static void main(String[] args) {

        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        NioEventLoopGroup eventExecutors1 = new NioEventLoopGroup();

        try {
            SpringLoader.Init();//初始化spring
            StartServer(eventExecutors,eventExecutors1);//启动服务
        } catch (InterruptedException e) {//表示启动失败
            throw new RuntimeException(e);//表示启动失败
        }
    }

    /**
     * 启动服务
     * @param boss
     * @param work
     * @throws InterruptedException
     */
    private  static  void StartServer(NioEventLoopGroup boss,NioEventLoopGroup work) throws InterruptedException {
        ServerBootstrap bootStrap=new ServerBootstrap()//表示启动服务
                .group(boss,work)//group表示两个线程池
                .channel(NioServerSocketChannel.class)//channel表示使用NIO
                .childHandler(SpringLoader.getBean(ServerInitializer.class));//childHandler表示使用自定义的初始化器
        Channel channel = bootStrap.bind(addr, port)//bind表示绑定地址和端口
                .sync()//sync表示阻塞等待
                .channel();//channel表示获取通道
        log.info("服务启动成功",addr,":",port);//表示启动成功
        channel
                .closeFuture()//closeFuture表示关闭通道
                .sync();//sync表示阻塞等待

    }
}