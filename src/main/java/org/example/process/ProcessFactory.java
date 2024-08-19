package org.example.process;

import org.example.entity.CmdTypeEnum;
import org.example.process.strategy.ProcessStrategy;
import org.example.process.strategy.PutParcelStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ProcessFactory表示一个工厂类，用于根据不同的命令类型创建不同的处理策略
 */
@Service
public class ProcessFactory {
    @Autowired
    private Map<String, ProcessStrategy> strategyMap;// 存储所有的处理策略
    public ProcessStrategy GetProcess(int cmd){// 根据命令类型获取对应的处理策略
        return strategyMap.get(CmdTypeEnum.getStrategyEnum(cmd).getStrategyName());// 根据命令类型获取对应的处理策略
    }
}
