package cn.covn.visasystem.service.user.impl;

import cn.covn.visasystem.mapper.UserMapper;
import cn.covn.visasystem.pojo.vo.UserLoginDTO;
import cn.covn.visasystem.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserLoginDTO checkUserByUsername(String username,String password) {
        UserLoginDTO userLoginDTO = userMapper.selectAdminUserByUsername(username);
        log.error("19:::userLoginDTO:{}",userLoginDTO);
        if(userLoginDTO.getPassword().equals(password)){
            return userLoginDTO;
        }
        return null;
    }
}
