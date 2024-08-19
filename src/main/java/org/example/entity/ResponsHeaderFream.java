package org.example.entity;

import lombok.Data;

/**
 * 响应数据头部帧
 */
@Data
public class ResponsHeaderFream {
    final byte[] frameHeader = { (byte) 0xAA, (byte) 0xBB };// 帧头
    byte frameType;// 帧类型
    short dataLength;// 数据长度
    byte[] data;// 数据
    int checksum;// 校验和
}
