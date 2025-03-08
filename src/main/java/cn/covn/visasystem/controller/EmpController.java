package cn.covn.visasystem.controller;

import cn.covn.visasystem.comment.response.JsonResult;
import cn.covn.visasystem.pojo.vo.EmpInfoDTO;
import cn.covn.visasystem.pojo.vo.EmpListDTO;
import cn.covn.visasystem.pojo.vo.EmpSearchNameDTO;
import cn.covn.visasystem.service.emp.IEmpService;
import cn.covn.visasystem.service.emp.impl.EmpService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/emp")
@Validated
@Slf4j
public class EmpController {
    @Autowired
    private IEmpService empService;
    @GetMapping("list")
    public JsonResult getEmpList() {
        ArrayList<EmpListDTO> empList = empService.getEmpList();
        return JsonResult.ok(empList);
    }

    @GetMapping("search/list")
    public JsonResult getSearchNameList() {
        ArrayList<EmpSearchNameDTO> nameList = empService.getSearchNameList();
        log.debug("getSearchNameList::52:::nameList:{}", nameList);
        return JsonResult.ok(nameList);
    }
    @GetMapping("search/name")
    public JsonResult searchName(String name) {
        if(!name.isEmpty()){
            EmpListDTO emp = empService.SearchName(name);
            return JsonResult.ok(emp);
        }else{
            return JsonResult.ok(empService.getEmpList());
        }
    }
    @GetMapping("search/time")
    public JsonResult searchName(int day) {
        if(day>0){
            EmpListDTO emp = empService.SearchTime(day);
            return JsonResult.ok(emp);
        }else{
            return JsonResult.ok(empService.getEmpList());
        }
    }

    @GetMapping("{id}/info")
    public JsonResult empInfo(@PathVariable("id") Integer id) {
        EmpInfoDTO empInfo=empService.getEmpInfo(id);
        log.debug(empInfo.toString());
        return JsonResult.ok(empInfo);
    }





}
