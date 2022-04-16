package com.lele.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lele.common.exception.Assert;
import com.lele.common.result.ResponseEnum;
import com.lele.common.util.MD5;
import com.lele.srb.base.util.JwtUtils;
import com.lele.srb.core.mapper.UserAccountMapper;
import com.lele.srb.core.mapper.UserInfoMapper;
import com.lele.srb.core.mapper.UserLoginRecordMapper;
import com.lele.srb.core.pojo.entity.UserAccount;
import com.lele.srb.core.pojo.entity.UserInfo;
import com.lele.srb.core.pojo.entity.UserLoginRecord;
import com.lele.srb.core.pojo.query.UserInfoQuery;
import com.lele.srb.core.pojo.vo.LoginVO;
import com.lele.srb.core.pojo.vo.RegisterVO;
import com.lele.srb.core.pojo.vo.UserInfoVO;
import com.lele.srb.core.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private UserLoginRecordMapper userLoginRecordMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(RegisterVO registerVO) {
        //判断手机号是否已经注册
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("mobile", registerVO.getMobile());
        Integer count = baseMapper.selectCount(userInfoQueryWrapper);
        Assert.isTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);
        //插入用户信息

        UserInfo userInfo = new UserInfo();
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setName(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        userInfo.setHeadImg(UserInfo.USER_AVATAR);
        baseMapper.insert(userInfo);

        //插入用户账户
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountMapper.insert(userAccount);

    }

    @Override
    public UserInfoVO login(LoginVO loginVO, String ip) {
        String mobile = loginVO.getMobile();
        Integer userType = loginVO.getUserType();
        String password = loginVO.getPassword();

        //查询用户
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper
                .eq("mobile", mobile)
                .eq("user_type", userType);

        UserInfo userInfo = baseMapper.selectOne(userInfoQueryWrapper);

        //用户是否存在
        Assert.notNull(userInfo,ResponseEnum.LOGIN_MOBILE_ERROR);

        //密码是否正确
        Assert.equals(MD5.encrypt(password),userInfo.getPassword(),ResponseEnum.LOGIN_PASSWORD_ERROR);

        //用户是否锁定
        Assert.equals(userInfo.getStatus(),UserInfo.STATUS_NORMAL,ResponseEnum.LOGIN_LOKED_ERROR);

        //插入登录日志
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userInfo.getId());
        userLoginRecord.setIp(ip);
        userLoginRecordMapper.insert(userLoginRecord);

        //生成token
        String token = JwtUtils.createToken(userInfo.getId(), userInfo.getName());

        //返回UserInfoVO
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setToken(token);
        userInfoVO.setUserType(userInfo.getUserType());
        userInfoVO.setMobile(userInfo.getMobile());
        userInfoVO.setName(userInfo.getName());
        userInfoVO.setHeadImg(userInfo.getHeadImg());
        userInfoVO.setNickName(userInfo.getNickName());
        return userInfoVO;
    }

    @Override
    public IPage<UserInfo> listPage(Page<UserInfo> userInfoPage, UserInfoQuery userInfoQuery) {
        if(userInfoQuery == null){
            return baseMapper.selectPage(userInfoPage,null);
        }
        String mobile = userInfoQuery.getMobile();
        Integer status = userInfoQuery.getStatus();
        Integer userType = userInfoQuery.getUserType();

        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();

        userInfoQueryWrapper.eq(StringUtils.isNotBlank(mobile),"mobile",mobile)
                .eq(userType!=null,"user_type", userType)
                .eq(status!=null,"status", status);
//        if(StringUtils.isNotBlank(mobile)){
//            userInfoQueryWrapper.eq("mobile",mobile);
//        }
//        if(userType!=null) {
//            userInfoQueryWrapper.eq("user_type", userType);
//        }
//        if(status!=null) {
//            userInfoQueryWrapper.eq("status", status);
//        }

        return baseMapper.selectPage(userInfoPage,userInfoQueryWrapper);

    }

    @Override
    public void lock(Long id, Integer status) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setStatus(status);
        baseMapper.updateById(userInfo);
    }

    @Override
    public boolean checkMobile(String mobile) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(userInfoQueryWrapper);
        return count>0;
    }
}
