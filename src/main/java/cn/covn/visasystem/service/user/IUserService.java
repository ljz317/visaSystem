package cn.covn.visasystem.service.user;

import cn.covn.visasystem.pojo.vo.UserLoginDTO;

public interface IUserService {
    UserLoginDTO checkUserByUsername(String username,String password);
}
