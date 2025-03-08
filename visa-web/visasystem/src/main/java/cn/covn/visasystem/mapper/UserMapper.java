package cn.covn.visasystem.mapper;

import cn.covn.visasystem.pojo.vo.UserLoginDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    // select id,password,is_admin isAdmin from user where username=#{username}
    UserLoginDTO selectAdminUserByUsername(String username);
}
