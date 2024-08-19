package org.example.process.strategy;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import org.example.entity.RequestHeaderFrame;

/**
 * ProcessStrategy表示处理策略
 */
public interface ProcessStrategy {
    void  Process(Channel channel, RequestHeaderFrame frame);//表示处理请求
}
