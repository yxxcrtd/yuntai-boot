package com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.enums;

import com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo.CreditRatingAssetReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo.PublicFundCreateReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo.PublicFundUpdateReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo.UpdateRatingAssetReqVO;
import lombok.Getter;

@Getter
public enum ExcelTemplateEnum {

    // 信评资产模板
    CREDIT_ASSET_CREATE("信评资产新增模板.xlsx", CreditRatingAssetReqVO.class),
    CREDIT_ASSET_UPDATE("信评资产变更模板.xlsx", UpdateRatingAssetReqVO.class),

    // 公募资管产品模板
    PUBLIC_FUND_CREATE("公募资管产品新增模板.xlsx", PublicFundCreateReqVO.class),
    PUBLIC_FUND_UPDATE("公募资管产品变更模板.xlsx", PublicFundUpdateReqVO.class);

    // 可转债/可交换债模板
//    CONVERTIBLE_BOND_CREATE("可转债可交换债新增模板.xlsx", ConvertibleBondCreateVO.class),
//    CONVERTIBLE_BOND_UPDATE("可转债可交换债变更模板.xlsx", ConvertibleBondUpdateVO.class),

    // 私募资管产品模板
//    PRIVATE_FUND_CREATE("私募资管产品新增模板.xlsx", PrivateFundCreateVO.class),
//    PRIVATE_FUND_UPDATE("私募资管产品变更模板.xlsx", PrivateFundUpdateVO.class);

    // Getter 方法
    private final String fileName;  // Excel 模板文件名
    private final Class<?> entityClass; // 关联的实体类

    ExcelTemplateEnum(String fileName, Class<?> entityClass) {
        this.fileName = fileName;
        this.entityClass = entityClass;
    }

    public static Class<?> getEntityClassByFileName(String fileName) {
        for (ExcelTemplateEnum template : values()) {
            if (fileName.equalsIgnoreCase(template.fileName)) {
                return template.entityClass;
            }
        }
        throw new IllegalArgumentException("未找到匹配的Excel模板: " + fileName);
    }

}
