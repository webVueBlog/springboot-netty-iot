package org.example.process.strategy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import org.example.cache.CacheService;
import org.example.entity.CmdTypeEnum;
import org.example.entity.RequestHeaderFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 存放包裹处理策略
 */
@Service("PutParcelStrategy")
public class PutParcelStrategy implements ProcessStrategy{//表示存放包裹
    private int UIDLENGTH=8;//用户id长度
    private int PUTID=8;//存放id长度
    private int PACKGEID=16;//包裹id长度

    @Autowired
    CacheService service;//存放包裹

    /**
     * 存放包裹
     * @param channel
     * @param frame
     */
    @Override
    public void Process(Channel channel, RequestHeaderFrame frame) {
        ByteBuf buf = frame.getData();//存放包裹
        byte[] uidArray= new byte[UIDLENGTH];//用户id
        buf.readBytes(uidArray);//读取用户id
        byte[] putArray= new byte[UIDLENGTH];//存放id
        buf.readBytes(putArray);//读取存放id
        byte[] packageIdArray= new byte[PACKGEID];//包裹id
        buf.readBytes(packageIdArray);//读取包裹id



    }
}
