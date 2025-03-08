package cn.covn.visasystem.controller;


import cn.covn.visasystem.comment.response.JsonResult;
import cn.covn.visasystem.comment.response.StatusCode;
import cn.covn.visasystem.pojo.dto.UserLoginVO;
import cn.covn.visasystem.pojo.vo.UserLoginDTO;
import cn.covn.visasystem.service.user.IUserService;
import cn.covn.visasystem.service.user.impl.UserService;
import io.swagger.v3.core.util.Json;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public JsonResult login(@Validated UserLoginVO loginVO) {

        UserLoginDTO userLoginDTO=userService.checkUserByUsername(loginVO.getUsername(),loginVO.getPassword());
        if(userLoginDTO==null) {
            return new JsonResult(StatusCode.OPERATION_FAILED,"アカウント名かパスワードが違います");
        }
        return JsonResult.ok("ログイン成功‼",userLoginDTO);
    }




}
