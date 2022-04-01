package com.lele.srb.core.service.impl;

import com.lele.srb.core.mapper.UserInfoMapper;
import com.lele.srb.core.pojo.entity.UserInfo;
import com.lele.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author lele
 * @since 2022-03-27
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
