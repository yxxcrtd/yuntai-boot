package com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node;

import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.enums.NotifyTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotifyNode extends AssigneeNode {
    private List<NotifyTypeEnum> types;
    private String subject;
    private String content;

}
