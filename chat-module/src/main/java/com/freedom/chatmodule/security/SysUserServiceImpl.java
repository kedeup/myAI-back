package com.freedom.chatmodule.security;

import com.freedom.chatmodule.domain.UserAuthInfo;
import com.freedom.chatmodule.domapper.UserAuthInfoMapper;
import com.freedom.chatmodule.security.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author kede·W  on  2023/4/13
 */
@Service
public class SysUserServiceImpl implements UserDetailsService {
    @Autowired
    UserAuthInfoMapper userAuthInfoMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        TODO
        /*通过Mapper 获取 user_auth 中的信息*/
        UserAuthInfo userAuthInfo = userAuthInfoMapper.selectByIdentifier(username);


        return new SysUser(userAuthInfo.getUserId(),userAuthInfo.getIdentifier(),
                userAuthInfo.getCredential());
    }
}
