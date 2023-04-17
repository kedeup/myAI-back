package com.freedom.chatmodule.domapper;

import com.freedom.chatmodule.domain.UserAuthInfo;

import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthInfoMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByUserId(Long id);
    int insert(UserAuthInfo row);

    int insertSelective(UserAuthInfo row);

    UserAuthInfo selectByPrimaryKey(Integer id);

    UserAuthInfo selectByIdentifier(String identifier);

    int updateByPrimaryKeySelective(UserAuthInfo row);

    int updateByPrimaryKey(UserAuthInfo row);
}