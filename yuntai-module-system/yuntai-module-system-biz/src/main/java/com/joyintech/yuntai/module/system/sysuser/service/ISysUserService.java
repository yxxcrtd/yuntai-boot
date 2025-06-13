package com.joyintech.yuntai.module.system.sysuser.service;

import java.util.Collection;
import java.util.List;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import com.joyintech.yuntai.module.system.controller.admin.user.vo.user.UserPageReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dept.DeptDO;
import com.joyintech.yuntai.module.system.dal.dataobject.user.AdminUserDO;

public interface ISysUserService extends SysUserService{

    List<DeptDO> getDeptListNew(DeptListReqVO reqVO);

    List<DeptDO> getDeptListNew(Collection<Long> ids);

    List<AdminUserDO> getUserListByStatusNew(Integer status);

    PageResult<AdminUserDO> getUserPageNew(UserPageReqVO reqVO, Collection<Long> deptIds);
}
