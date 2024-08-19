package org.example.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.example.entity.RequestHeaderFrame;
import org.example.utls.CheckSumUtls;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 头部帧解码器
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HeaderFrameDecoder extends ByteToMessageDecoder {
    private static final int FRAME_HEADER_LENGTH = 2;// 帧头长度
    private static final int FRAME_TYPE_LENGTH = 1;// 帧类型长度
    private static final int DATA_LENGTH_LENGTH = 2;// 数据长度长度
    private static final int LONGITUDE_LENGTH = 8;// 经度长度
    private static final int LATITUDE_LENGTH = 8;// 经纬度长度
    private static final int CABINET_ID_LENGTH = 16;// 柜子编号长度
    private static final int CHECKSUM_LENGTH = 4;// 校验和长度
    private static final int FRAME_TAIL_LENGTH = 2;// 帧尾长度
    private static final int HEADER_FRAME_LENGTH =
            FRAME_HEADER_LENGTH + FRAME_TYPE_LENGTH + DATA_LENGTH_LENGTH +
                    LONGITUDE_LENGTH + LATITUDE_LENGTH + CABINET_ID_LENGTH +
                    CHECKSUM_LENGTH + FRAME_TAIL_LENGTH;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 检查是否有足够的字节来解析头部帧
        if (in.readableBytes() < HEADER_FRAME_LENGTH) {
            return; // 等待更多数据
        }

        // 校验帧头
        if (in.readByte() != (byte) 0xAA || in.readByte() != (byte) 0xBB) {
            throw new Exception("Invalid frame header");
        }

        // 读取帧类型
        byte frameType = in.readByte();

        // 读取数据长度
        short dataLength = in.readShort();

        // 读取经度和纬度
        long longitude = in.readLong();
        long latitude = in.readLong();

        // 读取柜子编号
        byte[] cabinetId = new byte[16];
        in.readBytes(cabinetId);

        // 读取数据部分
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        // 读取校验和
        int checksum = in.readInt();

        int calculatedChecksum = CheckSumUtls.CheckSum(data);
        if (checksum != calculatedChecksum) {
            throw new Exception("Checksum verification failed");
        }
        // 校验帧尾
        if (in.readByte() != (byte) 0xCC || in.readByte() != (byte) 0xDD) {
            throw new Exception("Invalid frame tail");
        }

        // 创建并填充HeaderFrame对象
        RequestHeaderFrame headerFrame = new RequestHeaderFrame();
        headerFrame.setFrameType(frameType);
        headerFrame.setDataLength(dataLength);
        headerFrame.setLongitude(longitude);
        headerFrame.setLatitude(latitude);
        headerFrame.setCabinetId(cabinetId);
        headerFrame.setData(ByteArrayToByteBuf(data));
        headerFrame.setChecksum(checksum);

        // 将解析后的HeaderFrame对象添加到输出列表
        out.add(headerFrame);
    }

    /**
     * 将字节数组转换为ByteBuf
     * @param array
     * @return
     */
    private ByteBuf ByteArrayToByteBuf(byte[] array){
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();//ByteBufAllocator.DEFAULT.buffer(array.length);
        buffer.writeBytes(array);//表示将字节数组中的数据写入到ByteBuf中
        return  buffer;
    }
}
