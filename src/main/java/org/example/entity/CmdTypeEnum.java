package org.example.entity;

import lombok.Data;
import org.example.process.strategy.PutParcelStrategy;

/**
 * CmdTypeEnum表示命令枚举
 */
public enum CmdTypeEnum {
    PutParcel(100, PutParcelStrategy.class.getName());// 放包裹

    private String strategyName;// 策略名称
    private Integer cmd;// 命令

    public String getStrategyName() {//getStrategyName表示获取策略名称
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Integer getCmd() {// getCmd表示获取命令
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    CmdTypeEnum(Integer code, String strategyName) {// 构造函数
        this.strategyName = strategyName;
        this.cmd = code;
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static CmdTypeEnum getStrategyEnum(int code) {// getStrategyEnum表示获取策略枚举
        CmdTypeEnum[] payChannelStrategyEnums = CmdTypeEnum.values();// 获取枚举数组
        CmdTypeEnum result = null;// 定义结果
        for (CmdTypeEnum payChannelStrategyEnum : payChannelStrategyEnums) {// 遍历枚举数组
            if (payChannelStrategyEnum.getCmd() == code) {// 如果命令等于code
                result = payChannelStrategyEnum;// 设置结果
                break;// 跳出循环
            }
        }
        return result;
    }

}
