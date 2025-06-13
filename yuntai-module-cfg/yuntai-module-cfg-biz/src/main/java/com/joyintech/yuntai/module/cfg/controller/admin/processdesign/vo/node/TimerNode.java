package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node;

import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.enums.TimerWaitType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TimerNode extends Node {
    private TimerWaitType waitType;
    private String unit;
    private Integer duration;
    private String timeDate;


}
