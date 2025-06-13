package com.joyintech.yuntai.module.system.controller.admin.sysuser;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;
import com.joyintech.yuntai.module.system.sysuser.service.SysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 用户")
@RestController
@RequestMapping("/comm/sysuser")
@Validated
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    @GetMapping("/getUserList")
    public CommonResult<List<SysUser>> getUserList(@RequestParam("idList") String idList) {
        List<String> ids= Arrays.asList(idList.split(","));
        List<SysUser> userList = sysUserService.getUserList(ids);
        return success(userList);
    }

}