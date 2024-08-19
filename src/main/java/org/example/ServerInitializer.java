package org.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.example.codec.HeaderFrameDecoder;
import org.example.codec.HeaderFrameEncoder;
import org.example.handle.HevingHandle;
import org.example.spring.SpringLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerInitializer extends ChannelInitializer {
    @Autowired
    HevingHandle hevingHandle;//表示服务端处理器

    //初始化通道
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()//添加解码器
                .addLast(SpringLoader.getBean(HeaderFrameDecoder.class))//添加编码器
                .addLast(SpringLoader.getBean(HeaderFrameEncoder.class))//添加处理器
                .addLast(hevingHandle);//表示服务端处理器
    }
}
