package org.example.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.example.entity.ResponsHeaderFream;
import org.example.utls.CheckSumUtls;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 响应头帧编码器
 * SCOPE_PROTOTYPE：每次请求都会创建一个新的实例
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HeaderFrameEncoder extends MessageToByteEncoder<ResponsHeaderFream> {

    @Override
    protected void encode(ChannelHandlerContext ctx,  ResponsHeaderFream headerFrame, ByteBuf out) throws Exception {
        // 帧头
        out.writeShort(0xAABB);

        // 帧类型
        out.writeByte(headerFrame.getFrameType());

        // 数据长度
        out.writeShort(headerFrame.getDataLength());

        // 数据部分
        out.writeBytes(headerFrame.getData());

        // 计算并写入校验和（示例：简单的校验和算法）
        int checksum = CheckSumUtls.CheckSum(headerFrame.getData());
        out.writeInt(checksum);

        // 帧尾
        out.writeShort(0xAABB);
    }
}
