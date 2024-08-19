package org.example.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.RequestHeaderFrame;
import org.example.process.ProcessFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HevingHandle extends SimpleChannelInboundHandler<RequestHeaderFrame> {
    @Autowired
    ProcessFactory factory;//ProcessFactory表示处理工厂

    /**
     * 处理请求
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestHeaderFrame msg) throws Exception {
                factory
                    .GetProcess(msg.getFrameType())//根据请求类型获取处理类
                    .Process(ctx.channel(),msg);//处理请求
    }

    /**
     * 客户端连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("ip ",ctx.channel().localAddress(),"断开");//表示客户端断开连接
    }
}
