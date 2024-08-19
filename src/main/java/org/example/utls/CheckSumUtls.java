package org.example.utls;

/**
 * CheckSumUtls类用于计算校验和
 */
public final class CheckSumUtls {
    public static int CheckSum(byte[] data) {//计算校验和
        // 在实际应用中，你应该使用更强大的校验和算法
        int checksum = 0;// 初始化校验和为0
        for (byte b : data) {// 遍历数据中的每个字节
            checksum += b;// 将字节加到校验和中
        }// 计算校验和
        return checksum;// 返回校验和
    }
}
