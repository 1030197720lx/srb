package com.lele.srb.core.service;

import com.lele.srb.core.pojo.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lele.srb.core.pojo.vo.RegisterVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author lele
 * @since 2022-03-27
 */
public interface UserInfoService extends IService<UserInfo> {

    void register(RegisterVO registerVO);
}
