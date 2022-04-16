package com.lele.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lele.srb.core.pojo.entity.UserInfo;
import com.lele.srb.core.pojo.query.UserInfoQuery;
import com.lele.srb.core.pojo.vo.LoginVO;
import com.lele.srb.core.pojo.vo.RegisterVO;
import com.lele.srb.core.pojo.vo.UserInfoVO;

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

    UserInfoVO login(LoginVO loginVO, String ip);

    IPage<UserInfo> listPage(Page<UserInfo> userInfoPage, UserInfoQuery userInfoQuery);

    void lock(Long id,Integer status);

    boolean checkMobile(String mobile);
}
