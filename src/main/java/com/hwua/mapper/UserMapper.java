package com.hwua.mapper;

import com.hwua.pojo.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    public User queryUserByUser(User user) throws Exception;//

    public Integer saveUser(User user) throws Exception;;//注册

    public List<User> queryAllUsers() throws Exception;; //查询所有的用户

    public User queryUserBySendid(Long sendid) throws Exception;; //查询发送者用户信息

    public String queryUserByName(String name) throws Exception;;//判断用户名是否已经注册
}
