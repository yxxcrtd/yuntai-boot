package com.joyintech.yuntai.server.demo.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;

/**
 * TODO:描述
 *
 * @author abator 2024/10/9
 */
public class MapValidator {

    public static Set<ConstraintViolation<Map<String, Object>>> validateMap(Map<String, Object> userMap) {
        // 创建 Validator 实例
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Map<String, Object> map = new HashMap<>();
        // 创建校验规则
        javax.validation.metadata.BeanDescriptor beanDescriptor = validator.getConstraintsForClass(Map.class);
        javax.validation.metadata.PropertyDescriptor propertyDescriptor = beanDescriptor.getConstraintsForProperty("username");

        // // 添加约束
        // propertyDescriptor.addConstraint(NotNull.class);
        // propertyDescriptor.addConstraint(Size.class).addParameter("max", 10);
        // propertyDescriptor.addConstraint(Pattern.class).addParameter("regexp", "[a-z][a-z0-9]*");

        // 手动触发校验
        return validator.validate(userMap);
    }
}
