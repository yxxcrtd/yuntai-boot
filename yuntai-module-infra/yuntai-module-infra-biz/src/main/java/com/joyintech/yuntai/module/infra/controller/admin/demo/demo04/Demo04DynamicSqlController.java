package com.joyintech.yuntai.module.infra.controller.admin.demo.demo04;

import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.mybatis.core.util.DBDynamicSqlExecutorUtils;
import com.joyintech.yuntai.framework.mybatis.core.util.MyBatisUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - 示例动态SQL")
@RestController
@RequestMapping("/infra/demo04-dynamic-sql")
@Validated
public class Demo04DynamicSqlController {

    @Resource
    private DBDynamicSqlExecutorUtils sqlExecutorUtils;

    @GetMapping("/page")
    @Operation(summary = "动态SQL分页")
    public CommonResult<PageResult<Map<String, Object>>> getDynamicSqlPage(@Valid PageParam pageParam) {
        Map<String, Object> paramMap = new HashMap<>();
        IPage<Object> page = MyBatisUtils.buildPage(pageParam);
        paramMap.put("page", page);
        paramMap.put("tableId", 1846787453872283649L);
        String sql = "select * from cfg_column_definition  ";
        sql += "<if  test='tableId != null'>where table_id = #{tableId}</if>";
        List<Map<String, Object>> listData = sqlExecutorUtils.findListDataWithParse(sql, paramMap);
        System.out.println(listData.size() + " " + page.getTotal());

        page.setSize(5);
        paramMap.put("tableId", null);
        listData = sqlExecutorUtils.findListDataWithParse(sql, paramMap);
        System.out.println(listData.size() + " " + page.getTotal());
        PageResult<Map<String, Object>> pageResult = new PageResult<>(listData, page.getTotal());
        return success(pageResult);
    }

}